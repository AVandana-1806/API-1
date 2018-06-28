package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.std.ApiMarker;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"id", "code", "type"})
public class GovernmentAgencyIntake implements ApiMarker {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = true, value = "", example = "123")
  private String id;

  @NotEmpty
  @JsonProperty("code")
  @ApiModelProperty(required = true, value = "", example = "ABC1234567")
  @Size(max = CMS_ID_LEN)
  private String code;

  @JsonProperty("type")
  @ApiModelProperty(required = true, value = "", example = "COMMUNITY_CARE_LICENSING")
  @OneOf(value = {"COMMUNITY_CARE_LICENSING", "COUNTY_LICENSING", "DISTRICT_ATTORNEY",
      "DEPARTMENT_OF_JUSTICE", "LAW_ENFORCEMENT"})
  private String type;

  /**
   * 
   */
  public GovernmentAgencyIntake() {
    // no-opt
  }

  /**
   * @param id - id
   * @param code - code
   * @param type - type
   */
  public GovernmentAgencyIntake(String id, String code, String type) {
    this.id = id;
    this.code = code;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
