package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.referentialintegrity.RICrossReport;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class CrossReportServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private CrossReportService crossReportService;
  private CrossReportDao crossReportDao;
  private RICrossReport riCrossReport;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    crossReportDao = mock(CrossReportDao.class);
    crossReportService = new CrossReportService(crossReportDao, riCrossReport);
  }

  // find test
  // TODO: Story #136701343: Tech debt: exception handling in service layer.
  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsCorrectCrossReportWhenFound() throws Exception {
    CrossReport expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/CrossReport/valid.json"), CrossReport.class);

    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport(expected.getThirdId(), expected, "0X5",
            new Date());
    when(crossReportDao.find(eq(expected.getThirdId()))).thenReturn(crossReport);
    CrossReport found = crossReportService.find("1234ABC123");
    assertThat(found, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = crossReportService.find("0X51234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @SuppressWarnings("javadoc")
  @Test
  public void deleteDelegatesToCrudsService() {
    crossReportService.delete("ABC2345678");
    verify(crossReportDao, times(1)).delete("ABC2345678");
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deleteReturnsNullWhenNotFount() throws Exception {
    Response found = crossReportService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void crossReportServiceDeleteReturnsNotNull() throws Exception {
    String thirdId = "1234ABC123";
    CrossReport expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/CrossReport/valid.json"), CrossReport.class);
    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport(thirdId, expected, "0XA", new Date());

    when(crossReportDao.delete(thirdId)).thenReturn(crossReport);
    CrossReport found = crossReportService.delete(thirdId);
    assertThat(found, is(expected));
  }

  // update test
  @SuppressWarnings("javadoc")
  @Test
  public void updateReturnsCrossReportResponseOnSuccess() throws Exception {
    CrossReport expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/CrossReport/valid.json"), CrossReport.class);

    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport(expected.getThirdId(), expected, "ABC",
            new Date());

    when(crossReportDao.find("1234ABC123")).thenReturn(crossReport);
    when(crossReportDao.update(any())).thenReturn(crossReport);
    Object retval = crossReportService.update("1234ABC123", expected);
    assertEquals(retval.getClass(), CrossReport.class);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void updateReturnsCorrectCrossReportOnSuccess() throws Exception {
    CrossReport crossReportRequest = MAPPER
        .readValue(fixture("fixtures/domain/legacy/CrossReport/valid.json"), CrossReport.class);

    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport(crossReportRequest.getThirdId(),
            crossReportRequest, "ABC", new Date());

    when(crossReportDao.find("1234ABC123")).thenReturn(crossReport);
    when(crossReportDao.update(any())).thenReturn(crossReport);

    CrossReport expected = new CrossReport(crossReport);
    CrossReport updated = crossReportService.update("1234ABC123", expected);
    assertThat(updated, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void updateThrowsExceptionWhenCrossReportNotFound() throws Exception {

    try {
      CrossReport crossReportRequest = MAPPER
          .readValue(fixture("fixtures/domain/legacy/CrossReport/valid.json"), CrossReport.class);

      when(crossReportDao.update(any())).thenThrow(EntityNotFoundException.class);

      crossReportService.update("ZZZ1234567", crossReportRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test
  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsPostedCrossReportClass() throws Exception {
    CrossReport crossReportDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/CrossReport/valid.json"), CrossReport.class);
    gov.ca.cwds.data.persistence.cms.CrossReport toCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport(crossReportDomain.getThirdId(),
            crossReportDomain, "ABC", new Date());

    CrossReport request = new CrossReport(toCreate);
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(toCreate);

    Response response = crossReportService.create(request);
    assertEquals(response.getClass(), CrossReport.class);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void crossReportServiceCreateThrowsEntityExistsException() throws Exception {
    try {
      CrossReport crossReportRequest = MAPPER
          .readValue(fixture("fixtures/domain/legacy/CrossReport/valid.json"), CrossReport.class);

      when(crossReportDao.create(any())).thenThrow(EntityExistsException.class);

      crossReportService.create(crossReportRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsNonNull() throws Exception {
    CrossReport crossReportDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/CrossReport/valid.json"), CrossReport.class);
    gov.ca.cwds.data.persistence.cms.CrossReport toCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport(crossReportDomain.getThirdId(),
            crossReportDomain, "ABC", new Date());

    CrossReport request = new CrossReport(toCreate);
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(toCreate);

    CrossReport postedCrossReport = crossReportService.create(request);
    assertThat(postedCrossReport, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsCorrectPostedCrossReport() throws Exception {
    CrossReport crossReportDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/CrossReport/valid.json"), CrossReport.class);
    gov.ca.cwds.data.persistence.cms.CrossReport toCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport(crossReportDomain.getThirdId(),
            crossReportDomain, "ABC", new Date());

    CrossReport request = new CrossReport(toCreate);

    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(toCreate);

    CrossReport expected = new CrossReport(toCreate);
    CrossReport returned = crossReportService.create(request);
    assertThat(returned, is(expected));
  }

  /*
   * Test for checking the new thirdId Generated for crossReport
   */
  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsGeneratedThirdId() throws Exception {
    CrossReport crossReportDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/CrossReport/valid.json"), CrossReport.class);
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.CrossReport>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.CrossReport answer(InvocationOnMock invocation)
              throws Throwable {
            gov.ca.cwds.data.persistence.cms.CrossReport report =
                (gov.ca.cwds.data.persistence.cms.CrossReport) invocation.getArguments()[0];
            return report;
          }
        });

    CrossReport returned = crossReportService.create(crossReportDomain);
    Assert.assertNotEquals(returned.getThirdId(), crossReportDomain.getThirdId());
  }
}
