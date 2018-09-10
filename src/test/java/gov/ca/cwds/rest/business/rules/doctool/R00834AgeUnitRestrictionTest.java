package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.ReferralClientResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.business.rules.R00834AgeUnitRestriction;

/**
 * @author CWDS API Team
 *
 */
public class R00834AgeUnitRestrictionTest {

  @Test
  public void TestClientDObisNull() {
    String dateStarted = "2018-05-14";
    Client client = new ClientResourceBuilder().setBirthDate(null).setEstimatedDobCode("Y").build();
    ReferralClient referralClient = new ReferralClientResourceBuilder().setAgeNumber(null)
        .setAgePeriodCode(null).buildReferralClient();
    R00834AgeUnitRestriction r00834AgeUnitRestriction =
        new R00834AgeUnitRestriction(client, referralClient, dateStarted);
    r00834AgeUnitRestriction.execute();
    assertThat(referralClient.getAgeNumber(), is(nullValue()));
    assertThat(referralClient.getAgePeriodCode(), is(equalTo("")));
  }

  @Test
  public void TestWhenAgeAndAgeUnitSetYears() {
    String dateStarted = "2018-05-07";
    Client client =
        new ClientResourceBuilder().setBirthDate("2005-05-07").setEstimatedDobCode("N").build();
    ReferralClient referralClient = new ReferralClientResourceBuilder().setAgeNumber(null)
        .setAgePeriodCode(null).buildReferralClient();
    R00834AgeUnitRestriction r00834AgeUnitRestriction =
        new R00834AgeUnitRestriction(client, referralClient, dateStarted);
    r00834AgeUnitRestriction.execute();
    assertThat(referralClient.getAgeNumber(), is(equalTo((short) 13)));
    assertThat(referralClient.getAgePeriodCode(), is(equalTo("Y")));
  }

  @Test
  public void TestWhenAgeAndAgeUnitSetMonths() {
    String dateStarted = "2018-05-07";
    Client client =
        new ClientResourceBuilder().setBirthDate("2017-12-07").setEstimatedDobCode("N").build();
    ReferralClient referralClient = new ReferralClientResourceBuilder().setAgeNumber(null)
        .setAgePeriodCode(null).buildReferralClient();
    R00834AgeUnitRestriction r00834AgeUnitRestriction =
        new R00834AgeUnitRestriction(client, referralClient, dateStarted);
    r00834AgeUnitRestriction.execute();
    assertThat(referralClient.getAgeNumber(), is(equalTo((short) 5)));
    assertThat(referralClient.getAgePeriodCode(), is(equalTo("M")));
  }

  @Test
  public void TestWhenAgeAndAgeUnitSetWeeks() {
    String dateStarted = "2018-05-07";
    Client client =
        new ClientResourceBuilder().setBirthDate("2018-04-10").setEstimatedDobCode("N").build();
    ReferralClient referralClient = new ReferralClientResourceBuilder().setAgeNumber(null)
        .setAgePeriodCode(null).buildReferralClient();
    R00834AgeUnitRestriction r00834AgeUnitRestriction =
        new R00834AgeUnitRestriction(client, referralClient, dateStarted);
    r00834AgeUnitRestriction.execute();
    assertThat(referralClient.getAgeNumber(), is(equalTo((short) 3)));
    assertThat(referralClient.getAgePeriodCode(), is(equalTo("W")));
  }

  @Test
  public void TestWhenAgeAndAgeUnitSetDays() {
    String dateStarted = "2018-05-07";
    Client client =
        new ClientResourceBuilder().setBirthDate("2018-05-01").setEstimatedDobCode("N").build();
    ReferralClient referralClient = new ReferralClientResourceBuilder().setAgeNumber(null)
        .setAgePeriodCode(null).buildReferralClient();
    R00834AgeUnitRestriction r00834AgeUnitRestriction =
        new R00834AgeUnitRestriction(client, referralClient, dateStarted);
    r00834AgeUnitRestriction.execute();
    assertThat(referralClient.getAgeNumber(), is(equalTo((short) 6)));
    assertThat(referralClient.getAgePeriodCode(), is(equalTo("D")));
  }

  @Test
  public void TestBirthDateIsTheSameAsDateStarted() {
    String dateStarted = "2018-05-01";
    Client client =
        new ClientResourceBuilder().setBirthDate("2018-05-01").setEstimatedDobCode("N").build();
    ReferralClient referralClient = new ReferralClientResourceBuilder().setAgeNumber(null)
        .setAgePeriodCode(null).buildReferralClient();
    R00834AgeUnitRestriction r00834AgeUnitRestriction =
        new R00834AgeUnitRestriction(client, referralClient, dateStarted);
    r00834AgeUnitRestriction.execute();
    assertThat(referralClient.getAgeNumber(), is(equalTo((short) 0)));
    assertThat(referralClient.getAgePeriodCode(), is(equalTo("D")));
  }

}
