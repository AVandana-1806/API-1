package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class MQTHealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE = "MQT does not exists in the replicated schema";

  public MQTHealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }
}
