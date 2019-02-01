package gov.ca.cwds.rest.resources.system;

import com.codahale.metrics.health.HealthCheck.Result;
import gov.ca.cwds.rest.core.Api;
import java.util.Map;

public class FerbSystemHealthStatusStrategy implements SystemHealthStatusStrategy {

  @Override
  public boolean getSystemHealthStatus(Map<String, Result> healthChecks) {
    return healthChecks.entrySet().stream().filter(e -> !e.equals(Api.HealthCheck.SWAGGER_STATUS))
        .allMatch(e -> e.getValue().isHealthy());
  }
}

