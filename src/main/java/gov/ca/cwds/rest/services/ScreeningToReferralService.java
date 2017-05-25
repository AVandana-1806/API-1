package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.validation.ParticipantValidator;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */
public class ScreeningToReferralService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningToReferral.class);

  private static final String CLIENT_TABLE_NAME = "CLIENT_T";
  private static final String REFERRAL_TABLE_NAME = "REFERL_T";
  private static final String ALLEGATION_TABLE_NAME = "ALLGTN_T";
  private static final String CROSS_REPORT_TABLE_NAME = "CRSS_RPT";
  private static final String CLIENT_ADDRESS_TABLE_NAME = "ADDRS_T";
  private static final String REPORTER_TABLE_NAME = "REPTR_T";

  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  final DateFormat timeFormat = new SimpleDateFormat("HH:MM:SS");
  final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

  private Validator validator;

  private ReferralService referralService;
  private ClientService clientService;
  private AllegationService allegationService;
  private CrossReportService crossReportService;
  private ReferralClientService referralClientService;
  private ReporterService reporterService;
  private Reporter savedReporter;
  private AddressService addressService;
  private ClientAddressService clientAddressService;
  private LongTextService longTextService;
  private ChildClientService childClientService;

  private ReferralDao referralDao;

  private Short zipSuffix;

  // default values
  private static final short DEFAULT_CODE = 0;
  private static final BigDecimal DEFAULT_DECIMAL = new BigDecimal(0);
  private static final int DEFAULT_INT = 0;
  private static final String DEFAULT_RESPONSIBLE_AGENCY_CODE = "C";
  private static final short DEFAULT_STATE_CODE = 1828; // california
  private static final String DEFAULT_LIMITIED_ACCESS_CODE = "N";
  private static final short DEFAULT_APPROVAL_STATUS_CODE = 118;
  private static final String DEFAULT_SENSITIVITY_INDICATOR = "N";
  private static final short DEFAULT_SECONDARY_LANGUAGE_TYPE = 1253; // english
  private static final String DEFAULT_SOC158_PLACEMENT_CODE = "N";
  private static final String DEFAULT_SOCIAL_SECURITY_NUM_CHANGE_CODE = "N";
  private static final short DEFAULT_NAME_TYPE = 1313;
  private static final String DEFAULT_LITERATE_CODE = "U";
  private static final String DEFAULT_ESTIMATED_DOB_CODE = "N";
  private static final Boolean DEFAULT_CHILD_CLIENT_INDICATOR = false;
  private static final String DEFAULT_ADOPTION_STATUS_CODE = "N";
  private static final String DEFAULT_NON_PROTECTING_PARENT_CODE = "U";
  private static final short DEFAULT_ADDRESS_TYPE = 32; // residence
  private static final String DEFAULT_COUNTY_SPECIFIC_CODE = "62";
  private static final short DEFAULT_GOVERNMENT_ENTITY_TYPE = 1126;
  private static final String DEFAULT_NO = "N";
  private static final String DEFAULT_NA = "NA";
  private static final String DEFAULT_STAFF_PERSON_ID = "0X5";
  private static final String DEFAULT_SCREENER_NOTE_TEXT = "";
  private static final String DEFAULT_UNEMPLOYED_PARENT_CODE = "U";
  private static final String DEFAULT_INCAPCITATED_PARENT_CODE = "U";
  private static final String DEFAULT_HISPANIC_ORIGIN_CODE = "X";
  private static final String DEFAULT_MILITARY_STATUS_CODE = "N";
  private static final String DEFAULT_UNABLE_TO_DETAIN_CODE = "K";
  private static final String DEFAULT_DISPOSITION_CODE = "A";
  private static final String DEFAULT_THIRD_ID = "1234ABC123";

  // TODO: #142337489 Develop List of Value service to support Pi2 Save Referral to CWS/CMS
  private short communicationsMethodCode = 409; // default to telephone until #142337489 complete
  private short referralResponseTypeCode = 0;
  private short crossReportMethodCode = 0;
  private short allegationTypeCode = 0;



  /**
   * Constructor
   * 
   * @param referralService the referralService
   * @param clientService the clientServiec
   * @param allegationService the allegationService
   * @param crossReportService the crossReportService
   * @param referralClientService the referralClientService
   * @param reporterService the reporterService
   * @param addressService - cms address service
   * @param clientAddressService - cms ClientAddress service
   * @param longTextService - cms LongText service
   * @param childClientService - cms ChildClient service
   * @param validator - the validator
   * @param referralDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects.
   */
  @Inject
  public ScreeningToReferralService(ReferralService referralService, ClientService clientService,
      AllegationService allegationService, CrossReportService crossReportService,
      ReferralClientService referralClientService, ReporterService reporterService,
      AddressService addressService, ClientAddressService clientAddressService,
      LongTextService longTextService, ChildClientService childClientService, Validator validator,
      ReferralDao referralDao) {

    super();
    this.referralService = referralService;
    this.clientService = clientService;
    this.allegationService = allegationService;
    this.crossReportService = crossReportService;
    this.referralClientService = referralClientService;
    this.reporterService = reporterService;
    this.addressService = addressService;
    this.clientAddressService = clientAddressService;
    this.longTextService = longTextService;
    this.childClientService = childClientService;
    this.validator = validator;
    this.referralDao = referralDao;
  }

  @UnitOfWork(value = "cms")
  @Override
  public Response create(Request request) {
    ScreeningToReferral screeningToReferral = (ScreeningToReferral) request;

    Set<ErrorMessage> messages = new HashSet<ErrorMessage>();

    verifyReferralHasValidParticipants(screeningToReferral, messages);

    String dateStarted = extractStartDate(screeningToReferral, messages);
    String timeStarted = extractStartTime(screeningToReferral, messages);
    String referralId = createCmsReferral(screeningToReferral, messages, dateStarted, timeStarted);
    createReferralAddress(screeningToReferral, messages);

    Set<Participant> resultParticipants = new HashSet<>();
    HashMap<Long, String> victimClient = new HashMap<>();
    HashMap<Long, String> perpatratorClient = new HashMap<>();

    processParticipants(screeningToReferral, messages, dateStarted, referralId, resultParticipants,
        victimClient, perpatratorClient);

    Set<CrossReport> resultCrossReports =
        createCrossReports(screeningToReferral, messages, referralId);

    Set<Allegation> resultAllegations = createAllegations(screeningToReferral, messages, referralId,
        victimClient, perpatratorClient);

    return new PostedScreeningToReferral(screeningToReferral.getId(), referralId,
        REFERRAL_TABLE_NAME, screeningToReferral.getEndedAt(),
        screeningToReferral.getIncidentCounty(), screeningToReferral.getIncidentDate(),
        screeningToReferral.getLocationType(), screeningToReferral.getCommunicationMethod(),
        screeningToReferral.getName(), screeningToReferral.getReportNarrative(),
        screeningToReferral.getReference(), screeningToReferral.getResponseTime(),
        screeningToReferral.getStartedAt(), screeningToReferral.getAssignee(),
        screeningToReferral.getAdditionalInformation(), screeningToReferral.getScreeningDecision(),
        screeningToReferral.getScreeningDecisionDetail(), screeningToReferral.getAddress(),
        resultParticipants, resultCrossReports, resultAllegations);
  }

  private Set<Allegation> createAllegations(ScreeningToReferral screeningToReferral,
      Set<ErrorMessage> messages, String referralId, HashMap<Long, String> victimClient,
      HashMap<Long, String> perpatratorClient) {
    Set<Allegation> resultAllegations = null;
    try {
      resultAllegations = processAllegations(screeningToReferral, referralId, perpatratorClient,
          victimClient, messages);
    } catch (ServiceException e) {
      String message = e.getMessage();
      logError(message, e, messages);
    }
    return resultAllegations;
  }

  private Set<CrossReport> createCrossReports(ScreeningToReferral screeningToReferral,
      Set<ErrorMessage> messages, String referralId) {
    Set<CrossReport> resultCrossReports = null;
    try {
      resultCrossReports = processCrossReports(screeningToReferral, referralId, messages);
    } catch (ServiceException e) {
      String message = e.getMessage();
      logError(message, e, messages);
    }
    return resultCrossReports;
  }

  private void processParticipants(ScreeningToReferral screeningToReferral,
      Set<ErrorMessage> messages, String dateStarted, String referralId,
      Set<Participant> resultParticipants, HashMap<Long, String> victimClient,
      HashMap<Long, String> perpatratorClient) {
    Set<Participant> participants = screeningToReferral.getParticipants();
    for (Participant incomingParticipant : participants) {

      try {
        if (!ParticipantValidator.hasValidRoles(incomingParticipant)) {
          String message = " Participant contains incompatiable roles ";
          ServiceException exception = new ServiceException(message);
          logError(message, exception, messages);
        }
      } catch (Exception e1) {
        String message = e1.getMessage();
        logError(message, e1, messages);
      }

      String genderCode = "";
      if (!incomingParticipant.getGender().isEmpty()) {
        genderCode = incomingParticipant.getGender().toUpperCase().substring(0, 1);
      }
      Set<String> roles = new HashSet<>(incomingParticipant.getRoles());
      /**
       * process the roles of this participant
       */
      for (String role : roles) {

        try {
          if (ParticipantValidator.roleIsReporterType(role)
              && (!ParticipantValidator.roleIsAnonymousReporter(role)
                  && !ParticipantValidator.selfReported(incomingParticipant))) {
            /*
             * CMS Reporter - if role is 'mandated reporter' or 'non-mandated reporter' and not
             * anonymous reporter or self-reported
             */
            try {
              savedReporter = processReporter(incomingParticipant, role, referralId, messages);
              incomingParticipant.setLegacyId(savedReporter.getReferralId());
              incomingParticipant.setLegacySourceTable(REPORTER_TABLE_NAME);
            } catch (ServiceException e) {
              String message = e.getMessage();
              logError(message, e, messages);
            }
          } else {
            // not a reporter participant - make a CLIENT and REFERRAL_CLIENT unless anonymous
            // reporter
            if (!ParticipantValidator.roleIsAnonymousReporter(role)) {
              String clientId;

              if (incomingParticipant.getLegacyId().isEmpty()
                  || incomingParticipant.getLegacyId() == null) {
                // legacy Id not set - create a CLIENT row
                PostedClient postedClient;
                // not an anonymous reporter participant - create client
                Client client = new Client("", false, DEFAULT_ADOPTION_STATUS_CODE, "", "",
                    DEFAULT_CODE, incomingParticipant.getDateOfBirth(), "", DEFAULT_CODE, false,
                    DEFAULT_CHILD_CLIENT_INDICATOR, "", "", incomingParticipant.getFirstName(),
                    incomingParticipant.getLastName(), "", "", false, dateStarted, false, "", false,
                    "", false, "", "", "", DEFAULT_CODE, "", DEFAULT_ESTIMATED_DOB_CODE,
                    DEFAULT_UNABLE_TO_DETAIN_CODE, "", genderCode, "", "",
                    DEFAULT_HISPANIC_ORIGIN_CODE, DEFAULT_CODE, DEFAULT_CODE,
                    DEFAULT_INCAPCITATED_PARENT_CODE, false, false, DEFAULT_LITERATE_CODE, false,
                    DEFAULT_CODE, DEFAULT_MILITARY_STATUS_CODE, "", "", DEFAULT_NAME_TYPE, false,
                    false, "", false, DEFAULT_CODE, DEFAULT_CODE, DEFAULT_CODE,
                    DEFAULT_SECONDARY_LANGUAGE_TYPE, false, DEFAULT_SENSITIVITY_INDICATOR,
                    DEFAULT_SOC158_PLACEMENT_CODE, false, DEFAULT_SOCIAL_SECURITY_NUM_CHANGE_CODE,
                    incomingParticipant.getSsn(), "", false, false, DEFAULT_UNEMPLOYED_PARENT_CODE,
                    false, null);

                buildErrors(messages, validator.validate(client));

                postedClient = this.clientService.create(client);
                clientId = postedClient.getId();
                incomingParticipant.setLegacyId(clientId);
                incomingParticipant.setLegacySourceTable(CLIENT_TABLE_NAME);
              } else {
                // legacy Id passed - check for existenct in CWS/CMS - no update yet
                clientId = incomingParticipant.getLegacyId();
                Client foundClient = this.clientService.find(clientId);
                if (foundClient == null) {
                  String message =
                      " Legacy Id of Participant does not correspond to an existing CWS/CMS Client ";
                  ServiceException se = new ServiceException(message);
                  logError(message, se, messages);
                }
              }

              // CMS Referral Client
              // TODO: map the DISPOSITION_CODE accroding to rules of CWS/CMS
              ReferralClient referralClient = new ReferralClient("", DEFAULT_APPROVAL_STATUS_CODE,
                  DEFAULT_CODE, DEFAULT_DISPOSITION_CODE, "",
                  ParticipantValidator.selfReported(incomingParticipant), false, referralId,
                  clientId, "", DEFAULT_CODE, "", DEFAULT_COUNTY_SPECIFIC_CODE, false, false,
                  false);

              // validate referral client
              buildErrors(messages, validator.validate(referralClient));

              gov.ca.cwds.rest.api.domain.cms.ReferralClient postedReferralClient =
                  this.referralClientService.create(referralClient);
              /*
               * determine other participant/roles attributes relating to CWS/CMS allegation
               */
              if (ParticipantValidator.roleIsVictim(role)) {
                victimClient.put(incomingParticipant.getId(), clientId);
                // since this is the victim - process the ChildClient
                try {
                  this.processChildClient(incomingParticipant, clientId, messages);
                } catch (ServiceException e) {
                  String message = e.getMessage();
                  logError(message, e, messages);
                }
              }
              if (ParticipantValidator.roleIsPerpetrator(role)) {
                perpatratorClient.put(incomingParticipant.getId(), clientId);
              }
              try {
                // addresses associated with a client
                Participant resultParticipant =
                    processClientAddress(incomingParticipant, referralId, clientId, messages);
              } catch (ServiceException e) {
                String message = e.getMessage();
                logError(message, e, messages);
              }
            }
          }
        } catch (Exception e) {
          String message = e.getMessage();
          logError(message, e, messages);
        }
        resultParticipants.add(incomingParticipant);
      }
    }
  }

  private String extractStartTime(ScreeningToReferral screeningToReferral,
      Set<ErrorMessage> messages) {
    String timeStarted = null;
    try {
      Date dateTime = dateTimeFormat.parse(screeningToReferral.getStartedAt());
      timeStarted = timeFormat.format(dateTime);
    } catch (ParseException e) {
      String message = " parsing Start Date/Time ";
      logError(message, e, messages);
    }
    return timeStarted;
  }

  private String extractStartDate(ScreeningToReferral screeningToReferral,
      Set<ErrorMessage> messages) {
    String dateStarted = null;
    try {
      Date dateTime = dateTimeFormat.parse(screeningToReferral.getStartedAt());
      dateStarted = dateFormat.format(dateTime);
    } catch (ParseException e) {
      String message = " parsing Start Date/Time ";
      logError(message, e, messages);
    }
    return dateStarted;
  }

  private void createReferralAddress(ScreeningToReferral screeningToReferral,
      Set<ErrorMessage> messages) {
    try {
      gov.ca.cwds.rest.api.domain.Address referralAddress =
          processReferralAddress(screeningToReferral, messages);
      screeningToReferral.setAddress(referralAddress);
    } catch (ServiceException e1) {
      String message = e1.getMessage();
      logError(message, e1, messages);
    }
  }

  private String createCmsReferral(ScreeningToReferral screeningToReferral,
      Set<ErrorMessage> messages, String dateStarted, String timeStarted) {
    String referralId = null;

    if (screeningToReferral.getReferralId() == null
        || screeningToReferral.getReferralId().isEmpty() ) {
      // the legacy id is not set - create the referral
      String longTextId = generateLongTextId(screeningToReferral, messages);
      // create a CMS Referral
      Referral referral = null;
      try {
        referral = new Referral(false, ParticipantValidator.anonymousReporter(screeningToReferral),
            false, "", DEFAULT_APPROVAL_STATUS_CODE, false, "", communicationsMethodCode, "", "",
            "", "", false, false, DEFAULT_CODE, DEFAULT_NO, false, DEFAULT_LIMITIED_ACCESS_CODE, "",
            screeningToReferral.getName(), "", dateStarted, timeStarted, referralResponseTypeCode,
            DEFAULT_CODE, "", "", "", longTextId, DEFAULT_NO, DEFAULT_NO, DEFAULT_NO, "", "", "",
            DEFAULT_STAFF_PERSON_ID, DEFAULT_COUNTY_SPECIFIC_CODE, false, false, false, false, "",
            DEFAULT_RESPONSIBLE_AGENCY_CODE, DEFAULT_CODE, "", "", "", null, null, null, null, null,
            null);
      } catch (Exception e1) {
        String message = e1.getMessage();
        logError(message, e1, messages);
      }

      buildErrors(messages, validator.validate(referral));

      PostedReferral postedReferral = this.referralService.create(referral);
      referralId = postedReferral.getId();

    } else {
      // Referral ID passed - validate that Referral exist in CWS/CMS - no update for now
      referralId = screeningToReferral.getReferralId();
      Referral foundReferral = this.referralService.find(referralId);
      if (foundReferral == null) {
        String message = " Legacy Id does not correspond to an existing CMS/CWS Referral ";
        ServiceException se = new ServiceException(message);
        logError(message, se, messages);
      }
    }
    return referralId;
  }

  private String generateLongTextId(ScreeningToReferral screeningToReferral,
      Set<ErrorMessage> messages) {
    String longTextId = null;
    if (screeningToReferral.getAdditionalInformation() == null
        || screeningToReferral.getAdditionalInformation().isEmpty()) {
      longTextId = null;
    } else {
      try {
        longTextId = createLongText(DEFAULT_COUNTY_SPECIFIC_CODE,
            screeningToReferral.getAdditionalInformation(), messages);
      } catch (ServiceException e) {
        String message = e.getMessage();
        logError(message, e, messages);
      }
    }
    return longTextId;
  }

  private void verifyReferralHasValidParticipants(ScreeningToReferral screeningToReferral,
      Set<ErrorMessage> messages) {
    try {
      if (!ParticipantValidator.hasValidParticipants(screeningToReferral)) {
        String message = " Incompatiable participants included in request";
        ServiceException exception = new ServiceException(message);
        logError(message, exception, messages);
      }
    } catch (Exception e) {
      String message = e.getMessage();
      logError(message, e, messages);
    }
  }

  private void logError(String message, Exception exception, Set<ErrorMessage> messages) {
    messages.add(new ErrorMessage(ErrorMessage.ErrorType.VALIDATION, message, ""));
    LOGGER.error(message, exception.getMessage());
    throw new ServiceException(message);

  }

  private void buildErrors(Set<ErrorMessage> messages,
      Set<ConstraintViolation<DomainObject>> errors) {
    if (!errors.isEmpty()) {
      errors.forEach(error -> {
        final String message = " getRootBean: " + error.getRootBean() + " getLeafBean: "
            + error.getLeafBean() + " getConstraintDescriptor: " + error.getConstraintDescriptor()
            + " ERROR PROP PATH: " + error.getPropertyPath() + " " + error.getMessage();
        messages.add(new ErrorMessage(ErrorMessage.ErrorType.VALIDATION, message, ""));
      });
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    throw new NotImplementedException("Delete is not implemented");
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.Referral find(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.Referral persistedReferral = referralDao.find(primaryKey);
    if (persistedReferral != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Referral(persistedReferral);
    }
    return null;
  }

  @Override
  public Response update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    throw new NotImplementedException("Update is not implemented");
  }

  /*
   * CMS Cross Report
   */
  private Set<gov.ca.cwds.rest.api.domain.CrossReport> processCrossReports(ScreeningToReferral scr,
      String referralId, Set<ErrorMessage> messages) throws ServiceException {

    String crossReportId = "";
    Set<gov.ca.cwds.rest.api.domain.CrossReport> resultCrossReports = new HashSet<>();
    Set<CrossReport> crossReports;
    crossReports = scr.getCrossReports();

    if (crossReports != null) {

      for (CrossReport crossReport : crossReports) {

        Boolean lawEnforcementIndicator = false;
        if (crossReport.getAgencyType().contains("Law Enforcement")) {
          lawEnforcementIndicator = true;
        }
        if (crossReport.getLegacyId().isEmpty() || crossReport.getLegacyId() == null) {

          // create the cross report
          gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
              new gov.ca.cwds.rest.api.domain.cms.CrossReport(crossReportId, crossReportMethodCode,
                  false, false, "", "", DEFAULT_INT, DEFAULT_DECIMAL, crossReport.getInformDate(),
                  "", "", referralId, "", DEFAULT_STAFF_PERSON_ID, crossReport.getAgencyName(), "",
                  "", DEFAULT_COUNTY_SPECIFIC_CODE, lawEnforcementIndicator, false, false);

          buildErrors(messages, validator.validate(cmsCrossReport));

          gov.ca.cwds.rest.api.domain.cms.CrossReport postedCrossReport =
              this.crossReportService.create(cmsCrossReport);

          crossReport.setLegacyId(postedCrossReport.getThirdId());
          crossReport.setLegacySourceTable(CROSS_REPORT_TABLE_NAME);
          resultCrossReports.add(crossReport);
        } else {
          gov.ca.cwds.rest.api.domain.cms.CrossReport foundCrossReport =
              this.crossReportService.find(crossReport.getLegacyId());
          if (foundCrossReport == null) {
            String message =
                " Legacy Id on Cross Report does not correspond to an existing CMS/CWS Cross Report ";
            ServiceException se = new ServiceException(message);
            logError(message, se, messages);

          }
        }
      }
    }

    return resultCrossReports;
  }

  /*
   * CMS Allegation - one for each allegation
   */
  private Set<Allegation> processAllegations(ScreeningToReferral scr, String referralId,
      HashMap<Long, String> perpatratorClient, HashMap<Long, String> victimClient,
      Set<ErrorMessage> messages) throws ServiceException {

    Set<Allegation> processedAllegations = new HashSet<>();
    Set<Allegation> allegations;
    String victimClientId = "";
    String perpatratorClientId = "";

    allegations = scr.getAllegations();
    if (allegations == null || allegations.isEmpty()) {
      String message = " Referral must have at least one Allegation ";
      ServiceException exception = new ServiceException(message);
      logError(message, exception, messages);
    }
    for (Allegation allegation : allegations) {

      try {
        if (!ParticipantValidator.isVictimParticipant(scr, allegation.getVictimPersonId())) {
          throw new ServiceException(
              " Allegation/Victim Person Id does not contain a Participant with a role of Victim ");
        }
      } catch (Exception e) {
        throw new ServiceException(e);
      }
      if (victimClient.containsKey(allegation.getVictimPersonId())) {
        victimClientId = victimClient.get(allegation.getVictimPersonId());
      }

      if (allegation.getPerpetratorPersonId() != 0) {
        try {
          if (!ParticipantValidator.isPerpetratorParticipant(scr,
              allegation.getPerpetratorPersonId())) {
            throw new ServiceException(
                " Allegation/Perpetrator Person Id does not contain a Participant with a role of Perpetrator ");
          }
        } catch (Exception e) {
          throw new ServiceException(e);
        }
      }

      if (perpatratorClient.containsKey(allegation.getPerpetratorPersonId())) {
        perpatratorClientId = perpatratorClient.get(allegation.getPerpetratorPersonId());
      }
      if (victimClientId.isEmpty()) {
        throw new ServiceException(" Victim could not be determined for an allegation ");
      }

      if (allegation.getLegacyId().isEmpty() || allegation.getLegacyId() == null) {

        // create an allegation in CMS legacy database
        gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
            new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
                scr.getLocationType(), "", DEFAULT_CODE, allegationTypeCode,
                scr.getReportNarrative(), "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false,
                victimClientId, perpatratorClientId, referralId, DEFAULT_COUNTY_SPECIFIC_CODE,
                false, DEFAULT_CODE);

        buildErrors(messages, validator.validate(cmsAllegation));

        PostedAllegation postedAllegation = this.allegationService.create(cmsAllegation);
        allegation.setLegacyId(postedAllegation.getId());
        allegation.setLegacySourceTable(ALLEGATION_TABLE_NAME);
        processedAllegations.add(allegation);
      } else {
        gov.ca.cwds.rest.api.domain.cms.Allegation foundAllegation =
            this.allegationService.find(allegation.getLegacyId());
        if (foundAllegation == null) {
          String message =
              " Legacy Id on Allegation does not correspond to an existing CMS/CWS Allegation ";
          ServiceException se = new ServiceException(message);
          logError(message, se, messages);
        }
      }
    }
    return processedAllegations;
  }

  /*
   * CMS Address - create ADDRESS and CLIENT_ADDRESS for each address of the participant
   */
  private Participant processClientAddress(Participant clientParticipant, String referralId,
      String clientId, Set<ErrorMessage> messages) throws ServiceException {

    String addressId = new String("");
    Set<gov.ca.cwds.rest.api.domain.Address> addresses;
    addresses = clientParticipant.getAddresses();
    Set<gov.ca.cwds.rest.api.domain.Address> newAddresses = new HashSet();

    if (addresses == null) {
      return null;
    }

    for (gov.ca.cwds.rest.api.domain.Address address : addresses) {

      // TODO: 41511573 address parsing - Smarty Streets Free Form display requires standardizing
      // parsing to fields in CMS
      int zipCode = address.getZip();
      zipSuffix = 0;
      if (address.getZip().toString().length() > 5) {
        zipSuffix = Short.parseShort(address.getZip().toString().substring(5));
      }
      String[] streetAddress = address.getStreetAddress().split(" ");
      String streetNumber = streetAddress[0];
      String streetName = streetAddress[1];

      if (address.getLegacyId().isEmpty() || address.getLegacyId() == null) {
        // add the Address row
        Address domainAddress = new Address(" ", address.getCity(), DEFAULT_DECIMAL, DEFAULT_INT,
            false, DEFAULT_CODE, DEFAULT_DECIMAL, DEFAULT_INT, " ", DEFAULT_DECIMAL, DEFAULT_INT,
            DEFAULT_STATE_CODE, streetName, streetNumber, zipCode, address.getType(), zipSuffix,
            " ", " ", DEFAULT_CODE, DEFAULT_CODE, " ");

        buildErrors(messages, validator.validate(domainAddress));

        PostedAddress postedAddress = (PostedAddress) this.addressService.create(domainAddress);
        addressId = postedAddress.getExistingAddressId();
      } else {
        // verify that Address row exist - no update for now
        Address foundAddress = (Address) this.addressService.find(address.getLegacyId());
        if (foundAddress == null) {
          String message =
              " Legacy Id on Address does not correspond to an existing CMS/CWS Address ";
          ServiceException se = new ServiceException(message);
          logError(message, se, messages);
        }
        addressId = foundAddress.getExistingAddressId();
      }

      /*
       * CMS Client Address
       */
      if (addressId.isEmpty()) {
        String message = " ADDRESS/IDENTIFIER is required for CLIENT_ADDRESS table ";
        ServiceException exception = new ServiceException(message);
        throw exception;
      }
      if (clientId.isEmpty()) {
        String message = " CLIENT/IDENTIFIER is required for CLIENT_ADDRESS ";
        ServiceException exception = new ServiceException(message);
        throw exception;
      }

      if (address.getLegacyId().isEmpty() || address.getLegacyId() == null) {

        ClientAddress clientAddress = new ClientAddress(DEFAULT_ADDRESS_TYPE, "", "", "", addressId,
            clientId, "", referralId);
        buildErrors(messages, validator.validate(clientAddress));
        this.clientAddressService.create(clientAddress);

        buildErrors(messages, validator.validate(clientAddress));

        // update the addresses of the participant
        address.setLegacySourceTable(CLIENT_ADDRESS_TABLE_NAME);
        address.setLegacyId(addressId);
        newAddresses.add(address);
      } else {
        // verify that ClientAddress exists - no update for now
        ClientAddress foundClientAddress =
            (ClientAddress) this.clientAddressService.find(address.getLegacyId());
        if (foundClientAddress == null) {
          String message =
              " Legacy Id on Address does not correspond to an existing CMS/CWS Client Address ";
          ServiceException se = new ServiceException(message);
          logError(message, se, messages);
        }
      }
    }

    clientParticipant.setAddresses(newAddresses);

    return clientParticipant;
  }

  private gov.ca.cwds.rest.api.domain.Address processReferralAddress(ScreeningToReferral scr,
      Set<ErrorMessage> messages) throws ServiceException {
    gov.ca.cwds.rest.api.domain.Address address = scr.getAddress();
    if (address == null) {
      String message = " Screening address is null or empty";
      throw new ServiceException(message);
    }

    try {
      Integer zipCode = address.getZip();
      zipSuffix = 0;
      if (address.getZip().toString().length() > 5) {
        zipSuffix = Short.parseShort(address.getZip().toString().substring(5));
      }
      // TODO: 141511573 address parsing - Smarty Streets Free Form display requires standardizing
      // parsing to fields in CMS
      String[] streetAddress = address.getStreetAddress().split(" ");
      String streetNumber = streetAddress[0];
      String streetName = streetAddress[1];

      Address domainAddress = new Address(" ", address.getCity(), DEFAULT_DECIMAL, DEFAULT_INT,
          false, DEFAULT_CODE, DEFAULT_DECIMAL, DEFAULT_INT, "", DEFAULT_DECIMAL, DEFAULT_INT,
          DEFAULT_STATE_CODE, streetName, streetNumber, zipCode, address.getType(), zipSuffix, " ",
          " ", DEFAULT_CODE, DEFAULT_CODE, " ");

      buildErrors(messages, validator.validate(domainAddress));

      PostedAddress postedAddress = (PostedAddress) this.addressService.create(domainAddress);

      address.setLegacyId(postedAddress.getExistingAddressId());
      address.setLegacySourceTable("ADDRS_T");
    } catch (Exception e) {
      String message = " Referral Address is null or empty";
      LOGGER.error(e.getMessage(), e);
      throw new ServiceException(message);
    }

    return address;

  }

  private Reporter processReporter(Participant ip, String role, String referralId,
      Set<ErrorMessage> messages) throws ServiceException {

    String[] streetAddress;
    String streetNumber = "";
    String streetName = "";
    String zipCodeString = "";
    String city = "";

    if (ip.getAddresses() != null) {
      Set<gov.ca.cwds.rest.api.domain.Address> addresses = new HashSet<>(ip.getAddresses());

      // use the first address node only
      for (gov.ca.cwds.rest.api.domain.Address address : addresses) {

        // TODO: #141511573 address parsing - Smarty Streets Free Form display requires
        // standardizing
        // parsing to fields in CMS
        if (address == null) {
          // no address information for this reporter
          break;
        }
        zipCodeString = address.getZip().toString();
        zipSuffix = null;
        if (address.getZip().toString().length() > 5) {
          zipSuffix = Short.parseShort(address.getZip().toString().substring(5));
        }
        streetAddress = address.getStreetAddress().split(" ");
        streetNumber = streetAddress[0];
        streetName = streetAddress[1];
        city = address.getCity();

        break;
      }
    }

    Boolean mandatedReporterIndicator = ParticipantValidator.roleIsMandatedReporter(role);
    Reporter theReporter = null;

    if (ip.getLegacyId().isEmpty() || ip.getLegacyId() == null) {
      // create the Reporter in CWS/CMS
      Reporter reporter = new Reporter("", city, DEFAULT_CODE, DEFAULT_CODE, false, "", "", "",
          false, ip.getFirstName(), ip.getLastName(), mandatedReporterIndicator, 0, DEFAULT_DECIMAL,
          "", "", DEFAULT_DECIMAL, 0, DEFAULT_STATE_CODE, streetName, streetNumber, "",
          zipCodeString, referralId, "", DEFAULT_CODE, DEFAULT_COUNTY_SPECIFIC_CODE);

      buildErrors(messages, validator.validate(reporter));
      theReporter = reporterService.create(reporter);
    } else {
      // verify that Reporter exist in CWS/CMS - no update yet
      theReporter = reporterService.find(ip.getLegacyId());
      if (theReporter == null) {
        String message =
            " Legacy Id on participant does not correspond to an existing CMS/CWS Reporter ";
        ServiceException se = new ServiceException(message);
        logError(message, se, messages);

      }
    }
    return theReporter;
  }

  private String createLongText(String countySpecificCode, String textDescription,
      Set<ErrorMessage> messages) throws ServiceException {

    LongText longText = new LongText(countySpecificCode, textDescription);
    PostedLongText postedLongText = longTextService.create(longText);

    buildErrors(messages, validator.validate(longText));

    return postedLongText.getId();

  }

  private ChildClient processChildClient(Participant id, String clientId,
      Set<ErrorMessage> messages) throws ServiceException {

    ChildClient childClient = new ChildClient(clientId, "NA", DEFAULT_CODE, false, false, false, "",
        "", "", false, false, false, "", DEFAULT_CODE, "D", "U", "", "", false, "", false, "U",
        false, false, false, false, false, false, false, false, "X", false, false, DEFAULT_INT, "",
        "", false, false, "", false);

    buildErrors(messages, validator.validate(childClient));

    return this.childClientService.create(childClient);

  }

}

