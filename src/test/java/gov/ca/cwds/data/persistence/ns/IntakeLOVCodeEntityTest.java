package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.util.Doofenshmirtz;

public class IntakeLOVCodeEntityTest extends Doofenshmirtz<IntakeLOVCodeEntity> {

  IntakeLOVCodeEntity target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new IntakeLOVCodeEntity();
  }

  @Test
  public void type() throws Exception {
    assertThat(IntakeLOVCodeEntity.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getCatId_A$() throws Exception {
    Long actual = target.getCatId();
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLgSysId_A$() throws Exception {
    Long actual = target.getLgSysId();
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLgSysId_A$Long() throws Exception {
    Long lgSysId = null;
    target.setLgSysId(lgSysId);
  }

  @Test
  public void getIntakeCode_A$() throws Exception {
    String actual = target.getIntakeCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIntakeCode_A$String() throws Exception {
    String intakeCode = null;
    target.setIntakeCode(intakeCode);
  }

  @Test
  public void getIntakeDisplay_A$() throws Exception {
    String actual = target.getIntakeDisplay();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIntakeDisplay_A$String() throws Exception {
    String intakeDisplay = null;
    target.setIntakeDisplay(intakeDisplay);
  }

  @Test
  public void getOmitInd_A$() throws Exception {
    String actual = target.getOmitInd();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getParentLgSysId_A$() throws Exception {
    Long actual = target.getParentLgSysId();
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    Serializable actual = target.getPrimaryKey();
    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void hashCode_A$() throws Exception {
    int actual = target.hashCode();
    int expected = 0;
    assertThat(actual, is(not(expected)));
  }

  @Test
  public void equals_A$Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
