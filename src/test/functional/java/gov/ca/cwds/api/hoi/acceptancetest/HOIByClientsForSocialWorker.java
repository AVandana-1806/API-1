package gov.ca.cwds.api.hoi.acceptancetest;

import static gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.EVALUATE_OUT;
import static gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.IMMEDIATE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.RESPONSE_TIME_10_DAYS;
import static gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.RESPONSE_TIME_5_DAYS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.fixture.AllegationResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.core.Api;
import io.restassured.response.Response;

/**
 * @author CWDS API Team
 *
 */
public class HOIByClientsForSocialWorker extends FunctionalTest {

  String resourcePath;
  String referralsPath;
  private static Short GENERAL_NEGLECT = 2178;
  private static Short PHYSICAL_ABUSE = 2179;
  private static Short CARETAKER_ABSENCE = 2169;
  private static Short SEXUAL_ABUSE = 2181;
  private static Short SEVERE_NEGLECT = 2180;
  private HttpRequestHandler httpRequestHandler;

  /**
   * 
   */
  @Before
  public void setup() {
    referralsPath = getResourceUrlFor("/" + Api.RESOURCE_REFERRALS);
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_CLIENT + "/" + Api.HISTORY_OF_INVOLVEMENTS);
    httpRequestHandler = new HttpRequestHandler();
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForEvaluateOutResponseTimeReferral() throws Exception {
    String clientId = findVictimClientId("N", userInfo.getIncidentCounty(), EVALUATE_OUT.getCode(),
        GENERAL_NEGLECT);
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    String actualJson = httpRequestHandler.getRequest(resourcePath, queryParams).asString();
    final InvolvementHistory actualHOI =
        objectMapper.readValue(actualJson.getBytes(), InvolvementHistory.class);
    assertThat(actualHOI.getReferrals().get(0).getResponseTime().getId(), is(notNullValue()));
    assertThat(actualHOI.getReferrals().get(0).getResponseTime().getId(),
        is(equalTo(EVALUATE_OUT.getCode())));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testFor5DaysResponseTimeReferral() throws Exception {
    String clientId = findVictimClientId("N", userInfo.getIncidentCounty(),
        RESPONSE_TIME_5_DAYS.getCode(), GENERAL_NEGLECT);
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    String actualJson = httpRequestHandler.getRequest(resourcePath, queryParams).asString();
    final InvolvementHistory actualHOI =
        objectMapper.readValue(actualJson.getBytes(), InvolvementHistory.class);
    assertThat(actualHOI.getReferrals().get(0).getResponseTime().getId(), is(notNullValue()));
    assertThat(actualHOI.getReferrals().get(0).getResponseTime().getId(),
        is(equalTo(RESPONSE_TIME_5_DAYS.getCode())));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testFor10DaysResponseTimeReferral() throws Exception {
    String clientId = findVictimClientId("N", userInfo.getIncidentCounty(),
        RESPONSE_TIME_10_DAYS.getCode(), GENERAL_NEGLECT);
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    String actualJson = httpRequestHandler.getRequest(resourcePath, queryParams).asString();
    final InvolvementHistory actualHOI =
        objectMapper.readValue(actualJson.getBytes(), InvolvementHistory.class);
    assertThat(actualHOI.getReferrals().get(0).getResponseTime().getId(), is(notNullValue()));
    assertThat(actualHOI.getReferrals().get(0).getResponseTime().getId(),
        is(equalTo(RESPONSE_TIME_10_DAYS.getCode())));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForImmediateResponseTimeReferral() throws Exception {
    String clientId =
        findVictimClientId("N", userInfo.getIncidentCounty(), IMMEDIATE.getCode(), GENERAL_NEGLECT);
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    String actualJson = httpRequestHandler.getRequest(resourcePath, queryParams).asString();
    final InvolvementHistory actualHOI =
        objectMapper.readValue(actualJson.getBytes(), InvolvementHistory.class);
    assertThat(actualHOI.getReferrals().get(0).getResponseTime().getId(), is(notNullValue()));
    assertThat(actualHOI.getReferrals().get(0).getResponseTime().getId(),
        is(equalTo(IMMEDIATE.getCode())));
  }

  /**
   * @throws Exception
   */
  @Test
  public void testAllegationTypeIsPhysicalAbuse() throws Exception {
    String clientId =
        findVictimClientId("N", userInfo.getIncidentCounty(), IMMEDIATE.getCode(), PHYSICAL_ABUSE);
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    String actualJson = httpRequestHandler.getRequest(resourcePath, queryParams).asString();
    final InvolvementHistory actualHOI =
        objectMapper.readValue(actualJson.getBytes(), InvolvementHistory.class);
    assertThat(actualHOI.getReferrals().get(0).getAllegations().get(0).getType().getId(),
        is(notNullValue()));
    assertThat(actualHOI.getReferrals().get(0).getAllegations().get(0).getType().getId(),
        is(equalTo(PHYSICAL_ABUSE)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testAllegationTypeIsCareTakerAbsence() throws Exception {
    String clientId = findVictimClientId("N", userInfo.getIncidentCounty(), IMMEDIATE.getCode(),
        CARETAKER_ABSENCE);
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    String actualJson = httpRequestHandler.getRequest(resourcePath, queryParams).asString();
    final InvolvementHistory actualHOI =
        objectMapper.readValue(actualJson.getBytes(), InvolvementHistory.class);
    assertThat(actualHOI.getReferrals().get(0).getAllegations().get(0).getType().getId(),
        is(notNullValue()));
    assertThat(actualHOI.getReferrals().get(0).getAllegations().get(0).getType().getId(),
        is(equalTo(CARETAKER_ABSENCE)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testAllegationTypeIsSexualAbuse() throws Exception {
    String clientId =
        findVictimClientId("N", userInfo.getIncidentCounty(), IMMEDIATE.getCode(), SEXUAL_ABUSE);
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    String actualJson = httpRequestHandler.getRequest(resourcePath, queryParams).asString();
    final InvolvementHistory actualHOI =
        objectMapper.readValue(actualJson.getBytes(), InvolvementHistory.class);
    assertThat(actualHOI.getReferrals().get(0).getAllegations().get(0).getType().getId(),
        is(notNullValue()));
    assertThat(actualHOI.getReferrals().get(0).getAllegations().get(0).getType().getId(),
        is(equalTo(SEXUAL_ABUSE)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testAllegationTypeIsSevereNeglect() throws Exception {
    String clientId =
        findVictimClientId("N", userInfo.getIncidentCounty(), IMMEDIATE.getCode(), SEVERE_NEGLECT);
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", clientId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    String actualJson = httpRequestHandler.getRequest(resourcePath, queryParams).asString();
    final InvolvementHistory actualHOI =
        objectMapper.readValue(actualJson.getBytes(), InvolvementHistory.class);
    assertThat(actualHOI.getReferrals().get(0).getAllegations().get(0).getType().getId(),
        is(notNullValue()));
    assertThat(actualHOI.getReferrals().get(0).getAllegations().get(0).getType().getId(),
        is(equalTo(SEVERE_NEGLECT)));
  }

  protected String findVictimClientId(String sensitivityIndicator, String incidentCounty,
      Short responseTime, Short injuryHarmType)
      throws JsonParseException, JsonMappingException, IOException {
    Allegation allegation =
        new AllegationResourceBuilder().setInjuryHarmType(injuryHarmType).createAllegation();
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referrals = new ScreeningToReferralResourceBuilder().setEndedAt(null)
        .setAssigneeStaffId(userInfo.getStaffId()).setResponseTime(responseTime)
        .setIncidentCounty(incidentCounty).setLimitedAccessCode(sensitivityIndicator)
        .setAllegations(allegations).createScreeningToReferral();
    Response response = httpRequestHandler.postRequest(referrals, referralsPath, token);
    ScreeningToReferral screeningToReferral =
        objectMapper.readValue(response.asString(), ScreeningToReferral.class);

    Optional<Participant> victim = screeningToReferral.getParticipants().stream()
        .filter(value -> value.getRoles().contains("Victim")).findFirst();
    return victim.get().getLegacyDescriptor().getId();
  }

}
