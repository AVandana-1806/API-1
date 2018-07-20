package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.ns.ScreeningWrapper;
import gov.ca.cwds.rest.api.domain.DomainChef;

public class ScreeningWrapperEntityBuilder {

  private String id = "testscreeningdashboard";
  private String reference = "screening dashboard reference";
  private String name = "screening name";
  private String screeningDecision = "5 days";
  private String screeningDecisionDetail = "screening decision detail";
  private String assignee = "Test User";
  private String assigneeStaffId = "abb";
  private String screeningStatus = "Open";
  private Date startedAt;

  public ScreeningWrapper build() {
    ScreeningWrapper screeningWrapper = new ScreeningWrapper(id, reference, name, screeningDecision,
        assigneeStaffId, startedAt, screeningStatus);
    screeningWrapper.setScreeningDecisionDetail(screeningDecisionDetail);
    screeningWrapper.setAssignee(assignee);
    return screeningWrapper;
  }

  public ScreeningWrapperEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public ScreeningWrapperEntityBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public ScreeningWrapperEntityBuilder setReference(String reference) {
    this.reference = reference;
    return this;
  }

  public ScreeningWrapperEntityBuilder setScreeningDecisionDetail(String screeningDecisionDetail) {
    this.screeningDecisionDetail = screeningDecisionDetail;
    return this;
  }

  public ScreeningWrapperEntityBuilder setAssignee(String assignee) {
    this.assignee = assignee;
    return this;
  }

  public ScreeningWrapperEntityBuilder setAssigneeStaffId(String assigneeStaffId) {
    this.assigneeStaffId = assigneeStaffId;
    return this;
  }

  public ScreeningWrapperEntityBuilder setStartedAt(String startedAt) {
    this.startedAt = DomainChef.uncookDateString(startedAt);
    return this;
  }

  public ScreeningWrapperEntityBuilder setScreeningStatus(String screeningStatus) {
    this.screeningStatus = screeningStatus;
    return this;
  }
}
