package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.investigation.SafetyAlertsEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.SafetyAlerts;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import io.dropwizard.jackson.Jackson;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class SafetyAlertsServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Set<Short> alerts = new HashSet<>();

  private SafetyAlertsService safetyAlertsService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    safetyAlertsService = new SafetyAlertsService();
  }

  // find test
  @Test
  public void findReturnsExpectedSafetyAlerts() throws Exception {
    SafetyAlerts expected = new SafetyAlertsEntityBuilder().setAlerts(alerts).build();
    Response returned = safetyAlertsService.find("1234567ABC");
    assertThat(returned, is(expected));
  }

  // delete test
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    safetyAlertsService.delete("1234567ABC");
  }

  // update test
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    SafetyAlerts request = new SafetyAlertsEntityBuilder().build();
    thrown.expect(NotImplementedException.class);
    safetyAlertsService.update("1234567ABC", request);
  }

  // create test
  @Test
  public void createReturnsExpectedSafetyAlerts() throws Exception {
    SafetyAlerts request = new SafetyAlertsEntityBuilder().setAlerts(alerts).build();
    Response returned = safetyAlertsService.create(request);
    assertThat(returned, is(request));
  }

}
