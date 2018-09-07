package gov.ca.cwds.data.persistence.xa;

import java.util.Date;

import gov.ca.cwds.auth.realms.PerryUserIdentity;
import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * Track when database session open or close.
 * 
 * @author CWDS API Team
 */
public class CandaceSessionTracker implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
  private final long startTime = System.currentTimeMillis();
  private final long threadId = Thread.currentThread().getId();
  private final RequestExecutionContext ctx = RequestExecutionContext.instance();

  CandaceSessionTracker() {
    // no-op
  }

  public String getUserId() {
    return ctx.getUserId();
  }

  public String getStaffId() {
    return ctx.getStaffId();
  }

  public Date getRequestStartTime() {
    return ctx.getRequestStartTime();
  }

  public boolean isXaTransaction() {
    return ctx.isXaTransaction();
  }

  public PerryUserIdentity getUserIdentity() {
    return ctx.getUserIdentity();
  }

  public boolean isResourceReadOnly() {
    return ctx.isResourceReadOnly();
  }

  public long getThreadId() {
    return threadId;
  }

  public boolean thisThreadIsOwner() {
    return Thread.currentThread().getId() == getThreadId();
  }

  public boolean matchContextThreadId() {
    return ctx == null || ctx.getThreadId() == getThreadId();
  }

  public long getStartTime() {
    return startTime;
  }

  public StackTraceElement[] getStack() {
    return stack.clone();
  }

}
