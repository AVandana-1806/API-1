package gov.ca.cwds.es.live;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaresLogsTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(CaresLogsTest.class);

  @Test
  public void type() throws Exception {
    assertThat(CaresLog.class, notNullValue());
  }

  @Test
  public void logEvery_Args__Logger__int__String__StringArray() throws Exception {
    Logger log = mock(Logger.class);
    int cntr = 0;
    String action = null;
    Object[] args = new String[] {};
    CaresLog.logEvery(log, cntr, action, args);
  }

  @Test
  public void logEvery_Args__int__String__StringArray() throws Exception {
    int cntr = 0;
    String action = null;
    Object[] args = new String[] {};
    CaresLog.logEvery(cntr, action, args);
  }

  @Test(expected = CaresExceptionRuntime.class)
  public void throwFatalError_Args__Logger__Throwable__String__ObjectArray4() throws Exception {
    Logger log = mock(Logger.class);
    Throwable e = new IllegalStateException("hello world");
    String pattern = null;
    Object[] args = new Object[] {};
    throw CaresLog.buildRuntimeException(log, e, pattern, args);
  }

  @Test(expected = CaresExceptionRuntime.class)
  public void throwFatalError_Args__Logger__Throwable__String() throws Exception {
    Logger log = mock(Logger.class);
    Throwable e = new IllegalStateException("error message");
    String message = "hello world";
    throw CaresLog.buildRuntimeException(log, e, message);
  }

  @Test(expected = CaresExceptionRuntime.class)
  public void logEvery_Args__Logger__int__String__ObjectArray1() throws Exception {
    Exception e = new Exception();
    throw CaresLog.buildRuntimeException(LOGGER, e, "BATCH ERROR! {}", e.getMessage());
  }

  @Test
  public void logEvery_Args__int__String__ObjectArray() throws Exception {
    int cntr = 0;
    String action = null;
    Object[] args = new Object[] {};
    CaresLog.logEvery(cntr, action, args);
  }

  @Test(expected = CaresExceptionRuntime.class)
  public void raiseError_Args__Logger__Throwable__String__ObjectArray2() throws Exception {
    Logger log = mock(Logger.class);
    Throwable e = null;
    String pattern = null;
    Object[] args = new Object[] {};
    throw CaresLog.buildRuntimeException(log, e, pattern, args);
  }

  @Test(expected = CaresExceptionRuntime.class)
  public void raiseError_Args__Logger__Throwable__ObjectArray3() throws Exception {
    Logger log = mock(Logger.class);
    Throwable e = null;
    throw CaresLog.buildRuntimeException(log, e, "something bad", "who", "cares");
  }

  @Test
  public void logEvery_Args__Logger__int__int__String__ObjectArray() throws Exception {
    Logger log = mock(Logger.class);
    int logEvery = 0;
    int cntr = 0;
    String action = null;
    Object[] args = new Object[] {};
    CaresLog.logEvery(log, logEvery, cntr, action, args);
  }

  @Test
  public void logEvery_Args__Logger__int__String__ObjectArray() throws Exception {
    Logger log = mock(Logger.class);
    int cntr = 0;
    String action = null;
    Object[] args = new Object[] {};
    for (int i = 0; i < 10000; i++) {
      CaresLog.logEvery(log, ++cntr, action, args);
    }
  }

  @Test
  public void buildRuntimeException_Args__Logger__Throwable__String__ObjectArray()
      throws Exception {
    Logger log = mock(Logger.class);
    Throwable e = null;
    String pattern = null;
    Object[] args = new Object[] {};
    CaresExceptionRuntime actual = CaresLog.buildRuntimeException(log, e, pattern, args);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void buildRuntimeException_Args__2() throws Exception {
    Logger log = mock(Logger.class);
    Throwable e = null;
    String pattern = "uh oh: {}";
    Object[] args = new Object[] {"oops!"};
    CaresExceptionRuntime actual = CaresLog.buildRuntimeException(log, e, pattern, args);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void buildRuntimeException_Args__3() throws Exception {
    Logger log = null;
    Throwable e = null;
    String pattern = "uh oh: {}";
    Object[] args = new Object[] {"oops!"};
    CaresExceptionRuntime actual = CaresLog.buildRuntimeException(log, e, pattern, args);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void buildCheckedException_Args__Logger__Throwable__String__ObjectArray()
      throws Exception {
    Logger log = mock(Logger.class);
    Throwable e = null;
    String pattern = null;
    Object[] args = new Object[] {};
    CaresExceptionChecked actual = CaresLog.buildCheckedException(log, e, pattern, args);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void checked_Args__Logger__Throwable__String__ObjectArray() throws Exception {
    Logger log = mock(Logger.class);
    Throwable e = null;
    String pattern = null;
    Object[] args = new Object[] {};
    CaresExceptionChecked actual = CaresLog.checked(log, e, pattern, args);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void runtime_Args__Logger__Throwable__String__ObjectArray() throws Exception {
    Logger log = mock(Logger.class);
    Throwable e = null;
    String pattern = null;
    Object[] args = new Object[] {};
    CaresExceptionRuntime actual = CaresLog.runtime(log, e, pattern, args);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void stackToString_Args__Exception() throws Exception {
    Exception e = new IllegalStateException("test this");
    String actual = CaresLog.stackToString(e);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void runtime_Args__Logger__String__ObjectArray() throws Exception {
    Logger log = mock(Logger.class);
    String pattern = null;
    Object[] args = new Object[] {};
    CaresExceptionRuntime actual = CaresLog.runtime(log, pattern, args);
    assertThat(actual, is(notNullValue()));
  }

}
