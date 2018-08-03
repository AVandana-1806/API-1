package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;

/**
 * {@link CmsPersistentObject} representing a Injury Body Detail.
 * 
 * @author CWDS API Team
 */
@NamedQuery(
    name = "gov.ca.cwds.data.dao.investigation.InjuryBodyDetail.findInjuryBodyDetailsByInjuryHarmDetailId",
    query = "FROM InjuryBodyDetail WHERE secondaryInjuryHarmDetailId = :secondaryInjuryHarmDetailId")
@NamedQuery(
    name = "gov.ca.cwds.data.dao.investigation.InjuryBodyDetail.findInjuryBodyDetailsByAllegationId",
    query = "FROM InjuryBodyDetail WHERE primaryInjuryHarmDetailId = :allegationId")
@Entity
@Table(name = "IJBD_DET")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InjuryBodyDetail extends CmsPersistentObject {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String thirdId;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "ABS_BPTC")
  private Short physicalAbuseBodyPartType;

  @Column(name = "FKIJHM_DET")
  private String primaryInjuryHarmDetailId;

  @Column(name = "FKIJHM_DE0")
  private String secondaryInjuryHarmDetailId;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  /**
   * Default constructor
   */
  public InjuryBodyDetail() {
    super();
  }

  /**
   * @param thirdId - thirdId
   * @param physicalAbuseBodyPartType - physicalAbuseBodyPartType
   * @param primaryInjuryHarmDetailId - primaryInjuryHarmDetailId
   * @param secondaryInjuryHarmDetailId - secondaryInjuryHarmDetailId
   * @param countySpecificCode - countySpecificCode
   */
  public InjuryBodyDetail(String thirdId, Short physicalAbuseBodyPartType,
      String primaryInjuryHarmDetailId, String secondaryInjuryHarmDetailId,
      String countySpecificCode) {
    super();
    this.thirdId = thirdId;
    this.physicalAbuseBodyPartType = physicalAbuseBodyPartType;
    this.primaryInjuryHarmDetailId = primaryInjuryHarmDetailId;
    this.secondaryInjuryHarmDetailId = secondaryInjuryHarmDetailId;
    this.countySpecificCode = countySpecificCode;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return this.getThirdId();
  }

  /**
   * @return the id
   */
  public String getThirdId() {
    return thirdId;
  }

  /**
   * @return the physicalAbuseBodyPartType
   */
  public Short getPhysicalAbuseBodyPartType() {
    return physicalAbuseBodyPartType;
  }

  /**
   * @return the primaryInjuryHarmDetailId
   */
  public String getPrimaryInjuryHarmDetailId() {
    return primaryInjuryHarmDetailId;
  }

  /**
   * @return the secondaryInjuryHarmDetailId
   */
  public String getSecondaryInjuryHarmDetailId() {
    return secondaryInjuryHarmDetailId;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
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
