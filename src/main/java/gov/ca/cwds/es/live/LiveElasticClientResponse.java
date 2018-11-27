package gov.ca.cwds.es.live;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.std.ApiMarker;
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

  public static class LiveElasticClientHitsOuter implements ApiMarker {

    private static final long serialVersionUID = 1L;

    @JsonProperty("hits")
    private final LiveElasticClientHitsInner hits;

    public LiveElasticClientHitsOuter(LiveElasticClientHitsInner hits) {
      this.hits = hits;
    }

    public LiveElasticClientHitsInner getHits() {
      return hits;
    }

  }

  public static class LiveElasticClientHitsInner implements ApiMarker {

    private static final long serialVersionUID = 1L;

    @JsonProperty("hits")
    private final List<LiveElasticClientPerson> hits;

    /**
     * Construct from ElasticSearchPerson hits.
     * 
     * @param hits ElasticSearchPerson objects
     */
    public LiveElasticClientHitsInner(List<LiveElasticClientPerson> hits) {
      this.hits = hits;
    }

  }

  public static class LiveElasticClientPerson extends ElasticSearchPerson {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_source")
    private final ElasticSearchPerson person;

    public LiveElasticClientPerson(ElasticSearchPerson person) {
      this.person = person;
    }

    public ElasticSearchPerson getPerson() {
      return person;
    }

  }

  @JsonProperty("hits")
  private final LiveElasticClientHitsOuter hits;

  /**
   * Construct from ElasticSearchPerson hits.
   * 
   * @param hits ElasticSearchPerson objects
   */
  public LiveElasticClientResponse(List<ElasticSearchPerson> hits) {
    this.hits = new LiveElasticClientHitsOuter(new LiveElasticClientHitsInner(
        hits.stream().map(LiveElasticClientPerson::new).collect(Collectors.toList())));
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
