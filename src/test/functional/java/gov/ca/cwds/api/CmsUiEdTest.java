package gov.ca.cwds.api;

import static org.hamcrest.Matchers.equalTo;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.core.Api;

public class CmsUiEdTest extends FunctionalTest {

  String resourcePath;
  private HttpRequestHandler httpRequestHandler;
  
  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_CMS_UI_IDENTIFIER);
    httpRequestHandler = new HttpRequestHandler();
    this.loginUserGroup(UserGroup.SOCIAL_WORKER);    
  }
  
  @Test
  public void shouldReturn200WhenValidId() {
 
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("key", "FeXltcEaab");
    queryParams.put(httpRequestHandler.TOKEN, token);

    httpRequestHandler.getRequest(resourcePath, queryParams)
      .then()
      .statusCode(200)
      .and()
      .body("ui_identifier", equalTo("0889-1478-8133-4140653"));
     
  }
  
  @Test
  public void shouldReturn422WhenInvalidKey() {
    
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("key", "FeXl");
    queryParams.put(httpRequestHandler.TOKEN, token);
    
    httpRequestHandler.getRequest(resourcePath, queryParams)
      .then()
      .statusCode(422)
      .and()
      .body("issue_details.user_message", Matchers.hasItems("invalid legacy key"));
  }
}
