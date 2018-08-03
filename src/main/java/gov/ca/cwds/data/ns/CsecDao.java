package gov.ca.cwds.data.ns;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.CsecEntity;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * CWDS API Team
 */
public class CsecDao extends BaseDaoImpl<CsecEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public CsecDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param participantId - participantId
   * @return the Csec
   */
  public List<CsecEntity> findByParticipantId(String participantId) {
    final Query<CsecEntity> query =
        this.grabSession().getNamedQuery(constructNamedQueryName("findByParticipantId"))
            .setParameter("participantId", participantId);
    return query.getResultList();
  }
}
