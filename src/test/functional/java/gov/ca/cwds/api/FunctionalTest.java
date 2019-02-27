package gov.ca.cwds.api;

import java.io.FileNotFoundException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.authenticate.config.ConfigImpl;
import gov.ca.cwds.config.CwdsAuthenticationClientConfig;
import gov.ca.cwds.rest.authenticate.AuthenticationUtils;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.authenticate.UserInfo;
import gov.ca.cwds.test.support.CognitoLoginAuthParams;
import gov.ca.cwds.test.support.CognitoTokenProvider;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 */
public class FunctionalTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(FunctionalTest.class);

  protected ObjectMapper objectMapper = Jackson.newObjectMapper();

  String url;
  String authMode;

  CwdsAuthenticationClientConfig config;

  public static String token;

  public static UserInfo userInfo;

  /**
   * @throws FileNotFoundException - FileNotFoundException
   */
  @Before
  public void init() throws FileNotFoundException {
    ConfigImpl configImpl = new ConfigImpl();
    CwdsAuthenticationClientConfig config = configImpl.readConfig();
    url = config.getTestUrl().getBaseUrl();
    authMode = config.getAuthenticationMode();
    LOGGER.info(configImpl.toString());
    printEnv();
    try {
      String retrievedToken = "";
      if (authMode.equals("integration")) {
        retrievedToken = System.getProperty("token");
        if (StringUtils.isBlank(retrievedToken)) {
          System.setProperty("token", "noTokenFound");
          retrievedToken = new CognitoTokenProvider().doGetToken(getLoginParams(url));
          System.setProperty("token", retrievedToken);
        }
        token = retrievedToken;
        userInfo = getStaffpersonInfo(configImpl);
      } else {
        token = login(configImpl, UserGroup.SOCIAL_WORKER);
        userInfo = getStaffpersonInfo(configImpl);
      }
    } catch (Exception e) {
      LOGGER.info("Unable to extract token");
    }
  }

  private static CognitoLoginAuthParams getLoginParams(String url) {
    CognitoLoginAuthParams loginParams = new CognitoLoginAuthParams();
    loginParams.setUser(System.getenv("SMOKE_TEST_USER"));
    loginParams.setPassword(System.getenv("SMOKE_TEST_PASSWORD"));
    loginParams.setCode(System.getenv("SMOKE_VERIFICATION_CODE"));
    loginParams.setUrl(url);
    return loginParams;
  }

  /**
   * @param userGroup - userGroup
   */
  public void loginUserGroup(UserGroup userGroup) {
    // login as a specified user required for the specific test
    ConfigImpl configImpl = new ConfigImpl();
    token = login(configImpl, userGroup);
    userInfo = getStaffpersonInfo(configImpl);
  }

  private void printEnv() {
    Map<String, String> env = System.getenv();
    LOGGER.info("Functional Test Environment");
    for (String envName : env.keySet()) {
      LOGGER.info("{}={}", envName, env.get(envName));
    }
    LOGGER.info(" ");
  }

  private String login(ConfigImpl configImpl, UserGroup userGroup) {
    return new AuthenticationUtils(configImpl).getToken(userGroup);
  }

  private UserInfo getStaffpersonInfo(ConfigImpl configImpl) {
    return new AuthenticationUtils(configImpl).getStaffPersonDetails(token);
  }

  protected String getResourceUrlFor(String resource) {
    return url + resource;
  }

}
