package gov.ca.cwds.api.referrals.validation;

import static org.hamcrest.Matchers.notNullValue;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.api.builder.ResourceEndPoint;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

/**
 * @author CWDS API Team
 *
 */
public class SensitveAndSealedReferralAssignment extends FunctionalTest {
  // R - 00797 
  String referralPath;
  private HttpRequestHandler functionalTestBuilder;

  /**
   * 
   */
  @Before
  public void setup() {
    // default user login for Staff with Social Worker priv
    referralPath = getResourceUrlFor(ResourceEndPoint.REFERRALS.getResourcePath());
    functionalTestBuilder = new HttpRequestHandler();
  }
  
  @Test
  public void shouldReturn201WhenPostingSensitiveReferral() {
    ScreeningToReferral referral = buildScreeningToReferral("S");
    
    functionalTestBuilder.postRequest(referral, referralPath, token)
      .then()
      .statusCode(201)
      .and()
      .body("legacy_id", notNullValue());
  }
  
  @Test
  public void shouldReturn201WhenPostingSealedReferral() {
    ScreeningToReferral referral = buildScreeningToReferral("R");
    functionalTestBuilder.postRequest(referral, referralPath, token)
      .then()
      .statusCode(201)
      .and()
      .body("legacy_id", notNullValue());
   
  }
  
  protected ScreeningToReferral buildScreeningToReferral(String limitedAccessCode) {
    Participant victim = new ParticipantResourceBuilder()
        .setLegacyDescriptor(null)
        .createVictimParticipant();
    Participant Perp = new ParticipantResourceBuilder()
        .setGender("M")
        .setLegacyDescriptor(null)
        .createPerpParticipant();
    Participant reporter = new ParticipantResourceBuilder().setGender("M").setLegacyDescriptor(null)
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim, Perp, reporter));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessCode(limitedAccessCode)
        .setassigneeStaffId(userInfo.getStaffId())
        .setIncidentCounty(userInfo.getIncidentCounty())
        .setParticipants(participants)
        .createScreeningToReferral();
    return referral;
  }
}
