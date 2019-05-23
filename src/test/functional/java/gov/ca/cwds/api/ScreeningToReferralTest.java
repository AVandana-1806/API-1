package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.api.builder.ResourceEndPoint;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

/**
 * @author CWDS API Team
 */
public class ScreeningToReferralTest extends FunctionalTest {

  String referralPath;
  private HttpRequestHandler httpRequestHandler;

  /**
   *
   */
  @Before
  public void setup() {
    referralPath = getResourceUrlFor(ResourceEndPoint.REFERRALS.getResourcePath());
    httpRequestHandler = new HttpRequestHandler();
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
  public void return500ErrorWhenIncorrectStaffPerson() {
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setName("IncorrectStaffPerson").setAssigneeStaffId("bad").createScreeningToReferral();

    httpRequestHandler.postRequest(referral, referralPath, token).then()
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
  public void return422ErrorWhenNoVictimIsPresent() {
    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setName("return500ErrorWhenNoVictimIsPresent")
            .setassigneeStaffId("aab").setParticipants(null).createScreeningToReferral();

    httpRequestHandler.postRequest(referral, referralPath, token).then()
        .body("issue_details.user_message",
            hasItem("must contain at least one victim, only one reporter, and compatible roles"))
        .and().statusCode(422);
  }

  /**
   *
   */
  @Test
  public void return201SuccessForValidReferrals() {
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setName("return201SuccessForValidReferrals").setAssigneeStaffId(userInfo.getStaffId())
        .setIncidentCounty(userInfo.getIncidentCounty()).createScreeningToReferral();

    httpRequestHandler.postRequest(referral, referralPath, token).then().statusCode(201).and()
        .body("legacy_id", notNullValue());
  }

  /**
   * DocTool rule R - 02535
   * HOT-1648
   */
  @Test
  @Ignore("This test works locally if configured against preint but does not work in pipeline."
      +" Tom Parker found some issues with the triggers that are causing problems in pipeline.")
  public void testFiledCrossReportWithLawEnforcementIndicatorIsFalse() {
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setName("filedWithLawEnforcementIsFalse")
        .setFiledWithLawEnforcement(true)
        .setAssigneeStaffId(userInfo.getStaffId())
        .setIncidentCounty(userInfo.getIncidentCounty()).createScreeningToReferral();

    // post a referral with Filed With Law Enforcement flag set to true
    String legacyId = httpRequestHandler.postRequest(referral, referralPath, token).then()
        .statusCode(201).extract().path("legacy_id");

    // load the referral and make sure that the flag value in CWS/CMS is false
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put(httpRequestHandler.TOKEN, token);
    httpRequestHandler.getRequest(referralPath + "/" + legacyId, queryParams).then().statusCode(200)
        .and().body("filedSuspectedChildAbuseReporttoLawEnforcementIndicator", equalTo(false));
  }
}
