package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CWDS API Team
 *
 */
public class IntakeEthnicity {

  @JsonProperty("hispanic_latino_origin")
  private String hispanicLatinoOrigin;

  @JsonProperty("ethnicity_detail")
  private List<String> ethnicityDetail;

  /**
   * default constructor
   */
  public IntakeEthnicity() {
    // no-opt
  }

  /**
   * @param hispanicLatinoOrigin - hispanicLatinoOrigin
   * @param ethnicityDetail - ethnicityDetail
   */
  public IntakeEthnicity(String hispanicLatinoOrigin, List<String> ethnicityDetail) {
    super();
    this.hispanicLatinoOrigin = hispanicLatinoOrigin;
    this.ethnicityDetail = ethnicityDetail;
  }

  /**
   * @return the hispanicLatinoOrigin
   */
  public String getHispanicLatinoOrigin() {
    return hispanicLatinoOrigin;
  }

  /**
   * @return the ethnicityDetail
   */
  public List<String> getEthnicityDetail() {
    return ethnicityDetail;
  }
}


