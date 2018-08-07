package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies;

/**
 * Fixtures for SafelySurrenderedBabies.
 * 
 * @author CWDS API Team
 */
public class SafelySurrenderedBabiesBuilder {

  private String braceletId = "ssb-bracId";
  private String braceletInfoCode = "U";
  private String comments = "ssb-comments";
  private String medicalQuestionaireCode = "U";
  private Integer relationToChild = 1592;
  private String surrenderedByName = "ssb-surrenderedByName";

  /**
   * Default no-argument constructor.
   */
  public SafelySurrenderedBabiesBuilder() {
    // No-argument constructor.
  }

  /**
   * Builds SafelySurrenderedBabies object for testing.
   * 
   * @return SafelySurrenderedBabies object for testing
   */
  public SafelySurrenderedBabies build() {
    SafelySurrenderedBabies safelySurrenderedBabies = new SafelySurrenderedBabies();
    safelySurrenderedBabies.setBraceletId(braceletId);
    safelySurrenderedBabies.setBraceletInfoCode(braceletInfoCode);
    safelySurrenderedBabies.setComments(comments);
    safelySurrenderedBabies.setMedicalQuestionaireCode(medicalQuestionaireCode);
    safelySurrenderedBabies.setRelationToChild(relationToChild);
    safelySurrenderedBabies.setSurrenderedByName(surrenderedByName);
    return safelySurrenderedBabies;
  }
}
