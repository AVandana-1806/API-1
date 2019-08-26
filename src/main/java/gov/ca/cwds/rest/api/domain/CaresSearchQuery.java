package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.util.CaresRawJsonDeserialzier;

/**
 * {@link Request} representing a JSON Elasticsearch query.
 * 
 * @author CWDS API Team
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = CaresRawJsonDeserialzier.class)
public class CaresSearchQuery extends ApiObjectIdentity implements Request, Response {

  private static final long serialVersionUID = 1L;

  private String user;
  private String index;
  private String json;

  @JsonRawValue
  public String getQuery() {
    return json;
  }

  @JsonIgnore
  public void setJson(final String query) {
    this.json = query;
  }

  public String getUser() {
    return user;
  }

  @JsonIgnore
  public void setUser(String user) {
    this.user = user;
  }

  public String getIndex() {
    return index;
  }

  @JsonIgnore
  public void setIndex(String index) {
    this.index = index;
  }

}
