package gov.ca.cwds.rest.api.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a screening.
 *
 * @author CWDS API Team
 */
@JsonSnakeCase
@ApiModel("Screening")
@SuppressWarnings({"squid:S3437"})
public class Screening extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @Size(min = 1, max = 50)
  @ApiModelProperty(value = "Screening ID", example = "ABC1234568")
  private String id;

  @JsonProperty("referral_id")
  @Size(min = 10, max = 10)
  @ApiModelProperty(value = "Referral ID", example = "ABC1234568")
  private String referralId;

  @JsonProperty("name")
  @ApiModelProperty(value = "Screening Name", example = "Some Screening name")
  private String name;

  @JsonProperty("reference")
  @ApiModelProperty(value = "Screening Reference", example = "Screening Reference")
  private String reference;

  @JsonProperty("screening_decision")
  @ApiModelProperty(value = "Screening Decision", example = "Screening Decision")
  private String screeningDecision;

  @JsonProperty("screening_decision_detail")
  @ApiModelProperty(value = "Screening Decision Detail", example = "Screening Decision Detail")
  private String screeningDecisionDetail;

  @JsonProperty("assignee")
  @ApiModelProperty(value = "Screening Assignee", example = "Screening Assignee")
  private String assignee;

  @JsonProperty("assignee_staff_id")
  @ApiModelProperty(value = "Screening Assignee Id", example = "con")
  private String assigneeStaffId;

  @JsonProperty("started_at")
  @ApiModelProperty(value = "Screening Start Date", example = "2018-03-29T16:11:59")
  private LocalDateTime startedAt;

  @JsonProperty("ended_at")
  @ApiModelProperty(value = "Screening End Date", example = "2018-03-30T16:11:37")
  private LocalDateTime endedAt;

  @JsonProperty("additional_information")
  @ApiModelProperty("Additional screening information")
  private String additionalInformation;

  @JsonProperty("incident_county")
  @ApiModelProperty("Incident county")
  private String incidentCounty;

  @JsonProperty("incident_date")
  @ApiModelProperty(value = "Incident Date", example = "1992-05-18")
  private LocalDate incidentDate;

  @JsonProperty("indexable")
  @ApiModelProperty(value = "Indexable", example = "true")
  private Boolean indexable;

  @JsonProperty("location_type")
  @ApiModelProperty(value = "Location Type", example = "Child's Home")
  private String locationType;

  @JsonProperty("current_location_of_children")
  @ApiModelProperty(value = "Location Of Children", example = "Home with father mother")
  private String currentLocationOfChildren;

  @JsonProperty("communication_method")
  @ApiModelProperty(value = "Communication Method", example = "email")
  private String communicationMethod;

  @JsonProperty("access_restrictions")
  @ApiModelProperty(value = "Access Restrictions", example = "sensitive")
  private String accessRestrictions;

  @JsonProperty("restrictions_date")
  @ApiModelProperty(value = "Restrictions Date", example = "2001-01-01")
  private LocalDate restrictionsDate;

  @JsonProperty("restrictions_rationale")
  @ApiModelProperty(value = "Restrictions Rationale", example = "string")
  private String restrictionsRationale;

  @JsonProperty("report_narrative")
  @ApiModelProperty(value = "Report Narrative", example = "This is a test narrative")
  private String reportNarrative;

  @JsonProperty("safety_alerts")
  @ApiModelProperty(value = "Safety Alerts", example = "Dangerous Animal on Premises")
  private Set<String> safetyAlerts = new HashSet<>();

  @JsonProperty("safety_information")
  @ApiModelProperty(value = "Safety Information", example = "string")
  private String safetyInformation;

  @JsonProperty("cross_reports")
  @ApiModelProperty(value = "Cross Reports")
  private Set<CrossReportIntake> crossReports = new HashSet<>();

  @JsonProperty("incident_address")
  @ApiModelProperty(value = "Addresses")
  private AddressIntakeApi incidentAddress;

  @JsonProperty("allegations")
  @ApiModelProperty(value = "Allegations")
  private Set<AllegationIntake> allegations = new HashSet<>();

  @JsonProperty("participants")
  @ApiModelProperty(value = "Participants")
  private Set<ParticipantIntakeApi> participantIntakeApis = new HashSet<>();

  @JsonProperty("report_type")
  private String reportType;

  @JsonProperty("screening_status")
  private String screeningStatus;

  @JsonProperty("screening_contact_reference")
  private String screeningContactReference;

  /**
   * default constructor
   */
  public Screening() {
    super();
  }

  /**
   * @param id - id
   * @param name - name
   * @param reference - reference
   * @param screeningDecision - screeningDecision
   * @param screeningDecisionDetail - screeningDecisionDetail
   * @param assignee - assignee
   * @param startedAt - startedAt
   * @param referralId referral id, if provided
   * @param assigneeStaffId - assignee Id
   * @param reportType - report type
   * @param screeningStatus - screening status
   * @param screeningContactReference - screening contact reference
   */
  @SuppressWarnings("squid:S00107")
  public Screening(String id, String name, String reference, String screeningDecision,
      String screeningDecisionDetail, String assignee, LocalDateTime startedAt, String referralId,
      String assigneeStaffId, String reportType, String screeningStatus,
      String screeningContactReference) {
    super();
    this.id = id;
    this.name = name;
    this.reference = reference;
    this.screeningDecision = screeningDecision;
    this.screeningDecisionDetail = screeningDecisionDetail;
    this.assignee = assignee;
    this.startedAt = startedAt;
    this.referralId = referralId;
    this.assigneeStaffId = assigneeStaffId;
    this.reportType = reportType;
    this.screeningStatus = screeningStatus;
    this.screeningContactReference = screeningContactReference;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @return the screeningDecision
   */
  public String getScreeningDecision() {
    return screeningDecision;
  }

  /**
   * @return the screeningDecisionDetail
   */
  public String getScreeningDecisionDetail() {
    return screeningDecisionDetail;
  }

  /**
   * @return the assignee
   */
  public String getAssignee() {
    return assignee;
  }

  /**
   * @return the startedAt
   */
  public LocalDateTime getStartedAt() {
    return startedAt;
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the assigneeStaffId
   */
  public String getAssigneeStaffId() {
    return assigneeStaffId;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setReferralId(String referralId) {
    this.referralId = referralId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public void setScreeningDecision(String screeningDecision) {
    this.screeningDecision = screeningDecision;
  }

  public void setScreeningDecisionDetail(String screeningDecisionDetail) {
    this.screeningDecisionDetail = screeningDecisionDetail;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public void setAssigneeStaffId(String assigneeStaffId) {
    this.assigneeStaffId = assigneeStaffId;
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

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public String getIncidentCounty() {
    return incidentCounty;
  }

  public void setIncidentCounty(String incidentCounty) {
    this.incidentCounty = incidentCounty;
  }

  public LocalDate getIncidentDate() {
    return incidentDate;
  }

  public void setIncidentDate(LocalDate incidentDate) {
    this.incidentDate = incidentDate;
  }

  public Boolean getIndexable() {
    return indexable;
  }

  public void setIndexable(Boolean indexable) {
    this.indexable = indexable;
  }

  public String getLocationType() {
    return locationType;
  }

  public void setLocationType(String locationType) {
    this.locationType = locationType;
  }

  public String getCommunicationMethod() {
    return communicationMethod;
  }

  public void setCommunicationMethod(String communicationMethod) {
    this.communicationMethod = communicationMethod;
  }

  public String getAccessRestrictions() {
    return accessRestrictions;
  }

  public void setAccessRestrictions(String accessRestrictions) {
    this.accessRestrictions = accessRestrictions;
  }

  public LocalDate getRestrictionsDate() {
    return restrictionsDate;
  }

  public void setRestrictionsDate(LocalDate restrictionsDate) {
    this.restrictionsDate = restrictionsDate;
  }

  public String getRestrictionsRationale() {
    return restrictionsRationale;
  }

  public void setRestrictionsRationale(String restrictionsRationale) {
    this.restrictionsRationale = restrictionsRationale;
  }

  public String getReportNarrative() {
    return reportNarrative;
  }

  public void setReportNarrative(String reportNarrative) {
    this.reportNarrative = reportNarrative;
  }

  public Set<String> getSafetyAlerts() {
    return safetyAlerts;
  }

  public void setSafetyAlerts(Set<String> safetyAlerts) {
    this.safetyAlerts = safetyAlerts;
  }

  public String getSafetyInformation() {
    return safetyInformation;
  }

  public void setSafetyInformation(String safetyInformation) {
    this.safetyInformation = safetyInformation;
  }

  public Set<CrossReportIntake> getCrossReports() {
    return crossReports;
  }

  public AddressIntakeApi getIncidentAddress() {
    return incidentAddress;
  }

  public void setIncidentAddress(AddressIntakeApi incidentAddress) {
    this.incidentAddress = incidentAddress;
  }

  public Set<AllegationIntake> getAllegations() {
    return allegations;
  }

  public Set<ParticipantIntakeApi> getParticipantIntakeApis() {
    return participantIntakeApis;
  }

  public void setParticipantIntakeApis(Set<ParticipantIntakeApi> participantIntakeApis) {
    this.participantIntakeApis = participantIntakeApis;
  }

  public String getReportType() {
    return reportType;
  }

  public void setReportType(String reportType) {
    this.reportType = reportType;
  }

  public String getScreeningStatus() {
    return screeningStatus;
  }

  public void setScreeningStatus(String screeningStatus) {
    this.screeningStatus = screeningStatus;
  }

  public String getScreeningContactReference() {
    return screeningContactReference;
  }

  public void setScreeningContactReference(String screeningContactReference) {
    this.screeningContactReference = screeningContactReference;
  }

  /**
   * @return the currentLocationOfChildren
   */
  public String getCurrentLocationOfChildren() {
    return currentLocationOfChildren;
  }

  /**
   * @param currentLocationOfChildren the currentLocationOfChildren to set
   */
  public void setCurrentLocationOfChildren(String currentLocationOfChildren) {
    this.currentLocationOfChildren = currentLocationOfChildren;
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
