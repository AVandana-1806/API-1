package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class ViewsHealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE = "View does not exists in the replicated schema";

  public ViewsHealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }
}
