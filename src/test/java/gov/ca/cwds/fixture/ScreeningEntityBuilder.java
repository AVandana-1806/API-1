package gov.ca.cwds.fixture;

import static gov.ca.cwds.rest.api.domain.DomainChef.uncookISO8601Timestamp;

import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("javadoc")
public class ScreeningEntityBuilder {
  public static String DEFAULT_ASSIGNEE_STAFF_ID = "0X5";

  private String id = null;
  private String reference = "screening reference";
  private String incidentCounty = "020";
  private LocalDate incidentDate;
  private String locationType = "1111";
  private String communicationMethod = "2222";
  private String name = "screening name";
  private String screeningDecision = "screening decision";
  private String screeningDecisionDetail = null;
  private Date startedAt;
  private Date endedAt;
  private String narrative = "screening narrative";
  private String assigneeStaffId = DEFAULT_ASSIGNEE_STAFF_ID;
  private Set<ParticipantEntity> participants = new HashSet<>();
  private String reportType = "ssb";
  private String screeningStatus = "Open";
  private String screeningContactReference = "1234-5678-9ABC-DEFGHIJ";

  public ScreeningEntity build() {
    ScreeningEntity screeningEntity = new ScreeningEntity();
    screeningEntity.setId(id);
    screeningEntity.setReference(reference);
    screeningEntity.setStartedAt(startedAt);
    screeningEntity.setEndedAt(endedAt);
    screeningEntity.setIncidentCounty(incidentCounty);
    screeningEntity.setIncidentDate(incidentDate);
    screeningEntity.setLocationType(locationType);
    screeningEntity.setCommunicationMethod(communicationMethod);
    screeningEntity.setName(name);
    screeningEntity.setScreeningDecision(screeningDecision);
    screeningEntity.setScreeningDecisionDetail(screeningDecisionDetail);
    screeningEntity.setNarrative(narrative);
    screeningEntity.setAssigneeStaffId(assigneeStaffId);
    screeningEntity.setParticipants(participants);
    screeningEntity.setReportType(reportType);
    screeningEntity.setScreeningStatus(screeningStatus);
    screeningEntity.setScreeningContactReference(screeningContactReference);
    return screeningEntity;
  }

  public ScreeningEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public ScreeningEntityBuilder setReference(String reference) {
    this.reference = reference;
    return this;
  }

  public ScreeningEntityBuilder setEndedAt(Date endedAt) {
    this.endedAt = endedAt;
    return this;
  }

  public ScreeningEntityBuilder setEndedAt(String endedAt) {
    this.endedAt = uncookISO8601Timestamp(endedAt);
    return this;
  }

  public ScreeningEntityBuilder setIncidentCounty(String incidentCounty) {
    this.incidentCounty = incidentCounty;
    return this;
  }

  public ScreeningEntityBuilder setIncidentDate(Date incidentDate) {
    this.incidentDate = new java.sql.Date(incidentDate.getTime()).toLocalDate();
    return this;
  }

  public ScreeningEntityBuilder setCommunicationMethod(String communicationMethod) {
    this.communicationMethod = communicationMethod;
    return this;
  }

  public ScreeningEntityBuilder setName(String name) {
    this.name = name;
    return this;
  }


  public ScreeningEntityBuilder setScreeningDecision(String screeningDecision) {
    this.screeningDecision = screeningDecision;
    return this;
  }

  public ScreeningEntityBuilder setScreeningDecisionDetail(String screeningDecisionDetail) {
    this.screeningDecisionDetail = screeningDecisionDetail;
    return this;
  }

  public ScreeningEntityBuilder setStartedAt(Date startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  public ScreeningEntityBuilder setStartedAt(String startedAt) {
    this.startedAt = uncookISO8601Timestamp(startedAt);
    return this;
  }

  public ScreeningEntityBuilder setNarrative(String narrative) {
    this.narrative = narrative;
    return this;
  }

  public ScreeningEntityBuilder setScreeningStatus(String status) {
    this.screeningStatus = status;
    return this;
  }

  public ScreeningEntityBuilder addParticipant(ParticipantEntity participant) {
    this.participants.add(participant);
    return this;
  }
}
