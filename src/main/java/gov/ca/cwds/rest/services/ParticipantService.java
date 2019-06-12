package gov.ca.cwds.rest.services;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.services.screening.participant.ParticipantTransformer;

public class ParticipantService {
  ParticipantTransformer participantTransformer;

  @Inject
  public ParticipantService(ParticipantTransformer participantTransformer) {
    this.participantTransformer = participantTransformer;
  }

  public ParticipantIntakeApi findByLegacyId(String legacyId) {
    if (StringUtils.isBlank(legacyId)) {
      throw new ServiceException("NULL argument for CREATE participant");
    }
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor();
    legacyDescriptor.setId(legacyId);
    legacyDescriptor.setTableName("CLIENT_T");

    ParticipantIntakeApi queryParticipant = new ParticipantIntakeApi();
    queryParticipant.setLegacyDescriptor(legacyDescriptor);
    return participantTransformer.loadParticipant(queryParticipant);
  }

}
