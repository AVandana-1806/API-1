package gov.ca.cwds.data.cms;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link Assignment}.
 * 
 * @author CWDS API Team
 */
public class AssignmentDao extends CrudsDaoImpl<Assignment> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public AssignmentDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param staffPersonId - staffPerson Id
   * @return the caseLoads
   */
  @SuppressWarnings("unchecked")
  public CaseLoad[] findCaseLoads(String staffPersonId) {
    final Query<CaseLoad> query = this.grabSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.Assignment.findCaseLoads")
        .setParameter("fkStaffPerson", staffPersonId);
    return query.list().toArray(new CaseLoad[0]);

  }

  /**
   *
   * @param referralId referral ID
   * @return the assignments
   */
  @SuppressWarnings("unchecked")
  public Set<Assignment> findAssignmentsByReferralId(String referralId) {
    final Query<Assignment> query = this.grabSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.Assignment.findAssignmentsByReferralId")
        .setParameter("referralId", referralId);
    return new HashSet(query.list());
  }
}
