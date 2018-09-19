package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class DB2HealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE = "Cannot connect to DB2";

  public DB2HealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }

}
