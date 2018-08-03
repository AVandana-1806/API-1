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

  @Before
  public void setup() {
    relationshipDao = mock(RelationshipDao.class);
    relationshipMapper = RelationshipMapper.INSTANCE;
    service = new ScreeningRelationshipService(relationshipDao, relationshipMapper);

    Date now = new Date();

    relationshipEntity = new Relationship();
    relationshipEntity.setId("123");
    relationshipEntity.setClientId("ClientID");
    relationshipEntity.setRelativeId("RelationId");
    relationshipEntity.setRelationshipType(190);
    relationshipEntity.setCreatedAt(now);
    relationshipEntity.setUpdatedAt(now);
    relationshipEntity.setAbsentParentIndicator(true);
    relationshipEntity.setLegacyId("1111");

    relationship = new ScreeningRelationship();
    relationship.setId("123");
    relationship.setClientId("ClientID");
    relationship.setRelativeId("RelationId");
    relationship.setRelationshipType(190);
    relationship.setAbsentParentIndicator(true);
    relationship.setSameHomeStatus("U");
    relationship.setStartDate(now);
    relationship.setEndDate(now);
    relationship.setLegacyId("1234567890");
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
    relationshipEntity = new Relationship();
    relationshipEntity.setId(newId);
    relationshipEntity.setClientId(relationship.getClientId());
    relationshipEntity.setRelativeId(relationship.getRelativeId());
    relationshipEntity.setRelationshipType(relationship.getRelationshipType());
    relationshipEntity.setCreatedAt(savedDate);
    relationshipEntity.setUpdatedAt(updatedDate);
    relationshipEntity.setAbsentParentIndicator(true);
    relationshipEntity.setLegacyId("1111");


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
    Date now = new Date();

    Relationship existingEntity = new Relationship();
    existingEntity.setId("123");
    existingEntity.setClientId("ClientID");
    existingEntity.setRelativeId("RelationId");
    existingEntity.setRelationshipType(190);
    existingEntity.setCreatedAt(now);
    existingEntity.setUpdatedAt(now);
    existingEntity.setAbsentParentIndicator(true);
    existingEntity.setLegacyId("1111");

    Relationship updatedEntity = new Relationship();
    updatedEntity.setId("123");
    updatedEntity.setClientId("ClientID");
    updatedEntity.setRelativeId("RelationId");
    updatedEntity.setRelationshipType(191);
    updatedEntity.setCreatedAt(now);
    updatedEntity.setUpdatedAt(now);
    updatedEntity.setAbsentParentIndicator(true);
    updatedEntity.setLegacyId("1111");

    when(relationshipDao.find(any())).thenReturn(existingEntity);
    when(relationshipDao.update(any())).thenReturn(updatedEntity);

    ScreeningRelationship relationshipForUpdate = new ScreeningRelationship();
    relationshipForUpdate.setId("123");
    relationshipForUpdate.setClientId("ClientID");
    relationshipForUpdate.setRelativeId("RelationId");
    relationshipForUpdate.setRelationshipType(191);
    relationshipForUpdate.setAbsentParentIndicator(true);
    relationshipForUpdate.setSameHomeStatus("U");
    relationshipForUpdate.setStartDate(now);
    relationshipForUpdate.setEndDate(now);
    relationshipForUpdate.setLegacyId("1234567890");

    ScreeningRelationship updated = (ScreeningRelationship) service
        .update("123", relationshipForUpdate);

    assertEquals(updatedEntity.getId(), updated.getId());
    assertEquals(updatedEntity.getClientId(), updated.getClientId());
    assertEquals(updatedEntity.getRelativeId(), updated.getRelativeId());
    assertEquals(updatedEntity.getRelationshipType(), updated.getRelationshipType());
    assertEquals(updatedEntity.getCreatedAt(), now);
    assertEquals(updatedEntity.isAbsentParentIndicator(), updated.isAbsentParentIndicator());
    assertEquals(updatedEntity.getSameHomeStatus(),
        SameHomeStatus.toValue(updated.getSameHomeStatus()));
  }
}