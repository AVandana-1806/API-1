package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.api.domain.junit.template.DomainTestTemplate;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ClientTest implements DomainTestTemplate {

  /**
   * Initialize system code cache
   */
  @SuppressWarnings("unused")
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime1 = new Date();

  private String existingClientId = "ABC1234567";
  private DateTime lastUpdatedTime = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
      .parseDateTime("2004-03-31T09:45:58.000-0800");
  private Boolean adjudicatedDelinquentIndicator = Boolean.FALSE;
  private String adoptionStatusCode = "A";
  private String alienRegistrationNumber = "";
  private String birthCity = "Coletown";
  private Short birthCountryCodeType = 563;
  private String birthDate = "2007-01-31";
  private String birthFacilityName = "Mercy";
  private Short birthStateCodeType = 1829;
  private Boolean birthplaceVerifiedIndicator = Boolean.FALSE;
  private Boolean childClientIndicatorVar = Boolean.FALSE;
  private String clientIndexNumber = "123456789012";
  private String commentDescription = "client description";
  private String commonFirstName = "fname";
  private String commonLastName = "lname";
  private String commonMiddleName = "mname";
  private String confidentialityActionDate = null;
  private Boolean confidentialityInEffectIndicator = Boolean.FALSE;
  private String creationDate = "2016-10-31";
  private Boolean currCaChildrenServIndicator = Boolean.FALSE;
  private String currentlyOtherDescription = "other description";
  private Boolean currentlyRegionalCenterIndicator = Boolean.FALSE;
  private String deathDate = null;
  private Boolean deathDateVerified = Boolean.FALSE;
  private String deathPlace = "";
  private String deathReasonText = "";
  private String driversLicenseNumber = "C87634563";
  private Short driversLicenseStateCodeType = 1829;
  private String emailAddress = "anyone@yahoo.com";
  private String estimatedDateOfBirthCode = "Y";
  private String ethUnableToDetReasonCode = "K";
  private String fatherParentalRightTermDate = "";
  private String genderCode = "M";
  private Short genderIdentityType = 7075;
  private String giNotListedDescription = "some descrption";
  private Short genderExpressionType = 7081;
  private String healthSummaryText = "good";
  private String hispUnableToDetReasonCode = "";
  private String hispanicOriginCode = "X";
  private String id = "1234567ABC";
  private Short immigrationCountryCodeType = 0;
  private Short immigrationStatusType = 1199;
  private String incapcitatedParentCode = "N";
  private Boolean individualHealthCarePlanIndicator = Boolean.FALSE;
  private Boolean limitationOnScpHealthIndicator = Boolean.FALSE;
  private String literateCode = "Y";
  private Boolean maritalCohabitatnHstryIndicatorVar = Boolean.FALSE;
  private Short maritalStatusType = 1308;
  private String militaryStatusCode = "N";
  private String motherParentalRightTermDate = null;
  private String namePrefixDescription = "";
  private Short nameType = 0;
  private Boolean outstandingWarrantIndicator = Boolean.FALSE;
  private Boolean prevCaChildrenServIndicator = Boolean.FALSE;
  private String prevOtherDescription = "the previous description";
  private Boolean prevRegionalCenterIndicator = Boolean.FALSE;
  private Short primaryEthnicityType = 839;
  private Short primaryLanguageType = 1253;
  private Short religionType = 0;
  private Short secondaryLanguageType = 0;
  private Boolean sensitiveHlthInfoOnFileIndicator = Boolean.FALSE;
  private String sensitivityIndicator = "N";
  private Short sexualOrientationType = 7066;
  private String soUnableToDetermineCode = "D";
  private String soNotListedDescrption = "Sample Descrpion";
  private String soc158PlacementCode = "N";
  private Boolean soc158SealedClientIndicator = Boolean.FALSE;
  private String socialSecurityNumChangedCode = "N";
  private String socialSecurityNumber = "111122333";
  private String suffixTitleDescription = "MR";
  private Boolean tribalAncestryClientIndicatorVar = Boolean.FALSE;
  private Boolean tribalMembrshpVerifctnIndicatorVar = Boolean.FALSE;
  private String unemployedParentCode = "N";
  private Boolean zippyCreatedIndicator = Boolean.FALSE;
  private Boolean reporterConfidentialWaiver = Boolean.FALSE;
  private String reporterEmployerName = "Employer Name";
  private Boolean clientStaffPersonAdded = Boolean.FALSE;

  private MessageBuilder messageBuilder;
  private Validator validator;

  @Override
  @Before
  public void setup() throws Exception {
    messageBuilder = new MessageBuilder();

    CrudsDao crudsDao = mock(CrudsDao.class);
    when(crudsDao.find(any())).thenReturn(mock(gov.ca.cwds.data.persistence.cms.Client.class));

    Client validClient = validClient();
  }

  @Override
  public void teardown() throws Exception {

  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    Client domain = new Client(existingClientId, lastUpdatedTime, adjudicatedDelinquentIndicator,
        adoptionStatusCode, alienRegistrationNumber, birthCity, birthCountryCodeType, birthDate,
        birthFacilityName, birthStateCodeType, birthplaceVerifiedIndicator, childClientIndicatorVar,
        clientIndexNumber, commentDescription, commonFirstName, commonMiddleName, commonLastName,
        confidentialityActionDate, confidentialityInEffectIndicator, creationDate,
        currCaChildrenServIndicator, currentlyOtherDescription, currentlyRegionalCenterIndicator,
        deathDate, deathDateVerified, deathPlace, deathReasonText, driversLicenseNumber,
        driversLicenseStateCodeType, emailAddress, estimatedDateOfBirthCode,
        ethUnableToDetReasonCode, fatherParentalRightTermDate, genderCode, genderIdentityType,
        giNotListedDescription, genderExpressionType, healthSummaryText, hispUnableToDetReasonCode,
        hispanicOriginCode, immigrationCountryCodeType, immigrationStatusType,
        incapcitatedParentCode, individualHealthCarePlanIndicator, limitationOnScpHealthIndicator,
        literateCode, maritalCohabitatnHstryIndicatorVar, maritalStatusType, militaryStatusCode,
        motherParentalRightTermDate, namePrefixDescription, nameType, outstandingWarrantIndicator,
        prevCaChildrenServIndicator, prevOtherDescription, prevRegionalCenterIndicator,
        primaryEthnicityType, primaryLanguageType, religionType, secondaryLanguageType,
        sensitiveHlthInfoOnFileIndicator, sensitivityIndicator, sexualOrientationType,
        soUnableToDetermineCode, soNotListedDescrption, soc158PlacementCode,
        soc158SealedClientIndicator, socialSecurityNumChangedCode, socialSecurityNumber,
        suffixTitleDescription, tribalAncestryClientIndicatorVar,
        tribalMembrshpVerifctnIndicatorVar, unemployedParentCode, zippyCreatedIndicator, null);
    gov.ca.cwds.data.persistence.cms.Client persistent =
        new gov.ca.cwds.data.persistence.cms.Client(id, domain, lastUpdatedId, lastUpdatedTime1);

    assertThat(persistent.getAdjudicatedDelinquentIndicator(),
        is(equalTo(DomainChef.cookBoolean(adjudicatedDelinquentIndicator))));
    assertThat(persistent.getAdoptionStatusCode(), is(equalTo(adoptionStatusCode)));
    assertThat(persistent.getAlienRegistrationNumber(), is(equalTo(alienRegistrationNumber)));
    assertThat(persistent.getBirthCity(), is(equalTo(birthCity)));
    assertThat(persistent.getBirthCountryCodeType(), is(equalTo(birthCountryCodeType)));
    assertThat(persistent.getBirthDate(), is(equalTo(DomainChef.uncookDateString(birthDate))));
    assertThat(persistent.getBirthFacilityName(), is(equalTo(birthFacilityName)));
    assertThat(persistent.getBirthStateCodeType(), is(equalTo(birthStateCodeType)));
    assertThat(persistent.getBirthplaceVerifiedIndicator(),
        is(equalTo(DomainChef.cookBoolean(birthplaceVerifiedIndicator))));
    assertThat(persistent.getChildClientIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(childClientIndicatorVar))));
    assertThat(persistent.getClientIndexNumber(), is(equalTo(clientIndexNumber)));
    assertThat(persistent.getCommentDescription(), is(equalTo(commentDescription)));
    assertThat(persistent.getCommonFirstName(), is(equalTo(commonFirstName)));
    assertThat(persistent.getCommonLastName(), is(equalTo(commonLastName)));
    assertThat(persistent.getCommonMiddleName(), is(equalTo(commonMiddleName)));
    assertThat(persistent.getConfidentialityActionDate(), is(equalTo(confidentialityActionDate)));
    assertThat(persistent.getConfidentialityInEffectIndicator(),
        is(equalTo(DomainChef.cookBoolean(confidentialityInEffectIndicator))));
    assertThat(persistent.getCreationDate(),
        is(equalTo(DomainChef.uncookDateString(creationDate))));
    assertThat(persistent.getCurrCaChildrenServIndicator(),
        is(equalTo(DomainChef.cookBoolean(currCaChildrenServIndicator))));
    assertThat(persistent.getCurrentlyOtherDescription(), is(equalTo(currentlyOtherDescription)));
    assertThat(persistent.getCurrentlyRegionalCenterIndicator(),
        is(equalTo(DomainChef.cookBoolean(currentlyRegionalCenterIndicator))));
    assertThat(persistent.getDeathDate(), is(equalTo(DomainChef.uncookDateString(deathDate))));
    assertThat(persistent.getDeathDateVerifiedIndicator(),
        is(equalTo(DomainChef.cookBoolean(deathDateVerified))));
    assertThat(persistent.getDeathReasonText(), is(equalTo(deathReasonText)));
    assertThat(persistent.getDriverLicenseNumber(), is(equalTo(driversLicenseNumber)));
    assertThat(persistent.getDriverLicenseStateCodeType(),
        is(equalTo(driversLicenseStateCodeType)));
    assertThat(persistent.getEmailAddress(), is(equalTo(emailAddress)));
    assertThat(persistent.getEstimatedDobCode(), is(equalTo(estimatedDateOfBirthCode)));
    assertThat(persistent.getEthUnableToDetReasonCode(), is(equalTo(ethUnableToDetReasonCode)));
    assertThat(persistent.getFatherParentalRightTermDate(),
        is(equalTo(DomainChef.uncookDateString(fatherParentalRightTermDate))));
    assertThat(persistent.getGenderCode(), is(equalTo(genderCode)));
    assertThat(persistent.getHealthSummaryText(), is(equalTo(healthSummaryText)));
    assertThat(persistent.getHispUnableToDetReasonCode(), is(equalTo(hispUnableToDetReasonCode)));
    assertThat(persistent.getHispanicOriginCode(), is(equalTo(hispanicOriginCode)));
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getImmigrationCountryCodeType(), is(equalTo(immigrationCountryCodeType)));
    assertThat(persistent.getIncapacitatedParentCode(), is(equalTo(incapcitatedParentCode)));
    assertThat(persistent.getIndividualHealthCarePlanIndicator(),
        is(equalTo(DomainChef.cookBoolean(individualHealthCarePlanIndicator))));
    assertThat(persistent.getLimitationOnScpHealthIndicator(),
        is(equalTo(DomainChef.cookBoolean(limitationOnScpHealthIndicator))));
    assertThat(persistent.getLiterateCode(), is(equalTo(literateCode)));
    assertThat(persistent.getMaritalCohabitatnHstryIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(maritalCohabitatnHstryIndicatorVar))));
    assertThat(persistent.getMaritalStatusType(), is(equalTo(maritalStatusType)));
    assertThat(persistent.getMilitaryStatusCode(), is(equalTo(militaryStatusCode)));
    assertThat(persistent.getMotherParentalRightTermDate(),
        is(equalTo(DomainChef.uncookDateString(motherParentalRightTermDate))));
    assertThat(persistent.getNamePrefixDescription(), is(equalTo(namePrefixDescription)));
    assertThat(persistent.getNameType(), is(equalTo(nameType)));
    assertThat(persistent.getOutstandingWarrantIndicator(),
        is(equalTo(DomainChef.cookBoolean(outstandingWarrantIndicator))));
    assertThat(persistent.getPrevCaChildrenServIndicator(),
        is(equalTo(DomainChef.cookBoolean(prevCaChildrenServIndicator))));
    assertThat(persistent.getPrevOtherDescription(), is(equalTo(prevOtherDescription)));
    assertThat(persistent.getPrimaryEthnicityType(), is(equalTo(primaryEthnicityType)));
    assertThat(persistent.getPrimaryLanguageType(), is(equalTo(primaryLanguageType)));
    assertThat(persistent.getReligionType(), is(equalTo(religionType)));
    assertThat(persistent.getSecondaryLanguageType(), is(equalTo(secondaryLanguageType)));
    assertThat(persistent.getSensitiveHlthInfoOnFileIndicator(),
        is(equalTo(DomainChef.cookBoolean(sensitiveHlthInfoOnFileIndicator))));
    assertThat(persistent.getSensitivityIndicator(), is(equalTo(sensitivityIndicator)));
    assertThat(persistent.getSoc158PlacementCode(), is(equalTo(soc158PlacementCode)));
    assertThat(persistent.getSoc158SealedClientIndicator(),
        is(equalTo(DomainChef.cookBoolean(soc158SealedClientIndicator))));
    assertThat(persistent.getSocialSecurityNumChangedCode(),
        is(equalTo(socialSecurityNumChangedCode)));
    assertThat(persistent.getSocialSecurityNumber(), is(equalTo(socialSecurityNumber)));
    assertThat(persistent.getSuffixTitleDescription(), is(equalTo(suffixTitleDescription)));
    assertThat(persistent.getTribalAncestryClientIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(tribalAncestryClientIndicatorVar))));
    assertThat(persistent.getUnemployedParentCode(), is(equalTo(unemployedParentCode)));
    assertThat(persistent.getZippyCreatedIndicator(),
        is(equalTo(DomainChef.cookBoolean(zippyCreatedIndicator))));

  }

  @Override
  @Test
  public void testJSONCreatorConstructor() throws Exception {
    Client vc = validClient();

    Client domain = new ClientResourceBuilder().build();

    assertThat(domain.getAdjudicatedDelinquentIndicator(),
        is(equalTo(vc.getAdjudicatedDelinquentIndicator())));
    assertThat(domain.getAdoptionStatusCode(), is(equalTo(vc.getAdoptionStatusCode())));
    assertThat(domain.getAlienRegistrationNumber(), is(equalTo(vc.getAlienRegistrationNumber())));
    assertThat(domain.getBirthCity(), is(equalTo(vc.getBirthCity())));
    assertThat(domain.getBirthCountryCodeType(), is(equalTo(vc.getBirthCountryCodeType())));
    assertThat(domain.getBirthDate(), is(equalTo(vc.getBirthDate())));
    assertThat(domain.getBirthFacilityName(), is(equalTo(vc.getBirthFacilityName())));
    assertThat(domain.getBirthStateCodeType(), is(equalTo(vc.getBirthStateCodeType())));
    assertThat(domain.getBirthplaceVerifiedIndicator(),
        is(equalTo(vc.getBirthplaceVerifiedIndicator())));
    assertThat(domain.getChildClientIndicatorVar(), is(equalTo(vc.getChildClientIndicatorVar())));
    assertThat(domain.getClientIndexNumber(), is(equalTo(vc.getClientIndexNumber())));
    assertThat(domain.getCommentDescription(), is(equalTo(vc.getCommentDescription())));
    assertThat(domain.getCommonFirstName(), is(equalTo(vc.getCommonFirstName())));
    assertThat(domain.getCommonMiddleName(), is(equalTo(vc.getCommonMiddleName())));
    assertThat(domain.getCommonLastName(), is(equalTo(vc.getCommonLastName())));
    assertThat(domain.getConfidentialityActionDate(),
        is(equalTo(vc.getConfidentialityActionDate())));
    assertThat(domain.getConfidentialityInEffectIndicator(),
        is(equalTo(vc.getConfidentialityInEffectIndicator())));
    assertThat(domain.getCreationDate(), is(equalTo(vc.getCreationDate())));
    assertThat(domain.getCurrCaChildrenServIndicator(),
        is(equalTo(vc.getCurrCaChildrenServIndicator())));
    assertThat(domain.getCurrentlyOtherDescription(),
        is(equalTo(vc.getCurrentlyOtherDescription())));
    assertThat(domain.getCurrentlyRegionalCenterIndicator(),
        is(equalTo(vc.getCurrentlyRegionalCenterIndicator())));
    assertThat(domain.getDeathDate(), is(equalTo(vc.getDeathDate())));
    assertThat(domain.getDeathDateVerifiedIndicator(),
        is(equalTo(vc.getDeathDateVerifiedIndicator())));
    assertThat(domain.getDeathReasonText(), is(equalTo(vc.getDeathReasonText())));
    assertThat(domain.getDriverLicenseNumber(), is(equalTo(vc.getDriverLicenseNumber())));
    assertThat(domain.getDriverLicenseStateCodeType(),
        is(equalTo(vc.getDriverLicenseStateCodeType())));
    assertThat(domain.getEmailAddress(), is(equalTo(vc.getEmailAddress())));
    assertThat(domain.getEstimatedDobCode(), is(equalTo(vc.getEstimatedDobCode())));
    assertThat(domain.getEthUnableToDetReasonCode(), is(equalTo(vc.getEthUnableToDetReasonCode())));
    assertThat(domain.getFatherParentalRightTermDate(),
        is(equalTo(vc.getFatherParentalRightTermDate())));
    assertThat(domain.getGenderCode(), is(equalTo(vc.getGenderCode())));
    assertThat(domain.getHealthSummaryText(), is(equalTo(vc.getHealthSummaryText())));
    assertThat(domain.getHispUnableToDetReasonCode(),
        is(equalTo(vc.getHispUnableToDetReasonCode())));
    assertThat(domain.getHispanicOriginCode(), is(equalTo(vc.getHispanicOriginCode())));
    assertThat(domain.getImmigrationCountryCodeType(),
        is(equalTo(vc.getImmigrationCountryCodeType())));
    assertThat(domain.getIncapacitatedParentCode(), is(equalTo(vc.getIncapacitatedParentCode())));
    assertThat(domain.getIndividualHealthCarePlanIndicator(),
        is(equalTo(vc.getIndividualHealthCarePlanIndicator())));
    assertThat(domain.getLimitationOnScpHealthIndicator(),
        is(equalTo(vc.getLimitationOnScpHealthIndicator())));
    assertThat(domain.getLiterateCode(), is(equalTo(vc.getLiterateCode())));
    assertThat(domain.getMaritalCohabitatnHstryIndicatorVar(),
        is(equalTo(vc.getMaritalCohabitatnHstryIndicatorVar())));
    assertThat(domain.getMaritalStatusType(), is(equalTo(vc.getMaritalStatusType())));
    assertThat(domain.getMilitaryStatusCode(), is(equalTo(vc.getMilitaryStatusCode())));
    assertThat(domain.getMotherParentalRightTermDate(),
        is(equalTo(vc.getMotherParentalRightTermDate())));
    assertThat(domain.getNamePrefixDescription(), is(equalTo(vc.getNamePrefixDescription())));
    assertThat(domain.getNameType(), is(equalTo(vc.getNameType())));
    assertThat(domain.getOutstandingWarrantIndicator(),
        is(equalTo(vc.getOutstandingWarrantIndicator())));
    assertThat(domain.getPrevCaChildrenServIndicator(),
        is(equalTo(vc.getPrevCaChildrenServIndicator())));
    assertThat(domain.getPrevOtherDescription(), is(equalTo(vc.getPrevOtherDescription())));
    assertThat(domain.getPrimaryEthnicityType(), is(equalTo(vc.getPrimaryEthnicityType())));
    assertThat(domain.getPrimaryLanguage(), is(equalTo(vc.getPrimaryLanguage())));
    assertThat(domain.getReligionType(), is(equalTo(vc.getReligionType())));
    assertThat(domain.getSecondaryLanguage(), is(equalTo(vc.getSecondaryLanguage())));
    assertThat(domain.getSensitiveHlthInfoOnFileIndicator(),
        is(equalTo(vc.getSensitiveHlthInfoOnFileIndicator())));
    assertThat(domain.getSensitivityIndicator(), is(equalTo(vc.getSensitivityIndicator())));
    assertThat(domain.getSoc158PlacementCode(), is(equalTo(vc.getSoc158PlacementCode())));
    assertThat(domain.getSoc158SealedClientIndicator(),
        is(equalTo(vc.getSoc158SealedClientIndicator())));
    assertThat(domain.getSocialSecurityNumChangedCode(),
        is(equalTo(vc.getSocialSecurityNumChangedCode())));
    assertThat(domain.getSocialSecurityNumber(), is(equalTo(vc.getSocialSecurityNumber())));
    assertThat(domain.getSuffixTitleDescription(), is(equalTo(vc.getSuffixTitleDescription())));
    assertThat(domain.getTribalAncestryClientIndicatorVar(),
        is(equalTo(vc.getTribalAncestryClientIndicatorVar())));
    assertThat(domain.getUnemployedParentCode(), is(equalTo(vc.getUnemployedParentCode())));
    assertThat(domain.getZippyCreatedIndicator(), is(equalTo(vc.getZippyCreatedIndicator())));

  }

  @Test
  public void testCreateWithDefaultCreatesWithValues() {
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");

    Participant participant = new Participant(1, "sourceTable", "clientId", new LegacyDescriptor(),
        "firstName", "middleName", "lastName", "jr", "gender", "ssn", "dob", primaryLanguageType,
        secondaryLanguageType, 4, reporterConfidentialWaiver, reporterEmployerName,
        clientStaffPersonAdded, sensitivityIndicator, "12", "Y", new HashSet<>(), new HashSet<>(),
        raceAndEthnicity, null);

    String genderCode = "male";
    String dateStarted = "now";
    Short raceCode = (short) 0;
    Boolean childClientIndicatorVar = Boolean.TRUE;

    Client client = Client.createWithDefaults(participant, dateStarted, genderCode, raceCode,
        childClientIndicatorVar);

    assertEquals("Expected BirthDate field to be initialized with values",
        participant.getDateOfBirth(), client.getBirthDate());
    assertEquals("Expected First Name field to be initialized with values",
        participant.getFirstName(), client.getCommonFirstName());
    assertEquals("Expected Middle Name field to be initialized with values",
        participant.getMiddleName(), client.getCommonMiddleName());
    assertEquals("Expected LastName field to be initialized with values", participant.getLastName(),
        client.getCommonLastName());
    assertEquals("Expected suffix name field to be initialized with values",
        participant.getNameSuffix(), client.getSuffixTitleDescription());
    assertEquals("Expected SSN field to be initialized with values", participant.getSsn(),
        client.getSocialSecurityNumber());
    assertEquals("Expected genderCode field to be initialized with values", genderCode,
        client.getGenderCode());
    assertEquals("Expected dateStarted field to be initialized with values", dateStarted,
        client.getCreationDate());
    assertEquals("Expected estimatedDobCode field to be initialized with N", Client.ESTIMATED_DOB_CODE_NO, client.getEstimatedDobCode());
    
  }

  @Test
  public void shouldAllowClientNamesToBeUpdatedAfterInitialization() {
    Participant participant = new ParticipantResourceBuilder().createParticipant();
    Client client = Client.createWithDefaults(participant, "", "", (short) 0, true);

    client.update("Barney", "middlestone", "Rubble", "jr", "F", "765489675", (short) 0, "A", "A",
        "X", "10-May-2017");

    assertEquals("Expected Client first name to have been changed", "Barney",
        client.getCommonFirstName());
    assertEquals("Expected Client middle name to have been changed", "middlestone",
        client.getCommonMiddleName());
    assertEquals("Expected Client last name to have been changed", "Rubble",
        client.getCommonLastName());
    assertEquals("Expected Client name suffix to have been changed", "jr",
        client.getSuffixTitleDescription());
  }

  @Test 
  public void shouldAllowClientBirthDateToBeUpdated() {
    Participant participant = new ParticipantResourceBuilder().setDateOfBirth("01-Jun-2010").createParticipant();
    Client client = Client.createWithDefaults(participant, "", "", (short) 0, true);

    client.update("Barney", "middlestone", "Rubble", "jr", "F", "765489675", (short) 0, "A", "A",
        "X", "10-May-2017");
    
    assertEquals("Expected Client birth date to have been changed", "10-May-2017", client.getBirthDate());
    
  }
  @Test
  public void testCreateWithDefaultCreatesWithDefaultValues() {
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");

    Participant participant = new Participant(1, "sourceTable", "clientId", new LegacyDescriptor(),
        "firstName", "middleName", "lastName", "", "gender", "ssn", "dob", primaryLanguageType,
        secondaryLanguageType, 4, reporterConfidentialWaiver, reporterEmployerName,
        clientStaffPersonAdded, sensitivityIndicator, "12", "Y", new HashSet<>(), new HashSet<>(),
        raceAndEthnicity, null);

    String genderCode = "male";
    String dateStarted = "now";
    Short raceCode = (short) 0;
    Boolean childClientIndicatorVar = Boolean.TRUE;

    Client client = Client.createWithDefaults(participant, dateStarted, genderCode, raceCode,
        childClientIndicatorVar);

    assertEquals("Expected existingClientId field to be initialized with default values", "",
        client.getExistingClientId());
    assertEquals(
        "Expected adjudicatedDelinquentIndicator field to be initialized with default values",
        false, client.getAdjudicatedDelinquentIndicator());
    assertEquals("Expected adoptionStatusCode field to be initialized with default values", "N",
        client.getAdoptionStatusCode());
    assertEquals("Expected alienRegistrationNumber field to be initialized with default values", "",
        client.getAlienRegistrationNumber());
    assertEquals("Expected birth city field to be initialized with default values", "",
        client.getBirthCity());
    assertEquals("Expected birthCountryCodeType field to be initialized with default values",
        new Short("0"), client.getBirthCountryCodeType());
    assertEquals("Expected birthFacilityName field to be initialized with default values", "",
        client.getBirthFacilityName());
    assertEquals("Expected birthStateCodeType field to be initialized with default values",
        new Short("0"), client.getBirthStateCodeType());
    assertEquals("Expected birthplaceVerifiedIndicator field to be initialized with default values",
        false, client.getBirthplaceVerifiedIndicator());
    assertEquals("Expected clientIndexNumber field to be initialized with default values", "",
        client.getClientIndexNumber());
    assertEquals("Expected commentDescription field to be initialized with default values", "",
        client.getCommentDescription());
    assertEquals("Expected confidentialityActionDate field to be initialized with default values",
        "", client.getConfidentialityActionDate());
    assertEquals(
        "Expected confidentialityInEffectIndicator field to be initialized with default values",
        false, client.getConfidentialityInEffectIndicator());
    assertEquals("Expected currCaChildrenServIndicator field to be initialized with default values",
        false, client.getCurrCaChildrenServIndicator());
    assertEquals("Expected currentlyOtherDescription field to be initialized with default values",
        "", client.getCurrentlyOtherDescription());
    assertEquals(
        "Expected currentlyRegionalCenterIndicato field to be initialized with default values",
        false, client.getCurrentlyRegionalCenterIndicator());
    assertEquals("Expected deathDate field to be initialized with default values", "",
        client.getDeathDate());
    assertEquals("Expected deathDateVerifiedIndicato field to be initialized with default values",
        false, client.getDeathDateVerifiedIndicator());
    assertEquals("Expected deathPlace field to be initialized with default values", "",
        client.getDeathPlace());
    assertEquals("Expected deathReasonText field to be initialized with default values", "",
        client.getDeathReasonText());
    assertEquals("Expected driverLicenseNumber field to be initialized with default values", "",
        client.getDriverLicenseNumber());
    assertEquals("Expected driverLicenseStateCodeType field to be initialized with default values",
        new Short("0"), client.getDriverLicenseStateCodeType());
    assertEquals("Expected emailAddress field to be initialized with default values", "",
        client.getEmailAddress());
    assertEquals("Expected estimatedDobCode field to be initialized with default values", "N",
        client.getEstimatedDobCode());
    assertEquals("Expected fatherParentalRightTermDate field to be initialized with default values",
        "", client.getFatherParentalRightTermDate());
    assertEquals("Expected healthSummaryText field to be initialized with default values", "",
        client.getHealthSummaryText());
    assertEquals("Expected immigrationCountryCodeTyp field to be initialized with default values",
        new Short("0"), client.getImmigrationCountryCodeType());
    assertEquals("Expected immigrationStatusType field to be initialized with default values",
        new Short("0"), client.getImmigrationStatusType());
    assertEquals("Expected incapacitatedParentCode field to be initialized with default values",
        "U", client.getIncapacitatedParentCode());
    assertEquals(
        "Expected individualHealthCarePlanIndicator field to be initialized with default values",
        false, client.getIndividualHealthCarePlanIndicator());
    assertEquals(
        "Expected limitationOnScpHealthIndicator field to be initialized with default values",
        false, client.getLimitationOnScpHealthIndicator());
    assertEquals("Expected literateCode field to be initialized with default values", "U",
        client.getLiterateCode());
    assertEquals(
        "Expected maritalCohabitatnHstryIndicatorVar field to be initialized with default values",
        false, client.getMaritalCohabitatnHstryIndicatorVar());
    assertEquals("Expected maritalStatusType field to be initialized with default values",
        new Short("0"), client.getMaritalStatusType());
    assertEquals("Expected militaryStatusCod field to be initialized with default values", "N",
        client.getMilitaryStatusCode());
    assertEquals("Expected motherParentalRightTermDate field to be initialized with default values",
        "", client.getMotherParentalRightTermDate());
    assertEquals("Expected namePrefixDescription field to be initialized with default values", "",
        client.getNamePrefixDescription());
    assertEquals("Expected nameType field to be initialized with default values", new Short("1313"),
        client.getNameType());
    assertEquals("Expected outstandingWarrantIndicator field to be initialized with default values",
        false, client.getOutstandingWarrantIndicator());
    assertEquals("Expected prevCaChildrenServIndicator field to be initialized with default values",
        false, client.getPrevCaChildrenServIndicator());
    assertEquals("Expected prevOtherDescription field to be initialized with default values", "",
        client.getPrevOtherDescription());
    assertEquals("Expected prevRegionalCenterIndicato field to be initialized with default values",
        false, client.getPrevRegionalCenterIndicator());
    assertEquals("Expected religionType field to be initialized with default values",
        new Short("0"), client.getReligionType());
    assertEquals(
        "Expected sensitiveHlthInfoOnFileIndicator field to be initialized with default values",
        false, client.getSensitiveHlthInfoOnFileIndicator());
    assertEquals("Expected sensitivityIndicator field to be initialized with default values", "N",
        client.getSensitivityIndicator());
    assertEquals("Expected genderIdentityType field to be initialized with default values",
        new Short("7075"), client.getGenderIdentityType());
    assertEquals("Expected genderExpressionType field to be initialized with default values",
        new Short("7081"), client.getGenderExpressionType());
    assertEquals("Expected sexualOrientationType field to be initialized with default values",
        new Short("7066"), client.getSexualOrientationType());
    assertEquals("Expected soUnableToDetermineCode field to be initialized with default values",
        "D", client.getSoUnableToDetermineCode());
    assertEquals("Expected soc158PlacementCode field to be initialized with default values", "N",
        client.getSoc158PlacementCode());
    assertEquals("Expected soc158SealedClientIndicator field to be initialized with default values",
        false, client.getSoc158SealedClientIndicator());
    assertEquals(
        "Expected socialSecurityNumChangedCode field to be initialized with default values", "N",
        client.getSocialSecurityNumChangedCode());
    assertEquals(
        "Expected tribalAncestryClientIndicatorVar field to be initialized with default values",
        false, client.getTribalAncestryClientIndicatorVar());
    assertEquals(
        "Expected tribalMembrshpVerifctnIndicatorVar field to be initialized with default values",
        false, client.getTribalMembrshpVerifctnIndicatorVar());
    assertEquals("Expected unemployedParentCode field to be initialized with default values", "U",
        client.getUnemployedParentCode());
    assertEquals("Expected zippyCreatedIndicator field to be initialized with default values", true,
        client.getZippyCreatedIndicator());
    assertEquals("Expected address field to be initialized with default values", null,
        client.getAddress());
  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    Client validClient = validDomainClient();
    assertThat(validClient.hashCode(), is(not(0)));
  }

  @Override
  @Test
  public void testSerializesToJSON() throws Exception {}

  @Override
  public void testDeserializesFromJSON() throws Exception {}

  @Override
  @Test
  public void testSuccessWithValid() throws Exception {
    Client validClient = validClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Override
  @Test
  public void testSuccessWithOptionalsNotIncluded() throws Exception {}

  /*
   * adoption status code test
   */
  @Test
  public void testFailAdoptionStatusCodeEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setAdoptionStatusCode("").build();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("adoptionStatusCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailAdoptionStatusCodeInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setAdoptionStatusCode("X").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("adoptionStatusCode must be one of [T, P, N, A]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailAdoptionStatusCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setAdoptionStatusCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("adoptionStatusCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * alienRegistrationNumber test
   */
  @Test
  public void testFailAlienRegistrationNumberNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setAlienRegistrationNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("alienRegistrationNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailAlienRegistrationNumberTooLong() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setAlienRegistrationNumber("1234567890123").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("alienRegistrationNumber size must be between 0 and 12")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * birth city test
   * 
   */
  @Test
  public void testFailBirthCityNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthCity(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("birthCity may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailBirthCityTooLong() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setBirthCity("123456789012345678901234567890123456").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("birthCity size must be between 0 and 35")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * birthCountryCode test
   */
  @Test
  public void testSuccessBirthCountryCodeValid() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setBirthCountryCodeType(birthCountryCodeType).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testSuccessBirthCountryZero() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthCountryCodeType((short) 0).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testFailBirthCountryCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthCountryCodeType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("birthCountryCodeType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * birthDate test
   */
  @Test
  public void successWhenBirthDateValid() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthDate("2007-01-31").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenBirthDateNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenBirthDateWrongFormat() throws Exception {
    Client client = new ClientResourceBuilder().setBirthDate("01/31/2001").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("birthDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * birthFacilityName test
   */
  @Test
  public void sucessWhenBirthFacilityNameSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthFacilityName("  ").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failWhenBirthFacilityNameNull() throws Exception {
    Client client = new ClientResourceBuilder().setBirthFacilityName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("birthFacilityName may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failWhenBirthFacilityNameTooLong() throws Exception {
    Client client = new ClientResourceBuilder()
        .setBirthFacilityName("123456789012345678901234567890123456").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("birthFacilityName size must be between 0 and 35")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * birthStateCodetype test
   */
  @Test
  public void successWhenBirthStateCodeValid() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setBirthStateCodeType(birthStateCodeType).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenBirthStateCodeZero() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthStateCodeType((short) 0).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failWhenBirthStateCodeNull() throws Exception {
    Client client = new ClientResourceBuilder().setBirthStateCodeType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("birthStateCodeType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * birthplaceVerifiedInd test
   */
  @Test
  public void failWhenBirthplaceVerifiedNull() throws Exception {
    Client client = new ClientResourceBuilder().setBirthplaceVerifiedIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("birthplaceVerifiedIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * childClientIndicator test
   */
  @Test
  public void failWhenChildClientIndicatorNull() throws Exception {
    Client client = new ClientResourceBuilder().setChildClientIndicatorVar(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("childClientIndicatorVar may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * clientIndexNumber test
   */
  @Test
  public void successWhenClientIndexNumberNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setClientIndexNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failWhenClientIndexNumberTooLong() throws Exception {
    Client client = new ClientResourceBuilder().setClientIndexNumber("1234567890123").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("clientIndexNumber size must be between 0 and 12")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * commentDescription test
   */
  @Test
  public void failWhenCommentDescriptionNull() throws Exception {
    Client client = new ClientResourceBuilder().setCommentDescription(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("commentDescription may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * commonFirstName test
   */
  @Test
  public void failWhenCommonFirstNameNull() throws Exception {
    Client client = new ClientResourceBuilder().setCommonFirstName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("commonFirstName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failWhenCommonFirstNameEmpty() throws Exception {
    Client client = new ClientResourceBuilder().setCommonFirstName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("commonFirstName size must be between 1 and 20")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failWhenCommonFirstNameTooLong() throws Exception {
    Client client = new ClientResourceBuilder().setCommonFirstName("123456789012345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("commonFirstName size must be between 1 and 20")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failWhenCommonLastNameNull() throws Exception {
    Client client = new ClientResourceBuilder().setCommonLastName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("commonLastName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failWhenCommonLastNameEmpty() throws Exception {
    Client client = new ClientResourceBuilder().setCommonLastName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("commonLastName size must be between 1 and 25")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failWhenCommonLastNameTooLong() throws Exception {
    Client client = new ClientResourceBuilder()
        .setCommonLastName("1345123456789012345678901sffvfsvfsvfs").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("commonLastName size must be between 1 and 25")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCommonLastNameWhiteSpace() throws Exception {
    Client client = new ClientResourceBuilder().setCommonLastName("  ").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("commonLastName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void successWhenCommonMiddleNameEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setCommonMiddleName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenCommonMiddleNameTooLong() throws Exception {
    Client client =
        new ClientResourceBuilder().setCommonMiddleName("123456789012345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("commonMiddleName size must be between 0 and 20")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * confidentialityActionDate test
   */
  @Test
  public void successWhenConfidentialityActionDateNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setConfidentialityActionDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenConfidentialityActionDateEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setConfidentialityActionDate("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failWhenConfidentialityActionDateInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setConfidentialityActionDate("01-01-2010").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage()
          .equals("confidentialityActionDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * confidentialityInEffectInd test
   */
  @Test
  public void failWhenConfidentialityInEffectIndNull() throws Exception {
    Client client = new ClientResourceBuilder().setConfidentialityInEffectIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("confidentialityInEffectIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * creationDate test
   */
  @Test
  public void failWhenCreationDateNull() throws Exception {
    Client client = new ClientResourceBuilder().setCreationDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("creationDate may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failWhenCreationDateInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setCreationDate("01-01-1999").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("creationDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void shouldFailWhenCreationGreaterThanBirthDate() throws Exception {
    Client client = new ClientResourceBuilder().setBirthDate("2013-01-01")
        .setCreationDate("2011-01-01").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage()
          .equals("creationDate should be greater than or equal to birthDate")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * currCaChildrenServiceInd test
   */
  @Test
  public void failWhenCurrCaChildrenServIndicatorNull() throws Exception {
    Client client = new ClientResourceBuilder().setCurrCaChildrenServIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("currCaChildrenServIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * currentlyOtherDescription test
   */
  @Test
  public void successWhenCurrentlyOtherDescriptionEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setCurrentlyOtherDescription("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failWhenCurrentlyOtherDescriptionNull() throws Exception {
    Client client = new ClientResourceBuilder().setCurrentlyOtherDescription(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("currentlyOtherDescription may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failWhenCurrentlyOtherDescriptionTooLong() throws Exception {
    Client client = new ClientResourceBuilder()
        .setCurrentlyOtherDescription("12345678901234567890123456").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("currentlyOtherDescription size must be between 0 and 25")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * deathDate test
   */
  @Test
  public void successWhenDeathDateNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setDeathDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failWhenDeathDateInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setDeathDate("01-01-2010").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("deathDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void shouldFailWhenDeathBeforeBirthDate() throws Exception {
    Client client =
        new ClientResourceBuilder().setBirthDate("2011-01-01").setDeathDate("2010-01-01").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("deathDate should be greater than or equal to birthDate")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * deathDateVerifiedIndicator test
   */
  @Test
  public void failWhenDeathDateVerifiedNull() throws Exception {
    Client client = new ClientResourceBuilder().setDeathDateVerifiedIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("deathDateVerifiedIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * deathPlace test
   */
  @Test
  public void successWhenDeathPlaceNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setDeathPlace(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failWhenDeathPlaceTooLong() throws Exception {
    Client client =
        new ClientResourceBuilder().setDeathPlace("123456789012345678901234567890123456").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("deathPlace size must be between 0 and 35")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * deathReason test
   */

  @Test
  public void successWhenDeathResonTextNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setDeathReasonText(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenDeathReasonTextEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setDeathReasonText("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failWhenDeathReasonTooLong() throws Exception {
    Client client = new ClientResourceBuilder().setDeathReasonText("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("deathReasonText size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * driversLicenseNumber test
   */
  @Test
  public void failWhenDriverLicenseNumberNull() throws Exception {
    Client client = new ClientResourceBuilder().setDriverLicenseNumber(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("driverLicenseNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failWhenDriverLicenseNumberTooLong() throws Exception {
    Client client =
        new ClientResourceBuilder().setDriverLicenseNumber("C87634563C87634563123").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("driverLicenseNumber size must be between 0 and 20")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * driverLicenseStateCodeType test
   */
  @Test
  public void failWhenDriverLicenseStateCodeTypeNull() throws Exception {
    Client client = new ClientResourceBuilder().setDriverLicenseStateCodeType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("driverLicenseStateCodeType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * emailAddress test
   */
  @Test
  public void testSuccessEmailAddressNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setEmailAddress(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * estimatedDobCode test
   */
  @Test
  public void testFailEstimatedDobCodeInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setEstimatedDobCode("X").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("estimatedDobCode must be one of [Y, N, U]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailEstimatedDobCodeNull() throws Exception {
    Client client = new ClientResourceBuilder().setEstimatedDobCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("estimatedDobCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * ethUnableToDetReasonCode test
   */
  @Test
  public void testFailEthUnableToDetReasonCodeInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setEthUnableToDetReasonCode("NA").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("ethUnableToDetReasonCode must be one of [A, I, K, ]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testSuccessEthUnableToDetReasonCodeNull() throws Exception {
    Client client = new ClientResourceBuilder().setEthUnableToDetReasonCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }


  /*
   * fatherParentalRightTermDate test
   */
  @Test
  public void failWhenFatherParentalRightTermDateInvalid() throws Exception {
    Client client =
        new ClientResourceBuilder().setFatherParentalRightTermDate("01-02-2010").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage()
          .equals("fatherParentalRightTermDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testSuccessFatherParentalRightTermDateNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setFatherParentalRightTermDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * genderCode test
   */
  @Test
  public void testFailGenderCodeInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setGenderCode("Z").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("genderCode must be one of [M, F, U, I]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailGenderCodeEmpty() throws Exception {
    Client client = new ClientResourceBuilder().setGenderCode("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("genderCode must be one of [M, F, U, I]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailGenderCodeNull() throws Exception {
    Client client = new ClientResourceBuilder().setGenderCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("genderCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testSuccessGenderCodeI() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderCode("I").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * genderIdentityType
   */
  @Test
  public void testSuccessGenderIdentityTypeValidCode() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderIdentityType((short) 7075).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testFailGenderIdentityTypeInValidCode() throws Exception {
    Client client = new ClientResourceBuilder().setGenderIdentityType((short) 1234).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage()
          .equals("genderIdentityType must be a valid system code for category CLNT_GIC")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailGenderIdentityTypeNull() throws Exception {
    Client client = new ClientResourceBuilder().setGenderIdentityType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("genderIdentityType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * giNotListedDescription test
   */
  @Test
  public void testSuccessGiNotListedDescriptionNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setGiNotListedDescription(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testSuccessGiNotListedDescriptionEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setGiNotListedDescription("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * genderExpressionType test
   */
  @Test
  public void testSuccessGenderExpressionTypeValidCode() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setGenderExpressionType(genderExpressionType).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testFailGenderExpressionTypeInValidCode() throws Exception {
    Client client = new ClientResourceBuilder().setGenderExpressionType((short) 1234).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage()
          .equals("genderExpressionType must be a valid system code for category CLNT_GEC")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailGenderExpressionTypeNull() throws Exception {
    Client client = new ClientResourceBuilder().setGenderExpressionType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("genderExpressionType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * sexualOrientationType test
   */
  @Test
  public void testSuccessSexualOrientationTypeValidCode() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setSexualOrientationType(sexualOrientationType).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testFailSexualOrientationTypeInValidCode() throws Exception {
    Client client = new ClientResourceBuilder().setSexualOrientationType((short) 1234).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage()
          .equals("sexualOrientationType must be a valid system code for category CLNT_SOC")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailSexualOrientationTypeNull() throws Exception {
    Client client = new ClientResourceBuilder().setSexualOrientationType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("sexualOrientationType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * soUnableToDetermineCode test
   */
  @Test
  public void testSuccessSoUnableToDetermineCodeEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoUnableToDetermineCode("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testSuccessSoUnableToDetermineCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoUnableToDetermineCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testFailSoUnableToDetermineCodeInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setSoUnableToDetermineCode("Z").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("soUnableToDetermineCode must be one of [D, C, ]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * soNotListedDescrption test
   */
  @Test
  public void testSuccessSoNotListedDescrptionNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoNotListedDescrption(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * incapacitatedParentCode test
   */
  @Test
  public void testFailIncapacitatedParentCodeInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setIncapacitatedParentCode("X").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("incapacitatedParentCode must be one of [N, NA, U, Y]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailIncapacitatedParentCodeEmpty() throws Exception {
    Client client = new ClientResourceBuilder().setIncapacitatedParentCode("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("incapacitatedParentCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailIncapacitatedParentCodeNull() throws Exception {
    Client client = new ClientResourceBuilder().setIncapacitatedParentCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("incapacitatedParentCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * motherParentalRightTermDate test
   */
  @Test
  public void failWhenMotherParentalRightTermDateInvalid() throws Exception {
    Client client =
        new ClientResourceBuilder().setMotherParentalRightTermDate("01-02-2010").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage()
          .equals("motherParentalRightTermDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * literateCode test
   */
  @Test
  public void testFailLiterateCodeInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setLiterateCode("NA").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("literateCode must be one of [Y, N, U, D]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailLiterateCodeNull() throws Exception {
    Client client = new ClientResourceBuilder().setLiterateCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("literateCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * militaryStatusCode test
   */
  @Test
  public void testFailMilitaryStatusCodeInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setMilitaryStatusCode("X").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("militaryStatusCode must be one of [D, A, V, N]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailMilitaryStatusCodeNull() throws Exception {
    Client client = new ClientResourceBuilder().setMilitaryStatusCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("militaryStatusCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }


  /*
   * socPlacementCode test
   * 
   * 158 is removed on the naming Json convention to make jenkins build success
   */
  @Test
  public void testFailSoc158PlacementCodeInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setSoc158PlacementCode("NA").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("soc158PlacementCode must be one of [Y, M, N]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailSoc158PlacementCodeNull() throws Exception {
    Client client = new ClientResourceBuilder().setSoc158PlacementCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("soc158PlacementCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * socialSecurityNumChangedCode test
   */
  @Test
  public void testFailSocialSecurityNumChangedCodeInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setSocialSecurityNumChangedCode("NA").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("socialSecurityNumChangedCode must be one of [Y, N]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailSocialSecurityNumChangedCodeNull() throws Exception {
    Client client = new ClientResourceBuilder().setSocialSecurityNumChangedCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("socialSecurityNumChangedCode may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * socialSecurityNumber test
   */
  @Test
  public void testFailsocialSecurityNumberInvalid() throws Exception {

    Client client = new ClientResourceBuilder().setSocialSecurityNumber("123456kk9").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("socialSecurityNumber must match \"^(|[0-9]{9})$\"")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailsocialSecurityNumberTooLong() throws Exception {
    Client client = new ClientResourceBuilder().setSocialSecurityNumber("1234567890").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("socialSecurityNumber must match \"^(|[0-9]{9})$\"")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * unemployedParentCode test
   */
  @Test
  public void testFailUnemployedParentCodeInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setUnemployedParentCode("UA").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("unemployedParentCode must be one of [N, NA, U, Y]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailUnemployedParentCodeNull() throws Exception {
    Client client = new ClientResourceBuilder().setUnemployedParentCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(client));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("unemployedParentCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void shouldReturnTrueWhenOtherClientHasNotBeenModified() {
    DateTime lastUpdateTime = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.sssZ")
        .parseDateTime("2004-03-31T09:45:58.000-0800");

    Client client1 = new ClientResourceBuilder().setLastUpdateTime(lastUpdateTime).build();
    Client client2 = new ClientResourceBuilder().setLastUpdateTime(lastUpdateTime).build();
    assertTrue("Expect client 1 to have same last update time ",
        client1.hasSameLastUpdate(client2));
  }

  @Test
  public void shouldConvertNullValuesToDefault0() {
    Client client = new ClientResourceBuilder().setPrimaryLanguageType(null)
        .setSecondaryLanguageType(null).build();
    assertEquals(0, (int) client.getPrimaryLanguage());
    assertEquals(0, (int) client.getSecondaryLanguage());
  }

  @Test
  public void applySealedSensitiveIndicator() {
    Client client = new ClientResourceBuilder().setSensitivityIndicator(null).build();

    client.applySensitivityIndicator(LimitedAccessType.SENSITIVE);
    assertEquals("Expected indicator to not be unchanged", LimitedAccessType.SENSITIVE.getValue(),
        client.getSensitivityIndicator());
  }

  @Test
  public void testConstructorWithNullPrimaryLanguage() throws Exception {
    Short zero = 0;
    gov.ca.cwds.data.persistence.cms.Client persistent =
        new ClientEntityBuilder().setPrimaryLanguageType(null).build();

    Client domain = new Client(persistent, Boolean.FALSE);
    assertThat(domain.getPrimaryLanguage(), is(equalTo(zero)));
  }

  @Test
  public void testConstructorWithNullSecondaryLanguage() throws Exception {
    Short zero = 0;
    gov.ca.cwds.data.persistence.cms.Client persistent =
        new ClientEntityBuilder().setSecondaryLanguageType(null).build();
    Client domain = new Client(persistent, Boolean.FALSE);
    assertThat(domain.getSecondaryLanguage(), is(equalTo(zero)));
  }

  @Test
  /**
   * Rule - 06998
   */
  public void testZippyClientCreation() {

    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");

    Participant participant = new Participant(1, "sourceTable", "clientId", new LegacyDescriptor(),
        "firstName", "middleName", "lastName", "", "gender", "ssn", "dob", primaryLanguageType,
        secondaryLanguageType, 4, reporterConfidentialWaiver, reporterEmployerName,
        clientStaffPersonAdded, sensitivityIndicator, "12", "Y", new HashSet<>(), new HashSet<>(),
        raceAndEthnicity, null);

    Client client = Client.createWithDefaults(participant, "", "", (short) 0, true);

    assertEquals("Expected zippyCreatedIndicator field to be initialized as  True", Boolean.TRUE,
        client.getZippyCreatedIndicator());

  }

  private Client validClient() throws JsonParseException, JsonMappingException, IOException {
    Client vc = new ClientResourceBuilder().build();
    return vc;
  }

  private Client validDomainClient() {
    Client domain = new Client(existingClientId, lastUpdatedTime, adjudicatedDelinquentIndicator,
        adoptionStatusCode, alienRegistrationNumber, birthCity, birthCountryCodeType, birthDate,
        birthFacilityName, birthStateCodeType, birthplaceVerifiedIndicator, childClientIndicatorVar,
        clientIndexNumber, commentDescription, commonFirstName, commonMiddleName, commonLastName,
        confidentialityActionDate, confidentialityInEffectIndicator, creationDate,
        currCaChildrenServIndicator, currentlyOtherDescription, currentlyRegionalCenterIndicator,
        deathDate, deathDateVerified, deathPlace, deathReasonText, driversLicenseNumber,
        driversLicenseStateCodeType, emailAddress, estimatedDateOfBirthCode,
        ethUnableToDetReasonCode, fatherParentalRightTermDate, genderCode, genderIdentityType,
        giNotListedDescription, genderExpressionType, healthSummaryText, hispUnableToDetReasonCode,
        hispanicOriginCode, immigrationCountryCodeType, immigrationStatusType,
        incapcitatedParentCode, individualHealthCarePlanIndicator, limitationOnScpHealthIndicator,
        literateCode, maritalCohabitatnHstryIndicatorVar, maritalStatusType, militaryStatusCode,
        motherParentalRightTermDate, namePrefixDescription, nameType, outstandingWarrantIndicator,
        prevCaChildrenServIndicator, prevOtherDescription, prevRegionalCenterIndicator,
        primaryEthnicityType, primaryLanguageType, religionType, secondaryLanguageType,
        sensitiveHlthInfoOnFileIndicator, sensitivityIndicator, sexualOrientationType,
        soUnableToDetermineCode, soNotListedDescrption, soc158PlacementCode,
        soc158SealedClientIndicator, socialSecurityNumChangedCode, socialSecurityNumber,
        suffixTitleDescription, tribalAncestryClientIndicatorVar,
        tribalMembrshpVerifctnIndicatorVar, unemployedParentCode, zippyCreatedIndicator, null);

    return domain;

  }
}
