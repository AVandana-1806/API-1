package gov.ca.cwds.rest.services;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipDTO;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.clientrelationship.ClientRelationshipCoreService;
import gov.ca.cwds.data.access.dto.ClientRelationshipDtoBuilder;
import gov.ca.cwds.data.persistence.cms.BaseClient;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Validator;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.GovernmentAgency;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.AgencyType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.business.rules.R06998ZippyIndicator;
import gov.ca.cwds.rest.business.rules.R08740SetNonProtectingParentCode;
import gov.ca.cwds.rest.business.rules.Reminders;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.cms.AllegationPerpetratorHistoryService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationCrossReportService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.validation.ParticipantValidator;
import gov.ca.cwds.rest.validation.StartDateTimeValidator;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Business layer object to work on {@link Screening}.
 * 
 * @author CWDS API Team
 */
public class ScreeningToReferralService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningToReferralService.class);

  private Validator validator;
  private MessageBuilder messageBuilder;

  private ReferralService referralService;
  private AllegationService allegationService;
  private AllegationPerpetratorHistoryService allegationPerpetratorHistoryService;
  private CrossReportService crossReportService;
  private ParticipantService participantService;
  private ClientRelationshipCoreService clientRelationshipService;
  private Reminders reminders;
  private GovernmentOrganizationCrossReportService governmentOrganizationCrossReportService;

  private ReferralDao referralDao;
  private ClientRelationshipDao clientRelationshipDao;

  /**
   * Constructor
   * 
   * @param referralService referralService
   * @param allegationService allegationService
   * @param crossReportService crossReportService
   * @param participantService participantService
   * @param validator validator
   * @param referralDao referralDao
   * @param messageBuilder messageBuilder
   * @param allegationPerpetratorHistoryService allegationPerpetratorHistoryService
   * @param reminders reminders
   * @param governmentOrganizationCrossReportService governmentOrganizationCrossReportService
   * @param clientRelationshipDao clientRelationshipDao
   */
  @Inject
  public ScreeningToReferralService(ReferralService referralService,
      AllegationService allegationService, CrossReportService crossReportService,
      ParticipantService participantService, ClientRelationshipCoreService clientRelationshipService,
      Validator validator, ReferralDao referralDao, MessageBuilder messageBuilder,
      AllegationPerpetratorHistoryService allegationPerpetratorHistoryService, Reminders reminders,
      GovernmentOrganizationCrossReportService governmentOrganizationCrossReportService,
      ClientRelationshipDao clientRelationshipDao) {

    super();
    this.clientRelationshipDao = clientRelationshipDao;
    this.referralService = referralService;
    this.allegationService = allegationService;
    this.crossReportService = crossReportService;
    this.participantService = participantService;
    this.clientRelationshipService = clientRelationshipService;
    this.validator = validator;
    this.referralDao = referralDao;
    this.messageBuilder = messageBuilder;
    this.allegationPerpetratorHistoryService = allegationPerpetratorHistoryService;
    this.reminders = reminders;
    this.governmentOrganizationCrossReportService = governmentOrganizationCrossReportService;
  }

  @UnitOfWork(value = "cms")
  @Override
  public Response create(Request request) {
    ScreeningToReferral screeningToReferral = (ScreeningToReferral) request;
    verifyReferralHasValidParticipants(screeningToReferral);

    /**
     * <blockquote>
     * 
     * <pre>
     * BUSINESS RULE: "R - 05446" - Default dateStarted and timeStarted
     * 
     * Referral received date and received time need to set when referral was created 
     * </blockquote>
     * </pre>
     */
    String dateStarted =
        StartDateTimeValidator.extractStartDate(screeningToReferral.getStartedAt(), messageBuilder);
    String timeStarted =
        StartDateTimeValidator.extractStartTime(screeningToReferral.getStartedAt(), messageBuilder);

    String referralId = createCmsReferral(screeningToReferral, dateStarted, timeStarted);

    ClientParticipants clientParticipants = processParticipants(screeningToReferral, dateStarted,
        timeStarted, referralId, messageBuilder);

    saveRelationships(screeningToReferral);

    Set<CrossReport> resultCrossReports = createCrossReports(screeningToReferral, referralId);

    Set<Allegation> resultAllegations = createAllegations(screeningToReferral, referralId,
        clientParticipants.getVictimIds(), clientParticipants.getPerpetratorIds());

    PostedScreeningToReferral pstr =
        PostedScreeningToReferral.createWithDefaults(referralId, screeningToReferral,
            clientParticipants.getParticipants(), resultCrossReports, resultAllegations);

    reminders.createTickle(pstr);

    boolean foundError = false;
    for (ErrorMessage message : messageBuilder.getMessages()) {
      if (StringUtils.isNotBlank(message.getMessage())) {
        foundError = true;
      }
    }
    if (foundError) {
      throw new BusinessValidationException(messageBuilder.getIssues());
    }

    return pstr;
  }

  private Set<Allegation> createAllegations(ScreeningToReferral screeningToReferral,
      String referralId, Map<Long, String> victimClient, Map<Long, String> perpatratorClient) {
    Set<Allegation> resultAllegations = null;
    try {
      resultAllegations =
          processAllegations(screeningToReferral, referralId, perpatratorClient, victimClient);
    } catch (ServiceException e) {
      String message = e.getMessage();
      logError(message, e);
    }
    return resultAllegations;
  }

  private Set<CrossReport> createCrossReports(ScreeningToReferral screeningToReferral,
      String referralId) {
    Set<CrossReport> resultCrossReports = null;
    try {
      resultCrossReports = processCrossReports(screeningToReferral, referralId);
    } catch (ServiceException e) {
      String message = e.getMessage();
      logError(message, e);
    }
    return resultCrossReports;
  }

  private ClientParticipants processParticipants(ScreeningToReferral screeningToReferral,
      String dateStarted, String timeStarted, String referralId, MessageBuilder messageBuilder) {

    return participantService.saveParticipants(screeningToReferral, dateStarted, timeStarted,
        referralId, messageBuilder);
  }

  private void saveRelationships(ScreeningToReferral screeningToReferral){
    for (ScreeningRelationship relationship : screeningToReferral.getRelationships()){
      if(relationship.getId() == null || relationship.getId().isEmpty()){
        try {
          ClientRelationshipDtoBuilder builder = new ClientRelationshipDtoBuilder(relationship);
          gov.ca.cwds.data.legacy.cms.entity.ClientRelationship savedRelationship = clientRelationshipService.createRelationship(builder.build());
          relationship.setId(savedRelationship.getIdentifier());
        } catch (DataAccessServicesException e) {
          String message = e.getMessage();
          logError(message, e);
        }
      }
    }
  }

  private String createCmsReferral(ScreeningToReferral screeningToReferral, String dateStarted,
      String timeStarted) {
    return referralService.createCmsReferralFromScreening(screeningToReferral, dateStarted,
        timeStarted, messageBuilder);
  }

  private void verifyReferralHasValidParticipants(ScreeningToReferral screeningToReferral) {
    try {
      if (!ParticipantValidator.hasValidParticipants(screeningToReferral)) {
        String message = " Incompatiable participants included in request";
        logError(message);
      }
    } catch (Exception e) {
      String message = e.getMessage();
      logError(message, e);
    }
  }

  private void logError(String message) {
    messageBuilder.addError(message);
    LOGGER.error(message);
  }

  private void logError(String message, Exception exception) {
    messageBuilder.addError(message);
    LOGGER.error(message, exception.getMessage());
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   * 
   *      <pre>
   * 
   * DocTool Rule R - 00796 
   * 
   * If the user had originally indicated that the call should be treated as a referral,
   * and that referral had been committed to the database, that referral must be deleted from the
   * system. Also deleted would be any referral clients associated with the referral, any clients
   * associated to the referral if that referral had been their only interaction with the system,
   * and all allegations associated with the referral. Do NOT delete the client if the client
   * already exists on the Host and associated to other Case or Referral.
   * 
   * This rule involves deleting a referral and associated referral clients, clients and
   * allegations. Since there is no business requirement at this time to delete a referral we will
   * not be implementing this rule. A NO-OP delete method is provided that gives a Not
   * Implemented Exception.
   *      </pre>
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
    /*
     * Reuse part of functionality from legacy gov.ca.cwds.rest.services.cms.ReferralService: the
     * ReferralService.update method executes validation according to DocTool Rule R04611. The
     * R04611ReferralStartDateTimeValidator and R04611ReferralStartDateTimeAction shell be used here
     * similarly.
     */
    assert primaryKey instanceof String;
    throw new NotImplementedException("Update is not implemented");
  }

  /*
   * CMS Cross Report
   */
  private Set<gov.ca.cwds.rest.api.domain.CrossReport> processCrossReports(ScreeningToReferral scr,
      String referralId) {

    String crossReportId = "";
    Set<gov.ca.cwds.rest.api.domain.CrossReport> resultCrossReports = new HashSet<>();
    Set<CrossReport> crossReports;
    crossReports = scr.getCrossReports();

    if (crossReports != null) {

      for (CrossReport crossReport : crossReports) {

        Boolean outStateLawEnforcementIndicator = Boolean.FALSE;
        String outStateLawEnforcementAddr = "";

        outStateLawEnforcementIndicator(outStateLawEnforcementIndicator,
            outStateLawEnforcementAddr);

        validateCrossReport(scr, referralId, crossReportId, resultCrossReports, crossReport,
            outStateLawEnforcementIndicator, outStateLawEnforcementAddr);
      }
    }

    return resultCrossReports;
  }

  private void validateCrossReport(ScreeningToReferral screeningToReferral, String referralId,
      String crossReportId, Set<gov.ca.cwds.rest.api.domain.CrossReport> resultCrossReports,
      CrossReport crossReport, Boolean outStateLawEnforcementIndicator,
      String outStateLawEnforcementAddr) {

    if (StringUtils.isBlank(crossReport.getLegacyId())) {
      persistCrossReport(screeningToReferral, referralId, crossReportId, resultCrossReports,
          crossReport, outStateLawEnforcementIndicator, outStateLawEnforcementAddr);
    } else {
      gov.ca.cwds.rest.api.domain.cms.CrossReport foundCrossReport =
          this.crossReportService.find(crossReport.getLegacyId());
      if (foundCrossReport == null) {
        String message =
            " Legacy Id on Cross Report does not correspond to an existing CMS/CWS Cross Report ";
        ServiceException se = new ServiceException(message);
        logError(message, se);

      }
    }
  }

  /**
   * <blockquote>
   * 
   * <pre>
   * BUSINESS RULE: "R - 05930" - Out of State text Mandatory
   * 
   * IF    CrossReport out  StateLaw Enforcement Indicator is 'true'
   * THEN  CrossReport outStateLawEnforcementAddr can't be blank
   * </blockquote>
   * </pre>
   */
  private void outStateLawEnforcementIndicator(Boolean outStateLawEnforcementIndicator,
      String outStateLawEnforcementAddr) {
    if (outStateLawEnforcementIndicator && StringUtils.isBlank(outStateLawEnforcementAddr)) {
      String message =
          "outStateLawEnforcementIndicator is set true, Then outStateLawEnforcementAddr can't be blank";
      ServiceException se = new ServiceException(message);
      logError(message, se);
    }
  }

  private void persistCrossReport(ScreeningToReferral screeningToReferral, String referralId,
      String crossReportId, Set<gov.ca.cwds.rest.api.domain.CrossReport> resultCrossReports,
      CrossReport crossReport, Boolean outStateLawEnforcementIndicator,
      String outStateLawEnforcementAddr) {

    Map<String, String> agencyMap = getLawEnforcement(crossReport.getAgencies());
    String lawEnforcementId = agencyMap.get(AgencyType.LAW_ENFORCEMENT.name());
    Boolean governmentOrgCrossRptIndicatorVar = StringUtils.isNotBlank(agencyMap.get("OTHER"));

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults(crossReportId, crossReport,
            referralId, getStaffIdCreatedCrossreport(), outStateLawEnforcementAddr,
            lawEnforcementId, screeningToReferral.getIncidentCounty(),
            outStateLawEnforcementIndicator, governmentOrgCrossRptIndicatorVar);

    messageBuilder.addDomainValidationError(validator.validate(cmsCrossReport));

    gov.ca.cwds.rest.api.domain.cms.CrossReport postedCrossReport =
        this.crossReportService.create(cmsCrossReport);
    governmentOrganizationCrossReportService.createDomainConstructor(postedCrossReport,
        crossReport.getAgencies());
    crossReport.setLegacyId(postedCrossReport.getThirdId());
    crossReport.setLegacySourceTable(LegacyTable.CROSS_REPORT.getName());
    resultCrossReports.add(crossReport);
  }

  private static String getStaffIdCreatedCrossreport() {
    return RequestExecutionContext.instance().getStaffId();
  }

  private Map<String, String> getLawEnforcement(Set<GovernmentAgency> agencies) {
    Map<String, String> agencyMap = new HashMap<>();
    if (agencies != null && !agencies.isEmpty()) {
      for (GovernmentAgency agency : agencies) {
        if (AgencyType.LAW_ENFORCEMENT.name().equals(agency.getType())) {
          agencyMap.put(AgencyType.LAW_ENFORCEMENT.name(), agency.getId());
        } else {
          agencyMap.put("OTHER", "TRUE");
        }
      }
    }
    return agencyMap;
  }

  /*
   * CMS Allegation - one for each allegation
   */
  private Set<Allegation> processAllegations(ScreeningToReferral scr, String referralId,
      Map<Long, String> perpatratorClient, Map<Long, String> victimClient) {

    Set<Allegation> processedAllegations = new HashSet<>();
    Set<Allegation> allegations;
    String victimClientId = "";
    String perpatratorClientId = "";

    allegations = scr.getAllegations();

    /**
     * <blockquote>
     * 
     * <pre>
     * BUSINESS RULE: "R - 03895"
     * 
     * There must be at least one allegation
     * AND
     * Allegation disposition type should be 0 (zero)
     * </blockquote>
     * </pre>
     */
    final Short allegationDispositionType = LegacyDefaultValues.DEFAULT_CODE;

    if (allegations == null || allegations.isEmpty()) {
      String message = " Referral must have at least one Allegation ";
      ServiceException exception = new ServiceException(message);
      logError(message, exception);
      return processedAllegations;
    }

    for (Allegation allegation : allegations) {

      if (!validateAllegationHasVictim(scr, allegation)) {
        victimClientId =
            getClientLegacyId(victimClient, victimClientId, allegation.getVictimPersonId());

        boolean allegationHasPerpPersonId = allegation.getPerpetratorPersonId() != 0;
        boolean isNotPerpetrator = !ParticipantValidator.isPerpetratorParticipant(scr,
            allegation.getPerpetratorPersonId());
        validateAllegationHasPerpetrator(allegationHasPerpPersonId, isNotPerpetrator);

        perpatratorClientId = getClientLegacyId(perpatratorClient, perpatratorClientId,
            allegation.getPerpetratorPersonId());
        if (validateAllegationVictimExists(victimClientId)) {
          saveAllegation(scr, referralId, processedAllegations, victimClientId, perpatratorClientId,
              allegationDispositionType, allegation);
        }
      }
    }
    return processedAllegations;
  }

  private void saveAllegation(ScreeningToReferral scr, String referralId,
      Set<Allegation> processedAllegations, String victimClientId, String perpatratorClientId,
      Short allegationDispositionType, Allegation allegation) {
    if (allegation.getLegacyId() == null || allegation.getLegacyId().isEmpty()) {
      persistAllegation(scr, referralId, processedAllegations, victimClientId, perpatratorClientId,
          allegationDispositionType, allegation);

    } else {
      gov.ca.cwds.rest.api.domain.cms.Allegation foundAllegation =
          this.allegationService.find(allegation.getLegacyId());
      if (foundAllegation == null) {
        String message =
            "Legacy Id on Allegation does not correspond to an existing CMS/CWS Allegation";
        ServiceException se = new ServiceException(message);
        logError(message, se);
        return;
      }
    }
  }

  private boolean validateAllegationVictimExists(String victimClientId) {
    if (victimClientId.isEmpty()) {
      String message = "Victim could not be determined for an allegation";
      logError(message, new ServiceException(message));
      return false;
    }
    return true;
  }

  private void validateAllegationHasPerpetrator(boolean allegationHasPerpPersonId,
      boolean isNotPerpetrator) {
    if (allegationHasPerpPersonId && isNotPerpetrator) {
      String message =
          "Allegation/Perpetrator Person Id does not contain a Participant with a role of Perpetrator";
      ServiceException exception = new ServiceException(message);
      logError(message, exception);
    }
  }

  private String getClientLegacyId(Map<Long, String> client, String clientId, long personId) {
    if (client.containsKey(personId)) {
      clientId = client.get(personId);
    }
    return clientId;
  }

  private boolean validateAllegationHasVictim(ScreeningToReferral scr, Allegation allegation) {
    try {
      if (!ParticipantValidator.isVictimParticipant(scr, allegation.getVictimPersonId())) {
        String message =
            " Allegation/Victim Person Id does not contain a Participant with a role of Victim ";
        logError(message);
      }
    } catch (Exception e) {
      logError(e.getMessage(), e);
      return true;
    }
    return false;
  }

  private void persistAllegation(ScreeningToReferral scr, String referralId,
      Set<Allegation> processedAllegations, String victimClientId, String perpatratorClientId,
      final Short allegationDispositionType, Allegation allegation) {

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", LegacyDefaultValues.DEFAULT_CODE, "",
            scr.getLocationType(), "", allegationDispositionType, allegation.getType(), "", "",
            Boolean.FALSE, LegacyDefaultValues.DEFAULT_NON_PROTECTING_PARENT_CODE, Boolean.FALSE,
            victimClientId, perpatratorClientId, referralId, scr.getIncidentCounty(),
            R06998ZippyIndicator.YES.getCode(), LegacyDefaultValues.DEFAULT_CODE);
    executeR08740(cmsAllegation, victimClientId, perpatratorClientId);
    messageBuilder.addDomainValidationError(validator.validate(cmsAllegation));

    PostedAllegation postedAllegation = this.allegationService.create(cmsAllegation);
    allegation.setLegacyId(postedAllegation.getId());
    allegation.setLegacySourceTable(LegacyTable.ALLEGATION.getName());
    allegation.setNonProtectingParent(postedAllegation.getNonProtectingParentCode());
    processedAllegations.add(allegation);

    // create the Allegation Perpetrator History
    processAllegationPerpetratorHistory(scr, postedAllegation);
  }

  private void executeR08740(gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation,
      String victimClientId, String perpetratorClientId) {
    R08740SetNonProtectingParentCode r08740 = new R08740SetNonProtectingParentCode(cmsAllegation,
        clientRelationshipDao, victimClientId, perpetratorClientId);
    r08740.execute();
  }

  private void processAllegationPerpetratorHistory(ScreeningToReferral scr,
      PostedAllegation postedAllegation) {
    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory cmsPerpHistory =
        new gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory(scr.getIncidentCounty(),
            postedAllegation.getPerpetratorClientId(), postedAllegation.getId(),
            DomainChef.cookDate(RequestExecutionContext.instance().getRequestStartTime()));

    messageBuilder.addDomainValidationError(validator.validate(cmsPerpHistory));

    this.allegationPerpetratorHistoryService.create(cmsPerpHistory);
  }


}
