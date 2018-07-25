package gov.ca.cwds.api.hoi;

import static io.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.FunctionalTestingBuilder;
import gov.ca.cwds.rest.core.Api;
import io.restassured.http.ContentType;

/**
 * Functional Testing for client authorize end point with Social Worker Privilege Only(GVR_ENTC =
 * 1084)
 * 
 * @author CWDS API Team
 *
 */
public class ClientAuthorizeForSocialWorkerTest extends FunctionalTest {

  String resourcePath;
  private FunctionalTestingBuilder functionalTestingBuilder;

  /**
   * 
   */
  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_AUTHORIZE + "/client" + "/{id}");
    functionalTestingBuilder = new FunctionalTestingBuilder();
  }

  /**
   * Test to Authorize the client with no privilege
   */
  @Test
  public void testSuccessToAuthorizeSocialWorkerOnly() {
    given().pathParam("id", "CFOmFrm057").queryParam(functionalTestingBuilder.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(200);
  }

  /**
   * Test to Authorize the client with same county sensitive privilege
   */
  @Test
  public void testToAuthorizeSameCountySensitive() {
    given().pathParam("id", "B5mi8Qr00T").queryParam(functionalTestingBuilder.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void testToAuthorizeDifferentCountySensitive() {
    given().pathParam("id", "9PIxHucCON").queryParam(functionalTestingBuilder.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void testToAuthorizeSameCountySealed() {
    given().pathParam("id", "4kgIiDy00T").queryParam(functionalTestingBuilder.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void testToAuthorizeDifferentCountySealed() {
    given().pathParam("id", "AIwcGUp0Nu").queryParam(functionalTestingBuilder.TOKEN, token)
        .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
        .statusCode(403);
  }

}
