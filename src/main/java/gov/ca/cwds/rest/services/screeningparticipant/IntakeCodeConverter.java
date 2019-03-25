package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CWDS API Team
 */
public class IntakeCodeConverter {

  /**
   * Enum to lookup intake codes from legacy short description.
   */
  public enum IntakeRaceCode {

    ALASKA_NATIVE("Alaskan Native*", "American Indian or Alaska Native", "Alaska Native"),

    AMERICAN_INDIA("American Indian*", "American Indian or Alaska Native", "American Indian"),

    ASIAN_INDIAN("Asian Indian*", Constants.ASIAN, "Asian Indian"),

    BLACK("Black*", Constants.BLACK_AMERICAN, ""),

    COMBODIAN("Cambodian*", Constants.ASIAN, "Cambodian"),

    CARIBBEAN("Caribbean", Constants.BLACK_AMERICAN, "Caribbean"),

    CHINESE("Chinese*", Constants.ASIAN, "Chinese"),

    DECLINED_TO_STATE("Declines to State*", "Declined to answer", ""),

    ETHIOPIAN("Ethiopian*", Constants.BLACK_AMERICAN, "Ethiopian"),

    FILIPINO("Filipino*", Constants.ASIAN, "Filipino"),

    GUAMANIAN("Guamanian*", Constants.NATIVE_HAWAIIAN, "Guamanian"),

    HAWAIIAN("Hawaiian*", Constants.NATIVE_HAWAIIAN, "Hawaiian"),

    HMONG("Hmong*", Constants.ASIAN, "Hmong"),

    JAPANESE("Japanese*", Constants.ASIAN, "Japanese"),

    KOREAN("Korean*", Constants.ASIAN, "Korean"),

    LAOTIAN("Laotian*", Constants.ASIAN, "Laotian"),

    OTHER_ASIAN("Other Asian*", Constants.ASIAN, "Other Asian"),

    ASIAN("Other Asian*", Constants.ASIAN, ""),

    OTHER_PACIFIC_ISLANDER("Other Pacific Islander*", Constants.NATIVE_HAWAIIAN,
        "Other Pacific Islander"),

    // SNAP-1004: NPE on "Other Race Unknown"
    OTHER_RACE_UNKNOWN("Other Race Unknown", "Unknown", ""),

    OTHER_ASIAN_ISLANDER("Other Asian/Pacific Islander*", Constants.NATIVE_HAWAIIAN,
        "Other Asian/Pacific Islander"),

    NATIVE_HAWAIIAN("Other Pacific Islander*", Constants.NATIVE_HAWAIIAN, ""),

    POLYNESIAN("Polynesian*", Constants.NATIVE_HAWAIIAN, "Polynesian"),

    SAMOAN("Samoan*", Constants.NATIVE_HAWAIIAN, "Samoan"),

    UNABLE_TO_DETERMINE("Unable to Determine*", "Abandoned", ""),

    VIETNAMESE("Vietnamese*", Constants.ASIAN, "Vietnamese"),

    WHITE_ARMENIAN("White - Armenian*", Constants.WHITE, "Armenian"),

    WHITE_CENTRAL_AMERICAN("White - Central American*", Constants.WHITE, "Central American"),

    WHITE_EUROPEAN("White - European*", Constants.WHITE, "European"),

    WHITE_MIDDLE_EASTERN("White - Middle Eastern*", Constants.WHITE, "Middle Eastern"),

    WHITE_ROMANIAN("White - Romanian*", Constants.WHITE, "Romanian"),

    WHITE("White*", Constants.WHITE, "");

    private static final Map<String, List<IntakeRaceCode>> mapper = new HashMap<>();

    private final String legacyValue;
    private final String race;
    private final String raceDetail;

    IntakeRaceCode(String legacyValue, String race, String raceDetail) {
      this.legacyValue = legacyValue;
      this.race = race;
      this.raceDetail = raceDetail;
    }

    /**
     * @return the legacyValue
     */
    public String getLegacyValue() {
      return legacyValue;
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

    /**
     * @param legacyValue - legacyValue
     * @return the intake code values
     */
    public static IntakeRaceCode findByLegacyValue(String legacyValue) {
      return mapper.containsKey(legacyValue) ? mapper.get(legacyValue).get(0) : null;
    }

    /**
     * @param intakeRace - intakeRace
     * @return the intakeRaceCode
     */
    public static IntakeRaceCode lookUpByIntakeRace(IntakeRace intakeRace) {
      if (intakeRace == null) {
        return null;
      }
      if (intakeRace.getRaceDetail() == null) {
        intakeRace.setRaceDetail("");
      }
      IntakeRaceCode intakeRaceCode = null;
      for (IntakeRaceCode ce : IntakeRaceCode.values()) {
        if (intakeRace.getRace().equals(ce.getRace())
            && intakeRace.getRaceDetail().equals(ce.getRaceDetail())) {
          intakeRaceCode = ce;
          break;
        }
      }
      return intakeRaceCode;
    }

    static {
      for (IntakeRaceCode intakeRaceCode : IntakeRaceCode.values()) {
        List<IntakeRaceCode> list = null;
        if (mapper.containsKey(intakeRaceCode.legacyValue)) {
          list = mapper.get(intakeRaceCode.legacyValue);
          list.add(intakeRaceCode);
          mapper.put(intakeRaceCode.legacyValue, list);
        } else {
          list = new ArrayList<>();
          list.add(intakeRaceCode);
          mapper.put(intakeRaceCode.legacyValue, list);
        }
      }
    }
  }

  private static class Constants {
    public static final String ASIAN = "Asian";
    public static final String WHITE = "White";
    public static final String NATIVE_HAWAIIAN = "Native Hawaiian or Other Pacific Islander";
    public static final String BLACK_AMERICAN = "Black or African American";
  }

}
