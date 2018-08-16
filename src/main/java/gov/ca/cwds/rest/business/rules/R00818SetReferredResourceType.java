package gov.ca.cwds.rest.business.rules;

import static gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.EVALUATE_OUT;

import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.business.RuleValidator;

/**
 * 
 * <p>
 * BUSINESS RULE: "R - 00818"
 * 
 * If the Response Decision is 'Evaluate Out', then Rationale is mandatory and Agency Referred to is
 * enabled and mandatory, else Agency Referred To is disabled.
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class R00818SetReferredResourceType implements RuleValidator {

  private ScreeningToReferral screeningToReferral;

  /**
   * @param screeningToReferral - screeningToReferral
   */
  public R00818SetReferredResourceType(ScreeningToReferral screeningToReferral) {
    super();
    this.screeningToReferral = screeningToReferral;
  }

  @Override
  public boolean isValid() {
    Boolean referredToResourceType = Boolean.FALSE;
    if (screeningToReferral.getResponseTime() == EVALUATE_OUT.getCode()) {
      referredToResourceType = Boolean.TRUE;
    }
    return referredToResourceType;
  }

}
