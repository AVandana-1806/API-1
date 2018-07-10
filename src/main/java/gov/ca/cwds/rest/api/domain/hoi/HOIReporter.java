package gov.ca.cwds.rest.api.domain.hoi;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.validation.ParticipantValidator;

/**
 * Reporter person.
 *
 * @author CWDS API Team
 */
@JsonPropertyOrder({"id", "first_name", "last_name", "name_suffix", "role", "legacy_descriptor"})
@SuppressWarnings({"fb-contrib:COM_COPIED_OVERRIDDEN_METHOD"})
public class HOIReporter extends HOIPerson {

  private static final long serialVersionUID = 1L;

  public enum Role {

    MANDATED_REPORTER("Mandated Reporter"),

    NON_MANDATED_REPORTER("Non-mandated Reporter"),

    ANONYMOUS_REPORTER("Anonymous Reporter"),

    SELF_REPORTER("Self Reported");

    private final String description;

    Role(String description) {
      this.description = description;
    }

    /**
     * @return the description
     */
    public String getDescription() {
      return description;
    }

    @Override
    public String toString() {
      return this.getDescription();
    }

    /**
     * @param description a string like "Mandated Reporter"
     * @return a Role constant or null
     */
    public static Role fromString(String description) {
      if (description == null) {
        return null;
      }
      if (description.equals(MANDATED_REPORTER.description)) {
        return MANDATED_REPORTER;
      }
      if (description.equals(NON_MANDATED_REPORTER.description)) {
        return NON_MANDATED_REPORTER;
      }
      if (description.equals(ANONYMOUS_REPORTER.description)) {
        return ANONYMOUS_REPORTER;
      }
      if (description.equals(SELF_REPORTER.description)) {
        return SELF_REPORTER;
      }
      return null;
    }
  }

  private Role role;

  /**
   * Default no-argument constructor
   */
  public HOIReporter() {
    super();
  }

  /**
   * @param role - role
   * @param id - id
   * @param firstName - firstName
   * @param lastName - lastName
   * @param nameSuffix - nameSuffix
   * @param legacyDescriptor - legacyDescriptor
   */
  public HOIReporter(Role role, String id, String firstName, String lastName, String nameSuffix,
      LegacyDescriptor legacyDescriptor) {
    super(id, firstName, lastName, nameSuffix, legacyDescriptor);
    checkRole(role);
    this.role = role;
  }

  /**
   * @return the role
   */
  public Role getRole() {
    return role;
  }

  /**
   * @param role - role
   */
  public void setRole(Role role) {
    checkRole(role);
    this.role = role;
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

  private static void checkRole(Role role) {
    if (role != null && !ParticipantValidator.roleIsAnyReporter(role.getDescription())) {
      throw new IllegalArgumentException("Role '" + role + "' is not a Reporter role");
    }
  }

}
