package gov.ca.cwds.rest.resources.relationship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.core.type.TypeReference;
import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.CandidateTo;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.RelatedTo;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author CWDS TPT-3 Team
 */
public abstract class RelationshipsBaseTest extends IntakeBaseTest {

  public static final String RELATIONSHIPS = "relationships";
  public static final String SCREENING_PATH = "screenings";
  public static final String RELATIONSHIPS_WITH_CANDIDATES = "relationships_with_candidates";

  protected void validateResponse(String actualJson, String expectedResponse)
      throws IOException {
    List<ScreeningRelationshipsWithCandidates> expected = objectMapper
        .readValue(expectedResponse,
            new TypeReference<List<ScreeningRelationshipsWithCandidates>>() {
            });
    List<ScreeningRelationshipsWithCandidates> fromResponse = objectMapper
        .readValue(actualJson,
            new TypeReference<List<ScreeningRelationshipsWithCandidates>>() {
            });
    assertNotNull(expected);
    assertNotNull(fromResponse);
    assertNotEquals(0, fromResponse.size());
    assertNotEquals(0, expected.size());

    expected.forEach(e -> {
      Optional<ScreeningRelationshipsWithCandidates> optional = fromResponse.stream()
          .filter(b -> b.getId().equals(e.getId())).findFirst();
      if (optional.isPresent()) {
        e.setAge(optional.get().getAge());
        e.setAgeUnit(optional.get().getAgeUnit());

        Set<RelatedTo> relatedTos = optional.get().getRelatedTo();
        e.getRelatedTo().forEach(relatedTo -> {
          Optional<RelatedTo> optionalRelatedTo = relatedTos.stream()
              .filter(c -> c.getRelationshipId().equals(relatedTo.getRelationshipId())).findFirst();
          if (optionalRelatedTo.isPresent()) {
            relatedTo.setRelatedAge(optionalRelatedTo.get().getRelatedAge());
            relatedTo.setRelatedAgeUnit(optionalRelatedTo.get().getRelatedAgeUnit());
          }

          assertEquals(optionalRelatedTo.get().getRelationshipId(), relatedTo.getRelationshipId());
          assertEquals(optionalRelatedTo.get().getEstimatedDobCode(), relatedTo.getEstimatedDobCode());
          assertEquals(optionalRelatedTo.get().getRelatedPersonId(),
              relatedTo.getRelatedPersonId());

        });

        Set<CandidateTo> candidateTos = optional.get().getRelatedCandidatesTo();
        e.getRelatedCandidatesTo().forEach(candidateTo -> {
          Optional<CandidateTo> optionalCandidateTo = candidateTos.stream()
              .filter(c -> c.getCandidateId().equals(candidateTo.getCandidateId())).findFirst();
          if (optionalCandidateTo.isPresent()) {
            candidateTo.setCandidateAge(optionalCandidateTo.get().getCandidateAge());
            candidateTo.setCandidateAgeUnit(optionalCandidateTo.get().getCandidateAgeUnit());
          }

          assertEquals(optionalCandidateTo.get().getCandidateId(), candidateTo.getCandidateId());
          assertEquals(optionalCandidateTo.get().getCandidateId(), candidateTo.getCandidateId());
          assertEquals(optionalCandidateTo.get().getEstimatedDobCode(), candidateTo.getEstimatedDobCode());
        });
        assertEquals(e.getId(), optional.get().getId());
      }
    });
  }
}
