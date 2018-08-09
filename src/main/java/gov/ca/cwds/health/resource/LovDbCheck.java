package gov.ca.cwds.health.resource;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
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

/**
 * Health check for Postgres list of value (LOV) tables and views.
 * 
 * @author CWDS API Team
 */
public class LovDbCheck implements Pingable {

  protected static final Logger LOGGER = LoggerFactory.getLogger(LovDbCheck.class);

  private static final Map<String, Integer> lovTableCounts;

  static {
    final Map<String, Integer> tables = new TreeMap<>();
    tables.put("INTAKE_LOV_CATEGORIES", 23);
    tables.put("INTAKE_ONLY_LOV_CATEGORIES", 4);
    tables.put("INTAKE_LOV_CODES", 527);
    tables.put("INTAKE_ONLY_LOV_CODES", 13);
    tables.put("SYSTEM_CODES", 4274);
    tables.put("VW_INTAKE_LOV", 542);

    lovTableCounts = Collections.unmodifiableMap(tables);
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
    messages = new ArrayList<>();

    try (final Session session = sessionFactory.openSession()) {
      for (Map.Entry<String, Integer> entry : lovTableCounts.entrySet()) {
        boolean tableCountOk = checkTableCount(session, entry.getKey(), entry.getValue());
        ok = ok && tableCountOk;
      }
    } finally {
      // Session goes out of scope
    }

    return ok;
  }

  @Override
  public String getMessage() {
    return messages.toString();
  }

  protected boolean checkTableCount(Connection con, String tableName, int expectedCount) {
    final Query<?> query = session.createNativeQuery("SELECT count(*) FROM " + tableName);
    final int count = ((BigInteger) query.list().get(0)).intValue();

    addMessage("[Expected at least " + expectedCount + " " + tableName + ", found " + count + "]");
    return !(count < expectedCount);
  }

  private boolean checkTableCount(Session session, String tableName, int expectedCount) {
    final Query<?> query = session.createNativeQuery("SELECT count(*) FROM " + tableName);
    final int count = ((BigInteger) query.list().get(0)).intValue();

    addMessage("[Expected at least " + expectedCount + " " + tableName + ", found " + count + "]");
    return !(count < expectedCount);
  }

  private void addMessage(String message) {
    messages.add(message);
  }

}
