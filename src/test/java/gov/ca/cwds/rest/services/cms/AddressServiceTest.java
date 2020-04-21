package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.SsaName3Dao;
import gov.ca.cwds.fixture.CmsAddressResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.business.rules.UpperCaseTables;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AddressServiceTest {

  private AddressService addressService;
  private AddressDao addressDao;
  private SsaName3Dao ssaname3Dao;
  private UpperCaseTables upperCaseTables;
  private Validator validator;


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    addressService = mock(AddressService.class);
    addressDao = mock(AddressDao.class);
    ssaname3Dao = mock(SsaName3Dao.class);
    upperCaseTables = mock(UpperCaseTables.class);
    addressService = new AddressService(addressDao, ssaname3Dao, upperCaseTables, validator);
  }

  // find
  @Test
  public void addressServiceFindReturnsCorrectEntity() throws Exception {
    String id = "AaNli340MV";
    Date updated = new Date();
    DateTime lastUpdatedTime = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        .parseDateTime("2004-03-31T09:45:58.000-0800");

    gov.ca.cwds.rest.api.domain.cms.Address expected = new CmsAddressResourceBuilder()
        .setExistingAddressId("AaNli340MV").setLastUpdatedTime(lastUpdatedTime).buildCmsAddress();

    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address(id, expected, "04Z", updated);

    // gov.ca.cwds.rest.api.domain.cms.Address request = new Address(persistedAddress, isExist);
    when(addressDao.find(id)).thenReturn(address);
    Address found = addressService.find(id);
    assertThat(found.getExistingAddressId(), is(expected.getExistingAddressId()));
  }

  @Test
  public void addressServiceFindReturnsNullWhenNotFound() throws Exception {
    Response found = addressService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @Test
  public void addressServiceDeleteDelegatesToCrudsService() {
    addressService.delete("ABC2345678");
    verify(addressDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void addressServiceDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = addressService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void cmsAddressServiceDeleteReturnsNotNull() throws Exception {
    String id = "ABC1234567";
    gov.ca.cwds.rest.api.domain.cms.Address expected =
        new CmsAddressResourceBuilder().buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address(id, expected, "0XA", new Date());

    when(addressDao.delete(id)).thenReturn(address);
    Address found = addressService.delete(id);
    assertThat(found.getExistingAddressId(), is(expected.getExistingAddressId()));
  }

  // update test
  @Test
  public void addressServiceUpdateReturnsCorrectEntity() throws Exception {
    String id = "AaNli340MV";
    gov.ca.cwds.rest.api.domain.cms.Address addressDomain =
        new CmsAddressResourceBuilder().setCity("Fremont").buildCmsAddress();

    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address(id, addressDomain, "ABC", new Date());

    when(addressDao.find("ABC1234567")).thenReturn(address);
    when(addressDao.update(any())).thenReturn(address);

    Object retval = addressService.update("ABC1234567", addressDomain);
    assertEquals(retval.getClass(), gov.ca.cwds.rest.api.domain.cms.Address.class);
  }

  @Test
  public void addressServiceUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      gov.ca.cwds.rest.api.domain.cms.Address addressDomain =
          new CmsAddressResourceBuilder().buildCmsAddress();

      when(addressDao.update(any())).thenThrow(EntityNotFoundException.class);

      addressService.update("ZZZZZZZZZZ", addressDomain);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test
  @Test
  public void addressServiceCreateReturnsPostedClass() throws Exception {
    String id = "AaNli340MV";
    gov.ca.cwds.rest.api.domain.cms.Address addressDomain =
        new CmsAddressResourceBuilder().buildCmsAddress();

    gov.ca.cwds.data.persistence.cms.Address toCreate =
        new gov.ca.cwds.data.persistence.cms.Address(id, addressDomain, "ABC", new Date());

    gov.ca.cwds.rest.api.domain.cms.Address request = new Address(toCreate, false);
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(toCreate);

    Response response = addressService.create(request);
    assertEquals(response.getClass(), PostedAddress.class);
  }

  @Test
  public void addressServiceCreateReturnsNonNull() throws Exception {
    String id = "AaNli340MV";
    gov.ca.cwds.rest.api.domain.cms.Address addressDomain =
        new CmsAddressResourceBuilder().buildCmsAddress();

    gov.ca.cwds.data.persistence.cms.Address toCreate =
        new gov.ca.cwds.data.persistence.cms.Address(id, addressDomain, "ABC", new Date());

    Address request = new Address(toCreate, false);
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(toCreate);

    Response postedtickle = addressService.create(request);
    assertThat(postedtickle, is(notNullValue()));
  }

  @Test
  public void addressServiceCreateThrowsEntityExistsException() throws Exception {
    try {
      gov.ca.cwds.rest.api.domain.cms.Address addressDomain =
          new CmsAddressResourceBuilder().buildCmsAddress();

      when(addressDao.create(any())).thenThrow(EntityExistsException.class);

      addressService.create(addressDomain);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Test
  public void addressServiceCreateNullIDError() throws Exception {
    try {
      gov.ca.cwds.rest.api.domain.cms.Address addressDomain =
          new CmsAddressResourceBuilder().buildCmsAddress();

      gov.ca.cwds.data.persistence.cms.Address toCreate =
          new gov.ca.cwds.data.persistence.cms.Address(null, addressDomain, "ABC", new Date());

      when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
          .thenReturn(toCreate);

      Address expected = new PostedAddress(toCreate, false);
    } catch (ServiceException e) {
      assertEquals("Address ID cannot be empty", e.getMessage());
    }

  }

  @Test
  public void addressServiceCreateBlankIDError() throws Exception {
    try {
      gov.ca.cwds.rest.api.domain.cms.Address addressDomain =
          new CmsAddressResourceBuilder().buildCmsAddress();

      gov.ca.cwds.data.persistence.cms.Address toCreate =
          new gov.ca.cwds.data.persistence.cms.Address("  ", addressDomain, "ABC", new Date());

      when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
          .thenReturn(toCreate);

      Address expected = new PostedAddress(toCreate, false);
    } catch (ServiceException e) {
      assertEquals("Address ID cannot be empty", e.getMessage());
    }
  }

}
