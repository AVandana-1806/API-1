package gov.ca.cwds.rest.api.domain;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an SafelySurenderedBabies
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@SuppressWarnings({"common-java:DuplicatedBlocks"})
public class SafelySurrenderedBabiesIntakeApi extends DomainObject {

  private static final long serialVersionUID = -5667060392833194010L;

  @JsonProperty("surrendered_by")
  @ApiModelProperty(example = "Unknown")
  @Size(max = 40)
  private String surrenderedBy;

  @JsonProperty("relation_to_child")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1234",
      notes = "The code for relationship")
  private String relationToChild;

  @JsonProperty("bracelet_id")
  @ApiModelProperty(example = "1234")
  private String braceletId;

  @JsonProperty("parent_guardian_given_bracelet_id")
  @ApiModelProperty(example = "U")
  @Size(max = 1)
  private String parentGuardGivenBraceletId;

  @JsonProperty("parent_guardian_provided_med_questionaire")
  @ApiModelProperty(example = "U")
  @Size(max = 1)
  private String parentGuardProvMedQuestion;

  @SuppressWarnings("squid:S3437")
  @JsonProperty("med_questionaire_return_date")
  @ApiModelProperty(value = "Questionaire Return Date", example = "1992-05-18")
  private LocalDate medQuestionaireReturnDate;

  @JsonProperty("comments")
  @Size(max = 254)
  @ApiModelProperty(value = "comments", example = "Mother surrendered the baby")
  private String comments;

  @JsonProperty("participant_child")
  @ApiModelProperty(example = "ABC12345")
  private String participantId;


  public SafelySurrenderedBabiesIntakeApi() {
    // required by third party library
  }


  public String getSurrenderedBy() {
    return surrenderedBy;
  }


  public void setSurrenderedBy(String surrenderedBy) {
    this.surrenderedBy = surrenderedBy;
  }


  public String getRelationToChild() {
    return relationToChild;
  }


  public void setRelationToChild(String relationToChild) {
    this.relationToChild = relationToChild;
  }


  public String getBraceletId() {
    return braceletId;
  }


  public void setBraceletId(String braceletId) {
    this.braceletId = braceletId;
  }



  public String getParentGuardGivenBraceletId() {
    return parentGuardGivenBraceletId;
  }


  public void setParentGuardGivenBraceletId(String parentGuardGivenBraceletId) {
    this.parentGuardGivenBraceletId = parentGuardGivenBraceletId;
  }


  public String getParentGuardProvMedQuestion() {
    return parentGuardProvMedQuestion;
  }


  public void setParentGuardProvMedQuestion(String parentGuardProvMedQuestion) {
    this.parentGuardProvMedQuestion = parentGuardProvMedQuestion;
  }


  public LocalDate getMedQuestionaireReturnDate() {
    return medQuestionaireReturnDate;
  }


  public void setMedQuestionaireReturnDate(LocalDate medQuestionaireReturnDate) {
    this.medQuestionaireReturnDate = medQuestionaireReturnDate;
  }


  public String getComments() {
    return comments;
  }


  public void setComments(String comments) {
    this.comments = comments;
  }


  public String getParticipantId() {
    return participantId;
  }


  public void setParticipantId(String participantId) {
    this.participantId = participantId;
  }



}
