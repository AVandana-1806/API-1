package gov.ca.cwds.api.referrals.validation;

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
import gov.ca.cwds.fixture.CrossReportResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

/**
 * @author CWDS API Team
 *
 */
public class CrossReportTest extends FunctionalTest {

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
  public void shouldReturn201WhenNoCrossReportOnReferralWithGeneralNeglectAllegation() {
    Set<CrossReport> emptyCrossReports = new HashSet<>();
    ScreeningToReferral referral = buildScreeningToReferral(emptyCrossReports);

    httpRequestHandler.postRequest(referral, referralPath, token).then().statusCode(201).and()
        .body("legacy_id", notNullValue());
  }

  /**
   * 
   */
  @Test
  @Ignore("returns 500 status due to null date/time - error in CrossReportMapper")
  public void shouldReturn201WhenInformDateTimeNull() {
    Set<CrossReport> crossReports = new HashSet<>();
    CrossReport crossReport =
        new CrossReportResourceBuilder().setInformDate(null).createCrossReport();
    crossReports.add(crossReport);
    ScreeningToReferral referral = buildScreeningToReferral(crossReports);

    httpRequestHandler.postRequest(referral, referralPath, token).then().statusCode(201).and()
        .body("legacy_id", notNullValue());
  }

  private ScreeningToReferral buildScreeningToReferral(Set<CrossReport> crossReports) {
    Participant victim =
        new ParticipantResourceBuilder().setLegacyDescriptor(null).createVictimParticipant();
    Participant Perp = new ParticipantResourceBuilder().setGender("M").setLegacyDescriptor(null)
        .createPerpParticipant();
    Participant reporter = new ParticipantResourceBuilder().setGender("M").setLegacyDescriptor(null)
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim, Perp, reporter));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setassigneeStaffId(userInfo.getStaffId()).setIncidentCounty(userInfo.getIncidentCounty())
        .setParticipants(participants).setCrossReports(crossReports).createScreeningToReferral();
    return referral;
  }
}
