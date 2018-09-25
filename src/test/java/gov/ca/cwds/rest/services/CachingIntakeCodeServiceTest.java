package gov.ca.cwds.rest.services;


import static gov.ca.cwds.rest.api.domain.SystemCodeCategoryId.LANGUAGE_CODE;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.util.Doofenshmirtz;

/***
 * 
 * @author CWDS API Team
 */
public class CachingIntakeCodeServiceTest extends Doofenshmirtz<IntakeLov> {

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private IntakeLovDao intakeLovDao;
  private CachingIntakeCodeService target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    intakeLovDao = mock(IntakeLovDao.class);
    target = new CachingIntakeCodeService(intakeLovDao);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void type() throws Exception {
    assertThat(CachingIntakeCodeService.class, notNullValue());
  }

  /**
  *
  */
  @Test
  public void instantiation() {
    assertThat(target, notNullValue());
  }

  /**
  *
  */
  @Test
  public void testToGetValidLegacyId() {
    final IntakeLov intakeLov = new IntakeLov(1251L, LANGUAGE_CODE, "Cambodian", "19", false,
        LANGUAGE_CODE, "", null, "language", "Cambodian", "Cambodian");
    final IntakeLov intakeLov1 = new IntakeLov(1253L, LANGUAGE_CODE, "English", "07", false,
        LANGUAGE_CODE, "", null, "language", "English", "English");
    final List<IntakeLov> lovList = Arrays.asList(intakeLov, intakeLov1);

    when(intakeLovDao.findAll()).thenReturn(lovList);
    target = new CachingIntakeCodeService(intakeLovDao);

    Short actualLovCode =
        target.getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.LANGUAGE_CODE, "Cambodian");
    Assert.assertNotNull(actualLovCode);
    assertThat(actualLovCode, is(equalTo((short) 1251)));
  }

  /**
  *
  */
  @Test
  public void testToWhenCacheNull() {
    when(intakeLovDao.findByLegacyMetaId(null)).thenReturn(new ArrayList<>());
    final Short actualLovCode =
        target.getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.LANGUAGE_CODE, "112kfjn");
    Assert.assertNull(actualLovCode);
  }

  /**
  *
  */
  @Test
  public void testToGetAllLegacySystemCodesForMeta() {
    final IntakeLov intakeLov = new IntakeLov(1251L, LANGUAGE_CODE, "Cambodian", "19", false,
        LANGUAGE_CODE, "", null, "language", "Cambodian", "Cambodian");
    final IntakeLov intakeLov1 = new IntakeLov(1253L, LANGUAGE_CODE, "English", "07", false,
        LANGUAGE_CODE, "", null, "language", "English", "English");
    final List<IntakeLov> lovList = new ArrayList<>(Arrays.asList(intakeLov, intakeLov1));

    when(intakeLovDao.findAll()).thenReturn(lovList);
    target = new CachingIntakeCodeService(intakeLovDao);

    final List<IntakeLov> actualLovCode = target.getAllLegacySystemCodesForMeta(LANGUAGE_CODE);
    Assert.assertNotNull(actualLovCode);
    assertThat(actualLovCode.size(), is(equalTo(2)));
  }

  /**
  *
  */
  @Test
  public void testToGetAllLegacySystemCodesForMetaEmpty() {
    when(intakeLovDao.findAll()).thenReturn(new ArrayList<>());
    final List<IntakeLov> actualLovCode = target.getAllLegacySystemCodesForMeta("");
    Assert.assertNotNull(actualLovCode);
    assertThat(actualLovCode.size(), is(equalTo(0)));
  }

  // /**
  // *
  // */
  // @Test
  // public void testToGetLegacySystemIdForRaceCode() {
  // target = new CachingIntakeCodeService(intakeLovDao);
  // IntakeRace intakeRace = new IntakeRace("White", "Central American");
  // Short sysId = target
  // .getLegacySystemCodeForRace(SystemCodeCategoryId.ETHNICITY, intakeRace);
  // assertThat(sysId, is(equalTo((short) 841)));
  // }

  // /**
  // *
  // */
  // @Test
  // public void testWhenIntakeCodeConverterNull() {
  // target = new CachingIntakeCodeService(intakeLovDao);
  // IntakeRace intakeRace = new IntakeRace("Asian", "Central Indian");
  // Short sysId = target
  // .getLegacySystemCodeForRace(SystemCodeCategoryId.ETHNICITY, intakeRace);
  // Assert.assertNull(sysId);
  // }

  // /**
  // *
  // */
  // @Test
  // public void testWhenIntakeCodeNull() {
  // target = new CachingIntakeCodeService(intakeLovDao, 1500, true);
  // Short sysId =
  // target.getLegacySystemCodeForRace(SystemCodeCategoryId.ETHNICITY, null);
  // Assert.assertNull(sysId);
  // }

  // /**
  // *
  // */
  // @Test
  // public void testWhenMetaNullAndReturnNull() {
  // target = new CachingIntakeCodeService(intakeLovDao, 1500, true);
  // IntakeRace intakeRace = new IntakeRace("Asian", "Central American");
  // Short sysId = target.getLegacySystemCodeForRace(null, intakeRace);
  // Assert.assertNull(sysId);
  // }

}
