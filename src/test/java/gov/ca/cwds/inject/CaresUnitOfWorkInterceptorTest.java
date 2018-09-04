package gov.ca.cwds.inject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.xa.CaresUnitOfWorkInterceptor;
import gov.ca.cwds.rest.resources.investigation.InvestigationsResource;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class CaresUnitOfWorkInterceptorTest extends Doofenshmirtz<ClientAddress> {

  CaresUnitOfWorkInterceptor target;

  // Look up methods through reflection.
  public static final Method getAnyMethod(Class<?> cls, String name, Class<?>[] argTypes)
      throws NoSuchMethodException {
    try {
      return cls.getDeclaredMethod(name, argTypes);
    } catch (NoSuchMethodException ex) {
      final Class<?>[] clses = cls.getInterfaces();
      for (int j = 0; j < clses.length; ++j)
        try {
          return getAnyMethod(clses[j], name, argTypes);
        } catch (NoSuchMethodException e2) {
          // Ignore.
        }

      cls = cls.getSuperclass();
      if (cls == null)
        throw ex;
      return getAnyMethod(cls, name, argTypes);
    }
  }

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    target = new CaresUnitOfWorkInterceptor();
    target.setCmsSessionFactory(sessionFactory);
    target.setNsSessionFactory(sessionFactory);
    target.setRsSessionFactory(sessionFactory);
  }

  @Test
  public void type() throws Exception {
    assertThat(CaresUnitOfWorkInterceptor.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void resetRequest_A$() throws Exception {
    target.resetRequest();
  }

  @Test
  public void invoke_A$orgaopallianceinterceptMethodInvocation() throws Throwable {
    final org.aopalliance.intercept.MethodInvocation mi =
        mock(org.aopalliance.intercept.MethodInvocation.class);

    final Class<?>[] parameterTypes = {String.class};
    final Method m = getAnyMethod(InvestigationsResource.class, "find", parameterTypes);
    when(mi.getMethod()).thenReturn(m);

    final Object actual = target.invoke(mi);
    final Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void invoke_A$orgaopallianceinterceptMethodInvocation_T$Throwable() throws Throwable {
    final org.aopalliance.intercept.MethodInvocation mi =
        mock(org.aopalliance.intercept.MethodInvocation.class);
    try {
      target.invoke(mi);
      fail("Expected exception was not thrown!");
    } catch (Throwable e) {
    }
  }

}
