package gov.ca.cwds.rest.services.cms;

import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.cms.AssignmentUnitDao;
import gov.ca.cwds.data.cms.CaseLoadDao;
import gov.ca.cwds.data.cms.CwsOfficeDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.PostedAssignment;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.R01054PrimaryAssignmentAdding;
import gov.ca.cwds.rest.business.rules.R02473DefaultReferralAssignment;
import gov.ca.cwds.rest.business.rules.R04530AssignmentEndDateValidator;
import gov.ca.cwds.rest.business.rules.R06560CaseloadRequiredForFirstPrimaryAssignment;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object serves {@link Assignment}.
 * 
 * @author CWDS API Team
 */
public class AssignmentService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.Assignment, gov.ca.cwds.rest.api.domain.cms.Assignment> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentService.class);

  private AssignmentDao assignmentDao;
  private NonLACountyTriggers nonLACountyTriggers;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;
  private ExternalInterfaceTables externalInterfaceTables;
  private CaseLoadDao caseLoadDao;
  private ReferralDao referralDao;
  private AssignmentUnitDao assignmentUnitDao;
  private CwsOfficeDao cwsOfficeDao;
  private MessageBuilder messageBuilder; // not thread-safe, cannot re-use across requests

  private Validator validator;

  /**
   * Constructor
   * 
   * @param assignmentDao assignmentDao
   * @param nonLACountyTriggers nonLACountyTriggers
   * @param staffpersonDao staffpersonDao
   * @param triggerTablesDao triggerTablesDao
   * @param validator validator
   * @param externalInterfaceTables externalInterfaceTables
   * @param caseLoadDao caseLoadDao
   * @param referralDao referralDao
   * @param assignmentUnitDao assignmentUnitDao
   * @param cwsOfficeDao cwsOfficeDao
   * @param messageBuilder messageBuilder
   */
  @Inject
  public AssignmentService(AssignmentDao assignmentDao, NonLACountyTriggers nonLACountyTriggers,
      StaffPersonDao staffpersonDao, TriggerTablesDao triggerTablesDao, Validator validator,
      ExternalInterfaceTables externalInterfaceTables, CaseLoadDao caseLoadDao,
      ReferralDao referralDao, AssignmentUnitDao assignmentUnitDao, CwsOfficeDao cwsOfficeDao,
      MessageBuilder messageBuilder) {
    this.assignmentDao = assignmentDao;
    this.nonLACountyTriggers = nonLACountyTriggers;
    this.staffpersonDao = staffpersonDao;
    this.triggerTablesDao = triggerTablesDao;
    this.validator = validator;
    this.externalInterfaceTables = externalInterfaceTables;
    this.caseLoadDao = caseLoadDao;
    this.referralDao = referralDao;
    this.assignmentUnitDao = assignmentUnitDao;
    this.cwsOfficeDao = cwsOfficeDao;
    this.messageBuilder = messageBuilder;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Assignment find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.Assignment persistedAssignment =
        assignmentDao.find(primaryKey);
    if (persistedAssignment != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Assignment(persistedAssignment);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Assignment delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.Assignment persistedAssignment =
        assignmentDao.delete(primaryKey);
    externalInterfaceTables.createExtInterForDelete(primaryKey, LegacyTable.ASSIGNMENT.getName());
    if (persistedAssignment != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Assignment(persistedAssignment);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedAssignment create(gov.ca.cwds.rest.api.domain.cms.Assignment request) {

    try {
      Assignment managed = new Assignment(
          CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()), request,
          RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());
      this.validateAssignmentEndDate(request);
      managed = assignmentDao.create(managed);
      if (managed.getId() == null) {
        throw new ServiceException("Assignment ID cannot be null");
      }
      createDownStreamEntity(managed);
      executeR01054Rule(managed);
      executeR06560Rule(managed);
      return new PostedAssignment(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Assignment already exists : {}", request);
      throw new ServiceException(e);
    }
  }

  private void createDownStreamEntity(Assignment managed) {
    // checking the staffPerson county code
    StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
    if (staffperson != null
        && !(triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
      nonLACountyTriggers.createAndUpdateReferralCountyOwnership(managed);
    }
    externalInterfaceTables.createExtInterAssignment(managed, "N");
  }

  private void executeR01054Rule(Assignment managed) {
    R01054PrimaryAssignmentAdding r01054Rule = new R01054PrimaryAssignmentAdding(managed,
        referralDao, caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    try {
      r01054Rule.execute();
    } catch (Exception e) {
      String message = "R01054 rule execution is failed for assignment: " + managed.getId();
      messageBuilder.addMessageAndLog(message, e, LOGGER);
    }
  }

  void executeR06560Rule(Assignment managed) {
    R06560CaseloadRequiredForFirstPrimaryAssignment r06560Rule =
        new R06560CaseloadRequiredForFirstPrimaryAssignment(managed);
    if (!r06560Rule.isValid()) {
      messageBuilder.addError("R - 06560 Caseload Required For First Primary Asg is failed",
          ErrorMessage.ErrorType.BUSINESS);
      throw new BusinessValidationException(messageBuilder.getIssues());
    }
  }

  /**
   * @param screeningToReferral - screeningToReferral
   * @param referralId - referralId
   * @param referral - referral
   * @param strsMessageBuilder - ScreeningToReferralService messageBuilder
   */
  public void createDefaultAssignmentForNewReferral(ScreeningToReferral screeningToReferral,
      String referralId, Referral referral, MessageBuilder strsMessageBuilder) {
    R02473DefaultReferralAssignment r02473DefaultReferralAssignment =
        new R02473DefaultReferralAssignment(screeningToReferral, referralId, referral,
            strsMessageBuilder, assignmentDao, externalInterfaceTables, validator, this);
    r02473DefaultReferralAssignment.execute();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Assignment update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.Assignment request) {

    try {
      Assignment managed =
          new Assignment(primaryKey, request, RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      this.validateAssignmentEndDate(request);
      managed = assignmentDao.update(managed);
      externalInterfaceTables.createExtInterAssignment(managed, "C");
      return new gov.ca.cwds.rest.api.domain.cms.Assignment(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Assignment not found : {}", request);
      throw new ServiceException(e);
    }
  }

  private void validateAssignmentEndDate(gov.ca.cwds.rest.api.domain.cms.Assignment assignment) {
    if (!new R04530AssignmentEndDateValidator(assignment).isValid()) {
      throw new ServiceException(
          "Rule : R - 04530 - Assignment End Date and Time must be less than or equal to the current system date and time AND must be greater than or equal to Assignment Start Date and Time");
    }
  }

  // used by DocTool Rule R04611 - R04611ReferralStartDateTimeValidator
  public Assignment findReferralFirstAssignment(String referralId) {
    Assignment firstAssignment = null;
    Set<Assignment> assignmentSet = assignmentDao.findAssignmentsByReferralId(referralId);
    for (Assignment assignment : assignmentSet) {
      if (firstAssignment == null || isAssignmentStartedEarlier(assignment, firstAssignment)) {
        firstAssignment = assignment;
      }
    }
    return firstAssignment;
  }

  private boolean isAssignmentStartedEarlier(Assignment assignment1, Assignment assignment2) {
    if (assignment1.getStartDate().before(assignment2.getStartDate())) {
      return true;
    }
    return assignment1.getStartDate().equals(assignment2.getStartDate())
        && assignment1.getStartTime().before(assignment2.getStartTime());
  }

  public MessageBuilder getMessageBuilder() {
    return messageBuilder;
  }
}
