package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaAddressDao extends BaseDaoImpl<Address> {

  @Inject
  public XaAddressDao(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
