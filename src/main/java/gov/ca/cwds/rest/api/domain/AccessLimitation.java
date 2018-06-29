package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.util.FerbDateUtils;

/**
 * Access Limitation (sealed, sensitive, restricted).
 * 
 * @author CWDS API Team
 */
public class AccessLimitation extends ApiObjectIdentity {

  private static final long serialVersionUID = 1L;

  private static final LimitedAccessType DEFAULT_LIMITED_ACCESS_CODE = LimitedAccessType.NONE;

  @JsonProperty("limited_access_code")
  private LimitedAccessType limitedAccessCode = DEFAULT_LIMITED_ACCESS_CODE;

  @JsonProperty("limited_access_date")
  private Date limitedAccessDate;

  @JsonProperty("limited_access_description")
  private String limitedAccessDescription;

  @JsonProperty("limited_access_government_entity")
  private SystemCodeDescriptor limitedAccessGovernmentEntity;

  /**
   * No-argument constructor
   */
  public AccessLimitation() {
    // No-argument constructor
  }

  /**
   * @param limitedAccessCode - limitedAccessCode
   * @param limitedAccessDate - limitedAccessDate
   * @param limitedAccessDescription - limitedAccessDescription
   * @param limitedAccessGovernmentEntity - limitedAccessGovernmentEntity
   */
  public AccessLimitation(LimitedAccessType limitedAccessCode, Date limitedAccessDate,
      String limitedAccessDescription, SystemCodeDescriptor limitedAccessGovernmentEntity) {
    super();
    this.limitedAccessCode = limitedAccessCode;
    this.limitedAccessDate = freshDate(limitedAccessDate);
    this.limitedAccessDescription = limitedAccessDescription;
    this.limitedAccessGovernmentEntity = limitedAccessGovernmentEntity;
  }

  public LimitedAccessType getLimitedAccessCode() {
    return limitedAccessCode;
  }

  public void setLimitedAccessCode(LimitedAccessType limitedAccessCode) {
    this.limitedAccessCode = limitedAccessCode;
  }

  public Date getLimitedAccessDate() {
    return FerbDateUtils.freshDate(limitedAccessDate);
  }

  public void setLimitedAccessDate(Date limitedAccessDate) {
    this.limitedAccessDate = FerbDateUtils.freshDate(limitedAccessDate);
  }

  public String getLimitedAccessDescription() {
    return limitedAccessDescription;
  }

  public void setLimitedAccessDescription(String limitedAccessDescription) {
    this.limitedAccessDescription = limitedAccessDescription;
  }

  public SystemCodeDescriptor getLimitedAccessGovernmentEntity() {
    return limitedAccessGovernmentEntity;
  }

  public void setLimitedAccessGovernmentEntity(SystemCodeDescriptor limitedAccessGovernmentEntity) {
    this.limitedAccessGovernmentEntity = limitedAccessGovernmentEntity;
  }
}

