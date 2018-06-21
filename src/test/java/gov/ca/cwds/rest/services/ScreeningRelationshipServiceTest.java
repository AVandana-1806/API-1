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
    relationshipEntity = new Relationship("123", "ClientID", "RelationId", 190, new Date(),
        new Date(), true, null);
    relationship = new ScreeningRelationship("123", "ClientID", "RelationId", 190, true, "U");
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
        savedDate, updatedDate, true, null);

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
    assertEquals(relationshipEntity.getSameHomeStatus(), SameHomeStatus.toValue(relationship.getSameHomeStatus()));
  }

  @Test
  public void shouldUpdateTimeStampsWhenCreateIsCalled() {
    Date beforeTestTime = new Date();
    when(relationshipDao.create(any())).thenReturn(relationshipEntity);
    ScreeningRelationship saved = (ScreeningRelationship) service.create(relationship);
  }

  @Test
  public void shouldReturnNullWhenUpdateIsCalled() {
    assertNull(service.update(serialized, relationship));
  }
}