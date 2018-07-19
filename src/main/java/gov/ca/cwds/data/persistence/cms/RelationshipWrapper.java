package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.data.persistence.cms.RelationshipWrapper.FIND_ALL_RELATED_CLIENTS_BY_CLIENT_ID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.NamedNativeQuery;

import gov.ca.cwds.data.std.ApiMarker;

@Entity
// @formatter:off
@NamedNativeQuery(name = FIND_ALL_RELATED_CLIENTS_BY_CLIENT_ID,
    resultClass = RelationshipWrapper.class, readOnly = true,
    query = "SELECT DISTINCT \n" + "    CLNS.IDENTIFIER        AS Primary_LEGACY_ID, \n"
        + "    CLNS.COM_FST_NM        AS Primary_FIRST_NAME, \n"
        + "    CLNS.COM_MID_NM        AS Primary_MIDDLE_NAME, \n"
        + "    CLNS.COM_LST_NM        AS Primary_LAST_NAME, \n"
        + "    CLNS.SUFX_TLDSC        AS Primary_NAME_SUFFIX, \n"
        + "    CLNS.BIRTH_DT          AS Primary_BIRTH_DATE, \n"
        + "    CLNS.DEATH_DT          AS Primary_DEATH_DATE, \n"
        + "    CLNS.GENDER_CD         AS Primary_GENDER_CODE, \n"
        + "    CLNR.IDENTIFIER        AS RELATION_ID, \n"
        + "    CLNR.CLNTRELC          AS Primary_REL_ID, \n"
        + "    sc2.SHORT_DSC          AS Secondary_REL_TYPE, \n"
        + "    sc2.SYS_ID             AS Secondary_REL_CODE, \n"
        + "    CLNP.IDENTIFIER        AS Secondary_LEGACY_ID, \n"
        + "    CLNP.COM_FST_NM        AS Secondary_FIRST_NAME, \n"
        + "    CLNP.COM_MID_NM        AS Secondary_MIDDLE_NAME, \n"
        + "    CLNP.COM_LST_NM        AS Secondary_LAST_NAME, \n"
        + "    CLNP.SUFX_TLDSC        AS Secondary_NAME_SUFFIX, \n"
        + "    CLNP.BIRTH_DT          AS Secondary_BIRTH_DATE, \n"
        + "    CLNP.DEATH_DT          AS Secondary_DEATH_DATE, \n"
        + "    CLNP.GENDER_CD         AS Secondary_GENDER_CODE, \n"
        + "    CLNR.ABSENT_CD         AS ABSENT_CODE, \n"
        + "    CLNR.SAME_HM_CD        AS SAME_HOME_CODE, \n"
        + "    CLNR.START_DT          AS Relationship_START_DATE, \n"
        + "    CLNR.END_DT            AS Relationship_END_DATE \n"
        + "FROM {h-schema}CLIENT_T  GT \n"
        + "JOIN {h-schema}CLN_RELT CLNR ON GT.IDENTIFIER   = CLNR.FKCLIENT_T \n"
        + "JOIN {h-schema}CLIENT_T CLNS ON CLNR.FKCLIENT_0 = CLNS.IDENTIFIER \n"
        + "JOIN {h-schema}CLIENT_T CLNP ON CLNR.FKCLIENT_T = CLNP.IDENTIFIER \n"
        + "JOIN {h-schema}SYS_CD_C SC   ON SC.SYS_ID       = CLNR.CLNTRELC AND SC.FKS_META_T = 'CLNTRELC' \n"
        + "JOIN {h-schema}SYS_CD_C SC2  ON SC2.SYS_ID      = CAST(SC.LONG_DSC AS SMALLINT) \n"
        + "WHERE CLNR.FKCLIENT_0 = :clientId OR CLNR.FKCLIENT_T = :clientId \n" + "UNION \n"
        + "SELECT DISTINCT \n" + "    CLNS.IDENTIFIER        AS Primary_LEGACY_ID, \n"
        + "    CLNS.COM_FST_NM        AS Primary_FIRST_NAME, \n"
        + "    CLNS.COM_MID_NM        AS Primary_MIDDLE_NAME, \n"
        + "    CLNS.COM_LST_NM        AS Primary_LAST_NAME, \n"
        + "    CLNS.SUFX_TLDSC        AS Primary_NAME_SUFFIX, \n"
        + "    CLNS.BIRTH_DT          AS Primary_BIRTH_DATE, \n"
        + "    CLNS.DEATH_DT          AS Primary_DEATH_DATE, \n"
        + "    CLNS.GENDER_CD         AS Primary_GENDER_CODE, \n"
        + "    CLNR.IDENTIFIER        AS RELATION_ID, \n"
        + "    CLNR.CLNTRELC          AS Primary_REL_ID, \n"
        + "    sc2.SHORT_DSC          AS Secondary_REL_TYPE, \n"
        + "    sc2.SYS_ID             AS Secondary_REL_CODE, \n"
        + "    CLNP.IDENTIFIER        AS Secondary_LEGACY_ID, \n"
        + "    CLNP.COM_FST_NM        AS Secondary_FIRST_NAME, \n"
        + "    CLNP.COM_MID_NM        AS Secondary_MIDDLE_NAME, \n"
        + "    CLNP.COM_LST_NM        AS Secondary_LAST_NAME, \n"
        + "    CLNP.SUFX_TLDSC        AS Secondary_NAME_SUFFIX, \n"
        + "    CLNP.BIRTH_DT          AS Secondary_BIRTH_DATE, \n"
        + "    CLNP.DEATH_DT          AS Secondary_DEATH_DATE, \n"
        + "    CLNP.GENDER_CD         AS Secondary_GENDER_CODE, \n"
        + "    CLNR.ABSENT_CD         AS ABSENT_CODE, \n"
        + "    CLNR.SAME_HM_CD        AS SAME_HOME_CODE, \n"
        + "    CLNR.START_DT          AS Relationship_START_DATE, \n"
        + "    CLNR.END_DT            AS Relationship_END_DATE \n" + "FROM {h-schema}CLIENT_T GT \n"
        + "JOIN {h-schema}CLN_RELT CLNR ON GT.IDENTIFIER   = CLNR.FKCLIENT_0 \n"
        + "JOIN {h-schema}CLIENT_T CLNS ON CLNR.FKCLIENT_0 = CLNS.IDENTIFIER \n"
        + "JOIN {h-schema}CLIENT_T CLNP ON CLNR.FKCLIENT_T = CLNP.IDENTIFIER \n"
        + "JOIN {h-schema}SYS_CD_C SC   ON SC.SYS_ID       = CLNR.CLNTRELC AND SC.FKS_META_T = 'CLNTRELC' \n"
        + "JOIN {h-schema}SYS_CD_C SC2  ON SC2.SYS_ID      = CAST(SC.LONG_DSC AS SMALLINT) \n"
        + "WHERE CLNR.FKCLIENT_0 = :clientId OR CLNR.FKCLIENT_T = :clientId \n" + "WITH UR")
