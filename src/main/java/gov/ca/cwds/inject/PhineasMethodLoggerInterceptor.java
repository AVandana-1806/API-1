package gov.ca.cwds.inject;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.CaresStackUtils;

/**
 * Construct an interceptor to monitor stack traces for any injected class.
 *
 * <blockquote>
 *
 * <pre>
 * final PhineasMethodLoggerInterceptor daoInterceptor = new PhineasMethodLoggerInterceptor();
 * bindInterceptor(Matchers.subclassesOf(CrudsDao.class), Matchers.any(), daoInterceptor);
 * requestInjection(daoInterceptor);
 * </pre>
 *
 * </blockquote>
 *
 * @author CWDS API Team
 */
public class PhineasMethodLoggerInterceptor implements org.aopalliance.intercept.MethodInterceptor {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(PhineasMethodLoggerInterceptor.class);

  protected final Map<Method, AtomicLong> countMap = new ConcurrentHashMap<>();

  protected long incrementCount(Method m) {
    AtomicLong value;

    if (countMap.containsKey(m)) {
      value = countMap.get(m);
    } else {
      value = new AtomicLong(0L);
      countMap.put(m, value);
    }

    return value.incrementAndGet();
  }

  @Override
  public Object invoke(org.aopalliance.intercept.MethodInvocation mi) throws Throwable {
    try {
      final Method m = mi.getMethod();
      LOGGER.info("stack for method call: class: {}, method: {}", m.getDeclaringClass(),
          m.getName());
      CaresStackUtils.logStack();

      LOGGER.info("Phineas interceptor: before method: {}", m);
      final Object result = mi.proceed();
      final long callCount = incrementCount(m);
      LOGGER.info("Phineas interceptor: after  method: {}, callCount: {}", m, callCount);
      return result;
    } catch (Exception e) {
      LOGGER.error("Phineas interceptor: ERROR PRINTING STACK TRACE! {}", e.getMessage(), e);
      throw e;
    }
  }

}
