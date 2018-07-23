package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RelationshipWrapperTest {
  private String relationId = "relationId";
  private String primaryLegacyId = "primaryLegacyId";
  private String secondaryLegacyId = "secondaryLegacyId";
  private String primaryFirstName = "primaryFirstName";
  private String primaryMiddleName = "primaryMiddleName";
  private String primaryLastName = "primaryLastName";
  private String secondaryFirstName = "secondaryFirstName";
  private String secondaryMiddleName = "secondaryMiddleName";
  private String secondaryLastName = "secondaryLastName";
  private String primaryRelationshipCode = "primaryRelationshipCode";
  private String secondaryRelationshipCode = "secondaryRelationshipCode";
  private String primaryNameSuffix = "primaryNameSuffix";
  private String secondaryNameSuffix = "sceondaryNameSuffix";
  private String primaryDateOfBirth = "primaryDateOfBirth";
  private String primaryDateOfDeath = "primaryDateOfDeath";
  private String secondaryDateOfBirth = "secondaryDateOfBirth";
  private String secondaryDateOfDeath = "secondaryDateOfDeath";
  private String absentParentCode = "N";
  private String sameHomeCode = "Y";
  private String primaryGenderCode = "M";
  private String secondaryGenderCode = "F";


  @Test
  public void testDefaultConstructor() {
    RelationshipWrapper relationshipWrapper = new RelationshipWrapper();
    assertThat(relationshipWrapper, is(notNullValue()));
  }

  @Test
  public void shouldDefaultValuesWhenNoParamsGiven() {
    String relationId = "";
    String primaryLegacyId = "";
    String secondaryLegacyId = "";
    String primaryFirstName = "";
    String primaryLastName = "";
    String primaryMiddleName = "";
    String secondaryFirstName = "";
    String secondaryMiddleName = "";
    String secondaryLastName = "";
    String primaryRelationshipCode = "";
    String secondaryRelationshipCode = "";

    RelationshipWrapper relationshipWrapper = new RelationshipWrapper(relationId, primaryLegacyId,
        secondaryLegacyId, primaryFirstName, primaryMiddleName, primaryLastName, secondaryFirstName,
        secondaryMiddleName, secondaryLastName, primaryRelationshipCode, secondaryRelationshipCode);
    assertEquals(relationId, relationshipWrapper.getRelationId());
    assertEquals(primaryLegacyId, relationshipWrapper.getPrimaryLegacyId());
    assertEquals(secondaryLegacyId, relationshipWrapper.getSecondaryLegacyId());
    assertEquals(primaryFirstName, relationshipWrapper.getPrimaryFirstName());
    assertEquals(primaryMiddleName, relationshipWrapper.getPrimaryMiddleName());
    assertEquals(primaryLastName, relationshipWrapper.getPrimaryLastName());
    assertEquals(secondaryFirstName, relationshipWrapper.getSecondaryFirstName());
    assertEquals(secondaryMiddleName, relationshipWrapper.getSecondaryMiddleName());
    assertEquals(secondaryLastName, relationshipWrapper.getSecondaryLastName());
    assertEquals(primaryRelationshipCode, relationshipWrapper.getPrimaryRelationshipCode());
    assertEquals(secondaryRelationshipCode, relationshipWrapper.getSecondaryRelationshipCode());
  }

  @Test
  public void shouldConstructClassWhenParametersAreGiven() {

    RelationshipWrapper relationshipWrapper = new RelationshipWrapper(relationId, primaryLegacyId,
        secondaryLegacyId, primaryFirstName, primaryMiddleName, primaryLastName, secondaryFirstName,
        secondaryMiddleName, secondaryLastName, primaryRelationshipCode, secondaryRelationshipCode);
    assertEquals(relationId, relationshipWrapper.getRelationId());
    assertEquals(primaryLegacyId, relationshipWrapper.getPrimaryLegacyId());
    assertEquals(secondaryLegacyId, relationshipWrapper.getSecondaryLegacyId());
    assertEquals(primaryFirstName, relationshipWrapper.getPrimaryFirstName());
    assertEquals(primaryLastName, relationshipWrapper.getPrimaryLastName());
    assertEquals(secondaryFirstName, relationshipWrapper.getSecondaryFirstName());
    assertEquals(secondaryLastName, relationshipWrapper.getSecondaryLastName());
    assertEquals(primaryRelationshipCode, relationshipWrapper.getPrimaryRelationshipCode());
    assertEquals(secondaryRelationshipCode, relationshipWrapper.getSecondaryRelationshipCode());
  }

  @Test
  public void shouldSetValuesWithSetters() {
    RelationshipWrapper relationshipWrapper = new RelationshipWrapper();
    relationshipWrapper.setRelationId(relationId);
    relationshipWrapper.setPrimaryLegacyId(primaryLegacyId);
    relationshipWrapper.setSecondaryLegacyId(secondaryLegacyId);
    relationshipWrapper.setPrimaryFirstName(primaryFirstName);
    relationshipWrapper.setSecondaryFirstName(secondaryFirstName);
    relationshipWrapper.setPrimaryLastName(primaryLastName);
    relationshipWrapper.setSecondaryLastName(secondaryLastName);
    relationshipWrapper.setPrimaryRelationshipCode(primaryRelationshipCode);
    relationshipWrapper.setSecondaryRelationshipCode(secondaryRelationshipCode);
    relationshipWrapper.setPrimaryNameSuffix(primaryNameSuffix);
    relationshipWrapper.setSecondaryNameSuffix(secondaryNameSuffix);
    relationshipWrapper.setPrimaryDateOfBirth(primaryDateOfBirth);
    relationshipWrapper.setPrimaryDateOfDeath(primaryDateOfDeath);
    relationshipWrapper.setSecondaryDateOfBirth(secondaryDateOfBirth);
    relationshipWrapper.setSecondaryDateOfDeath(secondaryDateOfDeath);
    relationshipWrapper.setAbsentParentCode(absentParentCode);
    relationshipWrapper.setSameHomeCode(sameHomeCode);
    relationshipWrapper.setPrimaryGenderCode(primaryGenderCode);
    relationshipWrapper.setSecondaryGenderCode(secondaryGenderCode);

    assertEquals(relationId, relationshipWrapper.getRelationId());
    assertEquals(primaryLegacyId, relationshipWrapper.getPrimaryLegacyId());
    assertEquals(secondaryLegacyId, relationshipWrapper.getSecondaryLegacyId());
    assertEquals(primaryFirstName, relationshipWrapper.getPrimaryFirstName());
    assertEquals(primaryLastName, relationshipWrapper.getPrimaryLastName());
    assertEquals(secondaryFirstName, relationshipWrapper.getSecondaryFirstName());
    assertEquals(secondaryLastName, relationshipWrapper.getSecondaryLastName());
    assertEquals(primaryRelationshipCode, relationshipWrapper.getPrimaryRelationshipCode());
    assertEquals(secondaryRelationshipCode, relationshipWrapper.getSecondaryRelationshipCode());
    assertEquals(primaryNameSuffix, relationshipWrapper.getPrimaryNameSuffix());
    assertEquals(secondaryNameSuffix, relationshipWrapper.getSecondaryNameSuffix());
    assertEquals(primaryDateOfBirth, relationshipWrapper.getPrimaryDateOfBirth());
    assertEquals(secondaryDateOfBirth, relationshipWrapper.getSecondaryDateOfBirth());
    assertEquals(primaryDateOfDeath, relationshipWrapper.getPrimaryDateOfDeath());
    assertEquals(secondaryDateOfDeath, relationshipWrapper.getSecondaryDateOfDeath());
    assertEquals(absentParentCode, relationshipWrapper.getAbsentParentCode());
    assertEquals(sameHomeCode, relationshipWrapper.getSameHomeCode());
    assertEquals(primaryGenderCode, relationshipWrapper.getPrimaryGenderCode());
    assertEquals(secondaryGenderCode, relationshipWrapper.getSecondaryGenderCode());

  }

}
