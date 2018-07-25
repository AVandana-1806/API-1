package gov.ca.cwds.api.hoi;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.FunctionalTestingBuilder;
import gov.ca.cwds.rest.core.Api;
import io.restassured.response.Response;

/**
 * @author CWDS API Team
 *
 */
public class ClientsHistoryOfInvolvementTest extends FunctionalTest {

  private static String CLIENT_HOI_NO_CONDITION_RESPONSE =
      fixture("gov/ca/cwds/rest/api/fixtures/client-hoi-no-conditions-response.json");

  String resourcePath;
  private FunctionalTestingBuilder functionalTestingBuilder;

  /**
   * 
   */
  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_CLIENT + "/history_of_involvements");
    functionalTestingBuilder = new FunctionalTestingBuilder();
  }

  /**
   * @throws Exception - Exception
   * 
   */
  @Test
  public void testSuccessAccessToNoConditionClient() throws Exception {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", "K0rqEZ100R");
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    Response response = functionalTestingBuilder.processGetRequest(resourcePath, queryParams);
    String actualJson = response.asString();
    JSONAssert.assertEquals(CLIENT_HOI_NO_CONDITION_RESPONSE, actualJson,
        JSONCompareMode.NON_EXTENSIBLE);
  }

  /**
   * 
   */
  @Test
  public void testFailToAccessToSameCountySensitiveClient() {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", "B5mi8Qr00T");
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    functionalTestingBuilder.processGetRequest(resourcePath, queryParams).then()
        .body("cases[]", Matchers.empty()).body("referrals[]", Matchers.empty()).statusCode(200);
  }

  /**
   * 
   */
  @Test
  public void testFailToAccessToDifferentCountySensitiveClient() {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", "9PIxHucCON");
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    functionalTestingBuilder.processGetRequest(resourcePath, queryParams).then()
        .body("cases[]", Matchers.empty()).body("referrals[]", Matchers.empty()).statusCode(200);
  }

  /**
   * 
   */
  @Test
  public void testFailToAccessToSameCountySealedClient() {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", "4kgIiDy00T");
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    functionalTestingBuilder.processGetRequest(resourcePath, queryParams).then()
        .body("cases[]", Matchers.empty()).body("referrals[]", Matchers.empty()).statusCode(200);
  }

  /**
   * 
   */
  @Test
  public void testFailToAccessToDifferentCountySealedClient() {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", "AIwcGUp0Nu");
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    functionalTestingBuilder.processGetRequest(resourcePath, queryParams).then()
        .body("cases[]", Matchers.empty()).body("referrals[]", Matchers.empty()).statusCode(200);
  }

}
