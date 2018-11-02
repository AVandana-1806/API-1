package gov.ca.cwds.rest;

import java.io.File;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import javax.servlet.DispatcherType;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.google.inject.Injector;
import com.google.inject.Module;
import gov.ca.cwds.data.ns.PaperTrailDao;
import gov.ca.cwds.health.AuthHealthCheck;
import gov.ca.cwds.health.IntakeCodeCacheHealthCheck;
import gov.ca.cwds.health.LovHealthCheck;
import gov.ca.cwds.health.MQTHealthCheck;
import gov.ca.cwds.health.SpGenclncntyHealthCheck;
import gov.ca.cwds.health.SpSpssaname3HealthCheck;
import gov.ca.cwds.health.SwaggerHealthCheck;
import gov.ca.cwds.health.SystemCodeCacheHealthCheck;
import gov.ca.cwds.health.SystemCodeHealthCheck;
import gov.ca.cwds.health.TriggerHealthCheck;
import gov.ca.cwds.health.ViewsHealthCheck;
import gov.ca.cwds.health.resource.AuthServer;
import gov.ca.cwds.health.resource.IntakeLovCheck;
import gov.ca.cwds.health.resource.MQTExistCheck;
import gov.ca.cwds.health.resource.SpGenclncntyExistCheck;
import gov.ca.cwds.health.resource.SpSpssaname3ExistCheck;
import gov.ca.cwds.health.resource.SwaggerEndpoint;
import gov.ca.cwds.health.resource.SystemCodeCheck;
import gov.ca.cwds.health.resource.TriggerExistCheck;
import gov.ca.cwds.health.resource.ViewExistCheck;
import gov.ca.cwds.inject.ApplicationModule;
import gov.ca.cwds.inject.InjectorHolder;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.core.Api;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiApplication.class);

  private static final String LIQUIBASE_INTAKE_NS_DATABASE_MASTER_XML =
      "liquibase/intake_ns_database_master.xml";
  private static final String HIBERNATE_DEFAULT_SCHEMA_PROPERTY_NAME = "hibernate.default_schema";

  private ApplicationModule applicationModule;

  /**
   * Start the CWDS RESTful API application.
   *
   * @param args command line
   * @throws Exception if startup fails
   */
  public static void main(final String[] args) throws Exception {
    LOGGER.info("\n\n\t**** Starting Ferb. More Phineas, less Candace ****\n");
    new ApiApplication().run(args);
  }

  protected void nukeXaFiles() {
    // Clean up XA transaction log files.
    final String[] extensions = {"log", "lck"};
    final Collection<File> tmFiles =
        FileUtils.listFiles(new File(System.getProperty("user.dir")), extensions, false);
    LOGGER.info("XA transaction files: {}", tmFiles);
    tmFiles.stream().forEach(File::delete);
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.BaseApiApplication#applicationModule(io.dropwizard.setup.Bootstrap)
   */
  @Override
  public Module applicationModule(Bootstrap<ApiConfiguration> bootstrap) {
    applicationModule = new ApplicationModule(bootstrap);
    return applicationModule;
  }

  @Override
  public void runInternal(final ApiConfiguration configuration, final Environment environment) {
    nukeXaFiles();
    if (configuration.isUpgradeDbOnStart()) {
      upgradeNsDb(configuration);
    }

    environment.getApplicationContext().setAttribute(MetricsServlet.METRICS_REGISTRY,
        environment.metrics());
    environment.getApplicationContext().setAttribute(HealthCheckServlet.HEALTH_CHECK_REGISTRY,
        environment.healthChecks());
    environment.getApplicationContext().addServlet(new NonblockingServletHolder(new AdminServlet()),
        "/admin/*");

    final Injector injector = guiceBundle.getInjector();
    environment.servlets()
        .addFilter("RequestExecutionContextManagingFilter",
            injector.getInstance(RequestExecutionContextFilter.class))
        .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

    environment.servlets()
        .addFilter("AuditAndLoggingFilter",
            injector.getInstance(RequestResponseLoggingFilter.class))
        .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

    // -----------------------
    // Health checks:
    // -----------------------

    registerHealthChecks(injector, environment);

    // -----------------------
    // Specialty singletons:
    // -----------------------

    injector.getInstance(SystemCodeCache.class);
    injector.getInstance(IntakeCodeCache.class);

    // ERROR: "binder can only be called inside configure" -- but can't call it in configure().
    // Chicken and egg dilemma: HibernateBundle demands that Hibernate interceptors be constructed
    // ***before*** DAO's, entities, session factories, etc.
    // Without succumbing to convoluted Guice listeners, "assisted injection", or static methods,
    // this is the best we can do.
    // BETTER: inject a **delegate** with all dependencies.
    final PaperTrailDao paperTrailDao = InjectorHolder.INSTANCE.getInstance(PaperTrailDao.class);
    applicationModule.getDataAccessModule().getPaperTrailInterceptor()
        .setPaperTrailDao(paperTrailDao);
    LOGGER.info("PaperTrailInterceptor: {}",
        applicationModule.getDataAccessModule().getPaperTrailInterceptor());

    final Map<String, String> env = System.getenv();
    LOGGER.info(
        "\n\n******************* ENVIRONMENT VARIABLES ***********************************\n");
    for (Map.Entry<String, String> entry : env.entrySet()) {
      LOGGER.info("{}={}", entry.getKey(), entry.getValue());
    }
  }

  private void upgradeNsDb(ApiConfiguration configuration) {
    LOGGER.info("Upgrading INTAKE_NS DB...");

    final DataSourceFactory nsDataSourceFactory = configuration.getNsDataSourceFactory();
    final DatabaseHelper databaseHelper = new DatabaseHelper(nsDataSourceFactory.getUrl(),
        nsDataSourceFactory.getUser(), nsDataSourceFactory.getPassword());
    try {
      databaseHelper.runScript(LIQUIBASE_INTAKE_NS_DATABASE_MASTER_XML,
          nsDataSourceFactory.getProperties().get(HIBERNATE_DEFAULT_SCHEMA_PROPERTY_NAME));
    } catch (Exception e) {
      LOGGER.error("INTAKE_NS DB UPGRADE FAILED!", e);
      throw new ApiException("INTAKE_NS DB UPGRADE FAILED", e);
    }

    LOGGER.info("Finished Upgrading INTAKE_NS DB");
  }
  
  private void registerHealthChecks(final Injector injector, final Environment environment) {
    final AuthHealthCheck authHealthCheck =
        new AuthHealthCheck(injector.getInstance(AuthServer.class));
    environment.healthChecks().register(Api.HealthCheck.AUTH_STATUS, authHealthCheck);

    final LovHealthCheck lovHealthCheck =
        new LovHealthCheck(injector.getInstance(IntakeLovCheck.class));
    environment.healthChecks().register(Api.HealthCheck.LOV_STATUS, lovHealthCheck);

    final SystemCodeHealthCheck sysCodesHealthCheck =
        new SystemCodeHealthCheck(injector.getInstance(SystemCodeCheck.class));
    environment.healthChecks().register(Api.HealthCheck.SYSTEM_CODES_STATUS, sysCodesHealthCheck);

    environment.healthChecks().register(Api.HealthCheck.INTAKE_LOV_CODE_CACHE_STATUS,
        new IntakeCodeCacheHealthCheck());

    environment.healthChecks().register(Api.HealthCheck.SYSTEM_CODE_CACHE_STATUS,
        new SystemCodeCacheHealthCheck());

    final MQTHealthCheck mQTHealthCheck =
        new MQTHealthCheck(injector.getInstance(MQTExistCheck.class));
    environment.healthChecks().register(Api.HealthCheck.MQT_STATUS, mQTHealthCheck);

    final ViewsHealthCheck viewsHealthCheck =
        new ViewsHealthCheck(injector.getInstance(ViewExistCheck.class));
    environment.healthChecks().register(Api.HealthCheck.VIEW_STATUS, viewsHealthCheck);

    final TriggerHealthCheck triggerHealthCheck =
        new TriggerHealthCheck(injector.getInstance(TriggerExistCheck.class));
    environment.healthChecks().register(Api.HealthCheck.TRIGGER_STATUS, triggerHealthCheck);

    final SpGenclncntyHealthCheck spGenclncntyHealthCheck =
        new SpGenclncntyHealthCheck(injector.getInstance(SpGenclncntyExistCheck.class));
    environment.healthChecks().register(Api.HealthCheck.SP_GENCLNCNTY_STATUS,
        spGenclncntyHealthCheck);

    //
    // SP_SPSSANAME3_STATUS is disabled to fix this Jira:
    // https://osi-cwds.atlassian.net/browse/DS-1427
    //
//    final SpSpssaname3HealthCheck spSpssaname3HealthCheck =
//        new SpSpssaname3HealthCheck(injector.getInstance(SpSpssaname3ExistCheck.class));
//    environment.healthChecks().register(Api.HealthCheck.SP_SPSSANAME3_STATUS,
//        spSpssaname3HealthCheck);

    final SwaggerHealthCheck swaggerHealthCheck =
        new SwaggerHealthCheck(injector.getInstance(SwaggerEndpoint.class));
    environment.healthChecks().register(Api.HealthCheck.SWAGGER_STATUS, swaggerHealthCheck);
  }

}
