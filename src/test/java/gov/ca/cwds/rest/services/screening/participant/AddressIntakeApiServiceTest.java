package gov.ca.cwds.rest.services.screening.participant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.ns.LegacyDescriptorDao;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.fixture.AddressIntakeApiResourceBuilder;
import gov.ca.cwds.fixture.AddressesEntityBuilder;
import gov.ca.cwds.fixture.LegacyDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

/**
 * @author CWDS API Team
 *
 */
public class AddressIntakeApiServiceTest {

  private AddressIntakeApiService addressIntakeApiService;
  private AddressesDao addressesDao;
  private LegacyDescriptorDao legacyDescriptorDao;

  /**
   * @throws Exception - Exception
   */
  @Before
  public void setup() throws Exception {
    addressesDao = mock(AddressesDao.class);
    legacyDescriptorDao = mock(LegacyDescriptorDao.class);
    addressIntakeApiService = new AddressIntakeApiService(addressesDao, legacyDescriptorDao);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = addressIntakeApiService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForCreatePostedSuccessfully() throws Exception {
    AddressIntakeApi intakeApi = new AddressIntakeApiResourceBuilder()
        .setLegacyDescriptor(new LegacyDescriptorEntityBuilder().setId("23456").build()).build();
    Addresses addresses = new AddressesEntityBuilder().build();
    when(addressesDao.create(any(Addresses.class))).thenReturn(addresses);
    LegacyDescriptor legacyDescriptor = new LegacyDescriptorEntityBuilder().build();
    LegacyDescriptorEntity entity = new LegacyDescriptorEntity(legacyDescriptor, "Address", 1L);
    when(legacyDescriptorDao.create(any(LegacyDescriptorEntity.class))).thenReturn(entity);
    AddressIntakeApi postedAdddress = addressIntakeApiService.create(intakeApi);
    assertThat(postedAdddress.getLegacyDescriptor(), is(notNullValue()));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForCreateWithLegacyDescriptorNull() throws Exception {
    AddressIntakeApi intakeApi = new AddressIntakeApiResourceBuilder().build();
    Addresses addresses = new AddressesEntityBuilder().build();
    when(addressesDao.create(any(Addresses.class))).thenReturn(addresses);
    LegacyDescriptor legacyDescriptor = new LegacyDescriptorEntityBuilder().build();
    LegacyDescriptorEntity entity = new LegacyDescriptorEntity(legacyDescriptor, "Address", 1L);
    when(legacyDescriptorDao.create(any(LegacyDescriptorEntity.class))).thenReturn(entity);
    AddressIntakeApi postedAdddress = addressIntakeApiService.create(intakeApi);
    assertThat(postedAdddress.getLegacyDescriptor(), is(nullValue()));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForFind() throws Exception {
    Addresses addresses = new AddressesEntityBuilder().build();
    when(addressesDao.find(any(String.class))).thenReturn(addresses);
    LegacyDescriptor legacyDescriptor = new LegacyDescriptorEntityBuilder().build();
    LegacyDescriptorEntity entity = new LegacyDescriptorEntity(legacyDescriptor, "Address", 1L);
    when(legacyDescriptorDao.findAddressLegacyDescriptor(any(String.class))).thenReturn(entity);
    Response found = addressIntakeApiService.find("ABC1234567");
    assertThat(found, is(notNullValue()));
  }

  /**
   * Delete Test
   */
  @Test(expected = NotImplementedException.class)
  public void notImplementDelete() {
    addressIntakeApiService.delete("something");
  }

  /**
   * Create Test
   */
  @Test(expected = NotImplementedException.class)
  public void notImplementedUpdate() {
    addressIntakeApiService.update("something", new AddressIntakeApi());
  }

}
