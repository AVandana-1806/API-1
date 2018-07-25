package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.cms.AssignmentUnitDao;
import gov.ca.cwds.data.cms.CaseLoadDao;
import gov.ca.cwds.data.cms.CwsOfficeDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.AssignmentUnit;
import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.data.persistence.cms.CwsOffice;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralAssignment;
import gov.ca.cwds.rest.business.RuleAction;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * CWDS API Team
 * <p>
 * R - 01054 Prmary Assignment Adding
 * <p>
 * Rule Text<br>
 * When adding a Primary Assignment (to a CASE or REFERRAL) set the
 * CASE/REFERRAL.Government_Entity_Type to the CWS_Office.Government_Entity_Type of the Primary
 * Assignment.
 * <p>
 * Access Logic<br>
 * Set CASE/REFERRAL.Government_Entity_Type = ASSIGNMENT (where .Type_Of_Assignment_Code = 'P')&gt;
 * CASELOAD&gt; ASSIGNMENT_UNIT&gt; CWS_OFFICE.Government_Entity_Type.
 */
public class R01054PrimaryAssignmentAdding implements RuleAction {
  private ReferralDao referralDao;
  private CaseLoadDao caseLoadDao;
  private AssignmentUnitDao assignmentUnitDao;
  private CwsOfficeDao cwsOfficeDao;
  private Assignment assignment;

  /**
   * @param assignment - assignment
   * @param referralDao - referralDao
   * @param caseLoadDao - caseLoadDao
   * @param assignmentUnitDao - assignmentUnitDao
   * @param cwsOfficeDao - cwsOfficeDao
   */
  public R01054PrimaryAssignmentAdding(Assignment assignment, ReferralDao referralDao,
      CaseLoadDao caseLoadDao, AssignmentUnitDao assignmentUnitDao, CwsOfficeDao cwsOfficeDao) {
    this.assignment = assignment;
    this.referralDao = referralDao;
    this.caseLoadDao = caseLoadDao;
    this.assignmentUnitDao = assignmentUnitDao;
    this.cwsOfficeDao = cwsOfficeDao;
  }

  @Override
  public void execute() {
    if ("P".equals(assignment.getTypeOfAssignmentCode())) {
      CaseLoad caseLoad = caseLoadDao.find(assignment.getFkCaseLoad());
      validateOnNull(caseLoad,
          "Cannot find caseLoad for assignment: " + assignment.getPrimaryKey());

      AssignmentUnit assignmentUnit = assignmentUnitDao.find(caseLoad.getFkAssignmentUnit());
      validateOnNull(assignmentUnit,
          "Cannot find assignmentUnit for caseLoad: " + caseLoad.getPrimaryKey());

      CwsOffice cwsOffice = cwsOfficeDao.find(assignmentUnit.getFkCwsOffice());
      validateOnNull(cwsOffice,
          "Cannot find cwsOffice for assignmentUnit: " + assignmentUnit.getPrimaryKey());

      short governmentEntityType = cwsOffice.getGovernmentEntityType();

      String establishedForId = assignment.getEstablishedForId();
      String establishedForCode = assignment.getEstablishedForCode();
      if (ReferralAssignment.FOLDED_KEY_CODE.equals(establishedForCode)) {
        Referral referral = referralDao.find(establishedForId);
        validateOnNull(referral,
            "Cannot find referral for assignment: " + assignment.getPrimaryKey());

        referral.setGovtEntityType(governmentEntityType);
        referralDao.grabSession().merge(referral);
      }
    }
  }

  private void validateOnNull(Object value, String message) {
    if (value == null) {
      throw new ServiceException(message);
    }
  }

}
