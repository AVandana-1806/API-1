package gov.ca.cwds.data.es.transform;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.cms.LegacyKeyRequest;
import gov.ca.cwds.rest.api.domain.cms.LegacyKeyResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;

/**
 * Business service for for {@link CmsKeyIdGenerator}.
 * 
 * @author CWDS API Team
 */
public class LiveElasticClientService
    extends SimpleResourceService<String, LegacyKeyRequest, LegacyKeyResponse> {

  /**
   * Constructor
   */
  @Inject
  public LiveElasticClientService() {
    // Default, no-op.
  }

  @Override
  protected LegacyKeyResponse handleRequest(LegacyKeyRequest req) {
    return handleFind(req.getLegacyKey());
  }

  @Override
  protected LegacyKeyResponse handleFind(String key) {
    return new LegacyKeyResponse(CmsKeyIdGenerator.getUIIdentifierFromKey(key));
  }

}
