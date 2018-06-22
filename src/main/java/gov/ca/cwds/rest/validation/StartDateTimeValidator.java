package gov.ca.cwds.rest.validation;

import static gov.ca.cwds.rest.api.domain.DomainChef.DATE_FORMAT;
import static gov.ca.cwds.rest.api.domain.DomainChef.TIMESTAMP_ISO8601_FORMAT;
import static gov.ca.cwds.rest.api.domain.DomainChef.TIME_FORMAT;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.messages.MessageBuilder;

/**
 * @author CWDS API Team
 */
public class StartDateTimeValidator {
  private static final Logger LOGGER = LoggerFactory.getLogger(StartDateTimeValidator.class);

  /**
   * default constructor
   */
  private StartDateTimeValidator() {

  }

  /**
   *
   * @param startDateTime - start date/time
   * @param builder - logError messages
   * @return - timeStarted
   */
  public static String extractStartTime(String startDateTime, MessageBuilder builder) {
    String timeStarted = null;
    DateFormat dateTimeFormat = new SimpleDateFormat(TIMESTAMP_ISO8601_FORMAT, Locale.US);
    DateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
    try {
      Date dateTime = dateTimeFormat.parse(startDateTime);
      timeStarted = timeFormat.format(dateTime);
    } catch (ParseException | NullPointerException e) {
      String message = " parsing Start Date/Time ";
      builder.addError(message);
      logError(message, e);
    }
    return timeStarted;
  }

  /**
   *
   * @param startDateTime - start date/time
   * @param builder - logError messages
   * @return dateStarted
   */
  public static String extractStartDate(String startDateTime, MessageBuilder builder) {
    DateFormat dateTimeFormat = new SimpleDateFormat(TIMESTAMP_ISO8601_FORMAT, Locale.US);
    DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    String dateStarted = null;
    try {
      Date dateTime = dateTimeFormat.parse(startDateTime);
      dateStarted = dateFormat.format(dateTime);
    } catch (ParseException | NullPointerException e) {
      String message = " parsing Start Date/Time ";
      builder.addError(message);
      logError(message, e);
    }
    return dateStarted;
  }

  private static void logError(String message, Exception exception) {
    LOGGER.error(message, exception.getMessage());
  }
}
