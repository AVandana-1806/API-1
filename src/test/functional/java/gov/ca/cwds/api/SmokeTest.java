package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;

public class SmokeTest extends FunctionalTest {

  private String healthCheckPath;

  @Before
  public void before() {
    healthCheckPath = getResourceUrlFor("/admin/healthcheck");
  }

  @Test
  public void testPerryIsAvailible() {
    given().queryParam("token", token).when().get(healthCheckPath).then()
        .body("auth_status.healthy", equalTo(true));
  }

  @Test
  public void testDb2IsAvailible() {
    given().queryParam("token", token).when().get(healthCheckPath).then().body("db2_status.healthy",
        equalTo(true));
  }

  @Test
  public void testSwaggerIsAvailible() {
    given().queryParam("token", token).when().get(healthCheckPath).then()
        .body("swagger_status.healthy", equalTo(true));
  }

  @Test
  public void testPostgresConnectionIsOK() {
    given().queryParam("token", token).when().get(healthCheckPath).then().body("ns.healthy",
        equalTo(true));
  }

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

}
