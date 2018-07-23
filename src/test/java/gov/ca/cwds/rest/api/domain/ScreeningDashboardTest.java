package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.fixture.ScreeningDashboardResourceBuilder;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ScreeningDashboardTest {
  private String id = "testscreeningdashboard";
  private String reference = "screening dashboard reference";
  private String name = "screening name";
  private String screeningDecision = "5 days";
  private String screeningDecisionDetail = "screening decision detail";
  private String assignee = "Test User";
  private String assigneeStaffId = "abb";
  private Date startedAt;
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Before
  public void setup() {
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }
  
  @Test
  public void testConstructor() throws Exception {
	ScreeningDashboard domain = new ScreeningDashboard(id, reference, name, screeningDecision,
		screeningDecisionDetail, assignee, assigneeStaffId);
	domain.setStartedAt(startedAt);
	assertThat(domain.getId(), is(equalTo(id)));
	assertThat(domain.getReference(), is(equalTo(reference)));
	assertThat(domain.getName(), is(equalTo(name)));
	assertThat(domain.getScreeningDecision(), is(equalTo(screeningDecision)));
	assertThat(domain.getScreeningDecisionDetail(), is(equalTo(screeningDecisionDetail)));
	assertThat(domain.getAssignee(), is(equalTo(assignee)));
	assertThat(domain.getAssigneeStaffId(), is(equalTo(assigneeStaffId)));
	assertThat(domain.getStartedAt(), is(equalTo(startedAt)));
  }
  
  @Test
  public void testDefaultConstructor() throws Exception {
	ScreeningDashboard screeningDashboard = new ScreeningDashboard();
    assertNotNull(screeningDashboard);
  }
  
  @Test
  public void testSerializeToJSON() throws Exception {
	ScreeningDashboard screeningDashboard = new ScreeningDashboardResourceBuilder().build();
	String sdJSON  = MAPPER.writeValueAsString(screeningDashboard);
	String expected = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/domain/screening/valid/screeningDashboard.json"), ScreeningDashboard.class));
	assertThat(sdJSON, is(expected));
	
  }
  
  @Test
  public void testDeserializationFromJSON() throws Exception {
	ScreeningDashboard screeningDashboard = new ScreeningDashboardResourceBuilder().build();
	ScreeningDashboard sdObject = MAPPER.readValue(fixture("fixtures/domain/screening/valid/screeningDashboard.json"), ScreeningDashboard.class);
	assertThat(screeningDashboard, is(sdObject));
  }

  @Test
  public void testEquaslHashCodeWorks() throws Exception {
	EqualsVerifier.forClass(ScreeningDashboard.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
  }
}
