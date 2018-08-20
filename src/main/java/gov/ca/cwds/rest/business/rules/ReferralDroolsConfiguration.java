package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.drools.DroolsConfiguration;

public class ReferralDroolsConfiguration extends DroolsConfiguration<Referral> {

  public static final ReferralDroolsConfiguration DATA_PROCESSING_INSTANCE =
      new ReferralDroolsConfiguration("referrals-session", "referrals-data-processing-agenda",
          "referral-rules");

  private ReferralDroolsConfiguration(String rulesSession, String rulesAgenda,
      String kieContainerId) {
    super(rulesSession, rulesAgenda, kieContainerId);
  }

}
