package gov.ca.cwds.data.persistence.xa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class XAUnitOfWorkAspectTest extends Doofenshmirtz<Addresses> {

  ImmutableMap<String, SessionFactory> sessionFactories;
  XAUnitOfWorkAspect target;
  XAUnitOfWork xaUnitOfWork;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    sessionFactories =
        ImmutableMap.<String, SessionFactory>of(Api.DATASOURCE_XA_CMS, sessionFactory);
    xaUnitOfWork = mock(XAUnitOfWork.class);

    target = new XAUnitOfWorkAspect(sessionFactories);
    target.setXaUnitOfWork(xaUnitOfWork);

    final String[] values = {Api.DATASOURCE_XA_CMS, Api.DATASOURCE_XA_NS};
    when(xaUnitOfWork.value()).thenReturn(values);
    when(xaUnitOfWork.cacheMode()).thenReturn(CacheMode.NORMAL);
    when(xaUnitOfWork.flushMode()).thenReturn(FlushMode.MANUAL);
    when(xaUnitOfWork.readOnly()).thenReturn(false);
    when(xaUnitOfWork.transactional()).thenReturn(true);
  }

  @Test
  public void type() throws Exception {
    assertThat(XAUnitOfWorkAspect.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void beforeStart_A1() throws Exception {
    final Method method =
        MethodUtils.getMatchingMethod(this.getClass(), "beforeStart_A1", new Class[0]);
    target.beforeStart(method, xaUnitOfWork);
  }

  @Test
  public void afterEnd_A1() throws Exception {
    final Method method = MethodUtils.getMatchingMethod(getClass(), "afterEnd_A1", new Class[0]);
    target.beforeStart(method, xaUnitOfWork);
  }

  @Test
  public void onError_A$() throws Exception {
    target.onError();
  }

  @Test
  public void onFinish_A$() throws Exception {
    target.onFinish();
  }

  @Test
  public void grabSession_A$SessionFactory() throws Exception {
    Session actual = target.grabSession(Api.DATASOURCE_XA_CMS, sessionFactory);
    Session expected = session;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void openSessions_A$() throws Exception {
    target.openSessions();
  }

  @Test
  public void closeSessions_A$() throws Exception {
    target.closeSessions();
  }

  @Test
  public void closeSession_A$Session() throws Exception {
    target.closeSession(session);
  }

  @Test
  public void configureSession_A$Session() throws Exception {
    target.configureSession(session);
  }

  @Test
  public void beginTransaction_A$() throws Exception {
    target.beginXaTransaction();
  }

  @Test
  public void rollbackTransaction_A$() throws Exception {
    target.rollback();
  }

  @Test
  public void commitTransaction_A$() throws Exception {
    target.commit();
  }

  @Test
  public void getSessionFactories_A$() throws Exception {
    Map<String, SessionFactory> actual = target.getSessionFactories();
    Map<String, SessionFactory> expected = this.sessionFactories;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void beforeStart_A$Method$XAUnitOfWork() throws Exception {
    final Method method =
        MethodUtils.getMatchingMethod(this.getClass(), "beforeStart_A1", new Class[0]);
    XAUnitOfWork xaUnitOfWork = mock(XAUnitOfWork.class);
    target.beforeStart(method, xaUnitOfWork);
  }

  @Test(expected = CaresXAException.class)
  public void beforeStart_A$Method$XAUnitOfWork_T$CaresXAException() throws Exception {
    final Method method =
        MethodUtils.getMatchingMethod(this.getClass(), "beforeStart_A1", new Class[0]);
    when(xaUnitOfWork.transactional()).thenThrow(CaresXAException.class);
    target.beforeStart(method, xaUnitOfWork);
  }

  @Test
  public void afterEnd_A$() throws Exception {
    target.afterEnd();
  }

  @Test(expected = CaresXAException.class)
  public void afterEnd_A$_T$CaresXAException() throws Exception {
    sessionFactories = mock(ImmutableMap.class);
    when(sessionFactories.isEmpty()).thenThrow(IllegalStateException.class);
    target = new XAUnitOfWorkAspect(sessionFactories);
    target.setXaUnitOfWork(xaUnitOfWork);

    when(xaUnitOfWork.transactional()).thenThrow(CaresXAException.class);
    target.afterEnd();
  }

  @Test
  public void grabSession_A$String$SessionFactory() throws Exception {
    String key = null;
    Session actual = target.grabSession(key, sessionFactory);
    Session expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void hasTransactionalFlag_A$() throws Exception {
    boolean actual = target.hasTransactionalFlag();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void beginXaTransaction_A$() throws Exception {
    target.beginXaTransaction();
  }

  @Test(expected = CaresXAException.class)
  public void beginXaTransaction_A$_T$CaresXAException() throws Exception {
    when(xaUnitOfWork.transactional()).thenThrow(CaresXAException.class);
    target.beginXaTransaction();
  }

  @Test
  public void rollbackSessionTransaction_A$Session() throws Exception {
    Session session = mock(Session.class);
    target.rollbackSessionTransaction(session);
  }

  @Test
  public void rollback_A$() throws Exception {
    target.rollback();
  }

  @Test(expected = CaresXAException.class)
  public void rollback_A$_T$CaresXAException() throws Exception {
    when(xaUnitOfWork.transactional()).thenThrow(CaresXAException.class);
    target.rollback();
  }

  @Test
  public void commit_A$() throws Exception {
    target.commit();
  }

  @Test(expected = CaresXAException.class)
  public void commit_A$_T$CaresXAException() throws Exception {
    when(xaUnitOfWork.transactional()).thenThrow(CaresXAException.class);
    target.commit();
  }

  @Test
  public void getXaUnitOfWork_A$() throws Exception {
    XAUnitOfWork actual = target.getXaUnitOfWork();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void setXaUnitOfWork_A$XAUnitOfWork() throws Exception {
    XAUnitOfWork xaUnitOfWork = mock(XAUnitOfWork.class);
    target.setXaUnitOfWork(xaUnitOfWork);
  }

}
