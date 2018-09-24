package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.drools.DroolsConfiguration;
import gov.ca.cwds.rest.api.domain.cms.Address;

/**
 * @author CWDS API Team
 *
 */
public class ReferralAddressDroolsConfiguration extends DroolsConfiguration<Address> {

  public static final ReferralAddressDroolsConfiguration DATA_PROCESSING_INSTANCE =
      new ReferralAddressDroolsConfiguration("referrals-address-session",
          "referrals-address-data-processing-agenda", "rules/referral-address");

  private ReferralAddressDroolsConfiguration(String rulesSession, String rulesAgenda,
      String kieContainerId) {
    super(rulesSession, rulesAgenda, kieContainerId);
  }

}
