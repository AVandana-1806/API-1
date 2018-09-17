package gov.ca.cwds.rest.resources;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.data.persistence.xa.CandaceDatasourceSlate;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.util.Doofenshmirtz;
import io.dropwizard.testing.junit.ResourceTestRule;

public class IntakeLovResourceTest extends Doofenshmirtz<IntakeLov> {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_INTAKE_LOV;

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  protected static SessionFactory sf;

  protected static CandaceDatasourceSlate slate;

  static {
    sf = mock(SessionFactory.class);
    slate = new CandaceDatasourceSlate(sf, sf, sf);
  }

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new IntakeLovResource(slate)).build();

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get();
    // verify(resourceDelegate).handle(any());
  }

  @Test
  public void type() throws Exception {
    assertThat(IntakeLovResource.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    final IntakeLovResource target = new IntakeLovResource(slate);
    assertThat(target, notNullValue());
  }

}
