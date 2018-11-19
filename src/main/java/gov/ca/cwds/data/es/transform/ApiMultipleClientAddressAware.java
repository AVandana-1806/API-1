package gov.ca.cwds.data.es.transform;

import java.util.List;

import gov.ca.cwds.data.es.ElasticSearchPersonAddress;

@FunctionalInterface
public interface ApiMultipleClientAddressAware {

  /**
   * Get list of person addresses
   * 
   * @return List of person addresses
   */
  List<ElasticSearchPersonAddress> getElasticSearchPersonAddresses();

}
