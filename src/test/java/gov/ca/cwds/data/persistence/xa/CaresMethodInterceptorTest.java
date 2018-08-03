package gov.ca.cwds.data.persistence.xa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.inject.CaresUnitOfWorkInterceptorTest;
import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class CaresMethodInterceptorTest extends Doofenshmirtz<ClientAddress> {

  org.aopalliance.intercept.MethodInvocation mi;
  Method method;

  CaresMethodInterceptor target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new CaresMethodInterceptor();

    mi = mock(org.aopalliance.intercept.MethodInvocation.class);
    final Class<?>[] parameterTypes = {};
    method = CaresUnitOfWorkInterceptorTest.getAnyMethod(AccessLimitation.class,
        "getLimitedAccessCode", parameterTypes);
    when(mi.getMethod()).thenReturn(method);
  }

  @Test
  public void type() throws Exception {
    assertThat(CaresMethodInterceptor.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void incrementCount_A$Method$Map() throws Exception {
    final Map<Method, AtomicLong> map = new HashMap<Method, AtomicLong>();
    final long actual = target.incrementCount(method, map);
    final long expected = 1L;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void resetRequest_A$() throws Exception {
    target.resetRequest();
  }

  @Test
  public void incrementTotalCount_A$Method() throws Exception {
    final long actual = target.incrementTotalCount(method);
    final long expected = 1L;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void incrementRequestCount_A$Method() throws Exception {
    final long actual = target.incrementRequestCount(method);
    final long expected = 1L;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void invoke_A$orgaopallianceinterceptMethodInvocation() throws Throwable {
    final Object actual = target.invoke(mi);
    final Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void invoke_A$orgaopallianceinterceptMethodInvocation_T$Throwable() throws Throwable {
    try {
      target.invoke(mi);
      fail("Expected exception was not thrown!");
    } catch (Throwable e) {
    }
  }

  @Test
  public void key_A$() throws Exception {
    final Serializable actual = target.key();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void startRequest_A$RequestExecutionContext() throws Exception {
    final RequestExecutionContext ctx = mock(RequestExecutionContext.class);
    target.startRequest(ctx);
  }

  @Test
  public void endRequest_A$RequestExecutionContext() throws Exception {
    final RequestExecutionContext ctx = mock(RequestExecutionContext.class);
    target.endRequest(ctx);
  }

}
