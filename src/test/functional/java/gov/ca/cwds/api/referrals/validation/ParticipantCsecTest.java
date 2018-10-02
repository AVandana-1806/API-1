package gov.ca.cwds.api.referrals.validation;

import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.api.builder.ResourceEndPoint;
import gov.ca.cwds.fixture.CsecBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

public class ParticipantCsecTest extends FunctionalTest {
  String referralPath;
  private HttpRequestHandler functionalTestBuilder;

  /**
   * 
   */
  @Before
  public void setup() {
    referralPath = getResourceUrlFor(ResourceEndPoint.REFERRALS.getResourcePath());
    functionalTestBuilder = new HttpRequestHandler();
  }

  @Test
  public void shouldReturn201WhenValidCsec() {
    Csec csec = new CsecBuilder().build();
    List<Csec> csecs = new ArrayList();
    csecs.add(csec);
    ScreeningToReferral referral = buildScreeningToReferral(csecs);

    functionalTestBuilder.postRequest(referral, referralPath, token).then().statusCode(201).and()
        .body("legacy_id", notNullValue());
  }

  @Test
  @Ignore("incorrectly returns 201")
  public void shouldReturn402WhenInvalidCsec() {
    Csec csec = new CsecBuilder().setCsecCodeId("").build();
    List<Csec> csecs = new ArrayList();
    csecs.add(csec);
    ScreeningToReferral referral = buildScreeningToReferral(csecs);

    functionalTestBuilder.postRequest(referral, referralPath, token).then().statusCode(201).and()
        .body("legacy_id", notNullValue());

  }

  protected ScreeningToReferral buildScreeningToReferral(List<Csec> csecs) {
    Participant victim =
        new ParticipantResourceBuilder().setLegacyDescriptor(null).createVictimParticipant();
    victim.setCsecs(csecs);
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
