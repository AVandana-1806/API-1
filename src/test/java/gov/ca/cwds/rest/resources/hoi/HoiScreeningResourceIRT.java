package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.inject.FerbHibernateBundle.CMS_BUNDLE_TAG;
import static gov.ca.cwds.inject.FerbHibernateBundle.NS_BUNDLE_TAG;
import static gov.ca.cwds.rest.core.Api.RESOURCE_HOI_SCREENINGS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.services.hoi.HOIScreeningService;

public class HoiScreeningResourceIRT extends HOIBaseTest {

  @Test
  public void testGet() throws Exception {
    final List<HOIScreening> expectedHOIScreenings =
        getExpectedHOIScreenings(getExpectedInvolvementHistory());
    final String actualJson = doGet();
    final List<HOIScreening> actualHOIScreenings =
        objectMapper.readValue(actualJson.getBytes(), new TypeReference<List<HOIScreening>>() {});
    assertEquals(expectedHOIScreenings, actualHOIScreenings);
    assertHOIScreeningsAreSorted(new String[] {"750", "885", "862", "714"}, actualHOIScreenings);
    // assert HOI Screenings are sorted with Null StartDate on top
    assertNull(actualHOIScreenings.get(0).getStartDate());

    assertQueryExecutionCount(CMS_BUNDLE_TAG, 1);
    assertQueryExecutionCount(NS_BUNDLE_TAG, 4);
  }

  private String doGet() throws Exception {
    WebTarget target = clientTestRule.target(RESOURCE_HOI_SCREENINGS);
    Response response = target.queryParam("clientIds", "5DK5THO0DW", "SFpVhtC0DW").request()
        .accept(MediaType.APPLICATION_JSON).get();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  @Test(expected = NotImplementedException.class)
  public void handleRequestNotImplemented() {
    new HOIScreeningService().handleRequest(new HOIScreening());
  }

}
