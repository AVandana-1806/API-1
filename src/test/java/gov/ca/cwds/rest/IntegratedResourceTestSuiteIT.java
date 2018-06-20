package gov.ca.cwds.rest;

import gov.ca.cwds.rest.resources.ParticipantIntakeApiResourceIRT;
import gov.ca.cwds.rest.resources.ScreeningIntakeResourceIRT;
import gov.ca.cwds.rest.resources.auth.AuthorizationResourceIRT;
import gov.ca.cwds.rest.resources.contact.ContactResourceIRT;
import gov.ca.cwds.rest.resources.hoi.HoiCaseResourceAuthorizationIRT;
import gov.ca.cwds.rest.resources.hoi.HoiCaseResourceIRT;
import gov.ca.cwds.rest.resources.hoi.HoiReferralResourceIRT;
import gov.ca.cwds.rest.resources.hoi.HoiScreeningResourceIRT;
import gov.ca.cwds.rest.resources.hoi.HoiUsingClientIdResourceIRT;
import gov.ca.cwds.rest.resources.hoi.InvolvementHistoryResourceIRT;
import gov.ca.cwds.rest.resources.relationship.ScreeningRelationshipResourceIRT;
import gov.ca.cwds.test.support.BaseDropwizardApplication;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AuthorizationResourceIRT.class,
    HoiCaseResourceAuthorizationIRT.class,
    HoiCaseResourceIRT.class,
    HoiReferralResourceIRT.class,
    HoiScreeningResourceIRT.class,
    HoiUsingClientIdResourceIRT.class,
    InvolvementHistoryResourceIRT.class,
    ScreeningIntakeResourceIRT.class,
    ParticipantIntakeApiResourceIRT.class,
    ScreeningRelationshipResourceIRT.class,
    ContactResourceIRT.class
})
public class IntegratedResourceTestSuiteIT {

  @ClassRule
  public static final BaseDropwizardApplication<ApiConfiguration> application =
      new BaseDropwizardApplication<>(ApiApplication.class, "config/test-api.yml");

  protected BaseDropwizardApplication<ApiConfiguration> getApplication() {
    return application;
  }
}
