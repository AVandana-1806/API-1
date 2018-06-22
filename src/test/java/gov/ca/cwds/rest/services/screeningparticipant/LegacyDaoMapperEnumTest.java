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
public class LegacyDaoMapperEnumTest {

  /**
   * 
   */
  @Test
  public void type() {
    assertThat(LegacyDaoMapperEnum.class, notNullValue());
  }

  /**
   * 
   */
  @Test
  public void getDaoName_Args__() {
    LegacyDaoMapperEnum target = LegacyDaoMapperEnum.CLIENT;
    String race = target.getDaoName();
    String expected = "ClientDao";
    assertThat(race, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void getTableName_Args__() {
    LegacyDaoMapperEnum target = LegacyDaoMapperEnum.REPORTER;
    String race = target.getTableName();
    String expected = "REPTR_T";
    assertThat(race, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void getTranformerName_Args__() {
    LegacyDaoMapperEnum target = LegacyDaoMapperEnum.COLLATERAL_INDIVIDUAL;
    String race = target.getTranformerName();
    String expected = "CollateralIndividualTranformer";
    assertThat(race, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void findByTableNameNullNull_Args__String() {
    String tableName = null;
    LegacyDaoMapperEnum actual = LegacyDaoMapperEnum.findByTableName(tableName);
    IntakeRaceCode expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void findByTableName_ToGet_DaoName() {
    String tableName = "COLTRL_T";
    String actual = LegacyDaoMapperEnum.findByTableName(tableName).getDaoName();
    String expected = LegacyDaoMapperEnum.COLLATERAL_INDIVIDUAL.getDaoName();
    assertThat(actual, is(equalTo(expected)));
  }

}
