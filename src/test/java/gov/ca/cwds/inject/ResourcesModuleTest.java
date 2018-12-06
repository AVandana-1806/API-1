package gov.ca.cwds.inject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.google.inject.Injector;
import gov.ca.cwds.rest.services.AddressService;
import gov.ca.cwds.rest.services.PersonService;
import gov.ca.cwds.rest.services.ScreeningRelationshipService;
import gov.ca.cwds.rest.services.ScreeningService;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
import gov.ca.cwds.rest.services.cms.AllegationPerpetratorHistoryService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientCollateralService;
import gov.ca.cwds.rest.services.cms.ClientRelationshipService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CmsDocReferralClientService;
import gov.ca.cwds.rest.services.cms.CmsNSReferralService;
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
import gov.ca.cwds.rest.services.investigation.CrossReportListService;
import gov.ca.cwds.rest.services.investigation.HistoryOfInvolvementService;
import gov.ca.cwds.rest.services.investigation.InvestigationService;
import gov.ca.cwds.rest.services.investigation.SafetyAlertsService;
import org.junit.Before;
import org.junit.Test;

public class ResourcesModuleTest {
  ResourcesModule resourceModule;
  Injector injector;

  @Before
  public void setup() {
    resourceModule = new ResourcesModule();
    injector = mock(Injector.class);
  }

