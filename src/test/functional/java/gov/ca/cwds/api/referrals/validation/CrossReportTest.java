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
import gov.ca.cwds.fixture.CrossReportResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

public class CrossReportTest extends FunctionalTest {

  String referralPath;
  private HttpRequestHandler functionalTestBuilder;


  @Before
  public void setup() {
    referralPath = getResourceUrlFor(ResourceEndPoint.REFERRALS.getResourcePath());
    functionalTestBuilder = new HttpRequestHandler();
  }
  
  @Test
  public void shouldReturn201WhenNoCrossReportOnReferralWithGeneralNeglectAllegation() {
    Set<CrossReport> emptyCrossReports = new HashSet();
    ScreeningToReferral referral = buildScreeningToReferral(emptyCrossReports);

    functionalTestBuilder
      .postRequest(referral, referralPath, token).then()
      .statusCode(201)
      .and()
      .body("legacy_id", notNullValue());    
  }
  
  @Test
  public void shouldReturn201WhenInformDateTimeNull() {
    Set<CrossReport> crossReports = new HashSet();
    CrossReport crossReport = new CrossReportResourceBuilder().setInformDate(null).createCrossReport();
    crossReports.add(crossReport);
    ScreeningToReferral referral = buildScreeningToReferral(crossReports);

    functionalTestBuilder
      .postRequest(referral, referralPath, token).then()
      .statusCode(201)
      .and()
      .body("legacy_id", notNullValue());    
    
  }
  
  protected ScreeningToReferral buildScreeningToReferral(Set<CrossReport> crossReports) {
    Participant victim = new ParticipantResourceBuilder()
        .setLegacyDescriptor(null)
        .createVictimParticipant();
    Participant Perp = new ParticipantResourceBuilder()
        .setGender("M")
        .setLegacyDescriptor(null)
        .createPerpParticipant();
    Participant reporter = new ParticipantResourceBuilder()
        .setGender("M")
        .setLegacyDescriptor(null)
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim, Perp, reporter));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setassigneeStaffId(userInfo.getStaffId())
        .setIncidentCounty(userInfo.getIncidentCounty())
        .setParticipants(participants)
        .setCrossReports(crossReports)
        .createScreeningToReferral();
    return referral;
  }
}
