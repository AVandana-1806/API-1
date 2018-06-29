package gov.ca.cwds.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class SystemCodeCacheConfigurationTest extends Doofenshmirtz<ClientAddress> {

  SystemCodeCacheConfiguration target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new SystemCodeCacheConfiguration();
  }

  @Test
  public void type() throws Exception {
    assertThat(SystemCodeCacheConfiguration.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getPreLoad_A$() throws Exception {
    Boolean actual = target.getPreLoad();
    Boolean expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPreLoad_A$Boolean() throws Exception {
    Boolean defaultValue = null;
    Boolean actual = target.getPreLoad(defaultValue);
    Boolean expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPreLoad_A$Boolean() throws Exception {
    Boolean preLoad = null;
    target.setPreLoad(preLoad);
  }

  @Test
  public void getRefreshAfter_A$() throws Exception {
    Long actual = target.getRefreshAfter();
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getRefreshAfter_A$Long() throws Exception {
    Long defaultValue = null;
    Long actual = target.getRefreshAfter(defaultValue);
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRefreshAfter_A$Long() throws Exception {
    Long refreshAfter = null;
    target.setRefreshAfter(refreshAfter);
  }

}
