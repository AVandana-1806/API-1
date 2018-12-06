package gov.ca.cwds.rest.services.screening.participant;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

import gov.ca.cwds.cms.data.access.service.impl.CsecHistoryService;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.SexualExploitationType;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.services.mapper.cms.CsecMapper;
import gov.ca.cwds.rest.services.mapper.cms.CsecMapperImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ParticipantIntakeApiResourceBuilder;
import gov.ca.cwds.fixture.ReporterEntityBuilder;
import gov.ca.cwds.fixture.ScreeningEntityBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.enums.ScreeningStatus;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantDaoFactoryImpl;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantMapper;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantMapperFactoryImpl;
import gov.ca.cwds.rest.services.screeningparticipant.ReporterTransformer;

/**
 * @author CWDS API Team
 */
public class ParticipantTransformerTest {

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();

  private ParticipantTransformer participantTransformer = new ParticipantTransformer();
  private ScreeningDao screeningDao;
  private ClientDao clientDao;
  private ParticipantDaoFactoryImpl participantDaoFactory;
  private ParticipantMapperFactoryImpl participantMapperFactoryImpl;
  private ParticipantMapper<CmsPersistentObject> participantMapper;
  private CsecHistoryService csecHistoryService;
  private CsecMapper csecMapper;
  private CrudsDao<CmsPersistentObject> crudsDaoObject;
  ParticipantIntakeApi participantIntakeApi;
  LegacyDescriptor legacyDescriptor;

  @Before
  public void setup() {
    legacyDescriptor = new LegacyDescriptor("Abc1234567", null, new DateTime(),
        LegacyTable.REPORTER.getName(), null);
    participantIntakeApi = new ParticipantIntakeApiResourceBuilder()
        .setScreeningId("18").setLegacyDescriptor(legacyDescriptor).build();

    screeningDao = mock(ScreeningDao.class);
    when(screeningDao.find(any(String.class))).thenReturn(new ScreeningEntity());
    participantDaoFactory = mock(ParticipantDaoFactoryImpl.class);
    participantMapperFactoryImpl = mock(ParticipantMapperFactoryImpl.class);
    when(participantMapperFactoryImpl.create(any(String.class)))
        .thenReturn(new ReporterTransformer());

    clientDao = mock(ClientDao.class);
    Client probationYouthClient = new ClientEntityBuilder().build();
    when(clientDao.findProbationYouth(any(String.class))).thenReturn(probationYouthClient);

    csecHistoryService = mock(CsecHistoryService.class);
    when(csecHistoryService.findByClientId(anyString())).thenReturn(new ArrayList());
    csecMapper = new CsecMapperImpl();

    crudsDaoObject = mock(CrudsDao.class);
    Reporter reporter = new ReporterEntityBuilder().setReferralId(legacyDescriptor.getId()).build();
    when(crudsDaoObject.find(any(String.class))).thenReturn(reporter);
    when(participantDaoFactory.create(any(String.class))).thenReturn(crudsDaoObject);

    participantTransformer.setCsecHistoryService(csecHistoryService);
    participantTransformer.setCsecMapper(csecMapper);
    participantTransformer.setScreeningDao(screeningDao);
    participantTransformer.setClientDao(clientDao);
    participantTransformer.setParticipantDaoFactory(participantDaoFactory);
    participantTransformer.setParticipantMapperFactoryImpl(participantMapperFactoryImpl);
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldThrowExceptionWhenEntityIsNotFound() {
    when(screeningDao.find(any(String.class))).thenReturn(null);
    participantTransformer.prepareParticipantObject(participantIntakeApi);
  }

  @Test(expected = ServiceException.class)
  public void shouldThrowServiceExceptionWhenScreeningIdIsNull() {
    ParticipantIntakeApi participantIntakeApi = new ParticipantIntakeApiResourceBuilder()
        .setScreeningId(null).setLegacyDescriptor(legacyDescriptor).build();

    participantTransformer.prepareParticipantObject(participantIntakeApi);

    assertThat("issue_details[0].technical_message", is(equalTo("Screening not found")));
  }

  @Test(expected = ServiceException.class)
  public void testScreeningIsSubmitted() {
    ScreeningEntity screeningEntity = new ScreeningEntityBuilder().setId("12")
        .setScreeningStatus(ScreeningStatus.SUBMITTED.getStatus()).build();
    when(screeningDao.find(anyString())).thenReturn(screeningEntity);

    participantTransformer.prepareParticipantObject(participantIntakeApi);

    assertThat("issue_details[0].technical_message",
        is(equalTo("Screeening is already Submitted")));
  }

  @Test
  public void shouldNotTryToBuildParticipantWhenParticipantDoesNotExist() {
    when(crudsDaoObject.find(any(String.class))).thenReturn(null);
    participantTransformer.prepareParticipantObject(participantIntakeApi);
    verify(csecHistoryService, never()).findByClientId(participantIntakeApi.getLegacyDescriptor().getId());
  }

  @Test
  public void shouldReturnNullWhenParticipantDoesNotExist() {
    when(crudsDaoObject.find(any(String.class))).thenReturn(null);
    ParticipantIntakeApi participant = participantTransformer.prepareParticipantObject(participantIntakeApi);
    assertNull(participant);
  }

  @Test
  public void prepareParticipantObjectWithLegacyDescriptor() {
    ParticipantIntakeApi expected =
        participantTransformer.prepareParticipantObject(participantIntakeApi);
    assertThat(expected, is(notNullValue()));
    assertThat(expected.isProbationYouth(), is(Boolean.TRUE));
  }

  @Test
  public void prepareParticipantObjectNewParticipant() {
    when(screeningDao.find(any(String.class))).thenReturn(new ScreeningEntity());
    ParticipantIntakeApi expected =
        participantTransformer.prepareParticipantObject(participantIntakeApi);
    assertThat(expected, is(notNullValue()));
  }

  @Test
  public void shouldAddCsecHistoryAddAnyCsecDataToParticipant() {
    CsecHistory csec = new CsecHistory();
    SexualExploitationType type = new SexualExploitationType();
    type.setSystemId((short)1);
    csec.setSexualExploitationType(type);
    csec.setStartDate(LocalDate.parse("2001-01-30"));
    csec.setEndDate(LocalDate.parse("2001-01-30"));
    List csecList = new <CsecHistory>ArrayList();
    csecList.add(csec);
    when(csecHistoryService.findByClientId(participantIntakeApi.getLegacyDescriptor().getId()))
        .thenReturn(csecList);

    ParticipantIntakeApi expected =
        participantTransformer.prepareParticipantObject(participantIntakeApi);
    verify(csecHistoryService).findByClientId(participantIntakeApi.getLegacyDescriptor().getId());
    assertEquals(1, expected.getCsecs().size());
    Csec foundCsec = expected.getCsecs().get(0);

    assertEquals(csec.getSexualExploitationType().getSystemId().toString(),foundCsec.getCsecCodeId());
    assertEquals(csec.getStartDate(),foundCsec.getStartDate());
    assertEquals(csec.getEndDate(),foundCsec.getEndDate());
    assertEquals(null,foundCsec.getId());
  }
}
