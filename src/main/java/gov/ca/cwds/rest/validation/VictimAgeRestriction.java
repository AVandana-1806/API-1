package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import gov.ca.cwds.rest.business.rules.R00786VictimAgeRestriction;

/**
 * <blockquote>
 * 
 * <pre>
 * BUSINESS RULE: R - 05609 Victim must be <19
 * 
 *  Any alleged victim(s) must be known or assumed to be under 19 years of age at the time the referral was received. 
 *  This does not apply to a client named as a victim in only allegations concluded as 'Entered in Error'. If birth 
 *  date and age are not specified or if the client victim is 19 years or older, do not allow save to database.
 * </blockquote>
 * </pre>
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = R00786VictimAgeRestriction.class)
public @interface VictimAgeRestriction {

  String message() default "Victim's age must be less than 18 years";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
