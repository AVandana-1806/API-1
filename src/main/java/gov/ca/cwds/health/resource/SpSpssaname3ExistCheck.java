package gov.ca.cwds.health.resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.xa.CaresHibernateHackersKit;
import gov.ca.cwds.data.persistence.xa.CaresLogUtils;
import gov.ca.cwds.inject.CmsSessionFactory;

public class SpSpssaname3ExistCheck implements Pingable {

  private SessionFactory sessionFactory;
  private String message;
  protected static final Logger LOGGER = LoggerFactory.getLogger(SpSpssaname3ExistCheck.class);

  @Inject
  SpSpssaname3ExistCheck(@CmsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public boolean ping() {
    boolean ok = true;

    try (final Session session = sessionFactory.openSession()) {
      final String schema =
          (String) session.getSessionFactory().getProperties().get("hibernate.default_schema");
      final Connection con = CaresHibernateHackersKit.stealConnection(session);
      boolean tableExists = checkIfProcedureExists(con, schema);
      ok = ok && tableExists;
    }

    return ok;
  }

  private boolean checkIfProcedureExists(Connection con, String schema) {
    boolean ok = true;
    int count = 0;

    final String sql =
        "SELECT COUNT(*) AS SPSSANAME3_COUNT FROM SYSIBM.SYSROUTINES WHERE NAME = 'SPSSANAME3' AND SCHEMA = ? WITH UR";
    try (final PreparedStatement stmt = con.prepareStatement(sql)) {
      stmt.setString(1, schema);
      stmt.setMaxRows(1);
      stmt.setQueryTimeout(60);

      try (final ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          count = rs.getInt("SPSSANAME3_COUNT");
        }
        if (count < 1) {
          ok = false;
          message = "SPSSANAME3 does not exists in schema " + schema;
        }
      }
    } catch (Exception e) {
      LOGGER.debug("BOOM!", e);
      throw CaresLogUtils.runtime(LOGGER, e, "SPSSANAME3 HEALTH CHECK QUERY FAILED! SQL: {} {}",
          sql, e.getMessage(), e);
    }

    return ok;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
