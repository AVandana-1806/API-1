package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class IntakeLovResponseTest {

  @Test
  public void type() {
    assertThat(IntakeLovResponse.class, notNullValue());
  }

  @Test
  public void instantiation() {
    List<IntakeLovEntry> lovs = null;
    IntakeLovResponse target = new IntakeLovResponse(lovs);
    assertThat(target, notNullValue());
  }

  @Test
  public void getLovEntries_Args__() {
    List<IntakeLovEntry> lovs = null;
    IntakeLovResponse target = new IntakeLovResponse(lovs);
    List<IntakeLovEntry> actual = target.getLovEntries();
    List<IntakeLovEntry> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLovEntries_Args__List() {
    List<IntakeLovEntry> lovs = new ArrayList<IntakeLovEntry>();
    IntakeLovResponse target = new IntakeLovResponse(lovs);
    target.setLovEntries(lovs);
  }

  @Test
  public void hashCode_Args__() {
    List<IntakeLovEntry> lovs = null;
    IntakeLovResponse target = new IntakeLovResponse(lovs);
    int actual = target.hashCode();
    int expected = 629;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void equals_Args__Object() {
    List<IntakeLovEntry> lovs = null;
    IntakeLovResponse target = new IntakeLovResponse(lovs);
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }
}
