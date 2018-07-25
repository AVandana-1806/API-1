package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * {@link Response} adding an id to the {@link Assignment}.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"squid:S3437", "squid:S2160"})
public class PostedAssignment extends Assignment {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param assignment The persisted assignment
   */
  public PostedAssignment(gov.ca.cwds.data.persistence.cms.Assignment assignment) {
    super(assignment);
    if (StringUtils.isBlank(assignment.getId())) {
      throw new ServiceException("Assignment ID cannot be blank");
    }
    this.id = assignment.getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }
}
