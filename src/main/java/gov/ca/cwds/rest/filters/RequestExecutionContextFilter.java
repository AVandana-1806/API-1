package gov.ca.cwds.rest.filters;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.filters.RequestExecutionContext.Parameter;
import gov.ca.cwds.security.web.PerryAuthenticatingFilter;

/**
 * Store common info when an HTTP request begins. Can be merged with
 * {@link PerryAuthenticatingFilter} when ready. See story #147865633.
 * 
 * @author CWDS API Team
 * @see RequestExecutionContextImpl
 */
@Provider
@WebFilter
public class RequestExecutionContextFilter implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestExecutionContextFilter.class);

  /**
   * Constructor
   */
  @Inject
  public RequestExecutionContextFilter() {
    // No-op.
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    if (request instanceof HttpServletRequest) {
      final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
      RequestExecutionContextImpl.startRequest();

      final Date requestStartTime =
          (Date) RequestExecutionContext.instance().get(Parameter.REQUEST_START_TIME);
      final String requestStartTimeStr = DomainChef.cookTimestamp(requestStartTime);
      LOGGER.info("started request at {}", requestStartTimeStr);

      try {
        final String userId = RequestExecutionContext.instance().getUserId();
        MDC.put("userLog", userId);

        chain.doFilter(httpServletRequest, httpServletResponse);
      } finally {
        MDC.remove("userLog"); // remove the logging context, no matter what happens
        RequestExecutionContextImpl.stopRequest(); // mark request as "done", no matter what happens
      }
    }
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    LOGGER.info("filterConfig={}", filterConfig.getFilterName());
  }

  @Override
  public void destroy() {
    // No-op.
  }

}
