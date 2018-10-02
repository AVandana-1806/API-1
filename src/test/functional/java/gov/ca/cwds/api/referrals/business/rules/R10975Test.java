package gov.ca.cwds.api.referrals.business.rules;

import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.api.builder.ResourceEndPoint;
import gov.ca.cwds.fixture.CsecBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.core.FerbConstants.ReportType;

/**
 * @author CWDS API Team
 *
 */
public class R10975Test extends FunctionalTest {
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
   * @throws JsonProcessingException
   * 
   */
  @Test
  public void testShouldReturn422WhenStartDateGreaterThanEndDate() throws JsonProcessingException {
    LocalDate startDate = LocalDate.parse("2018-09-29");
    LocalDate endDate = LocalDate.parse("2018-09-28");
    ScreeningToReferral referral = buildScreeningToReferral(startDate, endDate);

    httpRequestHandler.postRequest(referral, referralPath, token).then()
        .body("issue_details.user_message[0]",
            equalTo("CSEC endDate should be greater than or equal to startDate"))
        .and().statusCode(422);
  }

  protected ScreeningToReferral buildScreeningToReferral(LocalDate startDate, LocalDate endDate) {
    List<Csec> csecs = buildCsecs(startDate, endDate);
    Participant victim = new ParticipantResourceBuilder().setLegacyDescriptor(null).setCsecs(csecs)
        .createParticipant();
    Participant Perp = new ParticipantResourceBuilder().setGender("M").setLegacyDescriptor(null)
        .createPerpParticipant();
    Participant reporter = new ParticipantResourceBuilder().setGender("M").setLegacyDescriptor(null)
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim, Perp, reporter));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setassigneeStaffId(userInfo.getStaffId()).setIncidentCounty(userInfo.getIncidentCounty())
        .setParticipants(participants).setReportType(ReportType.CSEC).createScreeningToReferral();
    return referral;
  }

  private List<Csec> buildCsecs(LocalDate startDate, LocalDate endDate) {
    Csec csec = new CsecBuilder().setStartDate(startDate).setEndDate(endDate).build();
    List<Csec> csecs = new LinkedList<>();
    csecs.add(csec);
    return csecs;
  }

}
