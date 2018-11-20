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
@SuppressWarnings({"squid:S2160"})
public class CandaceSessionTracker extends ApiObjectIdentity implements Response {

  private static final long serialVersionUID = 1L;

  private static final AtomicInteger sequence = new AtomicInteger(0);

  private final int id = sequence.incrementAndGet(); // unique id
  private final RequestExecutionContext ctx = RequestExecutionContext.instance();
  private final StackTraceElement[] stack = CaresStackUtils.getStackTrace();
  private final long startTime = System.currentTimeMillis();
  private final long threadId = Thread.currentThread().getId();

  private final CandaceSessionFactoryImpl sessionFactory;
  private final Session session;

  /**
   * Preferred constructor.
   * 
   * @param sessionFactory datasource session factory
   * @param session Hibernate session
   */
  CandaceSessionTracker(CandaceSessionFactoryImpl sessionFactory, Session session) {
    this.sessionFactory = sessionFactory;
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

  public boolean isThreadOwner() {
    final long id = getThreadId(); // Child classes may override #getThreadId()
    return (ctx != null && ctx.getThreadId() == id) && Thread.currentThread().getId() == id;
  }

  public long getStartTime() {
    return startTime;
  }

  public StackTraceElement[] getStack() {
    return stack.clone(); // Appease SonarQube
  }

  public Session getSession() {
    return session;
  }

  public int getId() {
    return id;
  }

  public CandaceSessionFactoryImpl getSessionFactory() {
    return sessionFactory;
  }

}
