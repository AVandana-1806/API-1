package gov.ca.cwds.data.persistence.xa;

import static gov.ca.cwds.rest.core.Api.DATASOURCE_CMS_REP;
import static gov.ca.cwds.rest.core.Api.DATASOURCE_XA_CMS;
import static gov.ca.cwds.rest.core.Api.DATASOURCE_XA_NS;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;

/**
 * Ferb annotation identifies XA transactions, distributed, two-phase commits across datasources.
 * 
 * <p>
 * This annotation is intended for methods only, since methods are Java's defacto AOP join point. It
 * would make sense to put this annotation on a member field or constructor.
 * </p>
 * 
 * <p>
 * When attribute {@link #transactional()} is set to true (the default), then execute an XA
 * transaction across all participating XA datasources. Callers may optionally specify datasources
 * by name in the {@link #value()} attribute.
 * </p>
 * 
 * @author CWDS API Team
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface XAUnitOfWork {

  /**
   * If {@code true}, the Hibernate session will default to loading read-only entities.
   *
   * @see org.hibernate.Session#setDefaultReadOnly(boolean)
   * @return true = read only
   */
  boolean readOnly() default false;

  /**
   * If {@code true}, a transaction will be automatically started before the resource method is
   * invoked, committed if the method returned, and rolled back if an exception was thrown.
   * 
   * @return true = XA transaction is required
   */
  boolean transactional() default true;

  /**
   * The Hibernate {@link CacheMode} for the session.
   *
   * @return chose CacheMode, defaults to CacheMode.NORMAL
   * @see CacheMode
   * @see org.hibernate.Session#setCacheMode(CacheMode)
   */
  CacheMode cacheMode() default CacheMode.NORMAL;

  /**
   * The Hibernate {@link FlushMode} for the session.
   *
   * @return chosen Flush mode, defaults to FlushMode.Auto
   * @see FlushMode
   * @see org.hibernate.Session#setFlushMode(org.hibernate.FlushMode)
   */
  FlushMode flushMode() default FlushMode.AUTO;

  /**
   * The name of a hibernate bundle (session factory) that specifies a datasource against which a
   * transaction will be opened.
   * 
   * @return array of Hibernate bundle names
   */
  String[] value() default {DATASOURCE_CMS_REP, DATASOURCE_XA_CMS, DATASOURCE_XA_NS};

}
