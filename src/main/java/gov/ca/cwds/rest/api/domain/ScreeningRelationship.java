package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("common-java:DuplicatedBlocks")
@JsonPropertyOrder({"id", "client_id", "relative_id", "relationship_type",
    "absent_parent_indicator", "same_home_status", "start_date", "end_date", "legacy_id"})
public class ScreeningRelationship extends ScreeningRelationshipBase {

  @JsonProperty("id")
  @ApiModelProperty(required = true, value = "Screening Relationship Id", example = "12345")
  private String id;

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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("\nScreeningRelationship{");
    builder.append("\n\tid:");
    builder.append(id);
    builder.append(",\n");
    builder.append("\tclientId:");
    builder.append(getClientId());
    builder.append(",\n");
    builder.append("\trelativeId:");
    builder.append(getRelativeId());
    builder.append(",\n");
    builder.append("\trelationshipType:");
    builder.append(getRelationshipType());
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
    return getRelationshipType() == that.getRelationshipType() && Objects.equals(id, that.id)
        && Objects.equals(getClientId(), that.getClientId()) && Objects
        .equals(getRelativeId(), that.getRelativeId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, getClientId(), getRelativeId(), getRelationshipType());
  }
}