package gov.ca.cwds;

import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.registerHibernateStatisticsConsumer;
import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.unRegisterHibernateStatisticsConsumer;
import static gov.ca.cwds.inject.FerbHibernateBundle.CMS_BUNDLE_TAG;
import static gov.ca.cwds.inject.FerbHibernateBundle.NS_BUNDLE_TAG;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.ApiApplicationTestSupport;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.test.support.BaseApiTest;
import gov.ca.cwds.test.support.BaseDropwizardApplication;
import io.dropwizard.jackson.Jackson;

/**
 * CWDS API Team
 */
public abstract class IntakeBaseTest extends BaseApiTest<ApiConfiguration> {

  private Map<String, Statistics> hibernateStatisticsMap = new HashMap<>();

  @ClassRule
  public static BaseDropwizardApplication<ApiConfiguration> application =
      ApiApplicationTestSupport.getApplication();

  @Override
  public BaseDropwizardApplication<ApiConfiguration> getApplication() {
    return ApiApplicationTestSupport.getApplication();
  }

  protected ObjectMapper objectMapper = Jackson.newObjectMapper();

  protected Response doGetCall(String pathInfo) throws IOException {
    WebTarget target = clientTestRule.target(pathInfo);
    return target.request(MediaType.APPLICATION_JSON).get();
  }

  protected int doAuthorizedGetCallStatus(String tokenFilePath, String pathInfo)
      throws IOException {
    WebTarget target = clientTestRule.withSecurityToken(tokenFilePath).target(pathInfo);
    Response response = target.request(MediaType.APPLICATION_JSON).get();
    return response.getStatus();
  }

  protected Response doPostCall(String pathInfo, String request) throws IOException {
    WebTarget target = clientTestRule.target(pathInfo);
    return target.request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
  }

  protected Response doPutCall(String pathInfo, String request) throws IOException {
    WebTarget target = clientTestRule.target(pathInfo);
    return target.request(MediaType.APPLICATION_JSON).put(Entity.entity(request,
        MediaType.APPLICATION_JSON_TYPE));
  }

  protected String doDeleteCall(String pathInfo) throws IOException {
    WebTarget target = clientTestRule.target(pathInfo);
    Response response = target.request().delete();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  protected String getStringResponse(Response response) throws IOException {
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  /*
   * methods for testing DB usage
   */

  @Before
  public void registerHibernateStatisticsConsumers() {
    registerHibernateStatisticsConsumer(CMS_BUNDLE_TAG,
        statistics -> hibernateStatisticsMap.put(CMS_BUNDLE_TAG, statistics));
    registerHibernateStatisticsConsumer(NS_BUNDLE_TAG,
        statistics -> hibernateStatisticsMap.put(NS_BUNDLE_TAG, statistics));
  }

  @After
  public void unRegisterHibernateStatisticsConsumers() {
    unRegisterHibernateStatisticsConsumer(CMS_BUNDLE_TAG);
    unRegisterHibernateStatisticsConsumer(NS_BUNDLE_TAG);
  }

  protected void assertQueryExecutionCount(String bundleTag, long maxCount) {
    assertNotNull(hibernateStatisticsMap.get(bundleTag));
    assertThat(hibernateStatisticsMap.get(bundleTag).getQueryExecutionCount(),
        is(lessThanOrEqualTo(maxCount)));
  }

  protected void assertDbNotTouched(String bundleTag) {
    assertNull(hibernateStatisticsMap.get(bundleTag));
  }
}
