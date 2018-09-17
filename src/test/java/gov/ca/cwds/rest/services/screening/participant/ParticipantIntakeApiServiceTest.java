package gov.ca.cwds.rest.services.screening.participant;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.ns.AllegationDao;
import gov.ca.cwds.data.ns.CsecDao;
import gov.ca.cwds.data.ns.LegacyDescriptorDao;
import gov.ca.cwds.data.ns.ParticipantAddressesDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.ParticipantPhoneNumbersDao;
import gov.ca.cwds.data.ns.PhoneNumbersDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantAddresses;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantPhoneNumbers;
import gov.ca.cwds.data.persistence.ns.PhoneNumbers;
import gov.ca.cwds.fixture.AddressesEntityBuilder;
import gov.ca.cwds.fixture.ParticipantEntityBuilder;
import gov.ca.cwds.fixture.PhoneNumbersEntityBuilder;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.resources.parameter.ParticipantResourceParameters;
import gov.ca.cwds.rest.services.AddressIntakeApiService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;
import gov.ca.cwds.rest.services.mapper.CsecMapper;
import gov.ca.cwds.rest.services.mapper.SafelySurrenderedBabiesMapper;

/**
 */
public class ParticipantIntakeApiServiceTest implements ServiceTestTemplate {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private ParticipantDao participantDao;
  @Mock
  private AllegationDao allegationDao;
  @Mock
  private LegacyDescriptorDao legacyDescriptorDao;
  @Mock
  private ScreeningDao screeningDao;
  @Mock
  private AddressesDao addressesDao;
  @Mock
  private ParticipantAddressesDao participantAddressesDao;
  @Mock
  private AddressIntakeApiService addressIntakeApiService;
  @Mock
  private PhoneNumbersDao phoneNumbersDao;
  @Mock
  private ParticipantPhoneNumbersDao participantPhoneNumbersDao;
  @Mock
  private CsecDao csecDao;

  ParticipantEntity participantEntity;
  Addresses addresses1;
  Addresses addresses2;
  PhoneNumbers phoneNumbers1;
  PhoneNumbers phoneNumbers2;
  LegacyDescriptorEntity legacyDescriptorEntity;
  LegacyDescriptor legacyDescriptor;

  @InjectMocks
  private ParticipantIntakeApiService participantIntakeApiService =
      new ParticipantIntakeApiService();

  @Before
  @Override
  public void setup() throws Exception {
    participantIntakeApiService.setCsecMapper(CsecMapper.INSTANCE);
    participantIntakeApiService
        .setSafelySurrenderedBabiesMapper(SafelySurrenderedBabiesMapper.INSTANCE);

    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);

    MockitoAnnotations.initMocks(this);

    String participantId = "100";
    String aId1 = "1";
    String aId2 = "2";
    String pN1 = "11";
    String pN2 = "22";

    participantEntity = new ParticipantEntityBuilder().setId(participantId).build();
    addresses1 = new AddressesEntityBuilder().setId(aId1).build();
    addresses2 = new AddressesEntityBuilder().setId(aId2).build();
    phoneNumbers1 =
        new PhoneNumbersEntityBuilder().setId(pN1).setNumber("111111111").setType("Home").build();
    phoneNumbers2 =
        new PhoneNumbersEntityBuilder().setId(pN2).setNumber("222222222").setType("Work").build();


    when(addressesDao.find(aId1)).thenReturn(addresses1);
    when(addressesDao.find(aId2)).thenReturn(null);
    when(addressesDao.create(any())).thenReturn(addresses2);

    when(phoneNumbersDao.find(pN1)).thenReturn(phoneNumbers1);
    when(phoneNumbersDao.find(pN2)).thenReturn(null);
    when(phoneNumbersDao.create(any())).thenReturn(phoneNumbers2);

    when(participantDao.find(participantId)).thenReturn(null);
    when(participantDao.create(any())).thenReturn(participantEntity);

