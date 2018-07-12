package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an address
 *
 * @author Intake Team 4
 */
@JsonSnakeCase
@ApiModel("nsAddresses")
public class AddressIntakeApi extends DomainObject implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = false, value = "Id", example = "28")
  private String id;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Id", example = "ABC0123456")
  @Size(max = CMS_ID_LEN)
  private String legacyId;

  @JsonProperty("legacy_source_table")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Source Table",
      example = "ADDRS_T")
  private String legacySourceTable;

  @JsonProperty("type")
  @ApiModelProperty(example = "Home")
  private String type;

  @JsonProperty("street_address")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  private String streetAddress;

  @JsonProperty("city")
  @ApiModelProperty(value = "City", example = "Sacramento")
  private String city;

  @JsonProperty("state")
  @ApiModelProperty(value = "State Code", example = "CA")
  private String state;

  @JsonProperty("zip")
  @ApiModelProperty(value = "Zip", example = "95835")
  private String zip;

  @JsonProperty("legacy_descriptor")
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private LegacyDescriptor legacyDescriptor;

  /**
   * Default Constructor.
   */
  public AddressIntakeApi() {
    // no-opt
  }

  /**
   * Constructor
   *
   * @param legacySourceTable - the legacy source table name
   * @param addressId - legacy Id
   * @param streetAddress - street address
   * @param city - city
   * @param state - state
   * @param zip - zip code
   * @param type the address type
   * @param legacyDescriptor - legacyDescriptor
   */
  @SuppressWarnings("squid:S00107")
  public AddressIntakeApi(String legacySourceTable, String addressId, String streetAddress,
      String city, String state, String zip, String type, LegacyDescriptor legacyDescriptor) {
    super();
    this.legacySourceTable = legacySourceTable;
    this.legacyId = addressId;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.type = type;
    this.legacyDescriptor = legacyDescriptor;
  }

  /**
   * Construct from persistence class
   *
   * @param address persistence level address object
   */
  public AddressIntakeApi(gov.ca.cwds.data.persistence.ns.Addresses address) {
    this.id = address.getId();
    this.legacySourceTable = address.getLegacySourceTable();
    this.legacyId = address.getLegacyId();
    this.streetAddress = address.getStreetAddress();
    this.city = address.getCity();
    this.state = address.getState();
    this.zip = address.getZip();
    this.type = address.getType();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return address type
   */
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  /**
   * @return legacy source table
   */
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  /**
   * @param legacySourceTable - the legacy source table name
   */
  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  /**
   * @return addressId
   */
  public String getLegacyId() {
    return legacyId;
  }

  /**
   * @param addressId - the legacy Id
   */
  public void setLegacyId(String addressId) {
    this.legacyId = addressId;
  }

  /**
   * @return - CMS record description
   */
  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * @param legacyDescriptor - CMS record description
   */
  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  /**
   * @return street address
   */
  public String getStreetAddress() {
    return streetAddress;
  }

  /**
   * @return city
   */
  public String getCity() {
    return city;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @return zip code
   */
  public String getZip() {
    return zip;
  }

  /**
   * {@inheritDoc}
   *
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
