package gov.ca.cwds.data.persistence.ns;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;
import static org.hibernate.annotations.CascadeType.DELETE;
import static org.hibernate.annotations.CascadeType.LOCK;
import static org.hibernate.annotations.CascadeType.MERGE;
import static org.hibernate.annotations.CascadeType.PERSIST;
import static org.hibernate.annotations.CascadeType.REFRESH;
import static org.hibernate.annotations.CascadeType.REMOVE;
import static org.hibernate.annotations.CascadeType.REPLICATE;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing Screening.
 *
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.ScreeningEntity.findScreeningsByReferralId",
    query = "FROM ScreeningEntity WHERE referralId = :referralId")
@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.ScreeningEntity.findScreeningsByClientIds",
    query = "SELECT s FROM ScreeningEntity s JOIN s.participants p WHERE p.legacyId IN :clientIds")
@Entity
@Table(name = "screenings")
public class ScreeningEntity implements PersistentObject {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "screenings_id_seq")
  @GenericGenerator(name = "screenings_id_seq",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name",
          value = "screenings_id_seq")})
  private String id;

  @Column(name = "reference")
  private String reference;

  @Column(name = "ended_at")
  private Date endedAt;

  @Column(name = "incident_county")
  private String incidentCounty;

  @SuppressWarnings("squid:S3437")
  @Column(name = "incident_date")
  private LocalDate incidentDate;

  @Column(name = "location_type")
  private String locationType;

  @Column(name = "communication_method")
  private String communicationMethod;

  @Column(name = "name")
  private String name;

  @Column(name = "screening_decision")
  private String screeningDecision;

  @Column(name = "started_at")
  private Date startedAt;

  @Column(name = "report_narrative")
  private String narrative;

  @Column(name = "assignee")
  private String assignee;

  @Column(name = "additional_information")
  private String additionalInformation;

  @Column(name = "screening_decision_detail")
  private String screeningDecisionDetail;

  @Column(name = "safety_information")
  private String safetyInformation;

  @Column(name = "safety_alerts")
  @Type(type = "gov.ca.cwds.data.persistence.hibernate.StringArrayType")
  private String[] safetyAlerts;

  @Column(name = "referral_id")
  private String referralId;

  @Column(name = "assignee_staff_id")
  private String assigneeStaffId;

  @Column(name = "access_restrictions")
  private String accessRestrictions;

  @Column(name = "restrictions_rationale")
  private String restrictionsRationale;

  @Column(name = "user_county_code")
  private Integer userCountyCode;

  @Column(name = "restrictions_date")
  @Type(type = "date")
  private Date restrictionsDate;

  @Column(name = "indexable")
  private Boolean indexable;

  @Column(name = "curr_loc_of_children")
  private String currentLocationOfChildren;

  @HashCodeExclude
  @OneToMany(mappedBy = "screeningEntity")
  @Cascade({PERSIST, MERGE, SAVE_UPDATE, DELETE, LOCK, REFRESH, REMOVE, REPLICATE})
  private Set<Allegation> allegations = new HashSet<>();

  @HashCodeExclude
  @OneToMany(mappedBy = "screeningEntity")
  @Cascade({PERSIST, MERGE, SAVE_UPDATE, DELETE, LOCK, REFRESH, REMOVE, REPLICATE})
  private Set<ParticipantEntity> participants = new HashSet<>();

  @Column(name = "report_type")
  private String reportType;

  @Column(name = "screening_status")
  private String screeningStatus;

  @Column(name = "screening_contact_reference")
  private String screeningContactReference;

  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public ScreeningEntity() {
    super();
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public void setEndedAt(Date endedAt) {
    this.endedAt = freshDate(endedAt);
  }

  public void setIncidentCounty(String incidentCounty) {
    this.incidentCounty = incidentCounty;
  }

  public void setIncidentDate(LocalDate incidentDate) {
    this.incidentDate = incidentDate;
  }

  public void setLocationType(String locationType) {
    this.locationType = locationType;
  }

  public void setCommunicationMethod(String communicationMethod) {
    this.communicationMethod = communicationMethod;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setScreeningDecision(String screeningDecision) {
    this.screeningDecision = screeningDecision;
  }

  public void setStartedAt(Date startedAt) {
    this.startedAt = freshDate(startedAt);
  }

  public void setNarrative(String narrative) {
    this.narrative = narrative;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public void setScreeningDecisionDetail(String screeningDecisionDetail) {
    this.screeningDecisionDetail = screeningDecisionDetail;
  }

  public void setSafetyInformation(String safetyInformation) {
    this.safetyInformation = safetyInformation;
  }

  public void setSafetyAlerts(String[] safetyAlerts) {
    if (safetyAlerts == null) {
      this.safetyAlerts = new String[0];
    } else {
      this.safetyAlerts = Arrays.copyOf(safetyAlerts, safetyAlerts.length);
    }
  }

  public void setReferralId(String referralId) {
    this.referralId = referralId;
  }

  public void setAssigneeStaffId(String assigneeStaffId) {
    this.assigneeStaffId = assigneeStaffId;
  }

  public void setRestrictionsRationale(String restrictionsRationale) {
    this.restrictionsRationale = restrictionsRationale;
  }

  public void setUserCountyCode(Integer userCountyCode) {
    this.userCountyCode = userCountyCode;
  }

  public void setRestrictionsDate(Date restrictionsDate) {
    this.restrictionsDate = freshDate(restrictionsDate);
  }

  public Boolean getIndexable() {
    return indexable;
  }

  public void setIndexable(Boolean indexable) {
    this.indexable = indexable;
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
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @return the endedAt
   */
  public Date getEndedAt() {
    return freshDate(endedAt);
  }

  /**
   * @return the incidentCounty
   */
  public String getIncidentCounty() {
    return incidentCounty;
  }

  /**
   * @return the incidentDate
   */
  public LocalDate getIncidentDate() {
    return incidentDate;
  }

  /**
   * @return the locationType
   */
  public String getLocationType() {
    return locationType;
  }

  /**
   * @return the communicationMethod
   */
  public String getCommunicationMethod() {
    return communicationMethod;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }


  /**
   * @return the screeningDecision
   */
  public String getScreeningDecision() {
    return screeningDecision;
  }

  /**
   * @return the startedAt
   */
  public Date getStartedAt() {
    return freshDate(startedAt);
  }

  /**
   * @return the narrative
   */
  public String getNarrative() {
    return narrative;
  }

  /**
   * @return the assignee
   */
  public String getAssignee() {
    return assignee;
  }

  /**
   * @return the additionalInformation
   */
  public String getAdditionalInformation() {
    return additionalInformation;
  }

  /**
   * @return the screeningDecisionDetail
   */
  public String getScreeningDecisionDetail() {
    return screeningDecisionDetail;
  }

  /**
   * @return the safetyInformation
   */
  public String getSafetyInformation() {
    return safetyInformation;
  }

  /**
   * @return the safetyAlerts
   */
  public String[] getSafetyAlerts() {
    if (safetyAlerts == null) {
      return new String[0];
    }
    return Arrays.copyOf(safetyAlerts, safetyAlerts.length);
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

  /**
   * @return the accessRestrictions
   */
  public String getAccessRestrictions() {
    return accessRestrictions;
  }

  /**
   * Sets access restriction
   *
   * @param accessRestrictions The access restriction
   */
  public void setAccessRestrictions(String accessRestrictions) {
    this.accessRestrictions = accessRestrictions;
  }

  /**
   * @return the restrictionsRationale
   */
  public String getRestrictionsRationale() {
    return restrictionsRationale;
  }

  /**
   * @return the userCountyCode
   */
  public Integer getUserCountyCode() {
    return userCountyCode;
  }

  /**
   * @return the restrictionsDate
   */
  public Date getRestrictionsDate() {
    return freshDate(restrictionsDate);
  }

  /**
   * @return the indexable
   */
  public Boolean isIndexable() {
    return indexable;
  }

  /**
   * @return the allegations
   */
  public Set<Allegation> getAllegations() {
    return allegations;
  }

  public Set<ParticipantEntity> getParticipants() {
    return participants;
  }

  public void setParticipants(Set<ParticipantEntity> participants) {
    this.participants = participants;
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
