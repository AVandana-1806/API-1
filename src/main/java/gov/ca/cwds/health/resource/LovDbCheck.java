package gov.ca.cwds.health.resource;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.NsSessionFactory;

public class LovDbCheck implements Pingable {

  private static final Logger LOGGER = LoggerFactory.getLogger(LovDbCheck.class);

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
  private List<String> messages = new ArrayList<>();

  @Inject
  LovDbCheck(@NsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public boolean ping() {
    boolean ok = true;
    Session session = null;

    try {
      session = sessionFactory.openSession();
      for (String tableName : lovTableCounts.keySet()) {
        boolean tableCountOk = checkTableCount(session, tableName, lovTableCounts.get(tableName));
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

    addMessage(
        "[Expected at least " + expectedCount + " in " + tableName + ", found " + count + "]");

    return ok;
  }

  private void addMessage(String message) {
    messages.add(message);
  }
}
