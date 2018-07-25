package gov.ca.cwds.inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.rest.ApiConfiguration;
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

  public FerbHibernateBundle(ImmutableList<Class<?>> entities,
      SessionFactoryFactory sessionFactoryFactory) {
    super(entities, sessionFactoryFactory);
    LOGGER.info("FerbHibernateBundle ctor");
  }

  @Override
  public abstract String name();

}
