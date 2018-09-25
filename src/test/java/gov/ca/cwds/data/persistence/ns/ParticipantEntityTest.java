package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.ScreeningEntityBuilder;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class ParticipantEntityTest extends Doofenshmirtz<ParticipantEntity> {

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
  private Boolean probationYouth = false;
  private String approximateAge = "24";
  private String approximateAgeUnits = "YR";
  private String relatedScreeningId = "123123";
  private ScreeningEntity screeningEntity;
  private Boolean estimatedDob;
  ParticipantEntity target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new ParticipantEntity();
  }

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ParticipantEntity.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void testConstructor() throws Exception {
    screeningEntity = new ScreeningEntityBuilder().build();
    ParticipantEntity pe = new ParticipantEntity(id, birthDate, deathDate, firstName, gender,
        lastName, ssn, screeningEntity, legacyId, roles, languages, middleName, nameSuffix, races,
        ethnicity, legacySourceTable, sensitivity, sealed, probationYouth, approximateAge, approximateAgeUnits, estimatedDob);
    pe.setRelatedScreeningId(relatedScreeningId);
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
    assertThat(pe.getProbationYouth(), is(equalTo(probationYouth)));
    assertThat(pe.getApproximateAge(), is(equalTo(approximateAge)));
    assertThat(pe.getApproximateAgeUnits(), is(equalTo(approximateAgeUnits)));
    assertThat(pe.getRelatedScreeningId(), is(equalTo(relatedScreeningId)));
    assertThat(pe.getEstimatedDob(), is(equalTo(estimatedDob)));
  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    ParticipantEntity pe = new ParticipantEntity();
    assertTrue(pe.equals(pe));
  }

  @Test
  public void type() throws Exception {
    assertThat(ParticipantEntity.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void updateFrom_A$ParticipantIntakeApi() throws Exception {
    ParticipantIntakeApi participantIntakeApi = mock(ParticipantIntakeApi.class);
    ParticipantEntity actual = target.updateFrom(participantIntakeApi);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    String actual = target.getPrimaryKey();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_A$() throws Exception {
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDateOfBirth_A$() throws Exception {
    Date actual = target.getDateOfBirth();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDateOfDeath_A$() throws Exception {
    Date actual = target.getDateOfDeath();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_A$() throws Exception {
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_A$() throws Exception {
    String actual = target.getGender();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_A$() throws Exception {
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_A$() throws Exception {
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getScreeningId_A$() throws Exception {
    String actual = target.getScreeningId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getScreening_A$() throws Exception {
    ScreeningEntity actual = target.getScreening();
    ScreeningEntity expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyId_A$() throws Exception {
    String actual = target.getLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getRoles_A$() throws Exception {
    String[] actual = target.getRoles();
    String[] expected = new String[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLanguages_A$() throws Exception {
    String[] actual = target.getLanguages();
    String[] expected = new String[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_A$() throws Exception {
    String actual = target.getMiddleName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_A$() throws Exception {
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getRaces_A$() throws Exception {
    String actual = target.getRaces();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEthnicity_A$() throws Exception {
    String actual = target.getEthnicity();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacySourceTable_A$() throws Exception {
    String actual = target.getLegacySourceTable();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSensitive_A$() throws Exception {
    Boolean actual = target.getSensitive();
    Boolean expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSealed_A$() throws Exception {
    Boolean actual = target.getSealed();
    Boolean expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApproximateAge_A$() throws Exception {
    String actual = target.getApproximateAge();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApproximateAgeUnits_A$() throws Exception {
    String actual = target.getApproximateAgeUnits();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setScreeningId_A$String() throws Exception {
    String screeningId = null;
    target.setScreeningId(screeningId);
  }

  @Test
  public void setDateOfBirth_A$Date() throws Exception {
    Date dateOfBirth = mock(Date.class);
    target.setDateOfBirth(dateOfBirth);
  }

  @Test
  public void setDateOfDeath_A$Date() throws Exception {
    Date dateOfDeath = mock(Date.class);
    target.setDateOfDeath(dateOfDeath);
  }

  @Test
  public void setFirstName_A$String() throws Exception {
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void setGender_A$String() throws Exception {
    String gender = null;
    target.setGender(gender);
  }

  @Test
  public void setLastName_A$String() throws Exception {
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void setSsn_A$String() throws Exception {
    String ssn = null;
    target.setSsn(ssn);
  }

  @Test
  public void getScreeningEntity_A$() throws Exception {
    ScreeningEntity actual = target.getScreeningEntity();
    ScreeningEntity expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setScreeningEntity_A$ScreeningEntity() throws Exception {
    ScreeningEntity screeningEntity = mock(ScreeningEntity.class);
    target.setScreeningEntity(screeningEntity);
  }

  @Test
  public void setLegacyId_A$String() throws Exception {
    String legacyId = null;
    target.setLegacyId(legacyId);
  }

  @Test
  public void getCsecs_A$() throws Exception {
    List<CsecEntity> actual = target.getCsecs();
    List<CsecEntity> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCsecs_A$List() throws Exception {
    List<CsecEntity> csecs = new ArrayList<CsecEntity>();
    target.setCsecs(csecs);
  }

  @Test
  public void getSafelySurrenderedBabies_A$() throws Exception {
    SafelySurrenderedBabiesEntity actual = target.getSafelySurrenderedBabies();
    SafelySurrenderedBabiesEntity expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSafelySurrenderedBabies_A$SafelySurrenderedBabiesEntity() throws Exception {
    SafelySurrenderedBabiesEntity safelySurrenderedBabies =
        mock(SafelySurrenderedBabiesEntity.class);
    target.setSafelySurrenderedBabies(safelySurrenderedBabies);
  }

  @Test
  public void setRoles_A$StringArray() throws Exception {
    String[] roles = new String[] {};
    target.setRoles(roles);
  }

  @Test
  public void setLanguages_A$StringArray() throws Exception {
    String[] languages = new String[] {};
    target.setLanguages(languages);
  }

  @Test
  public void setMiddleName_A$String() throws Exception {
    String middleName = null;
    target.setMiddleName(middleName);
  }

  @Test
  public void setNameSuffix_A$String() throws Exception {
    String nameSuffix = null;
    target.setNameSuffix(nameSuffix);
  }

  @Test
  public void setRaces_A$String() throws Exception {
    String races = null;
    target.setRaces(races);
  }

  @Test
  public void setEthnicity_A$String() throws Exception {
    String ethnicity = null;
    target.setEthnicity(ethnicity);
  }

  @Test
  public void setLegacySourceTable_A$String() throws Exception {
    String legacySourceTable = null;
    target.setLegacySourceTable(legacySourceTable);
  }

  @Test
  public void setSensitive_A$Boolean() throws Exception {
    Boolean sensitive = null;
    target.setSensitive(sensitive);
  }

  @Test
  public void setSealed_A$Boolean() throws Exception {
    Boolean sealed = null;
    target.setSealed(sealed);
  }

  @Test
  public void setApproximateAge_A$String() throws Exception {
    String approximateAge = null;
    target.setApproximateAge(approximateAge);
  }

  @Test
  public void setApproximateAgeUnits_A$String() throws Exception {
    String approximateAgeUnits = null;
    target.setApproximateAgeUnits(approximateAgeUnits);
  }

  @Test
  public void toString_A$() throws Exception {
    String actual = target.toString();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void hashCode_A$() throws Exception {
    int actual = target.hashCode();
    int expected = 0;
    assertThat(actual, is(not(expected)));
  }

  @Test
  public void equals_A$Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_A$String() throws Exception {
    String id = null;
    target.setId(id);
  }

  @Test
  public void getRelateScreeningId_A$() throws Exception {
    String actual = target.getRelatedScreeningId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEstimatedDob_A$Boolean() {
    Boolean actual = target.getEstimatedDob();
    Boolean expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
