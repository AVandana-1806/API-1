package gov.ca.cwds.rest.resources.relationship;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.fasterxml.jackson.core.type.TypeReference;
import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.CandidateTo;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.RelatedTo;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author CWDS TPT-3 Team
 */
public class ScreeningRelationshipsWithCandidatesIRT extends RelationshipsBaseTest {

  public static final String RELATIONSHIPS = "relationships";
  public static final String SCREENING_PATH = "screenings";
  public static final String RELATIONSHIPS_WITH_CANDIDATES = "relationships_with_candidates";

  public static final String FIXTURE_GET_RELATIONSHIPS_THREE_PARTICIPANTS_TWO_RELATIONSHIPS = "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-three-participants-one-relationships.json";
  public static final String SCREENING_ID_8 = "2107";

  public static final String FIXTURE_GET_RELATIONSHIPS_RESPONSE_NO_RELATIONSHIPS = "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-with-candidates-two-candidate-no-relations.json";
  public static final String SCREENING_ID_9 = "2109";

  public static final String SCREENING_ID_10 = "2110";

  public static final String FIXTURE_GET_RELATIONSHIPS_RESPONSE_TWO_RELATIONSHIPS_NO_CANDIDATES = "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-with-candidates-no-candidate-two-relationships.json";
  public static final String SCREENING_ID_11 = "2111";

  public static final String SCREENING_ID_12 = "2112";
  public static final String FIXTURE_GET_RELATIONSHIPS_ONE_PARTICIPANT = "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-with-candidates-one-participant.json";

  public static final String SCREENING_ID_13 = "2113";
  public static final String FIXTURE_GET_RELATIONSHIPS_FOUR_PARTICIPANTS = "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-with-candidates-four-participants.json";


  @Test
  public void getRelationshipsByScreeningIdWithCandidates_threeParticipantOneRelationshipTwoCandidates()
      throws IOException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_8 + "/" + RELATIONSHIPS_WITH_CANDIDATES));
    String expectedResponse =
        fixture(FIXTURE_GET_RELATIONSHIPS_THREE_PARTICIPANTS_TWO_RELATIONSHIPS);
    validateResponse(actualJson, expectedResponse);
  }

  @Test
  public void getRelationshipsByScreeningIdWithCandidates_noRelationsTwoParticipants()
      throws IOException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_9 + "/" + RELATIONSHIPS_WITH_CANDIDATES));
    String expectedResponse =
        fixture(FIXTURE_GET_RELATIONSHIPS_RESPONSE_NO_RELATIONSHIPS);

    validateResponse(actualJson, expectedResponse);
  }

  @Test
  public void getRelationshipsByScreeningIdWithCandidates_noRelationsNoParticipants()
      throws IOException, JSONException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_10 + "/" + RELATIONSHIPS));
    JSONAssert.assertEquals("[]", actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void getRelationshipsByScreeningIdWithCandidates_TwoRelationsNoCandidates()
      throws IOException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_11 + "/" + RELATIONSHIPS_WITH_CANDIDATES));
    String expectedResponse =
        fixture(FIXTURE_GET_RELATIONSHIPS_RESPONSE_TWO_RELATIONSHIPS_NO_CANDIDATES);

    validateResponse(actualJson, expectedResponse);
  }

  @Test
  public void getRelationshipsByScreeningIdWithCandidates_FourParticipants()
      throws IOException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_12 + "/" + RELATIONSHIPS_WITH_CANDIDATES));
    String expectedResponse =
        fixture(FIXTURE_GET_RELATIONSHIPS_FOUR_PARTICIPANTS);

    validateResponse(actualJson, expectedResponse);
  }

  @Test
  public void getRelationshipsByScreeningIdWithCandidates_OneParticipantsOneRelationship()
      throws IOException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_13 + "/" + RELATIONSHIPS_WITH_CANDIDATES));
    String expectedResponse =
        fixture(FIXTURE_GET_RELATIONSHIPS_ONE_PARTICIPANT);

    validateResponse(actualJson, expectedResponse);
  }

  @Test
  public void getRelationshipsWithLegacyDescriptor() throws IOException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + "1106" + "/" + RELATIONSHIPS_WITH_CANDIDATES));

    List<ScreeningRelationshipsWithCandidates> fromResponse = objectMapper
        .readValue(actualJson,
            new TypeReference<List<ScreeningRelationshipsWithCandidates>>() {
            });
    assertNotNull(fromResponse);
    assertEquals(3, fromResponse.size());
    assertNotNull(fromResponse.get(0).getRelatedCandidatesTo());
    assertEquals(1, fromResponse.get(0).getRelatedCandidatesTo().size());
    assertNotNull(fromResponse.get(0).getRelatedTo());
    assertEquals(1, fromResponse.get(0).getRelatedTo().size());
    assertNull(fromResponse.get(0).getRelatedTo().iterator().next().getLegacyDescriptor());
    assertNull(fromResponse.get(0).getRelatedCandidatesTo().iterator().next().getLegacyDescriptor());

    assertNotNull(fromResponse.get(1).getRelatedCandidatesTo());
    assertEquals(0, fromResponse.get(1).getRelatedCandidatesTo().size());
    assertEquals(2, fromResponse.get(1).getRelatedTo().size());
    assertNotNull(fromResponse.get(1).getRelatedTo().iterator().next().getLegacyDescriptor());
    assertEquals("0000000008", fromResponse.get(1).getRelatedTo().iterator().next().getLegacyDescriptor().getId());

    assertNotNull(fromResponse.get(2).getRelatedCandidatesTo());
    assertEquals(1, fromResponse.get(2).getRelatedCandidatesTo().size());
    assertNotNull(fromResponse.get(2).getRelatedTo());
    assertEquals(1, fromResponse.get(2).getRelatedTo().size());
    assertNull(fromResponse.get(2).getRelatedTo().iterator().next().getLegacyDescriptor());
    assertNotNull(fromResponse.get(2).getRelatedCandidatesTo().iterator().next().getLegacyDescriptor());
    assertEquals("0000000008", fromResponse.get(2).getRelatedCandidatesTo().iterator().next().getLegacyDescriptor().getId());
  }
}
