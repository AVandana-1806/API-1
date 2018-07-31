package gov.ca.cwds.data.persistence.xa;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.CaresStackUtils;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.RequestExecutionContextCallback;

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
public class PhineasMethodLoggerInterceptor
    implements org.aopalliance.intercept.MethodInterceptor, RequestExecutionContextCallback {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(PhineasMethodLoggerInterceptor.class);

  protected final Map<Method, AtomicLong> totalCounts = new ConcurrentHashMap<>();

  private static final ThreadLocal<Map<Method, AtomicLong>> requestCalls =
      ThreadLocal.withInitial(HashMap::new);

  protected long incrementCount(Method m, Map<Method, AtomicLong> map) {
    AtomicLong count;

    if (map.containsKey(m)) {
      count = map.get(m);
    } else {
      count = new AtomicLong(0L);
      map.put(m, count);
    }

    return count.incrementAndGet();
  }

  protected long incrementTotalCount(Method m) {
    return incrementCount(m, totalCounts);
  }

  protected long incrementRequestCount(Method m) {
    return incrementCount(m, requestCalls.get());
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
      final long totalCalls = incrementTotalCount(m);
      final long requestCalls = incrementRequestCount(m);
      LOGGER.info("Phineas interceptor: after  method: {}, total: {}, request: {}", m, totalCalls,
          requestCalls);
      return result;
    } catch (Exception e) {
      LOGGER.error("Phineas interceptor: ERROR PRINTING STACK TRACE! {}", e.getMessage(), e);
      throw e;
    }
  }

  @Override
  public Serializable key() {
    return getClass().getName();
  }

  @Override
  public void startRequest(RequestExecutionContext ctx) {
    requestCalls.get().clear();
  }

  @Override
  public void endRequest(RequestExecutionContext ctx) {
    requestCalls.get().clear();
  }

}
