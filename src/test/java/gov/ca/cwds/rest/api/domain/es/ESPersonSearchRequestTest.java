package gov.ca.cwds.rest.api.domain.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class ESPersonSearchRequestTest extends Doofenshmirtz<ClientAddress> {

  ESPersonSearchRequest target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new ESPersonSearchRequest();
  }

  @Test
  public void type() throws Exception {
    assertThat(ESPersonSearchRequest.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getFirstName_A$() throws Exception {
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFirstName_A$String() throws Exception {
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void getLastName_A$() throws Exception {
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastName_A$String() throws Exception {
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void getBirthDate_A$() throws Exception {
    String actual = target.getBirthDate();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setBirthDate_A$String() throws Exception {
    String birthDate = null;
    target.setBirthDate(birthDate);
  }

  @Test
  public void toString_A$() throws Exception {
    String actual = target.toString();
    assertThat(actual, is(notNullValue()));
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
