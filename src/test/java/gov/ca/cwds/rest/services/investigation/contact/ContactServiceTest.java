package gov.ca.cwds.rest.services.investigation.contact;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactReferralRequest;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;

public class ContactServiceTest {

  private static final String DEFAULT_KEY = "abc1234567";

  DeliveredService deliveredService;
  ReferralClientDeliveredService referralClientDeliveredService;
  ContactPartyDeliveredServiceDao contactPartyDeliveredServiceDao;
  ReferralDao referralDao;
  DeliveredToIndividualService deliveredToIndividualService;
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
    when(deliveredService.find(any()))
        .thenReturn(new DeliveredServiceEntityBuilder().buildDeliveredServiceEntity());
    when(deliveredService.getTheLastUpdatedByStaffPerson(any()))
        .thenReturn(new LastUpdatedBy("0X5", "Joe", "M", "Friday", "Mr.", "Jr."));
    when(deliveredService.combineDetailTextAndContinuation(any())).thenReturn("this is a test");
    when(deliveredToIndividualService.getPeopleInIndividualDeliveredService(any()))
        .thenReturn(new HashSet<PostedIndividualDeliveredService>());

    ReferralClientDeliveredServiceEntity[] entity =
        {new ReferralClientDeliveredServiceEntityBuilder().build()};
    when(referralClientDeliveredService.findByReferralId(any())).thenReturn(entity);

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
    final String primaryKey = "ABC1234567";
    Response actual = target.find(primaryKey);
    assertThat(actual, notNullValue());
  }

  @Test
  public void findSingleContactWhenNoCorrespondingDeliveredService() throws Exception {
    final String primaryKey = "ABC1234567:" + "ABC1234567";
    when(deliveredService.find(any())).thenReturn(null);
    Response actual = target.find(primaryKey);
    assertThat(actual, nullValue());
  }

  @Test(expected = ServiceException.class)
  public void findSingleContactWhenEmptyReferralClientDeliveredService() throws Exception {
    final String primaryKey = "ABC1234567:" + "ABC1234567";
    when(referralClientDeliveredService.findByReferralId(any()))
        .thenReturn(new ReferralClientDeliveredServiceEntity[0]);
    target.find(primaryKey);

  }

  @Test(expected = ServiceException.class)
  public void findSingleContactWhenNoCorrespondingReferralClientDeliveredService()
      throws Exception {
    final String primaryKey = "ABC1234567:" + "ABC1234999";
    target.find(primaryKey);
  }



  @Test(expected = ServiceException.class)
  public void createContactStartedAtDateAfterReferralDate() throws Exception {
    final String primaryKey = "ABC1234567";
    ContactRequest contactRequest =
        new ContactRequestBuilder().setStartedAt("2000-04-27T23:30:14.000Z").build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(primaryKey, contactRequest);
    Referral referral = mock(Referral.class);
    when(referral.getReceivedDate())
        .thenReturn(DomainChef.uncookISO8601Timestamp("2006-04-27T23:30:14.000Z"));
    when(referralDao.find(primaryKey)).thenReturn(referral);
    target.create(contactReferralRequest);
  }

  @Test(expected = ServiceException.class)
  public void updateContactWhenNoEntriesinReferralClientDeliveredService() throws Exception {
    final String primaryKey = "ABC1234567";
    final String contactId = "APc109852u";
    ContactRequest contactRequest = new ContactRequestBuilder().build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(primaryKey, contactRequest);
    when(referralClientDeliveredService.findByReferralId(any())).thenReturn(null);
    target.update(contactId, contactReferralRequest);
  }

  @Test(expected = ServiceException.class)
  public void updateContactStartedAtDateAfterReferralDate() throws Exception {
    final String primaryKey = "ABC1234567";
    final String contactId = "APc109852u";
    ContactRequest contactRequest =
        new ContactRequestBuilder().setStartedAt("2000-04-27T23:30:14.000Z").build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(primaryKey, contactRequest);
    Referral referral = mock(Referral.class);
    when(referral.getReceivedDate())
        .thenReturn(DomainChef.uncookISO8601Timestamp("2006-04-27T23:30:14.000Z"));
    when(referralDao.find(primaryKey)).thenReturn(referral);
    target.update(contactId, contactReferralRequest);
  }

  @Test(expected = ServiceException.class)
  public void validateReferralWhenReferralDoesNotExist() throws Exception {
    final String primaryKey = "ABC1234567";
    ContactRequest contactRequest = new ContactRequestBuilder().build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(primaryKey, contactRequest);
    when(referralDao.find(primaryKey)).thenReturn(null);
    target.create(contactReferralRequest);

  }

  @Test
  public void createContact() throws Exception {
    final String primaryKey = "ABC1234567";
    ContactRequest contactRequest =
        new ContactRequestBuilder().setStartedAt("2007-04-27T23:30:14.000Z").build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(primaryKey, contactRequest);
    Referral referral = mock(Referral.class);
    when(referral.getReceivedDate())
        .thenReturn(DomainChef.uncookISO8601Timestamp("2006-04-27T23:30:14.000Z"));
    when(referralDao.find(primaryKey)).thenReturn(referral);
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
    final String primaryKey = "ABC1234567";
    final String contactId = "ABC1234567";
    ContactRequest contactRequest =
        new ContactRequestBuilder().setStartedAt("2007-04-27T23:30:14.000Z").build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(primaryKey, contactRequest);
    Referral referral = mock(Referral.class);
    when(referral.getReceivedDate())
        .thenReturn(DomainChef.uncookISO8601Timestamp("2006-04-27T23:30:14.000Z"));
    when(referral.getId()).thenReturn("ABC1234567");
    when(referralDao.find(primaryKey)).thenReturn(referral);
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
    final String primaryKey = "ABC1234567";
    new TestingRequestExecutionContext("");
    ContactRequest contactRequest =
        new ContactRequestBuilder().setStartedAt("2000-04-27T23:30:14.000Z").build();
    ContactReferralRequest contactReferralRequest =
        new ContactReferralRequest(primaryKey, contactRequest);
    target.create(contactReferralRequest);
  }

  @Test(expected = NotImplementedException.class)
  public void delete_Args__String() throws Exception {
    String primaryKey = null;
    target.delete(primaryKey);
  }

}
