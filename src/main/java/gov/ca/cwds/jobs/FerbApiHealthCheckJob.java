package gov.ca.cwds.jobs;

/**
 * Health check job reporting events to NewRelic.
 * 
 * @author CWDS API Team
 */
public final class FerbApiHealthCheckJob extends NewRelicReportingHealthCheckJob {

  private static final String HEALTH_CHECK_EVENT = "FerbApiHealthCheck";

  @Override
  public String getEventType() {
    return HEALTH_CHECK_EVENT;
  }

}
