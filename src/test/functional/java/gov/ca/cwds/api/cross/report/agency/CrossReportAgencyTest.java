package gov.ca.cwds.api.cross.report.agency;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.core.Api;

public class CrossReportAgencyTest extends FunctionalTest {
  String crossReportAgencyPath;
  private HttpRequestHandler httpRequestHandler;
  
  @Before
  public void setup() {
    crossReportAgencyPath = getResourceUrlFor("/" + Api.RESOURCE_GOVERNMENT_ORG);
    httpRequestHandler = new HttpRequestHandler();
    this.loginUserGroup(UserGroup.SOCIAL_WORKER);
    
  }
  
  @Test
  public void shouldReturn200WhenValidCounty() {
    
    String countyId = "1126";
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("clientId", countyId);
    queryParams.put(httpRequestHandler.TOKEN, token);
    
    httpRequestHandler.getRequest(crossReportAgencyPath, queryParams)
      .then()
      .assertThat()
      .statusCode(200);
    
  }
}
