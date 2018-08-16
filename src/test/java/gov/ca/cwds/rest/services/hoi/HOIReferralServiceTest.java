package gov.ca.cwds.rest.services.hoi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.fixture.AllegationEntityBuilder;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.CmsReporterResourceBuilder;
import gov.ca.cwds.fixture.ReferralClientResourceBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.services.auth.AuthorizationService;
import gov.ca.cwds.rest.util.Doofenshmirtz;

/**
 * @author CWDS API Team
 */
public class HOIReferralServiceTest extends Doofenshmirtz<Client> {

  private static final String REFERRAL1_ID = "ABC1234567";
  private static final String REFERRAL2_ID = "ABC1234568";
  private static final String REFERRAL3_ID = "ABC1234569";
  private static final String REFERRAL4_ID = "ABC1234560";
  // to emulate an old referral without allegations
  private static final String REFERRAL9_ID = "ABC1234590";

  private static final String CLIENT1_ID = "1234567ABC";
  private static final String CLIENT2_ID = "2345678ABC";
  private static final String CLIENT3_ID = "P975G53fTh";
  private static final String CLIENT4_ID = "ABC5G53fTh";

  private ReferralClientDao referralClientDao = mock(ReferralClientDao.class);
  private ReferralDao referralDao = mock(ReferralDao.class);
  private StaffPersonDao staffPersonDao = mock(StaffPersonDao.class);
  private AllegationDao allegationDao = mock(AllegationDao.class);
  private AuthorizationService authorizationService;
  private HOIRequest request;
  private HOIReferralService hoiReferralService;

  private static final gov.ca.cwds.data.persistence.cms.ReferralClient referralClient11 = buildReferralClient(
      CLIENT1_ID, REFERRAL1_ID, false);
  private static final gov.ca.cwds.data.persistence.cms.ReferralClient referralClient12 = buildReferralClient(
      CLIENT1_ID, REFERRAL2_ID, false);
  private static final gov.ca.cwds.data.persistence.cms.ReferralClient referralClient21 = buildReferralClient(
      CLIENT2_ID, REFERRAL1_ID, false);
  private static final gov.ca.cwds.data.persistence.cms.ReferralClient referralClient22 = buildReferralClient(
      CLIENT2_ID, REFERRAL2_ID, false);
  private static final gov.ca.cwds.data.persistence.cms.ReferralClient referralClient3 = buildReferralClient(
      CLIENT3_ID, REFERRAL3_ID, false);
  private static final gov.ca.cwds.data.persistence.cms.ReferralClient referralClient4 = buildReferralClient(
      CLIENT4_ID, REFERRAL4_ID, false);
  private static final gov.ca.cwds.data.persistence.cms.ReferralClient referralClient9 = buildReferralClient(
      CLIENT3_ID, REFERRAL9_ID, false);
  private static final gov.ca.cwds.data.persistence.cms.ReferralClient referralClientS = buildReferralClient(
      CLIENT1_ID, REFERRAL1_ID, true);

  private static Map<String, Client> clientsMap;

  /**
   * Initialize system code cache
   */
  @SuppressWarnings("unused")
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @BeforeClass
  public static void beforeClass() {
    setUpClientsMap();
  }


