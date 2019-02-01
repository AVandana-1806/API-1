package gov.ca.cwds.rest.resources.system;

import com.codahale.metrics.health.HealthCheck.Result;
import gov.ca.cwds.rest.core.Api;
import java.util.Map;

/**
 * This strategy makes swagger health check optional.
 * Optional health check of a component means that if the component is not healthy then the common app. health status is still counted as healthy.
 * That is fine for components that are not critical for app. to function properly.
 * In this case, swagger is a developer's UI that could be needed for troubleshooting, but is not needed for Ferb endpoints to work.
 * We do not to remove the swagger health check completely but leave it there as optional,
 * because in case if swagger is down we could easily see the error message and catch the reason faster.
 */
public class FerbSystemHealthStatusStrategy implements SystemHealthStatusStrategy {

  @Override
  public boolean getSystemHealthStatus(Map<String, Result> healthChecks) {
    return healthChecks.entrySet().stream().filter(e -> !e.getKey().equals(Api.HealthCheck.SWAGGER_STATUS))
        .allMatch(e -> e.getValue().isHealthy());
  }
}

