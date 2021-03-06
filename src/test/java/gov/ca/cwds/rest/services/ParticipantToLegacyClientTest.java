package gov.ca.cwds.rest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;

import gov.ca.cwds.cms.data.access.service.impl.CsecHistoryService;
import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.legacy.cms.dao.SexualExploitationTypeDao;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.SexualExploitationType;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.CsecBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ReporterResourceBuilder;
import gov.ca.cwds.fixture.SafelySurrenderedBabiesBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.fixture.SpecialProjectReferralEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ParticipantTest;
import gov.ca.cwds.rest.api.domain.Role;
import gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.core.FerbConstants;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientScpEthnicityService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.SpecialProjectReferralService;

/**
 * @author CWDS API Team
 */
public class ParticipantToLegacyClientTest {

  private ParticipantToLegacyClient participantToLegacyClient;

  private Participant defaultVictim;
  private Participant defaultReporter;
  private Participant defaultMandatedReporter;
  private Participant defaultPerpetrator;

  ClientService clientService;
  ReferralClientService referralClientService;
  ChildClientService childClientService;
  AddressService addressService;
  ClientAddressService clientAddressService;
  private ClientScpEthnicityService clientScpEthnicityService;

  private static final String VICTIM_WHILE_ABSENT_FROM_PLACEMENT = "6871";
  private DateTime lastUpdateDate;
  private DateTime modifiedLastUpdateDate;
  private DateTime updatedTime;
  private String dateStarted;
  private String timeStarted;
  private String referralId;
  private MessageBuilder messageBuilder;
  private CaseDao caseDao;
  private ReferralClientDao referralClientDao;

  private Validator validator;
  private Client updatedClient = null;

  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();
  TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private String savedAddressId;

  private SexualExploitationTypeDao sexualExploitationTypeDao;
  private CsecHistoryService csecHistoryService;
  private SpecialProjectReferralService specialProjectReferralService;

  /**
   *
   */
  @Before
  public void setup() {
    new TestingRequestExecutionContext("0X5");
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor();
    legacyDescriptor.setLastUpdated(DateTime.now());
    defaultVictim =
        new ParticipantResourceBuilder().setLegacyDescriptor(null).createVictimParticipant();
    defaultReporter = new ParticipantResourceBuilder()
        .setRoles((new HashSet<>(Arrays.asList("Mandated Reporter")))).createReporterParticipant();
    defaultMandatedReporter = new ParticipantResourceBuilder().createReporterParticipant();
    defaultPerpetrator =
        new ParticipantResourceBuilder().setLegacyDescriptor(null).createPerpParticipant();

    clientService = mock(ClientService.class);
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient =
        new ClientEntityBuilder().setId("1234567ABC").build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    when(clientService.create(any())).thenReturn(savedClient);

    referralClientService = mock(ReferralClientService.class);

    // TODO: ReporterEntityBuilder Requires name change, and move rest of code to builder
    Reporter reporter = new ReporterResourceBuilder().setReferralId("1234567ABC").build();
    gov.ca.cwds.data.persistence.cms.Reporter savedEntityReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporter, "1234567ABC", new Date());
    PostedReporter savedReporter = new PostedReporter(savedEntityReporter);
    ReporterService reporterService = mock(ReporterService.class);
    when(reporterService.create(any())).thenReturn(savedReporter);

    childClientService = mock(ChildClientService.class);
    ChildClient childClient = mock(ChildClient.class);
    when(childClientService.create(any())).thenReturn(childClient);

    addressService = mock(AddressService.class);
    PostedAddress postedAddress = mock(PostedAddress.class);
    savedAddressId = "ZXCVBNMKJH";
    when(postedAddress.getExistingAddressId()).thenReturn(savedAddressId);
    when(addressService.create(any())).thenReturn(postedAddress);

    clientAddressService = mock(ClientAddressService.class);
    clientScpEthnicityService = mock(ClientScpEthnicityService.class);
    caseDao = mock(CaseDao.class);
    referralClientDao = mock(ReferralClientDao.class);

    messageBuilder = new MessageBuilder();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    lastUpdateDate = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        .parseDateTime("2010-03-14T13:33:12.456-0700");
    modifiedLastUpdateDate = DateTimeFormat.forPattern("yyyy-MM-dd-HH.mm.ss.SSS")
        .parseDateTime("2010-03-14-13.33.12.456");
    updatedTime = lastUpdateDate.plusHours(2);
    dateStarted = "2017-10-21";
    timeStarted = "00:00:00";
    referralId = "1234567890";

