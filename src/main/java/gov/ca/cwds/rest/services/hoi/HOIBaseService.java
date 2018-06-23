package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.security.annotations.Authorize;
import java.util.Collection;

import org.slf4j.Logger;

/**
 * Common services for HOI service implementations.
 *
 * @author CWDS API Team
 */
public interface HOIBaseService {

  /**
   * Expose the logger for default interface methods
   *
   * @return SLF4J logger
   */
  Logger getLogger();

  /**
   * SNAP-49: HOI not shown for client.
   *
   * <p>
   * Sometimes Cases or Referrals link to clients that the current user is not authorized to view
   * due to sealed/sensitivity restriction, county access privileges, or a short-coming with
   * authorization rules. The client authorizer throws an UnauthorizedException, then skip that
   * client and move on. Don't bomb all History of Involvement because the user is not authorized to
   * view a client's half-sister's foster sibling.
   * </p>
   *
   * @param clientIds client keys to authorize
   * @return collection of client id's that the user is authorized to view
   */
  default Collection<String> authorizeClientIds(
      @Authorize("client:read:clientIds") Collection<String> clientIds) {
    return clientIds;
  }

  default SystemCodeDescriptor constructCounty(Short governmentEntityType) {
    if (governmentEntityType == null) {
      return null;
    }
    return new SystemCodeDescriptor(governmentEntityType, SystemCodeCache.global()
        .getSystemCodeShortDescription(governmentEntityType) == null ? ""
        : SystemCodeCache.global().getSystemCodeShortDescription(governmentEntityType));
  }
}
