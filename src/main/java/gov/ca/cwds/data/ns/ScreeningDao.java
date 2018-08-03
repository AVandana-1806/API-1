package gov.ca.cwds.data.ns;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningWrapper;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Screening DAO
 *
 * @author CWDS API Team
 */
public class ScreeningDao extends BaseDaoImpl<ScreeningEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public ScreeningDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find ScreeningEntity objects by referral id.
   *
   * @param referralId - referral Id
   * @return list of ScreeningEntity object
   */
  public ScreeningEntity[] findScreeningsByReferralId(String referralId) {
    @SuppressWarnings("unchecked")
    final Query<ScreeningEntity> query =
        this.grabSession().getNamedQuery(constructNamedQueryName("findScreeningsByReferralId"))
            .setParameter("referralId", referralId);
    return query.list().toArray(new ScreeningEntity[0]);
  }

  /**
   * Find HOI ScreeningEntity objects by screening id.
   *
   * @param clientIds - Legacy Client Id's
   * @return list of ScreeningEntity objects
   */
  public Set<ScreeningEntity> findScreeningsByClientIds(Collection<String> clientIds) {
    @SuppressWarnings("unchecked")
    final Query<ScreeningEntity> query =
        this.grabSession().getNamedQuery(constructNamedQueryName("findScreeningsByClientIds"))
            .setParameter("clientIds", clientIds);
    return new HashSet<>(query.list());
  }

  /**
   * Find screenings of current user (staff ID).
   * 
   * @param staffId - staff Id
   * @return - array of screeningDashboard object
   */
  public List<ScreeningWrapper> findScreeningsByUserId(String staffId) {
    @SuppressWarnings("unchecked")
    final Query<ScreeningWrapper> query =
        this.grabSession().getNamedQuery(ScreeningWrapper.FIND_BY_USER_ID).setParameter("staffId",
            staffId, StringType.INSTANCE);
    return query.list();
  }

}
