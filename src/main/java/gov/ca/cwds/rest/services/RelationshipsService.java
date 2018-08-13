package gov.ca.cwds.rest.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.persistence.cms.RelationshipWrapper;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipList;
import gov.ca.cwds.rest.services.auth.AuthorizationService;

/**
 * Business layer object to work on {@link Relationship}.
 * 
 * @author CWDS API Team
 */
public class RelationshipsService implements TypedCrudsService<String, Relationship, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipsService.class);

  ClientRelationshipDao relationshipDao;
  Genealogist genealogist;
  AuthorizationService authCheckService;

  /**
   * Constructor and injecting the beans
   *
   * @param relationshipDao - relationshipDao instance
   * @param genealogist - genealogist instance
   * @param authCheckService - AuthorizationService instance
   */
  @Inject
  public RelationshipsService(ClientRelationshipDao relationshipDao, Genealogist genealogist,
      AuthorizationService authCheckService) {
    this.relationshipDao = relationshipDao;
    this.genealogist = genealogist;
    this.authCheckService = authCheckService;
  }

  @Override
  public Response find(String id) {
    List<RelationshipWrapper> relations = new ArrayList<RelationshipWrapper>();
    if (authorized(id)) {
      relations = getClientRelationships(id);
    }
    return genealogist.buildRelationships(relations, id);
  }

  private List<RelationshipWrapper> getClientRelationships(String clientId) {
    return relationshipDao.findRelationshipsByClientId(clientId);
  }

  /**
   * Find the relationships for a list of clients
   * 
   * @param clientIds - clientIds
   * @return the relationships
   */
//  @SuppressWarnings("unchecked")
//  public Response findForIds(List<String> clientIds) {
//    final Set relationships = new HashSet<>();
//    if (clientIds != null){
//      for (String id : clientIds) {
//        if (authorized(id)) {
//          relationships.add(find(id));
//        }
//      }
//    }
//    return new RelationshipList(relationships);
//  }
  public Response findForIds(List<String> clientIds) {
    final Set<Relationship> relationships = new HashSet<Relationship>();
    if (clientIds != null) {
      
      for (String id : clientIds) {
        if (authorized(id)) {
          List<RelationshipWrapper> relations = getClientRelationships(id);
          relationships.add(genealogist.buildRelationships(relations, id));
        }
      }
    }
    return new RelationshipList(relationships);
  }

  private boolean authorized(String clientId) {
    boolean authorized = true;
    try {
      authCheckService.ensureClientAccessAuthorized(clientId);
    } catch (AuthorizationException e) {
      LOGGER.warn("User tried to access unathorized client id: {}",
          clientId.replaceAll("[\r\n]", ""));
      authorized = false;
    }
    return authorized;
  }

  @Override
  public Response delete(String id) {
    throw new NotImplementedException("Delete is not implemented");
  }

  @Override
  public Response create(Relationship relationship) {
    throw new NotImplementedException("Create is not implemented");
  }

  @Override
  public Response update(String id, Relationship relationship) {
    throw new NotImplementedException("Update is not implemented");
  }
}
