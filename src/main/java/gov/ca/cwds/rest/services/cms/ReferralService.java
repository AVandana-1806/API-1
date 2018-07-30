package gov.ca.cwds.rest.services.cms;

import static java.lang.Math.min;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedDrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.business.rules.CountyOfAssignedStaffWorker;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.R00818SetReferredResourceType;
import gov.ca.cwds.rest.business.rules.R04611ReferralStartDateTimeAction;
import gov.ca.cwds.rest.business.rules.R04611ReferralStartDateTimeValidator;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferral;
import gov.ca.cwds.rest.util.DocUtils;
import gov.ca.cwds.rest.validation.ParticipantValidator;

/**
 * Business layer object to work on {@link Referral}
 *
 * @author CWDS API Team
 */
public class ReferralService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.Referral, gov.ca.cwds.rest.api.domain.cms.Referral> {

  private static final short NOT_REFERRED = (short) 3225;

  private static final Logger LOGGER = LoggerFactory.getLogger(ReferralService.class);

  private ReferralDao referralDao;
  private NonLACountyTriggers nonLaTriggers;
  private LACountyTrigger laCountyTrigger;
  private TriggerTablesDao triggerTablesDao;
  private StaffPersonDao staffpersonDao;

  private Validator validator;
  private AssignmentService assignmentService;
  private CmsDocumentService cmsDocumentService;
  private DrmsDocumentService drmsDocumentService;
  private DrmsDocumentTemplateService drmsDocumentTemplateService;
  private AddressService addressService;
  private LongTextService longTextService;
  private RIReferral riReferral;

