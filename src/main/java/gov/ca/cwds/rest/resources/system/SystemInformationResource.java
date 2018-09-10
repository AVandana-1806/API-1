package gov.ca.cwds.rest.resources.system;

import static gov.ca.cwds.rest.core.Api.RESOURCE_SYSTEM_INFORMATION;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.health.HealthCheck.Result;
import com.google.inject.Inject;

import gov.ca.cwds.rest.ApiConfiguration;
import io.dropwizard.setup.Environment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Display Ferb version and health checks.
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_SYSTEM_INFORMATION)
@Path(RESOURCE_SYSTEM_INFORMATION)
@Produces(MediaType.APPLICATION_JSON)
public class SystemInformationResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(SystemInformationResource.class);

  private static final String API_BUILD = "Api-Build";
  private static final String API_VERSION = "Api-Version";
  private static final String N_A = "N/A";

  private final String applicationName;
  private final String applicationVersion;
  private final Environment environment;
  private final String buildNumber;

  @Inject
  public SystemInformationResource(final ApiConfiguration configuration,
      final Environment environment) {
    this.applicationName = configuration.getApplicationName();
    this.environment = environment;
    final Attributes manifestProperties = getManifestProperties();
    String value = manifestProperties.getValue(API_VERSION);
    this.applicationVersion = StringUtils.isBlank(value) ? N_A : value;
    value = manifestProperties.getValue(API_BUILD);
    this.buildNumber = StringUtils.isBlank(value) ? N_A : value;
  }

  /**
   * Get the name of the application.
   *
   * @return the application name
   */
  @GET
  @ApiOperation(value = "Returns System Information", response = SystemInformationDTO.class)
  public SystemInformationDTO get() {
    final SystemInformationDTO systemInformation = new SystemInformationDTO();
    systemInformation.setApplication(applicationName);
    systemInformation.setVersion(applicationVersion);
    systemInformation.setBuild(buildNumber);

    final Map<String, Result> healthCheckResults = environment.healthChecks().runHealthChecks();
    for (Map.Entry<String, Result> resultEntry : healthCheckResults.entrySet()) {
      systemInformation.getHealthChecks().put(resultEntry.getKey(),
          new HealthCheckResultDTO(resultEntry.getValue()));
    }
    return systemInformation;
  }

  private Attributes getManifestProperties() {
    Attributes attributes = new Attributes();
    String resource = "/" + this.getClass().getName().replace('.', '/') + ".class";
    String fullPath = this.getClass().getResource(resource).toExternalForm();
    String archivePath = fullPath.substring(0, fullPath.length() - resource.length());
    if (archivePath.endsWith("\\WEB-INF\\classes") || archivePath.endsWith("/WEB-INF/classes")) {
      // Required for WAR files.
      archivePath = archivePath.substring(0, archivePath.length() - "/WEB-INF/classes".length());
    }
    try (InputStream input = new URL(archivePath + "/META-INF/MANIFEST.MF").openStream()) {
      attributes = new Manifest(input).getMainAttributes();
    } catch (Exception e) {
      LOGGER.error("Loading properties from MANIFEST failed! {}", e);
    }
    return attributes;
  }
}
