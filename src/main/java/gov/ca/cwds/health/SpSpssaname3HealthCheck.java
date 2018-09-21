package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class SpSpssaname3HealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE =
      "Procedure SPSSANAME3 check error";

  public SpSpssaname3HealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }
}
