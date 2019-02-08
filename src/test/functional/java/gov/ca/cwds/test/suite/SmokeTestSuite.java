package gov.ca.cwds.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import gov.ca.cwds.api.HealthCheckTest;
import gov.ca.cwds.api.ScreeningToReferralTest;

/**
 * @author CWDS API Team
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ScreeningToReferralTest.class})
public class SmokeTestSuite {

}
