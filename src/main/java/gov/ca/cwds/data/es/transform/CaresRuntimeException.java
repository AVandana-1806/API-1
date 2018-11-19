package gov.ca.cwds.data.es.transform;

/**
 * Base class for <strong>runtime</strong> exceptions. Specialized runtime exceptions should extend
 * this class.
 * 
 * @author CWDS API Team
 * @see CaresCheckedException
 */
@SuppressWarnings("serial")
public class CaresRuntimeException extends RuntimeException {

  /**
   * Pointless constructor. Use another one. Thanks Java.
   */
  @SuppressWarnings("unused")
  private CaresRuntimeException() {
    // Default, no-op.
  }

  /**
   * @param message error message
   */
  public CaresRuntimeException(String message) {
    super(message);
  }

  /**
   * @param cause original Throwable
   */
  public CaresRuntimeException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message error message
   * @param cause original Throwable
   */
  public CaresRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message error message
   * @param cause original Throwable
   * @param enableSuppression whether or not suppression is enabled or disabled
   * @param writableStackTrace whether or not the stack trace should be writable
   */
  public CaresRuntimeException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
