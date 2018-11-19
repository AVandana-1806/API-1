package gov.ca.cwds.data.es.transform;

/**
 * Base class for checked exceptions. Custom checked exceptions should extend this class.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class CaresCheckedException extends Exception {

  /**
   * Pointless constructor. Use another one.
   */
  @SuppressWarnings("unused")
  private CaresCheckedException() {
    // Default, no-op.
  }

  /**
   * @param message error message
   */
  public CaresCheckedException(String message) {
    super(message);
  }

  /**
   * @param cause original Throwable
   */
  public CaresCheckedException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message error message
   * @param cause original Throwable
   */
  public CaresCheckedException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message error message
   * @param cause original Throwable
   * @param enableSuppression whether or not suppression is enabled or disabled
   * @param writableStackTrace whether or not the stack trace should be writable
   */
  public CaresCheckedException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
