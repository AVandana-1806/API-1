package gov.ca.cwds.rest.api.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"legacy_id", "legacy_ui_id", "legacy_last_updated", "legacy_table_name",
    "legacy_table_description"})
public class LegacyDescriptor extends DomainObject {

  public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

  private static final long serialVersionUID = 1L;

  @JsonProperty("legacy_id")
  @ApiModelProperty(value = "CWS/CMS Id", example = "1234567ABC")
  private String id;

  @ApiModelProperty(value = "CWS/CMS user interface Id", example = "1111-2222-3333-4444555")
  @JsonProperty("legacy_ui_id")
  private String uiId;

  @JsonProperty("legacy_last_updated")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT)
  @ApiModelProperty(value = "CWS/CMS Last Updated Time", example = "2010-10-01T15:26:42.000-0700")
  private DateTime lastUpdated;

  @JsonProperty("legacy_table_name")
  @ApiModelProperty(value = "CWS/CMS Table Name", example = "CLIENT_T")
  private String tableName;

  @JsonProperty("legacy_table_description")
  @ApiModelProperty(value = "CWS/CMS Table Description", example = "Client")
  private String tableDescription;

  /**
   * Default constructor
   */
  public LegacyDescriptor() {
    // no-opt
  }

  /**
   * @param id - the CMS id
   * @param uiId - the CMS user interface Id
   * @param lastUpdated - date/time of last update to CMS record
   * @param tableName - the physical table name
   * @param tableDescription - the table description
   */
  public LegacyDescriptor(String id, String uiId, DateTime lastUpdated, String tableName,
      String tableDescription) {
    this.id = id;
    this.uiId = uiId;
    if (StringUtils.isBlank(uiId) && StringUtils.isNotBlank(id)) {
      this.uiId = CmsKeyIdGenerator.getUIIdentifierFromKey(id);
    }
    this.lastUpdated = lastUpdated;
    this.tableName = tableName;
    this.tableDescription = tableDescription;
  }

  /**
   * Construct from persistence class
   *
   * @param legacyDescriptorEntity persistence level LegacyDescriptorEntity object
   */
  public LegacyDescriptor(LegacyDescriptorEntity legacyDescriptorEntity) {
    this(legacyDescriptorEntity.getLegacyId(), legacyDescriptorEntity.getLegacyUiId(),
        convertDateTime(legacyDescriptorEntity), legacyDescriptorEntity.getLegacyTableName(),
        legacyDescriptorEntity.getLegacyTableDescription());
  }

  private static DateTime convertDateTime(LegacyDescriptorEntity legacyDescriptorEntity) {
    DateTime lastUpdated = null;
    if (legacyDescriptorEntity.getLegacyLastUpdated() != null) {
      lastUpdated = DateTimeFormat.forPattern(DATETIME_FORMAT)
          .parseDateTime(legacyDescriptorEntity.getLegacyLastUpdated());
    }
    return lastUpdated;
  }

  /**
   * @return the Legacy Id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id Set the legacy Id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the Ui Id
   */
  public String getUiId() {
    return uiId;
  }

  /**
   * @param uiId set the uiId
   */
  public void setUiId(String uiId) {
    this.uiId = uiId;
  }

  /**
   * @return return the last updated time as a string
   */
  public DateTime getLastUpdated() {
    return lastUpdated;
  }

  /**
   * @param lastUpdated set the last updated time as a string
   */
  public void setLastUpdated(DateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  /**
   * @return get legacy table name
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * @param tableName set legacy table name
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  /**
   * @return get legacy table description
   */
  public String getTableDescription() {
    return tableDescription;
  }

  /**
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
