package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.api.domain.enums.SameHomeStatus;
import gov.ca.cwds.rest.core.Api;
import java.util.Date;
/**
 * @author CWDS API Team
 *
 */
public class ScreeningRelationshipTest extends FunctionalTest {

  public static final int RELATIONSHIP_TYPE = 190;
  String resourcePath;
  ScreeningRelationship relationship;

  /**
   *
   */
  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.SCREENING_RELATIONSHIPS);

    Date now = new Date();
    relationship = new ScreeningRelationship();
    relationship.setId("id");
    relationship.setClientId("Client1");
    relationship.setRelativeId("Client2");
    relationship.setRelationshipType(RELATIONSHIP_TYPE);
    relationship.setAbsentParentIndicator(true);
    relationship.setSameHomeStatus(SameHomeStatus.U.getCode());
    relationship.setStartDate(now);
    relationship.setEndDate(now);
    relationship.setLegacyId("1234567890");
  }

  /**
   *
   */
  @Test
  public void whenCreatingRelationshipWithValidReferralThenCreatedCodeIsReturned() {
    given().queryParam("token", token).body(relationship).header("Content-Type", "application/json")
        .header("Accept", "application/json").when().post(resourcePath).then().statusCode(201);
  }

  /**
   *
   */
  @Test
  public void whenUpdatingReferralWithInvalidJsonThenErrorIsReturned() {
    given().queryParam("token", token).body("{bad object}")
        .header("Content-Type", "application/json").header("Accept", "application/json").when()
        .post(resourcePath).then().statusCode(400);
  }
}
