package gov.ca.cwds.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author CWDS API Team
 *
 */
public class CountyValidator implements ConstraintValidator<ValidCounty, Short> {

  private static final Short STATE_OF_CALIFORNIA_SYSTEM_CODE = Short.valueOf("1126");

  @Override
  public void initialize(ValidCounty constraintAnnotation) {
    // nothing to initialize
  }

  /**
   *
   * R - 02366 County drop downs
   *
   * @param value Government Entity Type id
   * @param context ConstraintValidatorContext
   * @return validation result
   */
  @Override
  public boolean isValid(Short value, ConstraintValidatorContext context) {
    return !STATE_OF_CALIFORNIA_SYSTEM_CODE.equals(value);
  }
}
