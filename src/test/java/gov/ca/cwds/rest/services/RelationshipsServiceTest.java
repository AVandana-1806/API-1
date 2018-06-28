package gov.ca.cwds.rest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.shiro.authz.AuthorizationException;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.persistence.cms.RelationshipWrapper;
import gov.ca.cwds.fixture.investigation.RelationshipEntityBuilder;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipList;
import gov.ca.cwds.rest.services.auth.AuthorizationService;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class RelationshipsServiceTest {
  private RelationshipsService service;
  private AuthorizationService authService;
  private ClientRelationshipDao relationshipDao;
  private Genealogist genealogist;
  private String clientId;
  private String clientId2;

  private List<String> clientIds = Arrays.asList(clientId, clientId2);
  private List clientRelationships;

  Relationship client1Relationship;
  Relationship client2Relationship;

  /**
   * 
   */
  @Before
  public void setup() {
    clientId = "1XVZ43D";
    clientId2 = "2ZY52Q";

    clientIds = Arrays.asList(clientId, clientId2);

    RelationshipWrapper relationship = mock(RelationshipWrapper.class);
    clientRelationships = Arrays.asList(relationship);

    relationshipDao = mock(ClientRelationshipDao.class);
    when(relationshipDao.findRelationshipsByClientId(clientId)).thenReturn(clientRelationships);
    when(relationshipDao.findRelationshipsByClientId(clientId2)).thenReturn(clientRelationships);

    authService = mock(AuthorizationService.class);
    genealogist = mock(Genealogist.class);
    service = new RelationshipsService(relationshipDao, genealogist, authService);

    client1Relationship = new RelationshipEntityBuilder().setFirstName("Fred").build();
    client2Relationship = new RelationshipEntityBuilder().setFirstName("Bill").build();
    when(genealogist.buildRelationships(clientRelationships, clientId))
        .thenReturn(client1Relationship);
    when(genealogist.buildRelationships(clientRelationships, clientId2))
        .thenReturn(client2Relationship);
  }

  @Test
  public void shouldSearchForClientsRelatedToClient() {
    service.find(clientId);
    verify(relationshipDao).findRelationshipsByClientId(clientId);
  }

  /**
   * 
   */
  @Test
  public void shouldBuildRelationshipForClient() {
    service.find(clientId);
    verify(genealogist).buildRelationships(clientRelationships, clientId);
  }

  @Test
  public void findShouldReturnTheClientAndHisRelations() {
    Relationship relationship = new Relationship();
    when(genealogist.buildRelationships(clientRelationships, clientId)).thenReturn(relationship);

    Relationship foundRelationship = (Relationship) service.find(clientId);
    assertSame(relationship, foundRelationship);
  }

  @Test
  public void findForIdsShouldReturnEmptyReplationshipsWhenNoClientIdsExist() {
    RelationshipList relationships = (RelationshipList) service.findForIds(null);
    assertTrue(relationships.getRelationship().isEmpty());
  }

  @Test
  public void findForIdsShouldSearchRelationshipsForMultipleClients() {
    service.findForIds(clientIds);
    verify(relationshipDao).findRelationshipsByClientId(clientId);
    verify(relationshipDao).findRelationshipsByClientId(clientId2);

  }

  @Test
  public void findForIdsShouldBuildRelationshipForMultipleClients() {
    service.findForIds(clientIds);
    verify(genealogist).buildRelationships(clientRelationships, clientId);
    verify(genealogist).buildRelationships(clientRelationships, clientId2);
  }

  @Test
  public void findForIdsShouldReturnTheMultipleClientsRelations() {

    RelationshipList foundRelationship = (RelationshipList) service.findForIds(clientIds);
    List relationships = new ArrayList();
    relationships.addAll(foundRelationship.getRelationship());
    assertTrue(relationships.contains(client1Relationship));
    assertTrue(relationships.contains(client2Relationship));
  }

  @Test
  public void givenNotAuthorizedIdsThenRelationshipsShouldNotBeReturned() {
    doThrow(new AuthorizationException()).when(authService)
        .ensureClientAccessAuthorized(anyString());

    RelationshipList foundRelationship = (RelationshipList) service.findForIds(clientIds);
    assertEquals(0, foundRelationship.getRelationship().size());
  }

  @Test(expected = NotImplementedException.class)
  public void shouldNotImplementDelete() {
    service.delete("something");
  }

  @Test(expected = NotImplementedException.class)
  public void shouldNotImplementreate() {
    service.create(new Relationship());
  }

  @Test(expected = NotImplementedException.class)
  public void shouldNotImplementUpdate() {
    service.update("something", new Relationship());
  }
}
