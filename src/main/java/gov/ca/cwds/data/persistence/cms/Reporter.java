package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.ReadablePhone;
import gov.ca.cwds.data.ns.NsPersistentObject;
import gov.ca.cwds.data.std.ApiPhoneAware;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link NsPersistentObject} representing a Reporter.
 * 
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Reporter.findAll",
    query = "FROM Reporter WHERE confidentialWaiverIndicator = 'Y' AND referralId IN "
        + "(SELECT id FROM Referral WHERE limitedAccessCode = 'N')")
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Reporter.findAllUpdatedAfterOLD",
    query = "FROM Reporter WHERE lastUpdatedTime > :after AND confidentialWaiverIndicator = 'Y' AND referralId IN "
        + "(SELECT id FROM Referral WHERE limitedAccessCode = 'N')")
@NamedQuery(
    name = "gov.ca.cwds.data.persistence.cms.Reporter.findInvestigationReportersByReferralId",
    query = "FROM Reporter WHERE confidentialWaiverIndicator = 'Y' AND referralId = :referralId")
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Reporter.findByReferralIds",
    query = "FROM Reporter WHERE referralId IN :referralIds")
@Entity
@Table(name = "REPTR_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings({"serial", "squid:S3437", "squid:S2160",
    "fb-contrib:COM_COPIED_OVERRIDDEN_METHOD"})
public class Reporter extends BaseReporter {

  /**
   * #147241489: referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent referral,
   * lawEnforcement and reporterDrmsDocument record.
   * </p>
   */
  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKREFERL_T", nullable = false, updatable = false, insertable = false)
  private Referral referral;

  /**
   * #147241489: referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent referral,
   * lawEnforcement and reporterDrmsDocument record.
   * </p>
   */
  @HashCodeExclude
  @EqualsExclude
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKLAW_ENFT", nullable = true, updatable = false, insertable = false)
  private LawEnforcementEntity lawEnforcement;

