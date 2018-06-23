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
  private String relatedLastName = "Briggs";
  private String relatednameSuffix = "Jr";
  private String relatedGender = "M";
  private String relatedDateOfBirth = "2000-10-01";
  private String relatedDateOfDeath = "2001-10-01";
  private String absentParentCode = "N";
  private String sameHomeCode = "Y";
  private String relationship = "Brother";
  private String relationshipToPerson = "Sister";
  private String relationshipContext = "step";
  private String newTableName = "REFERL_T";
  private String newId = "3456789ABC";
  private String newRelatedFirstName = "new first name";
  private String newRelatedLastName = "new last name";
  private String newRelatedNameSuffix = "Sr";
  private String newRelationship = "new Brother";
  private String newRelationshipToPerson = "new Sister";
  private String newRelationshipContext = "maternal";

  private CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor(id, "111-222-333-4444", tableName, "Client");


  @Test
  public void testDomainConstructorSuccess() throws Exception {
    RelationshipTo relationshipTo = new RelationshipTo(relatedFirstName, relatedLastName,
        relatednameSuffix, relatedGender, relatedDateOfBirth, relatedDateOfDeath, absentParentCode,
        sameHomeCode, relationship, relationshipContext, relationshipToPerson, id);

    assertThat(relatedFirstName, is(equalTo(relationshipTo.getRelatedFirstName())));
    assertThat(relatedLastName, is(equalTo(relationshipTo.getRelatedLastName())));
    assertThat(relationshipContext, is(equalTo(relationshipTo.getRelationshipContext())));
    assertThat(relationship, is(equalTo(relationshipTo.getRelationshipToPerson())));
    assertThat(relationshipToPerson, is(equalTo(relationshipTo.getRelatedPersonRelationship())));
    assertThat(cmsRecordDescriptor.getId(),
        is(equalTo(relationshipTo.getCmsRecordDescriptor().getId())));
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
    RelationshipTo relationshipTo =
        new RelationshipToEntityBuilder().setId(newId).setRelatedFirstName(newRelatedFirstName)
            .setRelatedLastName(newRelatedLastName).setRelatedNameSuffix(newRelatedNameSuffix)
            .setRelationship(newRelationship).setRelationshipContext(newRelationshipContext)
            .setRelationshipToPerson(newRelationshipToPerson).setTableName(newTableName).build();

    assertThat(relationshipTo.getRelatedFirstName(), is(equalTo(newRelatedFirstName)));
    assertThat(relationshipTo.getRelatedLastName(), is(equalTo(newRelatedLastName)));
    assertThat(relationshipTo.getRelatedNameSuffix(), is(equalTo(newRelatedNameSuffix)));
    assertThat(relationshipTo.getRelationshipToPerson(), is(equalTo(newRelationship)));
    assertThat(relationshipTo.getRelationshipContext(), is(equalTo(newRelationshipContext)));
    assertThat(relationshipTo.getRelatedPersonRelationship(), is(equalTo(newRelationshipToPerson)));

  }

}
