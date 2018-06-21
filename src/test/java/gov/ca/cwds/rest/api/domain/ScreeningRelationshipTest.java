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
        RELATIONSHIP_TYPE, true, "U");
    assertEquals(relationship.getId(), "123");
    assertEquals(relationship.getClientId(), "PersonLegacyId");
    assertEquals(relationship.getRelativeId(), "RelationLegacydId");
    assertEquals(relationship.getRelationshipType(), RELATIONSHIP_TYPE);
  }

  @Test
  public void shouldHaveAllFieldsWhenCreatingFullSimpleConstructor(){
    relationship = new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId",
        RELATIONSHIP_TYPE, true, "N");
    assertEquals(relationship.getId(), "123");
    assertEquals(relationship.getClientId(), "PersonLegacyId");
    assertEquals(relationship.getRelativeId(), "RelationLegacydId");
    assertEquals(relationship.getRelationshipType(), RELATIONSHIP_TYPE);
  }

  @Test
  public void shouldBeEqual() {
    relationship =
        new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId", RELATIONSHIP_TYPE, true, "Y");
    ScreeningRelationship relationshipEqual =
        new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId", RELATIONSHIP_TYPE, true, "Y");
    assertEquals(relationship, relationshipEqual);
    assertEquals(relationship, relationship);
  }

  @Test
  public void shouldNotBeEqual() {
    relationship =
        new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId", RELATIONSHIP_TYPE, true, "N");
    ScreeningRelationship relationshipNotEqual =
        new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId2", RELATIONSHIP_TYPE, true, "N");
    assertNotEquals(relationship, relationshipNotEqual);
    assertNotEquals(relationship, "Not Equal");
  }

  private boolean isCurrentDateTime(Date date){
    Long now = new Date().getTime();
    Long createdTime = date.getTime();
    Long aSecondAgo = 1000L;
    return createdTime <= now && createdTime > now - aSecondAgo;
  }
}