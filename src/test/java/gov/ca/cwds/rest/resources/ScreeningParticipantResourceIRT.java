package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_PARTICIPANTS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import static io.dropwizard.testing.FixtureHelpers.fixture;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;

/**
 * CWDS API Team
 */
public class ScreeningParticipantResourceIRT extends IntakeBaseTest {

  @Test
  @Ignore
  public void testPostParticipantWithActivePlacementHomeAddress() throws Exception {
    final String request = fixture(
        "fixtures/gov/ca/cwds/rest/resources/participant-intake-api-attach-to-screening-post-request.json");
    final String actualJson =
        getStringResponse(doPostCall(RESOURCE_SCREENINGS + "/36/participant", request));
    final ParticipantIntakeApi participant =
        objectMapper.readValue(actualJson.getBytes(), ParticipantIntakeApi.class);
    String expectedResponse = fixture(
        "fixtures/gov/ca/cwds/rest/resources/participant-intake-api-attach-to-screening-post-response.json");

    if (participant != null && StringUtils.isNotBlank(participant.getId())) {
      expectedResponse = expectedResponse.replace("${participant_id}", participant.getId());
    }

    if (participant != null && participant.getAddresses() != null
        && !participant.getAddresses().isEmpty()
        && StringUtils.isNotBlank(participant.getAddresses().get(0).getId())) {
      expectedResponse =
          expectedResponse.replace("${address_id}", participant.getAddresses().get(0).getId());
    }

    System.out.println(actualJson);
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);

    doDeleteCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/" + participant.getId());
  }

}
