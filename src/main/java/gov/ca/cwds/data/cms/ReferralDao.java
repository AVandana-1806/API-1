package gov.ca.cwds.data.cms;

import static gov.ca.cwds.data.persistence.cms.Referral.FIND_REFERRALS_WITH_REPORTERS_BY_IDS;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link Referral}.
 * 
 * @author CWDS API Team
 */
public class ReferralDao extends CrudsDaoImpl<Referral> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReferralDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find Referrals by id-s with Reporters pre-loaded
   *
   * @param ids Set of Referral id's
   * @return map where key is a Referral id and value is a Referral itself
   */
  public Map<String, Referral> findReferralsWithReportersByIds(Collection<String> ids) {
    if (ids == null || ids.isEmpty()) {
      return new HashMap<>();
    }
    @SuppressWarnings("unchecked")
    final Query<Referral> query = this.grabSession()
        .getNamedQuery(FIND_REFERRALS_WITH_REPORTERS_BY_IDS).setParameter("ids", ids);
    return query.list().stream().collect(Collectors.toMap(Referral::getId, r -> r));
  }
}
