package gov.ca.cwds.es.live;

import static gov.ca.cwds.rest.api.domain.DomainChef.freshDate;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import gov.ca.cwds.data.persistence.cms.VarargPrimaryKey;

@SuppressWarnings({"findsecbugs:SQL_INJECTION_JDBC", "squid:S2095",
    "findbugs:SE_TRANSIENT_FIELD_NOT_RESTORED"})
public class RawAddress extends ClientAddressReference
    implements LiveElasticJdbcReader<RawAddress> {

  private static final long serialVersionUID = 1L;

  protected enum ColumnPosition {
    START, CLT_IDENTIFIER, CLA_IDENTIFIER, ADR_IDENTIFIER, ADR_LST_UPD_ID, ADR_LST_UPD_TS, ADR_ADDR_DSC, ADR_CITY_NM, ADR_EMRG_EXTNO, ADR_EMRG_TELNO, ADR_FRG_ADRT_B, ADR_GVR_ENTC, ADR_HEADER_ADR, ADR_MSG_EXT_NO, ADR_MSG_TEL_NO, ADR_POSTDIR_CD, ADR_PREDIR_CD, ADR_PRM_EXT_NO, ADR_PRM_TEL_NO, ADR_STATE_C, ADR_STREET_NM, ADR_STREET_NO, ADR_ST_SFX_C, ADR_UNT_DSGC, ADR_UNIT_NO, ADR_ZIP_NO, ADR_ZIP_SFX_NO, ADR_IBMSNAP_LOGMARKER, ADR_IBMSNAP_OPERATION
  }

  // =======================
  // ADDRS_T: (address)
  // =======================

  @Id
  @Column(name = "ADR_IDENTIFIER")
  protected String adrId;

  @Enumerated(EnumType.STRING)
  @Column(name = "ADR_IBMSNAP_OPERATION", updatable = false)
  protected CmsReplicationOperation adrReplicationOperation;

  @Type(type = "timestamp")
  @Column(name = "ADR_IBMSNAP_LOGMARKER", updatable = false)
  protected Date adrReplicationDate;

  @Column(name = "ADR_CITY_NM")
  protected String adrCity;

  @Column(name = "ADR_EMRG_TELNO")
  protected Long adrEmergencyNumber;

  @Type(type = "integer")
  @Column(name = "ADR_EMRG_EXTNO")
  protected Integer adrEmergencyExtension;

  @Column(name = "ADR_FRG_ADRT_B")
  protected String adrFrgAdrtB;

  @Type(type = "short")
  @Column(name = "ADR_GVR_ENTC")
  protected Short adrGovernmentEntityCd;

  @Column(name = "ADR_MSG_TEL_NO")
  protected Long adrMessageNumber;

  @Type(type = "integer")
  @Column(name = "ADR_MSG_EXT_NO")
  protected Integer adrMessageExtension;

  @Column(name = "ADR_HEADER_ADR")
  protected String adrHeaderAddress;

  @Column(name = "ADR_PRM_TEL_NO")
  protected Long adrPrimaryNumber;

  @Type(type = "integer")
  @Column(name = "ADR_PRM_EXT_NO")
  protected Integer adrPrimaryExtension;

  @Type(type = "short")
  @Column(name = "ADR_STATE_C")
  protected Short adrState;

  @Column(name = "ADR_STREET_NM")
  @ColumnTransformer(read = "trim(ADR_STREET_NM)")
  protected String adrStreetName;

  @Column(name = "ADR_STREET_NO")
  protected String adrStreetNumber;

  @Column(name = "ADR_ZIP_NO")
  protected String adrZip;

  @Column(name = "ADR_ADDR_DSC")
  protected String adrAddressDescription;

  @Type(type = "short")
  @Column(name = "ADR_ZIP_SFX_NO")
  protected Short adrZip4;

  @Column(name = "ADR_POSTDIR_CD")
  protected String adrPostDirCd;

  @Column(name = "ADR_PREDIR_CD")
  protected String adrPreDirCd;

  @Type(type = "short")
  @Column(name = "ADR_ST_SFX_C")
  protected Short adrStreetSuffixCd;

  @Type(type = "short")
  @Column(name = "ADR_UNT_DSGC")
  protected Short adrUnitDesignationCd;

  @Column(name = "ADR_UNIT_NO")
  protected String adrUnitNumber;

  @Column(name = "ADR_LST_UPD_TS")
  protected Date adrLastUpdatedTime;

  @Override
  public RawAddress read(ResultSet rs) throws SQLException {
    super.read(rs);

    this.adrId = trimToNull(rs.getString(ColumnPosition.ADR_IDENTIFIER.ordinal()));
    this.adrCity = trimToNull(rs.getString(ColumnPosition.ADR_CITY_NM.ordinal()));
    this.adrEmergencyNumber = rs.getLong(ColumnPosition.ADR_EMRG_TELNO.ordinal());
    this.adrEmergencyExtension = rs.getInt(ColumnPosition.ADR_EMRG_EXTNO.ordinal());
    this.adrFrgAdrtB = trimToNull(rs.getString(ColumnPosition.ADR_FRG_ADRT_B.ordinal()));
    this.adrGovernmentEntityCd = rs.getShort(ColumnPosition.ADR_GVR_ENTC.ordinal());
    this.adrMessageNumber = rs.getLong(ColumnPosition.ADR_MSG_TEL_NO.ordinal());
    this.adrMessageExtension = rs.getInt(ColumnPosition.ADR_MSG_EXT_NO.ordinal());
    this.adrHeaderAddress = trimToNull(rs.getString(ColumnPosition.ADR_HEADER_ADR.ordinal()));
    this.adrPrimaryNumber = rs.getLong(ColumnPosition.ADR_PRM_TEL_NO.ordinal());
    this.adrPrimaryExtension = rs.getInt(ColumnPosition.ADR_PRM_EXT_NO.ordinal());
    this.adrState = rs.getShort(ColumnPosition.ADR_STATE_C.ordinal());
    this.adrStreetName = trimToNull(rs.getString(ColumnPosition.ADR_STREET_NM.ordinal()));
    this.adrStreetNumber = trimToNull(rs.getString(ColumnPosition.ADR_STREET_NO.ordinal()));
    this.adrZip = trimToNull(rs.getString(ColumnPosition.ADR_ZIP_NO.ordinal()));
    this.adrAddressDescription = trimToNull(rs.getString(ColumnPosition.ADR_ADDR_DSC.ordinal()));
    this.adrZip4 = rs.getShort(ColumnPosition.ADR_ZIP_SFX_NO.ordinal());
    this.adrPostDirCd = trimToNull(rs.getString(ColumnPosition.ADR_POSTDIR_CD.ordinal()));
    this.adrPreDirCd = trimToNull(rs.getString(ColumnPosition.ADR_PREDIR_CD.ordinal()));
    this.adrStreetSuffixCd = rs.getShort(ColumnPosition.ADR_ST_SFX_C.ordinal());
    this.adrUnitDesignationCd = rs.getShort(ColumnPosition.ADR_UNT_DSGC.ordinal());
    this.adrUnitNumber = trimToNull(rs.getString(ColumnPosition.ADR_UNIT_NO.ordinal()));
    this.adrLastUpdatedTime = rs.getTimestamp(ColumnPosition.ADR_LST_UPD_TS.ordinal());

    return this;
  }

  @Override
  public Serializable getPrimaryKey() {
    return new VarargPrimaryKey(getCltId(), getAdrId());
  }

  public String getAdrId() {
    return adrId;
  }

  public void setAdrId(String adrId) {
    this.adrId = adrId;
  }

  public String getAdrCity() {
    return adrCity;
  }

  public void setAdrCity(String adrCity) {
    this.adrCity = adrCity;
  }

  public Long getAdrEmergencyNumber() {
    return adrEmergencyNumber;
  }

  public void setAdrEmergencyNumber(Long adrEmergencyNumber) {
    this.adrEmergencyNumber = adrEmergencyNumber;
  }

  public Integer getAdrEmergencyExtension() {
    return adrEmergencyExtension;
  }

  public void setAdrEmergencyExtension(Integer adrEmergencyExtension) {
    this.adrEmergencyExtension = adrEmergencyExtension;
  }

  public String getAdrFrgAdrtB() {
    return adrFrgAdrtB;
  }

  public void setAdrFrgAdrtB(String adrFrgAdrtB) {
    this.adrFrgAdrtB = adrFrgAdrtB;
  }

  public Short getAdrGovernmentEntityCd() {
    return adrGovernmentEntityCd;
  }

  public void setAdrGovernmentEntityCd(Short adrGovernmentEntityCd) {
    this.adrGovernmentEntityCd = adrGovernmentEntityCd;
  }

  public Date getAdrLastUpdatedTime() {
    return freshDate(adrLastUpdatedTime);
  }

  public void setAdrLastUpdatedTime(Date adrLastUpdatedTime) {
    this.adrLastUpdatedTime = freshDate(adrLastUpdatedTime);
  }

  public Long getAdrMessageNumber() {
    return adrMessageNumber;
  }

  public void setAdrMessageNumber(Long adrMessageNumber) {
    this.adrMessageNumber = adrMessageNumber;
  }

  public Integer getAdrMessageExtension() {
    return adrMessageExtension;
  }

  public void setAdrMessageExtension(Integer adrMessageExtension) {
    this.adrMessageExtension = adrMessageExtension;
  }

  public String getAdrHeaderAddress() {
    return adrHeaderAddress;
  }

  public void setAdrHeaderAddress(String adrHeaderAddress) {
    this.adrHeaderAddress = adrHeaderAddress;
  }

  public Long getAdrPrimaryNumber() {
    return adrPrimaryNumber;
  }

  public void setAdrPrimaryNumber(Long adrPrimaryNumber) {
    this.adrPrimaryNumber = adrPrimaryNumber;
  }

  public Integer getAdrPrimaryExtension() {
    return adrPrimaryExtension;
  }

  public Short getAdrState() {
    return adrState;
  }

  public String getAdrStreetName() {
    return adrStreetName;
  }

  public String getAdrStreetNumber() {
    return adrStreetNumber;
  }

  public String getAdrZip() {
    return adrZip;
  }

  public String getAdrAddressDescription() {
    return adrAddressDescription;
  }

  public void setAdrAddressDescription(String adrAddressDescription) {
    this.adrAddressDescription = adrAddressDescription;
  }

  public Short getAdrZip4() {
    return adrZip4;
  }

  public String getAdrPostDirCd() {
    return adrPostDirCd;
  }

  public String getAdrPreDirCd() {
    return adrPreDirCd;
  }

  public Short getAdrStreetSuffixCd() {
    return adrStreetSuffixCd;
  }

  public Short getAdrUnitDesignationCd() {
    return adrUnitDesignationCd;
  }

  public String getAdrUnitNumber() {
    return adrUnitNumber;
  }

  public CmsReplicationOperation getAdrReplicationOperation() {
    return adrReplicationOperation;
  }

  public void setAdrReplicationOperation(CmsReplicationOperation adrReplicationOperation) {
    this.adrReplicationOperation = adrReplicationOperation;
  }

  public Date getAdrReplicationDate() {
    return freshDate(adrReplicationDate);
  }

  public void setAdrReplicationDate(Date adrReplicationDate) {
    this.adrReplicationDate = freshDate(adrReplicationDate);
  }

  public void setAdrPrimaryExtension(Integer adrPrimaryExtension) {
    this.adrPrimaryExtension = adrPrimaryExtension;
  }

  public void setAdrState(Short adrState) {
    this.adrState = adrState;
  }

  public void setAdrStreetName(String adrStreetName) {
    this.adrStreetName = adrStreetName;
  }

  public void setAdrStreetNumber(String adrStreetNumber) {
    this.adrStreetNumber = adrStreetNumber;
  }

  public void setAdrZip(String adrZip) {
    this.adrZip = adrZip;
  }

  public void setAdrZip4(Short adrZip4) {
    this.adrZip4 = adrZip4;
  }

  public void setAdrPostDirCd(String adrPostDirCd) {
    this.adrPostDirCd = adrPostDirCd;
  }

  public void setAdrPreDirCd(String adrPreDirCd) {
    this.adrPreDirCd = adrPreDirCd;
  }

  public void setAdrStreetSuffixCd(Short adrStreetSuffixCd) {
    this.adrStreetSuffixCd = adrStreetSuffixCd;
  }

  public void setAdrUnitDesignationCd(Short adrUnitDesignationCd) {
    this.adrUnitDesignationCd = adrUnitDesignationCd;
  }

  public void setAdrUnitNumber(String adrUnitNumber) {
    this.adrUnitNumber = adrUnitNumber;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((adrId == null) ? 0 : adrId.hashCode());
    return result;
  }

}
