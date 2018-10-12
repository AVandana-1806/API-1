package gov.ca.cwds.rest.resources.system;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.ApiConfiguration;
import io.dropwizard.setup.Environment;

/**
 * @author CWDS API Team
 *
 */
public class SystemInformationResourceTest {

  SystemInformationResource systemInformationResource;
  ApiConfiguration configuration;
  Environment environment;

  /**
   * 
   */
  @Before
  public void setup() {
    configuration = mock(ApiConfiguration.class);
    environment = mock(Environment.class);
    systemInformationResource = new SystemInformationResource(configuration, environment);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void type() throws Exception {
    assertThat(SystemInformationResource.class, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void instantiation() throws Exception {
    SystemInformationResource target = new SystemInformationResource(configuration, environment);
    assertThat(target, notNullValue());
  }

}
