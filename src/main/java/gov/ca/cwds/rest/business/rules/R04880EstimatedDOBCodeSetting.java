package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.business.RuleAction;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CWDS API Team
 *
 * R - 04880 Estimated_DOB_Code Setting
 * <p>
 * Rule Text<br> If Age and Age Unit are entered, set the CLIENT.Estimated_DOB_Code to Y and set the
 * CLIENT.Birth_Date to <br> System_Date - Age (by Unit); Else, If Date of Birth is entered set the
 * Estimated_DOB_Code to N and set <br> Birth_Date to Date of Birth entered; Else If Date of Birth,
 * Age, and Age Unit are blank, <br> then set Estimated_DOB_Code and Birth_Date U.
 * <p>
 * Access Logic
 * <p>
 * If Age and Age Unit are entered <br> then (set CLIENT.Estimated_DOB_Code to = 'Y' and set the
 * CLIENT.Birth_Date to System_Date - Age (by Unit)); <br> Else, If Date of Birth is entered <br>
 * then (set the Estimated_DOB_Code to N and set Birth_Date to Date of Birth entered); <br> Else If
 * Date of Birth, Age, and Age Unit are blank, then (set Estimated_DOB_Code and Birth_Date U).
 */
public class R04880EstimatedDOBCodeSetting implements RuleAction {

  private static final Logger LOGGER = LoggerFactory.getLogger(R04880EstimatedDOBCodeSetting.class);

  private Client client;
  private ReferralClient referralClient;
  private Date dateStarted;

  public R04880EstimatedDOBCodeSetting(Client client, ReferralClient referralClient,
      String startedAt) {
    this.client = client;
    this.referralClient = referralClient;
    this.dateStarted = DomainChef.uncookDateString(startedAt);

  }

  @Override
  public void execute() {
    final Short ageNumber =
        referralClient.getAgeNumber() == null ? 0 : referralClient.getAgeNumber();
    final String agePeriodCode =
        referralClient.getAgePeriodCode() == null ? "" : referralClient.getAgePeriodCode();

    if (ageNumber != 0 && !agePeriodCode.isEmpty()) {
      client.setEstimatedDobCode(gov.ca.cwds.rest.api.domain.cms.Client.ESTIMATED_DOB_CODE_YES);
      client.setBirthDate(DomainChef.cookDate(estimateDOB(ageNumber, agePeriodCode)));
    } else {
      if (client.getBirthDate() != null && !client.getBirthDate().isEmpty()) {
        client.setEstimatedDobCode(gov.ca.cwds.rest.api.domain.cms.Client.ESTIMATED_DOB_CODE_NO);
      } else {
        client
            .setEstimatedDobCode(gov.ca.cwds.rest.api.domain.cms.Client.ESTIMATED_DOB_CODE_UNKNOWN);
      }
    }
  }

  private Date estimateDOB(Short ageNumber, String ageNumberUnit) {
    Date estimatedDOB = dateStarted;
    ChronoUnit chronoUnit = null;
    if ("Y".equalsIgnoreCase(ageNumberUnit)) {
      chronoUnit = ChronoUnit.YEARS;
    } else if ("M".equalsIgnoreCase(ageNumberUnit)) {
      chronoUnit = ChronoUnit.MONTHS;
    } else if ("W".equalsIgnoreCase(ageNumberUnit)) {
      chronoUnit = ChronoUnit.WEEKS;
    } else if ("D".equalsIgnoreCase(ageNumberUnit)) {
      chronoUnit = ChronoUnit.DAYS;
    }
    if (chronoUnit != null) {
      try {
        estimatedDOB = Date.from(
            Instant.ofEpochMilli(dateStarted.getTime()).atZone(ZoneId.systemDefault()).toLocalDate()
                .minus(ageNumber, chronoUnit).atStartOfDay(ZoneId.systemDefault()).toInstant());
      } catch (NumberFormatException e) {
        LOGGER.warn("Error estimating DOB. " + e.getMessage());
      }
    }
    return estimatedDOB;
  }

}
