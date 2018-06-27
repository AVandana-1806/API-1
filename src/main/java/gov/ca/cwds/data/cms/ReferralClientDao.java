package gov.ca.cwds.data.cms;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ReferralClient}.
 * 
 * @author CWDS API Team
 */
public class ReferralClientDao extends CrudsDaoImpl<ReferralClient> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReferralClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find by referral id
   * 
   * @param referralId - referralId
   * @return the referralClient,
   */
  @SuppressWarnings("unchecked")
  public ReferralClient[] findByReferralId(String referralId) {
    final Query<ReferralClient> query = this.grabSession().createQuery(
        "gov.ca.cwds.data.persistence.cms.ReferralClient.findByReferral", ReferralClient.class);
    query.setParameter("referralId", referralId, StringType.INSTANCE);
    return query.list().toArray(new ReferralClient[0]);
  }

  /**
   * Find by client ids
   * 
   * @param clientIds - clientlIds
   * @return the referalClients
   */
  @SuppressWarnings("unchecked")
  public ReferralClient[] findByClientIds(Collection<String> clientIds) {
    final Query<ReferralClient> query = this.grabSession().createQuery(
        "gov.ca.cwds.data.persistence.cms.ReferralClient.findByClientIds", ReferralClient.class);
    query.setParameterList("clientIds", clientIds, StringType.INSTANCE);
    return query.list().toArray(new ReferralClient[0]);
  }

}
