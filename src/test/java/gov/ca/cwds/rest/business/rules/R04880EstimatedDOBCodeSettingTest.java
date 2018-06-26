package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.ReferralClientResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import java.util.Date;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * CWDS API Team
 */
public class R04880EstimatedDOBCodeSettingTest {

  @Test
  public void shouldSetEstimatedDOBCodeNoWhenBirthDateSet() throws Exception {
    Client client = new ClientResourceBuilder().setBirthDate("2010-01-01").build();
    ReferralClient referralClient = new ReferralClientResourceBuilder().setAgeNumber(null).setAgePeriodCode("").buildReferralClient();
    
    R04880EstimatedDOBCodeSetting rule = new R04880EstimatedDOBCodeSetting(client, referralClient);
    rule.execute();
    assertEquals(gov.ca.cwds.rest.api.domain.cms.Client.ESTIMATED_DOB_CODE_NO, client.getEstimatedDobCode());
  }

  @Test
  public void shouldSetEstimatedDOBCodeYesWhenAgeAndAgePeriodNotBlank() throws Exception {
    Client client = new ClientResourceBuilder().setBirthDate(null).build();
    ReferralClient referralClient = new ReferralClientResourceBuilder().setAgeNumber((short) 10).setAgePeriodCode("Y").buildReferralClient();
    R04880EstimatedDOBCodeSetting rule = new R04880EstimatedDOBCodeSetting(client, referralClient);
    rule.execute();
    assertEquals(gov.ca.cwds.rest.api.domain.cms.Client.ESTIMATED_DOB_CODE_YES, client.getEstimatedDobCode());
  }
  
  @Test
  public void shouldSetEstimatedDOBCodeUnknownWhenBirthDateAgeAgePeriodCodeBlank() throws Exception {
    Client client = new ClientResourceBuilder().setBirthDate("").build();
    ReferralClient referralClient = new ReferralClientResourceBuilder().setAgeNumber(null).setAgePeriodCode("").buildReferralClient();
    
    R04880EstimatedDOBCodeSetting rule = new R04880EstimatedDOBCodeSetting(client, referralClient);
    rule.execute();
    assertEquals(gov.ca.cwds.rest.api.domain.cms.Client.ESTIMATED_DOB_CODE_UNKNOWN, client.getEstimatedDobCode());
  }
  
  @Test
  public void shouldSetEstimatedDOBCodeUnknownWhenBirthDateNullAgeAgePeriodCodeInvalid() throws Exception {
    Client client = new ClientResourceBuilder().setBirthDate(null).build();
    ReferralClient referralClient = new ReferralClientResourceBuilder().setAgeNumber((short) 10).setAgePeriodCode("").buildReferralClient();
    
    R04880EstimatedDOBCodeSetting rule = new R04880EstimatedDOBCodeSetting(client, referralClient);
    rule.execute();
    assertEquals(gov.ca.cwds.rest.api.domain.cms.Client.ESTIMATED_DOB_CODE_UNKNOWN, client.getEstimatedDobCode());
    
  }
}