package gov.ca.cwds.health.resource;

import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CwsRsSessionFactory;

public class MQTExistCheck implements Pingable {

  private SessionFactory sessionFactory;
  private String message;

  @Inject
  MQTExistCheck(@CwsRsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public boolean ping() {
    boolean ok = true;
    Session session = null;

    try {
      session = sessionFactory.openSession();
      boolean tableExists = checkIfTableExists(session);
      ok = ok && tableExists;
    } finally {
      if (session != null) {
        session.close();
      }
    }

    return ok;
  }


  private boolean checkIfTableExists(Session session) {
    boolean ok = true;
    final Query<?> query = session.createNativeQuery(
        "SELECT COUNT(*) FROM SYSIBM.SYSTABLES WHERE NAME = 'MQT_CLIENT_ADDRESS' AND TYPE = 'M' AND CREATOR = {h-schema}");
    int count = ((BigInteger) query.list().get(0)).intValue();

    if (count < 1) {
      ok = false;
      message = "MQT_CLIENT_ADDRESS does not exists in replicated schema";
    }

    return ok;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
