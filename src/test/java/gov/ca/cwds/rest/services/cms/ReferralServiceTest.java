package gov.ca.cwds.rest.services.cms;

import static gov.ca.cwds.fixture.ReferralResourceBuilder.DEFAULT_REFERRAL_RECEIVED_DATE;
import static gov.ca.cwds.fixture.ReferralResourceBuilder.DEFAULT_REFERRAL_RECEIVED_TIME;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.AssignmentEntityBuilder;
import gov.ca.cwds.fixture.CmsDocumentResourceBuilder;
import gov.ca.cwds.fixture.DrmsDocumentResourceBuilder;
import gov.ca.cwds.fixture.DrmsDocumentTemplateResourceBuilder;
import gov.ca.cwds.fixture.LongTextEntityBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate;
import gov.ca.cwds.rest.api.domain.cms.PostedDrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferral;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ReferralServiceTest {

  private AssignmentService assignmentService;
  private CmsDocumentService cmsDocumentService;
  private DrmsDocumentService drmsDocumentService;
  private DrmsDocumentTemplateService drmsDocumentTemplateService;
  private AddressService addressService;
  private LongTextService longTextService;
  private ReferralService referralService;
  private ReferralDao referralDao;
  private NonLACountyTriggers nonLACountyTriggers;
  private LACountyTrigger laCountyTrigger;
  private TriggerTablesDao triggerTablesDao;
  private StaffPersonDao staffpersonDao;
  private Validator validator;
  private RIReferral riReferral;

  private MessageBuilder mockMessageBuilder;
  private String dateStarted;
  private String timeStarted;
  private Referral referralDomain;

  private static Boolean isLaCountyTrigger = false;

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Initialize system code cache
   */
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    referralDao = mock(ReferralDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    assignmentService = mock(AssignmentService.class);
    cmsDocumentService = mock(CmsDocumentService.class);
    drmsDocumentService = mock(DrmsDocumentService.class);
    drmsDocumentTemplateService = mock(DrmsDocumentTemplateService.class);
    addressService = mock(AddressService.class);
    longTextService = mock(LongTextService.class);
    StaffPerson staffPerson =
        new StaffPersonEntityBuilder().setId("q1p").setCountyCode("51").build();
    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("21");

    riReferral = mock(RIReferral.class);

    referralService = new ReferralService();
    referralService.setReferralDao(referralDao);
    referralService.setNonLaCountyTriggers(nonLACountyTriggers);
    referralService.setLaCountyTrigger(laCountyTrigger);
    referralService.setTriggerTablesDao(triggerTablesDao);
    referralService.setStaffpersonDao(staffpersonDao);
    referralService.setAssignmentService(assignmentService);
    referralService.setValidator(validator);
    referralService.setCmsDocumentService(cmsDocumentService);
    referralService.setDrmsDocumentService(drmsDocumentService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setAddressService(addressService);
    referralService.setLongTextService(longTextService);
    referralService.setRiReferral(riReferral);

    referralDomain = new ReferralResourceBuilder().setPrimaryContactStaffPersonId("q1p")
        .setCountySpecificCode("51").build();

    mockMessageBuilder = mock(MessageBuilder.class);
    dateStarted = "2009-12-20";
    timeStarted = "07:00:00";
  }

  private Assignment buildMockAssignment(String startDate, String startTime, String endDate,
      String endTime) {
    return new AssignmentEntityBuilder().setStartDate(startDate).setStartTime(startTime)
        .setEndDate(endDate).setEndTime(endTime).build();
  }

  // find test
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    Referral expected =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Referral/valid.json"), Referral.class);

    // #145948067: the Referral domain bean doesn't store the primary key???
    final String key = "1234567ABC";

    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral(key, expected, "0XA");

    when(referralDao.find(key)).thenReturn(referral);
    Referral found = referralService.find(key);
    assertThat(found, is(expected));
  }

  @Test
  public void testFindReturnsNullWhenNotFound() {
    Response found = referralService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @Test
  public void testDeleteDelegatesToCrudsService() {
    referralService.delete("ABC2345678");
    verify(referralDao, times(1)).delete("ABC2345678");
  }


  @Test
  public void testDeleteReturnsNullWhenNotFound() {
    Response found = referralService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }


  @Test
  public void testDeleteReturnsClass() {
    // delete success
    Referral referralDomain = new ReferralResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "ABC");

    when(referralDao.delete("ABC1234567")).thenReturn(referral);

    Referral returned = referralService.delete("ABC1234567");

    assertEquals(returned.getClass(), Referral.class);
  }

  // update test
  @Test
  public void testUpdateReturnsDomain() {
    Referral expected = new ReferralResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", expected, "ABC");

    when(referralDao.update(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referral);

    when(assignmentService.findReferralFirstAssignment(any(String.class)))
        .thenReturn(buildMockAssignment("2017-01-02", "15:11:45", "2017-01-04", "16:00:00"));

    Object retval = referralService.update("1234567ABC", expected);
    assertEquals(retval.getClass(), Referral.class);

    // R04611 - first assignment start date/time is updated with referral received date/time
    verify(assignmentService, times(1)).update(any(String.class),
        any(gov.ca.cwds.rest.api.domain.cms.Assignment.class));
  }

  @Test(expected = ServiceException.class)
  public void testUpdateThrowsServiceException() {
    Referral referralRequest = new ReferralResourceBuilder().build();

    when(assignmentService.findReferralFirstAssignment(any(String.class)))
        .thenReturn(buildMockAssignment("2017-01-02", "15:11:45", "2017-01-04", "16:00:00"));

    when(referralDao.update(any())).thenThrow(EntityNotFoundException.class);

    referralService.update("ZZZZZZZ0X5", referralRequest);
  }

  // create test
  @Test
  public void referralServiceCreateThrowsEntityExistsException() {
    try {
      Referral referralRequest = new ReferralResourceBuilder().build();

      when(referralDao.create(any())).thenThrow(EntityExistsException.class);

      referralService.create(referralRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Test
  public void testCreateReturnsPostedClass() throws Exception {
    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral toCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "0XA");

    Referral request =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Referral/valid.json"), Referral.class);

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(toCreate);

    Response response = referralService.create(request);
    assertEquals(response.getClass(), PostedReferral.class);
  }

  @Test
  public void testCreateReturnsCorrectEntity() {
    gov.ca.cwds.data.persistence.cms.Referral toCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "0XA");

    Referral request = new Referral(toCreate);
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(toCreate);

    PostedReferral expected = new PostedReferral(toCreate);
    PostedReferral returned = referralService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void testCreateEmptyIDError() {
    try {
      gov.ca.cwds.data.persistence.cms.Referral toCreate =
          new gov.ca.cwds.data.persistence.cms.Referral("", referralDomain, "0XA");

      Referral request = new Referral(toCreate);
      when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
          .thenReturn(toCreate);

      @SuppressWarnings("unused")
      PostedReferral returned = referralService.create(request);

    } catch (ServiceException e) {
      assertEquals("Referral ID cannot be empty", e.getMessage());
    }
  }

  @Test
  public void testCreateNullIDError() {
    try {
      Referral referralDomain = new ReferralResourceBuilder().build();
      gov.ca.cwds.data.persistence.cms.Referral toCreate =
          new gov.ca.cwds.data.persistence.cms.Referral(null, referralDomain, "0XA");

      when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
          .thenReturn(toCreate);

      @SuppressWarnings("unused")
      PostedReferral expected = new PostedReferral(toCreate);

    } catch (ServiceException e) {
      assertEquals("Referral ID cannot be empty", e.getMessage());
    }
  }

  @Test
  public void testCreateBlankIDError() {
    try {
      Referral referralDomain = new ReferralResourceBuilder().build();
      gov.ca.cwds.data.persistence.cms.Referral toCreate =
          new gov.ca.cwds.data.persistence.cms.Referral("   ", referralDomain, "0XA");

      when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
          .thenReturn(toCreate);

      @SuppressWarnings("unused")
      PostedReferral expected = new PostedReferral(toCreate);

    } catch (ServiceException e) {
      assertEquals("Referral ID cannot be empty", e.getMessage());
    }
  }

  @Test
  public void testCreateReturnsCorrectReferralId() {
    gov.ca.cwds.data.persistence.cms.Referral toCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "0XA");

    Referral request = new Referral(toCreate);

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(toCreate);

    PostedReferral returned = referralService.create(request);

    assertThat(returned.getId(), is("1234567ABC"));
  }

  /*
   * Test for checking the new Referral Id generated and lenght is 10
   */
  @Test
  public void createReturnsGeneratedId() {
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenAnswer(invocation -> invocation.getArguments()[0]);

    PostedReferral returned = referralService.create(referralDomain);
    assertEquals(10, returned.getId().length());
    PostedReferral newReturned = referralService.create(referralDomain);
    Assert.assertNotEquals(returned.getId(), newReturned.getId());
  }

  @Test
  public void testCreateLACountyTriggerForReferralCreate() {
    Referral referralDomain = new ReferralResourceBuilder().setPrimaryContactStaffPersonId("BTr")
        .setCountySpecificCode("19").build();
    gov.ca.cwds.data.persistence.cms.Referral toCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "BTr");

    Referral request = new Referral(toCreate);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("19");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(toCreate);

    when(laCountyTrigger.createCountyTrigger(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenAnswer(invocation -> {
          isLaCountyTrigger = true;
          return true;
        });

    referralService.create(request);
    assertThat(isLaCountyTrigger, is(true));

  }

  @Test
  public void testCreateLACountyTriggerForReferralUpdate() {
    Referral referralDomain = new ReferralResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.Referral toUpdate =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "BTr");

    Referral request = new Referral(toUpdate);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("19");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(referralDao.update(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(toUpdate);

    when(laCountyTrigger.createCountyTrigger(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenAnswer(invocation -> {
          isLaCountyTrigger = true;
          return true;
        });

    when(assignmentService.findReferralFirstAssignment(any(String.class)))
        .thenReturn(buildMockAssignment("2017-01-02", "15:11:45", "2017-01-04", "16:00:00"));

    referralService.update("1234567ABC", request);
    assertThat(isLaCountyTrigger, is(true));

    // R04611 - first assignment start date/time is updated with referral received date/time
    verify(assignmentService, times(1)).update(any(String.class),
        any(gov.ca.cwds.rest.api.domain.cms.Assignment.class));
  }

  @Test
  public void shouldSetApprovalCodeToNotSubmittedDefaultValue() {
    Address address = new AddressResourceBuilder().createAddress();
    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().setAddress(address).createScreeningToReferral();

    PostedDrmsDocument drmsDocument = mock(PostedDrmsDocument.class);
    DrmsDocumentService drmsDocumentService = mock(DrmsDocumentService.class);
    when(drmsDocumentService.create(any())).thenReturn(drmsDocument);

    addressService = mock(AddressService.class);
    when(addressService.createAddressFromScreening(eq(screeningToReferral), any()))
        .thenReturn(address);

    longTextService = mock(LongTextService.class);
    LongText longTextEntity = new LongTextEntityBuilder().setId("1234567890").build();
    PostedLongText postedLongText = new PostedLongText(longTextEntity);
    when(longTextService.create(any())).thenReturn(postedLongText);

    referralService = new ReferralService();
    referralService.setReferralDao(referralDao);
    referralService.setNonLaCountyTriggers(nonLACountyTriggers);
    referralService.setLaCountyTrigger(laCountyTrigger);
    referralService.setTriggerTablesDao(triggerTablesDao);
    referralService.setStaffpersonDao(staffpersonDao);
    referralService.setAssignmentService(assignmentService);
    referralService.setValidator(validator);
    referralService.setCmsDocumentService(cmsDocumentService);
    referralService.setDrmsDocumentService(drmsDocumentService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setAddressService(addressService);
    referralService.setLongTextService(longTextService);
    referralService.setRiReferral(riReferral);

    Referral referralCreated = referralService.createReferralWithDefaults(screeningToReferral,
        dateStarted, timeStarted, mockMessageBuilder);

    assertEquals("Expected the approval status type to be 118", (short) 118,
        (short) referralCreated.getApprovalStatusType());
  }

  @Test
  public void shouldReturnReferralIdWhenReferralHasExistingReferralID() {
    String referralId = "1234567890";
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setReferralId(referralId).createScreeningToReferral();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral = new ReferralEntityBuilder().build();
    when(referralDao.find(referralId)).thenReturn(savedReferral);

    referralService = new ReferralService();
    referralService.setReferralDao(referralDao);
    referralService.setNonLaCountyTriggers(nonLACountyTriggers);
    referralService.setLaCountyTrigger(laCountyTrigger);
    referralService.setTriggerTablesDao(triggerTablesDao);
    referralService.setStaffpersonDao(staffpersonDao);
    referralService.setAssignmentService(assignmentService);
    referralService.setValidator(validator);
    referralService.setCmsDocumentService(cmsDocumentService);
    referralService.setDrmsDocumentService(drmsDocumentService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setAddressService(addressService);
    referralService.setLongTextService(longTextService);
    referralService.setRiReferral(riReferral);

    MessageBuilder messageBuilder = new MessageBuilder();
    String response = referralService.createCmsReferralFromScreening(screeningToReferral,
        dateStarted, timeStarted, messageBuilder);

    assertEquals("Expected the reponse to be the referral ID", referralId, response);
    assertEquals("Expected there not to be any error messages", 0,
        messageBuilder.getMessages().size());
  }

  @Test
  public void shouldFailWhenSpecifyingALegacyReferralIdThatDoesNotExist() {
    String nonExistingReferralId = "9999999999";
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setReferralId(nonExistingReferralId).createScreeningToReferral();

    referralService = new ReferralService();
    referralService.setReferralDao(referralDao);
    referralService.setNonLaCountyTriggers(nonLACountyTriggers);
    referralService.setLaCountyTrigger(laCountyTrigger);
    referralService.setTriggerTablesDao(triggerTablesDao);
    referralService.setStaffpersonDao(staffpersonDao);
    referralService.setAssignmentService(assignmentService);
    referralService.setValidator(validator);
    referralService.setCmsDocumentService(cmsDocumentService);
    referralService.setDrmsDocumentService(drmsDocumentService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setAddressService(addressService);
    referralService.setLongTextService(longTextService);
    referralService.setRiReferral(riReferral);

    referralService.createCmsReferralFromScreening(screeningToReferral, dateStarted, timeStarted,
        mockMessageBuilder);

    verify(mockMessageBuilder).addMessageAndLog(
        eq("Legacy Id does not correspond to an existing CMS/CWS Referral"), any(), any());
  }

  @Test
  public void shouldFailSavingWhenReferralHasInvalidDateTimeFormat() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setassigneeStaffId("q1p").setIncidentCounty("51").createScreeningToReferral();

    PostedDrmsDocument drmsDocument = mock(PostedDrmsDocument.class);
    DrmsDocumentService drmsDocumentService = mock(DrmsDocumentService.class);
    when(drmsDocumentService.create(any())).thenReturn(drmsDocument);

    Address address = new AddressResourceBuilder().createAddress();
    addressService = mock(AddressService.class);
    when(addressService.createAddressFromScreening(eq(screeningToReferral), any()))
        .thenReturn(address);

    longTextService = mock(LongTextService.class);
    LongText longTextEntity = new LongTextEntityBuilder().setId("1234567890").build();
    PostedLongText postedLongText = new PostedLongText(longTextEntity);
    when(longTextService.create(any())).thenReturn(postedLongText);

    referralService = new ReferralService();
    referralService.setReferralDao(referralDao);
    referralService.setNonLaCountyTriggers(nonLACountyTriggers);
    referralService.setLaCountyTrigger(laCountyTrigger);
    referralService.setTriggerTablesDao(triggerTablesDao);
    referralService.setStaffpersonDao(staffpersonDao);
    referralService.setAssignmentService(assignmentService);
    referralService.setValidator(validator);
    referralService.setCmsDocumentService(cmsDocumentService);
    referralService.setDrmsDocumentService(drmsDocumentService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setAddressService(addressService);
    referralService.setLongTextService(longTextService);
    referralService.setRiReferral(riReferral);

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new ReferralEntityBuilder().setId("1234567890").setPrimaryContactStaffPersonId("q1p")
            .setCountySpecificCode("51").build();
    when(referralDao.create(any())).thenReturn(savedReferral);

    MessageBuilder messageBuilder = new MessageBuilder();

    referralService.createCmsReferralFromScreening(screeningToReferral, "01-30-2019", timeStarted,
        messageBuilder);

    assertEquals("Expected a single error message", 1, messageBuilder.getMessages().size());
    assertEquals("ExpectedDate Error message", "receivedDate must be in the format of yyyy-MM-dd",
        messageBuilder.getMessages().get(0).getMessage());
  }

  @Test
  public void testAllegesAbuseOccurredAtAddressId() {
    String legacyId = "1234567890";
    Address address = new AddressResourceBuilder().setLegacyId(legacyId).createAddress();
    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().setassigneeStaffId("q1p").setIncidentCounty("51")
            .setAddress(address).createScreeningToReferral();

    PostedDrmsDocument drmsDocument = mock(PostedDrmsDocument.class);
    DrmsDocumentService drmsDocumentService = mock(DrmsDocumentService.class);
    when(drmsDocumentService.create(any())).thenReturn(drmsDocument);

    addressService = mock(AddressService.class);
    when(addressService.createAddressFromScreening(eq(screeningToReferral), any()))
        .thenReturn(address);

    longTextService = mock(LongTextService.class);
    LongText longTextEntity = new LongTextEntityBuilder().setId("1234567890").build();
    PostedLongText postedLongText = new PostedLongText(longTextEntity);
    when(longTextService.create(any())).thenReturn(postedLongText);

    referralService = new ReferralService();
    referralService.setReferralDao(referralDao);
    referralService.setNonLaCountyTriggers(nonLACountyTriggers);
    referralService.setLaCountyTrigger(laCountyTrigger);
    referralService.setTriggerTablesDao(triggerTablesDao);
    referralService.setStaffpersonDao(staffpersonDao);
    referralService.setAssignmentService(assignmentService);
    referralService.setValidator(validator);
    referralService.setCmsDocumentService(cmsDocumentService);
    referralService.setDrmsDocumentService(drmsDocumentService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setAddressService(addressService);
    referralService.setLongTextService(longTextService);
    referralService.setRiReferral(riReferral);

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new ReferralEntityBuilder().setId("1234567890").setPrimaryContactStaffPersonId("q1p")
            .setCountySpecificCode("51").build();
    when(referralDao.create(any())).thenReturn(savedReferral);

    referralService.createCmsReferralFromScreening(screeningToReferral, dateStarted, timeStarted,
        mockMessageBuilder);

    ArgumentCaptor<gov.ca.cwds.data.persistence.cms.Referral> referralCaptor =
        ArgumentCaptor.forClass(gov.ca.cwds.data.persistence.cms.Referral.class);
    verify(referralDao).create(referralCaptor.capture());
    gov.ca.cwds.data.persistence.cms.Referral capturedReferral = referralCaptor.getValue();
    assertEquals("Expected referal to have been save allegeges Abuse Occured At to be the Address "
        + "legacy id", capturedReferral.getAllegesAbuseOccurredAtAddressId(), legacyId);
  }


  @Test(expected = ServiceException.class)
  public void shouldThrowErrorIfStaffPersonIdIsNotFound() {
    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral toCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "0XA");

    Referral request = new Referral(toCreate);

    referralService.create(request);

  }

  @Test(expected = ServiceException.class)
  public void shouldThrowErrorIfReferralIsNotSavedSuccessfully() {
    when(referralDao.create(any())).thenReturn(null);
    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral toCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "0XA");

    Referral request = new Referral(toCreate);

    referralService.create(request);
  }

  @Test(expected = ServiceException.class)
  public void testR04611shouldThrowErrorIfReferralStartDateTimeNotValid() {
    Referral domainReferral = new ReferralResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", domainReferral, "ABC");

    when(assignmentService.findReferralFirstAssignment(any(String.class)))
        .thenReturn(buildMockAssignment("1999-01-10", "15:11:00", "1999-01-15", "16:10:00"));

    when(referralDao.update(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referral);

    referralService.update("1234567ABC", domainReferral);
  }

  // R04611 - the Referral Start date/time can not exceed the End Date and can not equal or exceed
  // End Time of the first assignment
  @Test
  public void testR04611ReferralValidation() {
    Referral domainReferral = new ReferralResourceBuilder().build();

    // Referral earlier date
    Assignment firstAssignment =
        buildMockAssignment("2017-01-02", "15:11:45", "2017-01-04", "16:00:00");
    boolean isValid = referralService.isReferralStartDateTimeValid(domainReferral, firstAssignment);
    assertThat(isValid, is(true));

    // Referral same date, earlier time
    firstAssignment =
        buildMockAssignment(DEFAULT_REFERRAL_RECEIVED_DATE, "17:15:45", "2017-01-04", "16:00:00");
    isValid = referralService.isReferralStartDateTimeValid(domainReferral, firstAssignment);
    assertThat(isValid, is(true));

    // Referral same date, same time
    firstAssignment = buildMockAssignment(DEFAULT_REFERRAL_RECEIVED_DATE, "00:00:00",
        DEFAULT_REFERRAL_RECEIVED_DATE, DEFAULT_REFERRAL_RECEIVED_TIME);
    isValid = referralService.isReferralStartDateTimeValid(domainReferral, firstAssignment);
    assertThat(isValid, is(false));

    // Referral later date
    firstAssignment = buildMockAssignment("1999-01-10", "15:11:45", "1999-01-15", "16:00:00");
    isValid = referralService.isReferralStartDateTimeValid(domainReferral, firstAssignment);
    assertThat(isValid, is(false));
  }

  @Test
  public void shouldReturnCreateDefaultSreenerNarrativeForNewReferral() {
    String referralId = "";
    StaffPerson staffPerson =
        new StaffPersonEntityBuilder().setId("0X5").setCountyCode("99").build();
    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().setReferralId(referralId).setIncidentCounty("99")
            .setAssigneeStaffId("0X5").createScreeningToReferral();

    gov.ca.cwds.data.persistence.cms.Referral referral =
        new ReferralEntityBuilder().setId("ABC0987654").build();
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referral);

    MessageBuilder messageBuilder = new MessageBuilder();
    LongText longTextEntity = new LongTextEntityBuilder().setId("1234567890").build();
    PostedLongText postedLongText = new PostedLongText(longTextEntity);
    when(longTextService.create(any())).thenReturn(postedLongText);

    Address address = new AddressResourceBuilder().createAddress();
    when(addressService.createAddressFromScreening(eq(screeningToReferral), any()))
        .thenReturn(address);

    DrmsDocumentTemplate drmsDocument = new DrmsDocumentTemplateResourceBuilder().build();
    when(drmsDocumentTemplateService.findScreenerNarrativeTemplateNs(any(Short.class)))
        .thenReturn(drmsDocument);

    CmsDocument cmsDocument = new CmsDocumentResourceBuilder().build();
    when(cmsDocumentService.find(any(String.class))).thenReturn(cmsDocument);
    when(cmsDocumentService.create(any())).thenReturn(cmsDocument);

    DrmsDocument document = new DrmsDocumentResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.DrmsDocument persistedDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("1234567890", document, "0X5",
            new Date());
    PostedDrmsDocument postedId = new PostedDrmsDocument(persistedDocument);
    when(drmsDocumentService.create(any(DrmsDocument.class))).thenReturn(postedId);
    referralService.createCmsReferralFromScreening(screeningToReferral, "01-30-2019", timeStarted,
        messageBuilder);
    verify(drmsDocumentService, times(1)).create(any());
  }

  @Test
  public void shouldSetScreenerNoteTextWhenSafeAlertInformationProvided() throws Exception {
    String referralId = "";

    // mock the create of the Referral row
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new ReferralEntityBuilder().setId("1234567ABC").build();
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referral);

    // mock the three dummy DRMS document generation
    DrmsDocument document = new DrmsDocumentResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.DrmsDocument persistedDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("2345678ABC", document, "0X5",
            new Date());
    PostedDrmsDocument postedId = new PostedDrmsDocument(persistedDocument);
    when(drmsDocumentService.create(any(DrmsDocument.class))).thenReturn(postedId);

    // mock the longText service
    LongText longTextEntity = new LongTextEntityBuilder().setId("3456789ABC").build();
    PostedLongText postedLongText = new PostedLongText(longTextEntity);
    when(longTextService.create(any())).thenReturn(postedLongText);

    // create a screening to referral with safety alert information
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setReferralId(referralId).setIncidentCounty("99").setAssigneeStaffId("0X5")
        .setSafetyAlertInformationn("test safety alert information to screener alerts")
        .createScreeningToReferral();

    // mock the Address service
    Address address = new AddressResourceBuilder().createAddress();
    when(addressService.createAddressFromScreening(eq(screeningToReferral), any()))
        .thenReturn(address);

    referralService = new ReferralService();
    referralService.setReferralDao(referralDao);
    referralService.setNonLaCountyTriggers(nonLACountyTriggers);
    referralService.setLaCountyTrigger(laCountyTrigger);
    referralService.setTriggerTablesDao(triggerTablesDao);
    referralService.setStaffpersonDao(staffpersonDao);
    referralService.setAssignmentService(assignmentService);
    referralService.setValidator(validator);
    referralService.setCmsDocumentService(cmsDocumentService);
    referralService.setDrmsDocumentService(drmsDocumentService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setAddressService(addressService);
    referralService.setLongTextService(longTextService);
    referralService.setRiReferral(riReferral);

    Referral createdReferral = referralService.createReferralWithDefaults(screeningToReferral,
        dateStarted, timeStarted, mockMessageBuilder);

    assertEquals("Expected screener alert", "3456789ABC", createdReferral.getScreenerNoteText());
  }

  @Test
  public void shouldNotSetScreenerNoteTextWhenNoSafeAlertInformationProvided() throws Exception {
    String referralId = "";

    // mock the create of the Referral row
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new ReferralEntityBuilder().setId("1234567ABC").build();
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referral);

    // mock the three dummy DRMS document generation
    DrmsDocument document = new DrmsDocumentResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.DrmsDocument persistedDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("2345678ABC", document, "0X5",
            new Date());
    PostedDrmsDocument postedId = new PostedDrmsDocument(persistedDocument);
    when(drmsDocumentService.create(any(DrmsDocument.class))).thenReturn(postedId);

    // mock the longText service
    LongText longTextEntity = new LongTextEntityBuilder().setId("3456789ABC").build();
    PostedLongText postedLongText = new PostedLongText(longTextEntity);
    when(longTextService.create(any())).thenReturn(postedLongText);

    // create a screening to referral with safety alert information
    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().setReferralId(referralId).setIncidentCounty("99")
            .setAssigneeStaffId("0X5").setSafetyAlertInformationn("").createScreeningToReferral();

    // mock the Address service
    Address address = new AddressResourceBuilder().createAddress();
    when(addressService.createAddressFromScreening(eq(screeningToReferral), any()))
        .thenReturn(address);

    referralService = new ReferralService();
    referralService.setReferralDao(referralDao);
    referralService.setNonLaCountyTriggers(nonLACountyTriggers);
    referralService.setLaCountyTrigger(laCountyTrigger);
    referralService.setTriggerTablesDao(triggerTablesDao);
    referralService.setStaffpersonDao(staffpersonDao);
    referralService.setAssignmentService(assignmentService);
    referralService.setValidator(validator);
    referralService.setCmsDocumentService(cmsDocumentService);
    referralService.setDrmsDocumentService(drmsDocumentService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setAddressService(addressService);
    referralService.setLongTextService(longTextService);
    referralService.setRiReferral(riReferral);

    Referral createdReferral = referralService.createReferralWithDefaults(screeningToReferral,
        dateStarted, timeStarted, mockMessageBuilder);

    assertEquals("Expected no screener alert", null, createdReferral.getScreenerNoteText());
  }

  @Test
  public void shouldNotSetScreenerNoteTextWhenReportNarrativeProvided() throws Exception {
    String referralId = "";

    // mock the create of the Referral row
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new ReferralEntityBuilder().setId("1234567ABC").build();
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referral);

    // mock the three dummy DRMS document generation
    DrmsDocument document = new DrmsDocumentResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.DrmsDocument persistedDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("2345678ABC", document, "0X5",
            new Date());
    PostedDrmsDocument postedId = new PostedDrmsDocument(persistedDocument);
    when(drmsDocumentService.create(any(DrmsDocument.class))).thenReturn(postedId);

    // mock the longText service
    LongText longTextEntity = new LongTextEntityBuilder().setId("3456789ABC").build();
    PostedLongText postedLongText = new PostedLongText(longTextEntity);
    when(longTextService.create(any())).thenReturn(postedLongText);

    // create a screening to referral with safety alert information
    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().setReferralId(referralId).setIncidentCounty("99")
            .setAssigneeStaffId("0X5").setSafetyAlertInformationn("")
            .setReportNarrative("test the report narrative").createScreeningToReferral();

    // mock the Address service
    Address address = new AddressResourceBuilder().createAddress();
    when(addressService.createAddressFromScreening(eq(screeningToReferral), any()))
        .thenReturn(address);

    referralService = new ReferralService();
    referralService.setReferralDao(referralDao);
    referralService.setNonLaCountyTriggers(nonLACountyTriggers);
    referralService.setLaCountyTrigger(laCountyTrigger);
    referralService.setTriggerTablesDao(triggerTablesDao);
    referralService.setStaffpersonDao(staffpersonDao);
    referralService.setAssignmentService(assignmentService);
    referralService.setValidator(validator);
    referralService.setCmsDocumentService(cmsDocumentService);
    referralService.setDrmsDocumentService(drmsDocumentService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setDrmsDocumentTemplateService(drmsDocumentTemplateService);
    referralService.setAddressService(addressService);
    referralService.setLongTextService(longTextService);
    referralService.setRiReferral(riReferral);

    Referral createdReferral = referralService.createReferralWithDefaults(screeningToReferral,
        dateStarted, timeStarted, mockMessageBuilder);

    assertEquals("Expected no screener alert", null, createdReferral.getScreenerNoteText());
  }
}
