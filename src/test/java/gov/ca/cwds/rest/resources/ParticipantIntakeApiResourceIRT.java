package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_PARTICIPANTS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;

/**
 * CWDS API Team
 */
public class ParticipantIntakeApiResourceIRT extends IntakeBaseTest {

  @Test
  public void testGet() throws Exception {
    String actualJson = getStringResponse(
        doGetCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/25"));
    String expectedResponse = fixture(
        "fixtures/gov/ca/cwds/rest/resources/participant-intake-api-get-response.json");
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testPost() throws Exception {
    String request = fixture(
        "fixtures/gov/ca/cwds/rest/resources/participant-intake-api-post-request.json");
    String actualJson = getStringResponse(
        doPostCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS, request));
    ParticipantIntakeApi participant = objectMapper
        .readValue(actualJson.getBytes(), ParticipantIntakeApi.class);
    String expectedResponse = fixture(
        "fixtures/gov/ca/cwds/rest/resources/participant-intake-api-post-response.json");
    expectedResponse = expectedResponse.replace("${participant_id}", participant.getId());
    expectedResponse = expectedResponse.replace("${address_id_1}",
        participant.getAddresses().stream().filter(address -> "Home".equals(address.getType()))
            .findFirst().orElse(null).getId());
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
    expectedResponse = expectedResponse.replace("${csec_id_1}",
        participant.getCsecs().stream().filter(csec -> "At Risk".equals(csec.getCsecCodeId()))
            .findFirst().orElse(null).getId());
    expectedResponse = expectedResponse.replace("${csec_id_2}", participant.getCsecs().stream()
        .filter(csec -> "Victim Before Foster Care".equals(csec.getCsecCodeId())).findFirst()
        .orElse(null).getId());
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testPut() throws Exception {
    String request = fixture(
        "fixtures/gov/ca/cwds/rest/resources/participant-intake-api-put-request.json");
    String actualJson = getStringResponse(
        doPutCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/25", request));
    ParticipantIntakeApi participant = objectMapper
        .readValue(actualJson.getBytes(), ParticipantIntakeApi.class);
    String expectedResponse = fixture(
        "fixtures/gov/ca/cwds/rest/resources/participant-intake-api-put-response.json");
    expectedResponse = expectedResponse
        .replace("${address_id}", participant.getAddresses().iterator().next().getId());
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testPostUpdateDeleteCycle() throws Exception {
    ParticipantIntakeApi participant = objectMapper.readValue(
        fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-post-request.json")
            .getBytes(), ParticipantIntakeApi.class);

    String postRequest = objectMapper.writeValueAsString(participant);
    String postedJson = getStringResponse(
        doPostCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS, postRequest));

    ParticipantIntakeApi postedParticipant = objectMapper
        .readValue(postedJson.getBytes(), ParticipantIntakeApi.class);
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
    ParticipantIntakeApi updatedParticipant = objectMapper
        .readValue(updatedJson.getBytes(), ParticipantIntakeApi.class);

    assertNotEquals(homeAddressId, updatedParticipant.getAddresses().stream()
        .filter(address -> "Home".equals(address.getType())).findFirst().orElse(null).getId());
    assertEquals(dayCareAddressId, updatedParticipant.getAddresses().stream()
        .filter(address -> "Day Care".equals(address.getType())).findFirst().orElse(null).getId());

    assertNotEquals(cellPhoneNumberId, updatedParticipant.getPhoneNumbers().stream()
        .filter(phoneNumber -> "Cell".equals(phoneNumber.getType())).findFirst().orElse(null)
        .getId());
    assertEquals(homePhoneNumberId, updatedParticipant.getPhoneNumbers().stream()
        .filter(phoneNumber -> "Home".equals(phoneNumber.getType())).findFirst().orElse(null)
        .getId());

    assertNotEquals(atRiskCsecId, updatedParticipant.getCsecs().stream()
        .filter(csec -> "At Risk".equals(csec.getCsecCodeId())).findFirst().orElse(null).getId());
    assertEquals(victimBeforeFosterCareCsecId, updatedParticipant.getCsecs().stream()
        .filter(csec -> "Victim Before Foster Care".equals(csec.getCsecCodeId())).findFirst()
        .orElse(null).getId());

    String deleteResponse = doDeleteCall(
        RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/" + postedParticipant.getId());
    assertEquals("", deleteResponse);

    String getResponse = getStringResponse(doGetCall(
        RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/" + postedParticipant.getId()));
    assertEquals("", getResponse);
  }
}
