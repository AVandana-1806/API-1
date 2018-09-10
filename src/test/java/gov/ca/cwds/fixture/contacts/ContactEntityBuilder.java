package gov.ca.cwds.fixture.contacts;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.IndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;

@SuppressWarnings("javadoc")
public class ContactEntityBuilder {

  private String id = "1234567ABC";
  private LastUpdatedBy lastUpdatedBy = new LastUpdatedByEntityBuilder().build();
  private Date startedAt = DomainChef.uncookStrictTimestampString("2010-04-27T23:30:14.000-0000");
  private Date endedAt = DomainChef.uncookStrictTimestampString("2010-04-28T23:30:14.000-0000");
  private Integer purpose = 433;
  private Integer communicationMetod = 408;
  private String status = "C";
  private Set<Integer> services = new HashSet<>();
  private Integer location = 415;
  private String note = "contact description";
  private Map<String, IndividualDeliveredService> people = new HashMap<>();
  private CmsRecordDescriptor legacyDescriptor = new CmsRecordDescriptorEntityBuilder().build();

  private IndividualDeliveredService person = new IndividualDeliveredService(
      legacyDescriptor, "first", "middle", "last", "phd", "Mr", "teacher");

  public Contact build() {
    people.put("1", person);
    return new Contact(legacyDescriptor, lastUpdatedBy, startedAt, endedAt, purpose.toString(),
        communicationMetod.toString(), status, services, location.toString(), note, people);
  }

  public ContactEntityBuilder setLocation(Integer location) {
    this.location = location;
    return this;
  }
}
