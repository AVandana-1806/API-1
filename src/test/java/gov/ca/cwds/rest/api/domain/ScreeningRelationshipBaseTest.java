package gov.ca.cwds.rest.api.domain;

import static org.junit.Assert.*;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import io.dropwizard.validation.ConstraintViolations;
import java.util.Date;
import java.util.Set;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ScreeningRelationshipBaseTest {
  public static final int RELATIONSHIP_TYPE = 190;
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private ScreeningRelationship relationship;
  private Validator validator;

  @Before
  public void setup(){
//    private final Validator validator = BaseValidator.newValidator();
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();


    relationship = new ScreeningRelationship();
    Date now = new Date();
    relationship.setId("123");
    relationship.setClientId("PersonLegacyId");
    relationship.setRelativeId("RelationLegacydId");
    relationship.setRelationshipType(RELATIONSHIP_TYPE);
    relationship.setAbsentParentIndicator(true);
    relationship.setSameHomeStatus("N");
    relationship.setStartDate(now);
    relationship.setEndDate(now);
    relationship.setLegacyId("1233456789");
  }

  @Test
  public void clientIdShouldErrorWhenNull(){
    relationship.setClientId(null);
    Set errors = validator.validate(relationship);
    assertThat(ConstraintViolations.format(errors)).containsOnly("clientId may not be empty");
  }

  @Test
  public void relativeIdShouldErrorWhenNull(){
    relationship.setRelativeId(null);
    Set errors = validator.validate(relationship);
    assertThat(ConstraintViolations.format(errors)).containsOnly("relativeId may not be empty");
  }

  @Test
  public void releationshipTypeShouldErrorWhenNotValidValue(){
    relationship.setRelationshipType(1);
    Set errors = validator.validate(relationship);
    assertThat(ConstraintViolations.format(errors)).containsOnly("relationshipType must be a valid system code for category CLNTRELC");
  }

  @Test
  public void sameHomeStatusShouldErrorWhenNull(){
    relationship.setSameHomeStatus(null);
    Set errors = validator.validate(relationship);
    assertThat(ConstraintViolations.format(errors)).containsOnly("sameHomeStatus may not be empty");
  }

  @Test
  public void sameHomeStatusShouldErrorWhenNotCorrectValue(){
    relationship.setSameHomeStatus("Bad Value");
    Set errors = validator.validate(relationship);
    assertThat(ConstraintViolations.format(errors)).containsOnly("sameHomeStatus must be one of [Y, N, U]");
  }

  @Test
  public void shouldHaveAllFieldsWhenCreatingWithSimpleConstructor() {
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

    ScreeningRelationshipBase relationshipBase = new ScreeningRelationshipBase();
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

    assertEquals(relationshipBase, relationshipEqual);
    assertEquals(relationshipEqual, relationshipBase);
  }

  @Test
  public void shouldNotBeEqual() {
    Date now = new Date();

    ScreeningRelationshipBase relationshipBase = new ScreeningRelationshipBase();
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

    assertNotEquals(relationshipBase, relationshipNotEqual);
  }
}