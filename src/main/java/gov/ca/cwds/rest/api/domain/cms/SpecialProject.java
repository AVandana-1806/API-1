package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Special Project
 * 
 * @author CWDS API Team
 */

public class SpecialProject implements Request, Response{
  
  private static final long serialVersionUID = 1L;
      
  @NotNull
  @ApiModelProperty(required = false, readOnly = false)
  private Boolean archiveAssociationIndicator;
  
  @NotNull
  @Size(max = 254)
  @ApiModelProperty(required = false, readOnly = false,
      value = "description of the special project", example = "Special Project Description")
  private String description;
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT)
  @JsonProperty(value = "endDate")
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "date when special project ended",
      example = "2018-05-30")
  private String endDate;
 
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "0", example = "1234")
  private Short governmentEntityType;

  @NotNull
  @Size(max = 30)
  @ApiModelProperty(required = false, readOnly = false, 
      value = "name of the special project", example = "S-CSEC Referral")
  private String name;
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT)
  @JsonProperty(value = "startDate")
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "date when special project started",
      example = "2018-05-30")
  private String startDate;
  
  /**
   * Constructor
   * 
   * @param archiveAssociationIndicator - archive association indicator
   * @param description - special project description
   * @param endDate - end date
   * @param governmentEntityType = government entity type
   * @param name - special project name
   * @param startDate - start date
   */
  public SpecialProject(Boolean archiveAssociationIndicator, String description, String endDate, 
      Short governmentEntityType, String name, String startDate) {
    super();
    this.archiveAssociationIndicator = archiveAssociationIndicator;
    this.description = description;
    this.endDate = endDate;
    this.governmentEntityType = governmentEntityType;
    this.name = name;
    this.startDate = startDate;
  }

  /**
   * Constructor
   * 
   * @param specialProject - persisted CWS Special Project
   * 
   */
  public SpecialProject(gov.ca.cwds.data.legacy.cms.entity.SpecialProject specialProject) {
    this.archiveAssociationIndicator = specialProject.getArrchiveAssociationIndicator();
    this.description = specialProject.getProjectDescription();
    this.endDate = DomainChef.cookLocalDate(specialProject.getEndDate());
    this.governmentEntityType = specialProject.getGovernmentEntityType();
    this.name = specialProject.getName();
    this.startDate = DomainChef.cookLocalDate(specialProject.getStartDate());
  }
  
  /**
   *
   * @return the archiveAssoicationIndicator
   */
  public Boolean getArchiveAssociationIndicator() {
    return archiveAssociationIndicator;
  }

  /**
   * 
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * 
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }
  
  /**
   * 
   * @return the governmentEntityType
   */
  public Short getGovernmentEntityType() {
    return governmentEntityType;
  }

  /**
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * 
   * @return the startDate
   */
  public String getStartDate() {
    return startDate;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }
}
