package gov.ca.cwds.data.cms;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.cms.SystemMeta;

public class TestSystemCodeCache implements SystemCodeCache {

  private static final long serialVersionUID = 1L;

  private static final SystemCode[] ETHNICITY_CODES = {
      new SystemCode((short) 820, null, "N", "05", "Alaskan Native*", "05", null, "ETHNCTYC",
          "American Indian or Alaskan Native"),
      new SystemCode((short) 821, null, "N", "05", "American Indian*", "06", null, "ETHNCTYC",
          "American Indian or Alaskan Native"),
      new SystemCode((short) 822, null, "N", "13", "Asian Indian*", "N", null, "ETHNCTYC", "Asian"),
      new SystemCode((short) 823, null, "N", "03", "Black*", "03", null, "ETHNCTYC",
          "Black or African American"),
      new SystemCode((short) 824, null, "N", "16", "Cambodian*", "H", null, "ETHNCTYC", "Asian"),
      new SystemCode((short) 825, null, "N", "06", "Chinese*", "C", null, "ETHNCTYC", "Asian"),
      new SystemCode((short) 826, null, "N", "03", "Ethiopian*", "E", null, "ETHNCTYC",
          "Black or African American"),
      new SystemCode((short) 827, null, "N", "07", "Filipino*", "07", null, "ETHNCTYC", "Asian"),
      new SystemCode((short) 828, null, "N", "12", "Guamanian*", "R", null, "ETHNCTYC",
          "Native Hawaiian or Other Pacific Islander"),
      new SystemCode((short) 829, null, "N", "11", "Hawaiian*", "P", null, "ETHNCTYC",
          "Native Hawaiian or Other Pacific Islander"),
      new SystemCode((short) 830, null, "N", "02", "Hispanic", "02", null, "ETHNCTYC", "White"),
      new SystemCode((short) 831, null, "N", "08", "Japanese*", "J", null, "ETHNCTYC", "Asian"),
      new SystemCode((short) 832, null, "N", "09", "Korean*", "K", null, "ETHNCTYC", "Asian"),
      new SystemCode((short) 833, null, "N", "15", "Laotian*", "T", null, "ETHNCTYC", "Asian"),
      new SystemCode((short) 834, null, "Y", "17", "Other Asian/Pacific Islander*", "4", null,
          "ETHNCTYC", ""),
      new SystemCode((short) 835, null, "N", "17", "Hmong*", "04", null, "ETHNCTYC",
          "Native Hawaiian or Other Pacific Islander"),
      new SystemCode((short) 836, null, "N", "17", "Polynesian*", "04", null, "ETHNCTYC",
          "Native Hawaiian or Other Pacific Islander"),
      new SystemCode((short) 837, null, "N", "10", "Samoan*", "M", null, "ETHNCTYC",
          "Native Hawaiian or Other Pacific Islander"),
      new SystemCode((short) 838, null, "N", "14", "Vietnamese*", "V", null, "ETHNCTYC", "Asian"),
      new SystemCode((short) 839, null, "N", "01", "White*", "01", null, "ETHNCTYC", "White"),
      new SystemCode((short) 840, null, "N", "01", "White - Armenian*", "01", null, "ETHNCTYC",
          "White"),
      new SystemCode((short) 841, null, "N", "01", "White - Central American*", "01", null,
          "ETHNCTYC", "White"),
      new SystemCode((short) 842, null, "N", "01", "White - European*", "01", null, "ETHNCTYC",
          "White"),
      new SystemCode((short) 843, null, "N", "01", "White - Middle Eastern*", "01", null,
          "ETHNCTYC", "White"),
      new SystemCode((short) 844, null, "N", "01", "White - Romanian*", "01", null, "ETHNCTYC",
          "White"),
      new SystemCode((short) 3162, null, "N", "02", "Caribbean", "02", null, "ETHNCTYC", "White"),
      new SystemCode((short) 3163, null, "N", "02", "Central American", "01", null, "ETHNCTYC",
          "White"),
      new SystemCode((short) 3164, null, "N", "02", "Mexican", "02", null, "ETHNCTYC", "White"),
      new SystemCode((short) 3165, null, "N", "02", "South American", "02", null, "ETHNCTYC",
          "White"),
      new SystemCode((short) 5922, null, "N", "17", "Other Asian*", "04", null, "ETHNCTYC",
          "Asian"),
      new SystemCode((short) 5923, null, "N", "17", "Other Pacific Islander*", "04", null,
          "ETHNCTYC", "Native Hawaiian or Other Pacific Islander"),
      new SystemCode((short) 6351, null, "N", "99", "Unable to Determine*", "99", null, "ETHNCTYC",
          "Unable to Determine"),
      new SystemCode((short) 6352, null, "N", "99", "Declines to State*", "99", null, "ETHNCTYC",
          "Declines to State"),
      new SystemCode((short) 6453, null, "N", "99", "Other Race Unknown", "99", null, "ETHNCTYC",
          ""),
      new SystemCode((short) 7093, null, "Y", "ZZ", "Unknown at Conversion", "ZZ", null, "ETHNCTYC",
          "")};

  public static final Map<Short, SystemCode> ethnicityMap;

  static {
    final Map<Short, SystemCode> map = new HashMap<>();
    for (SystemCode sc : ETHNICITY_CODES) {
      map.put(sc.getSystemId(), sc);
    }

    ethnicityMap = Collections.unmodifiableMap(map);
  }

  public TestSystemCodeCache() {
    register();
  }

  @Override
  public Set<SystemMeta> getAllSystemMetas() {
    return null;
  }

  @Override
  public Set<SystemCode> getAllSystemCodes() {
    return null;
  }

