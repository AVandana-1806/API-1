package gov.ca.cwds.inject;

import org.hibernate.Interceptor;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.tracelog.SimpleTraceLogService;
import io.dropwizard.hibernate.SessionFactoryFactory;

/**
 * Configure Hibernate with a custom interceptor before constructing a session factory.
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class ApiSessionFactoryFactory extends SessionFactoryFactory {

  private Interceptor interceptor;

  /**
   * Default ctor, uses default interceptor, {@link SimpleTraceLogService}.
   */
  public ApiSessionFactoryFactory() {
    this.interceptor = new ApiHibernateInterceptor(new SimpleTraceLogService());
  }

  @Inject
  public ApiSessionFactoryFactory(Interceptor interceptor) {
    this.interceptor = interceptor;
  }

  @Override
  protected void configure(Configuration configuration, ServiceRegistry registry) {
    configuration.setInterceptor(interceptor);
  }

}
