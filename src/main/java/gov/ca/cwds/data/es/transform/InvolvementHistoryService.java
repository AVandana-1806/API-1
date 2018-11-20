package gov.ca.cwds.data.es.transform;

import static gov.ca.cwds.rest.core.Api.DS_CMS;
import static gov.ca.cwds.rest.core.Api.DS_CMS_REP;

import java.util.Collection;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.FlushMode;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.hoi.HOIScreeningData;
import gov.ca.cwds.rest.services.hoi.HOIScreeningService;
import gov.ca.cwds.rest.services.hoi.InvolvementHistoryData;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Business layer object to work on Screening History Of Involvement.
 *
 * @author CWDS API Team
 */
public class InvolvementHistoryService
    implements TypedCrudsService<String, InvolvementHistory, Response> {

  @Inject
  private HOIScreeningService hoiScreeningService;

  public InvolvementHistoryService() {
    super();
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#find(gov.ca.cwds.rest.api.Request)
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
    loadDataFromCMS(ihd);
    return new InvolvementHistory(ihd.getScreeningId(), ihd.getHoiCases(), ihd.getHoiReferrals(),
        ihd.getHoiScreenings());
  }

  @UnitOfWork(value = DS_CMS, readOnly = true, transactional = false, flushMode = FlushMode.MANUAL)
  @SuppressWarnings("WeakerAccess") // can't be private because the @UnitOfWork will not play
  protected void loadDataFromCMS(InvolvementHistoryData ihd) {
    final HOIScreeningData hsd = ihd.getHoiScreeningData();
    if (!hsd.getClientIds().isEmpty()) {
      hoiScreeningService.fetchDataFromCMS(hsd);
      final HOIRequest hoiRequest = new HOIRequest(hsd.getClientIds());
    }
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
