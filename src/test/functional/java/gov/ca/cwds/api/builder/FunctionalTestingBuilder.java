package gov.ca.cwds.api.builder;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * Functional Test Builder to used handle request for test, make it more cleaner and easier to build
 * the test classes.
 * 
 * @author CWDS API Team
 *
 */
public class FunctionalTestingBuilder {

  /**
   * Token
   */
  public final String TOKEN = "token";

  /**
   * Method to process the POST request and return the appropriate body or Json response.
   * 
   * @param object - object
   * @param resourcePath - resourcePath
   * @param token - token
   * @return the response
   */
  public Response processPostRequest(Object object, String resourcePath, String token) {
    return given().queryParam(TOKEN, token).contentType(ContentType.JSON).accept(ContentType.JSON)
        .body(object).when().post(resourcePath).then().extract().response();
  }

  /**
   * Method to process the GET request with one or multiple query parameters
   * 
   * @param resourcePath - resourcePath
   * @param parametersMap - parametersMap
   * @param token - token
   * @return the response
   */
  public Response processGetRequest(String resourcePath, Map<String, Object> parametersMap) {
    return given().queryParams(parametersMap).get(resourcePath).then().contentType(ContentType.JSON)
        .extract().response();
  }

  /**
   * Method to process the POST request with one or multiple path parameters
   * 
   * @param object - object
   * @param resourcePath - resourcePath
   * @param parameterNameValuePairs - parameterNameValuePairs
   * @param token - token
   * @return the post response
   */
  public Response processPostRequestWithPathParameters(Object object, String resourcePath,
      Map<String, Object> parameterNameValuePairs, String token) {
    return given().queryParam(TOKEN, token).contentType(ContentType.JSON)
        .pathParams(parameterNameValuePairs).accept(ContentType.JSON).body(object).when()
        .post(resourcePath).then().extract().response();
  }

  /**
   * Method to process the DELETE request with one or multiple path parameters
   * 
   * @param object - object
   * @param resourcePath - resourcePath
   * @param parameterNameValuePairs - parameterNameValuePairs
   * @param token - token
   * @return the post response
   */
  public Response processDeleteRequestWithPathParameters(Object object, String resourcePath,
      Map<String, Object> parameterNameValuePairs, String token) {
    return given().queryParam(TOKEN, token).contentType(ContentType.JSON)
        .pathParams(parameterNameValuePairs).accept(ContentType.JSON).body(object).when()
        .delete(resourcePath).then().extract().response();
  }

  /**
   * Method to process the PUT request with one or multiple path parameters
   * 
   * @param object - object
   * @param resourcePath - resourcePath
   * @param parameterNameValuePairs - parameterNameValuePairs
   * @param token - token
   * @return the post response
   */
  public Response processPutRequestWithPathParameters(Object object, String resourcePath,
      Map<String, Object> parameterNameValuePairs, String token) {
    return given().queryParam(TOKEN, token).contentType(ContentType.JSON)
        .pathParams(parameterNameValuePairs).accept(ContentType.JSON).body(object).when()
        .put(resourcePath).then().extract().response();
  }

}
