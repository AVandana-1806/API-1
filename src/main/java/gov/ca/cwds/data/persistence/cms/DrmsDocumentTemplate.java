package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate.NQ_TEMPLATES_BY_APPLICATION_CONTEXT_AND_GOVERNMENT_ENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} Class representing an DrmsDocumentTemplate.
 *
 * @author CWDS API Team
 */
@NamedQuery(name = NQ_TEMPLATES_BY_APPLICATION_CONTEXT_AND_GOVERNMENT_ENTITY,
    query = "FROM DrmsDocumentTemplate WHERE applicationContextType = :applicationContextType "
        + "AND (govermentEntityType = :govermentEntityType OR govermentEntityType = 0) "
        + "AND  inactive = 'N'")
@Entity
@Table(name = "DOCTMPLT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrmsDocumentTemplate implements PersistentObject {

  private static final long serialVersionUID = 1L;

  public static final String NQ_TEMPLATES_BY_APPLICATION_CONTEXT_AND_GOVERNMENT_ENTITY =
      "gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate.findByApplicationContext";

  @Id
  @Column(name = "THIRD_ID", length = 10)
  private String thirdId;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "APL_CTXC")
  private Short applicationContextType;

  @Column(name = "DDOS_FNM")
  private String documentDOSFilePrefixName;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "GVR_ENTC")
  private Short govermentEntityType;

  @Column(name = "TPLHNDL_NM", length = 30)
  private String cmsDocumentId;

  @Column(name = "INACT_IND", length = 1)
  private String inactive = "N";

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "LANG_TPC")
  private Short languageType = 1253;

  @Type(type = "timestamp")
  @Column(name = "LST_UPD_TS")
  private Date lastUpdatedTime;

  @Column(name = "TITLE_NM", length = 40)
  private String titleName;

  @Column(name = "TRNS_TYPE", length = 1)
  private String transactionType;

  /**
   * Default constructor
   *
   * Required for Hibernate.
   */
  public DrmsDocumentTemplate() {}

  /**
   * Convenience constructor.
   *
   * @param thirdId template identifier
   * @param applicationContextType applicationContextType
   * @param documentDOSFilePrefixName documentDOSFilePrefixName
   * @param governmentEntityType govermentEntityType
   * @param cmsDocumentId cmsDocumentId
   * @param inactive inactive
   * @param languageType languageType
   * @param lastUpdatedTime lastUpdatedTime
   * @param titleName titleName
   * @param transactionType transactionType
   */
  public DrmsDocumentTemplate(String thirdId, Short applicationContextType,
      String documentDOSFilePrefixName, Short governmentEntityType, String cmsDocumentId,
      String inactive, Short languageType, Date lastUpdatedTime, String titleName,
      String transactionType) {
    this.thirdId = thirdId;
    this.applicationContextType = applicationContextType;
    this.documentDOSFilePrefixName = documentDOSFilePrefixName;
    this.govermentEntityType = governmentEntityType;
    this.cmsDocumentId = cmsDocumentId;
    this.inactive = inactive;
    this.languageType = languageType;
    this.lastUpdatedTime = new Date(lastUpdatedTime.getTime());
    this.titleName = titleName;
    this.transactionType = transactionType;
  }

  /**
   * Constructor using domain
   *
   * @param drmsDocumentTemplate The domain object to construct this object from
   * @param lastUpdatedTime the time when this object is last updated
   */
  public DrmsDocumentTemplate(
      gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate drmsDocumentTemplate,
      Date lastUpdatedTime) {
    this.thirdId = drmsDocumentTemplate.getThirdId();
    this.applicationContextType = drmsDocumentTemplate.getApplicationContextType();
    this.documentDOSFilePrefixName = drmsDocumentTemplate.getDocumentDOSFilePrefixName();
    this.govermentEntityType = drmsDocumentTemplate.getGovermentEntityType();
    this.cmsDocumentId = drmsDocumentTemplate.getCmsDocumentId();
    this.languageType = drmsDocumentTemplate.getLanguageType();
    this.titleName = drmsDocumentTemplate.getTitleName();
    this.transactionType = drmsDocumentTemplate.getTransactionType();
    this.lastUpdatedTime = new Date(lastUpdatedTime.getTime());
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getThirdId();
  }

  /**
   * @return the
   */
  public String getThirdId() {
    return thirdId;
  }

  /**
   * @return the thirdId
   */
  public Short getApplicationContextType() {
    return applicationContextType;
  }

  /**
   * @return the documentDOSFilePrefixName
   */
  public String getDocumentDOSFilePrefixName() {
    return documentDOSFilePrefixName;
  }

  /**
   * @return the govermentEntityType
   */
  public Short getGovermentEntityType() {
    return govermentEntityType;
  }

  /**
   * @return the cmsDocumentId
   */
  public String getCmsDocumentId() {
    return cmsDocumentId;
  }

  /**
   * @return the inactive
   */
  public String getInactive() {
    return inactive;
  }

  /**
   * @return the languageType
   */
  public Short getLanguageType() {
    return languageType;
  }

  /**
   * @return the lastUpdatedTime
   */
  public Date getLastUpdatedTime() {
    return new Date(lastUpdatedTime.getTime());
  }

  /**
   * @return the titleName
   */
  public String getTitleName() {
    return titleName;
  }

  /**
   * @return the transactionType
   */
  public String getTransactionType() {
    return transactionType;
  }
}
