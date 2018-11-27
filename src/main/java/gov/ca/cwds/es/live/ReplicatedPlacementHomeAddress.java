package gov.ca.cwds.es.live;

import gov.ca.cwds.data.es.ElasticSearchLegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

public class ReplicatedPlacementHomeAddress extends ReplicatedAddress {

  private static final long serialVersionUID = 1L;

  @Override
  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return LiveElasticTransformer.createLegacyDescriptor(getId(), getLastUpdatedTime(),
        LegacyTable.PLACEMENT_HOME);
  }

}
