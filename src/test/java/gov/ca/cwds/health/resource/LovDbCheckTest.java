package gov.ca.cwds.health.resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class LovDbCheckTest extends Doofenshmirtz<ClientAddress> {

  LovDbCheck target;

  @Override
  public void setup() throws Exception {
    super.setup();
    target = new LovDbCheck(sessionFactory);
  }

  @Test
  public void type() throws Exception {
    assertThat(LovDbCheck.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void ping_A$() throws Exception {
    final boolean actual = target.ping();
    final boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMessage_A$() throws Exception {
    final String actual = target.getMessage();
    final String expected = "[]";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void checkTableCount_A$Connection$String$String$int() throws Exception {
    final String tableName = "INTAKE_LOV_CODES";
    final String schema = "intakens";
    final int expectedCount = 0;
    final boolean actual = target.checkTableCount(con, tableName, schema, expectedCount);
    final boolean expected = true;
    assertThat(actual, is(equalTo(expected)));
  }

}
