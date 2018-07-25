package gov.ca.cwds.data.persistence.ns;

import static org.junit.Assert.*;

import java.util.Date;
import org.junit.Test;

public class RelationshipTest {

  public static final int RELATIONSHIP_TYPE = 190;

  @Test
  public void whenCreatingRelationshipThenWeShouldBeAbleToRetrieveTheProperties() {
    Date anHourAgo = new Date(System.currentTimeMillis() - 3600 * 1000);
    Date now = new Date();
    Relationship relationship = new Relationship("123", "PersonLegacyId", "RelationLegacydId",
        RELATIONSHIP_TYPE, anHourAgo, now, true, false, "1111", null, null);
    assertEquals(relationship.getId(), "123");
    assertEquals(relationship.getClientId(), "PersonLegacyId");
    assertEquals(relationship.getRelativeId(), "RelationLegacydId");
    assertEquals(relationship.getRelationshipType(), RELATIONSHIP_TYPE);
    assertTrue(relationship.getCreatedAt().equals(anHourAgo));
    assertTrue(relationship.getUpdatedAt().equals(now));
    assertFalse(relationship.getSameHomeStatus());
    assertTrue(relationship.isAbsentParentIndicator());
    assertEquals("1111" , relationship.getLegacyId());
    assertNull(relationship.getStartDate());
    assertNull(relationship.getEndDate());
  }
  
  @Test
  public void shouldBeAbleToSetProperties() {
    Date anHourAgo = new Date(System.currentTimeMillis() - 3600 * 1000);
    Date now = new Date();
    Relationship relationship = new Relationship();
    relationship.setId("123");
    relationship.setClientId("PersonLegacyId");
    relationship.setRelativeId("RelationLegacydId");
    relationship.setRelationshipType(RELATIONSHIP_TYPE);
    relationship.setCreatedAt(anHourAgo);
    relationship.setUpdatedAt(now);
    relationship.setSameHomeStatus(true);
    relationship.setAbsentParentIndicator(false);
    assertEquals(relationship.getId(), "123");
    assertEquals(relationship.getClientId(), "PersonLegacyId");
    assertEquals(relationship.getRelativeId(), "RelationLegacydId");
    assertEquals(relationship.getRelationshipType(), RELATIONSHIP_TYPE);
    assertTrue(relationship.getCreatedAt().equals(anHourAgo));
    assertTrue(relationship.getUpdatedAt().equals(now));
    assertTrue(relationship.getSameHomeStatus());
    assertFalse(relationship.isAbsentParentIndicator());
  }

}