package gov.ca.cwds.rest.api.domain.elastic;

import java.util.Map;

import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.tracelog.elastic.CaresSearchQueryParser.CaresJsonField;

public class SearchQueryTerms extends ApiObjectIdentity implements Response {

  private static final long serialVersionUID = 1L;

  final Map<CaresJsonField, String> terms;

  public SearchQueryTerms(Map<CaresJsonField, String> terms) {
    this.terms = terms;
  }

  public Map<CaresJsonField, String> getTerms() {
    return terms;
  }

}
