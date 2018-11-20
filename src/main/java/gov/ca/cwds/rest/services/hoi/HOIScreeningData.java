package gov.ca.cwds.rest.services.hoi;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;

public class HOIScreeningData {

  private Collection<String> clientIds;

  private Set<ScreeningEntity> screeningEntities = new HashSet<>();

  private Map<String, IntakeLOVCodeEntity> countyIntakeLOVCodeEntityMap;

  private Map<String, LegacyDescriptorEntity> participantLegacyDescriptors;

  private Collection<String> assigneeStaffIds;

  private Map<String, StaffPerson> staffPersonMap;

  public HOIScreeningData(Collection<String> clientIds) {
    this.clientIds = clientIds;
  }

  public Collection<String> getClientIds() {
    return clientIds;
  }

  public void setClientIds(Collection<String> clientIds) {
    this.clientIds = clientIds;
  }

  public Set<ScreeningEntity> getScreeningEntities() {
    return screeningEntities;
  }

  public Map<String, IntakeLOVCodeEntity> getCountyIntakeLOVCodeEntityMap() {
    return countyIntakeLOVCodeEntityMap;
  }

  public void setCountyIntakeLOVCodeEntityMap(
      Map<String, IntakeLOVCodeEntity> countyIntakeLOVCodeEntityMap) {
    this.countyIntakeLOVCodeEntityMap = countyIntakeLOVCodeEntityMap;
  }

  public Map<String, LegacyDescriptorEntity> getParticipantLegacyDescriptors() {
    return participantLegacyDescriptors;
  }

  public void setParticipantLegacyDescriptors(
      Map<String, LegacyDescriptorEntity> participantLegacyDescriptors) {
    this.participantLegacyDescriptors = participantLegacyDescriptors;
  }

  public Collection<String> getAssigneeStaffIds() {
    return assigneeStaffIds;
  }

  public void setAssigneeStaffIds(Collection<String> assigneeStaffIds) {
    this.assigneeStaffIds = assigneeStaffIds;
  }

  public Map<String, StaffPerson> getStaffPersonMap() {
    return staffPersonMap;
  }

  public void setStaffPersonMap(Map<String, StaffPerson> staffPersonMap) {
    this.staffPersonMap = staffPersonMap;
  }

}
