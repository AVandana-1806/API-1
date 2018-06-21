package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.AllegationResourceBuilder;
import gov.ca.cwds.fixture.CrossReportResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.fixture.investigation.SafetyAlertsEntityBuilder;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.api.domain.investigation.SafetyAlerts;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.ScreeningToReferralResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

@SuppressWarnings("javadoc")
public class ScreeningToReferralTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_REFERRALS + "/";;
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  public static final String SACRAMENTO_COUNTY_CODE = "34";
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private static final ScreeningToReferralResource mockedScreeningToReferralResource =
      mock(ScreeningToReferralResource.class);

  private Integer method = 2095; // "electronic report";
  private String informDate = "2017-03-15";
  private Set<Participant> participants = new HashSet<Participant>();
  private Set<ScreeningRelationship> relationships = new HashSet<ScreeningRelationship>();
  private Set<CrossReport> crossReports = new HashSet<CrossReport>();
  private Set<Allegation> allegations = new HashSet<Allegation>();
  private long id = 2;
  private boolean filedOutOfState = false;
  private String countyId = "1101";

  int approvalStatus = 118;
  boolean familyAwarness = false;
  boolean filedWithLawEnforcement = false;
  String responsibleAgency = "C";

  Short communicationMethod = 409;
  String currentLocationOfChildren = "current location of children";
  String reportType = "ssb";

  private Short responseTime = (short) 1520;

  private MessageBuilder messageBuilder;
  private Validator validator;

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedScreeningToReferralResource).build();

  @Before
  public void setup() {
    messageBuilder = new MessageBuilder();

    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /*
   * incident Date Tests
   */
  @Test
  public void successWhenIncidentDateEmpty() throws Exception {
    ScreeningToReferral toCreate =
        new ScreeningToReferralResourceBuilder().setIncidentDate("").createScreeningToReferral();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenIncidentDateNull() throws Exception {
    ScreeningToReferral toCreate =
        new ScreeningToReferralResourceBuilder().setIncidentDate(null).createScreeningToReferral();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * Serialize
   */
  @Test
  public void shouldSerializeToJSON() throws Exception {

    Address address = new AddressResourceBuilder().createAddress();
    Participant participant = new ParticipantResourceBuilder().createVictimParticipant();
    participants.add(participant);
    CrossReport crossReport = new CrossReport("", "", "", filedOutOfState, method, informDate,
        countyId, Sets.newHashSet());
    crossReports.add(crossReport);
    Short injuryHarmType = 2178;
    Allegation allegation =
        new AllegationResourceBuilder().setLegacySourceTable("ALLGTN_T").setLegacyId("1234567ABC")
            .setPerpetratorPersonId(2).setInjuryHarmType(injuryHarmType).createAllegation();
    // Allegation allegation = validAllegation();
    allegations.add(allegation);
    SafetyAlerts safetyAlerts = new SafetyAlerts();

    String expected = MAPPER.writeValueAsString(new ScreeningToReferral(id, "", "",
        "2016-08-03T01:00:00.000", SACRAMENTO_COUNTY_CODE, "2016-08-02", "Foster Home",
        communicationMethod, currentLocationOfChildren, "The Rocky Horror Show",
        "Narrative 123 test", "123ABC", responseTime, "2016-08-03T01:00:00.000", "Michael Bastow",
        "0X5", "addtional information", "Screening Descision", "Detail", approvalStatus,
        familyAwarness, filedWithLawEnforcement, responsibleAgency, "S", "", "23", null, safetyAlerts.getAlerts(),
        safetyAlerts.getAlertInformation(), address,
        participants, relationships, crossReports, allegations, reportType));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validstr.json"),
            ScreeningToReferral.class));
    assertThat(serialized, is(expected));
  }

  @Test
  public void shouldSerializeToJSONwhenSafetyAlertsIncluded() throws Exception {
    Participant participant = new ParticipantResourceBuilder().createParticipant();
    participants.add(participant);
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    String expected = MAPPER.writeValueAsString(
        new ScreeningToReferralResourceBuilder().setSafetyAlerts(safetyAlerts.getAlerts())
            .setSafetyAlertInformationn(safetyAlerts.getAlertInformation())
            .setParticipants(participants).createScreeningToReferral());

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/validWithSafetyAlert.json"),
            ScreeningToReferral.class));
    assertThat(serialized, is(expected));
  }

  @Test
  public void shouldDeserializeFromJSON() throws Exception {
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    Address address = new AddressResourceBuilder().createAddress();
    Participant participant = this.validParticipant();
    participants.add(participant);
    CrossReport crossReport = new CrossReport("", "", "", filedOutOfState, method, informDate,
        countyId, Sets.newHashSet());
    crossReports.add(crossReport);
    Short injuryHarmType = 2178;
    Allegation allegation =
        new AllegationResourceBuilder().setLegacySourceTable("ALLGTN_T").setLegacyId("1234567ABC")
            .setPerpetratorPersonId(2).setInjuryHarmType(injuryHarmType).createAllegation();
    allegations.add(allegation);
    SafetyAlerts safetyAlerts = new SafetyAlerts();

    ScreeningToReferral expected = new ScreeningToReferral(id, "", "", "2016-08-03T01:00:00.000",
        SACRAMENTO_COUNTY_CODE, "2016-08-02", "Foster Home", communicationMethod,
        currentLocationOfChildren, "The Rocky Horror Show", "Narrative 123 test", "123ABC",
        responseTime, "2016-08-03T01:00:00.000", "Michael Bastow", "0X5", "addtional information",
        "Screening Descision", "Detail", approvalStatus, familyAwarness, filedWithLawEnforcement,
        responsibleAgency, "S", "", "23", null, safetyAlerts.getAlerts(), safetyAlerts.getAlertInformation(),
        address, participants, relationships, crossReports, allegations, reportType);

    ScreeningToReferral deserialized =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validstr.json"),
            ScreeningToReferral.class);

    assertThat(deserialized, is(expected));
  }

  @Test
  public void shouldValidateWithValid() throws Exception {
    ScreeningToReferral toValidate =
        new ScreeningToReferralResourceBuilder().createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void shouldPassWithSafetyAlerts() throws Exception {
    ScreeningToReferral screening =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/validWithWorkerSafety.json"),
            ScreeningToReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(screening));
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
    }
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));

  }

  @Test
  public void shouldPassWithMissingSafetyAlerts() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/missingSafetyAlerts.json"),
        ScreeningToReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void shouldFailWithNullParticipants() throws Exception {
    ScreeningToReferral toValidate =
        new ScreeningToReferralResourceBuilder().setParticipants(null).createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    Boolean theErrorDetected = false;
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("participants may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void shouldFailWithEmptyParticipants() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/emptyParticipants.json"),
        ScreeningToReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    Boolean theErrorDetected = false;
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("participants may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void shouldFailWithNullAllegations() throws Exception {
    Set<Allegation> allegatiopns = null;
    ScreeningToReferral toValidate = new ScreeningToReferralResourceBuilder()
        .setAllegations(allegatiopns).createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    Boolean theErrorDetected = false;
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("allegations may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void shouldFailWithEmptyAllegations() throws Exception {
    Set<Allegation> allegatiopns = new HashSet<>();
    ScreeningToReferral toValidate = new ScreeningToReferralResourceBuilder()
        .setAllegations(allegatiopns).createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    Boolean theErrorDetected = false;
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("allegations may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    Set<Allegation> allegations = new HashSet<>();
    ScreeningToReferral expected = new ScreeningToReferralResourceBuilder()
        .setAllegations(allegations).createScreeningToReferral();
    assertThat(expected.hashCode(), is(not(0)));
  }

  @Test
  public void shouldPassWithEmptyCrossReport() throws Exception {
    Set<CrossReport> crossReports = new HashSet<>();
    ScreeningToReferral toValidate = new ScreeningToReferralResourceBuilder()
        .setCrossReports(crossReports).createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void shouldPassWithNullCrossReport() throws Exception {
    CrossReport crossReport = null;
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    ScreeningToReferral toValidate = new ScreeningToReferralResourceBuilder()
        .setCrossReports(crossReports).createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void shouldPassWithMultipleCrossReports() throws Exception {
    CrossReport crossReport =
        new CrossReportResourceBuilder().setId("ABC147852").createCrossReport();
    CrossReport crossReport1 =
        new CrossReportResourceBuilder().setId("ABC147851").createCrossReport();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport, crossReport1));

    ScreeningToReferral toValidate = new ScreeningToReferralResourceBuilder()
        .setCrossReports(crossReports).createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void shouldFailWithInvalidIncidentDateFormat() throws Exception {
    ScreeningToReferral toValidate = new ScreeningToReferralResourceBuilder()
        .setIncidentDate("12-07-1992").createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    Boolean theErrorDetected = false;
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("incidentDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void shouldPassBlankLegacySourceTable() throws Exception {
    ScreeningToReferral toValidate = new ScreeningToReferralResourceBuilder()
        .setLegacySourceTable("").createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void shouldPassNullLegacySourceTable() throws Exception {
    ScreeningToReferral toValidate = new ScreeningToReferralResourceBuilder()
        .setLegacySourceTable(null).createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void shouldPassWithMissingLegacySourceTable() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/missingLegacySourceTable.json"),
        ScreeningToReferral.class);
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldPassWithEmptyLegacyReferralId() throws Exception {
    ScreeningToReferral toValidate =
        new ScreeningToReferralResourceBuilder().setReferralId("").createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void shouldPassWithNullLegacyReferralId() throws Exception {
    ScreeningToReferral toValidate =
        new ScreeningToReferralResourceBuilder().setReferralId(null).createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void shouldPassWithMissingLegacyReferralId() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/missingReferralId.json"),
        ScreeningToReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void shouldFailWhenReferralIdTooLong() throws Exception {
    ScreeningToReferral toValidate = new ScreeningToReferralResourceBuilder()
        .setReferralId("pouhG568F11").createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    Boolean theErrorDetected = false;
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("referralId size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void shouldPassWithoutPerpetratorParticipant() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/withoutPerpetratorParticipant.json"),
        ScreeningToReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toValidate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void shouldFailWithInvalidCountySpecificCode() throws Exception {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setIncidentCounty("XX").createScreeningToReferral();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(screeningToReferral));
    Boolean theErrorDetected = false;
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage()
          .equals("incidentCounty must be a valid logical id code for category GVR_ENTC")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void shouldHaveNoErrorsWhenLimitedAccessCodeIsNull() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessCode(null).createScreeningToReferral();

    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals("Expected limited access code value of null to not be an error", 0,
        constraintViolations.size());
  }

  @Test
  public void shouldHaveNoErrorsWhenLimitedAccessCodeIsValid() {
    List<String> accessCodes = Arrays.asList("N", "R", "S");

    for (String code : accessCodes) {
      ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
          .setLimitedAccessCode(code).createScreeningToReferral();

      Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
          validator.validate(screeningToReferral);
      String message = "Expected limited access code value of " + code + " to not be an error";
      assertEquals(message, 0, constraintViolations.size());
    }
  }

  @Test
  public void shouldHaveErrorsWhenLimitedAccessCodeIsInValid() {
    List<String> accessCodes = Arrays.asList("$", "X", "1", "Y", "n", "s", "r");

    for (String code : accessCodes) {
      ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
          .setLimitedAccessCode(code).createScreeningToReferral();

      Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
          validator.validate(screeningToReferral);
      String message = "Expected limited access code value of " + code + " to not be an error";
      assertEquals(message, 1, constraintViolations.size());
    }
  }

  @Test
  public void shouldHaveNoErrorsWhenLimitedAccessDescriptionIsNull() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessDescription(null).createScreeningToReferral();

    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals("Expected limited access description value of null to not be an error", 0,
        constraintViolations.size());
  }

  @Test
  public void shouldHaveNoErrorsWhenLimitedAccessDescriptionIsValid() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessDescription("Limited access because ...").createScreeningToReferral();

    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals("Expected limited access description value of null to not be an error", 0,
        constraintViolations.size());
  }

  @Test
  public void shouldHaveNoErrorsWhenLimitedAccessAgencyIsNull() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessAgency(null).createScreeningToReferral();

    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals("Expected limited access agency value of null to not be an error", 0,
        constraintViolations.size());
  }

  @Test
  public void shouldHaveNoErrorsWhenLimitedAccessAgencyIsAnEmptyString() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessAgency("").createScreeningToReferral();

    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals("Expected limited access agency value of null to not be an error", 0,
        constraintViolations.size());
  }

  @Test
  public void shouldNotHaveErrorsWhenLimitedAccessAgencyIsAValidCode() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessAgency("23").createScreeningToReferral();

    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals("Expected limited access agency to not have an error when code is correct", 0,
        constraintViolations.size());
  }

  @Test
  public void shouldHaveErrorsWhenLimitedAccessAgencyIsAnInValidCode() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessAgency("999").createScreeningToReferral();

    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals("Expected limited access agency to have an error when code is incorrect", 1,
        constraintViolations.size());
  }

  @Test
  public void shouldHaveNoErrorsWhenLimitedAccessDateIsNull() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessDate(null).createScreeningToReferral();

    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(screeningToReferral);
    assertEquals("Expected limited access date value of null to not be an error", 0,
        constraintViolations.size());
  }

  @Test
  public void shouldReportAccessIsLimitedWhenCodeIsS() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessCode("S").createScreeningToReferral();
    assertTrue("Expected access to be limited", screeningToReferral.isAccessLimited());
  }

  @Test
  public void shouldReportAccessIsLimitedWhenCodeIsR() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessCode("R").createScreeningToReferral();
    assertTrue("Expected access to be limited", screeningToReferral.isAccessLimited());

  }

  @Test
  public void shouldReportAccessIsNotLimitedWhenCodeIsN() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessCode("N").createScreeningToReferral();
    assertFalse("Expected access to not be limited", screeningToReferral.isAccessLimited());

  }

  @Test
  public void shouldReportAccessIsNotLimitedWhenCodeIsNull() {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessCode(null).createScreeningToReferral();
    assertFalse("Expected access to not be limited", screeningToReferral.isAccessLimited());

  }

  private Participant validParticipant() {

    try {
      Participant validParticipant = MAPPER
          .readValue(fixture("fixtures/domain/participant/valid/valid.json"), Participant.class);
      return validParticipant;

    } catch (JsonParseException e) {
      e.printStackTrace();
      return null;
    } catch (JsonMappingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
