package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Test;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral;

public class SpecialProjectReferralTest {
  private String lastUpdatedId = "0X5";
  private LocalDateTime lastUpdatedTime = LocalDateTime.now();

  private String countySpecificCode = "17";
  private String referralId = "1234567ABC";
  private String specialProjectId = "2345678ABC";
  private LocalDate participationEndDate = null;
  private LocalDate participationStartDate = LocalDate.now();
  private Boolean safelySurrenderedBabiesIndicator = Boolean.FALSE;
  private String id = "3456789ABC";
  
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(SpecialProjectReferral.class.newInstance(), is(notNullValue()));
  }
  
  @Test
  public void testConstructor() throws Exception {
    SpecialProjectReferral spr = new SpecialProjectReferral(countySpecificCode, referralId, 
       specialProjectId, participationEndDate, participationStartDate, 
       safelySurrenderedBabiesIndicator, id);
    assertThat(spr.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(spr.getReferralId(), is(equalTo(referralId)));
    assertThat(spr.getSpecialProjectId(), is(equalTo(specialProjectId)));
    assertThat(spr.getPartEndDate(), is(equalTo(participationEndDate)));
    assertThat(spr.getPartStartDate(), is(equalTo(participationStartDate)));
    assertThat(spr.getSsbIndicator(), is(equalTo(safelySurrenderedBabiesIndicator)));
    assertThat(spr.getId(), is(equalTo(id)));
  }
  
  @Test
  public void testSetters() throws Exception {
    LocalDate newDate = LocalDate.now();
    SpecialProjectReferral spr = new SpecialProjectReferral();
    spr.setCountySpecificCode(countySpecificCode);
    assertThat(spr.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    spr.setId(id);
    assertThat(spr.getId(), is(equalTo(id)));
    assertThat(spr.getPrimaryKey(), is(equalTo(id)));
    spr.setLastUpdateId(lastUpdatedId);
    assertThat(spr.getLastUpdateId(), is(equalTo(lastUpdatedId)));
    spr.setLastUpdateTime(lastUpdatedTime);;
    assertThat(spr.getLastUpdateTime(), is(equalTo(lastUpdatedTime)));
    spr.setPartEndDate(newDate);
    assertThat(spr.getPartEndDate(), is(equalTo(newDate)));
    spr.setPartStartDate(newDate);
    assertThat(spr.getPartStartDate(), is(equalTo(newDate)));
    spr.setReferralId(referralId);
    assertThat(spr.getReferralId(), is(equalTo(referralId)));
    spr.setSsbIndicator(safelySurrenderedBabiesIndicator);
    assertThat(spr.getSsbIndicator(), is(equalTo(safelySurrenderedBabiesIndicator)));
    spr.setSpecialProjectId(specialProjectId);
    assertThat(spr.getSpecialProjectId(), is(equalTo(specialProjectId)));
  }
  
}
