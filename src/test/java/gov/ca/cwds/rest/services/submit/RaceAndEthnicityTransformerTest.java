package gov.ca.cwds.rest.services.submit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.ParticipantIntakeApiResourceBuilder;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 */
public class RaceAndEthnicityTransformerTest {

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  /**
   * Test to check race and ethnicity transformed successfully
   */
  @Test
  public void testSuccesWhenBuildRaceAndEthnicty() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder().build();

    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicityTransformer().transform(participantsIntake);
    assertThat(raceAndEthnicity.getHispanicCode().get(0), is(equalTo((short) 3164)));
    assertThat(raceAndEthnicity.getRaceCode().get(0), is(equalTo((short) 841)));
  }

  /**
   * Test when the race code is "Abandoned" then the race code is 6351 and UnableToDetermineCode is
   * set to "U"
   */
  @Test
  public void testWhenRaceCodeIsAbandoned() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder()
        .setRaces("[\n" + "{\"race\":\"Unknown\",\"race_detail\":null}]").setEthnicity(null)
        .build();

    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicityTransformer().transform(participantsIntake);
    assertThat(raceAndEthnicity.getRaceCode().get(0), is(equalTo((short) 6351)));
    assertThat(raceAndEthnicity.getUnableToDetermineCode(), is(equalTo("U")));
  }

  /**
   * Test to validate when hispanic is empty and HispanicOriginCode is set "X"
   */
  @Test
  public void testWhenHispanicIsEmpty() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder()
        .setRaces(null)
        .setEthnicity(
            "{\n" + "  \"hispanic_latino_origin\": null,\n" + "  \"ethnicity_detail\": []\n" + "}")
        .build();
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicityTransformer().transform(participantsIntake);
    assertThat(raceAndEthnicity.getHispanicOriginCode(), is(equalTo("X")));
  }

  /**
   * Test when hispanic is No then hispanicOriginCode is set "N"
   */
  @Test
  public void testWhenHispanicIsNo() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder()
        .setRaces(null).setEthnicity("{\n" + "  \"hispanic_latino_origin\": \"No\",\n"
            + "  \"ethnicity_detail\": []\n" + "}")
        .build();
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicityTransformer().transform(participantsIntake);
    assertThat(raceAndEthnicity.getHispanicOriginCode(), is(equalTo("N")));
  }

  /**
   * Test when hispanic code is Yes and ethnicity detail is Empty, then hispanicOriginCode is set
   * "Y" and hispanic code to null
   */
  @Test
  public void testWhenHispanicIsYesAndEthnicityDeatilEmpty() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder()
        .setRaces(null).setEthnicity("{\n" + "  \"hispanic_latino_origin\": \"Yes\",\n"
            + "  \"ethnicity_detail\": []\n" + "}")
        .build();
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicityTransformer().transform(participantsIntake);
    assertThat(raceAndEthnicity.getHispanicOriginCode(), is(equalTo("Y")));
    assertThat(raceAndEthnicity.getHispanicCode(), is(nullValue()));
  }

  /**
   * Test when hispanic code is Yes and ethnicity detail is "Mexican", then hispanicOriginCode is
   * set "Y" and hispanic code to 3164
   */
  @Test
  public void testWhenHispanicIsYesAndEthnicityDeatilMexian() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder()
        .setRaces(null).setEthnicity("{\n" + "  \"hispanic_latino_origin\": \"Yes\",\n"
            + "  \"ethnicity_detail\": [\"Mexican\"]\n" + "}")
        .build();
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicityTransformer().transform(participantsIntake);
    assertThat(raceAndEthnicity.getHispanicOriginCode(), is(equalTo("Y")));
    assertThat(raceAndEthnicity.getHispanicCode().get(0), is(equalTo((short) 3164)));
  }

  /**
   * Test when hispanic is Unknown then hispanicOriginCode is set "U"
   */
  @Test
  public void testWhenHispanicIsUnknown() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder()
        .setRaces(null).setEthnicity("{\n" + "  \"hispanic_latino_origin\": \"Unknown\",\n"
            + "  \"ethnicity_detail\": []\n" + "}")
        .build();
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicityTransformer().transform(participantsIntake);
    assertThat(raceAndEthnicity.getHispanicOriginCode(), is(equalTo("U")));
  }

  /**
   * Test when hispanic is Abandoned then hispanicOriginCode is set "Z" and
   * hispanicUnableToDetermineCode "A"
   */
  @Test
  public void testWhenHispanicIsAbandoned() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder()
        .setRaces(null).setEthnicity("{\n" + "  \"hispanic_latino_origin\": \"Abandoned\",\n"
            + "  \"ethnicity_detail\": []\n" + "}")
        .build();
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicityTransformer().transform(participantsIntake);
    assertThat(raceAndEthnicity.getHispanicCode().get(0), is(equalTo((short) 6351)));
    assertThat(raceAndEthnicity.getHispanicOriginCode(), is(equalTo("Z")));
    assertThat(raceAndEthnicity.getHispanicUnableToDetermineCode(), is(equalTo("A")));
  }

  /**
   * Test when hispanic is Declined to answer then hispanicOriginCode is set "D"
   */
  @Test
  public void testWhenHispanicIsDeclinedToAnswer() {
    ParticipantIntakeApi participantsIntake =
        new ParticipantIntakeApiResourceBuilder().setRaces(null)
            .setEthnicity("{\n" + "  \"hispanic_latino_origin\": \"Declined to answer\",\n"
                + "  \"ethnicity_detail\": []\n" + "}")
            .build();
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicityTransformer().transform(participantsIntake);
    assertThat(raceAndEthnicity.getHispanicOriginCode(), is(equalTo("D")));
  }

  /**
   * Test to check service exception when Json mapper failed to parse Json
   */
  @Test(expected = ServiceException.class)
  public void testExceptionWhenJsonInValid() {
    ParticipantIntakeApi participantsIntake =
        new ParticipantIntakeApiResourceBuilder().setRaces("{invalid}").setEthnicity(null).build();
    @SuppressWarnings("unused")
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicityTransformer().transform(participantsIntake);
  }

  /**
   * Test when participant is null transform will be ignored
   */
  @Test
  public void testWhenParticipantNull() {
    ParticipantIntakeApi participantsIntake = null;
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicityTransformer().transform(participantsIntake);
    assertThat(raceAndEthnicity.getRaceCode(), is(nullValue()));
    assertThat(raceAndEthnicity.getHispanicCode(), is(nullValue()));
    assertThat(raceAndEthnicity.getUnableToDetermineCode(), is(nullValue()));
    assertThat(raceAndEthnicity.getHispanicOriginCode(), is(nullValue()));
    assertThat(raceAndEthnicity.getHispanicUnableToDetermineCode(), is(nullValue()));
  }

}
