package gov.ca.cwds.es.live;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class LiveElasticClientServiceTest extends Doofenshmirtz<RawClient> {

  ClientRelationshipDao dao;
  LiveElasticClientService target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    dao = new ClientRelationshipDao(sessionFactory);
    target = new LiveElasticClientService(dao);
  }

  @Test
  public void type() throws Exception {
    assertThat(LiveElasticClientService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void setSysPropsFromEnvVars_A$() throws Exception {
    LiveElasticClientService.setSysPropsFromEnvVars();
  }

  @Test
  public void handleRequest_A$LiveElasticClientRequest() throws Exception {
    LiveElasticClientRequest req = mock(LiveElasticClientRequest.class);
    LiveElasticClientResponse actual = target.handleRequest(req);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void handleFind_A$StringArray() throws Exception {
    String[] keys = new String[] {DEFAULT_CLIENT_ID};
    LiveElasticClientResponse actual = target.handleFind(keys);
    // LiveElasticClientResponse expected = null;
    // assertThat(actual, is(equalTo(expected)));
    assertThat(actual, is(notNullValue()));
  }

  @Test(expected = NotImplementedException.class)
  public void create_A$LiveElasticClientRequest() throws Exception {
    LiveElasticClientRequest you = mock(LiveElasticClientRequest.class);
    LiveElasticClientResponse actual = target.create(you);
    LiveElasticClientResponse expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = NotImplementedException.class)
  public void delete_A$StringArray() throws Exception {
    String[] found = new String[] {};
    LiveElasticClientResponse actual = target.delete(found);
    LiveElasticClientResponse expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = NotImplementedException.class)
  public void update_A$StringArray$LiveElasticClientRequest() throws Exception {
    String[] secret = new String[] {};
    LiveElasticClientRequest message = mock(LiveElasticClientRequest.class);
    LiveElasticClientResponse actual = target.update(secret, message);
    LiveElasticClientResponse expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
