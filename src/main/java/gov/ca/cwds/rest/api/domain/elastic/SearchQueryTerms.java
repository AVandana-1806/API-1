package gov.ca.cwds.rest.api.domain.elastic;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.tracelog.elastic.CaresSearchQueryParser.CaresJsonField;
import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
public class SearchQueryTerms extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("terms")
  final Map<CaresJsonField, String> terms;

  public SearchQueryTerms(Map<CaresJsonField, String> terms) {
    this.terms = terms;
  }

  public Map<CaresJsonField, String> getTerms() {
    return terms;
  }

}
