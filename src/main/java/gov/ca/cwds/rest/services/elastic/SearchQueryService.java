package gov.ca.cwds.rest.services.elastic;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.CaresSearchQuery;
import gov.ca.cwds.rest.api.domain.elastic.SearchQueryTerms;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.tracelog.core.TraceLogService;

/**
 * Ferb search query service wraps the Trace Log search query logging service.
 * 
 * @author CWDS API Team
 */
public class SearchQueryService implements TypedCrudsService<String, CaresSearchQuery, Response> {

  @Inject
  private TraceLogService traceLogService;

  public SearchQueryService() {
    super();
  }

  @Override
  public Response create(CaresSearchQuery request) {
    return new SearchQueryTerms(
        traceLogService.logSearchQuery(request.getUser(), request.getQuery()));
  }

}
