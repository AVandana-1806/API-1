package gov.ca.cwds.data.cms;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.StateId;
import gov.ca.cwds.data.persistence.xa.CaresQueryAccelerator;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * CWDS API Team
 */
public class StateIdDao extends CrudsDaoImpl<StateId> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public StateIdDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @SuppressWarnings("unchecked")
  public List<StateId> findAllByClientId(String clientId) {
    final Query<StateId> query = namedQuery(getEntityClass().getName() + ".findByClientId");
    query.setParameter("clientId", clientId);
    CaresQueryAccelerator.readOnlyQuery(query);
    return list(query);
  }

}
