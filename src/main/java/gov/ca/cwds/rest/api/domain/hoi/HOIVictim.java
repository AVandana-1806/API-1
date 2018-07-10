package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;

/**
 * Victim (child) person.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"findbugs:EQ_DOESNT_OVERRIDE_EQUALS"})
public class HOIVictim extends HOIPerson {

  private static final long serialVersionUID = 1L;

  @JsonProperty("limited_access_code")
  private LimitedAccessType limitedAccessType;

  /**
   * No-argument constructor
   */
  public HOIVictim() {
    super();
  }

  /**
   * @param id - primary key
   * @param firstName - first name
   * @param lastName - last name
   * @param nameSuffix - name suffix
   * @param legacyDescriptor - legacy descriptor
   */
  public HOIVictim(String id, String firstName, String lastName, String nameSuffix,
      LegacyDescriptor legacyDescriptor) {
    super(id, firstName, lastName, nameSuffix, legacyDescriptor);
  }

  /**
   * @return the limitedAccessType
   */
  public LimitedAccessType getLimitedAccessType() {
    return limitedAccessType;
  }

  /**
   * @param limitedAccessType - limitedAccessType
   */
  public void setLimitedAccessType(LimitedAccessType limitedAccessType) {
    this.limitedAccessType = limitedAccessType;
  }

}
