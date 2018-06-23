package gov.ca.cwds.fixture;

import java.time.LocalDate;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral;

public class SpecialProjectReferralEntityBuilder {

  private String countySpecificCode = "24";
  private String referralId = "1234567ABC";
  private String specialProjectId = "2345678ABC";
  private LocalDate participationEndDate = null;
  private LocalDate participationStartDate = LocalDate.now();
  private Boolean safelySurrenderedBabiesIndicator = Boolean.FALSE;
  private String id = "3456789ABC";
  
  public SpecialProjectReferral build() {
    SpecialProjectReferral spr = new SpecialProjectReferral();
    spr.setCountySpecificCode(countySpecificCode);
    spr.setId(id);
    spr.setPartEndDate(participationEndDate);
    spr.setPartStartDate(participationStartDate);
    spr.setReferralId(referralId);
    spr.setSpecialProjectId(specialProjectId);
    spr.setSsbIndicator(safelySurrenderedBabiesIndicator);
    return spr;
  }
  
  public SpecialProjectReferralEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }
  
  public SpecialProjectReferralEntityBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }
  
  public SpecialProjectReferralEntityBuilder setSpecialProjectId(String specialProjectId) {
    this.specialProjectId = specialProjectId;
    return this;
  }
  
  public SpecialProjectReferralEntityBuilder setParticipationEndDate(LocalDate participationEndDate) {
    this.participationEndDate = participationEndDate;
    return this;
  }
  
  public SpecialProjectReferralEntityBuilder setParticipationStartDate(LocalDate participationStartDate) {
    this.participationStartDate = participationStartDate;
    return this;
  }
  
  public SpecialProjectReferralEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }
}
