package gov.ca.cwds.rest.business.rules.doctool;

import java.time.LocalDate;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.business.rules.CommercialSexualExploitationHistoryDroolsConfiguration;
import gov.ca.cwds.rest.exception.IssueDetails;

/**
 * @author CWDS API Team
 *
 */
public class R10975Test {

  private DroolsService droolsService;

  /**
   * 
   */
  @Before
  public void setUp() {
    droolsService = new DroolsService();
  }

  /**
   * 
   */
  @Test
  public void testFailWhenCsecStartDateIsGreaterThanEndDate() {
    final Csec csec = new Csec();
    csec.setStartDate(LocalDate.of(2018, 9, 28));
    csec.setEndDate(LocalDate.of(2018, 9, 27));
    Set<IssueDetails> details = droolsService.performBusinessRules(
        CommercialSexualExploitationHistoryDroolsConfiguration.INSTANCE, csec);
    Assert.assertEquals("R-10975", details.iterator().next().getCode());
    Assert.assertEquals("CSEC endDate should be greater than or equal to startDate",
        details.iterator().next().getUserMessage());
  }

  /**
   * 
   */
  @Test
  public void testSuccessWhenCsecEndDateIsGreaterThanStartDate() {
    final Csec csec = new Csec();
    csec.setStartDate(LocalDate.of(2018, 9, 28));
    csec.setEndDate(LocalDate.of(2018, 9, 29));
    Set<IssueDetails> details = droolsService.performBusinessRules(
        CommercialSexualExploitationHistoryDroolsConfiguration.INSTANCE, csec);
    Assert.assertEquals(Boolean.TRUE, details.isEmpty());
  }

  /**
   * 
   */
  @Test
  public void testSuccessWhenCsecEndDateAndStartDateEqual() {
    final Csec csec = new Csec();
    csec.setStartDate(LocalDate.of(2018, 9, 28));
    csec.setEndDate(LocalDate.of(2018, 9, 28));
    Set<IssueDetails> details = droolsService.performBusinessRules(
        CommercialSexualExploitationHistoryDroolsConfiguration.INSTANCE, csec);
    Assert.assertEquals(Boolean.TRUE, details.isEmpty());
  }

}
