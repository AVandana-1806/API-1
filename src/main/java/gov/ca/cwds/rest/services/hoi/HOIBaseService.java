package gov.ca.cwds.rest.services.hoi;

import org.slf4j.Logger;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

/**
 * Common services for HOI service implementations.
 *
 * @author CWDS API Team
 */
public interface HOIBaseService extends ApiMarker {

  /**
   * Expose the logger for default interface methods
   *
   * @return SLF4J logger
   */
  Logger getLogger();

  default SystemCodeDescriptor constructCounty(Short governmentEntityType) {
    if (governmentEntityType == null) {
      return null;
    }
    return new SystemCodeDescriptor(governmentEntityType,
        SystemCodeCache.global().getSystemCodeShortDescription(governmentEntityType) == null ? ""
            : SystemCodeCache.global().getSystemCodeShortDescription(governmentEntityType));
  }

}
