package gov.ca.cwds.rest.api.domain.investigation.contact;

import com.fasterxml.jackson.annotation.*;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.Set;


@JsonSnakeCase
@JsonInclude(JsonInclude.Include.ALWAYS)
public class PostedContactIntake extends ContactIntake implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = false, value = "Contact Id", example = "12345")
  private Integer id;

  @NotEmpty
  @ApiModelProperty(required = false, readOnly = false)
  @JsonProperty("people_ids")
  private Set<String> peopleIds;

  /**
   * @param startedAt started at
   * @param endedAt ended at
   * @param purpose purpose
   * @param communicationMethod communication method
   * @param status status
   * @param service service
   * @param location location
   * @param note note
   */
  @JsonCreator
  public PostedContactIntake(
      @JsonProperty("id") Integer id,
      @JsonProperty("screening_id") String screeningId,
      @JsonProperty("started_at") LocalDateTime startedAt,
      @JsonProperty("ended_at") LocalDateTime endedAt,
      @JsonProperty("purpose") String purpose,
      @JsonProperty("communication_method") String communicationMethod,
      @JsonProperty("status") String status,
      @JsonProperty("service") Integer service,
      @JsonProperty("location") String location,
      @JsonProperty("note") String note) {
    super(
        screeningId,
        startedAt,
        endedAt,
        purpose,
        communicationMethod,
        status,
        service,
        location,
        note);
    this.id = id;
  }

  public PostedContactIntake() {

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Set<String> getPeopleIds() {
    return peopleIds;
  }

  public void setPeopleIds(Set<String> peopleIds) {
    this.peopleIds = peopleIds;
  }

}
