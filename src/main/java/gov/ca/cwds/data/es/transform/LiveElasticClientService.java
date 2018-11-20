package gov.ca.cwds.data.es.transform;

import com.google.inject.Inject;

import gov.ca.cwds.rest.resources.SimpleResourceService;

/**
 * Business service for live Elasticsearch client.
 * 
 * @author CWDS API Team
 */
public class LiveElasticClientService
    extends SimpleResourceService<String[], LiveElasticClientRequest, LiveElasticClientResponse> {

  /**
   * Constructor
   */
  @Inject
  public LiveElasticClientService() {
    // Default, no-op.
  }

  @Override
  protected LiveElasticClientResponse handleRequest(LiveElasticClientRequest req) {
    return handleFind(req.getClientIds().toArray(new String[0]));
  }

  @Override
  protected LiveElasticClientResponse handleFind(String[] key) {
    // TODO: call LiveElasticClientHandler
    return new LiveElasticClientResponse("placeholder");
  }

}
