package gov.ca.cwds.rest.validation.rules;

import gov.ca.cwds.CWSDateTime;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.business.rules.CrossReportDroolsConfiguration;
import gov.ca.cwds.rest.exception.IssueDetails;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * CWDS API Team
 */
public class CrossReportBusinessRulesValidationTest {
  private DroolsService droolsService;

  @Before
  public void setUpBase() {
    droolsService = new DroolsService();
  }

  @Test
  public void testThatActualDateStringIsParsedSuccessfully() {
    String crossReportDate = "2018-09-24T00:00:00.000";
    String screeningDate = "2018-09-23T00:00:00.000Z";

    CrossReport crossReport = new CrossReport();
    crossReport.setInformDate(crossReportDate);

    ScreeningToReferral screeningToReferral = new ScreeningToReferral();
    screeningToReferral.setStartedAt(screeningDate);

    droolsService.performBusinessRules(CrossReportDroolsConfiguration.INSTANCE,
            crossReport, screeningToReferral);
  }

  @Test
  public void crossReportReportedOnDate() {
    Date now = new Date();
    String crossReportString = DateFormatUtils.format(now, CWSDateTime.TIMESTAMP_ISO8601_FORMAT_TO_STRING);
    String screeningString = DateFormatUtils.format(now, CWSDateTime.TIMESTAMP_ISO8601_FORMAT);

    CrossReport crossReport = new CrossReport();
    crossReport.setInformDate(crossReportString);

    ScreeningToReferral screeningToReferral = new ScreeningToReferral();
    screeningToReferral.setStartedAt(screeningString);

    Set<IssueDetails> issueDetailsList =
        droolsService.performBusinessRules(CrossReportDroolsConfiguration.INSTANCE,
            crossReport, screeningToReferral);

    assertEquals(0, issueDetailsList.size());
  }

  @Test
  public void crossReportReportedOnDate_inFuture() {
    Date now = new Date();
    String nowString = DateFormatUtils.format(now.getTime(),
        CWSDateTime.TIMESTAMP_ISO8601_FORMAT);

    Calendar instance = Calendar.getInstance();
    instance.add(Calendar.DATE, 1);
    String futureString = DateFormatUtils.format(instance.getTime(),
        CWSDateTime.TIMESTAMP_ISO8601_FORMAT_TO_STRING);

    CrossReport crossReport = new CrossReport();
    crossReport.setInformDate(futureString);

    ScreeningToReferral screeningToReferral = new ScreeningToReferral();
    screeningToReferral.setStartedAt(nowString);

    Set<IssueDetails> issueDetailsList =
        droolsService.performBusinessRules(CrossReportDroolsConfiguration.INSTANCE,
            crossReport, screeningToReferral);

    assertEquals(1, issueDetailsList.size());
    IssueDetails issueDetails = issueDetailsList.iterator().next();
    assertEquals("cross-report-rules", issueDetails.getCode());
    assertEquals("Cross Report reported on date cannot be in future",
        issueDetails.getUserMessage());
  }

  @Test
  public void crossReportReportedOnDate_beforeScreeningStartDate() {
    Date now = new Date();
    String nowString = DateFormatUtils.format(now.getTime(),
        CWSDateTime.TIMESTAMP_ISO8601_FORMAT_TO_STRING);

    CrossReport crossReport = new CrossReport();
    crossReport.setInformDate(nowString);

    Calendar instance = Calendar.getInstance();
    instance.add(Calendar.DATE, 1);
    String futureString = DateFormatUtils.format(instance.getTime(),
        CWSDateTime.TIMESTAMP_ISO8601_FORMAT);

    ScreeningToReferral screeningToReferral = new ScreeningToReferral();
    screeningToReferral.setStartedAt(futureString);

    Set<IssueDetails> issueDetailsList =
        droolsService.performBusinessRules(CrossReportDroolsConfiguration.INSTANCE,
            crossReport, screeningToReferral);

    assertEquals(1, issueDetailsList.size());
    IssueDetails issueDetails = issueDetailsList.iterator().next();
    assertEquals("cross-report-rules", issueDetails.getCode());
    assertEquals("Cross Report reported on date cannot be before Screening start date",
        issueDetails.getUserMessage());
  }

  @Test
  public void crossReportReportedOnDate_nullDates() {
    CrossReport crossReport = new CrossReport();
    ScreeningToReferral screeningToReferral = new ScreeningToReferral();

    Set<IssueDetails> issueDetailsList =
        droolsService.performBusinessRules(CrossReportDroolsConfiguration.INSTANCE,
            crossReport, screeningToReferral);

    assertEquals(0, issueDetailsList.size());
  }
}
