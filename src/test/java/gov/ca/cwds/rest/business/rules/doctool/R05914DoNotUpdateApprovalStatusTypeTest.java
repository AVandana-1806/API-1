package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.ca.cwds.cms.data.access.service.impl.clientrelationship.ClientRelationshipCoreService;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.cms.AssignmentUnitDao;
import gov.ca.cwds.data.cms.CaseLoadDao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.ClientAddressDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.CwsOfficeDao;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.SsaName3Dao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.DrmsDocument;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.DrmsDocumentEntityBuilder;
import gov.ca.cwds.fixture.LongTextEntityBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.Reminders;
import gov.ca.cwds.rest.business.rules.UpperCaseTables;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ParticipantService;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.AllegationPerpetratorHistoryService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentTemplateService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationCrossReportService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegation;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegationPerpetratorHistory;
import gov.ca.cwds.rest.services.referentialintegrity.RIChildClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIClientAddress;
import gov.ca.cwds.rest.services.referentialintegrity.RICrossReport;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferral;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferralClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIReporter;
import io.dropwizard.jackson.Jackson;

/**
 * Test Class for DocTool Rule: R - 05914 - Do Not Update Approval Status Type
 * 
 * @author CWDS API Team
 */
public class R05914DoNotUpdateApprovalStatusTypeTest {
  private ScreeningToReferralService screeningToReferralService;
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private ReferralService referralService;
  private ClientService clientService;
  private ReferralClientService referralClientService;
  private AllegationService allegationService;
  private AllegationPerpetratorHistoryService allegationPerpetratorHistoryService;
  private CrossReportService crossReportService;
  private ReporterService reporterService;
  private AddressService addressService;
  private ClientAddressService clientAddressService;
  private ChildClientService childClientService;
  private LongTextService longTextService;
  private AssignmentService assignmentService;
  private ParticipantService participantService;
  private ClientRelationshipCoreService clientRelationshipService;
  private RIChildClient riChildClient;
  private RIAllegationPerpetratorHistory riAllegationPerpetratorHistory;
  private RIClientAddress riClientAddress;
  private RIAllegation riAllegation;
  private RICrossReport riCrossReport;
  private RIReporter riReporter;
  private RIReferral riReferral;
  private RIReferralClient riReferralClient;
  private GovernmentOrganizationCrossReportService governmentOrganizationCrossReportService;

  private ReferralDao referralDao;
  private ClientDao clientDao;
  private ReferralClientDao referralClientDao;
  private AllegationDao allegationDao;
  private AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao;
  private CrossReportDao crossReportDao;
  private ReporterDao reporterDao;
  private AddressDao addressDao;
  private ClientAddressDao clientAddressDao;
  private ChildClientDao childClientDao;
  private LongTextDao longTextDao;
  private StaffPersonDao staffpersonDao;
  private AssignmentDao assignmentDao;
  private NonLACountyTriggers nonLACountyTriggers;
  private LACountyTrigger laCountyTrigger;
  private TriggerTablesDao triggerTablesDao;
  private CmsDocumentService cmsDocumentService;
  private DrmsDocumentService drmsDocumentService;
  private DrmsDocumentTemplateService drmsDocumentTemplateService;
  private DrmsDocumentDao drmsDocumentDao;
  private SsaName3Dao ssaName3Dao;
  private Reminders reminders;
  private UpperCaseTables upperCaseTables;
  private Validator validator;
  private ExternalInterfaceTables externalInterfaceTables;
  private CaseLoadDao caseLoadDao;
  private AssignmentUnitDao assignmentUnitDao;
  private CwsOfficeDao cwsOfficeDao;
  private MessageBuilder messageBuilder;
  private ClientRelationshipDao clientRelationshipDao;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    validator = Validation.buildDefaultValidatorFactory().getValidator();

    longTextDao = mock(LongTextDao.class);
    longTextService = new LongTextService(longTextDao);

    referralDao = mock(ReferralDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);

    cmsDocumentService = mock(CmsDocumentService.class);
    drmsDocumentDao = mock(DrmsDocumentDao.class);
    drmsDocumentService = new DrmsDocumentService(drmsDocumentDao);

    addressDao = mock(AddressDao.class);
    addressService = new AddressService(addressDao, ssaName3Dao, upperCaseTables, validator);
    riReferral = mock(RIReferral.class);

    referralService =
        new ReferralService(referralDao, nonLACountyTriggers, laCountyTrigger, triggerTablesDao,
            staffpersonDao, assignmentService, validator, cmsDocumentService, drmsDocumentService,
            drmsDocumentTemplateService, addressService, longTextService, riReferral);

