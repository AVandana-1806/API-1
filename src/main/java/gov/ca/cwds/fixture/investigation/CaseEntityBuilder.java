package gov.ca.cwds.fixture.investigation;

import java.util.LinkedHashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.Case;
import gov.ca.cwds.rest.api.domain.investigation.SimpleLegacyDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.SimplePerson;
import gov.ca.cwds.rest.api.domain.investigation.SimplePersonWithRelationship;

@SuppressWarnings("javadoc")
public class CaseEntityBuilder {
  protected String endDate = "2017-10-30";
  protected String countyName = "Sacramento";
  private SimpleLegacyDescriptor legacyDescriptor = new SimpleLegacyDescriptor("3456789ABC");

  private SimplePerson focusChild = new SimplePersonEntityBuilder().build();
  protected String serviceComponent;
  private SimplePerson assignedSocialWorker =
      new SimplePersonEntityBuilder().setLastName("social worker").build();
  protected String serviceComponentId = "1234567ABC";
  protected String startDate = "2017-09-01";
  private Set<SimplePersonWithRelationship> parents = new LinkedHashSet<>();

  public Case build() {

    SimplePersonWithRelationship father = new SimplePersonWithRelationshipEntityBuilder().build();
    parents.add(father);
    return new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, serviceComponentId, startDate, parents);
  }

  public CaseEntityBuilder setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

  public CaseEntityBuilder setCountyName(String countyName) {
    this.countyName = countyName;
    return this;
  }

  public CaseEntityBuilder setCmsRecordDescriptor(SimpleLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public CaseEntityBuilder setFocusChild(SimplePerson focusChild) {
    this.focusChild = focusChild;
    return this;
  }

  public CaseEntityBuilder setServiceComponent(String serviceComponent) {
    this.serviceComponent = serviceComponent;
    return this;
  }

  public CaseEntityBuilder setAssignedSocialWorker(SimplePerson assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
    return this;
  }

  public CaseEntityBuilder setServiceComponentId(String serviceComponentId) {
    this.serviceComponentId = serviceComponentId;
    return this;
  }

  public CaseEntityBuilder setStartDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

  public CaseEntityBuilder setParents(Set<SimplePersonWithRelationship> parents) {
    this.parents = parents;
    return this;
  }

}
