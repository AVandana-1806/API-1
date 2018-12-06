package gov.ca.cwds.rest.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.services.ParticipantService;
import io.dropwizard.testing.junit.ResourceTestRule;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class ParticipantResourceTest {

  private ParticipantResource participantResource;
  private static  ParticipantService participantService = mock(ParticipantService.class);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new ParticipantResource(participantService))
      .build();

  @Before
  public void setup(){
    participantResource = new ParticipantResource(participantService);

  }
  @Test
  public void shouldInvokeServiceToFindClients(){
    String clientId = "abc";
    participantResource.get(clientId);
    verify(participantService).findByLegacyId(clientId);
  }

  @Test
  public void shouldReturnAParticipantAsAResponse(){
    ParticipantIntakeApi participant = new ParticipantIntakeApi();
    String clientId = "abc";
    when(participantService.findByLegacyId(clientId)).thenReturn(participant);
    Response participantResponse = participantResource.get(clientId);
    assertEquals(participant, participantResponse.getEntity());
  }

  @Test
  public void testResourceIsCalledWithApi(){
    ParticipantIntakeApi participant = new ParticipantIntakeApi();
    String clientId = "abc";
    when(participantService.findByLegacyId(clientId)).thenReturn(participant);
    Response participantResponse = resources.client().target("/participants/asdf").request().get();
    assertNotNull(participantResponse);
    verify(participantService).findByLegacyId("asdf");
  }
}