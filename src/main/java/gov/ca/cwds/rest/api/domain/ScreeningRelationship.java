package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Request;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;
import javax.ws.rs.DefaultValue;

public class ScreeningRelationship extends ReportingDomain implements Request {

  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = false, value = "Screening Relationship Id",
      example = "12345")
  private String id;

  @JsonProperty("client_id")
  @ApiModelProperty(required = true, readOnly = false,
      value = "The Id for the the Primary client this relationship refers to. Generated on create",
      example = "ZXY123")
  private String clientId;

  @JsonProperty("relative_id")
  @ApiModelProperty(required = true, readOnly = false,
      value = "The Id for the the primary client's relative", example = "ABC987")
  private String relativeId;

  @JsonProperty("relationship_type")
  @ApiModelProperty(required = true, readOnly = false,
      value = "The relationship type code", example = "190")
  private int relationshipType;

  @JsonProperty("absent_parent_indicator")
  @ApiModelProperty(required = true, readOnly = false,
      value = "This indicates if the parent CLIENT is absent for the child with whom the relationship is being defined", example = "true" )
  private boolean absentParentIndicator;

  @JsonProperty("same_home_status")
  @OneOf({"Y", "N", "U"})
  @DefaultValue(value = "U")
  @ApiModelProperty(required = true, readOnly = false,
      value = "Indicates whether the two CLIENTs live in the same home.", example = "Y")
  private String sameHomeStatus;

  public ScreeningRelationship() {
  }

  public ScreeningRelationship(String id, String personId, String relationId,
      int relationshipType, boolean absentParentIndicator, String sameHomeStatus) {
    this.id = id;
    this.clientId = personId;
    this.relativeId = relationId;
    this.relationshipType = relationshipType;
    this.absentParentIndicator = absentParentIndicator;
    this.sameHomeStatus = sameHomeStatus;
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
