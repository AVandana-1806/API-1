package gov.ca.cwds.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.slf4j.Logger;

public class CaresStackUtilsTest {

  @Test
  public void type() throws Exception {
    assertThat(CaresStackUtils.class, notNullValue());
  }

  @Test
  public void logStack_A$() throws Exception {
    CaresStackUtils.logStack();
  }

  @Test
  public void logStack_A$Logger() throws Exception {
    Logger logger = mock(Logger.class);
    CaresStackUtils.logStack(logger);
  }

  @Test
  public void stackToString_A$() throws Exception {
    String actual = CaresStackUtils.stackToString();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void stackToString_A$String() throws Exception {
    String delim = "\\n";
    String actual = CaresStackUtils.stackToString(delim);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getStackTrace_A$() throws Exception {
    StackTraceElement[] actual = CaresStackUtils.getStackTrace();
    assertThat(actual, is(notNullValue()));
  }

}
