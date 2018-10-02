package gov.ca.cwds.rest.services.relationship;

import java.util.List;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipBase;

/**
 * @author CWDS TPT-3 Team
 */
public interface RelationshipFacade {

  Response updateRelationship(String relationshipId, ScreeningRelationship relationship);

  List<Response> createRelationships(List<ScreeningRelationshipBase> relationships);

  List<Response> getRelationshipsWithCandidatesByScreeningId(String screeningId);

  List<Response> getRelationshipsByScreeningId(String screeningId);

  void deleteRelationshipsAndRelatedParticipants(String participantId, String screeningId);
}
