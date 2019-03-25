package gov.ca.cwds.rest.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.Role;

/**
 * This static class is used to get the valid victim's from participants.
 * 
 * <p>
 * Later we can add methods to get the remaining roles, like perpetrators and reporters.
 * </p>
 * 
 * @author CWDS API team
 */
public class ParticipantUtils {

  /**
   * 
   */
  private ParticipantUtils() {
    // no-opt
  }

  /**
   * @param participants - participants
   * @return the valid victims
   */
  public static Collection<Participant> getVictims(Collection<Participant> participants) {
    Collection<Participant> victims = new ArrayList<>(0);

    if (participants != null) {
      victims = participants.stream()
          .filter(value -> value.getRoles().contains(Role.VICTIM_ROLE.getType()))
          .collect(Collectors.toList());
    }

    return victims;
  }

}
