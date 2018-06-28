package gov.ca.cwds.health.resource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("fb-contrib:STT_TOSTRING_STORED_IN_FIELD")
public class PingableServer implements Pingable {
  private static final Logger LOGGER = LoggerFactory.getLogger(PingableServer.class);

  protected Client client;
  protected String url;
  private String message;
  private String mediaType;

  /**
   * @param client - client
   * @param url - url
   * @param mediaType - mediaType
   */
  public PingableServer(Client client, String url, String mediaType) {
    this.client = client;
    this.url = url;
    this.mediaType = mediaType;
  }

  @Override
  public boolean ping() {
    WebTarget webTarget = client.target(url);
    Invocation.Builder invocationBuilder = webTarget.request(mediaType);
    Response response = invocationBuilder.get();

    boolean ok = false;
    if (acceptableResponse(response)) {
      ok = true;
      message = "Status is OK";
    } else {

      int status = response.getStatus();
      LOGGER.warn("Unable to ping resource: url: {}", url);
      LOGGER.warn("Received status of: {}", status);
      message = "Status:" + status;
    }
    return ok;
  }

  private boolean acceptableResponse(Response response) {
    return response.getStatus() >= 200 && response.getStatus() < 500;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
