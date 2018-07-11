package gov.ca.cwds.data.access.dto;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipDTO;
import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown;
import gov.ca.cwds.data.persistence.cms.BaseClient;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.services.ClientParticipants;

public class ClientRelationshipDtoBuilder {
  private String id;
  private String clientId;
  private String relatedClientId;
  private short type;
  private ClientParticipants participants;
  private boolean absentParentIndicator;
  private String sameHomeStatus;

  public ClientRelationshipDtoBuilder(ScreeningRelationship relationship, ClientParticipants participants) {
    if (participants == null){
      throw new NullPointerException("Client Participants can not be null");
    }
    id = relationship.getId();
    type =(short)relationship.getRelationshipType();
    clientId = relationship.getClientId();
    relatedClientId = relationship.getRelativeId();
    this.participants = participants;
    this.absentParentIndicator = relationship.isAbsentParentIndicator();
    this.sameHomeStatus = relationship.getSameHomeStatus();
  }

  public ClientRelationshipDTO build(){
    ClientRelationshipDTO dto = new ClientRelationshipDTO();
    dto.setIdentifier(id);
    dto.setPrimaryClient(createClient(clientId));
    dto.setSecondaryClient(createClient(relatedClientId));
    dto.setAbsentParentIndicator(absentParentIndicator);
    dto.setSameHomeStatus(YesNoUnknown.fromCode(sameHomeStatus));
    dto.setType(type);
    return dto;
  }

  private BaseClient createClient(String clientId){
    BaseClient baseClient = new Client();
    baseClient.setId(findLegacyId(clientId));
    return baseClient;
  }

  private String findLegacyId(String id){
    for( Participant participant : participants.getParticipants()){
      Long participantId = participant.getId();
      if(participantId.toString().equals(id)){
        return participant.getLegacyDescriptor().getId();
      }
    }
    return null;
  }
}
