package gov.ca.cwds.data.persistence.xa;

import java.sql.Connection;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.BaseDaoImpl;

/**
 * Because CARES cares and wants to alleviate your suffering. We feel your pain.
 * 
 * @author CWDS API Team
 */
public class CaresHibernateHackersKit {

  protected static final Logger LOGGER = LoggerFactory.getLogger(CaresHibernateHackersKit.class);

  private CaresHibernateHackersKit() {
    // Static methods only.
  }

  /**
   * Steal a JDBC connection from a Hibernate session. Closing the session automatically returns the
   * connection to the pool.
   * 
   * <p>
   * Don't abuse this; we know where you live. And never mind that van parked across the street from
   * your place. And do change those abhorrent curtains; they clash with your carpet.
   * </p>
   * 
   * @param session session to steal from
   * @return the session's connection
   */
  public static Connection stealConnection(final Session session) {
    final CaresWorkConnectionStealer work = new CaresWorkConnectionStealer();
    doWork(session, work);
    return work.getConnection();
  }

  /**
   * Clear a Hibernate session to avoid transaction errors. Hibernate {@code session.clear()} may
   * fail without a transaction. Create a new transaction or join an existing one, as needed.
   * 
   * @param session active Hibernate session
   */
  public static void clearSession(final Session session) {
    try {
      grabTransaction(session);
      session.clear(); // Hibernate "duplicate object" bug
    } catch (Exception e) {
      LOGGER.warn("'clear' without transaction", e);
    }
  }

  /**
   * "Work-around" (gentle euphemism for <strong>HACK</strong>) for annoying condition where a
   * transaction should have started but did not.
   * 
   * <p>
   * Get the current transaction from the current session or start a new transaction.
   * </p>
   * 
   * @param dao DAO
   * @return current, active transaction
   */
  public static Transaction grabTransaction(final BaseDaoImpl<?> dao) {
    return grabTransaction(dao.grabSession());
  }

  /**
   * @param session active Hibernation session
   * @return current, active transaction
   * @see #grabTransaction(BaseDaoImpl)
   */
  public static Transaction grabTransaction(final Session session) {
    Transaction txn = null;
    try {
      txn = session.beginTransaction();
    } catch (Exception e) { // NOSONAR
      txn = session.getTransaction();
    }
    return txn;
  }

  /**
   * Generic method to execute a Hibernate Work implementation (arbitrary JDBC through Hibernate).
   * 
   * @param session active Hibernate session
   * @param work Hibernate Work instance
   */
  public static void doWork(final Session session, Work work) {
    clearSession(session);
    grabTransaction(session);
    session.doWork(work);
    clearSession(session);
  }

  /**
   * Make a Hibernate query read-only.
   * 
   * @param q query to make read-only
   * @see #optimizeQuery(Query)
   */
  public static void readOnlyQuery(Query<?> q) {
    optimizeQuery(q);
    q.setReadOnly(true);
  }

  /**
   * Optimize a Hibernate query for batch performance. Disable Hibernate caching, set flush mode to
   * manual, and set fetch size to {@code NeutronIntegerDefaults.FETCH_SIZE}.
   * 
   * @param q query to optimize
   */
  public static void optimizeQuery(Query<?> q) {
    q.setCacheable(false);
    q.setCacheMode(CacheMode.IGNORE);
    q.setFetchSize(1000);
    q.setHibernateFlushMode(FlushMode.MANUAL);
    q.setTimeout(120); // 2 minutes tops
  }

}
