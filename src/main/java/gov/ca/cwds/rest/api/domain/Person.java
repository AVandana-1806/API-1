package gov.ca.cwds.rest.api.domain;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.persistence.ns.PersonAddress;
import gov.ca.cwds.data.persistence.ns.PersonEthnicity;
import gov.ca.cwds.data.persistence.ns.PersonLanguage;
import gov.ca.cwds.data.persistence.ns.PersonPhone;
import gov.ca.cwds.data.persistence.ns.PersonRace;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a person.
 * 
 * @author CWDS API Team
 */
public class Person extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("first_name")
  @ApiModelProperty(example = "bart")
  @Size(max = 50)
  private String firstName;

  @JsonProperty("middle_name")
  @ApiModelProperty(example = "M.")
  @Size(max = 50)
  private String middleName;

  @JsonProperty("last_name")
  @ApiModelProperty(example = "simpson")
  @Size(max = 50)
  private String lastName;

  @JsonProperty("name_suffix")
  @ApiModelProperty(example = "jr.")
  @Size(max = 50)
  private String nameSuffix;

  @JsonProperty("gender")
  @ApiModelProperty(example = "M")
  @Size(max = 10)
  // @Pattern(message = "must be one of [M, F, O]", regexp = "[M|F|O]")
  private String gender;

  @Date
  // @PastDate()
  @JsonProperty("birth_date")
  @ApiModelProperty(example = "2012-04-01")
  private String birthDate;

  @JsonProperty("ssn")
  @ApiModelProperty(example = "999551111")
  @Size(min = 9, max = 9) // SSN is fixed width.
  private String ssn;

  @JsonProperty("address")
  private Set<Address> address;

  @JsonProperty("phone")
  private Set<PhoneNumber> phoneNumber;

  @JsonProperty("language")
  private Set<Language> language;

  @JsonProperty("race")
  private Set<Race> race;

  @JsonProperty("ethnicity")
  private Set<Ethnicity> ethnicity;

  /**
   * Constructor
   * 
   * @param firstName The first name
   * @param lastName The last name
   * @param middleName The middle name
   * @param nameSuffix The name suffix.
   * @param gender The gender
   * @param birthDate The date of birth
   * @param ssn The ssn
   * @param address The address
   * @param phoneNumber The phoneNumber
   * @param language The language
   * @param race The race
   * @param ethnicity The ethnicity
   */
  @JsonCreator
  public Person(@JsonProperty("first_name") String firstName,
      @JsonProperty("middle_name") String middleName, @JsonProperty("last_name") String lastName,
      @JsonProperty("name_suffix") String nameSuffix, @JsonProperty("gender") String gender,
      @JsonProperty("birth_date") String birthDate, @JsonProperty("ssn") String ssn,
      @JsonProperty("address") Set<Address> address,
      @JsonProperty("phone") Set<PhoneNumber> phoneNumber,
      @JsonProperty("language") Set<Language> language, @JsonProperty("race") Set<Race> race,
      @JsonProperty("ethnicity") Set<Ethnicity> ethnicity) {
    super();
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.nameSuffix = nameSuffix;
    this.gender = gender;
    this.birthDate = birthDate;
    this.ssn = ssn;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.language = language;
    this.ethnicity = ethnicity;
    this.race = race;
  }

  /**
   * Construct from persistence {@link gov.ca.cwds.data.persistence.ns.Person} object.
   * 
   * @param person persistence layer person
   */
  public Person(gov.ca.cwds.data.persistence.ns.Person person) {
    this.firstName = person.getFirstName();
    this.middleName = person.getMiddleName();
    this.lastName = person.getLastName();
    this.nameSuffix = "";
    this.gender = person.getGender();
    this.birthDate = DomainChef.cookDate(person.getDateOfBirth());
    this.ssn = person.getSsn();
    this.address = new HashSet<>();
    this.phoneNumber = new HashSet<>();
    this.language = new HashSet<>();
    this.race = new HashSet<>();
    this.ethnicity = new HashSet<>();

    Set<PersonAddress> addresses = person.getPersonAddress();
    if (addresses == null) {
      addresses = new HashSet<>();
    }
    for (PersonAddress personAddress : addresses) {
      this.address.add(new Address(personAddress.getAddress()));
    }

    Set<PersonPhone> phoneNumbers = person.getPersonPhone();
    if (phoneNumbers == null) {
      phoneNumbers = new HashSet<>();
    }
    for (PersonPhone personPhone : phoneNumbers) {
      this.phoneNumber.add(new PhoneNumber(personPhone.getPhoneNumber()));
    }

    Set<PersonLanguage> languages = person.getPersonLanguage();
    if (languages == null) {
      languages = new HashSet<>();
    }
    for (PersonLanguage personLanguage : languages) {
      this.language.add(new Language(personLanguage.getLanguage()));
    }

    Set<PersonRace> races = person.getPersonRace();
    if (races == null) {
      races = new HashSet<>();
    }
    for (PersonRace personRace : races) {
      this.race.add(new Race(personRace.getRace()));
    }

    Set<PersonEthnicity> enthnicities = person.getPersonEthnicity();
    if (enthnicities == null) {
      enthnicities = new HashSet<>();
    }
    for (PersonEthnicity personEthnicity : enthnicities) {
      this.ethnicity.add(new Ethnicity(personEthnicity.getEthnicity()));
    }
  }


  /**
   * @return the first_name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the middle_name
   */
  public String getMiddleName() {
    return middleName;
  }

  /**
   * @return the last_name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the name_suffix
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
   * @return the birth_date
   */
  public String getBirthDate() {
    return birthDate;
  }

  /**
   * @return the ssn
   */
  public String getSsn() {
    return ssn;
  }

  /**
   * @return the address
   */
  public Set<Address> getAddress() {
    return address;
  }

  /**
   * @return the phoneNumber
   */
  public Set<PhoneNumber> getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @return the language
   */
  public Set<Language> getLanguage() {
    return language;
  }

  /**
   * @return the race
   */
  public Set<Race> getRace() {
    return race;
  }

  /**
   * @return the ethnicity
   */
  public Set<Ethnicity> getEthnicity() {
    return ethnicity;
  }

  public void doSomething() {
    System.out.println("do something!");
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
