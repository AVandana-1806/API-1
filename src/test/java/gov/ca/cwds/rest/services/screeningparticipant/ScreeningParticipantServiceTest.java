package gov.ca.cwds.rest.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.persistence.EntityNotFoundException;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.fixture.ParticipantIntakeApiResourceBuilder;
import gov.ca.cwds.fixture.ReporterEntityBuilder;
import gov.ca.cwds.fixture.ScreeningEntityBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.enums.ScreeningStatus;
import gov.ca.cwds.rest.services.ParticipantIntakeApiService;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 *
 */
public class ScreeningParticipantServiceTest {

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();

  private ScreeningParticipantService screeningParticipantService =
      new ScreeningParticipantService();
  private ScreeningDao screeningDao;
  private ParticipantIntakeApiService participantIntakeApiService;
  private ParticipantDaoFactoryImpl participantDaoFactory;
  private ParticipantMapperFactoryImpl participantMapperFactoryImpl;

  @Before
  public void setup() {
    screeningDao = mock(ScreeningDao.class);
    participantIntakeApiService = mock(ParticipantIntakeApiService.class);
    participantDaoFactory = mock(ParticipantDaoFactoryImpl.class);
    participantMapperFactoryImpl = mock(ParticipantMapperFactoryImpl.class);

    screeningParticipantService.setScreeningDao(screeningDao);
    screeningParticipantService.setParticipantDaoFactory(participantDaoFactory);
    screeningParticipantService.setParticipantMapperFactoryImpl(participantMapperFactoryImpl);
    screeningParticipantService.setParticipantIntakeApiService(participantIntakeApiService);
  }

  /**
   * 
   */
  @Test(expected = EntityNotFoundException.class)
  public void testScreeningIdNotFoundInPostgres() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("Abc1234567", null, new DateTime(),
        LegacyTable.REPORTER.getName(), null);
    ParticipantIntakeApi participantIntakeApi = new ParticipantIntakeApiResourceBuilder()
        .setScreeningId("18").setLegacyDescriptor(legacyDescriptor).build();
    screeningParticipantService.create(participantIntakeApi);
  }

  /**
   * 
   */
  @Test(expected = ServiceException.class)
  public void testScreeningIdNull() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("Abc1234567", null, new DateTime(),
        LegacyTable.REPORTER.getName(), null);
    ParticipantIntakeApi participantIntakeApi = new ParticipantIntakeApiResourceBuilder()
        .setScreeningId(null).setLegacyDescriptor(legacyDescriptor).build();
    screeningParticipantService.create(participantIntakeApi);
    assertThat("issue_details[0].technical_message", is(equalTo("Screening not found")));
  }

  /**
   * 
   */
  @Test(expected = ServiceException.class)
  public void testScreeningIsSubmitted() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("Abc1234567", null, new DateTime(),
        LegacyTable.REPORTER.getName(), null);
    ParticipantIntakeApi participantIntakeApi = new ParticipantIntakeApiResourceBuilder()
        .setScreeningId("12").setLegacyDescriptor(legacyDescriptor).build();
    ScreeningEntity screeningEntity = new ScreeningEntityBuilder().setId("12")
        .setScreeningStatus(ScreeningStatus.SUBMITTED.getStatus()).build();
    when(screeningDao.find(anyString())).thenReturn(screeningEntity);
    screeningParticipantService.create(participantIntakeApi);
    assertThat("issue_details[0].technical_message",
        is(equalTo("Screeening is already Submitted")));
  }

  /**
   * 
   */
  @Test
  public void testCreateWithLegacyDescriptor() {
    Reporter reporter = new ReporterEntityBuilder().build();
    CrudsDao<CmsPersistentObject> crudsDaoObject = mock(CrudsDao.class);
    when(crudsDaoObject.find(any(String.class))).thenReturn(reporter);
    when(participantMapperFactoryImpl.create(any(String.class)))
        .thenReturn(new ReporterTransformer());
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("Abc1234567", null, new DateTime(),
        LegacyTable.REPORTER.getName(), null);
    ParticipantIntakeApi participantIntakeApi = new ParticipantIntakeApiResourceBuilder()
        .setScreeningId("18").setLegacyDescriptor(legacyDescriptor).build();
    when(screeningDao.find(any(String.class))).thenReturn(new ScreeningEntity());
    when(participantDaoFactory.create(any(String.class))).thenReturn(crudsDaoObject);
    when(participantIntakeApiService.create(any())).thenReturn(participantIntakeApi);
    ParticipantIntakeApi expected = screeningParticipantService.create(participantIntakeApi);
    assertThat(expected, is(notNullValue()));
  }

  /**
   * 
   */
  @Test
  public void createNewParticipant() {
    ParticipantIntakeApi participantIntakeApi = new ParticipantIntakeApiResourceBuilder()
        .setScreeningId("18").setLegacyDescriptor(null).build();
    when(screeningDao.find(any(String.class))).thenReturn(new ScreeningEntity());
    when(participantIntakeApiService.create(any())).thenReturn(participantIntakeApi);
    ParticipantIntakeApi expected = screeningParticipantService.create(participantIntakeApi);
    assertThat(expected, is(notNullValue()));
  }

}
