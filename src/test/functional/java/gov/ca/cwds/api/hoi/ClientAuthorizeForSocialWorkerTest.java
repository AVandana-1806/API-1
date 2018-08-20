package gov.ca.cwds.api.hoi;

import static io.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.rest.core.Api;
import io.restassured.http.ContentType;

/**
 * Functional Testing for client authorize end point with Social Worker Privilege Only (GVR_ENTC =
 * 1084 or Lake county).
 * 
 * User is determined by init() method in parent class (FunctionalTest.java)
 * 
 * @author CWDS API Team
 *
 */
public class ClientAuthorizeForSocialWorkerTest extends FunctionalTest {

  String resourcePath;
  private HttpRequestHandler httpRequestHandler;

  /**
   * 
   */
  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_AUTHORIZE + "/client" + "/{id}");
    httpRequestHandler = new HttpRequestHandler();
  }

  /**
   * Test to Authorize the client with no privilege
   */
  @Test
  public void testSuccessToAuthorizeSocialWorkerOnly() {
    given().pathParam("id", "CFOmFrm057").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(200);
  }

  /**
   * Test to Authorize the client with same county sensitive privilege
   */
  @Test
  public void testToAuthorizeSameCountySensitive() {
    given().pathParam("id", "B5mi8Qr00T").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void testToAuthorizeDifferentCountySensitive() {
    given().pathParam("id", "9PIxHucCON").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void testToAuthorizeSameCountySealed() {
    given().pathParam("id", "4kgIiDy00T").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void testToAuthorizeDifferentCountySealed() {
    given().pathParam("id", "AIwcGUp0Nu").queryParam(httpRequestHandler.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(403);
  }

}
