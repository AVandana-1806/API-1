package gov.ca.cwds.api.client.access;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertFalse;
import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.core.Api;
import io.restassured.http.ContentType;

public class ClientAccessForStaffInLakeCountyWithSealedAccess extends FunctionalTest {
  String resourcePath;
  private HttpRequestHandler httpRequestHandler;

  /**
   * 
   */
  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_AUTHORIZE + "/client" + "/{id}");
    httpRequestHandler = new HttpRequestHandler();
    this.loginUserGroup(UserGroup.COUNTY_SEALED);
  }

  @Test
  public void shouldReturnClientWithNoAccessRestrictions() {
//    assertFalse(Boolean.TRUE);
    given().pathParam("id", "CFOmFrm057").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(200);
  }
  
  @Test
  public void shouldNotReturnClientInSameCountyWithSensitive() {
    given().pathParam("id", "B5mi8Qr00T").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(403);
//    assertFalse(Boolean.TRUE);
    
  }
  
  @Test
  public void shouldReturnClientInSameCountyWithSealed() {
    given().pathParam("id", "Ba29OOP75a").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(200);
//    assertFalse(Boolean.TRUE);
    
  }
 
  @Test
  public void shouldNotReturnClientInDifferentCountyWithSensitive() {
    given().pathParam("id", "9PIxHucCON").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(403);
//    assertFalse(Boolean.TRUE);
    
  }
  
  @Test
  public void shouldNotReturnClientInDifferentCountyWithSealed() {
    given().pathParam("id", "AIwcGUp0Nu").queryParam(httpRequestHandler.TOKEN, token)
    .contentType(ContentType.JSON).accept(ContentType.JSON).when().get(resourcePath).then()
    .statusCode(403);
//    assertFalse(Boolean.TRUE);
    
  }
  
  @Test
  @Ignore
  public void shouldNotReturnClientInNoCountyWithSensitive() {
    assertFalse(Boolean.TRUE);
    
  }
  
  @Test
  @Ignore
  public void shouldReturnClientInNoCountyWithSealed() {
    assertFalse(Boolean.TRUE);
    
  }

}
