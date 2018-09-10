package gov.ca.cwds.health;

import com.codahale.metrics.health.HealthCheck;

import gov.ca.cwds.health.resource.Pingable;

public abstract class PingableResourceHealthCheck extends HealthCheck {

  Pingable resource;
  String errorMessage;

  public PingableResourceHealthCheck(Pingable resource, String errorMessage) {
    this.resource = resource;
    this.errorMessage = errorMessage;
  }

  @Override
  protected Result check() throws Exception {
    return resource.ping() ? Result.healthy(resource.getMessage())
        : Result.unhealthy(errorMessage + ", " + resource.getMessage());
  }

}
