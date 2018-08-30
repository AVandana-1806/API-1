package gov.ca.cwds.rest.validation;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

/**
 * @author CWDS API Team
 *
 */
public class ParticipantBirthValidatorTest {

  private Validator validator;
  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  /**
   * 
   */
  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Test to check the validation if Dob and Age Number is null
   */
  @Test
  public void testValidationFailWhenDOBAndAgeNumberNull() {
    String dateOfBith = null;
    String approximateAge = null;
    String approximateAgeUnits = null;
    int expectedViolations = 1;
    String expectedViolationMessage =
        "Victim's should have either of the value DOB or approximateAge";
    validVictimBirth(dateOfBith, approximateAge, approximateAgeUnits, expectedViolations,
        expectedViolationMessage);
  }

  /**
   * Test to check success validation when Dob is null and Age, AgeCode is set
   */
  @Test
  public void testValidationSuccessWhenDOBNullAndAgeNumberSet() {
    String dateOfBith = null;
    String approximateAge = "18";
    String approximateAgeUnits = "Y";
    int expectedViolations = 0;
    String expectedViolationMessage = null;
    validVictimBirth(dateOfBith, approximateAge, approximateAgeUnits, expectedViolations,
        expectedViolationMessage);
  }

  /**
   * Test for validation failure when age number is set and Age Unit Code is null
   */
  @Test
  public void testValidationFailWhenDOBAgeNumberSetAndAgeCodeNull() {
    String dateOfBith = null;
    String approximateAge = "18";
    String approximateAgeUnits = null;
    int expectedViolations = 1;
    String expectedViolationMessage =
        "Victim's approximateAgeUnits must be set if approximateAge is set";
    validVictimBirth(dateOfBith, approximateAge, approximateAgeUnits, expectedViolations,
        expectedViolationMessage);
  }

  /**
   * Test success when Dob, AgeNumber and AgeUnit are set
   */
  @Test
  public void testValidationSuccesssWhen3FieldsSet() {
    String dateOfBith = "2005-08-14";
    String approximateAge = "13";
    String approximateAgeUnits = "Y";
    int expectedViolations = 0;
    String expectedViolationMessage = null;
    validVictimBirth(dateOfBith, approximateAge, approximateAgeUnits, expectedViolations,
        expectedViolationMessage);
  }

  /**
   * Test Failure when participant victim dob in future
   */
  @Test
  public void testValidationFailWhenDobInFuture() {
    String dateOfBith = "2017-08-14";
    String approximateAge = null;
    String approximateAgeUnits = null;
    int expectedViolations = 1;
    String expectedViolationMessage = "date of Birth can not be in future";
    validVictimBirth(dateOfBith, approximateAge, approximateAgeUnits, expectedViolations,
        expectedViolationMessage);
  }

  private void validVictimBirth(String dateOfBith, String approximateAge,
      String approximateAgeUnits, int expectedViolations, String expectedViolationMessage) {

    Participant victim = new ParticipantResourceBuilder().setDateOfBirth(dateOfBith)
        .setApproximateAge(approximateAge).setApproximateAgeUnits(approximateAgeUnits)
        .createParticipant();
    Participant Perp = new ParticipantResourceBuilder().setGender("M").createParticipant();
    Participant reporter =
        new ParticipantResourceBuilder().setGender("M").createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim, Perp, reporter));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals(expectedViolations, constraintViolations.size());
    if (StringUtils.isNotBlank(expectedViolationMessage)) {
      assertEquals(expectedViolationMessage, constraintViolations.iterator().next().getMessage());
    }
  }

}
