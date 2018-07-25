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

public class AllegationEntityTest extends Doofenshmirtz<AllegationEntity> {

  AllegationEntity target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new AllegationEntity();
  }

  @Test
  public void type() throws Exception {
    assertThat(AllegationEntity.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    Serializable actual = target.getPrimaryKey();
    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_A$Integer() throws Exception {
    Integer id = null;
    target.setId(id);
  }

  @Test
  public void setScreeningId_A$String() throws Exception {
    String screeningId = null;
    target.setScreeningId(screeningId);
  }

  @Test
  public void setPerpetratorId_A$String() throws Exception {
    String perpetratorId = null;
    target.setPerpetratorId(perpetratorId);
  }

  @Test
  public void setVictimId_A$String() throws Exception {
    String victimId = null;
    target.setVictimId(victimId);
  }

  @Test
  public void setCreatedAt_A$Date() throws Exception {
    Date createdAt = mock(Date.class);
    target.setCreatedAt(createdAt);
  }

  @Test
  public void setUpdatedAt_A$Date() throws Exception {
    Date updatedAt = mock(Date.class);
    target.setUpdatedAt(updatedAt);
  }

  @Test
  public void getScreeningEntity_A$() throws Exception {
    ScreeningEntity actual = target.getScreeningEntity();
    ScreeningEntity expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setScreeningEntity_A$ScreeningEntity() throws Exception {
    ScreeningEntity screeningEntity = mock(ScreeningEntity.class);
    target.setScreeningEntity(screeningEntity);
  }

  @Test
  public void getId_A$() throws Exception {
    Integer actual = target.getId();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getScreeningId_A$() throws Exception {
    String actual = target.getScreeningId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPerpetratorId_A$() throws Exception {
    String actual = target.getPerpetratorId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getVictimId_A$() throws Exception {
    String actual = target.getVictimId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCreatedAt_A$() throws Exception {
    Date actual = target.getCreatedAt();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getUpdatedAt_A$() throws Exception {
    Date actual = target.getUpdatedAt();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAllegationTypes_A$() throws Exception {
    String[] actual = target.getAllegationTypes();
    String[] expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAllegationTypes_A$StringArray() throws Exception {
    String[] allegationTypes = new String[] {};
    target.setAllegationTypes(allegationTypes);
  }

  @Test
  public void getScreening_A$() throws Exception {
    ScreeningEntity actual = target.getScreening();
    ScreeningEntity expected = null;
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
