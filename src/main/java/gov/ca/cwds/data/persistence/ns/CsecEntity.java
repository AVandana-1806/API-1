package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * CWDS API Team
 *
 * Commercially Sexually Exploited Children
 */

@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.CsecEntity.findByParticipantId",
    query = "FROM gov.ca.cwds.data.persistence.ns.CsecEntity"
        + " WHERE participantId = :participantId")
@Entity
@Table(name = "csec")
public class CsecEntity implements PersistentObject, Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "participant_id")
  private String participantId;

  @Column(name = "csec_code_id")
  private String csecCodeId;

  @SuppressWarnings("squid:S3437")
  @Column(name = "start_date")
  private LocalDate startDate;

  @SuppressWarnings("squid:S3437")
  @Column(name = "end_date")
  private LocalDate endDate;

  public CsecEntity() {
    // required by third party library
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getParticipantId() {
    return participantId;
  }

  public String getCsecCodeId() {
    return csecCodeId;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public void setParticipantId(String participantId) {
    this.participantId = participantId;
  }

  public void setCsecCodeId(String csecCodeId) {
    this.csecCodeId = csecCodeId;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CsecEntity that = (CsecEntity) o;

    if (participantId != null ? !participantId.equals(that.participantId)
        : that.participantId != null) {
      return false;
    }
    return csecCodeId != null ? csecCodeId.equals(that.csecCodeId) : that.csecCodeId == null;

  }

  @Override
  public int hashCode() {
    int result = participantId != null ? participantId.hashCode() : 0;
    return 31 * result + (csecCodeId != null ? csecCodeId.hashCode() : 0);
  }
}
