package gov.ca.cwds.api.referrals.validation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.api.builder.ResourceEndPoint;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

/**
 * @author CWDS API Team
 *
 */
public class VictimBirthTest extends FunctionalTest {
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
  public void testVictimDObOrAgeIsRequired() {
    String dateOfBith = null;
    String approximateAge = null;
    String approximateAgeUnits = null;
    ScreeningToReferral referral =
        buildScreeningToReferral(dateOfBith, approximateAge, approximateAgeUnits);

    httpRequestHandler.postRequest(referral, referralPath, token).then()
        .body("issue_details.user_message[0]",
            equalTo("Victim's should have either of the value DOB or approximateAge"))
        .and().statusCode(422);
  }

  /**
   * 
   */
  @Test
  public void testToValidateAgeAndAgeUnitBothRequired() {
    String dateOfBith = null;
    String approximateAge = "12";
    String approximateAgeUnits = null;
    ScreeningToReferral referral =
        buildScreeningToReferral(dateOfBith, approximateAge, approximateAgeUnits);

    httpRequestHandler.postRequest(referral, referralPath, token).then()
        .body("issue_details.user_message[0]",
            equalTo("Victim's approximateAgeUnits must be set if approximateAge is set"))
        .and().statusCode(422);
  }

  /**
   * R - 05609 Victim must be < 19
   */
  @Test
  public void shouldReturn422WhenVictimTooOld() {
    String approximateAge = null;
    String approximateAgeUnits = null;
    String dateOfBith = "1994-06-18";
    ScreeningToReferral referral =
        buildScreeningToReferral(dateOfBith, approximateAge, approximateAgeUnits);

    httpRequestHandler.postRequest(referral, referralPath, token).then()
        .body("issue_details.user_message[0]", equalTo("Victim's age must be less than 18 years"))
        .and().statusCode(422);

  }

  /**
   * 
   */
  @Test
  @Ignore("TEMP Causes table lock")
  public void testSucessValidBirthDateGiven() {
    String dateOfBith = "2010-06-18";
    String approximateAge = null;
    String approximateAgeUnits = null;
    ScreeningToReferral referral =
        buildScreeningToReferral(dateOfBith, approximateAge, approximateAgeUnits);

    httpRequestHandler.postRequest(referral, referralPath, token).then().statusCode(201).and()
        .body("legacy_id", notNullValue());
  }

  /**
   * 
   */
  @Test
  @Ignore("TEMP Causes table lock")
  public void testSucessValidAgeAndAgeUnitGiven() {
    String dateOfBith = null;
    String approximateAge = "12";
    String approximateAgeUnits = "Y";
    ScreeningToReferral referral =
        buildScreeningToReferral(dateOfBith, approximateAge, approximateAgeUnits);

    httpRequestHandler.postRequest(referral, referralPath, token).then().statusCode(201).and()
        .body("legacy_id", notNullValue());
  }

  protected ScreeningToReferral buildScreeningToReferral(String dateOfBith, String approximateAge,
      String approximateAgeUnits) {
    Participant victim = new ParticipantResourceBuilder().setDateOfBirth(dateOfBith)
        .setApproximateAge(approximateAge).setApproximateAgeUnits(approximateAgeUnits)
        .setLegacyDescriptor(null).createVictimParticipant();
    Participant Perp = new ParticipantResourceBuilder().setGender("M").setLegacyDescriptor(null)
        .createPerpParticipant();
    Participant reporter = new ParticipantResourceBuilder().setGender("M").setLegacyDescriptor(null)
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim, Perp, reporter));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setassigneeStaffId(userInfo.getStaffId()).setIncidentCounty(userInfo.getIncidentCounty())
        .setParticipants(participants).createScreeningToReferral();
    return referral;
  }

}
