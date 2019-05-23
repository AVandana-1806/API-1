package gov.ca.cwds.api.hoi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.core.Api;
import io.restassured.response.Response;

/**
 * Functional Testing for hoi referrals end point with Social Worker Privilege Only(GVR_ENTC = 1084)
 * 
 * @author CWDS API Team
 */
public class HoiReferralsForSocialWorkerTest extends FunctionalTest {

  String referralsPath;
  String resourcePath;
  private HttpRequestHandler httpRequestHandler;

  /**
   * 
   */
  @Before
  public void setup() {
    referralsPath = getResourceUrlFor("/" + Api.RESOURCE_REFERRALS);
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT);
    httpRequestHandler = new HttpRequestHandler();
  }

  /**
   * @throws Exception - Exception
   * 
   */
  @Test
  public void testSuccessToAccessNoConditionClient() throws Exception {
    String clientId = findVictimClientId("N", userInfo.getIncidentCounty());
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    httpRequestHandler.getRequest(resourcePath, queryParams).then().body("isEmpty()",
        Matchers.is(false));
  }

  /**
   * @throws Exception - Exception
   * 
   */
  @Test
  public void failedToAccessSameCountySensitiveClient() throws Exception {
    String clientId = findVictimClientId("S", userInfo.getIncidentCounty());
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    httpRequestHandler.getRequest(resourcePath, queryParams).then()
        .body("isEmpty()", Matchers.is(true)).statusCode(200);
  }

  /**
   * @throws Exception - Exception
   * 
   */
  @Test
  public void failedToAccessSameCountySealedClient() throws Exception {
    String clientId = findVictimClientId("R", userInfo.getIncidentCounty());
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    httpRequestHandler.getRequest(resourcePath, queryParams).then()
        .body("isEmpty()", Matchers.is(true)).statusCode(200);
  }

  /**
   * 
   */
  @Test
  public void failedToAccessDifferentCountySensitiveClient() {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", "9PIxHucCON");
    queryParams.put(httpRequestHandler.TOKEN, token);
    httpRequestHandler.getRequest(resourcePath, queryParams).then()
        .body("isEmpty()", Matchers.is(true)).statusCode(200);
  }

  /**
   * 
   */
  @Test
  public void failedToAccessDifferentCountySealedClient() {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", "AIwcGUp0Nu");
    queryParams.put(httpRequestHandler.TOKEN, token);
    httpRequestHandler.getRequest(resourcePath, queryParams).then()
        .body("isEmpty()", Matchers.is(true)).statusCode(200);
  }

  private String findVictimClientId(String sensitivityIndicator, String incidentCounty)
      throws IOException, JsonParseException, JsonMappingException {
    ScreeningToReferral referrals = new ScreeningToReferralResourceBuilder().setEndedAt(null)
        .setAssigneeStaffId(userInfo.getStaffId()).setIncidentCounty(incidentCounty)
        .setLimitedAccessCode(sensitivityIndicator).createScreeningToReferral();
    Response response = httpRequestHandler.postRequest(referrals, referralsPath, token);
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JodaModule());
    mapper.registerModule(new JavaTimeModule());
    ScreeningToReferral screeningToReferral =
        mapper.readValue(response.asString(), ScreeningToReferral.class);

    Optional<Participant> victim = screeningToReferral.getParticipants().stream()
        .filter(value -> value.getRoles().contains("Victim")).findFirst();
    return victim.get().getLegacyDescriptor().getId();
  }

}
