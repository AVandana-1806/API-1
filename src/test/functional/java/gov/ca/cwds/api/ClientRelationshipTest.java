package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.api.builder.FunctionalTestingBuilder;
import gov.ca.cwds.api.builder.ResourceEndPoint;

public class ClientRelationshipTest extends FunctionalTest {

  String clientRelationshipPath;
  private FunctionalTestingBuilder functionalTestingBuilder;
  
  @Before
  public void setup() {
    clientRelationshipPath = getResourceUrlFor(ResourceEndPoint.CLIENTS_RELATIONSHIPS.getResourcePath());
    functionalTestingBuilder = new FunctionalTestingBuilder();
  }
  
  @Test
  public void returnErrorWhenNoHeaderIsProvided() {
    given().queryParam("token", token).when().post(clientRelationshipPath).then().statusCode(500);
  }

  @Test
  public void shouldReturnKnownRelationshipsOfPerson() {
    String clientId = "1234567ABC";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    
    functionalTestingBuilder.getRequest(clientRelationshipPath, queryParams)
      .then()
      .assertThat()
      .body("relationship_to.related_person_first_name", Matchers.hasItems("Anna","babe","Nina","Lawrence"))
      .and()
      .statusCode(200);
    
  }
  
  @Test
  public void shouldReturnEmptyWhenPersonHasNoKnownRelationships() {
    String clientId = "1234567ABC";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    
    functionalTestingBuilder.getRequest(clientRelationshipPath, queryParams)
      .then()
      .body("isEmpty()", Matchers.is(true))
      .and()
      .statusCode(200);
  }
  
  @Test
  public void shouldReturnErrorWhenUserNotAughoriziedToAccessClient() {
    String clientId = "1234567ABC";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    
    functionalTestingBuilder.getRequest(clientRelationshipPath, queryParams)
      .then()
      .statusCode(403);    
  }
    
}
