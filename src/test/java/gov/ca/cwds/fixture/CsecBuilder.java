package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.Csec;
import java.time.LocalDate;

/**
 * CWDS API Team
 */
public class CsecBuilder {
  private String id = "-1";
  private String csecCodeId = "6867";
  private String participantId = "-3";
  private LocalDate startDate = LocalDate.parse("2018-05-28");
  private LocalDate setEndDate = LocalDate.parse("2018-05-29");

  public Csec createCsec() {
    Csec csec = new Csec();
    csec.setId(id);
    csec.setCsecCodeId(csecCodeId);
    csec.setParticipantId(participantId);
    csec.setStartDate(startDate);
    csec.setEndDate(setEndDate);
    return csec;
  }
  
  public CsecBuilder setId(String id) {
    this.id = id;
    return this;
  }
  
  public CsecBuilder setCsecCodeId(String csecCodeId) {
    this.csecCodeId = csecCodeId;
    return this;
  }
  
  public CsecBuilder setParticipantId(String participantId) {
    this.participantId = participantId;
    return this;
  }
  
  public CsecBuilder setStartDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }
  
  public CsecBuilder setEndDate(LocalDate endDate) {
    this.setEndDate = endDate;
    return this;
  }
}
