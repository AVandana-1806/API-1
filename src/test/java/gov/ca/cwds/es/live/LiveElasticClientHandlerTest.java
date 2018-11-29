package gov.ca.cwds.es.live;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class LiveElasticClientHandlerTest extends Doofenshmirtz<RawClient> {

  LiveElasticClientHandler target;
  CaresInterruptibleOperation rocket;
  ClientRelationshipDao dao;
  String[] keyList = {DEFAULT_CLIENT_ID};

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    when(rs.next()).thenReturn(true, true, false);

    dao = new ClientRelationshipDao(sessionFactory);
    rocket = mock(CaresInterruptibleOperation.class);
    target = new LiveElasticClientHandler(rocket, keyList);
  }

  @Test
  public void type() throws Exception {
    assertThat(LiveElasticClientHandler.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void mapReplicatedClient_A$PlacementHomeAddress() throws Exception {
    PlacementHomeAddress pha = mock(PlacementHomeAddress.class);
    target.mapReplicatedClient(pha);
  }

  @Test
  public void handleJdbcDone_A$Pair() throws Exception {
    target.handleJdbcDone();
  }

  @Test
  public void handleFinishRange_A$Pair() throws Exception {
    target.handleFinish();
  }

  @Test
  public void getResults_A$() throws Exception {
    List<ReplicatedClient> actual = target.getNormalizedObjects();
    List<ReplicatedClient> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void addAll_A$Collection() throws Exception {
    Collection<ReplicatedClient> collection = mock(Collection.class);
    target.addAll(collection);
  }

  @Test
  public void clear_A$() throws Exception {
    target.clear();
  }

  @Test
  public void prepAffectedClients_A$PreparedStatement$Pair() throws Exception {
    Pair<String, String> p = pair;
    target.prepPlacementClients(prepStmt);
  }

  @Test(expected = SQLException.class)
  public void prepAffectedClients_A$PreparedStatement$Pair_T$SQLException() throws Exception {
    bombResultSet();
    Pair<String, String> p = pair;
    target.prepPlacementClients(prepStmt);
  }

  @Test
  public void readPlacementAddress_A$PreparedStatement() throws Exception {
    target.readPlacementAddress(prepStmt);
  }

  @Test(expected = SQLException.class)
  public void readPlacementAddress_A$PreparedStatement_T$SQLException() throws Exception {
    bombResultSet();
    target.readPlacementAddress(prepStmt);
  }

  @Test
  public void getNormalized_A$() throws Exception {
    Map<String, ReplicatedClient> actual = target.getNormalized();
    Map<String, ReplicatedClient> expected = new HashMap<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void isDoneHandlerRetrieve_A$() throws Exception {
    boolean actual = target.isDoneHandlerRetrieve();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void doneRetrieve_A$() throws Exception {
    target.doneThreadRetrieve();
  }

  @Test
  public void read_A$PreparedStatement$Consumer() throws Exception {
    when(rocket.isRunning()).thenReturn(true);
    Consumer<ResultSet> consumer = mock(Consumer.class);
    target.read(prepStmt, consumer);
  }

  @Test
  public void readClient_A$ResultSet() throws Exception {
    RawClientTest.prepResultSetGood(rs);
    target.readClient(rs);
  }

  @Test
  public void readClientAddress_A$ResultSet() throws Exception {
    RawClientAddressTest.prepResultSetGood(rs);
    target.readClientAddress(rs);
  }

  @Test
  public void readAddress_A$ResultSet() throws Exception {
    target.readAddress(rs);
  }

  @Test
  public void readClientCounty_A$ResultSet() throws Exception {
    target.readClientCounty(rs);
  }

  @Test
  public void readAka_A$ResultSet() throws Exception {
    target.readAka(rs);
  }

  @Test
  public void readCase_A$ResultSet() throws Exception {
    target.readCase(rs);
  }

  @Test
  public void readCsec_A$ResultSet() throws Exception {
    target.readCsec(rs);
  }

  @Test
  public void readEthnicity_A$ResultSet() throws Exception {
    target.readEthnicity(rs);
  }

  @Test
  public void readSafetyAlert_A$ResultSet() throws Exception {
    target.readSafetyAlert(rs);
  }

  @Test
  public void handleMainResults_A$ResultSet$Connection() throws Exception {
    target.handleMain(con);
  }

  @Test(expected = CaresExceptionRuntime.class)
  public void handleMainResults_A$ResultSet$Connection_T$SQLException() throws Exception {
    bombResultSet();
    target.handleMain(con);
  }

  @Test
  public void prepPlacementClients_A$PreparedStatement$Pair() throws Exception {
    PreparedStatement stmt = mock(PreparedStatement.class);
    target.prepPlacementClients(stmt);
  }

  @Test(expected = SQLException.class)
  public void prepPlacementClients_A$PreparedStatement$Pair_T$SQLException() throws Exception {
    bombResultSet();
    target.prepPlacementClients(prepStmt);
  }

  @Test
  public void doneThreadRetrieve_A$() throws Exception {
    target.doneThreadRetrieve();
  }

}
