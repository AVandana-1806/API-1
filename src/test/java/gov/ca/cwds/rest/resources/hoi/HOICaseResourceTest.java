package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CASE_HISTORY_OF_INVOLVEMENT;
import static io.dropwizard.testing.FixtureHelpers.fixture;

import gov.ca.cwds.IntakeBaseTest;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author CWDS API Team
 *
 */
public class HOICaseResourceTest extends IntakeBaseTest {
  private static final String USER_SOCIAL_WORKER_ONLY = "fixtures/gov/ca/cwds/rest/resources/hoi/user-social-worker-only.json";
  private static final String USER_COUNTY_SENSITIVE = "fixtures/gov/ca/cwds/rest/resources/hoi/user-county-sensitive.json";
  private static final String USER_COUNTY_SEALED = "fixtures/gov/ca/cwds/rest/resources/hoi/user-county-sealed.json";
  private static final String USER_STATE_SENSITIVE = "fixtures/gov/ca/cwds/rest/resources/hoi/user-state-sensetive.json";
  private static final String USER_STATE_SEALED = "fixtures/gov/ca/cwds/rest/resources/hoi/user-state-sealed.json";

  private static final String CLIENT_NO_CONDITIONS_ID = "AhGPhcm0T1";
  private static final String CLIENT_NO_CONDITIONS_HOI_RESPONSE = "fixtures/gov/ca/cwds/rest/resources/hoi/hoi-case-client-no-conditions-response.json";

  private static final String CLIENT_SAME_COUNTY_SENSITIVE_ID_1 = "1S3k0iv00T";
  private static final String CLIENT_SAME_COUNTY_SENSITIVE_ID_2 = "PQ3s1OD01t";
  private static final String CLIENT_SAME_COUNTY_SENSITIVE_ID_3 = "AbA4BJy0Aq";
  private static final String CLIENT_SAME_COUNTY_SENSITIVE_HOI_RESPONSE_1 = "fixtures/gov/ca/cwds/rest/resources/hoi/hoi-case-client-same-county-sensitive-response-1.json";
  private static final String CLIENT_SAME_COUNTY_SENSITIVE_HOI_RESPONSE_3 = "fixtures/gov/ca/cwds/rest/resources/hoi/hoi-case-client-same-county-sensitive-response-3.json";

  private static final String CLIENT_SAME_COUNTY_SEALED_ID_1 = "4kgIiDy00T";
  private static final String CLIENT_SAME_COUNTY_SEALED_ID_2 = "Ba29OOP75A";
  private static final String CLIENT_SAME_COUNTY_SEALED_ID_3 = "Abxl9D005Y";
  private static final String CLIENT_SAME_COUNTY_SEALED_HOI_RESPONSE_2 = "fixtures/gov/ca/cwds/rest/resources/hoi/hoi-case-client-same-county-sealed-response-2.json";
  private static final String CLIENT_SAME_COUNTY_SEALED_HOI_RESPONSE_3 = "fixtures/gov/ca/cwds/rest/resources/hoi/hoi-case-client-same-county-sealed-response-3.json";

  private static final String CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID = "8ROySNV00T";

  private static final String CLIENT_DIFFERENT_COUNTY_SEALED_ID = "4jCKVgx0GE";

  private static final String CLIENT_NO_COUNTY_SENSITIVE_ID = "F187hFj00E";
  private static final String CLIENT_NO_COUNTY_SENSITIVE_HOI_RESPONSE = "fixtures/gov/ca/cwds/rest/resources/hoi/hoi-case-client-no-county-sensitive-response.json";

  private static final String CLIENT_NO_COUNTY_SEALED_ID = "SIfUah90GD";
  private static final String CLIENT_NO_COUNTY_SEALED_HOI_RESPONSE = "fixtures/gov/ca/cwds/rest/resources/hoi/hoi-case-client-no-county-sealed-response.json";

  @Test
  public void testHandleFindNonExistingClientId() throws Exception {
    assertHandleFind(USER_SOCIAL_WORKER_ONLY, "-1", "[]");
  }

  @Test
  public void testHandleFindForUserSocialWorkerOnlyAndClientNoCondition() throws Exception {
    assertHandleFind(USER_SOCIAL_WORKER_ONLY, CLIENT_NO_CONDITIONS_ID, fixture(CLIENT_NO_CONDITIONS_HOI_RESPONSE));
  }

  //social worker only
  @Test
  public void testHandleFindForUserSocialWorkerOnlyAndClientSameCountySensitive() throws Exception {
    assertNonAuthorizedHandleFind(USER_SOCIAL_WORKER_ONLY, CLIENT_SAME_COUNTY_SENSITIVE_ID_1);
  }

  @Test
  public void testHandleFindForUserSocialWorkerOnlyAndClientSameCountySealed() throws Exception {
    assertNonAuthorizedHandleFind(USER_SOCIAL_WORKER_ONLY, CLIENT_SAME_COUNTY_SEALED_ID_1);
  }

