package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class ViewsHealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE = "View check error";

  public ViewsHealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }
}
