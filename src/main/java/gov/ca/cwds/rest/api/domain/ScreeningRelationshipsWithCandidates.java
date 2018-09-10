package gov.ca.cwds.rest.api.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CWDS TPT-3 Team
 */
public class ScreeningRelationshipsWithCandidates extends ReportingDomain
    implements Request, Response {

  public static final class ScreeningRelationshipsWithCandidatesBuilder {

    private ScreeningRelationshipsWithCandidates screeningRelationshipsWithCandidates;

    public ScreeningRelationshipsWithCandidatesBuilder() {
      screeningRelationshipsWithCandidates = new ScreeningRelationshipsWithCandidates();
    }

    public ScreeningRelationshipsWithCandidatesBuilder withId(String id) {
      screeningRelationshipsWithCandidates.id = id;
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder withDateOfBirth(Date dateOfBirth) {
      if (dateOfBirth == null) {
        return this;
      }
      screeningRelationshipsWithCandidates.dateOfBirth =
          new SimpleDateFormat(DATE_PATTERN).format(dateOfBirth);
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder withAge(String age) {
      if (age == null) {
        return this;
      }
      screeningRelationshipsWithCandidates.age = Short.valueOf(age);
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder withAgeUnit(String ageUnit) {
      screeningRelationshipsWithCandidates.ageUnit = ageUnit;
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder withFirstName(String firstName) {
      screeningRelationshipsWithCandidates.firstName = firstName;
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder withMiddleName(String middleName) {
      screeningRelationshipsWithCandidates.middleName = middleName;
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder withSuffixName(String suffixName) {
      screeningRelationshipsWithCandidates.suffixName = suffixName;
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder withGender(String gender) {
      screeningRelationshipsWithCandidates.gender = gender;
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder withLastName(String lastName) {
      screeningRelationshipsWithCandidates.lastName = lastName;
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder withDateOfDeath(Date dateOfDeath) {
      if (dateOfDeath == null) {
        return this;
      }
      screeningRelationshipsWithCandidates.dateOfDeath =
          new SimpleDateFormat(DATE_PATTERN).format(dateOfDeath);
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder withSensitive(Boolean sensitive) {
      screeningRelationshipsWithCandidates.sensitive = sensitive;
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder withSealed(Boolean sealed) {
      screeningRelationshipsWithCandidates.sealed = sealed;
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder withRelatedTo(Set<RelatedTo> relatedTo) {
      screeningRelationshipsWithCandidates.relatedTo = relatedTo;
      return this;
    }

    public ScreeningRelationshipsWithCandidatesBuilder witCandidatesTo(
        Set<CandidateTo> relatedCandidatesTo) {
      screeningRelationshipsWithCandidates.relatedCandidatesTo = relatedCandidatesTo;
      return this;
    }

    public ScreeningRelationshipsWithCandidates build() {
      if (screeningRelationshipsWithCandidates.relatedTo == null) {
        screeningRelationshipsWithCandidates.relatedTo = new HashSet<>();
      }
      if (screeningRelationshipsWithCandidates.relatedCandidatesTo == null) {
        screeningRelationshipsWithCandidates.relatedCandidatesTo = new HashSet<>();
      }

      calculateAgeAndAgeUnit();
      return screeningRelationshipsWithCandidates;
    }

    private void calculateAgeAndAgeUnit() {
      if (StringUtils.isEmpty(screeningRelationshipsWithCandidates.dateOfBirth)) {
        return;
      }

      Map<String, Object> ageWithUnit = calculateAge(
          screeningRelationshipsWithCandidates.dateOfBirth);
      if (ageWithUnit.isEmpty()) {
        return;
      }

      screeningRelationshipsWithCandidates.ageUnit = (String) ageWithUnit.get("UNIT");
      screeningRelationshipsWithCandidates.age = (Short) ageWithUnit.get("AGE");
    }
  }

  public static final class RelatedTo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final class RelatedToBuilder {

      private RelatedTo relatedTo;

      public RelatedToBuilder() {
        relatedTo = new RelatedTo();
      }

      public RelatedToBuilder withRelationshipId(String relationshipId) {
        relatedTo.relationshipId = relationshipId;
        return this;
      }

      public RelatedToBuilder withRelatedPersonId(String personId) {
        relatedTo.relatedPersonId = personId;
        return this;
      }


      public RelatedToBuilder withRelatedFirstName(String relatedFirstName) {
        relatedTo.relatedFirstName = relatedFirstName;
        return this;
      }

      public RelatedToBuilder withRelatedMiddleName(String relatedMiddleName) {
        relatedTo.relatedMiddleName = relatedMiddleName;
        return this;
      }

      public RelatedToBuilder withRelatedLastName(String relatedLastName) {
        relatedTo.relatedLastName = relatedLastName;
        return this;
      }

      public RelatedToBuilder withRelatedNameSuffix(String relatedNameSuffix) {
        relatedTo.relatedNameSuffix = relatedNameSuffix;
        return this;
      }

      public RelatedToBuilder withRelatedGender(String relatedGender) {
        relatedTo.relatedGender = relatedGender;
        return this;
      }

      public RelatedToBuilder withRelatedDateOfBirth(Date relatedDateOfBirth) {
        if (relatedDateOfBirth == null) {
          return this;
        }
        relatedTo.relatedDateOfBirth =
            new SimpleDateFormat(DATE_PATTERN).format(relatedDateOfBirth);
        calculateAgeAndAgeUnit();
        return this;
      }

      public RelatedToBuilder withRelatedAge(String relatedAge) {
        if (StringUtils.isEmpty(relatedAge)) {
          return this;
        }
        relatedTo.relatedAge = Short.valueOf(relatedAge);
        return this;
      }

      public RelatedToBuilder withRelatedAgeUnit(String relatedAgeUnit) {
        relatedTo.relatedAgeUnit = relatedAgeUnit;
        return this;
      }

      public RelatedToBuilder withAbsentParentCode(String absentParentCode) {
        relatedTo.absentParentCode = absentParentCode;
        return this;
      }

      public RelatedToBuilder withSameHomeCode(String sameHomeCode) {
        relatedTo.sameHomeCode = sameHomeCode;
        return this;
      }

      public RelatedToBuilder withRelationshipToPerson(String relationshipToPerson) {
        relatedTo.relationshipToPerson = relationshipToPerson;
        return this;
      }

      public RelatedToBuilder withRelatedPersonRelationship(String relatedPersonRelationship) {
        relatedTo.relatedPersonRelationship = relatedPersonRelationship;
        return this;
      }

      public RelatedToBuilder withReversedRelationship(boolean reversed) {
        relatedTo.reversed = reversed;
        return this;
      }

      public RelatedToBuilder withRelationshipStartDate(Date relationshipStartDate) {
        if (relationshipStartDate == null) {
          return this;
        }
        relatedTo.relationshipStartDate =
            new SimpleDateFormat(DATE_PATTERN).format(relationshipStartDate);
        return this;
      }

      public RelatedToBuilder withRelationshipEndDate(Date relationshipEndDate) {
        if (relationshipEndDate == null) {
          return this;
        }
        relatedTo.relationshipEndDate =
            new SimpleDateFormat(DATE_PATTERN).format(relationshipEndDate);
        return this;
      }

      private void calculateAgeAndAgeUnit() {
        if (StringUtils.isEmpty(relatedTo.relatedDateOfBirth)) {
          return;
        }

        Map<String, Object> ageWithUnit = calculateAge(relatedTo.relatedDateOfBirth);
        if (ageWithUnit.isEmpty()) {
          return;
        }

        relatedTo.relatedAgeUnit = (String) ageWithUnit.get("UNIT");
        relatedTo.relatedAge = (Short) ageWithUnit.get("AGE");
      }

      public RelatedToBuilder withLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
        relatedTo.legacyDescriptor = legacyDescriptor;
        return this;
      }

      public RelatedTo build() {
        return relatedTo;
      }
    }

    @JsonProperty("relationship_id")
    @ApiModelProperty(value = "relationship id", example = "1234")
    private String relationshipId;

    @JsonProperty("related_person_id")
    @ApiModelProperty(value = "participant id", example = "23412")
    private String relatedPersonId;

    @JsonProperty("related_person_first_name")
    @ApiModelProperty(value = "first name", example = "jane")
    private String relatedFirstName;

    @JsonProperty("related_person_middle_name")
    @ApiModelProperty(value = "middle name", example = "ann")
    private String relatedMiddleName;

    @JsonProperty("related_person_last_name")
    @ApiModelProperty(value = "last name", example = "sufer")
    private String relatedLastName;

    @JsonProperty("related_person_name_suffix")
    @ApiModelProperty(value = "name suffix", example = "Jr")
    private String relatedNameSuffix;

    @JsonProperty("related_person_gender")
    @ApiModelProperty(value = "Gender Code", example = "M")
    private String relatedGender;

    @JsonProperty("related_person_date_of_birth")
    @ApiModelProperty(value = "date of birth", example = "1999-10-01")
    private String relatedDateOfBirth;

    @JsonProperty("related_person_age")
    @ApiModelProperty(example = "31")
    private Short relatedAge;

    @JsonProperty("related_person_age_unit")
    @ApiModelProperty(value = "Age Unit", example = "M")
    private String relatedAgeUnit;

    @JsonProperty("absent_parent_code")
    @ApiModelProperty(value = "absent parent code")
    private String absentParentCode;

    @JsonProperty("same_home_code")
    @ApiModelProperty(value = "same home code", example = "Y")
    private String sameHomeCode;

    @JsonProperty("indexed_person_relationship")
    @ApiModelProperty(value = "relationship to the person", example = "Sister")
    private String relationshipToPerson;

    @JsonProperty("related_person_relationship")
    @ApiModelProperty(value = "relationship to the person", example = "Sister")
    private String relatedPersonRelationship;

    @JsonProperty("relationship_start_date")
    @ApiModelProperty(value = "relationship start date", example = "2000-10-01")
    private String relationshipStartDate;

    @JsonProperty("relationship_end_date")
    @ApiModelProperty(value = "relationship end date", example = "2001-10-01")
    private String relationshipEndDate;

    @JsonProperty("reversed")
    @ApiModelProperty(value = "If is reversed - related participant ID is Primary client ",
        example = "true")
    private boolean reversed;

    @JsonProperty("legacy_descriptor")
    @ApiModelProperty
    private LegacyDescriptor legacyDescriptor;

    private RelatedTo() {
    }

    public String getRelationshipId() {
      return relationshipId;
    }

    public String getRelatedPersonId() {
      return relatedPersonId;
    }

    public String getRelatedFirstName() {
      return relatedFirstName;
    }

    public String getRelatedMiddleName() {
      return relatedMiddleName;
    }

    public String getRelatedLastName() {
      return relatedLastName;
    }

    public String getRelatedNameSuffix() {
      return relatedNameSuffix;
    }

    public String getRelatedGender() {
      return relatedGender;
    }

    public String getRelatedDateOfBirth() {
      return relatedDateOfBirth;
    }

    public Short getRelatedAge() {
      return relatedAge;
    }

    public String getRelatedAgeUnit() {
      return relatedAgeUnit;
    }

    public String getAbsentParentCode() {
      return absentParentCode;
    }

    public String getSameHomeCode() {
      return sameHomeCode;
    }

    public String getRelationshipToPerson() {
      return relationshipToPerson;
    }

    public String getRelatedPersonRelationship() {
      return relatedPersonRelationship;
    }

    public String getRelationshipStartDate() {
      return relationshipStartDate;
    }

    public String getRelationshipEndDate() {
      return relationshipEndDate;
    }

    public boolean isReversed() {
      return reversed;
    }

    public void setRelatedAge(Short relatedAge) {
      this.relatedAge = relatedAge;
    }

    public void setRelatedAgeUnit(String relatedAgeUnit) {
      this.relatedAgeUnit = relatedAgeUnit;
    }

    public LegacyDescriptor getLegacyDescriptor() {
      return legacyDescriptor;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (!(o instanceof RelatedTo)) {
        return false;
      }

      RelatedTo relatedTo = (RelatedTo) o;

      return new EqualsBuilder().append(relationshipId, relatedTo.relationshipId)
          .append(relatedPersonId, relatedTo.relatedPersonId)
          .append(relatedFirstName, relatedTo.relatedFirstName)
          .append(relatedMiddleName, relatedTo.relatedMiddleName)
          .append(relatedLastName, relatedTo.relatedLastName)
          .append(relatedNameSuffix, relatedTo.relatedNameSuffix)
          .append(relatedGender, relatedTo.relatedGender)
          .append(relatedDateOfBirth, relatedTo.relatedDateOfBirth)
          .append(relatedAge, relatedTo.relatedAge).append(relatedAgeUnit, relatedTo.relatedAgeUnit)
          .append(absentParentCode, relatedTo.absentParentCode)
          .append(sameHomeCode, relatedTo.sameHomeCode)
          .append(relationshipToPerson, relatedTo.relationshipToPerson)
          .append(relatedPersonRelationship, relatedTo.relatedPersonRelationship)
          .append(relationshipStartDate, relatedTo.relationshipStartDate)
          .append(relationshipEndDate, relatedTo.relationshipEndDate)
          .append(reversed, relatedTo.reversed)
          .isEquals();
    }

    @Override
    public int hashCode() {
      return new HashCodeBuilder(17, 37).append(relationshipId).append(relatedPersonId)
          .append(relatedFirstName).append(relatedMiddleName).append(relatedLastName)
          .append(relatedNameSuffix).append(relatedGender).append(relatedDateOfBirth)
          .append(relatedAge).append(relatedAgeUnit).append(absentParentCode).append(sameHomeCode)
          .append(relationshipToPerson).append(relatedPersonRelationship)
          .append(relationshipStartDate).append(relationshipEndDate).append(reversed).toHashCode();
    }
  }

  public static final class CandidateTo implements Serializable {

    public static final class CandidateToBuilder {

      private CandidateTo candidateTo;

      public CandidateToBuilder() {
        candidateTo = new CandidateTo();
      }

      public CandidateToBuilder withId(String id) {
        candidateTo.candidateId = id;
        return this;
      }

      public CandidateToBuilder withCandidateFirstName(String candidateFirstName) {
        candidateTo.candidateFirstName = candidateFirstName;
        return this;
      }

      public CandidateToBuilder withCandidateMiddleName(String candidateMiddleName) {
        candidateTo.candidateMiddleName = candidateMiddleName;
        return this;
      }

      public CandidateToBuilder withCandidateLastName(String candidateLastName) {
        candidateTo.candidateLastName = candidateLastName;
        return this;
      }

      public CandidateToBuilder withCandidateSuffixtName(String candidateSuffixtName) {
        candidateTo.candidateSuffixtName = candidateSuffixtName;
        return this;
      }

      public CandidateToBuilder withCandidateGender(String candidateGender) {
        candidateTo.candidateGender = candidateGender;
        return this;
      }

      public CandidateToBuilder withCandidateDateOfBirth(Date candidateDateOfBirth) {
        if (candidateDateOfBirth == null) {
          return this;
        }
        candidateTo.candidateDateOfBirth =
            new SimpleDateFormat(DATE_PATTERN).format(candidateDateOfBirth);
        calculateAgeAndAgeUnit();
        return this;
      }

      public CandidateToBuilder withCandidateAge(String candidateAge) {
        if (StringUtils.isEmpty(candidateAge)) {
          return this;
        }
        candidateTo.candidateAge = Short.parseShort(candidateAge);
        return this;
      }

      public CandidateToBuilder withCandidateAgeUnit(String candidateAgeUnit) {
        candidateTo.candidateAgeUnit = candidateAgeUnit;
        return this;
      }

      private void calculateAgeAndAgeUnit() {
        if (StringUtils.isEmpty(candidateTo.candidateDateOfBirth)) {
          return;
        }

        Map<String, Object> ageWithUnit = calculateAge(candidateTo.candidateDateOfBirth);
        if (ageWithUnit.isEmpty()) {
          return;
        }

        candidateTo.candidateAgeUnit = (String) ageWithUnit.get("UNIT");
        candidateTo.candidateAge = (Short) ageWithUnit.get("AGE");
      }

      public CandidateToBuilder withLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
        candidateTo.legacyDescriptor = legacyDescriptor;
        return this;
      }

      public CandidateTo build() {
        return candidateTo;
      }
    }

    @JsonProperty("candidate_id")
    @ApiModelProperty(value = "Id")
    private String candidateId;

    @JsonProperty("candidate_first_name")
    @ApiModelProperty(value = "Candidate first name")
    private String candidateFirstName;

    @JsonProperty("candidate_middle_name")
    @ApiModelProperty(value = "Candidate middle name")
    private String candidateMiddleName;

    @JsonProperty("candidate_last_name")
    @ApiModelProperty(value = "Candidate last name")
    private String candidateLastName;

    @JsonProperty("candidate_name_suffix")
    @ApiModelProperty(value = "Candidate suffix name")
    private String candidateSuffixtName;

    @JsonProperty("candidate_gender")
    @ApiModelProperty(value = "Candidate gender")
    private String candidateGender;

    @JsonProperty("candidate_date_of_birth")
    @ApiModelProperty(value = "Candidate date of birth")
    private String candidateDateOfBirth;

    @JsonProperty("candidate_age")
    @ApiModelProperty(value = "Candidate age")
    private short candidateAge;

    @JsonProperty("candidate_age_unit")
    @ApiModelProperty(value = "Candidate age unit")
    private String candidateAgeUnit;

    @JsonProperty("legacy_descriptor")
    @ApiModelProperty(required = true)
    private LegacyDescriptor legacyDescriptor;

    private CandidateTo() {
    }

    public String getCandidateId() {
      return candidateId;
    }

    public String getCandidateFirstName() {
      return candidateFirstName;
    }

    public String getCandidateMiddleName() {
      return candidateMiddleName;
    }

    public String getCandidateLastName() {
      return candidateLastName;
    }

    public String getCandidateSuffixtName() {
      return candidateSuffixtName;
    }

    public String getCandidateGender() {
      return candidateGender;
    }

    public String getCandidateDateOfBirth() {
      return candidateDateOfBirth;
    }

    public LegacyDescriptor getLegacyDescriptor() {
      return legacyDescriptor;
    }

    public short getCandidateAge() {
      return candidateAge;
    }

    public String getCandidateAgeUnit() {
      return candidateAgeUnit;
    }

    public void setCandidateAge(short candidateAge) {
      this.candidateAge = candidateAge;
    }

    public void setCandidateAgeUnit(String candidateAgeUnit) {
      this.candidateAgeUnit = candidateAgeUnit;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (!(o instanceof CandidateTo)) {
        return false;
      }

      CandidateTo that = (CandidateTo) o;

      return new EqualsBuilder().append(candidateAge, that.candidateAge)
          .append(candidateId, that.candidateId).append(candidateFirstName, that.candidateFirstName)
          .append(candidateMiddleName, that.candidateMiddleName)
          .append(candidateLastName, that.candidateLastName)
          .append(candidateSuffixtName, that.candidateSuffixtName)
          .append(candidateGender, that.candidateGender)
          .append(candidateDateOfBirth, that.candidateDateOfBirth)
          .append(candidateAgeUnit, that.candidateAgeUnit).isEquals();
    }

    @Override
    public int hashCode() {
      return new HashCodeBuilder(17, 37).append(candidateId).append(candidateFirstName)
          .append(candidateMiddleName).append(candidateLastName).append(candidateSuffixtName)
          .append(candidateGender).append(candidateDateOfBirth).append(candidateAge)
          .append(candidateAgeUnit).toHashCode();
    }
  }

  private static final String DATE_PATTERN = "yyyy-MM-dd";
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = true, value = "identifier", example = "ABC1234567")
  private String id;

  @JsonProperty("date_of_birth")
  @ApiModelProperty(required = true, value = "date of birth", example = "1999-10-01")
  private String dateOfBirth;

  @JsonProperty("age")
  @ApiModelProperty(example = "12")
  private Short age;

  @JsonProperty("age_unit")
  @ApiModelProperty(required = true, value = "Age Unit", example = "M")
  @OneOf(value = {"Y", "M", "D"}, ignoreWhitespace = true)
  private String ageUnit;

  @JsonProperty("first_name")
  @ApiModelProperty(value = "first name", example = "joe")
  private String firstName;

  @JsonProperty("middle_name")
  @ApiModelProperty(required = true, value = "middle name", example = "w")
  private String middleName;

  @JsonProperty("last_name")
  @ApiModelProperty(required = true, value = "last name", example = "sufer")
  private String lastName;

  @JsonProperty("name_suffix")
  @ApiModelProperty(value = "Suffix Title Description")
  private String suffixName;

  @JsonProperty("gender")
  @ApiModelProperty(required = true, value = "Gender Code", example = "M")
  @OneOf(value = {"M", "F", "I", "U"}, ignoreWhitespace = true)
  private String gender;

  @JsonProperty("date_of_death")
  @ApiModelProperty(value = "date of death", example = "2010-10-01")
  private String dateOfDeath;

  @JsonProperty("sensitive")
  @ApiModelProperty(value = "sensitive", example = "false")
  private Boolean sensitive;

  @JsonProperty("sealed")
  @ApiModelProperty(value = "sealed", example = "false")
  private Boolean sealed;

  @JsonProperty("relationship_to")
  private Set<RelatedTo> relatedTo;
  @JsonProperty("candidate_to")
  private Set<CandidateTo> relatedCandidatesTo;

  private ScreeningRelationshipsWithCandidates() {
  }

  public Set<CandidateTo> getRelatedCandidatesTo() {
    return relatedCandidatesTo;
  }

  public String getId() {
    return id;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public Short getAge() {
    return age;
  }

  public String getAgeUnit() {
    return ageUnit;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getSuffixName() {
    return suffixName;
  }

  public String getGender() {
    return gender;
  }

  public String getDateOfDeath() {
    return dateOfDeath;
  }

  public Boolean getSensitive() {
    return sensitive;
  }

  public Boolean getSealed() {
    return sealed;
  }

  public Set<RelatedTo> getRelatedTo() {
    return relatedTo;
  }

  public void setAge(Short age) {
    this.age = age;
  }

  public void setAgeUnit(String ageUnit) {
    this.ageUnit = ageUnit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof ScreeningRelationshipsWithCandidates)) {
      return false;
    }

    ScreeningRelationshipsWithCandidates that = (ScreeningRelationshipsWithCandidates) o;

    return new EqualsBuilder()
        .append(id, that.id)
        .append(dateOfBirth, that.dateOfBirth)
        .append(age, that.age)
        .append(ageUnit, that.ageUnit)
        .append(firstName, that.firstName)
        .append(middleName, that.middleName)
        .append(lastName, that.lastName)
        .append(suffixName, that.suffixName)
        .append(gender, that.gender)
        .append(dateOfDeath, that.dateOfDeath)
        .append(sensitive, that.sensitive)
        .append(sealed, that.sealed)
        .append(relatedTo, that.relatedTo)
        .append(relatedCandidatesTo, that.relatedCandidatesTo)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id)
        .append(dateOfBirth)
        .append(age)
        .append(ageUnit)
        .append(firstName)
        .append(middleName)
        .append(lastName)
        .append(suffixName)
        .append(gender)
        .append(dateOfDeath)
        .append(sensitive)
        .append(sealed)
        .append(relatedTo)
        .append(relatedCandidatesTo)
        .toHashCode();
  }

  /**
   * @param dateOfBirth format dd-mm-yyyy
   */
  private static Map<String, Object> calculateAge(String dateOfBirth) {
    Map<String, Object> map = new HashMap<>();

    if (StringUtils.isEmpty(dateOfBirth)) {
      return map;
    }

    Period period = Period
        .between(LocalDate.parse(dateOfBirth),
            LocalDate.now());
    if (period.getYears() > 0) {
      map.put("AGE", (short) period.getYears());
      map.put("UNIT", AgeUnit.Y.name());
    } else if (period.getMonths() > 0) {
      map.put("AGE", (short) period.getMonths());
      map.put("UNIT", AgeUnit.M.name());
    } else {
      map.put("AGE", (short) period.getDays());
      map.put("UNIT", AgeUnit.D.name());
    }

    return map;
  }
}
