package gov.ca.cwds.rest.api.screeningparticipant;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.FunctionalTestingBuilder;
import gov.ca.cwds.fixture.ParticipantIntakeApiResourceBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.core.Api;

/**
 * @author CWDS API Team
 *
 */
public class ScreeningParticipantPost extends FunctionalTest {
  String resourcePath;
  private FunctionalTestingBuilder functionalTestingBuilder;

  /**
   * 
   */
  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_SCREENINGS + "/{id}" + "/participant");
    functionalTestingBuilder = new FunctionalTestingBuilder();
  }

  /**
   * 
   */
  @Test
  public void particpantCreateFailedToAddSameCountySensitiveClient() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("B2YtETx00T", null, null, LegacyTable.CLIENT.getName(), null);
    ParticipantIntakeApi intakeParticipant = new ParticipantIntakeApiResourceBuilder().setId(null)
        .setScreeningId("277").setLegacyDescriptor(legacyDescriptor).build();
    functionalTestingBuilder
        .processPostRequestWithPathParameter(intakeParticipant, resourcePath, "id", "277", token)
        .then().statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void particpantCreateFailedToAddSameCountySealedClient() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("B0gYFaU057", null, null, LegacyTable.CLIENT.getName(), null);
    ParticipantIntakeApi intakeParticipant = new ParticipantIntakeApiResourceBuilder().setId(null)
        .setScreeningId("277").setLegacyDescriptor(legacyDescriptor).build();
    functionalTestingBuilder
        .processPostRequestWithPathParameter(intakeParticipant, resourcePath, "id", "277", token)
        .then().statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void particpantCreateFailedToAddDifferentCountySensitiveClient() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("TbCDoJB0La", null, null, LegacyTable.CLIENT.getName(), null);
    ParticipantIntakeApi intakeParticipant = new ParticipantIntakeApiResourceBuilder().setId(null)
        .setScreeningId("277").setLegacyDescriptor(legacyDescriptor).build();
    functionalTestingBuilder
        .processPostRequestWithPathParameter(intakeParticipant, resourcePath, "id", "277", token)
        .then().statusCode(403);
  }

  /**
   * 
   */
  @Test
  public void particpantCreateFailedToAddDifferentCountySealedClient() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor("AIwcGUp0Nu", null, null, LegacyTable.CLIENT.getName(), null);
    ParticipantIntakeApi intakeParticipant = new ParticipantIntakeApiResourceBuilder().setId(null)
        .setScreeningId("277").setLegacyDescriptor(legacyDescriptor).build();
    functionalTestingBuilder
        .processPostRequestWithPathParameter(intakeParticipant, resourcePath, "id", "277", token)
        .then().statusCode(403);
  }

}