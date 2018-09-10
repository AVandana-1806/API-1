package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
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
 *
 */
@SuppressWarnings("javadoc")
public class CrossReportsTransformerTest {

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
    crossReportIntake.setInformDate("2017-03-15T00:00:00.000-0700");
    crossReportIntake.setAgencies(agencies);
    crossReportIntake.setCountyId("1101");

    nsCrossReports =
        Stream.of(crossReportIntake).collect(Collectors.toSet());

    crossReportResourceBuilder = new CrossReportResourceBuilder()
        .setCountyId("34")
        .setInformDate("2017-03-15T00:00:00.000Z");
  }

  @Test
  public void transformConvertsCrossReportsIntakeToCrossReports() {
    CrossReport crossReport = crossReportResourceBuilder.createCrossReport();
    Set<CrossReport> expected = Stream.of(crossReport).collect(Collectors.toSet());
    Set<CrossReport> actual = new CrossReportsTransformer().transform(nsCrossReports);

    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsCrossReportsIntakeToCrossReportsWhenMethodEmpty() {
    CrossReport crossReport = crossReportResourceBuilder.setMethod(null).createCrossReport();
    Set<CrossReport> expected = Stream.of(crossReport).collect(Collectors.toSet());

    crossReportIntake.setMethod("");
    nsCrossReports =
        Stream.of(crossReportIntake).collect(Collectors.toSet());
    Set<CrossReport> actual = new CrossReportsTransformer().transform(nsCrossReports);

    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsCrossReportsIntakeToCrossReportsWhenCountyEmpty() {
    CrossReport crossReport = crossReportResourceBuilder.setCountyId(null).createCrossReport();
    Set<CrossReport> expected = Stream.of(crossReport).collect(Collectors.toSet());

    crossReportIntake.setCountyId(null);
    nsCrossReports =
        Stream.of(crossReportIntake).collect(Collectors.toSet());
    Set<CrossReport> actual = new CrossReportsTransformer().transform(nsCrossReports);

    assertEquals(actual, expected);
  }

  @Test
  public void shouldTransformDate() {
    crossReportIntake.setInformDate("2017-03-15T10:20:30.000-0700");
    Set<CrossReport> transformedSetOfCrossReports = new CrossReportsTransformer().transform(nsCrossReports);

    Iterator<CrossReport>  iter = transformedSetOfCrossReports.iterator();
    CrossReport transformedCrossReport = iter.next();
    assertEquals("2017-03-15T10:20:30.000Z", transformedCrossReport.getInformDate());
  }

  @Test
  public void shouldTransformDateWithDefaultTimeWhenTimeStampNotPresent() {
    crossReportIntake.setInformDate("2017-03-15");
    Set<CrossReport> transformedSetOfCrossReports = new CrossReportsTransformer().transform(nsCrossReports);

    Iterator<CrossReport>  iter = transformedSetOfCrossReports.iterator();
    CrossReport transformedCrossReport = iter.next();
    assertEquals("2017-03-15T00:00:00.000-0700",transformedCrossReport.getInformDate());
  }
}
