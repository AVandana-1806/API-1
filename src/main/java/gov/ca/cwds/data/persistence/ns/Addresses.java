package gov.ca.cwds.data.persistence.ns;

import static gov.ca.cwds.data.persistence.ns.Addresses.FIND_BY_PARTICIPANT_ID;
import static gov.ca.cwds.data.persistence.ns.Addresses.FIND_BY_PARTICIPANT_ID_QUERY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQuery;
import gov.ca.cwds.Identifiable;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.ns.papertrail.HasPaperTrail;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

/**
 * {@link PersistentObject} representing an Address (Postgres table "addresses").
 *
 * @author CWDS API Team
 */
@NamedQuery(name = FIND_BY_PARTICIPANT_ID, query = FIND_BY_PARTICIPANT_ID_QUERY)
@Entity
@Table(name = "addresses")
public class Addresses implements PersistentObject, HasPaperTrail, Identifiable<String> {

  private static final long serialVersionUID = 1L;

  public static final String FIND_BY_PARTICIPANT_ID =
      "gov.ca.cwds.data.persistence.ns.Addresses.findByParticipantId";
  public static final String PARAM_PARTICIPANT_ID = "participantId";
  static final String FIND_BY_PARTICIPANT_ID_QUERY =
      "SELECT pa.address FROM ParticipantAddresses pa" + " WHERE pa.participant.id = :"
          + PARAM_PARTICIPANT_ID;

  @Id
  @Column(name = "id")
  @GenericGenerator(name = "addresses_id",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator", parameters = {
          @org.hibernate.annotations.Parameter(name = "sequence_name", value = "addresses_id_seq")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addresses_id")
  private String id;

  @Column(name = "street_address")
  private String streetAddress;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "zip")
  private String zip;

  @Column(name = "type")
  private String type;

  @Column(name = "legacy_id")
  private String legacyId;

  @Column(name = "legacy_source_table")
  private String legacySourceTable;

  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public Addresses() {
    super();
  }

  /**
   * Constructor
   *
   * @param id - primary key
   * @param streetAddress - street address
   * @param city - city
   * @param state - state
   * @param zip - zip code
   * @param type - address type
   * @param legacyId - legacy id
   * @param legacySourceTable - legacy source table
   */
  public Addresses(String id, String streetAddress, String city, String state, String zip,
      String type, String legacyId, String legacySourceTable) {
    this.id = id;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.type = type;
    this.legacyId = legacyId;
    this.legacySourceTable = legacySourceTable;
  }

  /**
   * Constructor
   *
   * @param address The domain object to construct this object from
   */
  public Addresses(gov.ca.cwds.rest.api.domain.AddressIntakeApi address) {
    this.id = address.getId();
    this.streetAddress = address.getStreetAddress();
    this.city = address.getCity();
    this.state = address.getState();
    this.zip = address.getZip();
    this.type = address.getType();
    this.setLegacyIfo(address);    
  }

  /**
   * Constructor
   *
   * @param id - primary key
   * @param address - domain object to construct this object from
   */
  public Addresses(String id, gov.ca.cwds.rest.api.domain.AddressIntakeApi address) {
    this.id = id;
    this.streetAddress = address.getStreetAddress();
    this.city = address.getCity();
    this.state = address.getState();
    this.zip = address.getZip();
    this.type = address.getType();
    this.setLegacyIfo(address);
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
   * @return the streetAddress
   */
  public String getStreetAddress() {
    return streetAddress;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @return the zip
   */
  public String getZip() {
    return zip;
  }

  /**
   * @return the address type
   */
  public String getType() {
    return type;
  }

  /**
   * @return the legacy id
   */
  public String getLegacyId() {
    return legacyId;
  }

  /**
   * @return the legacy source table
   */
  public String getLegacySourceTable() {
    return legacySourceTable;
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

  public void setId(String id) {
    this.id = id;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }
  
  private void setLegacyIfo(gov.ca.cwds.rest.api.domain.AddressIntakeApi address) {
    this.legacyId = address.getLegacyId();
    this.legacySourceTable = address.getLegacySourceTable();
    LegacyDescriptor legacyDesc = address.getLegacyDescriptor();
    
    if (StringUtils.isBlank(this.legacyId)) {
      this.legacyId = legacyDesc != null ? legacyDesc.getId() : null;
    }
    
    if (StringUtils.isBlank(this.legacySourceTable)) {
      this.legacySourceTable = legacyDesc != null ? legacyDesc.getTableName() : null;
    }    
  }

}
