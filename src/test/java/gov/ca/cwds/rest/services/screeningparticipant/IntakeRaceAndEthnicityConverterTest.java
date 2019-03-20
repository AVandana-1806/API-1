package gov.ca.cwds.rest.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientScpEthnicity;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ClientScpEthnicityEntityBuilder;

/**
 * @author CWDS API Team
 *
 */
public class IntakeRaceAndEthnicityConverterTest {

  private IntakeRaceAndEthnicityConverter raceAndEthnicityConverter =
      new IntakeRaceAndEthnicityConverter();

  /**
   * Initialize intake code cache
   */
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  public void lookupAndVerify(short sysId, String shortDesc) {
    // Test system cache doesn't load all ethnicities. Test fails.
    final Client client = new ClientEntityBuilder().setPrimaryEthnicityType(sysId).build();
    final String race = raceAndEthnicityConverter.createRace(client);
    assertThat(race, is(notNullValue()));
  }

  @Test
  public void testCreateRace__prod_codes() {
    lookupAndVerify((short) 820, "Alaskan Native*");
  }

  /**
   * 
   */
  @Test
  public void testCreateRaceIsNotNull() {
    Client client = new ClientEntityBuilder().setPrimaryEthnicityType((short) 821).build();
    String IntakeRace = raceAndEthnicityConverter.createRace(client);
    assertThat(IntakeRace, is(notNullValue()));
  }

  /**
   * 
   */
  @Test
  public void testCreateRaceIsCreatedSucessFully() {
    Client client = new ClientEntityBuilder().setPrimaryEthnicityType((short) 821).build();
    String IntakeRace = raceAndEthnicityConverter.createRace(client);
    assertThat(IntakeRace, is(equalTo(
        "[{\"race\":\"American Indian or Alaska Native\",\"race_detail\":\"American Indian\"}]")));
  }

  /**
   * 
   */
  @Test
  public void testCreateRaceWhenPrimaryCodeCaribbean() {
    Client client = new ClientEntityBuilder().setPrimaryEthnicityType((short) 3162).build();
    String IntakeHispanic = raceAndEthnicityConverter.createRace(client);
    assertThat(IntakeHispanic,
        is(equalTo("[{\"race\":\"Black or African American\",\"race_detail\":\"Caribbean\"}]")));
  }

  /**
   * 
   */
  @Test
  public void testCreateRaceForPrimaryAndSecondaryCodes() {
    Set<ClientScpEthnicity> clientScpEthnicities = new HashSet<>(
        Arrays.asList(new ClientScpEthnicityEntityBuilder().setEthnicity((short) 821).build()));
    Client client = new ClientEntityBuilder().setPrimaryEthnicityType((short) 3162).build();
    client.setClientScpEthnicities(clientScpEthnicities);
    String IntakeHispanic = raceAndEthnicityConverter.createRace(client);
    assertThat(IntakeHispanic, is(equalTo(
        "[{\"race\":\"Black or African American\",\"race_detail\":\"Caribbean\"},{\"race\":\"American Indian or Alaska Native\",\"race_detail\":\"American Indian\"}]")));
  }

  /**
   * 
   */
  @Test
  public void testCreateHispanicIsNotNull() {
    Client client = new ClientEntityBuilder().setPrimaryEthnicityType((short) 3164).build();
    String IntakeHispanic = raceAndEthnicityConverter.createHispanic(client);
    assertThat(IntakeHispanic, is(notNullValue()));
  }

  /**
   * 
   */
  @Test
  public void testCreateHispanicIsCreatedSuccessFully() {
    Client client = new ClientEntityBuilder().setPrimaryEthnicityType((short) 3164)
        .setHispanicOriginCode("Y").build();
    String IntakeHispanic = raceAndEthnicityConverter.createHispanic(client);
    assertThat(IntakeHispanic,
        is(equalTo("[{\"hispanic_latino_origin\":\"Yes\",\"ethnicity_detail\":[\"Mexican\"]}]")));
  }

  /**
   * 
   */
  @Test
  public void testCreateHispanicWhenHispanicCodeYAndEthnicityCode0() {
    Client client = new ClientEntityBuilder().setPrimaryEthnicityType((short) 0)
        .setHispanicOriginCode("Y").build();
    String IntakeHispanic = raceAndEthnicityConverter.createHispanic(client);
    assertThat(IntakeHispanic,
        is(equalTo("[{\"hispanic_latino_origin\":\"Yes\",\"ethnicity_detail\":[]}]")));
  }

  /**
   * 
   */
  @Test
  public void testCreateHispanicWhenHispanicCodeN() {
    Client client = new ClientEntityBuilder().setHispanicOriginCode("N").build();
    String IntakeHispanic = raceAndEthnicityConverter.createHispanic(client);
    assertThat(IntakeHispanic,
        is(equalTo("[{\"hispanic_latino_origin\":\"No\",\"ethnicity_detail\":[]}]")));
  }

  /**
   * 
   */
  @Test
  public void testCreateHispanicWhenHispanicCodeU() {
    Client client = new ClientEntityBuilder().setHispanicOriginCode("U").build();
    String IntakeHispanic = raceAndEthnicityConverter.createHispanic(client);
    assertThat(IntakeHispanic,
        is(equalTo("[{\"hispanic_latino_origin\":\"Unknown\",\"ethnicity_detail\":[]}]")));
  }

  /**
   * 
   */
  @Test
  public void testCreateHispanicWhenHispanicCodeZ() {
    Client client = new ClientEntityBuilder().setHispanicOriginCode("Z").build();
    String IntakeHispanic = raceAndEthnicityConverter.createHispanic(client);
    assertThat(IntakeHispanic,
        is(equalTo("[{\"hispanic_latino_origin\":\"Unknown\",\"ethnicity_detail\":[]}]")));
  }

  /**
   * 
   */
  @Test
  public void testCreateHispanicWhenHispanicCodeD() {
    Client client = new ClientEntityBuilder().setHispanicOriginCode("D").build();
    String IntakeHispanic = raceAndEthnicityConverter.createHispanic(client);
    assertThat(IntakeHispanic, is(
        equalTo("[{\"hispanic_latino_origin\":\"Declined to answer\",\"ethnicity_detail\":[]}]")));
  }

  /**
   * 
   */
  @Test
  public void testCreateHispanicWhenPrimaryCodeCaribbean() {
    Client client = new ClientEntityBuilder().setPrimaryEthnicityType((short) 3162).build();
    String IntakeHispanic = raceAndEthnicityConverter.createHispanic(client);
    assertThat(IntakeHispanic,
        is(equalTo("[{\"hispanic_latino_origin\":null,\"ethnicity_detail\":[]}]")));
  }

}
