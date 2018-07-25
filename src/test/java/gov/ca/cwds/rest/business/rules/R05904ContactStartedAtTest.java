package gov.ca.cwds.rest.business.rules;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.rest.api.domain.DomainChef;

@SuppressWarnings("javadoc")
public class R05904ContactStartedAtTest {

  @Test
  public void isValidWhenContactStartedAtBeforeReferralReceivedDateTimeReturnsFalse()
      throws Exception {
    Date contactStartedAtDateTime =
        DomainChef.uncookStrictTimestampString("2010-04-27T23:29:14.000-0000");
    Date referralReceivedDateTime =
        DomainChef.uncookStrictTimestampString("2010-04-27T23:30:14.000-0000");
    Boolean actual =
        new R05904ContactStartedAt(contactStartedAtDateTime, referralReceivedDateTime).isValid();
    assertEquals(actual, false);
  }

  @Test
  public void isValidWhenContactStartedAtEqualsReferralReceivedDateTimeReturnsTrue()
      throws Exception {
    Date contactStartedAtDateTime =
        DomainChef.uncookStrictTimestampString("2010-04-27T23:30:14.000-0000");
    Date referralReceivedDateTime =
        DomainChef.uncookStrictTimestampString("2010-04-27T23:30:14.000-0000");
    Boolean actual =
        new R05904ContactStartedAt(contactStartedAtDateTime, referralReceivedDateTime).isValid();
    assertEquals(actual, true);
  }

  @Test
  public void isValidWhenContactStartedAtAfterReferralReceivedDateTimeReturnsTrue()
      throws Exception {
    Date contactStartedAtDateTime =
        DomainChef.uncookStrictTimestampString("2010-04-27T23:31:14.000-0000");
    Date referralReceivedDateTime =
        DomainChef.uncookStrictTimestampString("2010-04-27T23:30:14.000-0000");
    Boolean actual =
        new R05904ContactStartedAt(contactStartedAtDateTime, referralReceivedDateTime).isValid();
    assertEquals(actual, true);
  }

}
