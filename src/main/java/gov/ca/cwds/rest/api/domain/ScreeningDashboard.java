package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.ns.ScreeningWrapper;

import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Screening Summary.
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
@JsonSnakeCase
@JsonPropertyOrder({"id", "reference", "name", "screening_decision", "screening_decision_detail",
    "assignee", "assignee_staff_id", "started_at"})

public class ScreeningDashboard extends ReportingDomain {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1")
  private String id;

  @JsonProperty("reference")
  @ApiModelProperty(required = false, readOnly = false, value = "Screening Reference",
      example = "Screening Reference")
  private String reference;

  @JsonProperty("name")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Henderson Screening")
  private String name;

  @JsonProperty("assignee")
  @ApiModelProperty(required = false, readOnly = false, value = "Screening Assignee",
      example = "Test User")
  private String assignee;

  @JsonProperty("screening_decision")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "promote_to_referral")
  private String screeningDecision;

  @JsonProperty("screening_decision_detail")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "immediate")
  private String screeningDecisionDetail;

  @JsonProperty("assignee_staff_id")
  @ApiModelProperty(required = true, readOnly = false, value = "Screening Assignee Id",
      example = "aab")
  private String assigneeStaffId;

  @JsonProperty("started_at")
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "2018-03-029T16:48:05.457Z")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainObject.TIMESTAMP_ISO8601_FORMAT)
  private Date startedAt;

  @JsonProperty("screening_status")
  @ApiModelProperty(required = false, readOnly = false, value = "Screening status",
          example = "Open")
  private String screeningStatus;

  public ScreeningDashboard() {
    super();
  }

  /**
   * Constructor.
   * 
   * @param id - screening id
   * @param reference - reference
   * @param name - screening name
   * @param screeningDecision - screening decision
   * @param screeningDecisionDetail - screening decision detail
   * @param assignee - assignee
   * @param assigneeStaffId - assignee staff id
   */
  @JsonCreator
  public ScreeningDashboard(@JsonProperty("id") String id,
      @JsonProperty("reference") String reference, @JsonProperty("name") String name,
      @JsonProperty("screening_decision") String screeningDecision,
      @JsonProperty("screening_decision_detail") String screeningDecisionDetail,
      @JsonProperty("assignee") String assignee,
      @JsonProperty("assignee_staff_id") String assigneeStaffId) {
    super();
    this.id = id;
    this.reference = reference;
    this.name = name;
    this.screeningDecision = screeningDecision;
    this.screeningDecisionDetail = screeningDecisionDetail;
    this.assignee = assignee;
    this.assigneeStaffId = assigneeStaffId;
  }

  /**
   * Constructor using persistent screening wrapper.
   * 
   * @param screening - persistent screening wrapper
   */
  public ScreeningDashboard(ScreeningWrapper screening) {
    this.id = screening.getId();
    this.reference = screening.getReference();
    this.name = screening.getName();
    this.screeningDecision = screening.getScreeningDecision();
    this.screeningDecisionDetail = screening.getScreeningDecisionDetail();
    this.assignee = screening.getAssignee();
    this.assigneeStaffId = screening.getAssigneeStaffId();
    this.startedAt = screening.getStartedAt();
    this.screeningStatus = screening.getScreeningStatus();
  }

  public String getId() {
    return id;
  }

  public String getReference() {
    return reference;
  }

  public String getName() {
    return name;
  }

  public String getScreeningDecision() {
    return screeningDecision;
  }

  public String getScreeningDecisionDetail() {
    return screeningDecisionDetail;
  }

  public String getAssignee() {
    return assignee;
  }

  public String getAssigneeStaffId() {
    return assigneeStaffId;
  }

  public Date getStartedAt() {
    return freshDate(startedAt);
  }
  
  public void setStartedAt(Date startedAt) {
    this.startedAt = startedAt;
  }

  public String getScreeningStatus() {
    return screeningStatus;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }
}
