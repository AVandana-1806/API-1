package gov.ca.cwds.rest.services;

import static junit.framework.TestCase.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.services.screening.participant.ParticipantTransformer;
import org.junit.Before;
import org.junit.Test;

public class ParticipantServiceTest {
  String legacyId = "123ABC";
  ParticipantTransformer transformer;
  ParticipantService service;

  @Before
  public void setup(){
    transformer = mock(ParticipantTransformer.class);
    service = new ParticipantService(transformer);
  }

  @Test(expected = ServiceException.class)
  public void shouldThrowExceptionIfIdIsNull(){
    service.findByLegacyId(null);
  }

  @Test(expected = ServiceException.class)
  public void shouldThrowExceptionIfIdIsBlank(){
    service.findByLegacyId("");
  }

  @Test
  public void shouldReturnParticipants(){
    ParticipantIntakeApi participantResponse = service.findByLegacyId(legacyId);
    verify(transformer).loadParticipant(any(ParticipantIntakeApi.class));
  }

  @Test
  public void shouldReturnNullIfTransformerReturnsNull(){
    when(transformer.prepareParticipantObject(any(ParticipantIntakeApi.class))).thenReturn(null);
    ParticipantIntakeApi participantResponse = service.findByLegacyId(legacyId);
    assertNull(participantResponse);
  }
}