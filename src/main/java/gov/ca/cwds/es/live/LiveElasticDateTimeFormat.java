package gov.ca.cwds.es.live;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public enum LiveElasticDateTimeFormat {

  /**
   * Date time format for last run date file.
   */
  LAST_RUN_DATE_FORMAT("yyyy-MM-dd HH:mm:ss"),

  /**
   * Date format for legacy DB2.
   */
  LEGACY_DATE_FORMAT("yyyy-MM-dd"),

  /**
   * Timestamp format for legacy DB2.
   */
  LEGACY_TIMESTAMP_FORMAT("yyyy-MM-dd HH:mm:ss.SSS");

  private final String format;

  private LiveElasticDateTimeFormat(String format) {
    this.format = format;
  }

  public String getFormat() {
    return format;
  }

  public DateFormat formatter() {
    return new SimpleDateFormat(format);
  }

}
