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
    final int count = SystemCodeCache.global().getAllSystemCodes().size();
    final String message =
        "Expected minimum system codes " + MINIMUM_SYSTEM_CODES + ", found " + count;
    return count >= MINIMUM_SYSTEM_CODES ? Result.healthy(message) : Result.unhealthy(message);
  }

}
