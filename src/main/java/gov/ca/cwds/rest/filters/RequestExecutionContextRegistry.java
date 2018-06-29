package gov.ca.cwds.rest.filters;

import gov.ca.cwds.data.std.ApiMarker;

/**
 * {@link RequestExecutionContext} registry based on ThreadLocal.
 * 
 * <p>
 * Also registers callbacks for request events, such as request start and end.
 * </p>
 * 
 * @author CWDS API Team
 */
public class RequestExecutionContextRegistry implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private static final ThreadLocal<RequestExecutionContext> bound = new ThreadLocal<>();

  private static final RequestContextCallbackRegistry callbackRegistry =
      new RequestContextCallbackRegistry();

  public static synchronized void registerCallback(RequestExecutionContextCallback callback) {
    callbackRegistry.register(callback);
  }

  /**
   * Register RequestExecutionContext on the current thread with ThreadLocal.
   * 
   * @param ctx request context for this thread
   */
  static void register(RequestExecutionContext ctx) {
    bound.set(ctx);
    callbackRegistry.startRequest(ctx);
  }

  /**
   * Remove RequestExecutionContext from ThreadLocal.
   */
  static void remove() {
    final RequestExecutionContext ctx = bound.get();
    bound.remove();
    callbackRegistry.endRequest(ctx);
  }

  /**
   * Get RequestExecutionContext from ThreadLocal.
   */
  static RequestExecutionContext get() {
    return bound.get();
  }

}
