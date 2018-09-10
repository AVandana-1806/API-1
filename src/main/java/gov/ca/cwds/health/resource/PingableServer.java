package gov.ca.cwds.health.resource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("fb-contrib:STT_TOSTRING_STORED_IN_FIELD")
public class PingableServer implements Pingable {
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
	Response response = null;
	boolean ok = false;
	
	try {
      WebTarget webTarget = client.target(url);
      Invocation.Builder invocationBuilder = webTarget.request(mediaType);
      response = invocationBuilder.get();
      int status = response != null ? response.getStatus() : -1;

      if (acceptableResponse(status)) {
        ok = true;
        message = "Status is OK: " + status;
      } else {
        message = "Status: " + status + ", URL: " + url;
      }
	} finally {
	  if (response != null) {
	    response.close();	    
	  }
	}
        
    return ok;
  }

  private boolean acceptableResponse(int responseStatus) {
    return responseStatus >= 200 && responseStatus < 500;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
