package gov.ca.cwds.health.resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * @author CWDS API Team
 */
public class DbCmsPermissionCheck implements Pingable {

  private static final Logger LOGGER = LoggerFactory.getLogger(DbCmsPermissionCheck.class);

  private SessionFactory sessionFactory;
  private String message;

  @Inject
  DbCmsPermissionCheck(@CmsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public boolean ping() {
    boolean permissionOk = true;
    try (Session session = sessionFactory.openSession()) {
      final Query<?> query =
          session.createNativeQuery("SELECT 1 FROM CLIENT_T FETCH FIRST ROW ONLY WITH UR");
      if (query.list().get(0) == null) {
        permissionOk = false;
        message = "Unable to retrieve from CLIENT_T";
      }
    } catch (Exception e) {
      permissionOk = false;
      message = "Exception occurred while reading CLIENT table: " + e.getMessage();
      LOGGER.warn(message, e);
    }
    return permissionOk;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
