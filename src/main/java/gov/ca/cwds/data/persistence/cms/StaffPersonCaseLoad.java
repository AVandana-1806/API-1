package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link CmsPersistentObject} representing a StaffPersonCaseLoad.
 * 
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Allegation.findByStaffId",
    query = "FROM StaffPersonCaseLoad WHERE fkStaffPerson = :staffId")
@Entity
@Table(name = "STFCSLDT")
public class StaffPersonCaseLoad extends CmsPersistentObject {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "FKCASE_LDT", length = CMS_ID_LEN)
  private String fkCaseLoad;

  @Id
  @Column(name = "FKSTFPERST", length = 3)
  private String fkStaffPerson;

  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String thirdId;

  @Column(name = "CNTY_SPFCD")
  private String countyCode;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  @SuppressWarnings("javadoc")
  public StaffPersonCaseLoad() {
    super();
  }

  /**
   * @param countyCode - county specific code
   * @param endDate - end data of staff person case load
   * @param fkCaseLoad - primary key to Case Load
   * @param fkStaffPerson - primary key to Staff Person
   * @param startDate - start date of staff person case load
   * @param thirdId - primary key to Staff Person Case Load table
   */
  public StaffPersonCaseLoad(String countyCode, Date endDate, String fkCaseLoad,
      String fkStaffPerson, Date startDate, String thirdId) {
    super();
    this.countyCode = countyCode;
    this.endDate = freshDate(endDate);
    this.fkCaseLoad = fkCaseLoad;
    this.fkStaffPerson = fkStaffPerson;
    this.startDate = freshDate(startDate);
    this.thirdId = thirdId;
  }

  /**
   * Constructor using domain
   * 
   * @param domainStaffPersonCaseLoad The domain object to construct this object from
   * @param lastUpdatedId the id of the last user to update this object
   */
  public StaffPersonCaseLoad(
      gov.ca.cwds.rest.api.domain.cms.StaffPersonCaseLoad domainStaffPersonCaseLoad,
      String lastUpdatedId) {
    super(lastUpdatedId);
    initialize(domainStaffPersonCaseLoad);
  }

  @SuppressWarnings("unused")
  private void initialize(
      gov.ca.cwds.rest.api.domain.cms.StaffPersonCaseLoad persistedStaffPersonCaseLoad) {
    this.countyCode = persistedStaffPersonCaseLoad.getCountyCode();
    this.endDate = DomainChef.uncookDateString(persistedStaffPersonCaseLoad.getEndDate());
    this.fkCaseLoad = persistedStaffPersonCaseLoad.getFkCaseLoad();
    this.fkStaffPerson = persistedStaffPersonCaseLoad.getFkStaffPerson();
    this.startDate = DomainChef.uncookDateString(persistedStaffPersonCaseLoad.getStartDate());
    this.thirdId = persistedStaffPersonCaseLoad.getThirdId();
  }

  @Override
  public Serializable getPrimaryKey() {
    return getThirdId();
  }

  @SuppressWarnings("javadoc")
  public String getCountyCode() {
    return countyCode;
  }

  @SuppressWarnings("javadoc")
  public Date getEndDate() {
    return freshDate(endDate);
  }

  @SuppressWarnings("javadoc")
  public String getFkCaseLoad() {
    return fkCaseLoad;
  }

  @SuppressWarnings("javadoc")
  public String getFkStaffPerson() {
    return fkStaffPerson;
  }

  @SuppressWarnings("javadoc")
  public Date getStartDate() {
    return freshDate(startDate);
  }

  @SuppressWarnings("javadoc")
  public String getThirdId() {
    return thirdId;
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
