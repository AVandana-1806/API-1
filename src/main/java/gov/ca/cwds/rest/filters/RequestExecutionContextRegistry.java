package gov.ca.cwds.rest.filters;

/**
 * Request execution context registry based on ThreadLocal.
 * 
 * @author CWDS API Team
 */
public class RequestExecutionContextRegistry {

  private static final ThreadLocal<RequestExecutionContext> pegged = new ThreadLocal<>();

  // TODO: Create callback registry for stop and start.

  /**
   * Register RequestExecutionContext with ThreadLocal
   * 
   * @param requestExecutionContext
   */
  static void register(RequestExecutionContext requestExecutionContext) {
    pegged.set(requestExecutionContext);
  }

  /**
   * Remove RequestExecutionContext from ThreadLocal
   */
  static void remove() {
    pegged.remove();
  }

  /**
   * Get RequestExecutionContext from ThreadLocal
   */
  static RequestExecutionContext get() {
    return pegged.get();
  }

}
