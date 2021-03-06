package gov.ca.cwds.es.live;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.Id;

@SuppressWarnings({"squid:S2095", "findbugs:SE_TRANSIENT_FIELD_NOT_RESTORED", "serial",
    "squid:S2160", "squid:S1206"})
public class ClientAddressReference extends ClientReference {

  private static final long serialVersionUID = 1L;

  protected enum ColumnPosition {
    START, CLT_IDENTIFIER, CLA_IDENTIFIER, CLA_LST_UPD_ID, CLA_LST_UPD_TS, CLA_ADDR_TPC, CLA_BK_INMT_ID, CLA_EFF_END_DT, CLA_EFF_STRTDT, CLA_FKADDRS_T, CLA_FKCLIENT_T, CLA_FKREFERL_T, CLA_HOMLES_IND, CLA_IBMSNAP_LOGMARKER, CLA_IBMSNAP_OPERATION
  }

  @Id
  @Column(name = "CLA_IDENTIFIER")
  protected String claId;

  @Override
  public ClientAddressReference read(ResultSet rs) throws SQLException {
    super.read(rs);
    this.claId = trimToNull(rs.getString(ColumnPosition.CLA_IDENTIFIER.ordinal()));
    return this;
  }

  @Override
  public Serializable getPrimaryKey() {
    return null;
  }

  public String getClaId() {
    return claId;
  }

  public void setClaId(String claId) {
    this.claId = claId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    return prime * result + ((getClaId() == null) ? 0 : getClaId().hashCode());
  }

}