    legacyDescriptor = new LegacyDescriptor("JhHq86Iaaf", "1118-8618-0978-2140657", new DateTime(),
        "tableName", "a table to store data");
    legacyDescriptorEntity = new LegacyDescriptorEntity(legacyDescriptor, "", 219L);
    when(legacyDescriptorDao.create(any())).thenReturn(legacyDescriptorEntity);
  }


  @Override
  @Test
  public void testFindThrowsAssertionError() throws Exception {
    try {
      participantIntakeApiService.find(null);
      fail();
    } catch (ServiceException e) {
      assertEquals("NULL argument for FIND participant", e.getMessage());
    }

    ParticipantResourceParameters nullScreeningIdParameters =
        new ParticipantResourceParameters(null, "-1");
    try {
      participantIntakeApiService.find(nullScreeningIdParameters);
      fail();
    } catch (ServiceException e) {
      assertEquals("NULL screening/participant id for FIND participant", e.getMessage());
    }

    ParticipantResourceParameters nullParticipantIdParameters =
        new ParticipantResourceParameters("-1", null);
    try {
      participantIntakeApiService.find(nullParticipantIdParameters);
      fail();
    } catch (ServiceException e) {
      assertEquals("NULL screening/participant id for FIND participant", e.getMessage());
    }
  }

  @Override
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    String participantId = "100";
    String aId1 = "1";
    String aId2 = "2";
    String pN1 = "11";
    String pN2 = "22";


    ParticipantEntity participantEntity =
        new ParticipantEntityBuilder().setId(participantId).build();
    Addresses addresses1 = new AddressesEntityBuilder().setId(aId1).build();
    Addresses addresses2 = new AddressesEntityBuilder().setId(aId2).build();
    PhoneNumbers phoneNumbers1 =
        new PhoneNumbersEntityBuilder().setId(pN1).setNumber("111111111").setType("Home").build();
    PhoneNumbers phoneNumbers2 =
        new PhoneNumbersEntityBuilder().setId(pN2).setNumber("222222222").setType("Work").build();

    when(csecDao.findByParticipantId(participantId)).thenReturn(new ArrayList<>());
    when(addressesDao.findByParticipant(participantId))
        .thenReturn(Arrays.asList(addresses1, addresses2));
    when(phoneNumbersDao.findByParticipant(participantId))
        .thenReturn(Arrays.asList(phoneNumbers1, phoneNumbers2));
    when(participantDao.findByScreeningIdAndParticipantId("-1", participantId))
        .thenReturn(participantEntity);

    ParticipantIntakeApi expected = new ParticipantIntakeApi(participantEntity);

    AddressIntakeApi addressIntakeApi1 = new AddressIntakeApi(addresses1);
    AddressIntakeApi addressIntakeApi2 = new AddressIntakeApi(addresses2);
    expected.addAddresses(Arrays.asList(addressIntakeApi1, addressIntakeApi2));

    PhoneNumber phoneNumber1 = new PhoneNumber(phoneNumbers1);
    PhoneNumber phoneNumber2 = new PhoneNumber(phoneNumbers2);
    expected.addPhoneNumbers(new HashSet<>(Arrays.asList(phoneNumber1, phoneNumber2)));
    expected.setSafelySurenderedBabies(null);

    ParticipantIntakeApi found =
        participantIntakeApiService.find(new ParticipantResourceParameters("-1", participantId));
    assertThat(found, is(expected));

  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    when(participantDao.findByScreeningIdAndParticipantId("-1", "000")).thenReturn(null);
    ParticipantIntakeApi found =
        participantIntakeApiService.find(new ParticipantResourceParameters("-1", "000"));

    assertThat(found, is(nullValue()));

  }

  @Override
  public void testFindThrowsNotImplementedException() throws Exception {

  }

  @Override
  @Test
  public void testCreateThrowsAssertionError() throws Exception {
    try {
      participantIntakeApiService.create(null);
      fail();
    } catch (ServiceException e) {
      assertEquals("NULL argument for CREATE participant", e.getMessage());
    }
  }

  @Override
  public void testCreateReturnsPostedClass() throws Exception {

  }

  @Override
  @Test
  @Ignore
  public void testCreateReturnsCorrectEntity() throws Exception {

    ParticipantIntakeApi expected = new ParticipantIntakeApi(participantEntity);
    ParticipantIntakeApi expected00 = new ParticipantIntakeApi(participantEntity);

    AddressIntakeApi addressIntakeApi1 = new AddressIntakeApi(addresses1);
    AddressIntakeApi addressIntakeApi11 = new AddressIntakeApi(addresses1);
    AddressIntakeApi addressIntakeApi2 = new AddressIntakeApi(addresses2);
    AddressIntakeApi addressIntakeApi22 = new AddressIntakeApi(addresses2);
    expected.addAddresses(Arrays.asList(addressIntakeApi1, addressIntakeApi2));
    expected00.addAddresses(Arrays.asList(addressIntakeApi11, addressIntakeApi22));

    PhoneNumber phoneNumber1 = new PhoneNumber(phoneNumbers1);
    PhoneNumber phoneNumber11 = new PhoneNumber(phoneNumbers1);
    PhoneNumber phoneNumber2 = new PhoneNumber(phoneNumbers2);
    PhoneNumber phoneNumber22 = new PhoneNumber(phoneNumbers2);
    expected.addPhoneNumbers(new HashSet<>(Arrays.asList(phoneNumber1, phoneNumber2)));
    expected00.addPhoneNumbers(new HashSet<>(Arrays.asList(phoneNumber11, phoneNumber22)));

    ParticipantIntakeApi found = participantIntakeApiService.create(expected);
    assertThat(found, is(expected00));
  }

  @Test
  public void shouldContainALegacyDescriptorWhenCreated() {
    ParticipantIntakeApi expected = new ParticipantIntakeApi(participantEntity);
    expected.setLegacyDescriptor(legacyDescriptor);
    ParticipantIntakeApi found = participantIntakeApiService.persistParticipantObjectInNS(expected);
    LegacyDescriptor legacyDescriptor = found.getLegacyDescriptor();
    assertEquals(legacyDescriptor.getId(), found.getLegacyDescriptor().getId());
    assertEquals(legacyDescriptor.getUiId(), found.getLegacyDescriptor().getUiId());
  }

  @Override
  public void testCreateBlankIDError() throws Exception {

  }

  @Override
  public void testCreateNullIDError() throws Exception {}

  @Override
  public void testCreateEmptyIDError() throws Exception {

  }

  @Override
  public void testCreateThrowsNotImplementedException() throws Exception {

  }

  @Override
  @Test
  public void testDeleteThrowsAssertionError() throws Exception {
    try {
      participantIntakeApiService.delete(null);
      fail();
    } catch (ServiceException e) {
      assertEquals("NULL argument for DELETE participant", e.getMessage());
    }

    ParticipantResourceParameters nullScreeningIdParameters =
        new ParticipantResourceParameters(null, "-1");
    try {
      participantIntakeApiService.delete(nullScreeningIdParameters);
      fail();
    } catch (ServiceException e) {
      assertEquals("NULL screening/participant id for DELETE participant", e.getMessage());
    }

    ParticipantResourceParameters nullParticipantIdParameters =
        new ParticipantResourceParameters("-1", null);
    try {
      participantIntakeApiService.delete(nullParticipantIdParameters);
      fail();
    } catch (ServiceException e) {
      assertEquals("NULL screening/participant id for DELETE participant", e.getMessage());
    }
  }

  @Test
  public void testDeleteNullParticipant() {
    participantIntakeApiService.delete(new ParticipantResourceParameters("1", "2"));
  }

  @Override
  public void testDeleteDelegatesToCrudsService() throws Exception {

  }

  @Override
  public void testDeleteReturnsNullWhenNotFound() throws Exception {

  }

  @Override
  public void testDeleteThrowsNotImplementedException() throws Exception {

  }

  @Override
  @Test
  public void testDeleteReturnsClass() throws Exception {
    String participantId = "100";
    String aId1 = "1";
    String aId2 = "2";
    String pN1 = "11";
    String pN2 = "22";


    ParticipantEntity participantEntity =
        new ParticipantEntityBuilder().setId(participantId).build();

    Addresses addresses1 = new AddressesEntityBuilder().setId(aId1).build();
    Addresses addresses2delete = new AddressesEntityBuilder().setId(aId2).build();

    when(csecDao.findByParticipantId(participantId)).thenReturn(new ArrayList<>());
    PhoneNumbers phoneNumbers1 =
        new PhoneNumbersEntityBuilder().setId(pN1).setNumber("111111111").setType("Home").build();
    PhoneNumbers phoneNumbers2delete =
        new PhoneNumbersEntityBuilder().setId(pN2).setNumber("222222222").setType("Work").build();

    ParticipantAddresses participantAddresses1 =
        new ParticipantAddresses(participantEntity, addresses1);
    ParticipantAddresses participantAddresses2 =
        new ParticipantAddresses(participantEntity, addresses2delete);

    ParticipantPhoneNumbers participantPhoneNumbers1 =
        new ParticipantPhoneNumbers(participantEntity, phoneNumbers1);
    ParticipantPhoneNumbers participantPhoneNumbers2 =
        new ParticipantPhoneNumbers(participantEntity, phoneNumbers2delete);

    when(participantAddressesDao.findByParticipantId(participantId))
        .thenReturn(new HashSet<>(Arrays.asList(participantAddresses1, participantAddresses2)));
    when(participantPhoneNumbersDao.findByParticipantId(participantId)).thenReturn(
        new HashSet<>(Arrays.asList(participantPhoneNumbers1, participantPhoneNumbers2)));
    when(participantDao.findByScreeningIdAndParticipantId("-1", participantId))
        .thenReturn(participantEntity);

    ParticipantIntakeApi expected = new ParticipantIntakeApi(participantEntity);

    ParticipantIntakeApi found =
        participantIntakeApiService.delete(new ParticipantResourceParameters("-1", participantId));

    assertThat(found, is(expected));
  }

  @Override
  @Test
  public void testUpdateThrowsAssertionError() throws Exception {
    try {
      participantIntakeApiService.update(null, new ParticipantIntakeApi());
      fail();
    } catch (Exception e) {
      assertEquals("NULL argument for UPDATE participant", e.getMessage());
    }

    try {
      participantIntakeApiService.update(new ParticipantResourceParameters(null, null), null);
      fail();
    } catch (Exception e) {
      assertEquals("NULL argument for UPDATE participant", e.getMessage());
    }

    ParticipantResourceParameters nullScreeningIdParameters =
        new ParticipantResourceParameters(null, "-1");
    try {
      participantIntakeApiService.update(nullScreeningIdParameters, new ParticipantIntakeApi());
      fail();
    } catch (ServiceException e) {
      assertEquals("NULL screening/participant id for UPDATE participant", e.getMessage());
    }

    ParticipantResourceParameters nullParticipantIdParameters =
        new ParticipantResourceParameters("-1", null);
    try {
      participantIntakeApiService.update(nullParticipantIdParameters, new ParticipantIntakeApi());
      fail();
    } catch (ServiceException e) {
      assertEquals("NULL screening/participant id for UPDATE participant", e.getMessage());
    }
  }

  @Override
  public void testUpdateReturnsDomain() throws Exception {

  }

  @Override
  @Test
  public void testUpdateReturnsCorrectEntity() throws Exception {
    String pId = "100";
    String aId1 = "1";
    String aId2 = "2";
    String aId3 = "3";
    String pN1 = "11";
    String pN2 = "22";
    String pN3 = "33";


    ParticipantEntity participantEntity = new ParticipantEntityBuilder().setId(pId).build();

    Addresses addresses1 = new AddressesEntityBuilder().setId(aId1).build();
    Addresses addresses2delete = new AddressesEntityBuilder().setId(aId2).build();
    Addresses addresses3new = new AddressesEntityBuilder().setId(aId3).build();
    PhoneNumbers phoneNumbers1 =
        new PhoneNumbersEntityBuilder().setId(pN1).setNumber("111111111").setType("Home").build();
    PhoneNumbers phoneNumbers2delete =
        new PhoneNumbersEntityBuilder().setId(pN2).setNumber("222222222").setType("Work").build();
    PhoneNumbers phoneNumbers3new =
        new PhoneNumbersEntityBuilder().setId(pN3).setNumber("3333333333").setType("Cell").build();

    ParticipantAddresses participantAddresses1 =
        new ParticipantAddresses(participantEntity, addresses1);
    ParticipantAddresses participantAddresses2 =
        new ParticipantAddresses(participantEntity, addresses2delete);
    ParticipantAddresses participantAddresses3 =
        new ParticipantAddresses(participantEntity, addresses3new);

    ParticipantPhoneNumbers participantPhoneNumbers1 =
        new ParticipantPhoneNumbers(participantEntity, phoneNumbers1);
    ParticipantPhoneNumbers participantPhoneNumbers2 =
        new ParticipantPhoneNumbers(participantEntity, phoneNumbers2delete);
    ParticipantPhoneNumbers participantPhoneNumbers3 =
        new ParticipantPhoneNumbers(participantEntity, phoneNumbers3new);

    when(csecDao.findByParticipantId(pId)).thenReturn(new ArrayList<>());

    when(addressesDao.find(aId1)).thenReturn(addresses1);
    when(addressesDao.find(aId2)).thenReturn(addresses2delete);
    when(addressesDao.find(aId3)).thenReturn(null);
    when(addressesDao.create(any())).thenReturn(addresses3new);

    when(phoneNumbersDao.find(pN1)).thenReturn(phoneNumbers1);
    when(phoneNumbersDao.find(pN2)).thenReturn(phoneNumbers2delete);
    when(phoneNumbersDao.find(pN3)).thenReturn(null);
    when(phoneNumbersDao.create(any())).thenReturn(phoneNumbers3new);

    when(participantAddressesDao.findByParticipantId(pId))
        .thenReturn(new HashSet<>(Arrays.asList(participantAddresses1, participantAddresses2)));
    when(participantAddressesDao.create(any())).thenReturn(participantAddresses3);
    when(participantPhoneNumbersDao.findByParticipantId(pId)).thenReturn(
        new HashSet<>(Arrays.asList(participantPhoneNumbers1, participantPhoneNumbers2)));
    when(participantPhoneNumbersDao.create(any())).thenReturn(participantPhoneNumbers3);

    when(participantDao.findByScreeningIdAndParticipantId("-1", pId)).thenReturn(participantEntity);
    when(participantDao.update(any())).thenReturn(participantEntity);

    LegacyDescriptorEntity legacyDesciptor = new LegacyDescriptorEntity();
    when(legacyDescriptorDao.findParticipantLegacyDescriptor(pId)).thenReturn(legacyDesciptor);

    ParticipantIntakeApi expected = new ParticipantIntakeApi(participantEntity);
    ParticipantIntakeApi expected00 = new ParticipantIntakeApi(participantEntity);

    AddressIntakeApi addressIntakeApi1 = new AddressIntakeApi(addresses1);
    AddressIntakeApi addressIntakeApi11 = new AddressIntakeApi(addresses1);
    AddressIntakeApi addressIntakeApi3 = new AddressIntakeApi(addresses3new);
    AddressIntakeApi addressIntakeApi33 = new AddressIntakeApi(addresses3new);
    expected.addAddresses(Arrays.asList(addressIntakeApi1, addressIntakeApi3));
    expected00.addAddresses(Arrays.asList(addressIntakeApi11, addressIntakeApi33));
    expected00.setSafelySurenderedBabies(null);

    expected00.setLegacyDescriptor(new LegacyDescriptor());

    PhoneNumber phoneNumber1 = new PhoneNumber(phoneNumbers1);
    PhoneNumber phoneNumber11 = new PhoneNumber(phoneNumbers1);
    PhoneNumber phoneNumber3 = new PhoneNumber(phoneNumbers3new);
    PhoneNumber phoneNumber33 = new PhoneNumber(phoneNumbers3new);
    expected.addPhoneNumbers(new HashSet<>(Arrays.asList(phoneNumber1, phoneNumber3)));
    expected00.addPhoneNumbers(new HashSet<>(Arrays.asList(phoneNumber11, phoneNumber33)));
    expected00.setScreeningId("-1");

    ParticipantIntakeApi found =
        participantIntakeApiService.update(new ParticipantResourceParameters("-1", pId), expected);
    assertThat(found, is(expected00));

  }

  @Override
  public void testUpdateThrowsServiceException() throws Exception {

  }

  @Override
  public void testUpdateThrowsNotImplementedException() throws Exception {

  }

}