// @formatter:on
@SuppressWarnings({"squid:S00107"})
public class RelationshipWrapper implements ApiMarker {

  private static final long serialVersionUID = 1L;

  public static final String FIND_ALL_RELATED_CLIENTS_BY_CLIENT_ID =
      "gov.ca.cwds.data.persistence.cms.RelationshipWrapper.findAllRelatedClientsByClientId";

  @Id
  @Column(name = "RELATION_ID")
  private String relationId;

  @Column(name = "Primary_LEGACY_ID")
  private String primaryLegacyId;

  @ColumnTransformer(read = "rtrim(Primary_FIRST_NAME)")
  @Column(name = "Primary_FIRST_NAME")
  private String primaryFirstName;

  @Column(name = "Primary_MIDDLE_NAME")
  private String primaryMiddleName;

  @Column(name = "Primary_LAST_NAME")
  private String primaryLastName;

  @Column(name = "Primary_NAME_SUFFIX")
  private String primaryNameSuffix;

  @Column(name = "Primary_BIRTH_DATE")
  private String primaryDateOfBirth;

  @Column(name = "Primary_DEATH_DATE")
  private String primaryDateOfDeath;

  @Column(name = "Primary_GENDER_CODE")
  private String primaryGenderCode;

  @Column(name = "Secondary_LEGACY_ID")
  private String secondaryLegacyId;

