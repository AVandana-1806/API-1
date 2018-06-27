package gov.ca.cwds.rest.services.screeningparticipant;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import gov.ca.cwds.rest.services.screeningparticipant.IntakeCodeConverter.IntakeRaceCode;

/**
 * @author CWDS API Team
 *
 */
public class IntakeCodeConverterTest {

  /**
   * 
   */
  @Test
  public void type() {
    assertThat(IntakeRaceCode.class, notNullValue());
  }

  /**
   * 
   */
  @Test
  public void getRaceValue_Args__() {
    IntakeRaceCode target = IntakeRaceCode.ALASKA_NATIVE;
    String race = target.getRace();
    String expected = "American Indian or Alaska Native";
    assertThat(race, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void getLegacyValue_Args__() {
    IntakeRaceCode target = IntakeRaceCode.ALASKA_NATIVE;
    String race = target.getLegacyValue();
    String expected = "Alaskan Native*";
    assertThat(race, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void getRaceDetail_Args__() {
    IntakeRaceCode target = IntakeRaceCode.AMERICAN_INDIA;
    String raceDetail = target.getRaceDetail();
    String expected = "American Indian";
    assertThat(raceDetail, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void testForConstants_Args__() {
    IntakeRaceCode target = IntakeRaceCode.JAPANESE;
    String race = target.getRace();
    String expected = "Asian";
    assertThat(race, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void findLegacyValueNull_Args__String() {
    String legacyValue = null;
    IntakeRaceCode actual = IntakeRaceCode.findByLegacyValue(legacyValue);
    IntakeRaceCode expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void findByLegacyValue_ToGet_IntakeRaceAndRaceDetail() {
    String legacyValue = "White - Romanian*";
    String actual = IntakeRaceCode.findByLegacyValue(legacyValue).getRace();
    String expected = IntakeRaceCode.WHITE_ROMANIAN.getRace();
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void lookUpByIntakeRace_ToGet_LegacyValue() {
    IntakeRace intakeRace = new IntakeRace("White", "Romanian");
    String actual = IntakeRaceCode.lookUpByIntakeRace(intakeRace).getLegacyValue();
    String expected = IntakeRaceCode.WHITE_ROMANIAN.getLegacyValue();
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void lookUpByIntakeRace_ReturnNull_WhenIntakeRaceNull() {
    IntakeRace intakeRace = null;
    IntakeRaceCode actual = IntakeRaceCode.lookUpByIntakeRace(intakeRace);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void lookUpByIntakeRace_WhenRaceDetailNull_LegacyValue() {
    IntakeRace intakeRace = new IntakeRace("White", null);
    String actual = IntakeRaceCode.lookUpByIntakeRace(intakeRace).getLegacyValue();
    String expected = IntakeRaceCode.WHITE.getLegacyValue();
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void lookUpByIntakeRace_ForAsian_LegacyValue() {
    IntakeRace intakeRace = new IntakeRace("Asian", "");
    String actual = IntakeRaceCode.lookUpByIntakeRace(intakeRace).getLegacyValue();
    String expected = IntakeRaceCode.ASIAN.getLegacyValue();
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void lookUpByIntakeRace_ForNativeHawaiian_LegacyValue() {
    IntakeRace intakeRace = new IntakeRace("Native Hawaiian or Other Pacific Islander", "");
    String actual = IntakeRaceCode.lookUpByIntakeRace(intakeRace).getLegacyValue();
    String expected = IntakeRaceCode.NATIVE_HAWAIIAN.getLegacyValue();
    assertThat(actual, is(equalTo(expected)));
  }

}
