package gov.ca.cwds.rest.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.rest.RestStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.AllegationIntakeDao;
import gov.ca.cwds.data.ns.CrossReportDao;
import gov.ca.cwds.data.ns.ScreeningAddressDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.CrossReportEntity;
import gov.ca.cwds.data.persistence.ns.GovernmentAgencyEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningWrapper;
import gov.ca.cwds.fixture.ScreeningWrapperEntityBuilder;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningDashboard;
import gov.ca.cwds.rest.api.domain.ScreeningDashboardList;
import gov.ca.cwds.rest.api.domain.cms.AgencyType;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.mapper.AllegationMapper;
import gov.ca.cwds.rest.services.mapper.CrossReportMapper;
import gov.ca.cwds.rest.services.mapper.ScreeningMapper;
import gov.ca.cwds.rest.services.screening.participant.ParticipantService;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class ScreeningServiceTest extends Doofenshmirtz<ScreeningEntity> {

  private ScreeningService target;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private ElasticsearchDao esDao;

  @Mock
  private ScreeningDao screeningDao;

  @Mock
  private AllegationIntakeDao allegationDao;

  @Mock
  private Client esClient;

  @Mock
  private IndexRequestBuilder indexRequestBuilder;

  @Mock
  private IndexResponse indexResponse;

  @Mock
  private ElasticsearchConfiguration esConfig;

  @Mock
  private ScreeningMapper screeningMapper;

  @Mock
  private AllegationMapper allegationMapper;

  @Mock
  private CrossReportDao crossReportDao;

  CrossReportMapper crossReportMapper = CrossReportMapper.INSTANCE;

  @Mock
  private ScreeningAddressDao screeningAddressDao;

  @Mock
  private ParticipantService participantService;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    MockitoAnnotations.initMocks(this);

    when(esDao.getConfig()).thenReturn(esConfig);
    when(esDao.getClient()).thenReturn(esClient);
    when(esConfig.getElasticsearchAlias()).thenReturn("screenings");
    when(esConfig.getElasticsearchDocType()).thenReturn("screening");

    when(esClient.prepareIndex(any(), any(), any())).thenReturn(indexRequestBuilder);
    when(indexRequestBuilder.get()).thenReturn(indexResponse);

    final ScreeningEntity screeningEntity = new ScreeningEntity();
    when(screeningDao.find(any(Serializable.class))).thenReturn(screeningEntity);
    when(screeningDao.create(screeningEntity)).thenReturn(screeningEntity);

    final Screening screening = new Screening();
    when(screeningMapper.map(any(ScreeningEntity.class))).thenReturn(screening);
    when(screeningMapper.map(any(Screening.class))).thenReturn(screeningEntity);

    final List<CrossReportEntity> crossReportEntities = new ArrayList<>();
    when(crossReportDao.findByScreeningId(any(String.class))).thenReturn(crossReportEntities);

    target = new ScreeningService();
    target.setScreeningDao(screeningDao);
    target.setScreeningMapper(screeningMapper);
    target.setAllegationDao(allegationDao);
    target.setAllegationMapper(allegationMapper);
    target.setCrossReportDao(crossReportDao);
    target.setCrossReportMapper(crossReportMapper);
    target.setScreeningAddressDao(screeningAddressDao);
    target.setParticipantService(participantService);

    new TestingRequestExecutionContext("0X5");
  }

  @Test
  public void testFilterLastUpdatedByCategory() {
    List<GovernmentAgencyEntity> agencyEntities = new ArrayList<>();

    Date lastDate = new Date();
    Date notLastDate = DateUtils.addDays(lastDate, -1);

    agencyEntities.add(createAgencyEntity("1", AgencyType.DISTRICT_ATTORNEY, lastDate));
    agencyEntities.add(createAgencyEntity("2", AgencyType.DISTRICT_ATTORNEY, notLastDate));

    agencyEntities.add(createAgencyEntity("12", AgencyType.LAW_ENFORCEMENT, lastDate));
    agencyEntities.add(createAgencyEntity("11", AgencyType.LAW_ENFORCEMENT, notLastDate));

    agencyEntities.add(createAgencyEntity("21", AgencyType.COMMUNITY_CARE_LICENSING, lastDate));
    agencyEntities.add(createAgencyEntity("22", AgencyType.COMMUNITY_CARE_LICENSING, notLastDate));

    agencyEntities.add(createAgencyEntity("32", AgencyType.COUNTY_LICENSING, lastDate));
    agencyEntities.add(createAgencyEntity("31", AgencyType.COUNTY_LICENSING, notLastDate));

    ScreeningService screeningService = new ScreeningService();
    List<GovernmentAgencyEntity> filteredAgencyEntities =
        screeningService.filterLastUpdatedByCategory(agencyEntities);

    assertEquals(4, filteredAgencyEntities.size());

    assertEquals(1, filteredAgencyEntities.stream().filter(a -> "1".equals(a.getId())).count());
    assertEquals(1, filteredAgencyEntities.stream().filter(a -> "12".equals(a.getId())).count());
    assertEquals(1, filteredAgencyEntities.stream().filter(a -> "21".equals(a.getId())).count());
    assertEquals(1, filteredAgencyEntities.stream().filter(a -> "32".equals(a.getId())).count());
  }

  private GovernmentAgencyEntity createAgencyEntity(String id, AgencyType type, Date updateDate) {
    GovernmentAgencyEntity agencyEntity = new GovernmentAgencyEntity();
    agencyEntity.setId(id);
    agencyEntity.setCategory(type.name());
    agencyEntity.setUpdatedAt(updateDate);
    return agencyEntity;
  }

  @Test(expected = Exception.class)
  public void testFind() {
    target.find("abc");
  }

  @Test(expected = Exception.class)
  public void testDelete() {
    target.delete("abc");
    fail("Expected exception");
  }

  @Test
  public void testCreate() {
    when(indexResponse.status()).thenReturn(RestStatus.CREATED);
    final Screening screening =
        new Screening("abc", null, null, null, null, null, null, null, "0X5", "", "Open", null);
    final Screening actual = target.create(screening);
    assertThat(actual, is(screening));
  }

  @Test
  public void testUpdate() {
    when(indexResponse.status()).thenReturn(RestStatus.OK);
    final Screening screening =
        new Screening("abc", null, null, null, null, null, null, null, "0X5", "ssb", "Open", null);
    final Screening actual = target.update("abc", screening);
    assertThat(actual, is(screening));
  }

  @Test
  public void testUpdatePrimaryKeyValueMismatch() {
    final Screening screening =
        new Screening("abc", null, null, null, null, null, null, null, "0X5", "", "Open", null);
    try {
      target.update("abcd", screening);
      fail("Expected exception");
    } catch (Exception e) {
      assertThat(e.getMessage(), is("Primary key mismatch, [abcd != abc]"));
    }
  }

  @Test(expected = ServiceException.class)
  public void testUpdateRequestObjectTypMismatchn() {
    final Request request = new Screening();
    target.update("abc", request);
  }

  @Test
  public void testCreateRequestObjectTypMismatchn() {
    final Request request = new Screening();
    target.create(request);
  }

  @Test
  public void testFindScreeningDashboard() throws Exception {
    final ScreeningWrapper sw1 = new ScreeningWrapperEntityBuilder().build();
    final ScreeningWrapper sw2 = new ScreeningWrapperEntityBuilder().build();

    final List<ScreeningWrapper> screenings = new ArrayList<>();
    screenings.add(sw1);
    screenings.add(sw2);
    when(screeningDao.findScreeningsByUserId(any())).thenReturn(screenings);

    final ScreeningDashboardList sdl = (ScreeningDashboardList) target.findScreeningDashboard();
    final List<ScreeningDashboard> screeningDashboard = sdl.getScreeningDashboard();
    assertThat(screeningDashboard.size(), is(2));
  }

  @Test
  public void testFindScreeningDashboardWhenEmptyShouldBeZero() throws Exception {
    final List<ScreeningWrapper> screenings = new ArrayList<>();
    when(screeningDao.findScreeningsByUserId(any())).thenReturn(screenings);

    final ScreeningDashboardList sdl = (ScreeningDashboardList) target.findScreeningDashboard();
    final List<ScreeningDashboard> screeningDashboard = sdl.getScreeningDashboard();
    assertThat(screeningDashboard.size(), is(0));
  }

  @Test
  public void type() throws Exception {
    assertThat(ScreeningService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test(expected = NotImplementedException.class)
  public void find_A$Serializable() throws Exception {
    final Serializable primaryKey = DEFAULT_CLIENT_ID;
    final Response actual = target.find(primaryKey);
    final Response expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findScreeningDashboard_A$() throws Exception {
    final Response actual = target.findScreeningDashboard();
    assertThat(actual, is(notNullValue()));
  }

  @Test(expected = NotImplementedException.class)
  public void delete_A$Serializable() throws Exception {
    final Serializable primaryKey = DEFAULT_CLIENT_ID;
    final Response actual = target.delete(primaryKey);
    final Response expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void create_A$Request() throws Exception {
    final Request request = new Screening();
    final Screening actual = target.create(request);
    final Screening expected = null;
    assertThat(actual.getId(), is(equalTo(expected)));
  }

  @Test
  public void update_A$Serializable$Request() throws Exception {
    final Serializable primaryKey = DEFAULT_CLIENT_ID;
    final Screening request = new Screening();
    request.setId(DEFAULT_CLIENT_ID);

    final Screening actual = target.update(primaryKey, request);
    assertThat(actual.getId(), is(equalTo(DEFAULT_CLIENT_ID)));
  }

  @Test
  public void getScreening_A$String() throws Exception {
    final String id = DEFAULT_CLIENT_ID;
    final Screening actual = target.getScreening(id);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void createScreening_A$Screening() throws Exception {
    final Screening screening = new Screening();
    final Screening actual = target.createScreening(screening);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void updateScreening_A$String$Screening() throws Exception {
    final String id = DEFAULT_CLIENT_ID;
    final Screening request = new Screening();
    request.setId(DEFAULT_CLIENT_ID);

    final Screening actual = target.updateScreening(id, request);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void setEsDao_A$ElasticsearchDao() throws Exception {
    final ElasticsearchDao esDao = mock(ElasticsearchDao.class);
    target.setEsDao(esDao);
  }

  @Test
  public void setScreeningDao_A$ScreeningDao() throws Exception {
    target.setScreeningDao(screeningDao);
  }

}
