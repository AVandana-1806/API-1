package gov.ca.cwds.rest.api.domain.investigation.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.fixture.contacts.DeliveredServiceEntityBuilder;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.IndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ContactTest {

  @Test
  public void defaultConstructorTest() {
    Contact contact = new Contact();
    assertNotNull(contact);
  }

  @Test
  public void createFromDeliveredServiceConstructorTest() throws Exception {
    DeliveredServiceEntity persistedDeliveredService =
        new DeliveredServiceEntityBuilder().setStartDate(new Date(1506543120))
            .setStartTime(new Date(1506543120)).setEndDate(new Date(1506543120))
            .setEndTime(new Date(1506543120)).buildDeliveredServiceEntity();
    final Map<String, IndividualDeliveredService> people = validPeople();
    CmsRecordDescriptor staffLegacyDescriptor = new CmsRecordDescriptorEntityBuilder().setId("0X5")
        .setUiId("0X5").setTableName("STFPERST").setTableDescription("Staff").build();

    LastUpdatedBy lastUpdatedByPerson =
        new LastUpdatedBy(staffLegacyDescriptor, "Joe", "M", "Friday", "Mr.", "Jr.");
    Contact domain = new Contact(persistedDeliveredService, lastUpdatedByPerson,
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
    assertThat(domain.getLegacyDescriptor().getId(),
        is(equalTo(persistedDeliveredService.getId())));
    assertThat(domain.getLastUpdatedBy(), is(equalTo(lastUpdatedByPerson)));
    assertThat(domain.getStartedAt(),
        is(equalTo(Date.from(Instant.parse("1970-01-18T10:29:03.120Z")))));
    assertThat(domain.getEndedAt(),
        is(equalTo(Date.from(Instant.parse("1970-01-18T10:29:03.120Z")))));
    assertThat(domain.getPurpose(),
        is(equalTo(persistedDeliveredService.getServiceContactType().toString())));
    assertThat(domain.getCommunicationMethod(),
        is(persistedDeliveredService.getCommunicationMethodType().toString()));
    assertThat(domain.getStatus(), is(equalTo(persistedDeliveredService.getStatusCode())));
    assertThat(domain.getServices(), is(equalTo(null)));
    assertThat(domain.getLocation(),
        is(equalTo(persistedDeliveredService.getContactLocationType().toString())));
    assertThat(domain.getNote(), is(
        equalTo("some text describing the contact of up to 8000 characters can be stored in CMS")));
    assertThat(domain.getPeople(), is(equalTo(people)));
  }

  @Test
  public void createFromDeliveredServiceConstructorWithNullDates() throws Exception {
    DeliveredServiceEntity persistedDeliveredService = new DeliveredServiceEntityBuilder()
        .setStartDate(null).setEndDate(null).buildDeliveredServiceEntity();
    final Map<String, IndividualDeliveredService> people = validPeople();
    CmsRecordDescriptor staffLegacyDescriptor = new CmsRecordDescriptorEntityBuilder().setId("0X5")
        .setUiId("0X5").setTableName("STFPERST").setTableDescription("Staff").build();

    LastUpdatedBy lastUpdatedByPerson =
        new LastUpdatedBy(staffLegacyDescriptor, "Joe", "M", "Friday", "Mr.", "Jr.");
    Contact domain = new Contact(persistedDeliveredService, lastUpdatedByPerson,
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
    assertThat(domain.getLegacyDescriptor().getId(),
        is(equalTo(persistedDeliveredService.getId())));
    assertThat(domain.getLastUpdatedBy(), is(equalTo(lastUpdatedByPerson)));
    assertNull(domain.getStartedAt());
    assertNull(domain.getEndedAt());
    assertThat(domain.getPurpose(),
        is(equalTo(persistedDeliveredService.getServiceContactType().toString())));
    assertThat(domain.getCommunicationMethod(),
        is(persistedDeliveredService.getCommunicationMethodType().toString()));
    assertThat(domain.getStatus(), is(equalTo(persistedDeliveredService.getStatusCode())));
    assertThat(domain.getServices(), is(equalTo(null)));
    assertThat(domain.getLocation(),
        is(equalTo(persistedDeliveredService.getContactLocationType().toString())));
    assertThat(domain.getNote(), is(
        equalTo("some text describing the contact of up to 8000 characters can be stored in CMS")));
    assertThat(domain.getPeople(), is(equalTo(people)));
  }


  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Set<Integer> services = new HashSet<>();
    final Map<String, IndividualDeliveredService> people = validPeople();
    CmsRecordDescriptor staffLegacyDescriptor = new CmsRecordDescriptorEntityBuilder().setId("0X5")
        .setUiId("0X5").setTableName("STFPERST").setTableDescription("Staff").build();
    CmsRecordDescriptor contactLegacyDescriptor =
        new CmsRecordDescriptorEntityBuilder().setId("1234567ABC").setUiId("1111-2222-3333-4444555")
            .setTableName("DL_SVC_T").setTableDescription("Delivered Service").build();

    LastUpdatedBy lastUpdatedByPerson =
        new LastUpdatedBy(staffLegacyDescriptor, "Joe", "M", "Friday", "Mr.", "Jr.");
    Contact domain = new Contact(contactLegacyDescriptor, lastUpdatedByPerson,
        DomainChef.uncookStrictTimestampString("2010-04-27T23:30:14.000-0000"),
        DomainChef.uncookStrictTimestampString("2012-04-27T03:30:00.000-0000"), "433", "408", "C",
        services, "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
    assertThat(domain.getLegacyDescriptor().getId(), is(equalTo("1234567ABC")));
    assertThat(domain.getLastUpdatedBy(), is(equalTo(lastUpdatedByPerson)));
    assertThat(domain.getStartedAt(),
        is(equalTo(Date.from(Instant.parse("2010-04-27T23:30:14.000Z")))));
    assertThat(domain.getEndedAt(),
        is(equalTo(Date.from(Instant.parse("2012-04-27T03:30:00.000Z")))));
    assertThat(domain.getPurpose(), is(equalTo("433")));
    assertThat(domain.getCommunicationMethod(), is(equalTo("408")));
    assertThat(domain.getStatus(), is(equalTo("C")));
    assertThat(domain.getServices(), is(equalTo(services)));
    assertThat(domain.getLocation(), is(equalTo("415")));
    assertThat(domain.getNote(), is(
        equalTo("some text describing the contact of up to 8000 characters can be stored in CMS")));
    assertThat(domain.getPeople(), is(equalTo(people)));
  }

  @Test
  public void jsonCreatorConstructorWithNullDates() throws Exception {
    Set<Integer> services = new HashSet<>();
    final Map<String, IndividualDeliveredService> people = validPeople();
    CmsRecordDescriptor staffLegacyDescriptor = new CmsRecordDescriptorEntityBuilder().setId("0X5")
        .setUiId("0X5").setTableName("STFPERST").setTableDescription("Staff").build();
    CmsRecordDescriptor contactLegacyDescriptor =
        new CmsRecordDescriptorEntityBuilder().setId("1234567ABC").setUiId("1111-2222-3333-4444555")
            .setTableName("DL_SVC_T").setTableDescription("Delivered Service").build();

    LastUpdatedBy lastUpdatedByPerson =
        new LastUpdatedBy(staffLegacyDescriptor, "Joe", "M", "Friday", "Mr.", "Jr.");
    Contact domain = new Contact(contactLegacyDescriptor, lastUpdatedByPerson, null, null, "433",
        "408", "C", services, "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
    assertThat(domain.getLegacyDescriptor().getId(), is(equalTo("1234567ABC")));
    assertThat(domain.getLastUpdatedBy(), is(equalTo(lastUpdatedByPerson)));
    assertNull(domain.getStartedAt());
    assertNull(domain.getEndedAt());
    assertThat(domain.getPurpose(), is(equalTo("433")));
    assertThat(domain.getCommunicationMethod(), is(equalTo("408")));
    assertThat(domain.getStatus(), is(equalTo("C")));
    assertThat(domain.getServices(), is(equalTo(services)));
    assertThat(domain.getLocation(), is(equalTo("415")));
    assertThat(domain.getNote(), is(
        equalTo("some text describing the contact of up to 8000 characters can be stored in CMS")));
    assertThat(domain.getPeople(), is(equalTo(people)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Contact.class).suppress(Warning.NONFINAL_FIELDS).verify();
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
    ret.put("1", new IndividualDeliveredService(person2LegacyDescriptor, "Sam", "Bill", "Jones",
        "Mr.", "III", "Reporter"));
    return ret;
  }

}
