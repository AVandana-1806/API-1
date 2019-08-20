package gov.ca.cwds.rest.api.domain;

import java.io.StringWriter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;

/**
 * {@link Request} representing a JSON Elasticsearch query.
 * 
 * @author CWDS API Team
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("")
public class CaresSearchQuery extends ApiObjectIdentity implements Request, Response {

  private static final long serialVersionUID = 1L;

  private String query;

  @JsonRawValue
  public String getQuery() {
    return query;
  }

  public void setJson(final String query) {
    this.query = query;
  }

  @JsonRawValue
  public void setQuery(JsonNode jsonNode) {
    try {
      final StringWriter stringWriter = new StringWriter();
      final ObjectMapper objectMapper = new ObjectMapper();
      new JsonFactory(objectMapper).createGenerator(stringWriter).writeTree(jsonNode);
      setJson(stringWriter.toString());
    } catch (Exception e) {
      throw new ApiException("FAILED TO PARSE SEARCH QUERY JSON", e);
    }
  }

}
