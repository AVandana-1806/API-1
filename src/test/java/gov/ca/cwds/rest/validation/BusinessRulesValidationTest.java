package gov.ca.cwds.rest.validation;

import gov.ca.cwds.data.legacy.cms.entity.NonCWSNumber;
import gov.ca.cwds.data.legacy.cms.entity.SafelySurrenderedBabies;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.business.rules.SafelySurrenderBabiesDroolsConfiguration;
import gov.ca.cwds.rest.exception.IssueDetails;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * CWDS API Team
 */
public class BusinessRulesValidationTest {
  private DroolsService droolsService;

  @Before
  public void setUpBase() {
    droolsService = new DroolsService();
  }

  @Test
  public void participantSsb() {
    Set<IssueDetails> issueDetailsList =
        droolsService.performBusinessRules(SafelySurrenderBabiesDroolsConfiguration.INSTANCE,
            createValidSsb(),  createValidNonCwsNumber());

    assertTrue(issueDetailsList.isEmpty());
  }

  @Test
  public void participantSsbBraceletIdIsRequired() {
    NonCWSNumber nonCwsNumber = createValidNonCwsNumber();
    nonCwsNumber.setOtherId(null);

    Set<IssueDetails> issueDetailsList =
        droolsService.performBusinessRules(SafelySurrenderBabiesDroolsConfiguration.INSTANCE,
            createValidSsb(), nonCwsNumber);

    assertEquals(1, issueDetailsList.size());
    IssueDetails issueDetails = issueDetailsList.iterator().next();
    assertEquals("safely-surrender-babies-rules", issueDetails.getCode());
    assertEquals("Safely Surrendered Babies Bracelet ID is required",
        issueDetails.getUserMessage());
  }

  @Test
  public void participantSsbBraceletIdInfoCodeAllowedValues() {
    SafelySurrenderedBabies ssb = createValidSsb();
    ssb.setBraceletIdInfoCode("non");

    Set<IssueDetails> issueDetailsList =
        droolsService.performBusinessRules(SafelySurrenderBabiesDroolsConfiguration.INSTANCE,
            ssb, createValidNonCwsNumber());

    assertEquals(1, issueDetailsList.size());
    IssueDetails issueDetails = issueDetailsList.iterator().next();
    assertEquals("safely-surrender-babies-rules", issueDetails.getCode());
    assertEquals("Parent/Guardian Given Bracelet ID should be one of ['A', 'N', 'U', 'Y']",
        issueDetails.getUserMessage());
  }

  @Test
  public void participantSsbMedicalQuestionnaireCodeAllowedValues() {
    SafelySurrenderedBabies ssb = createValidSsb();
    ssb.setMedicalQuestionnaireCode("non");

    Set<IssueDetails> issueDetailsList =
        droolsService.performBusinessRules(SafelySurrenderBabiesDroolsConfiguration.INSTANCE,
            ssb, createValidNonCwsNumber());
    assertEquals(1, issueDetailsList.size());
    IssueDetails issueDetails = issueDetailsList.iterator().next();
    assertEquals("safely-surrender-babies-rules", issueDetails.getCode());
    assertEquals(
        "Parent/Guardian Provided Medical Questionaire should be one of ['D', 'M', 'N', 'R', 'U']",
        issueDetails.getUserMessage());
  }

  private NonCWSNumber createValidNonCwsNumber() {
    NonCWSNumber nonCWSNumber = new NonCWSNumber();
    nonCWSNumber.setOtherId("test bracelet id");
    return nonCWSNumber;
  }

  private SafelySurrenderedBabies createValidSsb() {
    SafelySurrenderedBabies ssb = new SafelySurrenderedBabies();
    ssb.setBraceletIdInfoCode("U");
    ssb.setMedicalQuestionnaireCode("D");
    return ssb;
  }
}
