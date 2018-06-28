package gov.ca.cwds.rest.core;

import gov.ca.cwds.rest.api.domain.DomainChef;

public class FerbConstants {

  public static class ReportType {
    private ReportType() {
      // no-op
    }

    public static final String SSB = "ssb";
    public static final String CSEC = "csec";
  }

  public static final boolean SENSITIVITY_OVERRIDE =
      DomainChef.uncookBooleanString(System.getProperty("OVERRIDE_CLIENT_SENSITIVITY"));

  private FerbConstants() {
    // no-op
  }

}
