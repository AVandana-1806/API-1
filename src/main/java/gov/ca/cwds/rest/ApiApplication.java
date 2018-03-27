package gov.ca.cwds.rest;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.google.inject.Injector;
import com.google.inject.Module;

import gov.ca.cwds.health.AuthHealthCheck;
import gov.ca.cwds.health.DB2HealthCheck;
import gov.ca.cwds.health.SwaggerHealthCheck;
import gov.ca.cwds.health.resource.AuthServer;
import gov.ca.cwds.health.resource.DB2Database;
import gov.ca.cwds.health.resource.SwaggerEndpoint;
import gov.ca.cwds.inject.ApplicationModule;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.filters.RequestExecutionContextFilter;
import gov.ca.cwds.rest.filters.RequestResponseLoggingFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jetty.NonblockingServletHolder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Core execution class of CWDS REST API server application.
 *
 * <h3>Standard command line arguments:</h3>
 *
 * <blockquote> server config/api.yml </blockquote>
 *
 * <h3>Standard JVM arguments:</h3>
 *
 * <blockquote> -Ddb2.jcc.charsetDecoderEncoder=3
 * -Djava.library.path=${workspace_loc:CWDS_API}/lib:/usr/local/lib/ </blockquote>
 *
 * @author CWDS API Team
 */
public class ApiApplication extends BaseApiApplication<ApiConfiguration> {

  private static final String LIQUIBASE_INTAKE_NS_DATABASE_MASTER_XML =
      "liquibase/intake_ns_database_master.xml";
  private static final String HIBERNATE_DEFAULT_SCHEMA_PROPERTY_NAME = "hibernate.default_schema";

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(ApiApplication.class);

  /**
   * Start the CWDS RESTful API application.
   *
   * @param args command line
   * @throws Exception if startup fails
   */
  public static void main(final String[] args) throws Exception {
    new ApiApplication().run(args);
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.BaseApiApplication#applicationModule(io.dropwizard.setup.Bootstrap)
   */
  @Override
  public Module applicationModule(Bootstrap<ApiConfiguration> bootstrap) {
    return new ApplicationModule(bootstrap);
  }

  @Override
  public void runInternal(final ApiConfiguration configuration, final Environment environment) {
    if (configuration.isUpgradeDbOnStart()) {
      upgradeNsDb(configuration);
    }

    environment.getApplicationContext().setAttribute(MetricsServlet.METRICS_REGISTRY,
        environment.metrics());
    environment.getApplicationContext().setAttribute(HealthCheckServlet.HEALTH_CHECK_REGISTRY,
        environment.healthChecks());
    environment.getApplicationContext().addServlet(new NonblockingServletHolder(new AdminServlet()),
        "/admin/*");

    Injector injector = guiceBundle.getInjector();
    environment.servlets()
        .addFilter("RequestExecutionContextManagingFilter",
            injector.getInstance(RequestExecutionContextFilter.class))
        .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

    environment.servlets()
        .addFilter("AuditAndLoggingFilter",
            injector.getInstance(RequestResponseLoggingFilter.class))
        .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

    final DB2HealthCheck db2HealthCheck =
        new DB2HealthCheck(injector.getInstance(DB2Database.class));
    environment.healthChecks().register("db2_status", db2HealthCheck);

    final AuthHealthCheck authHealthCheck =
        new AuthHealthCheck(injector.getInstance(AuthServer.class));
    environment.healthChecks().register("auth_status", authHealthCheck);

    final SwaggerHealthCheck swaggerHealthCheck =
        new SwaggerHealthCheck(injector.getInstance(SwaggerEndpoint.class));
    environment.healthChecks().register("swagger_status", swaggerHealthCheck);

    injector.getInstance(SystemCodeCache.class);
  }

  private void upgradeNsDb(ApiConfiguration configuration) {
    LOGGER.info("Upgrading INTAKE_NS DB...");

    DataSourceFactory nsDataSourceFactory = configuration.getNsDataSourceFactory();
    DatabaseHelper databaseHelper = new DatabaseHelper(nsDataSourceFactory.getUrl(),
        nsDataSourceFactory.getUser(), nsDataSourceFactory.getPassword());
    try {
      databaseHelper.runScript(LIQUIBASE_INTAKE_NS_DATABASE_MASTER_XML,
          nsDataSourceFactory.getProperties().get(HIBERNATE_DEFAULT_SCHEMA_PROPERTY_NAME));
    } catch (Exception e) {
      LOGGER.error("Upgarding of INTAKE_NS DB is failed. ", e);
    }

    LOGGER.info("Finish Upgrading INTAKE_NS DB");
  }
}
