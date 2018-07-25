package gov.ca.cwds.rest.resources;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;


import static gov.ca.cwds.rest.core.Api.RESOURCE_PARTICIPANTS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import static io.dropwizard.testing.FixtureHelpers.fixture;

/**
 * CWDS API Team
 */
public class ScreeningParticipantResourceIRT extends IntakeBaseTest {
  @Test
  public void testPostParticipantWithActivePlacementHomeAddress() throws Exception {
    String request = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-attach-to-screening-post-request.json");
    String actualJson = getStringResponse(doPostCall(RESOURCE_SCREENINGS + "/36/participant", request));
    String expectedResponse = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-attach-to-screening-post-response.json");
    ParticipantIntakeApi participant = objectMapper.readValue(actualJson.getBytes(), ParticipantIntakeApi.class);

    expectedResponse = expectedResponse.replace("${participant_id}", participant.getId());
    expectedResponse = expectedResponse.replace("${address_id}", participant.getAddresses().get(0).getId());

    System.out.println(actualJson);
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);

    doDeleteCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/" + participant.getId());
  }
}
