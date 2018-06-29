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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.util.FerbDateUtils;

/**
 * CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.CrossReportEntity.findByScreeningId",
    query = "FROM gov.ca.cwds.data.persistence.ns.CrossReportEntity"
        + " WHERE screeningId = :screeningId")
@Entity
@Table(name = "cross_reports")
public class CrossReportEntity implements PersistentObject {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cross_reports_id_seq")
  @GenericGenerator(name = "cross_reports_id_seq",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name",
          value = "cross_reports_id_seq")})
  private String id;

  @Basic
  @Column(name = "screening_id", nullable = false)
  private String screeningId;

  @Column(name = "created_at", nullable = false)
  @Type(type = "timestamp")
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @Type(type = "timestamp")
  private Date updatedAt;

  @Basic
  @Column(name = "legacy_id")
  private String legacyId;

  @Basic
  @Column(name = "legacy_source_table")
  private String legacySourceTable;

  @Basic
  @Column(name = "communication_method")
  private String communicationMethod;

  @Column(name = "inform_date")
  @Type(type = "timestamp")
  private Date informDate;

  @Basic
  @Column(name = "county_id")
  private String countyId;

  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public CrossReportEntity() {
    // default
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
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

  public String getLegacyId() {
    return legacyId;
  }

  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  public String getScreeningId() {
    return screeningId;
  }

  public void setScreeningId(String screeningId) {
    this.screeningId = screeningId;
  }

  public String getCommunicationMethod() {
    return communicationMethod;
  }

  public void setCommunicationMethod(String communicationMethod) {
    this.communicationMethod = communicationMethod;
  }

  public Date getInformDate() {
    return FerbDateUtils.freshDate(informDate);
  }

  public void setInformDate(Date informDate) {
    this.informDate = FerbDateUtils.freshDate(informDate);
  }

  public String getCountyId() {
    return countyId;
  }

  public void setCountyId(String countyId) {
    this.countyId = countyId;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
