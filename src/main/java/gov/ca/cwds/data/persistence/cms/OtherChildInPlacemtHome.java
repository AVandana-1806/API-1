package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.math.BigDecimal;
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
 * {@link PersistentObject} representing an OtherChildInPlacemtHome.
 * 
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome.findAll",
    query = "FROM OtherChildInPlacemtHome")
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome.findAllUpdatedAfter",
    query = "FROM OtherChildInPlacemtHome WHERE lastUpdatedTime > :after")
@Entity
@Table(name = "OTH_KIDT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherChildInPlacemtHome extends BaseOtherChildInPlacemtHome {

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
  public OtherChildInPlacemtHome() {
    super();
  }

  /**
   * @param annualUnearnedIncomeAmount The annualUnearnedIncomeAmount
   * @param birthDate The birthDate
   * @param fkplcHmT The fkplcHmT
   * @param genderCode The genderCode
   * @param id The id
   * @param name The name
   */
  public OtherChildInPlacemtHome(BigDecimal annualUnearnedIncomeAmount, Date birthDate,
      String fkplcHmT, String genderCode, String id, String name) {
    super();
    this.annualUnearnedIncomeAmount = annualUnearnedIncomeAmount;
    this.birthDate = freshDate(birthDate);
    this.fkplcHmT = fkplcHmT;
    this.genderCode = genderCode;
    this.id = id;
    this.name = name;
  }

  /**
   * @return the placementHome
   */
  public PlacementHome getPlacementHome() {
    return placementHome;
  }

  /**
   * @param placementHome - placementHome
   */
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
