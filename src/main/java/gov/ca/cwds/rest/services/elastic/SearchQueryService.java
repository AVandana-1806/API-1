package gov.ca.cwds.rest.services.elastic;

import org.apache.commons.lang3.NotImplementedException;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.CaresSearchQuery;
import gov.ca.cwds.rest.api.domain.elastic.SearchQueryTerms;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.tracelog.elastic.CaresSearchQueryParser;

public class SearchQueryService implements TypedCrudsService<String, CaresSearchQuery, Response> {

  public SearchQueryService() {
    super();
  }

  @Override
  public Response create(CaresSearchQuery request) {
    return new SearchQueryTerms(new CaresSearchQueryParser().parse(request.getQuery()));
  }

  @Override
  public Response find(String s) {
    throw new NotImplementedException("Find is not implemented");
  }

  @Override
  public Response delete(String s) {
    throw new NotImplementedException("Delete is not implemented");
  }

  @Override
  public Response update(String s, CaresSearchQuery contactIntake) {
    throw new NotImplementedException("Update is not implemented");
  }

}
