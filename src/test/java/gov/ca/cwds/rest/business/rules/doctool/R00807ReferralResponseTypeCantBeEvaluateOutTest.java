package gov.ca.cwds.rest.business.rules.doctool;

import static gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.EVALUATE_OUT;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.validation.IfThenNot;

/**
 * @author CWDS API Team
 *
 */
public class R00807ReferralResponseTypeCantBeEvaluateOutTest {

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
   * Test when application petition indicator is true and referral response type is 1519, using the
   * ifThenNotValidator
   * 
   * @see IfThenNot
   * 
   * @throws Exception - Exception
   */
  @Test
  public void failsWhenPetitionIndicatorTrueAndResponeTypeEvaluateOut() throws Exception {
    Referral toValidate = new ReferralResourceBuilder().setApplicationForPetitionIndicator(true)
        .setReferralResponseType(EVALUATE_OUT.getCode()).build();
    Set<ConstraintViolation<Referral>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("is not valid since applicationForPetitionIndicator is set to true",
        constraintViolations.iterator().next().getMessage());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void successWhenPetitionIndicatorFalseAndResponeTypeEvaluateOut() throws Exception {
    Referral toValidate = new ReferralResourceBuilder().setApplicationForPetitionIndicator(false)
        .setReferralResponseType(EVALUATE_OUT.getCode()).build();
    Set<ConstraintViolation<Referral>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

}
