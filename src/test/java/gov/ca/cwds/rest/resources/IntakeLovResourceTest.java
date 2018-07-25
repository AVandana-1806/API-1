package gov.ca.cwds.rest.resources;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.testing.junit.ResourceTestRule;

public class IntakeLovResourceTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_INTAKE_LOV;

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new IntakeLovResource()).build();

  @Before
  public void setup() throws Exception {
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
    IntakeLovResource target = new IntakeLovResource();
    assertThat(target, notNullValue());
  }

}
