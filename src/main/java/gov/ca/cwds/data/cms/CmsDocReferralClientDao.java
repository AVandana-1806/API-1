package gov.ca.cwds.data.cms;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StringType;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.CmsDocReferralClient;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Data Access Object (DAO) for legacy CMS documents and client referral records.
 * 
 * @author CWDS API Team
 */
public class CmsDocReferralClientDao extends CrudsDaoImpl<CmsDocReferralClient> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CmsDocReferralClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Result set sort order is determined by {@link CmsDocReferralClient}.
   * 
   * @param docHandle "document handle", the primary key of CMS legacy document
   * @return ordered list of referral/client document records
   */
  @SuppressWarnings("unchecked")
  public List<CmsDocReferralClient> listDocReferralClient(String docHandle) {
    final NativeQuery<CmsDocReferralClient> query =
        this.grabSession().getNamedNativeQuery("DocReferalClient");
    query.setParameter("docHandle", docHandle, StringType.INSTANCE);
    return query.list();
  }

}
