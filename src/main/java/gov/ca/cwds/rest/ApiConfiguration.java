package gov.ca.cwds.rest;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.knowm.dropwizard.sundial.SundialConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.spinscale.dropwizard.jobs.JobConfiguration;
import io.dropwizard.db.DataSourceFactory;

/**
 * Container for Ferb API configuration.
 * 
 * @author CWDS API Team
 */
public class ApiConfiguration extends BaseApiConfiguration implements JobConfiguration {

  private DataSourceFactory rsDataSourceFactory;
  private TestingConfiguration testConfig;
  private boolean upgradeDbOnStart = false;

  @Valid
  @NotNull
  public SundialConfiguration sundial = new SundialConfiguration();

  @Nullable
  @JsonProperty(value = "systemCodeCache")
  private SystemCodeCacheConfiguration systemCodeCacheConfiguration;

  @Nullable
  @JsonProperty(value = "intakeCodeCache")
  private SystemCodeCacheConfiguration intakeCodeCacheConfiguration;

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

  public SystemCodeCacheConfiguration getIntakeCodeCacheConfiguration() {
    return intakeCodeCacheConfiguration;
  }

  public void setIntakeCodeCacheConfiguration(
      SystemCodeCacheConfiguration intakeCodeCacheConfiguration) {
    this.intakeCodeCacheConfiguration = intakeCodeCacheConfiguration;
  }

  @JsonProperty("sundial")
  public SundialConfiguration getSundial() {
    return sundial;
  }

  public void setSundial(SundialConfiguration sundial) {
    this.sundial = sundial;
  }
}
