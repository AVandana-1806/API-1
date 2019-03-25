package gov.ca.cwds.health;

import gov.ca.cwds.health.resource.Pingable;
import gov.ca.cwds.rest.api.ApiException;

/**
 * Health check exception indicates that the given health check failed to execute or method
 * {@link Pingable#ping()} returned false.
 * 
 * @author CWDS API Team
 */
public class FerbHealthCheckException extends ApiException {

  private static final long serialVersionUID = 1L;

  public FerbHealthCheckException() {
    super();
  }

  public FerbHealthCheckException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public FerbHealthCheckException(String message, Throwable cause) {
    super(message, cause);
  }

  public FerbHealthCheckException(String message) {
    super(message);
  }

  public FerbHealthCheckException(Throwable cause) {
    super(cause);
  }

}