  @Test
  public void testHandleFindForUserSocialWorkerOnlyAndClientDifferentCountySensistive() throws Exception {
    assertNonAuthorizedHandleFind(USER_SOCIAL_WORKER_ONLY, CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID);
  }

  @Test
  public void testHandleFindForUserSocialWorkerOnlyAndClientDifferentCountySealed() throws Exception {
    assertNonAuthorizedHandleFind(USER_SOCIAL_WORKER_ONLY, CLIENT_DIFFERENT_COUNTY_SEALED_ID);
  }

  @Test
  public void testHandleFindForUserSocialWorkerOnlyAndClientNoCountySensitive() throws Exception {
    assertNonAuthorizedHandleFind(USER_SOCIAL_WORKER_ONLY, CLIENT_NO_COUNTY_SENSITIVE_ID);
  }

  @Test
  public void testHandleFindForUserSocialWorkerOnlyAndClientNoCountySealed() throws Exception {
    assertNonAuthorizedHandleFind(USER_SOCIAL_WORKER_ONLY, CLIENT_NO_COUNTY_SEALED_ID);
  }


  //county sensitive
  @Test
  public void testHandleFindForUserCountySensitiveAndClientNoCondition() throws Exception {
    assertHandleFind(USER_COUNTY_SENSITIVE, CLIENT_NO_CONDITIONS_ID, fixture(CLIENT_NO_CONDITIONS_HOI_RESPONSE));
  }

  @Test
  public void testHandleFindForUserCountySensitiveAndClientSameCountySensitive() throws Exception {
    assertHandleFind(USER_COUNTY_SENSITIVE, CLIENT_SAME_COUNTY_SENSITIVE_ID_1, fixture(CLIENT_SAME_COUNTY_SENSITIVE_HOI_RESPONSE_1));
  }

  @Test
  public void testHandleFindForUserCountySensitiveAndClientSameCountySealed() throws Exception {
    assertNonAuthorizedHandleFind(USER_COUNTY_SENSITIVE, CLIENT_SAME_COUNTY_SEALED_ID_1);
  }

  @Test
  public void testHandleFindForUserCountySensitiveAndClientDifferentCountySensitive() throws Exception {
    assertNonAuthorizedHandleFind(USER_COUNTY_SENSITIVE, CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID);
  }

  @Test
  public void testHandleFindForUserCountySensitiveAndClientDifferentCountySealed() throws Exception {
    assertNonAuthorizedHandleFind(USER_COUNTY_SENSITIVE, CLIENT_DIFFERENT_COUNTY_SEALED_ID);
  }

  @Test
  public void testHandleFindForUserCountySensitiveAndClientNoCountySensitive() throws Exception {
    assertHandleFind(USER_COUNTY_SENSITIVE, CLIENT_NO_COUNTY_SENSITIVE_ID, fixture(CLIENT_NO_COUNTY_SENSITIVE_HOI_RESPONSE));
  }

  @Test
  public void testHandleFindForUserCountySensitiveAndClientNoCountySealed() throws Exception {
    assertNonAuthorizedHandleFind(USER_COUNTY_SENSITIVE, CLIENT_NO_COUNTY_SEALED_ID);
  }


  //county sealed
  @Test
  public void testHandleFindForUserCountySealedAndAndClientNoCondition() throws Exception {
    assertHandleFind(USER_COUNTY_SEALED, CLIENT_NO_CONDITIONS_ID, fixture(CLIENT_NO_CONDITIONS_HOI_RESPONSE));
  }

  @Test
  public void testHandleFindForUserCountySealedAndClientSameCountySensitive() throws Exception {
    assertNonAuthorizedHandleFind(USER_COUNTY_SEALED, CLIENT_SAME_COUNTY_SENSITIVE_ID_2);
  }

  @Test
  public void testHandleFindForUserCountySealedAndClientSameCountySealed() throws Exception {
    assertHandleFind(USER_COUNTY_SEALED, CLIENT_SAME_COUNTY_SEALED_ID_2, fixture(CLIENT_SAME_COUNTY_SEALED_HOI_RESPONSE_2));
  }

  @Test
  public void testHandleFindForUserCountySealedAndClientDifferentCountySensitive() throws Exception {
    assertNonAuthorizedHandleFind(USER_COUNTY_SEALED, CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID);
  }

  @Test
  public void testHandleFindForUserCountySealedAndClientDifferentCountySealed() throws Exception {
    assertNonAuthorizedHandleFind(USER_COUNTY_SEALED, CLIENT_DIFFERENT_COUNTY_SEALED_ID);
  }

  @Test
  public void testHandleFindForUserCountySealedAndClientNoCountySensitive() throws Exception {
    assertNonAuthorizedHandleFind(USER_COUNTY_SEALED, CLIENT_NO_COUNTY_SENSITIVE_ID);
  }

  @Test
  public void testHandleFindForUserCountySealedAndClientNoCountySealed() throws Exception {
    assertHandleFind(USER_COUNTY_SEALED, CLIENT_NO_COUNTY_SEALED_ID, fixture(CLIENT_NO_COUNTY_SEALED_HOI_RESPONSE));
  }


