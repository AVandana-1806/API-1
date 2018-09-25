package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class TriggerHealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE = "Trigger check error";

  public TriggerHealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }
}
