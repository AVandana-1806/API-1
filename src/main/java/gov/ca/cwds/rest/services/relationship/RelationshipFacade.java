package gov.ca.cwds.rest.services.relationship;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipBase;
import java.util.List;

/**
 * @author CWDS TPT-3 Team
 */
public interface RelationshipFacade {

  gov.ca.cwds.rest.api.Response updateRelationship(String relationshipId,
      ScreeningRelationship relationship);

  List<Response> createRelationships(List<ScreeningRelationshipBase> relationships);

  List<gov.ca.cwds.rest.api.Response> getRelationshipsWithCandidatesByScreeningId(
      String screeningId);

  List<gov.ca.cwds.rest.api.Response> getRelationshipsByScreeningId(String screeningId);
}
