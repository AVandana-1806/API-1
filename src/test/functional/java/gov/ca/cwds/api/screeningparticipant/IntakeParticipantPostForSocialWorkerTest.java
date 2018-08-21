package gov.ca.cwds.api.screeningparticipant;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.fixture.ParticipantIntakeApiResourceBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.core.Api;

/**
 * @author CWDS API Team
 *
 */
public class IntakeParticipantPostForSocialWorkerTest extends FunctionalTest {
  private static final String INTAKE_SCREENING_POST_REQUEST =
      "fixtures/gov/ca/cwds/rest/resources/screening-post-request.json";
  String resourcePath;
  String screeningPostPath;
  private HttpRequestHandler httpRequestHandler;
  private String id;

  /**
   * @throws Exception - Exception
   * 
   */
  @Before
  public void setup() throws Exception {
    httpRequestHandler = new HttpRequestHandler();
    getScreeningId();
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_SCREENINGS + "/{id}" + "/participant");
  }

  private void getScreeningId() throws Exception {
    screeningPostPath = getResourceUrlFor("/" + Api.RESOURCE_INTAKE_SCREENINGS);
    String intakeScreeningRequest = fixture(INTAKE_SCREENING_POST_REQUEST);
    String response =
        httpRequestHandler.postRequest(intakeScreeningRequest, screeningPostPath, token).asString();
    Screening screening = objectMapper.readValue(response.getBytes(), Screening.class);
    id = screening.getId();
  }

  /**
   * 
   */
  @Test
  public void particpantCreateFailedToAddSameCountySensitiveClient() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("B2YtETx00T", null, null, LegacyTable.CLIENT.getName(), null);
    ParticipantIntakeApi intakeParticipant = new ParticipantIntakeApiResourceBuilder().setId(null)
        .setScreeningId(id).setLegacyDescriptor(legacyDescriptor).build();
    Map<String, Object> pathParams = new HashMap<String, Object>();
    pathParams.put("id", id);
    httpRequestHandler
        .postRequestWithPathParameters(intakeParticipant, resourcePath, pathParams, token).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void particpantCreateFailedToAddSameCountySealedClient() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("B0gYFaU057", null, null, LegacyTable.CLIENT.getName(), null);
    ParticipantIntakeApi intakeParticipant = new ParticipantIntakeApiResourceBuilder().setId(null)
        .setScreeningId(id).setLegacyDescriptor(legacyDescriptor).build();
    Map<String, Object> pathParams = new HashMap<String, Object>();
    pathParams.put("id", id);
    httpRequestHandler
        .postRequestWithPathParameters(intakeParticipant, resourcePath, pathParams, token).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void particpantCreateFailedToAddDifferentCountySensitiveClient() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("TbCDoJB0La", null, null, LegacyTable.CLIENT.getName(), null);
    ParticipantIntakeApi intakeParticipant = new ParticipantIntakeApiResourceBuilder().setId(null)
        .setScreeningId(id).setLegacyDescriptor(legacyDescriptor).build();
    Map<String, Object> pathParams = new HashMap<String, Object>();
    pathParams.put("id", id);
    httpRequestHandler
        .postRequestWithPathParameters(intakeParticipant, resourcePath, pathParams, token).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void particpantCreateFailedToAddDifferentCountySealedClient() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("AIwcGUp0Nu", null, null, LegacyTable.CLIENT.getName(), null);
    ParticipantIntakeApi intakeParticipant = new ParticipantIntakeApiResourceBuilder().setId(null)
        .setScreeningId(id).setLegacyDescriptor(legacyDescriptor).build();
    Map<String, Object> pathParams = new HashMap<String, Object>();
    pathParams.put("id", id);
    httpRequestHandler
        .postRequestWithPathParameters(intakeParticipant, resourcePath, pathParams, token).then()
        .statusCode(403);
  }

}
