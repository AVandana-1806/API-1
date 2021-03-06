package gov.ca.cwds.es.live;

import static gov.ca.cwds.rest.api.domain.DomainChef.freshDate;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Type;

import gov.ca.cwds.data.persistence.cms.VarargPrimaryKey;

@SuppressWarnings({"squid:S2095", "findbugs:SE_TRANSIENT_FIELD_NOT_RESTORED", "serial",
    "squid:S2160"})
public class RawCsec extends ClientReference implements LiveElasticJdbcReader<RawCsec> {

  private static final long serialVersionUID = 1L;

  protected enum ColumnPosition {
    START, CLT_IDENTIFIER, CSH_THIRD_ID, CSH_CSEC_TPC, CSH_START_DT, CSH_END_DT, CSH_LST_UPD_ID, CSH_LST_UPD_TS, CSH_IBMSNAP_OPERATION, CSH_IBMSNAP_LOGMARKER
  }

  // ====================================
  // CSECHIST: (CSEC history)
  // =====================================

  @Column(name = "CSH_THIRD_ID")
  private String csecId;

  @Column(name = "CSH_CSEC_TPC")
  @Type(type = "short")
  private Short csecCodeId;

  @Column(name = "CSH_START_DT")
  @Type(type = "date")
  private Date csecStartDate;

  @Column(name = "CSH_END_DT")
  @Type(type = "date")
  private Date csecEndDate;

  @Column(name = "CSH_LST_UPD_ID")
  private String csecLastUpdatedId;

  @Column(name = "CSH_LST_UPD_TS")
  @Type(type = "timestamp")
  private Date csecLastUpdatedTimestamp;

  @Enumerated(EnumType.STRING)
  @Column(name = "CSH_IBMSNAP_OPERATION", updatable = false)
  private CmsReplicationOperation csecLastUpdatedOperation;

  @Column(name = "CSH_IBMSNAP_LOGMARKER")
  @Type(type = "timestamp")
  private Date csecReplicationTimestamp;

  @Override
  public RawCsec read(ResultSet rs) throws SQLException {
    super.read(rs);

    csecId = rs.getString(ColumnPosition.CSH_THIRD_ID.ordinal());
    csecCodeId = rs.getShort(ColumnPosition.CSH_CSEC_TPC.ordinal());
    csecStartDate = rs.getDate(ColumnPosition.CSH_START_DT.ordinal());
    csecEndDate = rs.getDate(ColumnPosition.CSH_END_DT.ordinal());
    csecLastUpdatedId = rs.getString(ColumnPosition.CSH_LST_UPD_ID.ordinal());
    csecLastUpdatedTimestamp = rs.getTimestamp(ColumnPosition.CSH_LST_UPD_TS.ordinal());

    return this;
  }

  @Override
  public Serializable getPrimaryKey() {
    return new VarargPrimaryKey(getCltId(), csecId);
  }

  public String getCsecId() {
    return csecId;
  }

  public void setCsecId(String csecId) {
    this.csecId = csecId;
  }

  public Short getCsecCodeId() {
    return csecCodeId;
  }

  public void setCsecCodeId(Short csecCodeId) {
    this.csecCodeId = csecCodeId;
  }

  public Date getCsecStartDate() {
    return freshDate(csecStartDate);
  }

  public void setCsecStartDate(Date csecStartDate) {
    this.csecStartDate = freshDate(csecStartDate);
  }

  public Date getCsecEndDate() {
    return freshDate(csecEndDate);
  }

  public void setCsecEndDate(Date csecEndDate) {
    this.csecEndDate = freshDate(csecEndDate);
  }

  public String getCsecLastUpdatedId() {
    return csecLastUpdatedId;
  }

  public void setCsecLastUpdatedId(String csecLastUpdatedId) {
    this.csecLastUpdatedId = csecLastUpdatedId;
  }

  public Date getCsecLastUpdatedTimestamp() {
    return freshDate(csecLastUpdatedTimestamp);
  }

  public void setCsecLastUpdatedTimestamp(Date csecLastUpdatedTimestamp) {
    this.csecLastUpdatedTimestamp = freshDate(csecLastUpdatedTimestamp);
  }

  public CmsReplicationOperation getCsecLastUpdatedOperation() {
    return csecLastUpdatedOperation;
  }

  public void setCsecLastUpdatedOperation(CmsReplicationOperation csecLastUpdatedOperation) {
    this.csecLastUpdatedOperation = csecLastUpdatedOperation;
  }

  public Date getCsecReplicationTimestamp() {
    return freshDate(csecReplicationTimestamp);
  }

  public void setCsecReplicationTimestamp(Date csecReplicationTimestamp) {
    this.csecReplicationTimestamp = freshDate(csecReplicationTimestamp);
  }

}
