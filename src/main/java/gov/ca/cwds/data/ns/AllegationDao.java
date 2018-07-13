package gov.ca.cwds.data.ns;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.AllegationEntity;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Address DAO
 * 
 * @author Intake Team 4
 */
public class AllegationDao extends CrudsDaoImpl<AllegationEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public AllegationDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<AllegationEntity> findByVictimId(String id) {
    final Query<AllegationEntity> query = this.grabSession()
        .getNamedQuery(AllegationEntity.FIND_BY_VICTIM_ID).setParameter("victimId", id);
    return query.list();
  }

  public List<AllegationEntity> findByPerpetratorId(String id) {
    final Query<AllegationEntity> query = this.grabSession()
        .getNamedQuery(AllegationEntity.FIND_BY_PERPETRATOR_ID).setParameter("perpetratorId", id);
    return query.list();
  }

  public List<AllegationEntity> findByVictimOrPerpetratorId(String id) {
    final Query<AllegationEntity> query = this.grabSession()
        .getNamedQuery(AllegationEntity.FIND_BY_VICTIM_OR_PERPETRATOR_ID).setParameter("id", id);
    return query.list();
  }

  public void deleteByIdList(List<Integer> list) {
    if (list != null) {
      for (Integer id : list) {
        delete(id);
      }
    }
  }

}
