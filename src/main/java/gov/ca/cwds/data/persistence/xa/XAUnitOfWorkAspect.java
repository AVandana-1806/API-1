package gov.ca.cwds.data.persistence.xa;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atomikos.icatch.jta.UserTransactionImp;

import gov.ca.cwds.data.dao.cms.BaseAuthorizationDao;
import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.RequestExecutionContext.Parameter;

/**
 * AOP aspect supports annotation {@link XAUnitOfWork}.
 * 
 * <p>
 * In AOP terms, this wrapper method follows the <strong>"around"</strong> protocol. Start with
 * {@link #beforeStart(Method, XAUnitOfWork)}, call the annotated method, and finish with
 * {@link #afterEnd()}.
 * </p>
 * 
 * <p>
 * {@link XAUnitOfWork} annotations may be nested. This aspect automatically adds nested
 * {@link XAUnitOfWork} to the XA transaction and opens sessions for datasources not already
 * included.
 * </p>
 *
 * @author CWDS API Team
 */
@SuppressWarnings({"deprecation", "rawtypes", "findbugs:SE_BAD_FIELD",
    "squid:CallToDeprecatedMethod", "squid:RedundantThrowsDeclarationCheck",
    "findbugs:SE_TRANSIENT_FIELD_NOT_RESTORED"})
