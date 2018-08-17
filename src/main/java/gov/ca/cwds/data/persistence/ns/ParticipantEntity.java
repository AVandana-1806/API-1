package gov.ca.cwds.data.persistence.ns;

import static gov.ca.cwds.data.persistence.ns.ParticipantEntity.FIND_BY_SCREENING_ID_AND_LEGACY_ID;
import static gov.ca.cwds.data.persistence.ns.ParticipantEntity.FIND_LEGACY_ID_LIST_BY_SCREENING_ID;
import static gov.ca.cwds.data.persistence.ns.ParticipantEntity.FIND_PARTICIPANTS_BY_PARTICIPANT_IDS;
import static gov.ca.cwds.data.persistence.ns.ParticipantEntity.FIND_PARTICIPANTS_BY_SCREENING_IDS;
import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.Identifiable;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.ns.papertrail.HasPaperTrail;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;

/**
 * {@link PersistentObject} representing a ParticipantIntakeApi.
 *
 * @author CWDS API Team
 */
@NamedQuery(name = FIND_LEGACY_ID_LIST_BY_SCREENING_ID,
    query = "SELECT legacyId FROM ParticipantEntity WHERE screeningEntity.id = :screeningId AND legacyId IS NOT NULL")
@NamedQuery(name = FIND_PARTICIPANTS_BY_SCREENING_IDS,
    query = "FROM ParticipantEntity WHERE screeningId IN :screeningIds")
@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningId",
    query = "FROM ParticipantEntity WHERE screeningId = :screeningId")
@NamedQuery(
    name = "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndParticipantId",
    query = "FROM ParticipantEntity WHERE screeningId = :screeningId AND id = :participantId")
@NamedQuery(
    name = FIND_BY_SCREENING_ID_AND_LEGACY_ID,
    query = "FROM ParticipantEntity WHERE screeningId = :screeningId AND legacyId = :legacyId")
@NamedQuery(name = FIND_PARTICIPANTS_BY_PARTICIPANT_IDS,
    query = "FROM ParticipantEntity WHERE id IN :participantIds")
