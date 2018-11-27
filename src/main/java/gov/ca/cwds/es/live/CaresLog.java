package gov.ca.cwds.es.live;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

/**
 * Neutron logging utilities.
 * 
 * <strong>Features</strong>
 * <table summary="Featured Methods">
 * <tr>
 * <th align="justify">Method</th>
 * <th align="justify">Purpose</th>
 * </tr>
 * <tr>
 * <td align="justify">{@link #checked(Logger, Throwable, String, Object...) checked}</td>
 * <td align="justify">Wrap Throwable and in {@link CaresExceptionChecked}</td>
 * </tr>
 * <tr>
 * <td align="justify">{@link #runtime(Logger, Throwable, String, Object...) runtime}</td>
 * <td align="justify">Wrap Throwable and in {@link CaresExceptionRuntime}</td>
 * </tr>
 * <tr>
 * <td align="justify">{@link #logEvery(Logger, int, int, String, Object...) logEvery}</td>
 * <td align="justify">Log every N records</td>
 * </tr>
 * <tr>
 * <td align="justify">{@link #stackToString(Exception) stackToString}</td>
 * <td align="justify">Print stack trace to a String</td>
 * </tr>
 * </table>
 * 
 * @author CWDS API Team
 */
public class CaresLog {

  private static final ConditionalLogger LOGGER = new CaresConditionalLoggerImpl(CaresLog.class);

  public static final int DEFAULT_LOG_EVERY = 100;

  /**
   * Static methods only; do not instantiate.
   * 
   * <p>
   * Evil singleton, blah, blah, blah ... I can't hear you ...
   * </p>
   */
  protected CaresLog() {
    // Protected so that child classes may inherit.
  }

  /**
   * Log every N records.
   * 
   * @param log Logger
   * @param logEvery log every N records
   * @param cntr record count
   * @param action action message (extract, transform, load, etc)
   * @param args variable message arguments
   */
  public static void logEvery(Logger log, int logEvery, int cntr, String action, Object... args) {
    if (cntr > 0 && (cntr % logEvery) == 0) {
      log.info("{} {} {}", action, cntr, args);
    }
  }

  /**
   * Log every {@link #DEFAULT_LOG_EVERY} records.
   * 
   * @param log Logger
   * @param cntr record count
   * @param action action message (extract, transform, load, etc)
   * @param args variable message arguments
   */
  public static void logEvery(Logger log, int cntr, String action, Object... args) {
    logEvery(log, DEFAULT_LOG_EVERY, cntr, action, args);
  }

  /**
   * Log every {@link #DEFAULT_LOG_EVERY} records.
   * 
   * @param cntr record count
   * @param action action message (extract, transform, load, etc)
   * @param args variable message arguments
   */
  public static void logEvery(int cntr, String action, Object... args) {
    logEvery(LOGGER, cntr, action, args);
  }

  /**
   * Format message and return a runtime {@link CaresExceptionRuntime}.
   * 
   * @param log class logger
   * @param e any Throwable
   * @param pattern MessageFormat pattern
   * @param args error message, excluding throwable message
   * @return JobsException runtime exception
   */
  public static CaresExceptionRuntime buildRuntimeException(final Logger log, Throwable e,
      String pattern, Object... args) {
    CaresExceptionRuntime ret;
    final boolean hasArgs = args == null || args.length == 0;
    final boolean hasPattern = !StringUtils.isEmpty(pattern);
    final Logger logger = log != null ? log : LOGGER;

    // Build message:
    final Object[] objs = hasArgs ? new Object[0] : args;
    final String pat = hasPattern ? pattern : StringUtils.join(objs, "{}");
    final String msg = hasPattern && hasArgs ? MessageFormat.format(pat, objs) : "";

    if (e != null) {
      logger.error(msg, e);
      ret = new CaresExceptionRuntime(msg, e);
    } else {
      logger.error(msg);
      ret = new CaresExceptionRuntime(msg);
    }

    return ret;
  }

  /**
   * Format message and return a runtime {@link CaresExceptionChecked}.
   * 
   * @param log class logger
   * @param e any Throwable
   * @param pattern MessageFormat pattern
   * @param args error message, excluding throwable message
   * @return NeutronCheckedException checked exception
   */
  public static CaresExceptionChecked buildCheckedException(final Logger log, Throwable e,
      String pattern, Object... args) {
    CaresExceptionChecked ret;
    final boolean hasArgs = args == null || args.length == 0;
    final boolean hasPattern = !StringUtils.isEmpty(pattern);
    final Logger logger = log != null ? log : LOGGER;

    // Build message:
    final Object[] objs = hasArgs ? new Object[0] : args;
    final String pat = hasPattern ? pattern : StringUtils.join(objs, "{}");
    final String msg = hasPattern && hasArgs ? MessageFormat.format(pat, objs) : "";

    if (e != null) {
      logger.error(msg, e);
      ret = new CaresExceptionChecked(msg, e);
    } else {
      logger.error(msg);
      ret = new CaresExceptionChecked(msg);
    }

    return ret;
  }

  public static CaresExceptionChecked checked(final Logger log, Throwable e, String pattern,
      Object... args) {
    return buildCheckedException(log, e, pattern, args);
  }

  public static CaresExceptionChecked checked(final Logger log, String pattern, Object... args) {
    return buildCheckedException(log, null, pattern, args);
  }

  public static CaresExceptionRuntime runtime(final Logger log, Throwable e, String pattern,
      Object... args) {
    return buildRuntimeException(log, e, pattern, args);
  }

  public static CaresExceptionRuntime runtime(final Logger log, String pattern, Object... args) {
    return buildRuntimeException(log, null, pattern, args);
  }

  public static String stackToString(Exception e) {
    return ExceptionUtils.getStackTrace(e);
  }

}
