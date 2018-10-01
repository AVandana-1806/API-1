package gov.ca.cwds.rest.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.cms.data.access.service.impl.CsecHistoryService;
import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.legacy.cms.dao.SexualExploitationTypeDao;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.SexualExploitationType;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.comparator.DateTimeComparator;
import gov.ca.cwds.rest.api.domain.comparator.DateTimeComparatorInterface;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage.ErrorType;
import gov.ca.cwds.rest.business.rules.CommercialSexualExploitationHistoryDroolsConfiguration;
import gov.ca.cwds.rest.business.rules.R00824SetDispositionCode;
import gov.ca.cwds.rest.business.rules.R00832SetStaffPersonAddedInd;
import gov.ca.cwds.rest.business.rules.R00834AgeUnitRestriction;
import gov.ca.cwds.rest.business.rules.R02265ChildClientExists;
import gov.ca.cwds.rest.business.rules.R04466ClientSensitivityIndicator;
import gov.ca.cwds.rest.business.rules.R04880EstimatedDOBCodeSetting;
import gov.ca.cwds.rest.business.rules.R10971CsecEndDate;
import gov.ca.cwds.rest.core.FerbConstants;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientScpEthnicityService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.SpecialProjectReferralService;
import gov.ca.cwds.rest.validation.ParticipantValidator;

/**
 * Business layer object to work on {@link Address}
 *
 * @author CWDS API Team
 */
public class ParticipantToLegacyClient {

  private static final String ASSESMENT = "A";

  private static final String VICTIM_WHILE_ABSENT_FROM_PLACEMENT = "6871";

