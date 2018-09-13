package gov.ca.cwds.data.persistence.ns;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * {@link PersistentObject} representing a version paper trail in Postgres.
 *
 * @author CWDS API Team
 */
@Entity
@Table(name = "versions")
public class PaperTrail extends ApiObjectIdentity implements PersistentObject {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "versions_id")
  @SequenceGenerator(name = "versions_id", sequenceName = "versions_id_seq", allocationSize = 1)
  private Long id;

  @Column(name = "item_type")
  private String itemType;

  @Column(name = "item_id")
  private Integer itemId;

  @Column(name = "event")
  private String event;

  @Column(name = "whodunnit")
  private String whoDunnIt;

  @Column(name = "object")
  @Type(type = "gov.ca.cwds.data.persistence.hibernate.StringJsonUserType")
  private String object;

  @Column(name = "object_changes")
  @Type(type = "gov.ca.cwds.data.persistence.hibernate.StringJsonUserType")
  private String objectChange;

  @Column(name = "created_at")
  private Date createdAt;

  public PaperTrail(String itemType, String itemId, String event) {
    this(itemType, Integer.valueOf(itemId), event);
  }

  public PaperTrail(String itemType, Integer itemId, String event) {
    this.itemType = itemType;
    this.itemId = itemId;
    this.event = event;
    this.whoDunnIt = RequestExecutionContext.instance().getStaffId();
    this.createdAt = new Date();
  }

  @Override
  public Long getPrimaryKey() {
    return getId();
  }

  public Long getId() {
    return id;
  }

  public String getItemType() {
    return itemType;
  }

  public Integer getItemId() {
    return itemId;
  }

  public String getEvent() {
    return event;
  }

  public String getWhoDunnIt() {
    return whoDunnIt;
  }

  public String getObject() {
    return object;
  }

  public String getObjectChange() {
    return objectChange;
  }

  public Date getCreatedAt() {
    return new Date(createdAt.getTime());
  }

}
