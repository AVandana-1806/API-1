package gov.ca.cwds.data.es.transform;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import gov.ca.cwds.data.persistence.xa.CaresWorkConnectionStealer;

/**
 * Steal a connection from a Hibernate session and make it available to the caller.
 * 
 * <p>
 * Facilities direct JDBC in a Hibernate environment.
 * </p>
 * 
 * @author CWDS API Team
 */
public class LiveElasticWorkConnectionStealer implements Work {

  private Connection conn;

  /**
   * Constructor.
   */
  public LiveElasticWorkConnectionStealer() {
    // default, no-op.
  }

  /**
   * 
   * 
   * @param con current database connection
   */
  @Override
  public void execute(Connection con) throws SQLException {
    conn = con;
    enableBatchSettings(con);
  }

  public static void enableBatchSettings(final Session session) {
    session.setCacheMode(CacheMode.IGNORE);
    session.setDefaultReadOnly(true);
    session.setHibernateFlushMode(FlushMode.MANUAL);
  }

  /**
   * @return default CMS schema name
   */
  public static String getDBSchemaName() {
    return System.getProperty("DB_CMS_SCHEMA");
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
      new CaresWorkConnectionStealer().execute(con);
    }
  }

  public Connection getConnection() {
    return conn;
  }

  public void setConnection(Connection conn) {
    this.conn = conn;
  }

}
