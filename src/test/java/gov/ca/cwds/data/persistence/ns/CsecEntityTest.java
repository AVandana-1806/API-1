package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import org.junit.Test;
import gov.ca.cwds.fixture.CsecEntityBuilder;

public class CsecEntityTest {

  private Integer id = 2345;
  private String participantId = "1234567XYZ";
  private String csecCodeId = "3456";
  private LocalDate startDate = LocalDate.parse("2018-06-01");
  private LocalDate endDate = LocalDate.parse("2018-08-01");
  
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(CsecEntity.class.newInstance(), is(notNullValue()));
  }
  
  @Test
  public void testGettersSetters() {
    CsecEntity csecEntity = new CsecEntityBuilder().setId(id)
        .setParticipantId(participantId)
        .setCsecCodeId(csecCodeId)
        .setStartDate(startDate)
        .setEndDate(endDate)
        .build();
    assertThat(csecEntity.getId(), is(equalTo(id)));
    assertThat(csecEntity.getParticipantId(), is(equalTo(participantId)));
    assertThat(csecEntity.getCsecCodeId(), is(equalTo(csecCodeId)));
    assertThat(csecEntity.getStartDate(), is(equalTo(startDate)));
    assertThat(csecEntity.getEndDate(), is(equalTo(endDate)));
  }
  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    CsecEntity csecEntity = new CsecEntity();
    assertTrue(csecEntity.equals(csecEntity));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
    CsecEntity csecEntity = new CsecEntity();
    assertFalse(csecEntity.equals(null));
  }
}
