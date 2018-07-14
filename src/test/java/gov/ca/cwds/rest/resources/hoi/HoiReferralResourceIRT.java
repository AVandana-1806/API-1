package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT;

import gov.ca.cwds.rest.core.Api;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.core.type.TypeReference;

import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.services.hoi.HOIReferralService;

public class HoiReferralResourceIRT extends HOIBaseTest {

  @Test
  public void testGet() throws Exception {
    final List<HOIReferral> expectedHOIReferrals = getExpectedInvolvementHistory().getReferrals();
    final String actualJson = doGet();
    final List<HOIReferral> actualHOIReferrals =
        objectMapper.readValue(actualJson.getBytes(), new TypeReference<List<HOIReferral>>() {});

    // convert lists of Referrals to JSON-s for comparison because otherwise it could fail because
    // of random order of Allegations
    JSONAssert.assertEquals(objectMapper.writeValueAsString(expectedHOIReferrals),
        objectMapper.writeValueAsString(actualHOIReferrals), JSONCompareMode.NON_EXTENSIBLE);

    assertHOIReferralsAreSorted(new String[] {"MYsSPHW0DW", "9OQhOAE0DW"}, actualHOIReferrals);

    assertQueryExecutionCount(Api.DATASOURCE_CMS, 6);
    assertDatasourceNotTouched(Api.DATASOURCE_NS);
  }

  private String doGet() throws Exception {
    WebTarget target = clientTestRule.target(RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT);
    Response response = target.queryParam("clientIds", "5DK5THO0DW", "SFpVhtC0DW").request()
        .accept(MediaType.APPLICATION_JSON).get();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  @Test(expected = NotImplementedException.class)
  public void handleRequestNotImplemented() {
    new HOIReferralService(null, null, null, null, null).handleRequest(new HOIReferral());
  }

}
