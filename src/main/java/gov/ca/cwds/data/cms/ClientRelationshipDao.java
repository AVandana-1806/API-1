package gov.ca.cwds.data.cms;

import static gov.ca.cwds.data.persistence.cms.ClientRelationship.FIND_CLIENT_RELATIONSHIPS_BY_PRIMARY_CLIENT_IDS;
import static gov.ca.cwds.data.persistence.cms.ClientRelationship.FIND_CLIENT_RELATIONSHIPS_BY_SECONDARY_CLIENT_IDS;
import static gov.ca.cwds.data.persistence.cms.ClientRelationship.FIND_CLIENT_RELATIONSHIP_BY_PRIMARY_CLIENT_ID;
import static gov.ca.cwds.data.persistence.cms.ClientRelationship.FIND_CLIENT_RELATIONSHIP_BY_SECONDARY_CLIENT_ID;
import static gov.ca.cwds.data.persistence.cms.RelationshipWrapper.FIND_ALL_RELATED_CLIENTS_BY_CLIENT_ID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.RelationshipWrapper;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ClientRelationship}.
 *
 * @author CWDS API Team
 */
public class ClientRelationshipDao extends BaseDaoImpl<ClientRelationship> {

  /**
   * Constructor
   *
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ClientRelationshipDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find by primary client id
   *
   * @param primaryClientId - clientId
   * @return the client relationship
   */
  public ClientRelationship[] findByPrimaryClientId(String primaryClientId) {
    @SuppressWarnings("unchecked")
    final Query<ClientRelationship> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(FIND_CLIENT_RELATIONSHIP_BY_PRIMARY_CLIENT_ID);
    query.setParameter("primaryClientId", primaryClientId, StringType.INSTANCE);
    return query.list().toArray(new ClientRelationship[0]);
  }

  /**
   * Find by secondary client id
   *
   * @param secondaryClientId - clientId
   * @return the client relationship
   */
  public ClientRelationship[] findBySecondaryClientId(String secondaryClientId) {
    @SuppressWarnings("unchecked")
    final Query<ClientRelationship> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(FIND_CLIENT_RELATIONSHIP_BY_SECONDARY_CLIENT_ID);
    query.setParameter("secondaryClientId", secondaryClientId, StringType.INSTANCE);
    return query.list().toArray(new ClientRelationship[0]);
  }

  /**
   * @param clientIds legacy client ID-s
   * @return map where key is a clientId and value is the client primary relationships
   */
  public Map<String, Collection<ClientRelationship>> findByPrimaryClientIds(
      Collection<String> clientIds) {
    if (clientIds == null || clientIds.isEmpty()) {
      return new HashMap<>();
    }
    @SuppressWarnings("unchecked")
    final Query<ClientRelationship> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(FIND_CLIENT_RELATIONSHIPS_BY_PRIMARY_CLIENT_IDS);
    query.setParameter("clientIds", clientIds);
    Map<String, Collection<ClientRelationship>> relationships = new HashMap<>(clientIds.size());
    for (ClientRelationship rel : query.list()) {
      if (!relationships.containsKey(rel.getPrimaryClientId())) {
        relationships.put(rel.getPrimaryClientId(), new ArrayList<>());
      }
      relationships.get(rel.getPrimaryClientId()).add(rel);
    }
    return relationships;
  }

  /**
   * @param clientIds legacy client ID-s
   * @return map where key is a clientId and value is the client secondary relationships
   */
  public Map<String, Collection<ClientRelationship>> findBySecondaryClientIds(
      Collection<String> clientIds) {
    if (clientIds == null || clientIds.isEmpty()) {
      return new HashMap<>();
    }
    @SuppressWarnings("unchecked")
    final Query<ClientRelationship> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(FIND_CLIENT_RELATIONSHIPS_BY_SECONDARY_CLIENT_IDS);
    query.setParameter("clientIds", clientIds);
    Map<String, Collection<ClientRelationship>> relationships = new HashMap<>(clientIds.size());
    for (ClientRelationship rel : query.list()) {
      if (!relationships.containsKey(rel.getSecondaryClientId())) {
        relationships.put(rel.getSecondaryClientId(), new ArrayList<>());
      }
      relationships.get(rel.getSecondaryClientId()).add(rel);
    }
    return relationships;
  }

  /**
   * Find related clients by client Id
   *
   * @param clientId - the client Id
   * @return - the client relationships
   */
  public List<RelationshipWrapper> findRelationshipsByClientId(String clientId) {
    @SuppressWarnings("unchecked")
    final Query<RelationshipWrapper> query = this.getSessionFactory().getCurrentSession()
        .getNamedNativeQuery(FIND_ALL_RELATED_CLIENTS_BY_CLIENT_ID)
        .addEntity(RelationshipWrapper.class)
        .setParameter("clientId", clientId, StringType.INSTANCE);
    return query.list();
  }
}
