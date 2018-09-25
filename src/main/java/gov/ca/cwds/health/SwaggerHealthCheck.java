package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class SwaggerHealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE = "Unable to reach Swagger endpoint";

  public SwaggerHealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }

}
