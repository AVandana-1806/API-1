package gov.ca.cwds.es.live;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.xa.WorkFerbUserInfo;

/**
 * JDBC utilities for Neutron rockets.
 * 
 * <p>
 * DB2 sorts results differently by platform due to native character sets. That is, {@code ORDER BY}
 * does <strong>NOT</strong> order results the same way in Linux and z/OS! You've been warned.
 * </p>
 * 
 * @author CWDS API Team
 */
public class LiveElasticJdbcHelper {

  protected static final Logger LOGGER = LoggerFactory.getLogger(LiveElasticJdbcHelper.class);

  public static final String CURRENT_SCHEMA = "DB_CMS_SCHEMA";

  private LiveElasticJdbcHelper() {
    // Static utility class.
  }

  /**
   * @return default CMS schema name
   */
  public static String getDBSchemaName() {
    return System.getProperty(CURRENT_SCHEMA);
  }

  /**
   * Steal a connection from an active Hibernate session.
   * 
   * @param session active Hibernate session
   * @return database Connection
   */
  public static Connection prepConnection(final Session session) {
    final LiveElasticWorkConnectionStealer work = new LiveElasticWorkConnectionStealer();
    doWork(session, work);
    return work.getConnection();
  }

  /**
   * Clear a Hibernate session and trap transaction errors.
   * 
   * @param session active Hibernate session
   */
  public static void clearSession(final Session session) {
    try {
      // Hibernate session clear may fail without a transaction.
      grabTransaction(session);
      session.clear(); // Hibernate "duplicate object" bug
    } catch (Exception e) {
      LOGGER.trace("'clear' without transaction", e);
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
    q.setFetchSize(500);
    q.setHibernateFlushMode(FlushMode.MANUAL);
    q.setTimeout(120); // 2 minutes tops
  }

  public static void enableBatchSettings(final Session session) {
    session.setCacheMode(CacheMode.IGNORE);
    session.setDefaultReadOnly(true);
    session.setHibernateFlushMode(FlushMode.MANUAL);
  }

  /**
   * Enable DB2 parallelism. Ignored for other databases.
   * 
   * @param con connection
   * @throws SQLException connection error
   */
  public static void enableBatchSettings(Connection con) throws SQLException {
    final String dbProductName = con.getMetaData().getDatabaseProductName();
    con.setSchema(getDBSchemaName());
    con.setAutoCommit(false);

    if (StringUtils.containsIgnoreCase(dbProductName, "db2")) {
      new WorkFerbUserInfo().execute(con);
    }
  }

}
