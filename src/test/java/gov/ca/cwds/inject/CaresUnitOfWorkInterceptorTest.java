package gov.ca.cwds.inject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class CaresUnitOfWorkInterceptorTest extends Doofenshmirtz<ClientAddress> {

  CaresUnitOfWorkInterceptor target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new CaresUnitOfWorkInterceptor();
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
    org.aopalliance.intercept.MethodInvocation mi =
        mock(org.aopalliance.intercept.MethodInvocation.class);
    Object actual = target.invoke(mi);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void invoke_A$orgaopallianceinterceptMethodInvocation_T$Throwable() throws Throwable {
    org.aopalliance.intercept.MethodInvocation mi =
        mock(org.aopalliance.intercept.MethodInvocation.class);
    try {
      target.invoke(mi);
      fail("Expected exception was not thrown!");
    } catch (Throwable e) {
    }

  }

}
