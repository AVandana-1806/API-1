package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.drools.DroolsConfiguration;
import gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies;

/**
 * @author CWDS API Team
 *
 */
public class SafelySurrenderBabiesDroolsConfiguration
    extends DroolsConfiguration<SafelySurrenderedBabies> {

  public static final SafelySurrenderBabiesDroolsConfiguration INSTANCE =
      new SafelySurrenderBabiesDroolsConfiguration("safely-surrender-babies-session",
          "safely-surrender-babies-agenda", "safely-surrender-babies-rules");

  private SafelySurrenderBabiesDroolsConfiguration(String rulesSession, String rulesAgenda,
      String kieContainerId) {
    super(rulesSession, rulesAgenda, kieContainerId);
  }

}
