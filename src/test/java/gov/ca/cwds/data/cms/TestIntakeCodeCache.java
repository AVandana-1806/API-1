package gov.ca.cwds.data.cms;

import java.util.ArrayList;
import java.util.List;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;

/**
 * @author CWDS API Team
 */
public class TestIntakeCodeCache implements IntakeCodeCache {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public TestIntakeCodeCache() {
    register();
  }

  @Override
  public List<IntakeLov> getAllLegacySystemCodesForMeta(String metaId) {
    if (SystemCodeCategoryId.COMMERCIALLY_SEXUALLY_EXPLOITED_CHILDREN.equals(metaId)) {
      List<IntakeLov> lovs = new ArrayList<>();
      lovs.add(new IntakeLov(0L, SystemCodeCategoryId.COMMERCIALLY_SEXUALLY_EXPLOITED_CHILDREN,
          null, null, false, null, null, null, null, "At Risk", null));
      return lovs;
    }
    return null;
  }

  @Override
  public Short getLegacySystemCodeForIntakeCode(String metaId, String intakeCode) {
    if ("DSP_RSNC".contains(metaId) && "Abandoned".contains(intakeCode)) {
      return 6351;
    }
    if (SystemCodeCategoryId.STATE_CODE.contains(metaId) && "CA".contains(intakeCode)) {
      return 1828;
    }
    if (SystemCodeCategoryId.ADDRESS_TYPE.contains(metaId) && "Day Care".equals(intakeCode)) {
      return 28;
    }
    if (SystemCodeCategoryId.CROSS_REPORT_METHOD.contains(metaId)
        && "Electronic Report".contains(intakeCode)) {
      return 2095;
    }
    if (SystemCodeCategoryId.LANGUAGE_CODE.contains(metaId) && "English".contains(intakeCode)) {
      return 1253;
    }
    if (SystemCodeCategoryId.LANGUAGE_CODE.contains(metaId) && "Russian".contains(intakeCode)) {
      return 1271;
    }
    if (SystemCodeCategoryId.INJURY_HARM_TYPE.contains(metaId)
        && "General neglect".contains(intakeCode)) {
      return 2178;
    }
    if (SystemCodeCategoryId.REFERRAL_RESPONSE.contains(metaId)
        && "evaluate_out".contains(intakeCode)) {
      return 1519;
    }
    if (SystemCodeCategoryId.COMMUNICATION_METHOD.contains(metaId)
        && "in_person".contains(intakeCode)) {
      return 408;
    }
    if (SystemCodeCategoryId.COMMERCIALLY_SEXUALLY_EXPLOITED_CHILDREN.contains(metaId)
        && "At Risk".contains(intakeCode)) {
      return 6867;
    }
    if (SystemCodeCategoryId.SAFETY_ALERTS.contains(metaId)
        && "Dangerous Animal on Premises".contains(intakeCode)) {
      return 6401;
    }
    return null;
  }

  @Override
  public String getIntakeCodeForLegacySystemCode(Number systemCodeId, String intakeType) {
    if (1828 == systemCodeId.intValue()) {
      return "CA";
    }
    if (32 == systemCodeId.intValue()) {
      return "Residence";
    }
    if (1248 == systemCodeId.intValue()) {
      return "American Sign Language";
    }
    if (1253 == systemCodeId.intValue()) {
      return "English";
    }
    return null;
  }

  @Override
  public List<IntakeLov> getAll() {
    return new ArrayList<>();
  }
}
