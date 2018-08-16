package gov.ca.cwds.rest.api.domain;

import static org.junit.Assert.*;

import java.util.Date;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class ScreeningRelationshipBaseTest {

  public static final int RELATIONSHIP_TYPE = 211;
  ScreeningRelationshipBase relationshipBase;

  @Test
  public void shouldHaveAllFieldsWhenCreatingWithSimpleConstructor() {
    Date now = new Date();
    relationshipBase = new ScreeningRelationshipBase();
    relationshipBase.setClientId("PersonLegacyId");
    relationshipBase.setRelativeId("RelationLegacydId");
    relationshipBase.setRelationshipType(RELATIONSHIP_TYPE);
    relationshipBase.setAbsentParentIndicator(true);
    relationshipBase.setSameHomeStatus("U");
    relationshipBase.setStartDate(now);
    relationshipBase.setEndDate(now);
    relationshipBase.setLegacyId("1233456789");

    assertEquals(relationshipBase.getClientId(), "PersonLegacyId");
    assertEquals(relationshipBase.getRelativeId(), "RelationLegacydId");
    assertEquals(relationshipBase.getRelationshipType(), RELATIONSHIP_TYPE);
    assertEquals(relationshipBase.isAbsentParentIndicator(), true);
    assertEquals(relationshipBase.getSameHomeStatus(), "U");
    assertEquals(relationshipBase.getStartDate(), now);
    assertEquals(relationshipBase.getEndDate(), now);
    assertEquals(relationshipBase.getLegacyId(), "1233456789");
  }

  @Test
  public void shouldBeEqual() {
    Date now = new Date();

    relationshipBase = new ScreeningRelationshipBase();
    relationshipBase.setClientId("PersonLegacyId");
    relationshipBase.setRelativeId("RelationLegacydId");
    relationshipBase.setRelationshipType(RELATIONSHIP_TYPE);
    relationshipBase.setAbsentParentIndicator(true);
    relationshipBase.setSameHomeStatus("Y");
    relationshipBase.setStartDate(now);
    relationshipBase.setEndDate(now);
    relationshipBase.setLegacyId("1233456789");

    ScreeningRelationshipBase relationshipEqual = new ScreeningRelationshipBase();
    relationshipEqual.setClientId("PersonLegacyId");
    relationshipEqual.setRelativeId("RelationLegacydId");
    relationshipEqual.setRelationshipType(RELATIONSHIP_TYPE);
    relationshipEqual.setAbsentParentIndicator(true);
    relationshipEqual.setSameHomeStatus("Y");
    relationshipEqual.setStartDate(now);
    relationshipEqual.setEndDate(now);
    relationshipEqual.setLegacyId("1233456789");

    assertEquals(this.relationshipBase, relationshipEqual);
    assertEquals(relationshipEqual, this.relationshipBase);
  }

  @Test
  public void shouldNotBeEqual() {
    Date now = new Date();

    relationshipBase = new ScreeningRelationshipBase();
    relationshipBase.setClientId("PersonLegacyId");
    relationshipBase.setRelativeId("RelationLegacydId");
    relationshipBase.setRelationshipType(RELATIONSHIP_TYPE);
    relationshipBase.setAbsentParentIndicator(true);
    relationshipBase.setSameHomeStatus("N");
    relationshipBase.setStartDate(now);
    relationshipBase.setEndDate(now);
    relationshipBase.setLegacyId("1233456789");

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

    assertNotEquals(this.relationshipBase, relationshipNotEqual);
  }
}