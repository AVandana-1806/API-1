package gov.ca.cwds.rest.services.investigation.contact;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import gov.ca.cwds.data.cms.AttorneyDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CollateralIndividualDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.ServiceProviderDao;
import gov.ca.cwds.data.cms.SubstituteCareProviderDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.dao.contact.IndividualDeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.Attorney;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.persistence.cms.ServiceProvider;
import gov.ca.cwds.data.persistence.cms.SubstituteCareProvider;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.CollateralIndividualEntityBuilder;
import gov.ca.cwds.fixture.contacts.DeliveredServiceEntityBuilder;
import gov.ca.cwds.fixture.contacts.IndividualDeliveredServiceEntityBuilder;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.IndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;

public class DeliveredToIndividualServiceTest {

  private static final String deliveredToIndividualId = "A0YcYQV0AB";
  ClientDao clientDao;
  AttorneyDao attorneyDao;
  CollateralIndividualDao collateralIndividualDao;
  ServiceProviderDao serviceProviderDao;
  SubstituteCareProviderDao substituteCareProviderDao;
  ReporterDao reporterDao;
  DeliveredToIndividualService deliveredToIndividualService;
  IndividualDeliveredServiceDao individualDeliveredServiceDao;

  @BeforeClass
  public static void setupSuite() {
    new TestSystemCodeCache();
    new TestingRequestExecutionContext("abc1234567");
  }

  @Before
  public void setup() throws Exception {
    attorneyDao = mock(AttorneyDao.class);
    clientDao = mock(ClientDao.class);
    collateralIndividualDao = mock(CollateralIndividualDao.class);
    reporterDao = mock(ReporterDao.class);
    serviceProviderDao = mock(ServiceProviderDao.class);
    individualDeliveredServiceDao = mock(IndividualDeliveredServiceDao.class);
    substituteCareProviderDao = mock(SubstituteCareProviderDao.class);
    deliveredToIndividualService = new DeliveredToIndividualService(clientDao, attorneyDao,
        collateralIndividualDao, serviceProviderDao, substituteCareProviderDao, reporterDao,
        individualDeliveredServiceDao);
  }

  @Test
  public void type() throws Exception {
    assertThat(DeliveredToIndividualService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(deliveredToIndividualService, notNullValue());
  }

  @Test
  public void findPerson__Collateral() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("O")
            .buildIndividualDeliveredServiceEntity();
    when(collateralIndividualDao.find(deliveredToIndividualId))
        .thenReturn(new CollateralIndividualEntityBuilder().build());
    deliveredToIndividualService.findPerson(individualDeliveredService);
    verify(collateralIndividualDao, atLeastOnce()).find(any());
  }

