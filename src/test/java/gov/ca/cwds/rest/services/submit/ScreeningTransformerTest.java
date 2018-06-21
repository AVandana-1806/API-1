package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.JsonProcessingException;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.AddressIntakeApiResourceBuilder;
import gov.ca.cwds.fixture.LegacyDescriptorEntityBuilder;
import gov.ca.cwds.fixture.ScreeningResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ScreeningTransformerTest {

  private Screening screening;

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Before
  public void setup() throws Exception {
    screening = new ScreeningResourceBuilder().build();
  }

  @Test
  public void transformConvertsScreeningToScreeningToReferral() throws JsonProcessingException {
    Set<CrossReport> crossReports = new HashSet<>();
    Set<Allegation> allegations = new HashSet<>();
    Set<Short> safetyAlerts = new HashSet<>();
    ScreeningToReferral expected = new ScreeningToReferralResourceBuilder()
        .setEndedAt("2017-01-03T11:10:09.999Z").setStartedAt("2017-01-02T10:09:08.999Z")
        .setIncidentDate("2017-01-01").setLegacySourceTable("REFERL_T").setLimitedAccessDate(null)
        .setResponseTime((short) 1519).setScreeningDecisionDetail("evaluate_out")
        .setLimitedAccessAgency("34").setLimitedAccessCode("N").setCommunicationMethod((short) 408)
        .setAdditionalInformation("additional information")
        .setScreeningDecision("Screening Decision").setCurrentLocationOfChildren(null)
        .setParticipants(null).setCrossReports(crossReports).setAddress(null)
        .setAllegations(allegations).setSafetyAlerts(safetyAlerts).setSafetyAlertInformationn(null)
        .createScreeningToReferral();
    ScreeningToReferral actual = new ScreeningTransformer().transform(screening, "0X5", "34");

    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsScreeningToScreeningToReferralWhenCommunicationMethodNull() {
    Set<CrossReport> crossReports = new HashSet<>();
    Set<Allegation> allegations = new HashSet<>();
    Set<Short> safetyAlerts = new HashSet<>();
    ScreeningToReferral expected = new ScreeningToReferralResourceBuilder()
        .setEndedAt("2017-01-03T11:10:09.999Z").setStartedAt("2017-01-02T10:09:08.999Z")
        .setIncidentDate("2017-01-01").setLegacySourceTable("REFERL_T").setLimitedAccessDate(null)
        .setResponseTime((short) 1519).setScreeningDecisionDetail("evaluate_out")
        .setLimitedAccessAgency("34").setLimitedAccessCode("N").setCommunicationMethod(null)
        .setAdditionalInformation("additional information")
        .setScreeningDecision("Screening Decision").setCurrentLocationOfChildren(null)
        .setParticipants(null).setCrossReports(crossReports).setAddress(null)
        .setAllegations(allegations).setSafetyAlerts(safetyAlerts).setSafetyAlertInformationn(null)
        .createScreeningToReferral();
    Screening screening = new ScreeningResourceBuilder().setCommunicationMethod("").build();
    ScreeningToReferral actual = new ScreeningTransformer().transform(screening, "0X5", "34");

    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsScreeningToScreeningToReferralWhenScreeningDecisionDetailBlank() {
    Set<CrossReport> crossReports = new HashSet<>();
    Set<Allegation> allegations = new HashSet<>();
    Set<Short> safetyAlerts = new HashSet<>();
    ScreeningToReferral expected = new ScreeningToReferralResourceBuilder()
        .setEndedAt("2017-01-03T11:10:09.999Z").setStartedAt("2017-01-02T10:09:08.999Z")
        .setIncidentDate("2017-01-01").setLegacySourceTable("REFERL_T").setLimitedAccessDate(null)
        .setResponseTime(null).setScreeningDecisionDetail("evaluate_out")
        .setLimitedAccessAgency("34").setLimitedAccessCode("N").setCommunicationMethod((short) 408)
        .setAdditionalInformation("additional information")
        .setScreeningDecision("Screening Decision").setScreeningDecisionDetail("")
        .setCurrentLocationOfChildren(null).setParticipants(null).setCrossReports(crossReports)
        .setAddress(null).setAllegations(allegations).setSafetyAlerts(safetyAlerts)
        .setSafetyAlertInformationn(null).createScreeningToReferral();
    Screening screening = new ScreeningResourceBuilder().setScreeningDecisionDetail("").build();
    ScreeningToReferral actual = new ScreeningTransformer().transform(screening, "0X5", "34");

    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsScreeningToScreeningToReferralWhenIncidentAddressProvided() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptorEntityBuilder().build();
    AddressIntakeApi nsAddress = new AddressIntakeApiResourceBuilder().setType("Day Care")
        .setLegacyDescriptor(legacyDescriptor).build();

    Address address = new Address("ADDRESS_T", "1234567ABC", "742 Evergreen Terrace", "Springfield",
        1828, "93838", 28, legacyDescriptor);

    Set<CrossReport> crossReports = new HashSet<>();
    Set<Allegation> allegations = new HashSet<>();
    Set<Short> safetyAlerts = new HashSet<>();
    ScreeningToReferral expected = new ScreeningToReferralResourceBuilder()
        .setEndedAt("2017-01-03T11:10:09.999Z").setStartedAt("2017-01-02T10:09:08.999Z")
        .setIncidentDate("2017-01-01").setLegacySourceTable("REFERL_T").setLimitedAccessDate(null)
        .setResponseTime((short) 1519).setScreeningDecisionDetail("evaluate_out")
        .setLimitedAccessAgency("34").setLimitedAccessCode("N").setCommunicationMethod((short) 408)
        .setAdditionalInformation("additional information")
        .setScreeningDecision("Screening Decision").setCurrentLocationOfChildren(null)
        .setParticipants(null).setCrossReports(crossReports).setAddress(address)
        .setAllegations(allegations).setSafetyAlerts(safetyAlerts).setSafetyAlertInformationn(null)
        .createScreeningToReferral();
    Screening screening = new ScreeningResourceBuilder().setIncidentAddress(nsAddress).build();
    ScreeningToReferral actual = new ScreeningTransformer().transform(screening, "0X5", "34");

    assertEquals(actual, expected);
  }

}
