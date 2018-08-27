package gov.ca.cwds.fixture.contacts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.domain.IndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;

public class ContactRequestBuilder {

  private String startedAt = "2010-04-27T23:30:14.000Z";
  private String endedAt = "2010-04-28T23:30:14.000Z";
  private String purpose = "433";
  private String communicationMetod = "408";
  private String status = "C";
  private Set<Integer> services = new HashSet();
  private String location = "415";
  private String note =
      "some text describing the contact of up to 8000 characters can be stored in CMS";
  private Map<String, IndividualDeliveredService> people = new HashMap<>();
  CmsRecordDescriptor personLegacyDescriptor =
      new CmsRecordDescriptorEntityBuilder().setId("1234567ABC").setUiId("3333-2222-3333-4444555")
          .setTableName("CLIENT_T").setTableDescription("Client").build();

  private IndividualDeliveredService person = new IndividualDeliveredService(
      personLegacyDescriptor, "first", "middle", "last", "phd", "Mr", "teacher");

  public ContactRequest build() {
    people.put("1", person);
    return new ContactRequest(startedAt, endedAt, purpose.toString(), communicationMetod.toString(),
        status, services, location.toString(), note, people);
  }

  public ContactRequestBuilder setLocation(String location) {
    this.location = location;
    return this;
  }

  public ContactRequestBuilder setStartedAt(String startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  public ContactRequestBuilder setEndedAt(String endedAt) {
    this.endedAt = endedAt;
    return this;
  }

  public ContactRequestBuilder setPurpose(String purpose) {
    this.purpose = purpose;
    return this;
  }

  public ContactRequestBuilder setCommunicationMetod(String communicationMetod) {
    this.communicationMetod = communicationMetod;
    return this;
  }

  public ContactRequestBuilder setStatus(String status) {
    this.status = status;
    return this;
  }

  public ContactRequestBuilder setServices(Set<Integer> services) {
    this.services = services;
    return this;
  }

  public ContactRequestBuilder setNote(String note) {
    this.note = note;
    return this;
  }

  public ContactRequestBuilder setPeople(Map<String, IndividualDeliveredService> people) {
    this.people = people;
    return this;
  }

  public ContactRequestBuilder setPersonLegacyDescriptor(
      CmsRecordDescriptor personLegacyDescriptor) {
    this.personLegacyDescriptor = personLegacyDescriptor;
    return this;
  }

  public ContactRequestBuilder setPerson(IndividualDeliveredService person) {
    this.person = person;
    return this;
  }


  public ContactRequestBuilder setLongNote() {
    StringBuilder sb = new StringBuilder();
    int length = 10000;
    while (sb.length() < length) {
      sb.append(
          "Test Data: Some text describing the contact of up to 8000 characters can be stored in CMS ex: Police brought in Margie  Child upset and had bruises and other contusions on her face and back area.");
    }
    this.note = sb.toString();
    return this;
  }

}
