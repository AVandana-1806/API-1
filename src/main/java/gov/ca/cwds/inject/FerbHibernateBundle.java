package gov.ca.cwds.inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.core.Api;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;

/**
 * Ferb extension of DropWizard's {@link HibernateBundle} increases the visibility of
 * {@link #name()} to public.
 * 
 * @author CWDS API Team
 */
public abstract class FerbHibernateBundle extends HibernateBundle<ApiConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(FerbHibernateBundle.class);

  public static final String CMS_BUNDLE_TAG = Api.DATASOURCE_CMS;
  public static final String NS_BUNDLE_TAG = Api.DATASOURCE_NS;
  public static final String RS_BUNDLE_TAG = Api.DATASOURCE_CMS_REP;

  public FerbHibernateBundle(ImmutableList<Class<?>> entities,
      SessionFactoryFactory sessionFactoryFactory) {
    super(entities, sessionFactoryFactory);
    LOGGER.info("FerbHibernateBundle ctor");
  }

  @Override
  public abstract String name();

}
