package gov.ca.cwds.data.persistence.xa;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CaresStackUtils;
import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.data.persistence.cms.SystemMeta;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link SystemMeta}.
 * 
 * @author CWDS API Team
 */
public class XaSystemMetaDao extends SystemMetaDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(XaSystemMetaDao.class);

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public XaSystemMetaDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @return all meta data records
   */
  @Override
  @SuppressWarnings("unchecked")
  public SystemMeta[] findAll() {
    LOGGER.info("XaSystemMetaDao.findAll");
    CaresStackUtils.logStack();
    final String namedQueryName = SystemMeta.class.getName() + ".findAll";
    final Session session = grabSession();
    joinTransaction(session);

    try {
      final Query<?> query = session.getNamedQuery(namedQueryName);
      query.setReadOnly(true);
      query.setCacheable(true);
      query.setHibernateFlushMode(FlushMode.MANUAL);
      return query.list().toArray(new SystemMeta[0]);
    } catch (HibernateException h) {
      LOGGER.info("XaSystemMetaDao.findAll: ERROR! {}", h.getMessage(), h);
      throw new DaoException(h);
    }
  }

}
