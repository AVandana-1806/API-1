package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class LovHealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE = "LOV error";

  public LovHealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }

}
