package gov.ca.cwds.health.resource;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.inject.NsSessionFactory;

public class TriggerExistCheck implements Pingable {

  private static String[] dbTriggers =
      {"TRIG_UPD_REFR", "TRIG_INS_REFR", "TRIG_UPD_CASE", "TRIG_INS_CASE", "TRIG_INS_CLIENT",
          "TRIG_UPD_CLIENT", "TRIG_INS_CLN_RELT", "TRIG_UPD_CLN_RELT"};

  private SessionFactory sessionFactory;
  private List<String> messages = new ArrayList<>();

  @Inject
  TriggerExistCheck(@NsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public boolean ping() {
    boolean ok = true;
    Session session = null;

    try {
      session = sessionFactory.openSession();
      for (int i = 0; i < dbTriggers.length; i++) {
        boolean triggerExists = checkIfTriggerExist(session, dbTriggers[i]);
        ok = ok && triggerExists;
      }
    } finally {
      if (session != null) {
        session.close();
      }
    }

    return ok;
  }

  @Override
  public String getMessage() {
    return messages.toString();
  }

  private boolean checkIfTriggerExist(Session session, String triggerName) {
    boolean ok = true;
    final Query<?> query = session.createNativeQuery(
        "SELECT count(*) FROM SYSIBM.SYSTRIGGERS WHERE SCHEMA = {h-schema} AND NAME = "
            + triggerName);
    int count = ((BigInteger) query.list().get(0)).intValue();

    if (count < 1) {
      ok = false;
      addMessage("[Database Trigger " + triggerName + "does not exist]");
    }



    return ok;
  }

  private void addMessage(String message) {
    messages.add(message);
  }
}
