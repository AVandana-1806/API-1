package gov.ca.cwds.rest.services;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.ca.cwds.data.ns.RelationshipDao;
import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.api.domain.enums.SameHomeStatus;
import java.io.Serializable;
import java.util.Date;

import gov.ca.cwds.rest.services.investigation.ClientsRelationshipsService;
import gov.ca.cwds.rest.services.mapper.RelationshipMapper;
import org.junit.Before;
import org.junit.Test;

public class ScreeningRelationshipServiceTest {

  private ScreeningRelationshipService service;
  private ScreeningRelationship relationship;
  private Relationship relationshipEntity;
  private Serializable serialized;
  private RelationshipDao relationshipDao;
  private RelationshipMapper relationshipMapper;
  private ClientsRelationshipsService clientsRelationshipsService;

  @Before
  public void setup() {
    relationshipDao = mock(RelationshipDao.class);
    relationshipMapper = RelationshipMapper.INSTANCE;
    clientsRelationshipsService = mock(ClientsRelationshipsService.class);
    service = new ScreeningRelationshipService(relationshipDao, relationshipMapper, clientsRelationshipsService);
    relationshipEntity = new Relationship("123", "ClientID", "RelationId", 190, new Date(),
        new Date(), true, null, "1111", null, null);
    relationship = new ScreeningRelationship("123", "ClientID", "RelationId", 190, true, "U", new Date(), new Date(), "1234567890");
  }

  @Test
  public void shouldReturnScreeningRelationshipWhenFindIsCalled() {
    when(relationshipDao.find(any())).thenReturn(relationshipEntity);
    String id = "123";
    assertEquals(service.find(id), relationship);
    verify(relationshipDao).find(isA(String.class));
  }

  @Test
  public void shouldReturnNullWhenNotFound() {
    when(relationshipDao.find(any())).thenReturn(null);
    String id = "123";
    assertNull(service.find(id));
    verify(relationshipDao).find(isA(String.class));
  }

  @Test
  public void shouldReturnNullWhenDeleteIsCalled() {
    assertNull(service.delete(serialized));
  }

  @Test
  public void shouldSaveRelationshipWhenCreateIsCalled() {
    when(relationshipDao.create(any())).thenReturn(relationshipEntity);
    service.create(relationship);
    verify(relationshipDao).create(isA(Relationship.class));
  }

  @Test
  public void shouldReturnSavedRelationWhenCreateIsCalled() {
    String newId = "123";
    Date savedDate = new Date();
    Date updatedDate = new Date();
    relationshipEntity = new Relationship(newId, relationship.getClientId(),
        relationship.getRelativeId(), relationship.getRelationshipType(),
        savedDate, updatedDate, true, null, "1111", null, null);

    when(relationshipDao.create(any())).thenReturn(relationshipEntity);
    ScreeningRelationship saved = (ScreeningRelationship) service.create(relationship);
    assertEquals(newId, saved.getId());
    assertEquals(relationshipEntity.getId(), saved.getId());
    assertEquals(relationshipEntity.getClientId(), saved.getClientId());
    assertEquals(relationshipEntity.getRelativeId(), saved.getRelativeId());
    assertEquals(relationshipEntity.getRelationshipType(), saved.getRelationshipType());
    assertEquals(relationshipEntity.getCreatedAt(), savedDate);
    assertEquals(relationshipEntity.getUpdatedAt(), updatedDate);
    assertEquals(relationshipEntity.isAbsentParentIndicator(), saved.isAbsentParentIndicator());
    assertEquals(relationshipEntity.getSameHomeStatus(),
        SameHomeStatus.toValue(relationship.getSameHomeStatus()));
  }

  @Test
  public void shouldUpdateTimeStampsWhenCreateIsCalled() {
    Date beforeTestTime = new Date();
    when(relationshipDao.create(any())).thenReturn(relationshipEntity);
    ScreeningRelationship saved = (ScreeningRelationship) service.create(relationship);
  }
  
  @Test
  public void update() throws Exception {
    Date creationDate = new Date();
    Relationship existingEntity = new Relationship("123", "ClientID", "RelationId", 190,
        creationDate,
        new Date(), true, null, "1111", null, null);
    Relationship updatedEntity = new Relationship("123", "ClientID", "RelationId", 191,
        creationDate,
        new Date(), true, null, "1111", null ,null);

    when(relationshipDao.find(any())).thenReturn(existingEntity);
    when(relationshipDao.update(any())).thenReturn(updatedEntity);

    ScreeningRelationship relationshipForUpdate = new ScreeningRelationship("123", "ClientID",
        "RelationId", 191, true, "U", new Date(), new Date(), "1234567890");
    ScreeningRelationship updated = (ScreeningRelationship) service
        .update("123", relationshipForUpdate);

    assertEquals(updatedEntity.getId(), updated.getId());
    assertEquals(updatedEntity.getClientId(), updated.getClientId());
    assertEquals(updatedEntity.getRelativeId(), updated.getRelativeId());
    assertEquals(updatedEntity.getRelationshipType(), updated.getRelationshipType());
    assertEquals(updatedEntity.getCreatedAt(), creationDate);
    assertEquals(updatedEntity.isAbsentParentIndicator(), updated.isAbsentParentIndicator());
    assertEquals(updatedEntity.getSameHomeStatus(),
        SameHomeStatus.toValue(updated.getSameHomeStatus()));
  }
}