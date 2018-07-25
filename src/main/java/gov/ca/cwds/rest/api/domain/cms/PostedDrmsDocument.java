package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * {@link Response} adding an id to the {@link DrmsDocument}.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"squid:S3437", "squid:S2160", "fb-contrib:COM_COPIED_OVERRIDDEN_METHOD",
    "findbugs:EQ_DOESNT_OVERRIDE_EQUALS"})
public class PostedDrmsDocument extends DrmsDocument {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param drmsDocument The persisted drmsDocument
   */
  public PostedDrmsDocument(gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument) {
    super(drmsDocument);
    if (StringUtils.isBlank(drmsDocument.getId())) {
      throw new ServiceException("drmsDocument ID cannot be blank");
    }

    this.id = drmsDocument.getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

}
