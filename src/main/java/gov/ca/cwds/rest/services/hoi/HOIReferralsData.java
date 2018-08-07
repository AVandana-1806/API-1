package gov.ca.cwds.rest.services.hoi;

import java.util.Collection;
import java.util.Map;

import gov.ca.cwds.data.persistence.cms.Referral;

class HOIReferralsData {

  private Collection<String> referralIds;

  private Map<String, Boolean> referralsSelfReportedIndicators;

  // key: ReferralId, value: Referral
  private Map<String, Referral> referrals;

  HOIReferralsData() {
    // no-op
  }

  Collection<String> getReferralIds() {
    return referralIds;
  }

  void setReferralIds(Collection<String> referralIds) {
    this.referralIds = referralIds;
  }

  Map<String, Boolean> getReferralsSelfReportedIndicators() {
    return referralsSelfReportedIndicators;
  }

  void setReferralsSelfReportedIndicators(Map<String, Boolean> referralsSelfReportedIndicators) {
    this.referralsSelfReportedIndicators = referralsSelfReportedIndicators;
  }

  Map<String, Referral> getReferrals() {
    return referrals;
  }

  void setReferrals(Map<String, Referral> referrals) {
    this.referrals = referrals;
  }

}
