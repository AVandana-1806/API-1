package gov.ca.cwds.data.persistence.ns;

import static gov.ca.cwds.data.persistence.ns.ScreeningWrapper.FIND_BY_USER_ID;
import static gov.ca.cwds.data.persistence.ns.ScreeningWrapper.FIND_BY_USER_ID_QUERY;
import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQuery(name = FIND_BY_USER_ID, query = FIND_BY_USER_ID_QUERY)

@Table(name = "screenings")

public class ScreeningWrapper implements Serializable {

  public static final String FIND_BY_USER_ID =
      "gov.ca.cwds.data.persistence.ns.ScreeningWrapper.findScreeningsOfUser";
  static final String FIND_BY_USER_ID_QUERY =
      "FROM ScreeningWrapper WHERE assignee_staff_id = :staffId ORDER BY started_at DESC";

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "reference")
  private String reference;

  @Column(name = "name")
  private String name;

  @Column(name = "screening_decision")
  private String screeningDecision;

  @Column(name = "screening_decision_detail")
  private String screeningDecisionDetail;

  @Column(name = "assignee")
  private String assignee;

  @Column(name = "assignee_staff_id")
  private String assigneeStaffId;

  @Column(name = "started_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date startedAt;

  @Column(name = "screening_status")
  private String screeningStatus;

  public ScreeningWrapper() {}

  public ScreeningWrapper(String id, String reference, String name, String screeningDecision,
      String assigneeStaffId, Date startedAt, String screeningStatus) {
    this.id = id;
    this.reference = reference;
    this.name = name;
    this.screeningDecision = screeningDecision;
    this.assignee = null;
    this.assigneeStaffId = assigneeStaffId;
    this.startedAt = freshDate(startedAt);
    this.screeningStatus = screeningStatus;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getScreeningDecision() {
    return screeningDecision;
  }

  public void setScreeningDecision(String screeningDecision) {
    this.screeningDecision = screeningDecision;
  }

  public String getScreeningDecisionDetail() {
    return screeningDecisionDetail;
  }

  public void setScreeningDecisionDetail(String screeningDecisionDetail) {
    this.screeningDecisionDetail = screeningDecisionDetail;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getAssigneeStaffId() {
    return assigneeStaffId;
  }

  public void setAssigneeStaffId(String assigneeStaffId) {
    this.assigneeStaffId = assigneeStaffId;
  }

  public Date getStartedAt() {
    return freshDate(startedAt);
  }

  public void setStartedAt(Date startedAt) {
    this.startedAt = freshDate(startedAt);
  }

  public String getScreeningStatus() {
    return screeningStatus;
  }

  public void setScreeningStatus(String screeningStatus) {
    this.screeningStatus = screeningStatus;
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
