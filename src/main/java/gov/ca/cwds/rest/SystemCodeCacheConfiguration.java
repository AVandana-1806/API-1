package gov.ca.cwds.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.services.cms.CachingSystemCodeService;

/**
 * Represents the configuration settings for {@link CachingSystemCodeService}.
 * 
 * @author CWDS API Team
 */
public class SystemCodeCacheConfiguration {

  @JsonProperty("preLoad")
  private Boolean preLoad;

  @JsonProperty("refreshAfter")
  private Long refreshAfter;

  /**
   * Default constructor.
   */
  public SystemCodeCacheConfiguration() {
    // Default.
  }

  public Boolean getPreLoad() {
    return preLoad;
  }

  public Boolean getPreLoad(Boolean defaultValue) {
    return preLoad != null ? preLoad : defaultValue;
  }

  public void setPreLoad(Boolean preLoad) {
    this.preLoad = preLoad;
  }

  public Long getRefreshAfter() {
    return refreshAfter;
  }

  public Long getRefreshAfter(Long defaultValue) {
    return refreshAfter != null ? refreshAfter : defaultValue;
  }

  public void setRefreshAfter(Long refreshAfter) {
    this.refreshAfter = refreshAfter;
  }
}