    clientDao = mock(ClientDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    upperCaseTables = mock(UpperCaseTables.class);
    externalInterfaceTables = mock(ExternalInterfaceTables.class);
    clientService = new ClientService(clientDao, staffpersonDao, triggerTablesDao,
        nonLACountyTriggers, ssaName3Dao, upperCaseTables, externalInterfaceTables);
    clientRelationshipDao = mock(ClientRelationshipDao.class);
    referralClientDao = mock(ReferralClientDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    riReferralClient = mock(RIReferralClient.class);
    referralClientService = new ReferralClientService(referralClientDao, nonLACountyTriggers,
        laCountyTrigger, triggerTablesDao, staffpersonDao, riReferralClient);

    allegationDao = mock(AllegationDao.class);
    riAllegation = mock(RIAllegation.class);
    allegationService = new AllegationService(allegationDao, riAllegation);

    allegationPerpetratorHistoryDao = mock(AllegationPerpetratorHistoryDao.class);
    riAllegationPerpetratorHistory = mock(RIAllegationPerpetratorHistory.class);
    allegationPerpetratorHistoryService = new AllegationPerpetratorHistoryService(
        allegationPerpetratorHistoryDao, riAllegationPerpetratorHistory);

    crossReportDao = mock(CrossReportDao.class);
    riCrossReport = mock(RICrossReport.class);
    crossReportService = new CrossReportService(crossReportDao, riCrossReport);

    reporterDao = mock(ReporterDao.class);
    riReporter = mock(RIReporter.class);
    reporterService = new ReporterService(reporterDao, riReporter);

    clientAddressDao = mock(ClientAddressDao.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    riClientAddress = mock(RIClientAddress.class);
    clientAddressService =
        new ClientAddressService(clientAddressDao, staffpersonDao, triggerTablesDao,
            laCountyTrigger, nonLACountyTriggers, riClientAddress, validator, addressService);

    childClientDao = mock(ChildClientDao.class);
    riChildClient = mock(RIChildClient.class);
    childClientService = new ChildClientService(childClientDao, riChildClient);

    assignmentDao = mock(AssignmentDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    caseLoadDao = mock(CaseLoadDao.class);
    assignmentUnitDao = mock(AssignmentUnitDao.class);
    cwsOfficeDao = mock(CwsOfficeDao.class);
    messageBuilder = mock(MessageBuilder.class);
    assignmentService = new AssignmentService(assignmentDao, nonLACountyTriggers, staffpersonDao,
        triggerTablesDao, validator, externalInterfaceTables, caseLoadDao, referralDao,
        assignmentUnitDao, cwsOfficeDao, messageBuilder);

    reminders = mock(Reminders.class);
    governmentOrganizationCrossReportService = mock(GovernmentOrganizationCrossReportService.class);
    participantService = mock(ParticipantService.class);
    clientRelationshipService = mock(ClientRelationshipCoreService.class);

    screeningToReferralService =
        new ScreeningToReferralService(referralService, allegationService, crossReportService,
            participantService, clientRelationshipService, Validation.buildDefaultValidatorFactory().getValidator(),
            referralDao, new MessageBuilder(), allegationPerpetratorHistoryService, reminders,
            governmentOrganizationCrossReportService, clientRelationshipDao);

  }

  /**
   * <blockquote>
   *
   * <pre>
   *
   * DocTool Rule: "R - 05914" -  Do Not Update Approval Status Type
   * 
   * When creating the Referral entity, set the Approval Status Type = 'Request Not Submitted'.
   * When updating the Referral entity, update every attribute except Approval Status Type.
   * The Approval Status Type will be updated by the Host, not the workstation.
   * </pre>
   *
   * </blockquote>
   *
   * @throws Exception general error
   */
  @Test
  public void createReferralWithDefaultsSetsApprovalCodeNotSubmitted() throws Exception {
    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().createScreeningToReferral();

    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new LongTextEntityBuilder().build();
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    DrmsDocument drmsDocumentToCreate = new DrmsDocumentEntityBuilder().build();
    when(drmsDocumentDao.create(any(DrmsDocument.class))).thenReturn(drmsDocumentToCreate);

    Referral referralCreated = referralService.createReferralWithDefaults(screeningToReferral,
        "2016-08-03T01:00:00.000Z", "2016-08-03T01:00:00.000Z", new MessageBuilder());
    System.out.println(referralCreated.getApprovalStatusType());
    assertThat(referralCreated.getApprovalStatusType(), is(equalTo((short) 118)));
  }

}
