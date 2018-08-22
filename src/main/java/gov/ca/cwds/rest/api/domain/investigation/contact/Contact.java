package gov.ca.cwds.rest.api.domain.investigation.contact;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.IndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.util.CmsRecordUtils;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Contact
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonInclude(Include.ALWAYS)
@JsonPropertyOrder({"legacy_descriptor", "lastUpdatedBy", "staffName", "startedAt", "endedAt",
    "purpose", "communicationMethod", "status", "services", "location", "note", "people"})
public class Contact extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @NotNull
  @JsonProperty("legacy_descriptor")
  private CmsRecordDescriptor legacyDescriptor;

  @JsonProperty("last_updated_by")
  @ApiModelProperty(required = false, readOnly = false// )
      , value = "primary contact staff person legacy_descriptor")
  private LastUpdatedBy lastUpdatedBy;

  @NotEmpty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainObject.TIMESTAMP_ISO8601_FORMAT,
      timezone = "UTC")
  @JsonProperty("started_at")
  @gov.ca.cwds.rest.validation.Date(format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
      example = "2010-04-27T23:30:14.000Z")
  private Date startedAt;

  @NotEmpty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainObject.TIMESTAMP_ISO8601_FORMAT,
      timezone = "UTC")
  @JsonProperty("ended_at")
  @gov.ca.cwds.rest.validation.Date(format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
      example = "2010-04-28T23:30:14.000Z")
  private Date endedAt;

  @JsonProperty("purpose")
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.CONTACT_TYPE)
  @ApiModelProperty(required = false, readOnly = false,
      value = "Delivered Service serviceContactType system code ID e.g)  -> 433 Conduct Client Evaluation ",
      example = "433")
  private String purpose;

  @JsonProperty("communication_method")
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.COMMUNICATION_METHOD)
  @ApiModelProperty(required = false, readOnly = false,
      value = "Delivered service communication method type system code ID e.g) 408 -> In-Person",
      example = "408")
  private String communicationMethod;

  @NotEmpty
  @JsonProperty("status")
  @Size(min = 1, max = 1)
  @OneOf(value = {"C", "A", "S"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "C")
  private String status;


  @JsonProperty("services")
  // need clarification
  @ApiModelProperty(required = false, readOnly = false)
  private Set<Integer> services;

  @JsonProperty("location")
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.CONTACT_LOCATION)
  @ApiModelProperty(required = false, readOnly = false,
      value = "Delivered Service contactLocationType system code ID e.g) 415 -> CWS Office",
      example = "415")
  private String location;

  @JsonProperty("note")
  @Size(max = 8000)
  @ApiModelProperty(required = false, readOnly = false,
      value = "The contact narrative that is entered in two rows in Long Text Table",
      example = "detail text")
  private String note;

  @ApiModelProperty(required = false, readOnly = false)
  @JsonProperty("people")
  private Map<String, IndividualDeliveredService> people;

  /**
   * @param legacyDescriptor the CmsRecordDescriptor
   * @param lastUpdatedBy last updated by staff
   * @param startedAt started at
   * @param endedAt ended at
   * @param purpose purpose
   * @param communicationMethod communication method
   * @param status status
   * @param services services
   * @param location location
   * @param note note
   * @param people people
   */
  @JsonCreator
  public Contact(@JsonProperty("legacy_descriptor") CmsRecordDescriptor legacyDescriptor,
      @JsonProperty("last_updated_by") LastUpdatedBy lastUpdatedBy,
      @JsonProperty("started_at") Date startedAt, @JsonProperty("ended_at") Date endedAt,
      @JsonProperty("purpose") String purpose,
      @JsonProperty("communication_method") String communicationMethod,
      @JsonProperty("status") String status, @JsonProperty("services") Set<Integer> services,
      @JsonProperty("location") String location, @JsonProperty("note") String note,
      @JsonProperty("people") Map<String, IndividualDeliveredService> people) {
    super();
    this.legacyDescriptor = legacyDescriptor;
    this.lastUpdatedBy = lastUpdatedBy;
    this.startedAt = freshDate(startedAt);
    this.endedAt = freshDate(endedAt);
    this.purpose = purpose;
    this.communicationMethod = communicationMethod;
    this.status = status;
    this.services = services;
    this.location = location;
    this.note = note;
    this.people = people;
  }

  /**
   * @param persistedDeliverdService - persistedDeliverdService
   * @param lastUpdatedBy - lastUpdatedBy
   * @param note - note
   * @param people - people
   */
  public Contact(DeliveredServiceEntity persistedDeliverdService, LastUpdatedBy lastUpdatedBy,
      String note, Map<String, IndividualDeliveredService> people) {
    super();
    this.legacyDescriptor = CmsRecordUtils.createLegacyDescriptor(persistedDeliverdService.getId(),
        LegacyTable.DELIVERED_SERVICE);
    this.lastUpdatedBy = lastUpdatedBy;
    Date startDate = persistedDeliverdService.getStartDate();
    if (startDate != null) {
      this.startedAt =
          DomainChef.concatenateDateAndTime(startDate, persistedDeliverdService.getStartTime());
    }
    Date endDate = persistedDeliverdService.getEndDate();
    if (endDate != null) {
      this.endedAt =
          DomainChef.concatenateDateAndTime(endDate, persistedDeliverdService.getEndTime());
    }
    this.purpose = String.valueOf(persistedDeliverdService.getServiceContactType());
    this.communicationMethod = String.valueOf(persistedDeliverdService.getCommunicationMethodType());
    this.status = persistedDeliverdService.getStatusCode();
    this.services = null;
    this.location = String.valueOf(persistedDeliverdService.getContactLocationType());
    this.note = note;
    this.people = people;
  }

  public Contact() {
    // default
  }

  /**
   * @return the legacy_descriptor
   */
  public CmsRecordDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * @return the lastUpdatedBy
   */
  public LastUpdatedBy getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  /**
   * @return the startedAt
   */
  public Date getStartedAt() {
    return freshDate(startedAt);
  }

  /**
   * @return the endedAt
   */
  public Date getEndedAt() {
    return freshDate(endedAt);
  }

  /**
   * @return the purpose
   */
  public String getPurpose() {
    return purpose;
  }

  /**
   * @return the communicationMethod
   */
  public String getCommunicationMethod() {
    return communicationMethod;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @return the services
   */
  public Set<Integer> getServices() {
    return services;
  }

  /**
   * @return the location
   */
  public String getLocation() {
    return location;
  }

  /**
   * @return the note
   */
  public String getNote() {
    return note;
  }


  public Map<String, IndividualDeliveredService> getPeople() {
    return people;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
