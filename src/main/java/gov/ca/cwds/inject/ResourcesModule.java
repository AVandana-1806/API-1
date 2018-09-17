package gov.ca.cwds.inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.SwaggerConfiguration;
import gov.ca.cwds.rest.api.contact.DeliveredServiceDomain;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.StaffPerson;
import gov.ca.cwds.rest.api.domain.auth.AuthorizationRequest;
import gov.ca.cwds.rest.api.domain.auth.AuthorizationResponse;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientCollateral;
import gov.ca.cwds.rest.api.domain.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.cms.CmsDocReferralClient;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganization;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationResponse;
import gov.ca.cwds.rest.api.domain.cms.LegacyKeyRequest;
import gov.ca.cwds.rest.api.domain.cms.LegacyKeyResponse;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOICaseResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.api.domain.investigation.People;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipList;
import gov.ca.cwds.rest.api.domain.investigation.SafetyAlerts;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactIntake;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactReferralRequest;
import gov.ca.cwds.rest.resources.AddressResource;
import gov.ca.cwds.rest.resources.ApplicationResource;
import gov.ca.cwds.rest.resources.IntakeLovResource;
import gov.ca.cwds.rest.resources.PersonResource;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.ScreeningDashboardResource;
import gov.ca.cwds.rest.resources.ScreeningIntakeResource;
import gov.ca.cwds.rest.resources.ScreeningRelationshipResource;
import gov.ca.cwds.rest.resources.ScreeningResource;
import gov.ca.cwds.rest.resources.ScreeningToReferralResource;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.resources.SwaggerResource;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.resources.TypedServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.auth.AuthorizationResource;
import gov.ca.cwds.rest.resources.cms.ClientCollateralResource;
import gov.ca.cwds.rest.resources.cms.CmsDocReferralClientResource;
import gov.ca.cwds.rest.resources.cms.CmsDocumentResource;
import gov.ca.cwds.rest.resources.cms.CmsNSReferralResource;
import gov.ca.cwds.rest.resources.cms.GovernmentOrganizationResource;
import gov.ca.cwds.rest.resources.contact.ContactIntakeResource;
import gov.ca.cwds.rest.resources.contact.DeliveredServiceResource;
import gov.ca.cwds.rest.resources.hoi.HoiCaseResource;
import gov.ca.cwds.rest.resources.hoi.HoiReferralResource;
import gov.ca.cwds.rest.resources.hoi.HoiScreeningResource;
import gov.ca.cwds.rest.resources.hoi.HoiUsingClientIdResource;
import gov.ca.cwds.rest.resources.hoi.InvolvementHistoryResource;
import gov.ca.cwds.rest.resources.investigation.ContactResource;
import gov.ca.cwds.rest.resources.investigation.HistoryOfInvolvementResource;
import gov.ca.cwds.rest.resources.investigation.PeopleResource;
import gov.ca.cwds.rest.resources.investigation.RelationshipListResource;
import gov.ca.cwds.rest.resources.investigation.SafetyAlertsResource;
import gov.ca.cwds.rest.resources.parameter.ParticipantResourceParameters;
import gov.ca.cwds.rest.resources.screening.participant.ParticipantIntakeApiResource;
import gov.ca.cwds.rest.resources.screeningparticipant.ScreeningParticipantResource;
import gov.ca.cwds.rest.resources.submit.ScreeningSubmitResource;
import gov.ca.cwds.rest.services.AddressService;
import gov.ca.cwds.rest.services.ContactIntakeApiService;
import gov.ca.cwds.rest.services.PersonService;
import gov.ca.cwds.rest.services.ScreeningRelationshipService;
import gov.ca.cwds.rest.services.ScreeningService;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
import gov.ca.cwds.rest.services.auth.AuthorizationService;
import gov.ca.cwds.rest.services.cms.AllegationPerpetratorHistoryService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientCollateralService;
import gov.ca.cwds.rest.services.cms.ClientRelationshipService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CmsDocReferralClientService;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;
import gov.ca.cwds.rest.services.cms.CmsNSReferralService;
import gov.ca.cwds.rest.services.cms.CmsReferralService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationService;
import gov.ca.cwds.rest.services.cms.LegacyKeyService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.StaffPersonService;
import gov.ca.cwds.rest.services.cms.SystemCodeService;
import gov.ca.cwds.rest.services.contact.DeliveredService;
import gov.ca.cwds.rest.services.hoi.HOICaseService;
import gov.ca.cwds.rest.services.hoi.HOIReferralService;
import gov.ca.cwds.rest.services.hoi.HOIScreeningService;
import gov.ca.cwds.rest.services.hoi.InvolvementHistoryService;
import gov.ca.cwds.rest.services.investigation.AllegationListService;
import gov.ca.cwds.rest.services.investigation.ClientsRelationshipsService;
import gov.ca.cwds.rest.services.investigation.HistoryOfInvolvementService;
import gov.ca.cwds.rest.services.investigation.InvestigationService;
import gov.ca.cwds.rest.services.investigation.PeopleService;
import gov.ca.cwds.rest.services.investigation.SafetyAlertsService;
import gov.ca.cwds.rest.services.investigation.contact.ContactService;
import gov.ca.cwds.rest.services.screening.participant.ParticipantIntakeApiService;
import gov.ca.cwds.rest.services.screeningparticipant.ScreeningParticipantService;
import gov.ca.cwds.rest.services.submit.ScreeningSubmitService;

