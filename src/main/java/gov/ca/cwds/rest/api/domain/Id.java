package gov.ca.cwds.rest.api.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;

/**
 * {@link Request} representing an id.
 * 
 * @author CWDS API Team
 */
public class Id implements Request {

  private static final long serialVersionUID = 1L;

  String identifier;

  public Id(@JsonProperty("identifier") String identifier) {
    super();
    this.identifier = identifier;
  }


  public String getIdentifier() {
    return identifier;
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
