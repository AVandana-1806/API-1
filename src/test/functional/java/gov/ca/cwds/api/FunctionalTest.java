package gov.ca.cwds.api;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.authenticate.config.ConfigImpl;
import gov.ca.cwds.config.CwdsAuthenticationClientConfig;
import gov.ca.cwds.rest.authenticate.AuthenticationUtils;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.authenticate.UserInfo;

/**
 * @author CWDS API Team
 */
public class FunctionalTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(FunctionalTest.class);

  String url;

  CwdsAuthenticationClientConfig config;

  public String token;

  public UserInfo userInfo;

  /**
   * @throws FileNotFoundException - FileNotFoundException
   */
  @Before
  public void init() throws FileNotFoundException {
    ConfigImpl configImpl = new ConfigImpl();
    CwdsAuthenticationClientConfig config = configImpl.readConfig();
    url = config.getTestUrl().getBaseUrl();
    LOGGER.info(configImpl.toString());
    printEnv();
    token = login(configImpl);
    userInfo = getStaffpersonInfo(configImpl);
  }

  private void printEnv() {
    Map<String, String> env = System.getenv();
    for (String envName : env.keySet()) {
      LOGGER.info("{}={}", envName, env.get(envName));
    }
  }

  private String login(ConfigImpl configImpl) {
    return new AuthenticationUtils(configImpl).getToken(UserGroup.SOCIAL_WORKER);
  }

  private UserInfo getStaffpersonInfo(ConfigImpl configImpl) {
    return new AuthenticationUtils(configImpl).getStaffPersonDetails(token);
  }

  protected String getResourceUrlFor(String resource) {
    return url + resource;
  }

}
