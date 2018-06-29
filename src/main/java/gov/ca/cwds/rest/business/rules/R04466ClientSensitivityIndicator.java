package gov.ca.cwds.rest.business.rules;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.business.RuleAction;

/**
 * Apply Doctool rule R - 04466: Set client sensitivity indicator
 * 
 * @author CWDS API Team
 */
public class R04466ClientSensitivityIndicator implements RuleAction {

  private CaseDao caseDao;
  private ReferralClientDao referralClientDao;
  private Client client;
  private LimitedAccessType screeningToReferralLimmitedAccessCode;

  /**
   * Constructor
   * 
   * @param client The client that must be updated
   * @param screeningToReferralLimmitedAccessCode The current limited access code requested with
   *        referral
   * @param caseDao Case DAO
   * @param referralClientDao Referral client DAO
   */
  public R04466ClientSensitivityIndicator(Client client,
      LimitedAccessType screeningToReferralLimmitedAccessCode, CaseDao caseDao,
      ReferralClientDao referralClientDao) {
    this.client = client;
    this.screeningToReferralLimmitedAccessCode = screeningToReferralLimmitedAccessCode;
    this.caseDao = caseDao;
    this.referralClientDao = referralClientDao;
  }

  @Override
  public void execute() {
    /*
     * If SOC158SealedClientIndicator is true the we can not change client's sensitivity
     */
    Boolean soc158SealedClientIndicator = client.getSoc158SealedClientIndicator();
    if (soc158SealedClientIndicator != null && soc158SealedClientIndicator) {
      return;
    }

    LimitedAccessType currentHighestLimitedAccessCode =
        screeningToReferralLimmitedAccessCode == null ? LimitedAccessType.NONE
            : screeningToReferralLimmitedAccessCode;

    /*
     * If we already have highest limited access code then use it otherwise check cases and
     * referrals
     */
    if (!LimitedAccessType.isHighestPriority(currentHighestLimitedAccessCode)) {
      String clientId = client.getExistingClientId();

      /*
       * Is it an existing?
       */
      if (StringUtils.isNotBlank(clientId)) {
        /*
         * Find highest limited access code from cases
         */
        currentHighestLimitedAccessCode = findHighestCaseLimitedAccessCode(clientId);

        /*
         * Find highest referral limited access code taking into account current highest limited
         * access code
         */
        currentHighestLimitedAccessCode =
            findHighestReferralLimitedAccessCode(clientId, currentHighestLimitedAccessCode);
      }
    }

    /*
     * Found highest limited access code, apply it to client
     */
    client.applySensitivityIndicator(currentHighestLimitedAccessCode);
  }

  private LimitedAccessType findHighestCaseLimitedAccessCode(String clientId) {
    LimitedAccessType currentHighestLimitedAccessCode = LimitedAccessType.NONE;

    CmsCase[] cmsCases = caseDao.findAllRelatedByVictimClientId(clientId);
    for (CmsCase cmsCase : cmsCases) {
      String limitedAccessCodeValue = cmsCase.getLimitedAccessCode();
      LimitedAccessType caseLimitedAccessCode =
          LimitedAccessType.getByValue(limitedAccessCodeValue);
      if (caseLimitedAccessCode == null) {
        caseLimitedAccessCode = LimitedAccessType.NONE;
      }

      if (caseLimitedAccessCode.getPriority() > currentHighestLimitedAccessCode.getPriority()) {
        currentHighestLimitedAccessCode = caseLimitedAccessCode;
      }

      if (LimitedAccessType.isHighestPriority(currentHighestLimitedAccessCode)) {
        break;
      }
    }

    return currentHighestLimitedAccessCode;
  }

  private LimitedAccessType findHighestReferralLimitedAccessCode(String clientId,
      LimitedAccessType highestLimitedAccessCode) {
    LimitedAccessType currentHighestLimitedAccessCode = highestLimitedAccessCode;

    /*
     * If current highest limited access code is the highest then return it otherwise check
     * referrals
     */
    if (!LimitedAccessType.isHighestPriority(currentHighestLimitedAccessCode)) {
      List<String> clientIds = new ArrayList<>();
      clientIds.add(clientId);
      ReferralClient[] referralClients = referralClientDao.findByClientIds(clientIds);

      for (ReferralClient referralClient : referralClients) {
        Referral referral = referralClient.getReferral();
        LimitedAccessType referralLimitedAccessCode =
            LimitedAccessType.getByValue(referral.getLimitedAccessCode());
        if (referralLimitedAccessCode == null) {
          referralLimitedAccessCode = LimitedAccessType.NONE;
        }

        if (referralLimitedAccessCode.getPriority() > currentHighestLimitedAccessCode
            .getPriority()) {
          currentHighestLimitedAccessCode = referralLimitedAccessCode;
        }

        if (LimitedAccessType.isHighestPriority(currentHighestLimitedAccessCode)) {
          break;
        }
      }
    }

    return currentHighestLimitedAccessCode;
  }
}
