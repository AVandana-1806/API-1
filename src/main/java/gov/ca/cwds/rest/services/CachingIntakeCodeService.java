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
 * Intake code cache implementation.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("squid:S2160")
public class CachingIntakeCodeService implements IntakeCodeCache {

  private static final long serialVersionUID = 1L;

  private transient List<IntakeLov> intakeLovs = new ArrayList<>();
  private transient Map<Short, List<IntakeLov>> mapBySystemCodeId = new TreeMap<>();
  private transient Map<String, List<IntakeLov>> mapByMeta = new TreeMap<>();

  /**
   * Construct the object.
   * 
   * @param intakeLovDao Intake LOV Dao
   */
  @Inject
  public CachingIntakeCodeService(IntakeLovDao intakeLovDao) {
    this.intakeLovs = intakeLovDao.findAll();

    if (this.intakeLovs == null) {
      this.intakeLovs = new ArrayList<>();
    }

    for (IntakeLov intakeLov : this.intakeLovs) {
      final Long sysCodeId = intakeLov.getLegacySystemCodeId();
      final String meta = StringUtils.lowerCase(intakeLov.getLegacyMeta());

      // populate mapBySystemCodeId map
      if (sysCodeId != null) {
        final Short sysCodeIdShort = sysCodeId.shortValue();
        List<IntakeLov> lovsForSysCode = mapBySystemCodeId.get(sysCodeIdShort);
        if (lovsForSysCode == null) {
          lovsForSysCode = new ArrayList<>();
          mapBySystemCodeId.put(sysCodeIdShort, lovsForSysCode);
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
    List<IntakeLov> intakeLovCodes = new ArrayList<>();
    if (StringUtils.isNotBlank(metaId)) {
      intakeLovCodes = mapByMeta.get(metaId.toLowerCase());
      if (intakeLovCodes == null) {
        intakeLovCodes = new ArrayList<>();
      }
    }
    return intakeLovCodes;
  }

  @Override
  public Short getLegacySystemCodeForIntakeCode(String metaId, String intakeCode) {
    Short sysCodeId = null;

    final List<IntakeLov> intakeLovs = getAllLegacySystemCodesForMeta(metaId);
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
    if (systemCodeId == null) {
      return intakeCodeId;
    }

    Short systemCodeIdShort = systemCodeId.shortValue();
    List<IntakeLov> intakeLovs = mapBySystemCodeId.get(systemCodeIdShort);
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
