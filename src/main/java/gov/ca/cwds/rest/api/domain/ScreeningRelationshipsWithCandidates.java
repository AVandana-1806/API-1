package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * @author CWDS TPT-3 Team
 */
public class ScreeningRelationshipsWithCandidates extends Relationship {

  @JsonProperty("candidate_to")
  private Set<RelationshipTo> relatedCandidatesTo;

  public ScreeningRelationshipsWithCandidates(String id, Date dateOfBirth,
      String firstName, String middleName, String lastName, String suffixName,
      String gender, Date dateOfDeath,
      Boolean sensitive, Boolean sealed,
      CmsRecordDescriptor cmsRecordDescriptor,
      Set<RelationshipTo> relatedTo,
      Set<RelationshipTo> relatedCandidatesTo) {
    super(id, dateOfBirth == null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth), firstName,
        middleName, lastName, suffixName,
        gender, dateOfDeath == null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(dateOfDeath),
        sensitive, sealed, cmsRecordDescriptor, relatedTo);
    this.relatedCandidatesTo = relatedCandidatesTo;
  }

  public Set<RelationshipTo> getRelatedCandidatesTo() {
    return relatedCandidatesTo;
  }
}
