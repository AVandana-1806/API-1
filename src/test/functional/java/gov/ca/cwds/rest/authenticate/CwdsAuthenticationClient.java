package gov.ca.cwds.rest.authenticate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.authenticate.config.YmlLoader;

/**
 * This class is used to generate the token using username and password with Perry, and handles all
 * the redirect from clicking the login to the end to get the token.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class CwdsAuthenticationClient extends HttpClientBuild implements CwdsClientCommon {

  private static final Logger LOGGER = LoggerFactory.getLogger(CwdsAuthenticationClient.class);

  private static final int GET_ONLY_ACCESS_CODE_VALUE = 11;
  private static final int GET_ONLY_THE_VALUE = 7;
  private static final String REDIRECT_URL_LOGGER = "Redirect URL: {}";
  private static final String POST_LOGGER = "POST : {}{}";
  private static final String GET_LOGGER = "GET: {}";

  private static final String ACCESS_CODE = "accessCode";
  private static final String USERNAMEFIELD = "username";
  private static final String LOCATIONFIELD = "Location";
  private static final String VALUE = "value=";
  private static final String SIGN_IN_SUBMIT_BUTTON = "signInSubmitButton";
  private static final String _CSRF = "_csrf";

  private static final String NEW_REQUEST_TO_BEGIN = "=========================================";

  private String userName;
  private String password;

  private HttpGet httpGet;
  private String authLoginUrl;
  private String callBackUrl;
  private String tokenUrl;

  /**
   * This constructor is to used to initialize the yaml and used over the class.
   * 
   * @param ymlLoader - ymlLoader
   * @param userName - userName
   * @param password - password
   */
  public CwdsAuthenticationClient(YmlLoader ymlLoader, String userName, String password) {
    this.userName = userName;
    this.password = password;
    this.authLoginUrl = ymlLoader.readConfig().getTestUrl().getAuthLoginUrl();
    this.callBackUrl = ymlLoader.readConfig().getTestUrl().getCallBackUrl();
    this.tokenUrl = ymlLoader.readConfig().getTestUrl().getTokenUrl();
  }

  /**
   * Default method to get the token.
   * 
   * @return the valid token
   */
  @Override
  public String getToken() {
    String token = null;
    try {
      ArrayList<NameValuePair> postParams = new ArrayList<>();
      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(GET_LOGGER, authLoginUrl);
      postParams.add(new BasicNameValuePair("callback", callBackUrl));
      postParams.add(new BasicNameValuePair("sp_id", null));
      httpGet = new HttpGet(authLoginUrl);
      URI uri = new URIBuilder(authLoginUrl).addParameters(postParams).build();
      httpGet.setURI(uri);
      HttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
      String redirectUrl = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();
      LOGGER.info(REDIRECT_URL_LOGGER, redirectUrl);

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(GET_LOGGER, redirectUrl);
      httpGet = new HttpGet(redirectUrl);
      httpResponse = httpClient.execute(httpGet, httpContext);
      String location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();
      LOGGER.info(REDIRECT_URL_LOGGER, location);

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(GET_LOGGER, location);
      httpGet = new HttpGet(location);
      httpResponse = httpClient.execute(httpGet, httpContext);
      String response = EntityUtils.toString(httpResponse.getEntity());
      String csrf = getCsrfValue(response);

      String accessCode = enterTheCredentials(postParams, csrf, location);
      requestToken(postParams, accessCode);

    } catch (Exception e) {
      LOGGER.error("Unable to create the token", e);
    } finally {
      this.httpGet.reset();
    }
    return token;
  }

  private String enterTheCredentials(ArrayList<NameValuePair> postParams, String csrf,
      String location) throws UnsupportedEncodingException, IOException, ClientProtocolException {
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info(POST_LOGGER, location);
    HttpPost httpPost = new HttpPost(location);
    postParams.clear();
    postParams.add(new BasicNameValuePair(_CSRF, csrf));
    postParams.add(new BasicNameValuePair("password", password));
    postParams.add(new BasicNameValuePair(SIGN_IN_SUBMIT_BUTTON, "Sign+in"));
    postParams.add(new BasicNameValuePair(USERNAMEFIELD, userName));
    httpPost.setEntity(new UrlEncodedFormEntity(postParams));
    HttpResponse httpResponse = httpClient.execute(httpPost, httpContext);
    location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();

    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info(GET_LOGGER, location);
    HttpGet httpGet = new HttpGet(location);
    httpResponse = httpClient.execute(httpGet, httpContext);
    location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();

    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info(GET_LOGGER, location);
    httpGet = new HttpGet(location);
    httpResponse = httpClient.execute(httpGet, httpContext);
    location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();
    return getAccessCode(location);
  }

  private void requestToken(ArrayList<NameValuePair> postParams, String accessCode)
      throws URISyntaxException, IOException {
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info("GET TOKEN: {}", tokenUrl);
    postParams.clear();
    postParams.add(new BasicNameValuePair(ACCESS_CODE, accessCode));
    HttpGet httpGet = new HttpGet(tokenUrl);
    URI uri = new URIBuilder(tokenUrl).addParameters(postParams).build();
    httpGet.setURI(uri);
    HttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
    String token = EntityUtils.toString(httpResponse.getEntity());
    LOGGER.info("TOKEN: {}", token);
  }

  private String getCsrfValue(String response) {
    String csrfIndex = response.substring(response.indexOf(_CSRF));
    int startIndex = csrfIndex.indexOf(VALUE) + GET_ONLY_THE_VALUE;
    int endIndex = csrfIndex.indexOf("/>") - 1;
    return csrfIndex.substring(startIndex, endIndex);
  }

  private String getAccessCode(String location) {
    String accessCodeParm = location.substring(location.indexOf(ACCESS_CODE));
    int startIndex = accessCodeParm.indexOf(ACCESS_CODE) + GET_ONLY_ACCESS_CODE_VALUE;
    return accessCodeParm.substring(startIndex);
  }

}
