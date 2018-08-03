package gov.ca.cwds.rest.api.domain.hoi;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

/**
 * Social worker person.
 * 
 * @author CWDS API Team
 */
public class HOISocialWorker extends HOIPerson {

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   */
  public HOISocialWorker() {
    super();
  }

  /**
   * 
   * @param id - id
   * @param firstName - firstName
   * @param lastName - lastName
   * @param nameSuffix - nameSuffix
   * @param legacyDescriptor - legacyDescriptor
   */
  public HOISocialWorker(String id, String firstName, String lastName, String nameSuffix,
      LegacyDescriptor legacyDescriptor) {
    super(id, firstName, lastName, nameSuffix, legacyDescriptor);
  }

}

