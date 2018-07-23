package gov.ca.cwds.rest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.RelationshipWrapper;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;

public class GenealogistTest {
  private static final String FATHER_SON = "211";
  private static final String SON_FATHER = "287";
  private static final String SON_MOTHER = "291";
  private static final String MONTHER_SON = "252";
  private static final String BROTHER_BROTHER = "179";
  private static final String SISTER_BROTHER = "276";
  private static final String BROTHER_SISTER = "182";

  private Genealogist genealogist;
  private ClientDao clientDao;

  private String sonClientId = "AXD19DS";
  private String dadClientId = "XZD876DS7";
  private String momClientId = "ZZYW3214D";
  private String sisterClientId = "ZZYW3214D";
  private String brotherClientId = "ZZYW3216D";

  private Client sonClient;
  private Client dadClient;
  private Client momClient;
  private Client sisterClient;
  private Client brotherClient;

  private List<RelationshipWrapper> relationships;
  private List<RelationshipWrapper> secondaryOnlyRelationships;

  @Before
  public void setup() {
    clientDao = mock(ClientDao.class);
    sonClient = new ClientEntityBuilder().setId("1").setCommonFirstName("Charlie")
        .setSensitivityIndicator("R").build();
    dadClient = new ClientEntityBuilder().setId("1").setCommonFirstName("Ralph")
        .setCommonLastName("Foo").setSensitivityIndicator("S").build();
    momClient = new ClientEntityBuilder().setId("2").setCommonFirstName("Rene")
        .setCommonLastName("Foo").build();
    sisterClient = new ClientEntityBuilder().setId("3").setCommonFirstName("Rebecca")
        .setCommonLastName("Foo").build();
    brotherClient = new ClientEntityBuilder().setId("4").setCommonFirstName("Ricky")
        .setCommonLastName("Foo").build();
    when(clientDao.find(sonClientId)).thenReturn(sonClient);
    when(clientDao.find(dadClientId)).thenReturn(dadClient);
    when(clientDao.find(momClientId)).thenReturn(momClient);
    when(clientDao.find(sisterClientId)).thenReturn(sisterClient);
    when(clientDao.find(brotherClientId)).thenReturn(brotherClient);

    RelationshipWrapper client1Mother = new RelationshipWrapper("123", sonClientId, momClientId,
        "Charlie", "", "Foo", "Rene", "", "Foo", MONTHER_SON, SON_MOTHER);
    RelationshipWrapper client1Father = new RelationshipWrapper("432", sonClientId, dadClientId,
        "Charlie", "", "Foo", "Ralph", "", "Foo", FATHER_SON, SON_FATHER);
    relationships = Arrays.asList(client1Mother, client1Father);

    RelationshipWrapper client1Brother = new RelationshipWrapper("123", brotherClientId,
        sonClientId, "Ricky", "", "Foo", "Charlie", "", "Foo", BROTHER_BROTHER, BROTHER_BROTHER);
    RelationshipWrapper client1Sister = new RelationshipWrapper("432", sisterClientId, sonClientId,
        "Rebecca", "", "Foo", "Charlie", "", "Foo", BROTHER_SISTER, SISTER_BROTHER);
    secondaryOnlyRelationships = Arrays.asList(client1Brother, client1Sister);

    genealogist = new Genealogist(clientDao);
  }

  @Test
  public void shouldReturnAnEmptyRelationshipIfClientIdIsNull() {
    Relationship foundRelations = genealogist.buildRelationships(relationships, null);
    assertEquals(foundRelations.getId(), null);
    assertEquals(foundRelations.getFirstName(), null);
    assertEquals(foundRelations.getLastName(), null);
  }

  @Test
  public void shouldReturnAnEmptyRelationshipIfRelationshipIsNull() {
    Relationship foundRelations = genealogist.buildRelationships(relationships, null);
    assertEquals(foundRelations.getId(), null);
    assertEquals(foundRelations.getFirstName(), null);
    assertEquals(foundRelations.getLastName(), null);
  }

  @Test
  public void givenAClientIdAndRelationshipWhenOnlyPrimaryRelationsExistsThenWeShouldGetRelationships() {
    Relationship relationship = genealogist.buildRelationships(relationships, sonClientId);
    ArrayList<RelationshipTo> orderedRelationships = convertToOrderedRelationships(relationship);

    assertEquals(dadClient.getCommonFirstName(), orderedRelationships.get(0).getRelatedFirstName());
    assertEquals(dadClient.getCommonLastName(), orderedRelationships.get(0).getRelatedLastName());
    assertEquals(SON_FATHER, orderedRelationships.get(0).getRelationshipToPerson());
    assertEquals(FATHER_SON, orderedRelationships.get(0).getRelatedPersonRelationship());

    assertEquals(momClient.getCommonFirstName(), orderedRelationships.get(1).getRelatedFirstName());
    assertEquals(momClient.getCommonLastName(), orderedRelationships.get(1).getRelatedLastName());
    assertEquals(SON_MOTHER, orderedRelationships.get(1).getRelationshipToPerson());
    assertEquals(MONTHER_SON, orderedRelationships.get(1).getRelatedPersonRelationship());
  }

