package gov.ca.cwds.inject;

import org.hibernate.SessionFactory;

import com.google.inject.Injector;
import com.google.inject.Key;

import gov.ca.cwds.cms.data.access.inject.AbstractDataAccessServicesModule;

/**
 * CWDS API Team
 */
public class DataAccessServicesModule extends AbstractDataAccessServicesModule {

  @Override
  protected SessionFactory getDataAccessServicesSessionFactory(Injector injector) {
    return injector.getInstance(Key.get(SessionFactory.class, CmsSessionFactory.class));
  }

}
