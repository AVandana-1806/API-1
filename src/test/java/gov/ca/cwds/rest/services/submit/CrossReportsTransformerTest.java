package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.CrossReportResourceBuilder;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;
import gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake;

/***
 * 
 * @author CWDS API Team
 */
public class CrossReportsTransformerTest {

  private final static String INFORM_DATE_GMT_TIME = "2017-03-15T00:00:00.000Z";
  private final static String INFORM_DATE_PST_TIME = "2017-03-14T17:00:00.000";

  private CrossReportIntake crossReportIntake;

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private CrossReportResourceBuilder crossReportResourceBuilder;

  private Set<CrossReportIntake> nsCrossReports;

  @Before
  public void setup() throws Exception {
    Set<GovernmentAgencyIntake> agencies =
        Stream.of(new GovernmentAgencyIntake("12", "Ad4ATcY00E", "LAW_ENFORCEMENT"))
            .collect(Collectors.toSet());
    crossReportIntake = new CrossReportIntake();
    crossReportIntake.setId("");
    crossReportIntake.setLegacyId("");
    crossReportIntake.setLegacySourceTable("");
    crossReportIntake.setMethod("Electronic Report");
    crossReportIntake.setFiledOutOfState(false);
    crossReportIntake.setInformDate(INFORM_DATE_GMT_TIME);
    crossReportIntake.setAgencies(agencies);
    crossReportIntake.setCountyId("1101");

    nsCrossReports = Stream.of(crossReportIntake).collect(Collectors.toSet());

    crossReportResourceBuilder =
        new CrossReportResourceBuilder().setCountyId("34").setInformDate(INFORM_DATE_PST_TIME);
  }

  @Test
  public void transformConvertsCrossReportsIntakeToCrossReports() {
    CrossReport expectedCrossReport = crossReportResourceBuilder.createCrossReport();
    Set<CrossReport> actual = new CrossReportsTransformer().transform(nsCrossReports);

    Iterator<CrossReport> crossReportsIterator = actual.iterator();
    assertEquivalent(expectedCrossReport, crossReportsIterator.next());
  }

  @Test
  public void transformConvertsCrossReportsIntakeToCrossReportsWhenMethodEmpty() {
    CrossReport expectedCrossReport =
        crossReportResourceBuilder.setMethod(null).createCrossReport();

    crossReportIntake.setMethod("");
    nsCrossReports = Stream.of(crossReportIntake).collect(Collectors.toSet());
    Set<CrossReport> actual = new CrossReportsTransformer().transform(nsCrossReports);

    Iterator<CrossReport> crossReportsIterator = actual.iterator();
    assertEquivalent(expectedCrossReport, crossReportsIterator.next());
  }

  @Test
  public void transformConvertsCrossReportsIntakeToCrossReportsWhenCountyEmpty() {
    CrossReport expectedCrossReport =
        crossReportResourceBuilder.setCountyId(null).createCrossReport();

    crossReportIntake.setCountyId(null);
    nsCrossReports = Stream.of(crossReportIntake).collect(Collectors.toSet());
    Set<CrossReport> actual = new CrossReportsTransformer().transform(nsCrossReports);

    Iterator<CrossReport> crossReportsIterator = actual.iterator();
    assertEquivalent(expectedCrossReport, crossReportsIterator.next());
  }

  @Test
  public void shouldTransformDate() {
    crossReportIntake.setInformDate(INFORM_DATE_GMT_TIME);
    Set<CrossReport> transformedSetOfCrossReports =
        new CrossReportsTransformer().transform(nsCrossReports);

    Iterator<CrossReport> iter = transformedSetOfCrossReports.iterator();
    CrossReport transformedCrossReport = iter.next();
    assertEquals(INFORM_DATE_PST_TIME, transformedCrossReport.getInformDate());
  }

  @Test
  public void shouldTransformDateWithDefaultTimeWhenTimeStampNotPresent() {
    crossReportIntake.setInformDate("2017-03-15");
    Set<CrossReport> transformedSetOfCrossReports =
        new CrossReportsTransformer().transform(nsCrossReports);

    Iterator<CrossReport> iter = transformedSetOfCrossReports.iterator();
    CrossReport transformedCrossReport = iter.next();
    assertEquals("2017-03-15T01:00:00.000", transformedCrossReport.getInformDate());
  }

  private void assertEquivalent(CrossReport crossReport1, CrossReport crossReport2) {
    assertEquals(crossReport1.getId(), crossReport2.getId());
    assertEquals(crossReport1.getLegacyId(), crossReport2.getLegacyId());
    assertEquals(crossReport1.getLegacySourceTable(), crossReport2.getLegacySourceTable());
    assertEquals(crossReport1.getMethod(), crossReport2.getMethod());
    assertEquals(crossReport1.getCountyId(), crossReport2.getCountyId());
    assertEquals(crossReport1.getAgencies(), crossReport2.getAgencies());
    assertEquals(crossReport1.isFiledOutOfState(), crossReport2.isFiledOutOfState());

    assertEquals(crossReport1.getInformDate(), crossReport2.getInformDate());
  }

}
