package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.CmsAddressResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.validation.IfThen;

/**
 * 
 * @author CWDS API Team
 * 
 * @see Address
 *
 */
public class R00725StreetNameExigencyTest {

  private Validator validator;

  /**
   * 
   */
  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Test when address street number is set and street name is set null, using the IfThenValidator
   * 
   * @see IfThen
   * 
   * @throws Exception - Exception
   */
  @Test
  public void failsWhenStreetNumberIsSetAndStreetNameMissing() throws Exception {
    Address toValidate =
        new CmsAddressResourceBuilder().setStreetNumber("2751").setStreetName("").buildCmsAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("is required since streetNumber is set",
        constraintViolations.iterator().next().getMessage());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void successWhenStreetNumberAndStreetNameIsSet() throws Exception {
    Address toValidate = new CmsAddressResourceBuilder().setStreetNumber("2751")
        .setStreetName("W River Dr").buildCmsAddress();
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

}
