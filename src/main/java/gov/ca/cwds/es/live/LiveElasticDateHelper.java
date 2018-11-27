package gov.ca.cwds.es.live;

import static gov.ca.cwds.es.live.LiveElasticDateTimeFormat.LEGACY_DATE_FORMAT;
import static gov.ca.cwds.es.live.LiveElasticDateTimeFormat.LEGACY_TIMESTAMP_FORMAT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Date and timestamp utilities, mainly for DB2 and last change mode.
 * 
 * @author CWDS API Team
 */
public class LiveElasticDateHelper {

  private LiveElasticDateHelper() {
    // no-op
  }

  /**
   * Appease SonarQube's concern about "OMG! This gives away your implementation!"
   * 
   * @param incoming date to clone
   * @return fresh new Date -- to shut SonarQube up
   */
  public static Date freshDate(Date incoming) {
    return incoming != null ? new Date(incoming.getTime()) : null;
  }

  public static Date lookBack(final Date lastRunTime) {
    final Calendar cal = Calendar.getInstance();
    cal.setTime(lastRunTime);
    cal.add(Calendar.MINUTE, LiveElasticIntegerDefaults.LOOKBACK_MINUTES.getValue());
    return cal.getTime();
  }

  public static Date uncookTimestampString(String timestamp) {
    final String trimTimestamp = StringUtils.trim(timestamp);
    if (StringUtils.isNotEmpty(trimTimestamp)) {
      try {
        return new SimpleDateFormat(LEGACY_TIMESTAMP_FORMAT.getFormat()).parse(trimTimestamp);
      } catch (Exception e) {
        throw new CaresRuntimeException(e);
      }
    }
    return null;
  }

  public static String makeTimestampString(final Date date) {
    final StringBuilder buf = new StringBuilder();
    buf.append("TIMESTAMP('")
        .append(new SimpleDateFormat(LEGACY_TIMESTAMP_FORMAT.getFormat()).format(date))
        .append("')");
    return buf.toString();
  }

  public static String makeSimpleTimestampString(final Date date) {
    Date useThisDate = date;

    if (date == null) {
      final Calendar cal = Calendar.getInstance();
      cal.add(Calendar.MINUTE, 2); // in case server time doesn't match database
      useThisDate = cal.getTime();
    }

    return new SimpleDateFormat(LEGACY_TIMESTAMP_FORMAT.getFormat()).format(useThisDate);
  }

  public static String makeSimpleDateString(final Date date) {
    return new SimpleDateFormat(LEGACY_DATE_FORMAT.getFormat()).format(date);
  }

  public static String makeTimestampStringLookBack(final Date date) {
    String ret;
    final DateFormat fmt = new SimpleDateFormat(LEGACY_TIMESTAMP_FORMAT.getFormat());

    if (date != null) {
      ret = fmt.format(lookBack(date));
    } else {
      final Calendar cal = Calendar.getInstance();
      cal.add(Calendar.MINUTE, LiveElasticIntegerDefaults.LOOKBACK_MINUTES.getValue());
      ret = fmt.format(lookBack(cal.getTime()));
    }

    return ret;
  }

}
