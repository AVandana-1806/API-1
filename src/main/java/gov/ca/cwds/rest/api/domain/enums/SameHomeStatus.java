package gov.ca.cwds.rest.api.domain.enums;

import gov.ca.cwds.data.legacy.cms.entity.enums.EntityEnum;
import java.util.Arrays;

/**
 * @author CWDS TPT-3 Team
 */
public enum SameHomeStatus implements EntityEnum<String> {
  Y("Y", Boolean.TRUE, "YES"),
  N("N", Boolean.FALSE, "NO"),
  U("U", null, "UNKNOWN");

  private final String code;
  private final String description;
  private final Boolean value;

  SameHomeStatus(String code, Boolean value, String description) {
    this.code = code;
    this.value = value;
    this.description = description;
  }

  public static SameHomeStatus fromCode(String code) {
    return Arrays.asList(values()).stream().filter(t -> t.getCode().equals(code)).findAny()
        .orElse(null);
  }

  public static String fromValue(Boolean value) {
    if (value == null) {
      return U.getCode();
    }

    return value ? Y.getCode() : N.getCode();
  }

  public static Boolean toValue(String code) {
    SameHomeStatus status = Arrays.asList(values()).stream().filter(t -> t.getCode().equals(code))
        .findAny()
        .orElse(null);
    return status == null ? null : status.value;
  }

  @Override
  public String getDescription() {
    return description;
  }

  public String getCode() {
    return this.code;
  }

  public Boolean getValue() {
    return this.value;
  }

}
