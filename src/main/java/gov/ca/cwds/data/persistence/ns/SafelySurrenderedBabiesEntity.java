package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;

import gov.ca.cwds.data.ns.NsPersistentObject;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.util.FerbDateUtils;

/**
 * {@link NsPersistentObject} representing SafelySurrenderedBabies
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "safely_surrendered_babies")
@SuppressWarnings({"common-java:DuplicatedBlocks"})
public class SafelySurrenderedBabiesEntity implements PersistentObject, Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "participant_child")
  private String participantId;

  @Column(name = "surrendered_by")
  private String surrenderedBy;

  @Column(name = "relation_to_child")
  private String relationToChild;

  @Column(name = "bracelet_id")
  private String braceletId;

  @Column(name = "parent_guard_given_bracelet_id")
  private String parentGuardGivenBraceletId;

  @Column(name = "parent_guard_prov_med_question")
  private String parentGuardProvMedQuestion;

  @Column(name = "med_questionaire_return_date")
  private Date medQuestionaireReturnDate;

  @Column(name = "comments")
  private String comments;

  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "participant_child", nullable = false, updatable = false, insertable = false)
  private ParticipantEntity participantEntity;

  public SafelySurrenderedBabiesEntity() {
    super();
  }



  public String getParticipantId() {
    return participantId;
  }



  public void setParticipantId(String participantId) {
    this.participantId = participantId;
  }



  public String getSurrenderedBy() {
    return surrenderedBy;
  }



  public void setSurrenderedBy(String surrenderedBy) {
    this.surrenderedBy = surrenderedBy;
  }



  public String getRelationToChild() {
    return relationToChild;
  }



  public void setRelationToChild(String relationToChild) {
    this.relationToChild = relationToChild;
  }



  public String getBraceletId() {
    return braceletId;
  }



  public void setBraceletId(String braceletId) {
    this.braceletId = braceletId;
  }


  public String getParentGuardGivenBraceletId() {
    return parentGuardGivenBraceletId;
  }



  public void setParentGuardGivenBraceletId(String parentGuardGivenBraceletId) {
    this.parentGuardGivenBraceletId = parentGuardGivenBraceletId;
  }



  public String getParentGuardProvMedQuestion() {
    return parentGuardProvMedQuestion;
  }



  public void setParentGuardProvMedQuestion(String parentGuardProvMedQuestion) {
    this.parentGuardProvMedQuestion = parentGuardProvMedQuestion;
  }



  public Date getMedQuestionaireReturnDate() {
    return FerbDateUtils.freshDate(medQuestionaireReturnDate);
  }



  public void setMedQuestionaireReturnDate(Date medQuestionaireReturnDate) {
    this.medQuestionaireReturnDate = FerbDateUtils.freshDate(medQuestionaireReturnDate);
  }



  public String getComments() {
    return comments;
  }



  public void setComments(String comments) {
    this.comments = comments;
  }



  public ParticipantEntity getParticipantEntity() {
    return participantEntity;
  }



  public void setParticipantEntity(ParticipantEntity participantEntity) {
    this.participantEntity = participantEntity;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

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


  @Override
  public Serializable getPrimaryKey() {
    return getParticipantId();
  }

}
