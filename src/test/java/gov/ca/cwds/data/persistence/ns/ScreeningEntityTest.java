package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.util.Doofenshmirtz;

public class ScreeningEntityTest extends Doofenshmirtz<ScreeningEntity> {

  ScreeningEntity target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new ScreeningEntity();
  }

  @Test
  public void type() throws Exception {
    assertThat(ScreeningEntity.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    String actual = target.getPrimaryKey();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_A$() throws Exception {
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_A$String() throws Exception {
    String id = null;
    target.setId(id);
  }

  @Test
  public void setReference_A$String() throws Exception {
    String reference = null;
    target.setReference(reference);
  }

  @Test
  public void setEndedAt_A$Date() throws Exception {
    Date endedAt = mock(Date.class);
    target.setEndedAt(endedAt);
  }

  @Test
  public void setIncidentCounty_A$String() throws Exception {
    String incidentCounty = null;
    target.setIncidentCounty(incidentCounty);
  }

  @Test
  public void setIncidentDate_A$LocalDate() throws Exception {
    LocalDate incidentDate = LocalDate.now();
    target.setIncidentDate(incidentDate);
  }

  @Test
  public void setLocationType_A$String() throws Exception {
    String locationType = null;
    target.setLocationType(locationType);
  }

  @Test
  public void setCommunicationMethod_A$String() throws Exception {
    String communicationMethod = null;
    target.setCommunicationMethod(communicationMethod);
  }

  @Test
  public void setName_A$String() throws Exception {
    String name = null;
    target.setName(name);
  }

  @Test
  public void setScreeningDecision_A$String() throws Exception {
    String screeningDecision = null;
    target.setScreeningDecision(screeningDecision);
  }

  @Test
  public void setStartedAt_A$Date() throws Exception {
    Date startedAt = mock(Date.class);
    target.setStartedAt(startedAt);
  }

  @Test
  public void setNarrative_A$String() throws Exception {
    String narrative = null;
    target.setNarrative(narrative);
  }

  @Test
  public void setAssignee_A$String() throws Exception {
    String assignee = null;
    target.setAssignee(assignee);
  }

  @Test
  public void setAdditionalInformation_A$String() throws Exception {
    String additionalInformation = null;
    target.setAdditionalInformation(additionalInformation);
  }

  @Test
  public void setScreeningDecisionDetail_A$String() throws Exception {
    String screeningDecisionDetail = null;
    target.setScreeningDecisionDetail(screeningDecisionDetail);
  }

  @Test
  public void setSafetyInformation_A$String() throws Exception {
    String safetyInformation = null;
    target.setSafetyInformation(safetyInformation);
  }

  @Test
  public void setSafetyAlerts_A$StringArray() throws Exception {
    String[] safetyAlerts = new String[] {};
    target.setSafetyAlerts(safetyAlerts);
  }

  @Test
  public void setReferralId_A$String() throws Exception {
    String referralId = null;
    target.setReferralId(referralId);
  }

  @Test
  public void setAssigneeStaffId_A$String() throws Exception {
    String assigneeStaffId = null;
    target.setAssigneeStaffId(assigneeStaffId);
  }

  @Test
  public void setRestrictionsRationale_A$String() throws Exception {
    String restrictionsRationale = null;
    target.setRestrictionsRationale(restrictionsRationale);
  }

  @Test
  public void setUserCountyCode_A$Integer() throws Exception {
    Integer userCountyCode = null;
    target.setUserCountyCode(userCountyCode);
  }

  @Test
  public void setRestrictionsDate_A$Date() throws Exception {
    Date restrictionsDate = mock(Date.class);
    target.setRestrictionsDate(restrictionsDate);
  }

  @Test
  public void getIndexable_A$() throws Exception {
    Boolean actual = target.getIndexable();
    Boolean expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIndexable_A$Boolean() throws Exception {
    Boolean indexable = null;
    target.setIndexable(indexable);
  }

  @Test
  public void getCurrentLocationOfChildren_A$() throws Exception {
    String actual = target.getCurrentLocationOfChildren();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCurrentLocationOfChildren_A$String() throws Exception {
    String currentLocationOfChildren = null;
    target.setCurrentLocationOfChildren(currentLocationOfChildren);
  }

  @Test
  public void getReference_A$() throws Exception {
    String actual = target.getReference();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEndedAt_A$() throws Exception {
    Date actual = target.getEndedAt();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getIncidentCounty_A$() throws Exception {
    String actual = target.getIncidentCounty();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getIncidentDate_A$() throws Exception {
    LocalDate actual = target.getIncidentDate();
    LocalDate expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLocationType_A$() throws Exception {
    String actual = target.getLocationType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCommunicationMethod_A$() throws Exception {
    String actual = target.getCommunicationMethod();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getName_A$() throws Exception {
    String actual = target.getName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getScreeningDecision_A$() throws Exception {
    String actual = target.getScreeningDecision();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStartedAt_A$() throws Exception {
    Date actual = target.getStartedAt();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNarrative_A$() throws Exception {
    String actual = target.getNarrative();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAssignee_A$() throws Exception {
    String actual = target.getAssignee();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAdditionalInformation_A$() throws Exception {
    String actual = target.getAdditionalInformation();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getScreeningDecisionDetail_A$() throws Exception {
    String actual = target.getScreeningDecisionDetail();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSafetyInformation_A$() throws Exception {
    String actual = target.getSafetyInformation();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSafetyAlerts_A$() throws Exception {
    String[] actual = target.getSafetyAlerts();
    String[] expected = new String[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReferralId_A$() throws Exception {
    String actual = target.getReferralId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAssigneeStaffId_A$() throws Exception {
    String actual = target.getAssigneeStaffId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAccessRestrictions_A$() throws Exception {
    String actual = target.getAccessRestrictions();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAccessRestrictions_A$String() throws Exception {
    String accessRestrictions = null;
    target.setAccessRestrictions(accessRestrictions);
  }

  @Test
  public void getRestrictionsRationale_A$() throws Exception {
    String actual = target.getRestrictionsRationale();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getUserCountyCode_A$() throws Exception {
    Integer actual = target.getUserCountyCode();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getRestrictionsDate_A$() throws Exception {
    Date actual = target.getRestrictionsDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void isIndexable_A$() throws Exception {
    Boolean actual = target.isIndexable();
    Boolean expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAllegations_A$() throws Exception {
    Set<Allegation> actual = target.getAllegations();
    Set<Allegation> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getParticipants_A$() throws Exception {
    Set<ParticipantEntity> actual = target.getParticipants();
    Set<ParticipantEntity> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setParticipants_A$Set() throws Exception {
    Set<ParticipantEntity> participants = new HashSet<>();
    target.setParticipants(participants);
  }

  @Test
  public void getReportType_A$() throws Exception {
    String actual = target.getReportType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setReportType_A$String() throws Exception {
    String reportType = null;
    target.setReportType(reportType);
  }

  @Test
  public void getScreeningStatus_A$() throws Exception {
    String actual = target.getScreeningStatus();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setScreeningStatus_A$String() throws Exception {
    String screeningStatus = null;
    target.setScreeningStatus(screeningStatus);
  }

  @Test
  public void getScreeningContactReference_A$() throws Exception {
    String actual = target.getScreeningContactReference();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setScreeningContactReference_A$String() throws Exception {
    String screeningContactReference = null;
    target.setScreeningContactReference(screeningContactReference);
  }

  @Test
  public void hashCode_A$() throws Exception {
    int actual = target.hashCode();
    int expected = 0;
    assertThat(actual, is(not(expected)));
  }

  @Test
  public void equals_A$Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
