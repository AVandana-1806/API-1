package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.messages.MessageBuilder;
import io.dropwizard.jackson.Jackson;

@SuppressWarnings("javadoc")
public class StartDateTimeValidatorTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Validator validator;
  private MessageBuilder messageBuilder;

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    messageBuilder = new MessageBuilder();

    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  @Test
  public void testExtractStartTimeSuccess() throws Exception {
    Participant participants = new ParticipantResourceBuilder()
        .setRoles(new HashSet(Arrays.asList("Victim", null))).createParticipant();
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(new HashSet(Arrays.asList(participants)))
        .setStartedAt("2017-08-01T08:30:00.000Z").createScreeningToReferral();
    assertThat(
        StartDateTimeValidator.extractStartTime(screeningToReferral.getStartedAt(), messageBuilder),
        equalTo("08:30:00"));
  }

  @Test
  public void testExtractStartTimeFail() throws Exception {
    Participant participants = new ParticipantResourceBuilder()
        .setRoles(new HashSet(Arrays.asList("Victim", null))).createParticipant();
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(new HashSet(Arrays.asList(participants)))
        .setStartedAt("2017-08-01T14:20:00.000Z").createScreeningToReferral();
    assertThat(
        StartDateTimeValidator.extractStartTime(screeningToReferral.getStartedAt(), messageBuilder),
        not(equalTo("14:10:00")));

  }

  @Test
  public void testExtractStartDateSuccess() throws Exception {
    Participant participants = new ParticipantResourceBuilder()
        .setRoles(new HashSet(Arrays.asList("Victim", null))).createParticipant();
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(new HashSet(Arrays.asList(participants)))
        .setStartedAt("2017-08-01T08:30:00.000Z").createScreeningToReferral();
    assertThat(
        StartDateTimeValidator.extractStartDate(screeningToReferral.getStartedAt(), messageBuilder),
        equalTo("2017-08-01"));

  }

  @Test
  public void testExtractStartDateFail() throws Exception {
    Participant participants = new ParticipantResourceBuilder()
        .setRoles(new HashSet(Arrays.asList("Victim", null))).createParticipant();
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(new HashSet(Arrays.asList(participants)))
        .setStartedAt("2017-08-01T14:20:00.000Z").createScreeningToReferral();
    assertThat(
        StartDateTimeValidator.extractStartTime(screeningToReferral.getStartedAt(), messageBuilder),
        not(equalTo("2017-08-02")));

  }

  @Test
  public void shouldReturnErrorMessageWhenStartDateBlank() throws Exception {
    Participant participants = new ParticipantResourceBuilder()
        .setRoles(new HashSet<String>(Arrays.asList("Victim", null))).createParticipant();
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(new HashSet<Participant>(Arrays.asList(participants))).setStartedAt("")
        .createScreeningToReferral();
    StartDateTimeValidator.extractStartDate(screeningToReferral.getStartedAt(), messageBuilder);
    List<ErrorMessage> errorMessages = messageBuilder.getMessages();
    assertThat(errorMessages.size(), is(equalTo(1)));

  }

  @Test
  public void shouldReturnErrorMessageWhenStartDateNull() throws Exception {
    Participant participants = new ParticipantResourceBuilder()
        .setRoles(new HashSet<String>(Arrays.asList("Victim", null))).createParticipant();
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(new HashSet<Participant>(Arrays.asList(participants))).setStartedAt(null)
        .createScreeningToReferral();
    StartDateTimeValidator.extractStartDate(screeningToReferral.getStartedAt(), messageBuilder);
    List<ErrorMessage> errorMessages = messageBuilder.getMessages();
    assertThat(errorMessages.size(), is(equalTo(1)));

  }
}
