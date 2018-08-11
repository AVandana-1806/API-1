package gov.ca.cwds.health.resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.xa.CaresHibernateHackersKit;
import gov.ca.cwds.data.persistence.xa.CaresLogUtils;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Health check for Postgres list of value (LOV) tables and views. Feel the LOVe.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"findsecbugs:SQL_INJECTION_JDBC", "squid:S2077"})
public class LovDbCheck implements Pingable {

  protected static final Logger LOGGER = LoggerFactory.getLogger(LovDbCheck.class);

  private static final Map<String, Integer> lovTableCounts;

  static {
    final Map<String, Integer> tables = new TreeMap<>();
    tables.put("INTAKE_LOV_CATEGORIES", 23);
    tables.put("INTAKE_LOV_CODES", 527);
    tables.put("INTAKE_ONLY_LOV_CATEGORIES", 4);
    tables.put("INTAKE_ONLY_LOV_CODES", 13);
    tables.put("SYSTEM_CODES", 4274);
    tables.put("VW_INTAKE_LOV", 542);

    lovTableCounts = Collections.unmodifiableMap(tables);
  }

  private SessionFactory sessionFactory;
  private List<String> messages = new ArrayList<>();

  @Inject
  LovDbCheck(@NsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public boolean ping() {
    LOGGER.info("Postgres LOV health check: ping start");
    boolean ok = true;

    // must clear messages list otherwise it keeps on adding into it...
    messages.clear();

    try (final Session session = sessionFactory.openSession()) {
      final String schema =
          (String) session.getSessionFactory().getProperties().get("hibernate.default_schema");
      final Connection con = CaresHibernateHackersKit.stealConnection(session);
      for (Map.Entry<String, Integer> entry : lovTableCounts.entrySet()) {
        final String table = entry.getKey();
        final boolean tableCountOk = checkTableCount(con, table, schema, entry.getValue());
        LOGGER.info("Postgres LOV health check: tableCountOk: {}, table: {}", tableCountOk, table);
        ok = ok && tableCountOk;
      }
    } // Session and connection go out of scope.

    LOGGER.info("Postgres LOV health check: ping done");
    return ok;
  }

  @Override
  public String getMessage() {
    return messages.toString();
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

    addMessage("[Expected at least " + expectedCount + " " + tableName + ", found " + count + "]");
    return !(count < expectedCount);
  }

  private void addMessage(String message) {
    LOGGER.debug("add message: {}", message);
    messages.add(message);
  }

}
