package gov.ca.cwds.data.access.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipDTO;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.services.ClientParticipants;

public class ClientRelationshipDtoBuilderTest {
  private ClientRelationshipDtoBuilder builder;
  private ScreeningRelationship relationship;
  private ClientParticipants participants;

  private Long clientId = 6L;
  private String clientLegacyId = "CLIENTID";
  private Long relativeId = 8L;
  private String relativeLegacyId = "RELATIVEID";

  @Before
  public void setup() {
    relationship = new ScreeningRelationship();
    relationship.setId("ABC");
    relationship.setClientId(clientId.toString());
    relationship.setRelativeId(relativeId.toString());
    relationship.setRelationshipType(120);
    relationship.setStartDate(DomainChef.uncookDateString("2010-01-19"));
    relationship.setEndDate(DomainChef.uncookDateString("2015-05-11"));


    ParticipantResourceBuilder victimBuilder = new ParticipantResourceBuilder();
    Participant victim =
        victimBuilder.setId(clientId).setLegacyId(clientLegacyId).createParticipant();
    victim.getLegacyDescriptor().setId(clientLegacyId);

    ParticipantResourceBuilder perpBuilder = new ParticipantResourceBuilder();
    Participant perp =
        perpBuilder.setId(relativeId).setLegacyId(relativeLegacyId).createParticipant();
    perp.getLegacyDescriptor().setId(relativeLegacyId);

    ParticipantResourceBuilder friendBuilder = new ParticipantResourceBuilder();
    Participant friend = friendBuilder.setId(333).createParticipant();

    participants = new ClientParticipants();
    participants.addParticipant(victim);
    participants.addParticipant(perp);
    participants.addParticipant(friend);
  }

  @Test
  public void shouldBuildClientRelationShipDtoFromParticipantsAndRelationship() {
    builder = new ClientRelationshipDtoBuilder(relationship, participants);
    ClientRelationshipDTO relationsDto = builder.build();
    assertEquals(relationship.getId(), relationsDto.getIdentifier());
    assertEquals(relationship.getRelationshipType(), relationsDto.getType());
    assertEquals(clientLegacyId, relationsDto.getPrimaryClient().getId());
    assertEquals(relativeLegacyId, relationsDto.getSecondaryClient().getId());
    assertEquals(relationship.getStartDate(), java.sql.Date.valueOf(relationsDto.getStartDate()));
    assertEquals(relationship.getEndDate(), java.sql.Date.valueOf(relationsDto.getEndDate()));

  }

  @Test
  public void doesNotBuildCompleteBaseClient() {
    builder = new ClientRelationshipDtoBuilder(relationship, participants);
    ClientRelationshipDTO relationsDto = builder.build();
    assertEquals("", relationsDto.getPrimaryClient().getAdjudicatedDelinquentIndicator());
    assertEquals("", relationsDto.getPrimaryClient().getAdoptionStatusCode());
    assertEquals("", relationsDto.getPrimaryClient().getAlienRegistrationNumber());
    assertEquals("", relationsDto.getPrimaryClient().getBirthCity());
    assertNull(relationsDto.getPrimaryClient().getBirthCountryCodeType());
    assertEquals("", relationsDto.getPrimaryClient().getBirthFacilityName());
    assertNull(relationsDto.getPrimaryClient().getBirthStateCodeType());
    assertEquals("", relationsDto.getPrimaryClient().getBirthplaceVerifiedIndicator());
    assertEquals("", relationsDto.getPrimaryClient().getChildClientIndicatorVar());
    assertEquals("", relationsDto.getPrimaryClient().getClientIndexNumber());
    assertEquals("", relationsDto.getPrimaryClient().getCommentDescription());
    assertEquals("", relationsDto.getPrimaryClient().getCommonFirstName());
    assertEquals("", relationsDto.getPrimaryClient().getCommonLastName());
    assertEquals("", relationsDto.getPrimaryClient().getCommonMiddleName());
    assertNull(relationsDto.getPrimaryClient().getConfidentialityActionDate());
    assertEquals("", relationsDto.getPrimaryClient().getConfidentialityInEffectIndicator());
    // etc
  }

  @Test(expected = NullPointerException.class)
  public void shouldHandleNullRelationship() {
    builder = new ClientRelationshipDtoBuilder(null, participants);
    builder.build();
  }

  @Test(expected = NullPointerException.class)
  public void shouldHandleNullClientParticipant() {
    builder = new ClientRelationshipDtoBuilder(null, participants);
    builder.build();
  }
}
