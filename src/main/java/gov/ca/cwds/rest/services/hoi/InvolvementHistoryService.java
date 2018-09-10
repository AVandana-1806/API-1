package gov.ca.cwds.rest.services.hoi;

import static gov.ca.cwds.rest.core.Api.DS_CMS;
import static gov.ca.cwds.rest.core.Api.DS_CMS_REP;
import static gov.ca.cwds.rest.core.Api.DS_NS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.FlushMode;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Business layer object to work on Screening History Of Involvement.
 *
 * @author CWDS API Team
 */
public class InvolvementHistoryService
    implements TypedCrudsService<String, InvolvementHistory, Response> {

  @Inject
  private ParticipantDao participantDao;

  @Inject
  private HOICaseService hoiCaseService;

  @Inject
  private HOIReferralService hoiReferralService;

  @Inject
  private HOIScreeningService hoiScreeningService;

  public InvolvementHistoryService() {
    super();
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response find(String screeningId) {
    return findInvolvementHistory(new InvolvementHistoryData(screeningId));
  }

  /**
   * @param clientIds - clientIds
   * @return the history of involvement for the clients
   */
  public InvolvementHistory findInvolvementHistoryByClientIds(Collection<String> clientIds) {
    return findInvolvementHistory(new InvolvementHistoryData(clientIds));
  }

  @UnitOfWork(value = DS_CMS_REP, readOnly = true, transactional = false,
      flushMode = FlushMode.MANUAL)
  @SuppressWarnings("WeakerAccess") // can't be private because the @UnitOfWork will not play
  protected InvolvementHistory findInvolvementHistory(InvolvementHistoryData ihd) {
    loadDataFromNS(ihd);
    loadDataFromCMS(ihd);
    buildHoiScreenings(ihd);
    return new InvolvementHistory(ihd.getScreeningId(), ihd.getHoiCases(), ihd.getHoiReferrals(),
        ihd.getHoiScreenings());
  }

  @UnitOfWork(value = DS_NS, readOnly = true, transactional = false, flushMode = FlushMode.MANUAL)
  @SuppressWarnings("WeakerAccess") // can't be private because the @UnitOfWork will not play
  protected void loadDataFromNS(InvolvementHistoryData ihd) {
    final HOIScreeningData hsd = ihd.getHoiScreeningData();
    if (hsd.getClientIds().isEmpty() && ihd.getScreeningId() != null) {
      // load client ID-s by incoming screening ID-s
      hsd.setClientIds(participantDao.findLegacyIdListByScreeningId(ihd.getScreeningId()));
    }
    if (!hsd.getClientIds().isEmpty()) {
      hoiScreeningService.fetchDataFromNS(hsd);
    }
  }

  @UnitOfWork(value = DS_CMS, readOnly = true, transactional = false, flushMode = FlushMode.MANUAL)
  @SuppressWarnings("WeakerAccess") // can't be private because the @UnitOfWork will not play
  protected void loadDataFromCMS(InvolvementHistoryData ihd) {
    final HOIScreeningData hsd = ihd.getHoiScreeningData();
    if (!hsd.getClientIds().isEmpty()) {
      hoiScreeningService.fetchDataFromCMS(hsd);
      final HOIRequest hoiRequest = new HOIRequest(hsd.getClientIds());
      ihd.setHoiCases(hoiCaseService.handleFind(hoiRequest).getHoiCases());
      ihd.setHoiReferrals(hoiReferralService.handleFind(hoiRequest).getHoiReferrals());
    }
  }

  private void buildHoiScreenings(InvolvementHistoryData ihd) {
    final Set<HOIScreening> hoiScreeningSet =
        hoiScreeningService.buildHoiScreenings(ihd.getHoiScreeningData());
    final List<HOIScreening> hoiScreenings = new ArrayList<>(hoiScreeningSet);
    if (ihd.getScreeningId() != null) {
      // exclude the screening with the incoming screening ID
      hoiScreenings.removeIf(screening -> screening.getId().equals(ihd.getScreeningId()));
    }
    ihd.setHoiScreenings(hoiScreenings);
  }

  @Override
  public Response create(InvolvementHistory request) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response update(String primaryKey, InvolvementHistory request) {
    throw new NotImplementedException("update not implemented");
  }

}
