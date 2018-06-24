package gov.ca.cwds.data.persistence.xa;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

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
    final String namedQueryName = SystemMeta.class.getName() + ".findAll";
    final Session session = grabSession();
    joinTransaction(session);

    try {
      final Query<?> query = session.getNamedQuery(namedQueryName);
      return query.list().toArray(new SystemMeta[0]);
    } catch (HibernateException h) {
      throw new DaoException(h);
    }
  }

}