  @ColumnTransformer(read = "rtrim(Secondary_FIRST_NAME)")
  @Column(name = "Secondary_FIRST_NAME")
  private String secondaryFirstName;

  @Column(name = "Secondary_MIDDLE_NAME")
  private String secondaryMiddleName;

  @Column(name = "Secondary_LAST_NAME")
  private String secondaryLastName;

  @Column(name = "Secondary_NAME_SUFFIX")
  private String secondaryNameSuffix;

  @Column(name = "Secondary_BIRTH_DATE")
  private String secondaryDateOfBirth;

  @Column(name = "Secondary_DEATH_DATE")
  private String secondaryDateOfDeath;

  @Column(name = "Secondary_GENDER_CODE")
  private String secondaryGenderCode;

  @Column(name = "Primary_REL_ID")
  private String primaryRelationshipCode;

  @Column(name = "Secondary_REL_CODE")
  private String secondaryRelationshipCode;

  @Column(name = "ABSENT_CODE")
  private String absentParentCode;

  @Column(name = "SAME_HOME_CODE")
  private String sameHomeCode;

  @Column(name = "Relationship_START_DATE")
  private String relationshipStartDate;

  @Column(name = "Relationship_END_DATE")
  private String relationshipEndDate;


  public RelationshipWrapper() {}

  public RelationshipWrapper(String relationId, String primaryLegacyId, String secondaryLegacyId,
      String primaryFirstName, String primaryMiddleName, String primaryLastName,
      String secondaryFirstName, String secondaryMiddleName, String secondaryLastName,
      String primaryRelationshipCode, String secondaryRelationshipCode) {
    this.relationId = relationId;
    this.primaryLegacyId = primaryLegacyId;
    this.secondaryLegacyId = secondaryLegacyId;
    this.primaryFirstName = primaryFirstName;
    this.primaryMiddleName = primaryMiddleName;
    this.primaryLastName = primaryLastName;
    this.secondaryFirstName = secondaryFirstName;
    this.secondaryMiddleName = secondaryMiddleName;
    this.secondaryLastName = secondaryLastName;
    this.primaryRelationshipCode = primaryRelationshipCode;
    this.secondaryRelationshipCode = secondaryRelationshipCode;
  }

  public String getRelationId() {
    return relationId;
  }

  public void setRelationId(String relationId) {
    this.relationId = relationId;
  }

  public String getPrimaryLegacyId() {
    return primaryLegacyId;
  }

  public void setPrimaryLegacyId(String primaryLegacyId) {
    this.primaryLegacyId = primaryLegacyId;
  }

  public String getSecondaryLegacyId() {
    return secondaryLegacyId;
  }

  public void setSecondaryLegacyId(String secondaryLegacyId) {
    this.secondaryLegacyId = secondaryLegacyId;
  }

  public String getPrimaryFirstName() {
    return primaryFirstName;
  }

  public void setPrimaryFirstName(String primaryFirstName) {
    this.primaryFirstName = primaryFirstName;
  }


  public String getPrimaryMiddleName() {
    return primaryMiddleName;
  }

  public String getSecondaryMiddleName() {
    return secondaryMiddleName;
  }

  public void setPrimaryMiddleName(String primaryMiddleName) {
    this.primaryMiddleName = primaryMiddleName;
  }