  /**
   * Test setup
   *
   * @throws Exception - Exception
   */
  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    setUpStaffPersons();
    setUpAllegations();
    authorizationService = new AuthorizationService();
    hoiReferralService = new HOIReferralService(authorizationService, referralClientDao,
        referralDao, staffPersonDao, allegationDao);
    request = new HOIRequest();
    request.setClientIds(Stream.of("CLIENT-123").collect(Collectors.toSet()));
  }

  @Test
  public void shouldEliminateDuplicateHOIReferralClients() {
    // two clients (victim and perpetrator) in two different referrals

    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients =
        {referralClient11, referralClient12, referralClient21, referralClient22};
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);
    setUpReferralsWithReporters(REFERRAL1_ID, REFERRAL2_ID);

    HOIReferralResponse response = hoiReferralService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }

  @Test
  public void shouldEliminateDuplicateHOIReferrals() {
    // two clients (victim and perpetrator) in a referral

    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients =
        {referralClient11, referralClient21};
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);
    setUpReferralsWithReporters(REFERRAL1_ID);

    HOIReferralResponse response = hoiReferralService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(1)));
  }

  @Test
  public void testHandleFindWhenReporterMandated() {
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {referralClient3};
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);
    setUpReferralsWithReporters(REFERRAL3_ID);

    HOIReferralResponse response = hoiReferralService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(1)));
  }

  @Test
  public void testHandleFindWhenAnonymousReporter() {
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {referralClient4};
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);
    setUpReferralsWithReporters(REFERRAL4_ID);

    HOIReferralResponse response = hoiReferralService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(1)));
  }

  @Test
  public void testHandleFindWhenSelfReporter() {
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {referralClientS};
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);
    setUpReferralsWithReporters(REFERRAL1_ID);

    HOIReferralResponse response = hoiReferralService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(1)));
  }

  @Test
  public void testHandleFindWhenReferralHasNoAllegations() {
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {referralClient9};
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);
    setUpReferralsWithReporters(REFERRAL9_ID);

    HOIReferralResponse response = hoiReferralService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(1)));
  }

  @Test
  public void testForHandleFindWhenPerpetratorAndVictimClientNull() {
    final Date allegationDate = new Date();
    final Allegation allegation1 = new AllegationEntityBuilder().setReferralId(REFERRAL1_ID)
        .setVictimClientId(CLIENT2_ID).setVictim(clientsMap.get(CLIENT2_ID))
        .setPerpetratorClientId(null).setAbuseEndDate(allegationDate)
        .setAbuseStartDate(allegationDate).build();
    final Allegation allegation2 = new AllegationEntityBuilder().setReferralId(REFERRAL2_ID)
        .setVictimClientId(null).setPerpetratorClientId(null)
        .setAbuseEndDate(allegationDate).setAbuseStartDate(allegationDate).build();

    // map where key is a referralId and value is a collection of Allegations of the referral
    final Map<String, Set<Allegation>> allegationsMap = new HashMap<>();
    final Set<Allegation> allegationsSet1 = new HashSet<>();
    final Set<Allegation> allegationsSet2 = new HashSet<>();
    allegationsSet1.add(allegation1);
    allegationsSet2.add(allegation2);
    allegationsMap.put(REFERRAL1_ID, allegationsSet1);
    allegationsMap.put(REFERRAL2_ID, allegationsSet2);

    when(allegationDao.findAllegationsWithClientsByReferralIds(any(Collection.class)))
        .thenReturn(allegationsMap);

    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {referralClient21,
        referralClient22};
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);
    setUpReferralsWithReporters(REFERRAL1_ID, REFERRAL2_ID);

    HOIReferralResponse response = hoiReferralService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }

  @Test
  public void testHandleFindWhenNoClientIdsProvided() {
    HOIRequest emptyRequest = new HOIRequest();
    emptyRequest.setClientIds(new HashSet<>());
    HOIReferralResponse response = hoiReferralService.handleFind(emptyRequest);
    assertThat(response, notNullValue());
  }

  @Test
  public void testUnAuthorizedClient() {
    final Client victim = new ClientEntityBuilder().setId(CLIENT1_ID).build();
    final Client perp = new ClientEntityBuilder().setId(CLIENT2_ID).build();

    final Set<Allegation> allegations = new HashSet<>();
    final Allegation validPersistent = new AllegationEntityBuilder().setVictimClientId(CLIENT1_ID)
        .setPerpetratorClientId(CLIENT2_ID).build();
    final gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("86XV1bG06k",
            validPersistent.getAbuseEndDate(), validPersistent.getAbuseStartDate(),
            validPersistent.getAbuseFrequency(), validPersistent.getAbuseFrequencyPeriodCode(),
            validPersistent.getAbuseLocationDescription(),
            validPersistent.getAllegationDispositionType(), validPersistent.getAllegationType(),
            validPersistent.getDispositionDescription(), validPersistent.getDispositionDate(),
            validPersistent.getInjuryHarmDetailIndicator(),
            validPersistent.getNonProtectingParentCode(),
            validPersistent.getStaffPersonAddedIndicator(), validPersistent.getVictimClientId(),
            validPersistent.getPerpetratorClientId(), validPersistent.getReferralId(),
            validPersistent.getCountySpecificCode(), validPersistent.getZippyCreatedIndicator(),
            validPersistent.getPlacementFacilityType(), victim, perp);
    allegations.add(allegation);

    final Referral referral = new Referral("86XV1bG06k", " ", "N", "N", "D5YRVOm0Ht", (short) 122,
        " ", null, (short) 409, "", "", "L3H7sSC0Ht", "", "N", "N", (short) 1118, " ", "N",
        "N", null, "Verification (R3)", " ", null, null, (short) 1520,
        (short) 0, null, null, "", "", " ", " ", " ", "", "", "0Ht", "0Ht", "51", "N",
        "N", "N", "N", null, "C", (short) 0, null, "", null, null, allegations,
        null, null);

    final gov.ca.cwds.data.persistence.cms.ReferralClient rc =
        new gov.ca.cwds.data.persistence.cms.ReferralClient("86XV1bG06k", CLIENT1_ID, "",
            (short) 122, (short) 681, "S", null, "N", "N", "", (short) 2, "", "", "Y", "N", "N");
    rc.setReferral(referral);
    final gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {rc};
    when(referralClientDao.findByClientIds(any())).thenReturn(referralClients);

    authorizationService = mock(AuthorizationService.class);

    request = new HOIRequest();
    request.setClientIds(Stream.of("unauthorizedId").collect(Collectors.toSet()));

    hoiReferralService = new HOIReferralService(authorizationService, referralClientDao,
        referralDao, staffPersonDao, allegationDao);
    final HOIReferralService spyTarget = spy(hoiReferralService);
    final HOIReferralResponse response = spyTarget.handleFind(request);
    assertThat("No Referrals have been returned", response.getHoiReferrals().isEmpty());
  }

  private static gov.ca.cwds.data.persistence.cms.ReferralClient buildReferralClient(
      String clientId,
      String referralId, boolean selfReportedIndicator) {
    ReferralClient referralClientDomain = new ReferralClientResourceBuilder()
        .setSelfReportedIndicator(selfReportedIndicator).setClientId(clientId)
        .setReferralId(referralId).buildReferralClient();
    return new gov.ca.cwds.data.persistence.cms.ReferralClient(referralClientDomain, "OXA",
        new Date());
  }

  private void setUpStaffPersons() {
    final StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();
    final StaffPerson otherStaffPerson = new StaffPersonEntityBuilder().setId("0Ht").build();

    // map where key is a StaffPerson id and value is a StaffPerson itself
    final Map<String, StaffPerson> staffPersonMap = new HashMap<>();
    staffPersonMap.put(staffPerson.getId(), staffPerson);
    staffPersonMap.put(otherStaffPerson.getId(), otherStaffPerson);
    when(staffPersonDao.findByIds(any(Collection.class))).thenReturn(staffPersonMap);
  }

  private void setUpAllegations() {
    final Date allegationDate = new Date();
    final Allegation allegation1 = new AllegationEntityBuilder().setReferralId(REFERRAL1_ID)
        .setVictimClientId(CLIENT1_ID).setPerpetratorClientId(CLIENT2_ID)
        .setVictim(clientsMap.get(CLIENT1_ID)).setPerpetrator(clientsMap.get(CLIENT2_ID))
        .setAbuseEndDate(allegationDate).setAbuseStartDate(allegationDate).build();
    final Allegation allegation2 = new AllegationEntityBuilder().setReferralId(REFERRAL2_ID)
        .setVictimClientId(CLIENT1_ID).setPerpetratorClientId(CLIENT2_ID)
        .setVictim(clientsMap.get(CLIENT1_ID)).setPerpetrator(clientsMap.get(CLIENT2_ID))
        .setAbuseEndDate(allegationDate).setAbuseStartDate(allegationDate).build();
    final Allegation allegation3 = new AllegationEntityBuilder().setReferralId(REFERRAL3_ID)
        .setVictimClientId(CLIENT1_ID).setPerpetratorClientId(CLIENT3_ID)
        .setVictim(clientsMap.get(CLIENT1_ID)).setPerpetrator(clientsMap.get(CLIENT3_ID))
        .build();
    final Allegation allegation4 = new AllegationEntityBuilder().setReferralId(REFERRAL4_ID)
        .setVictimClientId(CLIENT1_ID).setPerpetratorClientId(CLIENT4_ID)
        .setVictim(clientsMap.get(CLIENT1_ID)).setPerpetrator(clientsMap.get(CLIENT4_ID))
        .build();

    // map where key is a referralId and value is a collection of Allegations of the referral
    final Map<String, Set<Allegation>> allegationsMap = new HashMap<>();
    final Set<Allegation> allegationsSet1 = new HashSet<>();
    final Set<Allegation> allegationsSet2 = new HashSet<>();
    final Set<Allegation> allegationsSet3 = new HashSet<>();
    final Set<Allegation> allegationsSet4 = new HashSet<>();
    allegationsSet1.add(allegation1);
    allegationsSet2.add(allegation2);
    allegationsSet3.add(allegation3);
    allegationsSet4.add(allegation4);
    allegationsMap.put(REFERRAL1_ID, allegationsSet1);
    allegationsMap.put(REFERRAL2_ID, allegationsSet2);
    allegationsMap.put(REFERRAL3_ID, allegationsSet3);
    allegationsMap.put(REFERRAL4_ID, allegationsSet4);

    when(allegationDao.findAllegationsWithClientsByReferralIds(any(Collection.class)))
        .thenReturn(allegationsMap);
  }

  private void setUpReferralsWithReporters(String ... referralIds) {
    // set up referrals
    Date referralDate = new Date();
    Referral referral_1 = new ReferralEntityBuilder().setId(REFERRAL1_ID)
        .setReceivedDate(referralDate).setPrimaryContactStaffPersonId("0X5")
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();
    Referral referral_2 = new ReferralEntityBuilder().setId(REFERRAL2_ID)
        .setReceivedDate(referralDate).setPrimaryContactStaffPersonId("0X5")
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();
    Referral referral_3 = new ReferralEntityBuilder().setId(REFERRAL3_ID)
        .setReceivedDate(referralDate).setPrimaryContactStaffPersonId("0X5").build();
    Referral referral_4 = new ReferralEntityBuilder().setId(REFERRAL4_ID)
        .setReceivedDate(referralDate).setPrimaryContactStaffPersonId("0X5")
        .setAnonymousReporterIndicator("Y").build();
    Referral referral_9 = new ReferralEntityBuilder().setId(REFERRAL9_ID)
        .setReceivedDate(referralDate).setPrimaryContactStaffPersonId("0X5")
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();

    // map where key is a Referral id and value is a Referral itself
    Map<String, Referral> referralsMap = new HashMap<>();
    if (ArrayUtils.contains(referralIds, referral_1.getId())) {
      referralsMap.put(referral_1.getId(), referral_1);
    }
    if (ArrayUtils.contains(referralIds, referral_2.getId())) {
      referralsMap.put(referral_2.getId(), referral_2);
    }
    if (ArrayUtils.contains(referralIds, referral_3.getId())) {
      referralsMap.put(referral_3.getId(), referral_3);
    }
    if (ArrayUtils.contains(referralIds, referral_4.getId())) {
      referralsMap.put(referral_4.getId(), referral_4);
    }
    if (ArrayUtils.contains(referralIds, referral_9.getId())) {
      referralsMap.put(referral_9.getId(), referral_9);
    }
    when(referralDao.findReferralsWithReportersByIds(any(Collection.class))).thenReturn(referralsMap);

    // set up reporters
    gov.ca.cwds.data.persistence.cms.Reporter reporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(new CmsReporterResourceBuilder().build(),
            "0X5", new Date());
    gov.ca.cwds.data.persistence.cms.Reporter mandatedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(new CmsReporterResourceBuilder()
            .setMandatedReporterIndicator(true).build(), "0X5", new Date());

    referral_1.setReporter(reporter);
    referral_2.setReporter(reporter);
    referral_3.setReporter(mandatedReporter);
    referral_9.setReporter(reporter);
  }

  private static void setUpClientsMap() {
    final Client client1 = new ClientEntityBuilder().setId(CLIENT1_ID).build();
    final Client client2 = new ClientEntityBuilder().setId(CLIENT2_ID).build();
    final Client client3 = new ClientEntityBuilder().setId(CLIENT3_ID).build();
    final Client client4 = new ClientEntityBuilder().setId(CLIENT4_ID).build();

    // map where key is a Client id and value is a Client itself
    clientsMap = new HashMap<>();
    clientsMap.put(client1.getId(), client1);
    clientsMap.put(client2.getId(), client2);
    clientsMap.put(client3.getId(), client3);
    clientsMap.put(client4.getId(), client4);
  }
}
