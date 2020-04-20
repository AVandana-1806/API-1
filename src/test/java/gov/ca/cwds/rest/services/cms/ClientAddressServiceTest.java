package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import gov.ca.cwds.data.cms.ClientAddressDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.ClientAddressEntityBuilder;
import gov.ca.cwds.fixture.ClientAddressResourceBuilder;
import gov.ca.cwds.fixture.CmsAddressResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.referentialintegrity.RIClientAddress;

/**
 * @author CWS-NS2
 *
 */
@SuppressWarnings("javadoc")
public class ClientAddressServiceTest {
  ClientAddressService clientAddressService;

  ClientAddressDao clientAddressDao;
  StaffPersonDao staffpersonDao;
  TriggerTablesDao triggerTablesDao;
  LACountyTrigger laCountyTrigger;
  NonLACountyTriggers nonLACountyTriggers;
  RIClientAddress riClientAddress;
  Validator validator;
  AddressService addressService;
  private MessageBuilder messageBuilder;

  private static Boolean isLaCountyTrigger = false;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    clientAddressDao = mock(ClientAddressDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    riClientAddress = mock(RIClientAddress.class);
    validator = mock(Validator.class);
    addressService = mock(AddressService.class);
    messageBuilder = new MessageBuilder();

    clientAddressService =
        new ClientAddressService(clientAddressDao, staffpersonDao, triggerTablesDao,
            laCountyTrigger, nonLACountyTriggers, riClientAddress, validator, addressService);
  }

  /**
   * 
   */
  @Test
  public void shouldReturnNullWhenNotClientAddressesAreFound() {
    when(clientAddressDao.findByAddressAndClient(any(), any())).thenReturn(new ArrayList<>());
    Address address = mock(Address.class);
    when(address.getLegacyDescriptor()).thenReturn(new LegacyDescriptor());
    Participant participant = mock(Participant.class);

    List<Response> foundClients = clientAddressService.findByAddressAndClient(address, participant);
    assertThat(foundClients, is(empty()));
  }

  /**
   * 
   */
  @Test
  public void shouldReturnTheNumberOfClientAddressesFound() {
    ClientAddress clientAddress1 = mock(ClientAddress.class);
    when(clientAddress1.getId()).thenReturn("1");
    ClientAddress clientAddress2 = mock(ClientAddress.class);
    when(clientAddress2.getId()).thenReturn("2");
    List<ClientAddress> persistedClients = new ArrayList<ClientAddress>();
    persistedClients.add(clientAddress1);
    persistedClients.add(clientAddress2);
    when(clientAddressDao.findByAddressAndClient(any(), any())).thenReturn(persistedClients);

    Address address = mock(Address.class);
    when(address.getLegacyDescriptor()).thenReturn(new LegacyDescriptor());

    Participant participant = mock(Participant.class);

    List<Response> foundClients = clientAddressService.findByAddressAndClient(address, participant);
    assertEquals("Expected both elements returned", persistedClients.size(), foundClients.size());
    assertEquals("Expected first address to have been returned", persistedClients.get(0).getId(),
        clientAddress1.getId());
    assertEquals("Expected second address to have been returned", persistedClients.get(1).getId(),
        clientAddress2.getId());
  }

