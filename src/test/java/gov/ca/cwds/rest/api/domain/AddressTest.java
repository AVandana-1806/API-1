package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.LegacyDescriptorEntityBuilder;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AddressTest {

  private String street_name = "123 Main";
  private String city = "Sacramento";
  private Integer state = 1828; // "CA";
  private String zip = "95757";
  private Integer type = 32; // "Residence"
  private String legacySourceTable = "CLIENT_T";
  private String legacyId = "1234567ABC";
  private LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

  private Validator validator;

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(
        new Address("", "", "123 Main", "Sacramento", 1828, "95757", 32, legacyDescriptor));
    String serialized = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/address/valid/validLegacyDescriptorAddress.json"), Address.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void testDeserializesFromJSON() throws Exception {
    Address expected =
        new Address("", "", "123 Main", "Sacramento", 1828, "95757", 32, legacyDescriptor);
    Address serialized = MAPPER.readValue(
        fixture("fixtures/domain/address/valid/validLegacyDescriptorAddress.json"), Address.class);
    assertThat(serialized, is(expected));
  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    Address domain = new AddressResourceBuilder().createAddress();
    assertThat(domain.hashCode(), is(not(0)));
  }

  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Address domain = new AddressResourceBuilder().createAddress();

    gov.ca.cwds.data.persistence.ns.Address persistent =
        new gov.ca.cwds.data.persistence.ns.Address(domain, "12345", "12345");

    Address totest = new Address(persistent);
    assertThat(totest.getCity(), is(equalTo(persistent.getCity())));
    assertThat(totest.getState().toString(), is(equalTo(persistent.getState())));
    assertThat(totest.getStreetAddress(), is(equalTo(persistent.getStreetAddress())));
    assertThat(totest.getZip(), is(equalTo(persistent.getZip())));
    assertThat(totest.getType().toString(), is(equalTo(persistent.getType())));
  }

  @Test
  public void testJSONConstructorTest() throws Exception {
    Address domain = new Address("CLIENT_T", "1234567ABC", street_name, city, state, zip, type,
        legacyDescriptor);

    assertThat(domain.getCity(), is(equalTo(city)));
    assertThat(domain.getState(), is(equalTo(state)));
    assertThat(domain.getStreetAddress(), is(equalTo(street_name)));
    assertThat(domain.getZip(), is(equalTo(zip)));
    assertThat(domain.getType(), is(equalTo(type)));
    assertThat(domain.getLegacySourceTable(), is(equalTo(legacySourceTable)));
    assertThat(domain.getLegacyId(), is(equalTo(legacyId)));
  }

  @Test
  // R - 03232 Zip codes are five digits
  public void testZipCodeTooLongFail() throws Exception {
    Address toValidate = new AddressResourceBuilder().setZip("958333").createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    System.out.println(constraintViolations.iterator().next().getMessage());
    assertEquals(1, constraintViolations.size());
    assertEquals("Zip should be empty, zero, or 5 digits",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testZipCodeNull() throws Exception {
    Address toValidate = new AddressResourceBuilder().setZip(null).createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testZipCodeEmpty() throws Exception {
    Address toValidate = new AddressResourceBuilder().setZip("").createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testZipCodeWhiteSpaces() throws Exception {
    Address toValidate = new AddressResourceBuilder().setZip("   ").createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testZipCodeTooSmall() throws Exception {
    Address toValidate = new AddressResourceBuilder().setZip("123").createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("Zip should be empty, zero, or 5 digits",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testZipCodeZero() throws Exception {
    Address toValidate = new AddressResourceBuilder().setZip("0").createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testZipCodeZeroWithWhitespaces() throws Exception {
    Address toValidate = new AddressResourceBuilder().setZip(" 0    ").createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testZipCodeTooSmallContainsWhiteSpaces() throws Exception {
    Address toValidate = new AddressResourceBuilder().setZip(" 123 ").createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("Zip should be empty, zero, or 5 digits",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testBlankLegacySourceTableSuccess() throws Exception {
    Address toValidate = new AddressResourceBuilder().setLegacySourceTable("").createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());

  }

  @Test
  public void testNullLegacySourceTableSuccess() throws Exception {
    Address toValidate = new AddressResourceBuilder().setLegacySourceTable(null).createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());

  }

  @Test
  public void testMissingLegacySourceTableSuccess() throws Exception {
    Address toValidate = MAPPER.readValue(
        fixture("fixtures/domain/address/valid/missingLegacySourceTable.json"), Address.class);
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testBlankLegacyIdSuccess() throws Exception {
    Address toValidate = new AddressResourceBuilder().setLegacyId("").createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testNullLegacyIdSuccess() throws Exception {
    Address toValidate = new AddressResourceBuilder().setLegacyId(null).createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testMissingLegacyIdSuccess() throws Exception {
    Address toValidate = MAPPER
        .readValue(fixture("fixtures/domain/address/valid/missingLegacyId.json"), Address.class);
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testLegacyIdTooLongFail() throws Exception {
    Address toValidate = new AddressResourceBuilder().setLegacyId("A16H6gt06Bg").createAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("size must be between 0 and 10",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testLegacyDescriptorSetter() throws Exception {
    Address domain =
        new Address("", "", "123 Main", "Sacramento", 1828, "95757", 32, legacyDescriptor);
    LegacyDescriptor newLegacyDescriptor = new LegacyDescriptorEntityBuilder().build();
    domain.setLegacyDescriptor(newLegacyDescriptor);
    assertThat(domain.getLegacyDescriptor(), is(equalTo(newLegacyDescriptor)));
  }

}
