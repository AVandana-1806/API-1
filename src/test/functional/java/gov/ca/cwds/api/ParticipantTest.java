package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;

import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.api.builder.ResourceEndPoint;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ParticipantTest extends FunctionalTest{
  String baseUrl;
  String urlGetPath;
  String referralPath;

  private HttpRequestHandler httpRequestHandler;

  @Before
  public void setup() {
    baseUrl = ResourceEndPoint.PARTICIPANTS.getResourcePath();
    urlGetPath = getResourceUrlFor(baseUrl + "/{id}");
    referralPath = getResourceUrlFor(ResourceEndPoint.REFERRALS.getResourcePath());

    httpRequestHandler = new HttpRequestHandler();
  }
  @Ignore("TEMP causing 500 in Jenkins")
  @Test
  public void return404WhenparticipantIsNotFound(){
    given().pathParams("id", "asdf").queryParam("token", token)
        .when().get(urlGetPath)
        .then().statusCode(404);
  }
  @Ignore("TEMP causing 500 in Jenkins")
  @Test
  public void return200WhenParticipantIsFound(){
    ScreeningToReferral referralRequest = new ScreeningToReferralResourceBuilder()
        .setName("return201SuccessForValidReferrals").setAssigneeStaffId(userInfo.getStaffId())
        .setIncidentCounty(userInfo.getIncidentCounty()).createScreeningToReferral();

    Response response = httpRequestHandler.postRequest(referralRequest, referralPath, token);
    ArrayList<Map<String,?>> participants = response.path("participants");

    String victimId = findByFirstName(participants, "Victim");
    given().pathParams("id", victimId).queryParam("token", token)
        .when().get(urlGetPath)
        .then().statusCode(200);
  }
  /*
  Parsing through multiple participants to find the legacy id of a specific
  participant. The participants is a JSON representation in an ArrayList comprising
  of a map of strings, maps, or lists of maps. Assumes the first name is unique and
  known.
   */
  private String findByFirstName(ArrayList participants, String name){
    String legacyId = "";
    Iterator <Map<String,Map<String, String>> > iterator = participants.iterator();
    while (iterator.hasNext()){
      Map participant = iterator.next();
      if (participant.get("first_name").equals(name)){
        Map legacyDescriptor = (Map) participant.get("legacy_descriptor");
        legacyId = (String)legacyDescriptor.get("legacy_id");
      }
    }
    return legacyId;
  }
}
