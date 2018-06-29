package gov.ca.cwds.rest.api.domain;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;

/**
 * {@link Response} adding an id to the {@link Person}.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"fb-contrib:COM_COPIED_OVERRIDDEN_METHOD"})
public class PostedPerson extends Person {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private long id;

  public PostedPerson(long id, String firstName, String middleName, String lastName,
      String nameSuffix, String gender, String dateOfBirth, String ssn, Set<Address> address,
      Set<PhoneNumber> phoneNumber, Set<Language> language, Set<Race> race,
      Set<Ethnicity> ethnicity) {
    super(firstName, middleName, lastName, nameSuffix, gender, dateOfBirth, ssn, address,
        phoneNumber, language, race, ethnicity);
    this.id = id;
  }

  public PostedPerson(gov.ca.cwds.data.persistence.ns.Person person) {
    super(person);
    this.id = person.getId();
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
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
