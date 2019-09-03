package gov.ca.cwds.rest.api.domain.hoi;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import io.swagger.annotations.ApiModelProperty;

/**
 * Basic person.
 *
 * @author CWDS API Team
 */
public class HOIPerson extends ApiObjectIdentity implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(example = "111111")
  private String id;

  @JsonProperty("first_name")
  @ApiModelProperty(example = "joe")
  private String firstName;

  @JsonProperty("last_name")
  @ApiModelProperty(example = "west")
  private String lastName;

  @JsonProperty("name_suffix")
  @ApiModelProperty(example = "Jr.")
  private String nameSuffix;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

  /**
   * No-argument constructor
   */
  public HOIPerson() {
    // default constructor
  }

  /**
   * @param id - id
   * @param firstName - firstName
   * @param lastName - lastName
   * @param nameSuffix - name_suffix
   * @param legacyDescriptor - legacyDescriptor
   */
  public HOIPerson(String id, String firstName, String lastName, String nameSuffix,
      LegacyDescriptor legacyDescriptor) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.nameSuffix = nameSuffix;
    this.legacyDescriptor = legacyDescriptor;
  }

  /**
   * Construct from persistence class
   *
   * @param participantEntity persistence level participant object
   */
  public HOIPerson(ParticipantEntity participantEntity) {
    this.id = participantEntity.getId();
    this.firstName = participantEntity.getFirstName();
    this.lastName = participantEntity.getLastName();
    this.nameSuffix = participantEntity.getNameSuffix();
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getNameSuffix() {
    return nameSuffix;
  }

  public void setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
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
