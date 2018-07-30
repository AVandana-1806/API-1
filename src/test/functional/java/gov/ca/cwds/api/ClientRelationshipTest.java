package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;
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

}
