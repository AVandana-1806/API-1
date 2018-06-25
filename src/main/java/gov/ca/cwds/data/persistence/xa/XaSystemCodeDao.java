package gov.ca.cwds.data.persistence.xa;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.persistence.cms.SystemCode;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link SystemCode}.
 * 
 * @author CWDS API Team
 */
public class XaSystemCodeDao extends SystemCodeDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(XaSystemCodeDao.class);

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public XaSystemCodeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param foreignKeyMetaTable meta group
   * @return all keys by meta table
   */
  @Override
  @SuppressWarnings("unchecked")
  public SystemCode[] findByForeignKeyMetaTable(String foreignKeyMetaTable) {
    LOGGER.info("XaSystemCodeDao.findByForeignKeyMetaTable: foreignKeyMetaTable: {}",
        foreignKeyMetaTable);
    final String namedQueryName = SystemCode.class.getName() + ".findByForeignKeyMetaTable";

    // DRS: Don't interfere with transaction management, like XA.
    final Session session = grabSession();
    joinTransaction(session);

    try {
      final Query<SystemCode> query = session.getNamedQuery(namedQueryName)
          .setString("foreignKeyMetaTable", foreignKeyMetaTable).setReadOnly(true)
          .setCacheable(false).setHibernateFlushMode(FlushMode.MANUAL);
      return query.list().toArray(new SystemCode[0]);
    } catch (HibernateException h) {
      LOGGER.error("XaSystemCodeDao: OOPS! {}", h.getMessage(), h);
      throw new DaoException(h);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public SystemCode findBySystemCodeId(Number systemCodeId) {
    LOGGER.info("XaSystemCodeDao.findBySystemCodeId: systemCodeId: {}", systemCodeId);
    final String namedQueryName = SystemCode.class.getName() + ".findBySystemCodeId";
    final Session session = grabSession();
    joinTransaction(session);

    try {
      final Query<SystemCode> query = session.getNamedQuery(namedQueryName)
          .setShort("systemId", systemCodeId.shortValue()).setReadOnly(true).setCacheable(false);
      query.setHibernateFlushMode(FlushMode.MANUAL);
      return query.getSingleResult();
    } catch (HibernateException h) {
      LOGGER.error("XaSystemCodeDao: OOPS! {}", h.getMessage(), h);
      throw new DaoException(h);
    }
  }

}
