package gov.ca.cwds.fixture;

import static gov.ca.cwds.rest.api.domain.DomainChef.uncookISO8601Timestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.AllegationIntake;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.Screening;

/**
 * @author CWDS API Team
 *
 */
public class ScreeningResourceBuilder {

  String id = "1";
  String referralId = "";
  Date endedAt = uncookISO8601Timestamp("2017-01-03T11:10:09.999Z");
  String incidentCounty = "34";
  LocalDate incidentDate = LocalDate.parse("2017-01-01");
  String locationType = "Foster Home";
  String communicationMethod = "in_person";
  String currentLocationOfChildren = null;
  String name = "The Rocky Horror Show";
  String reportNarrative = "Narrative 123 test";
  String reference = "123ABC";
  String restrictionsRationale = "";
  Date startedAt = uncookISO8601Timestamp("2017-01-02T10:09:08.999Z");
  String assignee = "Michael Bastow";
  String assigneeStaffId = "0X5";
  String additionalInformation = "additional information";
  String screeningDecision = "Screening Decision";
  String screeningDecisionDetail = "evaluate_out";
  AddressIntakeApi incidentAddress = null;
  Set<ParticipantIntakeApi> participantIntakeApis = null;
  Set<AllegationIntake> allegations = null;
  Set<CrossReportIntake> crossReports = null;
  Set<String> safetyAlerts = new HashSet<>();
  String safetyInformation = null;
  String accessRestrictions = "none";


  /**
   * @return the Screening
   */
  public Screening build() {
    Screening screening = new Screening();
    screening.setId(id);
    screening.setReferralId(referralId);
    screening.setEndedAt(endedAt);
    screening.setIncidentCounty(incidentCounty);
    screening.setIncidentDate(incidentDate);
    screening.setLocationType(locationType);
    screening.setCommunicationMethod(communicationMethod);
    screening.setCurrentLocationOfChildren(currentLocationOfChildren);
    screening.setName(name);
    screening.setReportNarrative(reportNarrative);
    screening.setReference(reference);
    screening.setRestrictionsRationale(restrictionsRationale);
    screening.setStartedAt(startedAt);
    screening.setAssignee(assignee);
    screening.setAssigneeStaffId(assigneeStaffId);
    screening.setAdditionalInformation(additionalInformation);
    screening.setScreeningDecision(screeningDecision);
    screening.setScreeningDecisionDetail(screeningDecisionDetail);
    screening.setIncidentAddress(incidentAddress);
    screening.setParticipantIntakeApis(participantIntakeApis);
    screening.setSafetyAlerts(safetyAlerts);
    screening.setSafetyInformation(safetyInformation);
    screening.setAccessRestrictions(accessRestrictions);

    return screening;
  }


  public ScreeningResourceBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }


  public ScreeningResourceBuilder setEndedAt(String endedAt) {
    this.endedAt = uncookISO8601Timestamp(endedAt);
    return this;
  }


  public ScreeningResourceBuilder setIncidentCounty(String incidentCounty) {
    this.incidentCounty = incidentCounty;
    return this;
  }


  public ScreeningResourceBuilder setIncidentDate(String incidentDate) {
    this.incidentDate = LocalDate.parse(incidentDate);
    return this;
  }


  public ScreeningResourceBuilder setLocationType(String locationType) {
    this.locationType = locationType;
    return this;
  }


  public ScreeningResourceBuilder setCommunicationMethod(String communicationMethod) {
    this.communicationMethod = communicationMethod;
    return this;
  }


  public ScreeningResourceBuilder setCurrentLocationOfChildren(String currentLocationOfChildren) {
    this.currentLocationOfChildren = currentLocationOfChildren;
    return this;
  }


  public ScreeningResourceBuilder setName(String name) {
    this.name = name;
    return this;
  }


  public ScreeningResourceBuilder setReportNarrative(String reportNarrative) {
    this.reportNarrative = reportNarrative;
    return this;
  }


  public ScreeningResourceBuilder setReference(String reference) {
    this.reference = reference;
    return this;
  }


  public ScreeningResourceBuilder setRestrictionsRationale(String restrictionsRationale) {
    this.restrictionsRationale = restrictionsRationale;
    return this;
  }


  public ScreeningResourceBuilder setStartedAt(Date startedAt) {
    this.startedAt = startedAt;
    return this;
  }


  public ScreeningResourceBuilder setAssignee(String assignee) {
    this.assignee = assignee;
    return this;
  }


  public ScreeningResourceBuilder setAssigneeStaffId(String assigneeStaffId) {
    this.assigneeStaffId = assigneeStaffId;
    return this;
  }


  public ScreeningResourceBuilder setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
    return this;
  }


  public ScreeningResourceBuilder setScreeningDecision(String screeningDecision) {
    this.screeningDecision = screeningDecision;
    return this;
  }


  public ScreeningResourceBuilder setScreeningDecisionDetail(String screeningDecisionDetail) {
    this.screeningDecisionDetail = screeningDecisionDetail;
    return this;
  }


  public ScreeningResourceBuilder setIncidentAddress(AddressIntakeApi incidentAddress) {
    this.incidentAddress = incidentAddress;
    return this;
  }


  public ScreeningResourceBuilder setParticipantIntakeApis(
      Set<ParticipantIntakeApi> participantIntakeApis) {
    this.participantIntakeApis = participantIntakeApis;
    return this;
  }


  public ScreeningResourceBuilder setAllegations(Set<AllegationIntake> allegations) {
    this.allegations = allegations;
    return this;
  }


  public ScreeningResourceBuilder setCrossReports(Set<CrossReportIntake> crossReports) {
    this.crossReports = crossReports;
    return this;
  }


  public ScreeningResourceBuilder setSafetyAlerts(Set<String> safetyAlerts) {
    this.safetyAlerts = safetyAlerts;
    return this;
  }

  public ScreeningResourceBuilder setSafetyInformation(String safetyInformation) {
    this.safetyInformation = safetyInformation;
    return this;
  }

  public ScreeningResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }


}
