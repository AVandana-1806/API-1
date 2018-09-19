package gov.ca.cwds.rest.validation;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableList;

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
          break;
        }
      }
    }
    if (CollectionUtils.isNotEmpty(paricipants)) {
      for (Participant participant : paricipants) {
        if (!hasParticipantValidDOB(participant, context)) {
          valid = false;
          break;
        }
      }
    }
    return valid;
  }

  /**
   * Validates victim should have date of birth Or Approximate Age
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
   * Validates the participant date of birth should not be in the future.
   */
  private boolean hasParticipantValidDOB(Participant participant,
      ConstraintValidatorContext context) {
    ImmutableList.Builder<String> messages = new ImmutableList.Builder<>();
    if (StringUtils.isNotBlank(participant.getDateOfBirth())) {
      LocalDate todaysDate = LocalDate.now();
      LocalDate participantDob = LocalDate.parse(participant.getDateOfBirth());
      if (participantDob.isAfter(todaysDate)) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Date of Birth cannot be in the future")
            .addPropertyNode(participant.getFirstName()).addConstraintViolation();
        messages.add();
        return false;
      }
    }
    return true;
  }

  private void buildMessage(ConstraintValidatorContext context, String message) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
  }

}
