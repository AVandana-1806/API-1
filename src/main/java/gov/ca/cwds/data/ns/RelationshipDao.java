package gov.ca.cwds.data.ns;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.inject.NsSessionFactory;

public class RelationshipDao extends CrudsDaoImpl<Relationship> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipDao.class);

  @Inject
  public RelationshipDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<Relationship> getRelationshipsByScreeningId(String screeningId) {
    final Query<Relationship> query =
        this.grabSession().getNamedQuery(Relationship.FIND_RELATIONSHIPS_BY_SCREENING_ID)
            .setParameter("screeningId", screeningId);
    return query.getResultList();
  }

  @SuppressWarnings("squid:S1166")
  public Relationship getByLegacyId(String legacyId) {
    Relationship result = null;
    try {
      final Query<Relationship> query =
          this.grabSession().getNamedQuery(Relationship.FIND_RELATIONSHIPS_BY_LEGACY_ID)
              .setParameter("legacyId", legacyId);
      result = query.getSingleResult();
    } catch (NoResultException e) {
      LOGGER.info(e.getMessage());
    }
    return result;
  }
}