  //state sensitive
  @Test
  public void testHandleFindForUserStateSensitiveAndClientNoCondition() throws Exception {
    assertHandleFind(USER_STATE_SENSITIVE, CLIENT_NO_CONDITIONS_ID, fixture(CLIENT_NO_CONDITIONS_HOI_RESPONSE));
  }

  @Test
  public void testHandleFindForUserStateSensitiveAndClientSameCountySensitive() throws Exception {
    assertHandleFind(USER_STATE_SENSITIVE, CLIENT_SAME_COUNTY_SENSITIVE_ID_3, fixture(CLIENT_SAME_COUNTY_SENSITIVE_HOI_RESPONSE_3));
  }

  @Test
  public void testHandleFindForUserStateSensitiveAndClientSameCountySealed() throws Exception {
    assertNonAuthorizedHandleFind(USER_STATE_SENSITIVE, CLIENT_SAME_COUNTY_SEALED_ID_3);
  }

  @Test
  public void testHandleFindForUserStateSensitiveAndClientDifferentCountySensitive() throws Exception {
    assertNonAuthorizedHandleFind(USER_STATE_SENSITIVE, CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID);
  }

  @Test
  public void testHandleFindForUserStateSensitiveAndClientDifferentCountySealed() throws Exception {
    assertNonAuthorizedHandleFind(USER_STATE_SENSITIVE, CLIENT_DIFFERENT_COUNTY_SEALED_ID);
  }

  @Test
  public void testHandleFindForUserStateSensitiveAndClientNoCountySensitive() throws Exception {
    assertNonAuthorizedHandleFind(USER_STATE_SENSITIVE, CLIENT_NO_COUNTY_SENSITIVE_ID);
  }

  @Test
  public void testHandleFindForUserStateSensitiveAndClientNoCountySealed() throws Exception {
    assertNonAuthorizedHandleFind(USER_STATE_SENSITIVE, CLIENT_NO_COUNTY_SEALED_ID);
  }


  //state sealed
  @Test
  public void testHandleFindForUserStateSealedAndAndClientNoCondition() throws Exception {
    assertHandleFind(USER_STATE_SEALED, CLIENT_NO_CONDITIONS_ID, fixture(CLIENT_NO_CONDITIONS_HOI_RESPONSE));
  }

  @Test
  public void testHandleFindForUserStateSealedAndClientSameCountySensitive() throws Exception {
    assertNonAuthorizedHandleFind(USER_STATE_SEALED, CLIENT_SAME_COUNTY_SENSITIVE_ID_3);
  }

  @Test
  public void testHandleFindForUserStateSealedAndClientSameCountySealed() throws Exception {
    assertHandleFind(USER_STATE_SEALED, CLIENT_SAME_COUNTY_SEALED_ID_3, fixture(CLIENT_SAME_COUNTY_SEALED_HOI_RESPONSE_3));
  }

  @Test
  public void testHandleFindForUserStateSealedAndClientDifferentCountySensitive() throws Exception {
    assertNonAuthorizedHandleFind(USER_STATE_SEALED, CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID);
  }

  @Test
  public void testHandleFindForUserStateSealedAndClientDifferentCountySealed() throws Exception {
    assertNonAuthorizedHandleFind(USER_STATE_SEALED, CLIENT_DIFFERENT_COUNTY_SEALED_ID);
  }

  @Test
  public void testHandleFindForUserStateSealedAndClientNoCountySensitive() throws Exception {
    assertNonAuthorizedHandleFind(USER_STATE_SEALED, CLIENT_NO_COUNTY_SENSITIVE_ID);
  }

  @Test
  public void testHandleFindForUserStateSealedAndClientNoCountySealed() throws Exception {
    assertHandleFind(USER_STATE_SEALED, CLIENT_NO_COUNTY_SEALED_ID, fixture(CLIENT_NO_COUNTY_SEALED_HOI_RESPONSE));
  }


  private void assertHandleFind(String userFilePath, String clientIds, String expectedResponse) throws java.io.IOException, JSONException {
    String actualJson = doHandleFindCall(userFilePath, clientIds);

    JSONAssert.assertEquals(expectedResponse, actualJson, true);
  }

  private void assertNonAuthorizedHandleFind(String userFilePath, String clientIds) throws IOException {
    String actualJson = doHandleFindCall(userFilePath, clientIds);

    Assert.assertTrue(actualJson.contains("org.apache.shiro.authz.UnauthorizedException"));
  }

  private String doHandleFindCall(String userFilePath, String clientIds) throws java.io.IOException {
    WebTarget target = clientTestRule.withSecurityToken(userFilePath)
        .target(RESOURCE_CASE_HISTORY_OF_INVOLVEMENT);

    HOIRequest request = new HOIRequest();
    request.setClientIds(Stream.of(clientIds).collect(Collectors.toSet()));

    Invocation.Builder invocation = target.request(MediaType.APPLICATION_JSON_TYPE);
    Response response = invocation.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
    return IOUtils.toString((InputStream) response.getEntity(), "UTF-8");
  }
}
