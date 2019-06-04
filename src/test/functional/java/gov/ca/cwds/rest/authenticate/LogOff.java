package gov.ca.cwds.rest.authenticate;

import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class LogOff {

  private static final String LOCATIONFIELD = "Location";

  private static final Logger LOGGER = LoggerFactory.getLogger(LogOff.class);

  private static final String NEW_REQUEST_TO_BEGIN = "=========================================";
  private static final String CALL_BACK_URL = "https://ferbapi.integration.cwds.io/swagger";
  private static final String LOG_OUT_AUTH_URL =
      "https://web.integration.cwds.io/perry/authn/logout";

  public static void main(String[] args) {

    try {

      RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(false)
          .setCookieSpec(CookieSpecs.STANDARD).build();

      HttpClient httpClient =
          HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
      HttpClientContext httpContext = HttpClientContext.create();

      ArrayList<NameValuePair> postParams = new ArrayList<>();

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info("GET: {}", LOG_OUT_AUTH_URL);
      postParams.add(new BasicNameValuePair("callback", CALL_BACK_URL));
      postParams.add(new BasicHeader("Referer",
          "https://ferbapi.integration.cwds.io/swagger?token=1d9d450e-d32a-4976-a1ec-11505527508f"));
      HttpGet httpGet = new HttpGet(CALL_BACK_URL);
      URI uri = new URIBuilder(CALL_BACK_URL).addParameters(postParams).build();
      httpGet.setURI(uri);
      HttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
      String location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info("GET: {}", location);
      postParams.add(new BasicNameValuePair("redirectToClientLogin", CALL_BACK_URL));
      postParams.add(new BasicHeader("message", null));
      httpGet = new HttpGet(CALL_BACK_URL);
      uri = new URIBuilder(CALL_BACK_URL).addParameters(postParams).build();
      httpGet.setURI(uri);
      httpResponse = httpClient.execute(httpGet, httpContext);

      LOGGER.debug("************************************ httpResponse:\n" + httpResponse);

    } catch (Exception e) {
      LOGGER.error("Unable to LogOff", e);
    }
  }

}
