package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.data.persistence.cms.RelationshipWrapper.FIND_ALL_RELATED_CLIENTS_BY_CLIENT_ID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = FIND_ALL_RELATED_CLIENTS_BY_CLIENT_ID,
    resultClass = RelationshipWrapper.class, readOnly = true,
    query = "SELECT DISTINCT \n" + "    CLNS.IDENTIFIER        AS Primary_LEGACY_ID, \n"
        + "    CLNS.COM_FST_NM        AS Primary_FIRST_NAME, \n"
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
        + "    CLNP.COM_LST_NM        AS Secondary_LAST_NAME, \n"
        + "    CLNP.SUFX_TLDSC        AS Secondary_NAME_SUFFIX, \n"
        + "    CLNP.BIRTH_DT          AS Secondary_BIRTH_DATE, \n"
        + "    CLNP.DEATH_DT          AS Secondary_DEATH_DATE, \n"
        + "    CLNP.GENDER_CD         AS Secondary_GENDER_CODE, \n"
        + "    CLNR.ABSENT_CD         AS ABSENT_CODE, \n"
        + "    CLNR.SAME_HM_CD        AS SAME_HOME_CODE \n" + "FROM  {h-schema}CLIENT_T  GT \n"
        + "JOIN {h-schema}CLN_RELT CLNR  ON GT.IDENTIFIER   = CLNR.FKCLIENT_T \n"
        + "JOIN {h-schema}CLIENT_T CLNS  ON CLNR.FKCLIENT_0 = CLNS.IDENTIFIER \n"
        + "JOIN {h-schema}CLIENT_T CLNP  ON CLNR.FKCLIENT_T = CLNP.IDENTIFIER \n"
        + "JOIN {h-schema}SYS_CD_C SC  ON SC.SYS_ID         = CLNR.CLNTRELC AND SC.FKS_META_T = 'CLNTRELC' \n"
        + "JOIN {h-schema}SYS_CD_C SC2 ON SC2.SYS_ID        = CAST(SC.LONG_DSC AS SMALLINT) \n"
        + "WHERE \n" + "    CLNR.FKCLIENT_0 = :clientId \n"
        + "    OR CLNR.FKCLIENT_T = :clientId \n" + "UNION \n" + "SELECT DISTINCT \n"
        + "    CLNS.IDENTIFIER        AS Primary_LEGACY_ID, \n"
        + "    CLNS.COM_FST_NM        AS Primary_FIRST_NAME, \n"
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
        + "    CLNP.COM_LST_NM        AS Secondary_LAST_NAME, \n"
        + "    CLNP.SUFX_TLDSC        AS Secondary_NAME_SUFFIX, \n"
        + "    CLNP.BIRTH_DT          AS Secondary_BIRTH_DATE, \n"
        + "    CLNP.DEATH_DT          AS Secondary_DEATH_DATE, \n"
        + "    CLNP.GENDER_CD         AS Secondary_GENDER_CODE, \n"
        + "    CLNR.ABSENT_CD         AS ABSENT_CODE, \n"
        + "    CLNR.SAME_HM_CD        AS SAME_HOME_CODE \n" + "FROM  {h-schema}CLIENT_T GT \n"
        + "JOIN {h-schema}CLN_RELT CLNR ON GT.IDENTIFIER   = CLNR.FKCLIENT_0 \n"
        + "JOIN {h-schema}CLIENT_T CLNS ON CLNR.FKCLIENT_0 = CLNS.IDENTIFIER \n"
        + "JOIN {h-schema}CLIENT_T CLNP ON CLNR.FKCLIENT_T = CLNP.IDENTIFIER \n"
        + "JOIN {h-schema}SYS_CD_C SC  ON SC.SYS_ID        = CLNR.CLNTRELC AND SC.FKS_META_T = 'CLNTRELC' \n"
        + "JOIN {h-schema}SYS_CD_C SC2 ON SC2.SYS_ID       = CAST(SC.LONG_DSC AS SMALLINT) \n"
        + "WHERE \n" + "    CLNR.FKCLIENT_0 =  :clientId \n"
        + "    OR CLNR.FKCLIENT_T = :clientId \n" + "WITH UR")
public class RelationshipWrapper {

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


  public RelationshipWrapper() {}

  public RelationshipWrapper(String relationId, String primaryLegacyId, String secondaryLegacyId,
      String primaryFirstName, String primaryLastName, String secondaryFirstName,
      String secondaryLastName, String primaryRelationshipCode, String secondaryRelationshipCode) {
    this.relationId = relationId;
    this.primaryLegacyId = primaryLegacyId;
    this.secondaryLegacyId = secondaryLegacyId;
    this.primaryFirstName = primaryFirstName;
    this.primaryLastName = primaryLastName;
    this.secondaryFirstName = secondaryFirstName;
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



}
