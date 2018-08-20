package gov.ca.cwds.rest.business.rules.doctool;

import static gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.EVALUATE_OUT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.business.rules.R00824SetDispositionCode;
import gov.ca.cwds.rest.services.LegacyDefaultValues;

/**
 * 
 * @author CWDS API Team
 */
public class R00824SetDispositionCodeTest {

  private static final int APPROVED = 122;
  private static final int PENDING = 119;
  private static final short IMMEDIATE = 1520;
  private static final String ASSESMENT = "A";


  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  /**
   * <blockquote>
   *
   * <pre>
   * BUSINESS RULE: "R - 00824"
   *
   * IF    referralResponseTypeCode is set to Evaluate Out 
   * THEN  referralClient - dispositionCode is set to the "A"
   *
   * </pre>
   *
   * </blockquote>
   * 
   * @throws Exception - Exception
   */
  @SuppressWarnings("javadoc")
  @Test
  public void shouldSetReferralClientDispositionCodeToAssesment() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("2017-12-01").createParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim));
    ScreeningToReferral validScreeningToreferral =
        new ScreeningToReferralResourceBuilder().setResponseTime(EVALUATE_OUT.getCode())
            .setApprovalStatus(APPROVED).setParticipants(participants).createScreeningToReferral();
    Boolean dispositionCode =
        new R00824SetDispositionCode(validScreeningToreferral, victim).isValid();
    ReferralClient referralClient = ReferralClient.createWithDefault(Boolean.FALSE, Boolean.FALSE,
        dispositionCode ? ASSESMENT : "", "1234567ABC", "2345678ABC",
        LegacyDefaultValues.DEFAULT_COUNTY_SPECIFIC_CODE,
        LegacyDefaultValues.DEFAULT_APPROVAL_STATUS_CODE, (short) 12, "Y");
    assertThat(referralClient.getDispositionCode(), is(equalTo(ASSESMENT)));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldSetReferralClientDispositionCodeToBlank() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("2017-12-01").createParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim));
    ScreeningToReferral validScreeningToreferral =
        new ScreeningToReferralResourceBuilder().setResponseTime(EVALUATE_OUT.getCode())
            .setApprovalStatus(PENDING).setParticipants(participants).createScreeningToReferral();
    Boolean dispositionCode =
        new R00824SetDispositionCode(validScreeningToreferral, victim).isValid();
    ReferralClient referralClient = ReferralClient.createWithDefault(Boolean.FALSE, Boolean.FALSE,
        dispositionCode ? ASSESMENT : "", "1234567ABC", "2345678ABC",
        LegacyDefaultValues.DEFAULT_COUNTY_SPECIFIC_CODE,
        LegacyDefaultValues.DEFAULT_APPROVAL_STATUS_CODE, (short) 12, "Y");
    assertThat(referralClient.getDispositionCode(), is(equalTo("")));

  }

  /**
   * Test return false when participant date of birth is null
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testFalseWhenParticipantDobNull() throws Exception {
    Participant victim = new ParticipantResourceBuilder().setDateOfBirth(null).createParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim));
    ScreeningToReferral validScreeningToreferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    R00824SetDispositionCode r00824SetDispositionCode =
        new R00824SetDispositionCode(validScreeningToreferral, victim);
    assertEquals(Boolean.FALSE, r00824SetDispositionCode.isValid());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testTrueWhenChildParticipantEvaluateOutApproved() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("2017-12-01").createParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim));
    ScreeningToReferral validScreeningToreferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).setResponseTime(EVALUATE_OUT.getCode())
        .setApprovalStatus(APPROVED).createScreeningToReferral();
    R00824SetDispositionCode r00824SetDispositionCode =
        new R00824SetDispositionCode(validScreeningToreferral, victim);
    assertEquals(Boolean.TRUE, r00824SetDispositionCode.isValid());

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFalseWhenChildParticipantEvaluateOutNotApproved() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("2017-12-01").createParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim));
    ScreeningToReferral validScreeningToreferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).setResponseTime(EVALUATE_OUT.getCode())
        .setApprovalStatus(PENDING).createScreeningToReferral();
    R00824SetDispositionCode r00824SetDispositionCode =
        new R00824SetDispositionCode(validScreeningToreferral, victim);
    assertEquals(Boolean.FALSE, r00824SetDispositionCode.isValid());

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFalseWhenChildParticipantImmediateApproved() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("2017-12-01").createParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(victim));
    ScreeningToReferral validScreeningToreferral =
        new ScreeningToReferralResourceBuilder().setParticipants(participants)
            .setResponseTime(IMMEDIATE).setApprovalStatus(APPROVED).createScreeningToReferral();
    R00824SetDispositionCode r00824SetDispositionCode =
        new R00824SetDispositionCode(validScreeningToreferral, victim);
    assertEquals(Boolean.FALSE, r00824SetDispositionCode.isValid());

  }
}
