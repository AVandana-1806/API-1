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

    @Test
    public void testGetRelationshipsByScreeningId() throws IOException, JSONException {
        String screeningId = "1";
        String response = fixture(FIXTURE_GET_RELATIONSHIPS_RESPONSE_PATH);
        String actualJson = getStringResponse(doGetCall(SCREENING_PATH + MOCKED_SCREENING_ID + RELATIONSHIPS));

        JSONAssert.assertEquals(response, actualJson, JSONCompareMode.NON_EXTENSIBLE);

    }
}
