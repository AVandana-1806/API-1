package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.legacy.cms.entity.SafelySurrenderedBabies;
import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * @author CWDS API Team
 *
 */
public class SafelySurrenderBabiesDroolsConfiguration
    extends DroolsConfiguration<SafelySurrenderedBabies> {

  public static final SafelySurrenderBabiesDroolsConfiguration INSTANCE =
      new SafelySurrenderBabiesDroolsConfiguration("safely-surrender-babies-session",
          "safely-surrender-babies-agenda", "rules/safely-surrender-babies");

  private SafelySurrenderBabiesDroolsConfiguration(String rulesSession, String rulesAgenda,
      String kieContainerId) {
    super(rulesSession, rulesAgenda, kieContainerId);
  }

}