  @Test
  public void findPerson__Client() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("C")
            .buildIndividualDeliveredServiceEntity();
    when(clientDao.find(deliveredToIndividualId)).thenReturn(new ClientEntityBuilder().build());
    deliveredToIndividualService.findPerson(individualDeliveredService);
    verify(clientDao, atLeastOnce()).find(any());

  }

  @Test
  public void findPerson__Attorney() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("A")
            .buildIndividualDeliveredServiceEntity();
    when(attorneyDao.find(deliveredToIndividualId)).thenReturn(new Attorney());
    deliveredToIndividualService.findPerson(individualDeliveredService);
    verify(attorneyDao, atLeastOnce()).find(any());
  }

  @Test
  public void findPerson__Reporter() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("R")
            .buildIndividualDeliveredServiceEntity();
    when(reporterDao.find(deliveredToIndividualId)).thenReturn(new Reporter());
    deliveredToIndividualService.findPerson(individualDeliveredService);
    verify(reporterDao, atLeastOnce()).find(any());
  }


  @Test
  public void findPerson__ServiceProvider() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("P")
            .buildIndividualDeliveredServiceEntity();
    when(serviceProviderDao.find(deliveredToIndividualId)).thenReturn(new ServiceProvider());
    deliveredToIndividualService.findPerson(individualDeliveredService);
    verify(serviceProviderDao, atLeastOnce()).find(any());
  }


  @Test
  public void findPerson__SubstituteCareProvider() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("S")
            .buildIndividualDeliveredServiceEntity();
    when(substituteCareProviderDao.find(deliveredToIndividualId))
        .thenReturn(new SubstituteCareProvider());
    deliveredToIndividualService.findPerson(individualDeliveredService);
    verify(substituteCareProviderDao, atLeastOnce()).find(any());
  }

  @Test(expected = ServiceException.class)
  public void findPersonWhenNotFoundInClientTable() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("C")
            .buildIndividualDeliveredServiceEntity();
    when(clientDao.find(deliveredToIndividualId)).thenReturn(null);
    deliveredToIndividualService.findPerson(individualDeliveredService);

  }

  @Test
  public void getPeopleInIndividualDeliveredService() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().buildIndividualDeliveredServiceEntity();
    IndividualDeliveredServiceEntity[] entities = {individualDeliveredService};
    DeliveredServiceEntity deliveredServiceEntity =
        new DeliveredServiceEntityBuilder().buildDeliveredServiceEntity();
    when(individualDeliveredServiceDao.findByDeliveredServiceId("ABC1234567")).thenReturn(entities);
    when(clientDao.find(deliveredToIndividualId)).thenReturn(new ClientEntityBuilder().build());
    deliveredToIndividualService.getPeopleInIndividualDeliveredService(deliveredServiceEntity);
    verify(individualDeliveredServiceDao, atLeastOnce()).findByDeliveredServiceId(any());
  }

  @Test
  public void addPeopleToIndividualDeliveredService() throws Exception {
    deliveredToIndividualService.addPeopleToIndividualDeliveredService("123", validContactRequest(),
        "99");
    verify(individualDeliveredServiceDao, atLeastOnce()).create(any());
  }

  @Test
  public void updatePeopleToIndividualDeliveredService() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().buildIndividualDeliveredServiceEntity();
    IndividualDeliveredServiceEntity[] entities = {individualDeliveredService};
    when(individualDeliveredServiceDao.findByDeliveredServiceId("123")).thenReturn(entities);
    deliveredToIndividualService.updatePeopleToIndividualDeliveredService("123",
        validContactRequest(), "99");
    verify(individualDeliveredServiceDao, atLeastOnce()).delete(any());
  }

  @Test
  public void updatePeopleToIndividualDeliveredServiceWhen() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualId("3456789ABC")
            .buildIndividualDeliveredServiceEntity();
    IndividualDeliveredServiceEntity[] entities = {individualDeliveredService};
    when(individualDeliveredServiceDao.findByDeliveredServiceId("123")).thenReturn(entities);
    deliveredToIndividualService.updatePeopleToIndividualDeliveredService("123",
        validContactRequest(), "99");
    verify(individualDeliveredServiceDao, Mockito.times(0)).delete(any());

  }

  @Test
  public void addPersonDetailsClient() throws Exception {
    when(clientDao.find("A0YcYQV0AB")).thenReturn(
        new ClientEntityBuilder().setCommonFirstName("John").setId("A0YcYQV0AB").build());
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("C")
            .buildIndividualDeliveredServiceEntity();
    IndividualDeliveredService person =
        deliveredToIndividualService.findPerson(individualDeliveredService);
    String expected = person.getFirstName();
    assertEquals("John", expected);
  }

  @Test(expected = ApiException.class)
  public void findPersonWhenCodeIsEmpty() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("")
            .buildIndividualDeliveredServiceEntity();
    deliveredToIndividualService.findPerson(individualDeliveredService);
  }

  @Test(expected = ApiException.class)
  public void findPersonWhenCodeIsNull() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode(null)
            .buildIndividualDeliveredServiceEntity();
    deliveredToIndividualService.findPerson(individualDeliveredService);
  }

  @Test(expected = ApiException.class)
  public void lookupByValueWhenValueIsNotValid() throws Exception {
    DeliveredToIndividualService.Code.lookupByValue("unknown");
  }

  @Test(expected = ApiException.class)
  public void lookupByValueWhenValueIsNull() throws Exception {
    DeliveredToIndividualService.Code.lookupByValue(null);
  }

  @Test(expected = ApiException.class)
  public void lookupByCodeLiteralWhenItIsNull() throws Exception {
    DeliveredToIndividualService.Code.lookupByCodeLiteral(null);
  }

  @Test(expected = ApiException.class)
  public void lookupByCodeLiteralWhenItIsNotValid() throws Exception {
    DeliveredToIndividualService.Code.lookupByCodeLiteral("Z");
  }

  @Test
  public void valueOfCode() throws Exception {
    DeliveredToIndividualService.Code test = DeliveredToIndividualService.Code.valueOf("CLIENT");
    assertEquals(DeliveredToIndividualService.Code.CLIENT, test);
  }

  private ContactRequest validContactRequest() {
    final Map<String, IndividualDeliveredService> people = validPeople();
    return new ContactRequest("2010-04-27T23:30:14.000Z", "", "433", "408", "C",
        new HashSet<Integer>(), "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
  }


  private Map<String, IndividualDeliveredService> validPeople() {
    final Map<String, IndividualDeliveredService> ret = new HashMap<>();
    CmsRecordDescriptor person1LegacyDescriptor =
        new CmsRecordDescriptorEntityBuilder().setId("3456789ABC").setUiId("2222-2222-3333-4444555")
            .setTableName("CLIENT_T").setTableDescription("Client").build();
    CmsRecordDescriptor person2LegacyDescriptor =
        new CmsRecordDescriptorEntityBuilder().setId("4567890ABC").setUiId("3333-2222-3333-4444555")
            .setTableName("REPTR_T").setTableDescription("Reporter").build();

    ret.put("1", new IndividualDeliveredService(person1LegacyDescriptor, "John", "Bob", "Smith",
        "Mr.", "Jr.", ""));
    ret.put("2", new IndividualDeliveredService(person2LegacyDescriptor, "Sam", "Bill", "Jones",
        "Mr.", "III", "Reporter"));
    return ret;
  }

}
