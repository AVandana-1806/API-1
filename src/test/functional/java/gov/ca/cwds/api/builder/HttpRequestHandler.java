package gov.ca.cwds.api.builder;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * Http Request Handler to used handle request(POST, PUT, DELETE, GET) for test, make it more
 * cleaner and easier to build the test classes. <a href=
 * "https://github.com/rest-assured/rest-assured/wiki/usage#path-parameters">Rest-Assured-Wiki</a>
 * 
 * @author CWDS API Team
 *
 */
public class HttpRequestHandler {

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
  public Response postRequest(Object object, String resourcePath, String token) {
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
  public Response getRequest(String resourcePath, Map<String, Object> parametersMap) {
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
  public Response postRequestWithPathParameters(Object object, String resourcePath,
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
  public Response deleteRequestWithPathParameters(Object object, String resourcePath,
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
  public Response putRequestWithPathParameters(Object object, String resourcePath,
      Map<String, Object> parameterNameValuePairs, String token) {
    return given().queryParam(TOKEN, token).contentType(ContentType.JSON)
        .pathParams(parameterNameValuePairs).accept(ContentType.JSON).body(object).when()
        .put(resourcePath).then().extract().response();
  }

}
