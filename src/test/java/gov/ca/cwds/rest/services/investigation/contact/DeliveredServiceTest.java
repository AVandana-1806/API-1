package gov.ca.cwds.rest.services.investigation.contact;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.fixture.contacts.DeliveredServiceResourceBuilder;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.contact.DeliveredServiceDomain;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.IndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactReferralRequest;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;

@SuppressWarnings("javadoc")
public class DeliveredServiceTest {

  private static final String DEFAULT_KEY = "abc1234567";

  DeliveredServiceDao deliveredServiceDao;
  StaffPersonDao staffPersonDao;
  LongTextHelper longTextHelper;
  Date timestamp;

  DeliveredService target;


  @BeforeClass
  public static void setupSuite() {
    new TestSystemCodeCache();
    new TestingRequestExecutionContext("abc1234567");
  }

  @Before
  public void setup() throws Exception {

    new TestingRequestExecutionContext("0X5");
    deliveredServiceDao = mock(DeliveredServiceDao.class);
    staffPersonDao = mock(StaffPersonDao.class);
    longTextHelper = mock(LongTextHelper.class);
    target = new DeliveredService(deliveredServiceDao, staffPersonDao, longTextHelper);
    timestamp = new Date();
  }

  @Test
  public void type() throws Exception {
    assertThat(DeliveredService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getTheLastUpdatedByStaffPersonCallsStaffPersonDaoFind() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();
    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, "ABC", new Date());
    target.getTheLastUpdatedByStaffPerson(toTest);
    verify(staffPersonDao, atLeastOnce()).find(any());
  }

