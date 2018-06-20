package gov.ca.cwds.data.persistence.xa;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atomikos.icatch.jta.UserTransactionImp;

import gov.ca.cwds.data.std.ApiMarker;

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
    LOGGER.info("XA beforeStart.beforeStart(): BEGIN");
    if (xaUnitOfWork == null) {
      LOGGER.error("XA beforeStart.beforeStart(): no annotation");
      return;
    }

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
    if (sessions.isEmpty()) {
      LOGGER.warn("XA afterEnd: no sessions");
      return;
    }

    try {
      LOGGER.debug("XA afterEnd: commit");
      commit();
    } catch (Exception e) {
      rollback();
      throw e;
    }
  }

  /**
   * Call on error to rollback transactions and close sessions.
   * 
   * @throws CaresXAException on database error
   */
  public void onError() throws CaresXAException {
    if (sessions.isEmpty()) {
      LOGGER.warn("XA onError: no sessions");
      return;
    }

    LOGGER.warn("XA onError: rollback");
    try {
      rollback();
    } finally {
      // nix
    }
  }

  /**
   * Close open sessions, set transaction to null.
   */
  public void onFinish() {
    closeSessions();
  }

  /**
   * Get the current Hibernate session, if open, or open a new session.
   * 
   * <p>
   * For DB2 sessions, this method calls {@link WorkDB2UserInfo} to populate user information fields
   * on the JDBC connection.
   * </p>
   * 
   * @param key datasource name
   * @param sessionFactory - open a session for this datasource
   * @return session current session for this datasource
   */
  protected Session grabSession(String key, SessionFactory sessionFactory) {
    Session session;
    if (sessions.containsKey(key)) {
      session = sessions.get(key);
    } else {
      LOGGER.debug("XA grabSession()!");
      try {
        session = sessionFactory.getCurrentSession();
      } catch (HibernateException e) {
        LOGGER.debug("No current session. Open a new one. {}", e.getMessage());
        LOGGER.trace("No current session. Open a new one. {}", e.getMessage(), e);
        session = sessionFactory.openSession();
      }

      configureSession(session);
      sessions.put(key, session);

      // Add user info to DB2 connections. Harmless for other connections.
      session.doWork(new WorkDB2UserInfo());
    }

    return session;
  }

  /**
   * If any unit of work is marked transactional, then the whole run requires a transaction.
   * 
   * @return true = any unit is transactional
   */
  protected boolean hasTransactionalFlag() {
    return this.units.values().stream().anyMatch(XAUnitOfWork::transactional);
  }

  /**
   * Open sessions for selected datasources.
   */
  protected void openSessions() {
    LOGGER.debug("XA OPEN SESSIONS!");
    sessionFactories.entrySet().stream().forEach(e -> grabSession(e.getKey(), e.getValue()));
  }

  /**
   * Close all sessions.
   */
  protected void closeSessions() {
    LOGGER.debug("XA CLOSE SESSIONS!");
    sessions.values().stream().forEach(this::closeSession);
  }

  protected void closeSession(Session session) {
    if (session != null) {
      LOGGER.debug("XA CLOSE SESSION");
      try {
        session.flush();
      } catch (Exception e) {
        LOGGER.warn("FAILED TO FLUSH SESSION! {}", e.getMessage());
      }

      try {
        session.close();
      } catch (Exception e) {
        LOGGER.warn("FAILED TO CLOSE SESSION! {}", e.getMessage());
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
    if (!hasTransactionalFlag()) {
      LOGGER.trace("XA BEGIN TRANSACTION: unit of work is not transactional");
      return;
    } else if (transactionStarted) {
      LOGGER.debug("XA: transaction already started");
      return;
    }

    try {
      LOGGER.debug("XA BEGIN TRANSACTION!");
      txn = new UserTransactionImp();
      txn.setTransactionTimeout(80); // NEXT: soft-code timeout
      txn.begin();
      transactionStarted = true;
    } catch (Exception e) {
      LOGGER.error("XA BEGIN FAILED! {}", e.getMessage(), e);
      throw new CaresXAException("XA BEGIN FAILED!", e);
    }
  }

  protected void rollbackSessionTransaction(Session session) {
    try {
      final Transaction sessionTran = session.getTransaction();
      if (sessionTran != null && sessionTran.getStatus().canRollback()) {
        sessionTran.rollback();
      }
    } catch (Exception e) {
      LOGGER.warn("Unable to rollback! {}", e.getMessage(), e);
    }
  }

  /**
   * Roll back XA transaction.
   * 
   * @throws CaresXAException on database error
   */
  protected void rollback() throws CaresXAException {
    if (!hasTransactionalFlag()) {
      LOGGER.trace("XA ROLLBACK TRANSACTION: unit of work not transactional");
      return;
    } else if (!transactionStarted) {
      LOGGER.debug("XA: not transaction started");
      return;
    }

    try {
      LOGGER.debug("XA ROLLBACK TRANSACTION!");
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
    if (!firstXaUnitOfWork.transactional()) {
      LOGGER.debug("XA COMMIT TRANSACTION: unit of work not transactional");
      return;
    } else if (!transactionStarted) {
      LOGGER.debug("XA: not transaction started");
      return;
    }

    try {
      final int status = txn.getStatus();
      if (status == Status.STATUS_ROLLING_BACK || status == Status.STATUS_MARKED_ROLLBACK) {
        LOGGER.debug("XA ROLLBACK TRANSACTION!");
        txn.rollback();
      } else {
        LOGGER.debug("XA COMMIT TRANSACTION!");
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
