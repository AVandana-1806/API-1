package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import gov.ca.cwds.api.builder.FunctionalTestingBuilder;
import gov.ca.cwds.api.builder.ResourceEndPoint;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

/**
 * @author CWDS API Team
 *
 */
public class ScreeningToReferralTest extends FunctionalTest {
  String referralPath;
  private FunctionalTestingBuilder functionalTestingBuilder;

  /**
   * 
   */
  @Before
  public void setup() {
    referralPath = getResourceUrlFor(ResourceEndPoint.REFERRALS.getResourcePath());
    functionalTestingBuilder = new FunctionalTestingBuilder();
  }

  /**
   * 
   */
  @Test
  public void returnErrorWhenNoHeaderIsProvided() {
    given().queryParam("token", token).when().post(referralPath).then().statusCode(500);
  }

  /**
   * 
   */
  @Test
  @Ignore
  public void return500ErrorWhenIncorrectStaffPerson() {
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setName("IncorrectStaffPerson").setAssigneeStaffId("bad").createScreeningToReferral();

    functionalTestingBuilder.postRequest(referral, referralPath, token).then()
        .body("issue_details[0].user_message", equalTo(
            "There was an error processing your request. It has been logged with unique incident id"))
        .and()
        .body("issue_details[0].stack_trace", containsString(
            "gov.ca.cwds.rest.business.rules.CountyOfAssignedStaffWorker.isValid(CountyOfAssignedStaffWorker.java"))
        .and().statusCode(500);
  }

  /**
   * 
   */
  @Test
  public void return500ErrorWhenNoVictimIsPresent() {
    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setName("return500ErrorWhenNoVictimIsPresent")
            .setassigneeStaffId("NONE").setParticipants(null).createScreeningToReferral();

    functionalTestingBuilder.postRequest(referral, referralPath, token).then()
        .body("issue_details.user_message",
            hasItem("must contain at least one victim, only one reporter, and compatible roles"))
        .and().statusCode(422);
  }

  /**
   * 
   */
  @Test
  @Ignore
  public void return201SuccessForValidReferrals() {
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setName("return201SuccessForValidReferrals").setAssigneeStaffId(userInfo.getStaffId())
        .setIncidentCounty(userInfo.getIncidentCounty()).createScreeningToReferral();

    functionalTestingBuilder.postRequest(referral, referralPath, token).then().statusCode(201).and()
        .body("legacy_id", notNullValue());
  }
}
