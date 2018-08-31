package gov.ca.cwds.rest.filters;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import gov.ca.cwds.auth.realms.PerryUserIdentity;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.filters.RequestExecutionContext.Parameter;

/**
 * Manage non-HTTP requests for batch jobs, timer tasks, and health checks.
 * 
 * @author CWDS API Team
 */
public class CaresNonWebRequestContextManager {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CaresNonWebRequestContextManager.class);

  public void startRequest(String nonWebUser, String staffId) {
    RequestExecutionContextImpl.startRequest();
    final RequestExecutionContext ctx = RequestExecutionContext.instance();
    final PerryUserIdentity pui = ctx.getUserIdentity();
    pui.setStaffId(staffId);
    pui.setUser(nonWebUser);

    final Date requestStartTime = (Date) ctx.get(Parameter.REQUEST_START_TIME);
    final String requestStartTimeStr = DomainChef.cookTimestamp(requestStartTime);

    try {
      final String userId = ctx.getUserId();
      LOGGER.info("user id {} started request at {}", userId, requestStartTimeStr);
      MDC.put("userId", userId);
    } finally {
    }
  }

  public void stopRequest() {
    RequestExecutionContextImpl.stopRequest(); // mark request as "done", no matter what happens
    MDC.remove("userId"); // remove the logging context, no matter what happens
  }

}
