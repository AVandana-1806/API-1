package gov.ca.cwds.rest.api.domain.investigation;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.util.CmsRecordUtils;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a ClientRelationship
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"related_person_first_name", "related_person_last_name",
    "related_person_name_suffix", "related_person_gender", "related_person_date_of_birth",
    "related_person_date_of_death", "relationship_start_date", "relationship_end_date",
    "absent_parent_code", "same_home_code", "relationship_context", "indexed_person_relationship",
    "related_person_relationship", "legacy_description"})
public final class RelationshipTo implements Serializable {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("related_person_first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "first name", example = "jane")
  @Size(min = 1, max = 20)
  private String relatedFirstName;

  @JsonProperty("related_person_last_name")
  @NotBlank
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "last name", example = "sufer")
  private String relatedLastName;

  @JsonProperty("related_person_name_suffix")
  @Size(min = 1, max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "name suffix", example = "Jr")
  private String relatedNameSuffix;

  @JsonProperty("related_person_gender")
  @NotNull
  @Size(max = 1)
  @ApiModelProperty(required = true, readOnly = false, value = "Gender Code", example = "M")
  @OneOf(value = {"M", "F", "I", "U"}, ignoreCase = false, ignoreWhitespace = true)
  private String relatedGender;

  @JsonProperty("related_person_date_of_birth")
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT,
      required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "date of birth",
      example = "1999-10-01")
  private String relatedDateOfBirth;

  @JsonProperty("related_person_date_of_death")
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT,
      required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "date of death",
      example = "2000-10-01")
  private String relatedDateOfDeath;

  @JsonProperty("relationship_start_date")
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT,
      required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "relationship start date",
      example = "2000-10-01")
  private String relationshipStartDate;

  @JsonProperty("relationship_end_date")
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT,
      required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "relationship end date",
      example = "2001-10-01")
  private String relationshipEndDate;

  @JsonProperty("absent_parent_code")
  @Size(min = 1, max = 1)
  @ApiModelProperty(required = true, readOnly = false, value = "absent parent code")
  @OneOf(value = {"Y", "N"}, ignoreCase = false, ignoreWhitespace = true)
  private String absentParentCode;

  @JsonProperty("same_home_code")
  @Size(min = 1, max = 1)
  @ApiModelProperty(required = true, readOnly = false, value = "same home code", example = "Y")
  @OneOf(value = {"Y", "N", "U"}, ignoreCase = false, ignoreWhitespace = true)
  private String sameHomeCode;

  @JsonProperty("indexed_person_relationship")
  @ApiModelProperty(required = true, readOnly = false, value = "relationship to the person",
      example = "Sister")
  private String relationshipToPerson;

  @JsonProperty("relationship_context")
  @ApiModelProperty(required = true, readOnly = false, value = "context of the relationship",
      example = "Paternal")
  private String relationshipContext;

  @JsonProperty("related_person_relationship")
  @ApiModelProperty(required = true, readOnly = false, value = "persons relationship to",
      example = "Brother")
  private String relatedPersonRelationship;

  @JsonProperty("legacy_descriptor")
  private CmsRecordDescriptor cmsRecordDescriptor;

  /**
   * empty constructor
   */
  public RelationshipTo() {
    super();
  }

  /**
   * @param relatedFirstName - related person's first name
   * @param relatedLastName - related person's last name
   * @param relatedNameSuffix - name suffix of related person
   * @param relatedGender - gender of related person
   * @param relatedDateOfBirth - birth date of related person
   * @param relatedDateOfDeath - death date of related person
   * @param relationshipStartDate - relationship start date
   * @param relationshipEndDate - relationship end date
   * @param absentParentCode - absent parent code
   * @param sameHomeCode - same home code
   * @param relationshipToPerson - relation of owning person
   * @param relationshipContext - context information
   * @param relatedPersonRelationship - relation to owning person
   * @param cmsRecordDescriptor - The record descriptor containing meta data about legacy
   *        information
   */
  public RelationshipTo(String relatedFirstName, String relatedLastName, String relatedNameSuffix,
      String relatedGender,
      @Date(format = "yyyy-MM-dd", required = false) String relatedDateOfBirth,
      @Date(format = "yyyy-MM-dd", required = false) String relatedDateOfDeath,
      @Date(format = "yyyy-MM-dd", required = false) String relationshipStartDate,
      @Date(format = "yyyy-MM-dd", required = false) String relationshipEndDate,
      String absentParentCode, String sameHomeCode, String relationshipToPerson,
      String relationshipContext, String relatedPersonRelationship,
      CmsRecordDescriptor cmsRecordDescriptor) {
    super();
    this.relatedFirstName = relatedFirstName;
    this.relatedLastName = relatedLastName;
    this.relatedNameSuffix = relatedNameSuffix;
    this.relatedGender = relatedGender;
    this.relatedDateOfBirth = relatedDateOfBirth;
    this.relatedDateOfDeath = relatedDateOfDeath;
    this.relationshipStartDate = relationshipStartDate;
    this.relationshipEndDate = relationshipEndDate;
    this.absentParentCode = absentParentCode;
    this.sameHomeCode = sameHomeCode;
    this.relationshipToPerson = relationshipToPerson;
    this.relationshipContext = relationshipContext;
    this.relatedPersonRelationship = relatedPersonRelationship;
    this.cmsRecordDescriptor = cmsRecordDescriptor;
  }

  /**
   * @param relatedFirstName - related persons first name
   * @param relatedLastName - related persons last name
   * @param relatedNameSuffix - related persons name suffix
   * @param relatedGender - gender of related person
   * @param relatedDateOfBirth - birth date of related person
   * @param relatedDateOfDeath - death date of related person
   * @param relationshipStartDate - relationship start date
   * @param relationshipEndDate - relationship end date
   * @param absentParentCode - absent parent code
   * @param sameHomeCode - same home code
   * @param relationshipToPerson - relation of owning person
   * @param relationshipContext - context information
   * @param relatedPersonRelationship - relation to owning person
   * @param clientId - The Client this relationship pertains too
   */
  public RelationshipTo(String relatedFirstName, String relatedLastName, String relatedNameSuffix,
      String relatedGender,
      @Date(format = "yyyy-MM-dd", required = false) String relatedDateOfBirth,
      @Date(format = "yyyy-MM-dd", required = false) String relatedDateOfDeath,
      @Date(format = "yyyy-MM-dd", required = false) String relationshipStartDate,
      @Date(format = "yyyy-MM-dd", required = false) String relationshipEndDate,
      String absentParentCode, String sameHomeCode, String relationshipToPerson,
      String relationshipContext, String relatedPersonRelationship, String clientId) {
    this(relatedFirstName, relatedLastName, relatedNameSuffix, relatedGender, relatedDateOfBirth,
        relatedDateOfDeath, relationshipStartDate, relationshipEndDate, absentParentCode,
        sameHomeCode, relationshipToPerson, relationshipContext, relatedPersonRelationship,
        CmsRecordUtils.createLegacyDescriptor(clientId, LegacyTable.CLIENT_RELATIONSHIP));
  }

  /**
   * constructing RelationshipTo object
   * 
   * @param clientRelationship - client relationship object
   * @param client - client object
   */
  public RelationshipTo(ClientRelationship clientRelationship, Client client) {
    this.relatedFirstName = client.getFirstName();
    this.relatedLastName = client.getLastName();
    this.relatedNameSuffix = client.getNameSuffix();
    this.relatedGender = client.getGender();
    this.relatedDateOfBirth = DomainChef.cookDate(client.getBirthDate());
    this.relatedDateOfDeath = DomainChef.cookDate(client.getDeathDate());
    this.relationshipStartDate = DomainChef.cookDate(clientRelationship.getStartDate());
    this.relationshipEndDate = DomainChef.cookDate(clientRelationship.getEndDate());
    this.relationshipToPerson = String.valueOf(clientRelationship.getClientRelationshipType());
    relationshipContext = " ";
    relatedPersonRelationship = " ";
    this.absentParentCode = clientRelationship.getAbsentParentCode();
    this.sameHomeCode = clientRelationship.getSameHomeCode();
    this.cmsRecordDescriptor = CmsRecordUtils.createLegacyDescriptor(clientRelationship.getId(),
        LegacyTable.CLIENT_RELATIONSHIP);
  }

  /**
   * @return - related first name
   */
  public String getRelatedFirstName() {
    return relatedFirstName;
  }

  /**
   * @return - related name suffix
   */
  public String getRelatedNameSuffix() {
    return relatedNameSuffix;
  }

  /**
   * @return - related last name
   */
  public String getRelatedLastName() {
    return relatedLastName;
  }


  /**
   * @return - relationship to person
   */
  public String getRelationshipToPerson() {
    return relationshipToPerson;
  }


  /**
   * @return - relationship context
   */
  public String getRelationshipContext() {
    return relationshipContext;
  }


  /**
   * @return - related person relationship
   */
  public String getRelatedPersonRelationship() {
    return relatedPersonRelationship;
  }


  /**
   * @return - CMS record description
   */
  public CmsRecordDescriptor getCmsRecordDescriptor() {
    return cmsRecordDescriptor;
  }



  /**
   * @return the relatedGender
   */
  public String getRelatedGender() {
    return relatedGender;
  }

  /**
   * @return the relatedDateOfBirth
   */
  public String getrelatedDateOfBirth() {
    return relatedDateOfBirth;
  }

  /**
   * @return the relatedDateOfDeath
   */
  public String getrelatedDateOfDeath() {
    return relatedDateOfDeath;
  }


  /**
   * @return the absentParentCode
   */
  public String getAbsentParentCode() {
    return absentParentCode;
  }

  /**
   * @return the sameHomeCode
   */
  public String getSameHomeCode() {
    return sameHomeCode;
  }


  /**
   * @return the relationshipStartDate
   */
  public String getRelationshipStartDate() {
    return relationshipStartDate;
  }

  /**
   * @return the relationshipEndDate
   */
  public String getRelationshipEndDate() {
    return relationshipEndDate;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
