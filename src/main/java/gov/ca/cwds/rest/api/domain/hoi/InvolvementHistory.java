package gov.ca.cwds.rest.api.domain.hoi;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * {@link DomainObject} representing a History Of Involvement
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
@JsonSnakeCase
@JsonPropertyOrder({"id", "cases", "referrals", "screenings"})
@SuppressFBWarnings("SE_BAD_FIELD")
public class InvolvementHistory extends ApiObjectIdentity
    implements ApiTypedIdentifier<String>, Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("cases")
  private List<HOICase> cases = new ArrayList<>();

  @JsonProperty("referrals")
  private List<HOIReferral> referrals = new ArrayList<>();

  @JsonProperty("screenings")
  private List<HOIScreening> screenings = new ArrayList<>();

  @JsonIgnore
  private ArrayList<ErrorMessage> messages = new ArrayList<>();

  /**
   * Constructor
   */
  public InvolvementHistory() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id - the screening id
   * @param cases the cases
   * @param referrals the referrals
   * @param screenings the screenings
   */
  public InvolvementHistory(@JsonProperty("id") String id,
      @JsonProperty("cases") List<HOICase> cases,
      @JsonProperty("referrals") List<HOIReferral> referrals,
      @JsonProperty("screenings") List<HOIScreening> screenings) {
    super();
    this.id = id;
    this.cases = cases;
    this.referrals = referrals;
    this.screenings = screenings;
  }

  /**
   * @return the cases
   */
  public List<HOICase> getCases() {
    return cases;
  }

  /**
   * @return the referrals
   */
  public List<HOIReferral> getReferrals() {
    return referrals;
  }

  /**
   * @return the screenings
   */
  public List<HOIScreening> getScreenings() {
    return screenings;
  }


  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public List<ErrorMessage> getMessages() {
    return messages;
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
