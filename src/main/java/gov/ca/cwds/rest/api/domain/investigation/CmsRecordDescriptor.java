package gov.ca.cwds.rest.api.domain.investigation;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CWDS API Team
 *
 *         a descriptor of a record in the CWS/CMS database
 * 
 *         can be used to identify a record in the CWS/CMS database for reading or updating
 *         legacy_id - 10 characters in base 62 legacy_last_updated - date time stamp
 *         legacy_table_name - physical name of table in CMS database
 * 
 *         can be used to identify a record by caller - legacy_ui_id - 19 characters in base 10
 *         (digits) legacy_table_description - logical table name in CMS database
 * 
 */
@JsonSnakeCase
@JsonPropertyOrder({"legacy_id", "legacy_ui_id", "legacy_table_neme", "legacy_table_description"})
public class CmsRecordDescriptor implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("legacy_id")
  @Size(max = CmsPersistentObject.CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS Id", example = "1234567ABC")
  private String id;

  @JsonProperty("legacy_ui_id")
  @Size(max = 19)
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS user interface Id",
      example = "1111-2222-3333-4444555")
  private String uiId;

  @JsonProperty("legacy_table_name")
  @NotBlank
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS Table Name",
      example = "CLIENT_T")
  private String tableName;

  @JsonProperty("legacy_table_description")
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS Table Description",
      example = "Client")
  private String tableDescription;

  /**
   * empty constructor
   */
  public CmsRecordDescriptor() {
    // default
  }

  /**
   * @param id - the CMS id
   * @param uiId - the CMS user interface Id
   * @param tableName - the physical table name
   * @param tableDescription - the table Descrption
   */
  public CmsRecordDescriptor(String id, String uiId, String tableName, String tableDescription) {
    this.id = id;
    this.uiId = uiId;
    this.tableName = tableName;
    this.tableDescription = tableDescription;
  }

  /**
   *
   * @return the Legacy Id
   */
  public String getId() {
    return id;
  }

  /**
   *
   * @param id Set the legacy Id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   *
   * @return the Ui Id
   */
  public String getUiId() {
    return uiId;
  }

  /**
   *
   * @param uiId set the uiId
   */
  public void setUiId(String uiId) {
    this.uiId = uiId;
  }

  /**
   *
   * @return get legacy table name
   */
  public String getTableName() {
    return tableName;
  }

  /**
   *
   * @param tableName set legacy table name
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  /**
   *
   * @return get legacy table description
   */
  public String getTableDescription() {
    return tableDescription;
  }

  /**
   *
   * @param tableDescription set the legacy table description
   */
  public void setTableDescription(String tableDescription) {
    this.tableDescription = tableDescription;
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

