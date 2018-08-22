package gov.ca.cwds.rest.api.domain.investigation.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import gov.ca.cwds.fixture.contacts.ContactRequestBuilder;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.domain.IndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ContactRequestTest {

  @Test
  public void defaultConstructorTest() {
    Contact contact = new Contact();
    assertNotNull(contact);
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Set<Integer> services = new HashSet<>();
    final Map<String, IndividualDeliveredService> people = validPeople();
    ContactRequest domain = new ContactRequestBuilder().setEndedAt("").setPeople(people).build();
    assertThat(domain.getStartedAt(), is(equalTo("2010-04-27T23:30:14.000Z")));
    assertThat(domain.getEndedAt(), is(equalTo("")));
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
    EqualsVerifier.forClass(ContactRequest.class).suppress(Warning.NONFINAL_FIELDS).verify();
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
