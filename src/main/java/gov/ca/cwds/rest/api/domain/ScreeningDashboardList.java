package gov.ca.cwds.rest.api.domain;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * Container of {@link ScreeningDashboard} objects, represented as an unnamed array.
 *
 * Jackson best practice: annotation, {@code @JsonValue}, on getter
 * {@link #getScreeningDashboard()}, serializes this object as an unnamed array of
 * ScreeningDashboard objects.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ScreeningDashboardList extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  private List<ScreeningDashboard> screeningDashboard;

  public ScreeningDashboardList() {
    super();
  }

  public ScreeningDashboardList(List<ScreeningDashboard> screeningDashboard) {
    super();
    this.screeningDashboard = screeningDashboard;
  }

  /**
   * Jackson best practice: annotation, {@code @JsonValue}, on this getter tells Jackson to
   * serialize this object as an unnamed array of ScreeningDashboard objects.
   * 
   * @return - allegations of an investigation
   */
  @JsonValue
  public List<ScreeningDashboard> getScreeningDashboard() {
    return screeningDashboard;
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
