package gov.ca.cwds.rest.api.domain;

import java.util.List;

import gov.ca.cwds.data.persistence.cms.DeferredRegistry;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.services.screeningparticipant.IntakeRace;

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
   * Get the valid legacy system code id for the race, built a separate method as race are multiple
   * 
   * @param metaId - metaId
   * @param intakeRace - {@link IntakeRace}
   * @return the race system code
   */
  Short getLegacySystemCodeForRace(String metaId, IntakeRace intakeRace);

  /**
   * Get the valid IntakeLov object using legacy systemId
   * 
   * @param legacySystemCodeId - legacySystemCodeId
   * @return the intakeLov
   */
  IntakeLov getIntakeLov(Number legacySystemCodeId);

  /**
   * Get the valid intake Code based on the legacy systemId
   * 
   * @param systemCodeId - systemCodeId
   * @return the intake Code
   */
  String getIntakeCodeForLegacySystemCode(Number systemCodeId);

}
