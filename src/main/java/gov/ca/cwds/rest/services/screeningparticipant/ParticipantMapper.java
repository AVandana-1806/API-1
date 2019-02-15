package gov.ca.cwds.rest.services.screeningparticipant;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;

/**
 * @author CWDS API Team
 * 
 * @param <P> - CmsPersistentObject
 *
 */
@FunctionalInterface
public interface ParticipantMapper<P extends CmsPersistentObject> {

  /**
   * @param object - object
   * @return the object
   */
  ParticipantIntakeApi transform(P object);

}
