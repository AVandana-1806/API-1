package gov.ca.cwds.api.authorize;

import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ClientAccessForStaffInLakeWithSocialWorkerAccess extends FunctionalTest {

  String clientRelationshipPath;
  private HttpRequestHandler httpRequestHandler;
  
  @Before
  public void setup() {
    clientRelationshipPath = getResourceUrlFor("/");

    httpRequestHandler = new HttpRequestHandler();
    
  }
  
  @Test
  public void returnErrorWhenNoHeaderIsProvided() {
    given().queryParam("token", token).when().post(clientRelationshipPath).then().statusCode(500);
  }

  @Test
  public void shouldReturnKnownRelationshipsOfPerson() {
    String clientId = "C3IquYc0Co";
    clientRelationshipPath = clientRelationshipPath + "clients/" + clientId + "/relationships";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put(httpRequestHandler.TOKEN, token);
    
    httpRequestHandler.getRequest(clientRelationshipPath, queryParams)
      .then()
      .assertThat()
      .body("relationship_to.legacy_descriptor.legacy_id", Matchers.hasItems("4GrHFQK0Fj", "TBCF40g0D8", "3ju7sbi0Co"))
      .and()
      .statusCode(200);
    
  }
  
  @Test
  public void shouldReturnEmptyWhenPersonHasNoRelationships() {
    String clientId = "AbA4BJy0Aq";
    clientRelationshipPath = clientRelationshipPath + "clients/" + clientId + "/relationships";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put(httpRequestHandler.TOKEN, token);
  
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
    
  @Test
  public void shouldReturnKnownRelationshipsOfClients() {
    clientRelationshipPath = clientRelationshipPath + "clients/relationships";
    String clientId = "C3IquYc0Co";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    Response response = httpRequestHandler.getRequest(clientRelationshipPath, queryParams);
    JsonPath jsonPathEvaluator = response.jsonPath();
    ArrayList relatedIds = jsonPathEvaluator.get("relationship_to.legacy_descriptor.legacy_id");
    List<ArrayList> ids = (List<ArrayList>) relatedIds.get(0);
    assertThat(ids.contains("4GrHFQK0Fj"), is(true));
    assertThat(ids.contains("TBCF40g0D8"), is(true));
    assertThat(ids.contains("3ju7sbi0Co"), is(true));
  }
  
}
