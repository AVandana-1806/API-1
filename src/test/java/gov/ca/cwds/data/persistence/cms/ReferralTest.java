package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.util.Doofenshmirtz;

/**
 * @author CWDS API Team
 */
public class ReferralTest extends Doofenshmirtz<Referral> implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;
  private final static String STATE_OF_CALIFORNIA_COUNTY_ID = "1126";

  private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  @SuppressWarnings("unused")

  private final DateFormat tf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
  private final DateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss");

  private String id = "1234567ABC";
  private String lastUpdatedId = "0X5";
  private Validator validator;

  Referral target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new Referral();
  }

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Referral.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Referral domainReferral = new ReferralResourceBuilder().build();
    Referral persistent = new Referral(id, domainReferral, "0X5");
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(DomainChef.uncookBooleanString(persistent.getAdditionalInfoIncludedCode()),
        is(equalTo(domainReferral.getAdditionalInfoIncludedCode())));
    assertThat(DomainChef.uncookBooleanString(persistent.getAnonymousReporterIndicator()),
        is(equalTo(domainReferral.getAnonymousReporterIndicator())));
    assertThat(DomainChef.uncookBooleanString(persistent.getApplicationForPetitionIndicator()),
        is(equalTo(domainReferral.getApplicationForPetitionIndicator())));
    assertThat(persistent.getApprovalNumber(), is(equalTo(domainReferral.getApprovalNumber())));
    assertThat(persistent.getApprovalStatusType(),
        is(equalTo(domainReferral.getApprovalStatusType())));
    assertThat(DomainChef.uncookBooleanString(persistent.getCaretakersPerpetratorCode()),
        is(equalTo(domainReferral.getCaretakersPerpetratorCode())));
    assertThat(persistent.getClosureDate(),
        is(equalTo(DomainChef.uncookDateString(domainReferral.getClosureDate()))));
    assertThat(persistent.getCommunicationMethodType(),
        is(equalTo(domainReferral.getCommunicationMethodType())));
    assertThat(persistent.getCurrentLocationOfChildren(),
        is(equalTo(domainReferral.getCurrentLocationOfChildren())));
    assertThat(persistent.getDrmsAllegationDescriptionDoc(),
        is(equalTo(domainReferral.getDrmsAllegationDescriptionDoc())));
    assertThat(persistent.getDrmsErReferralDoc(),
        is(equalTo(domainReferral.getDrmsErReferralDoc())));
    assertThat(persistent.getDrmsInvestigationDoc(),
        is(equalTo(domainReferral.getDrmsInvestigationDoc())));
    assertThat(
        DomainChef.uncookBooleanString(
            persistent.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator()),
        is(equalTo(domainReferral.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator())));
    assertThat(DomainChef.uncookBooleanString(persistent.getFamilyAwarenessIndicator()),
        is(equalTo(domainReferral.getFamilyAwarenessIndicator())));
    assertThat(persistent.getGovtEntityType(), is(equalTo(domainReferral.getGovtEntityType())));
    assertThat(persistent.getLegalDefinitionCode(),
        is(equalTo(domainReferral.getLegalDefinitionCode())));
    assertThat(DomainChef.uncookBooleanString(persistent.getLegalRightsNoticeIndicator()),
        is(equalTo(domainReferral.getLegalRightsNoticeIndicator())));
    assertThat(persistent.getLimitedAccessCode(),
        is(equalTo(domainReferral.getLimitedAccessCode())));
    assertThat(persistent.getMandatedCrossReportReceivedDate(), is(
        equalTo(DomainChef.uncookDateString(domainReferral.getMandatedCrossReportReceivedDate()))));
    assertThat(persistent.getReferralName(), is(equalTo(domainReferral.getReferralName())));
    assertThat(persistent.getOpenAdequateCaseCode(),
        is(equalTo(domainReferral.getOpenAdequateCaseCode())));
    assertThat(persistent.getReceivedDate(),
        is(equalTo(DomainChef.uncookDateString(domainReferral.getReceivedDate()))));
    assertThat(persistent.getReceivedTime(),
        is(equalTo(DomainChef.uncookTimeString(domainReferral.getReceivedTime()))));
    assertThat(persistent.getReferralResponseType(),
        is(equalTo(domainReferral.getReferralResponseType())));
    assertThat(persistent.getReferredToResourceType(),
        is(equalTo(domainReferral.getReferredToResourceType())));
    assertThat(persistent.getResponseDeterminationDate(),
        is(equalTo(DomainChef.uncookDateString(domainReferral.getResponseDeterminationDate()))));
    assertThat(persistent.getResponseDeterminationTime(),
        is(equalTo(DomainChef.uncookTimeString(domainReferral.getResponseDeterminationTime()))));
    assertThat(persistent.getResponseRationaleText(),
        is(equalTo(domainReferral.getResponseRationaleText())));
    assertThat(persistent.getScreenerNoteText(), is(equalTo(domainReferral.getScreenerNoteText())));
    assertThat(persistent.getSpecificsIncludedCode(),
        is(equalTo(domainReferral.getSpecificsIncludedCode())));
    assertThat(persistent.getSufficientInformationCode(),
        is(equalTo(domainReferral.getSufficientInformationCode())));
    assertThat(persistent.getUnfoundedSeriesCode(),
        is(equalTo(domainReferral.getUnfoundedSeriesCode())));
    assertThat(persistent.getLinkToPrimaryReferralId(),
        is(equalTo(domainReferral.getLinkToPrimaryReferralId())));
    assertThat(persistent.getAllegesAbuseOccurredAtAddressId(),
        is(equalTo(domainReferral.getAllegesAbuseOccurredAtAddressId())));
    assertThat(persistent.getFirstResponseDeterminedByStaffPersonId(),
        is(equalTo(domainReferral.getFirstResponseDeterminedByStaffPersonId())));
    assertThat(persistent.getPrimaryContactStaffPersonId(),
        is(equalTo(domainReferral.getPrimaryContactStaffPersonId())));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(domainReferral.getCountySpecificCode())));
    assertThat(DomainChef.uncookBooleanString(persistent.getSpecialProjectReferralIndicator()),
        is(equalTo(domainReferral.getSpecialProjectReferralIndicator())));
    assertThat(DomainChef.uncookBooleanString(persistent.getZippyCreatedIndicator()),
        is(equalTo(domainReferral.getZippyCreatedIndicator())));
    assertThat(DomainChef.uncookBooleanString(persistent.getHomelessIndicator()),
        is(equalTo(domainReferral.getHomelessIndicator())));
    assertThat(DomainChef.uncookBooleanString(persistent.getFamilyRefusedServicesIndicator()),
        is(equalTo(domainReferral.getFamilyRefusedServicesIndicator())));
    assertThat(persistent.getFirstEvaluatedOutApprovalDate(), is(
        equalTo(DomainChef.uncookDateString(domainReferral.getFirstEvaluatedOutApprovalDate()))));
    assertThat(persistent.getResponsibleAgencyCode(),
        is(equalTo(domainReferral.getResponsibleAgencyCode())));
    assertThat(persistent.getLimitedAccessGovtAgencyType(),
        is(equalTo(domainReferral.getLimitedAccessGovtAgencyType())));
    assertThat(persistent.getLimitedAccessDate(),
        is(equalTo(DomainChef.uncookDateString(domainReferral.getLimitedAccessDate()))));
    assertThat(persistent.getLimitedAccessDesc(),
        is(equalTo(domainReferral.getLimitedAccessDesc())));
    assertThat(persistent.getOriginalClosureDate(),
        is(equalTo(DomainChef.uncookDateString(domainReferral.getOriginalClosureDate()))));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    Referral validReferral = new ReferralEntityBuilder().build();
    Referral persistentReferral = new Referral(validReferral.getId(),
        validReferral.getAdditionalInfoIncludedCode(),
        validReferral.getAnonymousReporterIndicator(),
        validReferral.getApplicationForPetitionIndicator(), validReferral.getApprovalNumber(),
        validReferral.getApprovalStatusType(), validReferral.getCaretakersPerpetratorCode(),
        validReferral.getClosureDate(), validReferral.getCommunicationMethodType(),
        validReferral.getCurrentLocationOfChildren(),
        validReferral.getDrmsAllegationDescriptionDoc(), validReferral.getDrmsErReferralDoc(),
        validReferral.getDrmsInvestigationDoc(),
        validReferral.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(),
        validReferral.getFamilyAwarenessIndicator(), validReferral.getGovtEntityType(),
        validReferral.getLegalDefinitionCode(), validReferral.getLegalRightsNoticeIndicator(),
        validReferral.getLimitedAccessCode(), validReferral.getMandatedCrossReportReceivedDate(),
        validReferral.getReferralName(), validReferral.getOpenAdequateCaseCode(),
        validReferral.getReceivedDate(), validReferral.getReceivedTime(),
        validReferral.getReferralResponseType(), validReferral.getReferredToResourceType(),
        validReferral.getResponseDeterminationDate(), validReferral.getResponseDeterminationTime(),
        validReferral.getResponseRationaleText(), validReferral.getScreenerNoteText(),
        validReferral.getSpecificsIncludedCode(), validReferral.getSufficientInformationCode(),
        validReferral.getUnfoundedSeriesCode(), validReferral.getLinkToPrimaryReferralId(),
        validReferral.getAllegesAbuseOccurredAtAddressId(),
        validReferral.getFirstResponseDeterminedByStaffPersonId(),
        validReferral.getPrimaryContactStaffPersonId(), validReferral.getCountySpecificCode(),
        validReferral.getSpecialProjectReferralIndicator(),
        validReferral.getZippyCreatedIndicator(), validReferral.getHomelessIndicator(),
        validReferral.getFamilyRefusedServicesIndicator(),
        validReferral.getFirstEvaluatedOutApprovalDate(), validReferral.getResponsibleAgencyCode(),
        validReferral.getLimitedAccessGovtAgencyType(), validReferral.getLimitedAccessDate(),
        validReferral.getLimitedAccessDesc(), validReferral.getOriginalClosureDate(), null, null,
        null, null);
    assertThat(persistentReferral.getId(), is(equalTo(validReferral.getId())));
    assertThat(persistentReferral.getAdditionalInfoIncludedCode(),
        is(equalTo(validReferral.getAdditionalInfoIncludedCode())));
    assertThat(persistentReferral.getAnonymousReporterIndicator(),
        is(equalTo(validReferral.getAnonymousReporterIndicator())));
    assertThat(persistentReferral.getApplicationForPetitionIndicator(),
        is(equalTo(validReferral.getApplicationForPetitionIndicator())));
    assertThat(persistentReferral.getApprovalNumber(),
        is(equalTo(validReferral.getApprovalNumber())));
    assertThat(persistentReferral.getApprovalStatusType(),
        is(equalTo(validReferral.getApprovalStatusType())));
    assertThat(persistentReferral.getCaretakersPerpetratorCode(),
        is(equalTo(validReferral.getCaretakersPerpetratorCode())));
    assertThat(persistentReferral.getClosureDate(), is(equalTo(validReferral.getClosureDate())));
    assertThat(persistentReferral.getCommunicationMethodType(),
        is(equalTo(validReferral.getCommunicationMethodType())));
    assertThat(persistentReferral.getCurrentLocationOfChildren(),
        is(equalTo(validReferral.getCurrentLocationOfChildren())));
    assertThat(persistentReferral.getDrmsAllegationDescriptionDoc(),
        is(equalTo(validReferral.getDrmsAllegationDescriptionDoc())));
    assertThat(persistentReferral.getDrmsErReferralDoc(),
        is(equalTo(validReferral.getDrmsErReferralDoc())));
    assertThat(persistentReferral.getDrmsInvestigationDoc(),
        is(equalTo(validReferral.getDrmsInvestigationDoc())));
    assertThat(persistentReferral.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(), is(
        equalTo(persistentReferral.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator())));
    assertThat(persistentReferral.getFamilyAwarenessIndicator(),
        is(equalTo(validReferral.getFamilyAwarenessIndicator())));
    assertThat(persistentReferral.getGovtEntityType(),
        is(equalTo(validReferral.getGovtEntityType())));
    assertThat(persistentReferral.getLegalDefinitionCode(),
        is(equalTo(validReferral.getLegalDefinitionCode())));
    assertThat(persistentReferral.getLegalRightsNoticeIndicator(),
        is(equalTo(validReferral.getLegalRightsNoticeIndicator())));
    assertThat(persistentReferral.getGovtEntityType(),
        is(equalTo(validReferral.getGovtEntityType())));
    assertThat(persistentReferral.getLegalDefinitionCode(),
        is(equalTo(validReferral.getLegalDefinitionCode())));
    assertThat(persistentReferral.getLegalRightsNoticeIndicator(),
        is(equalTo(validReferral.getLegalRightsNoticeIndicator())));
    assertThat(persistentReferral.getLimitedAccessCode(),
        is(equalTo(validReferral.getLimitedAccessCode())));
    assertThat(persistentReferral.getMandatedCrossReportReceivedDate(),
        is(equalTo(validReferral.getMandatedCrossReportReceivedDate())));
    assertThat(persistentReferral.getReferralName(), is(equalTo(validReferral.getReferralName())));
    assertThat(persistentReferral.getOpenAdequateCaseCode(),
        is(equalTo(validReferral.getOpenAdequateCaseCode())));
    assertThat(persistentReferral.getReceivedDate(), is(equalTo(validReferral.getReceivedDate())));
    assertThat(persistentReferral.getReceivedTime(), is(equalTo(validReferral.getReceivedTime())));
    assertThat(persistentReferral.getReferralResponseType(),
        is(equalTo(validReferral.getReferralResponseType())));
    assertThat(persistentReferral.getReferredToResourceType(),
        is(equalTo(validReferral.getReferredToResourceType())));
    assertThat(persistentReferral.getResponseDeterminationDate(),
        is(equalTo(validReferral.getResponseDeterminationDate())));
    assertThat(persistentReferral.getResponseDeterminationTime(),
        is(equalTo(validReferral.getResponseDeterminationTime())));
    assertThat(persistentReferral.getResponseRationaleText(),
        is(equalTo(validReferral.getResponseRationaleText())));
    assertThat(persistentReferral.getScreenerNoteText(),
        is(equalTo(validReferral.getScreenerNoteText())));
    assertThat(persistentReferral.getSpecificsIncludedCode(),
        is(equalTo(validReferral.getSpecificsIncludedCode())));
    assertThat(persistentReferral.getSufficientInformationCode(),
        is(equalTo(validReferral.getSufficientInformationCode())));
    assertThat(persistentReferral.getUnfoundedSeriesCode(),
        is(equalTo(validReferral.getUnfoundedSeriesCode())));
    assertThat(persistentReferral.getLinkToPrimaryReferralId(),
        is(equalTo(validReferral.getLinkToPrimaryReferralId())));
    assertThat(persistentReferral.getAllegesAbuseOccurredAtAddressId(),
        is(equalTo(validReferral.getAllegesAbuseOccurredAtAddressId())));
    assertThat(persistentReferral.getFirstResponseDeterminedByStaffPersonId(),
        is(equalTo(validReferral.getFirstResponseDeterminedByStaffPersonId())));
    assertThat(persistentReferral.getPrimaryContactStaffPersonId(),
        is(equalTo(validReferral.getPrimaryContactStaffPersonId())));
    assertThat(persistentReferral.getCountySpecificCode(),
        is(equalTo(validReferral.getCountySpecificCode())));
    assertThat(persistentReferral.getSpecialProjectReferralIndicator(),
        is(equalTo(validReferral.getSpecialProjectReferralIndicator())));
    assertThat(persistentReferral.getZippyCreatedIndicator(),
        is(equalTo(validReferral.getZippyCreatedIndicator())));
    assertThat(persistentReferral.getHomelessIndicator(),
        is(equalTo(validReferral.getHomelessIndicator())));
    assertThat(persistentReferral.getFamilyRefusedServicesIndicator(),
        is(equalTo(validReferral.getFamilyRefusedServicesIndicator())));
    assertThat(persistentReferral.getFirstEvaluatedOutApprovalDate(),
        is(equalTo(validReferral.getFirstEvaluatedOutApprovalDate())));
    assertThat(persistentReferral.getResponsibleAgencyCode(),
        is(equalTo(validReferral.getResponsibleAgencyCode())));
    assertThat(persistentReferral.getLimitedAccessGovtAgencyType(),
        is(equalTo(validReferral.getLimitedAccessGovtAgencyType())));
    assertThat(persistentReferral.getOriginalClosureDate(),
        is(equalTo(validReferral.getOriginalClosureDate())));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize() throws Exception {
    Referral vr = validReferral();
    Referral pr = new Referral(vr.getId(), vr.getAdditionalInfoIncludedCode(),
        vr.getAnonymousReporterIndicator(), vr.getApplicationForPetitionIndicator(),
        vr.getApprovalNumber(), vr.getApprovalStatusType(), vr.getCaretakersPerpetratorCode(),
        vr.getClosureDate(), vr.getCommunicationMethodType(), vr.getCurrentLocationOfChildren(),
        vr.getDrmsAllegationDescriptionDoc(), vr.getDrmsErReferralDoc(),
        vr.getDrmsInvestigationDoc(),
        vr.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(),
        vr.getFamilyAwarenessIndicator(), vr.getGovtEntityType(), vr.getLegalDefinitionCode(),
        vr.getLegalRightsNoticeIndicator(), vr.getLimitedAccessCode(),
        vr.getMandatedCrossReportReceivedDate(), vr.getReferralName(), vr.getOpenAdequateCaseCode(),
        vr.getReceivedDate(), vr.getReceivedTime(), vr.getReferralResponseType(),
        vr.getReferredToResourceType(), vr.getResponseDeterminationDate(),
        vr.getResponseDeterminationTime(), vr.getResponseRationaleText(), vr.getScreenerNoteText(),
        vr.getSpecificsIncludedCode(), vr.getSufficientInformationCode(),
        vr.getUnfoundedSeriesCode(), vr.getLinkToPrimaryReferralId(),
        vr.getAllegesAbuseOccurredAtAddressId(), vr.getFirstResponseDeterminedByStaffPersonId(),
        vr.getPrimaryContactStaffPersonId(), vr.getCountySpecificCode(),
        vr.getSpecialProjectReferralIndicator(), vr.getZippyCreatedIndicator(),
        vr.getHomelessIndicator(), vr.getFamilyRefusedServicesIndicator(),
        vr.getFirstEvaluatedOutApprovalDate(), vr.getResponsibleAgencyCode(),
        vr.getLimitedAccessGovtAgencyType(), vr.getLimitedAccessDate(), vr.getLimitedAccessDesc(),
        vr.getOriginalClosureDate(), null, null, null, null);
    final String expected = MAPPER.writeValueAsString((MAPPER.readValue(
        fixture("fixtures/persistent/Referral/valid/validWithSysCodes.json"), Referral.class)));
    assertThat(MAPPER.writeValueAsString(pr)).isEqualTo(expected);
  }

  private Referral validReferral() throws JsonParseException, JsonMappingException, IOException {
    Referral vr =
        MAPPER.readValue(fixture("fixtures/persistent/Referral/valid/valid.json"), Referral.class);
    return vr;
  }

  @Test
  public void type() throws Exception {
    assertThat(Referral.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    String actual = target.getPrimaryKey();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_A$() throws Exception {
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAdditionalInfoIncludedCode_A$() throws Exception {
    String actual = target.getAdditionalInfoIncludedCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAnonymousReporterIndicator_A$() throws Exception {
    String actual = target.getAnonymousReporterIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApplicationForPetitionIndicator_A$() throws Exception {
    String actual = target.getApplicationForPetitionIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApprovalNumber_A$() throws Exception {
    String actual = target.getApprovalNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApprovalStatusType_A$() throws Exception {
    Short actual = target.getApprovalStatusType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCaretakersPerpetratorCode_A$() throws Exception {
    String actual = target.getCaretakersPerpetratorCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getClosureDate_A$() throws Exception {
    Date actual = target.getClosureDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCommunicationMethodType_A$() throws Exception {
    Short actual = target.getCommunicationMethodType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCurrentLocationOfChildren_A$() throws Exception {
    String actual = target.getCurrentLocationOfChildren();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDrmsAllegationDescriptionDoc_A$() throws Exception {
    String actual = target.getDrmsAllegationDescriptionDoc();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDrmsErReferralDoc_A$() throws Exception {
    String actual = target.getDrmsErReferralDoc();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDrmsInvestigationDoc_A$() throws Exception {
    String actual = target.getDrmsInvestigationDoc();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator_A$() throws Exception {
    String actual = target.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFamilyAwarenessIndicator_A$() throws Exception {
    String actual = target.getFamilyAwarenessIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGovtEntityType_A$() throws Exception {
    Short actual = target.getGovtEntityType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setGovtEntityType_A$Short() throws Exception {
    Short govtEntityType = null;
    target.setGovtEntityType(govtEntityType);
  }

  @Test
  public void getLegalDefinitionCode_A$() throws Exception {
    String actual = target.getLegalDefinitionCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegalRightsNoticeIndicator_A$() throws Exception {
    String actual = target.getLegalRightsNoticeIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLimitedAccessCode_A$() throws Exception {
    String actual = target.getLimitedAccessCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLimitedAccessCode_A$String() throws Exception {
    String limitedAccessCode = null;
    target.setLimitedAccessCode(limitedAccessCode);
  }

  @Test
  public void getMandatedCrossReportReceivedDate_A$() throws Exception {
    Date actual = target.getMandatedCrossReportReceivedDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReferralName_A$() throws Exception {
    String actual = target.getReferralName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getOpenAdequateCaseCode_A$() throws Exception {
    String actual = target.getOpenAdequateCaseCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReceivedDate_A$() throws Exception {
    Date actual = target.getReceivedDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReceivedTime_A$() throws Exception {
    Date actual = target.getReceivedTime();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReferralResponseType_A$() throws Exception {
    Short actual = target.getReferralResponseType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReferredToResourceType_A$() throws Exception {
    Short actual = target.getReferredToResourceType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getResponseDeterminationDate_A$() throws Exception {
    Date actual = target.getResponseDeterminationDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getResponseDeterminationTime_A$() throws Exception {
    Date actual = target.getResponseDeterminationTime();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getResponseRationaleText_A$() throws Exception {
    String actual = target.getResponseRationaleText();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getScreenerNoteText_A$() throws Exception {
    String actual = target.getScreenerNoteText();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSpecificsIncludedCode_A$() throws Exception {
    String actual = target.getSpecificsIncludedCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSufficientInformationCode_A$() throws Exception {
    String actual = target.getSufficientInformationCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getUnfoundedSeriesCode_A$() throws Exception {
    String actual = target.getUnfoundedSeriesCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLinkToPrimaryReferralId_A$() throws Exception {
    String actual = target.getLinkToPrimaryReferralId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAllegesAbuseOccurredAtAddressId_A$() throws Exception {
    String actual = target.getAllegesAbuseOccurredAtAddressId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstResponseDeterminedByStaffPersonId_A$() throws Exception {
    String actual = target.getFirstResponseDeterminedByStaffPersonId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryContactStaffPersonId_A$() throws Exception {
    String actual = target.getPrimaryContactStaffPersonId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCountySpecificCode_A$() throws Exception {
    String actual = target.getCountySpecificCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSpecialProjectReferralIndicator_A$() throws Exception {
    String actual = target.getSpecialProjectReferralIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZippyCreatedIndicator_A$() throws Exception {
    String actual = target.getZippyCreatedIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getHomelessIndicator_A$() throws Exception {
    String actual = target.getHomelessIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFamilyRefusedServicesIndicator_A$() throws Exception {
    String actual = target.getFamilyRefusedServicesIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstEvaluatedOutApprovalDate_A$() throws Exception {
    Date actual = target.getFirstEvaluatedOutApprovalDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getResponsibleAgencyCode_A$() throws Exception {
    String actual = target.getResponsibleAgencyCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLimitedAccessGovtAgencyType_A$() throws Exception {
    Short actual = target.getLimitedAccessGovtAgencyType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLimitedAccessDate_A$() throws Exception {
    Date actual = target.getLimitedAccessDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLimitedAccessDesc_A$() throws Exception {
    String actual = target.getLimitedAccessDesc();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getOriginalClosureDate_A$() throws Exception {
    Date actual = target.getOriginalClosureDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStaffPerson_A$() throws Exception {
    StaffPerson actual = target.getStaffPerson();
    StaffPerson expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStaffPerson_A$StaffPerson() throws Exception {
    StaffPerson staffPerson = mock(StaffPerson.class);
    target.setStaffPerson(staffPerson);
  }

  @Test
  public void getAddresses_A$() throws Exception {
    Address actual = target.getAddresses();
    Address expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAllegations_A$() throws Exception {
    Set<Allegation> actual = target.getAllegations();
    Set<Allegation> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAllegations_A$Set() throws Exception {
    Set<Allegation> allegations = new HashSet<>();
    target.setAllegations(allegations);
  }

  @Test
  public void getCrossReports_A$() throws Exception {
    Set<CrossReport> actual = target.getCrossReports();
    Set<CrossReport> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReporter_A$() throws Exception {
    Reporter actual = target.getReporter();
    Reporter expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setReporter_A$Reporter() throws Exception {
    Reporter reporter = mock(Reporter.class);
    target.setReporter(reporter);
  }

  @Test
  public void getReferralClients_A$() throws Exception {
    Set<ReferralClient> actual = target.getReferralClients();
    Set<ReferralClient> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void toString_A$() throws Exception {
    String actual = target.toString();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void hashCode_A$() throws Exception {
    int actual = target.hashCode();
    int expected = 0;
    assertThat(actual, is(not(expected)));
  }

  @Test
  public void equals_A$Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
