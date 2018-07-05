package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.inject.FerbHibernateBundle.CMS_BUNDLE_TAG;
import static gov.ca.cwds.inject.FerbHibernateBundle.NS_BUNDLE_TAG;
import static gov.ca.cwds.rest.core.Api.RESOURCE_CASE_HISTORY_OF_INVOLVEMENT;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import gov.ca.cwds.rest.api.domain.hoi.HOICase;

public class HoiCaseResourceIRT extends HOIBaseTest {

  @Test
  public void testGet() throws Exception {
    final List<HOICase> expectedHOICases = getExpectedInvolvementHistory().getCases();
    final String actualJson = doGet();
    final List<HOICase> actualHOICases =
        objectMapper.readValue(actualJson.getBytes(), new TypeReference<List<HOICase>>() {});
    assertEquals(expectedHOICases, actualHOICases);
    assertHOICasesAreSorted(new String[] {"Co8uaDi0DW", "IdQImWo0DW"}, actualHOICases);

    assertQueryExecutionCount(CMS_BUNDLE_TAG, 9);
    assertDbNotTouched(NS_BUNDLE_TAG);
  }

  private String doGet() throws Exception {
    WebTarget target = clientTestRule.target(RESOURCE_CASE_HISTORY_OF_INVOLVEMENT);
    Response response = target.queryParam("clientIds", "5DK5THO0DW", "SFpVhtC0DW").request()
        .accept(MediaType.APPLICATION_JSON).get();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

}
