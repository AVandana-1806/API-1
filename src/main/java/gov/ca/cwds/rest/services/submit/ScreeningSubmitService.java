package gov.ca.cwds.rest.services.submit;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ScreeningToReferralService;

/**
 * Business layer object to work on submit a {@link Screening}. Create a {@link ScreeningToReferral}
 * and Update the {@link Screening } with the Referral Id.
 * 
 * @author CWDS API Team
 */
public class ScreeningSubmitService implements CrudsService {

  @Inject
  private ScreeningToReferralService screeningToReferralService;

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    throw new NotImplementedException("Find is not implemented");
  }

  @Override
  public Response create(Request request) {
    return screeningToReferralService.create(request);
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
