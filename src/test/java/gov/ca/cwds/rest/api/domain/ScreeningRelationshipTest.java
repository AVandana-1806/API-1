package gov.ca.cwds.rest.api.domain;

import static org.junit.Assert.*;

import java.util.Date;
import org.junit.Test;

public class ScreeningRelationshipTest {

  public static final int RELATIONSHIP_TYPE = 190;
  ScreeningRelationship relationship;

  @Test
  public void shouldHaveAllFieldsWhenCreatingWithSimpleConstructor(){
    relationship = new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId",
        RELATIONSHIP_TYPE, true, "U", new Date(), new Date(), "1233456789" );
    assertEquals(relationship.getId(), "123");
    assertEquals(relationship.getClientId(), "PersonLegacyId");
    assertEquals(relationship.getRelativeId(), "RelationLegacydId");
    assertEquals(relationship.getRelationshipType(), RELATIONSHIP_TYPE);
    // will do later  TODO
  }

  @Test
  public void shouldHaveAllFieldsWhenCreatingFullSimpleConstructor(){
    relationship = new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId",
        RELATIONSHIP_TYPE, true, "N",  new Date(), new Date(), "1233456789");
    assertEquals(relationship.getId(), "123");
    assertEquals(relationship.getClientId(), "PersonLegacyId");
    assertEquals(relationship.getRelativeId(), "RelationLegacydId");
    assertEquals(relationship.getRelationshipType(), RELATIONSHIP_TYPE);
    // will do later  TODO

  }

  @Test
  public void shouldBeEqual() {
    relationship =
        new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId", RELATIONSHIP_TYPE, true, "Y",  new Date(), new Date(), "1233456789");
    ScreeningRelationship relationshipEqual =
        new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId", RELATIONSHIP_TYPE, true, "Y",  new Date(), new Date(), "1233456789");
    assertEquals(relationship, relationshipEqual);
    assertEquals(relationship, relationship);
    // will do later  TODO

  }

  @Test
  public void shouldNotBeEqual() {
    relationship =
        new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId", RELATIONSHIP_TYPE, true, "N",  new Date(), new Date(), "1233456789");
    ScreeningRelationship relationshipNotEqual =
        new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId2", RELATIONSHIP_TYPE, true, "N",  new Date(), new Date(), "1233456789");
    assertNotEquals(relationship, relationshipNotEqual);
    assertNotEquals(relationship, "Not Equal");
    // will do later  TODO

  }

  private boolean isCurrentDateTime(Date date){
    Long now = new Date().getTime();
    Long createdTime = date.getTime();
    Long aSecondAgo = 1000L;
    return createdTime <= now && createdTime > now - aSecondAgo;
  }
}