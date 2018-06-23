package gov.ca.cwds.rest.api.domain.investigation;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.investigation.RelationshipEntityBuilder;
import gov.ca.cwds.fixture.investigation.RelationshipToEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class RelationshipTest {

  private ObjectMapper MAPPER = new ObjectMapper();
  private String tableName = "CLIENT_T";
  private String id = "1234567ABC";

  private CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor(id, "111-222-333-4444", tableName, "Client");
  private String firstName = "Jackson";
  private String middleName = "R";
  private String lastName = "Greene";
  private String dateOfBirth = "2000-10-01";
  private String suffixTitle = "";
  private String gender = "M";
  private String dateOfDeath = "2001-10-01";
  private Boolean sensitive = Boolean.FALSE;
  private Boolean sealed = Boolean.FALSE;
  private Set<RelationshipTo> relationshipsTo = new HashSet<>();
  private RelationshipTo relationshipTo = new RelationshipToEntityBuilder().build();

  @Before
  public void setup() {
    relationshipsTo.add(relationshipTo);

  }

  @Test
  public void shouldCreateObjectWithDefaultConstructor() {
    Relationship relationship = new Relationship();
    assertNotNull(relationship);
  }

  @Test
  public void testDomainConstructorSuccess() throws Exception {
    Relationship relationship = new Relationship(id, dateOfBirth, firstName, middleName, lastName,
        suffixTitle, gender, dateOfDeath, sensitive, sealed, cmsRecordDescriptor, relationshipsTo);

    assertThat(id, is(equalTo(relationship.getId())));
    assertThat(firstName, is(equalTo(relationship.getFirstName())));
    assertThat(middleName, is(equalTo(relationship.getMiddleName())));
    assertThat(lastName, is(equalTo(relationship.getLastName())));
    assertThat(suffixTitle, is(equalTo(relationship.getSuffixName())));
    assertThat(gender, is(equalTo(relationship.getGender())));
    assertThat(dateOfDeath, is(equalTo(relationship.getDateOfDeath())));
    assertThat(sensitive, is(equalTo(relationship.getSensitive())));
    assertThat(sealed, is(equalTo(relationship.getSealed())));
    assertThat(cmsRecordDescriptor, is(equalTo(relationship.getCmsRecordDescriptor())));
    assertThat(relationshipsTo, is(equalTo(relationship.getRelatedTo())));
  }

  @Test
  public void testPersistentClientConstructor() {
    Client client = new ClientEntityBuilder().build();
    Relationship relationship = new Relationship(client, relationshipsTo);
    assertThat(relationship.getId(), is(equalTo(client.getId())));
    assertThat(relationship.getDateOfBirth(),
        is(equalTo(DomainChef.cookDate(client.getBirthDate()))));
    assertThat(relationship.getFirstName(), is(equalTo(client.getFirstName())));
    assertThat(relationship.getMiddleName(), is(equalTo(client.getMiddleName())));
    assertThat(relationship.getLastName(), is(equalTo(client.getLastName())));
    assertThat(relationship.getGender(), is(equalTo(client.getGender())));
    assertThat(relationship.getDateOfDeath(),
        is(equalTo(DomainChef.cookDate(client.getDeathDate()))));
    assertThat(relationship.getRelatedTo(), is(equalTo(relationshipsTo)));

  }

  @Test
  public void testSealedIsTrue() {
    Client client = new ClientEntityBuilder().setSensitivityIndicator("R").build();
    Relationship relationship = new Relationship(client, relationshipsTo);
    assertThat(relationship.getSealed(), is(equalTo(Boolean.TRUE)));
    assertThat(relationship.getSensitive(), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void tesetSensitiveIsTrue() {
    Client client = new ClientEntityBuilder().setSensitivityIndicator("S").build();
    Relationship relationship = new Relationship(client, relationshipsTo);
    assertThat(relationship.getSealed(), is(equalTo(Boolean.FALSE)));
    assertThat(relationship.getSensitive(), is(equalTo(Boolean.TRUE)));
  }

  @Test
  public void testDateOfBirthNull() {
    Client client = new ClientEntityBuilder().setBirthDate(null).build();
    Relationship relationship = new Relationship(client, relationshipsTo);
    assertThat(relationship.getDateOfBirth(), is(equalTo(null)));
  }

  @Test
  public void testDateOfDeathNull() {
    Client client = new ClientEntityBuilder().setDeathDate(null).build();
    Relationship relationship = new Relationship(client, relationshipsTo);
    assertThat(relationship.getDateOfDeath(), is(equalTo(null)));
  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    Relationship relationship = new RelationshipEntityBuilder().build();
    Relationship otherRelationship = new RelationshipEntityBuilder().build();
    assertEquals(relationship, otherRelationship);
  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    Relationship relationship = new RelationshipEntityBuilder().build();
    Relationship otherRelationship =
        new RelationshipEntityBuilder().setDateOfBirth("2001-10-30").build();
    assertThat(relationship, is(not(equals(otherRelationship))));
  }

  @Test
  public void shouldFindMultipleItemInHashSetWhenItemsHaveWithDifferentValue() {
    Relationship relationship = new RelationshipEntityBuilder().build();
    Relationship otherRelationship =
        new RelationshipEntityBuilder().setSensitive(Boolean.TRUE).build();
    Set<Relationship> items = new HashSet<>();
    items.add(relationship);
    items.add(otherRelationship);

    assertTrue(items.contains(relationship));
    assertTrue(items.contains(otherRelationship));
    assertEquals(2, items.size());

  }

  @Test
  public void shouldFindSingleItemInHashSetWhenMultipleItemsAddedWithSameValue() {
    Relationship relationship = new RelationshipEntityBuilder().build();
    Relationship otherRelationship = new RelationshipEntityBuilder().build();
    Set<Relationship> items = new HashSet<>();
    items.add(relationship);
    items.add(otherRelationship);

    assertTrue(items.contains(relationship));
    assertTrue(items.contains(otherRelationship));
    assertEquals(1, items.size());

  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Relationship.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  // @Test
  // @Ignore
  // public void testSerializedOutput()
  // throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
  // Relationship safetyAlerts = new RelationshipEntityBuilder().build();
  // final String expected =
  // MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(safetyAlerts);
  // System.out.println(expected);
  // }

  @Test
  public void deserializesFromJSON() throws Exception {
    Relationship relationship = new RelationshipEntityBuilder().build();
    assertThat(MAPPER.readValue(fixture("fixtures/domain/investigation/relationship/valid.json"),
        Relationship.class), is(equalTo(relationship)));
  }

}
