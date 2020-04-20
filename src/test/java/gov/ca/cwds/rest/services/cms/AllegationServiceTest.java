package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegation;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AllegationServiceTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private AllegationService allegationService;
  private AllegationDao allegationDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    allegationDao = mock(AllegationDao.class);
    RIAllegation riAllegation = mock(RIAllegation.class);
    allegationService = new AllegationService(allegationDao, riAllegation);
  }

  // find test
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, expected, "0XA", new Date());

    when(allegationDao.find(id)).thenReturn(allegation);
    Allegation found = allegationService.find(id);
    assertThat(found, is(expected));
  }


  @Test
  public void testFindReturnsNullWhenNotFound() {
    Response found = allegationService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testDeleteDelegatesToCrudsService() {
    allegationService.delete("ABC2345678");
    verify(allegationDao, times(1)).delete("ABC2345678");
  }


  @Test
  public void testDeleteReturnsNullWhenNotFound() {
    Response found = allegationService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void assignmentServiceDeleteReturnsNotNull() throws Exception {
    String id = "AabekZX00F";
    Allegation expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, expected, "0XA", new Date());

    when(allegationDao.delete(id)).thenReturn(allegation);
    Allegation found = allegationService.delete(id);
    assertThat(found, is(expected));
  }

  // update test
  @Test
  public void testUpdateReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);

    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, expected, "ABC", new Date());

    when(allegationDao.find("ABC1234567")).thenReturn(allegation);
    when(allegationDao.update(any())).thenReturn(allegation);

    Object retval = allegationService.update("ABC1234567", expected);
    assertEquals(retval.getClass(), Allegation.class);
  }

  @Test
  public void testUpdateThrowsExceptionWhenNotFound() {
    try {
      Allegation allegationRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);

      when(allegationDao.update(any())).thenThrow(EntityNotFoundException.class);

      allegationService.update("ZZZZZZZZZZ", allegationRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test

  @Test
  public void testCreateReturnsPostedClass() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, allegationDomain, "ABC", new Date());

    Allegation request = new Allegation(toCreate);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    Response response = allegationService.create(request);
    assertEquals(response.getClass(), PostedAllegation.class);
  }

  @Test
  public void allegationServiceServiceCreateThrowsEntityExistsException() {
    try {
      Allegation allegationRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);

      when(allegationDao.create(any())).thenThrow(EntityExistsException.class);
      allegationService.create(allegationRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Test
  public void testCreateReturnsNonNull() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, allegationDomain, "ABC", new Date());

    Allegation request = new Allegation(toCreate);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    PostedAllegation postedAllegation = allegationService.create(request);
    assertThat(postedAllegation, is(notNullValue()));
  }

  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, allegationDomain, "ABC", new Date());

    Allegation request = new Allegation(toCreate);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    PostedAllegation expected = new PostedAllegation(toCreate);
    PostedAllegation returned = allegationService.create(request);
    assertThat(returned, is(expected));
  }


  @Test
  public void testCreateNullIDError() throws Exception {
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation(null, allegationDomain, "ABC", new Date());

    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    thrown.expect(ServiceException.class);
    thrown.expectMessage("Allegation ID cannot be blank");
    new PostedAllegation(toCreate);
  }

  @Test
  public void testCreateBlankIDError() throws Exception {
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("    ", allegationDomain, "ABC",
            new Date());

    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    thrown.expect(ServiceException.class);
    thrown.expectMessage("Allegation ID cannot be blank");
    new PostedAllegation(toCreate);
  }

  /*
   * Test for checking the new Allegation Id generated and lenght is 10
   */
  @Test
  public void createReturnsGeneratedId() throws Exception {
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class))).thenAnswer(
        (Answer<gov.ca.cwds.data.persistence.cms.Allegation>) invocation -> (gov.ca.cwds.data.persistence.cms.Allegation) invocation
            .getArguments()[0]);

    PostedAllegation returned = allegationService.create(allegationDomain);
    assertEquals(10, returned.getId().length());
    PostedAllegation newReturned = allegationService.create(allegationDomain);
    Assert.assertNotEquals(returned.getId(), newReturned.getId());
  }

  @Test(expected = ServiceException.class)
  public void testCreateFailedByR06505() throws IOException {
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    allegationDomain.setVictimClientId(null);

    allegationService.create(allegationDomain);
  }

  @Test
  public void testUpdateFailedByR06505() throws IOException {
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    allegationDomain.setAllegationType(null);

    thrown.expect(ServiceException.class);
    thrown.expectMessage(
        "R-06505: Allegation should have victim and abuse category. ID = OMNnHms0X5");
    allegationService.update("OMNnHms0X5", allegationDomain);
  }

}
