package gov.ca.cwds.data.ns;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.CrossReportEntity;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * CWDS API Team
 */
public class CrossReportDao extends BaseDaoImpl<CrossReportEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public CrossReportDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param screeningId - screeningId
   * @return the Cross Reports
   */
  @SuppressWarnings("unchecked")
  public List<CrossReportEntity> findByScreeningId(String screeningId) {
    final Query<CrossReportEntity> query =
        this.grabSession().getNamedQuery(constructNamedQueryName("findByScreeningId"))
            .setParameter("screeningId", screeningId);
    return query.getResultList();
  }

}
