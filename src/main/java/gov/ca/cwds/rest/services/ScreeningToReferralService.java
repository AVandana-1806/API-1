package gov.ca.cwds.rest.services;

import static gov.ca.cwds.rest.core.Api.DATASOURCE_CMS;

import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.business.rules.CrossReportDroolsConfiguration;
import gov.ca.cwds.rest.exception.IssueDetails;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.Validator;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.clientrelationship.ClientRelationshipCoreService;
import gov.ca.cwds.data.access.dto.ClientRelationshipDtoBuilder;
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
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
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
import gov.ca.cwds.rest.validation.CaresValidationUtils;
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

  @Inject
  private DroolsService droolsService;

  private ReferralService referralService;
  private AllegationService allegationService;
  private AllegationPerpetratorHistoryService allegationPerpetratorHistoryService;
  private CrossReportService crossReportService;
  private ParticipantToLegacyClient participantToLegacyClient;
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
   * @param participantToLegacyClient participantToLegacyClient
   * @param clientRelationshipService clientRelationshipService
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
      ParticipantToLegacyClient participantToLegacyClient,
      ClientRelationshipCoreService clientRelationshipService, Validator validator,
      ReferralDao referralDao, MessageBuilder messageBuilder,
      AllegationPerpetratorHistoryService allegationPerpetratorHistoryService, Reminders reminders,
      GovernmentOrganizationCrossReportService governmentOrganizationCrossReportService,
      ClientRelationshipDao clientRelationshipDao) {

    super();
    this.clientRelationshipDao = clientRelationshipDao;
    this.referralService = referralService;
    this.allegationService = allegationService;
    this.crossReportService = crossReportService;
    this.participantToLegacyClient = participantToLegacyClient;
    this.clientRelationshipService = clientRelationshipService;
    this.validator = validator;
    this.referralDao = referralDao;
    this.messageBuilder = messageBuilder;
    this.allegationPerpetratorHistoryService = allegationPerpetratorHistoryService;
    this.reminders = reminders;
    this.governmentOrganizationCrossReportService = governmentOrganizationCrossReportService;
  }

  @UnitOfWork(DATASOURCE_CMS)
  @Override
  public Response create(@Valid Request request) {
    final ScreeningToReferral screeningToReferral = (ScreeningToReferral) request;
    verifyReferralHasValidParticipants(screeningToReferral);

    //BUSINESS RULE: "R - 05446" - Default dateStarted and timeStarted
    //Referral received date and received time need to set when referral was created
    String referralToBeCreatedAt =
        DomainChef.cookISO8601Timestamp(RequestExecutionContext.instance().getRequestStartTime());
    final String dateStarted =
        StartDateTimeValidator.extractStartDate(referralToBeCreatedAt, messageBuilder);
    final String timeStarted =
        StartDateTimeValidator.extractStartTime(referralToBeCreatedAt, messageBuilder);

    final String referralId = createCmsReferral(screeningToReferral, dateStarted, timeStarted);
    final ClientParticipants clientParticipants = participantToLegacyClient.saveParticipants(screeningToReferral, dateStarted, timeStarted,
        referralId, messageBuilder);
    saveRelationships(screeningToReferral, clientParticipants);
    final PostedScreeningToReferral pstr = createPostedScreeningToReferral(screeningToReferral,
        referralId, clientParticipants);
    reminders.createTickle(pstr);

    handleErrors();

    return pstr;
  }

  private PostedScreeningToReferral createPostedScreeningToReferral(
      ScreeningToReferral screeningToReferral, String referralId,
      ClientParticipants clientParticipants) {
    final Set<CrossReport> resultCrossReports = createCrossReports(screeningToReferral, referralId);
    final Set<Allegation> resultAllegations = createAllegations(screeningToReferral, referralId,
        clientParticipants.getVictimIds(), clientParticipants.getPerpetratorIds());

    return PostedScreeningToReferral.createWithDefaults(referralId, screeningToReferral,
        clientParticipants.getParticipants(), resultCrossReports, resultAllegations);
  }

  private void handleErrors() {
    boolean foundError = false;
    for (ErrorMessage message : messageBuilder.getMessages()) {
      if (StringUtils.isNotBlank(message.getMessage())) {
        foundError = true;
      }
    }

    // DRS: XA DEBUG: turn off business rules locally with system property.
    if (foundError && CaresValidationUtils.isBusinessValidationEnabled()) {
      LOGGER.warn("XA: DISABLE BUSINESS VALIDATION!");
      foundError = false;
    }

    if (foundError) {
      throw new BusinessValidationException(messageBuilder.getIssues());
    }
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
      logError(e.getMessage(), e);
    }
    return resultCrossReports;
  }

  private void saveRelationships(ScreeningToReferral screeningToReferral,
      ClientParticipants clientParticipants) {
    if (screeningToReferral.getRelationships() == null) {
      return;
    }
    for (ScreeningRelationship relationship : screeningToReferral.getRelationships()) {
      if (relationship == null)
        continue;
      if (relationship.getId() == null || relationship.getId().isEmpty()) {
        try {
          final ClientRelationshipDtoBuilder builder =
              new ClientRelationshipDtoBuilder(relationship, clientParticipants);
          final gov.ca.cwds.data.legacy.cms.entity.ClientRelationship savedRelationship =
              clientRelationshipService.createRelationship(builder.build());
          relationship.setId(savedRelationship.getIdentifier());
        } catch (DataAccessServicesException e) {
          logError(e.getMessage(), e);
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
      logError(e.getMessage(), e);
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
   * <blockquote>
   * 
   * <pre>
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
   * </pre>
   * 
   * </blockquote>
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    throw new NotImplementedException("Delete is not implemented");
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.PostedReferral find(Serializable primaryKey) {
    assert primaryKey instanceof String;
    final gov.ca.cwds.data.persistence.cms.Referral persistedReferral =
        referralDao.find(primaryKey);
    if (persistedReferral != null) {
      return new gov.ca.cwds.rest.api.domain.cms.PostedReferral(persistedReferral);
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
    final Set<gov.ca.cwds.rest.api.domain.CrossReport> resultCrossReports = new HashSet<>();
    final Set<CrossReport> crossReports = scr.getCrossReports();

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

    Set<IssueDetails> detailsList = droolsService.performBusinessRules(
        CrossReportDroolsConfiguration.INSTANCE, crossReport, screeningToReferral);
    if (!detailsList.isEmpty()) {
      throw new BusinessValidationException(detailsList);
    }

    if (StringUtils.isBlank(crossReport.getLegacyId())) {
      persistCrossReport(screeningToReferral, referralId, crossReportId, resultCrossReports,
          crossReport, outStateLawEnforcementIndicator, outStateLawEnforcementAddr);
    } else {
      final gov.ca.cwds.rest.api.domain.cms.CrossReport foundCrossReport =
          this.crossReportService.find(crossReport.getLegacyId());
      if (foundCrossReport == null) {
        final String message =
            " Legacy Id on Cross Report does not correspond to an existing CMS/CWS Cross Report ";
        final ServiceException se = new ServiceException(message);
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
      final String message =
          "outStateLawEnforcementIndicator is set true, then outStateLawEnforcementAddr can't be blank";
      final ServiceException se = new ServiceException(message);
      logError(message, se);
    }
  }

  private void persistCrossReport(ScreeningToReferral screeningToReferral, String referralId,
      String crossReportId, Set<gov.ca.cwds.rest.api.domain.CrossReport> resultCrossReports,
      CrossReport crossReport, Boolean outStateLawEnforcementIndicator,
      String outStateLawEnforcementAddr) {

    final Map<String, String> agencyMap = getLawEnforcement(crossReport.getAgencies());
    final String lawEnforcementId = agencyMap.get(AgencyType.LAW_ENFORCEMENT.name());
    final Boolean governmentOrgCrossRptIndicatorVar =
        StringUtils.isNotBlank(agencyMap.get("OTHER"));

    final gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults(crossReportId, crossReport,
            referralId, getStaffIdCreatedCrossreport(), outStateLawEnforcementAddr,
            lawEnforcementId, screeningToReferral.getIncidentCounty(),
            outStateLawEnforcementIndicator, governmentOrgCrossRptIndicatorVar);

    messageBuilder.addDomainValidationError(validator.validate(cmsCrossReport));

    final gov.ca.cwds.rest.api.domain.cms.CrossReport postedCrossReport =
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
    final Map<String, String> agencyMap = new HashMap<>();

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

    final Set<Allegation> processedAllegations = new HashSet<>();
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
      final String message = " Referral must have at least one Allegation ";
      final ServiceException exception = new ServiceException(message);
      logError(message, exception);
      return processedAllegations;
    }

    for (Allegation allegation : allegations) {
      if (!validateAllegationHasVictim(scr, allegation)) {
        victimClientId =
            getClientLegacyId(victimClient, victimClientId, allegation.getVictimPersonId());

        final boolean allegationHasPerpPersonId = allegation.getPerpetratorPersonId() != 0;
        final boolean isNotPerpetrator = !ParticipantValidator.isPerpetratorParticipant(scr,
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
      final gov.ca.cwds.rest.api.domain.cms.Allegation foundAllegation =
          this.allegationService.find(allegation.getLegacyId());
      if (foundAllegation == null) {
        final String message =
            "Legacy Id on Allegation does not correspond to an existing CMS/CWS Allegation";
        final ServiceException se = new ServiceException(message);
        logError(message, se);
        return;
      }
    }
  }

  private boolean validateAllegationVictimExists(String victimClientId) {
    if (StringUtils.isBlank(victimClientId)) {
      final String message = "Victim could not be determined for an allegation";
      logError(message, new ServiceException(message));
      return false;
    }
    return true;
  }

  private void validateAllegationHasPerpetrator(boolean allegationHasPerpPersonId,
      boolean isNotPerpetrator) {
    if (allegationHasPerpPersonId && isNotPerpetrator) {
      final String message =
          "Allegation/Perpetrator Person Id does not contain a Participant with a role of Perpetrator";
      final ServiceException exception = new ServiceException(message);
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
        final String message =
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

    final gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", LegacyDefaultValues.DEFAULT_CODE, "",
            scr.getLocationType(), "", allegationDispositionType, allegation.getType(), "", "",
            Boolean.FALSE, LegacyDefaultValues.DEFAULT_NON_PROTECTING_PARENT_CODE, Boolean.FALSE,
            victimClientId, perpatratorClientId, referralId, scr.getIncidentCounty(),
            R06998ZippyIndicator.YES.getCode(), LegacyDefaultValues.DEFAULT_CODE);
    executeR08740(cmsAllegation, victimClientId, perpatratorClientId);
    messageBuilder.addDomainValidationError(validator.validate(cmsAllegation));

    final PostedAllegation postedAllegation = this.allegationService.create(cmsAllegation);
    allegation.setLegacyId(postedAllegation.getId());
    allegation.setLegacySourceTable(LegacyTable.ALLEGATION.getName());
    allegation.setNonProtectingParent(postedAllegation.getNonProtectingParentCode());
    processedAllegations.add(allegation);

    // create the Allegation Perpetrator History
    processAllegationPerpetratorHistory(scr, postedAllegation);
  }

  private void executeR08740(gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation,
      String victimClientId, String perpetratorClientId) {
    final R08740SetNonProtectingParentCode r08740 = new R08740SetNonProtectingParentCode(
        cmsAllegation, clientRelationshipDao, victimClientId, perpetratorClientId);
    r08740.execute();
  }

  private void processAllegationPerpetratorHistory(ScreeningToReferral scr,
      PostedAllegation postedAllegation) {
    final gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory cmsPerpHistory =
        new gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory(scr.getIncidentCounty(),
            postedAllegation.getPerpetratorClientId(), postedAllegation.getId(),
            DomainChef.cookDate(RequestExecutionContext.instance().getRequestStartTime()));

    messageBuilder.addDomainValidationError(validator.validate(cmsPerpHistory));
    this.allegationPerpetratorHistoryService.create(cmsPerpHistory);
  }

  public void setDroolsService(DroolsService droolsService) {
    this.droolsService = droolsService;
  }
}
