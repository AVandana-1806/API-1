package gov.ca.cwds.health.resource;

import javax.ws.rs.client.Client;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class AuthServer extends PingableServer {

  @Inject
  AuthServer(@Named("swaggerTokenClient") Client client, @Named("swagger-toekn-url") String url,
      @Named("json-media") String mediaType) {
    super(client, url, mediaType);
  }

}
