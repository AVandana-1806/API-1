package gov.ca.cwds.data.access.dto;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Referral;

/**
 * @author CWDS API Team
 *
 */
public class ReferralEntityAwareDTO extends BaseEntityAwareDTO<Referral> {

  private Referral referral;
  private Address addresses;

  public Referral getReferral() {
    return referral;
  }

  public void setReferral(Referral referral) {
    this.referral = referral;
  }

  public Address getAddresses() {
    return addresses;
  }

  public void setAddresses(Address addresses) {
    this.addresses = addresses;
  }

}
