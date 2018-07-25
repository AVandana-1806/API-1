package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.cms.data.access.service.impl.SafetyAlertService;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.legacy.cms.dao.CountiesDao;
import gov.ca.cwds.data.legacy.cms.dao.SafetyAlertActivationReasonTypeDao;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.SafetyAlertActivationReasonType;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.investigation.SafetyAlerts;

/**
 * @author CWDS API Team
 *
 */
public class ReferralSafetyAlertsServiceTest {

  /**
   * Initialize intake code cache
   */
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private ReferralDao referralDao;
  private CountiesDao countiesDao;
  private SafetyAlertActivationReasonTypeDao safetyAlertActivationReasonTypeDao;
  private SafetyAlertService safetyAlertService;
  private ReferralSafetyAlertsService referralSafetyAlertsService =
      new ReferralSafetyAlertsService();

  /**
   * 
   */
  @Before
  public void setup() {
    referralDao = mock(ReferralDao.class);
    countiesDao = mock(CountiesDao.class);
    safetyAlertService = mock(SafetyAlertService.class);
    safetyAlertActivationReasonTypeDao = mock(SafetyAlertActivationReasonTypeDao.class);
  }

  /**
   * Test Safety Alerts saved successfully
   */
  @SuppressWarnings("unchecked")
  @Test
  public void testSaveSafetyAlertsSuccessfully() {
    SafetyAlerts safetyAlerts =
        new SafetyAlerts(new HashSet<>(Arrays.asList((short) 6401)), "Some Danger Fellow");
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("098UijH1gf", null,
        new DateTime("2018-06-11T18:47:07.524Z"), LegacyTable.CLIENT.getName(), null);
    Participant victim =
        new ParticipantResourceBuilder().setLegacyDescriptor(legacyDescriptor).createParticipant();
    Participant Perp = new ParticipantResourceBuilder().setGender("M").createParticipant();
    Participant reporter =
        new ParticipantResourceBuilder().setGender("M").createReporterParticipant();
    Set<Participant> ScreeeningParticpants = new HashSet<>(Arrays.asList(victim, Perp, reporter));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(ScreeeningParticpants).setSafetyAlerts(safetyAlerts.getAlerts())
        .setSafetyAlertInformationn(safetyAlerts.getAlertInformation()).createScreeningToReferral();
    ClientParticipants clientParticipants = new ClientParticipants();
    Set<Participant> participants = screeningToReferral.getParticipants();
    clientParticipants.addParticipants(participants);
    Referral referral = new ReferralEntityBuilder().setId("071FJ86abM")
        .setGovtEntityType((short) 1101).setScreenerNoteText("0I61FGt15L").build();
    when(referralDao.find(anyString())).thenReturn(referral);
    County county = new County();
    county.setSystemId((short) 1101);
    when(countiesDao.findByLogicalId(anyString())).thenReturn(county);
    SafetyAlertActivationReasonType safetyAlertActivationReasonType =
        new SafetyAlertActivationReasonType();
    safetyAlertActivationReasonType.setSystemId((short) 6401);
    when(safetyAlertActivationReasonTypeDao.findBySystemId(any(Short.class)))
        .thenReturn(new SafetyAlertActivationReasonType());
    referralSafetyAlertsService.setReferralDao(referralDao);
    referralSafetyAlertsService.setCountiesDao(countiesDao);
    referralSafetyAlertsService
        .setSafetyAlertActivationReasonTypeDao(safetyAlertActivationReasonTypeDao);
    referralSafetyAlertsService.setSafetyAlertService(safetyAlertService);
    List<SafetyAlert> safetyAlert =
        referralSafetyAlertsService.create(screeningToReferral, "071FJ86abM", clientParticipants);
    assertThat(safetyAlert, is(notNullValue()));
    assertThat(safetyAlert.size(), is(equalTo(1)));
    assertThat(safetyAlert, containsInAnyOrder(hasProperty("fkClientId", is("098UijH1gf"))));
  }

  /**
   * Test return empty Set when safety alerts are empty
   */
  @Test
  public void testEmptySafetyAlerts() {
    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().createScreeningToReferral();
    ClientParticipants clientParticipants = new ClientParticipants();
    Set<Participant> participants = screeningToReferral.getParticipants();
    clientParticipants.addParticipants(participants);
    Referral referral = new ReferralEntityBuilder().setId("071FJ86abM")
        .setGovtEntityType((short) 1101).setScreenerNoteText("0I61FGt15L").build();
    when(referralDao.find(anyString())).thenReturn(referral);
    referralSafetyAlertsService.setReferralDao(referralDao);
    List<SafetyAlert> safetyAlert =
        referralSafetyAlertsService.create(screeningToReferral, "071FJ86abM", clientParticipants);
    assertThat(safetyAlert.size(), is(equalTo(0)));
  }

}
