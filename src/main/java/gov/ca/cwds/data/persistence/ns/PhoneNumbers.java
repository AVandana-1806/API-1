package gov.ca.cwds.data.persistence.ns;

import static gov.ca.cwds.data.persistence.ns.PhoneNumbers.FIND_BY_PARTICIPANT_ID;
import static gov.ca.cwds.data.persistence.ns.PhoneNumbers.FIND_BY_PARTICIPANT_ID_QUERY;

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

import gov.ca.cwds.Identifiable;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.ns.papertrail.HasPaperTrail;

/**
 * {@link PersistentObject} representing a collection {@link PhoneNumber}'s.
 *
 * @author CWDS API Team
 */
@NamedQuery(name = FIND_BY_PARTICIPANT_ID, query = FIND_BY_PARTICIPANT_ID_QUERY)
@Entity
@Table(name = "phone_numbers")
public class PhoneNumbers implements PersistentObject, HasPaperTrail, Identifiable<String> {
  private static final long serialVersionUID = 1L;

  public static final String FIND_BY_PARTICIPANT_ID =
      "gov.ca.cwds.data.persistence.ns.PhoneNumbers.findByParticipantId";
  public static final String PARAM_PARTICIPANT_ID = "participantId";
  static final String FIND_BY_PARTICIPANT_ID_QUERY =
      "SELECT ppn.phoneNumber FROM ParticipantPhoneNumbers ppn" + " WHERE ppn.participant.id = :"
          + PARAM_PARTICIPANT_ID;

  @Id
  @GenericGenerator(name = "phone_numbers_id",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name",
          value = "phone_numbers_id_seq")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_numbers_id")
  @Column(name = "id")
  private String id;

  @Column(name = "number")
  private String number;

  @Column(name = "type")
  private String type;

  public PhoneNumbers() {}

  /**
   * @param id - primary key
   * @param number - the phone number
   * @param type - the phone number type
   */
  public PhoneNumbers(String id, String number, String type) {
    this.id = id;
    this.number = number;
    this.type = type;
  }

  /**
   * Constructor
   *
   * @param phoneNumber The domain object to construct this object from
   */
  public PhoneNumbers(gov.ca.cwds.rest.api.domain.PhoneNumber phoneNumber) {
    this.id = phoneNumber.getId() == null ? null : String.valueOf(phoneNumber.getId());
    this.number = phoneNumber.getNumber();
    this.type = phoneNumber.getType();
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * @return the phone number
   */
  public String getNumber() {
    return number;
  }

  /**
   * @return the phone number type
   */
  public String getType() {
    return type;
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
