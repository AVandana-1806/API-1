package gov.ca.cwds.rest.api.domain.hoi;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.util.FerbDateUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Screening for HOI.
 *
 * @author CWDS API Team
 */
public class HOIScreening extends ApiObjectIdentity implements ApiTypedIdentifier<String>, Request {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC Screening")
  private String name;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonProperty("start_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-08-23")
  private Date startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonProperty("end_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-09-23")
  private Date endDate;

  @JsonProperty("county")
  private SystemCodeDescriptor county;

  @JsonProperty("decision")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "promote to referral")
  private String decision;

  @JsonProperty("decision_detail")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "drug counseling")
  private String decisionDetail;

  @JsonProperty("reporter")
  private HOIReporter reporter;

  @JsonProperty("assigned_social_worker")
  private HOISocialWorker assignedSocialWorker;

  @JsonProperty("all_people")
  private Set<HOIPerson> allPeople = new HashSet<>();

  /**
   * No-argument constructor
   */
  public HOIScreening() {
    // No-argument constructor
  }

  /**
   * Construct from persistence class
   *
   * @param screeningEntity persistence level screening object
   */
  public HOIScreening(ScreeningEntity screeningEntity) {
    this.id = screeningEntity.getId();
    this.name = screeningEntity.getName();
    this.decision = screeningEntity.getScreeningDecision();
    this.decisionDetail = screeningEntity.getScreeningDecisionDetail();
    this.startDate = screeningEntity.getStartedAt();
    this.endDate = screeningEntity.getEndedAt();
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDecisionDetail() {
    return decisionDetail;
  }

  public void setDecisionDetail(String decisionDetail) {
    this.decisionDetail = decisionDetail;
  }

  public Date getStartDate() {
    return FerbDateUtils.freshDate(startDate);
  }

  public void setStartDate(Date startDate) {
    this.startDate = FerbDateUtils.freshDate(startDate);
  }

  public Date getEndDate() {
    return FerbDateUtils.freshDate(endDate);
  }

  public void setEndDate(Date endDate) {
    this.endDate = FerbDateUtils.freshDate(endDate);
  }

  public SystemCodeDescriptor getCounty() {
    return county;
  }

  public void setCounty(SystemCodeDescriptor county) {
    this.county = county;
  }

  public HOIReporter getReporter() {
    return reporter;
  }

  public void setReporter(HOIReporter reporter) {
    this.reporter = reporter;
  }

  public HOISocialWorker getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  public void setAssignedSocialWorker(HOISocialWorker assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
  }


  public String getDecision() {
    return decision;
  }

  public void setDecision(String decision) {
    this.decision = decision;
  }

  public Set<HOIPerson> getAllPeople() {
    return allPeople;
  }

  public void setAllPeople(Set<HOIPerson> allPeople) {
    this.allPeople = allPeople;
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
