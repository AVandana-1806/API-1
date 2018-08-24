package gov.ca.cwds.rest.filters;

import java.util.Date;

import gov.ca.cwds.auth.realms.PerryUserIdentity;
import gov.ca.cwds.rest.messages.MessageBuilder;

/**
 * Request execution context. Binds the current HTTP/REST request to the current thread and exposes
 * common information to all services on that thread without polluting method parameters.
 * 
 * @author CWDS API Team
 */
public interface RequestExecutionContext {

  /**
   * Registered request execution parameters.
   */
  public enum Parameter {

    /**
     * Current user's user id or staff id.
     */
    USER_IDENTITY,

    /**
     * The request's start time.
     */
    REQUEST_START_TIME,

    /**
     * Request uses distributed XA transactions.
     */
    XA_TRANSACTION,

    /**
     * Request initiated by a non-HTTP source, such as cron'd health checks or scheduled tasks.
     */
    NON_HTTP_REQUEST,

    /**
     * Thread id of the request initiator.
     */
    THREAD_ID,

    /**
     * Convenient marker for read-only resources.
     */
    RESOURCE_READ_ONLY,

    SEQUENCE_EXTERNAL_TABLE,

    /**
     * Default error/warning message builder.
     */
    MESSAGE_BUILDER
  }

  /**
   * Store an arbitrary request execution parameter, per enum {@link Parameter}.
   * 
   * @param parameter Parameter
   * @param value Parameter value
   */
  void put(Parameter parameter, Object value);

  /**
   * Retrieve an arbitrary request execution parameter, per enum {@link Parameter}.
   * 
   * @param parameter Parameter
   * @return The parameter value
   */
  Object get(Parameter parameter);

  /**
   * Get user id, if stored.
   * 
   * @return The user id
   */
  String getUserId();

  /**
   * Get staff id, if stored.
   * 
   * @return The staff id
   */
  String getStaffId();

  /**
   * Get request start time, if stored
   * 
   * @return The request start time
   */
  Date getRequestStartTime();

  /**
   * Is the request using XA transactions?
   * 
   * @return true = request is using XA
   */
  boolean isXaTransaction();

  /**
   * Get logged in user's identity
   * 
   * @return Logged in user's identity
   */
  PerryUserIdentity getUserIdentity();

  /**
   * Return whether the resource is expected to perform read-only operations only.
   * 
   * @return true = resource is read-only
   */
  boolean isResourceReadOnly();

  /**
   * Get the initiator's thread id.
   * 
   * <p>
   * Helps to distinguish HTTP requests from non-HTTP requests.
   * </p>
   * 
   * @return the thread id
   */
  long getInitiatorThreadId();

  /**
   * Get the message builder for warnings and errors for this request.
   * 
   * @return message builder for warnings and errors
   */
  MessageBuilder getMessageBuilder();

  /**
   * Get instance of RequestExecutionContext from registry.
   * 
   * @return RequestExecutionContext instance
   */
  static RequestExecutionContext instance() {
    return RequestExecutionContextRegistry.get();
  }

}
