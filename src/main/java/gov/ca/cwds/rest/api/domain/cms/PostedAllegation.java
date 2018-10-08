package gov.ca.cwds.rest.api.domain.cms;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * {@link Response} adding an id to the {@link Allegation}
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"findbugs:EQ_DOESNT_OVERRIDE_EQUALS", "squid:S2160"})
public class PostedAllegation extends Allegation {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param allegation The persisted allegation
   */
  public PostedAllegation(gov.ca.cwds.data.persistence.cms.Allegation allegation) {
    super(allegation);
    if (StringUtils.isBlank(allegation.getId())) {
      throw new ServiceException("Allegation ID cannot be blank");
    }

    this.id = allegation.getId();
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
    return Objects.hash(super.hashCode(), id);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    PostedAllegation that = (PostedAllegation) o;
    return Objects.equals(id, that.id);
  }
}
