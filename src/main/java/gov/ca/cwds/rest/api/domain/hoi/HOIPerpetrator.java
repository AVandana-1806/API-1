package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;

/**
 * Perpetrator person.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"findbugs:EQ_DOESNT_OVERRIDE_EQUALS"})
public class HOIPerpetrator extends HOIPerson {

  private static final long serialVersionUID = 1L;

  @JsonProperty("limited_access_code")
  private LimitedAccessType limitedAccessType;

  /**
   * No-argument constructor
   */
  public HOIPerpetrator() {
    super();
  }

  /**
   * @param id - id
   * @param firstName - firstName
   * @param lastName - lastName
   * @param nameSuffix - nameSuffix
   * @param legacyDescriptor - legacyDescriptor
   */
  public HOIPerpetrator(String id, String firstName, String lastName, String nameSuffix,
      LegacyDescriptor legacyDescriptor) {
    super(id, firstName, lastName, nameSuffix, legacyDescriptor);
  }

  public LimitedAccessType getLimitedAccessType() {
    return limitedAccessType;
  }

  public void setLimitedAccessType(LimitedAccessType limitedAccessType) {
    this.limitedAccessType = limitedAccessType;
  }

}
