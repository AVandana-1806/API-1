package gov.ca.cwds.rest;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.db.DataSourceFactory;

/**
 * 
 * 
 * @author CWDS API Team
 */
public class ApiConfiguration extends BaseApiConfiguration {

  private DataSourceFactory rsDataSourceFactory;
  private TestingConfiguration testConfig;
  private boolean upgradeDbOnStart = false;

  @Nullable
  @JsonProperty(value = "systemCodeCache")
  private SystemCodeCacheConfiguration systemCodeCacheConfiguration;

  public void setRsDataSourceFactory(DataSourceFactory rsDataSourceFactory) {
    this.rsDataSourceFactory = rsDataSourceFactory;
  }

  @JsonProperty
  public DataSourceFactory getRsDataSourceFactory() {
    return rsDataSourceFactory;
  }

  @JsonProperty
  public TestingConfiguration getTestConfig() {
    return testConfig;
  }

  @JsonProperty
  public void setTestConfig(TestingConfiguration testConfig) {
    this.testConfig = testConfig;
  }

  @JsonProperty
  public boolean isUpgradeDbOnStart() {
    return upgradeDbOnStart;
  }

  public void setUpgradeDbOnStart(boolean upgradeDbOnStart) {
    this.upgradeDbOnStart = upgradeDbOnStart;
  }

  public SystemCodeCacheConfiguration getSystemCodeCacheConfiguration() {
    return systemCodeCacheConfiguration;
  }

  public void setSystemCodeCacheConfiguration(SystemCodeCacheConfiguration systemCodeCacheConfig) {
    this.systemCodeCacheConfiguration = systemCodeCacheConfig;
  }
}
