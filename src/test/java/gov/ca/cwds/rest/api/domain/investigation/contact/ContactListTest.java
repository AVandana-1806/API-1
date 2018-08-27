package gov.ca.cwds.rest.api.domain.investigation.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.contacts.ContactListEntityBuilder;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.IndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ContactListTest {
  private ObjectMapper MAPPER = new ObjectMapper();

  @Test
  public void defaultConstructorTest() {
    ContactList contact = new ContactList();
    assertNotNull(contact);
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Set<Integer> services = new HashSet<>();
    final Map<String, IndividualDeliveredService> people = validPeople();
    CmsRecordDescriptor legacyDescriptor = new CmsRecordDescriptorEntityBuilder().setId("0X5")
        .setUiId("0X5").setTableName("STFPERST").setTableDescription("Staff").build();
    CmsRecordDescriptor contactLegacyDescriptor =
        new CmsRecordDescriptorEntityBuilder().setId("ABC1234567").setUiId("1111-2222-3333-4444555")
            .setTableName("DL_SVC_T").setTableDescription("Delivered Service").build();

    LastUpdatedBy lastUpdatedByPerson =
        new LastUpdatedBy(legacyDescriptor, "Joe", "M", "Friday", "Mr.", "Jr.");
    Contact contact = new Contact(contactLegacyDescriptor, lastUpdatedByPerson,
        DomainChef.uncookStrictTimestampString("2010-04-27T23:30:14.000-0000"),
        DomainChef.uncookStrictTimestampString("2010-04-28T05:30:14.000-0000"), "433", "408", "C",
        services, "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
    Set<Contact> contacts = new HashSet<>();
    contacts.add(contact);
    ContactList domain = new ContactList(contacts);
    assertThat(domain.getContacts(), is(equalTo(contacts)));

  }


  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ContactList.class).suppress(Warning.NONFINAL_FIELDS).verify();
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

  @Test
  // @Ignore
  public void testSerilizedOutput()
      throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
    ContactList contactList = new ContactListEntityBuilder().build();
    final String expected = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(contactList);
    System.out.println(expected);
  }

}
