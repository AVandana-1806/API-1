package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.ContactEntity;
import gov.ca.cwds.inject.NsSessionFactory;
import org.hibernate.SessionFactory;

public class ContactDao extends CrudsDaoImpl<ContactEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public ContactDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
