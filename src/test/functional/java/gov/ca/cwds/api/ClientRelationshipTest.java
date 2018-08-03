package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.api.builder.ResourceEndPoint;

public class ClientRelationshipTest extends FunctionalTest {

  String clientRelationshipPath;
  private HttpRequestHandler httpRequestHandler;
  
  @Before
  public void setup() {
    clientRelationshipPath = getResourceUrlFor(ResourceEndPoint.CLIENTS_RELATIONSHIPS.getResourcePath());
    httpRequestHandler = new HttpRequestHandler();
  }
  
  @Test
  public void returnErrorWhenNoHeaderIsProvided() {
    given().queryParam("token", token).when().post(clientRelationshipPath).then().statusCode(500);
  }

  @Test
  public void shouldReturnKnownRelationshipsOfPerson() {
    String clientId = "0LIZAWH00h";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
//    Response response = httpRequestHandler.getRequest(clientRelationshipPath, queryParams);
//    System.out.println(response.body().asString());
    
    httpRequestHandler.getRequest(clientRelationshipPath, queryParams)
      .then()
      .assertThat()
      .body("relationship_to.related_person_first_name", Matchers.hasItems("Non-victim1","Perp1","Victim1","Victim2"))
      .and()
      .statusCode(200);
    
  }
  
  @Test
  public void shouldReturnEmptyWhenPersonHasNoKnownRelationships() {
    String clientId = "1234567ABC";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    
    httpRequestHandler.getRequest(clientRelationshipPath, queryParams)
      .then()
      .assertThat().body("", Matchers.hasSize(1))
      .and()
      .statusCode(200);
  }
  
  @Test
  public void shouldReturnErrorWhenUserNotAughoriziedToAccessClient() {
    String clientId = "1234567ABC";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    
    httpRequestHandler.getRequest(clientRelationshipPath, queryParams)
      .then()
      .statusCode(403);    
  }
  
  @Test
  public void shouldReturnEmptyWhenClientDoesNotExists() {
    String clientId = "1234567ABC";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    
    httpRequestHandler.getRequest(clientRelationshipPath, queryParams)
      .then()
      .statusCode(200);    
  }
    
}
