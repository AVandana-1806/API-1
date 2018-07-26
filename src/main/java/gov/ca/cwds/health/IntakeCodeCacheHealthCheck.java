package gov.ca.cwds.health;

import com.codahale.metrics.health.HealthCheck;

import gov.ca.cwds.rest.api.domain.IntakeCodeCache;

public class IntakeCodeCacheHealthCheck extends HealthCheck {

  public IntakeCodeCacheHealthCheck() {
    super();
  }

  @Override
  protected Result check() throws Exception {
    int lovCount = IntakeCodeCache.global().getAll().size();

    if (lovCount > 0) {
      return Result.healthy();
    } else {
      return Result.unhealthy("Intake LOV code cache is empty.");
    }
  }
}
