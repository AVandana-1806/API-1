package gov.ca.cwds.data.ns;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.inject.NsSessionFactory;

public class RelationshipDao extends CrudsDaoImpl<Relationship> {

  @Inject
  public RelationshipDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<Relationship> getRelationshipsByScreeningId(String screeningId) { // NOSONAR
    return new ArrayList<>();
  }
}
