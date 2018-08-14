package gov.ca.cwds.rest.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * CWDS API Team
 */
public class FerbDateUtilsTest {

  @Test
  public void testShiftTimeZone() throws Exception {
    String ldtValue = "2017-01-03T03:10:09";
    LocalDateTime ldt = LocalDateTime.parse(ldtValue);
    LocalDateTime shiftedLdt = FerbDateUtils.shiftTimeZone(ldt, ZoneOffset.UTC, ZoneId.of("+1"));
    assertEquals("2017-01-03T04:10:09", shiftedLdt.toString());
  }

  @Test
  public void testUtcToSystemTime() throws Exception {
    String supposedSystemValue = "2017-01-03T03:10:09";
    LocalDateTime supposedSystemTime = LocalDateTime.parse(supposedSystemValue);
    LocalDateTime utcDateTimeForCurrentSystemTime =
        FerbDateUtils.shiftTimeZone(supposedSystemTime, ZoneId.systemDefault(), ZoneOffset.UTC);

    LocalDateTime shiftedLdt = FerbDateUtils.utcToSystemTime(utcDateTimeForCurrentSystemTime);
    assertEquals("2017-01-03T03:10:09", shiftedLdt.toString());
  }

  @Test
  public void dateToLocalDateNullTest() throws Exception {
    LocalDate localDate = FerbDateUtils.dateToLocalDate(null);
    assertNull(localDate);
  }

  @Test
  public void dateToLocalDateTest() throws Exception {
    String dateValue = "2017-01-03";
    Date date = DomainChef.uncookDateString(dateValue);
    LocalDate localDate = FerbDateUtils.dateToLocalDate(date);
    assertEquals(LocalDate.of(2017, 01, 03), localDate);
  }

}
