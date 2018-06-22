package gov.ca.cwds.rest.resources.contact;

import gov.ca.cwds.IntakeBaseTest;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_CONTACTS;
import static io.dropwizard.testing.FixtureHelpers.fixture;

public class ContactResourceIRT extends IntakeBaseTest {

  @Test
  public void testPost() throws Exception {
    String request =
            fixture("fixtures/gov/ca/cwds/rest/resources/contact-intake-api-post-request.json");
    String actualJson = doPostCall(RESOURCE_INTAKE_CONTACTS, request);
    String expectedResponse =
            fixture("fixtures/gov/ca/cwds/rest/resources/contact-intake-api-post-response.json");

    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

}
