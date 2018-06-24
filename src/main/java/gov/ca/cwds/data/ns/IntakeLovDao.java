package gov.ca.cwds.data.ns;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * DAO for Intake LOV codes.
 * 
 * @author CWDS API Team
 */
public class IntakeLovDao extends BaseDaoImpl<IntakeLov> {

  /**
   * Constructor.
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public IntakeLovDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param legacyCategoryId - legacyCategoryId
   * @return the intake code based on the category id
   */
  @SuppressWarnings("unchecked")
  public List<IntakeLov> findByLegacyMetaId(String legacyCategoryId) {
    final String namedQueryName = IntakeLov.class.getName() + ".findByLegacyCategoryId";

    final Session session = grabSession();
    joinTransaction(session);

    try {
      final Query<IntakeLov> query =
          session.getNamedQuery(namedQueryName).setParameter("legacyCategoryId", legacyCategoryId);
      return query.list();
    } catch (HibernateException h) {
      throw new DaoException(h);
    }
  }

  /**
   * @param legacySystemCodeId - legacySystemCodeId
   * @return the intakeLov
   */
  @SuppressWarnings("unchecked")
  public IntakeLov findByLegacySystemCodeId(Number legacySystemCodeId) {
    final String namedQueryName = IntakeLov.class.getName() + ".findByLegacySystemId";

    final Session session = grabSession();
    joinTransaction(session);

    try {
      final Query<IntakeLov> query = session.getNamedQuery(namedQueryName)
          .setShort("legacySystemCodeId", legacySystemCodeId.shortValue());
      return query.getSingleResult();
    } catch (HibernateException h) {
      throw new DaoException(h);
    }
  }

}
