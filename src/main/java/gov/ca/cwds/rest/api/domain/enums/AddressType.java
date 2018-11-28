package gov.ca.cwds.rest.api.domain.enums;

public enum AddressType {
  HOME(32, "Residence"),
  DAY_CARE(28, "Day Care"),
  COMMON(6273, "Common"),
  HOMELESS(29, "Homeless"),
  OTHER(6272, "Other"),
  PENAL_INSTITUTION(30, "Penal Institution"),
  PERMANENT_MAILING_ADDRESS(31, "Permanent Mailing Address"),
  RESIDENCE_2(6271, "Residence 2"),
  WORK(27, "Work");

  private final int code;
  private final String value;

  AddressType(int code, String value){
    this.code = code;
    this.value = value;
  }

  public short getCode() {
    return (short)code;
  }

  public String getValue() {
    return value;
  }

}
