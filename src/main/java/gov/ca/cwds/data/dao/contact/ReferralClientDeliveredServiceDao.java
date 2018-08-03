package gov.ca.cwds.data.dao.contact;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ReferralClientDeliveredServiceEntity}.
 * 
 * @author CWDS API Team
 */
public class ReferralClientDeliveredServiceDao
    extends CrudsDaoImpl<ReferralClientDeliveredServiceEntity> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReferralClientDeliveredServiceDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @SuppressWarnings("unchecked")
  public ReferralClientDeliveredServiceEntity[] findByReferralId(String referralId) {
    final Query<ReferralClientDeliveredServiceEntity> query = this.grabSession().getNamedQuery(
        "gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity.findAllForReferralId");
    query.setParameter("referralId", referralId, StringType.INSTANCE);
    return query.list().toArray(new ReferralClientDeliveredServiceEntity[0]);
  }

}
