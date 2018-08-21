package gov.ca.cwds.api;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.authenticate.config.ConfigImpl;
import gov.ca.cwds.config.CwdsAuthenticationClientConfig;
import gov.ca.cwds.rest.authenticate.AuthenticationUtils;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.authenticate.UserInfo;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 */
public class FunctionalTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(FunctionalTest.class);

  protected ObjectMapper objectMapper = Jackson.newObjectMapper();

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
    // default login as a Staff person with social worker access privilege
    token = login(configImpl, UserGroup.SOCIAL_WORKER);
    userInfo = getStaffpersonInfo(configImpl);
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
