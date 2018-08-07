package gov.ca.cwds.rest.api.domain;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.dto.BaseDTO;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Domain object for safely surrendered babies special program.
 * 
 * @author CWDS API Team
 */
@ApiModel
public class SafelySurrenderedBabiesDTO extends BaseDTO implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("surrendered_by")
  @ApiModelProperty(required = true, readOnly = false, example = "Unknown")
  @NotEmpty
  @Size(max = 40)
  private String surrenderedByName;

  @JsonProperty("relation_to_child")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "1592",
      notes = "The code for relationship RMV_FRMC")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.REMOVEF_FROM_CARE_TAKER_TYPE)
  private Integer relationToChild;

  @JsonProperty("bracelet_id")
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  @NotEmpty
  @Size(max = 10)
  private String braceletId;

  @JsonProperty("parent_guardian_given_bracelet_id")
  @ApiModelProperty(required = true, readOnly = false, example = "U")
  @NotEmpty
  @Size(max = 1)
  @OneOf(value = {"A", "N", "U", "Y"})
  private String braceletInfoCode;

  @JsonProperty("parent_guardian_provided_med_questionaire")
  @ApiModelProperty(required = true, readOnly = false, example = "U")
  @Size(max = 1)
  @OneOf(value = {"D", "M", "N", "R", "U"})
  private String medicalQuestionaireCode;

  @SuppressWarnings("squid:S3437")
  @JsonProperty("med_questionaire_return_date")
  @ApiModelProperty(required = false, readOnly = false, value = "Questionaire Return Date",
      example = "1992-05-18")
  private LocalDate medicalQuestionaireReturnDate;

  @JsonProperty("comments")
  @ApiModelProperty(required = false, readOnly = false, value = "comments",
      example = "Mother surrendered the baby")
  @Size(max = 254)
  private String comments;

  /**
   * No-argument constructor
   */
  public SafelySurrenderedBabiesDTO() {
    super();
  }

  public String getSurrenderedByName() {
    return surrenderedByName;
  }

  public void setSurrenderedByName(String surrenderedByName) {
    this.surrenderedByName = surrenderedByName;
  }

  public Integer getRelationToChild() {
    return relationToChild;
  }

  public void setRelationToChild(Integer relationToChild) {
    this.relationToChild = relationToChild;
  }

  public String getBraceletId() {
    return braceletId;
  }

  public void setBraceletId(String braceletId) {
    this.braceletId = braceletId;
  }

  public String getBraceletInfoCode() {
    return braceletInfoCode;
  }

  public void setBraceletInfoCode(String braceletInfoCode) {
    this.braceletInfoCode = braceletInfoCode;
  }

  public String getMedicalQuestionaireCode() {
    return medicalQuestionaireCode;
  }

  public void setMedicalQuestionaireCode(String medicalQuestionaireCode) {
    this.medicalQuestionaireCode = medicalQuestionaireCode;
  }

  public LocalDate getMedicalQuestionaireReturnDate() {
    return medicalQuestionaireReturnDate;
  }

  public void setMedicalQuestionaireReturnDate(LocalDate medicalQuestionaireReturnDate) {
    this.medicalQuestionaireReturnDate = medicalQuestionaireReturnDate;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }
}
