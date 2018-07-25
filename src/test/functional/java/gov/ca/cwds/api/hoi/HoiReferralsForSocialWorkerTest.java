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
 * Functional Testing for hoi referrals end point with Social Worker Privilege Only(GVR_ENTC = 1084)
 * 
 * @author CWDS API Team
 */
public class HoiReferralsForSocialWorkerTest extends FunctionalTest {

  private static String HOI_REFERRAL_NO_CONDITIONS_RESPONSE =
      fixture("gov/ca/cwds/rest/api/fixtures/hoi-referral-no-conditions-response.json");

  String resourcePath;
  private FunctionalTestingBuilder functionalTestingBuilder;

  /**
   * 
   */
  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT);
    functionalTestingBuilder = new FunctionalTestingBuilder();
  }

  /**
   * @throws Exception - Exception
   * 
   */
  @Test
  public void testSuccessToAccessNoConditionClient() throws Exception {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", "CFOmFrm057");
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    Response response = functionalTestingBuilder.processGetRequest(resourcePath, queryParams);
    String actualJson = response.asString();
    JSONAssert.assertEquals(HOI_REFERRAL_NO_CONDITIONS_RESPONSE, actualJson,
        JSONCompareMode.NON_EXTENSIBLE);
  }

  /**
   * 
   */
  @Test
  public void failedToAccessSameCountySensitiveClient() {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", "B5mi8Qr00T");
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    functionalTestingBuilder.processGetRequest(resourcePath, queryParams).then()
        .body("isEmpty()", Matchers.is(true)).statusCode(200);
  }

  /**
   * 
   */
  @Test
  public void failedToAccessSameCountySealedClient() {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", "4kgIiDy00T");
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    functionalTestingBuilder.processGetRequest(resourcePath, queryParams).then()
        .body("isEmpty()", Matchers.is(true)).statusCode(200);
  }

  /**
   * 
   */
  @Test
  public void failedToAccessDifferentCountySensitiveClient() {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", "9PIxHucCON");
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    functionalTestingBuilder.processGetRequest(resourcePath, queryParams).then()
        .body("isEmpty()", Matchers.is(true)).statusCode(200);
  }

  /**
   * 
   */
  @Test
  public void failedToAccessDifferentCountySealedClient() {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientIds", "AIwcGUp0Nu");
    queryParams.put(functionalTestingBuilder.TOKEN, token);
    functionalTestingBuilder.processGetRequest(resourcePath, queryParams).then()
        .body("isEmpty()", Matchers.is(true)).statusCode(200);
  }

}
