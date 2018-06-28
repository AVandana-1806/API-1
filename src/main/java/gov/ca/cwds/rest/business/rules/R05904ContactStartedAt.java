package gov.ca.cwds.rest.business.rules;

import java.util.Date;

import gov.ca.cwds.rest.business.RuleValidator;

/**
 * 
 * DocTool Rule validation
 * <p>
 * R - 05904 Delivered Service Start date limits The start date of a contact, visit, or delivered
 * service must be greater than or equal to the referral received date or the start date of the
 * case, whichever pertains. If not, display message.
 * </p>
 * 
 * <p>
 * While the Rule allows the two dates to be equal, the validation does not allow it. This is to
 * match front end validation, as requested by Intake Team #151667712.
 * </p>
 * 
 * @author CWDS API Team
 */

public class R05904ContactStartedAt implements RuleValidator {

  private Date contactStartedAtDateTime;
  private Date referralReceivedDateTime;


  public R05904ContactStartedAt(Date contactStartedAtDateTime, Date referralReceivedDateTime) {
    super();
    this.contactStartedAtDateTime = new Date(contactStartedAtDateTime.getTime());
    this.referralReceivedDateTime = new Date(referralReceivedDateTime.getTime());
  }


  @Override
  public boolean isValid() {
    return (contactStartedAtDateTime.after(referralReceivedDateTime)
        || contactStartedAtDateTime.equals(referralReceivedDateTime));
  }
}
