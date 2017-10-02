package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class GovernmentOrganizationCrossReportEntityBuilder {

  String thirdId = "AbalBln0Ki";
  String countySpecificCode = "99";
  String crossReportThirdId = "LikGcFD0Ki";
  String referralId = "RI1Wuve0Ki";
  String governmentOrganizationId = "CS90ZBR01c";
  String organizationTypeInd = "D";

  public GovernmentOrganizationCrossReport build() {
    return new GovernmentOrganizationCrossReport(thirdId, countySpecificCode, crossReportThirdId,
        referralId, governmentOrganizationId, organizationTypeInd);
  }

  public String getThirdId() {
    return thirdId;
  }

  public GovernmentOrganizationCrossReportEntityBuilder setThirdId(String thirdId) {
    this.thirdId = thirdId;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public GovernmentOrganizationCrossReportEntityBuilder setCountySpecificCode(
      String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public String getCrossReportThirdId() {
    return crossReportThirdId;
  }

  public GovernmentOrganizationCrossReportEntityBuilder setCrossReportThirdId(
      String crossReportThirdId) {
    this.crossReportThirdId = crossReportThirdId;
    return this;
  }

  public String getReferralId() {
    return referralId;
  }

  public GovernmentOrganizationCrossReportEntityBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  public String getGovernmentOrganizationId() {
    return governmentOrganizationId;
  }

  public GovernmentOrganizationCrossReportEntityBuilder setGovernmentOrganizationId(
      String governmentOrganizationId) {
    this.governmentOrganizationId = governmentOrganizationId;
    return this;
  }

  public String getOrganizationTypeInd() {
    return organizationTypeInd;
  }

  public GovernmentOrganizationCrossReportEntityBuilder setOrganizationTypeInd(
      String organizationTypeInd) {
    this.organizationTypeInd = organizationTypeInd;
    return this;
  }

}
