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
 * final CaresMethodInterceptor daoInterceptor = new CaresMethodInterceptor();
 * bindInterceptor(Matchers.subclassesOf(CrudsDao.class), Matchers.any(), daoInterceptor);
 * requestInjection(daoInterceptor);
 * </pre>
 *
 * </blockquote>
 *
 * @author CWDS API Team
 */
public class CaresMethodInterceptor
    implements org.aopalliance.intercept.MethodInterceptor, RequestExecutionContextCallback {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(CaresMethodInterceptor.class);

  private static final Map<Method, AtomicLong> totalCounts = new ConcurrentHashMap<>();

  private static final ThreadLocal<Map<Method, AtomicLong>> requestCalls =
      ThreadLocal.withInitial(HashMap::new);

  /**
   * Increment counts in a method count map.
   * 
   * @param m join point method
   * @param map method count map
   * @return current count
   */
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

      final long start = System.currentTimeMillis();
      LOGGER.trace("before method: {}", m);
      final Object result = mi.proceed();
      final long totalCalls = incrementTotalCount(m);
      final long totalRequestCalls = incrementRequestCount(m);
      LOGGER.info("after  method: {}, total: {}, request: {}, millis: {}", m, totalCalls,
          totalRequestCalls, (System.currentTimeMillis() - start));
      return result;
    } catch (Exception e) {
      LOGGER.error("ERROR! {}", e.getMessage(), e);
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
