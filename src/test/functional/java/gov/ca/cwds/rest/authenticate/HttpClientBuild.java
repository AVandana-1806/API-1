package gov.ca.cwds.rest.authenticate;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class HttpClientBuild {

  PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
  RequestConfig requestConfig =
      RequestConfig.custom().setRedirectsEnabled(false).setCookieSpec(CookieSpecs.STANDARD).build();

  CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager)
      .setDefaultRequestConfig(requestConfig).build();
  HttpClientContext httpContext = HttpClientContext.create();
}
