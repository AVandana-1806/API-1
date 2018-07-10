package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_SCREENINGS;
import static io.dropwizard.testing.FixtureHelpers.fixture;

import java.util.Set;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;
import gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.Screening;

/**
 *
 * CWDS API Team
 */
public class ScreeningIntakeResourceIRT extends IntakeBaseTest {

  @Test
  public void testGet() throws Exception {
    String actualJson = getStringResponse(doGetCall(RESOURCE_INTAKE_SCREENINGS + "/52"));
    String expectedResponse =
        fixture("fixtures/gov/ca/cwds/rest/resources/screening-get-response.json");
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testPost() throws Exception {
    String request = fixture("fixtures/gov/ca/cwds/rest/resources/screening-post-request.json");
    String actualJson = getStringResponse(doPostCall(RESOURCE_INTAKE_SCREENINGS, request));
    Screening screening = objectMapper.readValue(actualJson.getBytes(), Screening.class);

    String expectedResponse =
        fixture("fixtures/gov/ca/cwds/rest/resources/screening-post-response.json");
    expectedResponse = expectedResponse.replace("${screening_id}", screening.getId());

    CrossReportIntake crossReport = screening.getCrossReports().iterator().next();
    expectedResponse = expectedResponse.replace("${cross_report_id}", crossReport.getId());

    Set<GovernmentAgencyIntake> agencies = crossReport.getAgencies();
    for (GovernmentAgencyIntake agency : agencies) {
      if ("DISTRICT_ATTORNEY".equals(agency.getType())) {
        expectedResponse = expectedResponse.replace("${agency1_id}", agency.getId());
      }
      if ("LAW_ENFORCEMENT".equals(agency.getType())) {
        expectedResponse = expectedResponse.replace("${agency2_id}", agency.getId());
      }
    }

    AddressIntakeApi address = screening.getIncidentAddress();
    expectedResponse = expectedResponse.replace("${address_id}", address.getId());

    ParticipantIntakeApi participantIntakeApi =
        screening.getParticipantIntakeApis().iterator().next();
    expectedResponse = expectedResponse.replace("${participant_id}", participantIntakeApi.getId());
    expectedResponse = expectedResponse.replace("${participant_address_id}",
        participantIntakeApi.getAddresses().iterator().next().getId());
    expectedResponse = expectedResponse.replace("${participant_phone_number_id}",
        String.valueOf(participantIntakeApi.getPhoneNumbers().iterator().next().getId()));
    expectedResponse = expectedResponse.replace("${csec_id}",
        String.valueOf(participantIntakeApi.getCsecs().get(0).getId()));

    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testPut() throws Exception {
    String request = fixture("fixtures/gov/ca/cwds/rest/resources/screening-put-request.json");
    String actualJson = getStringResponse(doPutCall(RESOURCE_INTAKE_SCREENINGS + "/52", request));
    String expectedResponse =
        fixture("fixtures/gov/ca/cwds/rest/resources/screening-put-response.json");
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

}
