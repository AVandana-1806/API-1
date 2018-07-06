package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.inject.NsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class RelationshipDao  extends CrudsDaoImpl<Relationship> {

  @Inject
  public RelationshipDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<Relationship> getRelationshipsByScreeningId(String screeningId) {
    final Query<Relationship> query = this.getSessionFactory().getCurrentSession()
            .getNamedQuery(Relationship.FIND_RELATIONSHIPS_BY_SCREENING_ID)
            .setParameter("screeningId", screeningId);
    return query.getResultList();
  }

  public Relationship getByLegacyId(String legacyId){
    final Query<Relationship> query = this.getSessionFactory().getCurrentSession()
            .getNamedQuery(Relationship.FIND_RELATIONSHIPS_BY_LEGACY_ID)
            .setParameter("legacyId", legacyId);
    return query.getSingleResult();
  }
}
