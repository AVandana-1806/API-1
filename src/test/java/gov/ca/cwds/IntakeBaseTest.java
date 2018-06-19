package gov.ca.cwds;

import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.registerHibernateStatisticsConsumer;
import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.unRegisterHibernateStatisticsConsumer;
import static gov.ca.cwds.inject.FerbHibernateBundle.CMS_BUNDLE_TAG;
import static gov.ca.cwds.inject.FerbHibernateBundle.NS_BUNDLE_TAG;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.rest.IntegratedResourceTestSuiteIT;
import gov.ca.cwds.test.support.DatabaseHelper;
import io.dropwizard.jackson.Jackson;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import liquibase.exception.LiquibaseException;
import org.apache.commons.io.IOUtils;
import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.test.support.BaseApiTest;
import gov.ca.cwds.test.support.BaseDropwizardApplication;

/**
 * CWDS API Team
 */
public abstract class IntakeBaseTest extends BaseApiTest<ApiConfiguration> {

  private Map<String, Statistics> hibernateStatisticsMap = new HashMap<>();

  @ClassRule
  public static final BaseDropwizardApplication<ApiConfiguration> application =
      IntegratedResourceTestSuiteIT.application;

  protected BaseDropwizardApplication<ApiConfiguration> getApplication() {
    return application;
  }

  protected ObjectMapper objectMapper = Jackson.newObjectMapper();

  @BeforeClass
  public static void beforeClass() throws LiquibaseException {
    ApiConfiguration configuration = application.getConfiguration();

    new gov.ca.cwds.test.support.DatabaseHelper(configuration.getNsDataSourceFactory().getUrl(),
        configuration.getNsDataSourceFactory().getUser(),
        configuration.getNsDataSourceFactory().getPassword())
        .runScript("liquibase/api/api_intake_ns_database_master.xml");

    new gov.ca.cwds.test.support.DatabaseHelper(configuration.getCmsDataSourceFactory().getUrl(),
        configuration.getCmsDataSourceFactory().getUser(),
        configuration.getCmsDataSourceFactory().getPassword())
        .runScript("liquibase/api/api_cwsint_database_master.xml");

    new DatabaseHelper(configuration.getRsDataSourceFactory().getUrl(),
        configuration.getRsDataSourceFactory().getUser(),
        configuration.getRsDataSourceFactory().getPassword())
        .runScript("liquibase/api/api_cwsrs_database_master.xml");
  }

  protected String doGetCall(String pathInfo) throws IOException {
    WebTarget target = clientTestRule.target(pathInfo);
    Response response = target.request(MediaType.APPLICATION_JSON).get();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  protected int doAuthorizedGetCallStatus(String tokenFilePath, String pathInfo)
      throws IOException {
    WebTarget target = clientTestRule.withSecurityToken(tokenFilePath).target(pathInfo);
    Response response = target.request(MediaType.APPLICATION_JSON).get();
    return response.getStatus();
  }

  protected String doPostCall(String pathInfo, String request) throws IOException {
    WebTarget target = clientTestRule.target(pathInfo);
    Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(request,
        MediaType.APPLICATION_JSON_TYPE));
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  protected String doPutCall(String pathInfo, String request) throws IOException {
    WebTarget target = clientTestRule.target(pathInfo);
    Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.entity(request,
        MediaType.APPLICATION_JSON_TYPE));
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  protected String doDeleteCall(String pathInfo) throws IOException {
    WebTarget target = clientTestRule.target(pathInfo);
    Response response = target.request().delete();
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