@Entity
@Table(name = "participants")
@SuppressWarnings({"squid:S00107"})
public class ParticipantEntity
    implements PersistentObject, HasPaperTrail, Identifiable<String>, Serializable {

  private static final long serialVersionUID = 1L;

  public static final String FIND_LEGACY_ID_LIST_BY_SCREENING_ID =
      "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findLegacyIdListByScreeningId";
  public static final String FIND_PARTICIPANTS_BY_SCREENING_IDS =
      "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIds";
  public static final String FIND_BY_SCREENING_ID_AND_LEGACY_ID =
      "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId";
  public static final String FIND_PARTICIPANTS_BY_PARTICIPANT_IDS =
      "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId";

  @Id
  @Column(name = "id")
  @GenericGenerator(name = "participant_id",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name",
          value = "participants_id_seq")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_id")
  private String id;

  @Column(name = "date_of_birth")
  @Type(type = "date")
  private Date dateOfBirth;

  @Column(name = "date_of_death")
  @Type(type = "date")
  private Date dateOfDeath;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "gender")
  private String gender;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "ssn")
  private String ssn;

  @Column(name = "screening_id")
  private String screeningId;

  @HashCodeExclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "screening_id", insertable = false, updatable = false)
  private ScreeningEntity screeningEntity;

  @Column(name = "legacy_id")
  private String legacyId;

  @Column(name = "roles")
  @Type(type = "gov.ca.cwds.data.persistence.hibernate.StringArrayType")
  private String[] roles;

  @Column(name = "languages")
  @Type(type = "gov.ca.cwds.data.persistence.hibernate.StringArrayType")
  private String[] languages;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "name_suffix")
  private String nameSuffix;

  @Column(name = "races")
  @Type(type = "gov.ca.cwds.data.persistence.hibernate.StringJsonUserType")
  private String races;

  @Column(name = "ethnicity")
  @Type(type = "gov.ca.cwds.data.persistence.hibernate.StringJsonUserType")
  private String ethnicity;

  @Column(name = "legacy_source_table")
  private String legacySourceTable;

  @Column(name = "sensitive")
  private Boolean sensitive;

  @Column(name = "sealed")
  private Boolean sealed;

  @Column(name = "probation_youth")
  private Boolean probationYouth;

  @Column(name = "approximate_age")
  private String approximateAge;

  @Column(name = "approximate_age_units")
  private String approximateAgeUnits;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "participant_id", insertable = false, updatable = false)
  @OrderBy("id")
  private List<CsecEntity> csecs = new ArrayList<>();

  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id", insertable = false, updatable = false)
  private SafelySurrenderedBabiesEntity safelySurrenderedBabies;

  /**
   * Default constructor, required for some frameworks.
   */
  public ParticipantEntity() {
    super();
  }

  public ParticipantEntity(ParticipantIntakeApi participantIntakeApi) {
    updateFrom(participantIntakeApi);
  }

  /**
   * @param id - id
   * @param dateOfBirth - dateOfBirth
   * @param dateOfDeath - dateOfDeath
   * @param firstName - firstName
   * @param gender - gender
   * @param lastName - lastName
   * @param ssn - ssn
   * @param screeningEntity - screeningEntity
   * @param legacyId - legacyId
   * @param roles - roles
   * @param languages - languages
   * @param middleName - middleName
   * @param nameSuffix - nameSuffix
   * @param races - races
   * @param ethnicity - ethnicity
   * @param legacySourceTable - legacySourceTable
   * @param sensitive - sensitive
   * @param sealed - sealed
   * @param probationYouth - probationYouth
   * @param approximateAge - approximateAge
   * @param approximateAgeUnits - approximateAgeUnits
   */
  public ParticipantEntity(String id, Date dateOfBirth, Date dateOfDeath, String firstName,
      String gender, String lastName, String ssn, ScreeningEntity screeningEntity, String legacyId,
      String[] roles, String[] languages, String middleName, String nameSuffix, String races,
      String ethnicity, String legacySourceTable, Boolean sensitive, Boolean sealed,
      Boolean probationYouth, String approximateAge, String approximateAgeUnits) {
    this.id = id;
    this.dateOfBirth = freshDate(dateOfBirth);
    this.dateOfDeath = freshDate(dateOfDeath);
    this.firstName = firstName;
    this.gender = gender;
    this.lastName = lastName;
    this.ssn = ssn;
    this.languages = languages == null ? new String[0] : Arrays.copyOf(languages, languages.length);
    this.middleName = middleName;
    this.nameSuffix = nameSuffix;
    this.screeningEntity = screeningEntity;
    this.legacyId = legacyId;
    this.roles = roles == null ? new String[0] : Arrays.copyOf(roles, roles.length);
    this.races = races;
    this.ethnicity = ethnicity;
    this.legacySourceTable = legacySourceTable;
    this.sensitive = sensitive;
    this.probationYouth = probationYouth;
    this.sealed = sealed;
    this.approximateAge = approximateAge;
    this.approximateAgeUnits = approximateAgeUnits;
  }

  /**
   * @param participantIntakeApi - participantIntakeApi
   * @return the ParticipantEntity
   */
  public final ParticipantEntity updateFrom(ParticipantIntakeApi participantIntakeApi) {
    id = participantIntakeApi.getId();
    dateOfBirth = participantIntakeApi.getDateOfBirth();
    dateOfDeath = participantIntakeApi.getDateOfDeath();
    firstName = participantIntakeApi.getFirstName();
    gender = participantIntakeApi.getGender();
    lastName = participantIntakeApi.getLastName();
    ssn = participantIntakeApi.getSsn();
    screeningId = participantIntakeApi.getScreeningId();
    legacyId = participantIntakeApi.getLegacyId();
    roles = participantIntakeApi.getRoles().toArray(new String[0]);
    languages = participantIntakeApi.getLanguages().toArray(new String[0]);
    middleName = participantIntakeApi.getMiddleName();
    nameSuffix = participantIntakeApi.getNameSuffix();
    races = participantIntakeApi.getRaces();
    ethnicity = participantIntakeApi.getEthnicity();
    legacySourceTable = participantIntakeApi.getLegacySourceTable();
    sensitive = participantIntakeApi.isSensitive();
    sealed = participantIntakeApi.isSealed();
    probationYouth = participantIntakeApi.isProbationYouth();
    approximateAge = participantIntakeApi.getApproximateAge();
    approximateAgeUnits = participantIntakeApi.getApproximateAgeUnits();
    return this;
  }

  @Override
  public String getPrimaryKey() {
    return id;
  }

  @Override
  public String getId() {
    return id;
  }

  public Date getDateOfBirth() {
    return freshDate(dateOfBirth);
  }

  public Date getDateOfDeath() {
    return freshDate(dateOfDeath);
  }

  public String getFirstName() {
    return firstName;
  }

  public String getGender() {
    return gender;
  }

  public String getLastName() {
    return lastName;
  }

  public String getSsn() {
    return ssn;
  }

  public String getScreeningId() {
    return screeningId;
  }

  public ScreeningEntity getScreening() {
    return screeningEntity;
  }

  public String getLegacyId() {
    return legacyId;
  }

  public String[] getRoles() {
    if (roles == null) {
      return new String[0];
    } else {
      return Arrays.copyOf(roles, roles.length);
    }
  }

  public String[] getLanguages() {
    if (languages == null) {
      return new String[0];
    } else {
      return Arrays.copyOf(languages, languages.length);
    }
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getNameSuffix() {
    return nameSuffix;
  }

  public String getRaces() {
    return races;
  }

  public String getEthnicity() {
    return ethnicity;
  }

  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  public Boolean getSensitive() {
    return sensitive;
  }

  public Boolean getSealed() {
    return sealed;
  }

  public String getApproximateAge() {
    return approximateAge;
  }

  public String getApproximateAgeUnits() {
    return approximateAgeUnits;
  }

  public void setScreeningId(String screeningId) {
    this.screeningId = screeningId;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = freshDate(dateOfBirth);
  }

  public void setDateOfDeath(Date dateOfDeath) {
    this.dateOfDeath = freshDate(dateOfDeath);
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public ScreeningEntity getScreeningEntity() {
    return screeningEntity;
  }

  public void setScreeningEntity(ScreeningEntity screeningEntity) {
    this.screeningEntity = screeningEntity;
  }

  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  public List<CsecEntity> getCsecs() {
    return csecs;
  }

  public void setCsecs(List<CsecEntity> csecs) {
    this.csecs = csecs;
  }


  public SafelySurrenderedBabiesEntity getSafelySurrenderedBabies() {
    return safelySurrenderedBabies;
  }

  public void setSafelySurrenderedBabies(SafelySurrenderedBabiesEntity safelySurrenderedBabies) {
    this.safelySurrenderedBabies = safelySurrenderedBabies;
  }

  public void setRoles(String[] roles) {
    if (roles == null) {
      this.roles = new String[0];
    } else {
      this.roles = Arrays.copyOf(roles, roles.length);
    }
  }

  public void setLanguages(String[] languages) {
    if (languages == null) {
      this.languages = new String[0];
    } else {
      this.languages = Arrays.copyOf(languages, languages.length);
    }
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public void setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
  }

  public void setRaces(String races) {
    this.races = races;
  }

  public void setEthnicity(String ethnicity) {
    this.ethnicity = ethnicity;
  }

  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  public void setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
  }

  public void setSealed(Boolean sealed) {
    this.sealed = sealed;
  }

  public Boolean getProbationYouth() {
    return probationYouth;
  }

  public void setProbationYouth(Boolean probationYouth) {
    this.probationYouth = probationYouth;
  }

  public void setApproximateAge(String approximateAge) {
    this.approximateAge = approximateAge;
  }

  public void setApproximateAgeUnits(String approximateAgeUnits) {
    this.approximateAgeUnits = approximateAgeUnits;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  public void setId(String id) {
    this.id = id;
  }
}
