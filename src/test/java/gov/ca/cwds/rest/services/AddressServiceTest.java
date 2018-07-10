package gov.ca.cwds.rest.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.ns.AddressDao;
import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.fixture.CmsAddressResourceBuilder;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.PostedAddress;
import gov.ca.cwds.rest.filters.RequestExecutionContextImplTest;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;
import gov.ca.cwds.rest.util.Doofenshmirtz;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 */
public class AddressServiceTest extends Doofenshmirtz<gov.ca.cwds.data.persistence.ns.Address>
    implements ServiceTestTemplate {

  private static final ObjectMapper MY_MAPPER = Jackson.newObjectMapper();

  AddressDao addressDao;
  AddressesDao xaNsAddressDao;
  gov.ca.cwds.data.cms.AddressDao xaCmsAddressDao;
  AddressService target;

  private LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    addressDao = mock(AddressDao.class);
    xaNsAddressDao = mock(AddressesDao.class);
    xaCmsAddressDao = mock(gov.ca.cwds.data.cms.AddressDao.class);
    target = new AddressService(addressDao, xaNsAddressDao, xaCmsAddressDao);

    legacyDescriptor.setId(DEFAULT_CLIENT_ID);

    Addresses adr1 = new Addresses(DEFAULT_PARTICIPANT_ID, "123 main street", "Elk Grove", "1838",
        "95757", "32", DEFAULT_CLIENT_ID, "ADDRS_T");
    when(xaNsAddressDao.find(any())).thenReturn(adr1);
    when(xaNsAddressDao.create(any())).thenReturn(adr1);
    when(xaNsAddressDao.update(any())).thenReturn(adr1);

    gov.ca.cwds.data.persistence.ns.Address adr2 = new gov.ca.cwds.data.persistence.ns.Address(10L,
        "123 main street", "Elk Grove", "1838", "95757", "32");
    when(addressDao.find(any())).thenReturn(adr2);
    when(addressDao.create(any())).thenReturn(adr2);

    gov.ca.cwds.rest.api.domain.cms.Address addressDomain =
        new CmsAddressResourceBuilder().buildCmsAddress();

    gov.ca.cwds.data.persistence.cms.Address adrCms = new gov.ca.cwds.data.persistence.cms.Address(
        DEFAULT_CLIENT_ID, addressDomain, "ABC", new Date());

    when(xaCmsAddressDao.find(any())).thenReturn(adrCms);
    when(xaCmsAddressDao.update(any())).thenReturn(adrCms);
    when(xaCmsAddressDao.create(any())).thenReturn(adrCms);

    MY_MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    new RequestExecutionContextImplTest().setup();
  }

  protected void printObject(Object obj) throws Exception {
    String e = MY_MAPPER.writeValueAsString(obj);
    System.out.println(e);
  }

  /*
   * find tests
   */
  @Override
  @Test
  public void testFindThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      target.find("nonLong");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    when(addressDao.find(new Long(1))).thenReturn(new gov.ca.cwds.data.persistence.ns.Address(1L,
        "742 Evergreen Terrace", "Springfield", "1877", "98700", "32"));
    Address expected = new Address("", "", "742 Evergreen Terrace", "Springfield", 1877, "98700",
        32, legacyDescriptor);
    Address found = target.find(new Long(1));
    // assertThat(found, is(expected));
    assertThat(found, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    when(addressDao.find(new Long(-1))).thenReturn(null);
    Address found = target.find(new Long(-1));
    assertThat(found, is(nullValue()));
  }

  @Override
  public void testFindThrowsNotImplementedException() throws Exception {}

  /*
   * create tests
   */
  @Override
  @Test
  public void testCreateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      @SuppressWarnings("unused")
      PostedAddress postedAddress = target.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }

  }

  @Override
  @Test
  public void testCreateReturnsPostedClass() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreate = new gov.ca.cwds.data.persistence.ns.Address(
        1L, "742 Evergreen Terrace", "Springfield", "1877", "98700", "32");
    Address request = new Address(toCreate);
    when(addressDao.create(any(gov.ca.cwds.data.persistence.ns.Address.class)))
        .thenReturn(toCreate);
    PostedAddress postedAddress = target.create(request);
    assertThat(postedAddress.getClass(), is(PostedAddress.class));
  }

  @Override
  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreate = new gov.ca.cwds.data.persistence.ns.Address(
        10L, "742 Evergreen Terrace", "Springfield", "1877", "98700", "32");
    Address request = new Address(toCreate);
    when(addressDao.create(any(gov.ca.cwds.data.persistence.ns.Address.class)))
        .thenReturn(toCreate);
    PostedAddress expected = new PostedAddress(10, "", "", "742 Evergreen Terrace", "Springfield",
        1877, "98700", 32, legacyDescriptor);
    PostedAddress returned = target.create(request);
    // assertThat(returned, is(expected));
    assertThat(returned, is(notNullValue()));
  }

  @Override
  @Test
  public void testCreateNullIDError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      @SuppressWarnings("unused")
      PostedAddress postedAddress = target.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }

  }

  @Override
  public void testCreateEmptyIDError() throws Exception {}

  @Override
  public void testCreateThrowsNotImplementedException() throws Exception {}

  @Override
  public void testCreateBlankIDError() throws Exception {}

  @Test
  public void testCreateExistsError() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreate = new gov.ca.cwds.data.persistence.ns.Address(
        (long) 1, "742 Evergreen Terrace", "Springfield", "1877", "98700", "32");
    Address request = new Address(toCreate);
    request.setLegacyId(DEFAULT_CLIENT_ID);
    when(addressDao.create(any(gov.ca.cwds.data.persistence.ns.Address.class)))
        .thenReturn(toCreate);
    PostedAddress expected = new PostedAddress(1, "", "", "742 Evergreen Terrace", "Springfield",
        1877, "98700", 32, legacyDescriptor);
    PostedAddress returned = target.create(request);

    printObject(expected);
    printObject(returned);

    // assertThat(returned, is(expected));
    assertThat(returned, notNullValue());
  }

  /*
   * delete tests
   */
  @Override
  @Test
  public void testDeleteThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      target.delete("nonLong");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }

  }

  @Override
  public void testDeleteReturnsClass() throws Exception {}

  @Override
  @Test
  public void testDeleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    target.delete(new Long(1));
  }

  @Override
  public void testDeleteDelegatesToCrudsService() throws Exception {}

  @Override
  public void testDeleteReturnsNullWhenNotFound() throws Exception {}

  /*
   * update tests
   */
  @Override
  @Test
  public void testUpdateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      target.update(null,
          new Address("", "", "street", "city", 1828, "95555", 32, legacyDescriptor));
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test(expected = SQLException.class)
  public void testUpdateThrowsNotImplementedException() throws Exception {
    when(xaCmsAddressDao.update(any())).thenThrow(SQLException.class);
    target.update(1L, new Address("", "", "street", "city", 1828, "95555", 32, legacyDescriptor));
  }

  @Override
  public void testUpdateReturnsDomain() throws Exception {}

  @Override
  public void testUpdateReturnsCorrectEntity() throws Exception {}

  @Override
  public void testUpdateThrowsServiceException() throws Exception {}

  @Test
  public void type() throws Exception {
    assertThat(AddressService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void find_A$Serializable() throws Exception {
    Serializable primaryKey = 10L;
    Address actual = target.find(primaryKey);
    assertThat(actual, is(notNullValue()));
  }

  @Test(expected = NotImplementedException.class)
  public void delete_A$Serializable() throws Exception {
    Serializable primaryKey = 10L;
    Response actual = target.delete(primaryKey);
    Response expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void create_A$Request() throws Exception {
    Address request = new Address("ADDRS_T", DEFAULT_CLIENT_ID, "street", "city", 1828, "95555", 32,
        legacyDescriptor);
    PostedAddress actual = target.create(request);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void update_A$Serializable$Request() throws Exception {
    Serializable primaryKey = 10L;
    Request request = new Address("ADDRS_T", DEFAULT_CLIENT_ID, "street", "city", 1828, "95555", 32,
        legacyDescriptor);
    Response actual = target.update(primaryKey, request);
    assertThat(actual, is(notNullValue()));
  }

}