  @Test
  public void givenAClientIdAndRelationshipWhenOnlySecondaryRelationsExistsThenWeShouldGetRelationships() {
    Relationship relationship =
        genealogist.buildRelationships(secondaryOnlyRelationships, sonClientId);
    ArrayList<RelationshipTo> orderedRelationships = convertToOrderedRelationships(relationship);

    assertEquals(brotherClient.getCommonFirstName(),
        orderedRelationships.get(1).getRelatedFirstName());
    assertEquals(brotherClient.getCommonLastName(),
        orderedRelationships.get(1).getRelatedLastName());
    assertEquals(BROTHER_BROTHER, orderedRelationships.get(1).getRelationshipToPerson());
    assertEquals(BROTHER_BROTHER, orderedRelationships.get(1).getRelatedPersonRelationship());

    assertEquals(sisterClient.getCommonFirstName(),
        orderedRelationships.get(0).getRelatedFirstName());
    assertEquals(sisterClient.getCommonLastName(),
        orderedRelationships.get(0).getRelatedLastName());
    assertEquals(BROTHER_SISTER, orderedRelationships.get(0).getRelationshipToPerson());
    assertEquals(SISTER_BROTHER, orderedRelationships.get(0).getRelatedPersonRelationship());

    assertEquals(sisterClientId, orderedRelationships.get(0).getCmsRecordDescriptor().getId());
    assertEquals(brotherClientId, orderedRelationships.get(1).getCmsRecordDescriptor().getId());
    assertNotEquals(orderedRelationships.get(0).getCmsRecordDescriptor().getId(),
        orderedRelationships.get(1).getCmsRecordDescriptor().getId());
  }

  @Test
  public void givenTwoSecondaryRelationshipsThenWeShouldOnlySearchForPrimaryClients() {
    int numberOfClientsSearchedFor = 1;
    int numberOfRelationsSearchedFor = 0;
    genealogist.buildRelationships(secondaryOnlyRelationships, sonClientId);
    verify(clientDao, times(numberOfClientsSearchedFor)).find(sonClientId);
    verify(clientDao, times(numberOfRelationsSearchedFor)).find(dadClientId);
    verify(clientDao, times(numberOfRelationsSearchedFor)).find(momClientId);
  }

  @Test
  public void shouldContainClientDetails() {
    Relationship relationship = genealogist.buildRelationships(relationships, sonClientId);
    String clientBday = DomainChef.cookDate(sonClient.getBirthDate());
    assertEquals(clientBday, relationship.getDateOfBirth());
    assertEquals(sonClient.getCommonFirstName(), relationship.getFirstName());
    assertEquals(sonClient.getCommonLastName(), relationship.getLastName());
    assertEquals(sonClient.getSuffixTitleDescription(), relationship.getSuffixName());
  }

  @Test
  public void givenAClientThatIsSealedThenRelationshipShouldBeSealed() {
    sonClient = new ClientEntityBuilder().setId("1").setCommonFirstName("Charlie")
        .setSensitivityIndicator("R").build();
    when(clientDao.find(sonClientId)).thenReturn(sonClient);
    Relationship relationship = genealogist.buildRelationships(relationships, sonClientId);
    assertTrue(relationship.getSealed());
    assertFalse(relationship.getSensitive());
  }

  @Test
  public void givenAClientIdThatDoesNotExistThenWeShouldGetAnEmptyRelationshipReturned() {
    when(clientDao.find(sonClientId)).thenReturn(null);
    Relationship relationship = genealogist.buildRelationships(relationships, sonClientId);
    assertNull(relationship.getId());
    assertNull(relationship.getRelatedTo());
  }

  @Test
  public void givenAClientThatIsSensitiveThenRelationShouldBeSensitive() {
    sonClient = new ClientEntityBuilder().setId("1").setCommonFirstName("Charlie")
        .setSensitivityIndicator("S").build();
    when(clientDao.find(sonClientId)).thenReturn(sonClient);
    Relationship relationship = genealogist.buildRelationships(relationships, sonClientId);
    assertFalse(relationship.getSealed());
    assertTrue(relationship.getSensitive());
  }

  private ArrayList<RelationshipTo> convertToOrderedRelationships(Relationship relationship) {
    ArrayList<RelationshipTo> orderedRelationships = new ArrayList();
    for (RelationshipTo relation : relationship.getRelatedTo()) {
      orderedRelationships.add(relation);
    }
    return sortByNameAndClient(orderedRelationships);
  }

  private ArrayList sortByNameAndClient(ArrayList<RelationshipTo> orderedRelationships) {
    Comparator relationshipComparator =
        Comparator.comparing((RelationshipTo p) -> p.getRelatedFirstName())
            .thenComparing(p -> p.getRelationshipToPerson());
    Collections.sort(orderedRelationships, relationshipComparator);
    return orderedRelationships;
  }
}
