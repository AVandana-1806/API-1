package gov.ca.cwds.rest.services.investigation.contact;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.dao.contact.ContactPartyDeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.fixture.contacts.ContactPartyDeliverdServiceEntityBuilder;
import gov.ca.cwds.fixture.contacts.ContactRequestBuilder;
import gov.ca.cwds.fixture.contacts.DeliveredServiceEntityBuilder;
import gov.ca.cwds.fixture.contacts.DeliveredServiceResourceBuilder;
import gov.ca.cwds.fixture.contacts.ReferralClientDeliveredServiceEntityBuilder;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.IndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactReferralRequest;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;

public class ContactServiceTest {

  private static final String DEFAULT_KEY = "ABC1234567";

  DeliveredService deliveredService;
  ReferralClientDeliveredService referralClientDeliveredService;
  ContactPartyDeliveredServiceDao contactPartyDeliveredServiceDao;
  ReferralDao referralDao;
  DeliveredToIndividualService deliveredToIndividualService;
  Referral referral;
  ContactService target;

  @BeforeClass
  public static void setupSuite() {
    new TestSystemCodeCache();
    new TestingRequestExecutionContext("0X5");
  }

  @Before
  public void setup() throws Exception {
    deliveredService = mock(DeliveredService.class);
    referralClientDeliveredService = mock(ReferralClientDeliveredService.class);
    contactPartyDeliveredServiceDao = mock(ContactPartyDeliveredServiceDao.class);
    referralDao = mock(ReferralDao.class);
    deliveredToIndividualService = mock(DeliveredToIndividualService.class);

    target = new ContactService(deliveredService, referralClientDeliveredService,
        deliveredToIndividualService, contactPartyDeliveredServiceDao, referralDao);
    CmsRecordDescriptor staffLegacyDescriptor = new CmsRecordDescriptorEntityBuilder().setId("0X5")
        .setUiId("0X5").setTableName("STFPERST").setTableDescription("Staff").build();

    when(deliveredService.find(any()))
        .thenReturn(new DeliveredServiceEntityBuilder().buildDeliveredServiceEntity());
    when(deliveredService.getTheLastUpdatedByStaffPerson(any()))
        .thenReturn(new LastUpdatedBy(staffLegacyDescriptor, "Joe", "M", "Friday", "Mr.", "Jr."));
    when(deliveredService.combineDetailTextAndContinuation(any())).thenReturn("this is a test");
    when(deliveredToIndividualService.getPeopleInIndividualDeliveredService(any()))
        .thenReturn(new HashMap<>());

    ReferralClientDeliveredServiceEntity[] entity =
        {new ReferralClientDeliveredServiceEntityBuilder().build()};
    when(referralClientDeliveredService.findByReferralId(any())).thenReturn(entity);

    referral = mock(Referral.class);
    when(referral.getReceivedDate())
        .thenReturn(Date.from(Instant.parse("2006-04-27T23:30:14.000Z")));
    when(referral.getReceivedTime())
        .thenReturn(Date.from(Instant.parse("2006-04-27T23:30:14.000Z")));
    when(referralDao.find(DEFAULT_KEY)).thenReturn(referral);
  }

