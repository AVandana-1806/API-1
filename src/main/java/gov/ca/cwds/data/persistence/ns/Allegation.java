package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.api.domain.DomainObject;

/**
 * {@link DomainObject} representing a Allegation
 * 
 * @author CWDS API Team
 *
 */
@Entity
@Table(name = "allegations")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Allegation implements PersistentObject {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "screening_id")
  private String screeningId;

  @Column(name = "perpetrator_id")
  private String perpetratorId;

  @Column(name = "victim_id")
  private String victimId;

  @Column(name = "created_at")
  private String createdAt;

  @Column(name = "updated_at")
  private String updatedAt;

  @Column(name = "allegation_types")
  @Type(type = "gov.ca.cwds.data.persistence.hibernate.StringArrayType")
  private String[] allegationTypes = new String[0];

  @ManyToOne
  @JoinColumn(name = "screening_id", insertable = false, updatable = false)
  private ScreeningEntity screeningEntity;

  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public Allegation() {
    // default
  }

  /**
   * @param id - id
   * @param screeningId - screeningEntity id
   * @param perpetratorId - perpetrator id
   * @param victimId - victim id
   * @param createdAt - date created
   * @param updatedAt - date updated
   * @param allegationType - allegation type array
   */
  public Allegation(String id, String screeningId, String perpetratorId, String victimId,
      String createdAt, String updatedAt, String[] allegationType) {
    this.id = id;
    this.screeningId = screeningId;
    this.perpetratorId = perpetratorId;
    this.victimId = victimId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.allegationTypes =
        allegationType != null ? Arrays.copyOf(allegationType, allegationType.length) : null;
    this.screeningEntity = null;
  }

  @Override
  public Serializable getPrimaryKey() {
    return this.getId();
  }

  /**
   * @return the id
   */
  public String getId() {
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
  public String getCreatedAt() {
    return createdAt;
  }

  /**
   * @return the updatedAt
   */
  public String getUpdatedAt() {
    return updatedAt;
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
