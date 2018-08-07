package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.validation.Date;
import gov.ca.cwds.rest.validation.NotEqual;
import gov.ca.cwds.rest.validation.OnlyIf;
import gov.ca.cwds.rest.validation.ParticipantValidator;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Participant.
 *
 * @author CWDS API Team
 */
@JsonSnakeCase
@OnlyIf(ifProperty = "primaryLanguage", property = "secondaryLanguage")
@NotEqual(ifProperty = "primaryLanguage", thenProperty = "secondaryLanguage")
@JsonPropertyOrder({"id", "legacySourceTable", "legacyId", "firstName", "lastName", "gender", "ssn",
    "dateOfBirth", "roles", "addresses", "race_ethnicity"})
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Participant extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = false, value = "Participant Id", example = "12345")
  private long id;

  @JsonProperty("legacy_source_table")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Source Table",
      example = "CLIENT_T")
  private String legacySourceTable;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Client Id",
      example = "ABC1234567")
  @Size(max = CMS_ID_LEN)
  private String legacyId;

  @ApiModelProperty(required = true, readOnly = false)
  @JsonProperty("legacy_descriptor")
  @Valid
  private LegacyDescriptor legacyDescriptor;

  @JsonProperty("first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "First Name", example = "John")
  private String firstName;

  @JsonProperty("middle_name")
  @Size(min = 0, max = 20)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "middle name")
  private String middleName;

  @JsonProperty("last_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Last name", example = "Smith")
  private String lastName;

  @JsonProperty("name_suffix")
  @ApiModelProperty(required = false, readOnly = false, value = "name suffix", example = "Jr.")
  private String nameSuffix;

  @OneOf(value = {"M", "F", "U", "I"}, ignoreCase = true, ignoreWhitespace = true)
  @NotNull
  @JsonProperty("gender")
  @ApiModelProperty(required = false, readOnly = false, value = "Gender Code", example = "M",
      allowableValues = "M, F, U, I")
  private String gender;

  @JsonProperty("ssn")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "123456789")
  // This regular expression(regexp) validates the ssn should be only numeric and length 9
  @Pattern(regexp = "^(|[0-9]{9})$")
  private String ssn;

  @Date
  @JsonProperty("date_of_birth")
  @ApiModelProperty(required = false, readOnly = false, value = "Date of Birth",
      example = "2001-09-13")
  private String dateOfBirth;

  @JsonProperty("primary_language")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1234",
      notes = "The code for primary Language")
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.LANGUAGE_CODE,
      ignoreable = true, ignoredValue = 0)
  private Short primaryLanguage;

  @JsonProperty("secondary_language")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1234",
      notes = "The code for secondary Language")
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.LANGUAGE_CODE,
      ignoreable = true, ignoredValue = 0)
  private Short secondaryLanguage;

  // only used for reporter
  @JsonProperty("reporter_confidential_waiver")
  @ApiModelProperty(required = false, value = "Reporter Confidential Waiver", example = "N")
  private boolean reporterConfidentialWaiver;

  // only used for reporter
  @JsonProperty("reporter_employer_name")
  @ApiModelProperty(required = false, value = "Reporter Employer Name",
      example = "Buisness Name, Inc")
  private String reporterEmployerName;

  // only used for Client
  @JsonProperty("client_staff_person_added")
  @ApiModelProperty(required = false, value = "Staff Person Added", example = "N")
  private boolean clientStaffPersonAdded;

  @JsonProperty("limited_access_code")
  @Size(max = 1)
  @OneOf(value = {"R", "S", "N"})
  @ApiModelProperty(required = true, readOnly = false, value = "Limited Access Code", example = "R")
  private String sensitivityIndicator;

  @JsonProperty("screening_id")
  @ApiModelProperty(required = false, readOnly = false, value = "Screening Id", example = "12345")
  private long screeningId;

  @JsonProperty("approximate_age")
  @Size(max = 3)
  @ApiModelProperty(required = false, readOnly = false, value = "Approximate Age", example = "12")
  private String approximateAge;

  @OneOf(value = {"Y", "M", "W", "D"}, ignoreCase = true, ignoreWhitespace = true)
  @JsonProperty("approximate_age_units")
  @ApiModelProperty(required = false, readOnly = false, value = "Approximate Age Units",
      example = "years")
  private String approximateAgeUnits;

  @Valid
  @JsonProperty("roles")
  @ApiModelProperty(required = true, readOnly = false, value = "Role of participant",
      dataType = "java.util.List", example = "['Victim', 'Mandated Reporter']")
  private Set<String> roles;

  @Valid
  @ApiModelProperty(dataType = "List[gov.ca.cwds.rest.api.domain.Address]")
  @JsonProperty("addresses")
  private Set<Address> addresses;

  @Valid
  @ApiModelProperty(dataType = "gov.ca.cwds.rest.api.domain.RaceAndEthnicity")
  @JsonProperty("race_ethnicity")
  private RaceAndEthnicity raceAndEthnicity;

  @Valid
  @ApiModelProperty(dataType = "gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies")
  @JsonProperty("safely_surrendered_babies")
  private SafelySurrenderedBabies safelySurrenderedBabies;

  @JsonIgnore
  private List<Csec> csecs = new ArrayList<>();

  @JsonIgnore
  private boolean perpetrator;

  @JsonIgnore
  private boolean victim;

  @JsonIgnore
  private boolean reporter;

  /**
   * empty constructor
   */
  public Participant() {
    super();
  }

  /**
   * Constructor
   *
   * @param id The id of the Participant
   * @param legacySourceTable - legacy source table name
   * @param clientId - the legacy clientId
   * @param legacyDescriptor legacy descriptor
   * @param firstName The first Name
   * @param middleName The middle Name
   * @param lastName The last Name
   * @param nameSuffix The participants suffix Name
   * @param gender The Gender
   * @param ssn The social security number
   * @param dateOfBirth The date Of Birth
   * @param primaryLanguage primary language
   * @param secondaryLanguage secondary language
   * @param screeningId The screening Id
   * @param reporterConfidentialWaiver Confidential Waiver indicator for reporter
   * @param reporterEmployerName Reporter Employer Name
   * @param clientStaffPersonAdded Client Staff person Added indicator
   * @param sensitivityIndicator Sensitivity Indicator
   * @param approximateAge Approximate Age
   * @param approximateAgeUnits Approximate Age Units
   * @param roles The roles of the participant
   * @param addresses The addresses of the participant
   * @param raceAndEthnicity The race And Ethnicity
   * @param csecs CSEC list
   * @throws ServiceException throw any exception
   */
  @JsonCreator
  public Participant(@JsonProperty("id") long id,
      @JsonProperty("legacy_source_table") String legacySourceTable,
      @JsonProperty("legacy_client_id") String clientId,
      @JsonProperty("legacy_descriptor") LegacyDescriptor legacyDescriptor,
      @JsonProperty("first_name") String firstName, @JsonProperty("middle_name") String middleName,
      @JsonProperty("last_name") String lastName, @JsonProperty("name_suffix") String nameSuffix,
      @JsonProperty("gender") String gender, @JsonProperty("ssn") String ssn,
      @JsonProperty("date_of_birth") String dateOfBirth,
      @JsonProperty("primary_language") Short primaryLanguage,
      @JsonProperty("secondary_language") Short secondaryLanguage,
      @JsonProperty("screening_id") long screeningId,
      @JsonProperty("reporter_confidential_waiver") boolean reporterConfidentialWaiver,
      @JsonProperty("reporter_employer_name") String reporterEmployerName,
      @JsonProperty("client_staff_person_added") boolean clientStaffPersonAdded,
      @JsonProperty("limited_access_code") String sensitivityIndicator,
      @JsonProperty("approximate_age") String approximateAge,
      @JsonProperty("approximate_age_units") String approximateAgeUnits,
      @JsonProperty("roles") Set<String> roles, @JsonProperty("addresses") Set<Address> addresses,
      @JsonProperty("race_ethnicity") RaceAndEthnicity raceAndEthnicity,
      @JsonProperty("csec") List<Csec> csecs) {
    super();
    this.id = id;
    this.legacySourceTable = legacySourceTable;
    this.legacyId = clientId;
    this.legacyDescriptor = legacyDescriptor;
    this.screeningId = screeningId;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.nameSuffix = nameSuffix;
    this.reporterConfidentialWaiver = reporterConfidentialWaiver;
    this.reporterEmployerName = reporterEmployerName;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
    this.primaryLanguage = primaryLanguage;
    this.secondaryLanguage = secondaryLanguage;
    this.approximateAge = approximateAge;
    this.approximateAgeUnits = approximateAgeUnits;
    this.roles = roles;
    this.addresses = addresses;
    this.clientStaffPersonAdded = clientStaffPersonAdded;
    this.sensitivityIndicator = sensitivityIndicator;
    this.raceAndEthnicity = raceAndEthnicity;
    this.csecs = csecs;

    try {
      victim = ParticipantValidator.hasVictimRole(this);
      reporter = ParticipantValidator.isReporterType(this);
      perpetrator = ParticipantValidator.isPerpetrator(this);
    } catch (Exception e) {
      throw new ServiceException("An Error Occured while validating participant roles", e);
    }
  }

  /**
   * @return id
   */
  public long getId() {
    return id;
  }

  /**
   * @return the legacy source table name
   */
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  /**
   * @param legacySourceTable - the legacy source table name
   */
  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  /**
   * @return the legacy clientId
   */
  public String getLegacyId() {
    return legacyId;
  }

  /**
   * @param clientId - the legacy Id
   */
  public void setLegacyId(String clientId) {
    this.legacyId = clientId;
  }

  /**
   * @return the legacyDescriptor
   */
  public LegacyDescriptor getLegacyDescriptor() {
    if (legacyDescriptor == null) {
      legacyDescriptor = new LegacyDescriptor();
    }
    return legacyDescriptor;
  }

  /**
   * @param legacyDescriptor The legacy Descriptor
   */
  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the middleName
   */
  public String getMiddleName() {

    return middleName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the name suffix
   */
  public String getNameSuffix() {
    return nameSuffix;
  }

  /**
   * @return the gender
   */
  public String getGender() {
    return gender;
  }

  /**
   * @return the screeningId
   */
  public long getScreeningId() {
    return screeningId;
  }

  /**
   * @return the roles
   */
  public Set<String> getRoles() {
    return this.roles;
  }

  /**
   * @return the dateOfBirth
   */
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * @return the ssn
   */
  public String getSsn() {
    return ssn;
  }

  /**
   * @return the primaryLanguage
   */
  public Short getPrimaryLanguage() {
    return primaryLanguage;
  }

  /**
   * @return the secondaryLanguage
   */
  public Short getSecondaryLanguage() {
    return secondaryLanguage;
  }

  /**
   * @return the reporterConfidentialWaiver
   */
  public boolean isReporterConfidentialWaiver() {
    return reporterConfidentialWaiver;
  }

  /**
   * @return the reporterEmployerName
   */
  public String getReporterEmployerName() {
    return reporterEmployerName;
  }

  /**
   * @return the clientStaffPersonAdded
   */
  public boolean isClientStaffPersonAdded() {
    return clientStaffPersonAdded;
  }

  /**
   * @return the sensitivityIndicator
   */
  public String getSensitivityIndicator() {
    return sensitivityIndicator;
  }

  /**
   * @return the approximateAge
   */
  public String getApproximateAge() {
    return approximateAge;
  }

  /**
   * @return the approximateAgeUnits
   */
  public String getApproximateAgeUnits() {
    return approximateAgeUnits;
  }

  /**
   * @return addresses
   */
  public Set<Address> getAddresses() {
    return addresses;
  }

  /**
   * @param addresses - domain addresses
   */
  public void setAddresses(Set<Address> addresses) {
    this.addresses = addresses;
  }

  /**
   * adds a set of addresses to current addresses.
   *
   * @param addresses - domain addresses
   */
  public void addAddresses(Set<Address> addresses) {
    if (addresses == null) {
      return;
    }
    if (this.addresses == null) {
      this.addresses = new HashSet<>();
    }
    this.addresses.addAll(addresses);
  }

  /**
   * @return the raceAndEthnicity
   */
  public RaceAndEthnicity getRaceAndEthnicity() {
    return raceAndEthnicity;
  }

  /**
   * @return boolean
   */
  public boolean isPerpetrator() {
    return perpetrator;
  }

  /**
   * @return boolean
   */
  public boolean isVictim() {
    return victim;
  }

  /**
   * @return boolean
   */
  public boolean isReporter() {
    return reporter;
  }

  public List<Csec> getCsecs() {
    return csecs;
  }

  public void setCsecs(List<Csec> csecs) {
    this.csecs = csecs;
  }

  public SafelySurrenderedBabies getSafelySurrenderedBabies() {
    return safelySurrenderedBabies;
  }

  public void setSafelySurrenderedBabies(SafelySurrenderedBabies safelySurrenderedBabies) {
    this.safelySurrenderedBabies = safelySurrenderedBabies;
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
