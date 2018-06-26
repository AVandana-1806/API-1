package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings({"javadoc"})
public class ParticipantTest {

  private final Short primaryLanguage = 1253;
  private final Short secondaryLanguage = 1271;
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private long id = 5432;
  private String legacySourceTable = "CLIENT_T";
  private String clientId = "1234567ABC";
  private long screeningId = 12345;
  private String firstName = "John";
  private String middleName = "T.";
  private String lastName = "Smith";
  private String suffix = "";
  private String gender = "M";
  private String dateOfBirth = "2001-03-15";
  private String ssn = "123456789";
  private boolean reporterConfidentialWaiver = false;
  private String reporterEmployerName = "Employer Name";
  private boolean clientStaffPersonAdded = false;
  private String sensitivityIndicator = "N";
  private String approximateAge = "12";
  private String approximateAgeUnits = "Y";
  private Set<String> roles = new HashSet<>();
  private Set<Address> addresses = new HashSet<>();
  private LegacyDescriptor legacyDescriptor = new LegacyDescriptor();
  private List<Csec> csecs = new ArrayList<>();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Validator validator;

  List<Short> racecodes = new ArrayList<>();
  List<Short> hispaniccodes = new ArrayList<>();
  private RaceAndEthnicity raceAndEthnicity =
      new RaceAndEthnicity(racecodes, "A", hispaniccodes, "X", "A");

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    roles.add("Victim");
    racecodes.add((short) 841);
    hispaniccodes.add((short) 3164);
    Address address =
        new Address("", "", "123 First St", "San Jose", 1828, "94321", 32, legacyDescriptor);
    addresses.add(address);
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);

  }

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {

    Participant participant = new ParticipantResourceBuilder().createVictimParticipant();

    String expected = MAPPER.writeValueAsString(participant);

    String serialized = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/participant/valid/valid.json"), Participant.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    DateTime dateTime = DateTime.parse("2018-06-11T11:47:07.524-07:00");
    DateTime dateTimeUTC = dateTime.withZone(DateTimeZone.UTC);
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("098UijH1gf", null, dateTimeUTC, LegacyTable.CLIENT.getName(), null);

    Participant participant = new ParticipantResourceBuilder().setLegacyDescriptor(legacyDescriptor)
        .createVictimParticipant();

    Participant serialized = MAPPER
        .readValue(fixture("fixtures/domain/participant/valid/valid.json"), Participant.class);
    MAPPER.writeValueAsString(serialized);
    MAPPER.writeValueAsString(participant);
    assertThat(serialized, is(participant));

  }

  @Test
  public void testEmptyConstructor() throws Exception {
    Participant empty = new Participant();
    assertThat(empty.getClass(), is(Participant.class));
  }

  public void testPersistentConstructor() throws Exception {
    // no persistent constructor yet
  }

  @Test
  public void testConstructorUsingDomain() throws Exception {
    Participant domain = new Participant(id, legacySourceTable, clientId, new LegacyDescriptor(),
        firstName, middleName, lastName, suffix, gender, ssn, dateOfBirth, primaryLanguage,
        secondaryLanguage, screeningId, reporterConfidentialWaiver, reporterEmployerName,
        clientStaffPersonAdded, sensitivityIndicator, approximateAge, approximateAgeUnits, roles,
        addresses, raceAndEthnicity, csecs);

    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getLegacySourceTable(), is(equalTo(legacySourceTable)));
    assertThat(domain.getLegacyId(), is(equalTo(clientId)));
    assertThat(domain.getScreeningId(), is(equalTo(screeningId)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getMiddleName(), is(equalTo(middleName)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getNameSuffix(), is(equalTo(suffix)));
    assertThat(domain.getGender(), is(equalTo(gender)));
    assertThat(domain.getDateOfBirth(), is(equalTo(dateOfBirth)));
    assertThat(domain.getSsn(), is(equalTo(ssn)));
    assertThat(domain.getPrimaryLanguage(), is(equalTo(primaryLanguage)));
    assertThat(domain.getSecondaryLanguage(), is(equalTo(secondaryLanguage)));
    assertThat(domain.getSensitivityIndicator(), is(equalTo(sensitivityIndicator)));
    assertThat(domain.getApproximateAge(), is(equalTo(approximateAge)));
    assertThat(domain.getApproximateAgeUnits(), is(equalTo(approximateAgeUnits)));
    assertThat(domain.isClientStaffPersonAdded(), is(equalTo(clientStaffPersonAdded)));
    assertThat(domain.isReporterConfidentialWaiver(), is(equalTo(reporterConfidentialWaiver)));
    assertThat(domain.getReporterEmployerName(), is(equalTo(reporterEmployerName)));
    assertThat(domain.getRoles(), is(equalTo(roles)));
    assertThat(domain.getAddresses(), is(equalTo(addresses)));
    assertThat(domain.getRaceAndEthnicity(), is(equalTo(raceAndEthnicity)));
    assertThat(domain.getCsecs(), is(equalTo(csecs)));
  }

  @Test
  public void shouldNotHaveValidationErrorForValidGenders() {
    List<String> acceptableGenders = Arrays.asList("M", "F", "U");
    acceptableGenders.forEach(gender -> {
      Participant participant =
          new ParticipantResourceBuilder().setGender(gender).createParticipant();
      String errorMessage = "Expected no validation error for gender value: " + gender;
      Set<ConstraintViolation<Participant>> violations = validator.validate(participant);
      assertEquals(errorMessage, 0, violations.size());
    });
  }

  @Test
  public void shouldHaveValidationErrorsForInvaldGenders() {
    List<String> acceptableGenders = Arrays.asList("q", "1", "6", null, "Male", "Female", "O");
    acceptableGenders.forEach(gender -> {
      Participant participant =
          new ParticipantResourceBuilder().setGender(gender).createParticipant();
      String errorMessage = "Expected a validation error for gender value: " + gender;
      Set<ConstraintViolation<Participant>> violations = validator.validate(participant);
      assertEquals(errorMessage, 1, violations.size());
    });
  }

  @Test
  public void testBlankLegacySourceTableSuccess() throws Exception {
    Participant toValidate =
        new ParticipantResourceBuilder().setLegacySourceTable("").createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testNullLegacySourceTableSuccess() throws Exception {
    Participant toValidate =
        new ParticipantResourceBuilder().setLegacySourceTable(null).createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testMissingLegacySourceTableSuccess() throws Exception {
    Participant toValidate =
        MAPPER.readValue(fixture("fixtures/domain/participant/valid/missingLegacySourceTable.json"),
            Participant.class);
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testWithEmptyClientIdSuccess() throws Exception {
    Participant toValidate = new ParticipantResourceBuilder().setLegacyId("").createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testWithNullClientIdSuccess() throws Exception {
    Participant toValidate = new ParticipantResourceBuilder().setLegacyId(null).createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testWithMissingClientIdSuccess() throws Exception {
    Participant toValidate = MAPPER.readValue(
        fixture("fixtures/domain/participant/valid/missingClientId.json"), Participant.class);
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testLegacyIdTooLongFail() throws Exception {
    Participant toValidate =
        new ParticipantResourceBuilder().setLegacyId("lkjghsgss333").createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("size must be between 0 and 10",
        constraintViolations.iterator().next().getMessage());
    // System.out.println(constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testIsVictim() throws IOException {
    Set<String> roles = new HashSet<>(Arrays.asList("Victim"));
    Participant participant = createParticipantWithRoles(roles);
    assertTrue("Expected participant with a victim role to be a victim", participant.isVictim());
  }

  @Test
  public void testParticipantIsNotAVictimWhenVictimIsNotInRole() throws IOException {
    Set<String> roles = new HashSet<>();
    Participant participant = createParticipantWithRoles(roles);
    assertFalse("Expected participant with out a victim role not to be a victim",
        participant.isVictim());
  }

  @Test
  public void testIsReporter() throws IOException {
    Set<String> roles = new HashSet<>(Arrays.asList("Mandated Reporter"));
    Participant participant = createParticipantWithRoles(roles);
    assertTrue("Expected participant with a reporter role to be a reporter",
        participant.isReporter());
  }

  @Test
  public void testParticipantIsNotAReporterWhenReportersNotInRole() throws IOException {
    Set<String> roles = new HashSet<>();
    Participant participant = createParticipantWithRoles(roles);
    assertFalse("Expected participant with out a reporter role not to be a reporter",
        participant.isReporter());
  }

  @Test
  public void testIsPerpetrator() throws IOException {
    Set<String> roles = new HashSet<>(Arrays.asList("Perpetrator"));
    Participant participant = createParticipantWithRoles(roles);
    assertTrue("Expected participant with a perpetrator role to be a perpetrator",
        participant.isPerpetrator());
  }

  @Test
  public void testParticipantIsNotAReporterWhenPerpetratorNotInRole() throws IOException {
    Set<String> roles = new HashSet<>();
    Participant participant = createParticipantWithRoles(roles);
    assertFalse("Expected participant with out a perpetrator role not to be a perpetrator",
        participant.isPerpetrator());
  }

  public void testParticipantDateOfBirthInvalidFail() throws Exception {
    Participant toValidate = MAPPER.readValue(
        fixture("fixtures/domain/participant/invalid/dateOfBirthInvalid.json"), Participant.class);
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be in the format of yyyy-MM-dd",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testGenderInvalidFail() throws Exception {
    Participant toValidate = new ParticipantResourceBuilder().setGender("Z").createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be one of [M, F, U, I]",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void shouldAddListOfAddressesToExistingAddresses() {
    Address existingAddress = new AddressResourceBuilder().setLegacyId("1").createAddress();
    Set<Address> existingAddresses = new HashSet(Arrays.asList(existingAddress));
    Participant participant =
        new ParticipantResourceBuilder().setAddresses(existingAddresses).createParticipant();

    Address newAddress = new AddressResourceBuilder().setLegacyId("2").createAddress();
    Set<Address> newAddresses = new HashSet(Arrays.asList(newAddress));
    participant.addAddresses(newAddresses);
    assertEquals("Expected 2 addresses", 2, participant.getAddresses().size());
    assertEquals("Expected 2 addresses", 2, participant.getAddresses().size());
    assertTrue("Expected to find existing Address",
        participant.getAddresses().contains(existingAddress));
    assertTrue("Expected to find new Address", participant.getAddresses().contains(newAddress));
  }

  @Test
  public void shouldReturnNewLegacyDescriptorWhenItHasANullValue() {
    Participant participant =
        new ParticipantResourceBuilder().setLegacyDescriptor(null).createParticipant();
    assertNotNull(participant.getLegacyDescriptor());
  }

  private Participant createParticipantWithRoles(Set<String> roles) {
    return createParticipant(roles);
  }

  @Test
  public void shouldNotHaveValidationErrorForValidAgeCodes() {
    List<String> acceptableAgeCodes = Arrays.asList("Y", "M", "W", "D");
    acceptableAgeCodes.forEach(ageCode -> {
      Participant participant =
          new ParticipantResourceBuilder().setApproximateAgeUnits(ageCode).createParticipant();
      String errorMessage = "Expected no validation error for gender value: " + ageCode;
      Set<ConstraintViolation<Participant>> violations = validator.validate(participant);
      assertEquals(errorMessage, 0, violations.size());
    });
  }

  @Test
  public void shouldHaveValidationErrorsForInvaldAgeCodes() {
    Participant participant =
        new ParticipantResourceBuilder().setApproximateAgeUnits("Z").createParticipant();
    Set<ConstraintViolation<Participant>> violations = validator.validate(participant);
    assertEquals(1, violations.size());
  }

  private Participant createParticipant(Set<String> roles) {
    Participant validParticipant = null;
    try {
      validParticipant = new Participant(id, legacySourceTable, clientId, new LegacyDescriptor(),
          firstName, middleName, lastName, suffix, gender, ssn, dateOfBirth, primaryLanguage,
          secondaryLanguage, screeningId, reporterConfidentialWaiver, reporterEmployerName,
          clientStaffPersonAdded, sensitivityIndicator, approximateAge, approximateAgeUnits, roles,
          addresses, raceAndEthnicity, null);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return validParticipant;

  }

}
