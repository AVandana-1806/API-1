package gov.ca.cwds.rest.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
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
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningWrapper;
import gov.ca.cwds.fixture.ScreeningWrapperEntityBuilder;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningDashboard;
import gov.ca.cwds.rest.api.domain.ScreeningDashboardList;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
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
  private Client esClient;

  @Mock
  private IndexRequestBuilder indexRequestBuilder;

  @Mock
  private IndexResponse indexResponse;

  @Mock
  private ElasticsearchConfiguration esConfig;

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

    target = new ScreeningService();
    target.setEsDao(esDao);
    target.setScreeningDao(screeningDao);

    new TestingRequestExecutionContext("0X5");
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

  @Test
  public void testUpdatePrimaryKeyObjectTypMismatchn() {
    try {
      target.update(new Integer(1), null);
      fail("Expected exception");
    } catch (java.lang.AssertionError e) {
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
    try {
      target.create(request);
      fail("Expected exception");
    } catch (java.lang.AssertionError e) {
    }
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

  @Test(expected = NullPointerException.class)
  public void create_A$Request() throws Exception {
    final Request request = new Screening();
    final Screening actual = target.create(request);
    final Screening expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void update_A$Serializable$Request() throws Exception {
    final Serializable primaryKey = DEFAULT_CLIENT_ID;
    final Request request = new Screening();
    final Screening actual = target.update(primaryKey, request);
    final Screening expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getScreening_A$String() throws Exception {
    final String id = DEFAULT_CLIENT_ID;
    final Screening actual = target.getScreening(id);
    final Screening expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createScreening_A$Screening() throws Exception {
    final Screening screening = new Screening();
    final Screening actual = target.createScreening(screening);
    final Screening expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void updateScreening_A$String$Screening() throws Exception {
    final String id = DEFAULT_CLIENT_ID;
    final Screening screening = new Screening();

    final Screening actual = target.updateScreening(id, screening);
    final Screening expected = null;
    assertThat(actual, is(equalTo(expected)));
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
