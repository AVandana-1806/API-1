package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"alerts", "alert_information"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class SafetyAlerts extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("alerts")
  @ApiModelProperty(required = false, readOnly = false, value = "Safety Alert Type")
  private Set<Short> alerts;

  @JsonProperty("alert_information")
  @ApiModelProperty(required = false, readOnly = false, value = "Alert Information")
  private String alertInformation;

  /**
   * 
   * 
   */
  public SafetyAlerts() {
    super();
  }

  /**
   * @param alerts - set of safety alert types
   * @param alertInformation - alert information
   */
  public SafetyAlerts(Set<Short> alerts, String alertInformation) {
    this.alerts = alerts;
    this.alertInformation = alertInformation;
  }

  /**
   * @return - set of safety alert types
   */
  public Set<Short> getAlerts() {
    return alerts;
  }

  /**
   * @param alerts - set of safety alert types
   */
  public void setAlerts(Set<Short> alerts) {
    this.alerts = alerts;
  }

  /**
   * @return - alert information
   */
  public String getAlertInformation() {
    return alertInformation;
  }

  /**
   * @param alertInformation - alert information
   */
  public void setAlertInformation(String alertInformation) {
    this.alertInformation = alertInformation;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}