  @Test
  public void type() throws Exception {
    assertThat(ContactService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findSingleContact() throws Exception {
    final String primaryKey = "ABC1234567:" + "ABC1234567";
    Response actual = target.find(primaryKey);
    assertThat(actual, notNullValue());
  }

  @Test
  public void findAllContactsForAReferral() throws Exception {
    Response actual = target.find(DEFAULT_KEY);
    assertThat(actual, notNullValue());
  }

  @Test
  public void findSingleContactWhenNoCorrespondingDeliveredService() throws Exception {
    final String primaryKey = "ABC1234567:" + "ABC1234567";
    when(deliveredService.find(any())).thenReturn(null);
    Response actual = target.find(primaryKey);
    assertThat(actual, nullValue());
  }

  @Test
  public void findSingleContactWhenEmptyReferralClientDeliveredService() {
    final String primaryKey = "ABC1234567:" + "ABC1234567";
    when(referralClientDeliveredService.findByReferralId(any()))
        .thenReturn(new ReferralClientDeliveredServiceEntity[0]);
    Response response = target.find(primaryKey);
    assertNull(response);
  }

  @Test
  public void findSingleContactWhenNoCorrespondingReferralClientDeliveredService() {
    final String primaryKey = "ABC1234567:" + "ABC1234999";
    Response response = target.find(primaryKey);
    assertNull(response);
  }

  @Test(expected = ServiceException.class)
  public void createContactStartedAtDateBeforeReferralDate() throws Exception {
    ContactRequest contactRequest =
        new ContactRequestBuilder().setStartedAt("2000-04-27T23:30:14.000Z").build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(DEFAULT_KEY, contactRequest);
    target.create(contactReferralRequest);
  }

  @Test(expected = ServiceException.class)
  public void createContactStartedAtDateEqualsReferralDate() throws Exception {
    ContactRequest contactRequest =
        new ContactRequestBuilder().setStartedAt("2003-04-27T23:30:14.000Z").build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(DEFAULT_KEY, contactRequest);
    target.create(contactReferralRequest);
  }

  @Test(expected = ServiceException.class)
  public void updateContactWhenNoEntriesinReferralClientDeliveredService() throws Exception {
    final String primaryKey = "ABC1234568";
    final String contactId = "APc109852u";
    ContactRequest contactRequest = new ContactRequestBuilder().build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(primaryKey, contactRequest);
    when(referralClientDeliveredService.findByReferralId(any())).thenReturn(null);
    target.update(contactId, contactReferralRequest);
  }

  @Test(expected = ServiceException.class)
  public void updateContactStartedAtDateAfterReferralDate() throws Exception {
    final String contactId = "APc109852u";
    ContactRequest contactRequest =
        new ContactRequestBuilder().setStartedAt("2000-04-27T23:30:14.000Z").build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(DEFAULT_KEY, contactRequest);
    target.update(contactId, contactReferralRequest);
  }

  @Test(expected = ServiceException.class)
  public void updateContactStartedAtDateEqualsReferralDate() throws Exception {
    final String contactId = "APc109852u";
    ContactRequest contactRequest =
        new ContactRequestBuilder().setStartedAt("2003-04-27T23:30:14.000Z").build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(DEFAULT_KEY, contactRequest);
    target.update(contactId, contactReferralRequest);
  }

  @Test(expected = ServiceException.class)
  public void validateReferralWhenReferralDoesNotExist() throws Exception {
    ContactRequest contactRequest = new ContactRequestBuilder().build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(DEFAULT_KEY, contactRequest);
    when(referralDao.find(DEFAULT_KEY)).thenReturn(null);
    target.create(contactReferralRequest);
  }

  @Test
  public void createLongTextContact() throws Exception {

    ContactRequest contactRequest = new ContactRequestBuilder().setLongNote().build();

    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(DEFAULT_KEY, contactRequest);
    when(deliveredService.create(any(), any())).thenReturn("ABC1234567");
    doNothing().when(referralClientDeliveredService).addOnBehalfOfClients(any(), any(), any());
    doNothing().when(deliveredToIndividualService).addPeopleToIndividualDeliveredService(any(),
        any(), any());
    when(contactPartyDeliveredServiceDao.create(any())).thenReturn(
        new ContactPartyDeliverdServiceEntityBuilder().buildContactPartyDeliveredService());
    Response actual = target.create(contactReferralRequest);
    assertThat(actual, notNullValue());

  }

  @Test
  public void createContact() throws Exception {
    ContactRequest contactRequest =
        new ContactRequestBuilder().setStartedAt("2007-04-27T23:30:14.000Z").build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(DEFAULT_KEY, contactRequest);
    when(deliveredService.create(any(), any())).thenReturn("ABC1234567");
    doNothing().when(referralClientDeliveredService).addOnBehalfOfClients(any(), any(), any());
    doNothing().when(deliveredToIndividualService).addPeopleToIndividualDeliveredService(any(),
        any(), any());
    when(contactPartyDeliveredServiceDao.create(any())).thenReturn(
        new ContactPartyDeliverdServiceEntityBuilder().buildContactPartyDeliveredService());
    Response actual = target.create(contactReferralRequest);
    assertThat(actual, notNullValue());
  }



  @Test
  public void updateContact() throws Exception {
    final String contactId = "ABC1234567";
    ContactRequest contactRequest =
        new ContactRequestBuilder().setStartedAt("2007-04-27T23:30:14.000Z").build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(DEFAULT_KEY, contactRequest);
    when(referral.getId()).thenReturn("ABC1234567");
    when(deliveredService.update(any(), any(), any()))
        .thenReturn(new DeliveredServiceResourceBuilder().buildDeliveredServiceResource());
    doNothing().when(referralClientDeliveredService).updateOnBehalfOfClients(any(), any(), any());
    doNothing().when(deliveredToIndividualService).updatePeopleToIndividualDeliveredService(any(),
        any(), any());
    when(contactPartyDeliveredServiceDao.findByDeliveredServiceId(any())).thenReturn(
        new ContactPartyDeliverdServiceEntityBuilder().buildContactPartyDeliveredService());
    when(contactPartyDeliveredServiceDao.update(any())).thenReturn(
        new ContactPartyDeliverdServiceEntityBuilder().buildContactPartyDeliveredService());
    Response actual = target.update(contactId, contactReferralRequest);
    assertThat(actual, notNullValue());
  }

  @Test(expected = ServiceException.class)
  public void createWithNoStaffId() throws Exception {
    new TestingRequestExecutionContext("");
    ContactRequest contactRequest =
        new ContactRequestBuilder().setStartedAt("2000-04-27T23:30:14.000Z").build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(DEFAULT_KEY, contactRequest);
    target.create(contactReferralRequest);
  }

  @Test(expected = NotImplementedException.class)
  public void delete_Args__String() throws Exception {
    String primaryKey = null;
    target.delete(primaryKey);
  }

}
