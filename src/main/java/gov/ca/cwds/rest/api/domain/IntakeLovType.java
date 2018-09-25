package gov.ca.cwds.rest.api.domain;

/**
 * @author CWDS API Team
 */
public enum IntakeLovType {

  LANGUAGE("language"),

  ADDRESS_COUNTY("address_county"),

  ADDRESS_TYPE("address_type"),

  US_STATE("us_state");

  private final String value;

  private IntakeLovType(String value) {
    this.value = value;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }

}
