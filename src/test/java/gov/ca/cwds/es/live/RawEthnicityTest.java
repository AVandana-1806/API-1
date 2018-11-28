package gov.ca.cwds.es.live;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.sql.SQLException;

import org.junit.Test;

import gov.ca.cwds.rest.util.Doofenshmirtz;

public class RawEthnicityTest extends Doofenshmirtz<RawClient> {

  RawEthnicity target;

  @Override
  public void setup() throws Exception {
    super.setup();
    target = new RawEthnicity();
  }

  @Test
  public void type() throws Exception {
    assertThat(RawEthnicity.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void read_A$ResultSet() throws Exception {
    RawEthnicity actual = target.read(rs);
    assertThat(actual, is(notNullValue()));
  }

  @Test(expected = SQLException.class)
  public void read_A$ResultSet_T$SQLException() throws Exception {
    bombResultSet();
    target.read(rs);
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    Serializable actual = target.getPrimaryKey();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getClientEthnicityId_A$() throws Exception {
    String actual = target.getClientEthnicityId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientEthnicityId_A$String() throws Exception {
    String clientEthnicityId = null;
    target.setClientEthnicityId(clientEthnicityId);
  }

  @Test
  public void getClientEthnicityCode_A$() throws Exception {
    Short actual = target.getClientEthnicityCode();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientEthnicityCode_A$Short() throws Exception {
    Short clientEthnicityCode = null;
    target.setClientEthnicityCode(clientEthnicityCode);
  }

}
