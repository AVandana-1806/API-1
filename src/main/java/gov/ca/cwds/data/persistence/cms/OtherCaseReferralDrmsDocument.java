package gov.ca.cwds.data.persistence.cms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * {@link CmsPersistentObject} Class representing an OtherCaseReferralDrmsDocumentService.
 *
 * @author Intake Team 4
 */
@Entity
@Table(name = "OTH_DOCT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherCaseReferralDrmsDocument extends CmsPersistentObject {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "FKDRMSDOCT", length = CMS_ID_LEN)
  private String drmsDocumentId;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "TITLE_NM")
  private String titleName;

  @Column(name = "FKREFERL_T", length = CMS_ID_LEN)
  private String referralId;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "DOC_EXTC")
  private Short documentExtensionTypeId;

  @Column(name = "DOC_LEN")
  private Integer documentLengthKb;



  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public OtherCaseReferralDrmsDocument() {
    super();
  }


  /**
   * @param drmsDocumentId - primary Key (The id of DrmsDocument)
   * @param countySpecificCode - countySpecificCode
   * @param titleName - titleName
   * @param referralId - referralId
   * @param documentExtensionTypeId - documentExtensionTypeId
   * @param documentLengthKb - documentLengthKb
   */
  public OtherCaseReferralDrmsDocument(String drmsDocumentId, String countySpecificCode, String titleName, String referralId, Short documentExtensionTypeId, Integer documentLengthKb) {
    super();
    this.drmsDocumentId = drmsDocumentId;
    this.countySpecificCode = countySpecificCode;
    this.titleName = titleName;
    this.referralId = referralId;
    this.documentExtensionTypeId = documentExtensionTypeId;
    this.documentLengthKb = documentLengthKb;
  }

  /**
   * Constructor using domain
   *
   * @param otherCaseReferralDrmsDocument The domain object to construct this object from
   * @param lastUpdatedId The id of the last person to update this object
   * @param lastUpdatedTime the time when this object is last updated
   */
  public OtherCaseReferralDrmsDocument(gov.ca.cwds.rest.api.domain.cms.OtherCaseReferralDrmsDocument otherCaseReferralDrmsDocument,
                      String lastUpdatedId, Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    this.drmsDocumentId = otherCaseReferralDrmsDocument.getDrmsDocumentId();
    this.countySpecificCode = otherCaseReferralDrmsDocument.getCountySpecificCode();
    this.titleName = otherCaseReferralDrmsDocument.getTitleName();
    this.referralId = otherCaseReferralDrmsDocument.getReferralId();
    this.documentExtensionTypeId = otherCaseReferralDrmsDocument.getDocumentExtensionTypeId();
    this.documentLengthKb = otherCaseReferralDrmsDocument.getDocumentLengthKb();
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Serializable getPrimaryKey() {
    return getDrmsDocumentId();
  }

  /**
   * @return the drmsDocumentId
   */
  public String getDrmsDocumentId() {
    return drmsDocumentId;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the titleName
   */
  public String getTitleName() {
    return titleName;
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the documentExtensionTypeId
   */
  public Short getDocumentExtensionTypeId() {
    return documentExtensionTypeId;
  }

  /**
   * @return the documentLengthKb
   */
  public Integer getDocumentLengthKb() {
    return documentLengthKb;
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
