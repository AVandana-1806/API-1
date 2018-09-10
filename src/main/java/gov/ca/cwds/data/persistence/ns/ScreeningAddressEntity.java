package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQuery;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.ScreeningAddressEntity.findByScreeningId",
    query = "FROM gov.ca.cwds.data.persistence.ns.ScreeningAddressEntity"
        + " WHERE screeningId = :screeningId")
@Entity
@Table(name = "screening_addresses")
public class ScreeningAddressEntity implements PersistentObject {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "screening_addresses_id_seq")
  @GenericGenerator(name = "screening_addresses_id_seq",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name",
          value = "screening_addresses_id_seq")})
  private String id;

  @Column(name = "screening_id")
  private String screeningId;

  @Column(name = "address_id")
  private String addressId;

  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public ScreeningAddressEntity() {
    // default
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getScreeningId() {
    return screeningId;
  }

  public void setScreeningId(String screeningId) {
    this.screeningId = screeningId;
  }

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

}
