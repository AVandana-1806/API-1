package gov.ca.cwds.rest.validation;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.util.ParticipantUtils;

/**
 * Victim Birth Validator is implemented to validate the Dob, or AgeUnit, AgeUnitCode is required
 * for the victim.
 * 
 * @author CWDS API team
 *
 */
public class ParticipantBirthValidator
    implements ConstraintValidator<ValidParticipantBirth, ScreeningToReferral> {

  @Override
  public void initialize(ValidParticipantBirth constraintAnnotation) {
    // nothing to initialize in this class
  }

  @Override
  public boolean isValid(ScreeningToReferral screening, ConstraintValidatorContext context) {
    boolean valid = true;
    Collection<Participant> paricipants = screening.getParticipants();
    if (!ParticipantUtils.getVictims(screening.getParticipants()).isEmpty()) {
      for (Participant victim : paricipants) {
        if (!hasValidBirthDateOrAge(victim, context)) {
          valid = false;
        }
      }
    }
    if (CollectionUtils.isNotEmpty(paricipants)) {
      for (Participant participant : paricipants) {
        if (!hasParticipantValidDOB(screening, participant, context)) {
          valid = false;
          break;
        }
      }
    }
    return valid;
  }

  /**
   * 
   * @param victim - victim
   * @param context - context
   * @return the boolean
   */
  private boolean hasValidBirthDateOrAge(Participant victim, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(victim.getDateOfBirth())
        && StringUtils.isBlank(victim.getApproximateAge())) {
      String message = "Victim's should have either of the value DOB or approximateAge";
      buildMessage(context, message);
      return false;
    }
    if (StringUtils.isNotBlank(victim.getApproximateAge())
        && !victim.getApproximateAge().contains("0")
        && StringUtils.isBlank(victim.getApproximateAgeUnits())) {
      String message = "Victim's approximateAgeUnits must be set if approximateAge is set";
      buildMessage(context, message);
      return false;
    }
    return true;
  }

  /**
   * Checking for the participant date of birth should not be in the future.
   */
  private boolean hasParticipantValidDOB(ScreeningToReferral screening, Participant participant,
      ConstraintValidatorContext context) {
    if (StringUtils.isNotBlank(screening.getStartedAt())
        && StringUtils.isNotBlank(participant.getDateOfBirth())) {
      String date = StartDateTimeValidator.extractStartDate(screening.getStartedAt(), null);
      LocalDate startDate = LocalDate.parse(date);
      LocalDate participantDob = LocalDate.parse(participant.getDateOfBirth());
      String message = "Participant date of birth is in future";
      buildMessage(context, message);
      return participantDob.isAfter(startDate);
    }
    return true;
  }

  private void buildMessage(ConstraintValidatorContext context, String message) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
  }

}
