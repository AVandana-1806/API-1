package gov.ca.cwds.rest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;

/**
 * Intake code cache Implementation
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("squid:S2160")
public class CachingIntakeCodeService implements IntakeCodeCache {

  private transient List<IntakeLov> intakeLovs = new ArrayList<>();
  private transient Map<Number, List<IntakeLov>> mapBySystemCodeId = new TreeMap<>();
  private transient Map<String, List<IntakeLov>> mapByMeta = new TreeMap<>();

  private static final long serialVersionUID = 1L;

  /**
   * Construct the object.
   * 
   * @param intakeLovDao Intake Lov Dao
   */
  @Inject
  public CachingIntakeCodeService(IntakeLovDao intakeLovDao) {
    intakeLovs = intakeLovDao.findAll();

    if (intakeLovs == null) {
      intakeLovs = new ArrayList<>();
    }

    for (IntakeLov intakeLov : intakeLovs) {
      Number sysCodeId = intakeLov.getLegacySystemCodeId();
      String meta = StringUtils.lowerCase(intakeLov.getLegacyMeta());

      // populate mapBySystemCodeId map
      if (sysCodeId != null) {
        List<IntakeLov> lovsForSysCode = mapBySystemCodeId.get(sysCodeId);
        if (lovsForSysCode == null) {
          lovsForSysCode = new ArrayList<>();
          mapBySystemCodeId.put(sysCodeId, lovsForSysCode);
        }
        lovsForSysCode.add(intakeLov);
      }

      // populate mapByMeta map
      if (StringUtils.isNotBlank(meta)) {
        List<IntakeLov> lovsForMeta = mapByMeta.get(meta);
        if (lovsForMeta == null) {
          lovsForMeta = new ArrayList<>();
          mapByMeta.put(meta, lovsForMeta);
        }
        lovsForMeta.add(intakeLov);
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<IntakeLov> getAllLegacySystemCodesForMeta(String metaId) {
    List<IntakeLov> intakeLovs = new ArrayList<>();
    if (StringUtils.isNotBlank(metaId)) {
      intakeLovs = mapByMeta.get(metaId.toLowerCase());
      if (intakeLovs == null) {
        intakeLovs = new ArrayList<>();
      }
    }
    return intakeLovs;
  }

  @Override
  public Short getLegacySystemCodeForIntakeCode(String metaId, String intakeCode) {
    Short sysCodeId = null;

    List<IntakeLov> intakeLovs = getAllLegacySystemCodesForMeta(metaId);
    for (IntakeLov lov : intakeLovs) {
      if (StringUtils.equalsIgnoreCase(intakeCode, lov.getIntakeCode())) {
        sysCodeId = lov.getLegacySystemCodeId().shortValue();
        break;
      }
    }

    return sysCodeId;
  }

  @Override
  public String getIntakeCodeForLegacySystemCode(Number systemCodeId, String intakeType) {
    String intakeCodeId = null;

    List<IntakeLov> intakeLovs = mapBySystemCodeId.get(systemCodeId);
    if (intakeLovs != null) {
      for (IntakeLov lov : intakeLovs) {
        if (StringUtils.equalsIgnoreCase(intakeType, lov.getIntakeType())) {
          intakeCodeId = lov.getIntakeCode();
          break;
        }
      }
    }

    return intakeCodeId;
  }

  @Override
  public List<IntakeLov> getAll() {
    return this.intakeLovs;
  }

  @Override
  public long getCacheSize() {
    return this.intakeLovs.size();
  }
}
