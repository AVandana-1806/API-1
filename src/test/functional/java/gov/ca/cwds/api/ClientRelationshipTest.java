package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyOrNullString;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import io.restassured.response.Response;

public class ClientRelationshipTest extends FunctionalTest {

  String clientRelationshipPath;
  private HttpRequestHandler httpRequestHandler;
  
  @Before
  public void setup() {
    clientRelationshipPath = getResourceUrlFor("/");

//    clientRelationshipPath = getResourceUrlFor(ResourceEndPoint.CLIENTS_RELATIONSHIPS.getResourcePath());
    httpRequestHandler = new HttpRequestHandler();
  }
  
  @Test
  public void returnErrorWhenNoHeaderIsProvided() {
    given().queryParam("token", token).when().post(clientRelationshipPath).then().statusCode(500);
  }

  @Test
  public void shouldReturnKnownRelationshipsOfPerson() {
    String clientId = "0LIZAWH00h";
    clientRelationshipPath = clientRelationshipPath + "clients/" + clientId + "/relationships";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put(httpRequestHandler.TOKEN, token);
    
    httpRequestHandler.getRequest(clientRelationshipPath, queryParams)
      .then()
      .assertThat()
      .body("relationship_to.legacy_descriptor.legacy_id", Matchers.hasItems("GkKl1Q900h", "DDA9oZd00h", "GjRyRJh00h", "P2Yok4X00h"))
      .and()
      .statusCode(200);
    
  }
  
  @Test
  public void shouldReturnEmptyWhenPersonHasNoRelationships() {
    String clientId = "AbA4BJy0Aq";
    clientRelationshipPath = clientRelationshipPath + "clients/" + clientId + "/relationships";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put(httpRequestHandler.TOKEN, token);
//    Response response = httpRequestHandler.getRequest(clientRelationshipPath, queryParams);
//    System.out.println(response.body().asString());
  
    httpRequestHandler.getRequest(clientRelationshipPath, queryParams)
      .then()
      .assertThat()
      .body("relationship_to", empty())
      .and()
      .statusCode(200);
  }
  
  @Test
  // should sealed and sensitive restrictions by applied by GET /clients{id}/relationships??
  public void shouldReturnErrorWhenUserNotAuthoriziedToAccessClient() {
    String clientId = "Rv6aDQ1007";
    clientRelationshipPath = clientRelationshipPath + "clients/" + clientId + "/relationships";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put(httpRequestHandler.TOKEN, token);
    Response response = httpRequestHandler.getRequest(clientRelationshipPath, queryParams);
    System.out.println(response.body().asString());
    
    httpRequestHandler.getRequest(clientRelationshipPath, queryParams)
      .then()
      .statusCode(200);
  }
  
  @Test
  public void shouldReturnEmptyWhenClientDoesNotExists() {
    String clientId = "AhrZzTH04y";
    clientRelationshipPath = clientRelationshipPath + "clients/" + clientId + "/relationships";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put(httpRequestHandler.TOKEN, token);
    
    httpRequestHandler.getRequest(clientRelationshipPath, queryParams)
      .then()
      .assertThat()
      .body("relationship_to", emptyOrNullString())
      .and()
      .statusCode(200);    
  }
    
}
