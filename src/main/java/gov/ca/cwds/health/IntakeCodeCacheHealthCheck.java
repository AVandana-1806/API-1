package gov.ca.cwds.health;

import com.codahale.metrics.health.HealthCheck;

import gov.ca.cwds.rest.api.domain.IntakeCodeCache;

public class IntakeCodeCacheHealthCheck extends HealthCheck {

  private static final long MINIMUM_CACHE_SIZE = 413;
  private static final long MINIMUM_LOVS = 394;

  public IntakeCodeCacheHealthCheck() {
    super();
  }

  @Override
  protected Result check() throws Exception {
    long cacheSize = IntakeCodeCache.global().getCacheSize();
    int lovCount = IntakeCodeCache.global().getAll().size();

    String message = "[Expected minimum LOV code cache size " + MINIMUM_CACHE_SIZE + ", found "
        + cacheSize + "], [Expected minimum LOVs " + MINIMUM_LOVS + ", found " + lovCount + "]";

    if (cacheSize >= MINIMUM_CACHE_SIZE && lovCount >= MINIMUM_LOVS) {
      return Result.healthy(message);
    } else {
      return Result.unhealthy(message);
    }
  }
}
