package gov.ca.cwds.rest.resources.parameter;

import java.io.Serializable;

/**
 * CWDS API Team
 */
public class ScreeningParticipantResourceParameters implements Serializable {
  private static final long serialVersionUID = 1L;

  private String screeningId;
  private String participantId;

  public ScreeningParticipantResourceParameters(String screeningId, String participantId) {
    this.screeningId = screeningId;
    this.participantId = participantId;
  }

  public String getScreeningId() {
    return screeningId;
  }

  public String getParticipantId() {
    return participantId;
  }
}
