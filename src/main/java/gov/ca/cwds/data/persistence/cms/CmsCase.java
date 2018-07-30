package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.validation.ValidCounty;

/**
 * {@link CmsPersistentObject} implementation representing a Case.
 * 
 * @author CWDS API Team
 */
//@formatter:off
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.CmsCase.findByClientIds",
    query = "FROM CmsCase WHERE fkchldClt IN :clientIds")

@NamedNativeQuery(name = "gov.ca.cwds.data.persistence.cms.CmsCase.findAllRelatedByVictimClientId",
    query = " SELECT C.*                            \n"
        + "     FROM {h-schema}CASE_T C             \n"
        + "     WHERE C.FKCHLD_CLT = :clientId      \n"
        + "   UNION                                 \n"
        + "     SELECT DISTINCT X.*                 \n"
        + "     FROM {h-schema}CLN_RELT T,          \n"
        + "     {h-schema}CASE_T X                  \n"
        + "     WHERE T.FKCLIENT_T = :clientId      \n"
        + "     AND T.FKCLIENT_0 <> :clientId       \n"
        + "     AND T.FKCLIENT_0 = X.FKCHLD_CLT     \n"
        + "   UNION                                 \n"
        + "     SELECT DISTINCT Y.*                 \n"
        + "     FROM {h-schema}CLN_RELT Z,          \n"
        + "     {h-schema}CASE_T Y                  \n"
        + "     WHERE Z.FKCLIENT_0  = :clientId     \n"
        + "     AND Z.FKCLIENT_T <> :clientId       \n"
        + "     AND Z.FKCLIENT_T = Y.FKCHLD_CLT     \n" 
        + "   FOR READ ONLY WITH UR",
    resultClass = CmsCase.class, readOnly = true)
