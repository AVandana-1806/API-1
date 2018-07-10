package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a OtherAdultInPlacemtHome
 * 
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAll",
    query = "FROM OtherAdultInPlacemtHome")
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAllUpdatedAfter",
    query = "FROM OtherAdultInPlacemtHome WHERE lastUpdatedTime > :after")
@Entity
@Table(name = "OTH_ADLT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherAdultInPlacemtHome extends BaseOtherAdultInPlacemtHome {

  private static final long serialVersionUID = 1L;

  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKPLC_HM_T", nullable = false, updatable = false, insertable = false)
  private PlacementHome placementHome;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public OtherAdultInPlacemtHome() {
    super();
  }

  /**
   * @param birthDate The birthDate
   * @param commentDescription The commentDescription
   * @param endDate The endDate
   * @param fkplcHmT The fkplcHmT
   * @param genderCode The genderCode
   * @param id The id
   * @param identifiedDate The identifiedDate
   * @param name The name
   * @param otherAdultCode The otherAdultCode
   * @param passedBackgroundCheckCode The passedBackgroundCheckCode
   * @param residedOutOfStateIndicator The residedOutOfStateIndicator
   * @param startDate The startDate
   */
  public OtherAdultInPlacemtHome(Date birthDate, String commentDescription, Date endDate,
      String fkplcHmT, String genderCode, String id, Date identifiedDate, String name,
      String otherAdultCode, String passedBackgroundCheckCode, String residedOutOfStateIndicator,
      Date startDate) {
    super();
    this.birthDate = freshDate(birthDate);
    this.commentDescription = commentDescription;
    this.endDate = freshDate(endDate);
    this.fkplcHmT = fkplcHmT;
    this.genderCode = genderCode;
    this.id = id;
    this.identifiedDate = freshDate(identifiedDate);
    this.name = name;
    this.otherAdultCode = otherAdultCode;
    this.passedBackgroundCheckCode = passedBackgroundCheckCode;
    this.residedOutOfStateIndicator = residedOutOfStateIndicator;
    this.startDate = freshDate(startDate);
  }


  /**
   * @return the placementHome
   */
  public PlacementHome getPlacementHome() {
    return placementHome;
  }

  public void setPlacementHome(PlacementHome placementHome) {
    this.placementHome = placementHome;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
