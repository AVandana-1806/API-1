package gov.ca.cwds.data.access.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipDTO;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import org.junit.Before;
import org.junit.Test;

public class ClientDtoBuilderTest {
  @Before
  public void setup(){
  }

  @Test
  public void shouldBuildObject(){
    ClientRelationshipDTO relationshipDTO = new ClientRelationshipDtoBuilder().build();
    assertNotNull(relationshipDTO);
  }

  @Test
  public void shouldBuildObjectFromScreeningRelationship(){
    ScreeningRelationship relationship = new ScreeningRelationship();
    relationship.setId("id");
    relationship.setRelationshipType(1);
    relationship.setClientId("clientId");
    relationship.setRelativeId("relativeId");
    ClientRelationshipDTO relationshipDTO = new ClientRelationshipDtoBuilder(relationship).build();
    assertEquals(relationship.getId(), relationshipDTO.getIdentifier());
    assertEquals(relationship.getClientId(), relationshipDTO.getPrimaryClient().getId());
    assertEquals(relationship.getRelativeId(), relationshipDTO.getSecondaryClient().getId());
    assertEquals(relationship.getRelationshipType(), relationshipDTO.getType());
  }
}