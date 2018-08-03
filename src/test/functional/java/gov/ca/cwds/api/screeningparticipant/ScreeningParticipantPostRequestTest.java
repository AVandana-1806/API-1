package gov.ca.cwds.api.screeningparticipant;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.fixture.ParticipantIntakeApiResourceBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.core.Api;

/**
 * @author CWDS API Team
 *
 */
public class ScreeningParticipantPostRequestTest extends FunctionalTest {
  String resourcePath;
  private HttpRequestHandler httpRequestHandler;

  /**
   * 
   */
  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_SCREENINGS + "/{id}" + "/participant");
    httpRequestHandler = new HttpRequestHandler();
  }

  /**
   * 
   */
  @Test
  @Ignore
  public void particpantCreateFailedToAddSameCountySensitiveClient() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("B2YtETx00T", null, null, LegacyTable.CLIENT.getName(), null);
    ParticipantIntakeApi intakeParticipant = new ParticipantIntakeApiResourceBuilder().setId(null)
        .setScreeningId("277").setLegacyDescriptor(legacyDescriptor).build();
    Map<String, Object> pathParams = new HashMap<String, Object>();
    pathParams.put("id", 277);
    httpRequestHandler
        .postRequestWithPathParameters(intakeParticipant, resourcePath, pathParams, token).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  @Ignore
  public void particpantCreateFailedToAddSameCountySealedClient() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("B0gYFaU057", null, null, LegacyTable.CLIENT.getName(), null);
    ParticipantIntakeApi intakeParticipant = new ParticipantIntakeApiResourceBuilder().setId(null)
        .setScreeningId("277").setLegacyDescriptor(legacyDescriptor).build();
    Map<String, Object> pathParams = new HashMap<String, Object>();
    pathParams.put("id", 277);
    httpRequestHandler
        .postRequestWithPathParameters(intakeParticipant, resourcePath, pathParams, token).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  @Ignore
  public void particpantCreateFailedToAddDifferentCountySensitiveClient() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("TbCDoJB0La", null, null, LegacyTable.CLIENT.getName(), null);
    ParticipantIntakeApi intakeParticipant = new ParticipantIntakeApiResourceBuilder().setId(null)
        .setScreeningId("277").setLegacyDescriptor(legacyDescriptor).build();
    Map<String, Object> pathParams = new HashMap<String, Object>();
    pathParams.put("id", 277);
    httpRequestHandler
        .postRequestWithPathParameters(intakeParticipant, resourcePath, pathParams, token).then()
        .statusCode(403);
  }

  /**
   * 
   */
  @Test
  @Ignore
  public void particpantCreateFailedToAddDifferentCountySealedClient() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("AIwcGUp0Nu", null, null, LegacyTable.CLIENT.getName(), null);
    ParticipantIntakeApi intakeParticipant = new ParticipantIntakeApiResourceBuilder().setId(null)
        .setScreeningId("277").setLegacyDescriptor(legacyDescriptor).build();
    Map<String, Object> pathParams = new HashMap<String, Object>();
    pathParams.put("id", 277);
    httpRequestHandler
        .postRequestWithPathParameters(intakeParticipant, resourcePath, pathParams, token).then()
        .statusCode(403);
  }

}
