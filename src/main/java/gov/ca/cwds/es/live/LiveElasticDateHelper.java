package gov.ca.cwds.es.live;

import static gov.ca.cwds.es.live.LiveElasticDateTimeFormat.LEGACY_TIMESTAMP_FORMAT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
   * Appease SonarQube's concern about "OMG! This gives away your secret implementation!"
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
