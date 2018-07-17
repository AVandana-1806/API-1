package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.IntakeBaseTestConstants.USER_SOCIAL_WORKER_ONLY;
import static gov.ca.cwds.rest.core.Api.RESOURCE_CASE_HISTORY_OF_INVOLVEMENT;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.core.Api;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class HoiCaseResourceIRT extends HOIBaseTest {

  @Test
  public void testGet() throws Exception {
    final List<HOICase> expectedHOICases = getExpectedInvolvementHistory().getCases();
    final String actualJson = doGet();
    final List<HOICase> actualHOICases =
        objectMapper.readValue(actualJson.getBytes(), new TypeReference<List<HOICase>>() {});
    assertEquals(expectedHOICases, actualHOICases);
    assertHOICasesAreSorted(new String[]{"Co8uaDi0DW", "IdQImWo0DW"}, actualHOICases);

    assertQueryExecutionCount(Api.DATASOURCE_CMS, 8);
    assertDatasourceNotTouched(Api.DATASOURCE_CMS_REP, Api.DATASOURCE_NS);
  }

  private String doGet() throws Exception {
    WebTarget target = clientTestRule.withSecurityToken(USER_SOCIAL_WORKER_ONLY)
        .target(RESOURCE_CASE_HISTORY_OF_INVOLVEMENT);
    // clients with ID-s "1S3k0iv00T", "SZdBGYk75C" are sensitive and will be filtered out
    Response response = target
        .queryParam("clientIds", "5DK5THO0DW", "SFpVhtC0DW", "1S3k0iv00T", "SZdBGYk75C").request()
        .accept(MediaType.APPLICATION_JSON).get();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

}
