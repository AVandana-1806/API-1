package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.query.Query;

/**
 * DAO for {@link ChildClient}.
 * 
 * @author CWDS API Team
 */
public class ChildClientDao extends CrudsDaoImpl<ChildClient> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ChildClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find the victim Child Clients associated with a referral.
   *
   * @param referralId the referral identifier
   * @return the Child Clients
   */
  @SuppressWarnings("unchecked")
  public ChildClient[] findVictimClients(String referralId) {
    Query query =
        this.getSessionFactory()
            .getCurrentSession()
            .getNamedQuery("gov.ca.cwds.data.persistence.cms.ChildClient.findVictimClients")
            .setParameter("referralId", referralId);
    return (ChildClient[]) query.list().toArray(new ChildClient[0]);
  }
}
