package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.services.screeningparticipant.IntakeCodeConverter;
import gov.ca.cwds.rest.services.screeningparticipant.IntakeRace;

/**
 * Intake code cache implementation for list-of-values (LOV).
 * 
 * @author CWDS API Team
 */
public class CachingIntakeCodeService extends IntakeLovService implements IntakeCodeCache {

  private transient LoadingCache<CacheKey, Object> intakeCodeCache;

  private static final long serialVersionUID = 1L;

  /**
   * Default no-arg constructor.
   */
  @SuppressWarnings("unused")
  private CachingIntakeCodeService() {
    // no-opt
  }

  /**
   * Construct the object.
   * 
   * @param intakeLovDao Intake list-of-values (LOV) Dao
   * @param secondsToRefreshCache Seconds after which cache entries will be invalidated for refresh.
   * @param preloadCache load the cache at server startup before requests are accepted
   */
  @Inject
  public CachingIntakeCodeService(IntakeLovDao intakeLovDao, long secondsToRefreshCache,
      boolean preloadCache) {
    super(intakeLovDao);

    final IntakeCodeCacheLoader cacheLoader = new IntakeCodeCacheLoader(this);
    intakeCodeCache = CacheBuilder.newBuilder()
        .refreshAfterWrite(secondsToRefreshCache, TimeUnit.SECONDS).build(cacheLoader);

    if (preloadCache) {
      try {
        intakeCodeCache.putAll(cacheLoader.loadAll());
      } catch (Exception e) {
        LOGGER.error("Error loading intake lovs", e);
        throw new ServiceException(e);
      }

      LOGGER.info("Intake LOV cache size: {}, pre-loaded: {}, refresh after (seconds): {}",
          intakeCodeCache.size(), preloadCache, secondsToRefreshCache);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<IntakeLov> getAllLegacySystemCodesForMeta(String metaId) {
    List<IntakeLov> intakeLov = new ArrayList<>();
    if (StringUtils.isNotBlank(metaId)) {
      CacheKey cacheKey = CacheKey.createForMeta(metaId);
      intakeLov = (List<IntakeLov>) getFromCache(cacheKey);
    }
    return intakeLov;
  }

  @Override
  public Short getLegacySystemCodeForIntakeCode(String metaId, String intakeCode) {
    Short sysId = null;
    List<IntakeLov> intakeLovs = getAllLegacySystemCodesForMeta(metaId);
    Optional<IntakeLov> intakeLovOptional = intakeLovs.stream()
        .filter(intakeLov -> intakeLov.getIntakeCode().equals(intakeCode)).findFirst();
    if (intakeLovOptional.isPresent()) {
      sysId = intakeLovOptional.get().getLegacySystemCodeId().shortValue();
    }

    return sysId;
  }

  @Override
  public Short getLegacySystemCodeForRace(String metaId, IntakeRace intakeRace) {
    Short sysId = null;
    IntakeCodeConverter.IntakeRaceCode intakeCodeCode =
        (intakeRace != null) ? IntakeCodeConverter.IntakeRaceCode.lookUpByIntakeRace(intakeRace)
            : null;
    if (intakeCodeCode != null && StringUtils.isNotBlank(intakeCodeCode.getLegacyValue())
        && StringUtils.isNotBlank(metaId)) {
      sysId = SystemCodeCache.global().getSystemCodeId(intakeCodeCode.getLegacyValue(), metaId);
    }
    return sysId;
  }

  @Override
  public IntakeLov getIntakeLov(Number legacySystemCodeId) {
    if (legacySystemCodeId == null || Integer.valueOf("0").equals(legacySystemCodeId.intValue())) {
      return null;
    }
    return (IntakeLov) getFromCache(CacheKey.createForSystemCode(legacySystemCodeId));
  }

  @Override
  public String getIntakeCodeForLegacySystemCode(Number systemCodeId) {
    String intakeCode = null;
    final IntakeLov intakeLov = getIntakeLov(systemCodeId);
    if (intakeLov != null) {
      intakeCode = intakeLov.getIntakeCode();
    }
    return intakeCode;
  }

  /**
   * Get cached object identified by given cache key.
   * 
   * @param cacheEntryKey Cache key
   * @return Cached object if found, otherwise null.
   */
  private Object getFromCache(CacheKey cacheEntryKey) {
    Object obj = null;
    try {
      obj = intakeCodeCache.get(cacheEntryKey);
    } catch (Exception e) {
      LOGGER.warn("getFromCache -> Unable to load object for key: " + cacheEntryKey, e);
    }

    return obj;
  }

  /**
   * =============================================================================== <br>
   * Cache loader for intake codes. <br>
   * ===============================================================================
   */
  private static class IntakeCodeCacheLoader extends CacheLoader<CacheKey, Object> {

    private IntakeLovService intakeLovService;

    /**
     * Construct the object
     * 
     * @param intakeLovService
     */
    IntakeCodeCacheLoader(IntakeLovService intakeLovService) {
      this.intakeLovService = intakeLovService;
    }

    @Override
    public Object load(CacheKey key) throws Exception {
      Object objectToCache = null;
      if (CacheKey.META_ID_TYPE.equals(key.getType())) {
        List<IntakeLov> intakeCodeList = intakeLovService.loadAllLegacyMetaIds(key.getValue());
        objectToCache = intakeCodeList;
      } else if (CacheKey.SYSTEM_CODE_ID_TYPE.equals(key.getType())) {
        // Add intakeLov objects keyed by Leagcy System Code ID.
        IntakeLov intakeLov = intakeLovService.loadLegacySystemCode(key.getValue());
        objectToCache = intakeLov;
      }
      return objectToCache;
    }

    /**
     * Load all intake LOVs and return.
     * 
     * @return All intake LOVs
     */
    public Map<CacheKey, Object> loadAll() {
      Map<CacheKey, Object> intakeLovMap = new HashMap<>();

      List<IntakeLov> intakeLovs = intakeLovService.loadAll();
      for (IntakeLov intakeLov : intakeLovs) {
        String metaId = intakeLov.getLegacyMeta();
        Long sysCodeId = intakeLov.getLegacySystemCodeId();

        CacheKey metaKey = CacheKey.createForMeta(metaId);
        CacheKey sysCodeKey = CacheKey.createForSystemCode(sysCodeId);

        @SuppressWarnings("unchecked")
        List<IntakeLov> metaLovs = (List<IntakeLov>) intakeLovMap.get(metaKey);
        if (metaLovs == null) {
          metaLovs = new ArrayList<>();
        }

        metaLovs.add(intakeLov);
        intakeLovMap.put(metaKey, metaLovs);
        intakeLovMap.put(sysCodeKey, intakeLov);
      }

      return intakeLovMap;
    }
  }

  /**
   * =============================================================================== <br>
   * Cache key. <br>
   * ===============================================================================
   */
  private static class CacheKey extends ApiObjectIdentity {

    private static final long serialVersionUID = 1L;

    private static final String META_ID_TYPE = "META_ID";
    private static final String SYSTEM_CODE_ID_TYPE = "SYSTEM_CODE_ID";

    private Serializable value;
    private String type;

    private CacheKey(String type, Serializable value) {
      this.type = type;
      this.value = value;
    }

    public Serializable getValue() {
      return value;
    }

    public String getType() {
      return type;
    }

    private static CacheKey createForMeta(Serializable value) {
      return new CacheKey(META_ID_TYPE, value);
    }

    private static CacheKey createForSystemCode(Serializable value) {
      return new CacheKey(SYSTEM_CODE_ID_TYPE, value);
    }
  }

}
