package gov.ca.cwds.health;

import com.codahale.metrics.health.HealthCheck;

import gov.ca.cwds.rest.api.domain.IntakeCodeCache;

public class IntakeCodeCacheHealthCheck extends HealthCheck {

  private static final long MINIMUM_LOVS = 531;

  public IntakeCodeCacheHealthCheck() {
    super();
  }

  @Override
  protected Result check() throws Exception {
    int lovCount = IntakeCodeCache.global().getAll().size();

    String message = "Expected minimum LOVs " + MINIMUM_LOVS + ", found " + lovCount;

    if (lovCount >= MINIMUM_LOVS) {
      return Result.healthy(message);
    } else {
      return Result.unhealthy(message);
    }
  }
}
