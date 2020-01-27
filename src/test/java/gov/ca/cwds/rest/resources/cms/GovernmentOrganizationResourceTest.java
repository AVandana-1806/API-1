package gov.ca.cwds.rest.resources.cms;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganization;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationResponse;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationService;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 *
 */
public class GovernmentOrganizationResourceTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_GOVERNMENT_ORG;

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static SimpleResourceDelegate<String, GovernmentOrganization, GovernmentOrganizationResponse, GovernmentOrganizationService> simpleResourceDelegate =
      mock(SimpleResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new GovernmentOrganizationResource(simpleResourceDelegate)).build();

  /**
   * @throws Exception - Exception
   */
  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    Mockito.reset(simpleResourceDelegate);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void type() throws Exception {
    assertThat(GovernmentOrganizationResource.class, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void instantiation() throws Exception {
    GovernmentOrganizationResource target =
        new GovernmentOrganizationResource(simpleResourceDelegate);
    assertThat(target, notNullValue());
  }
}