  // find
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    Response found = clientAddressService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @Test
  public void testDeleteDelegatesToCrudsService() {
    clientAddressService.delete("ABC2345678");
    verify(clientAddressDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void clientAddressServiceDeleteReturnsNotNull() throws Exception {
    String id = "LOmv8Jp0Nu";
    gov.ca.cwds.rest.api.domain.cms.ClientAddress expected =
        new ClientAddressResourceBuilder().buildClientAddress();
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddress =
        new gov.ca.cwds.data.persistence.cms.ClientAddress(id, expected, "0XA", new Date());

    when(clientAddressDao.delete(id)).thenReturn(clientAddress);
    gov.ca.cwds.rest.api.domain.cms.ClientAddress found = clientAddressService.delete(id);
    assertThat(found.getClientId(), is(equalTo("FKCLIENTXX")));
  }

  // update test
  @Test
  public void testUpdateReturnsCorrectEntity() throws Exception {
    String id = "LOmv8Jp0Nu";
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().setEffEndDt("2017-01-10").buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddress =
        new gov.ca.cwds.data.persistence.cms.ClientAddress(id, clientAddressDomain, "ABC",
            new Date());

    when(clientAddressDao.find("ABC1234567")).thenReturn(clientAddress);
    when(clientAddressDao.update(any())).thenReturn(clientAddress);

    Object retval = clientAddressService.update("ABC1234567", clientAddressDomain);
    assertEquals(retval.getClass(), gov.ca.cwds.rest.api.domain.cms.ClientAddress.class);
  }

  @Test
  public void testUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
          new ClientAddressResourceBuilder().setEffEndDt("2017-01-10").buildClientAddress();

      when(clientAddressDao.update(any())).thenThrow(EntityNotFoundException.class);

      clientAddressService.update("ZZZZZZZZZZ", clientAddressDomain);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test
  @Test
  public void testCreateReturnsPostedClass() throws Exception {
    String id = "QcNOYA10Nu";
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress(id, clientAddressDomain, "ABC",
            new Date());

    gov.ca.cwds.rest.api.domain.cms.ClientAddress request =
        new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(toCreate);

    Response response = clientAddressService.create(request);
    assertEquals(response.getClass(), gov.ca.cwds.rest.api.domain.cms.ClientAddress.class);
  }

  @Test
  public void testCreateReturnsNonNull() throws Exception {
    String id = "QcNOYA10Nu";
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress(id, clientAddressDomain, "ABC",
            new Date());

    gov.ca.cwds.rest.api.domain.cms.ClientAddress request =
        new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(toCreate);

    Response postedClientAddress = clientAddressService.create(request);
    assertThat(postedClientAddress, is(notNullValue()));
  }

  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    String id = "QcNOYA10Nu";
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress(id, clientAddressDomain, "ABC",
            new Date());

    gov.ca.cwds.rest.api.domain.cms.ClientAddress request =
        new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(toCreate);

    Response expected = new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);
    Response returned = clientAddressService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void testCreateThrowsEntityExistsException() throws Exception {
    try {
      gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
          new ClientAddressResourceBuilder().buildClientAddress();

      when(clientAddressDao.create(any())).thenThrow(EntityExistsException.class);

      clientAddressService.create(clientAddressDomain);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Test
  public void testCreateNullIDError() throws Exception {
    try {
      gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
          new ClientAddressResourceBuilder().buildClientAddress();

      gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientAddress(null, clientAddressDomain, "ABC",
              new Date());

      when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
          .thenReturn(toCreate);

      Response expected = new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);
    } catch (ServiceException e) {
      assertEquals("ClientAddress ID cannot be blank", e.getMessage());
    }

  }

  @Test
  public void testCreateBlankIDError() throws Exception {
    try {
      gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
          new ClientAddressResourceBuilder().buildClientAddress();

      gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientAddress("  ", clientAddressDomain, "ABC",
              new Date());

      when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
          .thenReturn(toCreate);

      Response expected = new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);
    } catch (ServiceException e) {
      assertEquals("Assignment ID cannot be blank", e.getMessage());
    }
  }

  @Test
  public void testCreateLACountyTriggerForClientAddress() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("1234567ABC", clientAddressDomain, "BTr",
            new Date());

    gov.ca.cwds.rest.api.domain.cms.ClientAddress request =
        new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("19");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(toCreate);

    when(laCountyTrigger.createClientAddressCountyTrigger(
        any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
            .thenAnswer(new Answer<Boolean>() {


              @Override
              public Boolean answer(InvocationOnMock invocation) throws Throwable {
                isLaCountyTrigger = true;
                return true;
              }
            });

    clientAddressService.create(request);
    assertThat(isLaCountyTrigger, is(true));

  }

  @Test
  public void testCreateLACountyTriggerForClientAddressNotCreated() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("1234567ABC", clientAddressDomain, "q1p",
            new Date());

    gov.ca.cwds.rest.api.domain.cms.ClientAddress request =
        new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("20");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(toCreate);

    when(laCountyTrigger.createClientAddressCountyTrigger(
        any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
            .thenAnswer(new Answer<Boolean>() {


              @Override
              public Boolean answer(InvocationOnMock invocation) throws Throwable {
                isLaCountyTrigger = true;
                return true;
              }
            });

    clientAddressService.create(request);
    assertThat(isLaCountyTrigger, is(true));

  }

  @Test
  public void testUpdateLACountyTriggerForClientAddressNotCreated() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("1234567ABC", clientAddressDomain, "BTr",
            new Date());

    gov.ca.cwds.rest.api.domain.cms.ClientAddress request =
        new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("19");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(clientAddressDao.update(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(toCreate);

    when(laCountyTrigger.createClientAddressCountyTrigger(
        any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
            .thenAnswer(new Answer<Boolean>() {
              @Override
              public Boolean answer(InvocationOnMock invocation) throws Throwable {
                isLaCountyTrigger = true;
                return true;
              }
            });

    clientAddressService.update("1234567ABC", request);
    assertThat(isLaCountyTrigger, is(true));
  }

  @Test
  public void testForAddressCreate() {
    Address adddress1 = new AddressResourceBuilder().createAddress();
    gov.ca.cwds.rest.api.domain.cms.Address cmsAddress =
        new CmsAddressResourceBuilder().buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC124569", cmsAddress, "0X5", new Date());
    gov.ca.cwds.rest.api.domain.cms.PostedAddress postedAddress =
        new gov.ca.cwds.rest.api.domain.cms.PostedAddress(address, true);
    Participant particpant = new ParticipantResourceBuilder()
        .setAddresses(new HashSet<>(Arrays.asList(adddress1))).createParticipant();
    when(addressService.create(any())).thenReturn(postedAddress);

    ClientAddress clientAddress = new ClientAddressEntityBuilder().buildClientAddress();
    when(clientAddressDao.create(any())).thenReturn(clientAddress);
    clientAddressService.saveClientAddress(particpant, "ABC1234567", "ABC1234568", messageBuilder);
    verify(addressService, times(1)).create(any());
  }

  @Test
  public void testFailureWhenAddressIdEmpty() {
    Address adddress1 = new AddressResourceBuilder().createAddress();
    gov.ca.cwds.rest.api.domain.cms.Address cmsAddress =
        new CmsAddressResourceBuilder().buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC124569", cmsAddress, "0X5", new Date());
    gov.ca.cwds.rest.api.domain.cms.PostedAddress postedAddress =
        new gov.ca.cwds.rest.api.domain.cms.PostedAddress(address, false);
    Participant particpant = new ParticipantResourceBuilder()
        .setAddresses(new HashSet<>(Arrays.asList(adddress1))).createParticipant();
    when(addressService.create(any())).thenReturn(postedAddress);

    ClientAddress clientAddress = new ClientAddressEntityBuilder().buildClientAddress();
    when(clientAddressDao.create(any())).thenReturn(clientAddress);
    clientAddressService.saveClientAddress(particpant, "ABC1234567", "ABC1234568", messageBuilder);
    verify(clientAddressDao, times(0)).create(any());
  }

  @Test
  public void testFailureWhenClientIdEmpty() {
    Address adddress1 = new AddressResourceBuilder().createAddress();
    gov.ca.cwds.rest.api.domain.cms.Address cmsAddress =
        new CmsAddressResourceBuilder().buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC124569", cmsAddress, "0X5", new Date());
    gov.ca.cwds.rest.api.domain.cms.PostedAddress postedAddress =
        new gov.ca.cwds.rest.api.domain.cms.PostedAddress(address, true);
    Participant particpant = new ParticipantResourceBuilder()
        .setAddresses(new HashSet<>(Arrays.asList(adddress1))).createParticipant();
    when(addressService.create(any())).thenReturn(postedAddress);

    ClientAddress clientAddress = new ClientAddressEntityBuilder().buildClientAddress();
    when(clientAddressDao.create(any())).thenReturn(clientAddress);
    clientAddressService.saveClientAddress(particpant, "ABC1234567", "", messageBuilder);
    verify(clientAddressDao, times(0)).create(any());
  }

  @Test
  public void testForAddressUpdate() {
    DateTime dateTime = new DateTime();
    Address adddress1 = new AddressResourceBuilder().setLegacyId("ABC0987654").setCity("fremont")
        .setLegacyDescriptor(new LegacyDescriptor(null, null, dateTime, null, null))
        .createAddress();
    gov.ca.cwds.rest.api.domain.cms.Address cmsAddress =
        new CmsAddressResourceBuilder().setLastUpdatedTime(dateTime).buildCmsAddress();

    Participant particpant = new ParticipantResourceBuilder()
        .setAddresses(new HashSet<>(Arrays.asList(adddress1))).createParticipant();
    when(addressService.find(any())).thenReturn(cmsAddress);
    ClientAddress clientAddress = new ClientAddressEntityBuilder().buildClientAddress();
    when(clientAddressDao.create(any())).thenReturn(clientAddress);
    clientAddressService.saveClientAddress(particpant, "ABC1234567", "ABC1234568", messageBuilder);
    Set<Address> savedAddresses = particpant.getAddresses();
    Iterator<Address> it = savedAddresses.iterator();
    Address updatedAddress = it.next();
    verify(addressService, times(0)).update(any(), any());
    assertThat(updatedAddress.getLegacyId(), is(equalTo("ABC0987654")));
    assertThat(updatedAddress.getCity(), is(equalTo("fremont")));
  }

  @Test
  public void testFailureWhenLastUpdatedTimeDiffer() {
    DateTime dateTime = new DateTime();
    DateTime dateTime1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        .parseDateTime("2004-03-31T09:45:58.000-0800");
    Address adddress1 = new AddressResourceBuilder().setLegacyId("ABC0987654").setCity("fremont")
        .setLegacyDescriptor(new LegacyDescriptor(null, null, dateTime, null, null))
        .createAddress();
    gov.ca.cwds.rest.api.domain.cms.Address cmsAddress =
        new CmsAddressResourceBuilder().setLastUpdatedTime(dateTime1).buildCmsAddress();

    Participant particpant = new ParticipantResourceBuilder()
        .setAddresses(new HashSet<>(Arrays.asList(adddress1))).createParticipant();
    when(addressService.find(any())).thenReturn(cmsAddress);
    ClientAddress clientAddress = new ClientAddressEntityBuilder().buildClientAddress();
    when(clientAddressDao.create(any())).thenReturn(clientAddress);
    clientAddressService.saveClientAddress(particpant, "ABC1234567", "ABC1234568", messageBuilder);
    verify(addressService, times(0)).update(any(), any());
  }

  @Test
  public void testFailureWhenGivenAddressNotFound() {
    DateTime dateTime = new DateTime();
    Address adddress1 = new AddressResourceBuilder().setLegacyId("ABC0987654").setCity("fremont")
        .setLegacyDescriptor(new LegacyDescriptor(null, null, dateTime, null, null))
        .createAddress();

    Participant particpant = new ParticipantResourceBuilder()
        .setAddresses(new HashSet<>(Arrays.asList(adddress1))).createParticipant();
    when(addressService.find(any())).thenReturn(null);
    ClientAddress clientAddress = new ClientAddressEntityBuilder().buildClientAddress();
    when(clientAddressDao.create(any())).thenReturn(clientAddress);
    clientAddressService.saveClientAddress(particpant, "ABC1234567", "ABC1234568", messageBuilder);
    verify(addressService, times(0)).update(any(), any());
  }

  @Test
  public void testForClientAddressCreateWhenNewAddressCreated() {
    Address adddress1 = new AddressResourceBuilder().createAddress();
    gov.ca.cwds.rest.api.domain.cms.Address cmsAddress =
        new CmsAddressResourceBuilder().buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC124569", cmsAddress, "0X5", new Date());
    gov.ca.cwds.rest.api.domain.cms.PostedAddress postedAddress =
        new gov.ca.cwds.rest.api.domain.cms.PostedAddress(address, true);
    Participant particpant = new ParticipantResourceBuilder()
        .setAddresses(new HashSet<>(Arrays.asList(adddress1))).createParticipant();
    when(addressService.create(any())).thenReturn(postedAddress);

    ClientAddress clientAddress = new ClientAddressEntityBuilder().buildClientAddress();
    when(clientAddressDao.create(any())).thenReturn(clientAddress);
    clientAddressService.saveClientAddress(particpant, "ABC1234567", "ABC1234568", messageBuilder);
    verify(addressService, times(1)).create(any());
    verify(clientAddressDao, times(1)).create(any());
  }

  @Test
  public void testForClientAddressCreateForExistingAddress() {
    DateTime dateTime = new DateTime();
    Address adddress1 = new AddressResourceBuilder().setLegacyId("ABC0987654").setCity("fremont")
        .setLegacyDescriptor(new LegacyDescriptor(null, null, dateTime, null, null))
        .createAddress();
    gov.ca.cwds.rest.api.domain.cms.Address cmsAddress =
        new CmsAddressResourceBuilder().setLastUpdatedTime(dateTime).buildCmsAddress();

    Participant particpant = new ParticipantResourceBuilder()
        .setAddresses(new HashSet<>(Arrays.asList(adddress1))).createParticipant();
    when(addressService.find(any())).thenReturn(cmsAddress);
    ClientAddress clientAddress = new ClientAddressEntityBuilder().buildClientAddress();
    when(clientAddressDao.create(any())).thenReturn(clientAddress);
    clientAddressService.saveClientAddress(particpant, "ABC1234567", "ABC1234568", messageBuilder);
    Set<Address> savedAddresses = particpant.getAddresses();
    Iterator<Address> it = savedAddresses.iterator();
    Address updatedAddress = it.next();
    verify(addressService, times(0)).update(any(), any());
    assertThat(updatedAddress.getLegacyId(), is(equalTo("ABC0987654")));
    assertThat(updatedAddress.getCity(), is(equalTo("fremont")));
    verify(clientAddressDao, times(0)).create(any());
  }

}
