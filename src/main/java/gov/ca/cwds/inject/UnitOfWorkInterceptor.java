package gov.ca.cwds.inject;

import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.prepareHibernateStatisticsConsumer;
import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.provideHibernateStatistics;

import java.lang.reflect.Method;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;

import gov.ca.cwds.data.dao.cms.BaseAuthorizationDao;
import gov.ca.cwds.data.persistence.xa.PhineasMethodLoggerInterceptor;
import gov.ca.cwds.data.persistence.xa.WorkFerbUserInfo;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.RequestExecutionContext.Parameter;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.hibernate.UnitOfWorkAspect;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;

/**
 * AOP method interceptor manages database transactions outside of DropWizard resource classes.
 *
 * <h3>Sample Usage</h3>
 *
 * <blockquote>
 *
 * <pre>
 * final UnitOfWorkInterceptor interceptor = new UnitOfWorkInterceptor();
 * bindInterceptor(Matchers.any(), Matchers.annotatedWith(UnitOfWork.class), interceptor);
 * requestInjection(interceptor);
 * </pre>
 *
 * </blockquote>
 *
 * @author CWDS API Team
 */
public class UnitOfWorkInterceptor extends PhineasMethodLoggerInterceptor {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(UnitOfWorkInterceptor.class);

  UnitOfWorkAwareProxyFactory proxyFactory;

  @Inject
  @CmsSessionFactory
  SessionFactory cmsSessionFactory;

  @Inject
  @CwsRsSessionFactory
  SessionFactory rsSessionFactory;

  @Inject
  @NsSessionFactory
  SessionFactory nsSessionFactory;

  @Override
  public Object invoke(org.aopalliance.intercept.MethodInvocation mi) throws Throwable {
    final Method m = mi.getMethod();
    final RequestExecutionContext ctx = RequestExecutionContext.instance();
    LOGGER.info("Unit of work interceptor: class: {}, method: {}", m.getDeclaringClass(),
        m.getName());

    // If already in an XA transaction, skip this @UnitOfWork.
    if (ctx != null && RequestExecutionContext.instance().isXaTransaction()) {
      LOGGER.warn("******* XA TRANSACTION: SKIP @UnitOfWork! class: {}, method: {}******* ",
          m.getDeclaringClass(), m.getName());
      return mi.proceed();
    }

    // Use our wrapped (Candace) session factories and related wrappers.
    final UnitOfWork annotation = mi.getMethod().getAnnotation(UnitOfWork.class);
    final String name = annotation.value().trim();
    SessionFactory currentSessionFactory;

    // Find the right session factory.
    switch (name) {
      case Api.DS_CMS:
        currentSessionFactory = cmsSessionFactory;
        break;
      case Api.DATASOURCE_CMS_REP:
        currentSessionFactory = rsSessionFactory;
        break;
      case Api.DS_NS:
        currentSessionFactory = nsSessionFactory;
        break;
      default:
        throw new IllegalStateException("Unknown datasource! " + annotation.value());
    }

    // Build the aspect with our wrapped session factory, not a hibernate bundle:
    proxyFactory = UnitOfWorkModule.getUnitOfWorkProxyFactory(name, currentSessionFactory);
    final UnitOfWorkAspect aspect =
        proxyFactory.newAspect(ImmutableMap.of(name, currentSessionFactory));

    try {
      // Not XA, so clear XA flags.
      BaseAuthorizationDao.clearXaMode();
      RequestExecutionContext.instance().put(Parameter.XA_TRANSACTION, Boolean.FALSE);

      aspect.beforeStart(annotation);
      final Session session = currentSessionFactory.getCurrentSession();
      session.doWork(new WorkFerbUserInfo()); // Fine for all datasources.

      prepareHibernateStatisticsConsumer(name, currentSessionFactory.getStatistics());
      final Object result = mi.proceed();
      provideHibernateStatistics(name, currentSessionFactory.getStatistics());
      aspect.afterEnd();

      return result;
    } catch (Exception e) {
      LOGGER.error("UNIT OF WORK FAILED! {}", e.getMessage(), e);
      aspect.onError();
      throw e;
    } finally {
      LOGGER.debug("Unit of work finished");
      aspect.onFinish();
    }
  }

}
