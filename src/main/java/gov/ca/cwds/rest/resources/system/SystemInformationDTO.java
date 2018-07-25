package gov.ca.cwds.rest.resources.system;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import gov.ca.cwds.dto.BaseDTO;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Intake Team 4
 */
@SuppressWarnings("squid:S2160")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SystemInformationDTO extends BaseDTO {

  private String application;
  private String version;
  private String build;
  private Map<String, HealthCheckResultDTO> healthChecks;

  public Map<String, HealthCheckResultDTO> getHealthChecks() {
    if (healthChecks == null) {
      healthChecks = new HashMap<>();
    }
    return healthChecks;
  }

  public String getApplication() {
    return application;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getBuild() {
    return build;
  }

  public void setBuild(String build) {
    this.build = build;
  }
}
