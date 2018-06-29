package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.Serializable;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.util.Doofenshmirtz;

public class GovernmentAgencyEntityTest extends Doofenshmirtz<GovernmentAgencyEntity> {
  GovernmentAgencyEntity target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new GovernmentAgencyEntity();
  }

  @Test
  public void type() throws Exception {
    assertThat(GovernmentAgencyEntity.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_A$() throws Exception {
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_A$String() throws Exception {
    String id = null;
    target.setId(id);
  }

  @Test
  public void getCrossReportId_A$() throws Exception {
    String actual = target.getCrossReportId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCrossReportId_A$String() throws Exception {
    String crossReportId = null;
    target.setCrossReportId(crossReportId);
  }

  @Test
  public void getCode_A$() throws Exception {
    String actual = target.getCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCode_A$String() throws Exception {
    String code = null;
    target.setCode(code);
  }

  @Test
  public void getCategory_A$() throws Exception {
    String actual = target.getCategory();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCategory_A$String() throws Exception {
    String category = null;
    target.setCategory(category);
  }

  @Test
  public void getCreatedAt_A$() throws Exception {
    Date actual = target.getCreatedAt();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCreatedAt_A$Date() throws Exception {
    Date createdAt = mock(Date.class);
    target.setCreatedAt(createdAt);
  }

  @Test
  public void getUpdatedAt_A$() throws Exception {
    Date actual = target.getUpdatedAt();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setUpdatedAt_A$Date() throws Exception {
    Date updatedAt = mock(Date.class);
    target.setUpdatedAt(updatedAt);
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

  @Test
  public void getPrimaryKey_A$() throws Exception {
    Serializable actual = target.getPrimaryKey();
    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
