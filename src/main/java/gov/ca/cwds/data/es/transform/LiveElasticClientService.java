package gov.ca.cwds.data.es.transform;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.resources.SimpleResourceService;

/**
 * Business service for for {@link CmsKeyIdGenerator}.
 * 
 * @author CWDS API Team
 */
public class LiveElasticClientService
    extends SimpleResourceService<String, LiveElasticClientRequest, LiveElasticClientResponse> {

  /**
   * Constructor
   */
  @Inject
  public LiveElasticClientService() {
    // Default, no-op.
  }

  @Override
  protected LiveElasticClientResponse handleRequest(LiveElasticClientRequest req) {
    return handleFind(req.getLegacyKey());
  }

  @Override
  protected LiveElasticClientResponse handleFind(String key) {
    return new LiveElasticClientResponse(CmsKeyIdGenerator.getUIIdentifierFromKey(key));
  }

}
