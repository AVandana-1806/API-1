package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} Class representing an DrmsDocument.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "DRMSDOCT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrmsDocument extends CmsPersistentObject {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Type(type = "timestamp")
  @Column(name = "CREATN_TS")
  private Date creationTimeStamp;

  @Column(name = "FKDOCTMPLT")
  private String drmsDocumentTemplateId;

  @Column(name = "FKFPSTFPRT")
  private String fingerprintStaffPerson;

  @Column(name = "FKSTFPERST")
  private String staffPersonId;

  @Column(name = "DOCHNDL_NM")
  private String handleName;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public DrmsDocument() {
    super();
  }

  /**
   * @param id - primary Key
   * @param creationTimeStamp - creationTimeStamp
   * @param drmsDocumentTemplateId - drmsDocumentTemplateId
   * @param fingerprintStaffPerson - fingerprintStaffPerson
   * @param staffPersonId - staffPersonId
   * @param handleName - handleName
   */
  public DrmsDocument(String id, Date creationTimeStamp, String drmsDocumentTemplateId,
      String fingerprintStaffPerson, String staffPersonId, String handleName) {
    super();
    this.id = id;
    this.creationTimeStamp = freshDate(creationTimeStamp);
    this.drmsDocumentTemplateId = drmsDocumentTemplateId;
    this.fingerprintStaffPerson = fingerprintStaffPerson;
    this.staffPersonId = staffPersonId;
    this.handleName = handleName;
  }

  /**
   * Constructor using domain
   * 
   * @param id The id
   * @param persisedDrmsDocument The domain object to construct this object from
   * @param lastUpdatedId The id of the last person to update this object
   * @param lastUpdatedTime the time when this object is last updated
   */
  public DrmsDocument(String id, gov.ca.cwds.rest.api.domain.cms.DrmsDocument persisedDrmsDocument,
      String lastUpdatedId, Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    this.id = id;
    this.creationTimeStamp = freshDate(persisedDrmsDocument.getCreationTimeStamp());
    this.drmsDocumentTemplateId = persisedDrmsDocument.getDrmsDocumentTemplateId();
    this.fingerprintStaffPerson = persisedDrmsDocument.getFingerprintStaffPerson();
    this.staffPersonId = persisedDrmsDocument.getStaffPersonId();
    this.handleName = persisedDrmsDocument.getHandleName();
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
  public String getId() {
    return id;
  }

  /**
   * @return the creationTimeStamp
   */
  public Date getCreationTimeStamp() {
    return freshDate(creationTimeStamp);
  }

  /**
   * @return the drmsDocumentTemplateId
   */
  public String getDrmsDocumentTemplateId() {
    return drmsDocumentTemplateId;
  }

  /**
   * @return the fingerprintStaffPerson
   */
  public String getFingerprintStaffPerson() {
    return fingerprintStaffPerson;
  }

  /**
   * @return the staffPersonId
   */
  public String getStaffPersonId() {
    return staffPersonId;
  }

  /**
   * @return the handleName
   */
  public String getHandleName() {
    return handleName;
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
