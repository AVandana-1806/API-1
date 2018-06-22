package gov.ca.cwds.rest;

import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.test.support.BaseDropwizardApplication;
import gov.ca.cwds.test.support.DatabaseHelper;
import io.dropwizard.setup.Environment;
import liquibase.exception.LiquibaseException;

/**
 * Testing support for ApiApplication.
 */
public class ApiApplicationTestSupport {

  private static BaseDropwizardApplication<ApiConfiguration> application;

  /**
   * Get application.
   * 
   * @return Application
   */
  public static synchronized BaseDropwizardApplication<ApiConfiguration> getApplication() {
    if (application == null) {
      application =
          new BaseDropwizardApplication<>(TestApiApplication.class, "config/test-api.yml");
    }
    return application;
  }

  /**
   * Setup database and load test data.
   * 
   * @param configuration
   * @throws LiquibaseException
   */
  public static void initializeDatabase(ApiConfiguration configuration) throws LiquibaseException {
    new gov.ca.cwds.test.support.DatabaseHelper(configuration.getNsDataSourceFactory().getUrl(),
        configuration.getNsDataSourceFactory().getUser(),
        configuration.getNsDataSourceFactory().getPassword())
            .runScript("liquibase/api/api_intake_ns_database_master.xml");

    new gov.ca.cwds.test.support.DatabaseHelper(configuration.getCmsDataSourceFactory().getUrl(),
        configuration.getCmsDataSourceFactory().getUser(),
        configuration.getCmsDataSourceFactory().getPassword())
            .runScript("liquibase/api/api_cwsint_database_master.xml");

    new DatabaseHelper(configuration.getRsDataSourceFactory().getUrl(),
        configuration.getRsDataSourceFactory().getUser(),
        configuration.getRsDataSourceFactory().getPassword())
            .runScript("liquibase/api/api_cwsrs_database_master.xml");
  }

  /**
   * Test ApiApplication that load tests data.
   */
  public static class TestApiApplication extends ApiApplication {

    @Override
    public void runInternal(ApiConfiguration configuration, Environment environment) {
      try {
        initializeDatabase(configuration);
      } catch (LiquibaseException e) {
        throw new ServiceException(e);
      }

      super.runInternal(configuration, environment);
    }
  }
}
