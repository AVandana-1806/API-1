package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.CmsCrossReportResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class CrossReportTest {


  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private final static DateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
  private String thirdId = "b";
  private Short crossReportMethodType = 1;
  private Boolean filedOutOfStateIndicator = Boolean.TRUE;
  private Boolean governmentOrgCrossRptIndicatorVar = Boolean.FALSE;
  private String informTime = "16:41:49";
  private String recipientBadgeNumber = "d";
  private Integer recipientPhoneExtensionNumber = 2;
  private Long recipientPhoneNumber = 3L;
  private String informDate = "1973-11-22";
  private String recipientPositionTitleDesc = "e";
  private String referenceNumber = "f";
  private String referralId = "a";
  private String lawEnforcementId = "g";
  private String staffPersonId = "h";
  private String description = "i";
  private String recipientName = "j";
  private String outStateLawEnforcementAddr = "k";
  private String countySpecificCode = "l";
  private Boolean lawEnforcementIndicator = Boolean.TRUE;
  private Boolean outStateLawEnforcementIndicator = Boolean.FALSE;
  private Boolean satisfyCrossReportIndicator = Boolean.TRUE;

  private MessageBuilder messageBuilder;
  private Validator validator;

  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Before
  public void setup() throws Exception {
    
    messageBuilder = new MessageBuilder();
    
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    CrossReport domain = new CrossReport(thirdId, crossReportMethodType, filedOutOfStateIndicator,
        governmentOrgCrossRptIndicatorVar, informTime, recipientBadgeNumber,
        recipientPhoneExtensionNumber, recipientPhoneNumber, informDate, recipientPositionTitleDesc,
        referenceNumber, referralId, lawEnforcementId, staffPersonId, description, recipientName,
        outStateLawEnforcementAddr, countySpecificCode, lawEnforcementIndicator,
        outStateLawEnforcementIndicator, satisfyCrossReportIndicator);
    gov.ca.cwds.data.persistence.cms.CrossReport persistent =
        new gov.ca.cwds.data.persistence.cms.CrossReport(thirdId, domain, "lastUpdatedId",
            new Date());

    CrossReport totest = new CrossReport(persistent);
    assertThat(totest.getThirdId(), is(equalTo(persistent.getThirdId())));
    assertThat(totest.getCrossReportMethodType(),
        is(equalTo(persistent.getCrossReportMethodType())));
    assertThat(totest.getFiledOutOfStateIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getFiledOutOfStateIndicator()))));
    assertThat(totest.getGovernmentOrgCrossRptIndicatorVar(), is(equalTo(
        DomainChef.uncookBooleanString(persistent.getGovernmentOrgCrossRptIndicatorVar()))));
    assertThat(totest.getInformTime(),
        is(equalTo(timeOnlyFormat.format(persistent.getInformTime()))));
    assertThat(totest.getRecipientBadgeNumber(), is(equalTo(persistent.getRecipientBadgeNumber())));
    assertThat(totest.getRecipientPhoneExtensionNumber(),
        is(equalTo(persistent.getRecipientPhoneExtensionNumber())));
    assertThat(totest.getRecipientPhoneNumber(), is(equalTo(persistent.getRecipientPhoneNumber())));
    assertThat(totest.getInformDate(), is(equalTo(df.format(persistent.getInformDate()))));
    assertThat(totest.getRecipientPositionTitleDesc(),
        is(equalTo(persistent.getRecipientPositionTitleDesc())));
    assertThat(totest.getReferenceNumber(), is(equalTo(persistent.getReferenceNumber())));
    assertThat(totest.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(totest.getLawEnforcementId(), is(equalTo(persistent.getLawEnforcementId())));
    assertThat(totest.getStaffPersonId(), is(equalTo(persistent.getStaffPersonId())));
    assertThat(totest.getDescription(), is(equalTo(persistent.getDescription())));
    assertThat(totest.getRecipientName(), is(equalTo(persistent.getRecipientName())));
    assertThat(totest.getOutStateLawEnforcementAddr(),
        is(equalTo(persistent.getOutStateLawEnforcementAddr())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(totest.getLawEnforcementIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getLawEnforcementIndicator()))));
    assertThat(totest.getOutStateLawEnforcementIndicator(), is(
        equalTo(DomainChef.uncookBooleanString(persistent.getOutStateLawEnforcementIndicator()))));
    assertThat(totest.getSatisfyCrossReportIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getSatisfyCrossReportIndicator()))));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    CrossReport domain = new CrossReport(thirdId, crossReportMethodType, filedOutOfStateIndicator,
        governmentOrgCrossRptIndicatorVar, informTime, recipientBadgeNumber,
        recipientPhoneExtensionNumber, recipientPhoneNumber, informDate, recipientPositionTitleDesc,
        referenceNumber, referralId, lawEnforcementId, staffPersonId, description, recipientName,
        outStateLawEnforcementAddr, countySpecificCode, lawEnforcementIndicator,
        outStateLawEnforcementIndicator, satisfyCrossReportIndicator);

    assertThat(domain.getThirdId(), is(equalTo(thirdId)));
    assertThat(domain.getCrossReportMethodType(), is(equalTo(crossReportMethodType)));
    assertThat(domain.getFiledOutOfStateIndicator(), is(equalTo(filedOutOfStateIndicator)));
    assertThat(domain.getGovernmentOrgCrossRptIndicatorVar(),
        is(equalTo(governmentOrgCrossRptIndicatorVar)));
    assertThat(domain.getInformTime(), is(equalTo(informTime)));
    assertThat(domain.getRecipientBadgeNumber(), is(equalTo(recipientBadgeNumber)));
    assertThat(domain.getRecipientPhoneExtensionNumber(),
        is(equalTo(recipientPhoneExtensionNumber)));
    assertThat(domain.getRecipientPhoneNumber(), is(equalTo(recipientPhoneNumber)));
    assertThat(domain.getInformDate(), is(equalTo(informDate)));
    assertThat(domain.getRecipientPositionTitleDesc(), is(equalTo(recipientPositionTitleDesc)));
    assertThat(domain.getReferenceNumber(), is(equalTo(referenceNumber)));
    assertThat(domain.getReferralId(), is(equalTo(referralId)));
    assertThat(domain.getLawEnforcementId(), is(equalTo(lawEnforcementId)));
    assertThat(domain.getStaffPersonId(), is(equalTo(staffPersonId)));
    assertThat(domain.getDescription(), is(equalTo(description)));
    assertThat(domain.getRecipientName(), is(equalTo(recipientName)));
    assertThat(domain.getOutStateLawEnforcementAddr(), is(equalTo(outStateLawEnforcementAddr)));
    assertThat(domain.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(domain.getLawEnforcementIndicator(), is(equalTo(lawEnforcementIndicator)));
    assertThat(domain.getOutStateLawEnforcementIndicator(),
        is(equalTo(outStateLawEnforcementIndicator)));
    assertThat(domain.getSatisfyCrossReportIndicator(), is(equalTo(satisfyCrossReportIndicator)));
  }

  @Test
  public void testCreateWithDefaultCreatesWithValues() {
    String id = "id";
    String referralId = "referralId";
    String staffId = "staffId";
    String outStateLawEnforcementAddr = "Law Enforcement Address";
    String countyCode = "countyCode";
    Boolean lawEnforcementIndicator = true;
    Boolean outStateLawEnforcementIndicator = true;
    String countyId = "34";

    String informDateTime = "1992-03-05T05:45:34";
    String informDate = "1992-03-05";
    String informTime = "05:45:34";
    gov.ca.cwds.rest.api.domain.CrossReport nsCrossReport =
        new gov.ca.cwds.rest.api.domain.CrossReport(id, "legacy_source_table", "legacy_id",
            filedOutOfStateIndicator, 2095, informDateTime, countyId, Sets.newHashSet());

    CrossReport cmsCrossReport = CrossReport.createWithDefaults(id, nsCrossReport, referralId,
        staffId, outStateLawEnforcementAddr, lawEnforcementId, countyId,
        outStateLawEnforcementIndicator, governmentOrgCrossRptIndicatorVar);
    assertEquals("Expected id field to be initialized with values", id,
        cmsCrossReport.getThirdId());
    assertEquals("Expected InformDate field to be initialized with values",
            informDate, cmsCrossReport.getInformDate());
    assertEquals("Expected InformDate field to be initialized with values",
            informTime, cmsCrossReport.getInformTime());
    assertEquals("Expected referralId field to be initialized with values", referralId,
        cmsCrossReport.getReferralId());
    assertEquals("Expected staffId field to be initialized with values", staffId,
        cmsCrossReport.getStaffPersonId());
    assertEquals("Expected outStateLawEnforcementAddr field to be initialized with values",
        outStateLawEnforcementAddr, cmsCrossReport.getOutStateLawEnforcementAddr());
    assertEquals("Expected lawEnforcementIndicator field to be initialized with values",
        lawEnforcementIndicator, cmsCrossReport.getLawEnforcementIndicator());
    assertEquals("Expected  field to be initialized with default values", true,
        cmsCrossReport.getFiledOutOfStateIndicator());
    assertEquals("Expected  field to be initialized with default values", true,
        cmsCrossReport.getOutStateLawEnforcementIndicator());
  }

  @Test
  public void testCreateWithDefaultCreatesWithDefaultValues() {
    String id = "id";
    String referralId = "referralId";
    String staffId = "staffId";
    String outStateLawEnforcementAddr = "Law Enforcement Address";
    Boolean lawEnforcementIndicator = true;
    Boolean outStateLawEnforcementIndicator = true;
    String countyId = "1068";

    String informDateTime = "1992-03-05T05:45:34";
    String informTime = "05:45:34";
    gov.ca.cwds.rest.api.domain.CrossReport nsCrossReport =
        new gov.ca.cwds.rest.api.domain.CrossReport(id, "legacy_source_table", "legacy_id",
            filedOutOfStateIndicator, 2095, informDateTime, countyId, Sets.newHashSet());

    CrossReport cmsCrossReport = CrossReport.createWithDefaults(id, nsCrossReport, referralId,
        staffId, outStateLawEnforcementAddr, lawEnforcementId, countyId,
        outStateLawEnforcementIndicator, governmentOrgCrossRptIndicatorVar);
     assertEquals("Expected  field to be initialized with default values", false,
        cmsCrossReport.getGovernmentOrgCrossRptIndicatorVar());
    assertEquals("Expected  field to be initialized with default values", informTime,
        cmsCrossReport.getInformTime());
    assertEquals("Expected  field to be initialized with default values", "",
        cmsCrossReport.getRecipientBadgeNumber());
    assertEquals("Expected  field to be initialized with default values", new Integer(0),
        cmsCrossReport.getRecipientPhoneExtensionNumber());
    assertEquals("Expected  field to be initialized with default values", new Long(0),
        cmsCrossReport.getRecipientPhoneNumber());
    assertEquals("Expected  field to be initialized with default values", "",
        cmsCrossReport.getRecipientPositionTitleDesc());
    assertEquals("Expected  field to be initialized with default values", "",
        cmsCrossReport.getReferenceNumber());
    assertEquals("Expected  field to be initialized with default values", "",
        cmsCrossReport.getRecipientName());
    assertEquals("Expected  field to be initialized with default values", false,
        cmsCrossReport.getSatisfyCrossReportIndicator());

  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(CrossReport.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid.json"), CrossReport.class));

    assertThat(MAPPER.writeValueAsString(validCrossReport()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/valid.json"),
        CrossReport.class), is(equalTo(validCrossReport())));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    CrossReport crossReport = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/optionalsNotIncluded.json"),
        CrossReport.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
    }
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * thirdId Tests
   */
  @Test
  public void failsWhenThirdIdNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setThirdId(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("thirdId may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenThirdIdTooLong() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setThirdId("12345678ABC").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("thirdId size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * crossReportMethodType Tests
   */
  @Test
  public void failsWhenCrossReportMethodTypeNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setCrossReportMethodType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("crossReportMethodType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * filedOutOfStateIndicator Tests
   */
  @Test
  public void failsWhenFiledOutOfStateIndicatorNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setFiledOutOfStateIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("filedOutOfStateIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

   /*
   * governmentOrgCrossRptIndicatorVar Tests
   */
  @Test
  public void failsWhenGovernmentOrgCrossRptIndicatorVarNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setGovernmentOrgCrossRptIndicatorVar(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("governmentOrgCrossRptIndicatorVar may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * informTime Tests
   */
  @Test
  public void successWhenInformTimeNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setInformTime(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenInformTimeWrongFormat() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setInformTime("59:32:24").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("informTime must be in the format of HH:mm:ss")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * recipientBadgeNumber Tests
   */
  @Test
  public void failsWhenRecipientBadgeNumberNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setRecipientBadgeNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("recipientBadgeNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

   @Test
  public void failsWhenRecipientBadgeNumberTooLong() throws Exception {
     CrossReport crossReport = new CmsCrossReportResourceBuilder().setRecipientBadgeNumber("1234567").build();
     validator = Validation.buildDefaultValidatorFactory().getValidator();
     messageBuilder.addDomainValidationError(validator.validate(crossReport));
     Boolean theErrorDetected = false;

     List<ErrorMessage> validationErrors = messageBuilder.getMessages();
     for (ErrorMessage message : validationErrors) {
//       System.out.println(message.getMessage());
       if (message.getMessage().equals("recipientBadgeNumber size must be between 0 and 6")) {
         theErrorDetected = true;
       }
     }
     assertThat(theErrorDetected, is(true));
  }

  /*
   * recipientPhoneExtensionNumber Tests
   */
  @Test
  public void failsWhenRecipientPhoneExtensionNumberNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setRecipientPhoneExtensionNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("recipientPhoneExtensionNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * informDate Tests
   */
  @Test
  public void failsWhenInformDateNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setInformDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("informDate may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenInformDateWrongFormat() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setInformDate("10-01-2017").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("informDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * recipientPositionTitleDesc Tests
   */
  @Test
  public void failsWhenRecipientPositionTitleDescNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setRecipientPositionTitleDesc(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("recipientPositionTitleDesc may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }


  @Test
  public void failsWhenRecipientPositionTitleDescTooLong() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setRecipientPositionTitleDesc("0123456789012345678901234567890").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("recipientPositionTitleDesc size must be between 0 and 30")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * referenceNumber Tests
   */
  @Test
  public void failsWhenReferenceNumberNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setReferenceNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referenceNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenReferenceNumberTooLong() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setReferenceNumber("01234567890").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referenceNumber size must be between 0 and 10")) {
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
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setReferralId(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referralId may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenReferralIdTooLong() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setReferralId("12345678ABC").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
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
  public void successWhenLawEnforcementIdNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setLawEnforcementId(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenLawEnforcementIdTooLong() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setLawEnforcementId("12345678ABC").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("lawEnforcementId size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * staffPersonId Tests
   */
  @Test
  public void failsWhenStaffPersonIdNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setStaffPersonId(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("staffPersonId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenStaffPersonIdTooLong() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setStaffPersonId("1234").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("staffPersonId size must be between 3 and 3")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenStaffPersonIdTooShort() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setStaffPersonId("12").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("staffPersonId size must be between 3 and 3")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * description Tests
   */
  @Test
  public void failsWhenDescriptionNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setDescription(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("description may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * recipientName Tests
   */
  @Test
  public void failsWhenRecipientNameNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setRecipientName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("recipientName may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
   }

  @Test
  public void failsWhenRecipientNameTooLong() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setRecipientName("12345678901234567890123456789012345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("recipientName size must be between 0 and 40")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * outStateLawEnforcementAddr Tests
   */
  @Test
  public void failsWhenOutstateLawEnforcementAddrNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setOutStateLawEnforcementAddr(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("outStateLawEnforcementAddr may not be null")) {
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
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setCountySpecificCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCountySpecificCodeTooLong() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setCountySpecificCode("123").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode size must be between 1 and 2")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * lawEnforcementIndicator Tests
   */

  @Test
  public void failsWhenLawEnforcementIndicatorNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setLawEnforcementIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("lawEnforcementIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * outStateLawEnforcementIndicator Tests
   */
  @Test
  public void failsWhenOutStateLawEnforcementIndicatorNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setOutStateLawEnforcementIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("outStateLawEnforcementIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * satisfyCrossReportIndicator Tests
   */
  @Test
  public void failsWhenSatisfyCrossReportIndicatorNull() throws Exception {
    CrossReport crossReport = new CmsCrossReportResourceBuilder().setSatisfyCrossReportIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(crossReport));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("satisfyCrossReportIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testValidCrossReportMethod() throws Exception {
    Short validCrossReportMethod = 2095;
    gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport =
        new gov.ca.cwds.rest.api.domain.cms.CrossReport("thirdId", validCrossReportMethod,
            Boolean.FALSE, Boolean.FALSE, "16:41:49", "ABC123", 123, 1234567L,
            "2000-01-01", "recipientPositionTitleDesc", "ABC123", "ABC1234567", "ABC1234567", "ABC",
            "description", "recipientName", "outStateLawEnforcementAddr", "AA", Boolean.FALSE,
            Boolean.FALSE, Boolean.FALSE);

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<gov.ca.cwds.rest.api.domain.cms.CrossReport>> constraintViolations =
        validator.validate(crossReport);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testInvalidCrossReportMethod() throws Exception {
    Short invalidCrossReportMethod = 9999;
    gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport =
        new gov.ca.cwds.rest.api.domain.cms.CrossReport("thirdId", invalidCrossReportMethod,
            Boolean.FALSE, Boolean.FALSE, "16:41:49", "ABC123", 123, 1234567L,
            "2000-01-01", "recipientPositionTitleDesc", "ABC123", "ABC1234567", "ABC1234567", "ABC",
            "description", "recipientName", "outStateLawEnforcementAddr", "AA", Boolean.FALSE,
            Boolean.FALSE, Boolean.FALSE);

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<gov.ca.cwds.rest.api.domain.cms.CrossReport>> constraintViolations =
        validator.validate(crossReport);
    assertEquals(1, constraintViolations.size());
  }

  /*
   * Utils
   */
  private CrossReport validCrossReport() throws Exception {

    CrossReport validCrossReport = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid.json"), CrossReport.class);
    return validCrossReport;
  }
}
