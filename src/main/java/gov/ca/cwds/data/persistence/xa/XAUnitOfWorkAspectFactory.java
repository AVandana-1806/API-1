package gov.ca.cwds.data.persistence.xa;

/**
 * Make a new aspect-oriented programming (AOP) handler, {@link XAUnitOfWorkAspect}, or join an
 * existing instance for this request.
 * 
 * @author CWDS API Team
 */
@FunctionalInterface
public interface XAUnitOfWorkAspectFactory {

  /**
   * Make a new AOP {@link XAUnitOfWorkAspect} or join an existing instance for this request, as
   * needed.
   * 
   * @return new AOP aspect or existing one
   */
  XAUnitOfWorkAspect make();

}
