package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * CWDS API Team
 */
public class CrossReportDroolsConfiguration extends DroolsConfiguration<CrossReport> {

  public static final CrossReportDroolsConfiguration INSTANCE =
      new CrossReportDroolsConfiguration("cross-report-session",
          "cross-report-agenda", "rules/cross-report");

  private CrossReportDroolsConfiguration(String rulesSession, String rulesAgenda,
      String kieContainerId) {
    super(rulesSession, rulesAgenda, kieContainerId);
  }

}