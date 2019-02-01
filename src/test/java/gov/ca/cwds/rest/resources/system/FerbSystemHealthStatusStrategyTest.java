package gov.ca.cwds.rest.resources.system;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.codahale.metrics.health.HealthCheck.Result;
import gov.ca.cwds.rest.core.Api;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class FerbSystemHealthStatusStrategyTest {

  private FerbSystemHealthStatusStrategy healthStatusStrategy = new FerbSystemHealthStatusStrategy();

  @Test
  public void testSystemHealthStatusFalseWhenDeadlocks() {
    Map<String, Result> healthChecks = prepareHealthChecks();
    healthChecks.put(Api.HealthCheck.DEADLOCKS, Result.unhealthy(""));
    assertFalse(healthStatusStrategy.getSystemHealthStatus(healthChecks));
  }

  @Test
  public void testSystemHealthStatusFalseWhenAuthNotHealthy() {
    Map<String, Result> healthChecks = prepareHealthChecks();
    healthChecks.put(Api.HealthCheck.AUTH_STATUS, Result.unhealthy(""));
    assertFalse(healthStatusStrategy.getSystemHealthStatus(healthChecks));
  }

  @Test
  public void testSystemHealthStatusTrueWhenSwaggerNotHealthy() {
    Map<String, Result> healthChecks = prepareHealthChecks();
    healthChecks.put(Api.HealthCheck.SWAGGER_STATUS, Result.unhealthy(""));
    assertTrue(healthStatusStrategy.getSystemHealthStatus(healthChecks));
  }

  @Test
  public void testSystemHealthStatusFalseWhenLovNotHealthy() {
    Map<String, Result> healthChecks = prepareHealthChecks();
    healthChecks.put(Api.HealthCheck.LOV_STATUS, Result.unhealthy(""));
    assertFalse(healthStatusStrategy.getSystemHealthStatus(healthChecks));
  }

  @Test
  public void testSystemHealthStatusFalseWhenSystemCodesNotHealthy() {
    Map<String, Result> healthChecks = prepareHealthChecks();
    healthChecks.put(Api.HealthCheck.SYSTEM_CODES_STATUS, Result.unhealthy(""));
    assertFalse(healthStatusStrategy.getSystemHealthStatus(healthChecks));
  }

  @Test
  public void testSystemHealthStatusFalseWhenIntakeLovCodeCacheNotHealthy() {
    Map<String, Result> healthChecks = prepareHealthChecks();
    healthChecks.put(Api.HealthCheck.INTAKE_LOV_CODE_CACHE_STATUS, Result.unhealthy(""));
    assertFalse(healthStatusStrategy.getSystemHealthStatus(healthChecks));
  }

  @Test
  public void testSystemHealthStatusFalseWhenSystemCodeCacheNotHealthy() {
    Map<String, Result> healthChecks = prepareHealthChecks();
    healthChecks.put(Api.HealthCheck.SYSTEM_CODE_CACHE_STATUS, Result.unhealthy(""));
    assertFalse(healthStatusStrategy.getSystemHealthStatus(healthChecks));
  }

  @Test
  public void testSystemHealthStatusFalseWhenMqtNotHealthy() {
    Map<String, Result> healthChecks = prepareHealthChecks();
    healthChecks.put(Api.HealthCheck.MQT_STATUS, Result.unhealthy(""));
    assertFalse(healthStatusStrategy.getSystemHealthStatus(healthChecks));
  }

  @Test
  public void testSystemHealthStatusFalseWhenViewsNotHealthy() {
    Map<String, Result> healthChecks = prepareHealthChecks();
    healthChecks.put(Api.HealthCheck.VIEW_STATUS, Result.unhealthy(""));
    assertFalse(healthStatusStrategy.getSystemHealthStatus(healthChecks));
  }

  @Test
  public void testSystemHealthStatusFalseWhenTriggersNotHealthy() {
    Map<String, Result> healthChecks = prepareHealthChecks();
    healthChecks.put(Api.HealthCheck.TRIGGER_STATUS, Result.unhealthy(""));
    assertFalse(healthStatusStrategy.getSystemHealthStatus(healthChecks));
  }

  @Test
  public void testSystemHealthStatusFalseWhenClientCountyNotHealthy() {
    Map<String, Result> healthChecks = prepareHealthChecks();
    healthChecks.put(Api.HealthCheck.SP_GENCLNCNTY_STATUS, Result.unhealthy(""));
    assertFalse(healthStatusStrategy.getSystemHealthStatus(healthChecks));
  }

  private Map<String, Result> prepareHealthChecks() {
    Map<String, Result> healthChecks = new HashMap<>();
    healthChecks.put(Api.HealthCheck.DEADLOCKS, Result.healthy());
    healthChecks.put(Api.HealthCheck.AUTH_STATUS, Result.healthy());
    healthChecks.put(Api.HealthCheck.SWAGGER_STATUS, Result.healthy());
    healthChecks.put(Api.HealthCheck.LOV_STATUS, Result.healthy());
    healthChecks.put(Api.HealthCheck.SYSTEM_CODES_STATUS, Result.healthy());
    healthChecks.put(Api.HealthCheck.INTAKE_LOV_CODE_CACHE_STATUS, Result.healthy());
    healthChecks.put(Api.HealthCheck.SYSTEM_CODE_CACHE_STATUS, Result.healthy());
    healthChecks.put(Api.HealthCheck.MQT_STATUS, Result.healthy());
    healthChecks.put(Api.HealthCheck.VIEW_STATUS, Result.healthy());
    healthChecks.put(Api.HealthCheck.TRIGGER_STATUS, Result.healthy());
    healthChecks.put(Api.HealthCheck.SP_GENCLNCNTY_STATUS, Result.healthy());
    return healthChecks;
  }
}
