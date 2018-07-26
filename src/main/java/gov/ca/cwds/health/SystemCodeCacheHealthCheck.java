package gov.ca.cwds.health;

import com.codahale.metrics.health.HealthCheck;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

public class SystemCodeCacheHealthCheck extends HealthCheck {

  private static final long MINIMUM_CACHE_SIZE = 4872;
  private static final long MINIMUM_SYSTEM_CODES = 4674;

  public SystemCodeCacheHealthCheck() {
    super();
  }

  @Override
  protected Result check() throws Exception {
    long cacheSize = SystemCodeCache.global().getCacheSize();
    int count = SystemCodeCache.global().getAllSystemCodes().size();

    String message = "[Expected minimum system code cache size " + MINIMUM_CACHE_SIZE + ", found "
        + cacheSize + "], [Expected minimum system codes " + MINIMUM_SYSTEM_CODES + ", found "
        + count + "]";

    if (cacheSize >= MINIMUM_CACHE_SIZE && count >= MINIMUM_SYSTEM_CODES) {
      return Result.healthy(message);
    } else {
      return Result.unhealthy(message);
    }
  }
}
