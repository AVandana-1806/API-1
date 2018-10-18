package gov.ca.cwds.rest.validation.rules;


import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.business.rules.CommercialSexualExploitationHistoryDroolsConfiguration;
import gov.ca.cwds.rest.exception.IssueDetails;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.SexualExploitationType;

public class CsecHistoryBusinessRulesValidationTest {
  private DroolsService droolsService;
  private static final LocalDate NEW_START_DATE = LocalDate.of(2017, 12, 12);
  private static final LocalDate SAME_START_DATE = LocalDate.of(2018, 2, 10);
  private static final LocalDate NEW_END_DATE = LocalDate.of(2018, 2, 10);
  private static final LocalDate BAD_END_DATE = LocalDate.of(2017, 11, 11);
  private static final String CLIENT_ID = "R06FKZ20X5";
  private static final short VICTIM_WHILE_ABSENT_FROM_PLACEMENT = 6871;
  private static final short OTHER_SYS_CODE = 6872;
  
  private SexualExploitationType sexualExploitationType = new SexualExploitationType();

  @Before
  public void setUpBase() {
    droolsService = new DroolsService();
    sexualExploitationType.setSystemId(VICTIM_WHILE_ABSENT_FROM_PLACEMENT);
  }
  
  @Test
  public void shouldFireR10971() {
    Set<IssueDetails> issueDetailsSet = 
        droolsService.performBusinessRules(CommercialSexualExploitationHistoryDroolsConfiguration.INSTANCE, 
            createValidCsecHistory());
    assertTrue(issueDetailsSet.isEmpty());
  }
  
  @Test
  public void shouldErrorWhenEndDateIsNullAndType6871() {
    CsecHistory csec = createValidCsecHistory();
    csec.setEndDate(null);
    Set<IssueDetails> issueDetailsSet = 
        droolsService.performBusinessRules(CommercialSexualExploitationHistoryDroolsConfiguration.INSTANCE, 
            csec);
    assertEquals(1, issueDetailsSet.size());
    IssueDetails issueDetails = issueDetailsSet.iterator().next();
    assertEquals("CSEC Victim while Absent from Placement requires an end date",
        issueDetails.getUserMessage());
  }
   
  @Test
  public void shouldPassWhenEndDateNullAndTypeNot6871() {
    sexualExploitationType.setSystemId(OTHER_SYS_CODE);
    CsecHistory csec = createValidCsecHistory();
    csec.setEndDate(null);
    csec.setSexualExploitationType(sexualExploitationType);
    Set<IssueDetails> issueDetailsSet = 
        droolsService.performBusinessRules(CommercialSexualExploitationHistoryDroolsConfiguration.INSTANCE, 
            csec);
    assertTrue(issueDetailsSet.isEmpty());   
  }
  
  @Test
  public void shoudPassR10975WhenEndDateGreaterThanStartDate() {
    Set<IssueDetails> issueDetailsSet = 
        droolsService.performBusinessRules(CommercialSexualExploitationHistoryDroolsConfiguration.INSTANCE, 
            createValidCsecHistory());
    assertTrue(issueDetailsSet.isEmpty());
    
  }
  
  @Test
  public void shouldFailR10975WhenEndDateLessThanStartDate() {
    CsecHistory csec = createValidCsecHistory();
    csec.setEndDate(BAD_END_DATE);
    Set<IssueDetails> issueDetailsSet = 
        droolsService.performBusinessRules(CommercialSexualExploitationHistoryDroolsConfiguration.INSTANCE, 
            csec);
    assertEquals(1, issueDetailsSet.size());
    IssueDetails issueDetails = issueDetailsSet.iterator().next();
    assertEquals("CSEC End Date must be greater than or equal to CSEC Start Date",
        issueDetails.getUserMessage());
  }
  
  @Test
  public void shouldPassWhenEndDateEqualStartDate() {
    CsecHistory csec = createValidCsecHistory();
    csec.setStartDate(SAME_START_DATE);
    Set<IssueDetails> issueDetailsSet = 
        droolsService.performBusinessRules(CommercialSexualExploitationHistoryDroolsConfiguration.INSTANCE, 
            csec);
    assertTrue(issueDetailsSet.isEmpty());
  }

  private CsecHistory createValidCsecHistory() {
    CsecHistory csec = new CsecHistory();
    csec.setStartDate(NEW_START_DATE);
    csec.setEndDate(NEW_END_DATE);
    csec.setSexualExploitationType(sexualExploitationType);
    csec.setChildClient(CLIENT_ID);
    return csec;    
  }
}
