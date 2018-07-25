package gov.ca.cwds;

import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.registerHibernateStatisticsConsumer;
import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.unRegisterHibernateStatisticsConsumer;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.HibernateStatisticsConsumer;
import gov.ca.cwds.rest.core.Api;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.hibernate.stat.Statistics;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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

  private static Map<String, Statistics> hibernateStatisticsMap = new ConcurrentHashMap<>();

  protected ObjectMapper objectMapper = Jackson.newObjectMapper();

  @ClassRule
  public static BaseDropwizardApplication<ApiConfiguration> application =
      ApiApplicationTestSupport.getApplication();

  @Override
  public BaseDropwizardApplication<ApiConfiguration> getApplication() {
    return ApiApplicationTestSupport.getApplication();
  }

  protected Response doGetCall(String pathInfo) throws IOException {
    final WebTarget target = clientTestRule.target(pathInfo);
    return target.request(MediaType.APPLICATION_JSON).get();
  }

  protected int doAuthorizedGetCallStatus(String tokenFilePath, String pathInfo)
      throws IOException {
    final WebTarget target = clientTestRule.withSecurityToken(tokenFilePath).target(pathInfo);
    final Response response = target.request(MediaType.APPLICATION_JSON).get();
    return response.getStatus();
  }

  protected Response doPostCall(String pathInfo, String request) throws IOException {
    final WebTarget target = clientTestRule.target(pathInfo);
    return target.request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
  }

  protected Response doPutCall(String pathInfo, String request) throws IOException {
    final WebTarget target = clientTestRule.target(pathInfo);
    return target.request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
  }

  protected String doDeleteCall(String pathInfo) throws IOException {
    final WebTarget target = clientTestRule.target(pathInfo);
    final Response response = target.request().delete();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  protected String getStringResponse(Response response) throws IOException {
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  /*
   * methods for testing DB usage
   */

  private static HibernateStatisticsConsumer createTestHibernateStatisticsConsumer(
      final String bundleTag) {
    return new HibernateStatisticsConsumer() {
      @Override
      public void prepare(Statistics hibernateStatistics) {
        hibernateStatistics.clear();
      }

      @Override
      public void consume(Statistics hibernateStatistics) {
        hibernateStatisticsMap.put(bundleTag, hibernateStatistics);
      }
    };
  }

  @BeforeClass
  public static void registerHibernateStatisticsConsumers() {
    registerHibernateStatisticsConsumer(Api.DATASOURCE_CMS,
        createTestHibernateStatisticsConsumer(Api.DATASOURCE_CMS));
    registerHibernateStatisticsConsumer(Api.DATASOURCE_CMS_REP,
        createTestHibernateStatisticsConsumer(Api.DATASOURCE_CMS_REP));
    registerHibernateStatisticsConsumer(Api.DATASOURCE_NS,
        createTestHibernateStatisticsConsumer(Api.DATASOURCE_NS));
  }

  @AfterClass
  public static void unRegisterHibernateStatisticsConsumers() {
    unRegisterHibernateStatisticsConsumer(Api.DATASOURCE_CMS);
    unRegisterHibernateStatisticsConsumer(Api.DATASOURCE_CMS_REP);
    unRegisterHibernateStatisticsConsumer(Api.DATASOURCE_NS);
  }

  protected void assertQueryExecutionCount(String bundleTag, long maxCount) {
    assertNotNull(hibernateStatisticsMap.get(bundleTag));
    assertTrue(hibernateStatisticsMap.get(bundleTag).getQueryExecutionCount() <= maxCount);
  }

  protected void assertDatasourceNotTouched(String ... bundleTags) {
    for (String bundleTag : bundleTags) {
      assertNull(hibernateStatisticsMap.get(bundleTag));
    }
  }

}
