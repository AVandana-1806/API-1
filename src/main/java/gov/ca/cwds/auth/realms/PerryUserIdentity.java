package gov.ca.cwds.auth.realms;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.security.realm.PerryAccount;

/**
 * Custom implementation of additional security attributes in JWT token.
 * 
 * @author CWDS API Team
 */
public class PerryUserIdentity extends PerryAccount {

  private static final String[] EXCLUDED_FIELDS =
      new String[] {"identifier", "lastUpdatedId", "lastUpdatedTime"};

  @JsonProperty
  private String staffId;

  @Override
  public String getStaffId() {
    return staffId;
  }

  @Override
  public void setStaffId(String staffId) {
    this.staffId = staffId;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(17, 37, this, false, PerryUserIdentity.class,
        EXCLUDED_FIELDS);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false, PerryUserIdentity.class,
        EXCLUDED_FIELDS);
  }

}
