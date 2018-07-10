package gov.ca.cwds.data.persistence.ns;

import static gov.ca.cwds.data.persistence.ns.AllegationEntity.FIND_BY_PERPETRATOR_ID;
import static gov.ca.cwds.data.persistence.ns.AllegationEntity.FIND_BY_VICTIM_ID;
import static gov.ca.cwds.data.persistence.ns.AllegationEntity.FIND_BY_VICTIM_OR_PERPETRATOR_ID;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.util.FerbDateUtils;

/**
 * {@link PersistentObject} representing a AllegationEntity.
 * 
 * @author CWDS API Team
 *
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.AllegationEntity.findByScreeningId",
    query = "FROM gov.ca.cwds.data.persistence.ns.AllegationEntity"
        + " WHERE screeningId = :screeningId")
@NamedQuery(name = FIND_BY_VICTIM_ID,
    query = "FROM AllegationEntity a WHERE a.victimId = :victimId")
@NamedQuery(name = FIND_BY_PERPETRATOR_ID,
    query = "FROM AllegationEntity a WHERE a.perpetratorId = :perpetratorId")
@NamedQuery(name = FIND_BY_VICTIM_OR_PERPETRATOR_ID,
    query = "FROM AllegationEntity a WHERE a.victimId = :id OR a.perpetratorId = :id")
@Entity
@Table(name = "allegations")
public class AllegationEntity implements PersistentObject {

  public static final String FIND_BY_VICTIM_ID =
      "gov.ca.cwds.data.persistence.ns.AllegationEntity.findByVictimId";
  public static final String FIND_BY_PERPETRATOR_ID =
      "gov.ca.cwds.data.persistence.ns.AllegationEntity.findByPerpetratorId";
  public static final String FIND_BY_VICTIM_OR_PERPETRATOR_ID =
      "gov.ca.cwds.data.persistence.ns.AllegationEntity.findByVictimOrPerpetratorId";

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allegations_id")
  @SequenceGenerator(name = "allegations_id", sequenceName = "allegations_id_seq")
  private Integer id;

  @Column(name = "screening_id")
  private String screeningId;

  @Column(name = "perpetrator_id")
  private String perpetratorId;

  @Column(name = "victim_id")
  private String victimId;

  @Column(name = "created_at")
  @Type(type = "timestamp")
  private Date createdAt;

  @Column(name = "updated_at")
  @Type(type = "timestamp")
  private Date updatedAt;

  @Column(name = "allegation_types")
  @Type(type = "gov.ca.cwds.data.persistence.hibernate.StringArrayType")
  private String[] allegationTypes;

  @ManyToOne
  @JoinColumn(name = "screening_id", insertable = false, updatable = false)
  private ScreeningEntity screeningEntity;

  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public AllegationEntity() {
    // default
  }

  @Override
  public Serializable getPrimaryKey() {
    return this.getId();
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setScreeningId(String screeningId) {
    this.screeningId = screeningId;
  }

  public void setPerpetratorId(String perpetratorId) {
    this.perpetratorId = perpetratorId;
  }

  public void setVictimId(String victimId) {
    this.victimId = victimId;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = FerbDateUtils.freshDate(createdAt);
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = FerbDateUtils.freshDate(updatedAt);
  }

  public ScreeningEntity getScreeningEntity() {
    return screeningEntity;
  }

  public void setScreeningEntity(ScreeningEntity screeningEntity) {
    this.screeningEntity = screeningEntity;
  }

  /**
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /**
   * @return the screeningId
   */
  public String getScreeningId() {
    return screeningId;
  }

  /**
   * @return the perpetratorId
   */
  public String getPerpetratorId() {
    return perpetratorId;
  }

  /**
   * @return the victimId
   */
  public String getVictimId() {
    return victimId;
  }

  /**
   * @return the createdAt
   */
  public Date getCreatedAt() {
    return FerbDateUtils.freshDate(createdAt);
  }

  /**
   * @return the updatedAt
   */
  public Date getUpdatedAt() {
    return FerbDateUtils.freshDate(updatedAt);
  }

  /**
   * @return the allegationTypes
   */
  public String[] getAllegationTypes() {
    return allegationTypes != null ? Arrays.copyOf(allegationTypes, allegationTypes.length) : null;
  }

  /**
   * @param allegationTypes the allegationTypes to set
   */
  public void setAllegationTypes(String[] allegationTypes) {
    this.allegationTypes =
        allegationTypes != null ? Arrays.copyOf(allegationTypes, allegationTypes.length) : null;
  }

  /**
   * @return the screeningEntity
   */
  public ScreeningEntity getScreening() {
    return screeningEntity;
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
