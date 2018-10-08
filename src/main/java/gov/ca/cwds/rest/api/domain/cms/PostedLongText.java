package gov.ca.cwds.rest.api.domain.cms;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * {@link Response} adding an id to the {@link LongText}.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"squid:S3437", "squid:S2160", "findbugs:EQ_DOESNT_OVERRIDE_EQUALS"})
public class PostedLongText extends LongText {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param longText The persisted longText
   */
  public PostedLongText(gov.ca.cwds.data.persistence.cms.LongText longText) {
    super(longText);
    if (StringUtils.isBlank(longText.getId())) {
      throw new ServiceException("LongText ID cannot be blank");
    }

    this.id = longText.getId();
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
    PostedLongText that = (PostedLongText) o;
    return Objects.equals(id, that.id);
  }
}
