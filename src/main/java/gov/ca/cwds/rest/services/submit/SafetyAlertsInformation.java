package gov.ca.cwds.rest.services.submit;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.Screening;

/**
 * @author CWDS API Team
 *
 */
public class SafetyAlertsInformation {

  /**
   * @param screening - screening
   * @return the safetyInformation
   */
  public String buildSafetyAlertsInfo(Screening screening) {
    String safetyInformation = null;
    StringBuilder stringBuilder = new StringBuilder();
    boolean hasSafetyAlerts = screening.getSafetyAlerts() != null && !screening.getSafetyAlerts().isEmpty();
    boolean hasSafetyInformation = StringUtils.isNotBlank(screening.getSafetyInformation());
    if ( hasSafetyAlerts && hasSafetyInformation) {
      screening.getSafetyAlerts().forEach(alert -> stringBuilder.append(alert).append(','));
      stringBuilder.append(screening.getSafetyInformation());
      return stringBuilder.toString();
    } else if ( !hasSafetyAlerts && hasSafetyInformation ) {
      return screening.getSafetyInformation();
    } else if (hasSafetyAlerts && ! hasSafetyInformation ) {
      screening.getSafetyAlerts().forEach(alert -> stringBuilder.append(alert).append(','));
      return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
    return safetyInformation;
  }
}


