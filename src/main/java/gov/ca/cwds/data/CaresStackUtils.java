package gov.ca.cwds.data;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * Find annoying bugs -- especially those nasty ones planted by your co-workers.
 * 
 * @author CWDS API Team
 */
public class CaresStackUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(CaresStackUtils.class);

  public static void logStack() {
    final RequestExecutionContext ctx = RequestExecutionContext.instance();
    if (ctx != null && ctx.isXaTransaction()) {
      try {
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        Arrays.stream(stack, 0, stack.length - 1)
            .filter(e -> e.getClassName().startsWith("gov.ca.cwds")
                && !e.getClassName().startsWith("gov.ca.cwds.rest.filters")
                && !e.getClassName().contains("$$"))
            .forEach(e -> LOGGER.info("\t{}", e));
      } catch (Exception e) {
        LOGGER.error("FAILED TO PRINT STACK! {}", e);
        throw e;
      }
    }
  }

}
