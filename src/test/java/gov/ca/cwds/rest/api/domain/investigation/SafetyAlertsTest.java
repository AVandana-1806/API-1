package gov.ca.cwds.rest.api.domain.investigation;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.investigation.SafetyAlertsEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class SafetyAlertsTest {
  private ObjectMapper MAPPER = new ObjectMapper();
  private Validator validator;

  private Set<Short> alerts = new HashSet<>();
  private String alertInformation = "information about the safety alert on this referral";

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testEmptyConstructorSuccess() {
    SafetyAlerts safetyAlerts = new SafetyAlerts();
    assertNotNull(safetyAlerts);
  }

  @Test
  public void testDomainConstructor() {
    SafetyAlerts safetyAlerts = new SafetyAlerts(alerts, alertInformation);
    assertThat(alerts, is(equalTo(safetyAlerts.getAlerts())));
    assertThat(alertInformation, is(equalTo(safetyAlerts.getAlertInformation())));
  }

  @Test
  public void testValidationSuccess() {
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().setAlerts(alerts).build();
    Set<ConstraintViolation<SafetyAlerts>> constraintViolations = validator.validate(safetyAlerts);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testValidationWithEmptyAlertInformation() {
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().setAlertInformation("").build();
    Set<ConstraintViolation<SafetyAlerts>> constraintViolations = validator.validate(safetyAlerts);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldValidateEmptyAlertsSafetyAlerts() {
    final Set<Short> alerts = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().setAlerts(alerts).build();
    Set<ConstraintViolation<SafetyAlerts>> constraintViolations = validator.validate(safetyAlerts);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(SafetyAlerts.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  // @Test
  // @Ignore
  // public void testSerilizedOutput()
  // throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
  // SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
  // final String expected =
  // MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(safetyAlerts);
  // System.out.println(expected);
  // }

  @Test
  public void deserializesFromJSON() throws Exception {
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().setAlerts(alerts).build();
    assertThat(MAPPER.readValue(fixture("fixtures/domain/investigation/safetyAlerts/valid.json"),
        SafetyAlerts.class), is(equalTo(safetyAlerts)));
  }

}
