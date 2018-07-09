package gov.ca.cwds.data.persistence.ns;

import static gov.ca.cwds.data.persistence.ns.Relationship.FIND_RELATIONSHIPS_BY_LEGACY_ID;
import static gov.ca.cwds.data.persistence.ns.Relationship.FIND_RELATIONSHIPS_BY_SCREENING_ID;
import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import gov.ca.cwds.data.persistence.PersistentObject;
import org.hibernate.annotations.NamedQuery;

@NamedQuery(
        name = FIND_RELATIONSHIPS_BY_SCREENING_ID,
        query = "FROM gov.ca.cwds.data.persistence.ns.Relationship r WHERE r.participantFrom.screeningId = :screeningId " +
                "OR r.participantTo.screeningId = :screeningId")
@NamedQuery(
        name = FIND_RELATIONSHIPS_BY_LEGACY_ID,
        query = "FROM gov.ca.cwds.data.persistence.ns.Relationship r WHERE r.legacyId = :legacyId")


@Entity
@Table(name = "relationships")
public class Relationship implements PersistentObject {
  public static final String FIND_RELATIONSHIPS_BY_SCREENING_ID = "gov.ca.cwds.data.persistence.ns.Relationship.findRelationshipsByScreeningId";
  public static final String FIND_RELATIONSHIPS_BY_LEGACY_ID = "gov.ca.cwds.data.persistence.ns.Relationship.findRelationshipsByLegacyId";
  @Id
  @GenericGenerator(name = "relationships_id",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name",
          value = "relationships_id_seq")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "relationships_id")

  @Column(name = "id")
  private String id;

  @Column(name = "client")
  private String clientId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client", nullable = false, insertable = false, updatable = false)
  private ParticipantEntity participantFrom;

  @Column(name = "relative")
  private String relativeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "relative", nullable = false, insertable = false, updatable = false)
  private ParticipantEntity participantTo;

  @Column(name = "relation")
  private int relationshipType;

  @Column(name = "created_at")
  private Date createdAt;

  public ParticipantEntity getParticipantFrom() {
    return participantFrom;
  }

  public ParticipantEntity getParticipantTo() {
    return participantTo;
  }

  @Column(name = "updated_at")

  private Date updatedAt;

  @Column(name = "absent_parent_indicator")
  private boolean absentParentIndicator;

  @Column(name = "same_home_status")
  private Boolean sameHomeStatus;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @Column(name = "legacy_id")
  private String legacyId;

  public Relationship() {}

    public Relationship(String id, String clientId, String relativeId, int relationshipType,
                        Date createdAt, Date updatedAt, Boolean absentParentIndicator, Boolean sameHomeStatus,
                        String legacyId, Date startDate, Date endDate){
    this.id = id;
    this.clientId = clientId;
    this.relativeId = relativeId;
    this.relationshipType = relationshipType;
    this.createdAt = freshDate(createdAt);
    this.updatedAt = freshDate(updatedAt);
    this.sameHomeStatus = sameHomeStatus;
    this.absentParentIndicator = absentParentIndicator;
    this.startDate = startDate;
    this.endDate = endDate;
    this.legacyId = legacyId;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getRelativeId() {
    return relativeId;
  }

  public void setRelativeId(String relativeId) {
    this.relativeId = relativeId;
  }

  public int getRelationshipType() {
    return relationshipType;
  }

  public void setRelationshipType(int relationshipType) {
    this.relationshipType = relationshipType;
  }

  public Date getCreatedAt() {
    return freshDate(createdAt);
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = freshDate(createdAt);
  }

  public Date getUpdatedAt() {
    return freshDate(updatedAt);
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = freshDate(updatedAt);
  }

  public boolean isAbsentParentIndicator() {
    return absentParentIndicator;
  }

  public void setAbsentParentIndicator(boolean absentParentIndicator) {
    this.absentParentIndicator = absentParentIndicator;
  }

  public Boolean getSameHomeStatus() {
    return sameHomeStatus;
  }

  public void setSameHomeStatus(Boolean sameHomeStatus) {
    this.sameHomeStatus = sameHomeStatus;
  }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(String legacyId) {
        this.legacyId = legacyId;
    }
}
