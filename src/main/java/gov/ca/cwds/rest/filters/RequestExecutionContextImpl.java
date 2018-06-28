package gov.ca.cwds.rest.filters;

import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import gov.ca.cwds.auth.realms.PerryUserIdentity;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever;

/**
 * Common information carrier for all requests. Includes the request start time stamp and user
 * information. Each request is separated by thread local.
 * 
 * @author CWDS API Team
 */
class RequestExecutionContextImpl implements RequestExecutionContext {

  private static final PerryUserIdentity DEFAULT_IDENTITY;

  static {
    PerryUserIdentity pui = null;
    final String staffId = System.getProperty("DEFAULT_FERB_STAFF_ID");
    if (StringUtils.isNotBlank(staffId)) {
      pui = new PerryUserIdentity();
      pui.setStaffId(staffId.trim());
    }
    DEFAULT_IDENTITY = pui;
  }

  /**
   * Context parameters
   */
  private final Map<Parameter, Object> contextParameters = new EnumMap<>(Parameter.class);

  /**
   * Private constructor
   * 
   * @param userIdentity User identity
   */
  protected RequestExecutionContextImpl(PerryUserIdentity userIdentity) {
    put(Parameter.REQUEST_START_TIME, new Date());
    put(Parameter.USER_IDENTITY, userIdentity);
    put(Parameter.SEQUENCE_EXTERNAL_TABLE, Integer.valueOf(0));
    put(Parameter.MESSAGE_BUILDER, new MessageBuilder());
    put(Parameter.RESOURCE_READ_ONLY, Boolean.TRUE);
    put(Parameter.XA_TRANSACTION, Boolean.FALSE);
  }

  /**
   * Store request execution parameter
   * 
   * @param parameter Parameter
   * @param value Parameter value
   */
  @Override
  public final void put(Parameter parameter, Object value) {
    contextParameters.put(parameter, value);
  }

  /**
   * Retrieve request execution parameter
   * 
   * @param parameter Parameter
   * @return The parameter value
   */
  @Override
  public Object get(Parameter parameter) {
    return contextParameters.get(parameter);
  }

  /**
   * Get user id, if stored.
   * 
   * @return The user id
   */
  @Override
  public String getUserId() {
    String userId = null;
    PerryUserIdentity userIdentity = (PerryUserIdentity) get(Parameter.USER_IDENTITY);
    if (userIdentity == null) {
      userIdentity = DEFAULT_IDENTITY;
    }

    if (userIdentity != null) {
      userId = userIdentity.getUser();
    }
    return userId;
  }

  /**
   * Get staff id, if stored.
   * 
   * @return The staff id
   */
  @Override
  public String getStaffId() {
    String staffId = null;
    PerryUserIdentity userIdentity = getUserIdentity();

    if (userIdentity != null) {
      staffId = userIdentity.getStaffId();
    }
    return staffId;
  }

  @Override
  public PerryUserIdentity getUserIdentity() {
    PerryUserIdentity userIdentity = (PerryUserIdentity) get(Parameter.USER_IDENTITY);
    if (userIdentity == null) {
      userIdentity = DEFAULT_IDENTITY;
    }

    return userIdentity;
  }

  @Override
  public MessageBuilder getMessageBuilder() {
    return (MessageBuilder) contextParameters.get(Parameter.MESSAGE_BUILDER);
  }

  /**
   * Get request start time, if stored.
   * 
   * @return The request start time
   */
  @Override
  public Date getRequestStartTime() {
    return (Date) get(Parameter.REQUEST_START_TIME);
  }

  @Override
  public boolean isResourceReadOnly() {
    final Boolean readOnly = (Boolean) get(Parameter.RESOURCE_READ_ONLY);
    return readOnly != null && readOnly.booleanValue();
  }

  @Override
  public boolean isXaTransaction() {
    final Boolean ret = (Boolean) get(Parameter.XA_TRANSACTION);
    return ret != null && ret.booleanValue();
  }

  /**
   * Servlet filter marks the start of a web request. This method is only accessible by the filters
   * package.
   */
  static void startRequest() {
    PerryUserIdentity userIdentity = StaffPersonIdRetriever.getPerryUserIdentity();
    if (userIdentity == null) {
      userIdentity = DEFAULT_IDENTITY;
    }
    RequestExecutionContextRegistry.register(new RequestExecutionContextImpl(userIdentity));
  }

  /**
   * Perform cleanup after request completion.
   */
  static void stopRequest() {
    RequestExecutionContextRegistry.remove();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
