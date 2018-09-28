package gov.ca.cwds.rest.resources.relationship;

import static gov.ca.cwds.rest.core.Api.RESOURCE_PARTICIPANTS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.core.type.TypeReference;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class RemoveParticipantsAndRelationshipsIRT extends RelationshipsBaseTest {

  public static final String SCREENING_ID = "2201";

  public static final String PARTICIPANT_ID_1 = "2036";
  public static final String PARTICIPANT_ID_2 = "2037";
  public static final String PARTICIPANT_ID_3 = "2038";

  @Test
  public void deleteOneParticipantFromScreeningWith3participants() throws IOException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID + "/" + RELATIONSHIPS_WITH_CANDIDATES));

    assertNotNull(actualJson);

    List<ScreeningRelationshipsWithCandidates> fromResponse = objectMapper
        .readValue(actualJson,
            new TypeReference<List<ScreeningRelationshipsWithCandidates>>() {
            });
    assertNotNull(fromResponse);
    assertEquals(1, fromResponse.size());
    assertEquals(PARTICIPANT_ID_1, fromResponse.get(0).getId());

    assertNotNull(fromResponse.get(0));
    assertNotNull(fromResponse.get(0).getRelatedTo());

    ArrayList listRelated1 = new ArrayList(fromResponse.get(0).getRelatedTo());
    assertEquals(4, listRelated1.size());

    deleteParticipant(SCREENING_ID, PARTICIPANT_ID_1);

    actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID + "/" + RELATIONSHIPS_WITH_CANDIDATES));

    assertNotNull(actualJson);

    fromResponse = objectMapper
        .readValue(actualJson,
            new TypeReference<List<ScreeningRelationshipsWithCandidates>>() {
            });
    assertNotNull(fromResponse);
    assertEquals(0, fromResponse.size());

    javax.ws.rs.core.Response participantIntakeApi2response = getParticipant(SCREENING_ID, PARTICIPANT_ID_2);
    assertEquals(404, participantIntakeApi2response.getStatus());
    javax.ws.rs.core.Response participantIntakeApi3response = getParticipant(SCREENING_ID, PARTICIPANT_ID_3);
    assertEquals(404, participantIntakeApi3response.getStatus());
  }

  private void deleteParticipant(String screeningId, String participantId) throws IOException {
    doDeleteCall(RESOURCE_SCREENINGS + "/" + screeningId + "/participants/" + participantId);
  }

  private javax.ws.rs.core.Response getParticipant(String screeningId, String participantId)
      throws IOException {
    return doGetCall(
            RESOURCE_SCREENINGS + "/" + screeningId + "/" + RESOURCE_PARTICIPANTS + "/"
                + participantId);
  }

}
