package gov.ca.cwds.rest.resources.relationship;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import static gov.ca.cwds.rest.core.Api.SCREENING_RELATIONSHIPS;
import static gov.ca.cwds.rest.core.Api.SCREENING_RELATIONSHIPS_BATCH;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.core.type.TypeReference;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipBase;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import gov.ca.cwds.IntakeBaseTest;

public class ScreeningRelationshipResourceIRT extends IntakeBaseTest {

  public static final String JSON_REQUEST_FOR_SUCCESS_RELATIONSHIPS_BY_SCREENING_ID =
      "fixtures/gov/ca/cwds/rest/resources/relationships-by-screening-id.json";
  public static final String JSON_REQUEST_FOR_CREATE_BATCH_RELATIONSHIPS =
      "fixtures/gov/ca/cwds/rest/resources/relationships/relationship-post-batch-mock.json";
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

  @Test
  public void testCreateRelationships() throws IOException {
    String request = fixture(JSON_REQUEST_FOR_CREATE_BATCH_RELATIONSHIPS);
    Response response =
        doPostCall(SCREENING_RELATIONSHIPS_BATCH, request);
    assertEquals(HttpStatus.SC_CREATED, response.getStatus());
  }

  @Test
  public void createRelationships_createOneRelationship() throws IOException {
    ScreeningRelationshipBase[] relationshipBases = {
        getOneRelationshipForCreate("2033", "2034", 185, "Y")};
    validateCreateRelationships(relationshipBases);
  }

  @Test
  public void createRelationships_createListRelationship() throws IOException {
    ScreeningRelationshipBase[] relationshipBases = {
        getOneRelationshipForCreate("2033", "2034", 185, "Y"),
        getOneRelationshipForCreate("2034", "2033", 211, "U")};
    validateCreateRelationships(relationshipBases);
  }

  @Test
  public void testCreateRelationships_createTwo() throws IOException {
    ScreeningRelationshipBase[] relationshipBases = {
        getOneRelationshipForCreate("44", "43", 185, "Y"),
        getOneRelationshipForCreate("43", "44", 211, "U")};
    validateCreateRelationships(relationshipBases);
  }

  @Test
  public void testCreateRelationships_createNine() throws IOException {
    ScreeningRelationshipBase[] relationshipBases = {
        getOneRelationshipForCreate("755", "756", 185, "Y"),
        getOneRelationshipForCreate("756", "755", 185, "Y"),
        getOneRelationshipForCreate("852", "853", 185, "Y"),
        getOneRelationshipForCreate("853", "852", 185, "Y"),
        getOneRelationshipForCreate("854", "855", 185, "Y"),
        getOneRelationshipForCreate("855", "854", 185, "Y"),
        getOneRelationshipForCreate("855", "856", 185, "Y"),
        getOneRelationshipForCreate("856", "855", 185, "Y"),
        getOneRelationshipForCreate("856", "755", 211, "U")};
    validateCreateRelationships(relationshipBases);
  }

  private void validateCreateRelationships(ScreeningRelationshipBase[] relationshipBases)
      throws IOException {
    String requestJson = objectMapper.writeValueAsString(relationshipBases);

    Response response = doPostCall(
        SCREENING_RELATIONSHIPS_BATCH,
        requestJson);

    assertEquals(HttpStatus.SC_CREATED, response.getStatus());

    List<ScreeningRelationship> actualResponse = objectMapper
        .readValue((InputStream) response.getEntity(),
            new TypeReference<List<ScreeningRelationship>>() {
            });

    assertNotNull(actualResponse);
    assertEquals(relationshipBases.length, actualResponse.size());

    List<ScreeningRelationship> createdRelationships = new ArrayList<>();
    actualResponse.forEach(e -> {
      try {
        Response relationByIdResponse = doGetCall(
            SCREENING_RELATIONSHIPS + "/" + e.getId());
        createdRelationships.add(objectMapper
            .readValue((InputStream) relationByIdResponse.getEntity(),
                ScreeningRelationship.class));
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    });

    Arrays.asList(relationshipBases).forEach(relationshipBase -> {
      Optional<ScreeningRelationship> optional = createdRelationships.stream().filter(
          relationship -> relationship.getClientId().equals(relationshipBase.getClientId())
              && relationship.getRelativeId().equals(relationshipBase.getRelativeId())).findFirst();
      if (optional.isPresent()) {
        validateResponseForCreatedRelationships(relationshipBase, optional.get());
      }
    });
  }

  private void validateResponseForCreatedRelationships(
      ScreeningRelationshipBase relationshipBase, ScreeningRelationship actualResponse) {
    assertNotNull(relationshipBase);
    assertNotNull(actualResponse);

    assertEquals(relationshipBase.getLegacyId(),
        actualResponse.getLegacyId());
    assertEquals(relationshipBase.getEndDate(),
        actualResponse.getEndDate());
    assertEquals(relationshipBase.getStartDate(),
        actualResponse.getStartDate());
    assertEquals(relationshipBase.getSameHomeStatus(),
        actualResponse.getSameHomeStatus());
    assertEquals(relationshipBase.getClientId(),
        actualResponse.getClientId());
    assertEquals(relationshipBase.getRelativeId(),
        actualResponse.getRelativeId());
    assertEquals(relationshipBase.getRelationshipType(),
        actualResponse.getRelationshipType());
    assertNotNull(actualResponse.getId());
    assertNotEquals("", actualResponse.getId());

  }

  private ScreeningRelationshipBase getOneRelationshipForCreate(String clientId,
      String relatedClientId, int relationshipType, String sameHomeStatus) {
    ScreeningRelationshipBase screeningRelationship = new ScreeningRelationshipBase();
    screeningRelationship.setClientId(clientId);
    screeningRelationship.setRelativeId(relatedClientId);
    screeningRelationship.setRelationshipType(relationshipType);
    screeningRelationship.setAbsentParentIndicator(false);
    screeningRelationship.setSameHomeStatus(sameHomeStatus);
    return screeningRelationship;
  }
}