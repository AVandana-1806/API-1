package gov.ca.cwds.inject;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.tracelog.SimpleTraceLogService;
import gov.ca.cwds.tracelog.TraceLogService;
import io.dropwizard.hibernate.SessionFactoryFactory;

/**
 * Configure Hibernate with a custom interceptor before constructing a session factory.
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class ApiSessionFactoryFactory extends SessionFactoryFactory {

  private final TraceLogService traceLogService;

  /**
   * Default ctor, uses default interceptor, {@link SimpleTraceLogService}.
   */
  public ApiSessionFactoryFactory() {
    this.traceLogService = new SimpleTraceLogService();
  }

  @Inject
  public ApiSessionFactoryFactory(TraceLogService traceLogService) {
    this.traceLogService = traceLogService;
  }

  @Override
  protected void configure(Configuration configuration, ServiceRegistry registry) {
    configuration.setInterceptor(new ApiHibernateInterceptor(traceLogService));
  }

}
