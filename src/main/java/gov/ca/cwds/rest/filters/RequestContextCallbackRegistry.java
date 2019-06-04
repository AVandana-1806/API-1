package gov.ca.cwds.rest.filters;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.std.ApiMarker;

/**
 * Register callbacks for request events, such as request start and end.
 * 
 * <p>
 * Callback methods are invoked on the same thread, since some callback implementations may depend
 * on ThreadLocal themselves.
 * </p>
 * 
 * @author CWDS API Team
 */
final class RequestContextCallbackRegistry implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(RequestContextCallbackRegistry.class);

  private final Map<Serializable, RequestExecutionContextCallback> callbacks =
      new ConcurrentHashMap<>();

  /**
   * Register a class instance for callbacks.
   * 
   * @param callback instance to call
   */
  public void register(RequestExecutionContextCallback callback) {
    callbacks.putIfAbsent(callback.key(), callback);
  }

  protected void safeStart(RequestExecutionContextCallback callback, RequestExecutionContext ctx) {
    try {
      callback.startRequest(ctx);
    } catch (Exception e) {
      LOGGER.error("START REQUEST FAILED ON CALLBACK! {}", callback.getClass().getName(), e);
    }
  }

  protected void safeStop(RequestExecutionContextCallback callback, RequestExecutionContext ctx) {
    try {
      callback.endRequest(ctx);
    } catch (Exception e) {
      LOGGER.error("STOP REQUEST FAILED ON CALLBACK! {}", callback.getClass().getName(), e);
    }
  }

  /**
   * Start a request. Notify all registered callbacks.
   * 
   * @param ctx request context
   */
  public void startRequest(RequestExecutionContext ctx) {
    callbacks.values().stream().sequential().forEach(c -> safeStart(c, ctx));
  }

  /**
   * End a request. Notify all registered callbacks.
   * 
   * @param ctx request context
   */
  public void endRequest(RequestExecutionContext ctx) {
    callbacks.values().stream().sequential().forEach(c -> safeStop(c, ctx));
  }

}
