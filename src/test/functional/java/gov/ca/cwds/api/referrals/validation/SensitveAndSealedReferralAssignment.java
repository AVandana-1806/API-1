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
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

/**
 * @author CWDS API Team
 *
 */
/**
 * <blockquote>
 * 
 * <pre>
 * BUSINESS RULE: R - 00797 Sensitive Case Assignment
 * 
 *  A Sealed or Sensitive CASE or REFERRAL may be assigned to any Staff Person, whether they 
 *  have sealed or sensitive authority or not.
 *  
 * </blockquote>
 * </pre>
 */
public class SensitveAndSealedReferralAssignment extends FunctionalTest {
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
    Set<CrossReport> emptyCrossReports = new HashSet();
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
        .setCrossReports(emptyCrossReports)
        .createScreeningToReferral();
    return referral;
  }
}
