package gov.ca.cwds.rest.filters;

import java.io.Serializable;

import gov.ca.cwds.data.std.ApiMarker;

/**
 * Classes implementing this interface will be notified of key events in the
 * {@link RequestExecutionContext}, such as a request's start and end.
 * 
 * @author CWDS API Team
 * @see RequestExecutionContext
 */
public interface RequestExecutionContextCallback extends ApiMarker {

  /**
   * Unique identifier for this callback type, such as the fully-qualified class name.
   * 
   * @return serializable, unique id
   */
  Serializable key();

  /**
   * Notifier the listener that the request has started. Allocate thread-level resources.
   * 
   * @param ctx current request context
   */
  void startRequest(RequestExecutionContext ctx);

  /**
   * Notifier the listener that the request has ended. De-allocate thread-level resources.
   * 
   * @param ctx current request context
   */
  void endRequest(RequestExecutionContext ctx);

}
