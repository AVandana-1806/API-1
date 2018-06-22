package gov.ca.cwds.rest.resources.system;


import com.codahale.metrics.health.HealthCheck;
import gov.ca.cwds.dto.BaseDTO;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Intake Team 4
 */
@SuppressWarnings("squid:S2160")
public class HealthCheckResultDTO extends BaseDTO {

  private boolean healthy;
  private String message;
  private Throwable error;
  private HashMap<String, Object> details;
  private String timestamp;

  //Restrict instantiating without Result
  private HealthCheckResultDTO() {
  }

  public HealthCheckResultDTO(HealthCheck.Result result) {
    healthy = result.isHealthy();
    message = result.getMessage();
    error = result.getError();
    details = result.getDetails() == null ? null : new HashMap<>(result.getDetails());
    timestamp = result.getTimestamp();
  }

  public boolean isHealthy() {
    return healthy;
  }

  public String getMessage() {
    return message;
  }

  public Throwable getError() {
    return error;
  }

  public Map<String, Object> getDetails() {
    return details;
  }

  public String getTimestamp() {
    return timestamp;
  }
}
