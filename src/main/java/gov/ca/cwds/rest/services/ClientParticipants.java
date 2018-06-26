package gov.ca.cwds.rest.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.Participant;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ClientParticipants {
  Set<Participant> participants;
  Map<Long, String> victimIds;
  Map<Long, String> perpetratorIds;

  /**
   * 
   */
  public ClientParticipants() {
    this.participants = new HashSet<>();
    this.victimIds = new HashMap<>();
    this.perpetratorIds = new HashMap<>();
  }

  /**
   * @param participants - participants
   * @param victimIds - victimIds
   * @param perpetratorIds - perpetratorIds
   */
  public ClientParticipants(Set<Participant> participants, Map<Long, String> victimIds,
      Map<Long, String> perpetratorIds) {
    this.participants = participants;
    this.victimIds = victimIds;
    this.perpetratorIds = perpetratorIds;
  }

  public Set<Participant> getParticipants() {
    return participants;
  }

  public void setParticipants(Set<Participant> participants) {
    this.participants = participants;
  }

  public void addParticipant(Participant participant) {
    if (participants == null) {
      participants = new HashSet<>();
    }
    addToClientIds(participant);
    this.participants.add(participant);
  }

  public Map<Long, String> getVictimIds() {
    return victimIds;
  }

  public void setVictimIds(Map<Long, String> victimIds) {
    this.victimIds = victimIds;
  }

  public void addVictimIds(Long id, String legacyId) {
    if (victimIds == null) {
      victimIds = new HashMap<>();
    }
    this.victimIds.put(id, legacyId);
  }

  public Map<Long, String> getPerpetratorIds() {
    return perpetratorIds;
  }

  public void setPerpetratorIds(Map<Long, String> perpetratorIds) {
    this.perpetratorIds = perpetratorIds;
  }

  public void addPerpetratorIds(Long id, String legacyId) {
    if (perpetratorIds == null) {
      perpetratorIds = new HashMap<>();
    }
    this.perpetratorIds.put(id, legacyId);
  }

  public void addParticipants(Set<Participant> participants) {
    for (Participant participant : participants) {
      addToClientIds(participant);
      addParticipant(participant);
    }
  }

  private void addToClientIds(Participant participant) {
    if (participant.isVictim()) {
      addVictimIds(participant.getId(), participant.getLegacyDescriptor().getId());
    } else if (participant.isPerpetrator()) {
      addPerpetratorIds(participant.getId(), participant.getLegacyDescriptor().getId());
    }
  }

}
