package gov.ca.cwds.data.persistence.xa;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
@SuppressWarnings({"squid:S1854"})
public class FerbSystemCodeDao extends SystemCodeDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(FerbSystemCodeDao.class);

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public FerbSystemCodeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * DRS: Don't interfere with transaction management, like XA.
   * 
   * @param foreignKeyMetaTable meta group
   * @return all keys by meta table
   */
  @Override
  @SuppressWarnings("unchecked")
  public SystemCode[] findByForeignKeyMetaTable(String foreignKeyMetaTable) {
    LOGGER.info("XaSystemCodeDao.findByForeignKeyMetaTable: meta: {}", foreignKeyMetaTable);
    SystemCode[] ret;

    final String namedQueryName = SystemCode.class.getName() + ".findByForeignKeyMetaTable";
    final CandaceSessionImpl session = new CandaceSessionImpl(grabSession());
    final Transaction txn = joinTransaction(session);

    try {
      final Query<SystemCode> query = session.getNamedQuery(namedQueryName)
          .setString("foreignKeyMetaTable", foreignKeyMetaTable).setReadOnly(true)
          .setCacheable(false).setHibernateFlushMode(FlushMode.MANUAL);
      ret = query.list().toArray(new SystemCode[0]);
      LOGGER.info("XaSystemCodeDao.findByForeignKeyMetaTable: meta: {}, count: {}",
          foreignKeyMetaTable, ret.length);

      if (!session.isXaTransaction()) {
        LOGGER.warn("COMMIT TRANSACTION, CLOSE SESSION");
        txn.commit();
        session.close();
      }

    } catch (Exception h) {
      LOGGER.error("XaSystemCodeDao.findByForeignKeyMetaTable: ERROR! {}", h.getMessage(), h);
      throw new DaoException(h);
    }

    return ret;
  }

  @Override
  @SuppressWarnings("unchecked")
  public SystemCode findBySystemCodeId(Number systemCodeId) {
    LOGGER.info("XaSystemCodeDao.findBySystemCodeId: systemCodeId: {}", systemCodeId);
    final String namedQueryName = SystemCode.class.getName() + ".findBySystemCodeId";
    final CandaceSessionImpl session = new CandaceSessionImpl(grabSession());
    final Transaction txn = joinTransaction(session);
    SystemCode ret;

    try {
      final Query<SystemCode> query = session.getNamedQuery(namedQueryName)
          .setShort("systemId", systemCodeId.shortValue()).setReadOnly(true).setCacheable(false);
      query.setHibernateFlushMode(FlushMode.MANUAL);

      ret = query.getSingleResult();
      if (!session.isXaTransaction()) {
        LOGGER.warn("COMMIT TRANSACTION, CLOSE SESSION");
        txn.commit();
        session.close();
      }

    } catch (Exception h) {
      LOGGER.error("XaSystemCodeDao.findBySystemCodeId: ERROR! {}", h.getMessage(), h);
      throw new DaoException(h);
    }

    return ret;
  }

}
