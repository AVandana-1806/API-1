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
    if (screening.getSafetyAlerts() != null && !screening.getSafetyAlerts().isEmpty()
        && StringUtils.isNotBlank(screening.getSafetyInformation())) {
      screening.getSafetyAlerts().forEach(alert -> stringBuilder.append(alert).append(','));
      stringBuilder.append(screening.getSafetyInformation());
      return stringBuilder.toString();
    } else if (screening.getSafetyAlerts() == null && screening.getSafetyAlerts().isEmpty()
        && StringUtils.isNotBlank(screening.getSafetyInformation())) {
      return screening.getSafetyInformation();
    } else if (screening.getSafetyAlerts() != null && !screening.getSafetyAlerts().isEmpty()
        && StringUtils.isBlank(screening.getSafetyInformation())) {
      screening.getSafetyAlerts().forEach(alert -> stringBuilder.append(alert).append(','));
      return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
    return safetyInformation;
  }

}