  private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantToLegacyClient.class);

  private Validator validator;

  private ClientService clientService;
  private ReferralClientService referralClientService;
  private ReporterService reporterService;
  private ChildClientService childClientService;
  private ClientAddressService clientAddressService;
  private ClientScpEthnicityService clientScpEthnicityService;

  private CaseDao caseDao;
  private ReferralClientDao referralClientDao;

  @Inject
  private CsecHistoryService csecHistoryService;

  @Inject
  private SexualExploitationTypeDao sexualExploitationTypeDao;

  @Inject
  private SpecialProjectReferralService specialProjectReferralService;

  @Inject
  private DroolsService droolsService;

  /**
   * Constructor
   *
   * @param clientService clientService
   * @param referralClientService referralClientService
   * @param reporterService reporterService
   * @param childClientService childClientService
   * @param clientAddressService clientAddressService
   * @param validator validator
   * @param clientScpEthnicityService clientScpEthnicityService
   * @param caseDao caseDao
   * @param referralClientDao referralClientDao
   */
  @Inject
  public ParticipantToLegacyClient(ClientService clientService,
      ReferralClientService referralClientService, ReporterService reporterService,
      ChildClientService childClientService, ClientAddressService clientAddressService,
      Validator validator, ClientScpEthnicityService clientScpEthnicityService, CaseDao caseDao,
      ReferralClientDao referralClientDao) {
    this.validator = validator;
    this.clientService = clientService;
    this.referralClientService = referralClientService;
    this.reporterService = reporterService;
    this.childClientService = childClientService;
    this.clientAddressService = clientAddressService;
    this.clientScpEthnicityService = clientScpEthnicityService;
    this.caseDao = caseDao;
    this.referralClientDao = referralClientDao;
  }

  /**
   * @param screeningToReferral - screeningToReferral
   * @param dateStarted - dateStarted
   * @param timeStarted - timeStarted
   * @param referralId - referralId
   * @param messageBuilder - messageBuilder
   * @return the savedParticiants
   */
  public ClientParticipants saveParticipants(ScreeningToReferral screeningToReferral,
      String dateStarted, String timeStarted, String referralId, MessageBuilder messageBuilder) {
    ClientParticipants clientParticipants = new ClientParticipants();

    Set<Participant> participants = screeningToReferral.getParticipants();
    for (Participant incomingParticipant : participants) {
      if (!ParticipantValidator.hasValidRoles(incomingParticipant)) {
        final String message = " Participant contains incompatible roles ";
        messageBuilder.addMessageAndLog(message, LOGGER);
        // next participant
        continue;
      }
      String sexAtBirth = "";
      if (!incomingParticipant.getGender().isEmpty()) {
        sexAtBirth = incomingParticipant.getGender().toUpperCase().substring(0, 1);
      }
      Set<String> roles = new HashSet<>(incomingParticipant.getRoles());
      processClients(screeningToReferral, dateStarted, timeStarted, referralId, messageBuilder,
          clientParticipants, incomingParticipant, sexAtBirth, roles);
    } // next participant

    return clientParticipants;
  }

  private void processClients(ScreeningToReferral screeningToReferral, String dateStarted,
      String timeStarted, String referralId, MessageBuilder messageBuilder,
      ClientParticipants clientParticipants, Participant incomingParticipant, String sexAtBirth,
      Set<String> roles) {
    /**
     * process the roles of this participant
     */
    for (String role : roles) {
      if (isReporter(role, incomingParticipant)) {
        saveRegularReporter(screeningToReferral, referralId, messageBuilder, incomingParticipant,
            role);

      } else if (isClient(role)) {
        saveClient(screeningToReferral, dateStarted, timeStarted, referralId, messageBuilder,
            clientParticipants, incomingParticipant, sexAtBirth, role);
      }
      clientParticipants.addParticipant(incomingParticipant);
    } // next role
  }

  private boolean isReporter(String role, Participant incomingParticipant) {
    return ParticipantValidator.roleIsReporterType(role)
        && (!ParticipantValidator.roleIsAnonymousReporter(role)
            && !ParticipantValidator.selfReported(incomingParticipant));
  }

  private boolean isClient(String role) {
    return !ParticipantValidator.roleIsAnyReporter(role);
  }

  private void saveRegularReporter(ScreeningToReferral screeningToReferral, String referralId,
      MessageBuilder messageBuilder, Participant incomingParticipant, String role) {
    saveReporter(screeningToReferral, referralId, messageBuilder, incomingParticipant, role);
  }

  private void saveClient(ScreeningToReferral screeningToReferral, String dateStarted,
      String timeStarted, String referralId, MessageBuilder messageBuilder,
      ClientParticipants clientParticipants, Participant incomingParticipant, String sexAtBirth,
      String role) {
    String clientId;

    LegacyDescriptor clientLegacyDesc = incomingParticipant.getLegacyDescriptor();

    // true if descriptor null or descriptor Id is blank or descriptor is not for the client table
    boolean newClient = StringUtils.isBlank(clientLegacyDesc.getId())
        || !StringUtils.equals(clientLegacyDesc.getTableName(), LegacyTable.CLIENT.getName());

    clientId = incomingParticipant.getLegacyDescriptor().getId();
    try {
      if (newClient) {
        clientId = createNewClient(screeningToReferral, dateStarted, messageBuilder,
            incomingParticipant, sexAtBirth);
      } else if (!isErrorMessagesExist(messageBuilder)) {
        updateClientIfItExistsInLegacy(screeningToReferral, messageBuilder, incomingParticipant,
            clientId);
      }
      processReferralClient(screeningToReferral, referralId, messageBuilder, incomingParticipant,
          clientId, dateStarted);
    } catch (ServiceException e) {
      final String message = "Error saving client: " + e.getMessage();
      messageBuilder.addMessageAndLog(message, e, LOGGER, ErrorType.DATA_ACCESS);
      // next role
    }
    /*
     * determine other participant/roles attributes relating to CWS/CMS allegation
     */
    if (ParticipantValidator.roleIsVictim(role)) {
      clientParticipants.addVictimIds(incomingParticipant.getId(), clientId);
      // since this is the victim - process the ChildClient
      try {
        processChildClient(clientId, referralId, dateStarted, timeStarted, messageBuilder,
            incomingParticipant.getCsecs(), incomingParticipant.getSafelySurrenderedBabies(),
            screeningToReferral);
      } catch (ServiceException e) {
        String message = e.getMessage();
        messageBuilder.addMessageAndLog(message, e, LOGGER);
        // next role
      }
    }

    if (ParticipantValidator.roleIsPerpetrator(role)) {
      clientParticipants.addPerpetratorIds(incomingParticipant.getId(), clientId);
    }

    try {
      // addresses associated with a client
      processClientAddress(incomingParticipant, referralId, clientId, messageBuilder);
    } catch (ServiceException e) {
      messageBuilder.addMessageAndLog(e.getMessage(), e, LOGGER);
      // next role
    }
  }

  private boolean isErrorMessagesExist(MessageBuilder messageBuilder) {
    return (!messageBuilder.getMessages().isEmpty());
  }

  private boolean saveReporter(ScreeningToReferral screeningToReferral, String referralId,
      MessageBuilder messageBuilder, Participant incomingParticipant, String role) {
    /*
     * CMS Reporter - if role is 'mandated reporter' or 'non-mandated reporter' and not anonymous
     * reporter or self-reported
     */
    try {
      final Reporter savedReporter = saveReporter(incomingParticipant, role, referralId,
          screeningToReferral.getIncidentCounty(), messageBuilder);
      incomingParticipant.setLegacyDescriptor(new LegacyDescriptor(savedReporter.getReferralId(),
          null, savedReporter.getLastUpdatedTime(), LegacyTable.REPORTER.getName(),
          LegacyTable.REPORTER.getDescription()));
    } catch (ServiceException e) {
      messageBuilder.addMessageAndLog(e.getMessage(), e, LOGGER);
      return true; // next role
    }
    return false;
  }

  /**
   * <blockquote>
   * 
   * <pre>
   * BUSINESS RULE: R - 06195 Do Not Update Approval Status Type
   * 
   *  When creating the REFERRAL_CLIENT entity, set the Approval Status Type = 'Request Not Submitted'. 
   *  master:src/main/java/gov/ca/cwds/rest/services/ParticipantToLegacyClient.java
   * </blockquote>
   * </pre>
   */
  private ReferralClient processReferralClient(ScreeningToReferral screeningToReferral,
      String referralId, MessageBuilder messageBuilder, Participant incomingParticipant,
      String clientId, String dateStarted) {
    boolean dispositionCode =
        new R00824SetDispositionCode(screeningToReferral, incomingParticipant).isValid();
    boolean staffPersonAddedIndicator =
        new R00832SetStaffPersonAddedInd(screeningToReferral).isValid();

    ReferralClient referralClient = ReferralClient.createWithDefault(
        ParticipantValidator.selfReported(incomingParticipant), staffPersonAddedIndicator,
        dispositionCode ? ASSESMENT : "", referralId, clientId,
        screeningToReferral.getIncidentCounty(), LegacyDefaultValues.DEFAULT_APPROVAL_STATUS_CODE,
        StringUtils.isNotBlank(incomingParticipant.getApproximateAge())
            ? Short.parseShort(incomingParticipant.getApproximateAge())
            : 0,
        incomingParticipant.getApproximateAgeUnits());

    Client client = this.clientService.find(clientId);

    // set the Estimated DOB code and estimate DOB if needed of CLIENT before setting
    // the Referral Client age and age unit
    if (!isErrorMessagesExist(messageBuilder)) {
      R04880EstimatedDOBCodeSetting r04880EstimatedDOBCodeSetting =
          new R04880EstimatedDOBCodeSetting(client, referralClient, dateStarted);
      r04880EstimatedDOBCodeSetting.execute();
      this.clientService.update(clientId, client);
    }

    R00834AgeUnitRestriction r00834AgeUnitRestriction =
        new R00834AgeUnitRestriction(client, referralClient, dateStarted);
    r00834AgeUnitRestriction.execute();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));

    try {
      referralClientService.create(referralClient);
    } catch (ServiceException se) {
      messageBuilder.addMessageAndLog(se.getMessage(), se, LOGGER);
    }
    return referralClient;
  }

  private void updateClientIfItExistsInLegacy(ScreeningToReferral screeningToReferral,
      MessageBuilder messageBuilder, Participant incomingParticipant, String clientId) {
    final Client foundClient = this.clientService.find(clientId);
    if (foundClient != null) {
      try {
        updateClient(screeningToReferral, messageBuilder, incomingParticipant, foundClient);
      } catch (PersistenceException e) {
        throw new ServiceException(
            "Unable to Update Client in CMS from Incoming Participant, Development may need to check with Hibernate Debug for possible Data issue",
            e);
      }
    } else {
      String message =
          " Legacy Id of Participant does not correspond to an existing CWS/CMS Client ";
      messageBuilder.addMessageAndLog(message, LOGGER);
    }
  }

  private void updateClient(ScreeningToReferral screeningToReferral, MessageBuilder messageBuilder,
      Participant incomingParticipant, Client foundClient) {
    DateTimeComparatorInterface comparator = new DateTimeComparator();
    if (okToUpdateClient(incomingParticipant, foundClient, comparator)) {
      List<Short> allRaceCodes = getAllRaceCodes(incomingParticipant.getRaceAndEthnicity());
      Short primaryRaceCode = getPrimaryRaceCode(allRaceCodes);
      List<Short> otherRaceCodes = getOtherRaceCodes(allRaceCodes, primaryRaceCode);

      String unableToDetermineCode = incomingParticipant.getRaceAndEthnicity() != null
          ? incomingParticipant.getRaceAndEthnicity().getUnableToDetermineCode()
          : "";
      String hispanicUnableToDetermineCode = incomingParticipant.getRaceAndEthnicity() != null
          ? incomingParticipant.getRaceAndEthnicity().getHispanicUnableToDetermineCode()
          : "";
      String hispanicOriginCode = incomingParticipant.getRaceAndEthnicity() != null
          ? incomingParticipant.getRaceAndEthnicity().getHispanicOriginCode()
          : "";

      /*
       * IMPORTANT: A referral client record must be added after updating client sensitivity
       * indicator
       */
      executeR04466ClientSensitivityIndicator(foundClient, screeningToReferral);

      foundClient.update(incomingParticipant.getFirstName(), incomingParticipant.getMiddleName(),
          incomingParticipant.getLastName(), incomingParticipant.getNameSuffix(),
          incomingParticipant.getGender(), incomingParticipant.getSsn(), primaryRaceCode,
          unableToDetermineCode, hispanicUnableToDetermineCode, hispanicOriginCode,
          incomingParticipant.getDateOfBirth());

      update(messageBuilder, incomingParticipant, foundClient, otherRaceCodes);
    } else {
      final String message = String.format(
          "Unable to update client %s %s. Client has been modified by another process.",
          incomingParticipant.getFirstName(), incomingParticipant.getLastName());
      messageBuilder.addMessageAndLog(message, LOGGER);
    }
  }

  private boolean okToUpdateClient(Participant incomingParticipant, Client foundClient,
      DateTimeComparatorInterface comparator) {
    return comparator.compare(incomingParticipant.getLegacyDescriptor().getLastUpdated(),
        foundClient.getLastUpdatedTime());
  }

  private void update(MessageBuilder messageBuilder, Participant incomingParticipant,
      Client foundClient, List<Short> otherRaceCodes) {
    final Client savedClient =
        this.clientService.update(incomingParticipant.getLegacyDescriptor().getId(), foundClient);
    clientScpEthnicityService.createOtherEthnicity(foundClient.getExistingClientId(),
        otherRaceCodes);
    if (savedClient != null) {
      incomingParticipant.getLegacyDescriptor().setLastUpdated(savedClient.getLastUpdatedTime());
    } else {
      messageBuilder.addMessageAndLog("Unable to save Client", LOGGER);
    }
  }

  private String createNewClient(ScreeningToReferral screeningToReferral, String dateStarted,
      MessageBuilder messageBuilder, Participant incomingParticipant, String sexAtBirth) {
    String clientId;

    final List<Short> allRaceCodes = getAllRaceCodes(incomingParticipant.getRaceAndEthnicity());
    final Short primaryRaceCode = getPrimaryRaceCode(allRaceCodes);
    final List<Short> otherRaceCodes = getOtherRaceCodes(allRaceCodes, primaryRaceCode);
    final boolean childClientIndicatorVar =
        new R02265ChildClientExists(incomingParticipant, dateStarted).isValid();

    final Client client = Client.createWithDefaults(incomingParticipant, dateStarted, sexAtBirth,
        primaryRaceCode, childClientIndicatorVar);

    /*
     * IMPORTANT: A referral client record must be added after updating client sensitivity indicator
     */
    executeR04466ClientSensitivityIndicator(client, screeningToReferral);

    messageBuilder.addDomainValidationError(validator.validate(client));
    try {
      final PostedClient postedClient = this.clientService.create(client);
      clientId = postedClient.getId();
      incomingParticipant.setLegacyDescriptor(
          new LegacyDescriptor(clientId, null, postedClient.getLastUpdatedTime(),
              LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription()));
      clientScpEthnicityService.createOtherEthnicity(postedClient.getId(), otherRaceCodes);
    } catch (Exception e) {
      throw new ServiceException("Error creating client: " + e.getMessage(), e);
    }
    return clientId;
  }

  private Reporter saveReporter(Participant ip, String role, String referralId,
      String countySpecificCode, MessageBuilder messageBuilder) {
    gov.ca.cwds.rest.api.domain.Address reporterAddress = null;

    if (ip.getAddresses() != null) {
      final Set<gov.ca.cwds.rest.api.domain.Address> addresses = new HashSet<>(ip.getAddresses());

      for (gov.ca.cwds.rest.api.domain.Address address : addresses) {
        if (address != null) {
          reporterAddress = address;
          // use the first address node only
          break;
        }
      }
    }

    final Boolean mandatedReporterIndicator = ParticipantValidator.roleIsMandatedReporter(role);
    Reporter theReporter = reporterService.find(referralId);
    if (theReporter == null) {
      final Reporter reporter = Reporter.createWithDefaults(referralId, mandatedReporterIndicator,
          reporterAddress, ip, countySpecificCode);

      messageBuilder.addDomainValidationError(validator.validate(reporter));
      theReporter = reporterService.create(reporter);
    }
    return theReporter;
  }

  private ChildClient processChildClient(String clientId, String referralId, String dateStarted,
      String timeStarted, MessageBuilder messageBuilder, List<Csec> csecs,
      SafelySurrenderedBabies ssb, ScreeningToReferral screeningToReferral) {
    ChildClient exsistingChild = this.childClientService.find(clientId);

    final boolean ssbReportType =
        FerbConstants.ReportType.SSB.equals(screeningToReferral.getReportType());
    final boolean csecReportType =
        FerbConstants.ReportType.CSEC.equals(screeningToReferral.getReportType());
    
    if (exsistingChild == null) {
      final ChildClient childClient = ChildClient.createWithDefaults(clientId);
      childClient.setSafelySurrendedBabiesIndicatorVar(ssbReportType);
      messageBuilder.addDomainValidationError(validator.validate(childClient));
      exsistingChild = this.childClientService.create(childClient);
    }

    if (csecReportType && isValidCsecs(csecs, messageBuilder)) {
      saveOrUpdateCsec(clientId, csecs, messageBuilder);
      // create a special project for this referral
      specialProjectReferralService.saveCsecSpecialProjectReferral(referralId,
          screeningToReferral.getIncidentCounty(), dateStarted, messageBuilder);
    }

    if (ssbReportType && isValidSafelySurrenderedBabies(ssb, messageBuilder)) {
      specialProjectReferralService.processSafelySurrenderedBabies(clientId, referralId,
          java.time.LocalDate.parse(dateStarted), java.time.LocalTime.parse(timeStarted), ssb);
    }

    return exsistingChild;
  }

  private boolean isValidCsecs(List<Csec> csecs, MessageBuilder messageBuilder) {
    
    final boolean validCsecEndDate = new R10971CsecEndDate(csecs).isValid();

    if (csecs == null || csecs.isEmpty()) {
      messageBuilder.addError("CSEC data is empty", ErrorMessage.ErrorType.VALIDATION);
      return false;
    }
    for (Csec csec : csecs) {
      if (null == csec.getStartDate()) {
        messageBuilder.addError("CSEC start date is not found for code: " + csec.getCsecCodeId(),
            ErrorMessage.ErrorType.VALIDATION);
        return false;
      }
    }

    final List<IntakeLov> intakeLovs = IntakeCodeCache.global().getAllLegacySystemCodesForMeta(
        SystemCodeCategoryId.COMMERCIALLY_SEXUALLY_EXPLOITED_CHILDREN);
    for (IntakeLov intakeLov : intakeLovs) {
      if (csecs.stream().filter(c -> intakeLov.getIntakeCode().equals(c.getCsecCodeId()))
          .count() > 1) {
        messageBuilder.addError("CSEC duplication for code: " + intakeLov.getIntakeCode(),
            ErrorMessage.ErrorType.VALIDATION);
        return false;
      }
    }

    if (!validCsecEndDate) {
      messageBuilder.addError("Victim while Absent from Placement requires an end date");
      return false;
    }
    return true;
  }

  private boolean isValidSafelySurrenderedBabies(SafelySurrenderedBabies ssb,
      MessageBuilder messageBuilder) {
    if (ssb == null) {
      messageBuilder.addError("SafelySurrenderedBabies info must be provided.",
          ErrorMessage.ErrorType.VALIDATION);
      return false;
    }

    return true;
  }

  private void saveOrUpdateCsec(String clientId, List<Csec> csecs, MessageBuilder messageBuilder) {
    final List<CsecHistory> csecHistories = new ArrayList<>();
    for (Csec csec : csecs) {
      Set<IssueDetails> detailsSet = droolsService.performBusinessRules(
          CommercialSexualExploitationHistoryDroolsConfiguration.INSTANCE, csec);
      if (!detailsSet.isEmpty()) {
        throw new BusinessValidationException(detailsSet);
      }
      final String csecCodeId = csec.getCsecCodeId();
      if (csecCodeId == null) {
        messageBuilder.addError("There is no CSEC code id provided for client with id: " + clientId,
            ErrorMessage.ErrorType.VALIDATION);
      } else {
        final Short csecLegacyId = Short.valueOf(csecCodeId);
        SexualExploitationType sexualExploitationType = null;

        if (csecLegacyId == null) {
          messageBuilder.addError("LOV code is not found for CSEC code id: " + csecCodeId,
              ErrorMessage.ErrorType.VALIDATION);
        } else {
          sexualExploitationType = sexualExploitationTypeDao.find(csecLegacyId);
        }
        if (sexualExploitationType == null) {
          messageBuilder.addError(
              "Legacy Id on CSEC does not correspond to an existing CMS/CWS CSEC",
              ErrorMessage.ErrorType.VALIDATION);
        } else {
          final CsecHistory csecHistory = new CsecHistory();
          csecHistory.setSexualExploitationType(sexualExploitationType);
          csecHistory.setChildClient(clientId);
          csecHistory.setStartDate(csec.getStartDate());
          csecHistory.setEndDate(csec.getEndDate());
          csecHistories.add(csecHistory);
        }
      }
    }

    csecHistoryService.updateCsecHistoriesByClientId(clientId, csecHistories);
  }

  /*
   * CMS Address - create ADDRESS and CLIENT_ADDRESS for each address of the participant
   */
  private Participant processClientAddress(Participant clientParticipant, String referralId,
      String clientId, MessageBuilder messageBuilder) {
    return clientAddressService.saveClientAddress(clientParticipant, referralId, clientId,
        messageBuilder);
  }

  /**
   * First address in the list is primary.
   *
   * @param allRaceCodes list of incoming races
   * @return race code
   */
  private Short getPrimaryRaceCode(List<Short> allRaceCodes) {
    Short primaryRaceCode = 0;
    if (!allRaceCodes.isEmpty()) {
      primaryRaceCode = allRaceCodes.get(0);
    }
    return primaryRaceCode;
  }

  private List<Short> getOtherRaceCodes(List<Short> allRaceCodes, Short primaryRaceCode) {
    final List<Short> otherRaceCodes = new ArrayList<>(allRaceCodes);
    if (!otherRaceCodes.isEmpty()) {
      otherRaceCodes.remove(primaryRaceCode);
    }
    return otherRaceCodes;
  }

  private List<Short> getAllRaceCodes(RaceAndEthnicity raceAndEthnicity) {
    final List<Short> allRaceCodes = new ArrayList<>();

    if (raceAndEthnicity != null) {
      final List<Short> raceCodes = raceAndEthnicity.getRaceCode();
      if (raceCodes != null) {
        allRaceCodes.addAll(raceCodes);
      }

      final List<Short> hispanicCodes = raceAndEthnicity.getHispanicCode();
      if (hispanicCodes != null) {
        allRaceCodes.addAll(hispanicCodes);
      }
    }
    return allRaceCodes;
  }

  private void executeR04466ClientSensitivityIndicator(Client client,
      ScreeningToReferral screeningToReferral) {
    new R04466ClientSensitivityIndicator(client,
        LimitedAccessType.getByValue(screeningToReferral.getLimitedAccessCode()), caseDao,
        referralClientDao).execute();
  }

  void setCsecHistoryService(CsecHistoryService csecHistoryService) {
    this.csecHistoryService = csecHistoryService;
  }

  void setSexualExploitationTypeDao(SexualExploitationTypeDao sexualExploitationTypeDao) {
    this.sexualExploitationTypeDao = sexualExploitationTypeDao;
  }

  public SpecialProjectReferralService getSpecialProjectReferralService() {
    return specialProjectReferralService;
  }

  public void setSpecialProjectReferralService(
      SpecialProjectReferralService specialProjectReferralService) {
    this.specialProjectReferralService = specialProjectReferralService;
  }

  public void setDroolsService(DroolsService droolsService) {
    this.droolsService = droolsService;
  }

}
