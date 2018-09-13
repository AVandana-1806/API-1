package gov.ca.cwds.rest.services.auth;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;

import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.auth.AuthorizationRequest;
import gov.ca.cwds.rest.api.domain.auth.AuthorizationResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.security.annotations.Authorize;

/**
 * Service to check that logged-in staff person is authorized to access entities.
 * 
 * @author CWDS API Team
 */
public class AuthorizationService
    extends SimpleResourceService<String, AuthorizationRequest, AuthorizationResponse> {

  /**
   * Constructor
   */
  public AuthorizationService() {
    // No-op
  }

  /**
   * Check if logged in user is authorized to access requested entity
   */
  @Override
  protected AuthorizationResponse handleRequest(AuthorizationRequest request) {
    if (request != null && StringUtils.isNotBlank(request.getId())) {
      ensureClientAccessAuthorized(request.getId());
    }
    return new AuthorizationResponse();
  }

  /**
   * Check if logged in user is authorized to access client identified by given clientId, throws
   * AuthorizationException if it is not.
   * 
   * @param clientId The client id
   * @throws AuthorizationException If client identified by given clientId is not accessible to
   *         logged in user.
   */
  public void ensureClientAccessAuthorized(@Authorize("client:read:clientId") String clientId) {
    // Authorizer annotation does the work
  }

  public Collection<String> filterClientIds(
      @Authorize("client:read:clientIds") Collection<String> clientIds) {
    // Authorizer annotation does the work.
    return clientIds;
  }

  /**
   * Check if logged in user is authorized to access clients identified by given clientIds, throws
   * AuthorizationException if any client is not authorized for staff person.
   * 
   * @param clientIds The client ids
   * @throws AuthorizationException If any client identified by given clientIds is not accessible to
   *         logged in user.
   */
  public void ensureClientAccessAuthorized(Collection<String> clientIds) {
    if (clientIds != null && !clientIds.isEmpty()) {
      final Collection<String> filteredClientIds = filterClientIds(clientIds);
      if (clientIds.size() != filteredClientIds.size()) {
        throw new UnauthorizedException();
      }
    }
  }

  /**
   * Check if logged in user is authorized to access given screening and all participants, throws
   * AuthorizationException if not authorized.
   * 
   * @param screeningEntity The screening
   * @throws AuthorizationException If given screening not accessible to logged in user.
   */
  public void ensureScreeningAccessAuthorized(
      @Authorize("screening:read:screeningEntity") ScreeningEntity screeningEntity) {

    if (screeningEntity == null) {
      return;
    }

    // Check participants
    final Set<ParticipantEntity> participants = screeningEntity.getParticipants();
    if (participants != null) {
      for (ParticipantEntity participant : participants) {
        final String participantId = participant.getLegacyId();
        if (StringUtils.isNotBlank(participantId)) {
          ensureClientAccessAuthorized(participantId);
        }
      }
    }
  }

  /**
   * This method in not implemented
   */
  @Override
  protected AuthorizationResponse handleFind(String arg0) {
    throw new UnsupportedOperationException();
  }
}
