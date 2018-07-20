package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ClientRelationshipResourceBuilder;
import gov.ca.cwds.fixture.investigation.RelationshipToEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class RelationshipToTest {

  private String tableName = "CLIENT_T";
  private String id = "2345678ABC";
  private String relatedFirstName = "Steve";
  private String relatedMiddleName = "James";
  private String relatedLastName = "Briggs";
  private String relatednameSuffix = "Jr";
  private String relatedGender = "M";
  private String relatedDateOfBirth = "2000-10-01";
  private Short relatedAge = 17;
  private String relatedAgeUnit = "Y";
  private String relatedDateOfDeath = "2001-10-01";
  private String relationshipStartDate = "2000-10-01";
  private String relationshipEndDate = "2001-10-01";
  private String absentParentCode = "N";
  private String sameHomeCode = "Y";
  private String relationship = "Brother";
  private String relationshipToPerson = "Sister";
  private String relationshipContext = "step";
  private String newTableName = "REFERL_T";
  private String newId = "3456789ABC";
  private String newRelatedFirstName = "new first name";
  private String newRelatedMiddleName = "new middle name";
  private String newRelatedLastName = "new last name";
  private String newRelatedNameSuffix = "Sr";
  private String newRelationship = "new Brother";
  private String newRelationshipToPerson = "new Sister";
  private String newRelationshipContext = "maternal";
  private String newRelatedGender = "F";
  private String newAbsentParentCode = "Y";
  private String newSameHomeCode = "N";
  private String newRelatedDateOfBirth = "2010-01-31";
  private String newRelatedDateOfDeath = "";
  private String newRelationshipStartDate = "2010-01-31";
  private String newRelationshipEndDate = "2017-01-30";
  private Boolean relatedPersonSensitive = false;
  private Boolean relatedPersonSealed = false;
  private Boolean newRelatedPersonSensitive = false;
  private Boolean newRelatedPersonSealed = false;

  private CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor(id, "111-222-333-4444", tableName, "Client");
  private CmsRecordDescriptor newCmsRecordDescriptor =
      new CmsRecordDescriptor(id, "222-333-444-5555", tableName, "Client");


  @Test
  public void testDomainConstructorSuccess() throws Exception {
    RelationshipTo relationshipTo = new RelationshipTo(relatedFirstName, relatedMiddleName,
        relatedLastName, relatednameSuffix, relatedGender, relatedDateOfBirth, relatedDateOfDeath,
        relationshipStartDate, relationshipEndDate, absentParentCode, sameHomeCode, relationship,
        relationshipContext, relationshipToPerson, id, relatedPersonSensitive, relatedPersonSealed);

    assertThat(relatedFirstName, is(equalTo(relationshipTo.getRelatedFirstName())));
    assertThat(relatedLastName, is(equalTo(relationshipTo.getRelatedLastName())));
    assertThat(relationshipContext, is(equalTo(relationshipTo.getRelationshipContext())));
    assertThat(relationship, is(equalTo(relationshipTo.getRelationshipToPerson())));
    assertThat(relationshipToPerson, is(equalTo(relationshipTo.getRelatedPersonRelationship())));
    assertThat(cmsRecordDescriptor.getId(),
        is(equalTo(relationshipTo.getCmsRecordDescriptor().getId())));
    assertThat(relatedGender, is(equalTo(relationshipTo.getRelatedGender())));
    assertThat(relatedDateOfBirth, is(equalTo(relationshipTo.getrelatedDateOfBirth())));
    assertThat(relatedDateOfDeath, is(equalTo(relationshipTo.getrelatedDateOfDeath())));
    assertThat(absentParentCode, is(equalTo(relationshipTo.getAbsentParentCode())));
    assertThat(sameHomeCode, is(equalTo(relationshipTo.getSameHomeCode())));
  }

  @Test
  public void testConstrutorUsingClientSuccess() {
    gov.ca.cwds.rest.api.domain.cms.ClientRelationship domainClientRelationship =
        new ClientRelationshipResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship =
        new gov.ca.cwds.data.persistence.cms.ClientRelationship("1234567ABC",
            domainClientRelationship, "0XA", new Date());

    Client client = new ClientEntityBuilder().build();

    RelationshipTo relationshipTo = new RelationshipTo(clientRelationship, client);

    assertThat(relationshipTo.getRelatedFirstName(), is(equalTo(client.getFirstName())));
    assertThat(relationshipTo.getRelatedLastName(), is(equalTo(client.getLastName())));
    assertThat(relationshipTo.getRelatedNameSuffix(), is(equalTo(client.getNameSuffix())));
    assertThat(relationshipTo.getRelationshipToPerson(),
        is(equalTo(clientRelationship.getClientRelationshipType().toString())));
  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    RelationshipTo relationshipTo = new RelationshipToEntityBuilder().build();
    RelationshipTo otherRelationshipTo = new RelationshipToEntityBuilder().build();
    assertEquals(relationshipTo, otherRelationshipTo);
  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    RelationshipTo relationshipTo = new RelationshipToEntityBuilder().build();
    RelationshipTo otherRelationshipTo =
        new RelationshipToEntityBuilder().setRelationshipContext("paternal").build();
    assertThat(relationshipTo, is(not(equals(otherRelationshipTo))));
  }

  @Test
  public void shouldFindMultipleItemInHashSetWhenItemsHaveWithDifferentValue() {
    RelationshipTo relationshipTo = new RelationshipToEntityBuilder().build();
    RelationshipTo otherRelationshipTo =
        new RelationshipToEntityBuilder().setRelationshipContext("paternal").build();

    Set<RelationshipTo> items = new HashSet<>();
    items.add(relationshipTo);
    items.add(otherRelationshipTo);

    assertTrue(items.contains(relationshipTo));
    assertTrue(items.contains(otherRelationshipTo));
    assertEquals(2, items.size());
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(RelationshipTo.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void shouldSetValuesWithBuilderSetters() {
    RelationshipTo relationshipTo = new RelationshipToEntityBuilder().setId(newId)
        .setRelatedFirstName(newRelatedFirstName).setRelatedLastName(newRelatedLastName)
        .setRelatedMiddleName(newRelatedMiddleName).setRelatedNameSuffix(newRelatedNameSuffix)
        .setRelationship(newRelationship).setRelationshipContext(newRelationshipContext)
        .setRelationshipToPerson(newRelationshipToPerson).setTableName(newTableName)
        .setRelatedGenderCode(newRelatedGender).setCmsRecordDescriptor(newCmsRecordDescriptor)
        .setAbsentParentCode(newAbsentParentCode).setSameHomeCode(newSameHomeCode)
        .setRelatedDateOfBirth(newRelatedDateOfBirth).setRelatedDateOfDeath(newRelatedDateOfDeath)
        .setRelationshipStartDate(newRelationshipStartDate)
        .setRelationshipEndDate(newRelationshipEndDate)
        .setRelatedPersonSensitive(newRelatedPersonSensitive)
        .setRelatedPersonSealed(newRelatedPersonSealed).build();

    assertThat(relationshipTo.getRelatedFirstName(), is(equalTo(newRelatedFirstName)));
    assertThat(relationshipTo.getRelatedMiddleName(), is(equalTo(newRelatedMiddleName)));
    assertThat(relationshipTo.getRelatedLastName(), is(equalTo(newRelatedLastName)));
    assertThat(relationshipTo.getRelatedNameSuffix(), is(equalTo(newRelatedNameSuffix)));
    assertThat(relationshipTo.getRelationshipToPerson(), is(equalTo(newRelationship)));
    assertThat(relationshipTo.getRelationshipContext(), is(equalTo(newRelationshipContext)));
    assertThat(relationshipTo.getRelatedPersonRelationship(), is(equalTo(newRelationshipToPerson)));
    assertThat(relationshipTo.getRelatedGender(), is(equalTo(newRelatedGender)));
    assertThat(relationshipTo.getCmsRecordDescriptor(), is(equalTo(newCmsRecordDescriptor)));
    assertThat(relationshipTo.getAbsentParentCode(), is(equalTo(newAbsentParentCode)));
    assertThat(relationshipTo.getSameHomeCode(), is(equalTo(newSameHomeCode)));
    assertThat(relationshipTo.getrelatedDateOfBirth(), is(equalTo(newRelatedDateOfBirth)));
    assertThat(relationshipTo.getRelationshipStartDate(), is(equalTo(newRelationshipStartDate)));
    assertThat(relationshipTo.getRelationshipEndDate(), is(equalTo(newRelationshipEndDate)));
    assertThat(relationshipTo.getRelatedPersonSensitive(), is(equalTo(newRelatedPersonSensitive)));
    assertThat(relationshipTo.getRelatedPersonSealed(), is(equalTo(newRelatedPersonSealed)));
  }

}
