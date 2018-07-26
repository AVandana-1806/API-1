package gov.ca.cwds.health;

import com.codahale.metrics.health.HealthCheck;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

public class SystemCodeCacheHealthCheck extends HealthCheck {

  public SystemCodeCacheHealthCheck() {
    super();
  }

  @Override
  protected Result check() throws Exception {
    int count = SystemCodeCache.global().getAllSystemCodes().size();

    if (count > 0) {
      return Result.healthy();
    } else {
      return Result.unhealthy("System code cache is empty");
    }
  }
}
