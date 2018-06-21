package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CWDS API Team
 *
 */
public class LegacyToIntakeCodeConveter {

  /**
   * Enum to get the intake codes using the legacy description.
   *
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

    OTHER_PACIFIC_ISLANDER("Other Pacific Islander*", Constants.NATIVE_HAWAIIAN,
        "Other Pacific Islander"),

    OTHER_RACE_UNKNOWN("Other Race Unknown*", "Unknown", ""),

    OTHER_ASIAN_ISLANDER("Other Asian/Pacific Islander*", Constants.NATIVE_HAWAIIAN,
        "Other Asian/Pacific Islander"),

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

    private static final Map<String, IntakeRaceCode> mapper = new HashMap<>();

    private final String legacyValue;
    private final String race;
    private final String raceDetail;

    private IntakeRaceCode(String legacyValue, String race, String raceDetail) {
      this.legacyValue = legacyValue;
      this.race = race;
      this.raceDetail = raceDetail;
    }

    public String getLegacyValue() {
      return legacyValue;
    }

    public String getRace() {
      return race;
    }

    public String getRaceDetail() {
      return raceDetail;
    }

    /**
     * @param legacyValue - legacyValue
     * @return the intake code values
     */
    public static IntakeRaceCode findByLegacyValue(String legacyValue) {
      return mapper.containsKey(legacyValue) ? mapper.get(legacyValue) : null;
    }

    static {
      for (IntakeRaceCode intakeRaceCode : IntakeRaceCode.values()) {
        mapper.put(intakeRaceCode.legacyValue, intakeRaceCode);
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
