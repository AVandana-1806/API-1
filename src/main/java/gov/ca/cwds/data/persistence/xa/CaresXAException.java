package gov.ca.cwds.data.persistence.xa;

/**
 * Generic, checked exception for XA transaction errors.
 * 
 * @author CWDS API Team
 */
public class CaresXAException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Construct a generic XA exception.
   */
  public CaresXAException() {
    super();
  }

  /**
   * Construct with a displayable message.
   * 
   * @param message message to display
   */
  public CaresXAException(String message) {
    super(message);
  }

  /**
   * Construct with a Throwable to wrap and rethrow.
   * 
   * @param cause Throwable to rethrow
   */
  public CaresXAException(Throwable cause) {
    super(cause);
  }

  /**
   * Construct with a displayable message and Throwable to wrap and rethrow.
   * 
   * @param message message to display
   * @param cause Throwable to rethrow
   */
  public CaresXAException(String message, Throwable cause) {
    super(message, cause);
  }

  public CaresXAException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
