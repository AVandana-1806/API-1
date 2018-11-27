package gov.ca.cwds.es.live;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;

/**
 * A domain API {@link Request} for Intake Person Auto-complete feature to Elasticsearch.
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
public class LiveElasticClientResponse extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("hits")
  private final List<ElasticSearchPerson> hits;

  /**
   * Preferred constructor. Build from UI identifier.
   * 
   * @param hits ElasticSearchPerson objects
   */
  public LiveElasticClientResponse(List<ElasticSearchPerson> hits) {
    this.hits = hits;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
