package gov.ca.cwds.rest.api.persistence.cms;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a CollateralIndividual
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.cms.CollateralIndividual.findAll",
        query = "FROM CollateralIndividual"),
    @NamedQuery(
        name = "gov.ca.cwds.rest.api.persistence.cms.CollateralIndividual.findAllUpdatedAfter",
        query = "FROM CollateralIndividual WHERE lastUpdatedTime > :after")})
@Entity
@Table(name = "COLTRL_T")
public class CollateralIndividual extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Column(name = "BADGE_NO")
  private String badgeNumber;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  private Date birthDate;

  @Column(name = "CITY_NM")
  private String cityName;

  @Column(name = "COMNT_DSC")
  private String commentDescription;

  @Column(name = "EMAIL_ADDR")
  private String emailAddress;

  @Column(name = "EMPLYR_NM")
  private String employerName;

  @Column(name = "ESTBLSH_CD")
  private String establishedForCode;

  @Column(name = "FAX_NO")
  private BigDecimal faxNumber;

  @Column(name = "FIRST_NM")
  private String firstName;

  @Column(name = "FRG_ADRT_B")
  private String foreignAddressIndicatorVariable;

  @Column(name = "GENDER_CD")
  private String genderCode;

  @Id
  @Column(name = "IDENTIFIER")
  private String id;

  @Column(name = "LAST_NM")
  private String lastName;

  @Type(type = "short")
  @Column(name = "MRTL_STC")
  private Short maritalStatus;

  @Column(name = "MID_INI_NM")
  private String middleInitialName;

  @Column(name = "NMPRFX_DSC")
  private String namePrefixDescription;

  @Type(type = "integer")
  @Column(name = "PRM_EXT_NO")
  private Integer primaryExtensionNumber;

  @Column(name = "PRM_TEL_NO")
  private BigInteger primaryPhoneNo;

  @Column(name = "RESOST_IND")
  private String residedOutOfStateIndicator;

  @Type(type = "short")
  @Column(name = "STATE_C")
  private Short stateCode;

  @Column(name = "STREET_NM")
  private String streetName;

  @Column(name = "STREET_NO")
  private String streetNumber;

  @Column(name = "SUFX_TLDSC")
  private String suffixTitleDescription;

  @Type(type = "integer")
  @Column(name = "ZIP_NO")
  private Integer zipNumber;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO")
  private Short zipSuffixNumber;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public CollateralIndividual() {
    super();
  }

  /**
   * Constructor
   * 
   * @param badgeNumber The unique badge number
   * @param birthDate Date of birth
   * @param cityName The name of the city
   * @param commentDescription A brief description of any unusual circumstances
   * @param emailAddress The e-mail address
   * @param employerName The employer name
   * @param establishedForCode Defines each type of recipient entity
   * @param faxNumber The FAX number
   * @param firstName The first name
   * @param foreignAddressIndicatorVariable Indicates if there are any occurrences of foreign
   *        addresses
   * @param genderCode Indicates the gender
   * @param id The unique identifier
   * @param lastName The last name
   * @param maritalStatus The Martial Status type
   * @param middleInitialName The middle initial
   * @param namePrefixDescription The salutation form
   * @param primaryExtensionNumber The primary phone extension number
   * @param primaryPhoneNo The primary phone number
   * @param residedOutOfStateIndicator Indicates if reside out of state
   * @param stateCode The State Code Type
   * @param streetName The street name
   * @param suffixTitleDescription The suffix name
   * @param zipNumber The zip code
   * @param zipSuffixNumber The zip suffix number
   */
  public CollateralIndividual(String badgeNumber, Date birthDate, String cityName,
      String commentDescription, String emailAddress, String employerName,
      String establishedForCode, BigDecimal faxNumber, String firstName,
      String foreignAddressIndicatorVariable, String genderCode, String id, String lastName,
      Short maritalStatus, String middleInitialName, String namePrefixDescription,
      Integer primaryExtensionNumber, BigInteger primaryPhoneNo, String residedOutOfStateIndicator,
      Short stateCode, String streetName, String streetNumber, String suffixTitleDescription,
      Integer zipNumber, Short zipSuffixNumber) {
    super();
    this.badgeNumber = badgeNumber;
    this.birthDate = birthDate;
    this.cityName = cityName;
    this.commentDescription = commentDescription;
    this.emailAddress = emailAddress;
    this.employerName = employerName;
    this.establishedForCode = establishedForCode;
    this.faxNumber = faxNumber;
    this.firstName = firstName;
    this.foreignAddressIndicatorVariable = foreignAddressIndicatorVariable;
    this.genderCode = genderCode;
    this.id = id;
    this.lastName = lastName;
    this.maritalStatus = maritalStatus;
    this.middleInitialName = middleInitialName;
    this.namePrefixDescription = namePrefixDescription;
    this.primaryExtensionNumber = primaryExtensionNumber;
    this.primaryPhoneNo = primaryPhoneNo;
    this.residedOutOfStateIndicator = residedOutOfStateIndicator;
    this.stateCode = stateCode;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.zipNumber = zipNumber;
    this.zipSuffixNumber = zipSuffixNumber;
  }


  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getBadgeNumber() {
    return badgeNumber;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public String getCityName() {
    return cityName;
  }

  public String getCommentDescription() {
    return commentDescription;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getEmployerName() {
    return employerName;
  }

  public String getEstablishedForCode() {
    return establishedForCode;
  }

  public BigDecimal getFaxNumber() {
    return faxNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getForeignAddressIndicatorVariable() {
    return foreignAddressIndicatorVariable;
  }

  public String getGenderCode() {
    return genderCode;
  }

  public String getId() {
    return id;
  }

  public String getLastName() {
    return lastName;
  }

  public Short getMaritalStatus() {
    return maritalStatus;
  }

  public String getMiddleInitialName() {
    return middleInitialName;
  }

  public String getNamePrefixDescription() {
    return namePrefixDescription;
  }

  public Integer getPrimaryExtensionNumber() {
    return primaryExtensionNumber;
  }

  public BigInteger getPrimaryPhoneNo() {
    return primaryPhoneNo;
  }

  public String getResidedOutOfStateIndicator() {
    return residedOutOfStateIndicator;
  }

  public Short getStateCode() {
    return stateCode;
  }

  public String getStreetName() {
    return streetName;
  }

  public String getStreetNumber() {
    return streetNumber;
  }

  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }

  public Integer getZipNumber() {
    return zipNumber;
  }

  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

  @Override
  public String getPrimaryKey() {
    return getId();
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((badgeNumber == null) ? 0 : badgeNumber.hashCode());
    result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
    result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
    result = prime * result + ((commentDescription == null) ? 0 : commentDescription.hashCode());
    result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
    result = prime * result + ((employerName == null) ? 0 : employerName.hashCode());
    result = prime * result + ((establishedForCode == null) ? 0 : establishedForCode.hashCode());
    result = prime * result + ((faxNumber == null) ? 0 : faxNumber.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((foreignAddressIndicatorVariable == null) ? 0
        : foreignAddressIndicatorVariable.hashCode());
    result = prime * result + ((genderCode == null) ? 0 : genderCode.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((maritalStatus == null) ? 0 : maritalStatus.hashCode());
    result = prime * result + ((middleInitialName == null) ? 0 : middleInitialName.hashCode());
    result =
        prime * result + ((namePrefixDescription == null) ? 0 : namePrefixDescription.hashCode());
    result =
        prime * result + ((primaryExtensionNumber == null) ? 0 : primaryExtensionNumber.hashCode());
    result = prime * result + ((primaryPhoneNo == null) ? 0 : primaryPhoneNo.hashCode());
    result = prime * result
        + ((residedOutOfStateIndicator == null) ? 0 : residedOutOfStateIndicator.hashCode());
    result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
    result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
    result = prime * result + ((streetNumber == null) ? 0 : streetNumber.hashCode());
    result =
        prime * result + ((suffixTitleDescription == null) ? 0 : suffixTitleDescription.hashCode());
    result = prime * result + ((zipNumber == null) ? 0 : zipNumber.hashCode());
    result = prime * result + ((zipSuffixNumber == null) ? 0 : zipSuffixNumber.hashCode());
    result = prime * result
        + ((super.getLastUpdatedId() == null) ? 0 : super.getLastUpdatedId().hashCode());
    result = prime * result
        + ((super.getLastUpdatedTime() == null) ? 0 : super.getLastUpdatedTime().hashCode());
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof CollateralIndividual)) {
      return false;
    }
    CollateralIndividual other = (CollateralIndividual) obj;
    if (badgeNumber == null) {
      if (other.badgeNumber != null)
        return false;
    } else if (!badgeNumber.equals(other.badgeNumber))
      return false;
    if (birthDate == null) {
      if (other.birthDate != null)
        return false;
    } else if (!birthDate.equals(other.birthDate))
      return false;
    if (cityName == null) {
      if (other.cityName != null)
        return false;
    } else if (!cityName.equals(other.cityName))
      return false;
    if (commentDescription == null) {
      if (other.commentDescription != null)
        return false;
    } else if (!commentDescription.equals(other.commentDescription))
      return false;
    if (emailAddress == null) {
      if (other.emailAddress != null)
        return false;
    } else if (!emailAddress.equals(other.emailAddress))
      return false;
    if (employerName == null) {
      if (other.employerName != null)
        return false;
    } else if (!employerName.equals(other.employerName))
      return false;
    if (establishedForCode == null) {
      if (other.establishedForCode != null)
        return false;
    } else if (!establishedForCode.equals(other.establishedForCode))
      return false;
    if (faxNumber == null) {
      if (other.faxNumber != null)
        return false;
    } else if (!faxNumber.equals(other.faxNumber))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (foreignAddressIndicatorVariable == null) {
      if (other.foreignAddressIndicatorVariable != null)
        return false;
    } else if (!foreignAddressIndicatorVariable.equals(other.foreignAddressIndicatorVariable))
      return false;
    if (genderCode == null) {
      if (other.genderCode != null)
        return false;
    } else if (!genderCode.equals(other.genderCode))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (maritalStatus == null) {
      if (other.maritalStatus != null)
        return false;
    } else if (!maritalStatus.equals(other.maritalStatus))
      return false;
    if (middleInitialName == null) {
      if (other.middleInitialName != null)
        return false;
    } else if (!middleInitialName.equals(other.middleInitialName))
      return false;
    if (namePrefixDescription == null) {
      if (other.namePrefixDescription != null)
        return false;
    } else if (!namePrefixDescription.equals(other.namePrefixDescription))
      return false;
    if (primaryExtensionNumber == null) {
      if (other.primaryExtensionNumber != null)
        return false;
    } else if (!primaryExtensionNumber.equals(other.primaryExtensionNumber))
      return false;
    if (primaryPhoneNo == null) {
      if (other.primaryPhoneNo != null)
        return false;
    } else if (!primaryPhoneNo.equals(other.primaryPhoneNo))
      return false;
    if (residedOutOfStateIndicator == null) {
      if (other.residedOutOfStateIndicator != null)
        return false;
    } else if (!residedOutOfStateIndicator.equals(other.residedOutOfStateIndicator))
      return false;
    if (stateCode == null) {
      if (other.stateCode != null)
        return false;
    } else if (!stateCode.equals(other.stateCode))
      return false;
    if (streetName == null) {
      if (other.streetName != null)
        return false;
    } else if (!streetName.equals(other.streetName))
      return false;
    if (streetNumber == null) {
      if (other.streetNumber != null)
        return false;
    } else if (!streetNumber.equals(other.streetNumber))
      return false;
    if (suffixTitleDescription == null) {
      if (other.suffixTitleDescription != null)
        return false;
    } else if (!suffixTitleDescription.equals(other.suffixTitleDescription))
      return false;
    if (zipNumber == null) {
      if (other.zipNumber != null)
        return false;
    } else if (!zipNumber.equals(other.zipNumber))
      return false;
    if (zipSuffixNumber == null) {
      if (other.zipSuffixNumber != null)
        return false;
    } else if (!zipSuffixNumber.equals(other.zipSuffixNumber))
      return false;
    if (super.getLastUpdatedId() == null) {
      if (other.getLastUpdatedId() != null) {
        return false;
      }
    } else if (!super.getLastUpdatedId().equals(other.getLastUpdatedId())) {
      return false;
    }
    if (super.getLastUpdatedTime() == null) {
      if (other.getLastUpdatedTime() != null) {
        return false;
      }
    } else if (!super.getLastUpdatedTime().equals(other.getLastUpdatedTime())) {
      return false;
    }
    return true;
  }

}
