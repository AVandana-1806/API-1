package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.business.RuleAction;

/**
 * CWDS API Team
 *
 * R - 04880 Estimated_DOB_Code Setting
 * <p>
 * Rule Text<br>
 * If Age and Age Unit are entered, set the CLIENT.Estimated_DOB_Code to Y and set the
 * CLIENT.Birth_Date to <br>
 * System_Date - Age (by Unit); Else, If Date of Birth is entered set the Estimated_DOB_Code to N
 * and set <br>
 * Birth_Date to Date of Birth entered; Else If Date of Birth, Age, and Age Unit are blank, <br>
 * then set Estimated_DOB_Code and Birth_Date U.
 * <p>
 * Access Logic
 * <p>
 * If Age and Age Unit are entered <br>
 * then (set CLIENT.Estimated_DOB_Code to = 'Y' and set the CLIENT.Birth_Date to System_Date - Age
 * (by Unit)); <br>
 * Else, If Date of Birth is entered <br>
 * then (set the Estimated_DOB_Code to N and set Birth_Date to Date of Birth entered); <br>
 * Else If Date of Birth, Age, and Age Unit are blank, then (set Estimated_DOB_Code and Birth_Date
 * U).
 */
public class R04880EstimatedDOBCodeSetting implements RuleAction {

  private Client client;
  private ReferralClient referralClient;

  public R04880EstimatedDOBCodeSetting(Client client, ReferralClient referralClient) {
    this.client = client;
    this.referralClient = referralClient;
  }

  @Override
  public void execute() {
    final Short ageNumber;
    if (referralClient.getAgeNumber()== null) {
      ageNumber = 0;
    } else {
      ageNumber = referralClient.getAgeNumber();
    }
    
    if (ageNumber != 0 && !referralClient.getAgePeriodCode().isEmpty()) {
      client.setEstimatedDobCode(gov.ca.cwds.rest.api.domain.cms.Client.ESTIMATED_DOB_CODE_YES);
    } else {
      if (client.getBirthDate() != null  && !client.getBirthDate().isEmpty()) {
        client.setEstimatedDobCode(gov.ca.cwds.rest.api.domain.cms.Client.ESTIMATED_DOB_CODE_NO);
      } else {
        client.setEstimatedDobCode(gov.ca.cwds.rest.api.domain.cms.Client.ESTIMATED_DOB_CODE_UNKNOWN);
      }
    }
  }
}
