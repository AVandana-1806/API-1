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
import gov.ca.cwds.inject.CwsRsSessionFactory;

public class ViewExistCheck implements Pingable {

  private SessionFactory sessionFactory;
  private String message;
  protected static final Logger LOGGER = LoggerFactory.getLogger(ViewExistCheck.class);

  @Inject
  ViewExistCheck(@CwsRsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public boolean ping() {
    boolean ok = true;

    try (final Session session = sessionFactory.openSession()) {
      final String schema =
          (String) session.getSessionFactory().getProperties().get("hibernate.default_schema");
      final Connection con = CaresHibernateHackersKit.stealConnection(session);
      ok = checkIfViewExists(con, schema);
    }
    return ok;

  }


  private boolean checkIfViewExists(Connection con, String schema) {
    boolean ok = true;
    int count = 0;
    final String sql =
        "SELECT COUNT(*) FROM SYSIBM.SYSTABLES WHERE NAME = 'VW_LST_CLIENT_ADDRESS' AND TYPE = 'V' AND CREATOR = "
            + schema;
    try (final PreparedStatement stmt = con.prepareStatement(sql)) {
      stmt.setMaxRows(10);
      stmt.setQueryTimeout(60);

      try (final ResultSet rs = stmt.executeQuery()) {
        count = rs.getInt(1);
        if (count < 1) {
          ok = false;
          message = "VW_LST_CLIENT_ADDRESS does not exists in replicated schema";
        }
      }
      con.commit();
    } catch (Exception e) {
      try {
        con.rollback();
      } catch (SQLException e1) {
        LOGGER.trace("BOOM!", e1);
        throw CaresLogUtils.runtime(LOGGER, e1,
            "View HEALTH CHECK QUERY FAILED ON ROLLBACK! SQL: {} {}", sql, e1.getMessage(), e1);
      }
      LOGGER.trace("BOOM!", e);
      throw CaresLogUtils.runtime(LOGGER, e, "View HEALTH CHECK QUERY FAILED! SQL: {} {}", sql,
          e.getMessage(), e);
    }

    return ok;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
