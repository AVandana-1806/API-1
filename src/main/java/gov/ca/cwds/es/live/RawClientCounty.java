package gov.ca.cwds.es.live;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Column;

import org.hibernate.annotations.Type;

import gov.ca.cwds.data.persistence.cms.VarargPrimaryKey;

@SuppressWarnings({"squid:S2095", "findbugs:SE_TRANSIENT_FIELD_NOT_RESTORED", "serial",
    "squid:S2160"})
public class RawClientCounty extends ClientReference
    implements LiveElasticJdbcReader<RawClientCounty> {

  private static final long serialVersionUID = 1L;

  protected enum ColumnPosition {
    START, CLT_IDENTIFIER, CLC_GVR_ENTC, CLC_LST_UPD_TS, CLC_LST_UPD_OP, CLC_CNTY_RULE
  }

  // ================================
  // CLIENT_CNTY: (client county)
  // ================================

  @Type(type = "short")
  @Column(name = "CLC_GVR_ENTC")
  protected Short clientCounty;

  @Column(name = "CLC_CNTY_RULE")
  protected String clientCountyRule;

  @Override
  public RawClientCounty read(ResultSet rs) throws SQLException {
    super.read(rs);
    this.clientCounty = rs.getShort(ColumnPosition.CLC_GVR_ENTC.ordinal());
    this.clientCountyRule = trimToNull(rs.getString(ColumnPosition.CLC_CNTY_RULE.ordinal()));
    return this;
  }

  @Override
  public Serializable getPrimaryKey() {
    return new VarargPrimaryKey(getCltId(), getClientCounty().toString());
  }

  public Short getClientCounty() {
    return clientCounty;
  }

  public void setClientCounty(Short clientCounty) {
    this.clientCounty = clientCounty;
  }

  public String getClientCountyRule() {
    return clientCountyRule;
  }

  public void setClientCountyRule(String clientCountyRule) {
    this.clientCountyRule = clientCountyRule;
  }

}
