package gov.ca.cwds.api.referrals.validation;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.api.builder.ResourceEndPoint;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

/**
 * @author CWDS API Team
 *
 */
public class ParticipantDobTest extends FunctionalTest {

  String referralPath;
  private HttpRequestHandler functionalTestingBuilder;

  /**
   * 
   */
  @Before
  public void setup() {
    referralPath = getResourceUrlFor(ResourceEndPoint.REFERRALS.getResourcePath());
    functionalTestingBuilder = new HttpRequestHandler();
  }

  /**
   * Test to check the validation on future date of Birth
   */
  @Test
  public void testParticipantDobCanNotInFuture() {
    String dateOfBith = "2018-06-18";
    String approximateAge = null;
    String approximateAgeUnits = null;
    ScreeningToReferral referral = new VictimBirthTest().buildScreeningToReferral(dateOfBith,
        approximateAge, approximateAgeUnits);

    functionalTestingBuilder.postRequest(referral, referralPath, token).then()
        .body("issue_details.user_message[0]", equalTo("date of Birth can not be in future"))
        .body("issue_details.property[0]", equalTo("John")).and().statusCode(422);
  }

}
