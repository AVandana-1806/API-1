package gov.ca.cwds.rest.services.hoi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.shiro.authz.AuthorizationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.fixture.AllegationEntityBuilder;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.CmsReporterResourceBuilder;
import gov.ca.cwds.fixture.ReferralClientEntityBuilder;
import gov.ca.cwds.fixture.ReferralClientResourceBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.services.auth.AuthorizationService;

/**
 * @author CWDS API Team
 *
 */
public class HOIReferralServiceTest {

  private ClientDao clientDao;
  private ReferralClientDao referralClientDao;
  private AuthorizationService authorizationService;
  private HOIReferralService hoiService;
  private HOIRequest request;

  /**
   * Initialize system code cache
   */
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test setup
   * 
   * @throws Exception - Exception
   */
  @Before
  public void setup() throws Exception {
    SystemCodeCache.global().getAllSystemCodes();
    clientDao = mock(ClientDao.class);
    referralClientDao = mock(ReferralClientDao.class);
    authorizationService = new AuthorizationService();
    hoiService = new HOIReferralService(clientDao, referralClientDao, authorizationService);
    request = new HOIRequest();
    request.setClientIds(Stream.of("123").collect(Collectors.toSet()));
  }

  /**
   * Test to find for handle find
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testForHandleFind() throws Exception {
    Allegation allegation1 = new AllegationEntityBuilder().setReferralId("ABC1234567")
        .setVictimClientId("1234567ABC").build();

    Allegation allegation2 = new AllegationEntityBuilder().setReferralId("ABC1234568")
        .setVictimClientId("1234567ABC").setPerpetratorClientId("P975G53fTh").build();

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();
    Reporter reporter = new CmsReporterResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Reporter persistentReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporter, "0X5", new Date());

    Referral referral1 = new ReferralEntityBuilder().setId("ABC1234567").setReceivedDate(new Date())
        .setAllegations(Arrays.asList(allegation1).stream().collect(Collectors.toSet()))
        .setReporter(persistentReporter).build();
    Referral referral2 = new ReferralEntityBuilder().setId("ABC1234568").setReceivedDate(new Date())
        .setAllegations(Arrays.asList(allegation2).stream().collect(Collectors.toSet()))
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();

    referral1.setStaffPerson(staffPerson);
    referral2.setStaffPerson(staffPerson);

    Client client = new ClientEntityBuilder().build();
    ReferralClient referralCliemtDomain = new ReferralClientResourceBuilder().buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent1 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain, "OXA",
            new Date());
    persistent1.setReferral(referral1);

    ReferralClient referralCliemtDomain1 =
        new ReferralClientResourceBuilder().setReferralId("ABC1234568").buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent2 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain1, "OXA",
            new Date());
    persistent2.setReferral(referral2);
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {persistent1, persistent2};

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }

  @Test
  public void shouldEliminateDuplicateHOIReferralClients() throws Exception {
	// two clients (victim and perpetrator) in two different referrals
	Date referralDate = new Date();
	Date clientDate = new Date();
	Date allegationDate = new Date();
	String clientId1 = "1234567ABC";
	String clientId2 = "2345678ABC";
	
	// set up the allegations
    Allegation allegation1 = new AllegationEntityBuilder()
    		.setReferralId("ABC1234567")
        .setVictimClientId(clientId1)
        .setPerpetratorClientId(clientId2)
        .setAbuseEndDate(allegationDate)
        .setAbuseStartDate(allegationDate)
        .build();
    Allegation allegation2 = new AllegationEntityBuilder()
    		.setReferralId("ABC1234568")
        .setVictimClientId(clientId1)
        .setPerpetratorClientId(clientId2)
        .setAbuseEndDate(allegationDate)
        .setAbuseStartDate(allegationDate)
        .build();

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();
    Reporter reporter = new CmsReporterResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Reporter persistentReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporter, "0X5", new Date());

    Referral referral1 = new ReferralEntityBuilder()
    		.setId("ABC1234567")
    		.setReceivedDate(referralDate)
        .setAllegations(Arrays.asList(allegation1).stream().collect(Collectors.toSet()))
        .setReporter(persistentReporter)
        .setFirstResponseDeterminedByStaffPersonId("0X5")
        .build();
    Referral referral2 = new ReferralEntityBuilder()
    		.setId("ABC1234568")
    		.setReceivedDate(referralDate)
        .setAllegations(Arrays.asList(allegation2).stream().collect(Collectors.toSet()))
        .setReporter(persistentReporter)
        .setFirstResponseDeterminedByStaffPersonId("0X5")
        .build();

    referral1.setStaffPerson(staffPerson);
    referral2.setStaffPerson(staffPerson);

    Client client1 = new ClientEntityBuilder().setId("1234567ABC").build();
    
    ReferralClient referralCliemtDomain11  = new ReferralClientResourceBuilder()
    		.setClientId(clientId1)
    		.setReferralId("ABC1234567")
    		.buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient1 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain11, "OXA",
        	clientDate);
    referralClient1.setReferral(referral1);

    ReferralClient referralCliemtDomain12 = new ReferralClientResourceBuilder()
		.setClientId(clientId2)
		.setReferralId("ABC1234568")
		.buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient2 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain12, "OXA",
        	clientDate);
    referralClient2.setReferral(referral2);
    
    ReferralClient referralCliemtDomain21 = new ReferralClientResourceBuilder()
		.setClientId(clientId1)
		.setReferralId("ABC1234567")
		.buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient3 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain21, "OXA",
        	clientDate);
    referralClient3.setReferral(referral1);
    
    ReferralClient referralCliemtDomain22 = new ReferralClientResourceBuilder()
		.setClientId(clientId2)
		.setReferralId("ABC1234568")
		.buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient4 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain22, "OXA",
        	clientDate);
    referralClient4.setReferral(referral2);

    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {referralClient1, 
    		referralClient2,
    		referralClient3,
    		referralClient4};

    when(clientDao.find(any(String.class))).thenReturn(client1);
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));	
  }
  
  @Test
  public void shouldEliminateDuplicateHOIReferrals() throws Exception {
	// two clients (victim and perpetrator) in a referral
	Date referralDate = new Date();
	Date clientDate = new Date();
	Date allegationDate = new Date();
	String clientId1 = "1234567ABC";
	String clientId2 = "2345678ABC";
	
	// set up the allegations
    Allegation allegation1 = new AllegationEntityBuilder()
    		.setReferralId("ABC1234567")
        .setVictimClientId(clientId1)
        .setPerpetratorClientId(clientId2)
        .setAbuseEndDate(allegationDate)
        .setAbuseStartDate(allegationDate)
        .build();

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();
    Reporter reporter = new CmsReporterResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Reporter persistentReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporter, "0X5", new Date());

    Referral referral1 = new ReferralEntityBuilder()
    		.setId("ABC1234567")
    		.setReceivedDate(referralDate)
        .setAllegations(Arrays.asList(allegation1).stream().collect(Collectors.toSet()))
        .setReporter(persistentReporter)
        .setFirstResponseDeterminedByStaffPersonId("0X5")
        .build();

    referral1.setStaffPerson(staffPerson);
 
    Client client1 = new ClientEntityBuilder().setId("1234567ABC").build();
    
    ReferralClient referralCliemtDomain11  = new ReferralClientResourceBuilder()
    		.setClientId(clientId1)
    		.setReferralId("ABC1234567")
    		.buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient1 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain11, "OXA",
        	clientDate);
    referralClient1.setReferral(referral1);

    ReferralClient referralCliemtDomain12 = new ReferralClientResourceBuilder()
		.setClientId(clientId2)
		.setReferralId("ABC1234567")
		.buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient2 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain12, "OXA",
        	clientDate);
    referralClient2.setReferral(referral1);

    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {referralClient1, 
    		referralClient2};

    when(clientDao.find(any(String.class))).thenReturn(client1);
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(1)));	
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testHandleFindWhenReporterMandated() throws Exception {
    Allegation allegation1 = new AllegationEntityBuilder().setReferralId("ABC1234567")
        .setVictimClientId("1234567ABC").build();

    Allegation allegation2 = new AllegationEntityBuilder().setReferralId("ABC1234568")
        .setVictimClientId("1234567ABC").setPerpetratorClientId("P975G53fTh").build();

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();
    Reporter reporter = new CmsReporterResourceBuilder().setMandatedReporterIndicator(true).build();
    gov.ca.cwds.data.persistence.cms.Reporter persistentReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporter, "0X5", new Date());

    Referral referral1 = new ReferralEntityBuilder().setId("ABC1234567").setReceivedDate(new Date())
        .setAllegations(Arrays.asList(allegation1).stream().collect(Collectors.toSet()))
        .setReporter(persistentReporter).build();
    Referral referral2 = new ReferralEntityBuilder().setId("ABC1234568").setReceivedDate(new Date())
        .setAllegations(Arrays.asList(allegation2).stream().collect(Collectors.toSet()))
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();

    referral1.setStaffPerson(staffPerson);
    referral2.setStaffPerson(staffPerson);

    Client client = new ClientEntityBuilder().build();
    ReferralClient referralCliemtDomain = new ReferralClientResourceBuilder().buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent1 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain, "OXA",
            new Date());
    persistent1.setReferral(referral1);

    ReferralClient referralCliemtDomain1 =
        new ReferralClientResourceBuilder().setReferralId("ABC1234568").buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent2 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain1, "OXA",
            new Date());
    persistent2.setReferral(referral2);
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {persistent1, persistent2};

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testHandleFindWhenAnonymousReporter() throws Exception {
    Allegation allegation1 = new AllegationEntityBuilder().setReferralId("ABC1234567")
        .setVictimClientId("1234567ABC").build();

    Allegation allegation2 = new AllegationEntityBuilder().setReferralId("ABC1234568")
        .setVictimClientId("1234567ABC").setPerpetratorClientId("P975G53fTh").build();

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();

    Referral referral1 = new ReferralEntityBuilder().setId("ABC1234567").setReceivedDate(new Date())
        .setAnonymousReporterIndicator("Y")
        .setAllegations(Arrays.asList(allegation1).stream().collect(Collectors.toSet())).build();
    Referral referral2 = new ReferralEntityBuilder().setId("ABC1234568").setReceivedDate(new Date())
        .setAllegations(Arrays.asList(allegation2).stream().collect(Collectors.toSet()))
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();

    referral1.setStaffPerson(staffPerson);
    referral2.setStaffPerson(staffPerson);

    Client client = new ClientEntityBuilder().build();
    ReferralClient referralCliemtDomain = new ReferralClientResourceBuilder().buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent1 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain, "OXA",
            new Date());
    persistent1.setReferral(referral1);

    ReferralClient referralCliemtDomain1 =
        new ReferralClientResourceBuilder().setReferralId("ABC1234568").buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent2 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain1, "OXA",
            new Date());
    persistent2.setReferral(referral2);
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {persistent1, persistent2};

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testHandleFindWhenSelfReporter() throws Exception {
    Allegation allegation1 = new AllegationEntityBuilder().setReferralId("ABC1234567")
        .setVictimClientId("1234567ABC").build();

    Allegation allegation2 = new AllegationEntityBuilder().setReferralId("ABC1234568")
        .setVictimClientId("1234567ABC").setPerpetratorClientId("P975G53fTh").build();

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();

    Referral referral1 = new ReferralEntityBuilder().setId("ABC1234567").setReceivedDate(new Date())
        .setAllegations(Arrays.asList(allegation1).stream().collect(Collectors.toSet())).build();
    Referral referral2 = new ReferralEntityBuilder().setId("ABC1234568").setReceivedDate(new Date())
        .setAllegations(Arrays.asList(allegation2).stream().collect(Collectors.toSet()))
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();

    referral1.setStaffPerson(staffPerson);
    referral2.setStaffPerson(staffPerson);

    Client client = new ClientEntityBuilder().build();
    ReferralClient referralCliemtDomain =
        new ReferralClientResourceBuilder().setSelfReportedIndicator(true).buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent1 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain, "OXA",
            new Date());
    persistent1.setReferral(referral1);

    ReferralClient referralCliemtDomain1 =
        new ReferralClientResourceBuilder().setReferralId("ABC1234568").buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent2 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain1, "OXA",
            new Date());
    persistent2.setReferral(referral2);
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {persistent1, persistent2};

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForHandleFindWhenPerpetratorAndVictimClientNull() throws Exception {
    Allegation allegation1 = new AllegationEntityBuilder().setReferralId("ABC1234567")
        .setVictimClientId("1234567ABC").setPerpetratorClientId(null).build();

    Allegation allegation2 = new AllegationEntityBuilder().setReferralId("ABC1234568")
        .setVictimClientId(null).setPerpetratorClientId(null).build();

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();
    Reporter reporter = new CmsReporterResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Reporter persistentReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporter, "0X5", new Date());

    Referral referral1 = new ReferralEntityBuilder().setId("ABC1234567").setReceivedDate(new Date())
        .setAllegations(Arrays.asList(allegation1).stream().collect(Collectors.toSet()))
        .setReporter(persistentReporter).build();
    Referral referral2 = new ReferralEntityBuilder().setId("ABC1234568").setReceivedDate(new Date())
        .setAllegations(Arrays.asList(allegation2).stream().collect(Collectors.toSet()))
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();

    referral1.setStaffPerson(staffPerson);
    referral2.setStaffPerson(staffPerson);

    Client client = new ClientEntityBuilder().build();
    ReferralClient referralCliemtDomain = new ReferralClientResourceBuilder().buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent1 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain, "OXA",
            new Date());
    persistent1.setReferral(referral1);

    ReferralClient referralCliemtDomain1 =
        new ReferralClientResourceBuilder().setReferralId("ABC1234568").buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent2 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain1, "OXA",
            new Date());
    persistent2.setReferral(referral2);
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {persistent1, persistent2};

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }


  @Test
  public void testHandleFindWhenNoClientIdsProvided() throws Exception {
    HOIRequest emptyRequest = new HOIRequest();
    emptyRequest.setClientIds(new HashSet<String>());
    HOIReferralResponse response = hoiService.handleFind(emptyRequest);
    assertThat(response, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = NotImplementedException.class)
  public void testHandleRequest() throws Exception {
    hoiService.handleRequest(new HOIReferral());
  }

  @Test(expected = AuthorizationException.class)
  public void testUnAuthorizedClient() {
    HOIReferralService spyTarget = spy(hoiService);
    request = new HOIRequest();
    request.setClientIds(Stream.of("unauthorizedId").collect(Collectors.toSet()));
    doThrow(AuthorizationException.class).when(spyTarget).authorizeClient("unauthorizedId");
    spyTarget.handleFind(request);
  }

  @Test(expected = AuthorizationException.class)
  public void testUnAuthorizedVictimClient() {
    HOIReferralService spyTarget = spy(hoiService);
    Allegation allegation =
        new AllegationEntityBuilder().setVictimClientId("unauthorizedId").build();
    Set<Allegation> allegations = Stream.of(allegation).collect(Collectors.toSet());
    Referral referral = new ReferralEntityBuilder().setAllegations(allegations).build();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient =
        new ReferralClientEntityBuilder().setReferral(referral).build();
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {referralClient};
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);
    doThrow(AuthorizationException.class).when(spyTarget).authorizeClient("unauthorizedId");
    spyTarget.handleFind(request);
  }

  @Test(expected = AuthorizationException.class)
  public void testUnAuthorizedPerpetratorClient() {
    HOIReferralService spyTarget = spy(hoiService);
    Allegation allegation =
        new AllegationEntityBuilder().setPerpetratorClientId("unauthorizedId").build();
    Set<Allegation> allegations = Stream.of(allegation).collect(Collectors.toSet());
    Referral referral = new ReferralEntityBuilder().setAllegations(allegations).build();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient =
        new ReferralClientEntityBuilder().setReferral(referral).build();
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {referralClient};
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);
    doThrow(AuthorizationException.class).when(spyTarget).authorizeClient("unauthorizedId");
    spyTarget.handleFind(request);
  }

}
