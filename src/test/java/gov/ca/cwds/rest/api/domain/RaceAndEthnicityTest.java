package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.investigation.RaceAndEthnicityEntityBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class RaceAndEthnicityTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Validator validator;

  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  /**
   * 
   */
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private List<Short> raceCode = new ArrayList<>();
  private String unableToDetermineCode = "A";
  private List<Short> hispanicCode = new ArrayList<>();
  private String hispanicOriginCode = "X";
  private String hispanicUnableToDetermineCode = "A";

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testSucessForValidRaceAndEthnicity() throws Exception {
    RaceAndEthnicity toValidate = new RaceAndEthnicityEntityBuilder().build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testFailureWhenHispanicUnableToDetermineCodeIgnoreCase() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicUnableToDetermineCode("a").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be one of [A, I, K, ]", constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testFailureWhenHispanicUnableToDetermineCodeInvalid() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicUnableToDetermineCode("Z").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be one of [A, I, K, ]", constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testSuccessWhenHispanicUnableToDetermineCodeIsA() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicUnableToDetermineCode("A").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenHispanicUnableToDetermineCodeIsI() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicUnableToDetermineCode("I").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenHispanicUnableToDetermineCodeIsK() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicUnableToDetermineCode("K").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenHispanicUnableToDetermineCodeIsEmpty() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicUnableToDetermineCode("").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenHispanicUnableToDetermineCodeIsNull() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicUnableToDetermineCode(null).build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testFailWhenRaceCodeInvalid() throws Exception {
    List<Short> raceCode = Arrays.asList((short) 123);
    RaceAndEthnicity toValidate = new RaceAndEthnicityEntityBuilder().setRaceCode(raceCode).build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be a valid system code for category ETHNCTYC",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testSuccessWhenRaceCodeValid() throws Exception {
    List<Short> raceCode = Arrays.asList((short) 841);
    RaceAndEthnicity toValidate = new RaceAndEthnicityEntityBuilder().setRaceCode(raceCode).build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testFailureToSetUnableDetermineCode() throws Exception {
    List<Short> raceCode = Arrays.asList((short) 6351);
    RaceAndEthnicity toValidate = new RaceAndEthnicityEntityBuilder().setRaceCode(raceCode)
        .setUnableToDetermineCode("").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    System.out.println(constraintViolations.iterator().next().getMessage());
    assertEquals(1, constraintViolations.size());
    assertEquals("Unable to determine code must be set if race codes include 6351",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testSuccessToSetUnableDetermineCode() throws Exception {
    List<Short> raceCode = Arrays.asList((short) 6351);
    RaceAndEthnicity toValidate = new RaceAndEthnicityEntityBuilder().setRaceCode(raceCode)
        .setUnableToDetermineCode("A").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testFailureWhenHispanicOriginCodeIgnoreCase() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicOriginCode("n").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be one of [D, N, U, X, Y, Z, ]",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testFailureWhenHispanicOriginCodeInvalid() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicOriginCode("K").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be one of [D, N, U, X, Y, Z, ]",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testSuccessWhenHispanicOriginCodeIsD() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicOriginCode("D").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenHispanicOriginCodeIsN() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicOriginCode("N").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenHispanicOriginCodeIsU() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicOriginCode("U").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenHispanicOriginCodeIsX() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicOriginCode("X").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenHispanicOriginCodeIsY() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicOriginCode("Y").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenHispanicOriginCodeIsZ() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicOriginCode("Z").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenHispanicOriginCodeIsEmpty() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicOriginCode("").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testFailWhenHispanicOriginCodeIsNull() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicOriginCode(null).build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("may not be null", constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testFailWhenHispanicCodeInvalid() throws Exception {
    List<Short> hispanicCode = Arrays.asList((short) 123);
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicCode(hispanicCode).build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be a valid system code for category ETHNCTYC",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testSuccessWhenHispanicCodeValid() throws Exception {
    List<Short> hispanicCode = Arrays.asList((short) 3164);
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setHispanicCode(hispanicCode).build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testFailureToSetHispanicUnableDetermineCode() throws Exception {
    List<Short> hispanicCode = Arrays.asList((short) 6351);
    RaceAndEthnicity toValidate = new RaceAndEthnicityEntityBuilder().setHispanicCode(hispanicCode)
        .setUnableToDetermineCode("").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    System.out.println(constraintViolations.iterator().next().getMessage());
    assertEquals(1, constraintViolations.size());
    assertEquals("Hispanic unable to determine code must be set if hispanic codes include 6351",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testSuccessToSetHispanicUnableDetermineCode() throws Exception {
    List<Short> hispanicCode = Arrays.asList((short) 6351);
    RaceAndEthnicity toValidate = new RaceAndEthnicityEntityBuilder().setHispanicCode(hispanicCode)
        .setHispanicUnableToDetermineCode("A").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testFailureWhenUnableToDetermineCodeIgnoreCase() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setUnableToDetermineCode("a").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be one of [A, I, K, ]", constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testFailureWhenUnableToDetermineCodeInvalid() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setUnableToDetermineCode("Z").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be one of [A, I, K, ]", constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testSuccessWhenUnableToDetermineCodeIsA() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setUnableToDetermineCode("A").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenUnableToDetermineCodeIsI() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setUnableToDetermineCode("I").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenUnableToDetermineCodeIsK() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setUnableToDetermineCode("K").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenUnableToDetermineCodeIsEmpty() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setUnableToDetermineCode("").build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testSuccessWhenUnableToDetermineCodeIsNull() throws Exception {
    RaceAndEthnicity toValidate =
        new RaceAndEthnicityEntityBuilder().setUnableToDetermineCode(null).build();

    Set<ConstraintViolation<RaceAndEthnicity>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  /**
   * testfor Empty Constructor
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    RaceAndEthnicity empty = new RaceAndEthnicity();
    assertEquals(empty.getClass(), RaceAndEthnicity.class);
  }

  /**
   * 
   */
  @Test
  public void equalsHashCodeWork() {
    // EqualsVerifier.forClass(RaceAndEthnicity.class).suppress(Warning.NONFINAL_FIELDS).verify();
    RaceAndEthnicity empty = new RaceAndEthnicity();
    assertThat(empty.hashCode(), is(not(0)));
  }

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    RaceAndEthnicity domain = new RaceAndEthnicity(raceCode, unableToDetermineCode, hispanicCode,
        hispanicOriginCode, hispanicUnableToDetermineCode);

    assertThat(domain.getRaceCode(), is(equalTo(raceCode)));
    assertThat(domain.getUnableToDetermineCode(), is(equalTo(unableToDetermineCode)));
    assertThat(domain.getHispanicCode(), is(equalTo(hispanicCode)));
    assertThat(domain.getHispanicOriginCode(), is(equalTo(hispanicOriginCode)));
    assertThat(domain.getHispanicUnableToDetermineCode(),
        is(equalTo(hispanicUnableToDetermineCode)));
  }

  @Test
  public void testEntityBuilderGetters() throws Exception {
    // required for test coverage while RaceAndEthnicityEntityBuilder class is under src/main/java
    RaceAndEthnicityEntityBuilder builder = new RaceAndEthnicityEntityBuilder();

    RaceAndEthnicity toValidate = new RaceAndEthnicityEntityBuilder().build();
    assertThat(toValidate.getRaceCode(), is(equalTo(builder.getRaceCode())));
    assertThat(toValidate.getHispanicCode(), is(equalTo(builder.getHispanicCode())));
    assertThat(toValidate.getHispanicOriginCode(), is(equalTo(builder.getHispanicOriginCode())));
    assertThat(toValidate.getHispanicUnableToDetermineCode(),
        is(equalTo(builder.getHispanicUnableToDetermineCode())));

  }

}
