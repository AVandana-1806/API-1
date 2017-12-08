package gov.ca.cwds.rest.api.domain.hoi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;

/**
 * A domain API {@link Request} for {@link HOIReferral}
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
public class HOIReferralResponse implements Response {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @JsonUnwrapped
  private List<HOIReferral> hoiReferrals = new ArrayList<>();

  /**
   * Disallow use of default constructor.
   */
  public HOIReferralResponse() {
    // no -opt
  }

  /**
   * Preferred constructor. Build from HOI Referral array.
   * 
   * @return the array of {@link HOIReferral}
   */
  @JsonValue
  public List<HOIReferral> getHoiReferrals() {
    return hoiReferrals;
  }

  /**
   * Setter for array of {@link HOIReferral hoiReferrals}.
   * 
   * @param hoiReferrals - hoiReferrals
   */
  public void setHoiReferrals(List<HOIReferral> hoiReferrals) {
    this.hoiReferrals = hoiReferrals;
  }

  /**
   * Add the {@link HOIReferral} to the {@link List}
   * 
   * @param hoiReferral - hoiReferral
   */
  public void addHoiReferral(HOIReferral hoiReferral) {
    if (hoiReferral != null) {
      hoiReferrals.add(hoiReferral);
    }
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
