package gov.ca.cwds.config;

import java.util.List;

/**
 * Get the values of the token credentials.
 * 
 * @author CWDS TPT-4 Team
 */
public class CwdsAuthenticationClientConfig {

  private String authenticationMode;
  private TokenCredentials testUrl;
  private List<User> defaultUsers;

  /**
   * authenticationMode.
   * 
   * @return the authenticationMode
   */
  public String getAuthenticationMode() {
    return authenticationMode;
  }

  /**
   * authenticationMode.
   * 
   * @param authenticationMode - authenticationMode
   */
  public void setAuthenticationMode(String authenticationMode) {
    this.authenticationMode = authenticationMode;
  }

  /**
   * tokenCredentials.
   * 
   * @return the tokenCredentials
   */
  public TokenCredentials getTestUrl() {
    return testUrl;
  }

  /**
   * tokenCredentials.
   * 
   * @param testUrl - testUrl
   */
  public void setTestUrl(TokenCredentials testUrl) {
    this.testUrl = testUrl;
  }

  /**
   * defaultUsers.
   * 
   * @return the defaultUsers
   */
  public List<User> getDefaultUsers() {
    return defaultUsers;
  }

  /**
   * defaultUsers.
   * 
   * @param defaultUsers - defaultUsers
   */
  public void setDefaultUsers(List<User> defaultUsers) {
    this.defaultUsers = defaultUsers;
  }

}
