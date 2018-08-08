package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class SpSpssaname3HealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE =
      "Stored Procedure GENCLNCNTY does not exists in the replicated schema";

  public SpSpssaname3HealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }
}
