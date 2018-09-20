package gov.ca.cwds.rest.resources.screening.participant;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.ParticipantIntakeApiResourceBuilder;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.resources.parameter.ParticipantResourceParameters;

/**
 * @author CWDS API Team
 *
 */
public class ParticipantResourceTest {

  ParticipantResource participantResource;
  TypedResourceDelegate<ParticipantResourceParameters, ParticipantIntakeApi> typedResourceDelegate;
  ParticipantIntakeApi participantIntakeApi;

  /**
   * 
   */
  @SuppressWarnings("unchecked")
  @Before
  public void setup() {
    typedResourceDelegate = mock(TypedResourceDelegate.class);
    participantResource = new ParticipantResource();
    participantResource.setResourceDelegate(typedResourceDelegate);
  }

  /**
   * 
   */
  @Test
  public void whenCreateIsInvoledThenWeShouldCallService() {
    participantIntakeApi = new ParticipantIntakeApiResourceBuilder().build();
    participantResource.create("12", participantIntakeApi);
    verify(typedResourceDelegate).create(participantIntakeApi);
  }

}
