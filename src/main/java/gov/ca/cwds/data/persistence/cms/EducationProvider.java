package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiAddressAware;
import gov.ca.cwds.data.std.ApiPhoneAware;

/**
 * {@link PersistentObject} representing a EducationProvider
 * 
 * @author CWDS API Team
 *
 */
@Entity
@Table(name = "ED_PVDRT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EducationProvider extends CmsPersistentObject
    implements ApiAddressAware, ApiPhoneAware {

  private static final long serialVersionUID = 1L;

  @Column(name = "ED_PVDR_NM", length = 35, nullable = false)
  protected String agencyName;

  @Column(name = "MAILCTYNM", length = 20, nullable = false)
  protected String cityName;

  @Column(name = "WEB_ADDR", length = 60)
  protected String webAddress;

  @Column(name = "SCHL_FAXNO")
  protected Long faxNumber;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  protected String id;

  @Type(type = "integer")
  @Column(name = "PHEXT_NO", nullable = false)
  protected Integer phoneExtensionNumber;

  @Column(name = "PHONE_NO")
  protected Long phoneNumber;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "MAILSTCD", nullable = false)
  protected Short stateCodeType;

  @Column(name = "MAIL_ST_NM", length = 40, nullable = false)
  protected String streetName;

  @Column(name = "MAIL_ST_NO", length = 10, nullable = false)
  protected String streetNumber;

  @Type(type = "integer")
  @Column(name = "MAIL_ZIP", nullable = false)
  protected Integer zipNumber;

  @Type(type = "short")
  @Column(name = "MAILZIPS", nullable = false)
  protected Short zipSuffixNumber;


  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public EducationProvider() {
    super();
  }

  public EducationProvider(String lastUpdatedId) {
    super(lastUpdatedId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return StringUtils.trimToEmpty(cityName);
  }

  /**
   * @return the webAddress
   */
  public String getWebAddress() {
    return StringUtils.trimToEmpty(webAddress);
  }

  /**
   * @return the faxNumber
   */
  public Long getFaxNumber() {
    return faxNumber;
  }

  /**
   * @return the id
   */
  public String getId() {
    return StringUtils.trimToEmpty(id);
  }

  /**
   * @return the phoneExtensionNumber
   */
  public Integer getPhoneExtensionNumber() {
    return phoneExtensionNumber;
  }

  /**
   * @return the phoneNumber
   */
  public Long getPhoneNumberAsDecimal() {
    return phoneNumber;
  }

  /**
   * @return the phoneNumber
   */
  @Override
  public String getPhoneNumber() {
    return phoneNumber != null ? String.valueOf(phoneNumber) : null;
  }

  /**
   * @return the stateCodeType
   */
  public Short getStateCodeType() {
    return stateCodeType;
  }

  /**
   * @return the streetName
   */
  @Override
  public String getStreetName() {
    return StringUtils.trimToEmpty(streetName);
  }

  /**
   * @return the streetNumber
   */
  @Override
  public String getStreetNumber() {
    return StringUtils.trimToEmpty(streetNumber);
  }

  /**
   * @return the zipNumber
   */
  public Integer getZipNumber() {
    return zipNumber;
  }

  /**
   * @return the zipSuffixNumber
   */
  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

  @JsonIgnore
  @Override
  public String getStreetAddress() {
    return this.getStreetNumber() + " " + this.getStreetName();
  }

  @JsonIgnore
  @Override
  public String getCity() {
    return this.getCityName();
  }

  @JsonIgnore
  @Override
  public String getState() {
    return this.getStateCodeType() != null ? this.getStateCodeType().toString() : null;
  }

  @JsonIgnore
  @Override
  public String getZip() {
    StringBuilder buf = new StringBuilder();

    if (this.getZipNumber() != null) {
      buf.append(getZipNumber());
    }
    if (this.getZipSuffixNumber() != null) {
      buf.append('-').append(getZipSuffixNumber());
    }

    return buf.toString();
  }

  @JsonIgnore
  @Override
  public String getCounty() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getPhoneNumberExtension() {
    return this.getPhoneExtensionNumber() != null ? this.getPhoneExtensionNumber().toString()
        : null;
  }

  @JsonIgnore
  @Override
  public PhoneType getPhoneType() {
    return null;
  }

  @Override
  @JsonIgnore
  public String getPhoneId() {
    return null;
  }

  @Override
  public String getAddressId() {
    return null;
  }

  @Override
  public Short getStateCd() {
    return this.stateCodeType;
  }

  public void setAgencyName(String agencyName) {
    this.agencyName = agencyName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public void setWebAddress(String webAddress) {
    this.webAddress = webAddress;
  }

  public void setFaxNumber(Long faxNumber) {
    this.faxNumber = faxNumber;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setPhoneExtensionNumber(Integer phoneExtensionNumber) {
    this.phoneExtensionNumber = phoneExtensionNumber;
  }

  public void setPhoneNumber(Long phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setStateCodeType(Short stateCodeType) {
    this.stateCodeType = stateCodeType;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }

  public void setZipNumber(Integer zipNumber) {
    this.zipNumber = zipNumber;
  }

  public void setZipSuffixNumber(Short zipSuffixNumber) {
    this.zipSuffixNumber = zipSuffixNumber;
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
