package gov.ca.cwds.data.persistence.ns;

import gov.ca.cwds.Identifiable;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.ns.papertrail.HasPaperTrail;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

/**
 * {@link PersistentObject} representing Participant.
 *
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findLegacyIdListByScreeningId",
    query = "SELECT legacyId FROM ParticipantEntity WHERE screeningEntity.id = :screeningId)")
@Entity
@Table(name = "participants")
public class ParticipantEntity implements PersistentObject, HasPaperTrail, Identifiable<String> {

  @Id
  @Column(name = "id")
  @GenericGenerator(
      name = "participant_id",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {
          @org.hibernate.annotations.Parameter(
              name = "sequence_name", value = "participants_id_seq")
      }
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_id")
  private String id;

  @Column(name = "date_of_birth")
  private Date dateOfBirth;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "gender")
  private String gender;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "ssn")
  private String ssn;

  @HashCodeExclude
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "screening_id", nullable = false)
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

  @Column(name = "approximate_age")
  private String approximateAge;

  @Column(name = "approximate_age_units")
  private String approximateAgeUnits;

  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public ParticipantEntity() {
    super();
  }

  public ParticipantEntity(ParticipantIntakeApi participantIntakeApi, ScreeningEntity screeningEntity) {
    id = participantIntakeApi.getId();
    dateOfBirth = participantIntakeApi.getDateOfBirth();
    firstName = participantIntakeApi.getFirstName();
    gender = participantIntakeApi.getGender();
    lastName = participantIntakeApi.getLastName();
    ssn = participantIntakeApi.getSsn();
    this.screeningEntity = screeningEntity;
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
    approximateAge = participantIntakeApi.getApproximateAge();
    approximateAgeUnits = participantIntakeApi.getApproximateAgeUnits();
 }

  public ParticipantEntity(String id, Date dateOfBirth, String firstName,
		  String gender, String lastName, String ssn,
		  ScreeningEntity screeningEntity, String legacyId, String[] roles,
		  String[] languages, String middleName, String nameSuffix,
		  String races, String ethnicity, String legacySourceTable,
		  Boolean sensitive, Boolean sealed, String approximateAge,
		  String approximateAgeUnits) {
	  this.id = id;
	  this.dateOfBirth = dateOfBirth;
	  this.firstName = firstName;
	  this.gender = gender;
	  this.lastName = lastName;
	  this.ssn = ssn;
	  this.languages = languages;
	  this.middleName = middleName;
	  this.nameSuffix = nameSuffix;
	  this.screeningEntity = screeningEntity;
	  this.legacyId = legacyId;
	  this.roles = roles;
	  this.races = races;
	  this.ethnicity = ethnicity;
	  this.legacySourceTable = legacySourceTable;
	  this.sensitive = sensitive;
	  this.sealed = sealed;
	  this.approximateAge = approximateAge;
	  this.approximateAgeUnits = approximateAgeUnits;	  
  }
  
		  
  @Override
  public String getPrimaryKey() {
    return id;
  }

  public String getId() {
    return id;
  }

  public Date getDateOfBirth() {
    return freshDate(dateOfBirth);
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

  public ScreeningEntity getScreening() {
    return screeningEntity;
  }

  public String getLegacyId() {
    return legacyId;
  }

  public String[] getRoles() {
    return roles;
  }

  public String[] getLanguages() {
    return languages;
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
}
