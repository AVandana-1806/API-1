package gov.ca.cwds.data.cms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link Reporter}.
 * 
 * @author CWDS API Team
 */
public class ReporterDao extends BaseDaoImpl<Reporter> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReporterDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * finding reporters based on referral id
   * 
   * @param referralId - referral id
   * @return - list of Reporters
   */
  public Reporter[] findInvestigationReportersByReferralId(String referralId) {
    @SuppressWarnings("unchecked")
    final Query<Reporter> query = this.grabSession()
        .getNamedQuery(
            "gov.ca.cwds.data.persistence.cms.Reporter.findInvestigationReportersByReferralId")
        .setParameter("referralId", referralId);
    return query.list().toArray(new Reporter[0]);
  }

  /**
   * Find Reporters by Referral ID-s
   *
   * @param referralIds Set of Referral id-s
   * @return map where key is a Referral id and value is a the Reporter of the Referral
   */
  public Map<String, Reporter> findByReferralIds(Collection<String> referralIds) {
    if (referralIds == null || referralIds.isEmpty()) {
      return new HashMap<>();
    }
    @SuppressWarnings("unchecked")
    final Query<Reporter> query = this.grabSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.Reporter.findByReferralIds")
        .setParameter("referralIds", referralIds);
    return query.list().stream().collect(Collectors.toMap(Reporter::getReferralId, r -> r));
  }
}
