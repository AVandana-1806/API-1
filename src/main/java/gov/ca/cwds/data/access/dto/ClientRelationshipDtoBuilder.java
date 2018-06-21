package gov.ca.cwds.data.access.dto;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipDTO;
import gov.ca.cwds.data.persistence.cms.BaseClient;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;

public class ClientRelationshipDtoBuilder {
  private String id;
  private BaseClient client;
  private BaseClient relatedClient;
  private short type;


  public ClientRelationshipDtoBuilder() {
  }

  public ClientRelationshipDtoBuilder(ScreeningRelationship relationship) {
    id = relationship.getId();
    type =(short)relationship.getRelationshipType();
    client = createClient(relationship.getClientId());
    relatedClient = createClient(relationship.getRelativeId());
  }

  public ClientRelationshipDTO build(){
    ClientRelationshipDTO dto = new ClientRelationshipDTO();
    dto.setIdentifier(id);
    dto.setPrimaryClient(client);
    dto.setSecondaryClient(relatedClient);
    dto.setType(type);
    return dto;
  }

  private BaseClient createClient(String clientId){
    BaseClient client = new Client();
    client.setId(clientId);
    return client;
  }
}
