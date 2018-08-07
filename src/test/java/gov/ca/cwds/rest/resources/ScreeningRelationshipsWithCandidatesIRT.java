package gov.ca.cwds.rest.resources;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;

import gov.ca.cwds.IntakeBaseTest;
import java.io.IOException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author CWDS TPT-3 Team
 */
public class ScreeningRelationshipsWithCandidatesIRT extends IntakeBaseTest {

  public static final String SCREENING_PATH = "screenings";
  public static final String RELATIONSHIPS = "relationships";
  public static final String RELATIONSHIPS_WITH_CANDIDATES = "relationships_with_candidates";

  public static final String FIXTURE_GET_RELATIONSHIPS_RESPONSE_ONE_CANDIDATE = "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-with-candidates-one-candidate.json";
  public static final String SCREENING_ID_8 = "2107";

  public static final String FIXTURE_GET_RELATIONSHIPS_RESPONSE_NO_RELATIONSHIPS = "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-with-candidates-two-candidate-no-relations.json";
  public static final String SCREENING_ID_9 = "2109";

  public static final String SCREENING_ID_10 = "2110";

  public static final String FIXTURE_GET_RELATIONSHIPS_RESPONSE_TWO_RELATIONSHIPS_NO_CANDIDATES = "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-with-candidates-no-candidate-two-relationships.json";
  public static final String SCREENING_ID_11 = "2111";

  @Test
  public void getRelationshipsByScreeningIdWithCandidates_oneCandidateOneRelationship()
      throws IOException, JSONException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_8 + "/" + RELATIONSHIPS_WITH_CANDIDATES));
    String expectedResponse =
        fixture(FIXTURE_GET_RELATIONSHIPS_RESPONSE_ONE_CANDIDATE);
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void getRelationshipsByScreeningIdWithCandidates_noRelationsTwoParticipants()
      throws IOException, JSONException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_9 + "/" + RELATIONSHIPS_WITH_CANDIDATES));
    String expectedResponse =
        fixture(FIXTURE_GET_RELATIONSHIPS_RESPONSE_NO_RELATIONSHIPS);
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void getRelationshipsByScreeningIdWithCandidates_noRelationsNoParticipants()
      throws IOException, JSONException {
    Response response = doGetCall(SCREENING_PATH + "/" + SCREENING_ID_10 + "/" + RELATIONSHIPS);
    assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
  }

  @Test
  public void getRelationshipsByScreeningIdWithCandidates_TwoRelationsNoCandidates()
      throws IOException, JSONException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_11 + "/" + RELATIONSHIPS_WITH_CANDIDATES));
    System.out.println(actualJson);
    String expectedResponse =
        fixture(FIXTURE_GET_RELATIONSHIPS_RESPONSE_TWO_RELATIONSHIPS_NO_CANDIDATES);
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }
}
