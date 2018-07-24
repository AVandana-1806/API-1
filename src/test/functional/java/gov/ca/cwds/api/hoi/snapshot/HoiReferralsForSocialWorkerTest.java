package gov.ca.cwds.api.hoi.snapshot;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.FunctionalTestingBuilder;
import gov.ca.cwds.api.builder.ResourceEndPoint;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import io.restassured.response.Response;

/**
 * Functional Testing for hoi referrals end point with Social Worker Privilege Only.
 * 
 * @author CWDS API Team
 */
public class HoiReferralsForSocialWorkerTest extends FunctionalTest {

  String hoiReferrals;
  String referralsPath;
  private FunctionalTestingBuilder functionalTestingBuilder;

  /**
   * 
   */
  @Before
  public void setup() {
    referralsPath = getResourceUrlFor(ResourceEndPoint.REFERRALS.getResourcePath());
    hoiReferrals = getResourceUrlFor(ResourceEndPoint.HOI_REFERRALS.getResourcePath());
    functionalTestingBuilder = new FunctionalTestingBuilder();
  }

  /**
   * @throws IOException
   */
  @Test
  public void testSuccessToAccessNoConditionClient() throws IOException {
    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setAssigneeStaffId(userInfo.getStaffId())
            .setIncidentCounty(userInfo.getIncidentCounty()).createScreeningToReferral();
    Response response = functionalTestingBuilder.processPostRequest(referral, referralsPath, token);
    String json = response.asString();
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JodaModule());
    ScreeningToReferral screeningToReferral = mapper.readValue(json, ScreeningToReferral.class);

    Optional<Participant> victim = screeningToReferral.getParticipants().stream()
        .filter(value -> value.getRoles().contains("Victim")).findFirst();
    String referralId = screeningToReferral.getReferralId();
    String clientId = "";
    if (victim.isPresent()) {
      clientId = victim.get().getLegacyId();
    }
    functionalTestingBuilder.processGetRequest(hoiReferrals, "clientIds", clientId, token).then()
        .body("id[0]", equalTo(referralId));
  }

  /**
   * 
   */
  @Test
  public void failedToAccessSameCountySensitiveClient() {
    functionalTestingBuilder.processGetRequest(hoiReferrals, "clientIds", "D4s6hW6057", token)
        .then().statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void failedToAccessSameCountySealedClient() {
    functionalTestingBuilder.processGetRequest(hoiReferrals, "clientIds", "B0gYFaU057", token)
        .then().statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void failedToAccessDifferentCountySensitiveClient() {
    functionalTestingBuilder.processGetRequest(hoiReferrals, "clientIds", "Aybe9HF00h", token)
        .then().statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void failedToAccessDifferentCountySealedClient() {
    functionalTestingBuilder.processGetRequest(hoiReferrals, "clientIds", "AIwcGUp0Nu", token)
        .then().statusCode(403);
  }

}
