package gov.ca.cwds.auth.realms;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.security.realm.PerryAccount;

/**
 * Custom implementation of additional security attributes in JWT token.
 * 
 * @author CWDS API Team
 */
public class PerryUserIdentity extends PerryAccount {

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

}
