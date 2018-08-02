package gov.ca.cwds.test.suite;

import gov.ca.cwds.api.HealthCheckTest;
import gov.ca.cwds.api.ScreeningToReferralTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({HealthCheckTest.class, ScreeningToReferralTest.class})
public class SmokeTestSuite {

}