public class XAUnitOfWorkAspect implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(XAUnitOfWorkAspect.class);

  private transient UserTransaction txn;

  private final Map<String, SessionFactory> sessionFactories = new ConcurrentHashMap<>();

  private final Map<String, Session> sessions = new ConcurrentHashMap<>();

  private transient Map<Method, XAUnitOfWork> units = new ConcurrentHashMap<>();

  private transient XAUnitOfWork firstXaUnitOfWork;

  private boolean transactionStarted = false;

  /**
   * Preferred constructor.
   * 
   * @param sessionFactories - all datasources to participate in the XA transaction
   */
  public XAUnitOfWorkAspect(Map<String, SessionFactory> sessionFactories) {
    this.sessionFactories.putAll(sessionFactories);
  }

  /**
   * Aspect entry point.
   * 
   * @param method join point
   * @param xaUnitOfWork take settings from annotation
   * @throws CaresXAException on database error
   */
  public void beforeStart(Method method, XAUnitOfWork xaUnitOfWork) throws CaresXAException {
    LOGGER.info("XaUnitOfWorkAspect.beforeStart()");
    if (xaUnitOfWork == null) {
      LOGGER.error("XA beforeStart.beforeStart(): no annotation");
      return;
    }

    LOGGER.info("Mark XA transaction in RequestExecutionContext");
    BaseAuthorizationDao.setXaMode(true);
    RequestExecutionContext.instance().put(Parameter.XA_TRANSACTION, Boolean.TRUE);
    RequestExecutionContext.instance().put(Parameter.RESOURCE_READ_ONLY, Boolean.FALSE);

    units.putIfAbsent(method, xaUnitOfWork);
    if (this.firstXaUnitOfWork == null) {
      this.firstXaUnitOfWork = xaUnitOfWork;
    }

    openSessions();
    beginXaTransaction();
  }

  /**
   * Commit or rollback.
   * <p>
   * NOTE: method {@link #onFinish()} closes the session.
   * </p>
   * 
   * @throws CaresXAException on database error
   */
  public void afterEnd() throws CaresXAException {
    LOGGER.info("XaUnitOfWorkAspect.afterEnd()");
    if (sessions.isEmpty()) {
      LOGGER.warn("XA afterEnd: no sessions");
      return;
    }

    try {
      LOGGER.warn("XaUnitOfWorkAspect.afterEnd(): commit");
      commit();
    } catch (Exception e) {
      LOGGER.error("XaUnitOfWorkAspect.afterEnd(): ERROR ON COMMIT! {}", e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Call on error to rollback transactions and close sessions.
   * 
   * @throws CaresXAException on database error
   */
  public void onError() throws CaresXAException {
    LOGGER.info("XaUnitOfWorkAspect.onError()");
    if (sessions.isEmpty()) {
      LOGGER.warn("XA onError: no sessions");
      return;
    }

    LOGGER.warn("XA onError: rollback");
    try {
      rollback();
    } catch (Exception e) {
      LOGGER.error("XaUnitOfWorkAspect.onError(): ROLLBACK FAILED! {} ", e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Close open sessions, set transaction to null.
   */
  public void onFinish() {
    LOGGER.info("XaUnitOfWorkAspect.onFinish()");
    BaseAuthorizationDao.clearXaMode();
    RequestExecutionContext.instance().put(Parameter.XA_TRANSACTION, Boolean.FALSE);
    RequestExecutionContext.instance().put(Parameter.RESOURCE_READ_ONLY, Boolean.TRUE);

    try {
      closeSessions();
      this.sessionFactories.values().stream().filter(ManagedSessionContext::hasBind)
          .forEach(ManagedSessionContext::unbind);
    } catch (Exception e) {
      LOGGER.warn("XaUnitOfWorkAspect.onFinish(): FAILED TO UNBIND SESSION FACTORY! {} ",
          e.getMessage(), e);
    }

    sessionFactories.clear();
    units.clear();
  }

  /**
   * Get the current Hibernate session, if open, or open a new session.
   * 
   * <p>
   * For DB2 sessions, this method calls {@link WorkFerbUserInfo} to populate user information fields
   * on the JDBC connection.
   * </p>
   * 
   * @param key datasource name
   * @param sessionFactory - open a session for this datasource
   * @return session current session for this datasource
   */
  protected Session grabSession(String key, SessionFactory sessionFactory) {
    LOGGER.info("XaUnitOfWorkAspect.grabSession()");
    Session session;
    if (sessions.containsKey(key)) {
      session = sessions.get(key);
    } else {
      LOGGER.info("XA grab a session");
      try {
        session = sessionFactory.getCurrentSession();
      } catch (Exception e) {
        LOGGER.info("No current session. Open a new one. {}", e.getMessage());
        if (LOGGER.isTraceEnabled()) {
          LOGGER.trace("No current session. Open a new one. {}", e.getMessage(), e);
        }
        session = sessionFactory.openSession();
      }

      configureSession(session);
      sessions.put(key, session);

      // Add user info to DB2 connections. Harmless for other connections.
      session.doWork(new WorkFerbUserInfo());
    }

    return session;
  }

  /**
   * If any unit of work is marked transactional, then the whole run requires a transaction.
   * 
   * @return true = any unit is transactional
   */
  protected boolean hasTransactionalFlag() {
    LOGGER.info("XaUnitOfWorkAspect.hasTransactionalFlag()");
    return this.units.values().stream().anyMatch(XAUnitOfWork::transactional);
  }

  /**
   * Open sessions for selected datasources.
   */
  protected void openSessions() {
    LOGGER.info("XaUnitOfWorkAspect.openSessions()");
    sessionFactories.entrySet().stream().forEach(e -> grabSession(e.getKey(), e.getValue()));
  }

  /**
   * Close all sessions.
   */
  protected void closeSessions() {
    LOGGER.info("XaUnitOfWorkAspect.closeSessions()");
    sessions.values().stream().forEach(this::closeSession);
    sessions.clear();
  }

  protected void closeSession(Session session) {
    LOGGER.info("XaUnitOfWorkAspect.closeSession()");
    if (session != null) {
      LOGGER.info("XA CLOSE SESSION!");
      try {
        session.flush();
        session.clear();
      } catch (Exception e) {
        LOGGER.error("FAILED TO FLUSH SESSION! {}", e.getMessage(), e);
      }

      try {
        session.close();
      } catch (Exception e) {
        LOGGER.error("FAILED TO CLOSE SESSION! {}", e.getMessage(), e);
      }
    }
  }

  /**
   * Set cache mode, flush mode, and read-only properties on a Hibernate session.
   * 
   * <p>
   * Read-only operations run faster when flush mode is set to manual.
   * </p>
   * 
   * @param session - target Hibernate session
   */
  protected void configureSession(Session session) {
    LOGGER.info("XaUnitOfWorkAspect.configureSession()");
    session.setDefaultReadOnly(firstXaUnitOfWork.readOnly());
    session.setCacheMode(firstXaUnitOfWork.cacheMode());
    session.setHibernateFlushMode(
        firstXaUnitOfWork.readOnly() ? FlushMode.MANUAL : firstXaUnitOfWork.flushMode());
  }

  /**
   * Start XA transaction. Set timeout to 80 seconds.
   * 
   * @throws CaresXAException on database error
   */
  protected void beginXaTransaction() throws CaresXAException {
    LOGGER.info("XaUnitOfWorkAspect.beginXaTransaction()");
    if (!hasTransactionalFlag()) {
      LOGGER.info("XA BEGIN TRANSACTION: unit of work is not transactional");
      return;
    } else if (transactionStarted) {
      LOGGER.info("XA: transaction already started");
      return;
    }

    try {
      LOGGER.info("XA BEGIN TRANSACTION!");
      txn = new UserTransactionImp();
      txn.setTransactionTimeout(180); // NEXT: soft-code timeout
      txn.begin();
      transactionStarted = true;
    } catch (Exception e) {
      LOGGER.error("XA BEGIN FAILED! {}", e.getMessage(), e);
      try {
        txn.setRollbackOnly();
        txn.rollback();
      } catch (SystemException e2) {
        LOGGER.warn("XA: ROLLBACK FAILED! {}", e.getMessage(), e);
      }

      throw new CaresXAException("XA BEGIN FAILED!", e);
    }
  }

  protected void rollbackSessionTransaction(Session session) {
    LOGGER.info("XaUnitOfWorkAspect.rollbackSessionTransaction()");
    try {
      final Transaction sessionTran = session.getTransaction();
      if (sessionTran != null && sessionTran.getStatus().canRollback()) {
        sessionTran.rollback();
      }
    } catch (Exception e) {
      LOGGER.warn("UNABLE TO ROLLBACK! {}", e.getMessage(), e);
    }
  }

  /**
   * Roll back XA transaction.
   * 
   * @throws CaresXAException on database error
   */
  protected void rollback() throws CaresXAException {
    LOGGER.info("XaUnitOfWorkAspect.rollback()");
    if (!hasTransactionalFlag()) {
      LOGGER.trace("XA ROLLBACK TRANSACTION: unit of work not transactional");
      return;
    } else if (!transactionStarted) {
      LOGGER.info("XA: not transaction started");
      return;
    }

    try {
      LOGGER.info("XA ROLLBACK TRANSACTION!");
      sessions.values().stream().forEach(this::rollbackSessionTransaction);
      txn.rollback(); // wrapping XA transaction
    } catch (Exception e) {
      LOGGER.error("XA ROLLBACK FAILED! {}", e.getMessage(), e);
      throw new CaresXAException("XA ROLLBACK FAILED!", e);
    }
  }

  /**
   * Commit XA transaction.
   * 
   * @throws CaresXAException on database error
   */
  protected void commit() throws CaresXAException {
    LOGGER.info("XaUnitOfWorkAspect.commit()");
    if (!firstXaUnitOfWork.transactional()) {
      LOGGER.info("XA COMMIT TRANSACTION: unit of work not transactional");
      return;
    } else if (!transactionStarted) {
      LOGGER.info("XA: not transaction started");
      return;
    }

    try {
      final int status = txn.getStatus();
      if (status == Status.STATUS_ROLLING_BACK || status == Status.STATUS_MARKED_ROLLBACK) {
        LOGGER.warn("XA ROLLBACK TRANSACTION!");
        txn.rollback();
      } else {
        LOGGER.warn("XA COMMIT TRANSACTION!");
        txn.commit();
      }
    } catch (Exception e) {
      LOGGER.error("XA COMMIT FAILED! {}", e.getMessage(), e);
      throw new CaresXAException("XA COMMIT FAILED!", e);
    }
  }

  public Map<String, SessionFactory> getSessionFactories() {
    return sessionFactories;
  }

  public XAUnitOfWork getXaUnitOfWork() {
    return firstXaUnitOfWork;
  }

  public void setXaUnitOfWork(XAUnitOfWork xaUnitOfWork) {
    this.firstXaUnitOfWork = xaUnitOfWork;
  }

}
