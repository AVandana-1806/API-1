package gov.ca.cwds.es.live;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;

/**
 * A domain API {@link Response} to retrieve live Elasticsearch client results.
 * 
 * <p>
 * Intake Snapshot expects JSON elements nested in order of {@code hits => hits => _source},
 * implemented by child classes {@link LiveElasticClientHits} and {@link LiveElasticClientPerson}.
 * </p>
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
@SuppressWarnings({"findsecbugs:SQL_INJECTION_JDBC", "squid:S2095",
    "findbugs:SE_TRANSIENT_FIELD_NOT_RESTORED", "squid:S1206", "serial"})
public class LiveElasticClientResponse extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonSnakeCase
  public static class LiveElasticClientHits implements ApiMarker {

    private static final long serialVersionUID = 1L;

    @JsonProperty("hits")
    private final List<LiveElasticClientPerson> hits;

    /**
     * Construct from ElasticSearchPerson hits.
     * 
     * @param hits ElasticSearchPerson objects
     */
    public LiveElasticClientHits(List<LiveElasticClientPerson> hits) {
      this.hits = hits;
    }

  }

  @JsonSnakeCase
  public static class LiveElasticClientPerson implements ApiMarker {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_source")
    private final ElasticSearchPerson person;

    public LiveElasticClientPerson(ElasticSearchPerson person) {
      this.person = person;
    }

  }

  @JsonProperty("hits")
  private final LiveElasticClientHits hits;

  /**
   * Construct from ElasticSearchPerson hits.
   * 
   * @param hits ElasticSearchPerson objects
   */
  public LiveElasticClientResponse(List<ElasticSearchPerson> hits) {
    this.hits = new LiveElasticClientHits(
        hits.stream().map(LiveElasticClientPerson::new).collect(Collectors.toList()));
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
