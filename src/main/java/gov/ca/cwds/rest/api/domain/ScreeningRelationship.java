package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("common-java:DuplicatedBlocks")
@JsonPropertyOrder({"id", "client_id", "relative_id", "relationship_type",
    "absent_parent_indicator", "same_home_status", "start_date", "end_date", "legacy_id", "error"})
public class ScreeningRelationship extends ScreeningRelationshipBase {

  @JsonProperty("id")
  @ApiModelProperty(required = true, value = "Screening Relationship Id", example = "12345")
  private String id;

  @JsonProperty("error")
  @ApiModelProperty(required = true, value = "Some text if relationship has an error", example = "Create relationship error")
  private String error;

  public ScreeningRelationship() {
    // comment is required by sonar
  }

  public ScreeningRelationship(ScreeningRelationshipBase screeningRelationshipBase) {
    setLegacyId(screeningRelationshipBase.getLegacyId());
    setClientId(screeningRelationshipBase.getClientId());
    setRelativeId(screeningRelationshipBase.getRelativeId());
    setRelationshipType(screeningRelationshipBase.getRelationshipType());
    setAbsentParentIndicator(screeningRelationshipBase.isAbsentParentIndicator());
    setSameHomeStatus(screeningRelationshipBase.getSameHomeStatus());
    setEndDate(screeningRelationshipBase.getEndDate());
    setStartDate(screeningRelationshipBase.getStartDate());
    setMessages(screeningRelationshipBase.getMessages());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder()
    .append("\nScreeningRelationship{\n\tid:")
    .append(id)
    .append(",\n\tclientId:")
    .append(getClientId())
    .append(",\n\trelativeId:")
    .append(getRelativeId())
    .append(",\n\trelationshipType:")
    .append(getRelationshipType())
    .append("\terror:")
    .append(getError())
    .append(",\n}");
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
    return getRelationshipType() == that.getRelationshipType() && Objects.equals(id, that.id)
        && Objects.equals(getClientId(), that.getClientId()) && Objects
        .equals(getRelativeId(), that.getRelativeId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, getClientId(), getRelativeId(), getRelationshipType());
  }
}