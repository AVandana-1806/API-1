package gov.ca.cwds.es.live;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Request;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;

/**
 * A domain API {@link Request} for converting a 10 character legacy key to a 19 digit UI
 * identifier.
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
@SuppressWarnings({"findsecbugs:SQL_INJECTION_JDBC", "squid:S2095",
    "findbugs:SE_TRANSIENT_FIELD_NOT_RESTORED", "squid:S1206", "serial"})
public class LiveElasticClientRequest extends ApiObjectIdentity implements Request {

  private static final long serialVersionUID = 1L;

  @JsonProperty("client_ids")
  private List<String> clientIds;

  public LiveElasticClientRequest() {
    // default
  }

  /**
   * JSON DropWizard Constructor. Takes solitary search term.
   * 
   * @param clientIds list of client ids to search
   */
  @JsonCreator
  public LiveElasticClientRequest(@Valid @NotNull @JsonProperty("key") List<String> clientIds) {
    this.clientIds = clientIds;
  }

  public List<String> getClientIds() {
    return clientIds;
  }

  public void setClientIds(List<String> clientIds) {
    this.clientIds = clientIds;
  }

}
