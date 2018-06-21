package gov.ca.cwds.rest.api.domain.investigation.contact;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;

/**
 * {@link DomainObject} representing a Contact Request
 *
 * @author CWDS API Team
 */
@JsonSnakeCase
@SuppressWarnings({"squid:S3437"})
public class ContactIntake implements Request{

  @JsonProperty("screening_id")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "1")
  private String screeningId;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  @JsonProperty("started_at")
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
          example = "2010-04-27T23:30:14.000Z")
  private LocalDateTime startedAt;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  @JsonProperty("ended_at")
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
          example = "2010-04-28T23:30:14.000Z")
  private LocalDateTime endedAt;

  @JsonProperty("purpose")
  @ApiModelProperty(required = true, readOnly = false,
          value = "Delivered service contact type system code ID e.g)  -> ", example = "433")
  private String purpose;

  @JsonProperty("communication_method")
  @ApiModelProperty(required = true, readOnly = false,
          value = "Delivered service communication method type system code ID e.g) 408 -> In-Person",
          example = "408")
  private String communicationMethod;

  @NotEmpty
  @JsonProperty("status")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "C")
  private String status;

  @JsonProperty("location")
  @ApiModelProperty(required = true, readOnly = false,
          value = "Delivered service contact location type system code ID e.g) 415 -> CWS Office",
          example = "415")
  private String location;

  @JsonProperty("note")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "detail text")
  private String note;

  @JsonProperty("service")
  @ApiModelProperty(required = false, readOnly = false)
  private Integer service;

  @JsonCreator
  public ContactIntake(
                 @JsonProperty("screening_id") String screeningId,
                 @JsonProperty("started_at") LocalDateTime startedAt,
                 @JsonProperty("ended_at") LocalDateTime endedAt,
                 @JsonProperty("purpose") String purpose,
                 @JsonProperty("communication_method") String communicationMethod,
                 @JsonProperty("status") String status,
                 @JsonProperty("services") Integer service,
                 @JsonProperty("location") String location,
                 @JsonProperty("note") String note) {
    super();
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

  public ContactIntake() {
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

  public Integer getService() {
    return service;
  }

  public void setService(Integer service) {
    this.service = service;
  }

  public String getScreeningId() {
    return screeningId;
  }

  public void setScreeningId(String screeningId) {
    this.screeningId = screeningId;
  }
}