  @Test
  public void addressServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.addressServiceBackedResource(injector);
    verify(injector).getInstance(AddressService.class);
  }

  @Test
  public void PersonServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.personServiceBackedResource(injector);
    verify(injector).getInstance(PersonService.class);
  }

  @Test
  public void screeningServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.screeningServiceBackedResource(injector);
    verify(injector).getInstance(ScreeningService.class);
  }

  @Test
  public void screeningRelationshipServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.screeningRelationshipServiceBackedResource(injector);
    verify(injector).getInstance(ScreeningRelationshipService.class);
  }

  @Test
  public void allegationServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.allegationServiceBackedResource(injector);
    verify(injector).getInstance(AllegationService.class);
  }

  @Test
  public void longtextServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.longTextServiceBackedResource(injector);
    verify(injector).getInstance(LongTextService.class);
  }

  @Test
  public void drmsDocumentServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.drmsDocumentServiceBackedResource(injector);
    verify(injector).getInstance(DrmsDocumentService.class);
  }

  @Test
  public void deliveredServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.deliveredServiceBackedResource(injector);
    verify(injector).getInstance(DeliveredService.class);
  }

  @Test
  public void historyOfInvolementServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.historyOfInvolvementResource(injector);
    verify(injector).getInstance(HistoryOfInvolvementService.class);
  }

  @Test
  public void screeningSummaryServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.screeningSummaryServiceBackedResource(injector);
    verify(injector)
        .getInstance(gov.ca.cwds.rest.services.investigation.ScreeningSummaryService.class);
  }

  @Test
  public void childClientServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.childClientServiceBackedResource(injector);
    verify(injector).getInstance(ChildClientService.class);
  }

  @Test
  public void allegationPerpetratorHistoryServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.allegationPerpetratorHistoryServiceBackedResource(injector);
    verify(injector).getInstance(AllegationPerpetratorHistoryService.class);
  }

  @Test
  public void cmsDocumentReferralClientServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.cmsDocumentReferralClientServiceBackedResource(injector);
    verify(injector).getInstance(CmsDocReferralClientService.class);
  }

  @Test
  public void clientClientServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.clientServiceBackedResource(injector);
    verify(injector).getInstance(ClientService.class);
  }

  @Test
  public void cmsNSReferralServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.cmsNSReferralServiceBackedResource(injector);
    verify(injector).getInstance(CmsNSReferralService.class);
  }

  @Test
  public void screeningToReferralServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.screeningToReferralBackedResource(injector);
    verify(injector).getInstance(ScreeningToReferralService.class);
  }

  @Test
  public void referralClientServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.referralClientServiceBackedResource(injector);
    verify(injector).getInstance(ReferralClientService.class);
  }

  @Test
  public void referralServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.referralServiceBackedResource(injector);
    verify(injector).getInstance(ReferralService.class);
  }

  @Test
  public void reporterServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.reporterServiceBackedResource(injector);
    verify(injector).getInstance(ReporterService.class);
  }

  @Test
  public void staffPersonServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.staffPersonServiceBackedResource(injector);
    verify(injector).getInstance(StaffPersonService.class);
  }

  @Test
  public void systemCodeServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.systemCodeServiceBackedResource(injector);
    verify(injector).getInstance(SystemCodeService.class);
  }

  @Test
  public void crossReportServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.crossReportServiceBackedResource(injector);
    verify(injector).getInstance(CrossReportService.class);
  }

  @Test
  public void clientParticipantServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.clientParticipantServiceBackedResource(injector);
    verify(injector).getInstance(gov.ca.cwds.rest.services.screening.participant.ParticipantService.class);
  }

  @Test
  public void participantServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.participantServiceBackedResource(injector);
    verify(injector).getInstance(gov.ca.cwds.rest.services.ParticipantService.class);
  }

  @Test
  public void assignmentServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.assignmentServiceBackedResource(injector);
    verify(injector).getInstance(AssignmentService.class);
  }

  @Test
  public void governmentOrganizationServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.governmentOrganizationResource(injector);
    verify(injector).getInstance(GovernmentOrganizationService.class);
  }

  @Test
  public void legacyKeyServiceBackedResourceShouldRetrieveServiceFromInjector() {
    resourceModule.legacyKeyResource(injector);
    verify(injector).getInstance(LegacyKeyService.class);
  }

  @Test
  public void clientRelationshipServiceBackedResource() {
    resourceModule.clientRelationshipServiceBackedResource(injector);
    verify(injector).getInstance(ClientRelationshipService.class);
  }

  @Test
  public void clientCollateralServiceBackedResource() {
    resourceModule.clientCollateralServiceBackedResource(injector);
    verify(injector).getInstance(ClientCollateralService.class);
  }

  @Test
  public void investigationServiceBackedResource() {
    resourceModule.investigationBackedResource(injector);
    verify(injector).getInstance(InvestigationService.class);
  }

  @Test
  public void relationshipListServiceBackedResource() {
    resourceModule.relationshipListBackedResource(injector);
    verify(injector).getInstance(ClientsRelationshipsService.class);
  }

  @Test
  public void peopleServiceBackedResource() {
    resourceModule.relationshipListBackedResource(injector);
    verify(injector).getInstance(ClientsRelationshipsService.class);
  }

  @Test
  public void allegationServiceBackedResource() {
    resourceModule.relationshipListBackedResource(injector);
    verify(injector).getInstance(ClientsRelationshipsService.class);
  }

  @Test
  public void allegationListServiceBackedResource() {
    resourceModule.allegationListBackedResource(injector);
    verify(injector).getInstance(AllegationListService.class);
  }

  @Test
  public void safetyAlertsServiceBackedResource() {
    resourceModule.safetyAlerts(injector);
    verify(injector).getInstance(SafetyAlertsService.class);
  }

  @Test
  public void crossReportServiceBackedResource() {
    resourceModule.safetyAlerts(injector);
    verify(injector).getInstance(SafetyAlertsService.class);
  }

  @Test
  public void crossReportListServiceBackedResource() {
    resourceModule.crossReportListBackedResource(injector);
    verify(injector).getInstance(CrossReportListService.class);
  }

  @Test
  public void involvementHistoryServiceBackedResource() {
    resourceModule.involvementHistoryServiceBackedResource(injector);
    verify(injector).getInstance(InvolvementHistoryService.class);
  }

  @Test
  public void referralHoiServiceBackedResource() {
    resourceModule.referralHoiServiceBackedResource(injector);
    verify(injector).getInstance(HOIReferralService.class);
  }

  @Test
  public void caseHoiServiceBackedResource() {
    resourceModule.caseHoiServiceBackedResource(injector);
    verify(injector).getInstance(HOICaseService.class);
  }

  @Test
  public void screeningHOIServiceBackedResource() {
    resourceModule.screeningHOIServiceBackedResource(injector);
    verify(injector).getInstance(HOIScreeningService.class);
  }
}
