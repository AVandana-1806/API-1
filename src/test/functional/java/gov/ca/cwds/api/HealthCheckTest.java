package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS API Team
 *
 *         Health Checks to check Ferb API is up and running.
 *
 */
public class HealthCheckTest extends FunctionalTest {

  private String healthCheckPath;

  /**
   * 
   */
  @Before
  public void before() {
    healthCheckPath = getResourceUrlFor("/admin/healthcheck");
  }


  /**
   * 
   */
  @Test
  public void testSwaggerIsAvailable() {
    given().queryParam("token", token).when().get(healthCheckPath).then()
        .body("swagger.healthy", equalTo(true));
  }

  @Test
  public void tesAuthIsAvailable() {
    given().queryParam("token", token).when().get(healthCheckPath).then()
        .body("auth.healthy", equalTo(true));
  }

  /**
   * 
   */
  @Test
  public void testPostgresConnectionIsOK() {
    given().queryParam("token", token).when().get(healthCheckPath).then().body("ns.healthy",
        equalTo(true));
  }

  /**
   * 
   */
  @Test
  public void testCmsConnectionIsOK() {
    given().queryParam("token", token).when().get(healthCheckPath).then().body("cms.healthy",
        equalTo(true));
  }

  @Test
  public void testRsConnectionIsOK() {
    given().queryParam("token", token).when().get(healthCheckPath).then().body("rs.healthy",
        equalTo(true));
  }

  @Test
  public void testXsForNSConnectionIsOK() {
    given().queryParam("token", token).when().get(healthCheckPath).then().body("xa_ns.healthy",
        equalTo(true));
  }

  @Test
  public void testXaForCmsRsConnectionIsOK() {
    given().queryParam("token", token).when().get(healthCheckPath).then().body("xa_cms_rs.healthy",
        equalTo(true));
  }

  @Test
  public void testXaForCmsConnectionIsOK() {
    given().queryParam("token", token).when().get(healthCheckPath).then().body("xa_cms.healthy",
        equalTo(true));
  }

  @Test
  public void testLOVIsOK() {
    given().queryParam("token", token).when().get(healthCheckPath).then().body("lov_db.healthy",
        equalTo(true));
  }
  @Test
  public void testLOVCacheIsOK() {
    given().queryParam("token", token).when().get(healthCheckPath).then().body("intake_lov_code_cache.healthy",
        equalTo(true));
  }

  @Test
  public void testDeadLocksIsOK() {
    given().queryParam("token", token).when().get(healthCheckPath).then().body("deadlocks.healthy",
        equalTo(true));
  }

  @Test
  public void testSystemCodeCacheIsOK() {
    given().queryParam("token", token).when().get(healthCheckPath).then().body("system_code_cache.healthy",
        equalTo(true));
  }
}
