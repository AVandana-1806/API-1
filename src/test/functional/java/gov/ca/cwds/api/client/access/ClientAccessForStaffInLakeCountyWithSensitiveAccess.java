package gov.ca.cwds.api.client.access;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.core.Api;
import io.restassured.http.ContentType;

/**
 * 
 * Functional test for client access (/authorize) when the staff person (user)
 * has sensitive access and is in Lake county.
 * 
 * Reference Section 4 of the Security_V2.7 document (Table 4 -  Client Search Access).
 * 
 * @author CWDS API Team
 *
 */
public class ClientAccessForStaffInLakeCountyWithSensitiveAccess extends FunctionalTest {
  String resourcePath;
  private HttpRequestHandler httpRequestHandler;
  

  /**
   * 
   */
  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_AUTHORIZE + "/client" + "/{id}");
    httpRequestHandler = new HttpRequestHandler();
    this.loginUserGroup(UserGroup.COUNTY_SENSITIVE);

  }

  @Test
  public void shouldReturnClientWithNoAccessRestrictions() {
    given().pathParam("id", "CFOmFrm057").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(200);
    
  }
  
  @Test
  public void shouldReturnClientInSameCountyWithSensitive() {    this.loginUserGroup(UserGroup.COUNTY_SENSITIVE);
    given().pathParam("id", "B5mi8Qr00T").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(200);
    
  }
  
  @Test
  public void shouldNotReturnClientInSameCountyWithSealed() {
   given().pathParam("id", "4kgIiDy00T").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(403);
    
  }
 
  @Test
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
  public void shouldReturnClientInNoCountyWithSensitive() {
    assertFalse(Boolean.TRUE);
  
  }
  
  @Test
  public void shouldNotReturnClientInNoCountyWithSealed() {
    assertFalse(Boolean.TRUE);
   
  }

}