  /**
   * Constructor
   *
   * @param referralDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects.
   * @param nonLaTriggers The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.NonLACountyTriggers} objects
   * @param laCountyTrigger The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.LACountyTrigger} objects
   * @param triggerTablesDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.rules.TriggerTablesDao} objects
   * @param staffpersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects
   * @param assignmentService the Assignment Service
   * @param validator the validator used for entity validation
   * @param cmsDocumentService the service for storing Cms Documents
   * @param drmsDocumentService the service for generating DRMS Documents
   * @param drmsDocumentTemplateService the service for DRMS Document Templates
   * @param addressService the service for creating addresses
   * @param longTextService the longText Service
   * @param riReferral FK to Referral
   */
  @Inject
  public ReferralService(final ReferralDao referralDao, NonLACountyTriggers nonLaTriggers,
      LACountyTrigger laCountyTrigger, TriggerTablesDao triggerTablesDao,
      StaffPersonDao staffpersonDao, AssignmentService assignmentService, Validator validator,
      CmsDocumentService cmsDocumentService, DrmsDocumentService drmsDocumentService,
      DrmsDocumentTemplateService drmsDocumentTemplateService, AddressService addressService,
      LongTextService longTextService, RIReferral riReferral) {
    this.referralDao = referralDao;
    this.nonLaTriggers = nonLaTriggers;
    this.laCountyTrigger = laCountyTrigger;
    this.triggerTablesDao = triggerTablesDao;
    this.staffpersonDao = staffpersonDao;
    this.assignmentService = assignmentService;
    this.validator = validator;
    this.cmsDocumentService = cmsDocumentService;
    this.drmsDocumentService = drmsDocumentService;
    this.drmsDocumentTemplateService = drmsDocumentTemplateService;
    this.addressService = addressService;
    this.longTextService = longTextService;
    this.riReferral = riReferral;
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Referral find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.Referral persistedReferral = referralDao.find(primaryKey);
    if (persistedReferral != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Referral(persistedReferral);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Referral delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.Referral persistedReferral = referralDao.delete(primaryKey);
    if (persistedReferral != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Referral(persistedReferral);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedReferral create(gov.ca.cwds.rest.api.domain.cms.Referral request) {
    try {
      StaffPerson staffperson =
          staffPersonValidate(RequestExecutionContext.instance().getStaffId());
      validateCountyOfAssignedStaffWorker(request);

      // work around to be able to pass externally generated ID
      String referralId =
          CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId());
      if (!StringUtils.isBlank(request.getUiIdentifier())
          && request.getUiIdentifier().length() == 10) {
        referralId = request.getUiIdentifier();
        request.setUiIdentifier(null);
      }

      Referral managed =
          new Referral(referralId, request, RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());

      managed = referralDao.create(managed);
      if (managed == null || managed.getId() == null) {
        LOGGER.warn("Unable to save referral: {}", request);
        throw new ServiceException("Referral Not successfully saved");
      }
      createLACountyTrigger(staffperson, managed);
      return new PostedReferral(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Referral already exists : {}", request);
      throw new ServiceException(e);
    }
  }

  private void validateCountyOfAssignedStaffWorker(
      gov.ca.cwds.rest.api.domain.cms.Referral referral) {
    if (!new CountyOfAssignedStaffWorker(referral, staffpersonDao).isValid()) {
      LOGGER.error("Assigned Staff Person County must be the same as the Incident County");
      throw new ServiceException(
          "Assigned Staff Person County must be the same as the Incident County");
    }
  }

  private void createLACountyTrigger(StaffPerson staffperson, Referral managed) {
    // checking the staffPerson county code
    if (staffperson != null
        && (triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
      laCountyTrigger.createCountyTrigger(managed);
    }
  }

  private StaffPerson staffPersonValidate(String staffPersonId) {
    if (staffPersonId == null) {
      LOGGER.error("Staff Person Id was not found");
      throw new ServiceException("Staff Person Id was not found.");
    }
    return staffpersonDao.find(staffPersonId);
  }

  /**
   * @param screeningToReferral - screeningToReferral
   * @param dateStarted - dateStarted
   * @param timeStarted - timeStarted
   * @param strsMessageBuilder - ScreeningToReferralService messageBuilder
   * @return the cmsReferral
   */
  public String createCmsReferralFromScreening(ScreeningToReferral screeningToReferral,
      String dateStarted, String timeStarted, MessageBuilder strsMessageBuilder) {

    String referralId;

    if (screeningToReferral.getReferralId() == null
        || screeningToReferral.getReferralId().isEmpty()) {
      // the legacy id is not set - create the referral
      // create a CMS Referral
      gov.ca.cwds.rest.api.domain.cms.Referral referral = null;
      try {
        referral = createReferralWithDefaults(screeningToReferral, dateStarted, timeStarted,
            strsMessageBuilder);
      } catch (ServiceException | NullPointerException e) {
        strsMessageBuilder.addMessageAndLog(e.getMessage(), e, LOGGER);
      } catch (Exception e) {
        strsMessageBuilder.addMessageAndLog(e.getMessage(), e, LOGGER);
        throw e;
      }

      if (referral == null) {
        return null;
      }

      strsMessageBuilder.addDomainValidationError(validator.validate(referral));

      /*
       * Attach default screener narrative created from template
       */
      referralId = CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId());

      String screenerNarrativeId =
          createDefaultSreenerNarrativeForNewReferral(screeningToReferral, referral, referralId);
      if (!StringUtils.isBlank(screenerNarrativeId)) {
        // Pass the referral Id
        referral.setUiIdentifier(referralId);
        referral.setDrmsAllegationDescriptionDoc(screenerNarrativeId);
      }

      PostedReferral postedReferral = this.create(referral);
      referralId = postedReferral.getId();

      // when creating a referral - create the default assignment to 0XA staff person
      assignmentService.createDefaultAssignmentForNewReferral(screeningToReferral, referralId,
          referral, strsMessageBuilder);

    } else {
      // Referral ID passed - validate that Referral exist in CWS/CMS - no update for now
      referralId = screeningToReferral.getReferralId();
      gov.ca.cwds.rest.api.domain.cms.Referral foundReferral = this.find(referralId);
      if (foundReferral == null) {
        String message = "Legacy Id does not correspond to an existing CMS/CWS Referral";
        ServiceException se = new ServiceException(message);
        strsMessageBuilder.addMessageAndLog(message, se, LOGGER);
      }
    }
    return referralId;
  }

  /**
   * @param screeningToReferral - screeningToReferral
   * @param dateStarted - dateStarted
   * @param timeStarted - timeStarted
   * @param strsMessageBuilder - the ScreeningToReferralService messageBuilder object responsible
   *        for handling errors
   * @return the referral
   * @throws ServiceException - ServiceException
   */
  public gov.ca.cwds.rest.api.domain.cms.Referral createReferralWithDefaults(
      ScreeningToReferral screeningToReferral, String dateStarted, String timeStarted,
      MessageBuilder strsMessageBuilder) {
    String screenerAlertLongTextId = generateScreenerAlert(screeningToReferral, strsMessageBuilder);
    String responseRationalLongTextId =
        generateResponseRationalText(screeningToReferral, strsMessageBuilder);
    String currentLocationOfChildrenLongTextId =
        generateCurrentLocationOfChildren(screeningToReferral, strsMessageBuilder);

    /*
     * create a three dummy records using generateDrmsDocumentId method
     */
    String drmsAllegationDescriptionDoc =
        drmsDocumentService.generateDrmsDocumentId(strsMessageBuilder);
    String drmsErReferralDoc = drmsDocumentService.generateDrmsDocumentId(strsMessageBuilder);
    String drmsInvestigationDoc = drmsDocumentService.generateDrmsDocumentId(strsMessageBuilder);

    /*
     * create the referralAddress and assign the value to the
     * allegesAbuseOccurredAtAddressId(FKADDRS_T)
     */
    createReferralAddress(screeningToReferral, strsMessageBuilder);
    String allegesAbuseOccurredAtAddressId = screeningToReferral.getAddress().getLegacyId();

    String limitedAccessDate = DomainChef.cookDate(screeningToReferral.getLimitedAccessDate());

    int govEnt = convertLogicalIdToSystemCodeFor(screeningToReferral.getIncidentCounty(),
        LegacyTable.GOVERNMENT_ORGANIZATION_ENTITY.getName());
    Short agencyCode = convertLimitedAccessAgencyToNumericCode(screeningToReferral);

    boolean referredToResourceType =
        new R00818SetReferredResourceType(screeningToReferral).isValid();

    return gov.ca.cwds.rest.api.domain.cms.Referral.createWithDefaults(
        ParticipantValidator.anonymousReporter(screeningToReferral),
        screeningToReferral.getCommunicationMethod(), currentLocationOfChildrenLongTextId,
        drmsAllegationDescriptionDoc, drmsErReferralDoc, drmsInvestigationDoc,
        screeningToReferral.isFamilyAwareness(), govEnt, screeningToReferral.getName(), dateStarted,
        timeStarted, screeningToReferral.getResponseTime(),
        referredToResourceType ? NOT_REFERRED : 0, allegesAbuseOccurredAtAddressId,
        firstResponseDeterminedByStaffPersonId(), screenerAlertLongTextId,
        screeningToReferral.getIncidentCounty(), (short) screeningToReferral.getApprovalStatus(),
        screeningToReferral.getAssigneeStaffId(), responseRationalLongTextId,
        screeningToReferral.getResponsibleAgency(), screeningToReferral.getLimitedAccessCode(),
        screeningToReferral.getLimitedAccessDescription(), limitedAccessDate, agencyCode);
  }

  private Short convertLimitedAccessAgencyToNumericCode(ScreeningToReferral screeningToReferral) {
    String agencyValue = screeningToReferral.getLimitedAccessAgency();
    return StringUtils.isNumeric(agencyValue) ? Short.parseShort(agencyValue) : 0;
  }

  private int convertLogicalIdToSystemCodeFor(String logicalCode, String governmentEntityCode) {
    int foundCode = 0;
    Set<SystemCode> systemCodes =
        SystemCodeCache.global().getSystemCodesForMeta(governmentEntityCode);
    for (SystemCode systemCode : systemCodes) {
      if (systemCode.getLogicalId().equals(logicalCode)) {
        foundCode = systemCode.getSystemId();
        break;
      }
    }
    return foundCode;
  }

  private void createReferralAddress(ScreeningToReferral screeningToReferral,
      MessageBuilder messageBuilder) {
    try {
      gov.ca.cwds.rest.api.domain.Address referralAddress =
          addressService.createAddressFromScreening(screeningToReferral, messageBuilder);
      screeningToReferral.setAddress(referralAddress);
    } catch (ServiceException e1) {
      String message = e1.getMessage();
      messageBuilder.addMessageAndLog(message, e1, LOGGER);
    }
  }

  /**
   * <blockquote>
   *
   * <pre>
   * BUSINESS RULE: "R - 04537" - FKSTFPERS0 set when first referral determined
   *
   * IF    referralResponseTypeCode is set to default
   * THEN  firstResponseDeterminedByStaffPersonId is set to the staffpersonId
   *
   * </pre>
   *
   * </blockquote>
   */
  private static String firstResponseDeterminedByStaffPersonId() {
    return RequestExecutionContext.instance().getStaffId();
  }

  private String generateScreenerAlert(ScreeningToReferral screening, MessageBuilder mb) {
    String longTextId = null;
    if (screening.getAlertInformation() != null && !screening.getAlertInformation().isEmpty()) {
      try {
        longTextId =
            createLongText(screening.getIncidentCounty(), screening.getAlertInformation(), mb);
      } catch (ServiceException e) {
        mb.addMessageAndLog(e.getMessage(), e, LOGGER);
      }
    }
    return longTextId;
  }

  private String generateResponseRationalText(ScreeningToReferral screeningToReferral,
      MessageBuilder messageBuilder) {
    String longTextId = null;
    if (screeningToReferral.getAdditionalInformation() != null
        && !screeningToReferral.getAdditionalInformation().isEmpty()) {
      try {
        longTextId = createLongText(screeningToReferral.getIncidentCounty(),
            screeningToReferral.getAdditionalInformation(), messageBuilder);
      } catch (ServiceException e) {
        String message = e.getMessage();
        messageBuilder.addMessageAndLog(message, e, LOGGER);
      }
    }
    return longTextId;
  }

  private String generateCurrentLocationOfChildren(ScreeningToReferral screeningToReferral,
      MessageBuilder messageBuilder) {
    String currentLocationOfChildren = null;
    if (screeningToReferral.getCurrentLocationOfChildren() != null
        && !screeningToReferral.getCurrentLocationOfChildren().isEmpty()) {
      try {
        currentLocationOfChildren = createLongText(screeningToReferral.getIncidentCounty(),
            screeningToReferral.getCurrentLocationOfChildren(), messageBuilder);
      } catch (ServiceException e) {
        String message = e.getMessage();
        messageBuilder.addMessageAndLog(message, e, LOGGER);
      }
    }
    return currentLocationOfChildren;
  }

  private String createLongText(String countySpecificCode, String textDescription,
      MessageBuilder messageBuilder) {
    LongText longText = new LongText(countySpecificCode, textDescription);
    PostedLongText postedLongText = longTextService.create(longText);

    messageBuilder.addDomainValidationError(validator.validate(longText));

    return postedLongText.getId();
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Referral update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.Referral request) {
    try {
      /*
       * Reuse the validation of DocTool Rule 04611 in the
       * gov.ca.cwds.rest.services.ScreeningToReferralService
       */
      validateMaxReferralStartDateTime(primaryKey, request);

      Referral managed =
          new Referral(primaryKey, request, RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      managed = referralDao.update(managed);
      // checking the staffPerson county code
      StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
      createLACountyTrigger(staffperson, managed);
      return new gov.ca.cwds.rest.api.domain.cms.Referral(managed);
    } catch (EntityNotFoundException e) {
      final String msg = "Referral not found : " + request;
      LOGGER.error("Referral not found : {}", request);
      throw new ServiceException(msg, e);
    }
  }

  private void validateMaxReferralStartDateTime(String referralId,
      gov.ca.cwds.rest.api.domain.cms.Referral request) {
    Assignment firstAssignment = assignmentService.findReferralFirstAssignment(referralId);
    if (isReferralStartDateTimeValid(request, firstAssignment)) {
      new R04611ReferralStartDateTimeAction(assignmentService, request, firstAssignment).execute();
    } else {
      throw new ServiceException(
          "Rule : R - 04611 - Referral Start Date & Time can not exceed the End Date AND can not equal or exceed End Time of the first assignment.");
    }
  }

  boolean isReferralStartDateTimeValid(gov.ca.cwds.rest.api.domain.cms.Referral request,
      Assignment firstAssignment) {
    return new R04611ReferralStartDateTimeValidator(request, firstAssignment).isValid();
  }

  private String createDefaultSreenerNarrativeForNewReferral(
      ScreeningToReferral screeningToReferral, gov.ca.cwds.rest.api.domain.cms.Referral referral,
      String referralId) {
    String screenerNarrativeId = null;
    DrmsDocumentTemplate drmsTemplate =
        drmsDocumentTemplateService.findScreenerNarrativeTemplateNs(referral.getGovtEntityType());

    if (drmsTemplate != null) {
      CmsDocument cmsTemplate = cmsDocumentService.find(drmsTemplate.getCmsDocumentId());

      // Make Word doc from Template with new DOC_HANDLE etc.
      final Date now = new Date();
      final String docAuth = RequestExecutionContext.instance().getUserId();

      final SecureRandom random = new SecureRandom();
      String docHandle = DocUtils.generateDocHandle(now, docAuth);
      Short segments = 1;
      Long docLength = 1L;

      // TO1DO ??? The server name through which the document was added.
      String docServ = "AUTOCRTD";

      String docDate = new SimpleDateFormat(DomainObject.DATE_FORMAT).format(now);
      String docTime = new SimpleDateFormat(DomainObject.TIME_FORMAT).format(now);

      // TO1DO Not sure about numbering algorithm. Will use random from "000" to "999" for now.
      String nameNumber = StringUtils.leftPad(String.valueOf(random.nextInt(999)), 3, "0");
      String docName = drmsTemplate.getDocumentDOSFilePrefixName().substring(0, 5)
          .concat(nameNumber).concat(".DOC");

      String base64Blob = DatatypeConverter
          .printBase64Binary(screenerNarrativeFromTemplate(screeningToReferral, referralId,
              referral, DatatypeConverter.parseBase64Binary(cmsTemplate.getBase64Blob())));

      CmsDocument cmsDocument = new CmsDocument(docHandle, segments, docLength, docAuth, docServ,
          docDate, docTime, docName, cmsTemplate.getCompressionMethod(), base64Blob);
      cmsDocument = cmsDocumentService.create(cmsDocument);

      DrmsDocument document = new DrmsDocument(new Date(), drmsTemplate.getThirdId(), null,
          RequestExecutionContext.instance().getStaffId(), cmsDocument.getId());

      PostedDrmsDocument posted = drmsDocumentService.create(document);
      screenerNarrativeId = posted.getId();
    }

    return screenerNarrativeId;
  }

  private byte[] screenerNarrativeFromTemplate(ScreeningToReferral screeningToReferral,
      String referralId, gov.ca.cwds.rest.api.domain.cms.Referral referral, byte[] template) {
    Map<String, String> keyValuePairs = new HashMap<>();

    String childName = "";
    String childNumber = "";

    // Get child name from ...victims, allegations, participants???
    if (screeningToReferral.getParticipants() != null) {
      for (Participant participant : screeningToReferral.getParticipants()) {
        if (participant.getRoles().contains("Victim")) {
          childName = childName.concat(", ").concat(participant.getFirstName()).concat(" ")
              .concat(participant.getLastName());
        }
      }
    }

    keyValuePairs.put("ChildName", childName.substring(min(childName.length(), 1)).trim());
    keyValuePairs.put("ChildNumber", childNumber.substring(min(childNumber.length(), 1)).trim());

    keyValuePairs.put("ReferralNumber", CmsKeyIdGenerator.getUIIdentifierFromKey(referralId));
    keyValuePairs.put("ReferralDate", referral.getReceivedDate());

    keyValuePairs.put("bkBody", screeningToReferral.getReportNarrative());

    return DocUtils.createFromTemplateUseBookmarks(template, keyValuePairs);
  }

  public RIReferral getRiReferral() {
    return riReferral;
  }

  public NonLACountyTriggers getNonLaTriggers() {
    return nonLaTriggers;
  }

}