  public void setSecondaryMiddleName(String secondaryMiddleName) {
    this.secondaryMiddleName = secondaryMiddleName;
  }

  public String getPrimaryLastName() {
    return primaryLastName;
  }

  public void setPrimaryLastName(String primaryLastName) {
    this.primaryLastName = primaryLastName;
  }

  public String getSecondaryFirstName() {
    return secondaryFirstName;
  }

  public void setSecondaryFirstName(String secondaryFirstName) {
    this.secondaryFirstName = secondaryFirstName;
  }

  public String getSecondaryLastName() {
    return secondaryLastName;
  }

  public void setSecondaryLastName(String secondaryLastName) {
    this.secondaryLastName = secondaryLastName;
  }

  public String getPrimaryRelationshipCode() {
    return primaryRelationshipCode;
  }

  public void setPrimaryRelationshipCode(String primaryRelationshipCode) {
    this.primaryRelationshipCode = primaryRelationshipCode;
  }

  public String getSecondaryRelationshipCode() {
    return secondaryRelationshipCode;
  }

  public void setSecondaryRelationshipCode(String secondaryRelationshipCode) {
    this.secondaryRelationshipCode = secondaryRelationshipCode;
  }

  public String getPrimaryNameSuffix() {
    return primaryNameSuffix;
  }

  public void setPrimaryNameSuffix(String primaryNameSuffix) {
    this.primaryNameSuffix = primaryNameSuffix;
  }

  public String getSecondaryNameSuffix() {
    return secondaryNameSuffix;
  }

  public void setSecondaryNameSuffix(String secondaryNameSuffix) {
    this.secondaryNameSuffix = secondaryNameSuffix;
  }

  public String getPrimaryDateOfBirth() {
    return primaryDateOfBirth;
  }

  public void setPrimaryDateOfBirth(String primaryDateOfBirth) {
    this.primaryDateOfBirth = primaryDateOfBirth;
  }

  public String getPrimaryDateOfDeath() {
    return primaryDateOfDeath;
  }

  public void setPrimaryDateOfDeath(String primaryDateOfDeath) {
    this.primaryDateOfDeath = primaryDateOfDeath;
  }

  public String getSecondaryDateOfBirth() {
    return secondaryDateOfBirth;
  }

  public void setSecondaryDateOfBirth(String secondaryDateOfBirth) {
    this.secondaryDateOfBirth = secondaryDateOfBirth;
  }

  public String getSecondaryDateOfDeath() {
    return secondaryDateOfDeath;
  }

  public void setSecondaryDateOfDeath(String secondaryDateOfDeath) {
    this.secondaryDateOfDeath = secondaryDateOfDeath;
  }

  public String getAbsentParentCode() {
    return absentParentCode;
  }

  public void setAbsentParentCode(String absentParentCode) {
    this.absentParentCode = absentParentCode;
  }

  public String getSameHomeCode() {
    return sameHomeCode;
  }

  public void setSameHomeCode(String sameHomeCode) {
    this.sameHomeCode = sameHomeCode;
  }

  public String getPrimaryGenderCode() {
    return primaryGenderCode;
  }

  public void setPrimaryGenderCode(String primaryGenderCode) {
    this.primaryGenderCode = primaryGenderCode;
  }

  public String getSecondaryGenderCode() {
    return secondaryGenderCode;
  }

  public void setSecondaryGenderCode(String secondaryGenderCode) {
    this.secondaryGenderCode = secondaryGenderCode;
  }

  public String getRelationshipStartDate() {
    return relationshipStartDate;
  }

  public void setRelationshipStartDate(String relationshipStartDate) {
    this.relationshipStartDate = relationshipStartDate;
  }

  public String getRelationshipEndDate() {
    return relationshipEndDate;
  }

  public void setRelationshipEndDate(String relationshipEndDate) {
    this.relationshipEndDate = relationshipEndDate;
  }



}
