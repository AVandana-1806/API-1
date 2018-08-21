package gov.ca.cwds.rest.api.domain;

import java.util.List;

import gov.ca.cwds.data.persistence.cms.DeferredRegistry;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.data.std.ApiMarker;

/**
 * @author CWDS API Team
 *
 */
public interface IntakeCodeCache extends ApiMarker {

  /**
   * Register this intakes Code cache instance for system-wide use.
   */
  default void register() {
    DeferredRegistry.<IntakeCodeCache>register(IntakeCodeCache.class, this);
  }

  /**
   * Globally available, singleton intakes code cache.
   * 
   * @return singleton intakes code cache
   */
  static IntakeCodeCache global() {
    return DeferredRegistry.<IntakeCodeCache>unwrap(IntakeCodeCache.class);
  }

  /**
   * Get all legacy system code using meta id.
   * 
   * @param metaId - metaId
   * @return All legacy system codes for the metaId.
   */
  List<IntakeLov> getAllLegacySystemCodesForMeta(String metaId);

  /**
   * Get the valid legacy system code identified by meta Id and intake code.
   * 
   * @param metaId - metaId
   * @param intakeCode - intakeCode
   * @return the legacy system code id
   */
  Short getLegacySystemCodeForIntakeCode(String metaId, String intakeCode);

  /**
   * Get the valid intake Code based on the legacy systemId
   * 
   * @param systemCodeId - systemCodeId
   * @param intakeType
   * @return the intake Code
   */
  String getIntakeCodeForLegacySystemCode(Number systemCodeId, String intakeType);

  /**
   * Retrieve all intake codes.
   * 
   * @return All intake codes.
   */
  List<IntakeLov> getAll();

  /**
   * Get number of items in cache.
   * 
   * @return Number of items in cache. If returns -1 then, size is unknown.
   */
  default long getCacheSize() {
    return -1;
  }
}
