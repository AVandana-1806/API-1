package gov.ca.cwds.data.ns;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
    Transaction txn = joinTransaction(session);
    boolean transactionExists = txn != null && txn.isActive();
    txn = transactionExists ? txn : session.beginTransaction();

    try {
      final Query<IntakeLov> query =
          session.getNamedQuery(namedQueryName).setParameter("legacyCategoryId", legacyCategoryId);
      final List<IntakeLov> intakeCodes = query.list();

      if (!transactionExists)
        txn.commit();
      return intakeCodes;
    } catch (HibernateException h) {
      txn.rollback();
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
    Transaction txn = joinTransaction(session);
    boolean transactionExists = txn != null && txn.isActive();
    txn = transactionExists ? txn : session.beginTransaction();

    try {
      final Query<IntakeLov> query = session.getNamedQuery(namedQueryName)
          .setShort("legacySystemCodeId", legacySystemCodeId.shortValue());
      final IntakeLov intakeLov = query.getSingleResult();
      if (!transactionExists)
        txn.commit();
      return intakeLov;
    } catch (HibernateException h) {
      txn.rollback();
      throw new DaoException(h);
    }
  }

}
