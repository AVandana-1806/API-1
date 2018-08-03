package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.dao.investigation.InvestigationDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.contacts.ContactEntityBuilder;
import gov.ca.cwds.fixture.investigation.InvestigationEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactList;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.investigation.contact.ContactService;

@SuppressWarnings("javadoc")
public class InvestigationServiceTest {
  private InvestigationDao investigationDao;
  private StaffPersonDao staffPersonDao;
  private AddressDao addressDao;
  private LongTextService longTextService;
  private PeopleService peopleService;
  private AllegationService allegationService;
  private ContactService contactService;
  private ScreeningSummaryService screeningSummaryService;

  private InvestigationService investigationService;

  private static final String DEFAULT_KEY = "Anb27uN00I";
  private static final String STUB_DATA_KEY = "999999";

  private Investigation investigationStub;


  @BeforeClass
  public static void setupSuite() {
    new TestingRequestExecutionContext("ABC");
  }

  @Before
  public void setup() throws Exception {
    this.investigationDao = mock(InvestigationDao.class);
    this.staffPersonDao = mock(StaffPersonDao.class);
    this.addressDao = mock(AddressDao.class);
    // this.contactDao = mock(ContactDao.class);
    this.longTextService = mock(LongTextService.class);
    this.peopleService = mock(PeopleService.class);
    this.allegationService = mock(AllegationService.class);
    this.contactService = mock(ContactService.class);
    this.screeningSummaryService = mock(ScreeningSummaryService.class);

    investigationService = new InvestigationService();
    investigationService.setInvestigationDao(investigationDao);
    investigationService.setStaffPersonDao(staffPersonDao);
    investigationService.setAddressDao(addressDao);
    investigationService.setLongTextService(longTextService);
    investigationService.setPeopleService(peopleService);
    investigationService.setAllegationService(allegationService);
    investigationService.setContactService(contactService);
    investigationService.setScreeningSummaryService(screeningSummaryService);

    investigationStub = new InvestigationEntityBuilder().build();

  }

  @Test
  public void type() throws Exception {
    assertThat(InvestigationDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(investigationService, notNullValue());
  }

  @Test
  public void findReturnsInvestigationStub() {
    Response response = investigationService.find(STUB_DATA_KEY);
    assertThat(response, is(equalTo(investigationStub)));

  }

  @Test
  public void investigationFindIsCalled() throws Exception {

    Referral referral = new ReferralEntityBuilder().setId(DEFAULT_KEY).build();
    Contact contact = new ContactEntityBuilder().build();
    Set<Contact> contacts = new HashSet<>();
    contacts.add(contact);
    ContactList contactList = new ContactList(contacts);

    when(investigationDao.find(DEFAULT_KEY)).thenReturn(referral);
    when(contactService.findAllContactsForTheReferral(DEFAULT_KEY)).thenReturn(contactList);

    investigationService.find(DEFAULT_KEY);
    verify(investigationDao, atLeastOnce()).find(DEFAULT_KEY);

  }

  @Test
  public void deleteReturnsInvestigationStub() {
    Response response = investigationService.delete(DEFAULT_KEY);
    assertThat(response, is(equalTo(investigationStub)));
  }

  @Test
  public void createReturnsInvestigationStub() {
    Response response = investigationService.create(investigationStub);
    assertThat(response, is(equalTo(investigationStub)));

  }

  @Test
  public void updateReturnsInvestigationStub() throws Exception {
    Response response = investigationService.update(DEFAULT_KEY, investigationStub);
    assertThat(response, is(equalTo(investigationStub)));

  }

  @Test
  public void testFindThrowsExceptionWhenReferralIsNull() {
    when(investigationDao.find(DEFAULT_KEY))
        .thenReturn(new ReferralEntityBuilder().setId(DEFAULT_KEY).build());
    Response response = investigationService.find("1234567ABC");
    assertNull(response);
  }
}
