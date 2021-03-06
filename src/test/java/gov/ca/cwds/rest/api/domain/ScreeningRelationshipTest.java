package gov.ca.cwds.rest.api.domain;

import static org.junit.Assert.*;

import java.util.Date;
import org.junit.Test;

public class ScreeningRelationshipTest {

  public static final int RELATIONSHIP_TYPE = 190;
  ScreeningRelationship relationship;

  @Test
  public void shouldHaveAllFieldsWhenCreatingWithSimpleConstructor(){
    Date now = new Date();
    relationship = new ScreeningRelationship();
    relationship.setId("123");
    relationship.setClientId("PersonLegacyId");
    relationship.setRelativeId("RelationLegacydId");
    relationship.setRelationshipType(RELATIONSHIP_TYPE);
    relationship.setAbsentParentIndicator(true);
    relationship.setSameHomeStatus("U");
    relationship.setStartDate(now);
    relationship.setEndDate(now);
    relationship.setLegacyId("1233456789");

    assertEquals(relationship.getId(), "123");
    assertEquals(relationship.getClientId(), "PersonLegacyId");
    assertEquals(relationship.getRelativeId(), "RelationLegacydId");
    assertEquals(relationship.getRelationshipType(), RELATIONSHIP_TYPE);
  }

  @Test
  public void shouldHaveAllFieldsWhenCreatingFullSimpleConstructor(){
    Date now = new Date();
    relationship = new ScreeningRelationship();
    relationship.setId("123");
    relationship.setClientId("PersonLegacyId");
    relationship.setRelativeId("RelationLegacydId");
    relationship.setRelationshipType(RELATIONSHIP_TYPE);
    relationship.setAbsentParentIndicator(true);
    relationship.setSameHomeStatus("N");
    relationship.setStartDate(now);
    relationship.setEndDate(now);
    relationship.setLegacyId("1233456789");

    assertEquals(relationship.getId(), "123");
    assertEquals(relationship.getClientId(), "PersonLegacyId");
    assertEquals(relationship.getRelativeId(), "RelationLegacydId");
    assertEquals(relationship.getRelationshipType(), RELATIONSHIP_TYPE);
  }

  @Test
  public void shouldHaveAllFieldsWhenCreatingWithBaseConstructor() {
    Date now = new Date();
    ScreeningRelationshipBase relationshipBase = new ScreeningRelationshipBase();
    relationshipBase.setClientId("PersonLegacyId");
    relationshipBase.setRelativeId("RelationLegacydId");
    relationshipBase.setRelationshipType(RELATIONSHIP_TYPE);
    relationshipBase.setAbsentParentIndicator(true);
    relationshipBase.setSameHomeStatus("U");
    relationshipBase.setStartDate(now);
    relationshipBase.setEndDate(now);
    relationshipBase.setLegacyId("1233456789");

    relationship = new ScreeningRelationship(relationshipBase);
    relationship.setId("1121");

    assertEquals(relationship.getId(), "1121");
    assertEquals(relationship.getClientId(), "PersonLegacyId");
    assertEquals(relationship.getRelativeId(), "RelationLegacydId");
    assertEquals(relationship.getRelationshipType(), RELATIONSHIP_TYPE);
    assertEquals(relationship.isAbsentParentIndicator(), true);
    assertEquals(relationship.getSameHomeStatus(), "U");
    assertEquals(relationship.getStartDate(), now);
    assertEquals(relationship.getEndDate(), now);
    assertEquals(relationship.getLegacyId(), "1233456789");
  }

  @Test
  public void shouldBeEqual() {
    Date now = new Date();

    relationship = new ScreeningRelationship();
    relationship.setId("123");
    relationship.setClientId("PersonLegacyId");
    relationship.setRelativeId("RelationLegacydId");
    relationship.setRelationshipType(RELATIONSHIP_TYPE);
    relationship.setAbsentParentIndicator(true);
    relationship.setSameHomeStatus("Y");
    relationship.setStartDate(now);
    relationship.setEndDate(now);
    relationship.setLegacyId("1233456789");

    ScreeningRelationship relationshipEqual = new ScreeningRelationship();
    relationshipEqual.setId("123");
    relationshipEqual.setClientId("PersonLegacyId");
    relationshipEqual.setRelativeId("RelationLegacydId");
    relationshipEqual.setRelationshipType(RELATIONSHIP_TYPE);
    relationshipEqual.setAbsentParentIndicator(true);
    relationshipEqual.setSameHomeStatus("Y");
    relationshipEqual.setStartDate(now);
    relationshipEqual.setEndDate(now);
    relationshipEqual.setLegacyId("1233456789");

    assertEquals(this.relationship, relationshipEqual);
    assertEquals(this.relationship, this.relationship);
  }

  @Test
  public void shouldNotBeEqual() {
    Date now = new Date();

    relationship = new ScreeningRelationship();
    relationship.setId("123");
    relationship.setClientId("PersonLegacyId");
    relationship.setRelativeId("RelationLegacydId");
    relationship.setRelationshipType(RELATIONSHIP_TYPE);
    relationship.setAbsentParentIndicator(true);
    relationship.setSameHomeStatus("N");
    relationship.setStartDate(now);
    relationship.setEndDate(now);
    relationship.setLegacyId("1233456789");

    ScreeningRelationship relationshipNotEqual = new ScreeningRelationship();
    relationshipNotEqual.setId("123");
    relationshipNotEqual.setClientId("PersonLegacyId");
    relationshipNotEqual.setRelativeId("RelationLegacydId2");
    relationshipNotEqual.setRelationshipType(RELATIONSHIP_TYPE);
    relationshipNotEqual.setAbsentParentIndicator(true);
    relationshipNotEqual.setSameHomeStatus("N");
    relationshipNotEqual.setStartDate(now);
    relationshipNotEqual.setEndDate(now);
    relationshipNotEqual.setLegacyId("1233456789");

    assertNotEquals(this.relationship, relationshipNotEqual);
    assertNotEquals(this.relationship, "Not Equal");
  }

  private boolean isCurrentDateTime(Date date){
    Long now = new Date().getTime();
    Long createdTime = date.getTime();
    Long aSecondAgo = 1000L;
    return createdTime <= now && createdTime > now - aSecondAgo;
  }
}