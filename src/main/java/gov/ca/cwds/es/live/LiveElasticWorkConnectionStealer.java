package gov.ca.cwds.es.live;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
 * Facilitates direct JDBC in a Hibernate environment.
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
   * Enable DB2 parallelism. Ignored for other databases.
   * 
   * @param con connection
   * @throws SQLException connection error
   */
  public static void enableBatchSettings(Connection con) throws SQLException {
    final DatabaseMetaData metaData = con.getMetaData();
    final String dbProductName = metaData.getDatabaseProductName();
    final String schema = LiveElasticJdbcHelper.getDBSchemaName();

    if (StringUtils.isNotBlank(schema)) {
      con.setSchema(schema);
    }
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
