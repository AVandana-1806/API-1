package gov.ca.cwds.api.staffpersons;

import static org.hamcrest.Matchers.equalTo;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.core.Api;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

public class staffpersonsTest extends FunctionalTest {

  String resourcePath;
  private HttpRequestHandler httpRequestHandler;
  
  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_STAFF_PERSONS + "/{id}");
    httpRequestHandler = new HttpRequestHandler();
    this.loginUserGroup(UserGroup.SOCIAL_WORKER);    
  }
  
  @Test
  public void shouldReturn200WhenValid() {
    
    given()
      .pathParam("id", "0X5")
      .queryParam(httpRequestHandler.TOKEN, token)
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      .when().get(resourcePath)
      .then()
      .statusCode(200);
    
  }
  
  @Test
  public void shouldReturn404WhenInvalidStaffPersonId() {

    given()
    .pathParam("id", "123")
    .queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON)
    .accept(ContentType.JSON)
    .when().get(resourcePath)
    .then()
    .statusCode(404);

  }
}
