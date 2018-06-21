package gov.ca.cwds.data.persistence.ns;

import gov.ca.cwds.data.persistence.PersistentObject;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@NamedQuery(name = "gov.ca.cwds.rest.api.persistence.ns.ContactEntity.findAll", query = "FROM ContactEntity")

@Entity
@Table(name = "contacts")
@SuppressWarnings({"squid:S3437"})
public class ContactEntity implements PersistentObject {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "screening_id")
  private String screeningId;

  @Column(name = "started_at")
  private LocalDateTime startedAt;

  @Column(name = "ended_at")
  private LocalDateTime endedAt;

  @Column(name = "purpose")
  private String purpose;

  @Column(name = "communication_method")
  private String communicationMethod;

  @Column(name = "status")
  private String status;

  @Column(name = "service")
  private Integer service;

  @Column(name = "location")
  private String location;

  @Column(name = "note")
  private String note;

  public ContactEntity() {
    //Empty constructor
  }

  public ContactEntity(
      String screeningId,
      LocalDateTime startedAt,
      LocalDateTime endedAt,
      String purpose,
      String communicationMethod,
      String status,
      Integer service,
      String location,
      String note) {
    this.screeningId = screeningId;
    this.startedAt = startedAt;
    this.endedAt = endedAt;
    this.purpose = purpose;
    this.communicationMethod = communicationMethod;
    this.status = status;
    this.service = service;
    this.location = location;
    this.note = note;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getScreeningId() {
    return screeningId;
  }

  public void setScreeningId(String screeningId) {
    this.screeningId = screeningId;
  }

  public LocalDateTime getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(LocalDateTime startedAt) {
    this.startedAt = startedAt;
  }

  public LocalDateTime getEndedAt() {
    return endedAt;
  }

  public void setEndedAt(LocalDateTime endedAt) {
    this.endedAt = endedAt;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public String getCommunicationMethod() {
    return communicationMethod;
  }

  public void setCommunicationMethod(String communicationMethod) {
    this.communicationMethod = communicationMethod;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getService() {
    return service;
  }

  public void setService(Integer service) {
    this.service = service;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

}
