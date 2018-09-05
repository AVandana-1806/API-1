package gov.ca.cwds.rest.business.rules;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.business.RuleAction;

/**
 * 
 * R - 00834 Age Unit Restriction
 * <p>
 * Rule Text<br>
 * If the CLIENT has a value for BIRTH DATE and Estimated_DOB_Code is N, <br>
 * then DOB, Age and Age Unit will be displayed and Age/ Age Unit may not be changed. Otherwise DOB
 * will be blank and Age and Age Unit will be displayed and editable. <br>
 * <p>
 * Access Logic
 * <p>
 * If CLIENT.BIRTH_DATE is not blank and CLIENT.ESTIMATED_DOB_CODE = 'N', <br>
 * then calculate and disable cboAgeUnit and txtAge, else enable cboAgeUnit and txtAge
 * <p>
 * 
 * @author CWDS API Team
 */
public class R00834AgeUnitRestriction implements RuleAction {

  private Client client;
  private ReferralClient referralClient;
  private String dateStarted;
  private String age;
  private String ageUnits;

  /**
   * @param client - client
   * @param referralClient - referralClient
   * @param dateStarted - dateStarted
   */
  public R00834AgeUnitRestriction(Client client, ReferralClient referralClient,
      String dateStarted) {
    this.client = client;
    this.referralClient = referralClient;
    this.dateStarted = dateStarted;
  }

  @Override
  public void execute() {
    if (client == null) {
      return;
    }
    Date startDate = DomainChef.uncookDateString(dateStarted);
    if (StringUtils.isNotBlank(client.getBirthDate())
        && client.getEstimatedDobCode().contains("N")) {
      Date birthDate = DomainChef.uncookDateString(client.getBirthDate());
      long noOfDays =
          TimeUnit.DAYS.convert(startDate.getTime() - birthDate.getTime(), TimeUnit.MILLISECONDS);
      calculateAgeByDob(noOfDays);
      referralClient.setAgeNumber(Short.valueOf(age));
      referralClient.setAgePeriodCode(ageUnits);
    }
  }

  private void calculateAgeByDob(long noOfDays) {
    if (noOfDays > 365) {
      age = Long.toString(noOfDays / 365);
      ageUnits = CalendarEnum.YEARS.getName();
    } else if (noOfDays > 30) {
      age = Long.toString(noOfDays / 30);
      ageUnits = CalendarEnum.MONTHS.getName();
    } else if (noOfDays > 7) {
      age = Long.toString(noOfDays / 7);
      ageUnits = CalendarEnum.WEEKS.getName();
    } else if (noOfDays >= 0) {
      age = Long.toString(noOfDays);
      ageUnits = CalendarEnum.DAYS.getName();
    }
  }

}
