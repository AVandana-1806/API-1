package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing Intake list-of-values (LOV) code.
 *
 * @author CWDS API Team
 */
@Entity
@Table(name = "intake_lov_codes")
@NamedQuery(
    name = "gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity.findIntakeLOVCodesByIntakeCodes",
    query = "SELECT c FROM IntakeLOVCodeEntity c WHERE c.intakeCode IN :intakeCodes")
public class IntakeLOVCodeEntity implements PersistentObject {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "cat_id")
  private Long catId;

  @Column(name = "lg_sys_id")
  private Long lgSysId;

  @Column(name = "intake_code")
  private String intakeCode;

  @Column(name = "intake_display")
  private String intakeDisplay;

  @Column(name = "omit_ind")
  private String omitInd;

  @Column(name = "parent_lg_sys_id")
  private Long parentLgSysId;

  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public IntakeLOVCodeEntity() {
    super();
  }

  public Long getCatId() {
    return catId;
  }

  public Long getLgSysId() {
    return lgSysId;
  }

  public void setLgSysId(Long lgSysId) {
    this.lgSysId = lgSysId;
  }

  public String getIntakeCode() {
    return intakeCode;
  }

  public void setIntakeCode(String intakeCode) {
    this.intakeCode = intakeCode;
  }

  public String getIntakeDisplay() {
    return intakeDisplay;
  }

  public void setIntakeDisplay(String intakeDisplay) {
    this.intakeDisplay = intakeDisplay;
  }

  public String getOmitInd() {
    return omitInd;
  }

  public Long getParentLgSysId() {
    return parentLgSysId;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getCatId();
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
