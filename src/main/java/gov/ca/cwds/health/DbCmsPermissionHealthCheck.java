package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;

public class DbCmsPermissionHealthCheck extends PingableResourceHealthCheck {

  private static final String ERROR_MESSAGE = "DB2 read error";

  public DbCmsPermissionHealthCheck(Pingable resource) {
    super(resource, ERROR_MESSAGE);
  }

}
