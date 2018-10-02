package gov.ca.cwds.rest.business.rules;

import java.util.List;
import java.util.ArrayList;

import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.business.RuleValidator;

/**
* <blockquote>
* 
* <pre>
* BUSINESS RULE: R - R - 10971
* 
*  If the CSEC Type is 'Victim while Absent from Placement' make the End Date mandatory.
*  
* </pre>
* </blockquote>
*/

public class R10971CsecEndDate implements RuleValidator {
  
  private static final String VICTIM_WHILE_ABSENT_FROM_PLACEMENT = "6871";
  private List<Csec> csecs = new ArrayList<>();

  /**
   * @param csecs - list of Csec objects
   * 
   */
  public R10971CsecEndDate(List<Csec> csecs) {
    super();
    this.csecs = csecs;    
  }
  
  @Override
  public boolean isValid() {
    if (null != csecs) {
      for (Csec csec :csecs) {
        final String csecCodeId = csec.getCsecCodeId();
        if (null != csecCodeId && VICTIM_WHILE_ABSENT_FROM_PLACEMENT.equals(csec.getCsecCodeId()) && null == csec.getEndDate()) {
          return false;
        }
      }
    }
    return true;
  }
}
