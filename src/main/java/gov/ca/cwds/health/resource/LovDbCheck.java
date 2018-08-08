package gov.ca.cwds.health.resource;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.inject.NsSessionFactory;

public class LovDbCheck implements Pingable {

  private static Map<String, Integer> lovTableCounts = new TreeMap<>();
  static {
    lovTableCounts.put("INTAKE_LOV_CATEGORIES", 23);
    lovTableCounts.put("INTAKE_ONLY_LOV_CATEGORIES", 4);
    lovTableCounts.put("INTAKE_LOV_CODES", 527);
    lovTableCounts.put("INTAKE_ONLY_LOV_CODES", 13);
    lovTableCounts.put("SYSTEM_CODES", 4274);
    lovTableCounts.put("VW_INTAKE_LOV", 542);
  }

  private SessionFactory sessionFactory;
  private List<String> messages;

  @Inject
  LovDbCheck(@NsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public boolean ping() {
    boolean ok = true;
    Session session = null;
    messages = new ArrayList<>();

    try {
      session = sessionFactory.openSession();
      for (Map.Entry<String, Integer> entry : lovTableCounts.entrySet()) {
        String tableName = entry.getKey();
        boolean tableCountOk = checkTableCount(session, tableName, entry.getValue());
        ok = ok && tableCountOk;
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

  private boolean checkTableCount(Session session, String tableName, int expectedCount) {
    boolean ok = true;
    final Query<?> query = session.createNativeQuery("SELECT count(*) FROM " + tableName);
    int count = ((BigInteger) query.list().get(0)).intValue();

    if (count < expectedCount) {
      ok = false;
    }

    addMessage("[Expected at least " + expectedCount + " " + tableName + ", found " + count + "]");

    return ok;
  }

  private void addMessage(String message) {
    messages.add(message);
  }
}
