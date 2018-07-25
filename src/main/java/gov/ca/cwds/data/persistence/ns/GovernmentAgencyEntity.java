package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQuery;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.util.FerbDateUtils;

/**
 * CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.GovernmentAgencyEntity.findByCrossReportId",
    query = "FROM gov.ca.cwds.data.persistence.ns.GovernmentAgencyEntity"
        + " WHERE crossReportId = :crossReportId")
@Entity
@Table(name = "agencies")
public class GovernmentAgencyEntity implements PersistentObject {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agencies_id_seq")
  @GenericGenerator(name = "agencies_id_seq",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator", parameters = {
          @org.hibernate.annotations.Parameter(name = "sequence_name", value = "agencies_id_seq")})
  private String id;

  @Basic
  @Column(name = "cross_report_id")
  private String crossReportId;

  @Basic
  @Column(name = "code")
  private String code;

  @Basic
  @Column(name = "category")
  private String category;

  @Column(name = "created_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public GovernmentAgencyEntity() {
    // default
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCrossReportId() {
    return crossReportId;
  }

  public void setCrossReportId(String crossReportId) {
    this.crossReportId = crossReportId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Date getCreatedAt() {
    return FerbDateUtils.freshDate(createdAt);
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = FerbDateUtils.freshDate(createdAt);
  }

  public Date getUpdatedAt() {
    return FerbDateUtils.freshDate(updatedAt);
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = FerbDateUtils.freshDate(updatedAt);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, "id", "createdAt", "updatedAt");
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, "id", "createdAt", "updatedAt");
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }
}