  @Test
  public void getTheLastUpdatedByStaffPersonWhenNotFound() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, "q1p", new Date());
    when(staffPersonDao.find("q1p")).thenReturn(null);
    LastUpdatedBy actual = target.getTheLastUpdatedByStaffPerson(toTest);
    LastUpdatedBy expected = new LastUpdatedBy();
    assertEquals(actual, expected);
  }

  @Test
  public void getTheLastUpdatedByStaffPersonWhenFound() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, "0X5", new Date());
    CmsRecordDescriptor legacyDescriptor = new CmsRecordDescriptorEntityBuilder().setId("0X5")
        .setUiId("0X5").setTableName("STFPERST").setTableDescription("Staff").build();
    LastUpdatedBy lastUpdatedByPerson =
        new LastUpdatedBy(legacyDescriptor, "Jak", "K", "Simmon", "P", "L");
    when(staffPersonDao.find("0X5"))
        .thenReturn(new StaffPersonEntityBuilder().setId("0X5").build());
    LastUpdatedBy actual = target.getTheLastUpdatedByStaffPerson(toTest);
    assertEquals(actual.getLegacyDescriptor().getId(),
        lastUpdatedByPerson.getLegacyDescriptor().getId());
  }

  @Test
  public void getTheLastUpdatedByStaffPersonAddressesNullStaffPersonId() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();
    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, null, new Date());
    LastUpdatedBy actual = target.getTheLastUpdatedByStaffPerson(toTest);
    LastUpdatedBy expected = new LastUpdatedBy();
    assertEquals(actual, expected);
  }

  @Test
  public void combineDetailTextAndContinuationCallsLongTextHelperTwice() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, "ABC", new Date());
    target.combineDetailTextAndContinuation(toTest);
    verify(longTextHelper, times(2)).getLongText(any());
  }

  @Test
  public void combineDetailTextAndContinuationHandlesNulls() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();
    when(longTextHelper.getLongText("ABC1234567")).thenReturn(null);
    when(longTextHelper.getLongText("ABC12345t7")).thenReturn(null);

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, "ABC", new Date());
    String actual = target.combineDetailTextAndContinuation(toTest);
    assertEquals(null, actual);
  }

  @Test
  public void combineDetailTextAndContinuationHandlesDetailTextNull() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();
    when(longTextHelper.getLongText("ABC1234567")).thenReturn(null);
    when(longTextHelper.getLongText("ABC12345t7")).thenReturn("test");
    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, "ABC", new Date());
    String actual = target.combineDetailTextAndContinuation(toTest);
    assertEquals("test", actual);
  }

  @Test
  public void combineDetailTextAndContinuationHandlesDetailTextContinuationNull() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();
    when(longTextHelper.getLongText("ABC1234567")).thenReturn("test");
    when(longTextHelper.getLongText("ABC12345t7")).thenReturn(null);

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, "ABC", new Date());
    String actual = target.combineDetailTextAndContinuation(toTest);
    assertEquals("test", actual);
  }

  @Test
  public void updateCallsDeliveredServiceDaoUpdate() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, "ABC", new Date());
    Set<Integer> services = new HashSet<>();
    final Map<String, IndividualDeliveredService> people = new HashMap<>();
    ContactRequest contactRequest = new ContactRequest("2010-04-27T23:30:14.000Z", "", "433", "408",
        "C", services, "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
    ContactReferralRequest request = new ContactReferralRequest("referralid", contactRequest);
    when(deliveredServiceDao.find(any())).thenReturn(toTest);
    when(deliveredServiceDao.update(any())).thenReturn(toTest);
    when(longTextHelper.updateLongText(any(), any(), any())).thenReturn("ABCD");
    target.update("123", request, "99");
    verify(deliveredServiceDao, atLeastOnce()).update(any());
  }

  @Test(expected = ServiceException.class)
  public void updateWhenExceptionThrownWhenPersistingToDatabase() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();
    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, "ABC", new Date());
    Set<Integer> services = new HashSet<>();
    final Map<String, IndividualDeliveredService> people = new HashMap<>();
    ContactRequest contactRequest = new ContactRequest("2010-04-27T23:30:14.000Z", "", "433", "408",
        "C", services, "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
    ContactReferralRequest request = new ContactReferralRequest("referralid", contactRequest);
    when(deliveredServiceDao.find(any())).thenReturn(toTest);
    when(deliveredServiceDao.update(any())).thenThrow(new ApiException("test"));
    target.update("123", request, "99");
  }

  @Test
  public void createCallsDeliveredServiceDaoCreate() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();
    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(DEFAULT_KEY,
            deliveredServiceDomain, "ABC", new Date());
    Set<Integer> services = new HashSet<>();
    final Map<String, IndividualDeliveredService> people = new HashMap<>();
    ContactRequest contactRequest = new ContactRequest("2010-04-27T23:30:14.000Z", "", "433", "408",
        "C", services, "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
    ContactReferralRequest request = new ContactReferralRequest("referralid", contactRequest);
    when(deliveredServiceDao.find(any())).thenReturn(toTest);
    when(deliveredServiceDao
        .create(any(gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity.class)))
            .thenReturn(toTest);
    when(longTextHelper.updateLongText(any(), any(), any())).thenReturn("ABCD");
    target.create(request, "99");
    verify(deliveredServiceDao, atLeastOnce()).create(any());
  }


  @Test
  public void testCreateMultipleContactsBasedOnLongText() throws Exception {
    StringBuilder sb = new StringBuilder();
    int length = 10000;
    while (sb.length() < length) {
      sb.append(
          "Test Data: Some text describing the contact of up to 8000 characters can be stored in CMS ex: Police brought in Margie  Child upset and had bruises and other contusions on her face and back area.");
    }
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();
    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(DEFAULT_KEY,
            deliveredServiceDomain, "ABC", new Date());
    Set<Integer> services = new HashSet<>();
    final Map<String, IndividualDeliveredService> people = new HashMap<>();
    ContactRequest contactRequest = new ContactRequest("2010-04-27T23:30:14.000Z", "", "433", "408",
        "C", services, "415", sb.toString(), people);
    ContactReferralRequest request = new ContactReferralRequest("referralid", contactRequest);
    when(deliveredServiceDao.find(any())).thenReturn(toTest);
    when(deliveredServiceDao
        .create(any(gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity.class)))
            .thenReturn(toTest);
    when(longTextHelper.updateLongText(any(), any(), any())).thenReturn("ABCD");
    target.create(request, "99");
    verify(deliveredServiceDao, atLeastOnce()).create(any());
  }

  @Test
  public void createDeliveredServiceWithNullNote() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();
    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(DEFAULT_KEY,
            deliveredServiceDomain, "ABC", new Date());
    Set<Integer> services = new HashSet<>();
    final Map<String, IndividualDeliveredService> people = new HashMap<>();
    ContactRequest contactRequest = new ContactRequest("2010-04-27T23:30:14.000Z", "", "433", "408",
        "C", services, "415", null, people);
    ContactReferralRequest request = new ContactReferralRequest("referralid", contactRequest);
    when(deliveredServiceDao.find(any())).thenReturn(toTest);
    when(deliveredServiceDao
        .create(any(gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity.class)))
            .thenReturn(toTest);
    when(longTextHelper.updateLongText(any(), any(), any())).thenReturn("ABCD");
    target.create(request, "99");
    verify(deliveredServiceDao, atLeastOnce()).create(any());
  }

  @Test(expected = ServiceException.class)
  public void createWhenEntityExists() throws Exception {
    Set<Integer> services = new HashSet<>();
    final Map<String, IndividualDeliveredService> people = new HashMap<>();
    ContactRequest contactRequest = new ContactRequest("2010-04-27T20:20:14.000Z", "", "433", "408",
        "C", services, "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
    ContactReferralRequest request = new ContactReferralRequest("referralid", contactRequest);
    when(deliveredServiceDao
        .create(any(gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity.class)))
            .thenThrow(new EntityExistsException());
    target.create(request, "99");
  }

  @Test
  public void findCallsDeliveredServiceDaoFind() throws Exception {
    target.find("1234567");
    verify(deliveredServiceDao, atLeastOnce()).find("1234567");
  }

}