//@formatter:on
@Entity
@Table(name = "CASE_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
// @Cacheable
public class CmsCase extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "ALERT_TXT")
  private String alertText;

  @Column(name = "APRVL_NO")
  private String approvalNumber;

  @Type(type = "short")
  @Column(name = "APV_STC")
  private Short approvalStatusType;

  @Type(type = "short")
  @Column(name = "CLS_RSNC")
  private Short caseClosureReasonType;

  @Column(name = "CSPL_DET_B")
  private String caseplanChildrenDetailIndVar;

  @Column(name = "CL_STM_TXT")
  private String closureStatementText;

  @Type(type = "short")
  @Column(name = "CNTRY_C")
  private Short countryCodeType;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "NOTES_DOC")
  private String drmsNotesDoc;

  @Type(type = "date")
  @Column(name = "EMANCPN_DT")
  private Date emancipationDate;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "FKCHLD_CLT")
  private String fkchldClt;

  @Column(name = "FKREFERL_T")
  private String fkreferlt;

  @Column(name = "FKSTFPERST")
  private String fkstfperst;

  @Type(type = "short")
  @Column(name = "GVR_ENTC")
  @ValidCounty
  private Short governmentEntityType;

  @Column(name = "ICPCSTAT_B")
  private String icpcOutgngPlcmtStatusIndVar;

  @Column(name = "ICPC_RQT_B")
  private String icpcOutgoingRequestIndVar;

  @Column(name = "LMT_ACSSCD")
  private String limitedAccessCode;

  @Type(type = "date")
  @Column(name = "LMT_ACS_DT")
  private Date limitedAccessDate;

  @Column(name = "LMT_ACSDSC")
  private String limitedAccessDesc;

  @Type(type = "short")
  @Column(name = "L_GVR_ENTC")
  private Short limitedAccessGovernmentEntityType;

  @Column(name = "CASE_NM")
  private String caseName;

  @Type(type = "date")
  @Column(name = "NXT_TILPDT")
  private Date nextTilpDueDate;

  @Type(type = "date")
  @Column(name = "PRJ_END_DT")
  private Date projectedEndDate;

  @Column(name = "RSP_AGY_CD")
  private String responsibleAgencyCode;

  @Column(name = "SPRJ_CST_B")
  private String specialProjectCaseIndVar;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  @Type(type = "short")
  @Column(name = "STATE_C")
  private Short stateCodeType;

  @Type(type = "short")
  @Column(name = "SRV_CMPC")
  private Short activeServiceComponentType;

  @Type(type = "date")
  @Column(name = "SRV_CMPDT")
  private Date activeSvcComponentStartDate;

  @Column(name = "TICKLE_T_B")
  private String tickleIndVar;

  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE},
      optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKCHLD_CLT", nullable = false, updatable = false, insertable = false)
  private ChildClient childClient;

  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE},
      optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKSTFPERST", nullable = false, updatable = false, insertable = false)
  private StaffPerson staffPerson;

  /**
   * Referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent client record.
   * </p>
   */
  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKREFERL_T", nullable = true, updatable = false, insertable = false)
  private Referral riReferral;

  /**
   * Default constructor.
   */
  public CmsCase() {
    super();
  }

  public CmsCase(String id, String alertText, String approvalNumber, Short approvalStatusType,
      Short caseClosureReasonType, String caseplanChildrenDetailIndVar, String closureStatementText,
      Short countryCodeType, String countySpecificCode, String drmsNotesDoc, Date emancipationDate,
      Date endDate, String fkchldClt, String fkreferlt, String fkstfperst,
      Short governmentEntityType, String icpcOutgngPlcmtStatusIndVar,
      String icpcOutgoingRequestIndVar, String limitedAccessCode, Date limitedAccessDate,
      String limitedAccessDesc, Short limitedAccessGovernmentEntityType, String caseName,
      Date nextTilpDueDate, Date projectedEndDate, String responsibleAgencyCode,
      String specialProjectCaseIndVar, Date startDate, Short stateCodeType,
      Short activeServiceComponentType, Date activeSvcComponentStartDate, String tickleIndVar,
      ChildClient childClient, StaffPerson staffPerson, Referral riReferral) {
    super();
    this.id = id;
    this.alertText = alertText;
    this.approvalNumber = approvalNumber;
    this.approvalStatusType = approvalStatusType;
    this.caseClosureReasonType = caseClosureReasonType;
    this.caseplanChildrenDetailIndVar = caseplanChildrenDetailIndVar;
    this.closureStatementText = closureStatementText;
    this.countryCodeType = countryCodeType;
    this.countySpecificCode = countySpecificCode;
    this.drmsNotesDoc = drmsNotesDoc;
    this.emancipationDate = freshDate(emancipationDate);
    this.endDate = freshDate(endDate);
    this.fkchldClt = fkchldClt;
    this.fkreferlt = fkreferlt;
    this.fkstfperst = fkstfperst;
    this.governmentEntityType = governmentEntityType;
    this.icpcOutgngPlcmtStatusIndVar = icpcOutgngPlcmtStatusIndVar;
    this.icpcOutgoingRequestIndVar = icpcOutgoingRequestIndVar;
    this.limitedAccessCode = limitedAccessCode;
    this.limitedAccessDate = freshDate(limitedAccessDate);
    this.limitedAccessDesc = limitedAccessDesc;
    this.limitedAccessGovernmentEntityType = limitedAccessGovernmentEntityType;
    this.caseName = caseName;
    this.nextTilpDueDate = freshDate(nextTilpDueDate);
    this.projectedEndDate = freshDate(projectedEndDate);
    this.responsibleAgencyCode = responsibleAgencyCode;
    this.specialProjectCaseIndVar = specialProjectCaseIndVar;
    this.startDate = freshDate(startDate);
    this.stateCodeType = stateCodeType;
    this.activeServiceComponentType = activeServiceComponentType;
    this.activeSvcComponentStartDate = freshDate(activeSvcComponentStartDate);
    this.tickleIndVar = tickleIndVar;
    this.childClient = childClient;
    this.staffPerson = staffPerson;
    this.riReferral = riReferral;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public String getId() {
    return this.id;
  }

  public String getAlertText() {
    return alertText;
  }

  public String getApprovalNumber() {
    return approvalNumber;
  }

  public Short getApprovalStatusType() {
    return approvalStatusType;
  }

  public Short getCaseClosureReasonType() {
    return caseClosureReasonType;
  }

  public String getCaseplanChildrenDetailIndVar() {
    return caseplanChildrenDetailIndVar;
  }

  public String getClosureStatementText() {
    return closureStatementText;
  }

  public Short getCountryCodeType() {
    return countryCodeType;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public String getDrmsNotesDoc() {
    return drmsNotesDoc;
  }

  public Date getEmancipationDate() {
    return freshDate(emancipationDate);
  }

  public Date getEndDate() {
    return freshDate(endDate);
  }

  public String getFkchldClt() {
    return fkchldClt;
  }

  public String getFkreferlt() {
    return fkreferlt;
  }

  public String getFkstfperst() {
    return fkstfperst;
  }

  public Short getGovernmentEntityType() {
    return governmentEntityType;
  }

  public void setGovernmentEntityType(Short governmentEntityType) {
    this.governmentEntityType = governmentEntityType;
  }

  public String getIcpcOutgngPlcmtStatusIndVar() {
    return icpcOutgngPlcmtStatusIndVar;
  }

  public String getIcpcOutgoingRequestIndVar() {
    return icpcOutgoingRequestIndVar;
  }

  public String getLimitedAccessCode() {
    return limitedAccessCode;
  }

  public void setLimitedAccessCode(String limitedAccessCode) {
    this.limitedAccessCode = limitedAccessCode;
  }

  public Date getLimitedAccessDate() {
    return freshDate(limitedAccessDate);
  }

  public String getLimitedAccessDesc() {
    return limitedAccessDesc;
  }

  public Short getLimitedAccessGovernmentEntityType() {
    return limitedAccessGovernmentEntityType;
  }

  public String getCaseName() {
    return caseName;
  }

  public Date getNextTilpDueDate() {
    return freshDate(nextTilpDueDate);
  }

  public Date getProjectedEndDate() {
    return freshDate(projectedEndDate);
  }

  public String getResponsibleAgencyCode() {
    return responsibleAgencyCode;
  }

  public String getSpecialProjectCaseIndVar() {
    return specialProjectCaseIndVar;
  }

  public Date getStartDate() {
    return freshDate(startDate);
  }

  public Short getStateCodeType() {
    return stateCodeType;
  }

  public Short getActiveServiceComponentType() {
    return activeServiceComponentType;
  }

  public Date getActiveSvcComponentStartDate() {
    return freshDate(activeSvcComponentStartDate);
  }

  public String getTickleIndVar() {
    return tickleIndVar;
  }

  public ChildClient getChildClient() {
    return childClient;
  }

  public StaffPerson getStaffPerson() {
    return staffPerson;
  }

  public void setStaffPerson(StaffPerson staffPerson) {
    this.staffPerson = staffPerson;
  }

  public Referral getRiReferral() {
    return riReferral;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
