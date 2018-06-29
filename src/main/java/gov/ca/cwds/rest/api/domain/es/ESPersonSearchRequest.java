package gov.ca.cwds.rest.api.domain.es;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Person search request of ElasticSearch.
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
public final class ESPersonSearchRequest extends DomainObject implements Request {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(example = "bart")
  @JsonProperty("first_name")
  private String firstName;

  @ApiModelProperty(example = "simpson")
  @JsonProperty("last_name")
  private String lastName;

  /**
   * Birth date input, following the standard API domain date format, {@link DATE_FORMAT}.
   * 
   * <p>
   * When searching ElasticSearch documents, birth date is lenient and allows wildcards (*,?), such
   * as "2000-*", "2000-12-*", or "2000-1?-*". This relaxed date format differs from most other
   * domain date fields.
   * </p>
   * 
   * <p>
   * That said, birth date is required when creating ElasticSearch documents, but that doesn't apply
   * to a "search request" object.
   * </p>
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainChef.DATE_FORMAT)
  @JsonProperty("birth_date")
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String birthDate;

  /**
   * Default, no-param, no-op ctor. Required by frameworks.
   */
  public ESPersonSearchRequest() {
    // Required by frameworks.
  }

  /**
   * JSON DropWizard Constructor.
   * 
   * @param firstName the first name to search
   * @param lastName the last name to search
   * @param birthDate the birth date to search
   */
  @JsonCreator
  public ESPersonSearchRequest(@JsonProperty("first_name") String firstName,
      @JsonProperty("last_name") String lastName, @JsonProperty("birth_date") String birthDate) {
    super();
    this.firstName = firstName != null ? firstName.trim() : "";
    this.lastName = lastName != null ? lastName.trim() : "";
    this.birthDate = birthDate != null ? birthDate.trim() : "";
  }

  /**
   * Gets the first name.
   * 
   * @return the first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name.
   * 
   * @param firstName the first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the last name.
   * 
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name.
   * 
   * @param lastName the last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets the birth date
   * 
   * @return birth date in format {@link DomainObject#DATE_FORMAT}
   */
  public String getBirthDate() {
    return birthDate;
  }

  /**
   * Sets the birth date for search, not necessarily a complete date expression.
   * 
   * @param birthDate set birth date for search
   * @see #birthDate
   */
  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
