package gov.ca.cwds.inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.rest.ApiConfiguration;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;

/**
 * Extension of DropWizard's {@link HibernateBundle} increases the visibility of {@link #name()}.
 * 
 * @author CWDS API Team
 */
public abstract class FerbHibernateBundle extends HibernateBundle<ApiConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(FerbHibernateBundle.class);

  public static final String CMS_BUNDLE_TAG = "cms";
  public static final String NS_BUNDLE_TAG = "ns";

  FerbHibernateBundle(ImmutableList<Class<?>> entities,
      SessionFactoryFactory sessionFactoryFactory) {
    super(entities, sessionFactoryFactory);
    LOGGER.info("FerbHibernateBundle ctor");
  }

  @Override
  public abstract String name();

}
