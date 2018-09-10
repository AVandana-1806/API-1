package gov.ca.cwds.rest.services.submit;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.GovernmentAgency;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

/**
 * Business layer object to transform NS {@link CrossReportIntake } to {@link CrossReport }
 * 
 * @author CWDS API Team
 */
public class CrossReportsTransformer {

  /**
   * @param crossReportsIntake - crossReportsIntake
   * @return the {@link CrossReport}
   */
  public Set<CrossReport> transform(Set<CrossReportIntake> crossReportsIntake) {
    Set<CrossReport> crossReports = new HashSet<>();
    GovernmentAgencyTransformer governmentAgencyIntake = new GovernmentAgencyTransformer();
    for (CrossReportIntake nsCrossReport : crossReportsIntake) {

      Set<GovernmentAgency> governmentAgency = new HashSet<>();
      nsCrossReport.getAgencies().stream().forEach(
          agencyaIntake -> governmentAgency.add(governmentAgencyIntake.transform(agencyaIntake)));
      Integer method = setCommuncationMethod(nsCrossReport);
      SystemCode systemCode = StringUtils.isNotBlank(nsCrossReport.getCountyId())
          ? SystemCodeCache.global().getSystemCode(Integer.valueOf(nsCrossReport.getCountyId()))
          : null;
      String county = systemCode != null ? systemCode.getLogicalId() : null;

      String informDate = extractDateFromCrossReport(nsCrossReport);
      crossReports.add(new CrossReport(nsCrossReport.getId(), nsCrossReport.getLegacySourceTable(),
          nsCrossReport.getLegacyId(), nsCrossReport.isFiledOutOfState(), method, informDate,
          county, governmentAgency));
    }
    return crossReports;
  }

  private String extractDateFromCrossReport(CrossReportIntake nsCrossReport) {
    String informDate = nsCrossReport.getInformDate();
    if (informDate.length() <= DomainChef.DATE_FORMAT.length()){
      Date date = DomainChef.uncookDateString(informDate);
      informDate = DomainChef.cookStrictTimestamp(date);
    } else {
      informDate = DomainChef
          .cookISO8601Timestamp(DomainChef.uncookStrictTimestampString(informDate));
    }
    return informDate;
  }

  private Integer setCommuncationMethod(CrossReportIntake nsCrossReport) {
    return StringUtils.isNotBlank(nsCrossReport.getMethod())
        ? IntakeCodeCache.global()
            .getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.CROSS_REPORT_METHOD,
                nsCrossReport.getMethod())
            .intValue()
        : null;
  }

}
