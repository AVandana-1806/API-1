package gov.ca.cwds.api.client.access;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertFalse;
import java.util.HashMap;
import java.util.Map;
import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.core.Api;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ClientAccessForStaffInStateWithSensitiveAccess extends FunctionalTest {
  String resourcePath;
  private HttpRequestHandler httpRequestHandler;

  /**
   * 
   */
  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_AUTHORIZE + "/client" + "/{id}");
    httpRequestHandler = new HttpRequestHandler();
    this.loginUserGroup(UserGroup.STATE_SENSITIVE);
  }

  @Test 
  public void testInvalidClientId() {
    given().pathParam("id", "999999XYZ").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(200);
  }  
  
  @Test
  public void shouldReturnClientWithNoAccessRestrictions() {
    given().pathParam("id", "CFOmFrm057").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(200);
  }
  
  @Test
  @Ignore
  public void shouldReturnClientInSameCountyWithSensitive() {
    given().pathParam("id", "1S3k0iH00T").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(200);
    
  }
  
  @Test
  @Ignore
  public void shouldNotReturnClientInSameCountyWithSealed() {
    given().pathParam("id", "4kgIiDy00T").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(403);
     
  }
 
  @Test
  @Ignore
  public void shouldNotReturnClientInDifferentCountyWithSensitive() {
    given().pathParam("id", "9PIxHucCON").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(403);
    
  }
  
  @Test
  public void shouldNotReturnClientInDifferentCountyWithSealed() {
    given().pathParam("id", "AIwcGUp0Nu").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(403);
    
  }
  
  @Test
  @Ignore
  public void shouldNotReturnClientInNoCountyWithSensitive() {
    assertFalse(Boolean.TRUE);
    
  }
  
  @Test
  @Ignore
  public void shouldNotReturnClientInNoCountyWithSealed() {
    assertFalse(Boolean.TRUE);
    
  }

}
