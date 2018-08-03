package gov.ca.cwds.data.ns;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.GovernmentAgencyEntity;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * CWDS API Team
 */
public class AgencyDao extends BaseDaoImpl<GovernmentAgencyEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public AgencyDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param crossReportId - crossReportId
   * @return the Government Agencies
   */
  @SuppressWarnings("unchecked")
  public List<GovernmentAgencyEntity> findByCrossReportId(String crossReportId) {
    final Query<GovernmentAgencyEntity> query =
        this.grabSession().getNamedQuery(constructNamedQueryName("findByCrossReportId"))
            .setParameter("crossReportId", crossReportId);
    return query.getResultList();
  }

}
