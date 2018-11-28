package gov.ca.cwds.rest.resources.relationship;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.core.type.TypeReference;

import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.RelatedTo;

/**
 * @author CWDS TPT-3 Team
 */
public class ScreeningRelationshipsWithCandidatesIRT extends RelationshipsBaseTest {

  public static final String RELATIONSHIPS = "relationships";
  public static final String SCREENING_PATH = "screenings";
  public static final String RELATIONSHIPS_WITH_CANDIDATES = "relationships_with_candidates";

  public static final String FIXTURE_GET_RELATIONSHIPS_THREE_PARTICIPANTS_TWO_RELATIONSHIPS =
      "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-three-participants-one-relationships.json";
  public static final String SCREENING_ID_8 = "2107";

  public static final String FIXTURE_GET_RELATIONSHIPS_RESPONSE_NO_RELATIONSHIPS =
      "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-with-candidates-two-candidate-no-relations.json";
  public static final String SCREENING_ID_9 = "2109";

  public static final String SCREENING_ID_10 = "2110";

  public static final String FIXTURE_GET_RELATIONSHIPS_RESPONSE_TWO_RELATIONSHIPS_NO_CANDIDATES =
      "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-with-candidates-no-candidate-two-relationships.json";
  public static final String SCREENING_ID_11 = "2111";

  public static final String SCREENING_ID_12 = "2112";
  public static final String FIXTURE_GET_RELATIONSHIPS_ONE_PARTICIPANT =
      "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-with-candidates-one-participant.json";

  public static final String SCREENING_ID_13 = "2113";
  public static final String FIXTURE_GET_RELATIONSHIPS_FOUR_PARTICIPANTS =
      "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-with-candidates-four-participants.json";

  public static final String SCREENING_ID_14 = "2215";
  public static final String PARTICIPANT_ID_14 = "2039";

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
    String expectedResponse = fixture(FIXTURE_GET_RELATIONSHIPS_RESPONSE_NO_RELATIONSHIPS);

    validateResponse(actualJson, expectedResponse);
  }

  @Test
  public void getRelationshipsByScreeningIdWithCandidates_noRelationsNoParticipants()
      throws IOException, JSONException {
    String actualJson =
        getStringResponse(doGetCall(SCREENING_PATH + "/" + SCREENING_ID_10 + "/" + RELATIONSHIPS));

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
  public void getRelationshipsByScreeningIdWithCandidates_FourParticipants() throws IOException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_12 + "/" + RELATIONSHIPS_WITH_CANDIDATES));
    String expectedResponse = fixture(FIXTURE_GET_RELATIONSHIPS_FOUR_PARTICIPANTS);

    validateResponse(actualJson, expectedResponse);
  }

  @Test
  public void getRelationshipsByScreeningIdWithCandidates_OneParticipantsOneRelationship()
      throws IOException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_13 + "/" + RELATIONSHIPS_WITH_CANDIDATES));
    String expectedResponse = fixture(FIXTURE_GET_RELATIONSHIPS_ONE_PARTICIPANT);
    validateResponse(actualJson, expectedResponse);
  }

  @Test
  public void getRelationshipsWithLegacyDescriptor() throws IOException {
    final String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + "1106" + "/" + RELATIONSHIPS_WITH_CANDIDATES));

    final List<ScreeningRelationshipsWithCandidates> fromResponse = objectMapper
        .readValue(actualJson, new TypeReference<List<ScreeningRelationshipsWithCandidates>>() {});
    assertNotNull(fromResponse);
    assertEquals(3, fromResponse.size());

    final ScreeningRelationshipsWithCandidates zero = fromResponse.get(0);
    assertNotNull(zero.getRelatedCandidatesTo());
    assertEquals(1, zero.getRelatedCandidatesTo().size());
    assertNotNull(zero.getRelatedTo());
    assertEquals(1, zero.getRelatedTo().size());
    assertNull(zero.getRelatedTo().iterator().next().getLegacyDescriptor());
    assertNull(zero.getRelatedCandidatesTo().iterator().next().getLegacyDescriptor());

    final ScreeningRelationshipsWithCandidates one = fromResponse.get(1);
    assertNotNull(one.getRelatedCandidatesTo());
    assertEquals(0, one.getRelatedCandidatesTo().size());
    assertEquals(2, one.getRelatedTo().size());
    assertNotNull(one.getRelatedTo().iterator().next().getLegacyDescriptor());
    assertEquals("0000000008", one.getRelatedTo().iterator().next().getLegacyDescriptor().getId());

    final ScreeningRelationshipsWithCandidates two = fromResponse.get(1);
    assertNotNull(two.getRelatedCandidatesTo());
    assertEquals(1, two.getRelatedCandidatesTo().size());
    assertNotNull(two.getRelatedTo());
    assertEquals(1, two.getRelatedTo().size());
    assertNull(two.getRelatedTo().iterator().next().getLegacyDescriptor());
    assertNotNull(two.getRelatedCandidatesTo().iterator().next().getLegacyDescriptor());
    assertEquals("0000000008",
        two.getRelatedCandidatesTo().iterator().next().getLegacyDescriptor().getId());
  }

  @Test
  public void getRelationshipsWithCandidateWithEstimatedDob() throws IOException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_14 + "/" + RELATIONSHIPS_WITH_CANDIDATES));
    System.out.println(actualJson);
    List<ScreeningRelationshipsWithCandidates> fromResponse = objectMapper.readValue(actualJson,
        new TypeReference<List<ScreeningRelationshipsWithCandidates>>() {});
    assertNotNull(fromResponse);
    assertEquals(1, fromResponse.size());
    assertEquals("N", fromResponse.get(0).getEstimatedDobCode());
    assertEquals("1944-01-30", fromResponse.get(0).getDateOfBirth());
    assertNull(fromResponse.get(0).getEstimatedDob());
    assertNotNull(fromResponse.get(0).getRelatedTo());
    assertEquals(2, fromResponse.get(0).getRelatedTo().size());

    final Iterator<RelatedTo> setIterator = fromResponse.get(0).getRelatedTo().iterator();
    RelatedTo relatedTo1 = setIterator.next();
    RelatedTo relatedTo2 = setIterator.next();

    RelatedTo relatedTo;
    if ("Y".equals(relatedTo1.getEstimatedDobCode())) {
      relatedTo = relatedTo1;
    } else {
      relatedTo = relatedTo2;
    }

    assertEquals("Y", relatedTo.getEstimatedDobCode());
    assertEquals("1944-01-30", relatedTo.getEstimatedDob());
    assertNull(relatedTo.getRelatedDateOfBirth());

    if ("U".equals(relatedTo1.getEstimatedDobCode())) {
      relatedTo2 = relatedTo1;
    }
    assertEquals("U", relatedTo2.getEstimatedDobCode());
    assertNull(relatedTo2.getEstimatedDob());
    assertNull(relatedTo2.getRelatedDateOfBirth());
  }

}
