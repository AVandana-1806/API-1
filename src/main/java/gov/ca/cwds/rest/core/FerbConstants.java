package gov.ca.cwds.rest.core;

import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * Convenient dumping ground for common Ferb constants.
 *
 * @author CWDS API Team
 */
public class FerbConstants {

  public static class ReportType {

    public static final String SSB = "ssb";
    public static final String CSEC = "csec";

    private ReportType() {
      // no-op
    }

  }

  public static final boolean SENSITIVITY_OVERRIDE =
      DomainChef.uncookBooleanString(System.getProperty("OVERRIDE_CLIENT_SENSITIVITY"));

  private FerbConstants() {
    // no-op
  }

}
