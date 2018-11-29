package gov.ca.cwds.es.live;

/**
 * Base class for <strong>runtime</strong> exceptions. Specialized runtime exceptions should extend
 * this class.
 * 
 * @author CWDS API Team
 * @see CaresExceptionChecked
 */
@SuppressWarnings("serial")
public class CaresExceptionRuntime extends RuntimeException {

  /**
   * Pointless constructor. Use another one. Thanks Java.
   */
  @SuppressWarnings("unused")
  private CaresExceptionRuntime() {
    // Default, no-op.
  }

  /**
   * @param message error message
   */
  public CaresExceptionRuntime(String message) {
    super(message);
  }

  /**
   * @param cause original Throwable
   */
  public CaresExceptionRuntime(Throwable cause) {
    super(cause);
  }

  /**
   * @param message error message
   * @param cause original Throwable
   */
  public CaresExceptionRuntime(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message error message
   * @param cause original Throwable
   * @param enableSuppression whether or not suppression is enabled or disabled
   * @param writableStackTrace whether or not the stack trace should be writable
   */
  public CaresExceptionRuntime(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
