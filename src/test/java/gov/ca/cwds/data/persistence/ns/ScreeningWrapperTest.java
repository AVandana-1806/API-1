package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ScreeningWrapperTest {
  private String id = "testscreeningdashboard";
  private String reference = "screening dashboard reference";
  private String name = "screening name";
  private String screeningDecision = "5 days";
  private String screeningDecisionDetail = "screening decision detail";
  private String assignee = null;
  private String assigneeStaffId = "abb";
  private String screeningStatus = "Open";
  private Date startedAt;

  @Test
  public void testConstructorAndGetters() throws Exception {
    ScreeningWrapper screeningWrapper = new ScreeningWrapper(id, reference, name, screeningDecision,
        assigneeStaffId, startedAt, screeningStatus);
    screeningWrapper.setScreeningDecisionDetail(screeningDecisionDetail);
    assertThat(screeningWrapper.getId(), is(equalTo(id)));
    assertThat(screeningWrapper.getReference(), is(equalTo(reference)));
    assertThat(screeningWrapper.getName(), is(equalTo(name)));
    assertThat(screeningWrapper.getScreeningDecision(), is(equalTo(screeningDecision)));
    assertThat(screeningWrapper.getScreeningDecisionDetail(), is(equalTo(screeningDecisionDetail)));
    assertThat(screeningWrapper.getAssignee(), is(equalTo(assignee)));
    assertThat(screeningWrapper.getAssigneeStaffId(), is(equalTo(assigneeStaffId)));
    assertThat(screeningWrapper.getStartedAt(), is(equalTo(startedAt)));
    assertThat(screeningWrapper.getScreeningStatus(), is(equalTo(screeningStatus)));
  }

  @Test
  public void testSetters() throws Exception {

    ScreeningWrapper screeningWrapper = new ScreeningWrapper(id, reference, name, screeningDecision,
        assigneeStaffId, startedAt, screeningStatus);
    screeningWrapper.setId("setscreeningid");
    screeningWrapper.setScreeningDecisionDetail(screeningDecisionDetail);
    assertThat(screeningWrapper.getId(), is(equalTo("setscreeningid")));
    screeningWrapper.setReference("new reference");
    assertThat(screeningWrapper.getReference(), is(equalTo("new reference")));
    screeningWrapper.setName("new name");
    assertThat(screeningWrapper.getName(), is(equalTo("new name")));
    screeningWrapper.setScreeningDecision("new screening decision");
    assertThat(screeningWrapper.getScreeningDecision(), is(equalTo("new screening decision")));
    screeningWrapper.setScreeningDecisionDetail("new screening decision detail");
    assertThat(screeningWrapper.getScreeningDecisionDetail(),
        is(equalTo("new screening decision detail")));
    screeningWrapper.setAssignee("new assignee");
    assertThat(screeningWrapper.getAssignee(), is(equalTo("new assignee")));
    screeningWrapper.setAssigneeStaffId("new staff id");
    assertThat(screeningWrapper.getAssigneeStaffId(), is(equalTo("new staff id")));
    startedAt = new Date();
    screeningWrapper.setStartedAt(startedAt);
    assertThat(screeningWrapper.getStartedAt(), is(equalTo(startedAt)));
    screeningWrapper.setScreeningStatus(screeningStatus);
    assertThat(screeningWrapper.getScreeningStatus(), is(equalTo(screeningStatus)));
  }

  @Test
  public void testDefaultConstructorNotNull() throws Exception {
    ScreeningWrapper screeningWrapper = new ScreeningWrapper();
    assertNotNull(screeningWrapper);
  }

  @Test
  public void testEquaslHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(ScreeningWrapper.class)
        .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
  }

}
