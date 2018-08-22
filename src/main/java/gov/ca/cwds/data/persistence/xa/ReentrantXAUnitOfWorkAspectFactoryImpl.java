package gov.ca.cwds.data.persistence.xa;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.dao.cms.BaseAuthorizationDao;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.RequestExecutionContext.Parameter;
import gov.ca.cwds.rest.filters.RequestExecutionContextCallback;
import gov.ca.cwds.rest.filters.RequestExecutionContextRegistry;

/**
 * Reentrant handler allows for nested {@link XAUnitOfWork} annotations.
 * 
 * <p>
 * Returns a new AOP {@link XAUnitOfWorkAspect} for the first {@link XAUnitOfWork} encountered, or
 * if XA has already started, returns the request's XA aspect and joins the transaction.
 * </p>
 * 
 * @author CWDS API Team
 * @see RequestExecutionContextRegistry
 */
public class ReentrantXAUnitOfWorkAspectFactoryImpl
    implements RequestExecutionContextCallback, XAUnitOfWorkAspectFactory {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ReentrantXAUnitOfWorkAspectFactoryImpl.class);

  private final Map<String, SessionFactory> sessionFactories;

  private transient ThreadLocal<XAUnitOfWorkAspect> local = new ThreadLocal<>();

  public ReentrantXAUnitOfWorkAspectFactoryImpl(Map<String, SessionFactory> sessionFactories) {
    this.sessionFactories = sessionFactories;

    // Notify this instance when requests start or end.
    RequestExecutionContextRegistry.registerCallback(this);
  }

  @Override
  public Serializable key() {
    return ReentrantXAUnitOfWorkAspectFactoryImpl.class.getName();
  }

  private void clearRequest() {
    local.set(null); // clear the current thread
    BaseAuthorizationDao.clearXaMode();
    RequestExecutionContext.instance().put(Parameter.XA_TRANSACTION, Boolean.FALSE);
  }

  @Override
  public void startRequest(RequestExecutionContext ctx) {
    LOGGER.info("start request");
    clearRequest();
  }

  @Override
  public void endRequest(RequestExecutionContext ctx) {
    LOGGER.info("end request");
    clearRequest();
  }

  protected XAUnitOfWorkAspect make(Map<String, SessionFactory> someSessionFactories) {
    BaseAuthorizationDao.setXaMode(true);
    RequestExecutionContext.instance().put(Parameter.XA_TRANSACTION, Boolean.TRUE);

    XAUnitOfWorkAspect ret = local.get();
    if (ret == null) {
      ret = new XAUnitOfWorkAspect(someSessionFactories);
      local.set(ret);
    }

    return ret;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.xa.XAUnitOfWorkAspectFactory#make()
   */
  @Override
  public XAUnitOfWorkAspect make() {
    return make(sessionFactories);
  }

}
