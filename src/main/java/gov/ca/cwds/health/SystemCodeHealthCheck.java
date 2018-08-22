package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class SystemCodeHealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE = "System codes error";

  public SystemCodeHealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }
}
