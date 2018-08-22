package gov.ca.cwds.health;

import com.codahale.metrics.health.HealthCheck;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

public class SystemCodeCacheHealthCheck extends HealthCheck {

  private static final long MINIMUM_SYSTEM_CODES = 4674;

  public SystemCodeCacheHealthCheck() {
    super();
  }

  @Override
  protected Result check() throws Exception {
    int count = SystemCodeCache.global().getAllSystemCodes().size();

    String message = "Expected minimum system codes " + MINIMUM_SYSTEM_CODES + ", found " + count;

    if (count >= MINIMUM_SYSTEM_CODES) {
      return Result.healthy(message);
    } else {
      return Result.unhealthy(message);
    }
  }
}
