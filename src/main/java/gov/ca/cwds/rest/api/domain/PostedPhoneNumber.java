package gov.ca.cwds.rest.api.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * {@link Response} adding an id to the {@link Address}
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@SuppressWarnings({"fb-contrib:COM_COPIED_OVERRIDDEN_METHOD"})
public class PostedPhoneNumber extends PhoneNumber {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor
   * 
   * @param id The id
   * @param number - phone number
   * @param type - phone number type
   */
  public PostedPhoneNumber(long id, String number, String type) {
    super(id, number, type);
    this.id = id;
  }

  /**
   * Constructor
   * 
   * @param phoneNumber The persisted phoneNumber
   */
  public PostedPhoneNumber(gov.ca.cwds.data.persistence.ns.PhoneNumber phoneNumber) {
    super(phoneNumber);
    assert phoneNumber.getId() != null;
    this.id = phoneNumber.getId();
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
