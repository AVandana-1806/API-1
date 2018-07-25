package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.ScreeningDashboard;

public class ScreeningDashboardResourceBuilder {
  private String id = "testscreeningdashboard";
  private String reference = "screening dashboard reference";
  private String name = "screening name";
  private String screeningDecision = "5 days";
  private String screeningDecisionDetail = "screening decision detail";
  private String assignee = "Test User";
  private String assigneeStaffId = "abb";
  private Date startedAt;
  
  public ScreeningDashboard build() {
    ScreeningDashboard sd = new ScreeningDashboard(id, reference, name, screeningDecision, 
        screeningDecisionDetail, assignee, assigneeStaffId);
    sd.setStartedAt(startedAt);
    return sd;
  }
  
  public ScreeningDashboardResourceBuilder setId(String id) {
	this.id = id;
	return this;
  }
  public ScreeningDashboardResourceBuilder setName(String name) {
    this.name = name;
    return this;
  }
  public ScreeningDashboardResourceBuilder setReference(String reference) {
    this.reference = reference;
    return this;
  }
  public ScreeningDashboardResourceBuilder setScreeningDecisionDetail(String screeningDecisionDetail) {
    this.screeningDecisionDetail = screeningDecisionDetail;
    return this;
  }
  public ScreeningDashboardResourceBuilder setAssignee(String assignee) {
	this.assignee =  assignee;
	return this;
  }
  public ScreeningDashboardResourceBuilder setAssigneeStaffId(String assigneeStaffId) {
	this.assigneeStaffId = assigneeStaffId;
	return this;
  }
  public ScreeningDashboardResourceBuilder setStartedAt(String startedAt) {
	this.startedAt = DomainChef.uncookDateString(startedAt);
	return this;
  }
}
