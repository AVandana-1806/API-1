package gov.ca.cwds.rest.api.domain.investigation;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.investigation.CaseEntityBuilder;
import gov.ca.cwds.fixture.investigation.SimplePersonEntityBuilder;
import gov.ca.cwds.fixture.investigation.SimplePersonWithRelationshipEntityBuilder;

@SuppressWarnings("javadoc")
public class CaseTest {
  private ObjectMapper MAPPER = new ObjectMapper();

  private String endDate = null;
  private String countyName = null;
  private SimpleLegacyDescriptor legacyDescriptor = null;
  private SimplePerson focusChild = null;
  private String serviceComponent = "Emergency Response";
  private SimplePerson assignedSocialWorker = null;
  private String serviceComponentId = "1694";
  private String startDate = "2017-09-01";
  private Set<SimplePersonWithRelationship> parents = new HashSet<>();

  @Before
  public void setup() {
    legacyDescriptor = new SimpleLegacyDescriptor("111-222-333-4444");
    focusChild = new SimplePersonEntityBuilder().build();
    assignedSocialWorker =
        new SimplePersonEntityBuilder().setLastName("Smith").setFirstName("Joe").build();
    SimplePersonWithRelationship parent1 = new SimplePersonWithRelationshipEntityBuilder().build();
    SimplePersonWithRelationship parent2 =
        new SimplePersonWithRelationshipEntityBuilder().setLastName("Smyth").build();
    parents.add(parent1);
    parents.add(parent2);
  }

  @Test
  public void type() throws Exception {
    assertThat(Case.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, serviceComponentId, startDate, parents);
    assertThat(target, notNullValue());
  }

  @Test
  public void getEndDate_Args__() throws Exception {
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, serviceComponentId, startDate, parents);
    assertThat(target.getEndDate(), is(equalTo(endDate)));
  }

  @Test
  public void getCountyName_Args__() throws Exception {
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, serviceComponentId, startDate, parents);
    assertThat(target.getCountyName(), is(equalTo(countyName)));
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, serviceComponentId, startDate, parents);
    assertThat(target.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
  }

  @Test
  public void getFocusChild_Args__() throws Exception {
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, serviceComponentId, startDate, parents);
    assertThat(target.getFocusChild(), is(equalTo(focusChild)));
  }

  @Test
  public void getServiceComponent_Args__() throws Exception {
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, serviceComponentId, startDate, parents);
    assertThat(target.getServiceComponent(), is(equalTo(serviceComponent)));
  }

  @Test
  public void getAssignedSocialWorker_Args__() throws Exception {
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, serviceComponentId, startDate, parents);
    assertThat(target.getAssignedSocialWorker(), is(equalTo(assignedSocialWorker)));
  }

  @Test
  public void getServiceComponentId_Args__() throws Exception {
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, serviceComponentId, startDate, parents);
    assertThat(target.getServiceComponentId(), is(equalTo(serviceComponentId)));
  }

  @Test
  public void getStartDate_Args__() throws Exception {
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, serviceComponentId, startDate, parents);
    assertThat(target.getStartDate(), is(equalTo(startDate)));
  }

  @Test
  public void getParents_Args__() throws Exception {
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, serviceComponentId, startDate, parents);
    Set<SimplePersonWithRelationship> actual = target.getParents();
    assertThat(actual, is(equalTo(parents)));
  }

  @Test
  public void hashCode_Args__() throws Exception {
    Case target = new Case(null, null, null, null, null, null, null, null, null);
    int actual = target.hashCode();
    int expected = -2120005430;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void equals_Args__Object() throws Exception {
    Case target = new Case(null, null, null, null, null, null, null, null, null);
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    Case validCase = new CaseEntityBuilder().build();
    assertThat(
        MAPPER.readValue(fixture("fixtures/domain/investigation/Case/valid.json"), Case.class),
        is(equalTo(validCase)));
  }

  @Test
  public void testGettersAndSettersOfBuilder() {
    SimplePerson assignedSocialWorker = new SimplePersonEntityBuilder()
        .setLastName("lastName")
        .build();
    SimplePerson focusChild = new SimplePersonEntityBuilder()
        .setLastName("Child")
        .build();
    Set<SimplePersonWithRelationship> parents = new LinkedHashSet<>();
    SimplePersonWithRelationship father = new SimplePersonWithRelationshipEntityBuilder()
        .setLastName("father")
        .build();
    parents.add(father);
    SimpleLegacyDescriptor legacyDescriptor = new SimpleLegacyDescriptor("5678901ABC");
    Case validCase = new CaseEntityBuilder()
        .setAssignedSocialWorker(assignedSocialWorker)
        .setCmsRecordDescriptor(legacyDescriptor)
        .setCountyName("Marin")
        .setEndDate("2018-10-01")
        .setFocusChild(focusChild)
        .setParents(parents)
        .setServiceComponent("0123456ABC")
        .setServiceComponentId("9012345ABC")
        .setStartDate("2018-06-01")
        .build();
    assertThat(validCase.getAssignedSocialWorker(), is(equalTo(assignedSocialWorker)));
    assertThat(validCase.getCountyName(), is(equalTo("Marin")));
    assertThat(validCase.getEndDate(), is(equalTo("2018-10-01")));
    assertThat(validCase.getFocusChild(), is(equalTo(focusChild)));
    assertThat(validCase.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
    assertThat(validCase.getParents(), is(equalTo(parents)));
    assertThat(validCase.getServiceComponent(), is(equalTo("0123456ABC")));
    assertThat(validCase.getServiceComponentId(), is(equalTo("9012345ABC")));
    assertThat(validCase.getStartDate(), is(equalTo("2018-06-01")));
  }
}
