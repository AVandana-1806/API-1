package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Inject;

import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Request;

/**
 * {@link Request} representing a JSON Elasticsearch query.
 * 
 * @author CWDS API Team
 */
public class CaresSearchQuery extends ApiObjectIdentity implements Request {

  private static final long serialVersionUID = 1L;

  private String query;

  @Inject
  public CaresSearchQuery(@JsonProperty("query") String query) {
    super();
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

}
