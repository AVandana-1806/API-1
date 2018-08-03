package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.RuleValidator;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * County Of Assigned Staff Worker validation
 * <p>
 * The incident county and the assignee's county should be the same to ensure that the referral that
 * is created in legacy can be edited
 * </p>
 * 
 * @author CWDS API Team
 */
public class CountyOfAssignedStaffWorker implements RuleValidator {

  private Referral referral;
  private StaffPersonDao staffPersonDao;

  public CountyOfAssignedStaffWorker(Referral referral, StaffPersonDao staffPersonDao) {
    super();
    this.referral = referral;
    this.staffPersonDao = staffPersonDao;
  }

  @Override
  public boolean isValid() {
    final StaffPerson assignedStaffWorker =
        validatedStaffPerson(referral.getPrimaryContactStaffPersonId());
    return (assignedStaffWorker.getCountyCode().equals(referral.getCountySpecificCode()));
  }

  private StaffPerson validatedStaffPerson(String staffPersonId) {
    if (staffPersonId == null) {
      throw new ServiceException("Assigned Staff Person Id is mandatory");
    } else {
      return staffPersonDao.find(staffPersonId);
    }
  }
}
