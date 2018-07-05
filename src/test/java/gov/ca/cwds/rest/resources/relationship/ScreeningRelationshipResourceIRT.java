package gov.ca.cwds.rest.resources.relationship;

import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import static gov.ca.cwds.rest.core.Api.SCREENING_RELATIONSHIPS;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import gov.ca.cwds.IntakeBaseTest;

public class ScreeningRelationshipResourceIRT extends IntakeBaseTest {

  public static final String JSON_REQUEST_FOR_SUCCESS_RELATIONSHIPS_BY_SCREENING_ID =
      "fixtures/gov/ca/cwds/rest/resources/relationships-by-screening-id.json";
  public static final String JSON_REQUEST_FOR_SUCCESS_UPDATE =
      "fixtures/gov/ca/cwds/rest/resources/relationship-update-request.json";
  public static final String JSON_REQUEST_FOR_FAILURE_UPDATE =
      "fixtures/gov/ca/cwds/rest/resources/relationship-failure-update-request.json";
  public static final String JSON_BAD_REQUEST_FOR_UPDATE =
      "fixtures/gov/ca/cwds/rest/resources/relationship-bad-update-request.json";
  public static final String JSON_RESPONSE_AFTER_SUCCESS_UPDATE =
      "fixtures/gov/ca/cwds/rest/resources/relationship-update-response.json";
  public static final String ID_EXISTING_RELATIONSHIP_FOR_UPDATE = "2";
  public static final String ID_NOT_EXISTING_RELATIONSHIP_FOR_UPDATE = "-1";

  @Test
  public void testGet() throws Exception {
    String actualJson = getStringResponse(doGetCall(SCREENING_RELATIONSHIPS + "/1"));
    String expectedResponse =
        fixture("fixtures/gov/ca/cwds/rest/resources/relationship_get_response.json");
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testSuccessUpdate() throws Exception {
    String request = fixture(JSON_REQUEST_FOR_SUCCESS_UPDATE);
    String actualJson = getStringResponse(
        doPutCall(SCREENING_RELATIONSHIPS + "/" + ID_EXISTING_RELATIONSHIP_FOR_UPDATE, request));
    String expectedResponse = fixture(JSON_RESPONSE_AFTER_SUCCESS_UPDATE);
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testRelationshipDoesNotExist() throws Exception {
    String request = fixture(JSON_REQUEST_FOR_FAILURE_UPDATE);
    Response response =
        doPutCall(SCREENING_RELATIONSHIPS + "/" + ID_NOT_EXISTING_RELATIONSHIP_FOR_UPDATE, request);
    assertEquals(HttpStatus.SC_OK, response.getStatus());
  }

  @Test
  public void testRelationshipBadRequest() throws Exception {
    String request = fixture(JSON_BAD_REQUEST_FOR_UPDATE);
    Response response =
        doPutCall(SCREENING_RELATIONSHIPS + "/" + ID_EXISTING_RELATIONSHIP_FOR_UPDATE, request);
    assertEquals(HttpStatus.SC_UNPROCESSABLE_ENTITY, response.getStatus());
  }

  @Test
  @Ignore // DRS: screening id 22 not found.
  public void testGetRelationshipsByScreeningId() throws Exception {
    String expectedResponse = fixture(JSON_REQUEST_FOR_SUCCESS_RELATIONSHIPS_BY_SCREENING_ID);
    String actualJson = getStringResponse(doGetCall(RESOURCE_SCREENINGS + "/22/relationships"));
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

}
