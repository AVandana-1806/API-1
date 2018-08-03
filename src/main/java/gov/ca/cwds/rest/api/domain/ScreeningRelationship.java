package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Request;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import javax.ws.rs.DefaultValue;

public class ScreeningRelationship extends ReportingDomain implements Request,
    gov.ca.cwds.rest.api.Response {

  @JsonProperty("id")
  @ApiModelProperty(required = true, value = "Screening Relationship Id",
      example = "12345")
  private String id;

  @JsonProperty("client_id")
  @ApiModelProperty(required = true,
      value = "The Id for the the Primary client this relationship refers to. Generated on create",
      example = "ZXY123")
  private String clientId;

  @JsonProperty("relative_id")
  @ApiModelProperty(required = true,
      value = "The Id for the the primary client's relative", example = "ABC987")
  private String relativeId;

  @JsonProperty("relationship_type")
  @ApiModelProperty(required = true,
      value = "The relationship type code", example = "190")
  private int relationshipType;

  @JsonProperty("absent_parent_indicator")
  @ApiModelProperty(required = true,
      value = "This indicates if the parent CLIENT is absent for the child with whom the relationship is being defined", example = "true")
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

  public ScreeningRelationship() {
    //comment is required by sonar
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getLegacyId() {
    return legacyId;
  }

  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("\nScreeningRelationship{");
    builder.append("\n\tid:");
    builder.append(id);
    builder.append(",\n");
    builder.append("\tclientId:");
    builder.append(clientId);
    builder.append(",\n");
    builder.append("\trelativeId:");
    builder.append(relativeId);
    builder.append(",\n");
    builder.append("\trelationshipType:");
    builder.append(relationshipType);
    builder.append(",\n");
    builder.append('}');
    return builder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScreeningRelationship that = (ScreeningRelationship) o;
    return relationshipType == that.relationshipType
        && Objects.equals(id, that.id)
        && Objects.equals(clientId, that.clientId)
        && Objects.equals(relativeId, that.relativeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, clientId, relativeId, relationshipType);
  }
}
