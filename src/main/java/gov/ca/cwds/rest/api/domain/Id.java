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

  String id;

  /**
   * @param id - primary key
   */
  public Id(@JsonProperty("id") String id) {
    super();
    this.id = id;
  }


  public String getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  final public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  final public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