    sexualExploitationTypeDao = mock(SexualExploitationTypeDao.class);
    SexualExploitationType sexualExploitationType = new SexualExploitationType();
    sexualExploitationType.setFkMeta("testFkMeta");
    sexualExploitationType.setShortDescription("testShortDescription");
    when(sexualExploitationTypeDao.find((short) 6867)).thenReturn(sexualExploitationType);

    csecHistoryService = mock(CsecHistoryService.class);
    specialProjectReferralService = mock(SpecialProjectReferralService.class);

    specialProjectReferralService = mock(SpecialProjectReferralService.class);
    gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral specialProjectReferral =
        new SpecialProjectReferralEntityBuilder().build();
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral postedSpecialProjectReferral =
        new gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral(specialProjectReferral);
    when(specialProjectReferralService.saveCsecSpecialProjectReferral(any(), any(), any(), any()))
        .thenReturn(postedSpecialProjectReferral);

    participantToLegacyClient = new ParticipantToLegacyClient(clientService, referralClientService,
        reporterService, childClientService, clientAddressService, validator,
        clientScpEthnicityService, caseDao, referralClientDao);
    participantToLegacyClient.setSexualExploitationTypeDao(sexualExploitationTypeDao);
    participantToLegacyClient.setCsecHistoryService(csecHistoryService);
    participantToLegacyClient.setSpecialProjectReferralService(specialProjectReferralService);
    participantToLegacyClient.setDroolsService(new DroolsService());
  }

  @Test
  public void testCsecIsUpdated() {
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setReportType(FerbConstants.ReportType.CSEC).createScreeningToReferral();
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient =
        new ClientEntityBuilder().setId("1234567ABC").build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    when(clientService.update(any(), any())).thenReturn(savedClient);
    when(clientService.find(any())).thenReturn(savedClient);

    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    verify(csecHistoryService).updateCsecHistoriesByClientId(argThat(new ArgumentMatcher<String>() {
      @Override
      public boolean matches(String clientId) {
        assertEquals("1234567ABC", clientId);
        return true;
      }
    }), argThat(new ArgumentMatcher<List<CsecHistory>>() {
      @Override
      public boolean matches(List<CsecHistory> csecs) {
        assertNotNull(csecs);
        assertEquals(1, csecs.size());
        CsecHistory csecHistory = csecs.get(0);
        assertNotNull(csecHistory);
        assertEquals("1234567ABC", csecHistory.getChildClient());
        assertEquals(LocalDate.parse("2018-05-28"), csecHistory.getStartDate());
        assertEquals(LocalDate.parse("2018-05-29"), csecHistory.getEndDate());
        SexualExploitationType sexualExploitationType = csecHistory.getSexualExploitationType();
        assertNotNull(sexualExploitationType);
        assertEquals("testFkMeta", sexualExploitationType.getFkMeta());
        assertEquals("testShortDescription", sexualExploitationType.getShortDescription());
        return true;
      }
    }));
  }

  @Test
  public void testCsecIsNotUpdatedForNotSupportedReportType() {
    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setReportType("").createScreeningToReferral();
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient =
        new ClientEntityBuilder().setId("1234567ABC").build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    when(clientService.update(any(), any())).thenReturn(savedClient);
    when(clientService.find(any())).thenReturn(savedClient);

    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    verify(csecHistoryService, times(0)).updateCsecHistoriesByClientId(any(), any());
  }

  @Test
  public void testCsecIsNull() {
    Participant victimParticipant = new ParticipantResourceBuilder().createVictimParticipant();
    victimParticipant.setCsecs(null);

    checkCsecEmptyMessage(victimParticipant);
  }

  @Test
  public void testCsecIsEmpty() {
    Participant victimParticipant = new ParticipantResourceBuilder().createVictimParticipant();
    victimParticipant.setCsecs(new ArrayList<>());

    checkCsecEmptyMessage(victimParticipant);
  }

  private void checkCsecEmptyMessage(Participant victimParticipant) {
    Set<Participant> participants =
        new HashSet<>(Arrays.asList(defaultReporter, victimParticipant));
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient =
        new ClientEntityBuilder().setId("1234567ABC").build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    when(clientService.update(any(), any())).thenReturn(savedClient);
    when(clientService.find(any())).thenReturn(savedClient);

    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setReportType(FerbConstants.ReportType.CSEC)
            .setParticipants(participants).createScreeningToReferral();
    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertTrue(messageBuilder.getMessages().stream().map(message -> message.getMessage())
        .collect(Collectors.toList()).contains("CSEC data is empty"));
  }

  @Test
  public void testCsecStartDateIsNotFound() {
    Participant victimParticipant = new ParticipantResourceBuilder().createVictimParticipant();
    victimParticipant.getCsecs().get(0).setStartDate(null);
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient =
        new ClientEntityBuilder().setId("1234567ABC").build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    when(clientService.update(any(), any())).thenReturn(savedClient);
    when(clientService.find(any())).thenReturn(savedClient);


    Set<Participant> participants =
        new HashSet<>(Arrays.asList(defaultReporter, victimParticipant));

    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setReportType(FerbConstants.ReportType.CSEC)
            .setParticipants(participants).createScreeningToReferral();
    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertTrue(messageBuilder.getMessages().stream().map(message -> message.getMessage())
        .collect(Collectors.toList()).contains("CSEC start date is not found for code: 6867"));
  }

  @Test
  public void shouldFailWhenDuplicateCsecCodeIds() {
    Participant victimParticipant = new ParticipantResourceBuilder().createVictimParticipant();
    victimParticipant.getCsecs().add(new CsecBuilder().build());

    Set<Participant> participants =
        new HashSet<>(Arrays.asList(defaultReporter, victimParticipant));

    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setReportType(FerbConstants.ReportType.CSEC)
            .setParticipants(participants).createScreeningToReferral();
    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    when(clientService.find(any())).thenReturn(foundClient);
    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertTrue(messageBuilder.getMessages().stream().map(message -> message.getMessage())
        .collect(Collectors.toList()).contains("CSEC duplication for code: 6867"));
  }

  @Test
  public void testCsecCodeIdIsNotFound() {
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient =
        new ClientEntityBuilder().setId("1234567ABC").build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    when(clientService.update(any(), any())).thenReturn(savedClient);
    when(clientService.find(any())).thenReturn(savedClient);
    Participant victimParticipant = new ParticipantResourceBuilder().createVictimParticipant();
    victimParticipant.getCsecs().get(0).setCsecCodeId(null);

    Set<Participant> participants =
        new HashSet<>(Arrays.asList(defaultReporter, victimParticipant));

    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setReportType(FerbConstants.ReportType.CSEC)
            .setParticipants(participants).createScreeningToReferral();
    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertTrue(messageBuilder.getMessages().stream().map(message -> message.getMessage())
        .collect(Collectors.toList())
        .contains("There is no CSEC code id provided for client with id: 1234567ABC"));
  }

  @Test
  public void testCsecTypeIsNotFound() {
    Participant victimParticipant = new ParticipantResourceBuilder().createVictimParticipant();
    victimParticipant.getCsecs().get(0).setCsecCodeId("9999");

    Set<Participant> participants =
        new HashSet<>(Arrays.asList(defaultReporter, victimParticipant));

    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setReportType(FerbConstants.ReportType.CSEC)
            .setParticipants(participants).createScreeningToReferral();
    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    when(clientService.find(any())).thenReturn(foundClient);
    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertTrue(messageBuilder.getMessages().stream().map(message -> message.getMessage())
        .collect(Collectors.toList())
        .contains("Legacy Id on CSEC does not correspond to an existing CMS/CWS CSEC History row"));
  }

  /**
   * Test the drools implementation of "commercial-sexual-exploitation-agenda" ParticipantToLegacy
   * class
   */
  @Test
  public void shouldFailIfCsecTypeVictimWhileAbsentFromPlacementWithNullEndDate() {
    Csec csec = new CsecBuilder().setCsecCodeId(VICTIM_WHILE_ABSENT_FROM_PLACEMENT).setEndDate(null)
        .build();
    List<Csec> csecs = new ArrayList<Csec>();
    csecs.add(csec);
    Participant victimParticipant = new ParticipantResourceBuilder().createVictimParticipant();
    victimParticipant.setCsecs(csecs);

    Set<Participant> participants =
        new HashSet<>(Arrays.asList(defaultReporter, victimParticipant));

    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setReportType(FerbConstants.ReportType.CSEC)
            .setParticipants(participants).createScreeningToReferral();
    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    SexualExploitationType sexualExploitationType = new SexualExploitationType();
    sexualExploitationType.setSystemId((short) 6871);
    sexualExploitationType.setFkMeta("CSEC_TPC");
    sexualExploitationType.setShortDescription("Victim while Absent from Placement");

    when(clientService.find(any())).thenReturn(foundClient);
    when(sexualExploitationTypeDao.find((short) 6871)).thenReturn(sexualExploitationType);
    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertTrue(messageBuilder.getMessages().stream().map(message -> message.getMessage())
        .collect(Collectors.toList())
        .contains("CSEC Victim while Absent from Placement requires an end date"));
  }

  /**
   * Test the drools implementation of "commercial-sexual-exploitation-agenda" in
   * ParticipantToLegacy class
   */
  @Test
  public void shouldFailWhenCsecEndDateBeforeStartDate() {
    LocalDate startDate = LocalDate.parse("2018-09-29");
    LocalDate endDate = LocalDate.parse("2018-09-28");

    Csec csec = new CsecBuilder().setCsecCodeId(VICTIM_WHILE_ABSENT_FROM_PLACEMENT)
        .setEndDate(endDate).setStartDate(startDate).build();
    List<Csec> csecs = new ArrayList<Csec>();
    csecs.add(csec);
    Participant victimParticipant = new ParticipantResourceBuilder().createVictimParticipant();
    victimParticipant.setCsecs(csecs);

    Set<Participant> participants =
        new HashSet<>(Arrays.asList(defaultReporter, victimParticipant));

    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setReportType(FerbConstants.ReportType.CSEC)
            .setParticipants(participants).createScreeningToReferral();
    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    SexualExploitationType sexualExploitationType = new SexualExploitationType();
    sexualExploitationType.setSystemId((short) 6871);
    sexualExploitationType.setFkMeta("CSEC_TPC");
    sexualExploitationType.setShortDescription("Victim while Absent from Placement");

    when(clientService.find(any())).thenReturn(foundClient);
    when(sexualExploitationTypeDao.find((short) 6871)).thenReturn(sexualExploitationType);
    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertTrue(messageBuilder.getMessages().stream().map(message -> message.getMessage())
        .collect(Collectors.toList())
        .contains("CSEC End Date must be greater than or equal to CSEC Start Date"));
  }

  @Test
  public void shouldFailWhenReporterHasIncompatiableRoles_MandatedNonMandatedFail()
      throws Exception {
    Participant mandatedNonMandatedReporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Mandated Reporter", "Non-mandated Reporter")))
        .createParticipant();
    Set<Participant> participants =
        new HashSet<>(Arrays.asList(mandatedNonMandatedReporter, defaultVictim));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    when(clientService.find(any())).thenReturn(foundClient);

    participantToLegacyClient.saveParticipants(screeningToReferral, dateStarted, timeStarted,
        referralId, messageBuilder);

    assertEquals("Expected only one error to have been recorded",
        messageBuilder.getMessages().size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Participant contains incompatible roles";
    assertEquals("Expected Incompatible participant Role message to have been recorded",
        expectedErrorMessage, message);
  }

  @Test
  // R - 00831
  public void shouldFailCreateWhenReporterRolesAreIncompatable_AnonymousSelfReporter()
      throws Exception {
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient =
        new ClientEntityBuilder().setId("1234567ABC").build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    when(clientService.update(any(), any())).thenReturn(savedClient);
    when(clientService.find(any())).thenReturn(savedClient);
    Participant anonymousSelfReporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Anonymous Reporter", "Self Reported")))
        .createParticipant();
    Set<Participant> participants =
        new HashSet<>(Arrays.asList(anonymousSelfReporter, defaultVictim));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    participantToLegacyClient.saveParticipants(screeningToReferral, dateStarted, timeStarted,
        referralId, messageBuilder);

    assertEquals("Expected only one error to have been recorded",
        messageBuilder.getMessages().size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Participant contains incompatible roles";
    assertEquals("Expected Incompatible participant Role message to have been recorded",
        expectedErrorMessage, message);
  }

  @Test
  public void shouldFailWhenVictimHasIncompatiableRoles_AnonymousVictim() throws Exception {
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient =
        new ClientEntityBuilder().setId("1234567ABC").build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    when(clientService.update(any(), any())).thenReturn(savedClient);
    when(clientService.find(any())).thenReturn(savedClient);
    Participant reporterVictim = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Anonymous Reporter", "Victim"))).setLegacyId("")
        .setLegacyDescriptor(null).createParticipant();
    Set<Participant> participants =
        new HashSet<>(Arrays.asList(reporterVictim, defaultPerpetrator));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    participantToLegacyClient.saveParticipants(screeningToReferral, dateStarted, timeStarted,
        referralId, messageBuilder);
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
    }

    assertEquals("Expected only one error to have been recorded",
        messageBuilder.getMessages().size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Participant contains incompatible roles";
    assertEquals("Expected Incompatible participant Role message to have been recorded",
        expectedErrorMessage, message);
  }

  @Test
  public void shouldNotUpdateClientWhenClientRecordHasBeenModifiedInLegacyDb() throws Exception {
    DateTime modifiedLastUpdateDate = DateTimeFormat.forPattern("yyyy-MM-dd-HH.mm.ss.SSS")
        .parseDateTime("2000-01-27-15.34.55.123");
    String victimClientLegacyId = "ABC123DSAF";

    LegacyDescriptor descriptor = new LegacyDescriptor("ABC123DSAF", null, lastUpdateDate,
        LegacyTable.CLIENT.getName(), null);
    Participant victim =
        new ParticipantResourceBuilder().setLegacyDescriptor(descriptor).createParticipant();

    Set<Participant> participants =
        new HashSet<>(Arrays.asList(victim, defaultReporter, defaultPerpetrator));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    Client updatedClient = new ClientResourceBuilder().setBirthDate(null).build();
    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    PostedClient createdClient = mock(PostedClient.class);
    when(createdClient.getId()).thenReturn("LEGACYIDXX");

    when(clientService.find(eq(victimClientLegacyId))).thenReturn(foundClient);
    when(clientService.create(any())).thenReturn(createdClient);
    when(clientService.find(eq("LEGACYIDXX"))).thenReturn(foundClient);
    when(clientService.update(eq(victimClientLegacyId), any())).thenReturn(updatedClient);

    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertEquals("Expected only one error to have been recorded",
        messageBuilder.getMessages().size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage =
        "Unable to update client John Smith. Client has been modified by another process.";
    assertEquals("Expected client previously modified message to have been recorded",
        expectedErrorMessage, message);
  }

  @Test
  public void shouldFailWhenVictimIsIncompatiableRoles_VictimPerpetrator() throws Exception {
    Participant perpVictim = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator", "Victim"))).createParticipant();
    Set<Participant> participants =
        new HashSet<>(Arrays.asList(perpVictim, defaultMandatedReporter));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    participantToLegacyClient.saveParticipants(screeningToReferral, dateStarted, timeStarted,
        referralId, messageBuilder);

    assertEquals("Expected only one error to have been recorded",
        messageBuilder.getMessages().size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Participant contains incompatible roles";
    assertEquals("Expected Incompatible participant Role message to have been recorded",
        expectedErrorMessage, message);
  }

  @Test
  public void shouldFailWhenReporterHasIncompatiableRoles_AnonymousReporterFail() throws Exception {
    Participant anonymousNonMandatedReporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Anonymous Reporter", "Non-mandated Reporter")))
        .createParticipant();
    Set<Participant> participants =
        new HashSet<>(Arrays.asList(anonymousNonMandatedReporter, defaultVictim));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    when(clientService.find(any())).thenReturn(foundClient);

    participantToLegacyClient.saveParticipants(screeningToReferral, dateStarted, timeStarted,
        referralId, messageBuilder);

    assertEquals("Expected only one error to have been recorded",
        messageBuilder.getMessages().size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Participant contains incompatible roles";
    assertEquals("Expected Incompatible participant Role message to have been recorded",
        expectedErrorMessage, message);
  }

  @Test
  public void testClientDoesNotExistFail() throws Exception {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("098UijH1gf", null,
        new DateTime("2018-06-11T18:47:07.524Z"), LegacyTable.CLIENT.getName(), null);
    defaultReporter = new ParticipantResourceBuilder()
        .setRoles((new HashSet<>(Arrays.asList("Mandated Reporter")))).createReporterParticipant();
    defaultVictim = new ParticipantResourceBuilder().setLegacyDescriptor(legacyDescriptor)
        .createPerpParticipant();
    String badLegacyId = "IUKNOWNIDI";

    LegacyDescriptor descriptor =
        new LegacyDescriptor("IUKNOWNIDI", "", lastUpdateDate, "CLIENT_T", "");
    Participant perp = new ParticipantResourceBuilder().setLegacyDescriptor(descriptor)
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    Set<Participant> participants =
        new HashSet<>(Arrays.asList(perp, defaultReporter, defaultVictim));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertEquals("Expected only one error to have been recorded",
        messageBuilder.getMessages().size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage =
        "Legacy Id of Participant does not correspond to an existing " + "CWS/CMS Client";
    assertEquals("Expected participant to not been found message to have been recorded",
        expectedErrorMessage, message);
    verify(clientService, never()).update(eq(badLegacyId), any());
  }

  @Test
  public void shouldSaveReporterIfAddressIsNull() throws Exception {
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient =
        new ClientEntityBuilder().setId("1234567ABC").build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    when(clientService.update(any(), any())).thenReturn(savedClient);
    when(clientService.find(any())).thenReturn(savedClient);

    Participant reporter =
        new ParticipantResourceBuilder().setLegacyId("").createReporterParticipant();
    reporter.setAddresses(null);
    Set<Participant> participants = new HashSet<>(Arrays.asList(reporter, defaultVictim));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    when(clientService.find(any())).thenReturn(foundClient);
    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);
    assertEquals("Expected no error to have been recorded", 0, messageBuilder.getMessages().size());
  }

  @Test
  public void testMultipleVictimSuccess() throws Exception {
    Participant victim1 = new ParticipantResourceBuilder().setFirstName("Sally").setLegacyId("")
        .setLegacyDescriptor(null).createVictimParticipant();
    Participant victim2 = new ParticipantResourceBuilder().setFirstName("Fred").setLegacyId("")
        .setLegacyDescriptor(null).createVictimParticipant();
    Set<Participant> participants =
        new HashSet<>(Arrays.asList(victim1, victim2, defaultReporter, defaultVictim));
    int numberOfClientsThatAreNotReporters = 3;

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    when(clientService.find(any())).thenReturn(foundClient);

    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertEquals("Expected no error to have been recorded", messageBuilder.getMessages().size(), 0);
    verify(clientService, times(numberOfClientsThatAreNotReporters)).create(any());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldNotProcessSelfReporterAsASeperateParticipant() {
    Set<String> roles = new HashSet<>();
    roles.add(Role.VICTIM_ROLE.getType());
    roles.add(Role.SELF_REPORTED_ROLE.getType());
    Participant selfReporter = new ParticipantResourceBuilder().setFirstName("Sally")
        .setRoles(roles).setLegacyId("").setLegacyDescriptor(null).createParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(selfReporter));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    when(clientService.find(any())).thenReturn(foundClient);

    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertEquals("Expected no error to have been recorded", messageBuilder.getMessages().size(), 0);

    verify(clientService, times(1)).find(any());
    verify(clientService, times(1)).create(any());
  }

  @Test
  public void shouldNotProcessAnonymousReporter() {
    Set<String> roles = new HashSet<>();
    roles.add(Role.VICTIM_ROLE.getType());
    roles.add(Role.ANONYMOUS_REPORTER_ROLE.getType());
    Participant selfReporter = new ParticipantResourceBuilder().setFirstName("Sally")
        .setRoles(roles).setLegacyId("").createParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(selfReporter));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    verify(clientService, never()).find(any());
    verify(clientService, times(0)).create(any());
    assertEquals("Expected to have one error message", messageBuilder.getMessages().size(), 1);
    assertEquals("Expected to have in compatible role error",
        messageBuilder.getMessages().get(0).getMessage().trim(),
        "Participant contains incompatible roles");
  }

  @Test
  public void shouldUpdatePerpetratorWhenAlreadyExists() throws Exception {
    String victimId = "VICTIM__ID";
    String existingPerpId = "1234567ABC";
    Participant reporter = new ParticipantResourceBuilder().setFirstName("Barney")
        .setLegacyId(victimId).setMiddleName("middlestone").setLastName("Rubble")
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter", "Victim")))
        .createParticipant();
    LegacyDescriptor descriptor =
        new LegacyDescriptor(victimId, "", lastUpdateDate, "CLIENT_T", "");
    reporter.setLegacyDescriptor(descriptor);
    Participant perp = new ParticipantResourceBuilder().setLegacyId(existingPerpId)
        .setFirstName("Fred").setMiddleName("Finnigan").setLastName("Flintsone")
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    descriptor = new LegacyDescriptor(existingPerpId, "", lastUpdateDate, "CLIENT_T", "");
    perp.setLegacyDescriptor(descriptor);
    Set<Participant> participants = new HashSet<>(Arrays.asList(reporter, perp));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    gov.ca.cwds.rest.api.domain.cms.Client updatedPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client updatedReporter =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    when(updatedReporter.getLastUpdatedTime()).thenReturn(updatedTime);
    gov.ca.cwds.rest.api.domain.cms.Client foundVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    when(foundVictim.getLastUpdatedTime()).thenReturn(lastUpdateDate) // first call return original
        .thenReturn(updatedTime); // second call return updated time
    gov.ca.cwds.rest.api.domain.cms.Client foundPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    when(foundPerp.getLastUpdatedTime()).thenReturn(lastUpdateDate);
    gov.ca.cwds.rest.api.domain.cms.PostedClient savedVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.PostedClient.class);
    when(savedVictim.getId()).thenReturn(victimId);
    when(savedVictim.getLastUpdatedTime()).thenReturn(updatedTime);
    when(clientService.update(eq(existingPerpId), any())).thenReturn(updatedPerp);
    when(clientService.create(any())).thenReturn(savedVictim);
    when(clientService.create(any())).thenReturn(savedVictim);
    when(clientService.update(eq(victimId), any())).thenReturn(updatedReporter);
    when(clientService.find(victimId)).thenReturn(foundVictim);
    when(clientService.find(existingPerpId)).thenReturn(foundPerp);

    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);
    verify(foundVictim, times(1)).update("Barney", "middlestone", "Rubble", "", "M", "123456789",
        (short) 841, "A", "A", "X", ParticipantTest.COMMON_TEST_BIRTH_DATE);
    verify(foundPerp, times(1)).update("Fred", "Finnigan", "Flintsone", "", "M", "123456789",
        (short) 841, "A", "A", "X", ParticipantTest.COMMON_TEST_BIRTH_DATE);
    verify(clientService, times(2)).update(eq(existingPerpId), any());
  }

  @Test
  public void shouldReturnErrorMessageWhenUnableToSaveClient() throws Exception {
    String existingPerpId = "1234567ABC";
    Participant reporter =
        new ParticipantResourceBuilder().setFirstName("Barney").setLastName("Rubble")
            .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter"))).createParticipant();
    LegacyDescriptor descriptor =
        new LegacyDescriptor("1234567ABC", "", lastUpdateDate, "CLIENT_T", "");
    reporter.setLegacyDescriptor(descriptor);
    Participant perp = new ParticipantResourceBuilder().setLegacyId(existingPerpId)
        .setFirstName("Fred").setLastName("Flintsone")
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    Participant victim = new ParticipantResourceBuilder().setLegacyId("").createVictimParticipant();
    descriptor = new LegacyDescriptor("1234567ABC", "", lastUpdateDate, "CLIENT_T", "");
    perp.setLegacyDescriptor(descriptor);
    Set<Participant> participants = new HashSet<>(Arrays.asList(reporter, perp, victim));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    String updatedReporterId = "ASDFGHJZXC";
    gov.ca.cwds.rest.api.domain.cms.Client updatedPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client updatedReporter =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client foundClient =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.PostedClient savedVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.PostedClient.class);
    when(savedVictim.getId()).thenReturn(updatedReporterId);
    when(clientService.update(eq(existingPerpId), any())).thenReturn(updatedPerp);
    when(clientService.create(any())).thenReturn(savedVictim);
    when(clientService.update(any(), any())).thenReturn(null);
    when(clientService.find(any())).thenReturn(foundClient);
    when(foundClient.getLastUpdatedTime()).thenReturn(lastUpdateDate);
    Address foundAddress = mock(Address.class);
    when(foundAddress.getExistingAddressId()).thenReturn(savedAddressId);
    when(addressService.find(savedAddressId)).thenReturn(foundAddress);

    ClientAddress clientAddress = mock(ClientAddress.class);
    ArrayList<Response> addresses = new ArrayList();
    addresses.add(clientAddress);
    when(clientAddressService.findByAddressAndClient(any(), eq(reporter))).thenReturn(addresses);

    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertEquals("Expected only one error to have been recorded", 1,
        messageBuilder.getMessages().size());
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Unable to save Client";
    assertEquals("Expected unable to save message to have been recorded", expectedErrorMessage,
        message);
  }

  @Test
  public void shouldApplySensitivityIndicatorFromClientWhenSavingNewClient() {
    Set<Participant> defaultParticipant =
        new HashSet<>(Arrays.asList(defaultVictim, defaultReporter, defaultPerpetrator));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessCode("N").setParticipants(defaultParticipant).createScreeningToReferral();

    PostedClient createdClient = mock(PostedClient.class);
    when(clientService.create(any())).thenReturn(createdClient);

    ArgumentCaptor<Client> clientArgCaptor = ArgumentCaptor.forClass(Client.class);
    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    when(clientService.find(any())).thenReturn(foundClient);

    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    verify(clientService, times(2)).create(clientArgCaptor.capture());
    assertEquals("Expected client to have sensitivty indicator applied",
        defaultVictim.getSensitivityIndicator(),
        clientArgCaptor.getValue().getSensitivityIndicator());
  }

  @Test
  public void testSSBIsNotUpdatedForNotSupportedReportType() {
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient =
        new ClientEntityBuilder().setId("1234567ABC").build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    when(clientService.update(any(), any())).thenReturn(savedClient);
    when(clientService.find(any())).thenReturn(savedClient);
    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setReportType("").createScreeningToReferral();

    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    verify(specialProjectReferralService, times(0)).processSafelySurrenderedBabies(any(), any(),
        any(), any(), any());
  }

  @Test
  public void testSSBIsNull() {
    Participant victimParticipant = new ParticipantResourceBuilder().createVictimParticipant();
    victimParticipant.setSafelySurrenderedBabies(null);
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient =
        new ClientEntityBuilder().setId("1234567ABC").build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    when(clientService.update(any(), any())).thenReturn(savedClient);
    when(clientService.find(any())).thenReturn(savedClient);

    Set<Participant> participants =
        new HashSet<>(Arrays.asList(defaultReporter, victimParticipant));

    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setReportType(FerbConstants.ReportType.SSB)
            .setParticipants(participants).createScreeningToReferral();
    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    assertTrue(messageBuilder.getMessages().stream().map(message -> message.getMessage())
        .collect(Collectors.toList()).contains("SafelySurrenderedBabies info must be provided."));
  }

  @Test
  public void testSSBIsUpdated() {
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setReportType(FerbConstants.ReportType.SSB).createScreeningToReferral();
    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    when(clientService.find(any())).thenReturn(foundClient);

    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);

    verify(specialProjectReferralService)
        .processSafelySurrenderedBabies(argThat(new ArgumentMatcher<String>() {
          @Override
          public boolean matches(String childClientId) {
            assertEquals("1234567ABC", childClientId);
            return true;
          }
        }), argThat(new ArgumentMatcher<String>() {
          @Override
          public boolean matches(String referralId) {
            assertEquals("1234567890", referralId);
            return true;
          }
        }), argThat(new ArgumentMatcher<LocalDate>() {
          @Override
          public boolean matches(LocalDate referralReceivedDate) {
            return true;
          }
        }), argThat(new ArgumentMatcher<LocalTime>() {
          @Override
          public boolean matches(LocalTime referralReceivedTime) {
            return true;
          }
        }), argThat(new ArgumentMatcher<gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies>() {
          @Override
          public boolean matches(gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies ssb) {
            assertNotNull(ssb);
            SafelySurrenderedBabies expected = new SafelySurrenderedBabiesBuilder().build();
            assertEquals(expected, ssb);
            return true;
          }
        }));
  }

  @Test
  public void shouldReportExceptionWhenUpdateClientThrowsPersistenceException() {
    LegacyDescriptor descriptor =
        new LegacyDescriptor("ABC123DSAF", "", lastUpdateDate, LegacyTable.CLIENT.getName(), "");
    Participant victim =
        new ParticipantResourceBuilder().setLegacyDescriptor(descriptor).createParticipant();
    Set<Participant> participants =
        new HashSet<>(Arrays.asList(victim, defaultReporter, defaultPerpetrator));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    Client foundClient = mock(Client.class);
    when(foundClient.getLastUpdatedTime()).thenReturn(modifiedLastUpdateDate);

    PostedClient createdClient = mock(PostedClient.class);

    when(createdClient.getId()).thenReturn("LEGACYIDXX");
    when(clientService.find(any(String.class))).thenReturn(foundClient);
    when(clientService.create(any())).thenReturn(createdClient);
    when(clientService.update(any(String.class), any())).thenThrow(new PersistenceException());
    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);
    assertEquals(messageBuilder.getMessages().size(), 1);
  }

  @Test
  public void shouldNotUpdateClientWhenErrorMessageExists() throws Exception {
    String victimClientLegacyId = "ABC123DSAF";

    LegacyDescriptor descriptor =
        new LegacyDescriptor("ABC123DSAF", "", lastUpdateDate, LegacyTable.CLIENT.getName(), "");
    Participant victim = new ParticipantResourceBuilder().setLegacyId(victimClientLegacyId)
        .setLegacyDescriptor(descriptor).createParticipant();
    Set<Participant> participants =
        new HashSet<>(Arrays.asList(victim, defaultReporter, defaultPerpetrator));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    Client foundClient = new ClientResourceBuilder().setBirthDate(null)
        .setLastUpdateTime(modifiedLastUpdateDate).build();
    when(clientService.find(any())).thenReturn(foundClient);
    messageBuilder.addError("this is a test error");
    participantToLegacyClient.saveParticipants(referral, dateStarted, timeStarted, referralId,
        messageBuilder);
    verify(clientService, never()).update(any(), any());
  }

}
