package gov.ca.cwds.health.resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.xa.CaresHibernateHackersKit;
import gov.ca.cwds.data.persistence.xa.CaresLogUtils;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Health check for Postgres system codes.
 * 
 * @author CWDS API Team
 */
public class SystemCodeCheck implements Pingable {

  protected static final Logger LOGGER = LoggerFactory.getLogger(SystemCodeCheck.class);

  // tables.put("SYSTEM_CODES", 4274);

  private SessionFactory sessionFactory;
  private String message;

  @Inject
  SystemCodeCheck(@NsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public boolean ping() {
    LOGGER.info("Postgres LOV health check: ping start");
    boolean ok = true;

    try (final Session session = sessionFactory.openSession()) {
      final String schema =
          (String) session.getSessionFactory().getProperties().get("hibernate.default_schema");
      final Connection con = CaresHibernateHackersKit.stealConnection(session);
      final String tableName = "SYSTEM_CODES";
      final int exepctedValues = 4274;
      final boolean tableCountOk = checkTableCount(con, tableName, schema, exepctedValues);
      LOGGER.info("Postgres SYSTEM_CODES health check: tableCountOk: {}, table: {}", tableCountOk,
          tableName);
      ok = ok && tableCountOk;
    } // Session and connection go out of scope.

    LOGGER.info("Postgres SYSTEM_CODES health check: ping done");
    return ok;
  }

  @Override
  public String getMessage() {
    return message;
  }

  /**
   * Check record count of target table.
   * 
   * <p>
   * No SonarQube, no SQL injection vulnerability here, because the prepared SQL only allows the
   * table name to vary and prevents injection of arbitrary SQL. That said, we could place the SQL
   * in String constants and build prepared statements with lambda functions in order to avoid any
   * possible risk of SQL injection, even if the JVM were hacked. But frankly, if an intruder gets
   * that far, then the game is up anyway, and SonarQube's suggested defenses wouldn't help.
   * </p>
   * 
   * @param con database connection
   * @param tableName table to check
   * @param schema target schema
   * @param expectedCount expected table count
   * @return true = counts match
   */
  protected boolean checkTableCount(Connection con, String tableName, String schema,
      int expectedCount) {
    final String sql =
        "SELECT COUNT(*) AS TOTAL FROM " + schema + "." + tableName + " FOR READ ONLY ";
    int count = 0;
    LOGGER.info("Postgres LOV health check: SQL: {}", sql);

    try (final PreparedStatement stmt = con.prepareStatement(sql)) {
      stmt.setMaxRows(10);
      stmt.setQueryTimeout(60);

      try (final ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          count = rs.getInt(1);
        }
      }

      LOGGER.info("Postgres LOV health check: count: {}, SQL: {}", count, sql);
      con.commit();
    } catch (Exception e) {
      try {
        con.rollback();
      } catch (SQLException e1) {
        LOGGER.trace("BOOM!", e1); // appease SonarQube by logging the exception
        throw CaresLogUtils.runtime(LOGGER, e1,
            "LOV HEALTH CHECK QUERY FAILED ON ROLLBACK! SQL: {} {}", sql, e1.getMessage(), e1);
      }
      LOGGER.trace("BOOM!", e);
      throw CaresLogUtils.runtime(LOGGER, e, "LOV HEALTH CHECK QUERY FAILED! SQL: {} {}", sql,
          e.getMessage(), e);
    }

    this.message = "Expected at least " + expectedCount + " " + tableName + ", found " + count;
    return count >= expectedCount;
  }
}
