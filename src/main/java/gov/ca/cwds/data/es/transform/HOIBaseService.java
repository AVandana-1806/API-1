package gov.ca.cwds.data.es.transform;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

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

    final String description = StringUtils
        .trimToNull(SystemCodeCache.global().getSystemCodeShortDescription(governmentEntityType));
    return new SystemCodeDescriptor(governmentEntityType, description == null ? "" : description);
  }

}
