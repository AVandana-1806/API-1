package gov.ca.cwds.fixture.investigation;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.SafetyAlerts;

@SuppressWarnings("javadoc")
public class SafetyAlertsEntityBuilder {

  private Set<Short> alerts = new HashSet<>();
  private String alertInformation = "information about the safety alert on this referral";

  public SafetyAlerts build() {
    alerts.add((short) 6401);
    alerts.add((short) 6402);

    return new SafetyAlerts(alerts, alertInformation);
  }

  public SafetyAlertsEntityBuilder setAlerts(Set<Short> alerts) {
    this.alerts = alerts;
    return this;
  }

  public Set<Short> getAlerts() {
    return alerts;
  }

  public SafetyAlertsEntityBuilder setAlertInformation(String alertInformation) {
    this.alertInformation = alertInformation;
    return this;
  }

  public String getAlertInformation() {
    return alertInformation;
  }
}
