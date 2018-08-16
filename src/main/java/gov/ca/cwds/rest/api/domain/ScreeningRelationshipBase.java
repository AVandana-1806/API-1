package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Request;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Optional;
import javax.ws.rs.DefaultValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author CWDS TPT-3 Team
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScreeningRelationshipBase extends ReportingDomain
    implements Request, gov.ca.cwds.rest.api.Response {

  @JsonProperty("client_id")
  @ApiModelProperty(required = true,
      value = "The Id for the the Primary client this relationship refers to. Generated on create",
      example = "ZXY123")
  private String clientId;

  @JsonProperty("relative_id")
  @ApiModelProperty(required = true, value = "The Id for the the primary client's relative",
      example = "ABC987")
  private String relativeId;

  @JsonProperty("relationship_type")
  @ApiModelProperty(required = true, value = "The relationship type code", example = "190")
  private int relationshipType;

  @JsonProperty("absent_parent_indicator")
  @ApiModelProperty(required = true,
      value = "This indicates if the parent CLIENT is absent for the child with whom the relationship is being defined",
      example = "true")
  private boolean absentParentIndicator;

  @JsonProperty("same_home_status")
  @OneOf({"Y", "N", "U"})
  @DefaultValue(value = "U")
  @ApiModelProperty(required = true,
      value = "Indicates whether the two CLIENTs live in the same home.", example = "Y")
  private String sameHomeStatus;

  @JsonProperty("start_date")
  @ApiModelProperty(value = "This indicates the start date of relationship", example = "1999-10-01")
  private Date startDate;

  @JsonProperty("end_date")
  @ApiModelProperty(value = "This indicates the end date of relationship", example = "2010-10-01")
  private Date endDate;

  @JsonProperty("legacy_id")
  @ApiModelProperty(value = "This indicates the legacy ID of relationship", example = "A1b2x")
  private String legacyId;

  public String getLegacyId() {
    return legacyId;
  }

  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String person) {
    this.clientId = person;
  }

  public String getRelativeId() {
    return relativeId;
  }

  public void setRelativeId(String relativeId) {
    this.relativeId = relativeId;
  }

  public int getRelationshipType() {
    return relationshipType;
  }

  public void setRelationshipType(int relationshipType) {
    this.relationshipType = relationshipType;
  }

  public boolean isAbsentParentIndicator() {
    return absentParentIndicator;
  }

  public void setAbsentParentIndicator(boolean absentParentIndicator) {
    this.absentParentIndicator = absentParentIndicator;
  }

  public String getSameHomeStatus() {
    return sameHomeStatus;
  }

  public void setSameHomeStatus(String sameHomeStatus) {
    this.sameHomeStatus = sameHomeStatus;
  }

  public Date getEndDate() {
    return Optional.ofNullable(endDate).map(Date::getTime).map(Date::new).orElse(null);
  }

  public void setEndDate(Date endDate) {
    this.endDate = Optional.ofNullable(endDate).map(Date::getTime).map(Date::new).orElse(null);
  }

  public Date getStartDate() {
    return Optional.ofNullable(startDate).map(Date::getTime).map(Date::new).orElse(null);
  }

  public void setStartDate(Date startDate) {
    this.startDate = Optional.ofNullable(startDate).map(Date::getTime).map(Date::new).orElse(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof ScreeningRelationshipBase)) {
      return false;
    }

    ScreeningRelationshipBase that = (ScreeningRelationshipBase) o;

    return new EqualsBuilder()
        .append(relationshipType, that.relationshipType)
        .append(absentParentIndicator, that.absentParentIndicator)
        .append(clientId, that.clientId)
        .append(relativeId, that.relativeId)
        .append(sameHomeStatus, that.sameHomeStatus)
        .append(startDate, that.startDate)
        .append(endDate, that.endDate)
        .append(legacyId, that.legacyId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(clientId)
        .append(relativeId)
        .append(relationshipType)
        .append(absentParentIndicator)
        .append(sameHomeStatus)
        .append(startDate)
        .append(endDate)
        .append(legacyId)
        .toHashCode();
  }
}