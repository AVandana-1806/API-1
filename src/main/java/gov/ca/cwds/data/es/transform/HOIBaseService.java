package gov.ca.cwds.data.es.transform;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

import org.slf4j.Logger;

/**
 * Common services for HOI service implementations.
 *
 * @author CWDS API Team
 */
@SuppressWarnings("squid:S1609")
public interface HOIBaseService {

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
    return new SystemCodeDescriptor(governmentEntityType, SystemCodeCache.global()
        .getSystemCodeShortDescription(governmentEntityType) == null ? ""
        : SystemCodeCache.global().getSystemCodeShortDescription(governmentEntityType));
  }
}
