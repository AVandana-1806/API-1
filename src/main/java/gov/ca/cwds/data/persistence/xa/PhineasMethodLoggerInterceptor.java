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
 * AOP interceptor tracks usage of any injected class.
 *
 * <h3>Sample Usage</h3>
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

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(PhineasMethodLoggerInterceptor.class);

  private static final Map<Method, AtomicLong> totalCounts = new ConcurrentHashMap<>();

  private static final ThreadLocal<Map<Method, AtomicLong>> requestCalls =
      ThreadLocal.withInitial(HashMap::new);

  private long incrementCount(Method m, Map<Method, AtomicLong> map) {
    AtomicLong count;

    if (map.containsKey(m)) {
      count = map.get(m);
    } else {
      count = new AtomicLong(0L);
      map.put(m, count);
    }

    return count.incrementAndGet();
  }

  /**
   * Clear containers and other request resources.
   */
  protected void resetRequest() {
    LOGGER.trace("reset request containers");
    requestCalls.get().clear();
  }

  /**
   * Increment total method calls across all requests.
   * 
   * @param m intercepted method
   * @return total calls across all requests
   */
  protected final long incrementTotalCount(Method m) {
    return incrementCount(m, totalCounts);
  }

  /**
   * Increment total method calls for <strong>this request</strong>.
   * 
   * @param m intercepted method
   * @return total calls for this request
   */
  protected final long incrementRequestCount(Method m) {
    return incrementCount(m, requestCalls.get());
  }

  @Override
  public Object invoke(org.aopalliance.intercept.MethodInvocation mi) throws Throwable {
    try {
      final Method m = mi.getMethod();
      LOGGER.debug("stack for method call: class: {}, method: {}", m.getDeclaringClass(),
          m.getName());
      CaresStackUtils.logStack();

      LOGGER.trace("Phineas interceptor: before method: {}", m);
      final Object result = mi.proceed();
      final long totalCalls = incrementTotalCount(m);
      final long requestCalls = incrementRequestCount(m);
      LOGGER.info("Phineas interceptor: after  method: {}, total: {}, request: {}", m, totalCalls,
          requestCalls);
      return result;
    } catch (Exception e) {
      LOGGER.error("Phineas interceptor: ERROR! {}", e.getMessage(), e);
      throw e;
    }
  }

  @Override
  public Serializable key() {
    return getClass().getName();
  }

  @Override
  public void startRequest(RequestExecutionContext ctx) {
    LOGGER.trace("start request");
    resetRequest();
  }

  @Override
  public void endRequest(RequestExecutionContext ctx) {
    LOGGER.trace("end request");
    resetRequest();
  }

}
