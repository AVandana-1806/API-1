package gov.ca.cwds.data.ns;

import java.util.List;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.inject.NsSessionFactory;
import javax.persistence.NoResultException;
import org.hibernate.query.Query;

public class RelationshipDao extends CrudsDaoImpl<Relationship> {

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
    Relationship result;
    try {
      final Query<Relationship> query = this.getSessionFactory().getCurrentSession()
          .getNamedQuery(Relationship.FIND_RELATIONSHIPS_BY_LEGACY_ID)
          .setParameter("legacyId", legacyId);
       result = query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
    return  result;
  }
}
