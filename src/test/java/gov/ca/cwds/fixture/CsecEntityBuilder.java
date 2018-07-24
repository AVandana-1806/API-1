package gov.ca.cwds.fixture;

import java.time.LocalDate;
import gov.ca.cwds.data.persistence.ns.CsecEntity;

public class CsecEntityBuilder {

  private Integer id = 1234;
  private String participantId = "1234567ABC";
  private String csecCodeId = "2345";
  private LocalDate startDate = LocalDate.parse("2018-05-28");
  private LocalDate endDate = LocalDate.parse("2018-07-28");
  
  public CsecEntity build() {
     CsecEntity csecEntity = new CsecEntity();
     csecEntity.setId(id);
     csecEntity.setParticipantId(participantId);
     csecEntity.setCsecCodeId(csecCodeId);
     csecEntity.setStartDate(startDate);
     csecEntity.setEndDate(endDate);
     return csecEntity;
  }
  
  public CsecEntityBuilder setId(Integer id) {
    this.id = id;
    return this;
  }
  
  public CsecEntityBuilder setParticipantId(String participantId) {
    this.participantId = participantId;
    return this;
  }
  
  public CsecEntityBuilder setCsecCodeId(String csecCodeId) {
    this.csecCodeId = csecCodeId;
    return this;
  }
  
  public CsecEntityBuilder setStartDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }
  
  public CsecEntityBuilder setEndDate(LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }
}
