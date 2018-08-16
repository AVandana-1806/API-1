package gov.ca.cwds.rest.authenticate;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.authenticate.config.YmlLoader;
import gov.ca.cwds.config.User;

/**
 * This class will handle the different type of login based on the authentication mode, If the
 * Authentication mode in the Yaml file is set to TEST it uses the Json type or else username and
 * password to get the token.
 * 
 * <p>
 * authenticationMode: TEST mean perry is in dev mode
 * </p>
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class CwdsLoginType {

  private static final Logger LOGGER = LoggerFactory.getLogger(CwdsLoginType.class);

  CwdsClientCommon cwdsClientCommon = null;
  YmlLoader ymlLoader = null;

  /**
   * Constructor.
   * 
   * @param ymlLoader - ymlLoader
   */
  public CwdsLoginType(YmlLoader ymlLoader) {
    this.ymlLoader = ymlLoader;
  }

  /**
   * This method differentiate with the login type between json and user/pwd and get a valid token.
   * 
   * @param userType - userType
   * @return the valid user token
   */
  public String login(UserGroup userType) {
    if ("TEST".equals(ymlLoader.readConfig().getAuthenticationMode())) {
      List<User> users = ymlLoader.readConfig().getDefaultUsers();
      Optional<User> user = users.stream()
          .filter(value -> userType.getName().equals(value.getUserType())).findFirst();
      String userJson = null;
      if (user.isPresent()) {
        userJson = user.get().getUsername();
      }
      cwdsClientCommon = new CwdsDevAuthenticationClient(ymlLoader, userJson);
    } else {
      List<User> users = ymlLoader.readConfig().getDefaultUsers();
      Optional<User> user = users.stream()
          .filter(value -> userType.getName().equals(value.getUserType())).findFirst();
      String userName = null;
      String password = null;
      if (user.isPresent()) {
        userName = user.get().getUsername();
        password = user.get().getPassword();
      }
      cwdsClientCommon = new CwdsAuthenticationClient(ymlLoader, userName, password);
    }
    return cwdsClientCommon.getToken();
  }

}
