package gov.ca.cwds.rest.business.reminders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StateIdDao;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.StateId;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ReferralClientEntityBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.services.cms.TickleService;

/**
 * @author CWDS API Team
 *
 */
public class R05443AndR03341StateIdMissingTest {

  private TickleService tickleService;
  private ClientDao clientDao;
  private ReferralDao referralDao;
  private ReferralClientDao referralClientDao;
  private StateIdDao stateIdDao;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * @throws Exception general test error
   */
  @Before
  public void setup() throws Exception {
    tickleService = mock(TickleService.class);
    clientDao = mock(ClientDao.class);
    referralDao = mock(ReferralDao.class);
    referralClientDao = mock(ReferralClientDao.class);
    stateIdDao = mock(StateIdDao.class);
  }

  @Test
  public void testStateIdIsPresent() {
    String back25YearsDate =
        LocalDate.now().minus(25, ChronoUnit.YEARS).format(DateTimeFormatter.ISO_LOCAL_DATE);

    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth(back25YearsDate).createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth(back25YearsDate).createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, null, null);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    gov.ca.cwds.data.persistence.cms.Client client =
        new ClientEntityBuilder().setEstimatedDobCode("Y").build();

    StateId stateId1 = new StateId();
    stateId1.setEndDate(new Date());
    StateId stateId2 = new StateId();
    List<StateId> stateIds = Arrays.asList(stateId1, stateId2);

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(client);
    when(stateIdDao.findAllByClientId(any())).thenReturn(stateIds);
    R05443AndR03341StateIdMissing r05443AndR03341StateIdMissing = new R05443AndR03341StateIdMissing(
        clientDao, referralDao, referralClientDao, stateIdDao, tickleService);

    r05443AndR03341StateIdMissing.stateIdMissing(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }

  /**
   * Test for the state id missing Reminder
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForStateIdMissingReminder() throws Exception {
    String back25YearsDate =
        LocalDate.now().minus(25, ChronoUnit.YEARS).format(DateTimeFormatter.ISO_LOCAL_DATE);

    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("098UijH1gf", null,
        new DateTime("2018-06-11T18:47:07.524Z"), LegacyTable.CLIENT.getName(), null);
    Participant victim = new ParticipantResourceBuilder().setDateOfBirth(back25YearsDate)
        .setLegacyDescriptor(legacyDescriptor).createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().setDateOfBirth(back25YearsDate)
        .setLegacyDescriptor(legacyDescriptor).createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, null, null);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    gov.ca.cwds.data.persistence.cms.Client client =
        new ClientEntityBuilder().setEstimatedDobCode("Y").build();

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(client);
    R05443AndR03341StateIdMissing r05443AndR03341StateIdMissing = new R05443AndR03341StateIdMissing(
        clientDao, referralDao, referralClientDao, stateIdDao, tickleService);

    r05443AndR03341StateIdMissing.stateIdMissing(postedScreeningToReferral);
    verify(tickleService, times(2)).create(any());
  }

  /**
   * Test for the no state id missing Reminder created when the DateOfBirth is Null
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForNoStateIdMissingReminderWhenDobIsNull() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth(null).createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth(null).createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, null, null);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    gov.ca.cwds.data.persistence.cms.Client client =
        new ClientEntityBuilder().setEstimatedDobCode("Y").build();


    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(client);
    R05443AndR03341StateIdMissing r05443AndR03341StateIdMissing = new R05443AndR03341StateIdMissing(
        clientDao, referralDao, referralClientDao, stateIdDao, tickleService);

    r05443AndR03341StateIdMissing.stateIdMissing(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }

  /**
   * @throws Exception - DateTimeParseException
   */
  @Test(expected = DateTimeParseException.class)
  public void testForStateIdMissingDateOfBirthParseException() throws Exception {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("098UijH1gf", null,
        new DateTime("2018-06-11T18:47:07.524Z"), LegacyTable.CLIENT.getName(), null);
    Participant victim = new ParticipantResourceBuilder().setDateOfBirth("1986-06-18")
        .setLegacyDescriptor(legacyDescriptor).createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().setDateOfBirth("1992:06:18")
        .setLegacyDescriptor(legacyDescriptor).createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, null, null);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    gov.ca.cwds.data.persistence.cms.Client client =
        new ClientEntityBuilder().setEstimatedDobCode("Y").build();

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(client);

    R05443AndR03341StateIdMissing r05443AndR03341StateIdMissing = new R05443AndR03341StateIdMissing(
        clientDao, referralDao, referralClientDao, stateIdDao, tickleService);

    r05443AndR03341StateIdMissing.stateIdMissing(postedScreeningToReferral);
    verify(tickleService, times(1)).create(any());
  }

  /**
   * Test for no remindes created when Client age is above 26
   * 
   * @throws Exception - exception
   */
  @Test
  public void testClientAgeIsAbove26() throws Exception {
    String back26YearsDate =
        LocalDate.now().minus(26, ChronoUnit.YEARS).format(DateTimeFormatter.ISO_LOCAL_DATE);

    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth(back26YearsDate).createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth(back26YearsDate).createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, null, null);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    gov.ca.cwds.data.persistence.cms.Client client =
        new ClientEntityBuilder().setEstimatedDobCode("Y").build();

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(client);
    R05443AndR03341StateIdMissing r05443AndR03341StateIdMissing = new R05443AndR03341StateIdMissing(
        clientDao, referralDao, referralClientDao, stateIdDao, tickleService);

    r05443AndR03341StateIdMissing.stateIdMissing(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testEstimatedAgeIsAbove26() throws Exception {
    int ageNumber = 26;

    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth(null).createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth(null).createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, null, null);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    gov.ca.cwds.data.persistence.cms.Client client =
        new ClientEntityBuilder().setEstimatedDobCode("Y").build();

    ReferralClient referralClient = new ReferralClientEntityBuilder()
        .setAgeNumber((short) ageNumber).setAgePeriodCode("Y").build();

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(referralClientDao.find(any())).thenReturn(referralClient);
    when(clientDao.find(any(String.class))).thenReturn(client);
    R05443AndR03341StateIdMissing r05443AndR03341StateIdMissing = new R05443AndR03341StateIdMissing(
        clientDao, referralDao, referralClientDao, stateIdDao, tickleService);

    r05443AndR03341StateIdMissing.stateIdMissing(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testEstimatedAgeIsLess26() throws Exception {
    int ageNumber = 25;

    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("098UijH1gf", null,
        new DateTime("2018-06-11T18:47:07.524Z"), LegacyTable.CLIENT.getName(), null);
    Participant victim = new ParticipantResourceBuilder().setDateOfBirth(null)
        .setLegacyDescriptor(legacyDescriptor).createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().setDateOfBirth(null)
        .setLegacyDescriptor(legacyDescriptor).createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, null, null);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    gov.ca.cwds.data.persistence.cms.Client client =
        new ClientEntityBuilder().setEstimatedDobCode("Y").build();

    ReferralClient referralClient = new ReferralClientEntityBuilder()
        .setAgeNumber((short) ageNumber).setAgePeriodCode("Y").build();

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(referralClientDao.find(any())).thenReturn(referralClient);
    when(clientDao.find(any(String.class))).thenReturn(client);
    R05443AndR03341StateIdMissing r05443AndR03341StateIdMissing = new R05443AndR03341StateIdMissing(
        clientDao, referralDao, referralClientDao, stateIdDao, tickleService);

    r05443AndR03341StateIdMissing.stateIdMissing(postedScreeningToReferral);
    verify(tickleService, times(2)).create(any());
  }

}
