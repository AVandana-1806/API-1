package gov.ca.cwds.es.live;

/**
 * Base class for checked exceptions. Custom checked exceptions should extend this class.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class CaresExceptionChecked extends Exception {

  /**
   * Pointless constructor. Use another one.
   */
  @SuppressWarnings("unused")
  private CaresExceptionChecked() {
    // Default, no-op.
  }

  /**
   * @param message error message
   */
  public CaresExceptionChecked(String message) {
    super(message);
  }

  /**
   * @param cause original Throwable
   */
  public CaresExceptionChecked(Throwable cause) {
    super(cause);
  }

  /**
   * @param message error message
   * @param cause original Throwable
   */
  public CaresExceptionChecked(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message error message
   * @param cause original Throwable
   * @param enableSuppression whether or not suppression is enabled or disabled
   * @param writableStackTrace whether or not the stack trace should be writable
   */
  public CaresExceptionChecked(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
