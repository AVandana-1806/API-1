package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class AuthHealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE = "Unable to reach authentication server";

  public AuthHealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }

}
