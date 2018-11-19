package gov.ca.cwds.data.es.transform;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.Id;

@SuppressWarnings({"squid:S1206"})
public class ClientAddressReference extends ClientReference {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "CLA_IDENTIFIER")
  protected String claId;

  @Override
  public ClientAddressReference read(ResultSet rs) throws SQLException {
    super.read(rs);
    this.claId = trimToEmpty(rs.getString("CLA_IDENTIFIER"));
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
