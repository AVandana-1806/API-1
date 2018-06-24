package gov.ca.cwds.data.persistence.xa;

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
    LOGGER.info("SystemCodeDao.findByForeignKeyMetaTable: foreignKeyMetaTable: {}",
        foreignKeyMetaTable);
    final String namedQueryName = SystemCode.class.getName() + ".findByForeignKeyMetaTable";

    // DRS: Don't interfere with transaction management, like XA.
    final Session session = grabSession();
    joinTransaction(session);

    try {
      final Query<SystemCode> query = session.getNamedQuery(namedQueryName)
          .setString("foreignKeyMetaTable", foreignKeyMetaTable);
      return query.list().toArray(new SystemCode[0]);
    } catch (HibernateException h) {
      throw new DaoException(h);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public SystemCode findBySystemCodeId(Number systemCodeId) {
    LOGGER.info("SystemCodeDao.findBySystemCodeId: systemCodeId: {}", systemCodeId);
    final String namedQueryName = SystemCode.class.getName() + ".findBySystemCodeId";
    final Session session = grabSession();
    joinTransaction(session);

    try {
      final Query<SystemCode> query =
          session.getNamedQuery(namedQueryName).setShort("systemId", systemCodeId.shortValue());
      final SystemCode systemCode = query.getSingleResult();
      return systemCode;
    } catch (HibernateException h) {
      throw new DaoException(h);
    }
  }

}
