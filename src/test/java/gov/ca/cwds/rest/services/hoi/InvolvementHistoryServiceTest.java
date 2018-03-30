package gov.ca.cwds.rest.services.hoi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.fixture.hoi.HOICaseResourceBuilder;
import gov.ca.cwds.fixture.hoi.HOIReferralResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOICaseResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import io.dropwizard.jackson.Jackson;

/***
 *
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class InvolvementHistoryServiceTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private HOICaseService hoiCaseService;
  private HOIReferralService hoiReferralService;
  private HOIScreeningService hoiScreeningService;
  private InvolvementHistoryService involvementHistoryService;
  private HOIRequest hoiRequest;
  private ParticipantDao participantDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    hoiRequest = new HOIRequest();
    hoiRequest.setClientIds(Stream.of("123").collect(Collectors.toSet()));
    hoiCaseService = mock(HOICaseService.class);
    HOICase hoicase = new HOICaseResourceBuilder().createHOICase();
    List<HOICase> hoicases = new ArrayList<>();
    hoicases.add(hoicase);
    HOICaseResponse hoiCaseResponse = new HOICaseResponse();
    hoiCaseResponse.setHoiCases(hoicases);
    when(hoiCaseService.handleFind(any(HOIRequest.class))).thenReturn(hoiCaseResponse);
    when(hoiCaseService.find(any(HOIRequest.class))).thenReturn(hoiCaseResponse);

    hoiReferralService = mock(HOIReferralService.class);
    HOIReferral hoireferral = new HOIReferralResourceBuilder().createHOIReferral();
    List<HOIReferral> hoireferrals = new ArrayList<>();
    hoireferrals.add(hoireferral);
    HOIReferralResponse hoiReferralResponse = new HOIReferralResponse();
    hoiReferralResponse.setHoiReferrals(hoireferrals);
    when(hoiReferralService.handleFind(any(HOIRequest.class))).thenReturn(hoiReferralResponse);

    hoiScreeningService = mock(HOIScreeningService.class);
    HOIScreeningResponse hoiScreeningResponse = new HOIScreeningResponse(new HashSet<>());
    when(hoiScreeningService.handleFind(any(HOIRequest.class))).thenReturn(hoiScreeningResponse);

    involvementHistoryService = new InvolvementHistoryService();
    involvementHistoryService.hoiCaseService = hoiCaseService;
    involvementHistoryService.hoiReferralService = hoiReferralService;
    involvementHistoryService.hoiScreeningService = hoiScreeningService;

    participantDao = mock(ParticipantDao.class);
    when(participantDao.findLegacyIdListByScreeningId(any(String.class)))
        .thenReturn(new HashSet<>());
    involvementHistoryService.participantDao = participantDao;
  }

  // find tests
  @Test
  public void findReturnsHistoryOfInvolvementWhenScreeningHasNoLegacyClientId() throws Exception {
    Response returned = involvementHistoryService.find("1");
    assertThat(returned, is(notNullValue()));
  }

  @Test
  public void findReturnsHistoryOfInvolvementWhenScreeningHasALegacyClientId() throws Exception {
    Set<String> clientIds = Stream.of("123").collect(Collectors.toSet());
    when(participantDao.findLegacyIdListByScreeningId(any(String.class))).thenReturn(clientIds);
    Response returned = involvementHistoryService.find("1");
    assertThat(returned, is(notNullValue()));
  }

  // Test for the method findInvolvementHistoryByClientIds
  @Test
  public void hOiNotReturingForEmptyClientId() throws Exception {
    Set<String> clientIds = new HashSet<>();
    Response returned = involvementHistoryService.findInvolvementHistoryByClientIds(clientIds);
    assertThat(returned, is(notNullValue()));
  }

  @Test
  public void findHOIUsingClientId() throws Exception {
    Set<String> clientIds = new HashSet<>();
    clientIds.add("123");
    Response returned = involvementHistoryService.findInvolvementHistoryByClientIds(clientIds);
    assertThat(returned, is(notNullValue()));
  }

  // delete test
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    involvementHistoryService.delete("string");
  }

  // update test
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    involvementHistoryService.update("string", null);
  }


  // create test
  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    involvementHistoryService.create(null);
  }

  @Test
  public void instantiation() throws Exception {
    InvolvementHistoryService target = new InvolvementHistoryService();
    assertThat(target, notNullValue());
  }

}
