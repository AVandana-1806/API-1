package gov.ca.cwds.rest.resources.system;

import static gov.ca.cwds.rest.core.Api.RESOURCE_SYSTEM_INFORMATION;

import java.io.InputStream;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.dto.app.SystemInformationDto;
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
public class SystemInformationResource extends AbstractSystemInformationResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(SystemInformationResource.class);

  private static final String API_BUILD = "Api-Build";
  private static final String API_VERSION = "Api-Version";
  private static final String N_A = "N/A";

  private final String applicationName;
  private final String applicationVersion;
  private final String buildNumber;
  private final String gitCommit;
  private final boolean healthStatus;

  /**
   * @param configuration - configuration
   * @param environment - environment
   */
  @Inject
  public SystemInformationResource(final ApiConfiguration configuration,
      final Environment environment) {
    super(environment.healthChecks());
    this.applicationName = configuration.getApplicationName();
    final Attributes manifestProperties = getManifestProperties();
    String value = manifestProperties.getValue(API_VERSION);
    this.applicationVersion = StringUtils.isBlank(value) ? N_A : value;
    value = manifestProperties.getValue(API_BUILD);
    this.buildNumber = StringUtils.isBlank(value) ? N_A : value;
    this.gitCommit = N_A;
    this.healthStatus = Boolean.TRUE;
  }

  /**
   * Get the name of the application.
   *
   * @return the application name
   */
  @Override
  @GET
  @ApiOperation(value = "Returns System Information", response = SystemInformationDto.class)
  public SystemInformationDto get() {
    final SystemInformationDto systemInformation = super.get();
    systemInformation.setApplicationName(applicationName);
    systemInformation.setVersion(applicationVersion);
    systemInformation.setBuildNumber(buildNumber);
    systemInformation.setHealthStatus(healthStatus);
    systemInformation.setGitCommitHash(gitCommit);
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
