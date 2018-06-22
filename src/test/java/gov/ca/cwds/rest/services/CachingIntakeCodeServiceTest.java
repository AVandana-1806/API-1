package gov.ca.cwds.rest.services;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.services.screeningparticipant.IntakeRace;

/***
 * 
 * @author CWDS API Team
 *
 */
public class CachingIntakeCodeServiceTest {

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private CachingIntakeCodeService cachingIntakeCodeService;
  private static IntakeLovDao intakeLovDao;

  /**
  *
  */
  @BeforeClass
  public static void setupClass() {
    intakeLovDao = mock(IntakeLovDao.class);
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
    CachingIntakeCodeService target = new CachingIntakeCodeService(intakeLovDao, 1);
    assertThat(target, notNullValue());
  }

  /**
  *
  */
  @Test
  public void testToGetValidLegacyId() {
    IntakeLov intakeLov = new IntakeLov(1251L, "lang_tpc", "Cambodian", "19", false, "LANG_TPC", "",
        null, "language", "Cambodian", "Cambodian");
    IntakeLov intakeLov1 = new IntakeLov(1253L, "lang_tpc", "English", "07", false, "LANG_TPC", "",
        null, "language", "English", "English");
    List<IntakeLov> lovList = Arrays.asList(intakeLov, intakeLov1);

    when(intakeLovDao.findByLegacyMetaId(any(String.class))).thenReturn(lovList);
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1500);
    Short actualLovCode = cachingIntakeCodeService
        .getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.LANGUAGE_CODE, "Cambodian");
    Assert.assertNotNull(actualLovCode);
    assertThat(actualLovCode, is(equalTo((short) 1251)));
  }

  /**
  *
  */
  @Test
  public void testToWhenCacheNull() {
    when(intakeLovDao.findByLegacyMetaId(null)).thenReturn(new ArrayList<>());
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1500);
    Short actualLovCode = cachingIntakeCodeService
        .getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.LANGUAGE_CODE, "112kfjn");
    Assert.assertNull(actualLovCode);
  }

  /**
  *
  */
  @Test
  public void testToGetAllLegacySystemCodesForMeta() {
    IntakeLov intakeLov = new IntakeLov(1251L, "lang_tpc", "Cambodian", "19", false, "LANG_TPC", "",
        null, "language", "Cambodian", "Cambodian");
    IntakeLov intakeLov1 = new IntakeLov(1253L, "lang_tpc", "English", "07", false, "LANG_TPC", "",
        null, "language", "English", "English");
    List<IntakeLov> lovList = new ArrayList<>(Arrays.asList(intakeLov, intakeLov1));

    when(intakeLovDao.findByLegacyMetaId(SystemCodeCategoryId.LANGUAGE_CODE)).thenReturn(lovList);
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1500);
    when(intakeLovDao.findByLegacyMetaId(SystemCodeCategoryId.LANGUAGE_CODE)).thenReturn(lovList);
    List<IntakeLov> actualLovCode =
        cachingIntakeCodeService.getAllLegacySystemCodesForMeta(SystemCodeCategoryId.LANGUAGE_CODE);
    Assert.assertNotNull(actualLovCode);
    assertThat(actualLovCode.size(), is(equalTo(2)));
  }

  /**
  *
  */
  @Test
  public void testToGetAllLegacySystemCodesForMetaEmpty() {
    when(intakeLovDao.findAll()).thenReturn(new ArrayList<>());
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1500);
    List<IntakeLov> actualLovCode = cachingIntakeCodeService.getAllLegacySystemCodesForMeta("");
    Assert.assertNotNull(actualLovCode);
    assertThat(actualLovCode.size(), is(equalTo(0)));
  }

  /**
   * 
   */
  @Test
  public void testToGetLegacySystemIdForRaceCode() {
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1500);
    IntakeRace intakeRace = new IntakeRace("White", "Central American");
    Short sysId = cachingIntakeCodeService
        .getLegacySystemCodeForRace(SystemCodeCategoryId.ETHNICITY, intakeRace);
    assertThat(sysId, is(equalTo((short) 841)));
  }

  /**
   * 
   */
  @Test
  public void testWhenIntakeCodeConverterNull() {
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1500);
    IntakeRace intakeRace = new IntakeRace("Asian", "Central Indian");
    Short sysId = cachingIntakeCodeService
        .getLegacySystemCodeForRace(SystemCodeCategoryId.ETHNICITY, intakeRace);
    Assert.assertNull(sysId);
  }

  /**
   * 
   */
  @Test
  public void testWhenIntakeCodeNull() {
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1500);
    Short sysId =
        cachingIntakeCodeService.getLegacySystemCodeForRace(SystemCodeCategoryId.ETHNICITY, null);
    Assert.assertNull(sysId);
  }

  /**
   * 
   */
  @Test
  public void testWhenMetaNullAndReturnNull() {
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1500);
    IntakeRace intakeRace = new IntakeRace("Asian", "Central American");
    Short sysId = cachingIntakeCodeService.getLegacySystemCodeForRace(null, intakeRace);
    Assert.assertNull(sysId);
  }

}
