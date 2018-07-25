package gov.ca.cwds.rest.filters;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

  public void startRequest(RequestExecutionContext ctx) {
    callbacks.values().stream().sequential().forEach(c -> c.startRequest(ctx));
  }

  public void endRequest(RequestExecutionContext ctx) {
    callbacks.values().stream().sequential().forEach(c -> c.endRequest(ctx));
  }

}