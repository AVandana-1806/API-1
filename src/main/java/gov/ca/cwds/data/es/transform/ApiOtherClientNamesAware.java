package gov.ca.cwds.data.es.transform;

import java.util.List;

import gov.ca.cwds.data.es.ElasticSearchPersonAka;

/**
 * @author CWDS API Team
 */
@FunctionalInterface
public interface ApiOtherClientNamesAware {

  /**
   * Get client other names.
   * 
   * @return The client other names
   */
  List<ElasticSearchPersonAka> getOtherClientNames();

}