/**
 * Identifies all CWDS API domain resource classes (REST endpoints) and supporting service classes
 * available for dependency injection by Guice.
 *
 * @author CWDS API Team
 */
public class ResourcesModule extends AbstractModule {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesModule.class);

  /**
   * Default, no-op constructor.
   */
  public ResourcesModule() {
    // Default, no-op.
  }

  @Override
  protected void configure() {
    LOGGER.info("configure: start");
    bind(ApplicationResource.class);
    bind(SwaggerResource.class);
    bind(AddressResource.class);
    bind(ParticipantIntakeApiResource.class);
    bind(PersonResource.class);
    bind(ScreeningResource.class);
    bind(ScreeningSubmitResource.class);
    bind(ScreeningIntakeResource.class);
    bind(ScreeningDashboardResource.class);
    bind(CmsDocReferralClientResource.class);
    bind(CmsDocumentResource.class);
    bind(CmsNSReferralResource.class);
    bind(ScreeningToReferralResource.class);
    bind(ScreeningRelationshipResource.class);
    bind(ClientCollateralResource.class);
    bind(gov.ca.cwds.rest.resources.StaffPersonResource.class);
    bind(DeliveredServiceResource.class);
    bind(ContactResource.class);
    bind(HistoryOfInvolvementResource.class);
    bind(gov.ca.cwds.rest.resources.investigation.ScreeningSummaryResource.class);
    bind(gov.ca.cwds.rest.resources.investigation.InvestigationsResource.class);
    bind(gov.ca.cwds.rest.resources.investigation.AllegationResource.class);
    bind(gov.ca.cwds.rest.resources.investigation.AllegationListResource.class);
    bind(gov.ca.cwds.rest.resources.investigation.CrossReportResource.class);
    bind(gov.ca.cwds.rest.resources.investigation.CrossReportListResource.class);
    bind(RelationshipListResource.class);
    bind(PeopleResource.class);
    bind(GovernmentOrganizationResource.class);
    bind(SafetyAlertsResource.class);
    bind(InvolvementHistoryResource.class);
    bind(HoiReferralResource.class);
    bind(HoiCaseResource.class);
    bind(HoiScreeningResource.class);
    bind(AuthorizationResource.class);
    bind(HoiUsingClientIdResource.class);
    bind(ScreeningParticipantResource.class);
    bind(ContactIntakeResource.class);
    bind(IntakeLovResource.class);
    LOGGER.info("configure: done");
  }

  @Provides
  public SwaggerConfiguration swaggerConfiguration(ApiConfiguration apiConfiguration) {
    return apiConfiguration.getSwaggerConfiguration();
  }

  @Provides
  @Named("app.name")
  public String appName(ApiConfiguration apiConfiguration) {
    return apiConfiguration.getApplicationName();
  }

  @Provides
  @Named("app.version")
  public String appVersion(ApiConfiguration apiConfiguration) {
    return apiConfiguration.getVersion();
  }

  @Provides
  @AddressServiceBackedResource
  public ResourceDelegate addressServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(AddressService.class));
  }

  @Provides
  @PersonServiceBackedResource
  public ResourceDelegate personServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(PersonService.class));
  }

  @Provides
  @ScreeningServiceBackedResource
  public ResourceDelegate screeningServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(ScreeningService.class));
  }

  @Provides
  @ScreeningRelationshipServiceBackedResource
  public ResourceDelegate screeningRelationshipServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(
        injector.getInstance(ScreeningRelationshipService.class));
  }

  @Provides
  @ScreeningSubmitServiceBackedResource
  public ResourceDelegate screeningSubmitServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(ScreeningSubmitService.class));
  }

  @Provides
  @AllegationServieBackedResource
  public TypedResourceDelegate<String, Allegation> allegationServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(AllegationService.class));
  }

  @Provides
  @LongTextServiceBackedResource
  public TypedResourceDelegate<String, LongText> longTextServiceBackedResource(Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(LongTextService.class));
  }

  @Provides
  @DrmsDocumentServiceBackedResource
  public TypedResourceDelegate<String, DrmsDocument> drmsDocumentServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(DrmsDocumentService.class));
  }

  @Provides
  @DeliveredServiceBackedResource
  public TypedResourceDelegate<String, DeliveredServiceDomain> deliveredServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(DeliveredService.class));
  }


  @Provides
  @ContactServiceBackedResource
  public TypedResourceDelegate<String, ContactReferralRequest> contactServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(ContactService.class));
  }

  @Provides
  @HistoryOfInvolvementServiceBackedResource
  public TypedResourceDelegate<String, InvolvementHistory> historyOfInvolementResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(HistoryOfInvolvementService.class));
  }

  @Provides
  @ScreeningParticipantServiceBackedResource
  public TypedResourceDelegate<String, ParticipantIntakeApi> screeningParticipantResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(ScreeningParticipantService.class));
  }

  @Provides
  @ScreeningSummaryServiceBackedResource
  public TypedResourceDelegate<String, gov.ca.cwds.rest.api.domain.investigation.ScreeningSummary> screeningSummaryServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector
        .getInstance(gov.ca.cwds.rest.services.investigation.ScreeningSummaryService.class));
  }

  @Provides
  @ChildClientServiceBackedResource
  public TypedResourceDelegate<String, ChildClient> childClientServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(ChildClientService.class));
  }

  @Provides
  @AllegationPerpetratorHistoryServiceBackedResource
  public TypedResourceDelegate<String, AllegationPerpetratorHistory> allegationPerpetratorHistoryServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(AllegationPerpetratorHistoryService.class));
  }

  @Provides
  @CmsDocumentReferralClientServiceBackedResource
  public TypedResourceDelegate<String, CmsDocReferralClient> cmsDocumentReferralClientServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(CmsDocReferralClientService.class));
  }

  @Provides
  @CmsDocumentBackedResource
  public TypedResourceDelegate<String, CmsDocument> cmsDocumentBackedResource(Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(CmsDocumentService.class));
  }

  @Provides
  @ClientServiceBackedResource
  public TypedResourceDelegate<String, Client> clientServiceBackedResource(Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(ClientService.class));
  }

  @Provides
  @CmsNSReferralServiceBackedResource
  public ResourceDelegate cmsNSReferralServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(CmsNSReferralService.class));
  }

  @Provides
  @CmsReferralServiceBackedResource
  public ResourceDelegate cmsReferralServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(CmsReferralService.class));
  }

  @Provides
  @ScreeningToReferralServiceBackedResource
  public ResourceDelegate screeningToReferralBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(
        injector.getInstance(ScreeningToReferralService.class));
  }

  @Provides
  @ReferralClientServiceBackedResource
  public TypedResourceDelegate<String, ReferralClient> referralClientServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(ReferralClientService.class));
  }

  @Provides
  @ReferralServiceBackedResource
  public TypedResourceDelegate<String, Referral> referralServiceBackedResource(Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(ReferralService.class));
  }

  @Provides
  @ReporterServiceBackedResource
  public TypedResourceDelegate<String, Reporter> reporterServiceBackedResource(Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(ReporterService.class));
  }

  @Provides
  @StaffPersonServiceBackedResource
  public ResourceDelegate staffPersonServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(StaffPersonService.class));
  }

  @Provides
  @StaffPersonsServiceBackedResource
  public TypedResourceDelegate<String, StaffPerson> staffPersonsServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(gov.ca.cwds.rest.services.StaffPersonService.class));
  }

  @Provides
  @SystemCodeServiceBackedResource
  public ResourceDelegate systemCodeServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(SystemCodeService.class));
  }

  @Provides
  @CrossReportServiceBackedResource
  public TypedResourceDelegate<String, CrossReport> crossReportServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(CrossReportService.class));
  }

  @Provides
  @ParticipantServiceBackedResource
  public TypedResourceDelegate<ParticipantResourceParameters, ParticipantIntakeApi> participantServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(ParticipantIntakeApiService.class));
  }

  @Provides
  @AssignmentServiceBackedResource
  public TypedResourceDelegate<String, Assignment> assignmentServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(AssignmentService.class));
  }

  @Provides
  @GovernmentOrganizationServiceBackedResource
  public SimpleResourceDelegate<String, GovernmentOrganization, GovernmentOrganizationResponse, GovernmentOrganizationService> governmentOrganizationResource(
      Injector injector) {
    return new SimpleResourceDelegate<>(injector.getInstance(GovernmentOrganizationService.class));
  }

  @Provides
  @LegacyKeyServiceResource
  public SimpleResourceDelegate<String, LegacyKeyRequest, LegacyKeyResponse, LegacyKeyService> legacyKeyResource(
      Injector inj) {
    return new SimpleResourceDelegate<>(inj.getInstance(LegacyKeyService.class));
  }

  @Provides
  @ClientRelationshipServiceBackedResource
  public TypedResourceDelegate<String, ClientRelationship> clientRelationshipServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(ClientRelationshipService.class));
  }

  @Provides
  @ClientCollateralServiceBackedResource
  public TypedResourceDelegate<String, ClientCollateral> clientCollateralServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(ClientCollateralService.class));
  }

  @Provides
  @InvestigationServiceBackedResource
  public TypedResourceDelegate<String, Investigation> investigationBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(InvestigationService.class));
  }

  @Provides
  @RelationshipListServiceBackedResource
  public TypedResourceDelegate<String, RelationshipList> relationshipListBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(ClientsRelationshipsService.class));
  }

  @Provides
  @PeopleServiceBackedResource
  public TypedResourceDelegate<String, People> peopleBackedResource(Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(PeopleService.class));
  }

  @Provides
  @InvestigationAllegationServiceBackedResource
  public TypedResourceDelegate<String, gov.ca.cwds.rest.api.domain.investigation.Allegation> allegationBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(gov.ca.cwds.rest.services.investigation.AllegationService.class));
  }

  @Provides
  @InvestigationAllegationListServiceBackedResource
  public TypedResourceDelegate<String, gov.ca.cwds.rest.api.domain.investigation.AllegationList> allegationListBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(AllegationListService.class));
  }

  @Provides
  @SafetyAlertsServiceBackedResource
  public TypedResourceDelegate<String, SafetyAlerts> safetyAlerts(Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(SafetyAlertsService.class));
  }

  @Provides
  @InvestigationCrossReportServiceBackedResource
  public TypedResourceDelegate<String, gov.ca.cwds.rest.api.domain.investigation.CrossReport> crossReportBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(gov.ca.cwds.rest.services.investigation.CrossReportService.class));
  }

  @Provides
  @InvestigationCrossReportListServiceBackedResource
  public TypedResourceDelegate<String, gov.ca.cwds.rest.api.domain.investigation.CrossReportList> crossReportListBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(gov.ca.cwds.rest.services.investigation.CrossReportListService.class));
  }

  @Provides
  @InvolvementHistoryServiceBackedResource
  public TypedResourceDelegate<String, InvolvementHistory> involvementHistoryServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(InvolvementHistoryService.class));
  }

  @Provides
  @HOIReferralServiceBackedResource
  public SimpleResourceDelegate<HOIRequest, HOIReferral, HOIReferralResponse, HOIReferralService> referralHoiServiceBackedResource(
      Injector injector) {
    return new SimpleResourceDelegate<>(injector.getInstance(HOIReferralService.class));
  }

  @Provides
  @HOICaseServiceBackedResource
  public SimpleResourceDelegate<HOIRequest, HOICase, HOICaseResponse, HOICaseService> caseHoiServiceBackedResource(
      Injector injector) {
    return new SimpleResourceDelegate<>(injector.getInstance(HOICaseService.class));
  }

  @Provides
  @HOIScreeningServiceBackedResource
  public SimpleResourceDelegate<HOIRequest, HOIScreening, HOIScreeningResponse, HOIScreeningService> screeningHOIServiceBackedResource(
      Injector injector) {
    return new SimpleResourceDelegate<>(injector.getInstance(HOIScreeningService.class));
  }

  @Provides
  @AuthorizationServiceBackedResource
  public SimpleResourceDelegate<String, AuthorizationRequest, AuthorizationResponse, AuthorizationService> authorizationServiceBackedResource(
      Injector injector) {
    return new SimpleResourceDelegate<>(injector.getInstance(AuthorizationService.class));
  }

  @Provides
  @ContactIntakeApiServiceBackedResource
  public TypedResourceDelegate<String, ContactIntake> contactIntakeApiServiceBackedResource(
      Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(
        injector.getInstance(ContactIntakeApiService.class));
  }

}
