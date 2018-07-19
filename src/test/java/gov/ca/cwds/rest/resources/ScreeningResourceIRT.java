package gov.ca.cwds.rest.resources;

import gov.ca.cwds.IntakeBaseTest;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;

public class ScreeningResourceIRT extends IntakeBaseTest {

  public static final String FIXTURE_GET_RELATIONSHIPS_RESPONSE_PATH = "fixtures/gov/ca/cwds/rest/resources/screening-get-relationships-response.json";
  public static final String SCREENING_PATH = "screenings";
  public static final String MOCKED_SCREENING_ID = "11";
  public static final String RELATIONSHIPS = "relationships";

  public static final String FIXTURE_GET_LIST_RELATIONSHIPS_RESPONSE_ONLY_POSTGRES_PATH = "fixtures/gov/ca/cwds/rest/resources/relationships/relationships-by-screening-id-only-postgres.json";
  public static final String SCREENING_ID_1 = "1101";

  public static final String FIXTURE_GET_ONE_RELATIONSHIP_RESPONSE_ONLY_POSTGRES_PATH = "fixtures/gov/ca/cwds/rest/resources/relationships/relationship-by-screening-id-only-postgres.json";
  public static final String SCREENING_ID_2 = "1102";

  public static final String FIXTURE_GET_ONE_RELATIONSHIP_IN_POSTGRES_AND_ON_DB2_RESPONSE_ONLY_POSTGRES_PATH = "fixtures/gov/ca/cwds/rest/resources/relationships/relationship-by-screening-id-only-postgres.json";
  public static final String SCREENING_ID_3 = "1103";

  @Test
  public void getRelationshipsByScreeningId_twoRelationsExist() throws IOException, JSONException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_1 + "/" + RELATIONSHIPS));
    String expectedResponse =
        fixture(FIXTURE_GET_LIST_RELATIONSHIPS_RESPONSE_ONLY_POSTGRES_PATH);
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void getRelationshipsByScreeningId_oneRelationExist() throws IOException, JSONException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_2 + "/" + RELATIONSHIPS));
    String expectedResponse =
        fixture(FIXTURE_GET_ONE_RELATIONSHIP_RESPONSE_ONLY_POSTGRES_PATH);
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void getRelationshipsByScreeningId_oneRelationInPostgress_oneRelationInDB2()
      throws IOException {
    String actualJson = getStringResponse(
        doGetCall(SCREENING_PATH + "/" + SCREENING_ID_3 + "/" + RELATIONSHIPS));
    System.out.println(actualJson);
    String expectedResponse =
        fixture(FIXTURE_GET_ONE_RELATIONSHIP_RESPONSE_ONLY_POSTGRES_PATH);
  }
}
