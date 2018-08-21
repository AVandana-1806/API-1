package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.legacy.cms.entity.SafelySurrenderedBabies;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.business.rules.SafelySurrenderBabiesDroolsConfiguration;

/**
 * @author CWDS API Team
 *
 */
public class R07993Test {
  private DroolsService droolsService;

  @Before
  public void setUp() {
    droolsService = new DroolsService();
  }

  /**
   * 
   */
  @Test
  public void testQuestionnaireReceivedDateIsSet() {
    final SafelySurrenderedBabies safelySurrenderedBabies = new SafelySurrenderedBabies();
    safelySurrenderedBabies.setSurrenderedDate(LocalDate.now());
    safelySurrenderedBabies.setMedicalQuestionnaireCode("R");
    droolsService.performBusinessRules(SafelySurrenderBabiesDroolsConfiguration.INSTANCE,
        safelySurrenderedBabies);
    assert safelySurrenderedBabies.getQuestionnaireReceivedDate().equals(LocalDate.now());
  }

  /**
   * 
   */
  @Test
  public void testQuestionnaireReceivedDateIsNull() {
    final SafelySurrenderedBabies safelySurrenderedBabies = new SafelySurrenderedBabies();
    safelySurrenderedBabies.setSurrenderedDate(LocalDate.now());
    safelySurrenderedBabies.setMedicalQuestionnaireCode("U");
    droolsService.performBusinessRules(SafelySurrenderBabiesDroolsConfiguration.INSTANCE,
        safelySurrenderedBabies);
    assertThat(safelySurrenderedBabies.getQuestionnaireReceivedDate(), is(nullValue()));
  }

}
