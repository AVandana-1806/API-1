package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class SpGenclncntyHealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE =
      "Procedure GENCLNCNTY check error";

  public SpGenclncntyHealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }
}