  /**
   * #147241489: referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent referral,
   * lawEnforcement and reporterDrmsDocument record.
   * </p>
   */
  @HashCodeExclude
  @EqualsExclude
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "FDBACK_DOC", nullable = true, updatable = false, insertable = false)
  private DrmsDocument drmsDocument;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Reporter() {
    super();
  }

  /**
   * @param referralId = Identifier
   * @param badgeNumber = badge number
   * @param cityName = city
   * @param colltrClientRptrReltnshpType - client/reporter relationship type
   * @param communicationMethodType - communication method type
   * @param confidentialWaiverIndicator - confidential waiver indicator
   * @param drmsMandatedRprtrFeedback = DRMS mandated reporter
   * @param employerName - name
   * @param feedbackDate - date
   * @param feedbackRequiredIndicator - feedback
   * @param firstName = first name
   * @param lastName - last name
   * @param mandatedReporterIndicator - mandated reporter
   * @param messagePhoneExtensionNumber - phone extension
   * @param messagePhoneNumber - phone number
   * @param middleInitialName - middle initial
   * @param namePrefixDescription - prefix
   * @param primaryPhoneNumber - phone number
   * @param primaryPhoneExtensionNumber - extension
   * @param stateCodeType - state code
   * @param streetName - street
   * @param streetNumber - street number
   * @param suffixTitleDescription - suffix
   * @param zipNumber - zip
   * @param lawEnforcementId - law enforcement id
   * @param zipSuffixNumber - suffix
   * @param countySpecificCode - county code
   */
  public Reporter(String referralId, String badgeNumber, String cityName,
      Short colltrClientRptrReltnshpType, Short communicationMethodType,
      String confidentialWaiverIndicator, String drmsMandatedRprtrFeedback, String employerName,
      Date feedbackDate, String feedbackRequiredIndicator, String firstName, String lastName,
      String mandatedReporterIndicator, Integer messagePhoneExtensionNumber,
      Long messagePhoneNumber, String middleInitialName, String namePrefixDescription,
      Long primaryPhoneNumber, Integer primaryPhoneExtensionNumber, Short stateCodeType,
      String streetName, String streetNumber, String suffixTitleDescription, Integer zipNumber,
      String lawEnforcementId, Short zipSuffixNumber, String countySpecificCode) {
    super();
    this.referralId = referralId;
    this.badgeNumber = badgeNumber;
    this.cityName = cityName;
    this.colltrClientRptrReltnshpType = colltrClientRptrReltnshpType;
    this.communicationMethodType = communicationMethodType;
    this.confidentialWaiverIndicator = confidentialWaiverIndicator;
    this.drmsMandatedRprtrFeedback = drmsMandatedRprtrFeedback;
    this.employerName = employerName;
    this.feedbackDate = freshDate(feedbackDate);
    this.feedbackRequiredIndicator = feedbackRequiredIndicator;
    this.firstName = firstName;
    this.lastName = lastName;
    this.mandatedReporterIndicator = mandatedReporterIndicator;
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    this.messagePhoneNumber = messagePhoneNumber;
    this.middleInitialName = middleInitialName;
    this.namePrefixDescription = namePrefixDescription;
    this.primaryPhoneNumber = primaryPhoneNumber;
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    this.stateCodeType = stateCodeType;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.zipNumber = zipNumber;
    this.lawEnforcementId = lawEnforcementId;
    this.zipSuffixNumber = zipSuffixNumber;
    this.countySpecificCode = countySpecificCode;
  }

  /**
   * Constructor
   * 
   * @param reporter The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   * @param lastUpdatedTime the time of last person to update this object
   */
  public Reporter(gov.ca.cwds.rest.api.domain.cms.Reporter reporter, String lastUpdatedId,
      Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    this.referralId = reporter.getReferralId();
    this.badgeNumber = reporter.getBadgeNumber();
    this.cityName = reporter.getCityName();
    this.colltrClientRptrReltnshpType = reporter.getColltrClientRptrReltnshpType();
    this.communicationMethodType = reporter.getCommunicationMethodType();
    this.confidentialWaiverIndicator =
        DomainChef.cookBoolean(reporter.getConfidentialWaiverIndicator());
    this.drmsMandatedRprtrFeedback =
        StringUtils.isBlank(reporter.getDrmsMandatedRprtrFeedback()) ? null
            : reporter.getDrmsMandatedRprtrFeedback();
    this.employerName = reporter.getEmployerName();
    this.feedbackDate = DomainChef.uncookDateString(reporter.getFeedbackDate());
    this.feedbackRequiredIndicator =
        DomainChef.cookBoolean(reporter.getFeedbackRequiredIndicator());
    this.firstName = reporter.getFirstName();
    this.lastName = reporter.getLastName();
    this.mandatedReporterIndicator =
        DomainChef.cookBoolean(reporter.getMandatedReporterIndicator());
    this.messagePhoneExtensionNumber = reporter.getMessagePhoneExtensionNumber();
    this.messagePhoneNumber = reporter.getMessagePhoneNumber();
    this.middleInitialName =
        StringUtils.isBlank(reporter.getMiddleInitialName()) ? "" : reporter.getMiddleInitialName();
    this.namePrefixDescription = StringUtils.isBlank(reporter.getNamePrefixDescription()) ? ""
        : reporter.getNamePrefixDescription();
    this.primaryPhoneNumber = reporter.getPrimaryPhoneNumber();
    this.primaryPhoneExtensionNumber = reporter.getPrimaryPhoneExtensionNumber();
    this.stateCodeType = reporter.getStateCodeType();
    this.streetName = StringUtils.isBlank(reporter.getStreetName()) ? "" : reporter.getStreetName();
    this.streetNumber =
        StringUtils.isBlank(reporter.getStreetNumber()) ? "" : reporter.getStreetNumber();
    this.suffixTitleDescription = StringUtils.isBlank(reporter.getSuffixTitleDescription()) ? ""
        : reporter.getSuffixTitleDescription();
    this.zipNumber = DomainChef.uncookZipcodeString(reporter.getZipcode());
    this.lawEnforcementId =
        StringUtils.isBlank(reporter.getLawEnforcementId()) ? null : reporter.getLawEnforcementId();
    this.zipSuffixNumber = reporter.getZipSuffixNumber();
    this.countySpecificCode = reporter.getCountySpecificCode();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getReferralId();
  }

  /**
   * @return the referralId
   */
  @Override
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the badgeNumber
   */
  @Override
  public String getBadgeNumber() {
    return StringUtils.trimToEmpty(badgeNumber);
  }

  /**
   * @return the cityName
   */
  @Override
  public String getCityName() {
    return StringUtils.trimToEmpty(cityName);
  }

  /**
   * @return the colltrClientRptrReltnshpType
   */
  @Override
  public Short getColltrClientRptrReltnshpType() {
    return colltrClientRptrReltnshpType;
  }

  /**
   * @return the communicationMethodType
   */
  @Override
  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }

  /**
   * @return the confidentialWaiverIndicator
   */
  @Override
  public String getConfidentialWaiverIndicator() {
    return confidentialWaiverIndicator;
  }

  /**
   * @return the drmsMandatedRprtrFeedback
   */
  @Override
  public String getDrmsMandatedRprtrFeedback() {
    return StringUtils.trimToEmpty(drmsMandatedRprtrFeedback);
  }

  /**
   * @return the employerName
   */
  @Override
  public String getEmployerName() {
    return StringUtils.trimToEmpty(employerName);
  }

  /**
   * @return the feedbackDate
   */
  @Override
  public Date getFeedbackDate() {
    return freshDate(feedbackDate);
  }

  /**
   * @return the feedbackRequiredIndicator
   */
  @Override
  public String getFeedbackRequiredIndicator() {
    return feedbackRequiredIndicator;
  }

  /**
   * @return the firstName
   */
  @Override
  public String getFirstName() {
    return StringUtils.trimToEmpty(firstName);
  }

  /**
   * @return the lastName
   */
  @Override
  public String getLastName() {
    return StringUtils.trimToEmpty(lastName);
  }

  /**
   * @return the mandatedReporterIndicator
   */
  @Override
  public String getMandatedReporterIndicator() {
    return mandatedReporterIndicator;
  }

  /**
   * @return the messagePhoneExtensionNumber
   */
  @Override
  public Integer getMessagePhoneExtensionNumber() {
    return messagePhoneExtensionNumber;
  }

  /**
   * @return the messagePhoneNumber
   */
  @Override
  public Long getMessagePhoneNumber() {
    return messagePhoneNumber;
  }

  /**
   * @return the middleInitialName
   */
  @Override
  public String getMiddleInitialName() {
    return StringUtils.trimToEmpty(middleInitialName);
  }

  /**
   * @return the namePrefixDescription
   */
  @Override
  public String getNamePrefixDescription() {
    return StringUtils.trimToEmpty(namePrefixDescription);
  }

  /**
   * @return the primaryPhoneNumber
   */
  @Override
  public Long getPrimaryPhoneNumber() {
    return primaryPhoneNumber;
  }

  /**
   * @return the primaryPhoneExtensionNumber
   */
  @Override
  public Integer getPrimaryPhoneExtensionNumber() {
    return primaryPhoneExtensionNumber;
  }

  /**
   * @return the stateCodeType
   */
  @Override
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
   * @return the suffixTitleDescription
   */
  @Override
  public String getSuffixTitleDescription() {
    return StringUtils.trimToEmpty(suffixTitleDescription);
  }

  /**
   * @return the zipNumber
   */
  @Override
  public Integer getZipNumber() {
    return zipNumber;
  }

  /**
   * @return the lawEnforcementId
   */
  @Override
  public String getLawEnforcementId() {
    return lawEnforcementId;
  }

  /**
   * @return the zipSuffixNumber
   */
  @Override
  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

  /**
   * @return the countySpecificCode
   */
  @Override
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  // ==================
  // IPersonAware:
  // ==================

  @JsonIgnore
  @Override
  @Transient
  public String getMiddleName() {
    return this.getMiddleInitialName();
  }

  @JsonIgnore
  @Override
  @Transient
  public String getGender() {
    // Does not apply.
    return super.getGender();
  }

  @JsonIgnore
  @Override
  @Transient
  public Date getBirthDate() {
    // Does not apply.
    return super.getBirthDate();
  }

  @JsonIgnore
  @Override
  @Transient
  public String getSsn() {
    // Does not apply.
    return super.getSsn();
  }

  @JsonIgnore
  @Override
  @Transient
  public String getNameSuffix() {
    return StringUtils.trimToEmpty(this.suffixTitleDescription);
  }

  // ==================
  // IAddressAware:
  // ==================

  @JsonIgnore
  @Override
  @Transient
  public String getStreetAddress() {
    return StringUtils.trimToEmpty(this.streetNumber) + " "
        + StringUtils.trimToEmpty(this.streetName);
  }

  @JsonIgnore
  @Override
  @Transient
  public String getCity() {
    return StringUtils.trimToEmpty(this.cityName);
  }

  @JsonIgnore
  @Override
  @Transient
  public String getState() {
    return this.getStateCodeType() != null ? this.getStateCodeType().toString() : null;
  }

  @JsonIgnore
  @Override
  @Transient
  public String getZip() {
    StringBuilder buf = new StringBuilder();

    if (this.zipNumber != null) {
      buf.append(zipNumber);
    }
    if (this.getZipSuffixNumber() != null) {
      buf.append('-').append(getZipSuffixNumber());
    }

    return buf.toString();
  }

  @Override
  public String getCounty() {
    return this.countySpecificCode;
  }

  // =======================
  // IMultiplePhonesAware:
  // =======================

  @Override
  public ApiPhoneAware[] getPhones() {
    List<ApiPhoneAware> phones = new ArrayList<>();
    if (this.primaryPhoneNumber != null && primaryPhoneNumber != 0) {
      String extension =
          this.primaryPhoneExtensionNumber != null ? this.primaryPhoneExtensionNumber.toString()
              : null;
      phones.add(new ReadablePhone(null, String.valueOf(this.primaryPhoneNumber), extension, null));
    }

    if (this.messagePhoneNumber != null && messagePhoneNumber != 0) {
      phones.add(new ReadablePhone(null, String.valueOf(this.messagePhoneNumber),
          this.messagePhoneExtensionNumber != null ? this.messagePhoneExtensionNumber.toString()
              : null,
          ApiPhoneAware.PhoneType.Cell));
    }

    return phones.toArray(new ApiPhoneAware[0]);
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
