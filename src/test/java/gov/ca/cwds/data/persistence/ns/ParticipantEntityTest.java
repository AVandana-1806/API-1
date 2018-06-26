package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.fixture.ScreeningEntityBuilder;

@SuppressWarnings("javadoc")
public class ParticipantEntityTest {

  private String id = "12345";
  private String firstName = "John";
  private String middleName = "";
  private String lastName = "Smitties";
  private String gender = "M";
  private Date birthDate = new Date();
  private Date deathDate = new Date();
  private String ssn = "222331111";
  private String legacyId = "1234567ABC";
  private String[] roles = {"Victim"};
  private String[] languages = {"English"};
  private String nameSuffix = "Esq.";
  private String races;
  private String ethnicity;
  private String legacySourceTable = "REFERL_T";
  private Boolean sensitivity = false;
  private Boolean sealed = false;
  private String approximateAge = "24";
  private String approximateAgeUnits = "YR";

  private ScreeningEntity screeningEntity;

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ParticipantEntity.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void testConstructor() throws Exception {
    screeningEntity = new ScreeningEntityBuilder().build();


    ParticipantEntity pe = new ParticipantEntity(id, birthDate, deathDate, firstName, gender,
        lastName, ssn, screeningEntity, legacyId, roles, languages, middleName, nameSuffix, races,
        ethnicity, legacySourceTable, sensitivity, sealed, approximateAge, approximateAgeUnits);

    assertThat(pe.getId(), is(equalTo(id)));
    assertThat(pe.getPrimaryKey(), is(equalTo(id)));
    assertThat(pe.getDateOfBirth(), is(equalTo(birthDate)));
    assertThat(pe.getDateOfDeath(), is(equalTo(deathDate)));
    assertThat(pe.getFirstName(), is(equalTo(firstName)));
    assertThat(pe.getGender(), is(equalTo(gender)));
    assertThat(pe.getLastName(), is(equalTo(lastName)));
    assertThat(pe.getSsn(), is(equalTo(ssn)));
    assertThat(pe.getScreening(), is(equalTo(screeningEntity)));
    assertThat(pe.getLegacyId(), is(equalTo(legacyId)));
    assertThat(pe.getRoles(), is(equalTo(roles)));
    assertThat(pe.getLanguages(), is(equalTo(languages)));
    assertThat(pe.getMiddleName(), is(equalTo(middleName)));
    assertThat(pe.getNameSuffix(), is(equalTo(nameSuffix)));
    assertThat(pe.getRaces(), is(equalTo(races)));
    assertThat(pe.getEthnicity(), is(equalTo(ethnicity)));
    assertThat(pe.getLegacySourceTable(), is(equalTo(legacySourceTable)));
    assertThat(pe.getSensitive(), is(equalTo(sensitivity)));
    assertThat(pe.getSealed(), is(equalTo(sealed)));
    assertThat(pe.getApproximateAge(), is(equalTo(approximateAge)));
    assertThat(pe.getApproximateAgeUnits(), is(equalTo(approximateAgeUnits)));
  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    ParticipantEntity pe = new ParticipantEntity();
    assertTrue(pe.equals(pe));
  }

}
