package gov.ca.cwds.health.resource;

import javax.ws.rs.client.Client;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class SwaggerEndpoint extends PingableServer {

  @Inject
  SwaggerEndpoint(@Named("swaggerClient") Client client, @Named("swagger-url") String url,
      @Named("http-media") String mediaType) {
    super(client, url, mediaType);
  }

}
