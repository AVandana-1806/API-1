package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.business.rules.R00786VictimAgeRestriction;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

/**
 * Unit tests for R00786VictimAgeRestriction rule.
 * 
 * @author CWDS API Team
 */
public class R00786VictimAgeRestrictionTest {

  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private Validator validator;

  /**
   * 
   */
  @Before
  public void setup() {
    new TestingRequestExecutionContext("0X5");
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Test that victim age is under max allowed.
   */
  @Test
  public void testVictimAgeNotOver() {
    // check with age = 10 years
    int ageYears = 10;
    int underAgeDays = 0;
    int overAgeDays = 0;
    int expectedViolations = 0;
    validateVictimAge(ageYears, overAgeDays, underAgeDays, expectedViolations);
  }

  /**
   * Test with victim age at maximum allowed
   */
  @Test
  public void testVictimAgeMax() {
    // check with age = 18 years
    int ageYears = R00786VictimAgeRestriction.MAX_VICTIM_AGE_YEARS;
    int underAgeDays = 0;
    int overAgeDays = 0;
    int expectedViolations = 0;
    validateVictimAge(ageYears, overAgeDays, underAgeDays, expectedViolations);
  }

  /**
   * Test with victim age below maximum allowed
   */
  @Test
  public void testVictimAgeBelowMax() {
    // check with age = 18 years - 1 year
    int ageYears = R00786VictimAgeRestriction.MAX_VICTIM_AGE_YEARS - 1;
    int underAgeDays = 0;
    int overAgeDays = 0;
    int expectedViolations = 0;
    validateVictimAge(ageYears, overAgeDays, underAgeDays, expectedViolations);
  }

  /**
   * Test with victim age over maximum allowed
   */
  @Test
  public void testVictimAgeOver() {
    // check with age = 18 years + 1 year
    int ageYears = R00786VictimAgeRestriction.MAX_VICTIM_AGE_YEARS + 1;
    int underAgeDays = 0;
    int overAgeDays = 0;
    int expectedViolations = 1;
    validateVictimAge(ageYears, overAgeDays, underAgeDays, expectedViolations);
  }

  /**
   * Test with victim age slightly over maximum allowed
   */
  @Test
  public void testVictimAgeSlightlyOver() {
    // check with age = 18 years + 1 day
    int ageYears = R00786VictimAgeRestriction.MAX_VICTIM_AGE_YEARS;
    int underAgeDays = 0;
    int overAgeDays = 1;
    int expectedViolations = 1;
    validateVictimAge(ageYears, overAgeDays, underAgeDays, expectedViolations);
  }

  /**
   * Test with victim age slightly under maximum allowed
   */
  @Test
  public void testVictimAgeSlightlyUnder() {
    // check with age = 18 years - 1 day
    int ageYears = R00786VictimAgeRestriction.MAX_VICTIM_AGE_YEARS;
    int underAgeDays = 1;
    int overAgeDays = 0;
    int expectedViolations = 0;
    validateVictimAge(ageYears, overAgeDays, underAgeDays, expectedViolations);
  }

  /**
   * 
   */
  @Test
  public void testForVictimAgeIsAbove18Years() {
    Participant victim = new ParticipantResourceBuilder().setDateOfBirth(null)
        .setApproximateAge("19").setApproximateAgeUnits("Y").createParticipant();

    Set<Participant> participants = buildPerpAndReporter(victim);
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals(1, constraintViolations.size());
    assertEquals("Victim's age must be less than 18 years",
        constraintViolations.iterator().next().getMessage());
  }

  /**
   * 
   */
  @Test
  public void testForValidatingAgeWithMonths() {
    Participant victim = new ParticipantResourceBuilder().setDateOfBirth(null)
        .setApproximateAge("228").setApproximateAgeUnits("M").createParticipant();

    Set<Participant> participants = buildPerpAndReporter(victim);
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals(1, constraintViolations.size());
    assertEquals("Victim's age must be less than 18 years",
        constraintViolations.iterator().next().getMessage());
  }

  /**
   * 
   */
  @Test
  public void testForValidatingAgeWithWeeks() {
    Participant victim = new ParticipantResourceBuilder().setDateOfBirth(null)
        .setApproximateAge("228").setApproximateAgeUnits("W").createParticipant();

    Set<Participant> participants = buildPerpAndReporter(victim);
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals(1, constraintViolations.size());
  }

  /**
   * 
   */
  @Test
  public void testForValidatinAgeWithDays() {
    Participant victim = new ParticipantResourceBuilder().setDateOfBirth(null)
        .setApproximateAge("228").setApproximateAgeUnits("D").createParticipant();

    Set<Participant> participants = buildPerpAndReporter(victim);
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals(1, constraintViolations.size());
  }

  /**
   * 
   */
  @Test
  public void testReturnFalseWhenDobAndAgeIsNull() {
    Participant victim = new ParticipantResourceBuilder().setDateOfBirth(null)
        .setApproximateAge(null).setApproximateAgeUnits(null).createParticipant();

    Set<Participant> participants = buildPerpAndReporter(victim);
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals(2, constraintViolations.size());
  }

  private Set<Participant> buildPerpAndReporter(Participant victim) {
    Participant Perp = new ParticipantResourceBuilder().setGender("M").createParticipant();
    Participant reporter =
        new ParticipantResourceBuilder().setGender("M").createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim, Perp, reporter));
    return participants;
  }

  /**
   * Create ScreeningToReferral for test and validate it for give victim age.
   * 
   * @param victimAgeYears
   * @param overAgeDays
   * @param underAgeDays
   * @param expectedViolations
   */
  private void validateVictimAge(int victimAgeYears, int overAgeDays, int underAgeDays,
      int expectedViolations) {
    String requestStartTime =
        DomainChef.cookISO8601Timestamp(RequestExecutionContext.instance().getRequestStartTime());
    DateTime referralToBeCreatedAtDateTime =
        new DateTime(DomainChef.uncookDateString(requestStartTime));

    ScreeningToReferralResourceBuilder builder = new ScreeningToReferralResourceBuilder();
    builder.setStartedAt(requestStartTime);

    String victimDob = DomainChef.cookDate(referralToBeCreatedAtDateTime.minusYears(victimAgeYears)
        .minusDays(overAgeDays).plusDays(underAgeDays).toDate());
    Participant victim = new ParticipantResourceBuilder().setGender("M").setDateOfBirth(victimDob)
        .createVictimParticipant();

    Participant perp = new ParticipantResourceBuilder().setGender("F").createPerpParticipant();
    Participant reporter =
        new ParticipantResourceBuilder().setGender("M").createReporterParticipant();

    Set<Participant> participants = new HashSet<>(Arrays.asList(victim, perp, reporter));
    builder.setParticipants(participants);

    ScreeningToReferral screeingToReferral = builder.createScreeningToReferral();

    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeingToReferral);

    assertEquals(expectedViolations, constraintViolations.size());
  }
}
