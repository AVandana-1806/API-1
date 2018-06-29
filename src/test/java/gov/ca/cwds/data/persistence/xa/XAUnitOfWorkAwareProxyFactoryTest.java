package gov.ca.cwds.data.persistence.xa;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.inject.FerbHibernateBundle;
import gov.ca.cwds.rest.resources.AddressResource;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class XAUnitOfWorkAwareProxyFactoryTest extends Doofenshmirtz<Addresses> {

  ImmutableMap<String, SessionFactory> sessionFactories;
  XAUnitOfWork xaUnitOfWork;
  FerbHibernateBundle[] bundles;
  XAUnitOfWorkAwareProxyFactory target;

  @Override
  public void setup() throws Exception {
    super.setup();
    sessionFactories = ImmutableMap.<String, SessionFactory>of("cms", sessionFactory);
    xaUnitOfWork = mock(XAUnitOfWork.class);

    final FerbHibernateBundle bundle = mock(FerbHibernateBundle.class);
    when(bundle.getSessionFactory()).thenReturn(sessionFactory);
    when(bundle.name()).thenReturn("cms");

    bundles = new FerbHibernateBundle[1];
    bundles[0] = bundle;
    target = new XAUnitOfWorkAwareProxyFactory(bundles);

    final String[] values = {"cms", "ns"};
    when(xaUnitOfWork.value()).thenReturn(values);
    when(xaUnitOfWork.cacheMode()).thenReturn(CacheMode.NORMAL);
    when(xaUnitOfWork.flushMode()).thenReturn(FlushMode.MANUAL);
    when(xaUnitOfWork.readOnly()).thenReturn(false);
    when(xaUnitOfWork.transactional()).thenReturn(true);
  }

  @Test
  public void type() throws Exception {
    assertThat(XAUnitOfWorkAwareProxyFactory.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test(expected = Exception.class)
  public void create_A$Class() throws Exception {
    final Class<?> clazz = AddressResource.class;
    Object actual = target.create(clazz);
    assertThat(actual, is(notNullValue()));
  }

  // @Test
  // public void create_A$Class$Class$Object() throws Exception {
  // Class<Object> clazz = xa;
  // Class<?> constructorParamType = mock(Class.class);
  // Object constructorArguments = null;
  // Object actual = target.create(clazz, constructorParamType, constructorArguments);
  // Object expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

  // @Test
  // public void create_A$Class$ClassArray$ObjectArray() throws Exception {
  // final Class<?> clazz = AddressService.class;
  // Class<?>[] constructorParamTypes = new Class<?>[] {};
  // Object[] constructorArguments = new Object[] {};
  // Object actual = target.create(clazz, constructorParamTypes, constructorArguments);
  // Object expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

  @Test
  public void newAspect_A$() throws Exception {
    XAUnitOfWorkAspect actual = target.newAspect();
    assertThat(actual, is(notNullValue()));
  }

}
