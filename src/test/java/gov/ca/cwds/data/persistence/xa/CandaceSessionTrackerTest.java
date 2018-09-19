package gov.ca.cwds.data.persistence.xa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.hibernate.Session;
import org.junit.Test;

import gov.ca.cwds.auth.realms.PerryUserIdentity;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.util.Doofenshmirtz;
import gov.ca.cwds.utils.JsonUtils;

public class CandaceSessionTrackerTest extends Doofenshmirtz<ClientAddress> {

  CandaceSessionTracker target;
  PerryUserIdentity defaultIdentity;
  final long startTime = System.currentTimeMillis();
  final String defaultUserId = "02f";

  @Override
  public void setup() throws Exception {
    super.setup();

    target = new CandaceSessionTracker(
        new CandaceSessionFactoryImpl(Api.DS_CMS, sessionFactory, sessionFactory), session);

    final String json =
        "{\"user\":\"02f\",\"roles\":null,\"staffId\":null,\"privileges\":null,\"authorityCodes\":null,\"first_name\":null,\"last_name\":null,\"county_code\":\"99\",\"county_cws_code\":\"1126\",\"county_name\":null}";
    defaultIdentity = JsonUtils.from(json, PerryUserIdentity.class);
  }

  @Test
  public void crap() throws Exception {
    final PerryUserIdentity actual = target.getUserIdentity();
    final String json = JsonUtils.to(actual);
    System.out.println(json);
  }

  @Test
  public void type() throws Exception {
    assertThat(CandaceSessionTracker.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getUserId_A$() throws Exception {
    final String actual = target.getUserId();
    final String expected = defaultUserId;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStaffId_A$() throws Exception {
    final String actual = target.getStaffId();
    final String expected = defaultUserId;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getRequestStartTime_A$() throws Exception {
    final Date actual = target.getRequestStartTime();
    final Date expected = new Date(startTime);
    assertThat(actual, is(greaterThan(expected)));
  }

  @Test
  public void isXaTransaction_A$() throws Exception {
    final boolean actual = target.isXaTransaction();
    final boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getUserIdentity_A$() throws Exception {
    final PerryUserIdentity actual = target.getUserIdentity();
    final PerryUserIdentity expected = defaultIdentity;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void isResourceReadOnly_A$() throws Exception {
    final boolean actual = target.isResourceReadOnly();
    final boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getThreadId_A$() throws Exception {
    final long actual = target.getThreadId();
    final long expected = 0L;
    assertThat(actual, is(greaterThan(expected)));
  }

  @Test
  public void isThreadOwner_A$() throws Exception {
    final boolean actual = target.isThreadOwner();
    final boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStartTime_A$() throws Exception {
    final long actual = target.getStartTime();
    final long expected = startTime;
    assertThat(actual, is(greaterThan(expected)));
  }

  @Test
  public void getStack_A$() throws Exception {
    final StackTraceElement[] actual = target.getStack();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getSession_A$() throws Exception {
    final Session actual = target.getSession();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getId_A$() throws Exception {
    final int actual = target.getId();
    final int expected = 16;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSessionFactory_A$() throws Exception {
    final CandaceSessionFactoryImpl actual = target.getSessionFactory();
    assertThat(actual, is(notNullValue()));
  }

}
