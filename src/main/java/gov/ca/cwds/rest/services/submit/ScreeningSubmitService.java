package gov.ca.cwds.rest.services.submit;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.enums.ScreeningStatus;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ScreeningService;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
import gov.ca.cwds.rest.services.StaffPersonService;

/**
 * Business layer object to work on submit a {@link Screening}. Create a {@link ScreeningToReferral}
 * and Update the {@link Screening } with the Referral Id.
 * 
 * @author CWDS API Team
 */
public class ScreeningSubmitService implements CrudsService {

  @Inject
  private ScreeningService screeningService;

  @Inject
  private ScreeningToReferralService screeningToReferralService;

  @Inject
  private StaffPersonService staffPersonService;

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    return submit(primaryKey);
  }

  /**
   * @param id - id
   * @return the screening
   */
  public Response submit(Serializable id) {
    Screening screening = screeningService.getScreening((String) id);
    String staffId = RequestExecutionContext.instance().getStaffId();
    String userCountyCode =
        staffPersonService.find(RequestExecutionContext.instance().getStaffId()).getCountyCode();
    // cms session
    ScreeningToReferral screeningToReferral =
        new ScreeningTransformer().transform(screening, staffId, userCountyCode);

    ScreeningToReferral str =
        (ScreeningToReferral) screeningToReferralService.create(screeningToReferral);
    screening.setReferralId(str.getReferralId());
    // ns session
    screening.setScreeningStatus(ScreeningStatus.SUBMITTED.getStatus());
    screeningService.updateScreening(screening.getId(), screening);
    return screening;
  }

  @Override
  public Response create(Request request) {
    throw new NotImplementedException("Create is not implemented");
  }

  @Override
  public Response delete(Serializable id) {
    throw new NotImplementedException("Delete is not implemented");
  }

  @Override
  public Response update(Serializable id, Request request) {
    throw new NotImplementedException("Update is not implemented");
  }

}
