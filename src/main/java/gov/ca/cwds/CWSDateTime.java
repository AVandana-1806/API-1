package gov.ca.cwds;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A DateTime class that provides CWDS operations for dates and times
 */
public class CWSDateTime {

  public static final String LOCAL_TIME_ZONE = "America/Los_Angeles";
  private ZonedDateTime utcDateTime;

  public static final String DATE_FORMAT = "yyyy-MM-dd";
  public static final String TIME_FORMAT = "HH:mm:ss";
  public static final String TIMESTAMP_ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  public static final String TIMESTAMP_ISO8601_FORMAT_TO_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSS";

  public CWSDateTime(ZonedDateTime dateTime) {
    this.utcDateTime = dateTime;
  }

  public CWSDateTime(String utcDateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIMESTAMP_ISO8601_FORMAT);
    if (utcDateTime.length() == DATE_FORMAT.length()) {
      utcDateTime += "T08:00:00.000Z";
    }
    LocalDateTime localDateTime = LocalDateTime.parse(utcDateTime, formatter);
    this.utcDateTime = localDateTime.atZone(ZoneId.of("UTC"));
  }

  public String toLocalTimeStamp() {
    return utcDateTime.withZoneSameInstant(localTimeZone()).format(toStringFormatter());
  }

  public String toLocalDate() {
    return utcDateTime.withZoneSameInstant(localTimeZone()).format(toStringDateFormatter());
  }

  public String toLocalTime() {
    return utcDateTime.withZoneSameInstant(localTimeZone()).format(toStringTimeFormatter());
  }

  private ZoneId localTimeZone() {
    return ZoneId.of(LOCAL_TIME_ZONE);
  }

  private DateTimeFormatter toStringFormatter() {
    return DateTimeFormatter.ofPattern(TIMESTAMP_ISO8601_FORMAT_TO_STRING);
  }

  private DateTimeFormatter toStringDateFormatter() {
    return DateTimeFormatter.ofPattern(DATE_FORMAT);
  }

  private DateTimeFormatter toStringTimeFormatter() {
    return DateTimeFormatter.ofPattern(TIME_FORMAT);
  }
}
