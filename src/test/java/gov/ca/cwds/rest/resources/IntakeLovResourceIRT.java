package gov.ca.cwds.rest.resources;

import gov.ca.cwds.IntakeBaseTest;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_LOV;
import static io.dropwizard.testing.FixtureHelpers.fixture;

/**
 * CWDS API Team
 */
public class IntakeLovResourceIRT extends IntakeBaseTest {
  @Test
  public void testGet() throws Exception {
    String actualJson =
        getStringResponse(doGetCall(RESOURCE_INTAKE_LOV));
    String expectedResponse =
        fixture("fixtures/gov/ca/cwds/rest/resources/intake-lov-response.json");
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

}
