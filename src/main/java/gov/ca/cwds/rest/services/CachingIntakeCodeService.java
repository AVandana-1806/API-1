package gov.ca.cwds.rest.services;

import static gov.ca.cwds.rest.core.Api.Datasource.NS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Intake code cache implementation.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"findbugs:EQ_DOESNT_OVERRIDE_EQUALS",
    "serial", "squid:S2095", "squid:S2160", "squid:S1206", "squid:S1948"})
@SuppressFBWarnings("SE_TRANSIENT_FIELD_NOT_RESTORED")
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
    init(intakeLovDao);
  }

  @UnitOfWork(value = NS, cacheMode = CacheMode.NORMAL, flushMode = FlushMode.MANUAL,
      readOnly = true, transactional = false)
  protected final void init(IntakeLovDao intakeLovDao) {
    this.intakeLovs = intakeLovDao.findAll();

    if (this.intakeLovs == null) {
      this.intakeLovs = new ArrayList<>();
    }

    for (IntakeLov intakeLov : this.intakeLovs) {
      Long sysCodeId = intakeLov.getLegacySystemCodeId();
      String meta = StringUtils.lowerCase(intakeLov.getLegacyMeta());

      // populate mapBySystemCodeId map
      if (sysCodeId != null) {
        Short sysCodeIdShort = sysCodeId.shortValue();
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

    final List<IntakeLov> theLovs = getAllLegacySystemCodesForMeta(metaId);
    for (IntakeLov lov : theLovs) {
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
    final List<IntakeLov> theLovs = mapBySystemCodeId.get(systemCodeIdShort);
    if (theLovs != null) {
      for (IntakeLov lov : theLovs) {
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
