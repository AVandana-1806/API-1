package gov.ca.cwds.data.persistence.xa;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.Session;

import gov.ca.cwds.auth.realms.PerryUserIdentity;
import gov.ca.cwds.data.CaresStackUtils;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * Track when database sessions open or close. Find orphaned or lingering connections.
 * 
 * @author CWDS API Team
 */
public class CandaceSessionTracker extends ApiObjectIdentity implements Response {

  private static final long serialVersionUID = 1L;

  private static final AtomicInteger sequence = new AtomicInteger(0);

  private final int id = sequence.incrementAndGet(); // unique id
  private final StackTraceElement[] stack = CaresStackUtils.getStackTrace();
  private final long startTime = System.currentTimeMillis();
  private final long threadId = Thread.currentThread().getId();
  private final RequestExecutionContext ctx = RequestExecutionContext.instance();

  private final Session session;

  CandaceSessionTracker(Session session) {
    this.session = session;
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

  public Session getSession() {
    return session;
  }

  public int getId() {
    return id;
  }

}
