package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import gov.ca.cwds.fixture.SpecialProjectReferralEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.messages.MessageBuilder;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class SpecialProjectReferralTest {
  private static final String DATE_FORMAT = "yyyy-MM-dd";
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

  private static final ObjectMapper MAPPER = 
      Jackson.newObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);

  private String countySpecificCode = "27";
  private String referralId = "1234567ABC";
  private String specialProjectId = "2345678ABC";
  private String participationEndDate = null;
  private String participationStartDate = "2018-05-30";
  private Boolean safelySurrenderedBabiesIndicator = Boolean.FALSE;
  private String id = "3456789ABC";

  private MessageBuilder messageBuilder;
  private Validator validator;

  @Before
  public void setup() throws Exception {
    
    messageBuilder = new MessageBuilder();
  }

    @Test
  public void testConstructor() throws Exception {
    SpecialProjectReferral spr = new SpecialProjectReferral(countySpecificCode, 
        referralId, specialProjectId, participationEndDate, participationStartDate,
        safelySurrenderedBabiesIndicator);
    assertThat(spr.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(spr.getReferralId(), is(equalTo(referralId)));
    assertThat(spr.getSpecialProjectId(), is(equalTo(specialProjectId)));
    assertThat(spr.getParticipationEndDate(), is(equalTo(participationEndDate)));
    assertThat(spr.getParticipationStartDate(), is(equalTo(participationStartDate)));
    assertThat(spr.getSafelySurrenderedBabiesIndicator(), is(equalTo(safelySurrenderedBabiesIndicator)));
  }
  
  @Test
  public void testConstructorWithPersistentObject() throws Exception {
    gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral persistent = 
        new SpecialProjectReferralEntityBuilder().build();
    SpecialProjectReferral spr = new SpecialProjectReferral(persistent);
    assertThat(spr.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(spr.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(spr.getSpecialProjectId(), is(equalTo(persistent.getSpecialProjectId())));
    assertThat(spr.getParticipationEndDate(), is(equalTo((DomainChef.cookLocalDate(persistent.getPartEndDate())))));
    assertThat(spr.getParticipationStartDate(), is(equalTo(DomainChef.cookLocalDate(persistent.getPartStartDate()))));
    assertThat(spr.getSafelySurrenderedBabiesIndicator(), is(equalTo(persistent.getSsbIndicator())));
  }

  @Test
  public void shouldSetIdWhenPostedSpecialProjectCreated() throws Exception {
    gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral persistent = 
        new SpecialProjectReferralEntityBuilder().build();
    SpecialProjectReferral pspr = new SpecialProjectReferral(persistent);
    assertThat(pspr.getId(), is(equalTo(persistent.getId())));    
  }
  
  @Test
  public void testDefaultConstructor() throws Exception {
    assertThat(SpecialProjectReferral.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(SpecialProjectReferral.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void serializesToJSON() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder().build();
    String sprString = MAPPER.writeValueAsString(spr);
    SpecialProjectReferral deserialized = MAPPER.readValue(fixture("fixtures/domain/cms/SpecialProjectReferral/valid.json"), SpecialProjectReferral.class);
    String deserializedString = MAPPER.writeValueAsString(deserialized);
    assertThat(sprString, is(equalTo(deserializedString)));
  }

//  @Test
//  public void deserializeFromJSON() throws Exception {
//    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder().build();
//    SpecialProjectReferral deserialized = MAPPER.readValue(fixture("fixtures/domain/cms/SpecialProjectReferral/valid.json"), SpecialProjectReferral.class);
//    assertThat(spr, is(equalTo(deserialized)));
//  }
  
  @Test
  public void successWithValidInput() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder().build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(spr));
  }
  
  @Test
  public void shouldFailWhenReferralIdIsBlank() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder()
        .setReferralId(null)
        .build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(spr));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referralId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));   
  }
  
  @Test
  public void shouldFailWhenReferralIdIsShort() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder()
        .setReferralId("123456789")
        .build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(spr));
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
  
  @Test
  public void shouldFailWhenReferralIdIsLong() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder()
        .setReferralId("12345678901")
        .build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(spr));
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
  
  @Test
  public void shouldFailWhenSpecialProjectIdIsBlank() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder()
        .setSpecialProjectId(null)
        .build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(spr));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("specialProjectId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));       
  }
  
  @Test
  public void shouldFailWhenSpecialProjectIdIsShort() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder()
        .setSpecialProjectId("123456789")
        .build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(spr));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("specialProjectId size must be between 10 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));       
  }
  @Test
  public void shouldFailWhenSpecialProjectIdIsLong() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder()
        .setSpecialProjectId("12345678901")
        .build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(spr));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("specialProjectId size must be between 10 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));       
  }
  
  @Test
  public void shouldFailWhenCountySpecificCodeIsBlank() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder()
        .setCountySpecificCode(null)
        .build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(spr));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));       
  }
  
  @Test
  public void shouldFailWhenCountySpecificCodeIsLong() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder()
        .setCountySpecificCode("ABC")
        .build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(spr));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode size must be between 0 and 2")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));       
   }
  
  @Test
  public void shouldFailWhenParticipationStartDateIsBlank() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder()
        .setParticipationStartDate(null)
        .build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(spr));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("participationStartDate may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));           
  }

  @Test
  public void shouldFailWhenParticipationStartDateIsInvalid() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder()
        .setParticipationStartDate("12-01-2018")
        .build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(spr));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("participationStartDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));               
  }
  
  @Test
  public void shouldFailWhenSafelySurrenderedBabiesIndicatorIsNull() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder()
        .setSafelySurrenderedBabiesIndicator(null)
        .build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(spr));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("safelySurrenderedBabiesIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));                   
  }
  
  @Test
  public void testSetters() throws Exception {
    String newDateString = "2018-06-13";
    SpecialProjectReferral spr = new SpecialProjectReferral();
    spr.setCountySpecificCode(countySpecificCode);
    assertThat(spr.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    spr.setParticipantEndDate(newDateString);
    assertThat(spr.getParticipationEndDate(), is(equalTo(newDateString)));
    spr.setParticipantStartDate(newDateString);
    assertThat(spr.getParticipationStartDate(), is(equalTo(newDateString)));
    spr.setReferralId(referralId);
    assertThat(spr.getReferralId(), is(equalTo(referralId)));
    spr.setSafelySurrenderedBabiesIndicator(safelySurrenderedBabiesIndicator);
    assertThat(spr.getSafelySurrenderedBabiesIndicator(), is(equalTo(safelySurrenderedBabiesIndicator)));
    spr.setSpecialProjectId(specialProjectId);
    assertThat(spr.getSpecialProjectId(), is(equalTo(specialProjectId)));
  }
}
