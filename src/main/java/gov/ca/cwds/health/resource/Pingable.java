package gov.ca.cwds.health.resource;

/**
 * CARES health check interface.
 * 
 * <p>
 * "Pingable" means callable at any moment, though {@code Pingable} implementations are typically
 * scheduled for repeated calls.
 * </p>
 * 
 * @author CWDS API Team
 */
public interface Pingable {

  /**
   * Execute health check.
   * 
   * @return true = health check succeeded
   */
  boolean ping();

  /**
   * Get message (warnings, errors, or otherwise), if any.
   * 
   * @return optional error message
   */
  String getMessage();

}
