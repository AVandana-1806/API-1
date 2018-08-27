package gov.ca.cwds.rest.business.rules.doctool;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.business.rules.ReferralAddressDroolsConfiguration;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.security.realm.PerryAccount;

/**
 * @author CWDS API Team
 *
 */
public class R05584Test {
  private DroolsService droolsService;
  private Address address;
  private PerryAccount principal;

  /**
   * 
   */
  @Before
  public void setUp() {
    principal = new TestingRequestExecutionContext("02f").getUserIdentity();
    droolsService = new DroolsService();
    address = new Address();
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testAddressGovtEntitySetToUserLoginCountyWhenRuleFires() throws Exception {
    checkR05584(() -> {
      address.setCity("Sacramento");
      address.setState((short) 1828);
      principal.getCountyCwsCode();
    });
    Assert.assertTrue(address.getGovernmentEntityCd() == 1126);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testAddressGovtEntityNotSetWhenRuleFails() throws Exception {
    checkR05584(() -> {
      address.setCity("Sacramento");
      address.setState((short) 0);
      address.setGovernmentEntityCd((short) 0);
      principal.getCountyCwsCode();
    });
    Assert.assertTrue(address.getGovernmentEntityCd() == 0);
  }

  private void checkR05584(Runnable testCase) {
    testCase.run();
    droolsService.performBusinessRules(ReferralAddressDroolsConfiguration.DATA_PROCESSING_INSTANCE,
        address, principal);
  }

}
