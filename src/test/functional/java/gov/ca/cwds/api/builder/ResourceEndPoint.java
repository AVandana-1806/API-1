package gov.ca.cwds.api.builder;

/**
 * @author CWDS API team
 *
 */
public enum ResourceEndPoint {

  //
  // CHECKSTYLE:OFF
  //
  HOI_REFERRALS("/hoi_referrals"),

  REFERRALS("/referrals"),
  
  CLIENTS_RELATIONSHIPS("/clients/relationships");

  private final String resourcePath;

  private ResourceEndPoint(String resourcePath) {
    this.resourcePath = resourcePath;
  }

  /**
   * getResourcePath.
   * 
   * @return the resourcePath
   */
  public String getResourcePath() {
    return resourcePath;
  }

}
