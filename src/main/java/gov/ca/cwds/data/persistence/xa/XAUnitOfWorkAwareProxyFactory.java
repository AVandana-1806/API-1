package gov.ca.cwds.data.persistence.xa;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.inject.FerbHibernateBundle;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

/**
 * A factory for creating proxies for components that use Hibernate data access objects outside
 * Jersey resources using two-phase commits via XA transactions.
 * 
 * <p>
 * A created proxy will be aware of the {@link XAUnitOfWork} annotation on the original class
 * methods and will open an XA transaction around them.
 * </p>
 * 
 * @author CWDS API Team
 */
public class XAUnitOfWorkAwareProxyFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(XAUnitOfWorkAwareProxyFactory.class);

  private final XAUnitOfWorkAspectFactory aspectFactory;

  /**
   * Construct from specialized Hibernate bundles.
   * 
   * @param bundles XA Hibernate bundles
   */
  public XAUnitOfWorkAwareProxyFactory(final FerbHibernateBundle... bundles) {
    final Map<String, SessionFactory> sessionFactories = new ConcurrentHashMap<>();
    for (FerbHibernateBundle bundle : bundles) {
      sessionFactories.put(bundle.name(), bundle.getSessionFactory());
    }

    aspectFactory = new ReentrantXAUnitOfWorkAspectFactoryImpl(sessionFactories);
  }

  /**
   * Alternate ctor takes the session factory name and the session factory itself.
   * 
   * @param factories session factories
   */
  @SafeVarargs
  public XAUnitOfWorkAwareProxyFactory(final Pair<String, SessionFactory>... factories) {
    final Map<String, SessionFactory> sessionFactories = new ConcurrentHashMap<>();
    for (Pair<String, SessionFactory> pair : factories) {
      sessionFactories.put(pair.getLeft(), pair.getRight());
    }

    aspectFactory = new ReentrantXAUnitOfWorkAspectFactoryImpl(sessionFactories);
  }

  /**
   * Creates a new <b>@XAUnitOfWork</b>-aware proxy of a class with the default constructor.
   *
   * @param clazz the specified class definition
   * @param <T> the type of the class
   * @return a new proxy
   * @throws CaresXAException on database error
   * @see #create(Class, Class[], Object[])
   */
  public <T> T create(Class<T> clazz) throws CaresXAException {
    LOGGER.debug("XAUnitOfWorkAwareProxyFactory.create: clazz: {}", clazz);
    return create(clazz, new Class<?>[] {}, new Object[] {});
  }

  /**
   * Creates a new <b>@XAUnitOfWork</b>-aware proxy of a class with an one-parameter constructor.
   *
   * @param clazz the specified class definition
   * @param constructorParamType the type of the constructor parameter
   * @param constructorArguments the argument passed to the constructor
   * @param <T> the type of the class
   * @return a new proxy
   * @throws CaresXAException on database error
   * @see #create(Class, Class[], Object[])
   */
  public <T> T create(Class<T> clazz, Class<?> constructorParamType, Object constructorArguments)
      throws CaresXAException {
    LOGGER.debug(
        "XAUnitOfWorkAwareProxyFactory.create: clazz: {}, constructorParamType: {}, constructorArguments: {}",
        clazz, constructorParamType, constructorArguments);
    return create(clazz, new Class<?>[] {constructorParamType},
        new Object[] {constructorArguments});
  }

  /**
   * Creates a new <b>@XAUnitOfWork</b>-aware proxy of a class by reflection.
   * 
   * <p>
   * In AOP terms, this wrapper method follows the <strong>"around"</strong> protocol by starting
   * with {@link XAUnitOfWorkAspect#beforeStart(java.lang.reflect.Method, XAUnitOfWork)}, calling
   * the target, annotated method, and finishing with {@link XAUnitOfWorkAspect#afterEnd()}.
   * </p>
   *
   * @param clazz the specified class definition, typically a REST resource
   * @param constructorParamTypes the types of the constructor parameters
   * @param constructorArguments the arguments passed to the constructor
   * @param <T> the type of the class
   * @return a new proxy
   * @throws CaresXAException on database error
   */
  @SuppressWarnings({"unchecked", "squid:S1166"})
  public <T> T create(Class<T> clazz, Class<?>[] constructorParamTypes,
      Object[] constructorArguments) throws CaresXAException {
    LOGGER.debug(
        "XAUnitOfWorkAwareProxyFactory.create: clazz: {}, constructorParamTypes: {}, constructorArguments: {}",
        clazz, constructorParamTypes, constructorArguments);
    final ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setSuperclass(clazz);

    try {
      final Proxy proxy = (Proxy) (constructorParamTypes.length == 0
          ? proxyFactory.createClass().getConstructor().newInstance()
          : proxyFactory.create(constructorParamTypes, constructorArguments));
      proxy.setHandler((self, overridden, proceed, args) -> {
        final XAUnitOfWork xaUnitOfWork = overridden.getAnnotation(XAUnitOfWork.class);
        final XAUnitOfWorkAspect aspect = newAspect();

        try {
          aspect.beforeStart(overridden, xaUnitOfWork); // BEFORE annotated method
          final Object result = proceed.invoke(self, args); // call annotated method
          aspect.afterEnd(); // AFTER annotated method
          return result;
        } catch (InvocationTargetException e) {
          LOGGER.error("XA ERROR! InvocationTargetException: {}", e.getCause(), e);
          throw e.getCause();
        } catch (Exception e) {
          LOGGER.error("XA ERROR! ", e);
          aspect.onError();
          throw e;
        } finally {
          aspect.onFinish();
        }
      });
      return (T) proxy;
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
        | InvocationTargetException e) {
      throw new CaresXAException("Unable to create a proxy for the class '" + clazz + "'", e);
    }
  }

  public XAUnitOfWorkAspect newAspect() {
    return aspectFactory.make();
  }

}
