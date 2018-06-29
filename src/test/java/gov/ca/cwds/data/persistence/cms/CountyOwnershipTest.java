package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.util.Doofenshmirtz;

/**
 * @author CWDS API Team
 */
public class CountyOwnershipTest extends Doofenshmirtz<CountyOwnership> {

  private String entityId = "1234567ABC";
  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;
  CountyOwnership target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new CountyOwnership();
  }

  /**
   * Constructor test
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(CountyOwnership.class.newInstance(), is(notNullValue()));
  }

  /**
   * persistent constructor
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {
    CountyOwnership vc = validCountyOwnership();
    gov.ca.cwds.data.persistence.cms.CountyOwnership persistent =
        new gov.ca.cwds.data.persistence.cms.CountyOwnership(entityId, vc.getEntityCode(),
            vc.getMultiFlag(), vc.getCounty00Flag(), vc.getCounty01Flag(), vc.getCounty02Flag(),
            vc.getCounty03Flag(), vc.getCounty04Flag(), vc.getCounty05Flag(), vc.getCounty06Flag(),
            vc.getCounty07Flag(), vc.getCounty08Flag(), vc.getCounty09Flag(), vc.getCounty10Flag(),
            vc.getCounty11Flag(), vc.getCounty12Flag(), vc.getCounty13Flag(), vc.getCounty14Flag(),
            vc.getCounty15Flag(), vc.getCounty16Flag(), vc.getCounty17Flag(), vc.getCounty18Flag(),
            vc.getCounty19Flag(), vc.getCounty20Flag(), vc.getCounty21Flag(), vc.getCounty22Flag(),
            vc.getCounty23Flag(), vc.getCounty24Flag(), vc.getCounty25Flag(), vc.getCounty26Flag(),
            vc.getCounty27Flag(), vc.getCounty28Flag(), vc.getCounty29Flag(), vc.getCounty30Flag(),
            vc.getCounty31Flag(), vc.getCounty32Flag(), vc.getCounty33Flag(), vc.getCounty34Flag(),
            vc.getCounty35Flag(), vc.getCounty36Flag(), vc.getCounty37Flag(), vc.getCounty38Flag(),
            vc.getCounty39Flag(), vc.getCounty40Flag(), vc.getCounty41Flag(), vc.getCounty42Flag(),
            vc.getCounty43Flag(), vc.getCounty44Flag(), vc.getCounty45Flag(), vc.getCounty46Flag(),
            vc.getCounty47Flag(), vc.getCounty48Flag(), vc.getCounty49Flag(), vc.getCounty50Flag(),
            vc.getCounty51Flag(), vc.getCounty52Flag(), vc.getCounty53Flag(), vc.getCounty54Flag(),
            vc.getCounty55Flag(), vc.getCounty56Flag(), vc.getCounty57Flag(), vc.getCounty58Flag(),
            vc.getCounty59Flag(), vc.getCounty60Flag(), vc.getCounty61Flag(), vc.getCounty62Flag(),
            vc.getCounty63Flag(), vc.getDeleteDate());

    assertThat(persistent.getEntityId(), is(equalTo(entityId)));
    assertThat(persistent.getEntityCode(), is(equalTo(vc.getEntityCode())));
    assertThat(persistent.getMultiFlag(), is(equalTo(vc.getMultiFlag())));
    assertThat(persistent.getCounty01Flag(), is(equalTo(vc.getCounty01Flag())));
    assertThat(persistent.getCounty02Flag(), is(equalTo(vc.getCounty02Flag())));
    assertThat(persistent.getCounty03Flag(), is(equalTo(vc.getCounty03Flag())));
    assertThat(persistent.getCounty04Flag(), is(equalTo(vc.getCounty04Flag())));
    assertThat(persistent.getCounty05Flag(), is(equalTo(vc.getCounty05Flag())));
    assertThat(persistent.getCounty06Flag(), is(equalTo(vc.getCounty06Flag())));
    assertThat(persistent.getCounty07Flag(), is(equalTo(vc.getCounty07Flag())));
    assertThat(persistent.getCounty08Flag(), is(equalTo(vc.getCounty08Flag())));
    assertThat(persistent.getCounty09Flag(), is(equalTo(vc.getCounty09Flag())));
    assertThat(persistent.getCounty10Flag(), is(equalTo(vc.getCounty10Flag())));
    assertThat(persistent.getCounty11Flag(), is(equalTo(vc.getCounty11Flag())));
    assertThat(persistent.getCounty12Flag(), is(equalTo(vc.getCounty12Flag())));
    assertThat(persistent.getCounty13Flag(), is(equalTo(vc.getCounty13Flag())));
    assertThat(persistent.getCounty14Flag(), is(equalTo(vc.getCounty14Flag())));
    assertThat(persistent.getCounty15Flag(), is(equalTo(vc.getCounty15Flag())));
    assertThat(persistent.getCounty16Flag(), is(equalTo(vc.getCounty16Flag())));
    assertThat(persistent.getCounty17Flag(), is(equalTo(vc.getCounty17Flag())));
    assertThat(persistent.getCounty18Flag(), is(equalTo(vc.getCounty18Flag())));
    assertThat(persistent.getCounty19Flag(), is(equalTo(vc.getCounty19Flag())));
    assertThat(persistent.getCounty20Flag(), is(equalTo(vc.getCounty20Flag())));
    assertThat(persistent.getCounty21Flag(), is(equalTo(vc.getCounty21Flag())));
    assertThat(persistent.getCounty22Flag(), is(equalTo(vc.getCounty22Flag())));
    assertThat(persistent.getCounty23Flag(), is(equalTo(vc.getCounty23Flag())));
    assertThat(persistent.getCounty24Flag(), is(equalTo(vc.getCounty24Flag())));
    assertThat(persistent.getCounty25Flag(), is(equalTo(vc.getCounty25Flag())));
    assertThat(persistent.getCounty26Flag(), is(equalTo(vc.getCounty26Flag())));
    assertThat(persistent.getCounty27Flag(), is(equalTo(vc.getCounty27Flag())));
    assertThat(persistent.getCounty28Flag(), is(equalTo(vc.getCounty28Flag())));
    assertThat(persistent.getCounty29Flag(), is(equalTo(vc.getCounty29Flag())));
    assertThat(persistent.getCounty30Flag(), is(equalTo(vc.getCounty30Flag())));
    assertThat(persistent.getCounty31Flag(), is(equalTo(vc.getCounty31Flag())));
    assertThat(persistent.getCounty32Flag(), is(equalTo(vc.getCounty32Flag())));
    assertThat(persistent.getCounty33Flag(), is(equalTo(vc.getCounty33Flag())));
    assertThat(persistent.getCounty34Flag(), is(equalTo(vc.getCounty34Flag())));
    assertThat(persistent.getCounty35Flag(), is(equalTo(vc.getCounty35Flag())));
    assertThat(persistent.getCounty36Flag(), is(equalTo(vc.getCounty36Flag())));
    assertThat(persistent.getCounty37Flag(), is(equalTo(vc.getCounty37Flag())));
    assertThat(persistent.getCounty38Flag(), is(equalTo(vc.getCounty38Flag())));
    assertThat(persistent.getCounty39Flag(), is(equalTo(vc.getCounty39Flag())));
    assertThat(persistent.getCounty40Flag(), is(equalTo(vc.getCounty40Flag())));
    assertThat(persistent.getCounty41Flag(), is(equalTo(vc.getCounty41Flag())));
    assertThat(persistent.getCounty42Flag(), is(equalTo(vc.getCounty42Flag())));
    assertThat(persistent.getCounty43Flag(), is(equalTo(vc.getCounty43Flag())));
    assertThat(persistent.getCounty44Flag(), is(equalTo(vc.getCounty44Flag())));
    assertThat(persistent.getCounty45Flag(), is(equalTo(vc.getCounty45Flag())));
    assertThat(persistent.getCounty46Flag(), is(equalTo(vc.getCounty46Flag())));
    assertThat(persistent.getCounty47Flag(), is(equalTo(vc.getCounty47Flag())));
    assertThat(persistent.getCounty48Flag(), is(equalTo(vc.getCounty48Flag())));
    assertThat(persistent.getCounty49Flag(), is(equalTo(vc.getCounty49Flag())));
    assertThat(persistent.getCounty50Flag(), is(equalTo(vc.getCounty50Flag())));
    assertThat(persistent.getCounty51Flag(), is(equalTo(vc.getCounty51Flag())));
    assertThat(persistent.getCounty52Flag(), is(equalTo(vc.getCounty52Flag())));
    assertThat(persistent.getCounty53Flag(), is(equalTo(vc.getCounty53Flag())));
    assertThat(persistent.getCounty54Flag(), is(equalTo(vc.getCounty54Flag())));
    assertThat(persistent.getCounty55Flag(), is(equalTo(vc.getCounty55Flag())));
    assertThat(persistent.getCounty56Flag(), is(equalTo(vc.getCounty56Flag())));
    assertThat(persistent.getCounty57Flag(), is(equalTo(vc.getCounty57Flag())));
    assertThat(persistent.getCounty58Flag(), is(equalTo(vc.getCounty58Flag())));
    assertThat(persistent.getCounty59Flag(), is(equalTo(vc.getCounty59Flag())));
    assertThat(persistent.getCounty60Flag(), is(equalTo(vc.getCounty60Flag())));
    assertThat(persistent.getCounty61Flag(), is(equalTo(vc.getCounty61Flag())));
    assertThat(persistent.getCounty62Flag(), is(equalTo(vc.getCounty62Flag())));
    assertThat(persistent.getCounty63Flag(), is(equalTo(vc.getCounty63Flag())));
    assertThat(persistent.getDeleteDate(), is(equalTo(vc.getDeleteDate())));
  }

  private CountyOwnership validCountyOwnership()
      throws JsonParseException, JsonMappingException, IOException {
    CountyOwnership validCountyOwnership = MAPPER.readValue(
        fixture("fixtures/persistent/CountyOwnership/valid/valid.json"), CountyOwnership.class);
    return validCountyOwnership;
  }

  @Test
  public void type() throws Exception {
    assertThat(CountyOwnership.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    Serializable actual = target.getPrimaryKey();
    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEntityId_A$() throws Exception {
    String actual = target.getEntityId();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEntityCode_A$() throws Exception {
    String actual = target.getEntityCode();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMultiFlag_A$() throws Exception {
    String actual = target.getMultiFlag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty00Flag_A$() throws Exception {
    String actual = target.getCounty00Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty01Flag_A$() throws Exception {
    String actual = target.getCounty01Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty02Flag_A$() throws Exception {
    String actual = target.getCounty02Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty03Flag_A$() throws Exception {
    String actual = target.getCounty03Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty04Flag_A$() throws Exception {
    String actual = target.getCounty04Flag();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty05Flag_A$() throws Exception {
    String actual = target.getCounty05Flag();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty06Flag_A$() throws Exception {
    String actual = target.getCounty06Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty07Flag_A$() throws Exception {
    String actual = target.getCounty07Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty08Flag_A$() throws Exception {
    String actual = target.getCounty08Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty09Flag_A$() throws Exception {
    String actual = target.getCounty09Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty10Flag_A$() throws Exception {
    String actual = target.getCounty10Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty11Flag_A$() throws Exception {
    String actual = target.getCounty11Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty12Flag_A$() throws Exception {
    String actual = target.getCounty12Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty13Flag_A$() throws Exception {
    String actual = target.getCounty13Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty14Flag_A$() throws Exception {
    String actual = target.getCounty14Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty15Flag_A$() throws Exception {
    String actual = target.getCounty15Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty16Flag_A$() throws Exception {
    String actual = target.getCounty16Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty17Flag_A$() throws Exception {
    String actual = target.getCounty17Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty18Flag_A$() throws Exception {
    String actual = target.getCounty18Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty19Flag_A$() throws Exception {
    String actual = target.getCounty19Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty20Flag_A$() throws Exception {
    String actual = target.getCounty20Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty21Flag_A$() throws Exception {
    String actual = target.getCounty21Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty22Flag_A$() throws Exception {
    String actual = target.getCounty22Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty23Flag_A$() throws Exception {
    String actual = target.getCounty23Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty24Flag_A$() throws Exception {
    String actual = target.getCounty24Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty25Flag_A$() throws Exception {
    String actual = target.getCounty25Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty26Flag_A$() throws Exception {
    String actual = target.getCounty26Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty27Flag_A$() throws Exception {
    String actual = target.getCounty27Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty28Flag_A$() throws Exception {
    String actual = target.getCounty28Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty29Flag_A$() throws Exception {
    String actual = target.getCounty29Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty30Flag_A$() throws Exception {
    String actual = target.getCounty30Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty31Flag_A$() throws Exception {
    String actual = target.getCounty31Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty32Flag_A$() throws Exception {
    String actual = target.getCounty32Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty33Flag_A$() throws Exception {
    String actual = target.getCounty33Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty34Flag_A$() throws Exception {
    String actual = target.getCounty34Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty35Flag_A$() throws Exception {
    String actual = target.getCounty35Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty36Flag_A$() throws Exception {
    String actual = target.getCounty36Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty37Flag_A$() throws Exception {
    String actual = target.getCounty37Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty38Flag_A$() throws Exception {
    String actual = target.getCounty38Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty39Flag_A$() throws Exception {
    String actual = target.getCounty39Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty40Flag_A$() throws Exception {
    String actual = target.getCounty40Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty41Flag_A$() throws Exception {
    String actual = target.getCounty41Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty42Flag_A$() throws Exception {
    String actual = target.getCounty42Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty43Flag_A$() throws Exception {
    String actual = target.getCounty43Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty44Flag_A$() throws Exception {
    String actual = target.getCounty44Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty45Flag_A$() throws Exception {
    String actual = target.getCounty45Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty46Flag_A$() throws Exception {
    String actual = target.getCounty46Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty47Flag_A$() throws Exception {
    String actual = target.getCounty47Flag();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty48Flag_A$() throws Exception {
    String actual = target.getCounty48Flag();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty49Flag_A$() throws Exception {
    String actual = target.getCounty49Flag();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty50Flag_A$() throws Exception {
    String actual = target.getCounty50Flag();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty51Flag_A$() throws Exception {
    String actual = target.getCounty51Flag();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty52Flag_A$() throws Exception {
    String actual = target.getCounty52Flag();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty53Flag_A$() throws Exception {
    String actual = target.getCounty53Flag();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty54Flag_A$() throws Exception {
    String actual = target.getCounty54Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty55Flag_A$() throws Exception {
    String actual = target.getCounty55Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty56Flag_A$() throws Exception {
    String actual = target.getCounty56Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty57Flag_A$() throws Exception {
    String actual = target.getCounty57Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty58Flag_A$() throws Exception {
    String actual = target.getCounty58Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty59Flag_A$() throws Exception {
    String actual = target.getCounty59Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty60Flag_A$() throws Exception {
    String actual = target.getCounty60Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty61Flag_A$() throws Exception {
    String actual = target.getCounty61Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty62Flag_A$() throws Exception {
    String actual = target.getCounty62Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty63Flag_A$() throws Exception {
    String actual = target.getCounty63Flag();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDeleteDate_A$() throws Exception {
    Date actual = target.getDeleteDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setEntityId_A$String() throws Exception {
    String entityId = null;
    target.setEntityId(entityId);
  }

  @Test
  public void setEntityCode_A$String() throws Exception {
    String entityCode = null;
    target.setEntityCode(entityCode);
  }

  @Test
  public void setMultiFlag_A$String() throws Exception {
    String multiFlag = null;
    target.setMultiFlag(multiFlag);
  }

  @Test
  public void setCounty00Flag_A$String() throws Exception {
    String county00Flag = null;
    target.setCounty00Flag(county00Flag);
  }

  @Test
  public void setCounty01Flag_A$String() throws Exception {
    String county01Flag = null;
    target.setCounty01Flag(county01Flag);
  }

  @Test
  public void setCounty02Flag_A$String() throws Exception {
    String county02Flag = null;
    target.setCounty02Flag(county02Flag);
  }

  @Test
  public void setCounty03Flag_A$String() throws Exception {
    String county03Flag = null;
    target.setCounty03Flag(county03Flag);
  }

  @Test
  public void setCounty04Flag_A$String() throws Exception {
    String county04Flag = null;
    target.setCounty04Flag(county04Flag);
  }

  @Test
  public void setCounty05Flag_A$String() throws Exception {
    String county05Flag = null;
    target.setCounty05Flag(county05Flag);
  }

  @Test
  public void setCounty06Flag_A$String() throws Exception {
    String county06Flag = null;
    target.setCounty06Flag(county06Flag);
  }

  @Test
  public void setCounty07Flag_A$String() throws Exception {
    String county07Flag = null;
    target.setCounty07Flag(county07Flag);
  }

  @Test
  public void setCounty08Flag_A$String() throws Exception {
    String county08Flag = null;
    target.setCounty08Flag(county08Flag);
  }

  @Test
  public void setCounty09Flag_A$String() throws Exception {
    String county09Flag = null;
    target.setCounty09Flag(county09Flag);
  }

  @Test
  public void setCounty10Flag_A$String() throws Exception {
    String county10Flag = null;
    target.setCounty10Flag(county10Flag);
  }

  @Test
  public void setCounty11Flag_A$String() throws Exception {
    String county11Flag = null;
    target.setCounty11Flag(county11Flag);
  }

  @Test
  public void setCounty12Flag_A$String() throws Exception {
    String county12Flag = null;
    target.setCounty12Flag(county12Flag);
  }

  @Test
  public void setCounty13Flag_A$String() throws Exception {
    String county13Flag = null;
    target.setCounty13Flag(county13Flag);
  }

  @Test
  public void setCounty14Flag_A$String() throws Exception {
    String county14Flag = null;
    target.setCounty14Flag(county14Flag);
  }

  @Test
  public void setCounty15Flag_A$String() throws Exception {
    String county15Flag = null;
    target.setCounty15Flag(county15Flag);
  }

  @Test
  public void setCounty16Flag_A$String() throws Exception {
    String county16Flag = null;
    target.setCounty16Flag(county16Flag);
  }

  @Test
  public void setCounty17Flag_A$String() throws Exception {
    String county17Flag = null;
    target.setCounty17Flag(county17Flag);
  }

  @Test
  public void setCounty18Flag_A$String() throws Exception {
    String county18Flag = null;
    target.setCounty18Flag(county18Flag);
  }

  @Test
  public void setCounty19Flag_A$String() throws Exception {
    String county19Flag = null;
    target.setCounty19Flag(county19Flag);
  }

  @Test
  public void setCounty20Flag_A$String() throws Exception {
    String county20Flag = null;
    target.setCounty20Flag(county20Flag);
  }

  @Test
  public void setCounty21Flag_A$String() throws Exception {
    String county21Flag = null;
    target.setCounty21Flag(county21Flag);
  }

  @Test
  public void setCounty22Flag_A$String() throws Exception {
    String county22Flag = null;
    target.setCounty22Flag(county22Flag);
  }

  @Test
  public void setCounty23Flag_A$String() throws Exception {
    String county23Flag = null;
    target.setCounty23Flag(county23Flag);
  }

  @Test
  public void setCounty24Flag_A$String() throws Exception {
    String county24Flag = null;
    target.setCounty24Flag(county24Flag);
  }

  @Test
  public void setCounty25Flag_A$String() throws Exception {
    String county25Flag = null;
    target.setCounty25Flag(county25Flag);
  }

  @Test
  public void setCounty26Flag_A$String() throws Exception {
    String county26Flag = null;
    target.setCounty26Flag(county26Flag);
  }

  @Test
  public void setCounty27Flag_A$String() throws Exception {
    String county27Flag = null;
    target.setCounty27Flag(county27Flag);
  }

  @Test
  public void setCounty28Flag_A$String() throws Exception {
    String county28Flag = null;
    target.setCounty28Flag(county28Flag);
  }

  @Test
  public void setCounty29Flag_A$String() throws Exception {
    String county29Flag = null;
    target.setCounty29Flag(county29Flag);
  }

  @Test
  public void setCounty30Flag_A$String() throws Exception {
    String county30Flag = null;
    target.setCounty30Flag(county30Flag);
  }

  @Test
  public void setCounty31Flag_A$String() throws Exception {
    String county31Flag = null;
    target.setCounty31Flag(county31Flag);
  }

  @Test
  public void setCounty32Flag_A$String() throws Exception {
    String county32Flag = null;
    target.setCounty32Flag(county32Flag);
  }

  @Test
  public void setCounty33Flag_A$String() throws Exception {
    String county33Flag = null;
    target.setCounty33Flag(county33Flag);
  }

  @Test
  public void setCounty34Flag_A$String() throws Exception {
    String county34Flag = null;
    target.setCounty34Flag(county34Flag);
  }

  @Test
  public void setCounty35Flag_A$String() throws Exception {
    String county35Flag = null;
    target.setCounty35Flag(county35Flag);
  }

  @Test
  public void setCounty36Flag_A$String() throws Exception {
    String county36Flag = null;
    target.setCounty36Flag(county36Flag);
  }

  @Test
  public void setCounty37Flag_A$String() throws Exception {
    String county37Flag = null;
    target.setCounty37Flag(county37Flag);
  }

  @Test
  public void setCounty38Flag_A$String() throws Exception {
    String county38Flag = null;
    target.setCounty38Flag(county38Flag);
  }

  @Test
  public void setCounty39Flag_A$String() throws Exception {
    String county39Flag = null;
    target.setCounty39Flag(county39Flag);
  }

  @Test
  public void setCounty40Flag_A$String() throws Exception {
    String county40Flag = null;
    target.setCounty40Flag(county40Flag);
  }

  @Test
  public void setCounty41Flag_A$String() throws Exception {
    String county41Flag = null;
    target.setCounty41Flag(county41Flag);
  }

  @Test
  public void setCounty42Flag_A$String() throws Exception {
    String county42Flag = null;
    target.setCounty42Flag(county42Flag);
  }

  @Test
  public void setCounty43Flag_A$String() throws Exception {
    String county43Flag = null;
    target.setCounty43Flag(county43Flag);
  }

  @Test
  public void setCounty44Flag_A$String() throws Exception {
    String county44Flag = null;
    target.setCounty44Flag(county44Flag);
  }

  @Test
  public void setCounty45Flag_A$String() throws Exception {
    String county45Flag = null;
    target.setCounty45Flag(county45Flag);
  }

  @Test
  public void setCounty46Flag_A$String() throws Exception {
    String county46Flag = null;
    target.setCounty46Flag(county46Flag);
  }

  @Test
  public void setCounty47Flag_A$String() throws Exception {
    String county47Flag = null;
    target.setCounty47Flag(county47Flag);
  }

  @Test
  public void setCounty48Flag_A$String() throws Exception {
    String county48Flag = null;
    target.setCounty48Flag(county48Flag);
  }

  @Test
  public void setCounty49Flag_A$String() throws Exception {
    String county49Flag = null;
    target.setCounty49Flag(county49Flag);
  }

  @Test
  public void setCounty50Flag_A$String() throws Exception {
    String county50Flag = null;
    target.setCounty50Flag(county50Flag);
  }

  @Test
  public void setCounty51Flag_A$String() throws Exception {
    String county51Flag = null;
    target.setCounty51Flag(county51Flag);
  }

  @Test
  public void setCounty52Flag_A$String() throws Exception {
    String county52Flag = null;
    target.setCounty52Flag(county52Flag);
  }

  @Test
  public void setCounty53Flag_A$String() throws Exception {
    String county53Flag = null;
    target.setCounty53Flag(county53Flag);
  }

  @Test
  public void setCounty54Flag_A$String() throws Exception {
    String county54Flag = null;
    target.setCounty54Flag(county54Flag);
  }

  @Test
  public void setCounty55Flag_A$String() throws Exception {
    String county55Flag = null;
    target.setCounty55Flag(county55Flag);
  }

  @Test
  public void setCounty56Flag_A$String() throws Exception {
    String county56Flag = null;
    target.setCounty56Flag(county56Flag);
  }

  @Test
  public void setCounty57Flag_A$String() throws Exception {
    String county57Flag = null;
    target.setCounty57Flag(county57Flag);
  }

  @Test
  public void setCounty58Flag_A$String() throws Exception {
    String county58Flag = null;
    target.setCounty58Flag(county58Flag);
  }

  @Test
  public void setCounty59Flag_A$String() throws Exception {
    String county59Flag = null;
    target.setCounty59Flag(county59Flag);
  }

  @Test
  public void setCounty60Flag_A$String() throws Exception {
    String county60Flag = null;
    target.setCounty60Flag(county60Flag);
  }

  @Test
  public void setCounty61Flag_A$String() throws Exception {
    String county61Flag = null;
    target.setCounty61Flag(county61Flag);
  }

  @Test
  public void setCounty62Flag_A$String() throws Exception {
    String county62Flag = null;
    target.setCounty62Flag(county62Flag);
  }

  @Test
  public void setCounty63Flag_A$String() throws Exception {
    String county63Flag = null;
    target.setCounty63Flag(county63Flag);
  }

  @Test
  public void setDeleteDate_A$Date() throws Exception {
    Date deleteDate = new Date();
    target.setDeleteDate(deleteDate);
  }

}
