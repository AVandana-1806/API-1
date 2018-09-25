package gov.ca.cwds.health.resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.xa.CaresHibernateHackersKit;
import gov.ca.cwds.data.persistence.xa.CaresLogUtils;
import gov.ca.cwds.inject.CwsRsSessionFactory;

public class TriggerExistCheck implements Pingable {

  private static String[] dbTriggers =
      {"TRIG_UPD_REFR", "TRIG_INS_REFR", "TRIG_UPD_CASE", "TRIG_INS_CASE", "TRIG_INS_CLIENT",
          "TRIG_UPD_CLIENT", "TRIG_INS_CLN_RELT", "TRIG_UPD_CLN_RELT"};

  private SessionFactory sessionFactory;
  protected static final Logger LOGGER = LoggerFactory.getLogger(TriggerExistCheck.class);
  private List<String> messages = new ArrayList<>();

  @Inject
  TriggerExistCheck(@CwsRsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public boolean ping() {
    boolean ok = true;
    messages.clear();

    try (final Session session = sessionFactory.openSession()) {
      final String schema =
          (String) session.getSessionFactory().getProperties().get("hibernate.default_schema");
      final Connection con = CaresHibernateHackersKit.stealConnection(session);
      for (int i = 0; i < dbTriggers.length; i++) {
        boolean triggerExists = checkIfTriggerExist(con, schema, dbTriggers[i]);
        ok = ok && triggerExists;
      }
    }
    return ok;
  }

  @Override
  public String getMessage() {
    return messages.toString();
  }

  private boolean checkIfTriggerExist(Connection con, String schema, String triggerName) {
    boolean ok = true;
    int count = 0;
    final String sql = "SELECT count(*) FROM SYSIBM.SYSTRIGGERS WHERE SCHEMA = ? AND NAME = ? WITH UR";

    try (final PreparedStatement stmt = con.prepareStatement(sql)) {
      stmt.setString(1, schema);
      stmt.setString(2, triggerName);
      stmt.setMaxRows(1);
      stmt.setQueryTimeout(60);

      try (final ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          count = rs.getInt(1);
        }
        if (count < 1) {
          ok = false;
          addMessage("[Database trigger " + triggerName + " does not exist in schema " + schema + "]");
        }
      }
    } catch (Exception e) {
      LOGGER.trace("BOOM!", e);
      throw CaresLogUtils.runtime(LOGGER, e, "Trigger HEALTH CHECK QUERY FAILED! SQL: {} {}", sql,
          e.getMessage(), e);
    }

    return ok;
  }

  private void addMessage(String message) {
    messages.add(message);
  }

}
