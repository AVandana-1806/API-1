package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.ReporterResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ReporterTest {

  private static final int NOT_FOUND = -1;
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private String referralId = "1234567ABC";
  private String badgeNumber = "123456";
  private String cityName = "ABC";
  private Short colltrClientRptrReltnshpType = 12;
  private Short communicationMethodType = 34;
  private Boolean confidentialWaiverIndicator = Boolean.FALSE;
  private String drmsMandatedRprtrFeedback = "ABC123";
  private String employerName = "";
  private String feedbackDate = "1210-01-31";
  private Boolean feedbackRequiredIndicator = Boolean.FALSE;
  private String firstName = "John";
  private String lastName = "Smith";
  private Boolean mandatedReporterIndicator = Boolean.FALSE;
  private int messagePhoneExtensionNumber = 123;
  private Long messagePhoneNumber = 1234567L;
  private String middleInitialName = "A";
  private String namePrefixDescription = "ABC123";
  private Long primaryPhoneNumber = 1234567L;
  private int primaryPhoneExtensionNumber = 123;
  private Short stateCodeType = 1234;
  private String streetName = "ABC STREET";
  private String streetNumber = "123";
  private String suffixTitleDescription = "Ms";
  private String zipcode = "95842";
  private String lawEnforcementId = "1234567ABC";
  private Short zipSuffixNumber = 1234;
  private String countySpecificCode = "AB";

  private Short primaryLanguage = 1253;
  private Short secondaryLanguage = 1271;
  private boolean reporterConfidentialWaiver = true;
  private String reporterEmployerName = "Employer Name";
  private boolean clientStaffPersonAdded = true;
  private String sensitivityIndicator = "R";
  private MessageBuilder messageBuilder;
  private Validator validator;

  @Before
  public void setup() throws Exception {
    messageBuilder = new MessageBuilder();
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Reporter domain = new Reporter(null, badgeNumber, cityName, colltrClientRptrReltnshpType,
        communicationMethodType, confidentialWaiverIndicator, drmsMandatedRprtrFeedback,
        employerName, feedbackDate, feedbackRequiredIndicator, firstName, lastName,
        mandatedReporterIndicator, messagePhoneExtensionNumber, messagePhoneNumber,
        middleInitialName, namePrefixDescription, primaryPhoneNumber, primaryPhoneExtensionNumber,
        stateCodeType, streetName, streetNumber, suffixTitleDescription, zipcode, referralId,
        lawEnforcementId, zipSuffixNumber, countySpecificCode);
    gov.ca.cwds.data.persistence.cms.Reporter persistent =
        new gov.ca.cwds.data.persistence.cms.Reporter(domain, "lastUpdatedId", new Date());

    Reporter totest = new Reporter(persistent);
    assertThat(totest.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(totest.getBadgeNumber(), is(equalTo(persistent.getBadgeNumber())));
    assertThat(totest.getCityName(), is(equalTo(persistent.getCityName())));
    assertThat(totest.getColltrClientRptrReltnshpType(),
        is(equalTo(persistent.getColltrClientRptrReltnshpType())));
    assertThat(totest.getCommunicationMethodType(),
        is(equalTo(persistent.getCommunicationMethodType())));
    assertThat(totest.getConfidentialWaiverIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getConfidentialWaiverIndicator()))));
    assertThat(totest.getDrmsMandatedRprtrFeedback(),
        is(equalTo(persistent.getDrmsMandatedRprtrFeedback())));
    assertThat(totest.getEmployerName(), is(equalTo(persistent.getEmployerName())));
    assertThat(totest.getFeedbackDate(), is(equalTo(df.format(persistent.getFeedbackDate()))));
    assertThat(totest.getFeedbackRequiredIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getFeedbackRequiredIndicator()))));
    assertThat(totest.getFirstName(), is(equalTo(persistent.getFirstName())));
    assertThat(totest.getLastName(), is(equalTo(persistent.getLastName())));
    assertThat(totest.getMandatedReporterIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getMandatedReporterIndicator()))));
    assertThat(totest.getMessagePhoneExtensionNumber(),
        is(equalTo(persistent.getMessagePhoneExtensionNumber())));
    assertThat(totest.getMessagePhoneNumber(), is(equalTo(persistent.getMessagePhoneNumber())));
    assertThat(totest.getMiddleInitialName(), is(equalTo(persistent.getMiddleInitialName())));
    assertThat(totest.getNamePrefixDescription(),
        is(equalTo(persistent.getNamePrefixDescription())));
    assertThat(totest.getPrimaryPhoneNumber(), is(equalTo(persistent.getPrimaryPhoneNumber())));
    assertThat(totest.getPrimaryPhoneExtensionNumber(),
        is(equalTo(persistent.getPrimaryPhoneExtensionNumber())));
    assertThat(totest.getStateCodeType(), is(equalTo(persistent.getStateCodeType())));
    assertThat(totest.getStreetName(), is(equalTo(persistent.getStreetName())));
    assertThat(totest.getStreetNumber(), is(equalTo(persistent.getStreetNumber())));
    assertThat(totest.getSuffixTitleDescription(),
        is(equalTo(persistent.getSuffixTitleDescription())));
    assertThat(totest.getZipcode(),
        is(equalTo(DomainChef.cookZipcodeNumber(persistent.getZipNumber()))));
    assertThat(totest.getLawEnforcementId(), is(equalTo(persistent.getLawEnforcementId())));
    assertThat(totest.getZipSuffixNumber(), is(equalTo(persistent.getZipSuffixNumber())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Reporter reporter = new Reporter(null, badgeNumber, cityName, colltrClientRptrReltnshpType,
        communicationMethodType, confidentialWaiverIndicator, drmsMandatedRprtrFeedback,
        employerName, feedbackDate, feedbackRequiredIndicator, firstName, lastName,
        mandatedReporterIndicator, messagePhoneExtensionNumber, messagePhoneNumber,
        middleInitialName, namePrefixDescription, primaryPhoneNumber, primaryPhoneExtensionNumber,
        stateCodeType, streetName, streetNumber, suffixTitleDescription, zipcode, referralId,
        lawEnforcementId, zipSuffixNumber, countySpecificCode);

    assertThat(reporter.getReferralId(), is(equalTo(referralId)));
    assertThat(reporter.getBadgeNumber(), is(equalTo(badgeNumber)));
    assertThat(reporter.getCityName(), is(equalTo(cityName)));
    assertThat(reporter.getColltrClientRptrReltnshpType(),
        is(equalTo(colltrClientRptrReltnshpType)));
    assertThat(reporter.getCommunicationMethodType(), is(equalTo(communicationMethodType)));
    assertThat(reporter.getConfidentialWaiverIndicator(), is(equalTo(confidentialWaiverIndicator)));
    assertThat(reporter.getDrmsMandatedRprtrFeedback(), is(equalTo(drmsMandatedRprtrFeedback)));
    assertThat(reporter.getEmployerName(), is(equalTo(employerName)));
    assertThat(reporter.getFeedbackDate(), is(equalTo(feedbackDate)));
    assertThat(reporter.getFeedbackRequiredIndicator(), is(equalTo(feedbackRequiredIndicator)));
    assertThat(reporter.getFirstName(), is(equalTo(firstName)));
    assertThat(reporter.getLastName(), is(equalTo(lastName)));
    assertThat(reporter.getMandatedReporterIndicator(), is(equalTo(mandatedReporterIndicator)));
    assertThat(reporter.getMessagePhoneExtensionNumber(), is(equalTo(messagePhoneExtensionNumber)));
    assertThat(reporter.getMessagePhoneNumber(), is(equalTo(messagePhoneNumber)));
    assertThat(reporter.getMiddleInitialName(), is(equalTo(middleInitialName)));
    assertThat(reporter.getNamePrefixDescription(), is(equalTo(namePrefixDescription)));
    assertThat(reporter.getPrimaryPhoneNumber(), is(equalTo(primaryPhoneNumber)));
    assertThat(reporter.getPrimaryPhoneExtensionNumber(), is(equalTo(primaryPhoneExtensionNumber)));
    assertThat(reporter.getStateCodeType(), is(equalTo(stateCodeType)));
    assertThat(reporter.getStreetName(), is(equalTo(streetName)));
    assertThat(reporter.getStreetNumber(), is(equalTo(streetNumber)));
    assertThat(reporter.getSuffixTitleDescription(), is(equalTo(suffixTitleDescription)));
    assertThat(reporter.getZipcode(), is(equalTo(zipcode)));
    assertThat(reporter.getLawEnforcementId(), is(equalTo(lawEnforcementId)));
    assertThat(reporter.getZipSuffixNumber(), is(equalTo(zipSuffixNumber)));
    assertThat(reporter.getCountySpecificCode(), is(equalTo(countySpecificCode)));
  }

  @Test
  public void createWithDefaultsShouldInitializeWithPassedInValues() {

    String streetNumber = "1";
    String streetName = "main";
    String streetAddress = streetNumber + " " + streetName;
    String city = "sacramento";
    Integer state = 1828; // "ca";
    String zipCode = "12345";
    Integer type = 32;
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

    gov.ca.cwds.rest.api.domain.Address address =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            city, state, zipCode, type, legacyDescriptor);

    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");

    String referralId = "referralId";
    boolean isMandatedReporter = true;
    String firstName = "firstName";
    String middleName = "middleName";
    String lastName = "lastName";
    String employerName = "Employer Name";
    boolean confidentialityWaver = true;
    String approximateAge = "12";
    String approximateAgeUnits = "Y";
    String suffix = "";
    Participant participant = new Participant(5L, "legacy_source_table", "legacy_client_id",
        new LegacyDescriptor(), firstName, middleName, lastName, suffix, "gender", "ssn",
        "date_of_birth", primaryLanguage, secondaryLanguage, 8L, reporterConfidentialWaiver,
        reporterEmployerName, clientStaffPersonAdded, sensitivityIndicator, approximateAge,
        approximateAgeUnits, new HashSet<>(), new HashSet<>(), raceAndEthnicity, null);

    String countyCode = "countyCode";
    Short stateCode = new Short("0");

    Reporter reporter = Reporter.createWithDefaults(referralId, isMandatedReporter, address,
        participant, countyCode);
    assertEquals("Expected referralId field to have been initialized with value", referralId,
        reporter.getReferralId());
    assertEquals("Expected isMandatedReporter field to have been initialized with value",
        isMandatedReporter, reporter.getMandatedReporterIndicator());
    assertEquals("Expected streetNumer field to have been initialized with value", streetNumber,
        reporter.getStreetNumber());
    assertEquals("Expected street name field to have been initialized with value", streetName,
        reporter.getStreetName());
    assertEquals("Expected city field to have been initialized with value", city,
        reporter.getCityName());
    assertEquals("Expected zipCode field to have been initialized with value", zipCode.toString(),
        reporter.getZipcode());
    assertEquals("Expected firstName field to have been initialized with value", firstName,
        reporter.getFirstName());
    assertEquals("Expected middleName field to have been initialized with value", middleName,
        reporter.getMiddleInitialName());
    assertEquals("Expected last name field to have been initialized with value", lastName,
        reporter.getLastName());
    assertEquals("Expected employerName field to have been initialized with value", employerName,
        reporter.getEmployerName());
    assertEquals("Expected reported confidentialWaiver field to have been initialized with value",
        employerName, reporter.getEmployerName());
    assertEquals("Expected suffix name field to have been initialized with value", suffix,
        reporter.getSuffixTitleDescription());
    assertEquals("Expected countyCode field to have been initialized with value", countyCode,
        reporter.getCountySpecificCode());
    assertEquals("Expected confidentialWaiverIndicator field to have been initialized with value",
        true, reporter.getConfidentialWaiverIndicator());
  }

  @Test
  public void createWithDefaultsShouldInitializeWithDefaultValues() {

    String streetNumber = "1";
    String streetName = "main";
    String streetAddress = streetNumber + " " + streetName;
    String city = "sacramento";
    Integer state = 1828; // "ca";
    String zipCode = "12345";
    Integer type = 32;
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

    gov.ca.cwds.rest.api.domain.Address address =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            city, state, zipCode, type, legacyDescriptor);

    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");


    String referralId = "referralId";
    boolean isMandatedReporter = true;
    String firstName = "firstName";
    String middleName = "middleName";
    String lastName = "lastName";
    String suffix = "jr";
    String approximateAge = "12";
    String approximateAgeUnits = "Y";
    Participant participant = new Participant(5L, "legacy_source_table", "legacy_client_id",
        new LegacyDescriptor(), firstName, middleName, lastName, "jr", "gender", "ssn",
        "date_of_birth", primaryLanguage, secondaryLanguage, 8L, reporterConfidentialWaiver,
        reporterEmployerName, clientStaffPersonAdded, sensitivityIndicator, approximateAge,
        approximateAgeUnits, new HashSet<>(), new HashSet<>(), raceAndEthnicity, null);

    String countyCode = "countyCode";
    Short stateCode = new Short("0");

    Reporter reporter = Reporter.createWithDefaults(referralId, isMandatedReporter, address,
        participant, countyCode);
    assertEquals("Expected badgeNumber field to have been initialized with value", "",
        reporter.getBadgeNumber());
    assertEquals("Expected colltrClientRptrReltnshpType field to have been initialized with value",
        new Short("0"), reporter.getColltrClientRptrReltnshpType());
    assertEquals("Expected communicationMethodType field to have been initialized with value",
        new Short("0"), reporter.getCommunicationMethodType());
    assertEquals("Expected drmsMandatedRprtrFeedback field to have been initialized with value", "",
        reporter.getDrmsMandatedRprtrFeedback());
    assertEquals("Expected feedbackDate field to have been initialized with value", "",
        reporter.getFeedbackDate());
    assertEquals("Expected feedbackRequiredIndicator field to have been initialized with value",
        true, reporter.getFeedbackRequiredIndicator());
    assertEquals("Expected messagePhoneExtensionNumber field to have been initialized with value",
        new Integer("0"), reporter.getMessagePhoneExtensionNumber());
    assertEquals("Expected messagePhoneNumber field to have been initialized with value",
        new Long(0), reporter.getMessagePhoneNumber());
    assertEquals("Expected namePrefixDescription field to have been initialized with value", "",
        reporter.getNamePrefixDescription());
    assertEquals("Expected primaryPhoneNumber field to have been initialized with value",
        new Long(0), reporter.getPrimaryPhoneNumber());
    assertEquals("Expected primaryPhoneExtensionNumber field to have been initialized with value",
        new Integer("0"), reporter.getPrimaryPhoneExtensionNumber());
    assertEquals("Expected lawEnforcementId field to have been initialized with value", "",
        reporter.getLawEnforcementId());
    assertEquals("Expected zipSuffixNumber field to have been initialized with value",
        new Short("0"), reporter.getZipSuffixNumber());
  }

  @Test
  public void streetNumberShouldBeParsedFromStreetAddress() {
    String streetAddress = "1 main";
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

    gov.ca.cwds.rest.api.domain.Address address =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            "city", 1828, "12345", 32, legacyDescriptor);

    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");

    Participant participant = new Participant(5L, "legacy_source_table", "legacy_client_id",
        new LegacyDescriptor(), "firstName", "middleName", "lastName", "jr", "gender", "ssn",
        "date_of_birth", primaryLanguage, secondaryLanguage, 8L, reporterConfidentialWaiver,
        reporterEmployerName, clientStaffPersonAdded, sensitivityIndicator, "12", "Y",
        new HashSet<>(), new HashSet<>(), raceAndEthnicity, null);

    Reporter reporter =
        Reporter.createWithDefaults("referralId", true, address, participant, "countyCode");

    assertEquals("Street Number not parsed from street address", "1", reporter.getStreetNumber());
  }

  @Test
  public void streetNameShouldBeParsedFromStreetAddress() {
    String streetAddress = "1 main";
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

    gov.ca.cwds.rest.api.domain.Address address =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            "city", 1828, "12345", 32, legacyDescriptor);

    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");

    Participant participant = new Participant(5L, "legacy_source_table", "legacy_client_id",
        new LegacyDescriptor(), "firstName", "middleName", "lastName", "jr", "gender", "ssn",
        "date_of_birth", primaryLanguage, secondaryLanguage, 8L, reporterConfidentialWaiver,
        reporterEmployerName, clientStaffPersonAdded, sensitivityIndicator, "12", "Y",
        new HashSet<>(), new HashSet<>(), raceAndEthnicity, null);

    Reporter reporter =
        Reporter.createWithDefaults("referralId", true, address, participant, "countyCode");

    assertEquals("Street Number not parsed from street address", "main", reporter.getStreetName());
  }

  @Test
  public void streetNameShouldNotIncludeTypeOfStreetsOrMultiPartStreetNames() {
    String streetAddress = "1 San Andreas Blvd";
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

    gov.ca.cwds.rest.api.domain.Address address =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            "city", 1828, "12345", 32, legacyDescriptor);
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");

    Participant participant = new Participant(5L, "legacy_source_table", "legacy_client_id",
        new LegacyDescriptor(), "firstName", "middleName", "lastName", "jr", "gender", "ssn",
        "date_of_birth", primaryLanguage, secondaryLanguage, 8L, reporterConfidentialWaiver,
        reporterEmployerName, clientStaffPersonAdded, sensitivityIndicator, "12", "Y",
        new HashSet<>(), new HashSet<>(), raceAndEthnicity, null);

    Reporter reporter =
        Reporter.createWithDefaults("referralId", true, address, participant, "countyCode");

    assertEquals("Street Number not parsed from street address", "San Andreas Blvd",
        reporter.getStreetName());
  }

  @Test
  public void streetNameShouldContainSecondWordWhenOnlyNoStreetNumberIsPresent() {
    String streetAddress = "Main St";
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

    gov.ca.cwds.rest.api.domain.Address address =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            "city", 1828, "12345", 32, legacyDescriptor);
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");

    Participant participant = new Participant(5L, "legacy_source_table", "legacy_client_id",
        new LegacyDescriptor(), "firstName", "middleName", "lastName", "jr", "gender", "ssn",
        "date_of_birth", primaryLanguage, secondaryLanguage, 8L, reporterConfidentialWaiver,
        reporterEmployerName, clientStaffPersonAdded, sensitivityIndicator, "12", "Y",
        new HashSet<>(), new HashSet<>(), raceAndEthnicity, null);

    Reporter reporter =
        Reporter.createWithDefaults("referralId", true, address, participant, "countyCode");

    assertEquals("Expected StreetName to contain second word as street name", "Main St",
        reporter.getStreetName());
  }

  @Test
  public void testForStreetAddressContainOnlyWordWhereStreetNumberisMissing() {
    String streetAddress = "Main St";
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

    gov.ca.cwds.rest.api.domain.Address nsAddress =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            "city", 1828, "12345", 32, legacyDescriptor);

    Address address = Address.createWithDefaults(nsAddress);
    assertThat(address.getStreetNumber(), is(equalTo("")));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Reporter.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid.json"), Reporter.class));

    assertThat(MAPPER.writeValueAsString(validReporter()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid.json"), Reporter.class),
        is(equalTo(validReporter())));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * Class level streetNameAndCityName tests
   * 
   * @IfThen(ifProperty = "streetName", thenProperty = "cityName", required = false)
   */
  @Test
  public void testStreetNameNotCityNameFails() throws Exception {
    Reporter reporter =
        new ReporterResourceBuilder().setStreetName("test street").setCityName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("cityName is required since streetName is set")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testStreetNameAndCityNameSuccess() throws Exception {
    Reporter reporter =
        new ReporterResourceBuilder().setStreetName("test street").setCityName("test city").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testNotStreetNoAndCityNameSuccess() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setStreetNumber("").setCityName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testNotStreetNameAndCityNameSuccess() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setStreetName("").setCityName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * 
   * Class level streetNumberAndStreetName tests
   * 
   * @IfThen.List({@IfThen(ifProperty = "streetNumber", thenProperty = "streetName", required =
   * false)
   */
  @Test
  public void testStreetNumberNotStreetNameFails() throws Exception {
    Reporter reporter =
        new ReporterResourceBuilder().setStreetNumber("test street").setStreetName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("streetName is required since streetNumber is set")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testStreetNumberAndStreetNameSuccess() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setStreetNumber("number")
        .setStreetName("test street name").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testNotStreetNumberNotStreetNameSuccess() throws Exception {
    Reporter reporter =
        new ReporterResourceBuilder().setStreetNumber("").setStreetName("test street name").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testStreetNameNotStreetNumberSuccess() throws Exception {
    Reporter reporter =
        new ReporterResourceBuilder().setStreetNumber("").setStreetName("test street name").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * Class level lawEnforcementIdAndEmployerName tests
   * 
   * Referential Integrity Rule tests R - 00849 Employer Name specification. If REPORTER is
   * associated with LAW ENFORCEMENT, the EMPLOYER NAME (AGENCY NAME) must not be specified.
   * 
   * @MutuallyExclusive(required = false, properties = {"employerName", "lawEnforcementId"})
   */
  @Test
  public void testLawEnforcementIdAndEmployerNameFails() throws Exception {
    Reporter reporter =
        new ReporterResourceBuilder().setLawEnforcementId("Y").setEmployerName("test name").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals(
          "Properties [employerName, lawEnforcementId] are mutually exclusive but multiple values are set")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testLawEnforcementIdNotEmployerNameNotSuccess() throws Exception {
    Reporter reporter =
        new ReporterResourceBuilder().setLawEnforcementId("Y").setEmployerName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * badgeNumber Tests
   * 
   * Referential Integrity Rule tests R - 00846 Badge Number specification. BADGE NUMBER may only be
   * specified if the REPORTER is associated with LAW ENFORCEMENT.
   * 
   * @OnlyIf(property = "badgeNumber", ifProperty = "lawEnforcementId")
   */
  @Test
  public void testBadgeNumberTooLong() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setBadgeNumber("1234567").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("badgeNumber size must be less than or equal to 6")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testBadgeNumberEmptyLawEnforcementIdSuccess() throws Exception {
    Reporter reporter =
        new ReporterResourceBuilder().setBadgeNumber("").setLawEnforcementId("12345").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testBadgeNumberEmptySuccess() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setBadgeNumber("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testBadgeNumberNullSuccess() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setBadgeNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * cityName Tests
   */
  @Test
  public void successWhenCityNameNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setCityName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("cityName may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCityNameTooLong() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setCityName("123456789012345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("cityName size must be less than or equal to 20")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * colltrClientRptrReltnshpType Tests
   */
  @Test
  public void failsWhenColltrClientRptrReltnshpTypeNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setColltrClientRptrReltnshpType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("colltrClientRptrReltnshpType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * communicationMethodType Tests
   */
  @Test
  public void failsWhenCommunicationMethodTypeNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setCommunicationMethodType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("communicationMethodType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * confidentialWaiverIndicator Tests
   */
  @Test
  public void failsWhenConfidentialWaiverIndicatorNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setConfidentialWaiverIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("confidentialWaiverIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * drmsMandatedRprtrFeedback Tests
   */
  @Test
  public void successWhenDrmsMandatedRprtrFeedbackEmpty() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setDrmsMandatedRprtrFeedback("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenDrmsMandatedRprtrFeedbackNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setDrmsMandatedRprtrFeedback(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenDrmsMandatedRprtrFeedbackTooLong() throws Exception {
    Reporter reporter =
        new ReporterResourceBuilder().setDrmsMandatedRprtrFeedback("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("drmsMandatedRprtrFeedback size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * employerName Tests
   */
  @Test
  public void testEmployerNameNullFails() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setEmployerName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("employerName may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenEmployerNameTooLong() throws Exception {
    Reporter reporter = new ReporterResourceBuilder()
        .setEmployerName("1234567890123456789012345678901234567890").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("employerName size must be less than or equal to 35")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * feedbackDate Tests
   */
  @Test
  public void successWhenFeedbackDateNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setFeedbackDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenFeedbackDateWrongFormat() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setFeedbackDate("01-10-2018").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("feedbackDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * feedbackRequiredIndicator Tests
   */
  @Test
  public void failsWhenFeedbackRequiredIndicatorNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setFeedbackRequiredIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("feedbackRequiredIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }


  /*
   * firstName Tests
   */
  @Test
  public void failsWhenFirstNameNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setFirstName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("firstName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenFirstNameEmpty() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setFirstName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("firstName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenFirstNameTooLong() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setFirstName("123456789012345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("firstName size must be between 1 and 20")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * lastName Tests
   */
  @Test
  public void failsWhenLastNameNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setLastName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("lastName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenLastNameEmpty() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setLastName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("lastName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenLastNameTooLong() throws Exception {
    Reporter reporter =
        new ReporterResourceBuilder().setLastName("12345678901234567890123456").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("lastName size must be between 1 and 25")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * mandatedReporterIndicator Tests
   */
  @Test
  public void failsWhenMandatedReporterIndicatorNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setMandatedReporterIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("mandatedReporterIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * messagePhoneExtensionNumber Tests
   */
  @Test
  public void failsWhenMessagePhoneExtensionNumberNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setMessagePhoneExtensionNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("messagePhoneExtensionNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * messagePhoneNumber Tests
   */
  @Test
  public void failsWhenMessagePhoneNumberNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setMessagePhoneNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("messagePhoneNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * middleInitialName Tests
   */
  @Test
  public void successWhenMiddleInitialNameEmpty() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setMiddleInitialName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenMiddleInitialNameTooLong() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setMiddleInitialName("AA").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("middleInitialName size must be between 0 and 1")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void successWhenMiddleInitialNameAllWhiteSpace() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setMiddleInitialName(" ").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * namePrefixDescription Tests
   */
  @Test
  public void failsWhenNamePrefixDescriptionNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setNamePrefixDescription(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("namePrefixDescription may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void successWhenNamePrefixDescriptionEmpty() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setNamePrefixDescription("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenNamePrefixDescriptionTooLong() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setNamePrefixDescription("1234567").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("namePrefixDescription size must be between 0 and 6")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * primaryPhoneNumber Tests
   */
  @Test
  public void failsWhenPrimaryPhoneNumberNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setPrimaryPhoneNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("primaryPhoneNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * primaryPhoneExtensionNumber Tests
   */
  @Test
  public void failsWhenPrimaryPhoneExtensionNumberNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setPrimaryPhoneExtensionNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("primaryPhoneExtensionNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * stateCodeType Tests
   */
  @Test
  public void failsWhenStateCodeTypeNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setStateCodeType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("stateCodeType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * streetName Tests
   */
  @Test
  public void failureWhenStreetNameNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setStreetName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("streetName may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void successWhenStreetNameEmpty() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setStreetName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenStreetNameTooLong() throws Exception {
    Reporter reporter = new ReporterResourceBuilder()
        .setStreetName("12345678901234567890123456789012345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("streetName size must be less than or equal to 40")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * streetNumber Tests
   */
  @Test
  public void failWhenStreetNumberNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setStreetNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("streetNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void successWhenStreetNumberEmpty() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setStreetNumber("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenStreetNumberTooLong() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setStreetNumber("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("streetNumber size must be less than or equal to 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * suffixTitleDescription Tests
   */
  @Test
  public void failsWhenSuffixTitleDescriptionNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setSuffixTitleDescription(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("suffixTitleDescription may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * Zipcode tests
   */
  @Test
  public void failsWhenZipcodeNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setZipcode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("zipcode may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }


  /*
   * referralId Tests
   */
  @Test
  public void failsWhenReferralIdNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setReferralId(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("referralId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenReferralIdEmpty() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setReferralId("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("referralId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenReferralIdTooLong() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setReferralId("1234567ABCD").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("referralId size must be between 10 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * lawEnforcementId Tests
   */
  @Test
  public void successWhenLawEnforcementNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setLawEnforcementId(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenLawEnforcementIdTooLong() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setLawEnforcementId("1234567ABCD").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("lawEnforcementId size must be 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * zipSuffixNumber Tests
   */
  @Test
  public void failsWhenZipSuffixNumberNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setZipSuffixNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("zipSuffixNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * countySpecificCode Tests
   */
  @Test
  public void failsWhenCountySpecificCodeNull() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setCountySpecificCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCountySpecificCodeEmpty() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setCountySpecificCode("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCountySpecificCodeTooLong() throws Exception {
    Reporter reporter = new ReporterResourceBuilder().setCountySpecificCode("123").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode size must be between 1 and 2")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * Utilities
   */
  private Reporter validReporter() throws JsonParseException, JsonMappingException, IOException {

    Reporter validReporter =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid.json"), Reporter.class);
    return validReporter;

  }
}
