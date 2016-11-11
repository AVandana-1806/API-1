package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.legacy.PostedReferralClient;
import gov.ca.cwds.rest.api.domain.legacy.ReferralClient;
import gov.ca.cwds.rest.jdbi.cms.ReferralClientDao;
import io.dropwizard.jackson.Jackson;

public class ReferralClientServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ReferralClientService referralClientService;
  private ReferralClientDao referralClientDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    referralClientDao = mock(ReferralClientDao.class);

    referralClientService = new ReferralClientService(referralClientDao);

  }

  // find test
  @Test
  public void findThrowsAssertionError() {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      referralClientService.find("referralId=1234567ABC,clientId=ABC1234567");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void findReturnsCorrectReferralClientWhenFound() throws Exception {
    ReferralClient expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);

    gov.ca.cwds.rest.api.persistence.cms.ReferralClient referralClient =
        new gov.ca.cwds.rest.api.persistence.cms.ReferralClient(expected.getReferralId(),
            expected.getClientId(), expected, "ABC");

    when(referralClientDao.find(referralClient.getPrimaryKey())).thenReturn(referralClient);

    ReferralClient found = referralClientService.find(referralClient.getPrimaryKey().toString());

    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    ReferralClient expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);

    gov.ca.cwds.rest.api.persistence.cms.ReferralClient referralClient =
        new gov.ca.cwds.rest.api.persistence.cms.ReferralClient(expected.getReferralId(),
            expected.getClientId(), expected, "ABC");

    Response found = referralClientService.find(referralClient.getPrimaryKey().toString());
    // String message = referralClient.getPrimaryKey().toString();
    // System.out.print(message);
    assertThat(found, is(nullValue()));
  }

  // delete test
  // @Test
  // public void deleteThrowsNotImplementedException() throws Exception {
  // thrown.expect(NotImplementedException.class);
  // referralClientService.delete("1");
  //
  // }

  @Test
  public void deleteThrowsAssersionError() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      referralClientService.delete("referralId=1234567ABC,clientId=ABC1234567");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  // update test
  @Test
  public void updateThrowsAssertionError() throws Exception {
    // TODO: thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      referralClientService.update("referralId=1234567ABC,clientId=ABC1234567", null);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void updateReturnsReferralClientResponseOnSuccess() throws Exception {
    ReferralClient expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);

    gov.ca.cwds.rest.api.persistence.cms.ReferralClient referralClient =
        new gov.ca.cwds.rest.api.persistence.cms.ReferralClient(expected.getReferralId(),
            expected.getClientId(), expected, "ABC");

    when(referralClientDao.find(referralClient.getPrimaryKey().toString()))
        .thenReturn(referralClient);
    when(referralClientDao.update(any())).thenReturn(referralClient);
    Object retval =
        referralClientService.update(referralClient.getPrimaryKey().toString(), expected);
    assertThat(retval.getClass(), is(ReferralClient.class));

  }

  @Test
  public void updateReturnsCorrectReferralClientOnSuccess() throws Exception {
    ReferralClient referralClientRequest = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);

    gov.ca.cwds.rest.api.persistence.cms.ReferralClient referralClient =
        new gov.ca.cwds.rest.api.persistence.cms.ReferralClient(
            referralClientRequest.getReferralId(), referralClientRequest.getClientId(),
            referralClientRequest, "ABC");

    when(referralClientDao.find(referralClient.getPrimaryKey().toString()))
        .thenReturn(referralClient);
    when(referralClientDao.update(any())).thenReturn(referralClient);

    ReferralClient expected = new ReferralClient(referralClient);
    ReferralClient updated =
        referralClientService.update(referralClient.getPrimaryKey().toString(), expected);

    assertThat(updated, is(expected));

  }

  @Test
  public void updateThrowsExceptionWhenReferralClientNotFound() throws Exception {
    // TODO : exception is not thrown from service update method
    //
    // thrown.expect(ServiceException.class);
    // thrown.expectCause(Is.isA(EntityNotFoundException.class));
    // thrown.expectMessage(contains("Expected test to throw"));

    ReferralClient referralClientRequest = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);

    gov.ca.cwds.rest.api.persistence.cms.ReferralClient referralClient =
        new gov.ca.cwds.rest.api.persistence.cms.ReferralClient(
            referralClientRequest.getReferralId(), referralClientRequest.getClientId(),
            referralClientRequest, "ABC");

    when(referralClientDao.find(referralClient.getPrimaryKey().toString()))
        .thenReturn(referralClient);
    when(referralClientDao.update(any())).thenReturn(referralClient);

    ReferralClient updated = referralClientService
        .update("referralId=ZZZZZZZABC,clientId=ABCZZZZZZZ", referralClientRequest);
  }

  @Test
  public void createReturnsPostedReferralClient() throws Exception {
    ReferralClient referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);
    gov.ca.cwds.rest.api.persistence.cms.ReferralClient toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.ReferralClient(
            referralClientDomain.getReferralId(), referralClientDomain.getClientId(),
            referralClientDomain, "last_update");

    ReferralClient request = new ReferralClient(toCreate);

    when(referralClientDao.create(any(gov.ca.cwds.rest.api.persistence.cms.ReferralClient.class)))
        .thenReturn(toCreate);

    Response response = referralClientService.create(request);

    assertThat(response.getClass(), is(PostedReferralClient.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    ReferralClient referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);
    gov.ca.cwds.rest.api.persistence.cms.ReferralClient toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.ReferralClient(
            referralClientDomain.getReferralId(), referralClientDomain.getClientId(),
            referralClientDomain, "last_update");

    ReferralClient request = new ReferralClient(toCreate);

    when(referralClientDao.create(any(gov.ca.cwds.rest.api.persistence.cms.ReferralClient.class)))
        .thenReturn(toCreate);

    PostedReferralClient postedReferralClient = referralClientService.create(request);

    assertThat(postedReferralClient, is(notNullValue()));
  }

  @Test
  public void createReturnsCorrectPostedPerson() throws Exception {
    ReferralClient referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);
    gov.ca.cwds.rest.api.persistence.cms.ReferralClient toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.ReferralClient(
            referralClientDomain.getReferralId(), referralClientDomain.getClientId(),
            referralClientDomain, "last_update");

    ReferralClient request = new ReferralClient(toCreate);

    when(referralClientDao.create(any(gov.ca.cwds.rest.api.persistence.cms.ReferralClient.class)))
        .thenReturn(toCreate);

    PostedReferralClient expected = new PostedReferralClient(toCreate);

    PostedReferralClient returned = referralClientService.create(request);

    assertThat(returned, is(expected));
  }
}
