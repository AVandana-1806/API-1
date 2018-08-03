package gov.ca.cwds.rest.api.domain.hoi;

import java.util.Collection;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
public class HOIRequest implements Request {

  private static final long serialVersionUID = 2L;

  @JsonProperty("client_ids")
  private Collection<String> clientIds;

  public HOIRequest() {
    // default
  }

  public HOIRequest(Collection<String> clientIds) {
    this.clientIds = clientIds;
  }

  public Collection<String> getClientIds() {
    return clientIds;
  }

  public void setClientIds(Collection<String> clientIds) {
    this.clientIds = clientIds;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
