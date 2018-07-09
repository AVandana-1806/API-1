package gov.ca.cwds.rest.business;

/**
 * A common interface for validating legacy business rules.
 *
 * Validators are responsible for checking if rule is implemented correctly.
 * 
 * @author CWDS API Team
 */
@FunctionalInterface
public interface RuleValidator {

  /**
   * @return the valid boolean
   */
  boolean isValid();

}
