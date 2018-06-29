package gov.ca.cwds.rest.business.rules;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.RuleAction;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.validation.StartDateTimeValidator;

/**
 * <p>
 * BUSINESS RULE: "R - 02473 Default Referral Assignment"
 * 
 * The initial assignment to a referral defaults to the caseLoad of the worker entering the referral
 * into the application (when that staff person has only one active caseLoad which is not on hold.)
 * <p>
 * 
 * @author CWS-NS2
 * @see Assignment
 */
public class R02473DefaultReferralAssignment implements RuleAction {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(R02473DefaultReferralAssignment.class);

  private ScreeningToReferral screeningToReferral;
  private String referralId;
  private Referral referral;
  private MessageBuilder strsMessageBuilder;
  private AssignmentDao assignmentDao;
  private ExternalInterfaceTables externalInterfaceTables;
  private Validator validator;
  private AssignmentService assignmentService;

  /**
   * @param screeningToReferral - screeningToReferral
   * @param referralId - referralId
   * @param referral - referral
   * @param strsMessageBuilder - ScreeningToReferralService messageBuilder
   * @param assignmentDao - assignmentDao
   * @param externalInterfaceTables - externalInterfaceTables
   * @param validator - validator
   * @param assignmentService - assignmentService
   */
  public R02473DefaultReferralAssignment(ScreeningToReferral screeningToReferral, String referralId,
      Referral referral, MessageBuilder strsMessageBuilder, AssignmentDao assignmentDao,
      ExternalInterfaceTables externalInterfaceTables, Validator validator,
      AssignmentService assignmentService) {
    super();
    this.screeningToReferral = screeningToReferral;
    this.referralId = referralId;
    this.referral = referral;
    this.strsMessageBuilder = strsMessageBuilder;
    this.assignmentDao = assignmentDao;
    this.externalInterfaceTables = externalInterfaceTables;
    this.validator = validator;
    this.assignmentService = assignmentService;
  }

  @Override
  public void execute() {

    if (isValidAssigneeStaffId(screeningToReferral)) {
      CaseLoad caseLoad = null;
      String caseLoadId = null;
      CaseLoad[] caseLoads = null;
      String countyCode = "00";

      caseLoads = assignmentDao.findCaseLoads(screeningToReferral.getAssigneeStaffId());
      if (caseLoads != null && caseLoads.length > 0) {
        caseLoad = caseLoads[0];
      }
      if (caseLoad == null) {
        String message = "R - 02473 Caseload is either inactive or on hold";
        ServiceException se = new ServiceException(message);
        strsMessageBuilder.addMessageAndLog(message, se, LOGGER);
      } else {
        caseLoadId = caseLoad.getId();
        countyCode = caseLoad.getCountySpecificCode();
      }

      gov.ca.cwds.rest.api.domain.cms.Assignment defaultAssignment =
          createDefaultAssignmentToCaseLoad(countyCode, referralId,
              screeningToReferral.getStartedAt(), caseLoadId, strsMessageBuilder);
      strsMessageBuilder.addDomainValidationError(validator.validate(defaultAssignment));

      setStartTime(defaultAssignment);
      createExternalInterface(defaultAssignment);
      try {
        assignmentService.getMessageBuilder().merge(strsMessageBuilder);
        assignmentService.create(defaultAssignment);
      } catch (ServiceException e) {
        strsMessageBuilder.addMessageAndLog(e.getMessage(), e, LOGGER);
      }
    }

  }

  private void setStartTime(gov.ca.cwds.rest.api.domain.cms.Assignment da) {
    if (!new R03731StartTimeSetting(referral, da).isValid()) {
      String message =
          "Referral Recieved Date/Time does not equal Assignment Start Date/Time (R - 03731)";
      ServiceException se = new ServiceException(message);
      strsMessageBuilder.addMessageAndLog(message, se, LOGGER);
    }
  }

  private void createExternalInterface(gov.ca.cwds.rest.api.domain.cms.Assignment da) {
    if ("R".equals(da.getEstablishedForCode())
        && ("P".equals(da.getTypeOfAssignmentCode()) || ("S".equals(da.getTypeOfAssignmentCode())))
        || (da.getSecondaryAssignmentRoleType() == 143)) {
      externalInterfaceTables.createExternalInterfaceReferral(referralId, "C");
    }
  }

  /**
   * @param countyCode - county code
   * @param referralId - referral Id
   * @param startDateTime - start date of assignment
   * @param caseLoadId - case load id of assignment
   * @return - default Assignment
   */
  private gov.ca.cwds.rest.api.domain.cms.Assignment createDefaultAssignmentToCaseLoad(
      String countyCode, String referralId, String startDateTime, String caseLoadId,
      MessageBuilder messageBuilder) {

    String dateStarted = StartDateTimeValidator.extractStartDate(startDateTime, messageBuilder);
    String timeStarted = StartDateTimeValidator.extractStartTime(startDateTime, messageBuilder);

    gov.ca.cwds.rest.api.domain.cms.Assignment assignment =
        new gov.ca.cwds.rest.api.domain.cms.Assignment();
    return assignment.createDefaultReferralAssignment(countyCode, referralId, caseLoadId,
        dateStarted, timeStarted);
  }

  private boolean isValidAssigneeStaffId(ScreeningToReferral screeningToReferral) {
    if (!screeningToReferral.getAssigneeStaffId()
        .equals(RequestExecutionContext.instance().getStaffId())) {
      strsMessageBuilder.addError("Assignee Staff Id is not the same as logged in User Staff Id");
      return false;
    }
    return true;
  }

}
