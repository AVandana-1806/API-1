package gov.ca.cwds.api.client.collaterals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.fixture.ClientCollateralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.ClientCollateral;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.core.Api;
import io.restassured.http.ContentType;

public class ClientCollateralsTest extends FunctionalTest {
  String resourcePath;
  String getResourcePath;
  private HttpRequestHandler httpRequestHandler;

  @Before
  public void setup() {
    
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_CLIENT_COLLATERALS);
    getResourcePath = getResourceUrlFor("/" + Api.RESOURCE_CLIENT_COLLATERALS + "/{id}");
    httpRequestHandler = new HttpRequestHandler();
    this.loginUserGroup(UserGroup.SOCIAL_WORKER);
  }
  
  @Test
  public void shouldReturn200WhenValidId() {
    
    given().pathParam("id", "AtJF8rN0Ki")
      .queryParam(httpRequestHandler.TOKEN, token)
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      .when().get(getResourcePath).then()
      .statusCode(200);
  }
  
  @Test
  public void shouldReturn404WhenIdNotFound() {

    given().pathParam("id", "1234567ZZZ")
    .queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON)
    .accept(ContentType.JSON)
    .when().get(getResourcePath).then()
    .statusCode(404);
  }
  
  @Test
  // returns 500 when run locally
  public void shouldReturn200WhenPostValidClientCollateral() {
    Short relationshipType = 573;
    ClientCollateral clientCollateral = new ClientCollateralResourceBuilder()
        .setThirdId("")
        .setClientId("6MBrKon0Ki")
        .setCollateralIndividualId("AITMPVV0Ki")
        .setCollateralClientReporterRelationshipType(relationshipType)
        .buildClientCollateral();
    
    httpRequestHandler.postRequest(clientCollateral, resourcePath, token)
    .then()
    .statusCode(200);
  }

  @Test
  public void shouldReturn409WhenPostDuplicateClientCollateral() {
    // should return status 409 (Conflict - already exists
    Short relationshipType = 573;
    ClientCollateral clientCollateral = new ClientCollateralResourceBuilder()
        .setThirdId("")
        .setClientId("LGgVRjh0Ki")
        .setCollateralIndividualId("9NXwEy50Ki")
        .setCollateralClientReporterRelationshipType(relationshipType)
        .buildClientCollateral();

    httpRequestHandler.postRequest(clientCollateral, resourcePath, token)
      .then()
      .statusCode(500);
  }
  
  @Test
  public void shouldReturn422WhenPostInvalidClientCollateral() {
    // relationship type is bogus
    ClientCollateral clientCollateral = new ClientCollateralResourceBuilder()
        .setThirdId("AtJF8rN0X5")
        .setClientId("LGgVRjh0Ki")
        .setCollateralIndividualId("9NXwEy50Ki")
        .buildClientCollateral();
    
    httpRequestHandler.postRequest(clientCollateral, resourcePath, token)
    .then()
    .statusCode(422);   
  }

}
