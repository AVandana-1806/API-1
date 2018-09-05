package gov.ca.cwds.rest.business.rules;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.util.ParticipantUtils;
import gov.ca.cwds.rest.validation.VictimAgeRestriction;

/**
 * @author CWDS API team
 *
 */
public class R00786VictimAgeRestriction
    implements ConstraintValidator<VictimAgeRestriction, ScreeningToReferral> {

  /**
   * 18 years
   */
  public static final int MAX_VICTIM_AGE_YEARS = 18;

  /*
   * (non-Javadoc)
   * 
   * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
   */
  @Override
  public void initialize(VictimAgeRestriction constraintAnnotation) {
    // No initialization currently needed apparently.
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
   * javax.validation.ConstraintValidatorContext)
   */
  @Override
  public boolean isValid(ScreeningToReferral screening, ConstraintValidatorContext context) {
    boolean valid = true;
    Collection<Participant> victims = ParticipantUtils.getVictims(screening.getParticipants());

    if (!victims.isEmpty()) {
      // Referral receive timestamp
      String referralReceiveTimestampStr =
          DomainChef.cookISO8601Timestamp(RequestExecutionContext.instance().getRequestStartTime());
      DateTime referralReceiveTimestamp =
          new DateTime(DomainChef.uncookDateString(referralReceiveTimestampStr));

      // Referral receive date - yyyy-mm-dd
      String referralReceiveDateStr = DomainChef.cookDate(referralReceiveTimestamp.toDate());
      DateTime referralReceiveDate =
          new DateTime(DomainChef.uncookDateString(referralReceiveDateStr));

      // Subtract MAX_VICTIM_AGE_YEARS from referral receive date
      DateTime victimOverAgeDate = referralReceiveDate.minusYears(MAX_VICTIM_AGE_YEARS);

      for (Participant victim : victims) {
        if (isVictimOverAge(victim, victimOverAgeDate)) {
          valid = false;
          break;
        }
      }
    }

    return valid;
  }

  private boolean isVictimOverAge(Participant victim, DateTime victimOverAgeDate) {
    boolean overage = false;
    String dobStr = victim.getDateOfBirth();

    if (StringUtils.isNotBlank(dobStr)) {
      DateTime dob = new DateTime(DomainChef.uncookDateString(dobStr));

      if (dob.isBefore(victimOverAgeDate)) {
        overage = true;
      }
    } else if (StringUtils.isNotBlank(victim.getApproximateAge())
        && StringUtils.isNotBlank(victim.getApproximateAgeUnits())) {
      int age = Integer.parseInt(victim.getApproximateAge());
      String ageUnits = victim.getApproximateAgeUnits();
      if (CalendarEnum.YEARS.getName().equalsIgnoreCase(ageUnits)) {
        return age > MAX_VICTIM_AGE_YEARS;
      } else if (CalendarEnum.MONTHS.getName().equalsIgnoreCase(ageUnits)) {
        return age / 12 > MAX_VICTIM_AGE_YEARS;
      } else if (CalendarEnum.WEEKS.getName().equalsIgnoreCase(ageUnits)) {
        return age / 54 > MAX_VICTIM_AGE_YEARS;
      } else if (CalendarEnum.DAYS.getName().equalsIgnoreCase(ageUnits)) {
        return age / 365 > MAX_VICTIM_AGE_YEARS;
      }
    }
    return overage;
  }
}
