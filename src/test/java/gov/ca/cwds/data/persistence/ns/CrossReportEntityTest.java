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

public class CrossReportEntityTest extends Doofenshmirtz<CrossReportEntity> {

  CrossReportEntity target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new CrossReportEntity();
  }

  @Test
  public void type() throws Exception {
    assertThat(CrossReportEntity.class, notNullValue());
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
  public void getPrimaryKey_A$() throws Exception {
    Serializable actual = target.getPrimaryKey();
    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
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
  public void getLegacyId_A$() throws Exception {
    String actual = target.getLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyId_A$String() throws Exception {
    String legacyId = null;
    target.setLegacyId(legacyId);
  }

  @Test
  public void getLegacySourceTable_A$() throws Exception {
    String actual = target.getLegacySourceTable();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacySourceTable_A$String() throws Exception {
    String legacySourceTable = null;
    target.setLegacySourceTable(legacySourceTable);
  }

  @Test
  public void getScreeningId_A$() throws Exception {
    String actual = target.getScreeningId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setScreeningId_A$String() throws Exception {
    String screeningId = null;
    target.setScreeningId(screeningId);
  }

  @Test
  public void getCommunicationMethod_A$() throws Exception {
    String actual = target.getCommunicationMethod();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCommunicationMethod_A$String() throws Exception {
    String communicationMethod = null;
    target.setCommunicationMethod(communicationMethod);
  }

  @Test
  public void getInformDate_A$() throws Exception {
    Date actual = target.getInformDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setInformDate_A$Date() throws Exception {
    Date informDate = mock(Date.class);
    target.setInformDate(informDate);
  }

  @Test
  public void getCountyId_A$() throws Exception {
    String actual = target.getCountyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCountyId_A$String() throws Exception {
    String countyId = null;
    target.setCountyId(countyId);
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
