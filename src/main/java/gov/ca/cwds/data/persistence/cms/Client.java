package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link PersistentObject} representing a Client.
 * 
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Client.findAll",
    query = "FROM Client WHERE sensitivityIndicator = 'N' AND soc158SealedClientIndicator = 'N'")
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Client.findByIds",
    query = "FROM Client WHERE id IN :ids")
@Entity
@Table(name = "CLIENT_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client extends BaseClient {

  private static final long serialVersionUID = 1L;

  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @OneToMany(cascade = CascadeType.DETACH)
  @JoinColumn(name = "FKCLIENT_T", referencedColumnName = "IDENTIFIER")
  private Set<ClientAddress> clientAddress = new HashSet<>();

  @ToStringExclude
  @Fetch(FetchMode.SELECT)
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "ESTBLSH_ID", referencedColumnName = "IDENTIFIER", nullable = false,
      updatable = false, insertable = false)
  private Set<ClientScpEthnicity> clientScpEthnicities = new HashSet<>();

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Client() {
    super();
  }

  /**
   * Construct from all fields.
   * 
   * @param adjudicatedDelinquentIndicator The adjudicatedDelinquentIndicator
   * @param adoptionStatusCode The adoptionStatusCode
   * @param alienRegistrationNumber The alienRegistrationNumber
   * @param birthCity The birthCity
   * @param birthCountryCodeType The birthCountryCodeType
   * @param birthDate The birthDate
   * @param birthFacilityName The birthFacilityName
   * @param birthStateCodeType The birthStateCodeType
   * @param birthplaceVerifiedIndicator The birthplaceVerifiedIndicator
   * @param childClientIndicatorVar The childClientIndicatorVar
   * @param clientIndexNumber The clientIndexNumber
   * @param commentDescription The commentDescription
   * @param commonFirstName The commonFirstName
   * @param commonMiddleName The commonMiddleName
   * @param commonLastName The commonLastName
   * @param confidentialityActionDate The confidentialityActionDate
   * @param confidentialityInEffectIndicator The confidentialityInEffectIndicator
   * @param creationDate The creationDate
   * @param currCaChildrenServIndicator The currCaChildrenServIndicator
   * @param currentlyOtherDescription The currentlyOtherDescription
   * @param currentlyRegionalCenterIndicator The currentlyRegionalCenterIndicator
   * @param deathDate The deathDate
   * @param deathDateVerifiedIndicator The deathDateVerifiedIndicator
   * @param deathPlace The deathPlace
   * @param deathReasonText The deathReasonText
   * @param driverLicenseNumber The driverLicenseNumber
   * @param driverLicenseStateCodeType The driverLicenseStateCodeType
   * @param emailAddress The emailAddress
   * @param estimatedDobCode The estimatedDobCode
   * @param ethUnableToDetReasonCode The ethUnableToDetReasonCode
   * @param fatherParentalRightTermDate The fatherParentalRightTermDate
   * @param genderCode The genderCode
   * @param genderIdentityType - genderIdentityType
   * @param giNotListedDescription - giNotListedDescription
   * @param genderExpressionType - genderExpressionType
   * @param healthSummaryText The healthSummaryText
   * @param hispUnableToDetReasonCode The hispUnableToDetReasonCode
   * @param hispanicOriginCode The hispanicOriginCode
   * @param id The id
   * @param immigrationCountryCodeType The immigrationCountryCodeType
   * @param immigrationStatusType The immigrationStatusType
   * @param incapacitatedParentCode The incapacitatedParentCode
   * @param individualHealthCarePlanIndicator The individualHealthCarePlanIndicator
   * @param limitationOnScpHealthIndicator The limitationOnScpHealthIndicator
   * @param literateCode The literateCode
   * @param maritalCohabitatnHstryIndicatorVar The maritalCohabitatnHstryIndicatorVar
   * @param maritalStatusType The maritalStatusType
   * @param militaryStatusCode The militaryStatusCode
   * @param motherParentalRightTermDate The motherParentalRightTermDate
   * @param namePrefixDescription The namePrefixDescription
   * @param nameType The nameType
   * @param outstandingWarrantIndicator The outstandingWarrantIndicator
   * @param prevCaChildrenServIndicator The prevCaChildrenServIndicator
   * @param prevOtherDescription The prevOtherDescription
   * @param prevRegionalCenterIndicator The prevRegionalCenterIndicator
   * @param primaryEthnicityType The primaryEthnicityType
   * @param primaryLanguageType The primaryLanguageType
   * @param religionType The religionType
   * @param secondaryLanguageType The secondaryLanguageType
   * @param sensitiveHlthInfoOnFileIndicator The sensitiveHlthInfoOnFileIndicator
   * @param sensitivityIndicator The sensitivityIndicator
   * @param sexualOrientationType - sexualOrientationType
   * @param soUnableToDetermineCode - soUnableToDetermineCode
   * @param soNotListedDescrption - soNotListedDescrption
   * @param soc158PlacementCode The soc158PlacementCode
   * @param soc158SealedClientIndicator The soc158SealedClientIndicator
   * @param socialSecurityNumChangedCode The socialSecurityNumChangedCode
   * @param socialSecurityNumber The socialSecurityNumber
   * @param suffixTitleDescription The suffixTitleDescription
   * @param tribalAncestryClientIndicatorVar The tribalAncestryClientIndicatorVar
   * @param tribalMembrshpVerifctnIndicatorVar The tribalMembrshpVerifctnIndicatorVar
   * @param unemployedParentCode The unemployedParentCode
   * @param zippyCreatedIndicator The zippyCreatedIndicator
   * @param clientAddress The ClientAddres
   */
  public Client(String adjudicatedDelinquentIndicator, String adoptionStatusCode,
      String alienRegistrationNumber, String birthCity, Short birthCountryCodeType, Date birthDate,
      String birthFacilityName, Short birthStateCodeType, String birthplaceVerifiedIndicator,
      String childClientIndicatorVar, String clientIndexNumber, String commentDescription,
      String commonFirstName, String commonMiddleName, String commonLastName,
      Date confidentialityActionDate, String confidentialityInEffectIndicator, Date creationDate,
      String currCaChildrenServIndicator, String currentlyOtherDescription,
      String currentlyRegionalCenterIndicator, Date deathDate, String deathDateVerifiedIndicator,
      String deathPlace, String deathReasonText, String driverLicenseNumber,
      Short driverLicenseStateCodeType, String emailAddress, String estimatedDobCode,
      String ethUnableToDetReasonCode, Date fatherParentalRightTermDate, String genderCode,
      Short genderIdentityType, String giNotListedDescription, Short genderExpressionType,
      String healthSummaryText, String hispUnableToDetReasonCode, String hispanicOriginCode,
      String id, Short immigrationCountryCodeType, Short immigrationStatusType,
      String incapacitatedParentCode, String individualHealthCarePlanIndicator,
      String limitationOnScpHealthIndicator, String literateCode,
      String maritalCohabitatnHstryIndicatorVar, Short maritalStatusType, String militaryStatusCode,
      Date motherParentalRightTermDate, String namePrefixDescription, Short nameType,
      String outstandingWarrantIndicator, String prevCaChildrenServIndicator,
      String prevOtherDescription, String prevRegionalCenterIndicator, Short primaryEthnicityType,
      Short primaryLanguageType, Short religionType, Short secondaryLanguageType,
      String sensitiveHlthInfoOnFileIndicator, String sensitivityIndicator,
      Short sexualOrientationType, String soUnableToDetermineCode, String soNotListedDescrption,
      String soc158PlacementCode, String soc158SealedClientIndicator,
      String socialSecurityNumChangedCode, String socialSecurityNumber,
      String suffixTitleDescription, String tribalAncestryClientIndicatorVar,
      String tribalMembrshpVerifctnIndicatorVar, String unemployedParentCode,
      String zippyCreatedIndicator, Set<ClientAddress> clientAddress) {
    super();
    this.adjudicatedDelinquentIndicator = adjudicatedDelinquentIndicator;
    this.adoptionStatusCode = adoptionStatusCode;
    this.alienRegistrationNumber = alienRegistrationNumber;
    this.birthCity = birthCity;
    this.birthCountryCodeType = birthCountryCodeType;
    this.birthDate = freshDate(birthDate);
    this.birthFacilityName = birthFacilityName;
    this.birthStateCodeType = birthStateCodeType;
    this.birthplaceVerifiedIndicator = birthplaceVerifiedIndicator;
    this.childClientIndicatorVar = childClientIndicatorVar;
    this.clientIndexNumber = clientIndexNumber;
    this.commentDescription = commentDescription;
    this.commonFirstName = commonFirstName;
    this.commonMiddleName = StringUtils.isBlank(commonMiddleName) ? "" : commonMiddleName;
    this.commonLastName = commonLastName;
    this.confidentialityActionDate = freshDate(confidentialityActionDate);
    this.confidentialityInEffectIndicator = confidentialityInEffectIndicator;
    this.creationDate = freshDate(creationDate);
    this.currCaChildrenServIndicator = currCaChildrenServIndicator;
    this.currentlyOtherDescription = currentlyOtherDescription;
    this.currentlyRegionalCenterIndicator = currentlyRegionalCenterIndicator;
    this.deathDate = freshDate(deathDate);
    this.deathDateVerifiedIndicator = deathDateVerifiedIndicator;
    this.deathPlace = deathPlace;
    this.deathReasonText = deathReasonText;
    this.driverLicenseNumber = driverLicenseNumber;
    this.driverLicenseStateCodeType = driverLicenseStateCodeType;
    this.emailAddress = emailAddress;
    this.estimatedDobCode = estimatedDobCode;
    this.ethUnableToDetReasonCode = ethUnableToDetReasonCode;
    this.fatherParentalRightTermDate = freshDate(fatherParentalRightTermDate);
    this.genderCode = genderCode;
    this.genderIdentityType = genderIdentityType;
    this.giNotListedDescription = giNotListedDescription;
    this.genderExpressionType = genderExpressionType;
    this.healthSummaryText = healthSummaryText;
    this.hispUnableToDetReasonCode = hispUnableToDetReasonCode;
    this.hispanicOriginCode = hispanicOriginCode;
    this.id = id;
    this.immigrationCountryCodeType = immigrationCountryCodeType;
    this.immigrationStatusType = immigrationStatusType;
    this.incapacitatedParentCode = incapacitatedParentCode;
    this.individualHealthCarePlanIndicator = individualHealthCarePlanIndicator;
    this.limitationOnScpHealthIndicator = limitationOnScpHealthIndicator;
    this.literateCode = literateCode;
    this.maritalCohabitatnHstryIndicatorVar = maritalCohabitatnHstryIndicatorVar;
    this.maritalStatusType = maritalStatusType;
    this.militaryStatusCode = militaryStatusCode;
    this.motherParentalRightTermDate = freshDate(motherParentalRightTermDate);
    this.namePrefixDescription = namePrefixDescription;
    this.nameType = nameType;
    this.outstandingWarrantIndicator = outstandingWarrantIndicator;
    this.prevCaChildrenServIndicator = prevCaChildrenServIndicator;
    this.prevOtherDescription = prevOtherDescription;
    this.prevRegionalCenterIndicator = prevRegionalCenterIndicator;
    this.primaryEthnicityType = primaryEthnicityType;
    this.primaryLanguageType = primaryLanguageType;
    this.religionType = religionType;
    this.secondaryLanguageType = secondaryLanguageType;
    this.sensitiveHlthInfoOnFileIndicator = sensitiveHlthInfoOnFileIndicator;
    this.sensitivityIndicator = sensitivityIndicator;
    this.sexualOrientationType = sexualOrientationType;
    this.soUnableToDetermineCode = soUnableToDetermineCode;
    this.soNotListedDescrption = soNotListedDescrption;
    this.soc158PlacementCode = soc158PlacementCode;
    this.soc158SealedClientIndicator = soc158SealedClientIndicator;
    this.socialSecurityNumChangedCode = socialSecurityNumChangedCode;
    this.socialSecurityNumber = socialSecurityNumber;
    this.suffixTitleDescription = suffixTitleDescription == null ? "" : suffixTitleDescription;
    this.tribalAncestryClientIndicatorVar = tribalAncestryClientIndicatorVar;
    this.tribalMembrshpVerifctnIndicatorVar = tribalMembrshpVerifctnIndicatorVar;
    this.unemployedParentCode = unemployedParentCode;
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    this.clientAddress = clientAddress;
  }

  /**
   * Constructor. Construct from counterpart domain class.
   * 
   * @param id primary key
   * @param client The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   * @param lastUpdatedTime the time when this object is last updated
   */
  public Client(String id, gov.ca.cwds.rest.api.domain.cms.Client client, String lastUpdatedId,
      Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    try {
      this.id = id;
      this.adjudicatedDelinquentIndicator =
          DomainChef.cookBoolean(client.getAdjudicatedDelinquentIndicator());
      this.adoptionStatusCode = client.getAdoptionStatusCode();
      this.alienRegistrationNumber = client.getAlienRegistrationNumber();
      this.birthCity = client.getBirthCity();
      this.birthCountryCodeType = client.getBirthCountryCodeType();
      this.birthDate = DomainChef.uncookDateString(client.getBirthDate());
      this.birthFacilityName = client.getBirthFacilityName();
      this.birthStateCodeType = client.getBirthStateCodeType();
      this.birthplaceVerifiedIndicator =
          DomainChef.cookBoolean(client.getBirthplaceVerifiedIndicator());
      this.childClientIndicatorVar = DomainChef.cookBoolean(client.getChildClientIndicatorVar());
      this.clientIndexNumber = client.getClientIndexNumber();
      this.commentDescription = client.getCommentDescription();
      this.commonFirstName = client.getCommonFirstName();
      this.commonMiddleName =
          StringUtils.isBlank(client.getCommonMiddleName()) ? "" : client.getCommonMiddleName();
      this.commonLastName = client.getCommonLastName();
      this.confidentialityActionDate =
          DomainChef.uncookDateString(client.getConfidentialityActionDate());
      this.confidentialityInEffectIndicator =
          DomainChef.cookBoolean(client.getConfidentialityInEffectIndicator());
      this.creationDate = DomainChef.uncookDateString(client.getCreationDate());
      this.currCaChildrenServIndicator =
          DomainChef.cookBoolean(client.getCurrCaChildrenServIndicator());
      this.currentlyOtherDescription = client.getCurrentlyOtherDescription();
      this.currentlyRegionalCenterIndicator =
          DomainChef.cookBoolean(client.getCurrentlyRegionalCenterIndicator());
      this.deathDate = DomainChef.uncookDateString(client.getDeathDate());
      this.deathDateVerifiedIndicator =
          DomainChef.cookBoolean(client.getDeathDateVerifiedIndicator());
      this.deathPlace = client.getDeathPlace();
      this.deathReasonText = client.getDeathReasonText();
      this.driverLicenseNumber = client.getDriverLicenseNumber();
      this.driverLicenseStateCodeType = client.getDriverLicenseStateCodeType();
      this.emailAddress = client.getEmailAddress();
      this.estimatedDobCode = client.getEstimatedDobCode();
      this.ethUnableToDetReasonCode =
          StringUtils.isBlank(client.getEthUnableToDetReasonCode()) ? null
              : client.getEthUnableToDetReasonCode();
      this.fatherParentalRightTermDate =
          DomainChef.uncookDateString(client.getFatherParentalRightTermDate());
      this.genderCode = client.getGenderCode();
      this.genderIdentityType = client.getGenderIdentityType();
      this.giNotListedDescription = client.getGiNotListedDescription();
      this.genderExpressionType = client.getGenderExpressionType();
      this.healthSummaryText = client.getHealthSummaryText();
      this.hispUnableToDetReasonCode =
          StringUtils.isBlank(client.getHispUnableToDetReasonCode()) ? null
              : client.getHispUnableToDetReasonCode();
      this.hispanicOriginCode =
          StringUtils.isBlank(client.getHispanicOriginCode()) ? "" : client.getHispanicOriginCode();
      this.immigrationCountryCodeType = client.getImmigrationCountryCodeType();
      this.immigrationStatusType = client.getImmigrationStatusType();
      this.incapacitatedParentCode = client.getIncapacitatedParentCode();
      this.individualHealthCarePlanIndicator =
          DomainChef.cookBoolean(client.getIndividualHealthCarePlanIndicator());
      this.limitationOnScpHealthIndicator =
          DomainChef.cookBoolean(client.getLimitationOnScpHealthIndicator());
      this.literateCode = client.getLiterateCode();
      this.maritalCohabitatnHstryIndicatorVar =
          DomainChef.cookBoolean(client.getMaritalCohabitatnHstryIndicatorVar());
      this.maritalStatusType = client.getMaritalStatusType();
      this.militaryStatusCode = client.getMilitaryStatusCode();
      this.motherParentalRightTermDate =
          DomainChef.uncookDateString(client.getMotherParentalRightTermDate());
      this.namePrefixDescription = client.getNamePrefixDescription();
      this.nameType = client.getNameType();
      this.outstandingWarrantIndicator =
          DomainChef.cookBoolean(client.getOutstandingWarrantIndicator());
      this.prevCaChildrenServIndicator =
          DomainChef.cookBoolean(client.getPrevCaChildrenServIndicator());
      this.prevOtherDescription = client.getPrevOtherDescription();
      this.prevRegionalCenterIndicator =
          DomainChef.cookBoolean(client.getPrevRegionalCenterIndicator());
      this.primaryEthnicityType = client.getPrimaryEthnicityType();
      this.primaryLanguageType = client.getPrimaryLanguage();
      this.religionType = client.getReligionType();
      this.secondaryLanguageType = client.getSecondaryLanguage();
      this.sensitiveHlthInfoOnFileIndicator =
          DomainChef.cookBoolean(client.getSensitiveHlthInfoOnFileIndicator());
      this.sensitivityIndicator = client.getSensitivityIndicator();
      this.sexualOrientationType = client.getSexualOrientationType();
      this.soUnableToDetermineCode = client.getSoUnableToDetermineCode();
      this.soNotListedDescrption = client.getSoNotListedDescrption();
      this.soc158PlacementCode = client.getSoc158PlacementCode();
      this.soc158SealedClientIndicator =
          DomainChef.cookBoolean(client.getSoc158SealedClientIndicator());
      this.socialSecurityNumChangedCode = client.getSocialSecurityNumChangedCode();
      this.socialSecurityNumber = StringUtils.isBlank(client.getSocialSecurityNumber()) ? ""
          : client.getSocialSecurityNumber();
      this.suffixTitleDescription =
          client.getSuffixTitleDescription() == null ? "" : client.getSuffixTitleDescription();
      this.tribalAncestryClientIndicatorVar =
          DomainChef.cookBoolean(client.getTribalAncestryClientIndicatorVar());
      this.tribalMembrshpVerifctnIndicatorVar =
          DomainChef.cookBoolean(client.getTribalMembrshpVerifctnIndicatorVar());
      this.unemployedParentCode = client.getUnemployedParentCode();
      this.zippyCreatedIndicator = DomainChef.cookBoolean(client.getZippyCreatedIndicator());
    } catch (ApiException e) {
      throw new PersistenceException(e);
    }
  }

  /**
   * @return the ClientAddress
   */
  public Set<ClientAddress> getClientAddress() {
    return clientAddress;
  }

  /**
   * @return the clientScpEthnicities
   */
  public Set<ClientScpEthnicity> getClientScpEthnicities() {
    return clientScpEthnicities;
  }

  /**
   * @param clientScpEthnicities - clientScpEthnicities
   */
  public void setClientScpEthnicities(Set<ClientScpEthnicity> clientScpEthnicities) {
    this.clientScpEthnicities = clientScpEthnicities;
  }

  /**
   * @param clientAddress - clientAddress
   */
  public void setClientAddress(Set<ClientAddress> clientAddress) {
    this.clientAddress = clientAddress;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
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
