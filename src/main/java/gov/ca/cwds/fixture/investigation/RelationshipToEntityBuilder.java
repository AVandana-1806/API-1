package gov.ca.cwds.fixture.investigation;

import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;

@SuppressWarnings("javadoc")
public class RelationshipToEntityBuilder {
  protected String tableName = "CLIENT_T";
  protected String id = "2345678ABC";
  protected String relatedFirstName = "Steve";
  protected String relatedMiddleName = "James";
  protected String relatedLastName = "Briggs";
  protected String relatedNameSuffix = "Jr";
  protected String relatedGenderCode = "I";
  protected String relationship = "Brother";
  protected String relationshipToPerson = "Sister";
  protected String relationshipContext = "step";
  protected String relatedDateOfBirth = "2000-10-01";
  protected String relatedDateOfDeath = "2001-10-01";
  protected String relationshipStartDate = "2000-10-01";
  protected String relationshipEndDate = "2001-10-01";
  protected String absentParentCode = "N";
  protected String sameHomeCode = "U";
  protected Boolean relatedPersonSensitive = Boolean.FALSE;
  protected Boolean relatedPersonSealed = Boolean.FALSE;

  protected CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor(id, "111-222-333-4444", tableName, "Client");

  public RelationshipTo build() {
    return new RelationshipTo(relatedFirstName, relatedMiddleName, relatedLastName,
        relatedNameSuffix, relatedGenderCode, relatedDateOfBirth, relatedDateOfDeath,
        relationshipStartDate, relationshipEndDate, absentParentCode, sameHomeCode, relationship,
        relationshipContext, relationshipToPerson, relatedPersonSensitive, relatedPersonSealed,
        cmsRecordDescriptor);
  }

  public RelationshipToEntityBuilder setTableName(String tableName) {
    this.tableName = tableName;
    return this;
  }

  public RelationshipToEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public RelationshipToEntityBuilder setRelatedFirstName(String relatedFirstName) {
    this.relatedFirstName = relatedFirstName;
    return this;
  }

  public RelationshipToEntityBuilder setRelatedMiddleName(String relatedMiddleName) {
    this.relatedMiddleName = relatedMiddleName;
    return this;
  }

  public RelationshipToEntityBuilder setRelatedLastName(String relatedLastName) {
    this.relatedLastName = relatedLastName;
    return this;
  }

  public RelationshipToEntityBuilder setRelatedNameSuffix(String relatedNameSuffix) {
    this.relatedNameSuffix = relatedNameSuffix;
    return this;
  }


  public RelationshipToEntityBuilder setRelatedGenderCode(String relatedGenderCode) {
    this.relatedGenderCode = relatedGenderCode;
    return this;
  }

  public RelationshipToEntityBuilder setRelationship(String relationship) {
    this.relationship = relationship;
    return this;
  }

  public RelationshipToEntityBuilder setRelationshipToPerson(String relationshipToPerson) {
    this.relationshipToPerson = relationshipToPerson;
    return this;
  }


  public RelationshipToEntityBuilder setCmsRecordDescriptor(
      CmsRecordDescriptor cmsRecordDescriptor) {
    this.cmsRecordDescriptor = cmsRecordDescriptor;
    return this;
  }

  public RelationshipToEntityBuilder setRelationshipContext(String relationshipContext) {
    this.relationshipContext = relationshipContext;
    return this;
  }

  public RelationshipToEntityBuilder setAbsentParentCode(String absentParentCode) {
    this.absentParentCode = absentParentCode;
    return this;
  }

  public RelationshipToEntityBuilder setSameHomeCode(String sameHomeCode) {
    this.sameHomeCode = sameHomeCode;
    return this;
  }

  public RelationshipToEntityBuilder setRelatedDateOfBirth(String relatedDateOfBirth) {
    this.relatedDateOfBirth = relatedDateOfBirth;
    return this;
  }

  public RelationshipToEntityBuilder setRelatedDateOfDeath(String relatedDateOfDeath) {
    this.relatedDateOfDeath = relatedDateOfDeath;
    return this;
  }

  public RelationshipToEntityBuilder setRelationshipStartDate(String relationshipStartDate) {
    this.relationshipStartDate = relationshipStartDate;
    return this;
  }

  public RelationshipToEntityBuilder setRelationshipEndDate(String relationshipEndDate) {
    this.relationshipEndDate = relationshipEndDate;
    return this;
  }

  public RelationshipToEntityBuilder setRelatedPersonSensitive(Boolean relatedPersonSensitive) {
    this.relatedPersonSensitive = relatedPersonSensitive;
    return this;
  }

  public RelationshipToEntityBuilder setRelatedPersonSealed(Boolean relatedPersonSealed) {
    this.relatedPersonSealed = relatedPersonSealed;
    return this;
  }



}
