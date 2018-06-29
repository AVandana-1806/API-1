package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.rest.services.auth.AuthorizationService;

/**
 * <p>
 * This service handles user requests to fetch all the clients' referrals.
 * </p>
 *
 * @author CWDS API Team
 */
public class HOIReferralService extends
    SimpleResourceService<HOIRequest, HOIReferral, HOIReferralResponse> implements HOIBaseService {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(HOIReferralService.class);

  private transient AuthorizationService authorizationService;
  private transient ReferralClientDao referralClientDao;
  private transient ReferralDao referralDao;
  private transient StaffPersonDao staffPersonDao;
  private transient AllegationDao allegationDao;

  /**
   * Preferred constructor.
   *
   * @param authorizationService - authorizationService
   * @param referralClientDao - referralClientDao
   * @param referralDao - referralDao
   * @param staffPersonDao - staffPersonDao
   * @param allegationDao - allegationDao
   */
  @Inject
  public HOIReferralService(AuthorizationService authorizationService,
      ReferralClientDao referralClientDao, ReferralDao referralDao, StaffPersonDao staffPersonDao,
      AllegationDao allegationDao) {
    super();
    this.authorizationService = authorizationService;
    this.referralClientDao = referralClientDao;
    this.referralDao = referralDao;
    this.staffPersonDao = staffPersonDao;
    this.allegationDao = allegationDao;
  }

  @Override
  public HOIReferralResponse handleFind(HOIRequest hoiRequest) {
    final List<String> authorizedClientIds = authorizeClients(hoiRequest.getClientIds());
    if (authorizedClientIds.isEmpty()) {
      return new HOIReferralResponse();
    }
    final ReferralClient[] referralClients = referralClientDao.findByClientIds(authorizedClientIds);
    if (referralClients.length == 0) {
      return new HOIReferralResponse();
    }

    final HOIReferralsData hrd = createHOIReferralsData(referralClients);
    loadReferralsWithReporters(hrd);
    loadStaffPersons(hrd);
    loadAllegationsWithClients(hrd);

    final List<HOIReferral> hoiReferrals = new ArrayList<>(hrd.getReferrals().size());
    final HOIReferralFactory hoiReferralFactory = new HOIReferralFactory();
    for (Referral referral : hrd.getReferrals().values()) {
      final SystemCodeDescriptor county = constructCounty(referral.getGovtEntityType());
      final HOIReferral hoiReferral = hoiReferralFactory.createHOIReferral(referral,
          hrd.getReferralsSelfReportedIndicators().get(referral.getId()), county);
      hoiReferrals.add(hoiReferral);
    }
    hoiReferrals.sort((r1, r2) -> r2.getStartDate().compareTo(r1.getStartDate()));
    return new HOIReferralResponse(hoiReferrals);
  }

  private HOIReferralsData createHOIReferralsData(ReferralClient[] referralClients) {
    final Collection<String> referralIds = new HashSet<>();
    final Map<String, Boolean> referralsSelfReportedIndicators = new HashMap<>();

    for (ReferralClient referralClient : referralClients) {
      String referralId = referralClient.getReferralId();
      referralIds.add(referralId);
      if (!referralsSelfReportedIndicators.containsKey(referralId)) {
        referralsSelfReportedIndicators.put(referralId, Boolean.FALSE);
      }
      final Boolean isSelfReported = referralsSelfReportedIndicators.get(referralId)
          || "Y".equals(referralClient.getSelfReportedIndicator());
      referralsSelfReportedIndicators.put(referralId, isSelfReported);
    }

    final HOIReferralsData hrd = new HOIReferralsData();
    hrd.setReferralIds(referralIds);
    hrd.setReferralsSelfReportedIndicators(referralsSelfReportedIndicators);
    return hrd;
  }

  private void loadReferralsWithReporters(HOIReferralsData hrd) {
    hrd.setReferrals(referralDao.findReferralsWithReportersByIds(hrd.getReferralIds()));
  }

  private void loadStaffPersons(HOIReferralsData hrd) {
    Collection<String> staffPersonIds =
        hrd.getReferrals().values().stream().map(Referral::getPrimaryContactStaffPersonId)
            .filter(Objects::nonNull).collect(Collectors.toSet());
    Map<String, StaffPerson> staffPersonsMap = staffPersonDao.findByIds(staffPersonIds);
    for (Referral referral : hrd.getReferrals().values()) {
      String staffPersonId = referral.getPrimaryContactStaffPersonId();
      referral.setStaffPerson(staffPersonsMap.get(staffPersonId));
    }
  }

  private void loadAllegationsWithClients(HOIReferralsData hrd) {
    Map<String, Set<Allegation>> referralAllegationsMap =
        allegationDao.findAllegationsWithClientsByReferralIds(hrd.getReferralIds());
    for (Referral referral : hrd.getReferrals().values()) {
      referral.setAllegations(referralAllegationsMap.getOrDefault(referral.getId(), null));
    }
  }

  @Override
  public HOIReferralResponse handleRequest(HOIReferral req) {
    throw new NotImplementedException("handle request not implemented");
  }

  @Override
  public AuthorizationService getAuthorizationService() {
    return authorizationService;
  }

  @Override
  public Logger getLogger() {
    return LOGGER;
  }

}
