package gov.ca.cwds.data.persistence.ns;

import static org.hibernate.annotations.CascadeType.DELETE;
import static org.hibernate.annotations.CascadeType.LOCK;
import static org.hibernate.annotations.CascadeType.MERGE;
import static org.hibernate.annotations.CascadeType.PERSIST;
import static org.hibernate.annotations.CascadeType.REFRESH;
import static org.hibernate.annotations.CascadeType.REMOVE;
import static org.hibernate.annotations.CascadeType.REPLICATE;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class is the reference to {@link gov.ca.cwds.data.persistence.ns.PersonAddress}.
 *
 * @author CWDS API Team
 */
@Embeddable
public class PersonAddressId implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonIgnore
  @ManyToOne
  @Cascade({PERSIST, MERGE, SAVE_UPDATE, DELETE, LOCK, REFRESH, REMOVE, REPLICATE})
  private Person person;

  @ManyToOne
  @Cascade({PERSIST, MERGE, SAVE_UPDATE, DELETE, LOCK, REFRESH, REMOVE, REPLICATE})
  private Address address;

  /**
   * Default constructor
   */
  public PersonAddressId() {
    super();
  }

  /**
   * @return the person
   */
  @JsonIgnore
  public Person getPerson() {
    return person;
  }

  /**
   * @param person - The person
   */
  public void setPerson(Person person) {
    this.person = person;
  }

  /**
   * @return the address
   */
  public Address getAddress() {
    return address;
  }

  /**
   * @param address - The address
   */
  public void setAddress(Address address) {
    this.address = address;
  }

  @SuppressWarnings("javadoc")
  public Serializable getPrimaryKey() {
    return null;
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
