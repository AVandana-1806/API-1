package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import gov.ca.cwds.rest.validation.ValidZipCode;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an address.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@ApiModel("nsAddress")
public class Address extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("legacy_source_table")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Source Table",
      example = "ADDRS_T")
  private String legacySourceTable;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Id", example = "ABC1234567")
  @Size(max = CMS_ID_LEN)
  private String legacyId;

  @JsonProperty("street_address")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String streetAddress;

  @JsonProperty("city")
  @ApiModelProperty(value = "City", example = "Springfield")
  @Size(max = 50)
  private String city;

  @JsonProperty("state")
  @ApiModelProperty(value = "State Code", example = "1828", allowableValues = "$ID:STATE_C")
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.STATE_CODE,
      ignoreable = true, ignoredValue = 0)
  private Integer state;

  @JsonProperty("zip")
  @ApiModelProperty(value = "Zip", example = "95757")
  @ValidZipCode
  private String zip;

  @JsonProperty("type")
  @ApiModelProperty(example = "32", allowableValues = "$ID:ADDR_TPC")
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.ADDRESS_TYPE,
      ignoreable = true, ignoredValue = 0)
  private Integer type;

  @ApiModelProperty(required = true, readOnly = false)
  @JsonProperty("legacy_descriptor")
  @Valid
  private LegacyDescriptor legacyDescriptor;

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
  @JsonCreator
  public Address(@JsonProperty("legacy_source_table") String legacySourceTable,
      @JsonProperty("legacy_id") String addressId,
      @JsonProperty("street_address") String streetAddress, @JsonProperty("city") String city,
      @JsonProperty("state") Integer state, @JsonProperty("zip") String zip,
      @JsonProperty("type") Integer type,
      @JsonProperty("legacy_descriptor") LegacyDescriptor legacyDescriptor) {
    super();
    this.legacySourceTable = legacySourceTable;
    this.legacyId = addressId;
    this.streetAddress = streetAddress == null ? "" : streetAddress;
    this.city = city;
    this.state = state == null ? 0 : state;
    this.zip = zip;
    this.type = type == null ? 0 : type;
    this.legacyDescriptor = legacyDescriptor;
  }

  /**
   * Construct from persistence class
   * 
   * @param address persistence level address object
   */
  public Address(gov.ca.cwds.data.persistence.ns.Address address) {
    this.legacySourceTable = "";
    this.legacyId = "";
    this.streetAddress = address.getStreetAddress();
    this.city = address.getCity();
    this.state = address.getState() != null ? Integer.valueOf(address.getState()) : 0;
    this.zip = address.getZip();
    this.type = address.getType() != null ? Integer.valueOf(address.getType()) : 0;
    this.legacyDescriptor = new LegacyDescriptor();
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
    if (legacyDescriptor == null) {
      legacyDescriptor = new LegacyDescriptor();
    }
    return this.legacyDescriptor;
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
  public Integer getState() {
    return state;
  }

  /**
   * @return zip code
   */
  public String getZip() {
    return zip;
  }

  /**
   * @return address type
   */
  public Integer getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
