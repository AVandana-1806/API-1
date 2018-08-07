package gov.ca.cwds.data.dao.contact;

import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StringType;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ContactPartyDeliveredServiceEntity}.
 *
 * @author CWDS API Team
 */
public class ContactPartyDeliveredServiceDao
    extends CrudsDaoImpl<ContactPartyDeliveredServiceEntity> {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public ContactPartyDeliveredServiceDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find the unique Contact Party Delivered Service Record associated with a Delivered Service
   * 
   * @param deliveredServiceId the Delivered Service identifier
   * @return the Contact Party Delivered Service Record
   */
  @SuppressWarnings("unchecked")
  public ContactPartyDeliveredServiceEntity findByDeliveredServiceId(String deliveredServiceId) {
    final NativeQuery<ContactPartyDeliveredServiceEntity> query =
        this.grabSession().getNamedNativeQuery(
            "gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity.findByDeliveredServiceId");
    query.setParameter("deliveredServiceId", deliveredServiceId, StringType.INSTANCE);
    return query.getSingleResult();
  }

}
