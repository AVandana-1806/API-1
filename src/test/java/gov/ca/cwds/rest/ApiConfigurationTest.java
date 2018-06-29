package gov.ca.cwds.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.util.Doofenshmirtz;
import io.dropwizard.db.DataSourceFactory;

public class ApiConfigurationTest extends Doofenshmirtz<ClientAddress> {

  ApiConfiguration target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new ApiConfiguration();
  }

  @Test
  public void type() throws Exception {
    assertThat(ApiConfiguration.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void setRsDataSourceFactory_A$DataSourceFactory() throws Exception {
    DataSourceFactory rsDataSourceFactory = mock(DataSourceFactory.class);
    target.setRsDataSourceFactory(rsDataSourceFactory);
  }

  @Test
  public void getRsDataSourceFactory_A$() throws Exception {
    DataSourceFactory actual = target.getRsDataSourceFactory();
    DataSourceFactory expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getTestConfig_A$() throws Exception {
    TestingConfiguration actual = target.getTestConfig();
    TestingConfiguration expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setTestConfig_A$TestingConfiguration() throws Exception {
    TestingConfiguration testConfig = mock(TestingConfiguration.class);
    target.setTestConfig(testConfig);
  }

  @Test
  public void isUpgradeDbOnStart_A$() throws Exception {
    boolean actual = target.isUpgradeDbOnStart();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setUpgradeDbOnStart_A$boolean() throws Exception {
    boolean upgradeDbOnStart = false;
    target.setUpgradeDbOnStart(upgradeDbOnStart);
  }

  @Test
  public void getSystemCodeCacheConfiguration_A$() throws Exception {
    SystemCodeCacheConfiguration actual = target.getSystemCodeCacheConfiguration();
    SystemCodeCacheConfiguration expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSystemCodeCacheConfiguration_A$SystemCodeCacheConfiguration() throws Exception {
    SystemCodeCacheConfiguration systemCodeCacheConfig = mock(SystemCodeCacheConfiguration.class);
    target.setSystemCodeCacheConfiguration(systemCodeCacheConfig);
  }

  @Test
  public void getIntakeCodeCacheConfiguration_A$() throws Exception {
    SystemCodeCacheConfiguration actual = target.getIntakeCodeCacheConfiguration();
    SystemCodeCacheConfiguration expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIntakeCodeCacheConfiguration_A$SystemCodeCacheConfiguration() throws Exception {
    SystemCodeCacheConfiguration intakeCodeCacheConfiguration =
        mock(SystemCodeCacheConfiguration.class);
    target.setIntakeCodeCacheConfiguration(intakeCodeCacheConfiguration);
  }

}
