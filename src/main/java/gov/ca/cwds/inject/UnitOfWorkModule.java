package gov.ca.cwds.inject;

import gov.ca.cwds.data.persistence.xa.XAUnitOfWorkAwareProxyFactory;
import gov.ca.cwds.rest.ApiConfiguration;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;

/**
 * Provide factories to manage Hibernate sessions and transactions.
 * 
 * @author CWDS API Team
 */
public class UnitOfWorkModule {

  private static UnitOfWorkAwareProxyFactory proxyFactory;

  private static XAUnitOfWorkAwareProxyFactory xaProxyFactory;

  private UnitOfWorkModule() {
    // Hidden, no-op
  }

  /**
   * @param bundles the Hibernate bundles
   * @return the proxyFactory
   */
  @SuppressWarnings("unchecked")
  public static UnitOfWorkAwareProxyFactory getUnitOfWorkProxyFactory(
      HibernateBundle<ApiConfiguration>... bundles) {
    if (proxyFactory == null) {
      proxyFactory = new UnitOfWorkAwareProxyFactory(bundles);
    }
    return proxyFactory;
  }

  public static XAUnitOfWorkAwareProxyFactory getXAUnitOfWorkProxyFactory(
      FerbHibernateBundle... bundles) {
    if (xaProxyFactory == null) {
      xaProxyFactory = new XAUnitOfWorkAwareProxyFactory(bundles);
    }
    return xaProxyFactory;
  }

}
