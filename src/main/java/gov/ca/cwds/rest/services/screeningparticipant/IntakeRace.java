package gov.ca.cwds.rest.services.screeningparticipant;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CWDS API Team
 *
 */
public class IntakeRace {

  @JsonProperty("race")
  private String race;

  @JsonProperty("race_detail")
  private String raceDetail;

  /**
   * default constructor
   */
  public IntakeRace() {
    // no-opt
  }

  /**
   * @param race - race
   * @param raceDetail - raceDetail
   */
  public IntakeRace(String race, String raceDetail) {
    super();
    this.race = race;
    this.raceDetail = raceDetail;
  }

  /**
   * @return the race
   */
  public String getRace() {
    return race;
  }

  /**
   * @return the raceDetail
   */
  public String getRaceDetail() {
    return raceDetail;
  }

  public void setRace(String race) {
    this.race = race;
  }

  public void setRaceDetail(String raceDetail) {
    this.raceDetail = raceDetail;
  }

}
