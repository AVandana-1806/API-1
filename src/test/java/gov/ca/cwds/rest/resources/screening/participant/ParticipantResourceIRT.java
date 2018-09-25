package gov.ca.cwds.rest.resources.screening.participant;

import static gov.ca.cwds.rest.core.Api.RESOURCE_PARTICIPANTS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import org.joda.time.DateTime;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;

/**
 * CWDS API Team
 */
public class ParticipantResourceIRT extends IntakeBaseTest {

  @Test
  public void testGet() throws Exception {
    String actualJson =
        getStringResponse(doGetCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/25"));
    String expectedResponse =
        fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-get-response.json");
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testPost() throws Exception {
    String request =
        fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-post-request.json");
    String actualJson = getStringResponse(
        doPostCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS, request));
    ParticipantIntakeApi participant =
        objectMapper.readValue(actualJson.getBytes(), ParticipantIntakeApi.class);
    String expectedResponse =
        fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-post-response.json");
    expectedResponse = populateGeneratedIdentifiers(expectedResponse, participant);
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  private String populateGeneratedIdentifiers(String expectedResponse,
      ParticipantIntakeApi participant) {
    expectedResponse = expectedResponse.replace("${participant_id}", participant.getId());
    expectedResponse =
        expectedResponse.replace("${address_id_1}", participant.getAddresses().stream()
            .filter(address -> "Home".equals(address.getType())).findFirst().orElse(null).getId());
    expectedResponse = expectedResponse.replace("${address_id_2}",
        participant.getAddresses().stream().filter(address -> "Day Care".equals(address.getType()))
            .findFirst().orElse(null).getId());
    expectedResponse = expectedResponse.replace("${phone_number_id_1}",
        participant.getPhoneNumbers().stream()
            .filter(phoneNumber -> "Cell".equals(phoneNumber.getType())).findFirst().orElse(null)
            .getId().toString());
    expectedResponse = expectedResponse.replace("${phone_number_id_2}",
        participant.getPhoneNumbers().stream()
            .filter(phoneNumber -> "Home".equals(phoneNumber.getType())).findFirst().orElse(null)
            .getId().toString());
    expectedResponse = expectedResponse.replace("${csec_id_1}", participant.getCsecs().stream()
        .filter(csec -> "At Risk".equals(csec.getCsecCodeId())).findFirst().orElse(null).getId());
    expectedResponse = expectedResponse.replace("${csec_id_2}",
        participant.getCsecs().stream()
            .filter(csec -> "Victim Before Foster Care".equals(csec.getCsecCodeId())).findFirst()
            .orElse(null).getId());
    return expectedResponse;
  }

  @Test
  public void testPut() throws Exception {
    String request =
        fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-put-request.json");
    String actualJson = getStringResponse(
        doPutCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/25", request));
    ParticipantIntakeApi participant =
        objectMapper.readValue(actualJson.getBytes(), ParticipantIntakeApi.class);
    String expectedResponse =
        fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-put-response.json");
    expectedResponse = expectedResponse.replace("${address_id}",
        participant.getAddresses().iterator().next().getId());
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testPostUpdateDeleteCycle() throws Exception {
    ParticipantIntakeApi participant = objectMapper.readValue(
        fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-post-request.json")
            .getBytes(),
        ParticipantIntakeApi.class);

    String postRequest = objectMapper.writeValueAsString(participant);
    String postedJson = getStringResponse(
        doPostCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS, postRequest));

    ParticipantIntakeApi postedParticipant =
        objectMapper.readValue(postedJson.getBytes(), ParticipantIntakeApi.class);
    AddressIntakeApi homeAddress = postedParticipant.getAddresses().stream()
        .filter(address -> "Home".equals(address.getType())).findFirst().orElse(null);
    String homeAddressId = homeAddress.getId();
    homeAddress.setId(null);

    String dayCareAddressId = postedParticipant.getAddresses().stream()
        .filter(address -> "Day Care".equals(address.getType())).findFirst().orElse(null).getId();

    PhoneNumber cellPhoneNumber = postedParticipant.getPhoneNumbers().stream()
        .filter(phoneNumber -> "Cell".equals(phoneNumber.getType())).findFirst().orElse(null);
    Long cellPhoneNumberId = cellPhoneNumber.getId();
    cellPhoneNumber.setId(null);

    Long homePhoneNumberId = postedParticipant.getPhoneNumbers().stream()
        .filter(phoneNumber -> "Home".equals(phoneNumber.getType())).findFirst().orElse(null)
        .getId();

    Csec atRiskCsec = postedParticipant.getCsecs().stream()
        .filter(csec -> "At Risk".equals(csec.getCsecCodeId())).findFirst().orElse(null);
    String atRiskCsecId = atRiskCsec.getId();
    atRiskCsec.setId(null);

    String victimBeforeFosterCareCsecId = postedParticipant.getCsecs().stream()
        .filter(csec -> "Victim Before Foster Care".equals(csec.getCsecCodeId())).findFirst()
        .orElse(null).getId();

    String updateRequest = objectMapper.writeValueAsString(postedParticipant);
    String updatedJson = getStringResponse(doPutCall(
        RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/" + postedParticipant.getId(),
        updateRequest));
    ParticipantIntakeApi updatedParticipant =
        objectMapper.readValue(updatedJson.getBytes(), ParticipantIntakeApi.class);

    assertNotEquals(homeAddressId, updatedParticipant.getAddresses().stream()
        .filter(address -> "Home".equals(address.getType())).findFirst().orElse(null).getId());
    assertEquals(dayCareAddressId, updatedParticipant.getAddresses().stream()
        .filter(address -> "Day Care".equals(address.getType())).findFirst().orElse(null).getId());

    assertNotEquals(cellPhoneNumberId,
        updatedParticipant.getPhoneNumbers().stream()
            .filter(phoneNumber -> "Cell".equals(phoneNumber.getType())).findFirst().orElse(null)
            .getId());
    assertEquals(homePhoneNumberId,
        updatedParticipant.getPhoneNumbers().stream()
            .filter(phoneNumber -> "Home".equals(phoneNumber.getType())).findFirst().orElse(null)
            .getId());

    assertNotEquals(atRiskCsecId, updatedParticipant.getCsecs().stream()
        .filter(csec -> "At Risk".equals(csec.getCsecCodeId())).findFirst().orElse(null).getId());
    assertEquals(victimBeforeFosterCareCsecId,
        updatedParticipant.getCsecs().stream()
            .filter(csec -> "Victim Before Foster Care".equals(csec.getCsecCodeId())).findFirst()
            .orElse(null).getId());

    String deleteResponse = doDeleteCall(
        RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/" + postedParticipant.getId());
    assertEquals("", deleteResponse);

    String getResponse = getStringResponse(doGetCall(
        RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/" + postedParticipant.getId()));
    assertEquals("", getResponse);
  }

  @Test
  public void testCreateExistingParticipant_sameLegacyId_sameRelatedScreeningId_scriningIdIsNull()
      throws IOException {
    final String participantId = "2035";
    final String screeningId = "2114";

    String existingPartisipantsJson = getStringResponse(
        doGetCall(RESOURCE_SCREENINGS + "/" + screeningId + "/" + RESOURCE_PARTICIPANTS + "/"
            + participantId));

    assertEquals("", existingPartisipantsJson);

    String request = objectMapper.writeValueAsString(
        getDefaultParticipant(getLegacyDescriptor("CLIENT_T", "0000jjj000", "Client")));
    String actualJson = getStringResponse(
        doPostCall(RESOURCE_SCREENINGS + "/" + screeningId + "/participants", request));

    ParticipantIntakeApi existingParticipant = objectMapper
        .readValue(actualJson, ParticipantIntakeApi.class);

    assertNotNull(existingParticipant);
    assertEquals(participantId, existingParticipant.getId());
    assertEquals(screeningId, existingParticipant.getScreeningId());
    assertEquals("Participant", existingParticipant.getLastName());
    assertEquals("Existing", existingParticipant.getFirstName());
    assertEquals("0000jjj000", existingParticipant.getLegacyId());
  }

  private ParticipantIntakeApi getDefaultParticipant(LegacyDescriptor legacyDescriptor) {
    ParticipantIntakeApi participantIntakeApi = new ParticipantIntakeApi();
    participantIntakeApi.setLegacyDescriptor(legacyDescriptor);
    participantIntakeApi.setFirstName("Existing");
    participantIntakeApi.setLastName("Participant");
    participantIntakeApi.setGender("male");
    participantIntakeApi.setSsn("123-45-6789");
    participantIntakeApi.setDateOfBirth(new Date(LocalDate.parse("1999-11-15").toEpochDay()));
    participantIntakeApi.setScreeningId("2114");
    participantIntakeApi.setProbationYouth(Boolean.TRUE);
    return participantIntakeApi;
  }

  private LegacyDescriptor getLegacyDescriptor(String tableName, String legacyId,
      String tableDescription) {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor();
    legacyDescriptor.setId(legacyId);
    legacyDescriptor.setUiId("0522-3101-9120-2000767");
    legacyDescriptor.setLastUpdated(DateTime.now());
    legacyDescriptor.setTableName(tableName);
    legacyDescriptor.setTableDescription("");
    return legacyDescriptor;
  }
}
