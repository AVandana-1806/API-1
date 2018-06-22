package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import gov.ca.cwds.config.CwdsAuthenticationClientConfig;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.server.TestingYmlConfig;

/**
 * To initialize the testConfig location.
 * 
 * @author CWDS API Team
 *
 */
public class TestModule extends AbstractModule {

  @Override
  protected void configure() {
    // An implementation of abstract module. No configuration is needed.
  }

  /**
   * getTestConfig.
   * 
   * @param configuration - configuration
   * @return the configuration
   */
  @Named("testConfig")
  @Provides
  public CwdsAuthenticationClientConfig getTestConfig(ApiConfiguration configuration) {
    return new TestingYmlConfig(configuration).createTestConfig();
  }

}
