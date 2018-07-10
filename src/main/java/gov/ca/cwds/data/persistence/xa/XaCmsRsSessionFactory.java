package gov.ca.cwds.data.persistence.xa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * Injection annotation for XA CWS/CMS (DB2) session factory for replicated schemas.
 * 
 * @author CWDS API Team
 */
@BindingAnnotation
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XaCmsRsSessionFactory {

}
