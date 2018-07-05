package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;
import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;

import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Participant.
 *
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"id", "legacySourceTable", "legacyId", "firstName", "lastName", "gender", "ssn",
    "dateOfBirth", "date_of_death", "roles", "addresses", "races", "ethnicity"})
public class ParticipantIntakeApi extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(value = "Participant Id", example = "12345")
  private String id;

  @JsonProperty("first_name")
  @ApiModelProperty(value = "First Name", example = "John")
  @Size(max = 64)
  private String firstName;

  @JsonProperty("middle_name")
  @Size(min = 0, max = 64)
  @ApiModelProperty(value = "", example = "middle name")
  private String middleName;

  @JsonProperty("last_name")
  @ApiModelProperty(value = "Last name", example = "Smith")
  @Size(max = 64)
  private String lastName;

  @JsonProperty("name_suffix")
  @ApiModelProperty(value = "name suffix", example = "Jr.")
  private String nameSuffix;

  @JsonProperty("gender")
  @OneOf(value = {"male", "female", "unknown", "intersex"})
  @ApiModelProperty(value = "Gender Code", example = "male",
      allowableValues = "male, female, unknown, intersex")
  private String gender;

  @JsonProperty("ssn")
  @ApiModelProperty(value = "", example = "123456789")
  private String ssn;

  @JsonProperty("date_of_birth")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @ApiModelProperty(value = "Date of Birth",
      example = "2001-09-11")
  private Date dateOfBirth;

  @JsonProperty("date_of_death")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @ApiModelProperty(value = "Date of Death",
      example = "2001-12-13")
  private Date dateOfDeath;

  @JsonProperty("approximate_age")
  @ApiModelProperty( value = "Approximate Age", example = "25")
  private String approximateAge;

  @JsonProperty("approximate_age_units")
  @OneOf(value = {"days", "weeks", "months", "years"})
  @ApiModelProperty(value = "Approximate Age Units",
      example = "years", allowableValues = "days, weeks, months, years")
  private String approximateAgeUnits;

  @JsonProperty("languages")
  @ApiModelProperty(dataType = "java.util.List", value = "",
      example = "American Sign Language", notes = "The Participant's Languages")
  private List<String> languages;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = true, value = "Legacy Client Id",
      example = "ABC1234567")
  @Size(max = CMS_ID_LEN)
  private String legacyId;

  @JsonProperty("legacy_source_table")
  @ApiModelProperty(required = true, value = "Legacy Source Table",
      example = "CLIENT_T")
  private String legacySourceTable;


  /*
   * Workafoung for fields containing raw json races ethnicity
   *
   */
  @ApiModelProperty(required = true, value = "Races",
      example = "['White', 'Black or African American']")
  private String races;

  @ApiModelProperty(required = true, value = "Ethnicity",
      example = "{\"hispanic_latino_origin\":\"Yes\",\"ethnicity_detail\":[\"Hispanic\"]}")
  private String ethnicity;

  @JsonProperty("screening_id")
  @ApiModelProperty(value = "Screening Id", example = "12345")
  private String screeningId;

  @Valid
  @JsonProperty("roles")
  @ApiModelProperty(required = true, value = "Role of participant",
      dataType = "java.util.List", example = "['Victim', 'Mandated Reporter']")
  private Set<String> roles = new HashSet<>();

  @Valid
  @ApiModelProperty(dataType = "List[gov.ca.cwds.rest.api.domain.AddressIntakeApi]")
  @JsonProperty("addresses")
  private List<AddressIntakeApi> addresses;

  @Valid
  @ApiModelProperty(dataType = "List[gov.ca.cwds.rest.api.domain.PhoneNumber]")
  @JsonProperty("phone_numbers")
  private Set<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumbers = new HashSet<>();

  @JsonProperty("sealed")
  @ApiModelProperty(value = "sealed", example = "true")
  private Boolean sealed;

  @JsonProperty("sensitive")
  @ApiModelProperty(value = "sensitive", example = "true")
  private Boolean sensitive;

  @JsonProperty("probation_youth")
  @ApiModelProperty(value = "probation youth", example = "true")
  private Boolean probationYouth;

  @JsonProperty("legacy_descriptor")
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private LegacyDescriptor legacyDescriptor;

  @JsonProperty("csec")
  private List<Csec> csecs = new ArrayList<>();

  @JsonProperty("safelySurenderedBabies")
  private SafelySurenderedBabiesIntakeApi safelySurenderedBabies;

  /**
   * empty constructor
   */
  public ParticipantIntakeApi() {
    super();
  }

  /**
   * Constructor
   *
   * @param id The id of the Participant
   * @param legacySourceTable - legacy source table name
   * @param clientId - the legacy clientId
   * @param legacyDescriptor legacy descriptor
   * @param screeningId The screening Id
   * @param firstName The first Name
   * @param middleName The middle Name
   * @param lastName The last Name
   * @param nameSuffix participant's suffix Name
   * @param gender participant's gender
   * @param approximateAge - guesstimate age
   * @param approximateAgeUnits - years or months/weeks for infants
   * @param dateOfBirth date of birth
   * @param dateOfDeath date of death
   * @param languages - languages spoken
   * @param ssn The social security number
   * @param races the races
   * @param ethnicity the ethnicity
   * @param roles The roles of the participant
   * @param addresses The addresses of the participant
   * @param phoneNumbers take a guess
   * @param sealed true if sealed
   * @param sensitive true if sensitive
   */
  @SuppressWarnings("squid:S00107")
  public ParticipantIntakeApi(String id, String legacySourceTable, String clientId,
      LegacyDescriptor legacyDescriptor, String firstName, String middleName, String lastName,
      String nameSuffix, String gender, String approximateAge, String approximateAgeUnits,
      String ssn, Date dateOfBirth, Date dateOfDeath, List<String> languages, String races,
      String ethnicity, String screeningId, Set<String> roles, List<AddressIntakeApi> addresses,
      Set<PhoneNumber> phoneNumbers, Boolean sealed, Boolean sensitive) {

    super();
    this.id = id;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.nameSuffix = nameSuffix;
    this.gender = gender;
    this.ssn = ssn;
    this.dateOfBirth = dateOfBirth != null ? new Date(dateOfBirth.getTime()) : null;
    this.dateOfDeath = dateOfDeath;
    this.approximateAge = approximateAge;
    this.approximateAgeUnits = approximateAgeUnits;
    this.roles = roles;
    this.languages = languages;
    this.races = races;
    this.ethnicity = ethnicity;
    this.legacyId = clientId;
    this.legacySourceTable = legacySourceTable;
    this.legacyDescriptor = legacyDescriptor;
    this.screeningId = screeningId;
    this.addresses = addresses;
    this.phoneNumbers = phoneNumbers;
    this.sealed = sealed;
    this.sensitive = sensitive;
  }

  /**
   * Copy constructor.
   *
   * @param participantEntity participant to copy from
   */
  @SuppressWarnings("squid:S00107")
  public ParticipantIntakeApi(ParticipantEntity participantEntity) {
    super();
    this.id = participantEntity.getId();
    this.firstName = participantEntity.getFirstName();
    this.middleName = participantEntity.getMiddleName();
    this.lastName = participantEntity.getLastName();
    this.nameSuffix = participantEntity.getNameSuffix();
    this.gender = participantEntity.getGender();
    this.ssn = participantEntity.getSsn();
    this.dateOfBirth = participantEntity.getDateOfBirth();
    this.dateOfDeath = participantEntity.getDateOfDeath();
    this.approximateAge = participantEntity.getApproximateAge();
    this.approximateAgeUnits = participantEntity.getApproximateAgeUnits();
    this.roles = new HashSet<>(Arrays.asList(participantEntity.getRoles()));
    this.languages = new LinkedList<>(Arrays.asList(participantEntity.getLanguages()));
    this.legacyId = participantEntity.getLegacyId();
    this.legacySourceTable = participantEntity.getLegacySourceTable();
    this.races = participantEntity.getRaces();
    this.ethnicity = participantEntity.getEthnicity();
    this.screeningId = participantEntity.getScreeningId();
    this.sealed = participantEntity.getSealed();
    this.sensitive = participantEntity.getSensitive();
    this.probationYouth = participantEntity.getProbationYouth();
  }

  /**
   * Work-around for fields containing raw JSON to embed into/extract from generated JSON races
   * ethnicity.
   *
   * @return JSON race codes
   */
  @JsonRawValue
  public String getRaces() {
    return races;
  }

  public void setRaces(final String races) {
    this.races = races;
  }

  @JsonProperty(value = "races")
  public void setRacesRaw(JsonNode jsonNode) {
    setRaces(jsonNode.toString());
  }

  @JsonRawValue
  public String getEthnicity() {
    return ethnicity;
  }

  public void setEthnicity(final String ethnicity) {
    this.ethnicity = ethnicity;
  }

  @JsonProperty(value = "ethnicity")
  public void setEthnicityRaw(JsonNode jsonNode) {
    setEthnicity(jsonNode.toString());
  }

  /**
   * @return id
   */
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
   * @return the legacyDescriptor
   */
  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * @param legacyDescriptor The legacy Descriptor
   */
  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
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
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = freshDate(dateOfBirth);
  }

  public void setDateOfDeath(Date dateOfDeath) {
    this.dateOfDeath = freshDate(dateOfDeath);
  }

  public void setApproximateAge(String approximateAge) {
    this.approximateAge = approximateAge;
  }

  public void setApproximateAgeUnits(String approximateAgeUnits) {
    this.approximateAgeUnits = approximateAgeUnits;
  }

  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }

  public void setScreeningId(String screeningId) {
    this.screeningId = screeningId;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }

  public Boolean getSealed() {
    return sealed;
  }

  public void setSealed(Boolean sealed) {
    this.sealed = sealed;
  }

  public Boolean getSensitive() {
    return sensitive;
  }

  public void setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
  }

  /**
   * @return the middleName
   */
  public String getMiddleName() {

    return middleName;
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
  public String getScreeningId() {
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
  public Date getDateOfBirth() {
    return freshDate(dateOfBirth);
  }


  /**
   * @return the dateOfDeath
   */
  public Date getDateOfDeath() {
    return freshDate(dateOfDeath);
  }

  /**
   * @return the ssn
   */
  public String getSsn() {
    return ssn;
  }

  /**
   * @return the languages
   */
  public List<String> getLanguages() {
    if (languages == null) {
      languages = new LinkedList<>();
    }
    return languages;
  }

  /**
   * @return addresses
   */
  public List<AddressIntakeApi> getAddresses() {
    if (addresses == null) {
      addresses = new ArrayList<>();
    }
    return addresses;
  }

  /**
   * @param addresses - domain addresses
   */
  public void setAddresses(List<AddressIntakeApi> addresses) {
    this.addresses = addresses;
  }

  /**
   * adds a set of addresses to a current set.
   *
   * @param addresses - domain addresses
   */
  public void addAddresses(List<AddressIntakeApi> addresses) {
    if (addresses == null) {
      return;
    }
    getAddresses().addAll(addresses);
  }

  /**
   * @return phone numbers
   */
  public Set<PhoneNumber> getPhoneNumbers() {
    if (phoneNumbers == null) {
      phoneNumbers = new HashSet<>();
    }
    return phoneNumbers;
  }

  /**
   * @param phoneNumbers - domain addresses
   */
  public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }

  /**
   * adds a set of phone numbers to current s set.
   *
   * @param phoneNumbers - domain addresses
   */
  public void addPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    if (phoneNumbers == null) {
      return;
    }
    getPhoneNumbers().addAll(phoneNumbers);
  }


  /**
   * @return the raceAndEthnicity
   */

  public String getApproximateAge() {
    return approximateAge;
  }

  public String getApproximateAgeUnits() {
    return approximateAgeUnits;
  }

  public Boolean isSealed() {
    return sealed;
  }

  public Boolean isSensitive() {
    return sensitive;
  }

  public Boolean isProbationYouth() {
    return probationYouth;
  }

  public void setProbationYouth(Boolean probationYouth) {
    this.probationYouth = probationYouth;
  }

  public List<Csec> getCsecs() {
    return csecs;
  }

  public void setCsecs(List<Csec> csecs) {
    this.csecs = csecs;
  }


  public SafelySurenderedBabiesIntakeApi getSafelySurenderedBabies() {
    return safelySurenderedBabies;
  }

  public void setSafelySurenderedBabies(SafelySurenderedBabiesIntakeApi safelySurenderedBabies) {
    this.safelySurenderedBabies = safelySurenderedBabies;
  }

  /**
   * {@inheritDoc}
   *
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
