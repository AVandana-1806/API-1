package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.PhoneNumber;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * @author CWDS API Team
 *
 */
public class ParticipantIntakeApiResourceBuilder {

  String id = "5432";
  String firstName = "John";
  String middleName = "T";
  String lastName = "Smith";
  String nameSuffix = "";
  String gender = "male";
  String ssn = "123456789";
  Date dateOfBirth = DomainChef.uncookDateString("2001-03-15");
  Date dateOfDeath = DomainChef.uncookDateString("2001-02-15");
  String approximateAge = "12";
  String approximateAgeUnits = "years";
  List<String> languages = new LinkedList<>(Arrays.asList("English", "Russian"));
  String clientId = "1234567ABC";
  String legacySourceTable = LegacyTable.CLIENT.getName();
  String races = "[\n" + "{\"race\":\"White\",\"race_detail\":\"Central American\"}]";
  String ethnicity = "{\n" + "  \"hispanic_latino_origin\": \"Yes\",\n"
      + "  \"ethnicity_detail\": [\n" + "    \"Mexican\"\n" + "  ]\n" + "}";
  String screeningId = "12345";
  Set<String> roles = new HashSet<>(Arrays.asList("Victim"));
  List<AddressIntakeApi> addresses = new ArrayList<>();
  List<PhoneNumber> phoneNumbers = new ArrayList<>();
  Boolean sealed = false;
  Boolean sensitive = false;
  LegacyDescriptor legacyDescriptor = new LegacyDescriptor();;
  List<Csec> csec = new ArrayList<>(Arrays.asList(new CsecBuilder().build()));

  /**
   * @return the ParticipantIntakeApi
   */
  public ParticipantIntakeApi build() {
    return new ParticipantIntakeApi(id, legacySourceTable, clientId, legacyDescriptor, firstName,
        middleName, lastName, nameSuffix, gender, approximateAge, approximateAgeUnits, ssn,
        dateOfBirth, dateOfDeath, languages, races, ethnicity, screeningId, roles, addresses,
        phoneNumbers, sealed, sensitive);
  }

  /**
   * @param id - id
   * @return the id
   */
  public ParticipantIntakeApiResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  /**
   * @param firstName - firstName
   * @return the firstName
   */
  public ParticipantIntakeApiResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * @param middleName - middleName
   * @return the middleName
   */
  public ParticipantIntakeApiResourceBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  /**
   * @param lastName - lastName
   * @return the lastName
   */
  public ParticipantIntakeApiResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * @param nameSuffix - nameSuffix
   * @return the nameSuffix
   */
  public ParticipantIntakeApiResourceBuilder setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
    return this;
  }

  /**
   * @param gender - gender
   * @return the gender
   */
  public ParticipantIntakeApiResourceBuilder setGender(String gender) {
    this.gender = gender;
    return this;
  }

  /**
   * @param ssn - ssn
   * @return the ssn
   */
  public ParticipantIntakeApiResourceBuilder setSsn(String ssn) {
    this.ssn = ssn;
    return this;
  }

  /**
   * @param dateOfBirth - dateOfBirth
   * @return the dateOfBirth
   */
  public ParticipantIntakeApiResourceBuilder setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }



  /**
   * @param dateOfDeath the dateOfDeath to set * @return the dateOfDeath
   */
  public ParticipantIntakeApiResourceBuilder setDateOfDeath(Date dateOfDeath) {
    this.dateOfDeath = dateOfDeath;
    return this;
  }

  /**
   * @param approximateAge - approximateAge
   * @return the approximateAge
   */
  public ParticipantIntakeApiResourceBuilder setApproximateAge(String approximateAge) {
    this.approximateAge = approximateAge;
    return this;
  }

  /**
   * @param approximateAgeUnits - approximateAgeUnits
   * @return the approximateAgeUnits
   */
  public ParticipantIntakeApiResourceBuilder setApproximateAgeUnits(String approximateAgeUnits) {
    this.approximateAgeUnits = approximateAgeUnits;
    return this;
  }

  /**
   * @param languages - languages
   * @return the languages
   */
  public ParticipantIntakeApiResourceBuilder setLanguages(List<String> languages) {
    this.languages = languages;
    return this;
  }

  /**
   * @param clientId - clientId
   * @return the clientId
   */
  public ParticipantIntakeApiResourceBuilder setClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * @param legacySourceTable - legacySourceTable
   * @return the legacySourceTable
   */
  public ParticipantIntakeApiResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  /**
   * @param races - races
   * @return the races
   */
  public ParticipantIntakeApiResourceBuilder setRaces(String races) {
    this.races = races;
    return this;
  }

  /**
   * @param ethnicity - ethnicity
   * @return the ethnicity
   */
  public ParticipantIntakeApiResourceBuilder setEthnicity(String ethnicity) {
    this.ethnicity = ethnicity;
    return this;
  }

  /**
   * @param screeningId - screeningId
   * @return the screeningId
   */
  public ParticipantIntakeApiResourceBuilder setScreeningId(String screeningId) {
    this.screeningId = screeningId;
    return this;
  }

  /**
   * @param roles - roles
   * @return the roles
   */
  public ParticipantIntakeApiResourceBuilder setRoles(Set<String> roles) {
    this.roles = roles;
    return this;
  }

  /**
   * @param addresses - addresses
   * @return the addresses
   */
  public ParticipantIntakeApiResourceBuilder setAddresses(List<AddressIntakeApi> addresses) {
    this.addresses = addresses;
    return this;
  }

  /**
   * @param phoneNumbers - phoneNumbers
   * @return the phoneNumbers
   */
  public ParticipantIntakeApiResourceBuilder setPhoneNumbers(
      List<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
    return this;
  }

  /**
   * @param sealed - sealed
   * @return the sealed
   */
  public ParticipantIntakeApiResourceBuilder setSealed(Boolean sealed) {
    this.sealed = sealed;
    return this;
  }

  /**
   * @param sensitive - sensitive
   * @return the sensitive
   */
  public ParticipantIntakeApiResourceBuilder setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
    return this;
  }

  /**
   * @param legacyDescriptor - legacyDescriptor
   * @return the legacyDescriptor
   */
  public ParticipantIntakeApiResourceBuilder setLegacyDescriptor(
      LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setCsec(List<Csec> csec) {
    this.csec = csec;
    return this;
  }

}
