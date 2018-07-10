package gov.ca.cwds.rest.api.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * {@link Response} adding an id to the {@link Address}.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@SuppressWarnings({"fb-contrib:COM_COPIED_OVERRIDDEN_METHOD"})
public class PostedAddress extends Address {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private long id;

  /**
   * Constructor
   * 
   * @param id The id
   * @param legacySourceTable - legacy source table name
   * @param legacyId - legacy Id
   * @param streetAddress The street address
   * @param city The city
   * @param state The state
   * @param zip The zip
   * @param type - address type
   * @param legacyDescriptor - legacyDescriptor
   */
  public PostedAddress(long id, String legacySourceTable, String legacyId, String streetAddress,
      String city, Integer state, String zip, Integer type, LegacyDescriptor legacyDescriptor) {
    super(legacySourceTable, legacyId, streetAddress, city, state, zip, type, legacyDescriptor);
    this.id = id;
  }

  /**
   * Copy constructor
   * 
   * @param address The persisted address
   */
  public PostedAddress(gov.ca.cwds.data.persistence.ns.Address address) {
    super(address);
    assert address.getId() != null;
    this.id = address.getId();
  }

  /**
   * Copy constructor
   * 
   * @param addr The persisted address
   */
  public PostedAddress(gov.ca.cwds.data.persistence.ns.Addresses addr) {
    super(new gov.ca.cwds.data.persistence.ns.Address(Long.valueOf(addr.getId()),
        addr.getStreetAddress(), addr.getCity(), "1828", addr.getZip(), addr.getType()));
    assert addr.getId() != null;
    this.id = Long.parseLong(addr.getId());
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
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
