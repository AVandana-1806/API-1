package gov.ca.cwds.data.es.transform;

import java.util.function.Supplier;

import org.slf4j.Logger;

/**
 * Conditionally logs arbitrary information without invoking {@link Object#toString()} or other
 * potentially expensive methods.
 * 
 * @author CWDS API Team
 */
public interface ConditionalLogger extends Logger {

  @SuppressWarnings("unchecked")
  void trace(String format, Supplier<Object>... args);

  @SuppressWarnings("unchecked")
  void debug(String format, Supplier<Object>... args);

  @SuppressWarnings("unchecked")
  void info(String format, Supplier<Object>... args);

  @SuppressWarnings("unchecked")
  void warn(String format, Supplier<Object>... args);

  @SuppressWarnings("unchecked")
  void error(String format, Supplier<Object>... args);

}
