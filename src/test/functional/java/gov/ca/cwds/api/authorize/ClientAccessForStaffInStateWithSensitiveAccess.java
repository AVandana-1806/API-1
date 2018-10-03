package gov.ca.cwds.api.authorize;

import static io.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.core.Api;
import io.restassured.http.ContentType;

/**
 * @author CWDS API Team
 *
 */
public class ClientAccessForStaffInStateWithSensitiveAccess extends FunctionalTest {
  String resourcePath;
  private HttpRequestHandler httpRequestHandler;

  /**
   * 
   */
  @Before
  public void setup() {
    // logged in staff with Sensitive access and
    // USERID->STAFF_PERSON->CWS_OFFICE.Government_Entity_type=1126 (California)
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_AUTHORIZE + "/client" + "/{id}");
    httpRequestHandler = new HttpRequestHandler();
    this.loginUserGroup(UserGroup.STATE_SENSITIVE);
  }

  /**
   * 
   */
  @Test
  public void testInvalidClientId() {
    given().pathParam("id", "999999XYZ").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(200);
  }

  /**
   * 
   */
  @Test
  public void shouldReturnClientWithNoAccessRestrictions() {
    given().pathParam("id", "CFOmFrm057").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(200);
  }

  /**
   * 
   */
  @Test
  public void shouldReturnClientInSameCountyWithSensitive() {
    // requires sensitive client owned by 1126 (California)
    given().pathParam("id", "1S3k0iH00T").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(200);
  }

  /**
   * 
   */
  @Test
  public void shouldNotReturnClientInSameCountyWithSealed() {
    // requires sealed client owned by 1126 (California)
    given().pathParam("id", "4kgIiDy00T").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void shouldNotReturnClientInDifferentCountyWithSensitive() {
    // should return status 403
    given().pathParam("id", "9PIxHucCON").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(200);

  }

  /**
   * 
   */
  @Test
  public void shouldNotReturnClientInDifferentCountyWithSealed() {
    given().pathParam("id", "AIwcGUp0Nu").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void shouldNotReturnClientInNoCountyWithSensitive() {
    // client with limited access code = 'S' and government entity of 1126 (California)
    // should not be able to access client - return status 403
    given().pathParam("id", "AYk7k55aaf").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(200);
  }

  /**
   * 
   */
  @Test
  public void shouldNotReturnClientInNoCountyWithSealed() {
    // client with limited access code = 'R' and government entity of 1126 (California)
    given().pathParam("id", "BK3EnRK0DE").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(403);
  }

}
