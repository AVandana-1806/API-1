package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.DomainChef;

@SuppressWarnings("javadoc")
public class ParticipantEntityBuilder {
  public static final String DEFAULT_PERSON_ID = "jhdgfkhaj";
  public static final String DEFAULT_REPORTER_ID = "reporterabc";

  private String id = DEFAULT_PERSON_ID;
  private Date dateOfBirth;
  private Date dateOfDeath;
  private String firstName = "John";
  private String gender;
  private String lastName = "Smith";
  private String ssn;
  private ScreeningEntity screeningEntity;
  private String legacyId;
  private String[] roles;
  private String[] languages;
  private String middleName;
  private String nameSuffix = "Jr.";
  private String races;
  private String ethnicity;
  private String legacySourceTable;
  private Boolean sensitive = Boolean.FALSE;
  private Boolean sealed = Boolean.FALSE;
  private Boolean probationYouth;
  private String approximateAge;
  private String approximateAgeUnits;

  {
    this.setDateOfBirth("1995-05-12");
  }

  public ParticipantEntity build() {
    return new ParticipantEntity(id, dateOfBirth, dateOfDeath, firstName, gender, lastName, ssn,
        screeningEntity, legacyId, roles, languages, middleName, nameSuffix, races, ethnicity,
        legacySourceTable, sensitive, sealed, probationYouth, approximateAge, approximateAgeUnits);
  }

  public ParticipantEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public ParticipantEntityBuilder setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = DomainChef.uncookDateString(dateOfBirth);
    return this;
  }


  public ParticipantEntityBuilder setDateOfDeath(Date dateOfDeath) {
    this.dateOfDeath = dateOfDeath;
    return this;
  }

  public ParticipantEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public ParticipantEntityBuilder setGender(String gender) {
    this.gender = gender;
    return this;
  }

  public ParticipantEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ParticipantEntityBuilder setSsn(String ssn) {
    this.ssn = ssn;
    return this;
  }

  public ParticipantEntityBuilder setScreeningEntity(ScreeningEntity screeningEntity) {
    this.screeningEntity = screeningEntity;
    return this;
  }

  public ParticipantEntityBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  public ParticipantEntityBuilder setRoles(String[] roles) {
    this.roles = roles;
    return this;
  }

  public ParticipantEntityBuilder setLanguages(String[] languages) {
    this.languages = languages;
    return this;
  }

  public ParticipantEntityBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  public ParticipantEntityBuilder setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
    return this;
  }

  public ParticipantEntityBuilder setRaces(String races) {
    this.races = races;
    return this;
  }

  public ParticipantEntityBuilder setEthnicity(String ethnicity) {
    this.ethnicity = ethnicity;
    return this;
  }

  public ParticipantEntityBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  public ParticipantEntityBuilder setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
    return this;
  }

  public ParticipantEntityBuilder setSealed(Boolean sealed) {
    this.sealed = sealed;
    return this;
  }

  public ParticipantEntityBuilder setApproximateAge(String approximateAge) {
    this.approximateAge = approximateAge;
    return this;
  }

  public ParticipantEntityBuilder setApproximateAgeUnits(String approximateAgeUnits) {
    this.approximateAgeUnits = approximateAgeUnits;
    return this;
  }
}
