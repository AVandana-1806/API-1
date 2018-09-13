package gov.ca.cwds;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CWSDateTImeTest {
  String utcTimestamp = "2018-09-11T16:48:08.000Z";

  @Test
  public void shouldConvertUTCTimestampStringToLocalStringWhenDateAndTimeIsProvided(){
    CWSDateTime dateTime = new CWSDateTime(utcTimestamp);
    assertEquals("2018-09-11T09:48:08.000", dateTime.toLocalTimeStamp());
  }

  @Test
  public void shouldConvertUTCTimestampStringToLocalStringWhenOnlyDateIsProvided(){
    CWSDateTime dateTime = new CWSDateTime("2018-09-11");
    assertEquals("2018-09-11T01:00:00.000", dateTime.toLocalTimeStamp());
  }
}