  @Override
  public SystemCode getSystemCode(Number systemCodeId) {
    return ethnicityMap.get(systemCodeId.shortValue());

    // if (1828 == systemCodeId.intValue()) {
    // return new SystemCode(systemCodeId.shortValue(), null, null, null, "California", "CA", null,
    // null, null);
    // }
    // if (1101 == systemCodeId.intValue()) {
    // return new SystemCode(systemCodeId.shortValue(), null, null, null, "Sacramento", "34", null,
    // null, null);
    // }
    // if (821 == systemCodeId.intValue()) {
    // return new SystemCode(systemCodeId.shortValue(), null, null, "05", "American Indian*", null,
    // null, null, null);
    // }
    // if (3164 == systemCodeId.intValue()) {
    // return new SystemCode(systemCodeId.shortValue(), null, null, "02", "Mexican", null, null,
    // null, null);
    // }
    // if (3162 == systemCodeId.intValue()) {
    // return new SystemCode(systemCodeId.shortValue(), null, null, "02", "Caribbean", null, null,
    // null, null);
    // }
    // return null;
  }

  @Override
  public Set<SystemCode> getSystemCodesForMeta(String metaId) {
    final Set<SystemCode> codes = new HashSet<>();
    if ("GVR_ENTC".equals(metaId)) {
      final short sysCode = 1101;
      SystemCode sacramento =
          new SystemCode(sysCode, null, "N", null, "Sacramento", "34", null, null, null);
      codes.add(sacramento);
    }

    return codes;
  }

  @Override
  public String getSystemCodeShortDescription(Number systemCodeId) {
    return null;
  }

  @Override
  public boolean verifyActiveSystemCodeIdForMeta(Number systemCodeId, String metaId,
      boolean checkCategoryIdValueIsZero) {
    if (456 == systemCodeId.intValue()) {
      return false;
    } else if (6404 == systemCodeId.intValue()) {
      return false;
    } else if (19 == systemCodeId.intValue()) {
      return true;
    }

    if (SystemCodeCategoryId.CROSS_REPORT_METHOD.equals(metaId)) {
      if (2095 == systemCodeId.intValue() || 0 == systemCodeId.intValue()
          || 123 == systemCodeId.intValue() || 1234 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.REFERRAL_RESPONSE.equals(metaId)) {
      if (1520 == systemCodeId.intValue()) {
        return true;
      }
      if (1516 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.COMMUNICATION_METHOD.equals(metaId)) {
      if (409 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.INJURY_HARM_TYPE.equals(metaId)) {
      if (2179 == systemCodeId.intValue() || 5001 == systemCodeId.intValue()
          || 2177 == systemCodeId.intValue() || 2180 == systemCodeId.intValue()
          || 2181 == systemCodeId.intValue()) {
        return true;
      }
      if (2178 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.ADDRESS_TYPE.equals(metaId)) {
      if (32 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.STATE_CODE.equals(metaId)) {
      if (1828 == systemCodeId.intValue() || 1877 == systemCodeId.intValue()
          || 1823 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.COUNTY_CODE.equals(metaId)) {
      if (5542 == systemCodeId.intValue() || 5548 == systemCodeId.intValue()
          || 5550 == systemCodeId.intValue() || 1101 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.LANGUAGE_CODE.equals(metaId)) {
      if (1253 == systemCodeId.intValue() || 1271 == systemCodeId.intValue()
          || 1274 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.CONTACT_LOCATION.equals(metaId)) {
      if (415 == systemCodeId.intValue() || 5524 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.APPROVAL_STATUS_TYPE.equals(metaId)) {
      if (118 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.ETHNICITY.equals(metaId)) {
      if (841 == systemCodeId.intValue() || 3164 == systemCodeId.intValue()
          || 6351 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.GENDER_IDENTITY_TYPE.equals(metaId)) {
      if (7075 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.GENDER_EXPRESSION_TYPE.equals(metaId)) {
      if (7081 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.SEXUAL_ORIENTATION_TYPE.equals(metaId)) {
      if (7066 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.REMOVEF_FROM_CARE_TAKER_TYPE.equals(metaId)) {
      if (1592 == systemCodeId.intValue()) {
        return true;
      }
    }
    if (SystemCodeCategoryId.SAFETY_ALERTS.equals(metaId)) {
      if (6401 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.CLIENT_RELATIONSHIP.equals(metaId)) {
      if (190 == systemCodeId.intValue()) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean verifyActiveLogicalIdForMeta(String logicalId, String metaId) {
    if (SystemCodeCategoryId.COUNTY_CODE.equals(metaId)) {
      if ("23".equals(logicalId) || "34".equals(logicalId) || "01".equals(logicalId)
          || "58".equals(logicalId) || "99".equals(logicalId)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean verifyActiveSystemCodeDescriptionForMeta(String shortDesc, String metaId) {
    if ("djdjskshahfdsa".equals(shortDesc)) {
      return false;
    } else if ("".equals(shortDesc)) {
      return false;
    } else if ("Breasts".equals(shortDesc)) {
      return true;
    }
    return false;
  }

  @Override
  public SystemCodeDescriptor getSystemCodeDescriptor(Number systemCodeId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Short getSystemCodeId(String arg0, String arg1) {
    if (SystemCodeCategoryId.APPROVAL_STATUS_TYPE.equals(arg1)
        && "Request Not Submitted".equals(arg0)) {
      return 118;
    }
    if (SystemCodeCategoryId.ETHNICITY.equals(arg1) && "Mexican".equals(arg0)) {
      return 3164;
    }
    if (SystemCodeCategoryId.ETHNICITY.equals(arg1) && "White - Central American*".equals(arg0)) {
      return 841;
    }
    if (SystemCodeCategoryId.ETHNICITY.equals(arg1) && "Unable to Determine*".equals(arg0)) {
      return 6351;
    }
    return null;
  }
}
